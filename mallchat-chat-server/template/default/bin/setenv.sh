#!/bin/sh

#must check APP_HOME before
if [ -z "$APP_HOME" ]; then
    echo "Your must set APP_HOME before run this script."
    exit 1
fi

#invoke setting
if [ -r "$APP_HOME"/setting.sh ]; then
    . "$APP_HOME"/setting.sh
else
    echo "Can't find the setting file: setting.sh"
    exit 1 
fi

#set CLASSPATH
JAVA_CLASS_PATH=".:$APP_HOME/lib/*:$APP_HOME/classes"
if [ -n "$APP_CLASS_PATH" ]; then
    JAVA_CLASS_PATH="$APP_CLASS_PATH:$JAVA_CLASS_PATH"
fi

JAVA_OPTS="${JAVA_MEMORY_OPTS} $JAVA_MONITOR_OPTS $JAVA_GC_OPTS $REMOTE_DEBUG_OPTS $JAVA_APP_OPTS"
JAVA_OPTS="${JAVA_OPTS} -DLOCAL_IP=${LOCAL_IP} -DJAPP_FLAG=JAPP -Dlog_path=${LogPath}/app -Drunlog.dir=${LogPath}/app/runlog -Dbusilog.dir=${LogPath}/app/busilog -Dfile.encoding=utf8" 
