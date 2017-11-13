###停止

moduleName="ps"

tpid=`cat /var/run/$moduleName-tpid | awk '{print $1}'`
tpid=`ps -aef | grep $tpid | awk '{print $2}' |grep $tpid`
if [ ${tpid} ]; then
kill -9 $tpid
fi