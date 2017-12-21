/*
 * Copyright (c) 2017, China Mobile IOT All Rights Reserved.
 */
package cn.iot.api.file.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.iot.api.file.config.custom.CustomCacheHandler;
import cn.iot.api.file.config.custom.CustomConfigProperties;
import cn.iot.api.file.util.DateUtil;
import cn.iot.api.file.util.NetUtils;

/**
 * 自定义文件下载拦截器 拦截token超时和判断条件同一个IP只能同时下载一个文件
 *
 * @author wangjian
 * @date 2017年12月20日 上午11:18:01
 * @modify 2017年12月20日 wangjian v4.3.0 创建文件
 * @since v4.3.0
 */
@Component
@EnableConfigurationProperties(value = CustomConfigProperties.class)
public class CustomFileDownloadInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(CustomFileDownloadInterceptor.class);

    @Autowired
    private CustomConfigProperties customConfigProperties;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView) throws Exception {
        // 处理完就移除此ip的信息
        String ip = NetUtils.getClientIp(request);
        CustomCacheHandler.cacheContainer.remove(ip);
        logger.info("postHandle remove ip [{}]!", ip);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception {
        //
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        String customToken = request.getParameter("customToken");
        if (!StringUtils.hasText(customToken)) {
            logger.info("prameter customToken can't is empty!");
            return false;
        }
        long nowTime = DateUtil.getNowDateTimestamp();
        long expressTime = customConfigProperties.getCustomTokenExpressTime() + Long.valueOf(customToken);
        boolean isExpressed = (expressTime - nowTime) > 0;
        if (!isExpressed) {
            logger.info("customToken [{}] is expressed.", customToken);
            return false;
        }
        logger.debug("pass custom customToken validate!");
        // 同一个IP只能同时下载一个文件
        String ip = NetUtils.getClientIp(request);
        if (CustomCacheHandler.cacheContainer != null && CustomCacheHandler.cacheContainer.containsKey(ip)) {
            logger.info("current download IP[{}] is downloading,Please try again later! ", ip);
            return false;
        }
        CustomCacheHandler.cacheContainer.put(ip, true);
        logger.info("current IP [{}] can download file!", ip);

        return isExpressed;
    }


}
