#!/bin/bash
systime=`date -d "-3 minute" +%Y%m-%d-%H%M`
ym=`echo ${systime} | awk -F '-' '{print $1}'`
day=`echo ${systime} | awk -F '-' '{print $2}'`
hm=`echo ${systime} | awk -F '-' '{print $3}'`


#执行 hive 的命令
hive -e "load data inpath '/user/centos/applogs/startup/${ym}/${day}/${hm}' into table
applogsdb.ext_startup_logs partition(ym='${ym}',day='${day}',hm='${hm}')"
hive -e "load data inpath '/user/centos/applogs/error/${ym}/${day}/${hm}' into table
applogsdb.ext_error_logs partition(ym='${ym}',day='${day}',hm='${hm}')"
hive -e "load data inpath '/user/centos/applogs/event/${ym}/${day}/${hm}' into table
applogsdb.ext_event_logs partition(ym='${ym}',day='${day}',hm='${hm}')"
hive -e "load data inpath '/user/centos/applogs/usage/${ym}/${day}/${hm}' into table
applogsdb.ext_usage_logs partition(ym='${ym}',day='${day}',hm='${hm}')"
hive -e "load data inpath '/user/centos/applogs/page/${ym}/${day}/${hm}' into table
applogsdb.ext_page_logs partition(ym='${ym}',day='${day}',hm='${hm}')"