/*
 * Copyright (c) 2017, China Mobile IOT All Rights Reserved.
 */
package cn.iot.api.file.config.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import cn.iot.api.file.interceptor.CustomFileDownloadInterceptor;

/**
 * 自定义拦截器注册管理
 *
 * @author wangjian
 * @date 2017年12月20日 上午11:18:01
 * @modify 2017年12月20日 wangjian v4.3.0 创建文件
 * @since v4.3.0
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {


    @Autowired
    private CustomFileDownloadInterceptor customFileDownloadInterceptor;

    /**
     * 注册 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(customFileDownloadInterceptor).addPathPatterns("/**/**File");
    }


}
