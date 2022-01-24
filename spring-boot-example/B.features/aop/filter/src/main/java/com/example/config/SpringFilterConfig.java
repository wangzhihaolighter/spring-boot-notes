package com.example.config;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ServletComponentScan
public class SpringFilterConfig {

  /*
  filter说明：
    可以通过filter改变request和response
    它不是一个servlet，不能产生一个response，它能够在一个request到达servlet之前预处理request,也可以在离开servlet时处理response

  注解说明：
    @ServletComponentScan：扫描带有@WebFilte的Filter类，让@WebFilter起作用。
    @WebFilter：标识Filter过滤匹配的路径、Filter的名称
    @Order：指定执行顺序，spring会按照order值的大小，从小到大的顺序来依次过滤
   */

}
