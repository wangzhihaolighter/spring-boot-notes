package com.example.jjwt;

import com.example.jjwt.jwt.JwtDto;
import com.example.jjwt.jwt.JwtProperties;
import com.example.jjwt.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
public class SimpleController {
    @Autowired
    private JwtProperties jwtProperties;

    @GetMapping("/")
    public String sayHello() {
        return "Hello,JWT!";
    }

    @GetMapping("/createJwt")
    public String createJwt(JwtDto jwtDto) {
        Map<String, Object> customClaims = new HashMap<>(2);
        customClaims.put("abc","123");
        customClaims.put("now",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return JwtUtil.createJwt(jwtDto, jwtProperties.getSigningKey(), customClaims);
    }

    @GetMapping("/parseJwt")
    public Set<Map.Entry<String, Object>> parseJwt(HttpServletRequest request) {
        String jwtHeader = request.getHeader(jwtProperties.getHeader());
        String jwt = jwtHeader.replace(jwtProperties.getTokenStartWith(), "");
        Claims claims = JwtUtil.parseJwt(jwt, jwtProperties.getSigningKey());
        return claims.entrySet();
    }
}
