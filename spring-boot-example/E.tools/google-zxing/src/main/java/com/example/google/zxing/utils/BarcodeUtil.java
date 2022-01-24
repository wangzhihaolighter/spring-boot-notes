package com.example.google.zxing.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/** 多维码生成工具 */
public class BarcodeUtil {

  /*
  com.google.zxing.EncodeHintType：编码提示类型,枚举类型
      EncodeHintType.CHARACTER_SET：设置字符编码类型
      EncodeHintType.ERROR_CORRECTION：设置误差校正
          ErrorCorrectionLevel：误差校正等级，L = ~7% correction、M = ~15% correction、Q = ~25% correction、H = ~30% correction，不设置时，默认为 L 等级，等级不一样，生成的图案不同，但扫描的结果是一样的
      EncodeHintType.MARGIN：设置二维码边距，单位像素，值越小，二维码距离四周越近
  com.google.zxing.MultiFormatWriter:多格式写入，这是一个工厂类，里面重载了两个 encode 方法，用于写入条形码或二维码
      encode(String contents,BarcodeFormat format,int width, int height,Map<EncodeHintType,?> hints)
          contents:条形码/二维码内容
          format：编码类型，如 条形码，二维码 等
          width：码的宽度
          height：码的高度
          hints：码内容的编码类型
  com.google.zxing.BarcodeFormat：枚举该程序包已知的条形码格式，即创建何种码，如 1 维的条形码，2 维的二维码 等
  com.google.zxing.common.BitMatrix：位(比特)矩阵或叫2D矩阵，也就是需要的二维码
      BitMatrix 的 get(int x, int y) 获取比特矩阵内容，指定位置有值，则返回true，将其设置为前景色，否则设置为背景色
  com.google.zxing.client.j2se.BufferedImageLuminanceSource：缓冲图像亮度源
  com.google.zxing.common.HybridBinarizer：用于读取二维码图像数据
  com.google.zxing.BinaryBitmap：二进制位图

  java.awt.image.BufferedImage：具有图像数据的可访问缓冲图像，实现了 RenderedImage 接口
      BufferedImage 的 setRGB(int x, int y, int rgb) 方法设置图像像素
          x：像素位置的横坐标，即列
          y：像素位置的纵坐标，即行
          rgb：像素的值，采用 16 进制,如 0xFFFFFF 白色
  javax.imageio.ImageIO java 扩展的图像IO
      write(RenderedImage im,String formatName,File output)
          im：待写入的图像
          formatName：图像写入的格式
          output：写入的图像文件，文件不存在时会自动创建
   */

  public static final int CODE_WIDTH = 400;

  public static final int CODE_HEIGHT = 400;

  public static final int FRONT_COLOR = 0x000000;

  public static final int BACKGROUND_COLOR = 0xFFFFFF;

  public static final Map<EncodeHintType, Object> HINTS = new HashMap<>(4);

  static {
    HINTS.put(EncodeHintType.CHARACTER_SET, "UTF-8");
    HINTS.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
    HINTS.put(EncodeHintType.MARGIN, 1);
  }

  // ========== 生成 ==========

