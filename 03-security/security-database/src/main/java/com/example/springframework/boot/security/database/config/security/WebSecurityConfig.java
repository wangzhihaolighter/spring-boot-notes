package com.example.springframework.boot.security.database.config.security;

import com.example.springframework.boot.security.database.config.security.filter.SystemUsernamePasswordAuthenticationFilter;
import com.example.springframework.boot.security.database.config.security.handler.SystemAuthenticationFailureHandler;
import com.example.springframework.boot.security.database.config.security.handler.SystemAuthenticationSuccessHandler;
import com.example.springframework.boot.security.database.config.security.handler.SystemLoginUrlAuthenticationEntryPoint;
import com.example.springframework.boot.security.database.config.security.handler.SystemLogoutSuccessHandler;
import com.example.springframework.boot.security.database.config.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    SessionRegistry sessionRegistry;
    @Autowired
    private SecurityUserService securityUserService;

    /**
     * 页面形式
     */
    //@Override
    //protected void configure(HttpSecurity http) throws Exception {
    //    http
    //            //禁用csrf（不禁用,不携带对应请求头的post请求都会403：forbidden）
    //            //.csrf().ignoringAntMatchers().and()
    //            .csrf().disable()
    //            .authorizeRequests()
    //            //这里放行需要自定义校验的路径，未放行直接需要登录认证
    //            .antMatchers("/**").permitAll()
    //            .anyRequest().authenticated()
    //            .and()
    //            .sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry)
    //            .and().and()
    //            .formLogin()
    //            .failureUrl("/login?error").permitAll()
    //            .and()
    //            .logout().invalidateHttpSession(true).clearAuthentication(true)
    //            .and()
    //            .httpBasic();
    //}

    /**
     * 不跳转登录页，而是提示用户登录
     *
     * @return
     */
    @Bean
    public AuthenticationEntryPoint systemLoginUrlAuthenticationEntryPoint() {
        return new SystemLoginUrlAuthenticationEntryPoint("/login");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * json登录
     */
    @Bean
    public UsernamePasswordAuthenticationFilter systemUsernamePasswordAuthenticationFilter() throws Exception {
        SystemUsernamePasswordAuthenticationFilter authenticationFilter = new SystemUsernamePasswordAuthenticationFilter();
        //注意：一定要配置
        authenticationFilter.setAuthenticationManager(authenticationManagerBean());
        //自定义登录成功处理：登录成功，返回登录成功json信息
        authenticationFilter.setAuthenticationSuccessHandler(systemAuthenticationSuccessHandler());
        //自定义登录失败处理：登录失败，返回登录失败json信息
        authenticationFilter.setAuthenticationFailureHandler(systemAuthenticationFailureHandler());
        return authenticationFilter;
    }

    /**
     * 登录成功
     */
    @Bean
    public AuthenticationSuccessHandler systemAuthenticationSuccessHandler() {
        return new SystemAuthenticationSuccessHandler();
    }

    /**
     * 登录失败
     */
    @Bean
    public AuthenticationFailureHandler systemAuthenticationFailureHandler() {
        return new SystemAuthenticationFailureHandler();
    }

    /**
     * 登出成功
     */
    @Bean
    public LogoutSuccessHandler systemLogoutSuccessHandler() {
        return new SystemLogoutSuccessHandler();
    }

    /**
     * json形式
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //禁用csrf（不禁用,不携带对应请求头的post请求都会403：forbidden）
                //.csrf().ignoringAntMatchers().and()
                .csrf().disable()
                .authorizeRequests()
                //这里放行需要自定义校验的路径，未放行直接需要登录认证
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry)
                .and().and()
                .formLogin().permitAll()
                .and()
                .logout().logoutSuccessHandler(systemLogoutSuccessHandler())
                .invalidateHttpSession(true).clearAuthentication(true)
                .and()
                //实现认证时不跳转登录页而是提示需要认证的json信息
                .exceptionHandling().authenticationEntryPoint(systemLoginUrlAuthenticationEntryPoint())
                .and()
                .httpBasic();

        //可以使用json形式的用户名密码登录
        http.addFilter(systemUsernamePasswordAuthenticationFilter());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //放行swagger相关
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/webjars/**");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(securityUserService)
                //自定义密码加密及匹配方式
                .passwordEncoder(new PasswordEncoder() {
                    @Override
                    public String encode(CharSequence rawPassword) {
                        return MD5Util.getEncryptedPwd(rawPassword.toString());
                    }

                    @Override
                    public boolean matches(CharSequence rawPassword, String dbPassword) {
                        //由于自定义加密及比较方法，故上面的encode实际并无作用
                        //常规用法为：return dbPassword.equals(encode(rawPassword));
                        return MD5Util.validPassword(rawPassword.toString(), dbPassword);
                    }
                })
        ;
    }

    @Bean
    public SessionRegistry getSessionRegistry() {
        return new SessionRegistryImpl();
    }
}