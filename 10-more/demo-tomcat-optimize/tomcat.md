### Tomcat的优化
分成两块：
1. Tomcat启动命令行中的优化参数即JVM优化
2. Tomcat容器自身参数的优化（这块很像ApacheHttp Server）

#### spring boot tomcat 配置
1. server.tomcat.max-threads = 0 ＃最大工作线程数。
1. server.tomcat.max-connections = 0 #最大连接数
1. server.tomcat.min-spare-threads = 100 #最小
1. server.tomcat.accesslog.directory = logs ＃创建日志文件的目录。可以相对于tomcat的base dir或绝对。
1. server.tomcat.accesslog.enabled = false ＃启用访问日志。
1. server.tomcat.accesslog.pattern = common ＃访问日志的格式模式。
1. server.tomcat.accesslog.prefix = access_log ＃日志文件名前缀。
1. server.tomcat.accesslog.suffix = .log ＃日志文件名后缀。
1. server.tomcat.background-processor-delay = 30 ＃调用backgroundProcess方法之间的延迟（以秒为单位）。
1. server.tomcat.basedir = ＃Tomcat基目录。如果未指定，将使用临时目录。
1. server.tomcat.internal-proxies = ＃正则表达式匹配可信IP地址。
1. server.tomcat.max-http-header-size = 0 ＃HTTP消息头的最大大小（以字节为单位）。
1. server.tomcat.port-header = X-Forwarded-Port ＃用于覆盖原始端口值的HTTP头的名称。
1. server.tomcat.protocol-header =＃保存传入协议的头，通常命名为“X-Forwarded-Proto”。
1. server.tomcat.protocol-header-https-value = https ＃指示入局请求使用SSL的协议头的值。
1. server.tomcat.remote-ip-header = ＃从中提取远程ip的http标头的名称。例如`X-FORWARDED-FOR`
1. server.tomcat.uri-encoding = UTF-8 ＃用于解码URI的字符编码。

#### jvm配置
-Xms：Java虚拟机初始化时堆的最小内存，一般与 Xmx配置为相同值，这样的好处是GC不必再为扩展内存空间而消耗性能；
-Xmx：Java虚拟机可使用堆的最大内存；
-XX:PermSize：Java虚拟机永久代大小；
-XX:MaxPermSize：Java虚拟机永久代大小最大值；

#### 一些须知
1. 默认情况下 Tomcat只支持200线程访问，超过这个数量的连接将被等待甚至超时放弃，所以我们需要提高这方面的处理能力
2. 可以通过以下几种方式来获取 maxThreads的最佳值：
> （1）通过线上系统不断使用和用户的不断增长来进行性能测试，观察QPS，响应时间，这种方式会在爆发式增长时系统崩溃，如双12等。<br>
> （2）根据公式计算，服务器端最佳线程数量=((线程等待时间+线程cpu时间)/线程cpu时间) * cpu数量，这种方式有时会被误导，因为某些系统处理环节可能会耗时比较长，从而影响公式的结果。<br>
> （3）单、多用户压力测试，查看CPU的消耗，然后直接乘以百分比，再进行压测，一般这个值的附近应该就是最佳线程数量，这种方式理想场景比较适用，实际情况会比这个复杂的多。<br>
> （4）根据系统的自身情况调整，如硬件限制，系统限制，程序处理能力限制等。<br>
> （5）定期修改为不同的 maxThreads值，看服务器响应结果及用户反应。<br>

