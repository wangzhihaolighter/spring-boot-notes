package com.example.springboot.part0402druid.admin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.springboot.part0402druid.admin.entity.primary.People;
import com.example.springboot.part0402druid.admin.mapper.primary.PeopleMapper;
import com.example.springboot.part0402druid.admin.service.PeopleService;
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
public class PeopleServiceImpl extends ServiceImpl<PeopleMapper, People> implements PeopleService {

}
