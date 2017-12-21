/*
 * Copyright (c) 2017, China Mobile IOT All Rights Reserved.
 */
package cn.iot.api.file.config.custom;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义缓存处理器
 * 
 * @author wangjian
 * @date 2017年12月21日 上午9:22:33
 * @modify 2017年12月21日 wangjian v4.3.0创建文件
 * @since v4.3.0
 */
public class CustomCacheHandler {

    /** 自定义缓存容器 */
    public static ConcurrentHashMap<Object, Object> cacheContainer = new ConcurrentHashMap<>();

    /**
     * 
     * 缓存对象
     * 
     * @param cacheName
     * @param cacheValue
     * @author wangjian
     * @date 2017年12月21日
     */
    public static synchronized void put(Object cacheName, Object cacheValue) {
        cacheContainer.put(cacheName, cacheValue);
    }

    /**
     * 
     * 获取缓存
     * 
     * @param cacheName
     * @return
     * @author wangjian
     * @date 2017年12月21日
     */
    public static Object get(Object cacheName) {
        return cacheContainer.get(cacheName);
    }

    /**
     * 
     * 移除某个缓存
     * 
     * @param cahcheName
     * @return
     * @author wangjian
     * @date 2017年12月21日
     */
    public static synchronized boolean remove(Object cahcheName) {
        return cacheContainer.remove(cahcheName) != null;
    }

    /**
     * 
     * 清空缓存
     * 
     * @author wangjian
     * @date 2017年12月21日
     */
    public static synchronized void clearAllCache() {
        cacheContainer.clear();
    }

    /**
     * 创建一个新的实例 CustomCacheHandler.
     * 
     */
    private CustomCacheHandler() {
        super();
    }


}
