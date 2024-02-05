#!/bin/bash

PRG="$0"

while [ -h "$PRG" ]; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else
        PRG=`dirname "$PRG"`/"$link"
    fi
done

PRGDIR=`dirname "$PRG"`

[ -z "$APP_HOME" ] && APP_HOME=`cd "$PRGDIR/.."; pwd`
PID_FILE="$APP_HOME/run/app.pid"

JAVA_PATH=$JAVA_HOME/bin/java
if [ -z "$JAVA_PATH" ]; then
    JAVA_PATH=`which java 2>/dev/null`
fi

if [ -z "$JAVA_PATH" ]; then
    echo "Error: can't find java"
fi

if [ -r "$APP_HOME"/bin/setenv.sh ]; then
    . "$APP_HOME"/bin/setenv.sh
else
    echo "Error: Can't find the $PRGDIR/bin/setenv.sh file."
    exit 1
fi

if [ -z "$MAIN_JAR_NAME" ]; then
    echo "Error: Please set MAIN_JAR_NAME in $PRGDIR/setting.sh"
    exit 1
fi

have_tty=0
if [ "`tty`" != "not a tty" ]; then
    have_tty=1
fi
#Show setting
if [ $have_tty -eq 1 ]; then
    echo "================================================================================== "
    echo "Using APP_HOME:     $APP_HOME"
    echo "Using JAVA_PATH:    $JAVA_PATH"
    echo "Using MAIN_JAR:     $MAIN_JAR_NAME"
    echo "Using PID_FILE:     $PID_FILE"
    echo "Using CLASSPATH:    $JAVA_CLASS_PATH"
    echo "Using JAVA_OPTS:    $JAVA_OPTS"
    echo "================================================================================== "
fi

if [ -f "$PID_FILE" ]; then
    APP_PID=`cat "$PID_FILE"`
    if [ ! -z "$APP_PID" ]; then
        isRunning=`ps -p $APP_PID --no-header -o comm|grep "java"|wc -l`
        if [ "$isRunning" -gt 0  ]; then
            echo "Error: Application is running."
            exit 1
        else
            echo "Warring: PID file is error."
        fi
    fi
fi

#Run application
"$JAVA_PATH" $JAVA_OPTS -Xbootclasspath/a:"$JAVA_CLASS_PATH" -jar -Dloader.path=$APP_HOME/lib,$APP_HOME/classes "$MAIN_JAR_NAME" $APP_ARG &

#get java application pid
APP_PID=`/bin/ps -ef|/bin/grep "JAPP_FLAG=JAPP"|/bin/grep "$MAIN_CLASS_NAME"|/bin/grep "$APP_HOME/lib"|/bin/grep -v "/bin/grep"|/bin/awk '{print $2}'`

echo $APP_PID > $PID_FILE
