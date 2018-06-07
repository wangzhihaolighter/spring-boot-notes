## 01-hello
#### hello:quick start
1. spring-boot 2.0 hello world

#### properties:Spring Boot属性配置文件
1. 自定义属性与编码
1. 参数间引用
1. 随机数
1. 多环境配置

#### test:单元测试
1. log打印结果
1. 断言结果
1. mock接口测试

#### webflux:flux,函数式web框架
1. 创建handler(相当于service)
1. 注册路由

#### starter:快速开发一个自定义Spring Boot Starter，并使用它
1. starter的artifactId命名格式：Spring官方建议 - 非官方Starter命名应遵循{name}-spring-boot-starter的格式
1. starter中提供service,并编写AutoConfiguration自动配置类
1. 使用`@AutoConfigureAfter`和`@AutoConfigureBefore`可以限制xxxAutoConfiguration.class的加载顺序
1. spring-configuration-metadata.json,配置xxxAutoConfiguration需要用到的一些properties
1. additional-spring-configuration-metadata.json,提供配置属性的说明、类型及默认值
1. 打包安装至本地maven仓库，并使用