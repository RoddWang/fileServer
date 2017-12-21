/*
 * Copyright (c) 2017, China Mobile IOT All Rights Reserved.
 */
package cn.iot.api.file.constants;

/**
 * 错误码
 * 
 * @author wangjian
 * @date 2017年12月19日 上午10:36:20
 * @modify 2017年12月19日 wangjian v4.3.0 创建文件
 * @since v4.3.0
 */
public enum ErrorCode {

    SUCCESS("0", "正确"), 
    ERR01000("10", "请求参数不正确，请参考能力调用文档"),
    ERR01400("14","日期参数格式不正确，请参考能力调用文档"),
    ERR00430("43", "文件不存在"),
    ERR07300("73","系统忙，请稍后再试"),
    ERR09900("99", "其它错误，请联系管理员"),
    ;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误码描述
     */
    private String msg;

    ErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
