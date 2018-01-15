package com.example.mybatis.part0302mybatis.mapper;

import com.example.mybatis.part0302mybatis.entity.Dog;

import java.util.List;

/**
 * Description:
 *
 * @author zhihao.wang
 * @date 2018/1/11
 */
public interface DogMapper {
    /**
     * 保存
     *
     * @param dog 狗实体
     */
    void insert(Dog dog);

    /**
     * 根据姓名查询狗
     *
     * @param name 姓名
     * @return 狗实体集合
     */
    List<Dog> findByName(String name);
}
