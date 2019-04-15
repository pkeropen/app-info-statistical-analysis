#!/usr/bin/python
# -*- coding: UTF-8 -*-

import time
import os
from datetime import datetime, timedelta

dt = datetime.now() + timedelta(minutes=-3)
print("current time : ", dt.strftime("%Y-%m-%d %H:%M:%S"))

ym = dt.strftime("%Y%m")
day = dt.strftime("%d")
hm = dt.strftime("%H%M")

time_path = ym + '/' + day + '/' + hm

os.system("hive -e \"load data inpath '/user/centos/applogs/startup/" + time_path + "' into table applogsdb.ext_startup_logs partition(ym='" + ym + "',day='" + day + "',hm='" + hm + "')\"")

os.system(
    "hive -e \"load data inpath '/user/centos/applogs/error/" + time_path + "' into table applogsdb.ext_error_logs partition(ym='" + ym + "',day='" + day + "',hm='" + hm + "')\"")

os.system(
    "hive -e \"load data inpath '/user/centos/applogs/event/" + time_path + "' into table applogsdb.ext_event_logs partition(ym='" + ym + "',day='" + day + "',hm='" + hm + "')\"")

os.system(
    "hive -e \"load data inpath '/user/centos/applogs/usage/" + time_path + "' into table applogsdb.ext_usage_logs partition(ym='" + ym + "',day='" + day + "',hm='" + hm + "')\"")

os.system(
    "hive -e \"load data inpath '/user/centos/applogs/page/" + time_path + "' into table applogsdb.ext_page_logs partition(ym='" + ym + "',day='" + day + "',hm='" + hm + "')\"")

print("excute script finish!\n")
