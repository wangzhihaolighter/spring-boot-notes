# Internationalization

Spring Boot国际化信息(i18n)示例。

## 资料

- Spring Boot 官方文档：[boot-features-internationalization](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-internationalization)

## 默认国际化配置

SpringBoot提供了自动配置类`org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration`。

可以看到自动配置类中提供的可配置参数为`spring.messages`，其中典型配置为：

```properties
#默认配置国际化文件路径
spring.messages.basename=messages
```

默认将读取`resources`名为`messages`的Resource Bundle文件。

如果要使用该自动化配置的国际化资源，注入`org.springframework.context.MessageSource`实例即可。

## 自定义国际化配置

如果需要更灵活的国际化配置，可以自行指定对应的国际化资源配置，自行读取。

这里以一个动态响应错误信息的国际化示例为例。

### 创建ResultCodeMessages国际化文件

在`resources`目录下创建`i18n`文件夹，用于存放国际化文件。

在`i18n`目录下创建名为`ResultCodeMessages`的Resource Bundle文件。

ResultCodeMessages.properties

```properties
00=success
97=request param error
98=not found
99=fail. arg : {0} , {1}
```

ResultCodeMessages_en.properties

```properties
#同ResultCodeMessages.properties，这里默认Locale为ENGLISH
00=success
97=request param error
98=not found
99=fail. arg : {0} , {1}
```

ResultCodeMessages_zh_CN.properties

```properties
00=成功
97=请求入参校验出错
98=未找到
99=失败。错了，说点啥吧：{0}，{1}
```

### 创建标准响应结果类和响应码枚举类

响应码枚举类

```java
public enum ResultCodeEnum {
    //成功
    SUCCESS("00"),
    //失败
    FAIL("99"),
    //未找到
    NOT_FOUND("98"),
    //请求入参校验出错
    REQUEST_PARAM_ERROR("97");

    private final String code;

    public String getCode() {
        return code;
    }

    ResultCodeEnum(String code) {
        this.code = code;
    }

    public static ResultCodeEnum getEnum(String code) {
        for (ResultCodeEnum resultCodeEnum : ResultCodeEnum.values()) {
            if (resultCodeEnum.getCode().equals(code)) {
                return resultCodeEnum;
            }
        }
        return null;
    }

}
```

标准响应结果类

```java
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResult<T> implements Serializable {
    private String code;
    private String message;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    private T data;

    private ApiResult(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.data = data;
    }

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(ResultCodeEnum.SUCCESS.getCode(), null, data);
    }

    public static ApiResult<Void> fail(String code, String message) {
        return new ApiResult<>(code, message, null);
    }

}
```

### 自定义国际化拦截器

Spring Boot中国际化拦截器默认为`org.springframework.web.servlet.i18n.LocaleChangeInterceptor`，默认国际化参数为URL参数`locale`。

这里自定义修改国际化拦截器，默认国际化参数为URL参数（可以修改为从请求头获取），这里不做实质上的修改

```java
public class CustomizeLocaleChangeInterceptor extends LocaleChangeInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws ServletException {
        //从URL参数中获取
        String newLocale = request.getParameter(getParamName());

        //可修改成从请求头获取
        //String newLocale = request.getHeader(getParamName());

        if (newLocale != null) {
            if (checkHttpMethod(request.getMethod())) {
                LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
                if (localeResolver == null) {
                    throw new IllegalStateException(
                            "No LocaleResolver found: not in a DispatcherServlet request?");
                }
                try {
                    localeResolver.setLocale(request, response, parseLocaleValue(newLocale));
                } catch (IllegalArgumentException ex) {
                    if (isIgnoreInvalidLocale()) {
                        if (logger.isDebugEnabled()) {
                            logger.debug("Ignoring invalid locale value [" + newLocale + "]: " + ex.getMessage());
                        }
                    } else {
                        throw ex;
                    }
                }
            }
        }
        // Proceed in any case.
        return true;
    }

    private boolean checkHttpMethod(String currentMethod) {
        String[] configuredMethods = getHttpMethods();
        if (ObjectUtils.isEmpty(configuredMethods)) {
            return true;
        }
        for (String configuredMethod : configuredMethods) {
            if (configuredMethod.equalsIgnoreCase(currentMethod)) {
                return true;
            }
        }
        return false;
    }

}
```

注册自定义国际化拦截器

```java
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Resource(name = "customizeLocaleChangeInterceptor")
    LocaleChangeInterceptor localeChangeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor);
    }

}
```

### 国际化配置

i18n配置

