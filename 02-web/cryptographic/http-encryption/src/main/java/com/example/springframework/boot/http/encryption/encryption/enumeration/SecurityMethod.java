package com.example.springframework.boot.http.encryption.encryption.enumeration;

import java.util.HashMap;
import java.util.Map;

/**
 * 加解密、签名算法枚举
 */
public enum SecurityMethod {
    //无加密
    NULL,

    AES,
    RSA,
    DES,
    DES3,

    SHA1,
    MD5;

    private static Map<String, SecurityMethod> securityMethodMap = new HashMap<>(7);

    static {
        securityMethodMap.put(null, NULL);
        securityMethodMap.put("AES", AES);
        securityMethodMap.put("RSA", RSA);
        securityMethodMap.put("DES", DES);
        securityMethodMap.put("DES3", DES3);
        securityMethodMap.put("SHA1", SHA1);
        securityMethodMap.put("MD5", MD5);
    }


    public static SecurityMethod getByCode(String encodeMethod) {
        SecurityMethod securityMethod = securityMethodMap.get(encodeMethod);
        if (null == securityMethod) {
            securityMethod = NULL;
        }
        return securityMethod;
    }

}