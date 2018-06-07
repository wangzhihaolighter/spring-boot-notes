package com.example.springframework.boot.http.encryption.encryption.constant;

/**
 * 系统常量类
 */
public class SystemConstants {

    /**
     * 系统定义的http请求头参数
     */
    public interface HttpHeaders {
        /**
         * 加密方法
         */
        String ENCODE_METHOD = "encode_method";
        /**
         *
         */
        String HEADER_CONTENT_TYPE = "header_content_type";
        /**
         * 响应格式
         */
        String APPLICATION_BASE64_JSON_UTF8 = "application_base64_json_utf8";

        /**
         * 加解密方式
         */
        interface Encryption {
            String AES = "aes";
        }

    }

}
