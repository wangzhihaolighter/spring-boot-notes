package com.example.springframework.boot.utils.web.http;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HTTP工具类，使用Apache httpclient实现
 */
public class ApacheHttpClientUtils {

    /**
     * get
     *
     * @param host    主机名
     * @param path    路径
     * @param headers 请求头参数
     * @param querys  url后拼接参数
     * @return 响应结果
     * @throws Exception any exception
     */
    public static HttpResponse doGet(String host, String path,
                                     Map<String, String> headers,
                                     Map<String, String> querys)
            throws Exception {
        HttpGet request = new HttpGet(buildUrl(host, path, querys));
        wrapRequestHeaders(request, headers);

        return sendRequest(request);
    }

    /**
     * post form
     *
     * @param host    主机名
     * @param path    路径
     * @param headers 请求头参数
     * @param querys  url后拼接参数
     * @param bodys   请求体参数
     * @return 响应结果
     * @throws Exception any exception
     */
    public static HttpResponse doPost(String host, String path,
                                      Map<String, String> headers,
                                      Map<String, String> querys,
                                      Map<String, String> bodys)
            throws Exception {
        HttpPost request = new HttpPost(buildUrl(host, path, querys));
        wrapRequestHeaders(request, headers);
        wrapRequestEntity(request, bodys);

        return sendRequest(request);
    }

    /**
     * Post String
     *
     * @param host    主机名
     * @param path    路径
     * @param headers 请求头参数
     * @param querys  url后拼接参数
     * @param body    请求体参数
     * @return 响应结果
     * @throws Exception any exception
     */
    public static HttpResponse doPost(String host, String path,
                                      Map<String, String> headers,
                                      Map<String, String> querys,
                                      String body)
            throws Exception {
        HttpPost request = new HttpPost(buildUrl(host, path, querys));
        wrapRequestHeaders(request, headers);
        wrapRequestEntity(request, body);

        return sendRequest(request);
    }

    /**
     * Post stream
     *
     * @param host    主机名
     * @param path    路径
     * @param headers 请求头参数
     * @param querys  url后拼接参数
     * @param body    请求体参数
     * @return 响应结果
     * @throws Exception any exception
     */
    public static HttpResponse doPost(String host, String path,
                                      Map<String, String> headers,
                                      Map<String, String> querys,
                                      byte[] body)
            throws Exception {
        HttpPost request = new HttpPost(buildUrl(host, path, querys));
        wrapRequestHeaders(request, headers);
        wrapRequestEntity(request, body);

        return sendRequest(request);
    }

    /**
     * Put String
     *
     * @param host    主机名
     * @param path    路径
     * @param headers 请求头参数
     * @param querys  url后拼接参数
     * @param body    请求体参数
     * @return 响应结果
     * @throws Exception any exception
     */
    public static HttpResponse doPut(String host, String path,
                                     Map<String, String> headers,
                                     Map<String, String> querys,
                                     String body)
            throws Exception {
        HttpPut request = new HttpPut(buildUrl(host, path, querys));
        wrapRequestHeaders(request, headers);
        wrapRequestEntity(request, body);

        return sendRequest(request);
    }

    /**
     * Put stream
     *
     * @param host    主机名
     * @param path    路径
     * @param headers 请求头参数
     * @param querys  url后拼接参数
     * @param body    请求体参数
     * @return 响应结果
     * @throws Exception any exception
     */
    public static HttpResponse doPut(String host, String path,
                                     Map<String, String> headers,
                                     Map<String, String> querys,
                                     byte[] body)
            throws Exception {
        HttpPut request = new HttpPut(buildUrl(host, path, querys));
        wrapRequestHeaders(request, headers);
        wrapRequestEntity(request, body);

        return sendRequest(request);
    }

    /**
     * Delete
     *
     * @param host    主机名
     * @param path    路径
     * @param headers 请求头参数
     * @param querys  url后拼接参数
     * @return 响应结果
     * @throws Exception any exception
     */
    public static HttpResponse doDelete(String host, String path,
                                        Map<String, String> headers,
                                        Map<String, String> querys)
            throws Exception {
        HttpClient httpClient = buildClient();
        HttpDelete request = new HttpDelete(buildUrl(host, path, querys));
        wrapRequestHeaders(request, headers);

        return httpClient.execute(request);
    }

    /**
     * 执行一个通用的 http/https 请求
     *
     * @param request 请求
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws IOException
     */
    public static HttpResponse sendRequest(HttpUriRequest request) throws NoSuchAlgorithmException, KeyManagementException, IOException {
        HttpClient httpClient = buildClient();
        return httpClient.execute(request);
    }

    private static String buildUrl(String host, String path, Map<String, String> querys) throws UnsupportedEncodingException {
        StringBuilder sbUrl = new StringBuilder();
        sbUrl.append(host);
        if (!StringUtils.isBlank(path)) {
            sbUrl.append(path);
        }
        if (null != querys) {
            StringBuilder sbQuery = new StringBuilder();
            for (Map.Entry<String, String> query : querys.entrySet()) {
                if (0 < sbQuery.length()) {
                    sbQuery.append("&");
                }
                if (StringUtils.isBlank(query.getKey()) && !StringUtils.isBlank(query.getValue())) {
                    sbQuery.append(query.getValue());
                }
                if (!StringUtils.isBlank(query.getKey())) {
                    sbQuery.append(query.getKey());
                    if (!StringUtils.isBlank(query.getValue())) {
                        sbQuery.append("=");
                        sbQuery.append(URLEncoder.encode(query.getValue(), "utf-8"));
                    }
                }
            }
            if (0 < sbQuery.length()) {
                sbUrl.append("?").append(sbQuery);
            }
        }

        return sbUrl.toString();
    }

    /**
     * build HttpClient
     *
     * @return HttpClient
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     */
    private static HttpClient buildClient() throws KeyManagementException, NoSuchAlgorithmException {
        SSLContext ctx = createIgnoreVerifySSL();
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(ctx))
                .build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        return HttpClients.custom().setConnectionManager(connManager).build();
    }

    /**
     * 绕过验证
     *
     * @return SSLContext
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[]{trustManager}, null);
        return sc;
    }

    private static void wrapRequestHeaders(HttpRequestBase request, Map<String, String> headers) {
        if (MapUtils.isNotEmpty(headers)) {
            for (Map.Entry<String, String> e : headers.entrySet()) {
                request.addHeader(e.getKey(), e.getValue());
            }
        }
    }

    private static void wrapRequestEntity(HttpEntityEnclosingRequestBase request, String body) {
        if (StringUtils.isNotBlank(body)) {
            request.setEntity(new StringEntity(body, "utf-8"));
        }
    }

    private static void wrapRequestEntity(HttpEntityEnclosingRequestBase request, byte[] body) {
        if (body != null) {
            request.setEntity(new ByteArrayEntity(body));
        }
    }

    private static void wrapRequestEntity(HttpEntityEnclosingRequestBase request, Map<String, String> bodys) throws UnsupportedEncodingException {
        if (MapUtils.isNotEmpty(bodys)) {
            List<NameValuePair> nameValuePairList = new ArrayList<>();
            for (String key : bodys.keySet()) {
                nameValuePairList.add(new BasicNameValuePair(key, bodys.get(key)));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairList, "utf-8");
            formEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
            request.setEntity(formEntity);
        }
    }

    public static void main(String[] args) throws Exception {
        HttpResponse httpResponse = doGet("https://www.baidu.com/", null, null, null);
        System.out.println(EntityUtils.toString(httpResponse.getEntity(), "utf-8"));
    }

}
