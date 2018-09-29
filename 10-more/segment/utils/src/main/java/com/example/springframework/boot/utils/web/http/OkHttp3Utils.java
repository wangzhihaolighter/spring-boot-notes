package com.example.springframework.boot.utils.web.http;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * HTTP工具类，使用OkHttp3实现
 */
public class OkHttp3Utils {

    /**
     * 懒汉 安全 加同步
     * 私有的静态成员变量 只声明不创建
     * 私有的构造方法
     * 提供返回实例的静态方法
     */
    private static OkHttpClient okHttpClient = getInstance();

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType STREAM = MediaType.parse("application/octet-stream");

    private OkHttp3Utils() {
    }

    private static OkHttpClient getInstance() {
        if (okHttpClient == null) {
            //加同步安全
            synchronized (OkHttp3Utils.class) {
                if (okHttpClient == null) {
                    //缺省 - 带有连接池
                    return new OkHttpClient.Builder()
                            .retryOnConnectionFailure(true)
                            .connectTimeout(60, TimeUnit.SECONDS)
                            .writeTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(10, TimeUnit.SECONDS)
                            .build();
                }
            }

        }
        return okHttpClient;
    }

    public static String doGet(String url) throws IOException {
        return doGet(url, null, null);
    }

    public static String doGet(String url, Map<String, String> param, Map<String, String> headers) throws IOException {
        url = buildUrl(url, param);
        Request.Builder builder = new Request.Builder().get().url(url);
        wrapRequestHeaders(builder, headers);

        return sendRequest(builder.build());
    }

    public static String doPost(String url, Map<String, String> headers, Map<String, String> param) throws IOException {
        RequestBody requestBody = wrapRequestBody(param);
        return executePost(url, headers, requestBody);
    }

    public static String doPost(String url, Map<String, String> headers, String jsonStr) throws IOException {
        RequestBody requestBody = wrapRequestBody(jsonStr);
        return executePost(url, headers, requestBody);
    }

    public static String doPost(String url, Map<String, String> headers, byte[] content) throws IOException {
        RequestBody requestBody = wrapRequestBody(content);
        return executePost(url, headers, requestBody);
    }

    public static String doPut(String url, Map<String, String> headers, Map<String, String> param, String content) throws IOException {
        RequestBody requestBody = wrapRequestBody(content);
        return executePut(url, headers, param, requestBody);
    }

    public static String doPut(String url, Map<String, String> headers, Map<String, String> param, byte[] content) throws IOException {
        RequestBody requestBody = wrapRequestBody(content);
        return executePut(url, headers, param, requestBody);
    }

    public static String doDelete(String url, Map<String, String> headers) throws IOException {
        Request.Builder builder = new Request.Builder().delete().url(url);
        Request request = getRequest(builder, headers);
        return sendRequest(request);
    }

    /*---------- 异步调用 ----------*/

    public static void doGet(String url, Callback callback) {
        doGet(url, null, null, callback);
    }

    public static void doGet(String url, Map<String, String> param, Map<String, String> headers, Callback callback) {
        url = buildUrl(url, param);
        Request.Builder builder = new Request.Builder().get().url(url);
        wrapRequestHeaders(builder, headers);

        okHttpClient.newCall(builder.build()).enqueue(callback);
    }

    public static void doPost(String url, Map<String, String> headers, Map<String, String> param, Callback callback) {
        RequestBody requestBody = wrapRequestBody(param);
        asyncExecutePost(url, headers, requestBody, callback);
    }

    public static void doPost(String url, Map<String, String> headers, String jsonStr, Callback callback) {
        RequestBody requestBody = wrapRequestBody(jsonStr);
        asyncExecutePost(url, headers, requestBody, callback);
    }

    public static void doPost(String url, Map<String, String> headers, byte[] content, Callback callback) {
        RequestBody requestBody = wrapRequestBody(content);
        asyncExecutePost(url, headers, requestBody, callback);
    }

    public static void doPut(String url, Map<String, String> headers, Map<String, String> param, String content, Callback callback) {
        RequestBody requestBody = wrapRequestBody(content);
        asyncExecutePut(url, headers, param, requestBody, callback);
    }

