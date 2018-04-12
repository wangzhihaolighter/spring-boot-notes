package com.example.springframework.boot.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.StaticResourceLocation;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                // 1
                .requestMatchers(EndpointRequest.to("health"))
                .permitAll()
                // 2
                .requestMatchers(EndpointRequest.toAnyEndpoint())
                .hasRole("ACTUATOR")
                // 3
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations().excluding(StaticResourceLocation.FAVICON))
                .permitAll()
                // 4
                .antMatchers("/**")
                .hasRole("USER")
            .and()
            .formLogin().permitAll()
            .and()
            .logout().permitAll();
        // ...additional configuration

        //1.根据@Endpoint(id="xx")判断端点,/actuator/health端点不需要认证(注意：未暴露的端口配置后依旧不能访问)。
        //2.所有其他执行器端点受该ACTUATOR角色保护。
        //3.常见的静态资源位置向所有人开放,除了/**/favicon.ico。
        //4.所有其他应用程序端点受该USER角色保护。
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication().passwordEncoder(new SystemPasswordEncoder())
            .withUser("user").password("password").roles("ACTUATOR").disabled(false).and()
            .withUser("admin").password("123456").roles("USER","ACTUATOR").disabled(false).and()
        ;
    }
}