package com.example.springframework.boot.security.database.config.security;

import com.example.springframework.boot.security.database.config.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    SessionRegistry sessionRegistry;
    @Autowired
    private SecurityUserService securityUserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //禁用csrf（不禁用,不携带对应请求头的post请求都会403：forbidden）
                .csrf().disable()
                //权限管理
                .authorizeRequests()
                // 1 指定端口无需认证
                .requestMatchers(EndpointRequest.to("health", "info")).permitAll()
                // 2 其他端口需要指定角色
                .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole(RoleEnum.ACTUATOR.toString())
                // 3 常见的静态资源位置向所有人开放
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                // 4 指定路径需要指定角色
                .antMatchers("/system/**").hasRole(RoleEnum.ADMIN.toString())
                .antMatchers("/swagger-ui.html").hasRole(RoleEnum.DEVELOPER.toString())
                // 5 其他路径无需认证 get/post
                .antMatchers("/**").permitAll()
                .and()
                .sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry)
                .and().and()
                .formLogin().permitAll()
                .and()
                .logout().invalidateHttpSession(true).clearAuthentication(true)
                .and()
                .httpBasic();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserService).and()
                .inMemoryAuthentication()
                //自定义密码加密及匹配方式
                .passwordEncoder(new PasswordEncoder() {
                    @Override
                    public String encode(CharSequence charSequence) {
                        return MD5Util.getEncryptedPwd(charSequence.toString());
                    }

                    @Override
                    public boolean matches(CharSequence rawPassword, String encodedPassword) {
                        return MD5Util.validPassword(rawPassword.toString(), encodedPassword);
                    }
                })
        ;
    }

    @Bean
    public SessionRegistry getSessionRegistry() {
        return new SessionRegistryImpl();
    }
}