/*
 * Copyright (c) 2017, China Mobile IOT All Rights Reserved.
 */
package cn.iot.api.file.config.custom;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 自定义属性配置
 * 
 * @author wangjian
 * @date 2017年12月19日 上午11:19:24
 * @modify 2017年12月19日 wangjian v 创建文件
 * @since v4.3.0
 */
@ConfigurationProperties(prefix = "custom")
public class CustomConfigProperties {

    /** 过期时间 单位天 */
    private Long customTokenExpressTime;

    /** 文件下载属性 */
    private List<Map<String, Map<String, String>>> fileDownloadPropsList;


    /**
     * 获取属性 customTokenExpressTime 的值
     *
     * @return customTokenExpressTime
     */
    public Long getCustomTokenExpressTime() {
        return customTokenExpressTime;
    }

    /**
     * 设置属性 customTokenExpressTime 的值为customTokenExpressTime
     * 
     * @Param customTokenExpressTime
     */
    public void setCustomTokenExpressTime(Long customTokenExpressTime) {
        this.customTokenExpressTime = customTokenExpressTime == null ? 0 : customTokenExpressTime * 24 * 60 * 60 * 1000;
    }

    /**
     * 获取属性 fileDownloadPropsList 的值
     *
     * @return fileDownloadPropsList
     */
    public List<Map<String, Map<String, String>>> getFileDownloadPropsList() {
        return fileDownloadPropsList;
    }

    /**
     * 设置属性 fileDownloadPropsList 的值为fileDownloadPropsList
     * 
     * @Param fileDownloadPropsList
     */
    public void setFileDownloadPropsList(List<Map<String, Map<String, String>>> fileDownloadPropsList) {
        this.fileDownloadPropsList = fileDownloadPropsList;
    }

    /**
     * 
     * 获取下载文件map
     * 
     * @param parentKey
     * @return
     * @author wangjian
     * @date 2017年12月20日
     */
    public Map<String, String> getPropertiesMap(String parentKey) {
        if (this.fileDownloadPropsList.isEmpty()) {
            return null;
        }
        for (Map<String, Map<String, String>> map : this.fileDownloadPropsList) {
            if (map.containsKey(parentKey)) {
                return map.get(parentKey);
            }
        }
        return null;
    }

    /**
     * 
     * 获取下载文件map中的某个属性
     * 
     * @param parentKey
     * @param childKey
     * @return
     * @author wangjian
     * @date 2017年12月20日
     */
    public String getProertiesMapProp(String parentKey, String childKey) {
        Map<String, String> map = this.getPropertiesMap(parentKey);
        if (map.isEmpty()) {
            return null;
        }
        if (map.containsKey(childKey)) {
            return map.get(childKey);
        }
        return null;
    }

    /**
     * 此方法描述的是：tostring
     * 
     * @author: wangjian
     * @date: 2017年12月20日 上午11:06:58
     */
    @Override
    public String toString() {
        return "CustomConfigProperties [customTokenExpressTime=" + customTokenExpressTime + ", fileDownloadPropsList=" + fileDownloadPropsList
            + "]";
    }


}
