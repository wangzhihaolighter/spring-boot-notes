package com.example.spring.web.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件管理示例控制器
 *
 * @author wangzhihao
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class FileExampleController {

  @GetMapping(value = "/download")
  public void download(HttpServletResponse response) {
    try {
      String filename = "test.txt";
      String classpath = ResourceUtils.getURL("classpath:").getPath();
      String filePath = String.join(File.separator, classpath, "static", filename);
      String contentType = "application/octet-stream";
      String headerKey = "Content-Disposition";
      String headerValue = String.format("attachment; filename=\"%s\"", filename);

      // set headers for the response
      response.setHeader(headerKey, headerValue);
      // set content-type
      response.setContentType(contentType);
      // response write
      File downloadFile = new File(filePath);
      @Cleanup OutputStream out = response.getOutputStream();
      @Cleanup BufferedInputStream bis = new BufferedInputStream(new FileInputStream(downloadFile));
      byte[] buffer = new byte[8192 * 10];
      int len;
      while ((len = bis.read(buffer)) != -1) {
        out.write(buffer, 0, len);
      }
      out.flush();
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** 文件上传，限制以multipart数据的形式提交表单 */
  @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file)
      throws IOException {
    String fileName = file.getOriginalFilename();
    String classpath = ResourceUtils.getURL("classpath:").getPath();
    String uploadPath = String.join(File.separator, classpath, "static");
    String uploadFilePath = String.join(File.separator, classpath, "static", fileName);

    // 上传文件目录
    File upload = new File(uploadPath);
    if (!upload.exists()) {
      upload.mkdirs();
    }
    log.info("upload url:" + upload.getAbsolutePath());

    // 保存文件
    File dest = new File(uploadFilePath);
    file.transferTo(dest);

    return ResponseEntity.ok(dest.getAbsolutePath());
  }
}
