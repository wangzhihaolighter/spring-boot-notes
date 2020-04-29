package com.example.ip2region;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class SimpleController {

    @GetMapping("/")
    public String getIpRegion(HttpServletRequest request) {
        String ip = IpUtils.getIp(request);
        return IpUtils.getRegionInfo(ip);
    }
}
