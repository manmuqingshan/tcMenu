/*
 * Copyright (c)  2016-2019 https://www.thecoderscorner.com (Nutricherry LTD).
 * This product is licensed under an Apache license, see the LICENSE file in the top-level directory.
 *
 */

package com.thecoderscorner.menu.editorui.generator.arduino;

import com.thecoderscorner.menu.domain.EditableTextMenuItemBuilder;
import com.thecoderscorner.menu.domain.MenuItem;
import com.thecoderscorner.menu.domain.state.MenuTree;
import com.thecoderscorner.menu.editorui.generator.CodeGeneratorOptions;
import com.thecoderscorner.menu.editorui.generator.core.CreatorProperty;
import com.thecoderscorner.menu.editorui.generator.core.NameAndKey;
import com.thecoderscorner.menu.editorui.generator.plugin.*;
import com.thecoderscorner.menu.editorui.generator.util.LibraryStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import static com.thecoderscorner.menu.editorui.generator.plugin.EmbeddedPlatform.ARDUINO32;
import static com.thecoderscorner.menu.editorui.generator.plugin.EmbeddedPlatform.ARDUINO_AVR;
import static com.thecoderscorner.menu.editorui.util.TestUtils.assertEqualsIgnoringCRLF;
import static com.thecoderscorner.menu.editorui.util.TestUtils.buildSimpleTreeReadOnly;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;

public class ArduinoGeneratorTest {

    private Path projectDir;
    private Path pluginDir;
    private Path rootDir;
    private CodePluginConfig pluginConfig;

    @BeforeEach
    public void setUp() throws Exception {
        rootDir = Files.createTempDirectory("tcmenutest");
        projectDir = rootDir.resolve("project");
        Files.createDirectories(projectDir);

        pluginDir = rootDir.resolve("plugin");
        pluginDir = DefaultXmlPluginLoaderTest.makeStandardPluginInPath(pluginDir);
        var embeddedPlatforms = new PluginEmbeddedPlatformsImpl();
        var loader = new DefaultXmlPluginLoader(embeddedPlatforms);
        pluginConfig = loader.loadPluginLib(pluginDir);

    }

    @AfterEach
    public void tearDown() throws Exception {
        Files.walk(rootDir)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @Test
    void testConversionForAvr() throws IOException {
        runConversionWith(ARDUINO_AVR, "/generator/template", false);
    }

    private MenuItem generateItemWithName(String name) {
        return EditableTextMenuItemBuilder.aTextMenuItemBuilder()
                .withId(11)
                .withName(name)
                .withEepromAddr(22)
                .withFunctionName(null)
                .withLength(10)
                .menuItem();
    }

    @Test
    void testConversionForSamd() throws IOException {
        runConversionWith(ARDUINO32, "/generator/template32", true);
    }

    @SuppressWarnings("unchecked")
    private void runConversionWith(EmbeddedPlatform platform, String templateToUse, boolean recursiveName) throws IOException {
        ArduinoSketchFileAdjuster adjuster = Mockito.mock(ArduinoSketchFileAdjuster.class);

        MenuTree tree = buildSimpleTreeReadOnly();
        ArduinoLibraryInstaller installer = Mockito.mock(ArduinoLibraryInstaller.class);
        Mockito.when(installer.statusOfAllLibraries()).thenReturn(new LibraryStatus(true, true, true));

        CodeGeneratorOptions standardOptions = new CodeGeneratorOptions(
                ARDUINO32.getBoardId(),
                "", "", "",
                List.<CreatorProperty>of(),
                UUID.randomUUID(),
                "app",
                recursiveName, false);
        ArduinoGenerator generator = new ArduinoGenerator(adjuster, installer, platform, standardOptions);

        var firstPlugin = pluginConfig.getPlugins().get(0);
        firstPlugin.getProperties().stream()
                .filter(p -> p.getName().equals("SWITCH_IODEVICE"))
                .findFirst()
                .ifPresent(p -> p.getProperty().setValue("io23017"));

        assertTrue(generator.startConversion(projectDir, pluginConfig.getPlugins(), tree,
                new NameAndKey("uuid1", "tester"), List.of(), false));

        assertEquals("GenState", generator.makeNameToVar(generateItemWithName("Gen &^%State")));
        assertEquals("ChannelÖôóò", generator.makeNameToVar(generateItemWithName("ChannelÖôóò")));

        var cppGenerated = new String(Files.readAllBytes(projectDir.resolve(projectDir.getFileName() + "_menu.cpp")));
        var hGenerated = new String(Files.readAllBytes(projectDir.resolve(projectDir.getFileName() + "_menu.h")));
        var pluginGeneratedH = new String(Files.readAllBytes(projectDir.resolve("source.h")));
        var pluginGeneratedCPP = new String(Files.readAllBytes(projectDir.resolve("source.cpp")));

        var cppTemplate = new String(getClass().getResourceAsStream(templateToUse + ".cpp").readAllBytes());
        var hTemplate = new String(getClass().getResourceAsStream(templateToUse + ".h").readAllBytes());

        cppGenerated = cppGenerated.replaceAll("#include \"tcmenu[^\"]*\"", "replacedInclude");
        cppTemplate = cppTemplate.replaceAll("#include \"tcmenu[^\"]*\"", "replacedInclude");

        // these files should line up. IF they do not because of the change in the ArduinoGenerator,
        // then make sure the change is good before adjusting the templates.
        assertEqualsIgnoringCRLF(cppTemplate, cppGenerated);
        assertEqualsIgnoringCRLF(hTemplate, hGenerated);
        assertEqualsIgnoringCRLF("CPP_FILE_CONTENT 10 otherKey", pluginGeneratedCPP);
        assertEqualsIgnoringCRLF("H_FILE_CONTENT 10 otherKey", pluginGeneratedH);

        Mockito.verify(adjuster).makeAdjustments(any(Consumer.class),
                eq(projectDir.resolve(projectDir.resolve(projectDir.getFileName() + ".ino")).toString()),
                eq(projectDir.getFileName().toString()), anyCollection());
    }
}