##### Tomcat启动行参数的优化
1. -server
我不管你什么理由，只要你的tomcat是运行在生产环境中的，这个参数必须给我加上
因为tomcat默认是以一种叫java –client的模式来运行的，server即意味着你的tomcat是以真实的production的模式在运行的，这也就意味着你的tomcat以 server模式运行时将拥有：更大、更高的并发处理能力，更快更强捷的JVM垃圾回收机制，可以获得更多的负载与吞吐量
1. -Xms–Xmx
即JVM内存设置了，把Xms与Xmx两个值设成一样是最优的做法，有人说Xms为最小值，Xmx为最大值不是挺好的，这样设置还比较人性化，科学化。人性？科学？你个头啊。
大家想一下这样的场景：
一个系统随着并发数越来越高，它的内存使用情况逐步上升，上升到最高点不能上升了，开始回落，你们不要认为这个回落就是好事情，由其是大起大落，在内存回落 时它付出的代价是CPU高速开始运转进行垃圾回收，此时严重的甚至会造成你的系统出现“卡壳”就是你在好好的操作，突然网页像死在那边一样几秒甚至十几秒 时间，因为JVM正在进行垃圾回收。
因此一开始我们就把这两个设成一样，使得Tomcat在启动时就为最大化参数充分利用系统的效率，这个道理和jdbcconnection pool里的minpool size与maxpool size的需要设成一个数量是一样的原理。
如何知道我的JVM能够使用最大值啊？拍脑袋？不行！
在设这个最大内存即Xmx值时请先打开一个命令行，键入如下的命令：java -Xmx2048m -version
看，能够正常显示JDK的版本信息，说明，这个值你能够用。不是说32位系统下最高能够使用2GB内存吗？即：2048m，我们不防来试试
可以吗？不可以！不要说2048m呢，我们小一点，试试1700m如何
嘿嘿，连1700m都不可以，更不要说2048m了呢，2048m只是一个理论数值，这样说吧我这边有几台机器，有的机器-Xmx1800都没问题，有的机器最高只能到-Xmx1500m。
因此在设这个-Xms与-Xmx值时一定一定记得先这样测试一下，要不然直接加在tomcat启动命令行中你的tomcat就再也起不来了，要飞是飞不了，直接成了一只瘟猫了。
1. –Xmn
设置年轻代大小为512m。整个堆大小=年轻代大小 + 年老代大小 + 持久代大小。持久代一般固定大小为64m，所以增大年轻代后，将会减小年老代大小。此值对系统性能影响较大，Sun官方推荐配置为整个堆的3/8。
1. -Xss
是指设定每个线程的堆栈大小。这个就要依据你的程序，看一个线程 大约需要占用多少内存，可能会有多少线程同时运行等。一般不易设置超过1M，要不然容易出现out ofmemory。
1. -XX:+AggressiveOpts
作用如其名（aggressive），启用这个参数，则每当JDK版本升级时，你的JVM都会使用最新加入的优化技术（如果有的话）
1. -XX:+UseBiasedLocking
启用一个优化了的线程锁，我们知道在我们的appserver，每个http请求就是一个线程，有的请求短有的请求长，就会有请求排队的现象，甚至还会出现线程阻塞，这个优化了的线程锁使得你的appserver内对线程处理自动进行最优调配。
1. -XX:PermSize=128M-XX:MaxPermSize=256M
JVM使用-XX:PermSize设置非堆内存初始值，默认是物理内存的1/64；
在数据量的很大的文件导出时，一定要把这两个值设置上，否则会出现内存溢出的错误。
由XX:MaxPermSize设置最大非堆内存的大小，默认是物理内存的1/4。
那么，如果是物理内存4GB，那么64分之一就是64MB，这就是PermSize默认值，也就是永生代内存初始大小；
四分之一是1024MB，这就是MaxPermSize默认大小。
1. -XX:+DisableExplicitGC
在程序代码中不允许有显示的调用”System.gc()”。看到过有两个极品工程中每次在DAO操作结束时手动调用System.gc()一下，觉得这样 做好像能够解决它们的out ofmemory问题一样，付出的代价就是系统响应时间严重降低，就和我在关于Xms,Xmx里的解释的原理一样，这样去调用GC导致系统的JVM大起大 落，性能不到什么地方去哟！
1. -XX:+UseParNewGC
对年轻代采用多线程并行回收，这样收得快。
1. -XX:+UseConcMarkSweepGC
即CMS gc，这一特性只有jdk1.5即后续版本才具有的功能，它使用的是gc估算触发和heap占用触发。
我们知道频频繁的GC会造面JVM的大起大落从而影响到系统的效率，因此使用了CMS GC后可以在GC次数增多的情况下，每次GC的响应时间却很短，比如说使用了CMS GC后经过jprofiler的观察，GC被触发次数非常多，而每次GC耗时仅为几毫秒。
1. -XX:MaxTenuringThreshold
设置垃圾最大年龄。如果设置为0的话，则年轻代对象不经过Survivor区，直接进入年老代。对于年老代比较多的应用，可以提高效率。如果将此值设置为一 个较大值，则年轻代对象会在Survivor区进行多次复制，这样可以增加对象再年轻代的存活时间，增加在年轻代即被回收的概率。
这个值的设置是根据本地的jprofiler监控后得到的一个理想的值，不能一概而论原搬照抄。
1. -XX:+CMSParallelRemarkEnabled
在使用UseParNewGC 的情况下, 尽量减少 mark 的时间
1. -XX:+UseCMSCompactAtFullCollection
在使用concurrent gc 的情况下, 防止 memoryfragmention, 对live object 进行整理, 使 memory 碎片减少。
1. -XX:LargePageSizeInBytes
指定 Java heap的分页页面大小
1. -XX:+UseFastAccessorMethods
get,set 方法转成本地代码
1. -XX:+UseCMSInitiatingOccupancyOnly
指示只有在 oldgeneration 在使用了初始化的比例后concurrent collector 启动收集
1. -XX:CMSInitiatingOccupancyFraction=70
CMSInitiatingOccupancyFraction，这个参数设置有很大技巧，基本上满足(Xmx-Xmn)*(100- CMSInitiatingOccupancyFraction)/100>=Xmn就 不会出现promotion failed。在我的应用中Xmx是6000，Xmn是512，那么Xmx-Xmn是5488兆，也就是年老代有5488 兆，CMSInitiatingOccupancyFraction=90说明年老代到90%满的时候开始执行对年老代的并发垃圾回收（CMS），这时还 剩10%的空间是5488*10%=548兆，所以即使Xmn（也就是年轻代共512兆）里所有对象都搬到年老代里，548兆的空间也足够了，所以只要满 足上面的公式，就不会出现垃圾回收时的promotion failed；
因此这个参数的设置必须与Xmn关联在一起。
1. -Djava.awt.headless=true
这个参数一般我们都是放在最后使用的，这全参数的作用是这样的，有时我们会在我们的J2EE工程中使用一些图表工具如：jfreechart，用于在web 网页输出GIF/JPG等流，在winodws环境下，一般我们的app server在输出图形时不会碰到什么问题，但是在linux/unix环境下经常会碰到一个exception导致你在winodws开发环境下图片显示的好好可是在linux/unix下却显示不出来，因此加上这个参数以免避这样的情况出现。

