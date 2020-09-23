package com.example.springframework.boot.utils.web.http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 简易的 HTTP 请求工具, 一个静态方法完成请求, 支持 301, 302 的重定向, 支持自动识别 charset, 支持同进程中 Cookie 的自动保存与发送  <br/><br/>
 *
 * Demo:
 *
 * <pre>{@code
 *     // (可选) 设置默认的User-Agent是否为移动浏览器模式, 默认为false(PC浏览器模式)
 *     SimpleHttpUtils.setMobileBrowserModel(true);
 *     // (可选) 设置默认的请求头, 每次请求时都将会 添加 并 覆盖 原有的默认请求头
 *     SimpleHttpUtils.setDefaultRequestHeader("header-key", "header-value");
 *     // (可选) 设置 连接 和 读取 的超时时间, 连接超时时间默认为15000毫秒, 读取超时时间为0(即不检查超时)
 *     SimpleHttpUtils.setTimeOut(15000, 0);
 *
 *     // GET 请求, 返回响应文本
 *     String html = SimpleHttpUtils.get("http://blog.csdn.net/");
 *
 *     // GET 请求, 下载文件, 返回文件路径
 *     SimpleHttpUtils.get("http://blog.csdn.net/", new File("csdn.txt"));
 *
 *     // POST 请求, 返回响应文本
 *     String respText = SimpleHttpUtils.post("http://url", "body-data".getBytes());
 *
 *     // POST 请求, 上传文件, 返回响应文本
 *     SimpleHttpUtils.post("http://url", new File("aa.jpg"));
 *
 *     // 还有其他若干 get(...) 和 post(...) 方法的重载(例如请求时单独添加请求头), 详见代码实现
 * }</pre>
 *
 * 来源：https://blog.csdn.net/xietansheng/article/details/70478221
 * @author xietansheng
 */
public class SimpleHttpUtils {

    /** 文本请求时所限制的最大响应长度, 5MB */
    private static final int TEXT_REQUEST_MAX_LENGTH = 5 * 1024 * 1024;

    /** 默认的请求头 */
    private static final Map<String, String> DEFAULT_REQUEST_HEADERS = new HashMap<String, String>();

    /** 操作默认请求头的读写锁 */
    private static final ReadWriteLock RW_LOCK = new ReentrantReadWriteLock();

    /** User-Agent PC: Windows10 IE 11 */
    private static final String USER_AGENT_FOR_PC = "Mozilla 0.0 Mozilla/5.0 (Windows NT 10.0; Trident/7.0; rv:11.0) like Gecko";

    /** User-Agent Mobile: Android 7.0 Chrome 浏览器 */
    private static final String USER_AGENT_FOR_MOBILE = "Chrome Mozilla/5.0 (Linux; Android 7.0; Nexus 6 Build/NBD92D) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.132 Mobile Safari/537.36";

    /** 连接超时时间, 单位: ms, 0 表示无穷大超时(即不检查超时), 默认 15s 超时 */
    private static int CONNECT_TIME_OUT = 15 * 1000;

    /** 读取超时时间, 单位: ms, 0 表示无穷大超时(即不检查超时), 默认为 0 */
    private static int READ_TIME_OUT = 0;

    static {
        // 设置一个默认的 Cookie 管理器, 使用 HttpURLConnection 请求时,
        // 会在内存中自动保存相应的 Cookie, 并且在下一个请求时自动发送相应的 Cookie
        CookieHandler.setDefault(new CookieManager());

        // 默认使用PC浏览器模式
        setMobileBrowserModel(false);
    }

    /**
     * 设置默认的User-Agent是否为移动浏览器模式, 默认为PC浏览器模式,  <br/>
     *
     * 也可以通过 {@link #setDefaultRequestHeader(String, String)} 自定义设置 User-Agent
     *
     * @param isMobileBrowser true: 手机浏览器; false: PC浏览器
     */
    public static void setMobileBrowserModel(boolean isMobileBrowser) {
        setDefaultRequestHeader("User-Agent", isMobileBrowser ? USER_AGENT_FOR_MOBILE : USER_AGENT_FOR_PC);
    }

    /**
     * 设置超时时间, 单位: ms, 0 表示无穷大超时(即不检查超时)
     *
     * @param connectTimeOut 连接超时时间, 默认为 15s
     * @param readTimeOut 读取超时时间, 默认为 0
     */
    public static void setTimeOut(int connectTimeOut, int readTimeOut) {
        if (connectTimeOut < 0 || readTimeOut < 0) {
            throw new IllegalArgumentException("timeout can not be negative");
        }
        RW_LOCK.writeLock().lock();
        try {
            CONNECT_TIME_OUT = connectTimeOut;
            READ_TIME_OUT = readTimeOut;
        } finally {
            RW_LOCK.writeLock().unlock();
        }
    }