    public static void doPut(String url, Map<String, String> headers, Map<String, String> param, byte[] content, Callback callback) {
        RequestBody requestBody = wrapRequestBody(content);
        asyncExecutePut(url, headers, param, requestBody, callback);
    }

    public static void doDelete(String url, Map<String, String> headers, Callback callback) {
        Request.Builder builder = new Request.Builder().delete().url(url);
        Request request = getRequest(builder, headers);
        okHttpClient.newCall(request).enqueue(callback);
    }

    /*---------- 其他 ----------*/

    private static String executePost(String url, Map<String, String> headers, RequestBody requestBody) throws IOException {
        Request.Builder builder = new Request.Builder().post(requestBody).url(url);
        Request request = getRequest(builder, headers);
        return sendRequest(request);
    }

    private static void asyncExecutePost(String url, Map<String, String> headers, RequestBody requestBody, Callback callback) {
        Request.Builder builder = new Request.Builder().post(requestBody).url(url);
        Request request = getRequest(builder, headers);
        okHttpClient.newCall(request).enqueue(callback);
    }

    private static String executePut(String url, Map<String, String> headers, Map<String, String> param, RequestBody requestBody) throws IOException {
        url = buildUrl(url, param);
        Request.Builder builder = new Request.Builder().post(requestBody).url(url);
        Request request = getRequest(builder, headers);
        return sendRequest(request);
    }

    private static void asyncExecutePut(String url, Map<String, String> headers, Map<String, String> param, RequestBody requestBody, Callback callback) {
        url = buildUrl(url, param);
        Request.Builder builder = new Request.Builder().post(requestBody).url(url);
        Request request = getRequest(builder, headers);
        okHttpClient.newCall(request).enqueue(callback);
    }

    private static String sendRequest(Request request) throws IOException {
        String result = null;
        Response response = okHttpClient.newCall(request).execute();
        if (Objects.nonNull(response)) {
            result = response.body() != null ? response.body().string() : null;
            response.close();
        }
        return result;
    }

    private static Request getRequest(Request.Builder builder, Map<String, String> headers) {
        wrapRequestHeaders(builder, headers);
        return builder.build();
    }

    private static String buildUrl(String url, Map<String, String> param) {
        if (param == null || param.size() == 0) {
            return url;
        }
        StringBuilder sbUrl = new StringBuilder();
        sbUrl.append(url).append("?");
        for (Map.Entry<String, String> entry : param.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
                continue;
            }
            sbUrl.append(key).append("=").append(value).append("&");
        }
        //删除最后的一个"&";无拼接参数时，删除"?"
        sbUrl.deleteCharAt(sbUrl.length() - 1);
        return sbUrl.toString();
    }

    private static void wrapRequestHeaders(Request.Builder builder, Map<String, String> headers) {
        if (headers == null || !headers.isEmpty()) {
            return;
        }
        Headers requestHeaders = Headers.of(headers);
        builder.headers(requestHeaders);
    }

    private static RequestBody wrapRequestBody(Map<String, String> map) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }

    private static RequestBody wrapRequestBody(String jsonParam) {
        return RequestBody.create(JSON, jsonParam);
    }

    private static RequestBody wrapRequestBody(byte[] content) {
        return RequestBody.create(STREAM, content);
    }

    public static void main(String[] args) throws IOException {
        //测试
        //异步抵用
        Callback callback = new Callback() {
            @Override
            @ParametersAreNonnullByDefault
            public void onFailure(Call call, IOException e) {
                System.out.println("出错了，错误信息:" + e.getMessage());
            }

            @Override
            @ParametersAreNonnullByDefault
            public void onResponse(Call call, Response response) throws IOException {
                String result = Objects.requireNonNull(response.body()).string();
                System.out.println("响应结果：\r\n" + result);
            }
        };
        doGet("https://www.baidu.com/",callback);
        //同步调用
        String result = doGet("https://www.baidu.com/");
        System.out.println(result);
    }

}
