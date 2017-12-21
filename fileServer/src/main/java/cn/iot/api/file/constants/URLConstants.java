/*
 * Copyright (c) 2017, China Mobile IOT All Rights Reserved.
 */
package cn.iot.api.file.constants;

/**
 * 
 * URL常量
 *
 * @author wangjian
 * @date 2017年12月20日 上午11:18:01
 * @modify 2017年12月20日 wangjian v4.3.0 创建文件
 * @since v4.3.0
 */
public final class URLConstants {

    /**
     * 版本号
     */
    public static final String VERSION = "/v2";

    /**
     * 物联卡激活时间
     */
    public static final String ACTIVE_TIME_BY_DATE_FILE = VERSION + "/activeTimeByDateFile";

    /**
     * 集团下物联卡卡号查询
     */
    public static final String CARD_NO_BY_DATE_FILE = VERSION + "/cardNoByDateFile";

    /**
     * 物联卡状态查询
     */
    public static final String CARD_STATUS_BY_DATE_FILE = VERSION + "/cardStatusByDateFile";

    private URLConstants() {}



}
