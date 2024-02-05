@echo off

setlocal

rem set APP_HOME JAVA_PATH PID_FILE
set "CURRENT_DIR=%~dp0"
cd %CURRENT_DIR%
cd ..
set "APP_HOME=%cd%"
set PID_FILE="%APP_HOME%\run\app.pid"

set JAVA_PATH="%JAVA_HOME%\bin\java"

if exist "%APP_HOME%/bin/setenv.bat" goto setEnv
echo "Error: Can't find the %APP_HOME%\bin\setenv.bat file."
goto end

:setEnv
call "%APP_HOME%/bin/setenv.bat"

if not %MAIN_JAR_NAME% == "" goto showSetting
echo "Error: Please set MAIN_JAR_NAME in %APP_HOME%\setting.bat"
goto end

:showSetting
echo ==================================================================================
echo Using APP_HOME:     %APP_HOME%
echo Using JAVA_PATH:    %JAVA_PATH%
echo Using MAIN_JAR:     %MAIN_JAR_NAME%
echo Using PID_FILE:     %PID_FILE%
echo Using CLASSPATH:    %JAVA_CLASS_PATH%
echo Using JAVA_OPTS:    %JAVA_OPTS%
echo ==================================================================================

:runApplication
%JAVA_PATH% %JAVA_OPTS% -Xbootclasspath/a:%JAVA_CLASS_PATH% -jar -Dloader.path="%APP_HOME%\lib","%APP_HOME%\classes" %MAIN_JAR_NAME% %APP_ARG%


:setPid


:end
