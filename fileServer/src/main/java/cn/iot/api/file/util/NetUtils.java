/*
 * Copyright (c) 2017, China Mobile IOT All Rights Reserved.
 */

package cn.iot.api.file.util;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * 此类描述的是：
 * 
 * @author wangjian
 * @date 2017年12月20日 上午11:18:01
 * @modify 2017年12月20日 wangjian v4.3.0 创建文件
 * @since v4.3.0
 */
public class NetUtils {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(NetUtils.class);
    public static final String UNKNOWN = "unknown";

    /**
     * 
     * 创建一个新的私有实例 NetUtils. 禁止实例化工具类!
     *
     */
    private NetUtils() {}

    /**
     * 根据系统的类型获取本服务器的ip地址
     * 
     * InetAddress inet = InetAddress.getLocalHost(); 但是上述代码在Linux下返回127.0.0.1。
     * 主要是在linux下返回的是/etc/hosts中配置的localhost的ip地址， 而不是网卡的绑定地址。后来改用网卡的绑定地址，可以取到本机的ip地址：）：
     * 
     * @return the system local ip
     * @throws UnknownHostException the unknown host exception
     */
    public static String getSystemLocalIp() throws UnknownHostException {
        InetAddress inet = null;
        String osname = getSystemOSName();
        String ipString = "";

        try {
            // 针对window系统
            if (osname.equalsIgnoreCase("Windows XP")) {
                inet = getWinLocalIp();
                ipString = inet.getHostAddress();
                logger.info("windows xp system local ip address --> {}", ipString);
                // 针对linux系统
            }
            if (osname.equalsIgnoreCase("Windows 7")) {
                inet = InetAddress.getLocalHost();
                ipString = inet.getHostAddress();
                logger.info("windows 7 system local ip address--> {}", ipString);
            } else if (osname.equalsIgnoreCase("Linux")) {
                logger.info("Linux system local ip address-->" + getLocalIP());
                ipString = getLocalIP();
            }
            if (null == ipString) {
                throw new UnknownHostException("主机的ip地址未知");
            }
        } catch (Exception e) {
            logger.error("cn.iot.api.base.util.NetUtils.getSystemLocalIp()" + e.getMessage());
        }
        return ipString;
    }

    /**
     * 获取FTP的配置操作系统.
     * 
     * @return the system os name
     */
    private static String getSystemOSName() {
        // 获得系统属性集
        Properties props = System.getProperties();
        // 操作系统名称
        String osname = props.getProperty("os.name");
        if (logger.isDebugEnabled()) {
            logger.info("the system os Name " + osname);
        }
        return osname;
    }

    /**
     * 获取window 本地ip地址.
     * 
     * @return the win local ip
     * @throws UnknownHostException the unknown host exception
     */
    private static InetAddress getWinLocalIp() throws UnknownHostException {
        InetAddress inet = InetAddress.getLocalHost();
        logger.info("本机的ip={}", inet.getHostAddress());
        return inet;
    }

    /**
     * Gets the local ip.
     * 
     * @return the local ip
     */
    private static String getLocalIP() {
        String ip = "";
        try {
            Enumeration<?> e1 = NetworkInterface.getNetworkInterfaces();
            while (e1.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) e1.nextElement();
                logger.info("----getLocalIP of {}", ni.getName());
                if (!ni.getName().equals("eth0") && !"eno1".equals(ni.getName()) && !ni.getName().equals("bond0")) {
                    logger.info("name:{}", ni.getName());
                } else {
                    ip = getIpExtract(ip, ni);
                    break;
                }
            }
        } catch (SocketException e) {
            logger.error("获取本机ip错误" + e.getMessage());
        }
        return ip;
    }

    private static String getIpExtract(String ip, NetworkInterface ni) {
        String innerVaribleIp = ip;
        Enumeration<?> e2 = ni.getInetAddresses();
        while (e2.hasMoreElements()) {
            InetAddress ia = (InetAddress) e2.nextElement();
            if (ia instanceof Inet6Address) {
                continue;
            }
            innerVaribleIp = ia.getHostAddress();
        }
        return innerVaribleIp;
    }

    /**
     * 
     * get client ip
     * 
     * @param request
     * @return
     * @author wangjian
     * @date 2017年9月27日
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
