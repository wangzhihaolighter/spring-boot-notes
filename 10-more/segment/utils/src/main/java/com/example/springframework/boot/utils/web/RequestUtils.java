package com.example.springframework.boot.utils.web;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 请求工具类
 */
public class RequestUtils {

    /**
     * 获取当前网络ip
     *
     * @param request 请求
     * @return ip
     */
    private String getIpAddr(HttpServletRequest request) {
        String unknown = "unknown";
        String localIpv4 = "127.0.0.1";
        String localIpv6 = "0:0:0:0:0:0:0:1";
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (localIpv4.equals(ipAddress) || localIpv6.equals(ipAddress)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                    ipAddress = inet.getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }

        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        //"***.***.***.***".length() = 15
        int multiIpLength = 15;
        String multiIpSplitter = ",";
        if (ipAddress != null && ipAddress.length() > multiIpLength) {
            if (ipAddress.indexOf(multiIpSplitter) > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(multiIpSplitter));
            }
        }
        return ipAddress;
    }

}
