# bean-validation

Spring Boot中Bean的数据校验示例。

Bean Validation 官方：[beanvalidation.org](https://beanvalidation.org/)

hibernate validator 官方:[hibernate.org/validator/](http://hibernate.org/validator/)

## 介绍

`spring-boot-starter-validation`是Spring Boot对`hibernate validator`整合。

`hibernate validator`是 [Bean Validation](https://beanvalidation.org/) 的具体实现，它提供了一套比较完善、便捷的验证实现方式。

## Spring Boot项目中使用

项目中引入Maven依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

## javax.validation / hibernate validator常用注解

- 空检查
  - `@Null`: 验证对象是否为null
  - `@NotNull`: 验证对象是否不为null，无法查检长度为0的字符串
  - `@NotEmpty`: 检查约束元素是否为NULL或者是EMPTY
  - `@NotBlank`: 检查约束字符串是不是Null还有被Trim的长度是否大于0，只对字符串，且会去掉前后空格
  - `@AssertTrue`: 验证 Boolean 对象是否为 true
- Booelan检查
  - `@AssertTrue`: 验证 Boolean 对象是否为 true
  - `@AssertFalse`: 验证 Boolean 对象是否为 false
- 长度检查
  - `@Size(min=, max=)`: 验证对象（Array,Collection,Map,String）长度是否在给定的范围之内
  - `@Length(min=, max=)`: 验证字符串的长度是否在给定的范围之内，包含两端
- 日期检查
  - `@Past`: 验证 Date 和 Calendar 对象是否在当前时间之前
  - `@Future`: 验证 Date 和 Calendar 对象是否在当前时间之后
- 数值检查：建议使用在Stirng、Integer类型，不建议使用在int类型上，因为表单值为“”时无法转换为int，但可以转换为Stirng为""，Integer为null
  - `@Min`: 验证 Number 和 String 对象是否大等于指定的值
  - `@Max`: 验证 Number 和 String 对象是否小等于指定的值
  - `@DecimalMax`: 被标注的值必须不大于约束中指定的最大值，这个约束的参数是一个通过BigDecimal定义的最大值的字符串表示，小数存在精度
  - `@DecimalMin`: 被标注的值必须不小于约束中指定的最小值，这个约束的参数是一个通过BigDecimal定义的最小值的字符串表示，小数存在精度
  - `@Digits`: 验证 Number 和 String 的构成是否合法
  - `@Digits(integer=,fraction=)`: 验证字符串是否是符合指定格式的数字，interger指定整数精度，fraction指定小数精度。
  - `@Range(min=, max=)`: Checks whether the annotated value lies between (inclusive) the specified minimum and maximum.
- 其他
  -`@Valid`: 递归的对关联对象进行校验，如果关联对象是个集合或者数组，那么对其中的元素进行递归校验，如果是一个map，则对其中的值部分进行校验(是否进行递归验证)
  - `@Validated`: Spring Validator 校验机制使用
  - `@Email`: 验证是否是邮件地址，如果为null，不进行验证，算通过验证。
  - `@CreditCardNumber`: 信用卡验证
  - `@Pattern`: 验证 String 对象是否符合正则表达式的规则
  - `@ScriptAssert(lang= ,script=, alias=)`: 指定进行校验的方法，通过方法来进行复杂业务逻辑的校验，然后返回true或false来表明是否校验成功
  - `@URL(protocol=,host=, port=,regexp=, flags=)`: 验证字符串是否是URL

## 校验模式配置

hibernate的校验模式:

- 普通模式（默认）：校验所有的属性，然后返回所有的验证失败信息
- 快速失败返回模式：只要有一个验证失败，则返回

快速失败校验模式配置如下：

```java
    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                //设置validator模式：快速失败返回
                //方式一
                .failFast(true)
                //方式二
                //.addProperty("hibernate.validator.fail_fast", "true")
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }
```

## 国际化（i18n）

在`resources`目录下，创建`i18n`目录，用于存放国际化配置文件。

再在`i18n`目录下创建校验提示的Resource Bundle国际化配置文件`SystemValidationMessages`。

Validation国际化核心配置：

```java
    @Bean
    public LocalValidatorFactoryBean defaultValidator(@Qualifier("validationMessageResource") MessageSource messageSource ) {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        //设置提示消息国际化
        localValidatorFactoryBean.setMessageInterpolator(new ResourceBundleMessageInterpolator(new PlatformResourceBundleLocator("i18n/SystemValidationMessages")));
        return localValidatorFactoryBean;
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(Environment environment, @Lazy Validator validator) {
        MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
        MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
        boolean proxyTargetClass = environment.getProperty("spring.aop.proxy-target-class", Boolean.class, true);
        processor.setProxyTargetClass(proxyTargetClass);
        processor.setValidator(validator);
        return postProcessor;
    }
```

至于为什么要这么配置，从Validation的自动化配置类`org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration`中可以看出，Spring Boot项目中如何配置自定义的Validaiton处理器。

```java
/**
 * {@link EnableAutoConfiguration Auto-configuration} to configure the validation
 * infrastructure.
 *
 * @author Stephane Nicoll
 * @author Madhura Bhave
 * @since 1.5.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(ExecutableValidator.class)
@ConditionalOnResource(resources = "classpath:META-INF/services/javax.validation.spi.ValidationProvider")
@Import(PrimaryDefaultValidatorPostProcessor.class)
public class ValidationAutoConfiguration {

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @ConditionalOnMissingBean(Validator.class)
    public static LocalValidatorFactoryBean defaultValidator() {
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        MessageInterpolatorFactory interpolatorFactory = new MessageInterpolatorFactory();
        factoryBean.setMessageInterpolator(interpolatorFactory.getObject());
        return factoryBean;
    }

    @Bean
    @ConditionalOnMissingBean
    public static MethodValidationPostProcessor methodValidationPostProcessor(Environment environment, @Lazy Validator validator) {
        MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
        boolean proxyTargetClass = environment.getProperty("spring.aop.proxy-target-class", Boolean.class, true);
        processor.setProxyTargetClass(proxyTargetClass);
        processor.setValidator(validator);
        return processor;
    }

}
```

## 自定义验证器

一般情况，原生的校验器可以满足业务需求，但也有无法满足情况的时候，此时，可以实现validator的接口，自定义自己需要的验证器。

自定义校验器示例：校验字符串是否包含违禁词。

```java
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义校验器：校验字符串是否包含违禁词
 *
 * @author wangzhihao
 */
@Documented
@Constraint(validatedBy = {CannotHaveProhibitedWordValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Repeatable(CannotHaveProhibitedWord.List.class)
public @interface CannotHaveProhibitedWord {

    //默认错误消息，这里错误信息从国际化配置文件中获取
    String message() default "{system.validation.CannotHaveProhibitedWord.message}";

    //分组
    Class<?>[] groups() default {};

    //负载
    Class<? extends Payload>[] payload() default {};

    //指定多个时使用
    @Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        CannotHaveProhibitedWord[] value();
    }

}
```

实现`ConstraintValidator`定义该校验器的约束规则：

```java
/**
 * 规则：字符串中不能包含违禁词
 */
@Slf4j
public class CannotHaveProhibitedWordValidator implements ConstraintValidator<CannotHaveProhibitedWord, String> {

    @Override
    public void initialize(CannotHaveProhibitedWord constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //null时不进行校验
        if (value == null) {
            return true;
        }
        return !haveProhibitedWord(value);
    }

    /**
     * 是否包含违禁词
     *
     * @param value 校验字符串
     * @return 是否包含
     */
    private boolean haveProhibitedWord(String value) {
        String[] prohibitedWordArr = {"违禁词1", "违禁词2", "违禁词3"};
        for (String prohibitedWord : prohibitedWordArr) {
            if (value.contains(prohibitedWord)) {
                return true;
            }
        }
        return false;
    }
}
```

## 参考

- [Spring Boot Validator以及国际化（i18n）](https://blog.csdn.net/choimroc/article/details/101880225)
- [springboot使用hibernate validator校验](https://www.cnblogs.com/mr-yang-localhost/p/7812038.html#_labelTop)
