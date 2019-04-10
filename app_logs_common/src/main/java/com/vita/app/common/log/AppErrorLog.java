package com.vita.app.common.log;

import com.vita.app.common.Constants;

/**
 * APP发送错误日志
 */
public class AppErrorLog extends AppBaseLog {

    private String errorBrief;         //错误摘要
    private String errorDetail;        //错误详情

    public String getErrorBrief() {
        return errorBrief;
    }


    public void setErrorBrief(String errorBrief) {
        this.errorBrief = errorBrief;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }
}
