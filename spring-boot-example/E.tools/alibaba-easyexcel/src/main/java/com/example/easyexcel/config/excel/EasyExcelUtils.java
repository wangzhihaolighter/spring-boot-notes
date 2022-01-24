package com.example.easyexcel.config.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/** EasyExcel工具类 */
@Slf4j
public class EasyExcelUtils {

  /**
   * Excel导入
   *
   * @param in /
   * @param sheetIndex sheet索引，从0开始
   * @param clazz /
   * @param <T> /
   * @return /
   */
  public static <T> List<T> importData(InputStream in, int sheetIndex, Class<T> clazz) {
    try {
      EasyExcelDataListener<T> listener = new EasyExcelDataListener<>();
      EasyExcel.read(in, clazz, listener).sheet(sheetIndex).doRead();
      return listener.getData();
    } catch (Exception e) {
      log.error("导入Excel异常：" + e.getMessage(), e);
    }
    return Collections.emptyList();
  }

  /**
   * Excel导出
   *
   * @param response /
   * @param filename /
   * @param sheetName /
   * @param clazz /
   * @param data /
   * @param <T> /
   */
  public static <T> void exportData(
      HttpServletResponse response,
      String filename,
      String sheetName,
      Class<T> clazz,
      List<T> data) {
    try {
      response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
      response.setCharacterEncoding("utf-8");
      String fileName = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
      response.setHeader(
          "Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
      EasyExcel.write(response.getOutputStream(), clazz)
          .sheet(sheetName)
          .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
          .doWrite(data);
    } catch (Exception e) {
      log.error("Excel导出异常：" + e.getMessage(), e);
    }
  }

  /**
   * Excel导出
   *
   * @param outputStream /
   * @param sheetName /
   * @param clazz /
   * @param data /
   * @param <T> /
   */
  public static <T> void exportData(
      OutputStream outputStream, String sheetName, Class<T> clazz, List<T> data) {
    try {
      EasyExcel.write(outputStream, clazz)
          .sheet(sheetName)
          .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
          .doWrite(data);
    } catch (Exception e) {
      log.error("Excel导出异常：" + e.getMessage(), e);
    }
  }
}
