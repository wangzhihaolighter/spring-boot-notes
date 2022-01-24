package com.example.google.zxing.controller;

import com.example.google.zxing.utils.BarcodeUtil;
import com.google.zxing.WriterException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

  @GetMapping("/")
  public void sayHello(HttpServletResponse response) throws IOException, WriterException {
    String codeContent = "https://jdk.java.net/";
    Resource resource = new ClassPathResource("java_logo.jpg");
    BufferedImage qrCodeWithLog =
        BarcodeUtil.createQRCodeWithLog(codeContent, resource.getInputStream());
    ImageIO.write(qrCodeWithLog, "jpg", response.getOutputStream());
  }
}
