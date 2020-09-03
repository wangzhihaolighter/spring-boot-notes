package com.example.poi.config.poi;

import com.example.poi.config.poi.excel.ExcelImageData;
import com.example.poi.config.poi.excel.ExcelProperty;
import com.example.poi.config.poi.excel.ExcelTypeEnum;
import com.example.poi.config.image.SmMsImageService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

@Service
public class PoiExcelService {
    private final SmMsImageService smMsImageService;

    public PoiExcelService(SmMsImageService smMsImageService) {
        this.smMsImageService = smMsImageService;
    }

    public <T> List<T> read(InputStream excelIn, Class<T> dataClazz, ExcelTypeEnum excelTypeEnum) throws IOException, IllegalAccessException, InstantiationException {
        Workbook workbook = null;
        try {
            // ----- 获取工作簿，默认读取第一个sheet -----
            if (excelTypeEnum.equals(ExcelTypeEnum.XLS)) {
                workbook = new HSSFWorkbook(excelIn);
            } else if (excelTypeEnum.equals(ExcelTypeEnum.XLSX)) {
                workbook = new XSSFWorkbook(excelIn);
            } else {
                throw new RuntimeException("不支持的Excel类型");
            }
            Sheet sheet = workbook.getSheetAt(0);

            // ----- 上传图片，获取图片链接 -----
            Map<String, PictureData> pictureDataMap = PoiExcelService.getExcelPictures(sheet);
            Map<String, String> imageInfoMap = new HashMap<>(pictureDataMap.size());
            for (Map.Entry<String, PictureData> pictureDataEntry : pictureDataMap.entrySet()) {
                String key = pictureDataEntry.getKey();
                PictureData pictureData = pictureDataEntry.getValue();
                String imageUrl = smMsImageService.upload(pictureData.getData());
                imageInfoMap.put(key, imageUrl);
            }

            // ----- 获取Excel数据 -----
            List<T> dataList = new ArrayList<>();
            Field[] declaredFields = dataClazz.getDeclaredFields();
            //数据总行数
            int totalRowNum = sheet.getLastRowNum();
            if (totalRowNum <= 1) {
                return null;
            }
            //第一行默认视为标题行，从第二行开始读取
            for (int i = 1; i <= totalRowNum; i++) {
                T t = dataClazz.newInstance();
                //当前行数据
                Row row = sheet.getRow(i);
                //封装对应列的数据
                for (Field declaredField : declaredFields) {
                    ExcelProperty excelProperty = declaredField.getAnnotation(ExcelProperty.class);
                    if (excelProperty == null) {
                        continue;
                    }
                    //当前字段对应列索引
                    int index = excelProperty.index() - 1;
                    //自定义图片数据
                    if (declaredField.isAnnotationPresent(ExcelImageData.class)) {
                        String indexKey = i + "-" + index;
                        declaredField.setAccessible(true);
                        declaredField.set(t, imageInfoMap.get(indexKey));
                    }
                    //普通数据
                    else {
                        declaredField.setAccessible(true);
                        declaredField.set(t, getCellValue(row.getCell(index)));
                    }
                }
                dataList.add(t);
            }

            return dataList;
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
    }

    /**
     * 创建 2007 以上版本工作簿
     *
     * @param sheetName
     * @param dataList
     * @param clazz
     * @return
     */
    public XSSFWorkbook writeXSSFWorkbook(String sheetName, List<?> dataList, Class<?> clazz) {
        short headRowHeight = (short) 1000;
        short headRowFontHeight = (short) 15;
        int columnWidth = 6000;
        short dataRowHeight = (short) 1500;

        // ----- 获取标题 -----
        List<String> titleList = new LinkedList<>();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            ExcelProperty annotation = declaredField.getAnnotation(ExcelProperty.class);
            String value = annotation.value()[0];
            titleList.add(value);
        }

        // ----- 工作簿 -----
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetName);
        // 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
        XSSFDrawing patriarch = sheet.createDrawingPatriarch();
        XSSFRow row = sheet.createRow(0);
        row.setHeight(headRowHeight);

        // ----- 标题样式 -----
        XSSFCellStyle headStyle = workbook.createCellStyle();
        headStyle.setAlignment(HorizontalAlignment.CENTER);
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //单元格背景
        headStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //单元格边框
        headStyle.setBorderBottom(BorderStyle.MEDIUM);
        headStyle.setBorderLeft(BorderStyle.MEDIUM);
        headStyle.setBorderTop(BorderStyle.MEDIUM);
        headStyle.setBorderRight(BorderStyle.MEDIUM);
        XSSFFont font = workbook.createFont();
        //设置excel数据字体颜色
        font.setColor(Font.COLOR_NORMAL);
        //设置excel数据字体大小
        font.setFontHeightInPoints(headRowFontHeight);
        headStyle.setFont(font);
        //自动换行
        headStyle.setWrapText(true);

