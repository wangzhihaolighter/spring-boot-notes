package com.example.google.zxing.controller;

import com.example.google.zxing.utils.BarcodeUtil;
import com.google.zxing.WriterException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
public class SimpleController {

    @GetMapping("/")
    public void sayHello(HttpServletResponse response) throws IOException, WriterException {
        String codeContent = "https://www.baidu.com/";
        Resource resource = new ClassPathResource("java_logo.jpg");
        BufferedImage qrCodeWithLog = BarcodeUtil.createQRCodeWithLog(codeContent, BarcodeUtil.CODE_WIDTH, BarcodeUtil.CODE_HEIGHT, BarcodeUtil.FRONT_COLOR, BarcodeUtil.BACKGROUND_COLOR, BarcodeUtil.HINTS, resource.getInputStream());
        ImageIO.write(qrCodeWithLog, "jpg", response.getOutputStream());
    }

}
