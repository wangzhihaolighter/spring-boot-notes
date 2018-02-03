package com.example.springboot.part0402druid.admin.service.impl;

import com.example.springboot.part0402druid.admin.entity.Dog;
import com.example.springboot.part0402druid.admin.entity.People;
import com.example.springboot.part0402druid.admin.entity.User;
import com.example.springboot.part0402druid.admin.mapper.DogMapper;
import com.example.springboot.part0402druid.admin.mapper.PeopleMapper;
import com.example.springboot.part0402druid.admin.mapper.UserMapper;
import com.example.springboot.part0402druid.admin.service.SimpleService;
import com.example.springboot.part0402druid.common.config.datasource.DynamicDataSource;
import com.example.springboot.part0402druid.common.constant.GlobalDatasourceConstant;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleServiceImpl implements SimpleService {

    @Autowired
    private DogMapper dogMapper;

    @Autowired
    private PeopleMapper peopleMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    @DynamicDataSource(GlobalDatasourceConstant.PRIMARY_DATA_SOURCE_KEY)
    public List<Dog> queryDogs() {
        RowBounds rowBounds = new RowBounds(0, 5);
        return dogMapper.selectPage(rowBounds, null);
    }

    @Override
    @DynamicDataSource(GlobalDatasourceConstant.PRIMARY_DATA_SOURCE_KEY)
    public List<People> queryPeople() {
        RowBounds rowBounds = new RowBounds(0, 5);
        return peopleMapper.selectPage(rowBounds, null);
    }

    @Override
    @DynamicDataSource(GlobalDatasourceConstant.SECOND_DATA_SOURCE_KEY)
    public List<User> queryUser() {
        RowBounds rowBounds = new RowBounds(0, 5);
        return userMapper.selectPage(rowBounds, null);
    }
}
