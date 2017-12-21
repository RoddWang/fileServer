/*
 * Copyright (c) 2017, China Mobile IOT All Rights Reserved.
 */

package cn.iot.api.file.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 此类描述的是： 日期工具类
 * 
 * @author wangjian
 * @date 2017年12月20日 上午11:18:01
 * @modify 2017年12月20日 wangjian v4.3.0 创建文件
 * @since v4.3.0
 */
public final class DateUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
    /**
     * yyyy-MM-dd
     */
    public static final String DATE_1 = "yyyy-MM-dd";

    /**
     * yyyyMMdd
     */
    public static final String DATE_2 = "yyyyMMdd";

    /**
     * yyyyMM
     */
    public static final String DATE_3 = "yyyyMM";

    /**
     * yyyy-MM-dd HH:mm:ss,SSS
     */
    public static final String DATETIME_1 = "yyyy-MM-dd HH:mm:ss,SSS";

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String DATETIME_2 = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyyMMddHHmmss
     */
    public static final String DATETIME_3 = "yyyyMMddHHmmss";

    /**
     * 19位时间格式 yyyyMMddHHmmss.
     */
    public static final String DATETIME_4 = "yyyy-MM-dd HH:mm";

    /**
     * yyyyMMddHHmm
     */
    public static final String DATETIME_5 = "yyyyMMddHHmm";


    /**
     * 判断当天是否是本月的第一天
     *
     * @return
     */
    public static boolean isFirstDayOfMonth() {
        LocalDate today = LocalDate.now();
        int day = today.getDayOfMonth();
        return day == 1;
    }

    /**
     * 获取昨天23:59:59时间戳
     *
     * @return the yestday time long
     */
    public static long getYestday235959Timestamp() {
        LocalDateTime yesterday = LocalDateTime.of(getYestdayLocalDate(), getTime235959());
        return toTimeStamp(yesterday);
    }

    /**
     * 获取当前时间的时间戳
     *
     * @return the yestday time long
     */
    public static long getNowDateTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        return toTimeStamp(now);
    }

    /**
     * 获取本月第一天00:00:00时间戳
     *
     * @return
     */
    public static long getFirstDatOfMonthTimestamp() {
        LocalDate lastMonthDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        LocalDateTime lastMonthTime = LocalDateTime.of(lastMonthDate, getTime000000());
        return toTimeStamp(lastMonthTime);
    }

    /**
     * 获取本月最后一天23:59:59时间戳
     *
     * @return
     */
    public static long getLastDatOfMonthTimestamp() {
        LocalDate lastMonthDate =
            LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().lengthOfMonth());
        LocalDateTime lastMonthTime = LocalDateTime.of(lastMonthDate, getTime235959());
        return toTimeStamp(lastMonthTime);
    }

    /**
     * 获取上个月第一天00:00:00时间戳
     *
     * @return
     */
    public static long getFirstDatOfLastMonthTimestamp() {
        LocalDate lastMoth = LocalDate.now().minusMonths(1);
        LocalDate lastMonthDate = LocalDate.of(lastMoth.getYear(), lastMoth.getMonth(), 1);
        LocalDateTime firstDatOfLastMonthTime = LocalDateTime.of(lastMonthDate, getTime000000());
        return toTimeStamp(firstDatOfLastMonthTime);
    }

    /**
     * 获取上个月最后一天23:59:59时间戳
     *
     * @return
     */
    public static long getLastDatOfLastMonthTimestamp() {
        LocalDate lastMoth = LocalDate.now().minusMonths(1);
        LocalDate lastMonthDate = LocalDate.of(lastMoth.getYear(), lastMoth.getMonth(), lastMoth.lengthOfMonth());
        LocalDateTime lastDatOfLastMonthTime = LocalDateTime.of(lastMonthDate, getTime235959());
        return toTimeStamp(lastDatOfLastMonthTime);
    }

    /**
     * 获取指定日期的开始时间戳（指定日期00:00:00）
     * <p>
     * 时间格式：yyyyMMdd
     * 
     * @param date
     * @return
     */
    public static long getDateStartTimestamp(String date) {
        LocalDate localDate = formatLocalDate(date, DATE_2);
        LocalDateTime time = LocalDateTime.of(localDate, getTime000000());
        return toTimeStamp(time);
    }

    /**
     * 获取指定日期的结束时间戳（指定日期23:59:59）
     * <p>
     * 时间格式：yyyyMMdd
     *
     * @param date
     * @return
     */
    public static long getDateEndTimestamp(String date) {
        LocalDate localDate = formatLocalDate(date, DATE_2);
        LocalDateTime time = LocalDateTime.of(localDate, getTime235959());
        return toTimeStamp(time);
    }


    /**
     * 格式化LocalDate
     *
     * @param dataStr
     * @return
     */
    public static LocalDate formatLocalDate(String dataStr, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(dataStr, formatter);
    }

    /**
     * 根据formatStr转换对应string字符串值 string.
     *
     * @param localDateTime 日期时间
     * @param formatStr 描述此参数
     * @return string 描述此返回参数
     * @author Dul
     */
    public static String toString(LocalDateTime localDateTime, String formatStr) {
        return localDateTime.format(DateTimeFormatter.ofPattern(formatStr));
    }

    /**
     * LocalDateTime转换时间戳
     *
     * @param localDateTime
     */
    public static long toTimeStamp(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * LocalDate转换时间戳
     * 
     * @param localDate 描述此参数
     */
    public static long toTimeStamp(LocalDate localDate) {
        return localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }


    /**
     * 时间戳转换LocalDateTime
     * 
     * @param timeStamp 描述此参数
     * @return 返回 local date time 描述此返回参数
     */
    public static LocalDateTime toLocalDateTime(long timeStamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZoneId.systemDefault());
    }

    /**
     * 获取一个00:00:00的时间
     *
     * @return
     */
    public static LocalTime getTime000000() {
        return LocalTime.of(0, 0, 0);
    }

    /**
     * 获取一个23:59:59的时间
     *
     * @return
     */
    public static LocalTime getTime235959() {
        return LocalTime.of(23, 59, 59);
    }

    /**
     * 获取一个昨天的日期
     *
     * @return
     */
    public static LocalDate getYestdayLocalDate() {
        return LocalDate.now().minusDays(1);
    }

    /**
     * 判断所传年月的月份是否为当月
     *
     * @param date 传入的时间，时间格式为：yyyyMM
     * @return 返回所传月份是否是当月
     * @author wangjunhua
     * @date 2017 -08-24 15:48:14
     * @modify 2017 -08-24 wangjunhua v0.0.0 修改原因、修改记录等
     * @since v0.0.0 版本号请手动修改
     */
    public static boolean isNowMonth(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        LocalDate today = LocalDate.now();
        int nowMonth = today.getMonthValue();
        int nowYear = today.getYear();
        return year == nowYear && month == nowMonth;
    }

    /**
     * 获取一个月的第一天00:00:00的时间戳
     *
     * @param date 传入的时间为年月格式：yyyyMM
     * @return 返回传入的月份的第一天的00:00:00的时间戳
     * @author wangjunhua
     * @date 2017 -08-24 15:42:05
     * @modify 2017 -08-24 wangjunhua v0.0.0 修改原因、修改记录等
     * @since v0.0.0 版本号请手动修改
     */
    public static long getFirstDateOfMonthTimestamp(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        LocalDate localDate = LocalDate.of(year, month, 1);
        LocalDateTime time = LocalDateTime.of(localDate, getTime000000());
        return toTimeStamp(time);
    }

    /**
     * 获取一个月的最后一天的00:00:00的时间戳
     *
     * @param date 传入的时间为年月格式：yyyyMM
     * @return 返回传入的月份的最后一天的00:00:00的时间戳
     * @author wangjunhua
     * @date 2017 -08-24 15:43:59
     * @modify 2017 -08-24 wangjunhua v0.0.0 修改原因、修改记录等
     * @since v0.0.0 版本号请手动修改
     */
    public static long getEndDateOfMonthTimestamp(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        LocalDate localDate = LocalDate.of(year, month, 1);
        int day = localDate.lengthOfMonth();
        LocalDate endDate = LocalDate.of(year, month, day);
        LocalDateTime time = LocalDateTime.of(endDate, getTime235959());
        return toTimeStamp(time);
    }

    /**
     * 将日期转换未指定类型的字符串.
     *
     * @param timeStamp 要转换的时间戳
     * @param format 格式
     * @return 转换完成的字符串
     */
    public static String formatTimeStamp(Long timeStamp, String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(timeStamp);
    }

    /**
     * 转换字符串为Date.
     *
     * @param dateStr 要转换的时间字符串
     * @param format 时间格式
     * @return Date 时间类型结果
     */
    public static Date parseString(String dateStr, String format) {
        if (StringUtils.isEmpty(dateStr)) {
            logger.warn("invalid data,dateStr={}", dateStr);
            return null;
        }

        if (StringUtils.isEmpty(format)) {
            logger.warn("invalid format,format={}", format);
            return null;
        }
        DateFormat df = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = df.parse(dateStr);
            if (!dateStr.equals(df.format(date))) {
                date = null;
            }
        } catch (ParseException e) {
            logger.error("fail to parse date", e);
        }
        return date;
    }

    /**   
     * 创建一个新的实例 DateUtil.   
     *      
     */
    private DateUtil() {
        super();
    }

}
