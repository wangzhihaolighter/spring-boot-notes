package com.example.springboot.part0401hikaricp.service.impl;

import com.example.springboot.part0401hikaricp.mapper.primary.PeopleMapper;
import com.example.springboot.part0401hikaricp.mapper.second.UserMapper;
import com.example.springboot.part0401hikaricp.model.primary.People;
import com.example.springboot.part0401hikaricp.model.second.User;
import com.example.springboot.part0401hikaricp.service.SystemService;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Description:
 *
 * @author zhihao.wang
 * @date 2018/1/25
 */
@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PeopleMapper peopleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveUser(User user) {
        //主库保存用户人信息
        People people = user.getPeople();
        peopleMapper.insert(people);
        //second库保存用户信息
        user.setPassword(MD5Encoder.encode(user.getPassword().getBytes()));
        return userMapper.insert(user);
    }

    @Override
    public User queryUserInfo(String username, String password) {
        //用户信息
        User user = new User();
        user.setName(username);
        user.setPassword(MD5Encoder.encode(password.getBytes()));
        User result = userMapper.selectOne(user);
        //人信息
        People people = new People();
        people.setName(username);
        List<People> peopleList = peopleMapper.select(people);
        result.setPeople(CollectionUtils.isEmpty(peopleList) ? people : peopleList.get(0));
        return result;
    }
}
