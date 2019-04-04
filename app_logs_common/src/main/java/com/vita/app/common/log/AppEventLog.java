package com.vita.app.common.log;

import java.util.Map;

public class AppEventLog extends AppBaseLog {

    private String eventId;        //事件唯一标识
    private Long eventDurationSecs; //事件持续时长
    private Map<String, String> paramMap;//参数


    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Long getEventDurationSecs() {
        return eventDurationSecs;
    }

    public void setEventDurationSecs(Long eventDurationSecs) {
        this.eventDurationSecs = eventDurationSecs;
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }
}
