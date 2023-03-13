/*
    The code in this file uses open source libraries provided by thecoderscorner

    DO NOT EDIT THIS FILE, IT WILL BE GENERATED EVERY TIME YOU USE THE UI DESIGNER
    INSTEAD EITHER PUT CODE IN YOUR SKETCH OR CREATE ANOTHER SOURCE FILE.

    All the variables you may need access to are marked extern in this file for easy
    use elsewhere.
 */

#include <tcMenu.h>
#include "project_menu.h"
#include <Fonts/sans24p7b.h>

// Global variable declarations
const  ConnectorLocalInfo applicationInfo = { "tester", "d7e57e8d-4528-4081-9b1b-cec5bc37a82e" };
TcMenuRemoteServer remoteServer(applicationInfo);
HalStm32EepromAbstraction glBspRom;
const AuthBlock authMgrAllowedRemotes[] PROGMEM = {
    { "first-uuid", "name1" },
    { "second-uuid", "name2" }
};
const char pgmAuthMgrPassword[] PROGMEM = "1234";
ReadOnlyAuthenticationManager authManager(authMgrAllowedRemotes, 2, pgmAuthMgrPassword);
ArduinoAnalogDevice analogDevice(42);
const int anotherVar;
const int allowedPluginVar;

// Global Menu Item declarations
RENDERING_CALLBACK_NAME_OVERRIDDEN(fnTextEditorRtCall, textRenderRtCall, TC_I18N_MENU_10004_NAME, 0)
TextMenuItem menuTextEditor(fnTextEditorRtCall, "", 10004, 10, NULL);
AnalogMenuInfo minfoAnalogRam = { TC_I18N_MENU_10003_NAME, 10003, 0, 100, NO_CALLBACK, 0, 10, TC_I18N_MENU_10003_UNIT };
AnalogMenuItem menuAnalogRam(&minfoAnalogRam, 0, &menuTextEditor, INFO_LOCATION_RAM);
const char pgmStrIoTMonText[] = { TC_I18N_MENU_10002_NAME };
RemoteMenuItem menuIoTMon(pgmStrIoTMonText, 10002, &menuAnalogRam);
const char pgmStrCustomAuthText[] = { TC_I18N_MENU_10001_NAME };
EepromAuthenticationInfoMenuItem menuCustomAuth(pgmStrCustomAuthText, NO_CALLBACK, 10001, &menuIoTMon);
ScrollChoiceMenuItem menuMySubSub1CustomChoice(18, fnMySubSub1CustomChoiceRtCall, 0, 6, NULL);
extern char myChoiceRam[];
RENDERING_CALLBACK_NAME_INVOKE(fnMySubSub1RamChoiceRtCall, enumItemRenderFn, "Ram Choice", 31, onRamChoice)
ScrollChoiceMenuItem menuMySubSub1RamChoice(17, fnMySubSub1RamChoiceRtCall, 0, myChoiceRam, 5, 6, &menuMySubSub1CustomChoice);
RENDERING_CALLBACK_NAME_INVOKE(fnMySubSub1EepromChoiceRtCall, enumItemRenderFn, "EepromChoice", 29, onRomChoice)
ScrollChoiceMenuItem menuMySubSub1EepromChoice(16, fnMySubSub1EepromChoiceRtCall, 0, 128, 11, 4, &menuMySubSub1RamChoice);
RENDERING_CALLBACK_NAME_INVOKE(fnMySubSub1RGBRtCall, rgbAlphaItemRenderFn, "RGB", -1, onRgb)
Rgb32MenuItem menuMySubSub1RGB(fnMySubSub1RGBRtCall, RgbColor32(0, 0, 0, 255), 15, true, &menuMySubSub1EepromChoice);
RENDERING_CALLBACK_NAME_INVOKE(fnMySubSub1DateFieldRtCall, dateItemRenderFn, "Date Field", 25, NO_CALLBACK)
DateFormattedMenuItem menuMySubSub1DateField(fnMySubSub1DateFieldRtCall, DateStorage(1, 1, 2020), 14, &menuMySubSub1RGB);
RENDERING_CALLBACK_NAME_INVOKE(fnMySubSub1TimeFieldRtCall, timeItemRenderFn, "Time Field", -1, NO_CALLBACK)
TimeFormattedMenuItem menuMySubSub1TimeField(fnMySubSub1TimeFieldRtCall, TimeStorage(0, 0, 0, 0), 13, (MultiEditWireType)8, &menuMySubSub1DateField);
RENDERING_CALLBACK_NAME_INVOKE(fnMySubSub1IPAddressRtCall, ipAddressRenderFn, "IP Address", 21, onIpChange)
IpAddressMenuItem menuMySubSub1IPAddress(fnMySubSub1IPAddressRtCall, IpAddressStorage(127, 0, 0, 1), 12, &menuMySubSub1TimeField);
RENDERING_CALLBACK_NAME_INVOKE(fnMySubSub1TextItemRtCall, textItemRenderFn, "Text Item", 7, NO_CALLBACK)
TextMenuItem menuMySubSub1TextItem(fnMySubSub1TextItemRtCall, "", 11, 14, &menuMySubSub1IPAddress);
RENDERING_CALLBACK_NAME_INVOKE(fnMySubSub1IntLargeRtCall, largeNumItemRenderFn, "Int Large", -1, NO_CALLBACK)
EditableLargeNumberMenuItem menuMySubSub1IntLarge(fnMySubSub1IntLargeRtCall, LargeFixedNumber(8, 0, 0U, 0U, false), 10, false, &menuMySubSub1TextItem);
RENDERING_CALLBACK_NAME_INVOKE(fnMySubSub1DecLargeRtCall, largeNumItemRenderFn, "Dec Large", -1, onDecLarge)
EditableLargeNumberMenuItem menuMySubSub1DecLarge(fnMySubSub1DecLargeRtCall, LargeFixedNumber(8, 3, 0U, 0U, false), 9, true, &menuMySubSub1IntLarge);
const SubMenuInfo minfoMySubSub1 = { "Sub1", 8, 0xffff, 0, NO_CALLBACK };
BackMenuItem menuBackMySubSub1(&minfoMySubSub1, &menuMySubSub1DecLarge, INFO_LOCATION_PGM);
SubMenuItem menuMySubSub1(&minfoMySubSub1, &menuBackMySubSub1, &menuCustomAuth, INFO_LOCATION_PGM);
ListRuntimeMenuItem menuMySubMyList(7, 0, fnMySubMyListRtCall, &menuMySubSub1);
const AnyMenuInfo minfoMySubMyAction = { "My Action", 6, 0xffff, 0, onActionItem };
ActionMenuItem menuMySubMyAction(&minfoMySubMyAction, &menuMySubMyList, INFO_LOCATION_PGM);
const FloatMenuInfo minfoMySubMyFloat = { "My Float", 5, 0xffff, 3, NO_CALLBACK };
FloatMenuItem menuMySubMyFloat(&minfoMySubMyFloat, 0.0, &menuMySubMyAction, INFO_LOCATION_PGM);
const BooleanMenuInfo minfoMySubMyBoolean = { "My Boolean", 4, 6, 1, onBoolChange, NAMING_YES_NO };
BooleanMenuItem menuMySubMyBoolean(&minfoMySubMyBoolean, false, &menuMySubMyFloat, INFO_LOCATION_PGM);
const char enumStrMySubMyEnum_0[] = "Item1";
const char enumStrMySubMyEnum_1[] = "Item2";
const char* const enumStrMySubMyEnum[]  = { enumStrMySubMyEnum_0, enumStrMySubMyEnum_1 };
const EnumMenuInfo minfoMySubMyEnum = { "MyEnum", 3, 4, 1, onEnumChange, enumStrMySubMyEnum };
EnumMenuItem menuMySubMyEnum(&minfoMySubMyEnum, 0, &menuMySubMyBoolean, INFO_LOCATION_PGM);
const AnalogMenuInfo minfoMySubMyAnalog = { "My Analog", 2, 2, 255, onAnalogItem, 0, 1, "Unit" };
AnalogMenuItem menuMySubMyAnalog(&minfoMySubMyAnalog, 0, &menuMySubMyEnum, INFO_LOCATION_PGM);

void setupMenu() {
    // First we set up eeprom and authentication (if needed).
    setSizeBasedEEPROMStorageEnabled(true);
    glBspRom.initialise(50);
    menuMgr.setEepromRef(&glBspRom);
    menuMgr.setAuthenticator(&authManager);
    // Now add any readonly, non-remote and visible flags.
    menuMySubSub1TextItem.setReadOnly(true);
    menuMySubSub1DecLarge.setLocalOnly(true);

    // Code generated by plugins.
    switches.initialise(io23017, true, MenuFontDef(&sans24p7b, 1), internalDigitalIo());
    switches.addSwitch(BUTTON_PIN, &null);
    switches.onRelease(BUTTON_PIN, [](uint8_t /*key*/, bool held) {
            anotherFn(20);
        });
    initialiseMyTheme();
    turboTron.begin(&Serial, &applicationInfo, &menuMySubMyAnalog, MBED_RTOS);

    // We have an IoT monitor, register the server
    menuIoTMon.setRemoteServer(remoteServer);

    // We have an EEPROM authenticator, it needs initialising
    menuCustomAuth.init();
}

