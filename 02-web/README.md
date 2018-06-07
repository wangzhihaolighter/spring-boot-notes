## 02-web:web业务开发
#### restful:构建简单的RESTFul API
1. web mvc
1. web flux
1. test

#### mvc:spring mvc的基本用法
1. 映射请求
1. 请求方式
1. 接收数据及数据绑定
1. 返回数据
1. 文件处理

#### freemarker:使用Freemarker模板引擎渲染web视图
1. 默认文件位置
1. 错误页
1. Freemarker基础语法

#### thymeleaf:使用Thymeleaf模板引擎渲染web视图
1. 默认文件位置
1. 错误页
1. Thymeleaf基础语法

#### jsp:使用JSP模板引擎渲染web视图
1. 依赖
1. 文件位置及配置
3. 注意事项

#### swagger:使用Swagger2构建RESTFul API
1. 依赖与基本配置
1. 常用注解

#### exception:统一异常处理
1. 统一异常处理配置 - rest:`@RestControllerAdvice` / html:`@ControllerAdvice`
1. 自定义错误类型、枚举

#### validation:使用校验框架validation校验
1. 校验注解说明
1. 分组校验
1. 自定义校验
1. 统一错误处理

#### HTTP Encryption:HTTP通信加解密
1. `RequestBodyAdvice`接口:实现后注入，可对请求参数预处理
1. `ResponseBodyAdvice`接口：实现后注入，可对响应参数进行预处理
1. 参考链接：https://blog.csdn.net/lanmo555/article/details/77059879
1. 思路
    1. 客户端请求需要加解密的api接口 - 指定解密方法,按解密方法解密；未指定，则视作明文
    1. 服务端响应内容加密 - 响应头中携带加密方法，密钥由客户端认证获取