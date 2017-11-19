; Java Launcher
;--------------
 
Name "SSM Client"
Caption "SSM Client"
OutFile "SSMClient.exe"
 
SilentInstall silent
AutoCloseWindow true
ShowInstDetails nevershow
 
Section ""
  SetOutPath $TEMP
  file ../target\ssm-app-1.0-SNAPSHOT.one-jar.jar
  StrCpy $1 '"$EXEDIR\jre\bin\javaw.exe" -jar "$TEMP/ssm-app-1.0-SNAPSHOT.one-jar.jar"'
  ExecWait $1
  Delete 'ssm-app-1.0-SNAPSHOT.one-jar.jar'
SectionEnd