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

    @RequestMapping("/email/send")
    public String sendEmail() {
        //发件人邮箱必须与配置的邮箱一致
        String fromEmail = "xxx@qq.com";
        String toEmail = "xxx@qq.com";
        // 构造Email消息
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("邮件标题");
        message.setText("邮件内容");
        javaMailSender.send(message);
        return "email send success!";
    }

}