        // ----- 组装标题行 -----
        //声明列对象
        XSSFCell cell;
        //创建标题
        for (int i = 0; i < titleList.size(); i++) {
            sheet.setColumnWidth(i, columnWidth);
            cell = row.createCell(i);
            cell.setCellValue(titleList.get(i));
            cell.setCellStyle(headStyle);
        }

        // ----- 数据样式样式 -----
        XSSFCellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setAlignment(HorizontalAlignment.CENTER);
        dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //自动换行
        dataStyle.setWrapText(true);

        // ----- 组装数据行 -----
        for (int i = 0; i < dataList.size(); i++) {
            row = sheet.createRow(i + 1);
            row.setHeight(dataRowHeight);
            Object data = dataList.get(i);
            for (Field declaredField : declaredFields) {
                Object o = null;
                try {
                    declaredField.setAccessible(true);
                    o = declaredField.get(data);
                    if (o == null) {
                        continue;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                ExcelProperty excelProperty = declaredField.getAnnotation(ExcelProperty.class);
                int index = excelProperty.index();

                //普通数据
                if (!declaredField.isAnnotationPresent(ExcelImageData.class)) {
                    cell = row.createCell(index - 1);
                    cell.setCellValue(String.valueOf(o));
                    cell.setCellStyle(dataStyle);
                }
                //图片数据
                else {
                    try (ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream()) {
                        BufferedImage image;
                        String imageUrl = String.valueOf(o);
                        URL url = new URL(imageUrl);
                        URLConnection urlConnection = url.openConnection();
                        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36");
                        image = ImageIO.read(urlConnection.getInputStream());
                        ImageIO.write(image, "jpg", byteArrayOut);
                        //图片定位
                        XSSFClientAnchor anchor = new XSSFClientAnchor(
                                0, 0, columnWidth, dataRowHeight,
                                (short) index - 1, i + 1, (short) index, i + 2
                        );
                        // 插入图片
                        patriarch.createPicture(
                                anchor,
                                workbook.addPicture(
                                        byteArrayOut.toByteArray(),
                                        XSSFWorkbook.PICTURE_TYPE_JPEG
                                )
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return workbook;
    }

    /**
     * 获取sheet中图片及其图片位置信息
     *
     * @param sheet
     * @return 图片及其图片位置信息
     */
    public static Map<String, PictureData> getExcelPictures(Sheet sheet) {
        //key格式：行号-列号
        Map<String, PictureData> map = new HashMap<>(sheet.getLastRowNum());
        // xls
        if (sheet instanceof HSSFSheet) {
            HSSFSheet hssfSheet = (HSSFSheet) sheet;
            List<HSSFShape> hssfShapes = hssfSheet.getDrawingPatriarch().getChildren();
            for (HSSFShape shape : hssfShapes) {
                if (!(shape instanceof HSSFPicture)) {
                    continue;
                }
                HSSFPicture picture = (HSSFPicture) shape;
                HSSFClientAnchor cAnchor = (HSSFClientAnchor) picture.getAnchor();
                PictureData pictureData = picture.getPictureData();
                String key = cAnchor.getRow1() + "-" + cAnchor.getCol1();
                map.put(key, pictureData);
            }
        }
        //xlsx
        else if (sheet instanceof XSSFSheet) {
            XSSFSheet xssfSheet = (XSSFSheet) sheet;
            List<POIXMLDocumentPart> relations = xssfSheet.getRelations();
            for (POIXMLDocumentPart part : relations) {
                if (!(part instanceof XSSFDrawing)) {
                    continue;
                }
                XSSFDrawing drawing = (XSSFDrawing) part;
                List<XSSFShape> shapes = drawing.getShapes();
                for (XSSFShape shape : shapes) {
                    XSSFPicture picture = (XSSFPicture) shape;
                    XSSFClientAnchor anchor = picture.getPreferredSize();
                    CTMarker marker = anchor.getFrom();
                    String key = marker.getRow() + "-" + marker.getCol();
                    map.put(key, picture.getPictureData());
                }
            }
        }

        return map;
    }

    /**
     * 获取单元格值
     *
     * @param cell 单元格
     * @return 单元格值
     */
    public static Object getCellValue(Cell cell) {
        if (cell.getCellType() == CellType.BOOLEAN) {
            return cell.getBooleanCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            double numericCellValue = cell.getNumericCellValue();
            return Double.valueOf(numericCellValue).intValue();
        } else {
            return cell.getStringCellValue();
        }
    }

}
