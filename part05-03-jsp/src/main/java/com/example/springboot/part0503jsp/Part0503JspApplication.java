package com.example.springboot.part0503jsp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Part0503JspApplication {
    public static void main(String[] args) {
        SpringApplication.run(Part0503JspApplication.class, args);
    }

    //官方示例：https://github.com/spring-projects/spring-boot/tree/v1.3.2.RELEASE/spring-boot-samples/spring-boot-sample-web-jsp

    //值得注意的点:
    // 1.pom文件中编译jsp的依赖<artifactId>tomcat-embed-jasper</artifactId>中为什么要加上provided依赖范围？
    /*
     * 相信也发现了，用启动类启动访问jsp页面会报错，而使用maven命令spring-boot:run则可以正常访问
     * 可以看一下这个分析，(为什么整合jsp后必须通过spring-boot:run方式启动？)[https://segmentfault.com/a/1190000009785247]
     * 第一选择，肯定会选择将这个依赖范围注释掉，效果很好，启动类启动访问页面也正常了？(其实我注释掉仍旧不能正常访问页面。。。其他人博客说可以，可能是版本问题，姑且当它有过可行性吧)
     * 但是很遗憾，在将项目打成war放入后放入外部tomcat容器时，会报错
     * 原因：spring-boot-starter-web中包含内嵌的tomcat容器，所以直接部署在外部容器会冲突报错
     * 当然注意：要将项目放入外部tomcat容器还有其他必要修改，这里是解释，下面是具体方法：
     * 1.继承SpringBootServletInitializer类，重写configure(final SpringApplicationBuilder application)方法
     * 2.1排除<artifactId>spring-boot-starter-web</artifactId>依赖中的<artifactId>spring-boot-starter-tomcat</artifactId>依赖
     * 2.2 或者不排除，而是将这个依赖的依赖范围改为provided
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
            <scope>provided</scope>
        </dependency>
     */
}
