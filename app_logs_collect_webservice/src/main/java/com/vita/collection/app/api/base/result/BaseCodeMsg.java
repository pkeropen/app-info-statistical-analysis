package com.vita.collection.app.api.base.result;

/**
 * （基类）返回码及描述信息
 */
public class BaseCodeMsg {

    /** 返回码 */
    private Integer code;

    /** 返回码描述 */
    private String msg;

    /**
     * 无参数构造方法
     */
    private BaseCodeMsg() {}

    /**
     * 构造方法
     * @param code
     * @param msg
     */
    private BaseCodeMsg(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public static BaseCodeMsg app(Integer code, String msg) {
        return new BaseCodeMsg(code, msg);
    }

    /**
     * 返回码填充
     * @param args 填充内容
     * @return CodeMsgEnum
     */
    public BaseCodeMsg fillArgs(Object ... args) {

        this.msg = String.format(this.msg, args);

        return this;
    }

    /**
     * 获取返回码
     * @return 返回码
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 获取描述信息
     * @return 描述信息
     */
    public String getMsg() {
        return msg;
    }

    /** 成功 */
    public static final BaseCodeMsg SUCCESS = BaseCodeMsg.app(0, "Success");

    /** 失败 */
    public static final BaseCodeMsg ERROR_INIT = BaseCodeMsg.app(-1, "Error");

}
