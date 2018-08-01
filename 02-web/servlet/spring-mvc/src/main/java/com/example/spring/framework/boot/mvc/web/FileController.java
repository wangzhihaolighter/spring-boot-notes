package com.example.spring.framework.boot.mvc.web;

import org.springframework.http.MediaType;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 文件处理
 */
@RestController
@RequestMapping("/file")
public class FileController {

    /**
     * 文件上传，限制以multipart数据的形式提交表单
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void upload(@RequestParam("file") MultipartFile file) throws IOException {
        //文件名
        String fileName = file.getOriginalFilename();

        //获取项目根目录目录
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
        }
        System.out.println("path:" + path.getAbsolutePath());

        //上传文件目录
        File upload = new File(path.getAbsolutePath() + File.separator + "static" + File.separator + "upload" + File.separator);
        if (!upload.exists()) {
            upload.mkdirs();
        }
        System.out.println("upload url:" + upload.getAbsolutePath());

        //保存文件
        File dest = new File(upload.getAbsolutePath() + File.separator + fileName);
        file.transferTo(dest);
    }

}
