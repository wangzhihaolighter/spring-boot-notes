package com.example.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {
  private final JavaMailSender javaMailSender;

  public EmailService(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  public void sendEmail() {
    // 发件人邮箱必须与配置的邮箱一致
    String fromEmail = "xxx@qq.com";
    String toEmail = "yyy@qq.com";
    // 构造Email消息
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(fromEmail);
    message.setTo(toEmail);
    message.setSubject("邮件标题");
    message.setText("邮件内容");
    javaMailSender.send(message);
  }
}
