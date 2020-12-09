package com.example.kaptcha.autoconfigure;

import com.google.code.kaptcha.Constants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * Kaptcha配置属性类
 */
@Data
@ConfigurationProperties(prefix = KaptchaProperties.PREFIX)
public class KaptchaProperties {
    /**
     * 配置属性前缀
     */
    public static final String PREFIX = "kaptcha";

    /**
     * 用于标记是否自动化配置
     */
    public static final String ENABLE_KEY = "enable";

    /**
     * enabled config Kaptcha
     */
    private boolean enable = true;

    /**
     * 会话
     */
    @NestedConfigurationProperty
    private Session session = new Session();
    /**
     * 边框
     */
    @NestedConfigurationProperty
    private Border border = new Border();
    /**
     * 干扰
     */
    @NestedConfigurationProperty
    private Noise noise = new Noise();
    /**
     * 图片样式
     */
    @NestedConfigurationProperty
    private Obscurificator obscurificator = new Obscurificator();
    /**
     * 图片生产
     */
    @NestedConfigurationProperty
    private Producer producer = new Producer();
    /**
     * 文本生产
     */
    @NestedConfigurationProperty
    private TextProducer textProducer = new TextProducer();
    /**
     * 文字渲染器
     */
    @NestedConfigurationProperty
    private WordRenderer wordRenderer = new WordRenderer();
    /**
     * 背景
     */
    @NestedConfigurationProperty
    private Background background = new Background();
    /**
     * 图片
     */
    @NestedConfigurationProperty
    private Image image = new Image();

    /**
     * 会话
     */
    @Data
    static class Session {

        /**
         * session key
         */
        private String configKey = Constants.KAPTCHA_SESSION_KEY;
        /**
         * session date
         */
        private String configDate = Constants.KAPTCHA_SESSION_DATE;

    }

    /**
     * 边框
     */
    @Data
    static class Border {

        /**
         * 边框是否开启
         */
        private Boolean enabled = true;
        /**
         * 边框颜色，合法值： r,g,b (and optional alpha) 或者 white,black,blue.
         */
        private String color = "black";
        /**
         * 边框厚度，合法值：>0
         */
        private Integer thickness = 1;

    }

    /**
     * 干扰
     */
    @Data
    static class Noise {

        /**
         * 干扰颜色，合法值： r,g,b 或者 white,black,blue.
         */
        private String color = "black";
        /**
         * 干扰实现类
         */
        private String impl = "com.google.code.kaptcha.impl.DefaultNoise";

    }

    /**
     * 图片样式
     */
    @Data
    static class Obscurificator {

        /**
         * 实现类
         * 水纹 {@link com.google.code.kaptcha.impl.WaterRipple}
         * 鱼眼 {@link com.google.code.kaptcha.impl.FishEyeGimpy}
         * 阴影 {@link com.google.code.kaptcha.impl.ShadowGimpy}
         */
        private String impl = "com.google.code.kaptcha.impl.WaterRipple";

    }

    /**
     * 图片生产
     */
    @Data
    static class Producer {

        /**
         * 实现类
         */
        private String impl = "com.google.code.kaptcha.impl.DefaultKaptcha";

    }

    /**
     * 文本生产
     */
    @Data
    static class TextProducer {

        /**
         * 文本实现类
         */
        private String impl = "com.google.code.kaptcha.text.impl.DefaultTextCreator";
        /**
         * 文本集合，验证码值从此集合中获取
         */
        private String charString = "abcde2345678gfynmnpwx";
        /**
         * 文本集合，验证码值从此集合中获取
         */
        private Integer charLength = 4;
        /**
         * 字体
         */
        private String fontName = "宋体,楷体,微软雅黑";
        /**
         * 字体颜色，合法值： r,g,b  或者 white,black,blue.
         */
        private String fontColor = "black";
        /**
         * 字体大小
         */
        private Integer fontSize = 30;
        /**
         * 文字间隔
         */
        private Integer charSpace = 2;

    }

    /**
     * 文字渲染器
     */
    @Data
    static class WordRenderer {

        /**
         * 实现类
         */
        private String impl = "com.google.code.kaptcha.text.impl.DefaultWordRenderer";

    }

    /**
     * 背景
     */
    @Data
    static class Background {

        /**
         * 实现类
         */
        private String impl = "com.google.code.kaptcha.impl.DefaultBackground";
        /**
         * 背景颜色渐变，开始颜色 {@link java.awt.Color}
         */
        private String clearFrom = "lightGray";
        /**
         * 背景颜色渐变，结束颜色 {@link java.awt.Color}
         */
        private String clearTo = "white";

    }

    /**
     * 图片
     */
    @Data
    static class Image {

        /**
         * 宽度
         */
        private Integer width = 140;
        /**
         * 高度
         */
        private Integer height = 40;

    }

}