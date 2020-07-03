package com.nervenets.general.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Joe
 * Date: 2011-4-30 23:51:24
 */
public final class HttpTools {
    private static Log log = LogFactory.getLog(HttpTools.class);

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-real-ip");//先从nginx自定义配置获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static long getIp(HttpServletRequest request) {
        final String ipAddress = HttpTools.getIpAddress(request);
        return NumberUtil.ipToLong(ipAddress);
    }
}
