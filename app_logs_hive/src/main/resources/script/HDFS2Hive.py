#!/usr/bin/python
# -*- coding: UTF-8 -*-

import time
import os
from datetime import datetime

dt = datetime.now()
print("current time : ", dt.strftime("%Y-%m-%d %H:%M:%S"))

ym = dt.strftime("%Y%m")
day = dt.strftime("%d")
hm = dt.strftime("%H%M")
hm = str(1858)

time_path = ym + '/' + day + '/' + hm
os.system(
    "hive -e \"load data inpath '/user/centos/applogs/startup/" + time_path + "' into table applogsdb.ext_startup_logs partition(ym='" + ym + "',day='" + day + "',hm='" + hm + "')\"")
#
# os.system(
#     "hive -e \"load data inpath '/user/centos/applogs/error/${ym}/${day}/${hm}' into table applogsdb.ext_error_logs partition(ym='${ym}',day='${day}',hm='${hm}')\"")
#
# os.system(
#     "hive -e \"load data inpath '/user/centos/applogs/event/${ym}/${day}/${hm}' into table applogsdb.ext_event_logs partition(ym='${ym}',day='${day}',hm='${hm}')\"")
#
# os.system(
#     "hive -e \"load data inpath '/user/centos/applogs/usage/${ym}/${day}/${hm}' into table applogsdb.ext_usage_logs partition(ym='${ym}',day='${day}',hm='${hm}')\"")
#
# os.system(
#     "hive -e \"load data inpath '/user/centos/applogs/page/${ym}/${day}/${hm}' into table applogsdb.ext_page_logs partition(ym='${ym}',day='${day}',hm='${hm}')\"")

print("excute script finish!\n")
