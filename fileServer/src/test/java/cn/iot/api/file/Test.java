package cn.iot.api.file;

import cn.iot.api.file.util.DateUtil;

public class Test {

    public static void main(String[] args) {
        long minutes = 60*1000;
        long now = DateUtil.getNowDateTimestamp();
        System.out.println(now);
        System.out.println("当前时间戳"+DateUtil.formatTimeStamp(now+ minutes*30, DateUtil.DATETIME_1));
    }
}
