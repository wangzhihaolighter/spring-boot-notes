package com.example.easyexcel.config.dto;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.example.easyexcel.config.excel.LocalDateTimeConverter;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@ExcelIgnoreUnannotated
public class ExcelDTO {
  @ExcelProperty("ID")
  private Long id;

  @ExcelProperty("用户名称")
  private String username;

  private String password;

  @ExcelProperty(value = "时间戳", converter = LocalDateTimeConverter.class)
  private LocalDateTime timestamp;
}
