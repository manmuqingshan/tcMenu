package com.thecoderscorner.menu.editorui.cli;

import com.thecoderscorner.menu.editorui.util.StringHelper;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.zip.GZIPOutputStream;

import static com.thecoderscorner.menu.editorui.generator.core.CoreCodeGenerator.LINE_BREAK;

@CommandLine.Command(name="wrap-ws")
public class WrapWebServerFilesCommand implements Callable<Integer> {
    public enum CreationMode { REGULAR_ARDUINO }
    private final DateTimeFormatter httpFmt = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH).withZone(ZoneId.of("GMT"));

    @CommandLine.Option(names = {"-m", "--mode"}, description = "the webserver to create for")
    private CreationMode creationMode;
    @CommandLine.Option(names = {"-d", "--directory"}, description = "the source directory")
    private File srcDir;
    @CommandLine.Option(names = {"-a", "--want-maps"}, defaultValue = "false", description = "Keep map files in output")
    private boolean wantMaps;
    @CommandLine.Option(names = {"-o", "--output"}, defaultValue = "", description = "Optionally provide the output file name")
    private String outputFile;

    @Override
    public Integer call() throws Exception {
        System.out.println("Wrapping web files in " + srcDir + " for mode " + creationMode);

        // skip hidden files, directories and map files (unless map file mode is turned on)
        var allFiles = Files.walk(srcDir.toPath(), FileVisitOption.FOLLOW_LINKS)
                .filter(path -> !path.toFile().isDirectory() && !path.getFileName().toString().startsWith("."))
                .collect(Collectors.toList());

        StringBuilder sbVariables = new StringBuilder(8192);
        StringBuilder sbFunctions = new StringBuilder(8192);

        sbVariables.append("#include <Arduino.h>").append(LINE_BREAK);
        sbVariables.append("#include <remote/TcMenuWebServer.h>").append(LINE_BREAK).append(LINE_BREAK);

        sbVariables.append("// Generated by tcMenu Designer wrap-ws command on ").append(LocalDateTime.now()).append(LINE_BREAK);
        sbVariables.append("// use tcmenu wrap-ws cli command to regenerate ").append(LINE_BREAK).append(LINE_BREAK);
        sbVariables.append("using namespace tcremote;").append(LINE_BREAK);
        sbVariables.append("const char lastModifiedStatic[] = \"").append(httpFmt.format(Instant.now())).append("\";")
                .append(LINE_BREAK).append(LINE_BREAK);

        sbFunctions.append("void prepareWebServer(TcMenuWebServer& webServer) {").append(LINE_BREAK);

        int entireSize = 0;
        for (var file : allFiles) {
            if ((!wantMaps && file.toString().endsWith(".map"))) {
                System.out.println("Skipping map file " + file);
                continue;
            }

            boolean binFile = checkFileIsBinary(file);

            var theLocalPath = file.toString().replace(srcDir.toPath().toString(), "");
            theLocalPath = theLocalPath.replace('\\', '/');
            var varName = "webs" + theLocalPath.replaceAll("[^a-zA-Z0-9]", "_");

            long size = Files.size(file);
            var shouldGzip = size > 2000 && !binFile;

            sbVariables.append(toVariableForPlatform(varName, binFile, shouldGzip));
            sbVariables.append(LINE_BREAK);

            if (shouldGzip) {
                binFile = true;
                var byteStream = new ByteArrayOutputStream((int) size);
                try (var gzipStream = new GZIPOutputStream(byteStream)) {
                    var allBytes = Files.readAllBytes(file);
                    gzipStream.write(allBytes);
                }
                byte[] allBytes = byteStream.toByteArray();
                System.out.println("Zipped " + file + " before " + size + ", after " + allBytes.length);
                processAsBinary(sbVariables, allBytes);
                entireSize += allBytes.length;
                varName += "_gz";
            } else if (!binFile) {
                System.out.println("Small text " + file + " size is " + size);
                var lines = Files.readAllLines(file);
                lines.forEach(l -> sbVariables.append("    \"").append(l.replace("\"", "\\\""))
                        .append("\" \\").append(LINE_BREAK));
                entireSize += size;
            } else {
                System.out.println("Binary " + file + " size is " + size);
                var allBytes = Files.readAllBytes(file);
                processAsBinary(sbVariables, allBytes);
                entireSize += size;
            }
            sbVariables.append("};").append(LINE_BREAK);

            sbFunctions.append(writeServeLogicFor(theLocalPath, varName, binFile, shouldGzip));
        }

        sbVariables.append(LINE_BREAK);
        sbFunctions.append("}").append(LINE_BREAK);
        sbFunctions.append(LINE_BREAK);
        sbFunctions.append("// Add the following forward-definition to your code if you're calling this yourself");
        sbFunctions.append(LINE_BREAK);
        sbFunctions.append("// void prepareWebServer(AsyncWebServer& server); ");
        sbFunctions.append(LINE_BREAK);

        Path outputPath;
        if (!StringHelper.isStringEmptyOrNull(outputFile)) {
            outputPath = Paths.get(outputFile);
        } else {
            outputPath = srcDir.toPath().resolve("esp_ws.cpp");
        }
        Files.writeString(outputPath, sbVariables + sbFunctions.toString(), StandardOpenOption.CREATE);

        System.out.println("Written file " + outputPath + ". Approx memory requirement for data is " + entireSize);
        System.out.println("If you need to use this directly in your project, forward declare as follows:");
        System.out.println("   void prepareWebServer(AsyncWebServer& server);");

        return 0;
    }

    private void processAsBinary(StringBuilder sbVariables, byte[] allBytes) {
        for(int i = 0; i< allBytes.length; i++) {
            sbVariables.append(String.format("0x%02x", allBytes[i]));
            if(i != (allBytes.length -1)) {
                sbVariables.append(",");
            }
            else {
                sbVariables.append("\n");
            }

            if((i%20)==19) {
                sbVariables.append("\n");
            }
        }
    }

    private String writeServeLogicFor(String theLocalPath, String varName, boolean binary, boolean gzipped) {
        String mime = mimeTypeFor(theLocalPath);
        String requestProcessing;
        if(gzipped) {
            requestProcessing = "        response.startHeader();" + LINE_BREAK +
                                "        response.setHeader(WSH_LAST_MODIFIED, lastModifiedStatic);" + LINE_BREAK +
                                "        response.setHeader(WSH_CONTENT_ENCODING, \"gzip\");" + LINE_BREAK +
                                "        response.setHeader(WSH_CACHE_CONTROL, \"max-age=2592000\");" + LINE_BREAK +
                                "        response.contentInfo(WebServerResponse::" + mime + ", sizeof(" + varName + ") - 1);" + LINE_BREAK +
                                "        response.send_P(" + varName + ", sizeof(" + varName + ") - 1);"+ LINE_BREAK;

        } else if(binary) {
             requestProcessing = "        response.startHeader();" + LINE_BREAK +
                                 "        response.setHeader(WSH_LAST_MODIFIED, lastModifiedStatic);" + LINE_BREAK +
                                 "        response.setHeader(WSH_CACHE_CONTROL, \"max-age=2592000\");" + LINE_BREAK +
                                 "        response.contentInfo(WebServerResponse::" + mime + ", sizeof(" + varName + ") - 1);" + LINE_BREAK +
                                 "        response.send_P(" + varName + ", sizeof(" + varName + ") - 1);"+ LINE_BREAK;
        } else {
             requestProcessing = "        response.startHeader();" + LINE_BREAK +
                                 "        response.setHeader(WSH_LAST_MODIFIED, lastModifiedStatic);" + LINE_BREAK +
                                 "        response.contentInfo(WebServerResponse::" + mime + ", sizeof(" + varName + ") - 1);" + LINE_BREAK +
                                 "        response.send_P(" + varName + ", sizeof(" + varName + ") - 1);"+ LINE_BREAK;
        }

        return "    webServer.onUrlGet(\"" + theLocalPath + "\", [](WebServerResponse& response) {" + LINE_BREAK +
               requestProcessing + "    });" + LINE_BREAK;
    }

    private String mimeTypeFor(String p) {
        String strPath = p.toLowerCase();

        if(strPath.endsWith(".png")) return "PNG_IMAGE";
        else if(strPath.endsWith(".jpg")) return "JPG_IMAGE";
        else if(strPath.endsWith(".webp")) return "WEBP_IMAGE";
        else if(strPath.endsWith(".ico")) return "IMG_ICON";
        else if(strPath.endsWith(".js")) return "JAVASCRIPT";
        else if(strPath.endsWith(".json")) return "JSON_TEXT";
        else if(strPath.endsWith(".css")) return "TEXT_CSS";
        else if(strPath.endsWith(".html")) return "HTML_TEXT";
        else return "PLAIN_TEXT";
    }

    private boolean checkFileIsBinary(Path file) {
        var strFile = file.toString().toLowerCase();
        return strFile.endsWith(".jpg") || strFile.endsWith(".png") || strFile.endsWith(".ico");
    }

    private String toVariableForPlatform(String varName,  boolean bin, boolean shouldGZip) {
        var reqType = bin || shouldGZip ? "uint8_t" : "char";
        var gzExt = shouldGZip ? "_gz" : "";
        return String.format("const %s %s%s[] PROGMEM = {", reqType, varName, gzExt);
    }
}