  /**
   * 创建条形码
   *
   * @param codeContent 条形码内容
   * @param format 条形码类型
   * @param codeWidth 宽度，单位像素
   * @param codeHeight 高度，单位像素
   * @param frontColor 前景色，0x000000 表示黑色
   * @param backgroundColor 背景色，0xFFFFFF 表示白色
   * @param hints 编码提示类型
   * @return /
   * @throws WriterException /
   */
  public static BufferedImage createBarcode(
      String codeContent,
      BarcodeFormat format,
      int codeWidth,
      int codeHeight,
      int frontColor,
      int backgroundColor,
      Map<EncodeHintType, ?> hints)
      throws WriterException {
    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
    BitMatrix bitMatrix =
        multiFormatWriter.encode(codeContent, format, codeWidth, codeHeight, hints);
    BufferedImage bufferedImage =
        new BufferedImage(codeWidth, codeHeight, BufferedImage.TYPE_INT_BGR);
    for (int x = 0; x < codeWidth; x++) {
      for (int y = 0; y < codeHeight; y++) {
        bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? frontColor : backgroundColor);
      }
    }
    return bufferedImage;
  }

  /**
   * 生成二维码
   *
   * @param codeContent 二维码内容
   * @return /
   * @throws WriterException /
   */
  public static BufferedImage createQRBarcode(String codeContent)
      throws WriterException, IOException {
    return createQRCodeWithLog(
        codeContent, CODE_WIDTH, CODE_HEIGHT, FRONT_COLOR, BACKGROUND_COLOR, HINTS, null);
  }

  /**
   * 生成二维码
   *
   * @param codeContent 二维码内容
   * @param codeWidth 宽度，单位像素
   * @param codeHeight 高度，单位像素
   * @param frontColor 前景色，0x000000 表示黑色
   * @param backgroundColor 背景色，0xFFFFFF 表示白色
   * @param hints 编码提示类型
   * @return /
   * @throws WriterException /
   */
  public static BufferedImage createQRBarcode(
      String codeContent,
      int codeWidth,
      int codeHeight,
      int frontColor,
      int backgroundColor,
      Map<EncodeHintType, ?> hints)
      throws WriterException, IOException {
    return createQRCodeWithLog(
        codeContent, codeWidth, codeHeight, frontColor, backgroundColor, hints, null);
  }

  /**
   * 生成二维码(带logo)
   *
   * @param codeContent 二维码内容
   * @param logoIn logo图片输入流
   * @return /
   * @throws WriterException /
   * @throws IOException /
   */
  public static BufferedImage createQRCodeWithLog(String codeContent, InputStream logoIn)
      throws WriterException, IOException {
    // 生成二维码
    BufferedImage image =
        createBarcode(
            codeContent,
            BarcodeFormat.QR_CODE,
            CODE_WIDTH,
            CODE_HEIGHT,
            FRONT_COLOR,
            BACKGROUND_COLOR,
            HINTS);

    // logo
    return logoMatrix(image, logoIn);
  }

  /**
   * 生成二维码(带logo)
   *
   * @param codeContent 二维码内容
   * @param codeWidth 宽度，单位像素
   * @param codeHeight 高度，单位像素
   * @param frontColor 前景色，0x000000 表示黑色
   * @param backgroundColor 背景色，0xFFFFFF 表示白色
   * @param hints 编码提示类型
   * @param logoIn logo图片输入流
   * @return /
   * @throws WriterException /
   * @throws IOException /
   */
  public static BufferedImage createQRCodeWithLog(
      String codeContent,
      int codeWidth,
      int codeHeight,
      int frontColor,
      int backgroundColor,
      Map<EncodeHintType, ?> hints,
      InputStream logoIn)
      throws WriterException, IOException {
    // 生成二维码
    BufferedImage image =
        createBarcode(
            codeContent,
            BarcodeFormat.QR_CODE,
            codeWidth,
            codeHeight,
            frontColor,
            backgroundColor,
            hints);

    // logo
    return logoMatrix(image, logoIn);
  }

  /**
   * 生成 Code128 条形码
   *
   * @param codeContent 条形码内容
   * @return /
   * @throws WriterException /
   */
  public static BufferedImage createCode128Barcode(String codeContent) throws WriterException {
    return createBarcode(
        codeContent,
        BarcodeFormat.CODE_128,
        CODE_WIDTH,
        CODE_HEIGHT,
        FRONT_COLOR,
        BACKGROUND_COLOR,
        HINTS);
  }

  /**
   * 生成 Code128 条形码
   *
   * @param codeContent 条形码内容
   * @param codeWidth 宽度，单位像素
   * @param codeHeight 高度，单位像素
   * @param frontColor 前景色，0x000000 表示黑色
   * @param backgroundColor 背景色，0xFFFFFF 表示白色
   * @param hints 编码提示类型
   * @return /
   * @throws WriterException /
   */
  public static BufferedImage createCode128Barcode(
      String codeContent,
      int codeWidth,
      int codeHeight,
      int frontColor,
      int backgroundColor,
      Map<EncodeHintType, ?> hints)
      throws WriterException {
    return createBarcode(
        codeContent,
        BarcodeFormat.CODE_128,
        codeWidth,
        codeHeight,
        frontColor,
        backgroundColor,
        hints);
  }

  /**
   * 生成 Codabar 条形码
   *
   * @param codeContent 条形码内容
   * @return /
   * @throws WriterException /
   */
  public static BufferedImage createCodabarBarcode(String codeContent) throws WriterException {
    return createBarcode(
        codeContent,
        BarcodeFormat.CODABAR,
        CODE_WIDTH,
        CODE_HEIGHT,
        FRONT_COLOR,
        BACKGROUND_COLOR,
        HINTS);
  }

  /**
   * 生成 Codabar 条形码
   *
   * @param codeContent 条形码内容
   * @param codeWidth 宽度，单位像素
   * @param codeHeight 高度，单位像素
   * @param frontColor 前景色，0x000000 表示黑色
   * @param backgroundColor 背景色，0xFFFFFF 表示白色
   * @param hints 编码提示类型
   * @return /
   * @throws WriterException /
   */
  public static BufferedImage createCodabarBarcode(
      String codeContent,
      int codeWidth,
      int codeHeight,
      int frontColor,
      int backgroundColor,
      Map<EncodeHintType, ?> hints)
      throws WriterException {
    return createBarcode(
        codeContent,
        BarcodeFormat.CODABAR,
        codeWidth,
        codeHeight,
        frontColor,
        backgroundColor,
        hints);
  }

  // ========== 解析 ==========

  /**
   * 解析二维码/条形码
   *
   * @param filePath 文件路径
   * @return 解析值
   */
  public static String decodeQRCode(String filePath) {
    String value = null;
    try {
      File file = new File(filePath);
      if (!file.exists()) {
        return null;
      }

      BufferedImage bufferedImage = ImageIO.read(file);
      MultiFormatReader formatReader = new MultiFormatReader();
      LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
      Binarizer binarizer = new HybridBinarizer(source);
      BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
      Map<DecodeHintType, Object> map = new HashMap<>(2);
      map.put(DecodeHintType.CHARACTER_SET, "UTF-8");
      Result result = formatReader.decode(binaryBitmap, map);
      value = result.getText();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return value;
  }

  /** 二维码添加logo */
  public static BufferedImage logoMatrix(BufferedImage matrixImage, InputStream logoIn)
      throws IOException {
    if (logoIn == null) {
      return matrixImage;
    }

    // 读取二维码图片，并构建绘图对象
    Graphics2D g2 = matrixImage.createGraphics();

    int matrixWidth = matrixImage.getWidth();
    int matrixHeight = matrixImage.getHeight();

    // 读取Logo图片
    BufferedImage logo = ImageIO.read(logoIn);

    // 开始绘制图片
    g2.drawImage(
        logo, matrixWidth / 5 * 2, matrixHeight / 5 * 2, matrixWidth / 5, matrixHeight / 5, null);
    // 绘制
    BasicStroke stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    // 设置笔画对象
    g2.setStroke(stroke);
    // 指定弧度的圆角矩形
    RoundRectangle2D.Float round =
        new RoundRectangle2D.Float(
            matrixWidth / 5f * 2f,
            matrixHeight / 5f * 2f,
            matrixWidth / 5f,
            matrixHeight / 5f,
            BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_ROUND);
    g2.setColor(Color.white);
    // 绘制圆弧矩形
    g2.draw(round);

    // 设置logo 有一道灰色边框
    BasicStroke stroke2 = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    // 设置笔画对象
    g2.setStroke(stroke2);
    // 指定弧度的圆角矩形
    RoundRectangle2D.Float round2 =
        new RoundRectangle2D.Float(
            matrixWidth / 5f * 2f + 2f,
            matrixHeight / 5f * 2f + 2f,
            matrixWidth / 5f - 4f,
            matrixHeight / 5f - 4f,
            BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_ROUND);
    g2.setColor(Color.GRAY);
    // 绘制圆弧矩形
    g2.draw(round2);

    g2.dispose();
    matrixImage.flush();

    return matrixImage;
  }

  public static void main(String[] args) throws Exception {
    String codeContent = "https://www.baidu.com/";

    // codabar格式：第一位和最后一位可为A、B、C、D其中一个字母，其余位位数字
    String codabarContent = "A0123456789D";

    // 二维码
    BufferedImage qrBarcode = createQRBarcode(codeContent);
    // 带logo的二维码
    Resource resource = new ClassPathResource("java_logo.jpg");
    BufferedImage qrCodeWithLog = createQRCodeWithLog(codeContent, resource.getInputStream());

    // 条形码
    BufferedImage code128Barcode = createCode128Barcode(codeContent);
    BufferedImage codabarBarcode = createCodabarBarcode(codabarContent);

    String dir = System.getProperty("user.dir");
    File path = new File(dir + "/temp/zxing");
    if (!path.exists()) {
      path.mkdir();
    }
    System.out.println(path.getPath());
    File qrBarcodeFile = new File(path, "qrBarcode.jpg");
    ImageIO.write(qrBarcode, "jpg", qrBarcodeFile);
    System.out.println("生成二维码图片：：" + qrBarcodeFile.getPath());
    File qrCodeWithLogFile = new File(path, "qrCodeWithLog.jpg");
    ImageIO.write(qrCodeWithLog, "jpg", qrCodeWithLogFile);
    System.out.println("生成带logo的二维码：：" + qrCodeWithLogFile.getPath());
    File code128BarcodeFile = new File(path, "code128Barcode.jpg");
    ImageIO.write(code128Barcode, "jpg", code128BarcodeFile);
    System.out.println("生成code128条形码：：" + code128BarcodeFile.getPath());
    File codabarBarcodeFile = new File(path, "codabarBarcode.jpg");
    ImageIO.write(codabarBarcode, "jpg", codabarBarcodeFile);
    System.out.println("生成codabar条形码：：" + codabarBarcodeFile.getPath());

    // 解析
    String filePath = qrCodeWithLogFile.getPath();
    System.out.println(filePath);
    String content = decodeQRCode(filePath);
    System.out.println("带logo二维码解析出的内容：" + content);
  }
}