    /**
     * 设置默认的请求头, 每次请求时都将会 添加 并 覆盖 原有的默认请求头
     */
    public static void setDefaultRequestHeader(String key, String value) {
        RW_LOCK.writeLock().lock();
        try {
            DEFAULT_REQUEST_HEADERS.put(key, value);
        } finally {
            RW_LOCK.writeLock().unlock();
        }
    }

    /**
     * 移除默认的请求头
     */
    public static void removeDefaultRequestHeader(String key) {
        RW_LOCK.writeLock().lock();
        try {
            DEFAULT_REQUEST_HEADERS.remove(key);
        } finally {
            RW_LOCK.writeLock().unlock();
        }
    }

    public static String get(String url) throws Exception  {
        return get(url, null, null);
    }

    public static String get(String url, Map<String, String> headers) throws Exception  {
        return get(url, headers, null);
    }

    public static String get(String url, File saveToFile) throws Exception  {
        return get(url, null, saveToFile);
    }

    public static String get(String url, Map<String, String> headers, File saveToFile) throws Exception  {
        return sendRequest(url, "GET", headers, null, saveToFile);
    }

    public static String post(String url, byte[] body) throws Exception {
        return post(url, null, body);
    }

    public static String post(String url, Map<String, String> headers, byte[] body) throws Exception {
        InputStream in = null;
        if (body != null && body.length > 0) {
            in = new ByteArrayInputStream(body);
        }
        return post(url, headers, in);
    }

    public static String post(String url, File bodyFile) throws Exception {
        return post(url, null, bodyFile);
    }

    public static String post(String url, Map<String, String> headers, File bodyFile) throws Exception {
        InputStream in = null;
        if (bodyFile != null && bodyFile.exists() && bodyFile.isFile() && bodyFile.length() > 0) {
            in = new FileInputStream(bodyFile);
        }
        return post(url, headers, in);
    }

    public static String post(String url, InputStream bodyStream) throws Exception {
        return post(url, null, bodyStream);
    }

    public static String post(String url, Map<String, String> headers, InputStream bodyStream) throws Exception {
        return sendRequest(url, "POST", headers, bodyStream, null);
    }

    /**
     * 执行一个通用的 http/https 请求, 支持 301, 302 的重定向, 支持自动识别 charset, 支持同进程中 Cookie 的自动保存与发送
     *
     * @param url
     *          请求的链接, 只支持 http 和 https 链接
     *
     * @param method
     *          (可选) 请求方法, 可以为 null
     *
     * @param headers
     *          (可选) 请求头 (将覆盖默认请求), 可以为 null
     *
     * @param bodyStream
     *          (可选) 请求内容, 流将自动关闭, 可以为 null
     *
     * @param saveToFile
     *          (可选) 响应保存到该文件, 可以为 null
     *
     * @return
     *          如果响应内容保存到文件, 则返回文件路径, 否则返回响应内容的文本 (自动解析 charset 进行解码)
     *
     * @throws Exception
     *          http 响应 code 非 200, 或发生其他异常均抛出异常
     */
    public static String sendRequest(String url, String method, Map<String, String> headers, InputStream bodyStream, File saveToFile) throws Exception {
        assertUrlValid(url);

        HttpURLConnection conn = null;

        try {
            // 打开链接
            URL urlObj = new URL(url);
            conn = (HttpURLConnection) urlObj.openConnection();

            // 设置各种默认属性
            setDefaultProperties(conn);

            // 设置请求方法
            if (method != null && method.length() > 0) {
                conn.setRequestMethod(method);
            }

            // 添加请求头
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    conn.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            // 设置请求内容
            if (bodyStream != null) {
                conn.setDoOutput(true);
                copyStreamAndClose(bodyStream, conn.getOutputStream());
            }

            // 获取响应code
            int code = conn.getResponseCode();

            // 处理重定向
            if (code == HttpURLConnection.HTTP_MOVED_PERM || code == HttpURLConnection.HTTP_MOVED_TEMP) {
                String location = conn.getHeaderField("Location");
                if (location != null) {
                    closeStream(bodyStream);
                    // 重定向为 GET 请求
                    return sendRequest(location, "GET", headers, null, saveToFile);
                }
            }

            // 获取响应内容长度
            long contentLength = conn.getContentLengthLong();
            // 获取内容类型
            String contentType = conn.getContentType();

            // 获取响应内容输入流
            InputStream in = conn.getInputStream();

            // 没有响应成功, 均抛出异常
            if (code != HttpURLConnection.HTTP_OK) {
                throw new IOException("Http Error: " + code + "; Desc: " + handleResponseBodyToString(in, contentType));
            }

            // 如果文件参数不为null, 则把响应内容保存到文件
            if (saveToFile != null) {
                handleResponseBodyToFile(in, saveToFile);
                return saveToFile.getPath();
            }

            // 如果需要将响应内容解析为文本, 则限制最大长度
            if (contentLength > TEXT_REQUEST_MAX_LENGTH) {
                throw new IOException("Response content length too large: " + contentLength);
            }
            return handleResponseBodyToString(in, contentType);

        } finally {
            closeConnection(conn);
        }
    }

