package com.example.mybatis.plus.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import java.util.Collections;

/**
 * mybatis-plus代码生成器
 *
 * @author wangzhihao
 */
public class MybatisPlusGenerator {

  public static void main(String[] args) {
    String path = System.getProperty("user.dir");
    String javaOutPutPath = path + "/spring-boot-example/E.tools/mybatis-plus/src/main/java";
    String mapperXmlOutPutPath =
        path + "/spring-boot-example/E.tools/mybatis-plus/src/main/resources/mybatis/mapper";
    String url =
        "jdbc:h2:file:./temp/h2database_data/test_db;MODE=MYSQL";
    String username = "admin";
    String password = "123456";

    FastAutoGenerator.create(url, username, password)
        .globalConfig(
            builder ->
                builder
                    // 设置作者
                    .author("wangzhihao")
                    // 覆盖已生成文件
                    .fileOverride()
                    // 指定输出目录
                    .outputDir(javaOutPutPath))
        .packageConfig(
            builder ->
                builder
                    // 设置父包名
                    .parent("com.example.mybatis.plus.generator")
                    // 设置模块名
                    .moduleName("")
                    // 设置mapperXml生成路径
                    .pathInfo(Collections.singletonMap(OutputFile.mapperXml, mapperXmlOutPutPath)))
        .strategyConfig(
            builder -> {
              // 设置需要生成的表名
              builder
                  .addInclude("T_USER")
                  .addTablePrefix("T_")
                  .controllerBuilder()
                  .enableRestStyle()
                  .enableHyphenStyle()
                  .serviceBuilder()
                  .mapperBuilder()
                  .enableMapperAnnotation()
                  .entityBuilder()
                  .enableLombok();
            })
        // 使用Freemarker引擎模板，默认的是Velocity引擎模板
        .templateEngine(new FreemarkerTemplateEngine())
        .execute();
  }
}
