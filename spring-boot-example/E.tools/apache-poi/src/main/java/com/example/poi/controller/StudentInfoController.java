package com.example.poi.controller;

import com.example.poi.entity.StudentInfo;
import com.example.poi.service.StudentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class StudentInfoController {
    private final StudentInfoService studentInfoService;

    public StudentInfoController(StudentInfoService studentInfoService) {
        this.studentInfoService = studentInfoService;
    }

    @GetMapping("/list")
    public List<StudentInfo> list() {
        return studentInfoService.selectList();
    }

    @PostMapping("/import")
    public String importStudentInfoExcel(@RequestParam("file") MultipartFile file){
        studentInfoService.importStudentInfoExcel(file);
        return "success";
    }

    @GetMapping(value = "/export")
    public void exportStudentInfoExcel(HttpServletResponse response) {
        studentInfoService.exportStudentInfoExcel(response);
    }

}
