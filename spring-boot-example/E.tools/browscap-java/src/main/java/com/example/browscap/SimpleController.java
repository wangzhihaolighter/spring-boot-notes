package com.example.browscap;

import com.blueconic.browscap.Capabilities;
import com.blueconic.browscap.ParseException;
import com.blueconic.browscap.UserAgentParser;
import com.blueconic.browscap.UserAgentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class SimpleController {

    private static UserAgentParser parser = null;

    static {
        try {
            parser = new UserAgentService().loadParser();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/")
    public Capabilities parseUserAgentString(HttpServletRequest request) {
        if (parser == null) {
            return null;
        }

        String userAgent = request.getHeader("User-Agent");
        final Capabilities capabilities = parser.parse(userAgent);
        final String browser = capabilities.getBrowser();
        final String browserType = capabilities.getBrowserType();
        final String browserMajorVersion = capabilities.getBrowserMajorVersion();
        final String deviceType = capabilities.getDeviceType();
        final String platform = capabilities.getPlatform();
        final String platformVersion = capabilities.getPlatformVersion();
        System.out.println("浏览器名称：" + browser);
        System.out.println("浏览器类别：" + browserType);
        System.out.println("浏览器主版本：" + browserMajorVersion);
        System.out.println("访问设备类型：" + deviceType);
        System.out.println("访问平台：" + platform);
        System.out.println("访问平台版本：" + platformVersion);
        return capabilities;
    }
}
