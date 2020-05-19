package com.example.jjwt.jwt;

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
