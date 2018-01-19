package com.example.springboot.part0305mybatismapperpagehelper.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Description:
 *
 * @author Administrator
 * @date 2018/1/19
 */
public interface CommonMapper<T> extends Mapper<T>,MySqlMapper<T> {
}
