package com.example.springframework.boot.utils.web;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * 请求工具类
 */
public class RequestUtils {

    /**
     * 获取当前网络ip
     *
     * @param request http请求
     * @return ip
     */
    private static String getIpAddr(HttpServletRequest request) {
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

    /**
     * 判断是否为搜索引擎
     *
     * @param request http请求
     * @return 是否
     */
    public static boolean isRobot(HttpServletRequest request) {
        String ua = request.getHeader("user-agent");
        if (StringUtils.isBlank(ua)) {
            return false;
        }
        return ua.contains("Baiduspider")
                || ua.contains("Googlebot")
                || ua.contains("sogou")
                || ua.contains("sina")
                || ua.contains("iaskspider")
                || ua.contains("ia_archiver")
                || ua.contains("Sosospider")
                || ua.contains("YoudaoBot")
                || ua.contains("yahoo")
                || ua.contains("yodao")
                || ua.contains("MSNBot")
                || ua.contains("spider")
                || ua.contains("Twiceler")
                || ua.contains("Sosoimagespider")
                || ua.contains("naver.com/robots")
                || ua.contains("Nutch")
                || ua.contains("spider")
                ;
    }

    /**
     * 获取COOKIE
     *
     * @param request http请求
     * @param name    cookie名
     * @return cookie
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie ck : cookies) {
            if (StringUtils.equalsIgnoreCase(name, ck.getName())) {
                return ck;
            }
        }
        return null;
    }

    /**
     * 获取COOKIE值
     *
     * @param request http请求
     * @param name    cookie名
     * @return cookie值
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie ck : cookies) {
            if (StringUtils.equalsIgnoreCase(name, ck.getName())) {
                return ck.getValue();
            }
        }
        return null;
    }

    /**
     * 设置COOKIE
     *
     * @param request  http请求
     * @param response http响应
     * @param name     cookie名
     * @param value    cookie值
     * @param maxAge   cookie最大存活时间
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name,
                                 String value, int maxAge) {
        setCookie(request, response, name, value, maxAge, true);
    }

    /**
     * 设置COOKIE
     *
     * @param request      http请求
     * @param response     http响应
     * @param name         cookie名
     * @param value        cookie值
     * @param maxAge       cookie最大存活时间
     * @param allSubDomain 是否所有子域
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name,
                                 String value, int maxAge, boolean allSubDomain) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        if (allSubDomain) {
            String serverName = request.getServerName();
            String domain = getDomainOfServerName(serverName);
            if (domain != null && domain.indexOf('.') != -1) {
                //cookie.setDomain 设置跨域共享cookie
                cookie.setDomain('.' + domain);
            }
        }
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 删除cookie
     *
     * @param request      http请求
     * @param response     http响应
     * @param name         cookie名
     * @param allSubDomain 是否所有子域
     */
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response,
                                    String name, boolean allSubDomain) {
        setCookie(request, response, name, "", 0, allSubDomain);
    }

    /**
     * 获取用户访问URL中的根域名
     * 例如: www.google.com -> google.com
     *
     * @param host 域名
     * @return URL中的根域名
     */
    public static String getDomainOfServerName(String host) {
        if (isIPAddr(host)) {
            return null;
        }
        String[] names = StringUtils.split(host, '.');
        int len = names.length;
        if (len == 1) {
            return null;
        }
        if (len == 3) {
            return makeup(names[len - 2], names[len - 1]);
        }
        if (len > 3) {
            String dp = names[len - 2];
            if (
                    "com".equalsIgnoreCase(dp)
                            || "gov".equalsIgnoreCase(dp)
                            || "net".equalsIgnoreCase(dp)
                            || "edu".equalsIgnoreCase(dp)
                            || "org".equalsIgnoreCase(dp)
            ) {
                return makeup(names[len - 3], names[len - 2], names[len - 1]);
            } else {
                return makeup(names[len - 2], names[len - 1]);
            }
        }
        return host;
    }

    /**
     * 判断字符串是否是一个IP地址
     *
     * @param addr 地址
     * @return 是否
     */
    public static boolean isIPAddr(String addr) {
        if (StringUtils.isEmpty(addr)) {
            return false;
        }
        String[] ips = StringUtils.split(addr, '.');
        if (ips.length != 4) {
            return false;
        }
        try {
            int ipa = Integer.parseInt(ips[0]);
            int ipb = Integer.parseInt(ips[1]);
            int ipc = Integer.parseInt(ips[2]);
            int ipd = Integer.parseInt(ips[3]);
            return ipa >= 0 && ipa <= 255 && ipb >= 0 && ipb <= 255 && ipc >= 0
                    && ipc <= 255 && ipd >= 0 && ipd <= 255;
        } catch (Exception ignored) {
        }
        return false;
    }

    private static String makeup(String... ps) {
        StringBuilder s = new StringBuilder();
        for (int idx = 0; idx < ps.length; idx++) {
            if (idx > 0) {
                s.append('.');
            }
            s.append(ps[idx]);
        }
        return s.toString();
    }

    /**
     * 获取HTTP端口
     *
     * @param request http请求
     * @return 端口
     */
    public static int getHttpPort(HttpServletRequest request) {
        try {
            return new URL(request.getRequestURL().toString()).getPort();
        } catch (MalformedURLException excp) {
            return 80;
        }
    }

    /**
     * 获取浏览器提交的整形参数
     *
     * @param request      http请求
     * @param param        参数
     * @param defaultValue 默认值
     * @return 参数
     */
    public static int getParam(HttpServletRequest request, String param, int defaultValue) {
        return NumberUtils.toInt(request.getParameter(param), defaultValue);
    }

    /**
     * 获取浏览器提交的整形参数
     *
     * @param request      http请求
     * @param param        参数
     * @param defaultValue 默认值
     * @return 参数
     */
    public static long getParam(HttpServletRequest request, String param, long defaultValue) {
        return NumberUtils.toLong(request.getParameter(param), defaultValue);
    }

    /**
     * 获取浏览器提交的字符串参
     *
     * @param request      http请求
     * @param param        参数
     * @param defaultValue 默认值
     * @return 参数
     */
    public static String getParam(HttpServletRequest request, String param, String defaultValue) {
        String value = request.getParameter(param);
        return (StringUtils.isEmpty(value)) ? defaultValue : value;
    }

    /**
     * 获取浏览器提交参数的整形参数值数组
     *
     * @param request http请求
     * @param name    参数名
     * @return 整形参数值数组
     */
    public static long[] getParamValues(HttpServletRequest request, String name) {
        String[] values = request.getParameterValues(name);
        if (values == null) {
            return null;
        }
        return (long[]) ConvertUtils.convert(values, long.class);
    }

}
