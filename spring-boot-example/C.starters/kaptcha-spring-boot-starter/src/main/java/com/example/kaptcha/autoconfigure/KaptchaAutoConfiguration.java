package com.example.kaptcha.autoconfigure;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import java.util.Properties;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Kaptcha自动配置类
 *
 * @author wangzhihao
 */
@AllArgsConstructor
@Configuration
@ConditionalOnClass(DefaultKaptcha.class)
@EnableConfigurationProperties(KaptchaProperties.class)
public class KaptchaAutoConfiguration {

  private final KaptchaProperties properties;

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnProperty(
      prefix = KaptchaProperties.PREFIX,
      value = KaptchaProperties.ENABLE_KEY,
      havingValue = "true")
  DefaultKaptcha defaultKaptcha() {
    Properties prop = new Properties();

    // 会话
    KaptchaProperties.Session session = properties.getSession();
    prop.setProperty(Constants.KAPTCHA_SESSION_CONFIG_KEY, session.getConfigKey());
    prop.setProperty(Constants.KAPTCHA_SESSION_CONFIG_DATE, session.getConfigDate());

    // 边框
    KaptchaProperties.Border border = properties.getBorder();
    prop.setProperty(Constants.KAPTCHA_BORDER, border.isEnabled() ? "yes" : "no");
    prop.setProperty(Constants.KAPTCHA_BORDER_COLOR, border.getColor());
    prop.setProperty(Constants.KAPTCHA_BORDER_THICKNESS, String.valueOf(border.getThickness()));

    // 干扰
    KaptchaProperties.Noise noise = properties.getNoise();
    prop.setProperty(Constants.KAPTCHA_NOISE_COLOR, noise.getColor());
    prop.setProperty(Constants.KAPTCHA_NOISE_IMPL, noise.getImpl());

    // 图片样式
    KaptchaProperties.Obscurificator obscurificator = properties.getObscurificator();
    prop.setProperty(Constants.KAPTCHA_OBSCURIFICATOR_IMPL, obscurificator.getImpl());

    // 图片生产
    KaptchaProperties.Producer producer = properties.getProducer();
    prop.setProperty(Constants.KAPTCHA_PRODUCER_IMPL, producer.getImpl());

    // 文本生产
    KaptchaProperties.TextProducer textProducer = properties.getTextProducer();
    prop.setProperty(Constants.KAPTCHA_TEXTPRODUCER_IMPL, textProducer.getImpl());
    prop.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, textProducer.getCharString());
    prop.setProperty(
        Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, String.valueOf(textProducer.getCharLength()));
    prop.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES, textProducer.getFontName());
    prop.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, textProducer.getFontColor());
    prop.setProperty(
        Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, String.valueOf(textProducer.getFontSize()));
    prop.setProperty(
        Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE, String.valueOf(textProducer.getCharSpace()));

    // 文字渲染器
    KaptchaProperties.WordRenderer wordRenderer = properties.getWordRenderer();
    prop.setProperty(Constants.KAPTCHA_WORDRENDERER_IMPL, wordRenderer.getImpl());

    // 背景
    KaptchaProperties.Background background = properties.getBackground();
    prop.setProperty(Constants.KAPTCHA_BACKGROUND_IMPL, background.getImpl());
    prop.setProperty(Constants.KAPTCHA_BACKGROUND_CLR_FROM, background.getClearFrom());
    prop.setProperty(Constants.KAPTCHA_BACKGROUND_CLR_TO, background.getClearTo());

    // 图片
    KaptchaProperties.Image image = properties.getImage();
    prop.setProperty(Constants.KAPTCHA_IMAGE_WIDTH, String.valueOf(image.getWidth()));
    prop.setProperty(Constants.KAPTCHA_IMAGE_HEIGHT, String.valueOf(image.getHeight()));

    DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
    defaultKaptcha.setConfig(new Config(prop));

    return defaultKaptcha;
  }
}
