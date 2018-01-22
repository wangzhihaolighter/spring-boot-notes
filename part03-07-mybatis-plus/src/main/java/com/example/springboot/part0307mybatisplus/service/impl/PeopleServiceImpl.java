package com.example.springboot.part0307mybatisplus.service.impl;

import com.example.springboot.part0307mybatisplus.entity.People;
import com.example.springboot.part0307mybatisplus.mapper.PeopleMapper;
import com.example.springboot.part0307mybatisplus.service.PeopleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
