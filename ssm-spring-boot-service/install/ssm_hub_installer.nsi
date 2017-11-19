; example2.nsi
;
; This script is based on example1.nsi, but it remember the directory, 
; has uninstall support and (optionally) installs start menu shortcuts.
;
; It will install example2.nsi into a directory that the user selects,

;--------------------------------
!include "TextFunc.nsh"

!define APPNAME "XCafe"
!define COMPANYNAME "HoangSoft"
!define DESCRIPTION "A short description goes here"

# These three must be integers
!define VERSIONMAJOR 1
!define VERSIONMINOR 1
!define VERSIONBUILD 1

!define TEMP1 $R0 ;Temp variable

; The name of the installer
Name "${COMPANYNAME} - ${APPNAME}"
Icon "server.ico"

; The file to write
OutFile "ssm-hub-installer.exe"

InstallDir "$PROGRAMFILES\${COMPANYNAME}\${APPNAME}"


; Request application privileges for Windows Vista
RequestExecutionLevel user

;--------------------------------

; Pages

Page directory
Page custom SetCustom ValidateCustom ": Testing InstallOptions" ;Custom page. InstallOptions gets called in SetCustom.
Page instfiles

UninstPage uninstConfirm
UninstPage instfiles

Function .onInit
  ;Extract InstallOptions files
  ;$PLUGINSDIR will automatically be removed when the installer closes
  InitPluginsDir
  File /oname=$PLUGINSDIR\ssm_hub.ini "ssm_hub.ini"
FunctionEnd

Function SetCustom
  ;Display the InstallOptions dialog
  Push ${TEMP1}
    InstallOptions::dialog "$PLUGINSDIR\ssm_hub.ini"
	Pop ${TEMP1}
  Pop ${TEMP1}
FunctionEnd

Function ValidateCustom
FunctionEnd


Section "install"

  ; Set output path to the installation directory.
  SetOutPath $INSTDIR
  
  file SSMHub.exe
  file /r jre
  file "server.ico"
  file ssm_hub.ini
  file ssm_hub_config.conf
  
  writeUninstaller "$INSTDIR\uninstall.exe"
  
  # Start Menu
	createDirectory "$SMPROGRAMS\${COMPANYNAME}"
	createShortCut "$SMPROGRAMS\${COMPANYNAME}\${APPNAME}.lnk" "$INSTDIR\SSMHub.exe" "" "$INSTDIR\server.ico"
	
  # create desktop shortcut
    createShortCut "$DESKTOP\${APPNAME}.lnk" "$INSTDIR\SSMHub.exe" "" "$INSTDIR\server.ico"

  # Registry information for add/remove programs
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "DisplayName" "${COMPANYNAME} - ${APPNAME} - ${DESCRIPTION}"
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "UninstallString" "$\"$INSTDIR\uninstall.exe$\""
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "QuietUninstallString" "$\"$INSTDIR\uninstall.exe$\" /S"
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "InstallLocation" "$\"$INSTDIR$\""
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "DisplayIcon" "$\"$INSTDIR\server.ico$\""
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "Publisher" "$\"${COMPANYNAME}$\""
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "HelpLink" "$\"${HELPURL}$\""
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "URLUpdateInfo" "$\"${UPDATEURL}$\""
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "URLInfoAbout" "$\"${ABOUTURL}$\""
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "DisplayVersion" "$\"${VERSIONMAJOR}.${VERSIONMINOR}.${VERSIONBUILD}$\""
	WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "VersionMajor" ${VERSIONMAJOR}
	WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "VersionMinor" ${VERSIONMINOR}
	# There is no option for modifying or repairing the install
	WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "NoModify" 1
	WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "NoRepair" 1
	# Set the INSTALLSIZE constant (!defined at the top of this script) so Add/Remove Programs can accurately report the size
	#WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "EstimatedSize" ${INSTALLSIZE}
	
	ReadINIStr ${TEMP1} "$PLUGINSDIR\ssm_hub.ini" "Field 2" "State"
	${ConfigWrite} "$INSTDIR\ssm_hub_config.conf" "port=" ${TEMP1} $R0
SectionEnd ;

section "uninstall"
	delete "$SMPROGRAMS\${COMPANYNAME}\${APPNAME}.lnk"
	delete "$DESKTOP\${APPNAME}.lnk"
	# Try to remove the Start Menu folder - this will only happen if it is empty
	rmDir "$SMPROGRAMS\${COMPANYNAME}"
 
	# Remove files
	delete $INSTDIR\SSMHub.exe
	delete $INSTDIR\server.ico
	delete $INSTDIR\ssm_hub.ini
	delete $INSTDIR\ssm_hub_config.conf
	RMDir /r $INSTDIR\jre
 
	# Always delete uninstaller as the last action
	delete $INSTDIR\uninstall.exe
 
	# Try to remove the install directory - this will only happen if it is empty
	rmDir $INSTDIR
 
	# Remove uninstaller information from the registry
	DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}"
sectionEnd


