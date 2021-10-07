# springfox-swagger使用

## 资料

- swagger官网：[swagger.io](https://swagger.io/)

- springfox官网：[springfox](http://springfox.github.io/springfox/)

- springfox Github仓库：[springfox / springfox](https://github.com/springfox/springfox)

- springfox-demos Github仓库：[springfox / springfox-demos](https://github.com/springfox/springfox-demos)

- springfox Maven仓库：[Home » io.springfox](https://mvnrepository.com/artifact/io.springfox)

## swagger介绍

对于 Rest API 来说很重要的一部分内容就是文档，Swagger 为我们提供了一套通过代码和注解自动生成文档的方法，这一点对于保证 API 文档的及时性将有很大的帮助。

Swagger 是一套基于 OpenAPI 规范（OpenAPI Specification，OAS）构建的开源工具，可以帮助我们设计、构建、记录以及使用 Rest API。

OAS本身是一个API规范，它用于描述一整套API接口，包括一个接口是哪种请求方式、哪些参数、哪些header等，都会被包括在这个文件中。它在设计的时候通常是YAML格式，这种格式书写起来比较方便，而在网络中传输时又会以json形式居多，因为json的通用性比较强。

Swagger 主要包含了以下三个部分：

- Swagger Editor：基于浏览器的编辑器，我们可以使用它编写我们 OpenAPI 规范。
- Swagger UI：它会将我们编写的 OpenAPI 规范呈现为交互式的 API 文档，后文我将使用浏览器来查看并且操作我们的 Rest API。
- Swagger Codegen：它可以通过为 OpenAPI（以前称为 Swagger）规范定义的任何 API 生成服务器存根和客户端 SDK 来简化构建过程。

## springfox介绍

由于Spring的流行，Marty Pitt编写了一个基于Spring的组件swagger-springmvc，用于将swagger集成到springmvc中来，而springfox则是从这个组件发展而来。

通常SpringBoot项目整合swagger需要用到两个依赖：`springfox-swagger2`和`springfox-swagger-ui`，用于自动生成swagger文档。

- springfox-swagger2：这个组件的功能用于帮助我们自动生成描述API的json文件
- springfox-swagger-ui：就是将描述API的json文件解析出来，用一种更友好的方式呈现出来。

## SpringFox 3.0.0 发布

官方说明：

- SpringFox 3.0.0 发布了，SpringFox 的前身是 swagger-springmvc，是一个开源的 API doc 框架，可以将 Controller 的方法以文档的形式展现。
- 首先，非常感谢社区让我有动力参与这个项目。在这个版本中，在代码、注释、bug报告方面有一些非常惊人的贡献，看到人们在问题论坛上跳槽来解决问题，我感到很谦卑。它确实激励我克服“困难”，开始认真地工作。有什么更好的办法来摆脱科维德的忧郁！
- 注意：这是一个突破性的变更版本，我们已经尽可能地保持与springfox早期版本的向后兼容性。在2.9之前被弃用的api已经被积极地删除，并且标记了将在不久的将来消失的新api。所以请注意这些，并报告任何遗漏的内容。

新特性：

- Remove explicit dependencies on springfox-swagger2
- Remove any @EnableSwagger2… annotations
- Add the springfox-boot-starter dependency
- Springfox 3.x removes dependencies on guava and other 3rd party libraries (not zero dep yet! depends on spring plugin
  and open api libraries for annotations and models) so if you used guava predicates/functions those will need to
  transition to java 8 function interfaces.

此版本的亮点：

- Spring5，Webflux支持（仅支持请求映射，尚不支持功能端点）。
- Spring Integration支持（非常感谢反馈）。
- SpringBoot支持`springfox Boot starter`依赖性（零配置、自动配置支持）。
- 具有自动完成功能的文档化配置属性。
- 更好的规范兼容性与2.0。
- 支持OpenApi 3.0.3。
- 零依赖。几乎只需要spring-plugin，[swagger-core](https://github.com/swagger-api/swagger-core) ，现有的swagger2注释将继续工作并丰富openapi3.0规范。

兼容性说明：

- 需要Java 8
- 需要Spring5.x（未在早期版本中测试）
- 需要SpringBoot 2.2+（未在早期版本中测试）

注意：

应用主类增加注解`@EnableOpenApi`，删除之前版本的SwaggerConfig.java。

启动项目，访问地址：`http://localhost:8080/swagger-ui/index.html`，注意2.x版本中访问的地址的为`http://localhost:8080/swagger-ui.html`

## 整合使用

Maven项目中引入`springfox-boot-starter`依赖：

```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
```

使用@EnableOpenApi注解，启用swagger配置

```java
@EnableOpenApi
@Configuration
public class SwaggerConfiguration {

}
```

application.yml配置

```yaml
spring:
  application:
    name: springfox-swagger
server:
  port: 8080

# ===== 自定义swagger配置 ===== #
swagger:
  enable: true
  application-name: ${spring.application.name}
  application-version: 1.0
  application-description: springfox swagger 3.0整合Demo
  try-host: http://localhost:${server.port}
```

自定义swagger配置类SwaggerProperties

```java
@Component
@ConfigurationProperties("swagger")
public class SwaggerProperties {
    /**
     * 是否开启swagger，生产环境一般关闭，所以这里定义一个变量
     */
    private Boolean enable;

    /**
     * 项目应用名
     */
    private String applicationName;

    /**
     * 项目版本信息
     */
    private String applicationVersion;

    /**
     * 项目描述信息
     */
    private String applicationDescription;

    /**
     * 接口调试地址
     */
    private String tryHost;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public void setApplicationVersion(String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    public String getApplicationDescription() {
        return applicationDescription;
    }

    public void setApplicationDescription(String applicationDescription) {
        this.applicationDescription = applicationDescription;
    }

    public String getTryHost() {
        return tryHost;
    }

    public void setTryHost(String tryHost) {
        this.tryHost = tryHost;
    }
}
```

一个完整详细的springfox swagger配置示例：

```java
import io.swagger.models.auth.In;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.boot.SpringBootVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.lang.reflect.Field;
import java.util.*;

@EnableOpenApi
@Configuration
public class SwaggerConfiguration implements WebMvcConfigurer {
    private final SwaggerProperties swaggerProperties;

    public SwaggerConfiguration(SwaggerProperties swaggerProperties) {
        this.swaggerProperties = swaggerProperties;
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30).pathMapping("/")

                // 定义是否开启swagger，false为关闭，可以通过变量控制
                .enable(swaggerProperties.getEnable())

                // 将api的元信息设置为包含在json ResourceListing响应中。 
                .apiInfo(apiInfo())

                // 接口调试地址
                .host(swaggerProperties.getTryHost())

                // 选择哪些接口作为swagger的doc发布
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()

                // 支持的通讯协议集合
                .protocols(newHashSet("https", "http"))

                // 授权信息设置，必要的header token等认证信息
                .securitySchemes(securitySchemes())

                // 授权信息全局应用
                .securityContexts(securityContexts());
    }

    /**
     * API 页面上半部分展示信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(swaggerProperties.getApplicationName() + " Api Doc")
                .description(swaggerProperties.getApplicationDescription())
                .contact(new Contact("lighter", null, "123456@gmail.com"))
                .version("Application Version: " + swaggerProperties.getApplicationVersion() + ", Spring Boot Version: " + SpringBootVersion.getVersion())
                .build();
    }

    /**
     * 设置授权信息
     */
    private List<SecurityScheme> securitySchemes() {
        ApiKey apiKey = new ApiKey("BASE_TOKEN", "token", In.HEADER.toValue());
        return Collections.singletonList(apiKey);
    }

    /**
     * 授权信息全局应用
     */
    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(
                SecurityContext.builder()
                        .securityReferences(Collections.singletonList(new SecurityReference("BASE_TOKEN", new AuthorizationScope[]{new AuthorizationScope("global", "")})))
                        .build()
        );
    }

    @SafeVarargs
    private final <T> Set<T> newHashSet(T... ts) {
        if (ts.length > 0) {
            return new LinkedHashSet<>(Arrays.asList(ts));
        }
        return null;
    }

    /**
     * 通用拦截器排除swagger设置，所有拦截器都会自动加swagger相关的资源排除信息
     */
    @SuppressWarnings("unchecked")
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        try {
            Field registrationsField = FieldUtils.getField(InterceptorRegistry.class, "registrations", true);
            List<InterceptorRegistration> registrations = (List<InterceptorRegistration>) ReflectionUtils.getField(registrationsField, registry);
            if (registrations != null) {
                for (InterceptorRegistration interceptorRegistration : registrations) {
                    interceptorRegistration
                            .excludePathPatterns("/swagger**/**")
                            .excludePathPatterns("/webjars/**")
                            .excludePathPatterns("/v3/**")
                            .excludePathPatterns("/doc.html");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
```

## 一些常用注解说明

- @Api：用在controller类，描述API接口
- @ApiOperation：描述接口方法
- @ApiModel：描述对象
- @ApiModelProperty：描述对象属性
- @ApiImplicitParams：描述接口参数
- @ApiResponses：描述接口响应
- @ApiIgnore：忽略接口方法

## 参考

- [在 Spring Boot 项目中使用 Swagger 文档](https://www.ibm.com/developerworks/cn/java/j-using-swagger-in-a-spring-boot-project/index.html)
- [Springfox 3.0.0（包含springfox-swagger2-3.0.0）即OpenAPI 3的发布与系统集成](https://blog.csdn.net/lilinhai548/article/details/107394670)
