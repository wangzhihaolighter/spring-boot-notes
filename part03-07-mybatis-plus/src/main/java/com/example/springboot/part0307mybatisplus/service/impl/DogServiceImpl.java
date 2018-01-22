package com.example.springboot.part0307mybatisplus.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.springboot.part0307mybatisplus.entity.Dog;
import com.example.springboot.part0307mybatisplus.mapper.DogMapper;
import com.example.springboot.part0307mybatisplus.service.DogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author K神带你飞123
 * @since 2018-01-19
 */
@Service
public class DogServiceImpl extends ServiceImpl<DogMapper, Dog> implements DogService {

}
