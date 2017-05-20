#!/bin/bash
cd `dirname $0`
LIB_DIR=`pwd`

SERVER_NAME='own-warehouse-boss-1.0-SNAPSHOT.jar'
PIDS=`ps -ef | grep java | grep "$LIB_DIR" |grep $SERVER_NAME|awk '{print $2}'`
if [ -n "$PIDS" ]; then
    echo "start fail! The $SERVER_NAME already started!"
    exit 1
fi



cd ..
nohup java -server -Xms256m -Xmx256m -XX:PermSize=128m $SERVER_NAME --server.port=8080 --spring.config.location=file:conf/db.properties nohup.out 2>&1 &
echo "start "$SERVER_NAME" success!"