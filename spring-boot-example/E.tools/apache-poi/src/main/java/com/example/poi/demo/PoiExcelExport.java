package com.example.poi.demo;

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
        //自动换行
        dataStyle.setWrapText(true);

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
        //自动换行
        dataStyle.setWrapText(true);

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
        FileOutputStream fileOutputStream = new FileOutputStream(new File("D:\\测试.xlsx"));
        workbook.write(fileOutputStream);

        workbook.close();
        fileOutputStream.close();
    }

}