```java
@Configuration
public class LocaleConfig {

    /**
     * 默认本地化解析器
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        //指定默认语言
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        return localeResolver;
    }

    /**
     * 默认本地化拦截器
     */
    @Bean("customizeLocaleChangeInterceptor")
    public LocaleChangeInterceptor localeChangeInterceptor() {
        CustomizeLocaleChangeInterceptor localeChangeInterceptor = new CustomizeLocaleChangeInterceptor();
        //自定义国际化参数
        localeChangeInterceptor.setParamName("language");
        return localeChangeInterceptor;
    }

    // result code : 响应码信息国际化配置

    @Bean("resultCodeMessageResource")
    public MessageSource resultCodeMessageResource() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        //指定国际化的Resource Bundle地址
        resourceBundleMessageSource.setBasename("i18n/ResultCodeMessages");
        //指定国际化的默认编码
        resourceBundleMessageSource.setDefaultEncoding("UTF-8");
        return resourceBundleMessageSource;
    }

    @Bean("resultCodeLocaleMessage")
    public LocaleMessage resultCodeLocaleMessage() {
        return new LocaleMessage(resultCodeMessageResource());
    }

}
```

国际化资源工具类

```java
public class LocaleMessage {

    private final MessageSource messageSource;

    public LocaleMessage(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String code) {
        return getMessage(code, new Object[]{});
    }

    public String getMessage(String code, String defaultMessage) {
        return getMessage(code, new Object[]{}, defaultMessage);
    }

    public String getMessage(String code, String defaultMessage, Locale locale) {
        return getMessage(code, new Object[]{}, defaultMessage, locale);
    }

    public String getMessage(String code, Locale locale) {
        return getMessage(code, new Object[]{}, "", locale);
    }

    public String getMessage(String code, Object[] args) {
        return getMessage(code, args, "");
    }

    public String getMessage(String code, Object[] args, Locale locale) {
        return getMessage(code, args, "", locale);
    }

    public String getMessage(String code, Object[] args, String defaultMessage) {
        //根据应用部署的服务器系统来决定国际化
        return getMessage(code, args, defaultMessage, LocaleContextHolder.getLocale());
    }

    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return messageSource.getMessage(code, args == null ? new Object[]{} : args, defaultMessage, locale);
    }

}
```

### 全局统一异常处理

业务异常类

```java
public class BusinessException extends RuntimeException {

    private final String resultCode;

    private final Object[] args;

    public String getResultCode() {
        return resultCode;
    }

    public Object[] getArgs() {
        return args;
    }

    public BusinessException(ResultCodeEnum resultCodeEnum, Object[] args) {
        this.resultCode = resultCodeEnum.getCode();
        this.args = args;
    }

    public BusinessException(String code, Object[] args) {
        this.resultCode = code;
        this.args = args;
    }

    public static void throwMessage(String errorCode, Object[] args) {
        throw new BusinessException(errorCode, args);
    }

    public static void throwMessage(ResultCodeEnum resultCodeEnum, Object[] args) {
        throw new BusinessException(resultCodeEnum.getCode(), args);
    }

}
```

统一异常处理

```java
@Slf4j
@RestControllerAdvice
class GlobalExceptionHandler {
    @Resource(name = "resultCodeLocaleMessage")
    LocaleMessage resultCodeLocaleMessage;

    @ExceptionHandler(BindException.class)
    public ApiResult<Void> handlerBindExceptionHandler(HttpServletRequest req, final BindException e) {
        log.error(req.getServletPath() + " Bind Exception", e);
        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(";"));
        return ApiResult.fail(ResultCodeEnum.REQUEST_PARAM_ERROR.getCode(), message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult<Void> handlerMethodArgumentNotValidException(HttpServletRequest req, final MethodArgumentNotValidException e) {
        log.error(req.getServletPath() + " MethodArgumentNotValid Exception", e);
        String message = e.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(";"));
        return ApiResult.fail(ResultCodeEnum.REQUEST_PARAM_ERROR.getCode(), message);
    }

    @ExceptionHandler(BusinessException.class)
    public ApiResult<Void> handlerBusinessException(HttpServletRequest req, final BusinessException e) {
        log.error(req.getServletPath() + " Business Exception", e);
        return ApiResult.fail(e.getResultCode(), resultCodeLocaleMessage.getMessage(e.getResultCode(), e.getArgs(), RequestContextUtils.getLocale(req)));
    }

}
```

### 请求示例

```java
    @GetMapping("/success")
    public ApiResult<String> success() {
        return ApiResult.success("success");
    }

    @GetMapping("/fail")
    public void fail(@RequestParam(value = "code", required = false, defaultValue = "99") String code,
                     @RequestParam(value = "content", required = false, defaultValue = "错了吧，说点啥吧") List<String> contentList) {
        BusinessException.throwMessage(code, contentList.toArray());
    }
```

http测试示例

```http
### 测试成功响应
GET http://localhost:8080/success HTTP/1.1

### 测试中文，响应码99
GET http://localhost:8080/fail?language=zh_CN&code=99&content=我没错&content=是系统的错 HTTP/1.1

### 测试英文，响应码99
GET http://localhost:8080/fail?language=en&code=99&content=I'm not wrong&content=It's the system's fault HTTP/1.1
```

## 参考

- [SpringBoot项目国际化](https://www.jianshu.com/p/4d5f16f6ab82)
