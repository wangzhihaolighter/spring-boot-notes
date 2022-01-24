package com.example.kaptcha.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;

public class CaptchaUtil {

  private static final String CAPTCHA_TYPE = "data:image/png;base64,";

  /**
   * bufferedImage 转为 base64编码
   *
   * @param bufferedImage /
   * @return /
   */
  public static String bufferedImageToBase64(BufferedImage bufferedImage) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    try {
      ImageIO.write(bufferedImage, "png", outputStream);
    } catch (IOException e) {
      e.printStackTrace();
    }
    byte[] bytes = outputStream.toByteArray();
    Base64.Encoder encoder = Base64.getEncoder();
    String pngBase64 = encoder.encodeToString(bytes).trim();
    pngBase64 = pngBase64.replace("\n", "").replace("\r", "");
    return CAPTCHA_TYPE + pngBase64;
  }

  /**
   * base64 编码转换为 BufferedImage
   *
   * @param base64 /
   * @return /
   */
  public static BufferedImage base64ToBufferedImage(String base64) {
    Base64.Decoder decoder = Base64.getDecoder();
    try {
      byte[] bytes1 = decoder.decode(base64);
      ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
      return ImageIO.read(bais);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
