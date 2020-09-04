# Spring Boot整合邮件发送

1.pom文件中引入mail的starter依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

2.项目配置文件中设置邮件相关配置

```properties
#邮件发送服务器地址
spring.mail.host=smtp.qq.com
#邮件发送服务器端口
spring.mail.port=465
#用户名
spring.mail.username=xxx@qq.com
#密码
spring.mail.password=
spring.mail.default-encoding=utf-8
#开启debug模式，控制台会打印出邮件发送的相关信息
spring.mail.properties.mail.debug=true
spring.mail.properties.mail.smtp.ssl.enable=true
spring.mail.properties.mail.smtp.starttls.required=false
spring.mail.properties.mail.smtp.connectiontimeout=5000
spring.mail.properties.mail.smtp.timeout=3000
spring.mail.properties.mail.smtp.writetimeout=5000
#邮件socket代理配置
spring.mail.properties.proxySet=true
spring.mail.properties.mail.smtp.socks.host=
spring.mail.properties.mail.smtp.socks.port=
```

具体可配置属性可以查看类`org.springframework.boot.autoconfigure.mail.MailProperties`。

`spring.mail.properties`是一个map，它支持更多属性，更详细的配置可以查看官方主页：<https://javaee.github.io/javamail/>。

3.注入`org.springframework.mail.javamail.JavaMailSender`，使用它发送邮件

```java
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
```
