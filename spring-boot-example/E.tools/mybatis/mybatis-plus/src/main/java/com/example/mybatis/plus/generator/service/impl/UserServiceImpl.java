package com.example.mybatis.plus.generator.service.impl;

import com.example.mybatis.plus.generator.entity.User;
import com.example.mybatis.plus.generator.mapper.UserMapper;
import com.example.mybatis.plus.generator.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangzhihao
 * @since 2021-11-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
