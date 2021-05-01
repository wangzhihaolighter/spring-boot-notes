package com.example.kaptcha.controller;

import com.example.kaptcha.config.cache.CacheService;
import com.example.kaptcha.config.dto.AuthUserDto;
import com.example.kaptcha.config.dto.CaptchaDto;
import com.example.kaptcha.entity.User;
import com.example.kaptcha.service.AuthorizationService;
import com.example.kaptcha.util.CaptchaUtil;
import com.example.kaptcha.util.IdUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    private final AuthorizationService authorizationService;
    private final CacheService cacheService;
    private final DefaultKaptcha defaultKaptcha;

    public AuthorizationController(AuthorizationService authorizationService, CacheService cacheService, DefaultKaptcha defaultKaptcha) {
        this.authorizationService = authorizationService;
        this.cacheService = cacheService;
        this.defaultKaptcha = defaultKaptcha;
    }

    /**
     * 获取验证码
     */
    @GetMapping("/captcha")
    public ResponseEntity<CaptchaDto> getCaptcha() {
        //生成验证码
        String text = defaultKaptcha.createText();
        BufferedImage image = defaultKaptcha.createImage(text);
        String uuid = IdUtil.generatorCaptchaUuid();

        //验证码信息存入缓存
        cacheService.putValue(uuid, text);

        //响应验证码信息
        CaptchaDto captchaDto = new CaptchaDto();
        captchaDto.setUuid(uuid);
        captchaDto.setImgBase64(CaptchaUtil.bufferedImageToBase64(image));
        return ResponseEntity.ok(captchaDto);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthUserDto authUserDto) {
        Object value = cacheService.getValue(authUserDto.getCaptchaUuid());
        if (value == null || !String.valueOf(value).equalsIgnoreCase(authUserDto.getCaptchaText())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("验证码错误");
        }

        User user = authorizationService.findUser(authUserDto.getUsername(), authUserDto.getPassword());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("用户名或密码错误！");
        }

        String token = IdUtil.generatorLoginTokenUuid();
        cacheService.putValue(token, user.getId());
        return ResponseEntity.ok(token);
    }

}
