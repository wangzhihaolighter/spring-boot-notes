package com.example.springframework.boot.security.swagger.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //禁用csrf（不禁用,不携带对应请求头的post请求都会403：forbidden）
                //忽略可使用:.csrf().ignoringAntMatchers()
                .csrf().disable()
                .authorizeRequests()
                // 1 指定端口无需认证
                .requestMatchers(EndpointRequest.to("health"))
                .permitAll()
                // 2 其他端口需要指定角色
                .requestMatchers(EndpointRequest.toAnyEndpoint())
                .hasRole(RoleEnum.ACTUATOR.toString())
                // 3 常见的静态资源位置向所有人开放
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .permitAll()
                // 4 指定路径需要指定角色
                .antMatchers("/swagger-ui.html")
                .hasRole(RoleEnum.DEVELOPER.toString())
                // 5 放行swagger页面相关及api
                .antMatchers("/welcome", "/user/**").permitAll()
                // 6 其他路径均需认证
                //这是一个值得注意的点，当把其他所有未配置的请求拦截后会导致swagger文档无法显示，必须将swagger相关的资源地址放行
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        /**
         * 放行swagger相关
         */
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**");

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication().passwordEncoder(new SystemPasswordEncoder())
                .withUser("admin").password("123456").roles(RoleEnum.ADMIN.toString(), RoleEnum.ACTUATOR.toString(), RoleEnum.DEVELOPER.toString()).disabled(false).and()
        ;
    }
}