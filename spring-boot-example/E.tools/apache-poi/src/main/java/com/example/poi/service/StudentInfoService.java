package com.example.poi.service;

import com.example.poi.config.dto.StudentInfoExcelDTO;
import com.example.poi.config.poi.excel.ExcelTypeEnum;
import com.example.poi.config.image.SmMsImageService;
import com.example.poi.config.poi.PoiExcelService;
import com.example.poi.dao.StudentInfoRepository;
import com.example.poi.entity.StudentInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class StudentInfoService {
    private final StudentInfoRepository studentInfoRepository;
    private final PoiExcelService poiExcelService;
    private final SmMsImageService smMsImageService;

    public StudentInfoService(StudentInfoRepository studentInfoRepository, PoiExcelService poiExcelService, SmMsImageService smMsImageService) {
        this.studentInfoRepository = studentInfoRepository;
        this.poiExcelService = poiExcelService;
        this.smMsImageService = smMsImageService;
    }

    public List<StudentInfo> selectList() {
        return studentInfoRepository.findAll();
    }

    /**
     * 导入学生信息Excel（包含图片）
     *
     * @param file 导入Excel文件
     */
    public void importStudentInfoExcel(MultipartFile file) {
        // 这里省略所有校验逻辑...

        try {
            InputStream inputStream = file.getInputStream();
            ExcelTypeEnum excelTypeEnum = file.getOriginalFilename().endsWith(".xls") ? ExcelTypeEnum.XLS : ExcelTypeEnum.XLSX;
            List<StudentInfoExcelDTO> studentInfoExcelDTOList = poiExcelService.read(inputStream, StudentInfoExcelDTO.class, excelTypeEnum);

            // ---- 转化数据 -----
            List<StudentInfo> studentInfos = new ArrayList<>();
            for (StudentInfoExcelDTO studentInfoExcelDTO : studentInfoExcelDTOList) {
                StudentInfo studentInfo = new StudentInfo();
                BeanUtils.copyProperties(studentInfoExcelDTO, studentInfo);
                studentInfo.setAge(Integer.parseInt(studentInfoExcelDTO.getAge()));
                studentInfos.add(studentInfo);
            }

            // ----- 持久化数据 -----
            studentInfoRepository.saveAll(studentInfos);
        } catch (IOException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        } finally {
            try {
                file.getInputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 导出学生信息（包含图片）
     *
     * @param response /
     */
    public void exportStudentInfoExcel(HttpServletResponse response) {
        // ----- 获取导出的学生信息数据 -----
        List<StudentInfo> studentInfos = studentInfoRepository.findAll();

        // ----- 转换数据类型 -----
        List<StudentInfoExcelDTO> studentInfoExcelDTOList = new ArrayList<>();
        for (StudentInfo studentInfo : studentInfos) {
            StudentInfoExcelDTO excelDto = new StudentInfoExcelDTO();
            BeanUtils.copyProperties(studentInfo, excelDto);
            studentInfoExcelDTOList.add(excelDto);
        }

        try (
                // ----- 获取工作簿 -----
                XSSFWorkbook workbook = poiExcelService.writeXSSFWorkbook("学生信息", studentInfoExcelDTOList, StudentInfoExcelDTO.class);
                // ----- 响应到客户端 -----
                OutputStream os = response.getOutputStream()
        ) {
            String exportFileName = "学生信息导出.xlsx";
            setExcelResponseHeader(response, exportFileName);
            workbook.write(os);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setExcelResponseHeader(HttpServletResponse response, String fileName) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel");
            //避免中文乱码
            fileName = new String(fileName.getBytes(), "ISO8859-1");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pragma", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
