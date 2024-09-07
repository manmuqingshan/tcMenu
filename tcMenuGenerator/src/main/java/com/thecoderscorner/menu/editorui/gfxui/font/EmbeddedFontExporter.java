package com.thecoderscorner.menu.editorui.gfxui.font;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;

import static com.thecoderscorner.menu.editorui.generator.core.CoreCodeGenerator.LINE_BREAK;
import static com.thecoderscorner.menu.editorui.util.StringHelper.printArrayToStream;

/**
 * The EmbeddedFontExporter class is a FontEncoder implementation that exports embedded fonts.
 * It takes an EmbeddedFont object and an output name as parameters and generates font data in various formats.
 * The font data takes the form of a series of C++ arrays and structs.
 */
public class EmbeddedFontExporter implements FontEncoder {
    private final EmbeddedFont font;
    private final List<TcUnicodeFontBlock> blocks;
    private final int yAdvance;
    private final String variableName;

    /**
     * Represents an exporter for an embedded font. This class is used to export
     * an embedded font to a desired output format.
     * @param font the embedded font object to convert.
     * @param outputName the base name of the structs to be created
     */
    public EmbeddedFontExporter(EmbeddedFont font, String outputName) {
        this.font = font;
        this.variableName = outputName;
        var blocks = new ArrayList<TcUnicodeFontBlock>();
        var availableMappings = font.getAvailableMappings().stream()
                .sorted(Comparator.comparingInt(UnicodeBlockMapping::getStartingCode))
                .toList();
        for(var blockMapping : availableMappings) {
            var glyphsInBlock = new ArrayList<TcUnicodeFontGlyph>();
            var sortedGlyphs = font.getGlyphsForBlock(blockMapping).stream()
                    .filter(EmbeddedFontGlyph::selected)
                    .sorted(Comparator.comparingInt(EmbeddedFontGlyph::code)).toList();

            for(var rawGlyph : sortedGlyphs) {
                int totalHeight = font.getYAdvance() - font.getBelowBaseline();
                glyphsInBlock.add(new TcUnicodeFontGlyph(rawGlyph.code(), rawGlyph.rawData(), rawGlyph.fontDims().width(),
                        rawGlyph.fontDims().height(), rawGlyph.totalWidth(),
                        rawGlyph.fontDims().startX(), -rawGlyph.fontDims().startY()));
            }

            blocks.add(new TcUnicodeFontBlock(blockMapping, glyphsInBlock));
        }
        this.blocks = blocks;
        this.yAdvance = font.getYAdvance();
    }

    List<TcUnicodeFontGlyph> glyphsFromAllBlocks() {
        return blocks.stream().flatMap(b -> b.glyphs().stream())
                .sorted(Comparator.comparingInt(TcUnicodeFontGlyph::charNum))
                .toList();
    }

    /**
     * Encodes the font into the given output stream in the specified format.
     *
     * @param stream the output stream to write the encoded font to
     * @param fmt the format in which the font should be encoded
     */
    @Override
    public void encodeFontToStream(OutputStream stream, FontFormat fmt) {
        var ps = new PrintStream(stream);
        ps.println("// Font file generated by theCodersCorner.com Font Generator");
        ps.printf("// Format:           %s%n", fmt);
        ps.printf("// Approximate size: %d bytes%n", font.fontSizeInBytes(fmt));
        ps.printf("// Source file:      %s%n", font.getFontName());
        ps.printf("// Point size:       %dpt%n", font.getSize());
        ps.printf("// Variable name:    %s%n", variableName);
        ps.println();
        switch (fmt) {
            case ADAFRUIT -> encodeAdafruit(ps);
            case TC_UNICODE -> encodeTcUnicode(ps);
        }
    }

    private void encodeAdafruit(PrintStream ps) {
        ps.printf("const uint8_t %sBitmaps[] PROGMEM = {%n", variableName);
        List<TcUnicodeFontGlyph> allGlyphs = glyphsFromAllBlocks();
        printByteArray(ps, allGlyphs);
        ps.println("};");
        ps.println();

        ps.println("const GFXglyph %sGlyphs[] PROGMEM = {".formatted(variableName));
        int min = allGlyphs.getFirst().charNum();
        int max = allGlyphs.stream().map(TcUnicodeFontGlyph::charNum).reduce(0, Integer::max);

        int bmpOffset = 0;
        boolean first = true;
        int lastCode = -1;
        for (var item : allGlyphs) {
            if (!first) {
                ps.println(",");
            }
            first = false;
            while (lastCode != -1 && item.charNum != (lastCode + 1)) {
                // adafruit cannot have gaps, we need to fill in any holes in the range. Usually OK for ASCII
                ps.printf("    { %d, %d, %d, %d, %d, %d }, /* empty fill %d */", bmpOffset, 0, 0, 0, 0, 0, lastCode + 1);
                ps.println();
                lastCode = lastCode + 1;
            }
            lastCode = item.charNum();
            ps.printf("    { %d, %d, %d, %d, %d, %d } /* %s %d */", bmpOffset, item.width(),
                    item.height(), item.xAdvance(), item.xOffset(), item.yOffset(),
                    Arrays.toString(Character.toChars(item.charNum())), item.charNum());
            bmpOffset += item.bitmapData().length;
        }
        ps.println();
        ps.println("};");
        ps.println();
        ps.printf("const GFXfont %s PROGMEM = {%n", variableName);
        ps.printf("    (uint8_t*)%sBitmaps,%n", variableName);
        ps.printf("    (GFXglyph*)%sGlyphs,%n", variableName);
        ps.printf("    %d, %d,%n", min, max);
        ps.printf("    %d%n", yAdvance);
        ps.println("};");
    }

