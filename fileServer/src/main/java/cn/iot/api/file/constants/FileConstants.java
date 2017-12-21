/*
 * Copyright (c) 2017, China Mobile IOT All Rights Reserved.
 */
package cn.iot.api.file.constants;

/**
 * 
 * 文件接口下载路径
 *
 * @author wangjian
 * @date 2017年12月20日 上午11:18:01
 * @modify 2017年12月20日 wangjian v4.3.0 创建文件
 * @since v4.3.0
 */
public final class FileConstants {

    /** 下载文件路径 */
    public static final String DOWNLOAD_FILE_DIR = "fileDir";
    /** 下载文件前缀 */
    public static final String DOWNLOAD_FILE_PREFIX = "filePrefix";

    /** 下载文件后缀名 */
    public static final String DOWNLOAD_FILE_SUFFIX = ".csv";
    /** 下载文件压缩后缀名 */
    public static final String DOWNLOAD_ZIP_FILE_SUFFIX = ".zip";

    /** 需要压缩文件的类型 */
    private static final String[] ZIP_FILE_TYPE = new String[] { "csv", "txt" };

    /**
     * 
     * 获取需要压缩文件的类型
     *
     * @author wangjian
     * @date 2017年12月15日 下午3:32:15
     */
    public static String[] getZipFileType() {
        return ZIP_FILE_TYPE;
    }

    private FileConstants() {}


}
