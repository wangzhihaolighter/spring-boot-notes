package com.example.springboot.part0307mybatisplus.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.example.springboot.part0307mybatisplus.entity.People;
import com.example.springboot.part0307mybatisplus.mapper.PeopleMapper;
import com.example.springboot.part0307mybatisplus.service.PeopleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author K神带你飞123
 * @since 2018-01-19
 */
@Service
public class PeopleServiceImpl extends ServiceImpl<PeopleMapper, People> implements PeopleService {

    @Autowired
    private PeopleMapper peopleMapper;

    @Override
    public List<People> queryPageList(Integer pageNum, Integer pageSize) {
        Page<People> page = new Page<>(pageNum,pageSize);
        //1.和分页插件的使用不一样，分页查询需要通用方法无法传入分页参数，不能分页
        //List<People> peopleList = peopleMapper.selectList(null);

        //2.自定义方法传入分页参数，可以分页
        Map<String,Object> param = new HashMap<>(0);
        return peopleMapper.queryList(page,param);
    }
}