    private void encodeTcUnicode(PrintStream ps) {
        ps.println("#include <UnicodeFontDefs.h>");
        ps.println();

        var blockData = new ArrayList<String>();
        var sortedBlocks = blocks.stream()
                .sorted(Comparator.comparingInt(TcUnicodeFontBlock::firstUnicodeCharacter).reversed())
                .toList();

        for (var block : sortedBlocks) {
            ps.println("// Bitmaps for "+ block.mapping());
            ps.printf("const uint8_t %sBitmaps_%d[] PROGMEM = {", variableName, block.mapping().ordinal());
            ps.println();
            printByteArray(ps, block.glyphs());
            ps.println("};");
            ps.println();

            blockData.add(String.format("    {%d, %sBitmaps_%d, %2$sGlyphs_%3$d, %d} /* %s */",
                    block.mapping().getStartingCode(), variableName, block.mapping().ordinal(),
                    block.mapping().getEndingCode() - block.mapping().getStartingCode(), block.mapping()));

            ps.println("// Glyphs for " + block.mapping());
            ps.printf("const UnicodeFontGlyph %sGlyphs_%d[] PROGMEM = {", variableName, block.mapping().ordinal());
            ps.println();

            int bmpOffset = 0;
            boolean first = true;
            for (var item : block.glyphs()) {
                if (!first) {
                    ps.println(",");
                }
                first = false;
                ps.printf("    { %d, %d, %d, %d, %d, %d, %d} /* %s %d*/ ",
                        item.charNum() - block.mapping().getStartingCode(),
                        bmpOffset, item.width(),
                        item.height(), item.xAdvance(), item.xOffset(), item.yOffset(),
                        Arrays.toString(Character.toChars(item.charNum())), item.charNum());
                bmpOffset += item.bitmapData().length;
            }
            ps.println();
            ps.println("};");
            ps.println();
        }

        ps.printf("const UnicodeFontBlock %sBlocks[] PROGMEM = {%n", variableName);
        ps.println(blockData.stream().collect(Collectors.joining("," + LINE_BREAK)));
        ps.println("};");
        ps.println();

        ps.printf("const UnicodeFont %s[] PROGMEM = { {%sBlocks, %d, %d, TCFONT_ONE_BIT_PER_PIXEL} };", variableName, variableName, blocks.size(), yAdvance);
        ps.println();
    }

    private void printByteArray(PrintStream ps, List<TcUnicodeFontGlyph> glyphs) {
        int dataSize = glyphs.stream().map(i -> i.bitmapData().length).reduce(0, Integer::sum);
        byte[] dataBytes = new byte[dataSize];
        int current = 0;
        for (var item : glyphs) {
            System.arraycopy(item.bitmapData(), 0, dataBytes, current, item.bitmapData().length);
            current += item.bitmapData().length;
        }

        printArrayToStream(ps, dataBytes, 20);
    }

    public EmbeddedFont font() {
        return font;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (EmbeddedFontExporter) obj;
        return Objects.equals(this.font, that.font) &&
                Objects.equals(this.blocks, that.blocks) &&
                this.yAdvance == that.yAdvance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(font, blocks, yAdvance);
    }

    @Override
    public String toString() {
        return "EmbeddedFontExporter[font=%s, blocks=%s, yAdvance=%d]".formatted(font, blocks, yAdvance);
    }


    public record TcUnicodeFontGlyph(int charNum, byte[] bitmapData, int width, int height, int xAdvance, int xOffset,
                                     int yOffset) {
    }

    public record TcUnicodeFontBlock(UnicodeBlockMapping mapping, List<TcUnicodeFontGlyph> glyphs) {
        int firstUnicodeCharacter() {
            return mapping.getStartingCode();
        }
    }
}
