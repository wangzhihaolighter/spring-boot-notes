# google-zxing开源多维码生成工具整合使用

## 资料

- zxing 官网：[zxing.github.io/zxing](https://zxing.github.io/zxing/index.html)

- zxing Github 仓库：[zxing / zxing](https://github.com/zxing/zxing)

- zxing 官方开发文档：[zxing / wiki / Getting-Started-Developing](https://github.com/zxing/zxing/wiki/Getting-Started-Developing)

- zxing Maven 仓库：[Home » com.google » zxing](https://mvnrepository.com/artifact/com.google.zxing)

## 介绍

ZXing是一个开放源码的，用Java实现的多种格式的1D/2D条码图像处理库，它包含了联系到其他语言的端口。

Zxing可以实现条形码编码和解码以及使用手机的内置的摄像头完成条形码的扫描及解码。

Zxing支持的条码格式如下：

| 1D product            | 1D industrial | 2D           |
| --------------------- | ------------- | ------------ |
| UPC-A                 | Code 39       | QR Code      |
| UPC-E                 | Code 93       | Data Matrix  |
| EAN-8                 | Code 128      | Aztec        |
| EAN-13                | Codabar       | PDF 417      |
| UPC/EAN Extension 2/5 | ITF           | MaxiCode     |
|                       |               | RSS-14       |
|                       |               | RSS-Expanded |

Zxing库的主要部分支持以下几个功能：

- 核心代码的使用
- 适用于J2SE客户端的版本
- 适用于Android客户端的版本（即BarcodeScanner）
- Android的集成（通过Intent支持和BarcodeScanner的集成）
- 等等

这里使用到的部分是适用于J2SE客户端的版本。

## 整合使用

Maven项目引入依赖：

```xml
<!-- 开源多维码生成工具 -->
<dependency>
    <groupId>com.google.zxing</groupId>
    <artifactId>core</artifactId>
    <version>3.4.0</version>
</dependency>
<dependency>
    <groupId>com.google.zxing</groupId>
    <artifactId>javase</artifactId>
    <version>3.4.0</version>
</dependency>
```

依赖说明：

- zxing/core 是二维码操作的核心包，必须使用
- zxing/javase 是对 Java Web 应用的拓展

生成条形码示例：

```java
    /**
     * 生成条形码
     *
     * @param codeContent     条形码内容
     * @param format          条形码类型
     * @param codeWidth       宽度，单位像素
     * @param codeHeight      高度，单位像素
     * @param frontColor      前景色，0x000000 表示黑色
     * @param backgroundColor 背景色，0xFFFFFF 表示白色
     * @param hints           编码提示类型
     * @return /
     * @throws WriterException /
     */
    public static BufferedImage createBarcode(String codeContent, BarcodeFormat format, int codeWidth, int codeHeight, int frontColor, int backgroundColor, Map<EncodeHintType, ?> hints) throws WriterException {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        BitMatrix bitMatrix = multiFormatWriter.encode(codeContent, format, codeWidth, codeHeight, hints);
        BufferedImage bufferedImage = new BufferedImage(codeWidth, codeHeight, BufferedImage.TYPE_INT_BGR);
        for (int x = 0; x < codeWidth; x++) {
            for (int y = 0; y < codeHeight; y++) {
                bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? frontColor : backgroundColor);
            }
        }
        return bufferedImage;
    }
```

生成二维码示例：

```java
    /**
     * 生成二维码
     *
     * @param codeContent     二维码内容
     * @param codeWidth       宽度，单位像素
     * @param codeHeight      高度，单位像素
     * @param frontColor      前景色，0x000000 表示黑色
     * @param backgroundColor 背景色，0xFFFFFF 表示白色
     * @param hints           编码提示类型
     * @return /
     * @throws WriterException /
     */
    public static BufferedImage createQRBarcode(String codeContent, int codeWidth, int codeHeight, int frontColor, int backgroundColor, Map<EncodeHintType, ?> hints) throws WriterException {
        return createBarcode(codeContent, BarcodeFormat.QR_CODE, codeWidth, codeHeight, frontColor, backgroundColor, hints);
    }
```

生成二维码(带logo)示例

```java
    /**
     * 生成二维码(带logo)
     *
     * @param codeContent     二维码内容
     * @param codeWidth       宽度，单位像素
     * @param codeHeight      高度，单位像素
     * @param frontColor      前景色，0x000000 表示黑色
     * @param backgroundColor 背景色，0xFFFFFF 表示白色
     * @param hints           编码提示类型
     * @param logoIn          logo图片输入流
     * @return /
     * @throws WriterException /
     * @throws IOException     /
     */
    public static BufferedImage createQRCodeWithLog(String codeContent, int codeWidth, int codeHeight, int frontColor, int backgroundColor, Map<EncodeHintType, ?> hints, InputStream logoIn) throws WriterException, IOException {
        // ===== 生成二维码 =====
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        BitMatrix bitMatrix = multiFormatWriter.encode(codeContent, BarcodeFormat.QR_CODE, codeWidth, codeHeight, hints);

        BufferedImage bufferedImage = new BufferedImage(codeWidth, codeHeight, BufferedImage.TYPE_INT_BGR);
        for (int x = 0; x < codeWidth; x++) {
            for (int y = 0; y < codeHeight; y++) {
                bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? frontColor : backgroundColor);
            }
        }
        BufferedImage qrBarcode = createQRBarcode(codeContent, codeWidth, codeHeight, frontColor, backgroundColor, hints);

        // ===== 绘制logo =====
        logoMatrix(qrBarcode, logoIn);
        return qrBarcode;
    }

    /**
     * 二维码添加logo
     */
    public static void logoMatrix(BufferedImage matrixImage, InputStream logoIn) throws IOException {
        //读取二维码图片，并构建绘图对象
        Graphics2D g2 = matrixImage.createGraphics();

        int matrixWidth = matrixImage.getWidth();
        int matrixHeigh = matrixImage.getHeight();

        //读取Logo图片
        BufferedImage logo = ImageIO.read(logoIn);

        //开始绘制图片
        g2.drawImage(logo, matrixWidth / 5 * 2, matrixHeigh / 5 * 2, matrixWidth / 5, matrixHeigh / 5, null);//绘制
        BasicStroke stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke);// 设置笔画对象
        //指定弧度的圆角矩形
        RoundRectangle2D.Float round = new RoundRectangle2D.Float(matrixWidth / 5f * 2f, matrixHeigh / 5f * 2f, matrixWidth / 5f, matrixHeigh / 5f, 20, 20);
        g2.setColor(Color.white);
        g2.draw(round);// 绘制圆弧矩形

        //设置logo 有一道灰色边框
        BasicStroke stroke2 = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke2);// 设置笔画对象
        RoundRectangle2D.Float round2 = new RoundRectangle2D.Float(matrixWidth / 5f * 2f + 2f, matrixHeigh / 5f * 2f + 2f, matrixWidth / 5f - 4f, matrixHeigh / 5f - 4f, 20, 20);
        g2.setColor(new Color(128, 128, 128));
        g2.draw(round2);// 绘制圆弧矩形

        g2.dispose();
        matrixImage.flush();
    }
```

解析二维码示例：

```java
    /**
     * 解析二维码/条形码
     */
    public static String parseBarcodeByInputStream(InputStream in) throws IOException, NotFoundException {
        BufferedImage bufferedImage = ImageIO.read(in);
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        HashMap<DecodeHintType, String> hints = new HashMap<>(2);
        hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
        Result result = new MultiFormatReader().decode(bitmap, hints);
        return result.getText();
    }
```
