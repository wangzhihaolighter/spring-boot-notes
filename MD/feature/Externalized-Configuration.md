# Spring Boot 中的配置

## 1.配置随机值

Spring Boot的配置支持使用随机值，在配置文件中以`${random.xxx}`形式添加，具体有以下几种类型：

```properties
value1=${random.long}
value2=${random.long(value,[max])}
value3=${random.int}
value4=${random.int(value,[max])}
value5=${random.uuid}
value6=${random.value}
```

## 2.属性中的占位符

在Spring Boot的配置文件中，可以引用以前定义的值。

```properties
app.name=MyApp
app.description=${app.name} is a Spring Boot application
```

## 3.多环境配置

Spring Boot中可以指定加载不同环境的环境的配置文件，通过`spring.profiles.active`属性指定加载不同环境配置文件。

不同环境配置文件使用`application-xxx.properties`或者`application-xxx.yml`形式命名即可，这里`xxx`就是环境值。

Spring Boot多环境配置支持加载多个环境配置，可以通过`spring.profiles.active[0]=xxx`或者`spring.profiles.active=xxx,yyy,zzz`形式指定加载相应环境配置。

如果多个环境中出现相同的配置属性，生效属性值的会是最后加载环境的配置中的属性值。

```properties
#方式一
spring.profiles.active[0]=default
spring.profiles.active[1]=dev
spring.profiles.active[2]=prod

#方式二
spring.profiles.active=default,dev,prod
```

## 4.获取配置文件中属性值

**方式一**：在Spring管理的Bean中使用`@Value("${xxx})`获取，这里xxx就是属性的key。

```java
import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.*;

@Component
public class MyBean {

    @Value("${name}")
    private String name;

    // ...

}
```

**方式二**：在Spring管理的Bean中注入`org.springframework.core.env.Environment`，使用环境类获取配置值。

```java
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentBean implements InitializingBean {
    @Autowired
    private Environment environment;

    @Override
    public void afterPropertiesSet() throws Exception {
        String appName = environment.getProperty("spring.application.name");
        Integer serverPort = environment.getProperty("server.port", Integer.class);
    }
}
```

## 5.多配置的YAML文档

Spring Boot中可以在一个YAML文档指定多个环境的配置信息，通过配置`spring.profiles`，指示文档配置在何种环境生效。

```yaml
server:
  address: 192.168.1.100
---
spring:
  profiles: development
server:
  address: 127.0.0.1
---
spring:
  profiles: production,eu-central
server:
  address: 192.168.1.120
```

## 6.加载`.properties`配置文件

使用`@PropertySource("classpath:xxx.properties")`指定配置文件路径。

可以使用`@ConfigurationProperties(prefix = "xxx")`指定配置属性前缀，默认前缀为空。

```java
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "my.love")
@PropertySource("classpath:config/simple.properties")
public class SimpleProperties {
    private String song;
    private String food;
    private List<String> color;
}
```

## 7.加载`.yml`配置文件

**方式一**：在Spring Boot管理的Bean中使用`YamlPropertiesFactoryBean`把yaml文件注入到系统配置中。

```java
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "my.enjoy")
public class SimpleYaml implements InitializingBean {
    private List<String> website;
    private String openSource;


    /**
     * 使用YamlPropertiesFactoryBean加载yaml配置文件
     *
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("config/simple.yml"));
        propertySourcesPlaceholderConfigurer.setProperties(yaml.getObject());
        return propertySourcesPlaceholderConfigurer;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(this.toString());
    }
}
```

**方式二**：使用`YamlMapFactoryBean`加载yaml文件为Map，自行读取

```java
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
public class SimpleYaml2 implements InitializingBean {
    private Map<String, Object> object = loadYaml();

    private static Map<String, Object> loadYaml() {
        YamlMapFactoryBean yaml = new YamlMapFactoryBean();
        yaml.setResources(new ClassPathResource("config/simple.yml"));
        return yaml.getObject();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(this.toString());
    }
}
```

**方式三**：扩展`PropertySourceFactory`，使它支持加载yaml文件

扩展`PropertySourceFactory`，实现一个加载yaml文件的配置文件工厂类

```java
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;
import java.util.List;

public class YamlPropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        List<PropertySource<?>> sources = new YamlPropertySourceLoader().load(resource.getResource().getFilename(), resource.getResource());
        return sources.get(0);
    }
}
```

在`@PropertySource(value="xxx",factory="xxx")`中指定工厂为自定义加载yaml文件的配置文件工厂类

```java
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "my.like")
@PropertySource(value = "classpath:config/simple.yml", factory = YamlPropertySourceFactory.class)
public class SimpleYaml3 implements InitializingBean {
    private String food;
    private List<String> pc;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(this.toString());
    }
}
```

## 8.为配置文件中的自定义属性增加注释

在IDEA中，经常使用自定义属性时会出现警告，并且没有注释，就像下图：

![示例](/IMG/externalized-configuration/01.png)

可以在 `src/main/resources/META-INF` 路径创建additional-spring-configuration-metadata.json属性元文件，为该属性值添加说明：

![示例](/IMG/externalized-configuration/02.png)

文件内容：

```json
{
  "properties": [
    {
      "name": "alone.properties",
      "type": "java.lang.String",
      "description": "A lonely attribute,We annotate him.",
      "defaultValue": "alone"
    }
  ]
}
```

查看效果：

![示例](/IMG/externalized-configuration/03.png)

---

**一些注意事项：**

- 用ide开发时，`.properties`文件的默认编码为`GBK`，获取中文值会乱码；而`.yml`文件的默认编码为`UTF-8`，则没有中文乱码的问题。
