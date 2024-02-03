#!/bin/bash
target="bee-api"
APP_HOME=/home/bee/${target}
pid="`ps -ef | grep "${target}" | grep -v grep | grep -v bash |  awk '{print $2}'`"
if [ -n "${pid}" ] ; then
  kill -15 $pid
  echo "kill -15 $pid"
  sleep 10
fi

now=`date '+%Y%m%d%H%M%S'`
mv ${APP_HOME}/${target}.jar ${APP_HOME}/${target}${now}.jar

nohup java -jar $APP_HOME/${target}.jar 2>&1 &
