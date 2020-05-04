# UserAgentUtils使用

Github仓库地址：[blueconic / browscap-java](https://github.com/blueconic/browscap-java)

Maven仓库：[Home » com.blueconic » browscap-java](https://mvnrepository.com/artifact/com.blueconic/browscap-java)

## 介绍

该库可用于解析useragent头，以提取有关使用的浏览器、浏览器版本、平台、平台版本和设备类型的信息。确定客户端是台式机、平板电脑还是移动设备，或者确定客户端是否在Windows或Mac OS上。

## 整合使用

Maven项目中引入：

```xml
<!-- https://mvnrepository.com/artifact/com.blueconic/browscap-java -->
<dependency>
    <groupId>com.blueconic</groupId>
    <artifactId>browscap-java</artifactId>
    <version>1.2.16</version>
</dependency>
```

Java代码使用（官方示例）：

```java
import com.blueconic.browscap.BrowsCapField;
import com.blueconic.browscap.Capabilities;
import com.blueconic.browscap.ParseException;
import com.blueconic.browscap.UserAgentParser;
import com.blueconic.browscap.UserAgentService;

// ... class definition

// create a parser with the default fields
final UserAgentParser parser = new UserAgentService().loadParser(); // handle IOException and ParseException

// or create a parser with a custom defined field list
// the list of available fields can be seen inthe BrowsCapField enum
final UserAgentParser parser =
        new UserAgentService().loadParser(Arrays.asList(BrowsCapField.BROWSER, BrowsCapField.BROWSER_TYPE,
                BrowsCapField.BROWSER_MAJOR_VERSION,
                BrowsCapField.DEVICE_TYPE, BrowsCapField.PLATFORM, BrowsCapField.PLATFORM_VERSION,
                BrowsCapField.RENDERING_ENGINE_VERSION, BrowsCapField.RENDERING_ENGINE_NAME,
                BrowsCapField.PLATFORM_MAKER, BrowsCapField.RENDERING_ENGINE_MAKER));

// It's also possible to supply your own ZIP file by supplying a correct path to a ZIP file in the constructor.
// This can be used when a new BrowsCap version is released which is not yet bundled in this package.
// final UserAgentParser parser = new UserAgentService("E:\\anil\\browscap.zip").loadParser();

// parser can be re-used for multiple lookup calls
final String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36";
final Capabilities capabilities = parser.parse(userAgent);

// the default fields have getters
final String browser = capabilities.getBrowser();
final String browserType = capabilities.getBrowserType();
final String browserMajorVersion = capabilities.getBrowserMajorVersion();
final String deviceType = capabilities.getDeviceType();
final String platform = capabilities.getPlatform();
final String platformVersion = capabilities.getPlatformVersion();

// the custom defined fields are available
final String renderingEngineMaker = capabilities.getValue(BrowsCapField.RENDERING_ENGINE_MAKER);

// do something with the values
```
