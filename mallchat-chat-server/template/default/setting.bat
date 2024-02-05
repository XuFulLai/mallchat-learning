@echo off

rem Java main jar (must set)
set MAIN_JAR_NAME="%APP_HOME%\lib\${package.name}"

rem Java memory 
set JAVA_MEMORY_OPTS=-server -Xmx512M -Xms512M -Xmn256M

rem Jvm params
set JAVA_APP_OPTS=-Dcfg.env=idc -Djava.awt.headless=true

rem Jvm remote dubug enable
set REMOTE_DEBUG_OPTS=

rem Java gc
set JAVA_GC_OPTS=-XX:+UseG1GC -XX:MaxGCPauseMillis=20 -XX:InitiatingHeapOccupancyPercent=35 -XX:+ExplicitGCInvokesConcurrent
set JAVA_GC_OPTS=%JAVA_GC_OPTS% -Xloggc:"%APP_HOME%\logs\gc.log" -verbose:gc -XX:+PrintGCDetails

rem Language
set LC_ALL=zh_CN.utf8

rem Log path
set LogPath="%APP_HOME%\logs"

rem IP
set LOCAL_IP=