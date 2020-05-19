# JSON Web Tokens

官网地址：[jwt.io](https://jwt.io/)

Java JWT库：[jwtk / jjwt](https://jwt.io/)，更多实现库可查看JWT官网

jjwt Github仓库：[jwtk / jjwt](https://github.com/jwtk/jjwt)

jjwt Maven仓库：[Home » io.jsonwebtoken » jjwt](https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt)

## 什么是JSON Web Token

JWT(Json Web Token)是实现token技术的一种解决方案，是为了在网络应用环境间传递声明而执行的一种基于JSON的开放标准（(RFC 7519)。

什么场景下使用JSON Web Token？

- **授权**：这是使用JWT的最常见方案。一旦用户登录，每个后续请求将包括JWT，从而允许用户访问该令牌允许的路由，服务和资源。单一登录是当今广泛使用JWT的一项功能，因为它的开销很小并且可以在不同的域中轻松使用。
- **信息交换**：JSON Web Token是在各方之间安全地传输信息的好方法。因为可以对JWT进行签名（例如，使用公钥/私钥对），所以您可以确定发件人是他们所说的人。另外，由于签名是使用标头和有效负载计算的，因此您还可以验证内容是否未被篡改。

## JWT 的数据结构

JWT由三部分组成，依次如下: header(头)、payload(载体)、signature(签名)。

写成一行就是下面这个样子：

```java
Header.Payload.Signature
```

![示例](../../IMG/JWT/01.jpg)

它们按照 A.B.C 的格式拼接起来，其中C由A和B生成，他们之间的格式为 Base64(header).Base64(payload).H256(A.B)。

注意：header和payload都是使用Base64URL 算法对象序列化之后的字符串。

下面依次介绍这三个部分。

### Header

Header 部分是一个 JSON 对象，描述 JWT 的元数据，通常是下面的样子：

```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```

上面代码中:

- `alg`属性表示签名的算法（algorithm），默认是 HMAC SHA256（写成 HS256）
- `typ`属性表示这个令牌（token）的类型（type），JWT 令牌统一写为JWT。

### Payload

Payload 部分也是一个 JSON 对象，用来存放实际需要传递的数据。

JWT 规定了7个官方字段，供选用：

- iss (issuer)：签发人
- exp (expiration time)：过期时间
- sub (subject)：主题
- aud (audience)：受众
- nbf (Not Before)：生效时间
- iat (Issued At)：签发时间
- jti (JWT ID)：编号

除了官方字段，你还可以在这个部分定义私有字段，下面就是一个例子。

```json
{
  "sub": "1234567890",
  "name": "John Doe",
  "admin": true
}
```

注意，JWT 默认是不加密的，任何人都可以读到，所以不要把秘密信息放在这个部分。

这个 JSON 对象也要使用 Base64URL 算法转成字符串。

### Signature

Signature 部分是对前两部分的签名，防止数据篡改。

首先，需要指定一个密钥（secret）。这个密钥只有服务器才知道，不能泄露给用户。

然后，使用 Header 里面指定的签名算法（默认是 HMAC SHA256），按照下面的公式产生签名。

```c
HMACSHA256(
  base64UrlEncode(header) + "." + base64UrlEncode(payload),
  secret
)
```

算出签名以后，把 Header、Payload、Signature 三个部分拼成一个字符串，每个部分之间用"点"（.）分隔，就可以返回给用户。

### Base64URL

前面提到，Header 和 Payload 串型化的算法是 Base64URL。这个算法跟 Base64 算法基本类似，但有一些小的不同。

JWT 作为一个令牌（token），有些场合可能会放到 URL（比如 api.example.com/?token=xxx）。

Base64 有三个字符+、/和=，在 URL 里面有特殊含义，所以要被替换掉：=被省略、+替换成-，/替换成_ 。

这就是 Base64URL 算法。

## JWT 的使用方式

客户端收到服务器返回的 JWT，可以储存在 `Cookie` 里面，也可以储存在 `localStorage`。

此后，客户端每次与服务器通信，都要带上这个 JWT。

你可以把它放在 Cookie 里面自动发送，但是这样不能跨域，所以更好的做法是放在 HTTP 请求的头信息`Authorization`字段里面。

```text
Authorization: Bearer <token>
```

另一种做法是，跨域的时候，JWT 就放在 POST 请求的数据体里面。

## JWT 的几个特点

- JWT 默认是不加密，但也是可以加密的。生成原始 Token 以后，可以用密钥再加密一次。
- JWT 不加密的情况下，不能将秘密数据写入 JWT。
- JWT 不仅可以用于认证，也可以用于交换信息。有效使用 JWT，可以降低服务器查询数据库的次数。
- JWT 的最大缺点是，由于服务器不保存 session 状态，因此无法在使用过程中废止某个 token，或者更改 token 的权限。也就是说，一旦 JWT 签发了，在到期之前就会始终有效，除非服务器部署额外的逻辑。
- JWT 本身包含了认证信息，一旦泄露，任何人都可以获得该令牌的所有权限。为了减少盗用，JWT 的有效期应该设置得比较短。对于一些比较重要的权限，使用时应该再次对用户进行认证。
- 为了减少盗用，JWT 不应该使用 HTTP 协议明码传输，要使用 HTTPS 协议传输。

## Java JWT 库 jjwt 使用

JJWT旨在成为最易于使用和理解的库，用于在JVM和Android上创建和验证JSON Web Token（JWT）。

JJWT是仅基于JWT，JWS，JWE，JWK和JWA RFC规范以及Apache 2.0许可条款下的开源的纯Java实现。

Maven项目中引入依赖：

```xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.1</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.11.1</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId> <!-- or jjwt-gson if Gson is preferred -->
    <version>0.11.1</version>
    <scope>runtime</scope>
</dependency>
<!-- Uncomment this next dependency if you are using JDK 10 or earlier and you also want to use 
     RSASSA-PSS (PS256, PS384, PS512) algorithms.  JDK 11 or later does not require it for those algorithms:
<dependency>
    <groupId>org.bouncycastle</groupId>
    <artifactId>bcprov-jdk15on</artifactId>
    <version>1.60</version>
    <scope>runtime</scope>
</dependency>
-->
```

测试的Java示例代码如下：

```java
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.BeanUtils;

import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    public static String createJwt(JwtDto jwtDto, String signingKey, Map<String, Object> customClaims) {
        String jwt = Jwts.builder()
                //编号
                .setId(jwtDto.getId())
                //主题
                .setSubject(jwtDto.getSubject())
                //受众
                .setAudience(jwtDto.getAudience())
                //签发人
                .setIssuer(jwtDto.getIssuer())
                //设置签发时间
                .setIssuedAt(jwtDto.getIssuedAt())
                //设置生效时间
                .setNotBefore(jwtDto.getNotBefore())
                //设置过期时间
                .setExpiration(jwtDto.getExpiration())
                //自定义的参数
                .addClaims(customClaims)
                //设置签名秘钥
                .signWith(Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.ISO_8859_1)), SignatureAlgorithm.HS256)
                .compact();
        System.out.println("jwt:" + jwt);
        return jwt;
    }

    public static Claims parseJwt(String jwt, String signingKey) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.ISO_8859_1)))
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        JwtDto jwtDto = new JwtDto();
        BeanUtils.copyProperties(claims, jwtDto);
        System.out.println(jwtDto);
        return claims;
    }

    public static void main(String[] args) {
        JwtDto jwtDto = new JwtDto();
        jwtDto.setId("01");
        jwtDto.setSubject("jwt测试");
        jwtDto.setAudience("程序员");
        jwtDto.setIssuer("lighter");
        Calendar instance = Calendar.getInstance();
        jwtDto.setIssuedAt(instance.getTime());
        jwtDto.setNotBefore(instance.getTime());
        instance.add(Calendar.HOUR, 2);
        jwtDto.setExpiration(instance.getTime());

        String signingKey = "2DUhGe0BUgowMJmLazY3nQ8N8Z3Q16g90sbBQ7lAXwzg385xlrUc5Z4AL1Y9Mkkp";
        Map<String, Object> customClaims = new HashMap<>(2);
        customClaims.put("Hello", "World");
        customClaims.put("happy", "every day");
        String jwt = createJwt(jwtDto, signingKey, customClaims);
        Claims claims = parseJwt(jwt, signingKey);
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

}
```

## 参考资料

- [阮一峰 - JSON Web Token 入门教程](http://www.ruanyifeng.com/blog/2018/07/json_web_token-tutorial.html)
