package com.example.springframework.boot.http.encryption.encryption.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

public class AESUtil {

    /**
     * 偏移量
     * AES 为16bytes. DES 为8bytes
     */
    public static final String OFFSET = "1234567890123456";

    /**
     * 编码方式
     */
    public static final String CODE_TYPE = "UTF-8";

    /**
     * 加密/解密算法/工作模式/填充方式
     */
    public static final String AES_TYPE = AesType.PKCS5;

    interface AesType {
        String PKCS5 = "AES/ECB/PKCS5Padding";
        String PKCS7 = "AES/ECB/PKCS7Padding";
        /**
         * 此类型 加密内容,密钥必须为16字节的倍数位,否则抛异常,需要字节补全再进行加密
         */
        String NO = "AES/ECB/NoPadding";
        /**
         * java 不支持ZeroPadding
         */
        String ZERO = "AES/CBC/ZeroPadding";
    }

    /**
     * 私钥
     * AES固定格式为128/192/256 bits.即：16/24/32bytes。DES固定格式为128bits，即8bytes。
     */
    private static final String AES_KEY = "1234567890123456";

    /**
     * 字符补全
     */
    private static final String[] CONSULT = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G"};

    /**
     * 默认 AES加密
     *
     * @param cleartext 加密前文本
     * @return 加密后文本
     */
    public static String encrypt(String cleartext) {
        return encrypt(cleartext, AES_TYPE, AES_KEY, OFFSET, CODE_TYPE);
    }

    /**
     * AES加密
     *
     * @param cleartext 加密前文本
     * @param aesType   填充类型
     * @param aesKey    私钥
     * @param offset    偏移量
     * @param codeType  编码方式
     * @return 加密后文本
     */
    public static String encrypt(String cleartext, String aesType, String aesKey, String offset, String codeType) {
        try {
            IvParameterSpec zeroIv = new IvParameterSpec(offset.getBytes());
            //两个参数，第一个为私钥字节数组， 第二个为加密方式 AES或者DES    
            SecretKeySpec key = new SecretKeySpec(aesKey.getBytes(), "AES");
            //实例化加密类，参数为加密方式，要写全
            //PKCS5Padding比PKCS7Padding效率高，PKCS7Padding可支持IOS加解密
            Cipher cipher = Cipher.getInstance(aesType);
            //初始化，此方法可以采用三种方式，按加密算法要求来添加。（1）无第三个参数（2）第三个参数为SecureRandom random = new SecureRandom();中random对象，随机数。(AES不可采用这种方法)（3）采用此代码中的IVParameterSpec    
            //加密时使用:ENCRYPT_MODE;  解密时使用:DECRYPT_MODE;
            //CBC类型的可以在第三个参数传递偏移量zeroIv,ECB没有偏移量
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //加密操作,返回加密后的字节数组，然后需要编码。主要编解码方式有Base64, HEX, UUE,7bit等等。此处看服务器需要什么编码方式   
            byte[] encryptedData = cipher.doFinal(cleartext.getBytes(codeType));

            return new BASE64Encoder().encode(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 默认解密
     *
     * @param encrypted 解密前文本
     * @return 解密后文本
     */
    public static String decrypt(String encrypted) {
        return decrypt(encrypted, AES_TYPE, AES_KEY, OFFSET, CODE_TYPE);
    }

    /**
     * 解密
     *
     * @param encrypted 解密前文本
     * @param aesType   填充类型
     * @param aesKey    私钥
     * @param offset    偏移量
     * @param codeType  编码方式
     * @return 解密后文本
     */
    public static String decrypt(String encrypted, String aesType, String aesKey, String offset, String codeType) {
        try {
            byte[] byteMi = new BASE64Decoder().decodeBuffer(encrypted);
            IvParameterSpec zeroIv = new IvParameterSpec(offset.getBytes());
            SecretKeySpec key = new SecretKeySpec(
                    aesKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance(aesType);
            //与加密时不同MODE:Cipher.DECRYPT_MODE
            //CBC类型的可以在第三个参数传递偏移量zeroIv,ECB没有偏移量
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedData = cipher.doFinal(byteMi);
            return new String(decryptedData, codeType);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * NoPadding
     * 补全字符
     */
    public static String completionCodeFor16Bytes(String str) throws UnsupportedEncodingException {
        int num = str.getBytes(CODE_TYPE).length;
        int index = num % 16;
        //进行加密内容补全操作, 加密内容应该为 16字节的倍数, 当不足16*n字节是进行补全, 差一位时 补全16+1位  
        //补全字符 以 $ 开始,$后一位代表$后补全字符位数,之后全部以0进行补全;  
        if (index != 0) {
            StringBuilder sbBuffer = new StringBuilder(str);
            if (16 - index == 1) {
                sbBuffer.append("$").append(CONSULT[16 - 1]).append(addStr(16 - 1 - 1));
            } else {
                sbBuffer.append("$").append(CONSULT[16 - index - 1]).append(addStr(16 - index - 1 - 1));
            }
            str = sbBuffer.toString();
        }
        return str;
    }

    /**
     * 追加字符
     */
    public static String addStr(int num) {
        StringBuilder sbBuffer = new StringBuilder("");
        for (int i = 0; i < num; i++) {
            sbBuffer.append("0");
        }
        return sbBuffer.toString();
    }


    /**
     * 还原字符(进行字符判断)
     */
    public static String resumeCodeOf16Bytes(String str) {
        int indexOf = str.lastIndexOf("$");
        if (indexOf == -1) {
            return str;
        }
        String trim = str.substring(indexOf + 1, indexOf + 2).trim();
        int num = 0;
        for (int i = 0; i < CONSULT.length; i++) {
            if (trim.equals(CONSULT[i])) {
                num = i;
            }
        }
        if (num == 0) {
            return str;
        }
        return str.substring(0, indexOf).trim();
    }

    /* test */

    public static void main(String[] args) throws Exception {
        //测试
        String content = "它是一只小跳蛙 越过蓝色大西洋,跳到遥远的东方 跳到我们身旁,春夏秋冬 我们是最好的伙伴,亲吻它就会变得不一样";
        test(content);
    }

    public static void test(String content) throws UnsupportedEncodingException {
        System.out.println("加密内容：" + content);
        //字节数
        int num = content.getBytes(CODE_TYPE).length;
        System.out.println("加密内容字节数: " + num);
        System.out.println("加密内容是否16倍数: " + (num % 16 == 0));

        System.out.println();

        // 加密
        String encryptResult = encrypt(content);
        content = encryptResult;
        System.out.println("加密后：" + content);

        System.out.println();

        // 解密
        content = decrypt(encryptResult);
        System.out.println("解密完成后：" + content);
    }

}   