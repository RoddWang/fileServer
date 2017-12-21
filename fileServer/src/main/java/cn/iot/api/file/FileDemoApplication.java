/*
 * Copyright (c) 2017, China Mobile IOT All Rights Reserved.
 */
package cn.iot.api.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * 
 * 启动类
 *
 * @author wangjian
 * @date 2017年12月20日 上午11:18:01
 * @modify 2017年12月20日 wangjian v4.3.0 创建文件
 * @since v4.3.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class FileDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileDemoApplication.class, args);
    }
}
