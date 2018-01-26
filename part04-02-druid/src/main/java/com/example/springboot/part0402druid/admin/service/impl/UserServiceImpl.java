package com.example.springboot.part0402druid.admin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.springboot.part0402druid.admin.entity.second.User;
import com.example.springboot.part0402druid.admin.mapper.second.UserMapper;
import com.example.springboot.part0402druid.admin.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 佚名123
 * @since 2018-01-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
