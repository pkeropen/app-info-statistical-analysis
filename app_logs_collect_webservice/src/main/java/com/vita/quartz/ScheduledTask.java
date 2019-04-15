package com.vita.quartz;

import org.python.util.PythonInterpreter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
public class ScheduledTask {

    @Scheduled(cron = "1 * * * * *")
    //提供了一种通用的定时任务表达式，这里表示每隔5秒执行一次，更加详细的信息可以参考cron表达式。
    public void reportCurrentTimeCron() throws InterruptedException {
        System.out.println(new Date());
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.execfile("D:\\project\\spark\\app-info-statistical-analysis\\app_logs_hive\\src\\main\\resources\\script\\HDFS2Hive.py");
        interpreter.close();
    }

}