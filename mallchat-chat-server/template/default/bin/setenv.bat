@echo off

if not %APP_HOME% == "" goto checkSetting
echo "Your must set APP_HOME before run this script."
goto end

:checkSetting
if exist "%APP_HOME%\setting.bat" goto invokeSetting
echo "Can't find the setting file: %APP_HOME%\setting.bat"
goto end

:invokeSetting
call "%APP_HOME%\setting.bat"

set JAVA_CLASS_PATH=.;"%APP_HOME%\lib\*";"%APP_HOME%\classes"

if exist %APP_CLASS_PATH% and not %APP_CLASS_PATH%=="" goto :appendJavaClassPath
goto :setJavaOpts

:appendJavaClassPath
set JAVA_CLASS_PATH=%APP_CLASS_PATH%;%JAVA_CLASS_PATH%

:setJavaOpts
set JAVA_OPTS=%JAVA_MEMORY_OPTS% %JAVA_MONITOR_OPTS% %JAVA_GC_OPTS% %REMOTE_DEBUG_OPTS% %JAVA_APP_OPTS%
set JAVA_OPTS=%JAVA_OPTS% -DLOCAL_IP=%LOCAL_IP% -DJAPP_FLAG=JAPP -Dlog_path="%LogPath%\app" -Drunlog.dir="%LogPath%\app\runlog" -Dbusilog.dir="%LogPath%\app\busilog" -Dfile.encoding=utf8


:exit
exit /b 1

:end
exit /b 0
