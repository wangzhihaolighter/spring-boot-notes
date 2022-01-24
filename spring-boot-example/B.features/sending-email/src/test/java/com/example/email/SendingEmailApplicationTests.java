package com.example.email;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailException;

@SpringBootTest
class SendingEmailApplicationTests {
  @Autowired EmailService emailService;

  @Test
  void sendEmailTest() {
    try {
      emailService.sendEmail();
    } catch (MailException e) {
      e.printStackTrace();
    }
  }
}
