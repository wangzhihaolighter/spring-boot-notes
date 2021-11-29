package com.example;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.configuration.WrapAutoConfiguration;
import com.example.service.WrapService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

/** 测试自动化配置 */
@SpringBootTest
public class WrapAutoConfigurationApplicationTests {
  private final ApplicationContextRunner contextRunner =
      new ApplicationContextRunner()
          .withConfiguration(AutoConfigurations.of(WrapAutoConfiguration.class));

  @Test
  public void serviceNameCanBeConfigured() {
    this.contextRunner
        .withPropertyValues("wrap.enabled=true")
        .run(
            (context) -> {
              assertThat(context).hasSingleBean(WrapService.class);
            });
  }
}
