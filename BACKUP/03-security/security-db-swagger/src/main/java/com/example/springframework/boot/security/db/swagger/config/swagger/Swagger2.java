package com.example.springframework.boot.security.db.swagger.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket createRestApi() {
        //添加head参数
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        //paramType - Could be header, cookie, body, query etc.
        tokenPar.name("x-access-token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());

        //自定义方法响应消息
        List<ResponseMessage> responseMessages = new ArrayList<>();
        ResponseMessage responseMessage403 = new ResponseMessageBuilder()
                .code(403).message("403 message")
                .build();
        ResponseMessage responseMessage500 = new ResponseMessageBuilder()
                .code(500).message("500 message")
                .build();
        responseMessages.add(responseMessage403);
        responseMessages.add(responseMessage500);

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.springframework.boot.security.db.swagger.web"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars)
                .apiInfo(apiInfo()).useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, responseMessages)
                .securitySchemes(Collections.singletonList(securityScheme()))
                .securityContexts(Collections.singletonList(securityContext()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot中使用Swagger2构建RESTFul APIs")
                .description("demo swagger")
                .termsOfServiceUrl("http://springfox.github.io/springfox/")
                .contact(new Contact("lighter", "https://github.com/wangzhihaolighter", "1984800194wzh@gmail.com"))
                .version("1.0")
                .license("license")
                .licenseUrl("https://github.com/spring-projects/spring-boot/tree/master/spring-boot-samples")
                .build();
    }

    @Bean
    public SecurityConfiguration securityConfiguration() {
        //可添加安全认证信息
        return SecurityConfigurationBuilder.builder()
                .clientId("admin")
                .clientSecret("123456")
                .scopeSeparator(" ")
                .useBasicAuthenticationWithAccessCodeGrant(true)
                .build();
    }

    private SecurityScheme securityScheme() {
        GrantType grantType = new AuthorizationCodeGrantBuilder()
                .tokenEndpoint(new TokenEndpoint("http://localhost:8080" + "/token", "X-AUTH-TOKEN"))
                .tokenRequestEndpoint(
                        new TokenRequestEndpoint("http://localhost:8080" + "/authorize", "admin", "123456"))
                .build();

        return new OAuthBuilder().name("spring_oauth")
                .grantTypes(Collections.singletonList(grantType))
                .scopes(Arrays.asList(scopes()))
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(new SecurityReference("spring_oauth", scopes())))
                .forPaths(PathSelectors.regex("/system.*"))
                .build();
    }

    private AuthorizationScope[] scopes() {
        return new AuthorizationScope[]{
                new AuthorizationScope("read", "for read operations"),
                new AuthorizationScope("write", "for write operations"),
                new AuthorizationScope("welcome", "Access welcome API")};
    }

}