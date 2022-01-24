package com.example.easyexcel.config.excel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** 数据类型转化 - LocalDateTime */
public class LocalDateTimeConverter implements Converter<LocalDateTime> {

  private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

  @Override
  public Class<LocalDateTime> supportJavaTypeKey() {
    return LocalDateTime.class;
  }

  @Override
  public CellDataTypeEnum supportExcelTypeKey() {
    return CellDataTypeEnum.STRING;
  }

  @Override
  public LocalDateTime convertToJavaData(ReadConverterContext<?> context) throws Exception {
    return Converter.super.convertToJavaData(context);
  }

  @Override
  public LocalDateTime convertToJavaData(
      ReadCellData<?> cellData,
      ExcelContentProperty contentProperty,
      GlobalConfiguration globalConfiguration)
      throws Exception {
    return LocalDateTime.parse(cellData.getStringValue(), DateTimeFormatter.ofPattern(PATTERN));
  }

  @Override
  public WriteCellData<?> convertToExcelData(
      LocalDateTime value,
      ExcelContentProperty contentProperty,
      GlobalConfiguration globalConfiguration)
      throws Exception {
    return new WriteCellData<>(value.format(DateTimeFormatter.ofPattern(PATTERN)));
  }
}
