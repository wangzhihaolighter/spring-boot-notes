package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    @Autowired
    private JavaMailSender javaMailSender;

    @RequestMapping("/email")
    public String email() {
        String fromEmail = "xxx@qq.com";
        String toEmail = "xxx@qq.com";
        // 构造Email消息
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("到 2022 年，75% 的数据库将托管在云端");
        message.setText("不久前，MySQL 首席技术官在博客发文表示：“我们正在向云迁移！”所以，数据库向云平台迁移将会是一个趋势吗？");
        javaMailSender.send(message);
        return "email send success!";
    }

}
