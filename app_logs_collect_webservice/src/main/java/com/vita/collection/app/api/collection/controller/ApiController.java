package com.vita.collection.app.api.collection.controller;

import com.vita.app.common.Constants;
import com.vita.app.common.entity.AppLogEntity;
import com.vita.app.common.log.*;
import com.vita.app.utils.GeoUtils;
import com.vita.collection.app.api.flume.FlumeRpcClientFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "API接口Demo")
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ApiController {

    // 缓存地址信息
    private Map<String, GeoInfoLog> cache = new HashMap<String, GeoInfoLog>();


    @ApiOperation(value = "收集信息", notes = "通过统计dto，收集数据")
    @ResponseBody
    @PostMapping("/collect")
    public AppLogEntity collect(@RequestBody AppLogEntity e, HttpServletRequest req) {
        // 1 修正服务器和客户端时间
        verifyTime(e, req);
        // 2 获取国家、省份和ip地址信息
        processIP(e, req);
        // 3 向Flume发送消息
        sendMessage(e);

        return e;
    }

    /**
     * 测试发送
     *
     * @param e
     */
    private void sendMessage(AppLogEntity e) {

        //根据日志类型分别向5个主题发送消息
        sendFlumeLog(Constants.TOPIC_APP_STARTUP, e.getAppStartupLogs());
        sendFlumeLog(Constants.TOPIC_APP_ERRROR, e.getAppErrorLogs());
        sendFlumeLog(Constants.TOPIC_APP_EVENT, e.getAppEventLogs());
        sendFlumeLog(Constants.TOPIC_APP_PAGE, e.getAppPageLogs());
        sendFlumeLog(Constants.TOPIC_APP_USAGE, e.getAppUsageLogs());

    }

    /**
     * 发送单个的log消息给flume
     *
     * @param topic 发送kafka的topic
     * @param logs  日志bean
     */
    private void sendFlumeLog(String topic, AppBaseLog[] logs) {

        FlumeRpcClientFacade flumeRpcClientFacade = FlumeRpcClientFacade.getInstance();
        for (AppBaseLog log : logs) {
            flumeRpcClientFacade.sendObjectToFlume(log);
        }
    }

    /**
     * 获取正确的IP
     *
     * @param e
     * @param req
     */
    private void processIP(AppLogEntity e, HttpServletRequest req) {
        //  获取客户端ip地址
        String clientIP = req.getRemoteAddr();

        // 从缓存中获取数据
        GeoInfoLog geoInfoLog = cache.get(clientIP);

        // 如果客户端ip地址没有获取过国家和省份信息，则通过工具类获取；
        // 如果客户端ip地址已经获取过国家和省份信息，则直接从缓存对象中获取
        if (geoInfoLog == null) {
            geoInfoLog = new GeoInfoLog();
            geoInfoLog.setCountry(GeoUtils.getCountry(clientIP));
            geoInfoLog.setProvince(GeoUtils.getProvince(clientIP));

            //缓存数据
            cache.put(clientIP, geoInfoLog);
        }

        // 设置国家、省份和客户端ip地址信息
        for (AppStartupLog log : e.getAppStartupLogs()) {
            log.setCountry(geoInfoLog.getCountry());
            log.setProvince(geoInfoLog.getProvince());
            log.setIpAddress(clientIP);
        }

    }

    /**
     * 校正时间
     *
     * @param e
     * @param req
     */
    private void verifyTime(AppLogEntity e, HttpServletRequest req) {
        // 1 获取服务器时间
        long myTime = System.currentTimeMillis();

        // 2 获取客户端时间
//        long clientTime = Long.parseLong(req.getHeader("clientTime"));
        long clientTime = System.currentTimeMillis();

        // 3 计算服务器和客户端时间差
        long diff = myTime - clientTime;

        // 4 根据时间差，修正日志中时间
        for (AppStartupLog log : e.getAppStartupLogs()) {
            log.setCreatedAtMs(log.getCreatedAdMs() + diff);
        }

        for (AppUsageLog log : e.getAppUsageLogs()) {
            log.setCreatedAtMs(log.getCreatedAdMs() + diff);
        }

        for (AppPageLog log : e.getAppPageLogs()) {
            log.setCreatedAtMs(log.getCreatedAdMs() + diff);
        }

        for (AppEventLog log : e.getAppEventLogs()) {
            log.setCreatedAtMs(log.getCreatedAdMs() + diff);
        }

        for (AppErrorLog log : e.getAppErrorLogs()) {
            log.setCreatedAtMs(log.getCreatedAdMs() + diff);
        }
    }
}