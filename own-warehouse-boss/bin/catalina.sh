#!/bin/bash
cd `dirname $0`
## service name
APP_NAME=boss

SERVICE_NAME=own-warehouse-$APP_NAME
JAR_NAME=$SERVICE_NAME\.jar
PID=$SERVICE_NAME\.pid

cd ..

case "$1" in

    start)
        java -Xms256m -Xmx512m -jar $JAR_NAME --server.port=8080 --spring.config.location=file:conf/db.properties >nohup.log 2>&1
        #nohup java -Xms256m -Xmx512m -jar $JAR_NAME --server.port=8080 --spring.config.location=file:conf/db.properties >nohup.log 2>&1 &
        ##exec java -Xms256m -Xmx512m -jar $JAR_NAME --server.port=8080 --spring.config.location=file:conf/db.properties &
        echo $! > $PID
        echo "--------------- start $SERVICE_NAME"
        ;;

    stop)
        if [ -f "$PID" ]; then
            kill `cat $PID`
            rm -rf $PID
            echo "--------------- stop $SERVICE_NAME"
        fi

        sleep 5
        ##
        P_ID=`ps -ef | grep -w "$SERVICE_NAME" | grep -v "grep" | awk '{print $2}'`
        if [ "$P_ID" == "" ]; then
            echo "--------------- $SERVICE_NAME process not exists or stop success"
        else
            echo "--------------- $SERVICE_NAME process pid is:$P_ID"
            echo "--------------- begin kill $SERVICE_NAME process, pid is:$P_ID"
            kill -9 $P_ID
        fi
        ;;

    restart)
        $0 stop
        sleep 2
        $0 start
        echo "--------------- restart $SERVICE_NAME"
        ;;

    *)
        ## restart
        $0 stop
        sleep 2
        $0 start
        ;;

esac