package com.vita.app.common.log;

import java.io.Serializable;

/**
 * APP 基本日志类型
 */
public class AppBaseLog implements Serializable {

    private String logType;          //日志类型
    private Long createdAdMs;     //日志创建时间
    private String appId;         //应用唯一标识
    private String tenantId;      //企业用户唯一标识
    private String deviceId;      //设备唯一标识
    private String appVersion;    //app版本
    private String appChannel;    //渠道
    private String appPlatform;   //平台
    private String osType;        //操作系统
    private String deviceStyle;   //机型

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public void setCreatedAdMs(Long createdAdMs) {
        this.createdAdMs = createdAdMs;
    }

    public Long getCreatedAdMs() {
        return createdAdMs;
    }

    public void setCreatedAtMs(Long createdAdMs) {
        this.createdAdMs = createdAdMs;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppChannel() {
        return appChannel;
    }

    public void setAppChannel(String appChannel) {
        this.appChannel = appChannel;
    }

    public String getAppPlatform() {
        return appPlatform;
    }

    public void setAppPlatform(String appPlatform) {
        this.appPlatform = appPlatform;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getDeviceStyle() {
        return deviceStyle;
    }

    public void setDeviceStyle(String deviceStyle) {
        this.deviceStyle = deviceStyle;
    }
}
