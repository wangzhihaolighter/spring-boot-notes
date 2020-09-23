package com.example.springframework.boot.http.encryption.web;

import com.example.springframework.boot.http.encryption.dto.DemoDTO;
import com.example.springframework.boot.http.encryption.encryption.annotation.RequestDecode;
import com.example.springframework.boot.http.encryption.encryption.annotation.ResponseEncode;
import com.example.springframework.boot.http.encryption.encryption.enumeration.SecurityMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SimpleController {

    @GetMapping("/")
    public String home() {
        return "hello http encryption";
    }

    /**
     * 响应数据加密
     */
    @ResponseEncode(method = SecurityMethod.AES)
    @PostMapping("/post/response/encode")
    public DemoDTO postResponseEncode(@RequestBody DemoDTO demoDTO) {
        log.info("[ post response encode] demoDTO:" + demoDTO);
        return demoDTO;
    }

    /**
     * 请求数据解密
     */
    @RequestDecode
    @PostMapping("/post/request/decode")
    public DemoDTO post(@RequestBody DemoDTO demoDTO) {
        log.info("[ post request decode] demoDTO:" + demoDTO);
        return demoDTO;
    }

}