##### Tomcat容器内的优化
1. URIEncoding=”UTF-8”
使得tomcat可以解析含有中文名的文件的url，真方便，不像apache里还有搞个mod_encoding，还要手工编译
1. maxSpareThreads
maxSpareThreads 的意思就是如果空闲状态的线程数多于设置的数目，则将这些线程中止，减少这个池中的线程总数。
1. minSpareThreads
最小备用线程数，tomcat启动时的初始化的线程数。
1. enableLookups
这个功效和Apache中的HostnameLookups一样，设为关闭。
1. connectionTimeout
connectionTimeout为网络连接超时时间毫秒数。
1. maxThreads
maxThreads Tomcat使用线程来处理接收的每个请求。这个值表示Tomcat可创建的最大的线程数，即最大并发数。
1. acceptCount
acceptCount是当线程数达到maxThreads后，后续请求会被放入一个等待队列，这个acceptCount是这个队列的大小，如果这个队列也满了，就直接refuse connection
1. maxProcessors与minProcessors
在 Java中线程是程序运行时的路径，是在一个程序中与其它控制线程无关的、能够独立运行的代码段。它们共享相同的地址空间。多线程帮助程序员写出CPU最 大利用率的高效程序，使空闲时间保持最低，从而接受更多的请求。
通常Windows是1000个左右，Linux是2000个左右。
1. useURIValidationHack
我们来看一下tomcat中的一段源码：
``` java
if (connector.getUseURIValidationHack()) {
    String uri = validate(request.getRequestURI());
    if (uri == null) {
        res.setStatus(400);
        res.setMessage("Invalid URI");
        throw new IOException("Invalid URI");
    } else {
        req.requestURI().setString(uri);
        // Redoing the URI decoding
        req.decodedURI().duplicate(req.requestURI());
        req.getURLDecoder().convert(req.decodedURI(), true);
    }
}
```
可以看到如果把useURIValidationHack设成"false"，可以减少它对一些url的不必要的检查从而减省开销。
1. enableLookups="false"
为了消除DNS查询对性能的影响我们可以关闭DNS查询，方式是修改server.xml文件中的enableLookups参数值。
1. disableUploadTimeout
类似于Apache中的keeyalive一样
1. 给Tomcat配置gzip压缩(HTTP压缩)功能
```
compression="on" compressionMinSize="2048"
compressableMimeType="text/html,text/xml,text/javascript,text/css,text/plain"
```
HTTP 压缩可以大大提高浏览网站的速度，它的原理是，在客户端请求网页后，从服务器端将网页文件压缩，再下载到客户端，由客户端的浏览器负责解压缩并浏览。相对 于普通的浏览过程HTML,CSS,Javascript , Text ，它可以节省40%左右的流量。更为重要的是，它可以对动态生成的，包括CGI、PHP , JSP , ASP , Servlet,SHTML等输出的网页也能进行压缩，压缩效率惊人。
> 1)compression="on" 打开压缩功能
> 2)compressionMinSize="2048" 启用压缩的输出内容大小，这里面默认为2KB
> 3)noCompressionUserAgents="gozilla, traviata" 对于以下的浏览器，不启用压缩
> 4)compressableMimeType="text/html,text/xml"　压缩类型
最后不要忘了把8443端口的地方也加上同样的配置，因为如果我们走https协议的话，我们将会用到8443端口这个段的配置，对吧？
``` xml
<connector port="8443" protocol="HTTP/1.1" <="" p="" style="word-wrap: break-word;">
    URIEncoding="UTF-8"  minSpareThreads="25" maxSpareThreads="75"
    enableLookups="false" disableUploadTimeout="true" connectionTimeout="20000"
    acceptCount="300"  maxThreads="300" maxProcessors="1000" minProcessors="5"
    useURIValidationHack="false"
    compression="on" compressionMinSize="2048"
    compressableMimeType="text/html,text/xml,text/javascript,text/css,text/plain"
    SSLEnabled="true"
    scheme="https" secure="true"
    clientAuth="false" sslProtocol="TLS"
    keystoreFile="d:/tomcat2/conf/shnlap93.jks" keystorePass="aaaaaa"
/>
```

上述这样的配置，基本上可以达到：
>系统响应时间增快<br>
>JVM回收速度增快同时又不影响系统的响应率<br>
>JVM内存最大化利用<br>
>线程阻塞情况最小化<br>