package com.example.springboot.part0303jdbctemplate.dao;

import com.example.springboot.part0303jdbctemplate.entity.People;

/**
 * Description:
 *
 * @author zhihao.wang
 * @date 2018/1/15
 */
public interface PeopleDao {
    /**
     * 新增人记录
     *
     * @param people 人实体
     */
    void insertSimple(People people);

    /**
     * 新增人记录,返回记录id
     *
     * @param people 人实体
     * @return 记录id
     */
    Long insert(People people);

    /**
     * 根据id查询人记录
     *
     * @param id 主键id
     * @return 人实体
     */
    People findById(Long id);
}
