package com.example.easyexcel.controller;

import com.example.easyexcel.config.dto.ExcelDTO;
import com.example.easyexcel.config.excel.EasyExcelUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/excel")
public class ExcelController {

  @SneakyThrows
  @PostMapping("/import")
  public ResponseEntity<Boolean> importExcel(MultipartFile file) {
    List<ExcelDTO> list = EasyExcelUtils.importData(file.getInputStream(), 0, ExcelDTO.class);
    list.forEach(l -> log.info(l.toString()));
    return ResponseEntity.ok(true);
  }

  @GetMapping("/export")
  public void export(HttpServletResponse response) {
    List<ExcelDTO> list = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      ExcelDTO excelDTO = new ExcelDTO();
      excelDTO.setId((long) i);
      excelDTO.setUsername(UUID.randomUUID().toString());
      excelDTO.setPassword("123456");
      excelDTO.setTimestamp(LocalDateTime.now());
      list.add(excelDTO);
    }
    EasyExcelUtils.exportData(response, "EasyExcel导出示例", "导出数据", ExcelDTO.class, list);
  }
}
