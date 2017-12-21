/*
 * Copyright (c) 2017, China Mobile IOT All Rights Reserved.
 */
package cn.iot.api.file.vo;

import java.util.ArrayList;
import java.util.List;

import cn.iot.api.file.constants.ErrorCode;

/**
 * 请求返回实体
 * 
 * @author wangjian
 * @date 2017年12月19日 上午10:30:45
 * @modify 2017年12月19日 wangjian v4.3.0 创建文件
 * @since v4.3.0
 */
public class ResponseVo<T> {

    private String status;
    private String message;
    private List<T> result = new ArrayList<>();

    public ResponseVo() {
        //
    }

    public ResponseVo(ErrorCode errorCode) {
        this.status = errorCode.getCode();
        this.message = errorCode.getMsg();
    }

    public ResponseVo(ErrorCode errorCode, List<T> result) {
        this.status = errorCode.getCode();
        this.message = errorCode.getMsg();
        if (!result.isEmpty()) {
            this.result.addAll(result);
        }
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;

    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;

    }

    public List<T> getResult() {
        return this.result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

}
