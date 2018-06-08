package com.example.springframework.boot.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        //添加head参数
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        //paramType - Could be header, cookie, body, query etc.
        tokenPar.name("x-access-token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("demo swagger api")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.springframework.boot.swagger.web"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //大标题
                .title("Spring Boot中使用Swagger2构建RESTFul APIs")
                //描述
                .description("demo swagger")
                //服务条款
                .termsOfServiceUrl("http://springfox.github.io/springfox/")
                //作者信息
                .contact(new Contact("lighter", "https://github.com/wangzhihaolighter", "1984800194wzh@gmail.com"))
                //版本
                .version("1.0")
                //许可证显示文字
                .license("license")
                //许可证链接
                .licenseUrl("https://github.com/spring-projects/spring-boot/tree/master/spring-boot-samples")
                .build();
    }

    @Bean
    public SecurityConfiguration securityConfiguration() {
        //可添加安全认证信息
        return SecurityConfigurationBuilder.builder().build();
    }

}