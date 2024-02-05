@echo off


if not exist "%JAVA_HOME%\bin\jps.exe" echo Please set the JAVA_HOME variable in your environment, We need java(x64)! & EXIT /B 1

setlocal

rem set APP_HOME JAVA_PATH PID_FILE
set "CURRENT_DIR=%~dp0"
cd %CURRENT_DIR%
cd ..
set "APP_HOME=%cd%"
set JAVA_PATH="%JAVA_HOME%\bin\java"
set PID_FILE=%APP_HOME%\run\app.pid
set MAIN_JAR_NAME=${package.name}

if not %MAIN_JAR_NAME% == "" goto showSetting
echo "Error: Please set MAIN_JAR_NAME in %APP_HOME%\shutdown.bat"
goto end

:showSetting
echo ==================================================================================
echo Using APP_HOME:     %APP_HOME%
echo Using JAVA_PATH:    %JAVA_PATH%
echo Using MAIN_JAR:     %MAIN_JAR_NAME%
echo ==================================================================================

:killTask
echo killing process for %MAIN_JAR_NAME% 
for /f "tokens=1" %%i in ('jps -m ^| find "%MAIN_JAR_NAME%"') do ( taskkill /F /PID %%i )
echo Done!

:cleanPid
rem echo "" > %PID_FILE%

:end
