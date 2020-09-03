package com.example.poi.config.dto;

import com.example.poi.config.poi.excel.ExcelImageData;
import com.example.poi.config.poi.excel.ExcelProperty;
import lombok.Data;

@Data
public class StudentInfoExcelDTO {
    @ExcelProperty(value = "学号", index = 1)
    private String studentNumber;
    @ExcelProperty(value = "姓名", index = 2)
    private String name;
    @ExcelProperty(value = "年龄", index = 3)
    private String age;
    @ExcelProperty(value = "年纪", index = 4)
    private String grade;
    @ExcelProperty(value = "班级", index = 5)
    private String classroom;
    @ExcelImageData
    @ExcelProperty(value = "照片", index = 6)
    private String photoUrl;
}