    private static void assertUrlValid(String url) throws IllegalAccessException {
        boolean isValid = false;
        if (url != null) {
            url = url.toLowerCase();
            if (url.startsWith("http://") || url.startsWith("https://")) {
                isValid = true;
            }
        }
        if (!isValid) {
            throw new IllegalAccessException("Only support http or https url: " + url);
        }
    }

    private static void setDefaultProperties(HttpURLConnection conn) {
        RW_LOCK.readLock().lock();
        try {
            // 设置连接超时时间
            conn.setConnectTimeout(CONNECT_TIME_OUT);

            // 设置读取超时时间
            conn.setReadTimeout(READ_TIME_OUT);

            // 添加默认的请求头
            if (DEFAULT_REQUEST_HEADERS.size() > 0) {
                for (Map.Entry<String, String> entry : DEFAULT_REQUEST_HEADERS.entrySet()) {
                    conn.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
        } finally {
            RW_LOCK.readLock().unlock();
        }
    }

    private static void handleResponseBodyToFile(InputStream in, File saveToFile) throws Exception  {
        OutputStream out = null;
        try {
            out = new FileOutputStream(saveToFile);
            copyStreamAndClose(in, out);
        } finally {
            closeStream(out);
        }
    }

    private static String handleResponseBodyToString(InputStream in, String contentType) throws Exception {
        ByteArrayOutputStream bytesOut = null;

        try {
            bytesOut = new ByteArrayOutputStream();

            // 读取响应内容
            copyStreamAndClose(in, bytesOut);

            // 响应内容的字节序列
            byte[] contentBytes = bytesOut.toByteArray();

            // 解析文本内容编码格式
            String charset = parseCharset(contentType);
            if (charset == null) {
                charset = parseCharsetFromHtml(contentBytes);
                if (charset == null) {
                    charset = "utf-8";
                }
            }

            // 解码响应内容
            String content = null;
            try {
                content = new String(contentBytes, charset);
            } catch (UnsupportedEncodingException e) {
                content = new String(contentBytes);
            }

            return content;

        } finally {
            closeStream(bytesOut);
        }
    }

    private static void copyStreamAndClose(InputStream in, OutputStream out) {
        try {
            byte[] buf = new byte[1024];
            int len = -1;
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStream(in);
            closeStream(out);
        }
    }

    private static String parseCharsetFromHtml(byte[] htmlBytes) {
        if (htmlBytes == null || htmlBytes.length == 0) {
            return null;
        }
        String html = null;
        try {
            // 先使用单字节编码的 ISO-8859-1 去尝试解码
            html = new String(htmlBytes, "ISO-8859-1");
            return parseCharsetFromHtml(html);
        } catch (UnsupportedEncodingException e) {
            html = new String(htmlBytes);
        }
        return parseCharsetFromHtml(html);
    }

    private static String parseCharsetFromHtml(String html) {
        if (html == null || html.length() == 0) {
            return null;
        }
        html = html.toLowerCase();
        Pattern p = Pattern.compile("<meta [^>]+>");
        Matcher m = p.matcher(html);
        String meta = null;
        String charset = null;
        while (m.find()) {
            meta = m.group();
            charset = parseCharset(meta);
            if (charset != null) {
                break;
            }
        }
        return charset;
    }

    private static String parseCharset(String content) {
        // text/html; charset=iso-8859-1
        // <meta charset="utf-8">
        // <meta charset='utf-8'>
        // <meta http-equiv="Content-Type" content="text/html; charset=gbk" />
        // <meta http-equiv="Content-Type" content='text/html; charset=gbk' />
        // <meta http-equiv=content-type content=text/html;charset=utf-8>
        if (content == null) {
            return null;
        }
        content = content.trim().toLowerCase();
        Pattern p = Pattern.compile("(?<=((charset=)|(charset=')|(charset=\")))[^'\"/> ]+(?=($|'|\"|/|>| ))");
        Matcher m = p.matcher(content);
        String charset = null;
        while (m.find()) {
            charset = m.group();
            if (charset != null) {
                break;
            }
        }
        return charset;
    }

    private static void closeConnection(HttpURLConnection conn) {
        if (conn != null) {
            try {
                conn.disconnect();
            } catch (Exception e) {
                // nothing
            }
        }
    }

    private static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (Exception e) {
                // nothing
            }
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println(SimpleHttpUtils.get("https://www.baidu.com/"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}