# ip2region使用

Github地址：[lionsoul2014 / ip2region](https://github.com/lionsoul2014/ip2region)
码云地址：[狮子的魂 / ip2region](https://gitee.com/lionsoul/ip2region)
Maven地址：[Home » org.lionsoul » ip2region](https://mvnrepository.com/artifact/org.lionsoul/ip2region)

## Ip2region是什么？

ip2region - 准确率99.9%的离线IP地址定位库，0.0x毫秒级查询，ip2region.db数据库只有数MB，提供了java,php,c,python,nodejs,golang,c#等查询绑定和Binary,B树,内存三种查询算法。

## 标准化的数据格式

每条ip数据段都固定了格式：

> _城市Id|国家|区域|省份|城市|ISP_

只有中国的数据精确到了城市，其他国家有部分数据只能定位到国家，后前的选项全部是0，已经包含了全部你能查到的大大小小的国家（请忽略前面的城市Id，个人项目需求）。

## 整合使用

Maven项目中引入依赖：

```xml
<dependency>
    <groupId>org.lionsoul</groupId>
    <artifactId>ip2region</artifactId>
    <version>${ip2region.version}</version>
</dependency>
```

clone项目，在`ip2region\data`目录下，找到`ip2region.db`文件，引入项目，这里放在Spring Boot项目resource目录下。

获取ip定位信息的示例Java代码如下：

```java
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpUtils {

    private static final String UNKNOWN = "unknown";

    /**
     * 获取ip地址
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String comma = ",";
        String localhost = "127.0.0.1";
        if (ip.contains(comma)) {
            ip = ip.split(",")[0];
        }
        if (localhost.equals(ip)) {
            // 获取本机真正的ip地址
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return ip;
    }

    /**
     * 根据ip获取详细地址，格式：国家|_城市Id|省份|城市|ISP_
     */
    public static String getRegionInfo(String ip) {
        DbSearcher searcher = null;
        try {
            String classPath = "ip2region.db";
            String name = "ip2region.db";
            DbConfig config = new DbConfig();
            File file = inputStreamToFile(new ClassPathResource(classPath).getInputStream(), name);
            searcher = new DbSearcher(config, file.getPath());
            Method method;
            method = searcher.getClass().getMethod("btreeSearch", String.class);
            DataBlock dataBlock;
            dataBlock = (DataBlock) method.invoke(searcher, ip);
            return dataBlock.getRegion();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (searcher != null) {
                try {
                    searcher.close();
                } catch (IOException ignored) {
                }
            }
        }
        return "";
    }

    private static File inputStreamToFile(InputStream ins, String name) throws Exception {
        File file = new File(System.getProperty("java.io.tmpdir") + File.separator + name);
        if (file.exists()) {
            return file;
        }
        OutputStream os = new FileOutputStream(file);
        int bytesRead;
        int len = 8192;
        byte[] buffer = new byte[len];
        while ((bytesRead = ins.read(buffer, 0, len)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
        return file;
    }
}
```
