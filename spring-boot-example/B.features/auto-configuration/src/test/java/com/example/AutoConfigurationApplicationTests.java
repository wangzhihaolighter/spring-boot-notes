package com.example;

import com.example.configuration.AutoConfiguration;
import com.example.service.WrapService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试自动化配置
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AutoConfigurationApplicationTests {
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(AutoConfiguration.class));

    @Test
    public void serviceNameCanBeConfigured() {
        this.contextRunner.withPropertyValues("wrap.enabled=true").run((context) -> {
            assertThat(context).hasSingleBean(WrapService.class);
        });
    }

}
