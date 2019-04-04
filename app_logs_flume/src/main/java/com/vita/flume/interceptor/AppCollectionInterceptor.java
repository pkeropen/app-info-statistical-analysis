package com.vita.flume.interceptor;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 自定义 flume 的拦截器,提取 body 中的日志类型作为 header
 */
public class AppCollectionInterceptor implements Interceptor {

    private final boolean preserveExisting;

    private AppCollectionInterceptor(boolean preserveExisting) {
        this.preserveExisting = preserveExisting;
    }

    @Override
    public void initialize() {
    }

    @Override
    public Event intercept(Event event) {
        //获取 flume 接收消息头
        Map<String, String> headers = event.getHeaders();

        //获取flume接收的json 数据数组
        byte[] json = event.getBody();

        //将json数组转换为字符串
        String jsonStr = new String(json);

        //pageLog
        String logType = "";

        //pageLog
        if (jsonStr.contains("pageId")) {
            logType = "page";
        } else if (jsonStr.contains("eventId")) {//eventLog
            logType = "event";
        } else if (jsonStr.contains("singleUseDurationSecs")) {//usageLog
            logType = "usage";
        } else if (jsonStr.contains("errorBrief")) {//error
            logType = "error";
        } else if (jsonStr.contains("network")) {//startup
            logType = "startup";
        }

        //将日志类型存储到flume中
        headers.put("logType", logType);

        return event;
    }

    @Override
    public List<Event> intercept(List<Event> events) {
        Iterator iterator = events.iterator();

        while (iterator.hasNext()) {
            Event event = (Event) iterator.next();
            this.intercept(event);
        }
        return events;
    }

    @Override
    public void close() {

    }

    public static class Constants {
        public static String TIMESTAMP = "timestamp";
        public static String PRESERVE = "preserveExisting";
        public static boolean PRESERVE_DFLT = false;

        public Constants() {
        }
    }

    public static class Builder implements Interceptor.Builder {
        private boolean preserveExisting;

        public Builder() {
            this.preserveExisting = AppCollectionInterceptor.Constants.PRESERVE_DFLT;
        }

        public Interceptor build() {
            return new AppCollectionInterceptor(this.preserveExisting);
        }

        public void configure(Context context) {
            this.preserveExisting = context.getBoolean(AppCollectionInterceptor.Constants.PRESERVE, AppCollectionInterceptor.Constants.PRESERVE_DFLT).booleanValue();
        }
    }

}
