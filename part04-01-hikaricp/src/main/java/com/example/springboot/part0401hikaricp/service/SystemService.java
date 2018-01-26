package com.example.springboot.part0401hikaricp.service;

import com.example.springboot.part0401hikaricp.model.second.User;

/**
 * Description:
 *
 * @author zhihao.wang
 * @date 2018 /1/25
 */
public interface SystemService {
    /**
     * Save user int.
     *
     * @param user the user
     * @return the int
     */
    int saveUser(User user);

    /**
     * Query user info user.
     *
     * @param username the username
     * @param password the password
     * @return the user
     */
    User queryUserInfo(String username, String password);
}
