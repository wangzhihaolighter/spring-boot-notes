package com.example.springframework.boot.security.db.html.config.security;

import com.example.springframework.boot.security.db.html.config.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
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
                //.csrf().ignoringAntMatchers().and()
                .csrf().disable()
                .authorizeRequests()
                //所有路径均需认证
                //无需登录的路径需放行,放行后由自定义权限认证拦截器校验
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
                //只能登陆一次
                .sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry)
                .and().and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error").permitAll()
                .and()
                .logout().invalidateHttpSession(true).clearAuthentication(true)
                .and()
                .httpBasic();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(securityUserService)
                //自定义密码加密及匹配方式
                .passwordEncoder(new PasswordEncoder() {
                    @Override
                    public String encode(CharSequence rawPassword) {
                        return MD5Util.encode(rawPassword.toString());
                    }

                    @Override
                    public boolean matches(CharSequence rawPassword, String dbPassword) {
                        return dbPassword.equals(encode(rawPassword));
                    }
                })
        ;
    }

    @Bean
    public SessionRegistry getSessionRegistry() {
        return new SessionRegistryImpl();
    }
}