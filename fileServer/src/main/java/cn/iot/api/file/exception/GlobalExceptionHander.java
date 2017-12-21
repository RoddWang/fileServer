/*
 * Copyright (c) 2017, China Mobile IOT All Rights Reserved.
 */
package cn.iot.api.file.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.iot.api.file.config.custom.CustomCacheHandler;
import cn.iot.api.file.constants.ErrorCode;
import cn.iot.api.file.util.NetUtils;
import cn.iot.api.file.vo.ResponseVo;

/**
 * 
 * 统一异常处理类
 *
 * @author wangjian
 * @date 2017年12月20日 上午11:18:01
 * @modify 2017年12月20日 wangjian v4.3.0 创建文件
 * @since v4.3.0
 */
@ControllerAdvice
public class GlobalExceptionHander {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHander.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseVo handleException(Exception e) {
        String transid = "";
        ServletRequestAttributes servletRequestAttributes =
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes != null) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            if (request != null) {
                transid = request.getParameter("transid");
                String ip = NetUtils.getClientIp(request);
                CustomCacheHandler.cacheContainer.remove(ip);
            }
        }
        
        LOGGER.error("transid[{}] error, exctption:{}", transid, e);
        return new ResponseVo<>(ErrorCode.ERR09900);
    }
}
