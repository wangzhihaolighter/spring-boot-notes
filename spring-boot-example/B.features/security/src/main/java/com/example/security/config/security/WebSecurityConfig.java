package com.example.security.config.security;

import com.example.security.config.security.filter.AuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableGlobalMethodSecurity(
    // 启用Spring Security的安全控制注解：@PreAuthorize @PostAuthorize
    prePostEnabled = true,
    // 启用JSR-250安全控制注解：@DenyAll @RolesAllowed @PermitAll
    jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsService;
  private final AuthenticationTokenFilter authenticationTokenFilter;
  private final AuthenticationEntryPoint authenticationEntryPoint;
  private final AccessDeniedHandler accessDeniedHandler;
  private final LogoutSuccessHandler logoutSuccessHandler;

  public WebSecurityConfig(
      UserDetailsService userDetailsService,
      AuthenticationTokenFilter authenticationTokenFilter,
      AuthenticationEntryPoint authenticationEntryPoint,
      AccessDeniedHandler accessDeniedHandler,
      LogoutSuccessHandler logoutSuccessHandler) {
    this.userDetailsService = userDetailsService;
    this.authenticationTokenFilter = authenticationTokenFilter;
    this.authenticationEntryPoint = authenticationEntryPoint;
    this.accessDeniedHandler = accessDeniedHandler;
    this.logoutSuccessHandler = logoutSuccessHandler;
  }

  /** 密码加密实现 */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * 配置参数说明
   *
   * <p>anyRequest | 匹配所有请求路径
   *
   * <p>access | SpringEl表达式结果为true时可以访问
   *
   * <p>anonymous | 匿名可以访问
   *
   * <p>denyAll | 用户不能访问
   *
   * <p>fullyAuthenticated | 用户完全认证可以访问（非remember-me下自动登录）
   *
   * <p>hasAnyAuthority | 如果有参数，参数表示权限，则其中任何一个权限可以访问
   *
   * <p>hasAnyRole | 如果有参数，参数表示角色，则其中任何一个角色可以访问
   *
   * <p>hasAuthority | 如果有参数，参数表示权限，则其权限可以访问
   *
   * <p>hasIpAddress | 如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
   *
   * <p>hasRole | 如果有参数，参数表示角色，则其角色可以访问
   *
   * <p>permitAll | 用户可以任意访问
   *
   * <p>rememberMe | 允许通过remember-me登录的用户访问
   *
   * <p>authenticated | 用户登录后可访问
   */
  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        // 禁用csrf，因为不使用session
        .csrf()
        .disable()
        // 基于token，所以不需要session
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        // 异常处理
        .exceptionHandling()
        // 认证失败处理
        .authenticationEntryPoint(authenticationEntryPoint)
        // 访问受限处理
        .accessDeniedHandler(accessDeniedHandler)
        .and()
        // 过滤请求
        .authorizeRequests()
        // 允许匿名访问
        .antMatchers("/", "/login")
        .anonymous()
        // 除上面外的所有请求全部需要鉴权认证
        .anyRequest()
        .authenticated()
        .and()
        .headers()
        .frameOptions()
        .disable();
    // 登出处理 - 使用spring security提供的登出机制
    httpSecurity.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
    // 调整过滤器执行顺序，token验证过滤器早于用户验证过滤器
    httpSecurity.addFilterBefore(
        authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
        // 用户验证处理
        .userDetailsService(userDetailsService)
        // 密码加密处理
        .passwordEncoder(passwordEncoder());
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
