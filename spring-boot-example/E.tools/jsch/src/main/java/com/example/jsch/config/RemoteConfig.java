package com.example.jsch.config;

import com.example.jsch.util.Remote;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RemoteConfig {

    @Bean
    public Remote remote(RemoteProperties remoteProperties) {
        Remote remote = new Remote();
        BeanUtils.copyProperties(remoteProperties, remote);
        return remote;
    }

}
