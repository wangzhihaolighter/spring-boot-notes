package com.example.controller;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    private final JavaMailSender javaMailSender;

    public SimpleController(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

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
