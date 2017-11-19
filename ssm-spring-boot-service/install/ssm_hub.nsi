; Java Launcher
;--------------
 
Name "SSM HUB"
Caption "SSM Hub"
OutFile "SSMHub.exe"
 
SilentInstall silent
AutoCloseWindow true
ShowInstDetails nevershow
 
Section ""
  SetOutPath $TEMP
  file 'ssm-spring-boot-service-1.0-SNAPSHOT.jar'
  StrCpy $1 '"$EXEDIR\jre\bin\javaw.exe" -jar "$TEMP/ssm-spring-boot-service-1.0-SNAPSHOT.jar"'
  ExecWait $1
  Delete 'ssm-spring-boot-service-1.0-SNAPSHOT.jar'
SectionEnd