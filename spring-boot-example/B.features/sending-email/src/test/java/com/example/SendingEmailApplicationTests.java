package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
public class SendingEmailApplicationTests {
  @Autowired JavaMailSender javaMailSender;

  @Test
  void sendEmail() {
    try {
      // 发件人邮箱,必须与配置的邮箱一致
      String fromEmail = "xxx@qq.com";
      // 收件人邮箱
      String toEmail = "yyy@qq.com";
      // 构造Email消息
      SimpleMailMessage message = new SimpleMailMessage();
      message.setFrom(fromEmail);
      message.setTo(toEmail);
      message.setSubject("邮件标题");
      message.setText("邮件内容");
      javaMailSender.send(message);
    } catch (MailException e) {
      e.printStackTrace();
    }
  }
}
