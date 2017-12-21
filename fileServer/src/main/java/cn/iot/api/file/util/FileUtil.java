/*
 * Copyright (c) 2017, China Mobile IOT All Rights Reserved.
 */
package cn.iot.api.file.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * 文件操作相关工具类
 *
 * @author wangjian
 * @date 2017年12月20日 上午11:18:01
 * @modify 2017年12月20日 wangjian v4.3.0 创建文件
 * @since v4.3.0
 */
public final class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);


    private FileUtil() {
        super();
    }

    /**
     * 
     * 将存放在sourceFilePath目录下符合要求的源文件，打包成fileName名称的zip文件，并存放到zipFilePath路径下
     *
     * @param sourceFilePath 待压缩的文件路径
     * @param zipSourceFilePrefix 待压缩的文件前缀
     * @param zipFileType 待压缩的文件包括类型
     * @param zipFilePath 压缩后存放路径
     * @param fileName 压缩后文件的名称（包括.zip）
     * @author wangjian
     * @date 2017年12月20日 下午3:35:05
     */
    public static boolean fileToZip(String sourceFilePath, String zipSourceFilePrefix, String[] zipFileType,
        String zipFilePath, String fileName) {
        File sourceFile = new File(sourceFilePath);
        if (!sourceFile.exists()) {
            logger.info("the compress file directory [{}] does not exist!", sourceFilePath);
            return false;
        }
        File[] sourceFiles = sourceFile.listFiles();
        if (null == sourceFiles || sourceFiles.length < 1) {
            logger.info("There is no file in the directory [{}] to be compressed", sourceFilePath);
            return false;
        }
        // 查找符合条件的文件
        Map<Integer, String> sourceFileMap = new HashMap<>();
        for (int i = 0; i < sourceFiles.length; i++) {
            String sourceFileName = sourceFiles[i].getName();
            String sourceFileType = sourceFileName.substring(sourceFileName.lastIndexOf('.') + 1);
            if (sourceFileName.contains(zipSourceFilePrefix) && ArrayUtils.contains(zipFileType, sourceFileType)) {
                sourceFileMap.put(i, sourceFileName);
            }

        }
        if (sourceFileMap.isEmpty()) {
            logger.info("Please confirm and try again without the documents.");
            return false;
        }

        String zipPathName = zipFilePath + File.separatorChar + fileName;
        File zipFile = new File(zipPathName);
        if (zipFile.exists()) {
            logger.info("The directory of files [{}] to be packaged already exists,be going to delete it.",
                zipPathName);
            if (!deleteFile(zipPathName)) {
                return false;
            }
        }
        try (
            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fos));
            ) {
            byte[] bufs = new byte[1024 * 10];
            for (Entry<Integer, String> entry : sourceFileMap.entrySet()) {
                // 创建ZIP实体，并添加进压缩包
                ZipEntry zipEntry = new ZipEntry(sourceFileMap.get(entry.getKey()));
                zos.putNextEntry(zipEntry);
                // 读取待压缩的文件并写进压缩包里
                readFileWriteToZip(sourceFiles, entry.getKey(), bufs, zos);
            }
        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException: exception msg is: {}", e);
            return false;
        } catch (IOException e) {
            logger.error("IOException: exception msg is: {}", e);
            return false;
        }
        logger.info("File compression success.and file name is {}.", fileName);
        return true;

    }

    /**
     * 读取待压缩的文件并写进压缩包里
     * 
     * @param sourceFiles
     * @param key
     * @param bufs
     * @param zos
     * @author wangjian
     * @date 2017年12月20日
     */
    private static boolean readFileWriteToZip(File[] sourceFiles, Integer key, byte[] bufs, ZipOutputStream zos) {
        try (
            FileInputStream fis = new FileInputStream(sourceFiles[key]);
            BufferedInputStream bis = new BufferedInputStream(fis, 1024 * 10);
            ) {
            int read = 0;
            while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                zos.write(bufs, 0, read);
            }
        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException: exception msg is: {}", e);
            return false;
        } catch (IOException e) {
            logger.error("IOException: exception msg is: {}", e);
            return false;
        }
        return true;
    }

    /**
     * 
     * 下载文件
     * 
     * @param res
     * @param parent [parent pathname string]
     * @param child [child pathname string]
     * @author wangjian
     * @date 2017年12月20日 下午3:15:11
     */
    public static boolean downloadFile(HttpServletResponse res, String parent, String child) {
        if (!StringUtils.hasText(parent) || !StringUtils.hasText(child)) {
            logger.info("parent pathname string and child pathname string can't is blank.");
            return false;
        }
        File file = new File(parent, child);
        String pathName = parent + child;
        if (!file.exists()) {
            logger.info("file [{}] isn't exist", pathName);
            return false;
        }
        if (!file.canRead()) {
            logger.info("don't permission to download this file [{}].", pathName);
            return false;
        }
        byte[] buff = new byte[1024];
        try (
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            OutputStream os = new BufferedOutputStream(res.getOutputStream());
            ) {
            res.setHeader("content-type", "application/octet-stream");
            // 设置强制下载不打开
            res.setContentType("application/force-download");
            res.setHeader("Content-Disposition", "attachment;filename=" + child);
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            logger.error(" BufferedInputStream exception:{}", e);
            return false;
        }
        logger.info("download file success.file name is {}", child);
        return true;
    }

    /**
     * 
     * 删除文件
     * 
     * @param sourceFilePath
     * @author wangjian
     * @date 2017年12月20日 下午4:31:03
     */
    public static boolean deleteFile(String sourceFilePath) {

        File file = new File(sourceFilePath);
        if (!file.exists()) {
            logger.info("file [{}] isn't exist,delete fail!", sourceFilePath);
            return false;
        }
        logger.info("delete file [{}] success!", sourceFilePath);
        return file.delete();
    }
}
