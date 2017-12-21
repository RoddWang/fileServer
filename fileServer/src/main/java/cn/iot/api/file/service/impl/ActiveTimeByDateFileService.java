/*
 * Copyright (c) 2017, China Mobile IOT All Rights Reserved.
 */
package cn.iot.api.file.service.impl;

import java.io.File;
/**
 * 此类描述的是： 集团下物联卡卡号查询service实现层
 * 
 * @author wangjian
 * @date 2017年12月20日 上午11:18:01
 * @modify 2017年12月20日 wangjian v4.3.0 创建文件
 * @since v4.3.0
 */
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.iot.api.file.config.custom.CustomConfigProperties;
import cn.iot.api.file.constants.ErrorCode;
import cn.iot.api.file.constants.FileConstants;
import cn.iot.api.file.service.IActiveTimeByDateFileService;
import cn.iot.api.file.util.DateUtil;
import cn.iot.api.file.util.FileUtil;
import cn.iot.api.file.vo.ResponseVo;

/**
 * 物联卡激活时间service 实现层
 * 
 * @author wangjian
 * @date 2017年12月20日 下午2:22:41
 * @modify 2017年12月20日 wangjian v4.3.0 创建文件
 * @since v4.3.0
 */
@Service
public class ActiveTimeByDateFileService implements IActiveTimeByDateFileService {

    private static final Logger logger = LoggerFactory.getLogger(ActiveTimeByDateFileService.class);

    /** 物联卡激活时间 文件下载的配置key值 */
    private static final String ACTIVE_TIME = "activeTime";
    /** 自定义文件下载的相关属性 */
    @Autowired
    private CustomConfigProperties customConfigProperties;

    @Override
    public ResponseVo downloadActiveTimeByDateFile(HttpServletResponse res, String queryDate) {
        String fileDir = customConfigProperties.getProertiesMapProp(ACTIVE_TIME, FileConstants.DOWNLOAD_FILE_DIR);
        String filePrefix = customConfigProperties.getProertiesMapProp(ACTIVE_TIME, FileConstants.DOWNLOAD_FILE_PREFIX);
        if (!StringUtils.hasText(fileDir) || !StringUtils.hasText(filePrefix)) {
            logger.info(
                "The relevant content is not configured in the custom property configuration.prarent key[{}],child key:[{}],[{}]",
                ACTIVE_TIME, FileConstants.DOWNLOAD_FILE_DIR, FileConstants.DOWNLOAD_FILE_PREFIX);
            return new ResponseVo<>(ErrorCode.ERR09900);
        }
        String zipSourceFilePrefix = filePrefix + queryDate;
        String zipFileName = zipSourceFilePrefix+"_"+DateUtil.getNowDateTimestamp()+FileConstants.DOWNLOAD_ZIP_FILE_SUFFIX;
        boolean zipFlag =
            FileUtil.fileToZip(fileDir, zipSourceFilePrefix, FileConstants.getZipFileType(), fileDir, zipFileName);
        if (!zipFlag) {
            logger.info("compress file fail!queryDate: [{}],zipFileName: [{}].", queryDate, zipFileName);
            return new ResponseVo<>(ErrorCode.ERR00430);
        }
        boolean downloadFlag = FileUtil.downloadFile(res, fileDir, zipFileName);
        if (!downloadFlag) {
            logger.info("download file fail.queryDate: [{}],zipFileName: [{}].", queryDate, zipFileName);
            return new ResponseVo<>(ErrorCode.ERR09900);
        }
        String zipPathName = fileDir + File.separatorChar + zipFileName;
        FileUtil.deleteFile(zipPathName);
        return new ResponseVo<>(ErrorCode.SUCCESS);
    }
}
