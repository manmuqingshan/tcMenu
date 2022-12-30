; Script generated by the Inno Setup Script Wizard.
; SEE THE DOCUMENTATION FOR DETAILS ON CREATING INNO SETUP SCRIPT FILES!

#define MyAppName "embedCONTROL"
#define MyAppVersion "3.0.0"
#define MyAppPublisher "The coders corner"
#define MyAppURL "https://www.thecoderscorner.com"
#define MyAppExeName "embedCONTROL.exe"
#define MyEmbedControlDir "C:\Users\dave\IdeaProjects\tcMenu\embedCONTROLFx\target"

[Setup]
; NOTE: The value of AppId uniquely identifies this application.
; Do not use the same AppId value in installers for other applications.
; (To generate a new GUID, click Tools | Generate GUID inside the IDE.)
AppId={{414B5A80-36C8-4DEE-978A-9B76870C18F1}
AppName={#MyAppName}
AppVersion={#MyAppVersion}
;AppVerName={#MyAppName} {#MyAppVersion}
AppPublisher={#MyAppPublisher}
AppPublisherURL={#MyAppURL}
AppSupportURL={#MyAppURL}
AppUpdatesURL={#MyAppURL}
DefaultDirName={pf}\{#MyAppName}
DisableProgramGroupPage=yes
LicenseFile=C:\Users\dave\IdeaProjects\tcMenu\LICENSE
OutputBaseFilename=setupEmbedCONTROL-{#MyAppVersion}
Compression=lzma
SolidCompression=yes
SignTool=ecSign
SignedUninstaller=yes
SetupIconFile={#MyEmbedControlDir}\classes\fximg\embedCONTROL.ico

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked

[Files]
Source: "{#MyEmbedControlDir}\embedCONTROL\embedCONTROL.exe"; DestDir: "{app}"; Flags: ignoreversion signonce
Source: "{#MyEmbedControlDir}\embedCONTROL\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs
; NOTE: Don't use "Flags: ignoreversion" on any shared system files

[Icons]
Name: "{commonprograms}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"
Name: "{commondesktop}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; Tasks: desktopicon

[Run]
Filename: "{app}\{#MyAppExeName}"; Description: "{cm:LaunchProgram,{#StringChange(MyAppName, '&', '&&')}}"; Flags: nowait postinstall skipifsilent

