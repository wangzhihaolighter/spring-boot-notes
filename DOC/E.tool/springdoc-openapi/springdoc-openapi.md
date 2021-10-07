# springdoc-openapi使用

## 资料

- springdoc-openapi官网：[springdoc.org](https://springdoc.org/)

- springdoc-openapi Github仓库：[springdoc / springdoc-openapi](https://github.com/springdoc/springdoc-openapi)

- springdoc-openapi
  Maven仓库：[Home » org.springdoc » springdoc-openapi-ui](https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-ui)

## open api 简介

OpenApi是一个业界的 api 文档标准，一个规范。

好比java里面一个抽象的概念，即是一个抽象类，只是提供了一个api文档规范的抽象方法。

该方法目前被两大非官方实现了，一个是springfox，另一个是springdoc。

## swagger 简介

swagger 是一个 api 文档维护组织，后来成为了 Open API 标准的主要定义者，现在最新的版本为17年发布的 Swagger3（Open Api3）。

## springdoc 简介

SpringDoc也是 spring 社区维护的一个项目（非官方），帮助使用者将 swagger3 集成到 Spring 中。也是用来在 Spring 中帮助开发者生成文档，并可以轻松的在spring boot中使用。

该组织下的项目支持swagger页面Oauth2登录（Open API3的内容），相较 Springfox 来说，它的支撑时间更长，无疑是更好的选择。

springdoc-openapi的工作原理是在运行时检查应用程序，根据spring配置、类结构和各种注释推断API语义。自动生成JSON/YAML和HTML格式api中的文档。这个文档可以通过使用swaggerapi注释的注释来完成。

springdoc-openapi库支持：

- OpenAPI 3
- Spring-boot (v1 and v2)
- JSR-303，专门用于@NotNull、@Min、@Max和@Size。 Swagger-ui
- OAuth 2

## 整合使用

Maven项目中引入`springdoc-openapi-ui`依赖：

```xml
   <dependency>
      <groupId>org.springdoc</groupId>
      <artifactId>springdoc-openapi-ui</artifactId>
      <version>1.4.3</version>
   </dependency>
```

springfox和springdoc注解映射关系：

```text
@Api -> @Tag
@ApiIgnore -> @Parameter(hidden = true) or @Operation(hidden = true) or @Hidden
@ApiImplicitParam -> @Parameter
@ApiImplicitParams -> @Parameters
@ApiModel -> @Schema
@ApiModelProperty(hidden = true) -> @Schema(accessMode = READ_ONLY)
@ApiModelProperty -> @Schema
@ApiOperation(value = "foo", notes = "bar") -> @Operation(summary = "foo", description = "bar")
@ApiParam -> @Parameter
@ApiResponse(code = 404, message = "foo") -> @ApiResponse(responseCode = "404", description = "foo")
```

application.yml配置文件参考：

```yaml
spring:
  application:
    name: springdoc-openapi
server:
  port: 8080

# ===== SpringDoc配置 =====#
springdoc:
  swagger-ui:
    # 自定义的文档界面访问路径。默认访问路径是/swagger-ui.html
    path: /springdoc/docs.html

    # 字符串类型，一共三个值来控制操作和标记的默认展开设置。它可以是“list”（仅展开标记）、“full”（展开标记和操作）或“none”（不展开任何内容）。
    docExpansion: none

    # 布尔值。控制“试用”请求的请求持续时间（毫秒）的显示。
    displayRequestDuration: true

    # 布尔值。控制供应商扩展（x-）字段和操作、参数和架构值的显示。
    showExtensions: true

    # 布尔值。控制参数的扩展名（pattern、maxLength、minLength、maximum、minminimum）字段和值的显示。
    showCommonExtensions: true

    # 布尔值。禁用swagger用户界面默认petstore url。（从v1.4.1开始提供）。
    disable-swagger-default-url: true

  api-docs:
    # enabled the /v3/api-docs endpoint
    enabled: true

    # 自定义的文档api元数据访问路径。默认访问路径是/v3/api-docs
    path: /springdoc/api-docs

    # 布尔值。在@Schema（名称name、标题title和说明description，三个属性）上启用属性解析程序。
    resolve-schema-properties: true

  # 布尔值。实现OpenApi规范的打印。
  writer-with-default-pretty-printer: true

# ===== swagger配置 =====#
swagger:
  application-name: ${spring.application.name}
  application-version: 1.0
  application-description: springdoc openapi整合Demo
  try-host: http://localhost:${server.port}
```

swagger配置类：

```java
@Component
@ConfigurationProperties("swagger")
public class SwaggerProperties {
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

一个完整详细的springdoc openapi配置示例：

```java
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.boot.SpringBootVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class SpringdocOpenapiConfiguration implements WebMvcConfigurer {

    private final SwaggerProperties swaggerProperties;

    public SpringdocOpenapiConfiguration(SwaggerProperties swaggerProperties) {
        this.swaggerProperties = swaggerProperties;
    }

    @Bean
    public OpenAPI springDocOpenAPI() {
        //配置认证、请求头参数
        Components components = new Components();
        Map<String, Object> myHeader2extensions = new HashMap<>(2);
        myHeader2extensions.put("name", "myHeader2");
        components
                .addSecuritySchemes("bearer-key", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))
                .addSecuritySchemes("basicScheme", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic"))
                .addParameters("myHeader1", new Parameter().in("header").schema(new StringSchema()).name("myHeader1"))
                //注：这种方式有问题，不推荐
                .addHeaders("myHeader2", new Header().description("myHeader2 header").schema(new StringSchema()).extensions(myHeader2extensions))
                .addParameters("myGlobalHeader", new HeaderParameter().required(true).name("My-Global-Header").description("My Global Header").schema(new StringSchema()).required(false))
        ;

        // 接口调试路径
        Server tryServer = new Server();
        tryServer.setUrl(swaggerProperties.getTryHost());

        return new OpenAPI()
                .components(components)
                .servers(Collections.singletonList(tryServer))
                .info(new Info()
                        .title(swaggerProperties.getApplicationName() + " Api Doc")
                        .description(swaggerProperties.getApplicationDescription())
                        .version("Application Version: " + swaggerProperties.getApplicationVersion() + "\n Spring Boot Version: " + SpringBootVersion.getVersion())
                        .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0.html"))
                )
                .externalDocs(new ExternalDocumentation()
                        .description("SpringDoc Full Documentation")
                        .url("https://springdoc.org/")
                );
    }

    /**
     * 添加全局的请求头参数
     */
    @Bean
    public OpenApiCustomiser customerGlobalHeaderOpenApiCustomiser() {
        return openApi -> openApi.getPaths().values().stream().flatMap(pathItem -> pathItem.readOperations().stream())
                .forEach(operation -> {
                    operation.addParametersItem(new HeaderParameter().$ref("#/components/parameters/myGlobalHeader"));
                });
    }

    /**
     * 通用拦截器排除设置，所有拦截器都会自动加springdoc-opapi相关的资源排除信息，不用在应用程序自身拦截器定义的地方去添加，算是良心解耦实现。
     */
    @SuppressWarnings("unchecked")
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        try {
            Field registrationsField = FieldUtils.getField(InterceptorRegistry.class, "registrations", true);
            List<InterceptorRegistration> registrations = (List<InterceptorRegistration>) ReflectionUtils.getField(registrationsField, registry);
            if (registrations != null) {
                for (InterceptorRegistration interceptorRegistration : registrations) {
                    interceptorRegistration.excludePathPatterns("/springdoc**/**");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
```

## 参考

- [Springfox/swagger迁移springdoc-openapi & springdoc-openapi最新版本和springboot应用集成](https://blog.csdn.net/lilinhai548/article/details/107433510)
