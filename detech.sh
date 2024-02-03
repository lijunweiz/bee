#!/bin/bash
loop=0
times=360
sleep=10
target="bee-api"
now="`date +'%Y-%m-%d %H:%M:%S'`"
echo "${now} 开始探测服务"
while true
do
  sleep ${sleep}
  pid="`ps -ef | grep "${target}" | grep -v grep | grep -v bash |  awk '{print $2}'`"
  if [ -n "${pid}" ] ;then
    loop="`expr ${loop} + 1`"
    yu="`expr ${loop} % ${times}`"
    if [ "${yu}" == 0 ] ;then
      loop=0
      now="`date +'%Y-%m-%d %H:%M:%S'`"
      echo "${now} 重置loop成功" 
    fi
  else
    now="`date +'%Y-%m-%d %H:%M:%S'`"
    echo "${now} ${target} 异常终止, 开始重启"
    sh restart.sh
    sleep 30
    now="`date +'%Y-%m-%d %H:%M:%S'`"
    echo "${now} 成功等待应用启动30s"
  fi
done

