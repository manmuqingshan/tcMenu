package com.thecoderscorner.menu.editorui.generator.font;

public enum UnicodeBlockMapping {
    BASIC_LATIN(0x0000, 0x007F, "Basic Latin"),
    LATIN1_SUPPLEMENT(0x0080, 0x00FF, "Latin-1 Supplement"),
    LATIN_EXTENDED_A(0x0100, 0x017F, "Latin Extended-A"),
    LATIN_EXTENDED_B(0x0180, 0x024F, "Latin Extended-B"),
    IPA_EXTENSIONS(0x0250, 0x02AF, "IPA Extensions"),
    SPACING_MODIFIER_LETTER(0x02B0, 0x02FF, "Spacing Modifier Letters"),
    COMBINING_DIACRITICAL_MARKS(0x0300, 0x036F, "Combining Diacritical Marks"),
    GREEK_COPTIC(0x0370, 0x03FF, "Greek and Coptic"),
    CYRILLIC(0x0400, 0x04FF, "Cyrillic"),
    CYRILLIC_SUPPLEMENT(0x0500, 0x052F, "Cyrillic Supplement"),
    ARMENIAN(0x0530, 0x058F, "Armenian"),
    HEBREW(0x0590, 0x05FF, "Hebrew"),
    ARABIC(0x0600, 0x06FF, "Arabic"),
    SYRAIC(0x0700, 0x074F, "Syriac"),
    ARABIC_SUPPLEMENT(0x0750, 0x077F, "Arabic Supplement"),
    THAANA(0x0780, 0x07BF, "Thaana"),
    KNO(0x07C0, 0x07FF, "NKo"),
    SAMARITAN(0x0800, 0x083F, "Samaritan"),
    MANDAIC(0x0840, 0x085F, "Mandaic"),
    SYRIAC_SUPPLEMENT(0x0860, 0x086F, "Syriac Supplement"),
    ARABIC_EXTENDED_B(0x0870, 0x089F, "Arabic Extended-B"),
    ARABIC_EXTENDED_A(0x08A0, 0x08FF, "Arabic Extended-A"),
    DEVANAGARI(0x0900, 0x097F, "Devanagari"),
    BENGALI(0x0980, 0x09FF, "Bengali"),
    GURMUKHI(0x0A00, 0x0A7F, "Gurmukhi"),
    GUJARATI(0x0A80, 0x0AFF, "Gujarati"),
    ORIYA(0x0B00, 0x0B7F, "Oriya"),
    TAMIL(0x0B80, 0x0BFF, "Tamil"),
    TELUGU(0x0C00, 0x0C7F, "Telugu"),
    KANNADA(0x0C80, 0x0CFF, "Kannada"),
    MALAYALAM(0x0D00, 0x0D7F, "Malayalam"),
    SINHALA(0x0D80, 0x0DFF, "Sinhala"),
    THAI(0x0E00, 0x0E7F, "Thai"),
    LAO(0x0E80, 0x0EFF, "Lao"),
    TIBETAN(0x0F00, 0x0FFF, "Tibetan"),
    MYANMAR(0x1000, 0x109F, "Myanmar"),
    GEORGIAN(0x10A0, 0x10FF, "Georgian"),
    HANGUL_JAMO(0x1100, 0x11FF, "Hangul Jamo"),
    ETHIOPIC(0x1200, 0x137F, "Ethiopic"),
    ETHIOPIC_SUPPLEMENT(0x1380, 0x139F, "Ethiopic Supplement"),
    CHEROKEE(0x13A0, 0x13FF, "Cherokee"),
    UNIFIED_CANADIAN_ABORIGINAL_SYM(0x1400, 0x167F, "Unified Canadian Aboriginal Syllabics"),
    OGHAM(0x1680, 0x169F, "Ogham"),
    RUNIC(0x16A0, 0x16FF, "Runic"),
    TAGALOG(0x1700, 0x171F, "Tagalog"),
    HANUNOO(0x1720, 0x173F, "Hanunoo"),
    BUHID(0x1740, 0x175F, "Buhid"),
    TAGBANWA(0x1760, 0x177F, "Tagbanwa"),
    KHMER(0x1780, 0x17FF, "Khmer"),
    MONGOLIAN(0x1800, 0x18AF, "Mongolian"),
    UNIFIED_CANADIAN_ABORIGINAL_SYLL_EXT(0x18B0, 0x18FF, "Unified Canadian Aboriginal Syllabics Extended"),
    LIMBU(0x1900, 0x194F, "Limbu"),
    TAI_LE(0x1950, 0x197F, "Tai Le"),
    NEW_TAI_LE(0x1980, 0x19DF, "New Tai Lue"),
    KHMER_SYMBOLS(0x19E0, 0x19FF, "Khmer Symbols"),
    BUGINESE(0x1A00, 0x1A1F, "Buginese"),
    TAI_THAM(0x1A20, 0x1AAF, "Tai Tham"),
    COMBINING_DIACRITICAL_MARKS_EXT(0x1AB0, 0x1AFF, "Combining Diacritical Marks Extended"),
    BALINESE(0x1B00, 0x1B7F, "Balinese"),
    SUDANESE(0x1B80, 0x1BBF, "Sundanese"),
    BATAK(0x1BC0, 0x1BFF, "Batak"),
    LEPCHA(0x1C00, 0x1C4F, "Lepcha"),
    OL_CHIKA(0x1C50, 0x1C7F, "Ol Chiki"),
    CYRILLIC_EXT_C(0x1C80, 0x1C8F, "Cyrillic Extended-C"),
    GEORGIAN_EXT(0x1C90, 0x1CBF, "Georgian Extended"),
    SUDANESE_SUPPLEMENT(0x1CC0, 0x1CCF, "Sundanese Supplement"),
    VEDIC_EXT(0x1CD0, 0x1CFF, "Vedic Extensions"),
    PHONETIC_EXT(0x1D00, 0x1D7F, "Phonetic Extensions"),
    PHONETIC_EXT_SUPPLEMENT(0x1D80, 0x1DBF, "Phonetic Extensions Supplement"),
    COMBINING_DIACRITICAL_MARKS_SUPPLEMENT(0x1DC0, 0x1DFF, "Combining Diacritical Marks Supplement"),
    LATIN_EXTENDED_ADDITIONAL(0x1E00, 0x1EFF, "Latin Extended Additional"),
    GREEK_EXTENDED(0x1F00, 0x1FFF, "Greek Extended"),
    GENERAL_PUNCTUATION(0x2000, 0x206F, "General Punctuation"),
    SUPERSCRIPTS_SUBSCRIPTS(0x2070, 0x209F, "Superscripts and Subscripts"),
    CURRENCY_SYMBOLS(0x20A0, 0x20CF, "Currency Symbols"),
    COMBINING_DIACRITICAL_MARKS_SYMBOLS(0x20D0, 0x20FF, "Combining Diacritical Marks for Symbols"),
    LETTER_LIKE_SYMBOLS(0x2100, 0x214F, "Letterlike Symbols"),
    NUMBER_FORMS(0x2150, 0x218F, "Number Forms"),
    ARROWS(0x2190, 0x21FF, "Arrows"),
    MATHEMATICAL_OPERATIONS(0x2200, 0x22FF, "Mathematical Operators"),
    MISCELLANEOUS_TECHNICAL(0x2300, 0x23FF, "Miscellaneous Technical"),
    CONTROL_PICTURES(0x2400, 0x243F, "Control Pictures"),
    OPTICAL_CHARACTER_RECOGNITION(0x2440, 0x245F, "Optical Character Recognition"),
    ENCLOSED_ALPHANUMERICS(0x2460, 0x24FF, "Enclosed Alphanumerics"),
    BOX_DRAWING(0x2500, 0x257F, "Box Drawing"),
    BLOCK_SHAPES(0x2580, 0x259F, "Block Elements"),
    GEOMETRIC_SHAPES(0x25A0, 0x25FF, "Geometric Shapes"),
    MISC_SYMBOLS(0x2600, 0x26FF, "Miscellaneous Symbols"),
    DINGBATS(0x2700, 0x27BF, "Dingbats"),
    MISC_MATHS_SYMBOLS(0x27C0, 0x27EF, "Miscellaneous Mathematical Symbols-A"),
    SUPPLEMENTAL_ARROWS_A(0x27F0, 0x27FF, "Supplemental Arrows-A"),
    BRAILLE_PATTERNS(0x2800, 0x28FF, "Braille Patterns"),
    SUPPLEMENTAL_ARROWS_B(0x2900, 0x297F, "Supplemental Arrows-B"),
    MISC_MATHS_SYMBOLS_B(0x2980, 0x29FF, "Miscellaneous Mathematical Symbols-B"),
    SUPPLEMENTAL_MATHS_OPERATORS(0x2A00, 0x2AFF, "Supplemental Mathematical Operators"),
    MISCELLANEOUS_SYMBOLS_ARROWS(0x2B00, 0x2BFF, "Miscellaneous Symbols and Arrows"),
    GLAGOLITIC(0x2C00, 0x2C5F, "Glagolitic"),
    LATIN_EXTENDED_C(0x2C60, 0x2C7F, "Latin Extended-C"),
    COPTIC(0x2C80, 0x2CFF, "Coptic"),
    GEORGIAN_SUPPLEMENT(0x2D00, 0x2D2F, "Georgian Supplement"),
    TIFINAGH(0x2D30, 0x2D7F, "Tifinagh"),
    ETHIOPIC_EXTENDED(0x2D80, 0x2DDF, "Ethiopic Extended"),
    CYRILLIC_EXTENDED_A(0x2DE0, 0x2DFF, "Cyrillic Extended-A"),
    SUPPLEMENTAL_PUNCTUATION(0x2E00, 0x2E7F, "Supplemental Punctuation"),
    CJK_RADICALS_SUPPLEMENT(0x2E80, 0x2EFF, "CJK Radicals Supplement"),
    KANGXI_RADICALS(0x2F00, 0x2FDF, "Kangxi Radicals"),
    IDEOGRAPHIC_DESCRIPTION_CHARS(0X2FF0, 0X2FFF, "Ideographic Description Characters"),
    CJK_SYMBOLS_PUNCTUATION(0X3000, 0X303F, "CJK Symbols and Punctuation"),
    HIRAGANA(0X3040, 0X309F, "Hiragana"),
    KATAKANA(0X30A0, 0X30FF, "Katakana"),
    BOPOMOFO(0X3100, 0X312F, "Bopomofo"),
    HUNGUL_COMPATIBILITY_JAMO(0X3130, 0X318F, "Hangul Compatibility Jamo"),
    KANBUN(0X3190, 0X319F, "Kanbun"),
    BOPOMOFO_EXTENDED(0X31A0, 0X31BF, "Bopomofo Extended"),
    CJK_STROKES(0X31C0, 0X31EF, "CJK Strokes"),
    KATAKANA_PHONETIC_EXT(0X31F0, 0X31FF, "Katakana Phonetic Extensions"),
    ENCLOSED_CJK_LETTERS_MONTHS(0X3200, 0X32FF, "Enclosed CJK Letters and Months"),
    CJK_COMPATIBILITY(0X3300, 0X33FF, "CJK Compatibility"),
    CJK_UNIFIED_IDEOGRAPHS_EXT_A(0X3400, 0X4DBF, "CJK Unified Ideographs Extension A"),
    YIJING_HEXAGRAM_SYMBOLS(0X4DC0, 0X4DFF, "Yijing Hexagram Symbols"),
    CJK_UNIFIED_IDEOGRAPHS(0X4E00, 0X9FFF, "CJK Unified Ideographs"),
    YI_SYLLABLES(0XA000, 0XA48F, "Yi Syllables"),
    YI_RADICALS(0XA490, 0XA4CF, "Yi Radicals"),
    LISU(0XA4D0, 0XA4FF, "Lisu"),
    VAI(0XA500, 0XA63F, "Vai"),
    CYRILLIC_EXTENDED_B(0XA640, 0XA69F, "Cyrillic Extended-B"),
    BAMUM(0XA6A0, 0XA6FF, "Bamum"),
    MODIFIER_TONE_LETTERS(0XA700, 0XA71F, "Modifier Tone Letters"),
    LATIN_EXTENDED_D(0XA720, 0XA7FF, "Latin Extended-D"),
    SYLOTI_NAGRI(0XA800, 0XA82F, "Syloti Nagri"),
    COMMON_INDIC_NUMBER_FORMS(0XA830, 0XA83F, "Common Indic Number Forms"),
    PHAGS_PA(0XA840, 0XA87F, "Phags-pa"),
    SAURASHTRA(0XA880, 0XA8DF, "Saurashtra"),
    DEVANAGARI_EXTENDED(0XA8E0, 0XA8FF, "Devanagari Extended"),
    KAYAH_LI(0XA900, 0XA92F, "Kayah Li"),
    REJANG(0XA930, 0XA95F, "Rejang"),
    HANGUL_JAMO_EXTENDED_A(0XA960, 0XA97F, "Hangul Jamo Extended-A"),
    JAVANESE(0XA980, 0XA9DF, "Javanese"),
    MYANMAR_EXTENDED_B(0XA9E0, 0XA9FF, "Myanmar Extended-B"),
    CHAM(0XAA00, 0XAA5F, "Cham"),
    MYANMAR_EXTENDED_A(0XAA60, 0XAA7F, "Myanmar Extended-A"),
    TAI_VIET(0XAA80, 0XAADF, "Tai Viet"),
    MEETEI_MAYEK_EXT(0XAAE0, 0XAAFF, "Meetei Mayek Extensions"),
    ETHIOPIC_EXTENDED_A(0XAB00, 0XAB2F, "Ethiopic Extended-A"),
    LATIN_EXTENDED_E(0XAB30, 0XAB6F, "Latin Extended-E"),
    CHEROKEE_SUPPLEMENT(0XAB70, 0XABBF, "Cherokee Supplement"),
    MEETEI_MAYEK(0XABC0, 0XABFF, "Meetei Mayek"),
    HANGUL_SYLLABLES(0xAC00, 0xD7AF, "Hangul Syllables"),
    HANGUL_JAMO_EXTENDED_B(0xD7B0, 0xD7FF, "Hangul Jamo Extended-B"),
    HIGH_SURROGATES(0xD800, 0xDB7F, "High Surrogates"),
    HIGH_PRIVATE_USE_SURROGATES(0xDB80, 0xDBFF, "High Private Use Surrogates"),
    LOW_SURROGATES(0xDC00, 0xDFFF, "Low Surrogates"),
    PRIVATE_USE_AREA(0xE000, 0xF8FF, "Private Use Area"),
    CJK_COMPATIBILITY_IDEOGRAPHS(0xF900, 0xFAFF, "CJK Compatibility Ideographs"),
    ALPHABETIC_PRESENTATION_FORMS(0xFB00, 0xFB4F, "Alphabetic Presentation Forms"),
    ARABIC_PRESENTATION_FORMS_A(0xFB50, 0xFDFF, "Arabic Presentation Forms-A"),
    VARIATION_SELECTORS(0xFE00, 0xFE0F, "Variation Selectors"),
    VERTICAL_FORMS(0xFE10, 0xFE1F, "Vertical Forms"),
    COMBINING_HALF_MARKS(0xFE20, 0xFE2F, "Combining Half Marks"),
    CJK_COMPATIBILITY_FORMS(0xFE30, 0xFE4F, "CJK Compatibility Forms"),
    SMALL_FORM_VARIANTS(0xFE50, 0xFE6F, "Small Form Variants"),
    ARABIC_PRESENTATION_FORMS_B(0xFE70, 0xFEFF, "Arabic Presentation Forms-B"),
    HALF_AND_FULL_WIDTH_FORMS(0xFF00, 0xFFEF, "Halfwidth and Fullwidth Forms"),
    SPECIALS(0xFFF0, 0xFFFF, "Specials"),
    LINEAR_B_SYLLABARY(0x10000, 0x1007F, "Linear B Syllabary"),
    LINEAR_B_IDEOGRAMS(0x10080, 0x100FF, "Linear B Ideograms"),
    AEGEAN_NUMBERS(0x10100, 0x1013F, "Aegean Numbers"),
    ANCIENT_GREEK_NUMBERS(0x10140, 0x1018F, "Ancient Greek Numbers"),
    ANCIENT_SYMBOLS(0x10190, 0x101CF, "Ancient Symbols"),
    PHAISTOS_DISC(0x101D0, 0x101FF, "Phaistos Disc"),
    LYCIAN(0x10280, 0x1029F, "Lycian"),
    CARIAN(0x102A0, 0x102DF, "Carian"),
    COPTIC_EPACT_NUMBERS(0x102E0, 0x102FF, "Coptic Epact Numbers"),
    OLD_ITALIC(0x10300, 0x1032F, "Old Italic"),
    GOTHIC(0x10330, 0x1034F, "Gothic"),
    OLD_PERMIC(0x10350, 0x1037F, "Old Permic"),
    UGARITIC(0x10380, 0x1039F, "Ugaritic"),
    OLD_PERSIAN(0x103A0, 0x103DF, "Old Persian"),
    DESERET(0x10400, 0x1044F, "Deseret"),
    SHAVIAN(0x10450, 0x1047F, "Shavian"),
    OSMANYA(0x10480, 0x104AF, "Osmanya"),
    OSAGE(0x104B0, 0x104FF, "Osage"),
    ELBASAN(0x10500, 0x1052F, "Elbasan"),
    CAUCASIAN_ALBANIAN(0x10530, 0x1056F, "Caucasian Albanian"),
    VITHKUGI(0x10570, 0x105BF, "Vithkuqi"),
    LINEAR_A(0x10600, 0x1077F, "Linear A"),
    LATIN_EXT_F(0x10780, 0x107BF, "Latin Extended-F"),
    CYPRIOT_SYLLABARY(0x10800, 0x1083F, "Cypriot Syllabary"),
    IMPERIAL_ARAMAIC(0x10840, 0x1085F, "Imperial Aramaic"),
    PALMYRENE(0x10860, 0x1087F, "Palmyrene"),
    NABATAEAN(0x10880, 0x108AF, "Nabataean"),
    HATRAN(0x108E0, 0x108FF, "Hatran"),
    PHOENICAIN(0x10900, 0x1091F, "Phoenician"),
    LYDIAN(0x10920, 0x1093F, "Lydian"),
    MEROITIC_HIEROGLYPHS(0x10980, 0x1099F, "Meroitic Hieroglyphs"),
    MEROITIC_CURSIVE(0x109A0, 0x109FF, "Meroitic Cursive"),
    KHAROSHTHI(0x10A00, 0x10A5F, "Kharoshthi"),
    OLD_SOUTH_ARABIAN(0x10A60, 0x10A7F, "Old South Arabian"),
    OLD_NORTH_ARABIAN(0x10A80, 0x10A9F, "Old North Arabian"),
    MANICHAEAN(0x10AC0, 0x10AFF, "Manichaean"),
    AVESTAN(0x10B00, 0x10B3F, "Avestan"),
    INSCRIPTIONAL_PARTHIAN(0x10B40, 0x10B5F, "Inscriptional Parthian"),
    INSCRIPTIONAL_PAHLAVI(0x10B60, 0x10B7F, "Inscriptional Pahlavi"),
    PSALTER_PAHLAVI(0x10B80, 0x10BAF, "Psalter Pahlavi"),
    OLD_TURKIC(0x10C00, 0x10C4F, "Old Turkic"),
    OLD_HUNGARIAN(0x10C80, 0x10CFF, "Old Hungarian"),
    HANIFI_ROHINGYA(0x10D00, 0x10D3F, "Hanifi Rohingya"),
    RUMI_NUMERAL_SYMBOLS(0x10E60, 0x10E7F, "Rumi Numeral Symbols"),
    YEZIDI(0x10E80, 0x10EBF, "Yezidi"),
    OLD_SOGDIAN(0x10F00, 0x10F2F, "Old Sogdian"),
    SOGDIAN(0x10F30, 0x10F6F, "Sogdian"),
    OLD_UYGHUR(0x10F70, 0x10FAF, "Old Uyghur"),
    CHORASMIAN(0x10FB0, 0x10FDF, "Chorasmian"),
    ELYMAIC(0x10FE0, 0x10FFF, "Elymaic"),
    BRAHMI(0x11000, 0x1107F, "Brahmi"),
    KAITHI(0x11080, 0x110CF, "Kaithi"),
    SORA_SOMPENG(0x110D0, 0x110FF, "Sora Sompeng"),
    CHAKMA(0x11100, 0x1114F, "Chakma"),
    MAHAJINI(0x11150, 0x1117F, "Mahajani"),
    SHARADA(0x11180, 0x111DF, "Sharada"),
    SINHALA_ARCHAIC_NUMBERS(0x111E0, 0x111FF, "Sinhala Archaic Numbers"),
    KHOJKI(0x11200, 0x1124F, "Khojki"),
    MULTANI(0x11280, 0x112AF, "Multani"),
    KHUDAWADI(0x112B0, 0x112FF, "Khudawadi"),
    GRANTHA(0x11300, 0x1137F, "Grantha"),
    NEWA(0x11400, 0x1147F, "Newa"),
    TIRHUTA(0x11480, 0x114DF, "Tirhuta"),
    SIDDHAM(0x11580, 0x115FF, "Siddham"),
    MODI(0x11600, 0x1165F, "Modi"),
    MONGOLIAN_SUPPLEMENT(0x11660, 0x1167F, "Mongolian Supplement"),
    TAKRI(0x11680, 0x116CF, "Takri"),
    AHOM(0x11700, 0x1174F, "Ahom"),
    DOGRA(0x11800, 0x1184F, "Dogra"),
    WARANG_CITI(0x118A0, 0x118FF, "Warang Citi"),
    DIVES_AKURU(0x11900, 0x1195F, "Dives Akuru"),
    NANDINAGARI(0x119A0, 0x119FF, "Nandinagari"),
    ZANZABAR_SQUARE(0x11A00, 0x11A4F, "Zanabazar Square"),
    SOYOMBO(0x11A50, 0x11AAF, "Soyombo"),
    UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS_EXT_A(0x11AB0, 0x11ABF, "Unified Canadian Aboriginal Syllabics Extended-A"),
    PAU_CIN_HAU(0x11AC0, 0x11AFF, "Pau Cin Hau"),
    BHAIKSUKI(0x11C00, 0x11C6F, "Bhaiksuki"),
    MARCHEN(0x11C70, 0x11CBF, "Marchen"),
    MASARAM_GONDI(0x11D00, 0x11D5F, "Masaram Gondi"),
    GUNJALA_GONDI(0x11D60, 0x11DAF, "Gunjala Gondi"),
    MAKASAR(0x11EE0, 0x11EFF, "Makasar"),
    LISU_SUPPLEMENT(0x11FB0, 0x11FBF, "Lisu Supplement"),
    TAMIL_SUPPLEMENT(0x11FC0, 0x11FFF, "Tamil Supplement"),
    CUNEIFORM(0x12000, 0x123FF, "Cuneiform"),
    CUNEIFORM_NUMBERS_AND_PUNCTUATION(0x12400, 0x1247F, "Cuneiform Numbers and Punctuation"),
    EARLY_DYNASTIC_CUNEIFORM(0x12480, 0x1254F, "Early Dynastic Cuneiform"),
    CYPRO_MINOAN(0x12F90, 0x12FFF, "Cypro-Minoan"),
    EGYPTIAN_HIEROGLYPHS(0x13000, 0x1342F, "Egyptian Hieroglyphs"),
    EGYPTIAN_HIEROGLYPH_FORMAT_CONTROLS(0x13430, 0x1343F, "Egyptian Hieroglyph Format Controls"),
    ANATOLIAN_HIEROGLYPHS(0x14400, 0x1467F, "Anatolian Hieroglyphs"),
    BAMUM_SUPPLEMENT(0x16800, 0x16A3F, "Bamum Supplement"),
    MRO(0x16A40, 0x16A6F, "Mro"),
    TANGSA(0x16A70, 0x16ACF, "Tangsa"),
    BASSA_VAH(0x16AD0, 0x16AFF, "Bassa Vah"),
    PAHAWH_HMONG(0x16B00, 0x16B8F, "Pahawh Hmong"),
    MEDEFAIDRIN(0x16E40, 0x16E9F, "Medefaidrin"),
    MIAO(0x16F00, 0x16F9F, "Miao"),
    IDEOGRAPHIC_SYMBOLS_AND_PUNCTUATION(0x16FE0, 0x16FFF, "Ideographic Symbols and Punctuation"),
    TANGUT(0x17000, 0x187FF, "Tangut"),
    TANGUT_COMPONENTS(0x18800, 0x18AFF, "Tangut Components"),
    KHITAN_SMALL_SCRIPT(0x18B00, 0x18CFF, "Khitan Small Script"),
    TANGUT_SUPPLEMENT(0x18D00, 0x18D7F, "Tangut Supplement"),
    KANA_EXTENDED_B(0x1AFF0, 0x1AFFF, "Kana Extended-B"),
    KANA_SUPPLEMENT(0x1B000, 0x1B0FF, "Kana Supplement"),
    KANA_EXTENDED_A(0x1B100, 0x1B12F, "Kana Extended-A"),
    SMALL_KANA_EXTENSION(0x1B130, 0x1B16F, "Small Kana Extension"),
    NUSHU(0x1B170, 0x1B2FF, "Nushu"),
    DUPOYAN(0x1BC00, 0x1BC9F, "Duployan"),
    SHORTHAND_FORMAT_CONTROLS(0x1BCA0, 0x1BCAF, "Shorthand Format Controls"),
    ZNAMENNY_MUSICAL_NOTATION(0x1CF00, 0x1CFCF, "Znamenny Musical Notation"),
    BYZANTINE_MUSICAL_SYMBOLS(0x1D000, 0x1D0FF, "Byzantine Musical Symbols"),
    MUSICAL_SYMBOLS(0x1D100, 0x1D1FF, "Musical Symbols"),
    ANCIENT_GREEK_MUSICAL_NOTATION(0x1D200, 0x1D24F, "Ancient Greek Musical Notation"),
    MAYAN_NUMERALS(0x1D2E0, 0x1D2FF, "Mayan Numerals"),
    TAI_XUAN_JING_SYMBOLS(0x1D300, 0x1D35F, "Tai Xuan Jing Symbols"),
    COUNTING_ROD_NUMERALS(0x1D360, 0x1D37F, "Counting Rod Numerals"),
    MATHEMATICAL_ALPHANUMERIC_SYMBOLS(0x1D400, 0x1D7FF, "Mathematical Alphanumeric Symbols"),
    SUTTON_SIGNWRITING(0x1D800, 0x1DAAF, "Sutton SignWriting"),
    LATIN_EXTENDED_G(0x1DF00, 0x1DFFF, "Latin Extended-G"),
    GLAGOLITIC_SUPPLEMENT(0x1E000, 0x1E02F, "Glagolitic Supplement"),
    NYIAKENG_PUACHUE_HMONG(0x1E100, 0x1E14F, "Nyiakeng Puachue Hmong"),
    TOTO(0x1E290, 0x1E2BF, "Toto"),
    WANCHO(0x1E2C0, 0x1E2FF, "Wancho"),
    ETHIOPIC_EXT_B(0x1E7E0, 0x1E7FF, "Ethiopic Extended-B"),
    MENDE_KIKAKUI(0x1E800, 0x1E8DF, "Mende Kikakui"),
    ADLAM(0x1E900, 0x1E95F, "Adlam"),
    INDIC_SIYAQ_NUMBERS(0x1EC70, 0x1ECBF, "Indic Siyaq Numbers"),
    OTTOMAN_SIYAQ_NUMBERS(0x1ED00, 0x1ED4F, "Ottoman Siyaq Numbers"),
    ARABIC_MATHEMATICAL_ALPHABETIC_SYMBOLS(0x1EE00, 0x1EEFF, "Arabic Mathematical Alphabetic Symbols"),
    MAHJONG_TILES(0x1F000, 0x1F02F, "Mahjong Tiles"),
    DOMINO_TILES(0x1F030, 0x1F09F, "Domino Tiles"),
    PLAYING_CARDS(0x1F0A0, 0x1F0FF, "Playing Cards"),
    ENCLOSED_ALPHANUMERIC_SUPPLEMENT(0x1F100, 0x1F1FF, "Enclosed Alphanumeric Supplement"),
    ENCLOSED_IDEOGRAPHIC_SUPPLEMENT(0x1F200, 0x1F2FF, "Enclosed Ideographic Supplement"),
    MISCELLANEOUS_SYMBOLS_PICTOGRAPHS(0x1F300, 0x1F5FF, "Miscellaneous Symbols and Pictographs"),
    EMOTICONS(0x1F600, 0x1F64F, "Emoticons"),
    ORNAMENTAL_DINGBATS(0x1F650, 0x1F67F, "Ornamental Dingbats"),
    TRANSPORT_MAP_SYMBOLS(0x1F680, 0x1F6FF, "Transport and Map Symbols"),
    ALCHEMICAL_SYMBOLS(0x1F700, 0x1F77F, "Alchemical Symbols"),
    GEOMETRIC_SHAPES_EXTENDED(0x1F780, 0x1F7FF, "Geometric Shapes Extended"),
    ARROWS_SUPPLEMENT_C(0x1F800, 0x1F8FF, "Supplemental Arrows-C"),
    SUPPLEMENTAL_SYMBOLS_PICTOGRAPHS(0x1F900, 0x1F9FF, "Supplemental Symbols and Pictographs"),
    CHESS_SYMBOLS(0x1FA00, 0x1FA6F, "Chess Symbols"),
    SYMBOLS_PICTOGRAPHS_EXTENDED_A(0x1FA70, 0x1FAFF, "Symbols and Pictographs Extended-A"),
    SYMBOLS_FOR_LEGACY_COMPUTING(0x1FB00, 0x1FBFF, "Symbols for Legacy Computing"),
    CJK_UNIFIED_IDEOGRAPHS_EXT_B(0x20000, 0x2A6DF, "CJK Unified Ideographs Extension B"),
    CJK_UNIFIED_IDEOGRAPHS_EXT_C(0x2A700, 0x2B73F, "CJK Unified Ideographs Extension C"),
    CJK_UNIFIED_IDEOGRAPHS_EXT_D(0x2B740, 0x2B81F, "CJK Unified Ideographs Extension D"),
    CJK_UNIFIED_IDEOGRAPHS_EXT_E(0x2B820, 0x2CEAF, "CJK Unified Ideographs Extension E"),
    CJK_UNIFIED_IDEOGRAPHS_EXT_F(0x2CEB0, 0x2EBEF, "CJK Unified Ideographs Extension F"),
    CJK_COMPATIBILITY_IDEOGRAPH_SUPPLEMENT(0x2F800, 0x2FA1F, "CJK Compatibility Ideographs Supplement"),
    CJK_UNIFIED_IDEOGRAPHS_EXT_G(0x30000, 0x3134F, "CJK Unified Ideographs Extension G"),
    TAGS(0xE0000, 0xE007F, "Tags"),
    VARIATION_SELECTORS_SUPPLEMENT(0xE0100, 0xE01EF, "Variation Selectors Supplement");

    private final int startingCode;
    private final int endingCode;
    private final String friendlyName;

    UnicodeBlockMapping(int startingCode, int endingCode, String friendlyName) {
        this.startingCode = startingCode;
        this.endingCode = endingCode;
        this.friendlyName = friendlyName;
    }

    boolean isWithinRange(int charCode) {
        return charCode >= startingCode && charCode <= endingCode;
    }

    public int getStartingCode() {
        return startingCode;
    }

    public int getEndingCode() {
        return endingCode;
    }

    @Override
    public String toString() {
        return friendlyName;
    }

}
