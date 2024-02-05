#!/bin/sh

#Java main jar (must set)
MAIN_JAR_NAME="$APP_HOME/lib/${package.name}"

#Java memory 
JAVA_MEMORY_OPTS="-server -Xmx512M -Xms512M -Xmn256M"

#Jvm params
JAVA_APP_OPTS="-Dcfg.env=idc -Djava.awt.headless=true"

#Jvm remote dubug enable
REMOTE_DEBUG_OPTS=

#Java gc
JAVA_GC_OPTS="$JAVA_GC_OPTS -XX:+UseG1GC -XX:MaxGCPauseMillis=20 -XX:InitiatingHeapOccupancyPercent=35 -XX:+ExplicitGCInvokesConcurrent"
JAVA_GC_OPTS="$JAVA_GC_OPTS -Xloggc:$APP_HOME/logs/gc.log -verbose:gc -XX:+PrintGCDetails"

#Language
export LC_ALL="zh_CN.utf8"

#Log path
LogPath="$APP_HOME/logs"

#IP
LOCAL_IP=""
