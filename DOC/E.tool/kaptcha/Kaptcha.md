# Kaptcha 使用

## 资料

- Kaptcha 官网：[Google Code - kaptcha](https://code.google.com/archive/p/kaptcha/)
- Kaptcha Maven
  仓库地址：[Home » com.google.code.kaptcha » kaptcha](https://mvnrepository.com/artifact/com.google.code.kaptcha/kaptcha)

## 介绍

Kaptcha 是一个基于 SimpleCaptcha 的验证码开源项目，一个可高度配置的实用验证码生成工具。

可自由配置的选项如下：

- 验证码的字体
- 验证码字体的大小
- 验证码字体的字体颜色
- 验证码内容的范围 (数字，字母，中文汉字！)
- 验证码图片的大小，边框，边框粗细，边框颜色
- 验证码的干扰线
- 验证码的样式 (鱼眼样式、3D、普通模糊、...)

## Kaptcha 可选配置

These values are stored in the `com.google.code.kaptcha.Constants` class.

| 配置项                           | 描述                                                                                                                                                                     | 默认值                                                |
| -------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----------------------------------------------------- |
| kaptcha.border                   | 图片边框，合法值：yes , no                                                                                                                                               | yes                                                   |
| kaptcha.border.color             | 边框颜色，合法值： r,g,b (and optional alpha) 或者 white,black,blue.                                                                                                     | black                                                 |
| kaptcha.image.width              | 图片宽                                                                                                                                                                   | 200                                                   |
| kaptcha.image.height             | 图片高                                                                                                                                                                   | 50                                                    |
| kaptcha.producer.impl            | 图片实现类                                                                                                                                                               | com.google.code.kaptcha.impl.DefaultKaptcha           |
| kaptcha.textproducer.impl        | 文本实现类                                                                                                                                                               | com.google.code.kaptcha.text.impl.DefaultTextCreator  |
| kaptcha.textproducer.char.string | 文本集合，验证码值从此集合中获取                                                                                                                                         | abcde2345678gfynmnpwx                                 |
| kaptcha.textproducer.char.length | 验证码长度                                                                                                                                                               | 5                                                     |
| kaptcha.textproducer.font.names  | 字体                                                                                                                                                                     | Arial, Courier                                        |
| kaptcha.textproducer.font.size   | 字体大小                                                                                                                                                                 | 40px.                                                 |
| kaptcha.textproducer.font.color  | 字体颜色，合法值： r,g,b 或者 white,black,blue.                                                                                                                          | black                                                 |
| kaptcha.textproducer.char.space  | 文字间隔                                                                                                                                                                 | 2                                                     |
| kaptcha.noise.impl               | 干扰实现类                                                                                                                                                               | com.google.code.kaptcha.impl.DefaultNoise             |
| kaptcha.noise.color              | 干扰 颜色，合法值： r,g,b 或者 white,black,blue.                                                                                                                         | black                                                 |
| kaptcha.obscurificator.impl      | 图片样式：<br /> 水纹 com.google.code.kaptcha.impl.WaterRipple <br /> 鱼眼 com.google.code.kaptcha.impl.FishEyeGimpy<br /> 阴影 com.google.code.kaptcha.impl.ShadowGimpy | com.google.code.kaptcha.impl.WaterRipple              |
| kaptcha.background.impl          | 背景实现类                                                                                                                                                               | com.google.code.kaptcha.impl.DefaultBackground        |
| kaptcha.background.clear.from    | 背景颜色渐变，开始颜色                                                                                                                                                   | light grey                                            |
| kaptcha.background.clear.to      | 背景颜色渐变， 结束颜色                                                                                                                                                  | white                                                 |
| kaptcha.word.impl                | 文字渲染器                                                                                                                                                               | com.google.code.kaptcha.text.impl.DefaultWordRenderer |
| kaptcha.session.key              | session key                                                                                                                                                              | KAPTCHA_SESSION_KEY                                   |
| kaptcha.session.date             | session date                                                                                                                                                             | KAPTCHA_SESSION_DATE                                  |

## 整合使用

由于 Google Code kaptcha 没有上传到 Maven Central，这里选择的依赖是第三方上传至 Maven Central
的副本：[penggle /kaptcha](https://github.com/penggle/kaptcha)。

```xml
<dependency>
    <groupId>com.github.penggle</groupId>
    <artifactId>kaptcha</artifactId>
    <version>2.3.2</version>
</dependency>
```

使用

```java
DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
defaultKaptcha.setConfig(new Config(prop));
String text = defaultKaptcha.createText();
BufferedImage image = defaultKaptcha.createImage(text);
```
