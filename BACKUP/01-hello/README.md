# 01-hello
说明：Spring Boot入门，一些特性的demo

## hello：从打个招呼开始
- spring-boot 2.0 hello world

## properties：Spring Boot属性配置文件
- 自定义属性与编码
- 参数间引用
- 随机数
- 多环境配置

## test：单元测试
- log打印结果
- 断言结果
- mock接口测试

## starter：自定义一个Spring Boot Starter
- starter命名格式：Spring官方建议 - 非官方Starter命名应遵循{name}-spring-boot-starter的格式
- starter中提供service,并编写AutoConfiguration自动配置类
- 使用`@AutoConfigureAfter`和`@AutoConfigureBefore`可以限制xxxAutoConfiguration.class的加载顺序
- spring-configuration-metadata.json,配置xxxAutoConfiguration需要用到的一些properties
- additional-spring-configuration-metadata.json,提供配置属性的说明、类型及默认值
- 打包安装至本地maven仓库，并使用