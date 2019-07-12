package example.bean;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 我的自定义配置属性
 */
@Data
@ToString
@Component
@ConfigurationProperties(prefix = "my")
public class MyProperties implements InitializingBean {

    private String name;
    private Integer age;
    private String description;
    private Love love;

    @Data
    @ToString
    public static class Love {
        private String area;
        private List<String> fruits;
        private List<String> songs;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(this.toString());
    }
}