/*
 * Copyright (c) 2017, China Mobile IOT All Rights Reserved.
 */
package cn.iot.api.file.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.iot.api.file.constants.ErrorCode;
import cn.iot.api.file.constants.URLConstants;
import cn.iot.api.file.service.IActiveTimeByDateFileService;
import cn.iot.api.file.util.DateUtil;
import cn.iot.api.file.vo.ResponseVo;

/**
 * 
 * 物联卡激活时间
 *
 * @author wangjian
 * @date 2017年12月20日 上午11:18:01
 * @modify 2017年12月20日 wangjian v4.3.0 创建文件
 * @since v4.3.0
 */
@RestController
public class ActiveTimeByDateFileController {

    private static final Logger logger = LoggerFactory.getLogger(ActiveTimeByDateFileController.class);

    @Autowired
    private IActiveTimeByDateFileService activeTimeByDateFileService;

    @RequestMapping(value = { URLConstants.ACTIVE_TIME_BY_DATE_FILE })
    public ResponseVo downloadActiveTimeByDateFile(HttpServletResponse res, HttpServletRequest req) {
        long startTime = System.currentTimeMillis();
        String queryDate = req.getParameter("query_date");
        String transid = req.getParameter("transid");
        if (!StringUtils.hasText(queryDate)) {
            logger.info("query_date isn't empty!");
            return new ResponseVo<>(ErrorCode.ERR01000);
        }
        if (null == DateUtil.parseString(queryDate, DateUtil.DATE_2)) {
            logger.info("parmeter query_date [{}] format is error.", queryDate);
            return new ResponseVo<>(ErrorCode.ERR01400);
        }
        ResponseVo responseVo =
            activeTimeByDateFileService.downloadActiveTimeByDateFile(res, queryDate);
        logger.info("[{}] end downloadActiveTimeByDateFile.timeConsu:[{}]. ", transid,
            (System.currentTimeMillis() - startTime));
        return responseVo;
    }
}
