; Java Launcher
;--------------
 
Name "WOACrm"
Caption "WOA Crm"
OutFile "WOACrm.exe"

SilentInstall silent
AutoCloseWindow true
ShowInstDetails nevershow
 
Section ""
  SetOutPath $EXEDIR
  file 'ssm-crm-server-1.0-SNAPSHOT.jar'
  StrCpy $1 '"$EXEDIR\jre\bin\java.exe" -jar "$EXEDIR/ssm-crm-server-1.0-SNAPSHOT.jar"'
  ExecWait $1
  Delete 'ssm-crm-server-1.0-SNAPSHOT.jar'
SectionEnd