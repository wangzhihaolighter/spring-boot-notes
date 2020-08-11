package com.example.jsch.util;

import lombok.Data;

/**
 * 记录服务器登录信息
 */
@Data
public class Remote {
    private String host;
    private int port = 22;
    private String user;
    private String password;
    private String identity = "~/.ssh/id_rsa";
    private String passphrase;
}