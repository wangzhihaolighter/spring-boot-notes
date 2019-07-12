package example.bean;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@ToString
@Component
@ConfigurationProperties(prefix = "my.love")
@PropertySource("classpath:config/simple.properties")
public class SimpleProperties implements InitializingBean {
    private String song;
    private String food;
    private List<String> color;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(this.toString());
    }
}
