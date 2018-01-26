package com.example.springboot.part0402druid.admin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.springboot.part0402druid.admin.entity.primary.Dog;
import com.example.springboot.part0402druid.admin.mapper.primary.DogMapper;
import com.example.springboot.part0402druid.admin.service.DogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 佚名123
 * @since 2018-01-26
 */
@Service
public class DogServiceImpl extends ServiceImpl<DogMapper, Dog> implements DogService {

}
