package com.example.springboot.part0307mybatisplus.service;

import com.baomidou.mybatisplus.service.IService;
import com.example.springboot.part0307mybatisplus.entity.People;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author K神带你飞123
 * @since 2018-01-19
 */
public interface PeopleService extends IService<People> {
    List<People> queryPageList(Integer pageNum, Integer pageSize);
}
