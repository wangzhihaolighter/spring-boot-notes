# Apache POI使用

## 资料

- Apache POI官网：[poi.apache.org](https://poi.apache.org/)

- Apache POI Maven仓库：[Home » org.apache » poi](https://mvnrepository.com/artifact/org.apache.poi)

- Apache POI components API：[Apache POI - Component Overview](https://poi.apache.org/components/index.html)

- Apache POI 官方示例：[asf - Revision 1881339: /poi/trunk/src/examples/src/org/apache/poi/examples](https://svn.apache.org/repos/asf/poi/trunk/src/examples/src/org/apache/poi/examples/)

## Apache POI 简介

Apache POI 是 Apache 软件基金会的开源项目，是一个 Java 实现的操作 Microsoft Office格式文档的工具库，通常用于处理 Excel 文件，同时它也支持读写 MS Word 、 MS PowerPoint 文件等其他Office格式文档。

POI 是 `Poor Obfuscation Implementation` 首字母缩写，意为“简洁版的模糊实现”。

## Apache POI 组件概述

Apache POI 项目组件对应的格式文档

- POIFS for OLE 2 Documents
- HSSF and XSSF for Excel Documents
- HWPF and XWPF for Word Documents
- HSLF and XSLF for PowerPoint Documents
- HPSF for OLE 2 Document Properties
- HDGF and XDGF for Visio Documents
- HPBF for Publisher Documents
- HMEF for TNEF (winmail.dat) Outlook Attachments
- HSMF for Outlook Messages

Component Map (组件对应文档及Maven仓库项目)：

![example](/IMG/POI/001.png)

![example](/IMG/POI/002.png)

## 整合使用

项目中引入Maven依赖：

```xml
<!--Apache POI-->
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi</artifactId>
    <version>4.1.2</version>
</dependency>
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>4.1.2</version>
</dependency>
```

## Excel文档处理示例

### 导出带图片的Excel示例

```java
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * POI导出带图片的Excel示例
 */
public class PoiExcelExport {

    public static void main(String[] args) throws IOException {
        exportXls();
        exportXlsx();
    }

    public static void exportXls() throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("sheet1");
        //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) 650);

        HSSFCellStyle headStyle = workbook.createCellStyle();
        headStyle.setAlignment(HorizontalAlignment.CENTER);
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        //声明列对象
        HSSFCell cell;

        //创建标题
        List<String> titleList = Arrays.asList("姓名", "人物图片");
        for (int i = 0; i < titleList.size(); i++) {
            sheet.setColumnWidth(i, 6000);
            cell = row.createCell(i);
            cell.setCellValue(titleList.get(i));
            HSSFFont font = workbook.createFont();
            //设置excel数据字体颜色
            font.setColor(Font.COLOR_NORMAL);
            //设置excel数据字体大小
            font.setFontHeightInPoints((short) 15);
            headStyle.setFont(font);
            cell.setCellStyle(headStyle);
        }

        HSSFCellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setAlignment(HorizontalAlignment.CENTER);
        dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        dataStyle.setWrapText(true);//自动换行

        //填充数据
        row = sheet.createRow(1);
        row.setHeight((short) 2000);
        cell = row.createCell(0);
        cell.setCellValue("蒙奇·D·路飞");
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        BufferedImage image = ImageIO.read(new URL("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1400063385,2378282079&fm=26&gp=0.jpg"));
        ImageIO.write(image, "jpg", byteArrayOut);
        HSSFClientAnchor anchor = new HSSFClientAnchor(
                0, 0, 0, 0,
                //第二行、第二列
                (short) 1, 1, (short) 2, 2
        );
        //插入图片
        patriarch.createPicture(
                anchor,
                workbook.addPicture(
                        byteArrayOut.toByteArray(),
                        HSSFWorkbook.PICTURE_TYPE_JPEG
                )
        );
        byteArrayOut.close();

        //输出至文件
        workbook.write(new FileOutputStream(new File("D:\\测试.xls")));

        workbook.close();
    }

    public static void exportXlsx() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("sheet1");
        //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
        XSSFDrawing patriarch = sheet.createDrawingPatriarch();
        XSSFRow row = sheet.createRow(0);
        row.setHeight((short) 650);

        XSSFCellStyle headStyle = workbook.createCellStyle();
        headStyle.setAlignment(HorizontalAlignment.CENTER);
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        //声明列对象
        XSSFCell cell;

        //创建标题
        List<String> titleList = Arrays.asList("姓名", "人物图片");
        for (int i = 0; i < titleList.size(); i++) {
            sheet.setColumnWidth(i, 6000);
            cell = row.createCell(i);
            cell.setCellValue(titleList.get(i));
            XSSFFont font = workbook.createFont();
            //设置excel数据字体颜色
            font.setColor(Font.COLOR_NORMAL);
            //设置excel数据字体大小
            font.setFontHeightInPoints((short) 15);
            headStyle.setFont(font);
            cell.setCellStyle(headStyle);
        }

        XSSFCellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setAlignment(HorizontalAlignment.CENTER);
        dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        dataStyle.setWrapText(true); //自动换行

        //填充数据
        row = sheet.createRow(1);
        row.setHeight((short) 2000);
        cell = row.createCell(0);
        cell.setCellValue("蒙奇·D·路飞");
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        BufferedImage image = ImageIO.read(new URL("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1400063385,2378282079&fm=26&gp=0.jpg"));
        ImageIO.write(image, "jpg", byteArrayOut);
        XSSFClientAnchor anchor = new XSSFClientAnchor(
                0, 0, 0, 0,
                //第二行、第二列
                (short) 1, 1, (short) 2, 2
        );
        // 插入图片
        patriarch.createPicture(
                anchor,
                workbook.addPicture(
                        byteArrayOut.toByteArray(),
                        XSSFWorkbook.PICTURE_TYPE_JPEG
                )
        );
        byteArrayOut.close();

        //输出至文件
        workbook.write(new FileOutputStream(new File("D:\\测试.xlsx")));

        workbook.close();
    }

}
```

### 导入带图片的Excel示例

```java
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * POI导入带图片的Excel示例
 */
public class PoiExcelImport {

    public static void main(String[] args) throws IOException {
        String filePath = "D:\\测试.xlsx";
        String picStorePath = "D:\\";

        File file = new File(filePath);
        importExcelWithImage(new FileInputStream(file), file.getName(), picStorePath);
    }

    public static void importExcelWithImage(InputStream in, String fileName, String picStorePath) {
        Workbook workbook;
        Sheet sheet;
        //图片数据
        Map<String, PictureData> pictureDataMap = null;

        //得到工作簿
        try {
            if (fileName.endsWith(".xls")) {
                //2003版本的excel，用.xls结尾
                workbook = new HSSFWorkbook(in);
            } else if (fileName.endsWith(".xlsx")) {
                //2007版本的excel，用.xlsx结尾
                workbook = new XSSFWorkbook(in);
            } else {
                System.err.println("不支持的Excel格式！");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // ----- 获取图片数据 -----
        sheet = workbook.getSheetAt(0);
        // 判断用07还是03的方法获取图片
        if (fileName.endsWith(".xls")) {
            pictureDataMap = getXlsPictures((HSSFSheet) sheet);
        } else {
            pictureDataMap = getXlsxPictures((XSSFSheet) sheet);
        }

        // ----- 获得标题 -----
        Row rowHead = sheet.getRow(0);
        int rowHeadPhysicalNumberOfCells = rowHead.getPhysicalNumberOfCells();
        for (int i = 0; i < rowHeadPhysicalNumberOfCells; i++) {
            Cell cell = rowHead.getCell(i);
            System.out.println(cell.getStringCellValue());
        }

        // ----- 获取其他数据 -----
        int totalRowNum = sheet.getLastRowNum();
        for (int i = 1; i <= totalRowNum; i++) {
            Row row = sheet.getRow(i);
            int physicalNumberOfCells = row.getPhysicalNumberOfCells();
            for (int j = 0; j < physicalNumberOfCells; j++) {
                Cell cell = row.getCell(j);
                System.out.println(cell.getStringCellValue());
            }
        }

        // ----- 输出图片 -----
        try {
            printImg(pictureDataMap, picStorePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取图片和位置 (xls)
     *
     * @param sheet /
     * @return /
     */
    public static Map<String, PictureData> getXlsPictures(HSSFSheet sheet) {
        Map<String, PictureData> map = new HashMap<>();
        List<HSSFShape> list = sheet.getDrawingPatriarch().getChildren();
        for (HSSFShape shape : list) {
            if (shape instanceof HSSFPicture) {
                HSSFPicture picture = (HSSFPicture) shape;
                HSSFClientAnchor cAnchor = (HSSFClientAnchor) picture.getAnchor();
                // 行号-列号
                String key = cAnchor.getRow1() + "-" + cAnchor.getCol1();
                map.put(key, picture.getPictureData());
            }
        }
        return map;
    }

    /**
     * 获取图片和位置 (xlsx)
     *
     * @param sheet /
     * @return /
     */
    public static Map<String, PictureData> getXlsxPictures(XSSFSheet sheet) {
        Map<String, PictureData> map = new HashMap<>(4);
        List<POIXMLDocumentPart> list = sheet.getRelations();
        for (POIXMLDocumentPart part : list) {
            if (part instanceof XSSFDrawing) {
                XSSFDrawing drawing = (XSSFDrawing) part;
                List<XSSFShape> shapes = drawing.getShapes();
                for (XSSFShape shape : shapes) {
                    XSSFPicture picture = (XSSFPicture) shape;
                    XSSFClientAnchor anchor = picture.getPreferredSize();
                    // 行号-列号
                    CTMarker marker = anchor.getFrom();
                    String key = marker.getRow() + "-" + marker.getCol();
                    map.put(key, picture.getPictureData());
                }
            }
        }
        return map;
    }

    /**
     * 输出图片
     *
     * @param pictureDataMap 图片信息
     * @param path           图片保存路径
     * @throws IOException /
     */
    public static void printImg(Map<String, PictureData> pictureDataMap, String path) throws IOException {
        Object[] key = pictureDataMap.keySet().toArray();
        for (int i = 0; i < pictureDataMap.size(); i++) {
            // 获取图片索引
            String picIndex = key[i].toString();
            // 获取图片流
            PictureData pic = pictureDataMap.get(picIndex);
            // 获取图片格式
            String ext = pic.suggestFileExtension();

            byte[] data = pic.getData();

            //图片保存路径
            String imgPath = path + picIndex + "." + ext;
            FileOutputStream out = new FileOutputStream(imgPath);
            out.write(data);
            out.close();

            System.out.println("图片路径：" + imgPath);
        }
    }

}
```
