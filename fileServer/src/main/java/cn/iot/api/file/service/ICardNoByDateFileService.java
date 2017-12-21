/**
 * Copyright (c) 2017, China Mobile IOT All Rights Reserved.
 */
package cn.iot.api.file.service;

import javax.servlet.http.HttpServletResponse;

import cn.iot.api.file.vo.ResponseVo;

/**
 * 此类描述的是： 集团下物联卡卡号查询service层
 * 
 * @author wangjian
 * @date 2017年12月20日 上午11:18:01
 * @modify 2017年12月20日 wangjian v4.3.0 创建文件
 * @since v4.3.0
 */
public interface ICardNoByDateFileService {

    /**
     * 
     * 集团下物联卡卡号查询文件下载
     * 
     * @param res
     * @param queryDate
     * @return
     * @author wangjian
     * @date 2017年12月20日
     */
    ResponseVo downloadCardNoByDateFile(HttpServletResponse res, String queryDate);
}
