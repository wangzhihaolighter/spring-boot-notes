package com.example.springboot.part0307mybatisplus.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.example.springboot.part0307mybatisplus.entity.People;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author K神带你飞123
 * @since 2018-01-19
 */
public interface PeopleMapper extends BaseMapper<People> {
    List<People> queryList(Page<People> page, Map<String, Object> param);
}
