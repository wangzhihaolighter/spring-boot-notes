package com.example.mybatis.part0302mybatis.mapper;

import com.example.mybatis.part0302mybatis.entity.People;
import com.example.mybatis.part0302mybatis.sqltool.PeopleSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Description:
 *
 * @author zhihao.wang
 * @date 2018/1/11
 */
public interface PeopleMapper {

    //注解方式写sql语句，个人感觉非常不适应，只做了解，不推荐使用

    //方式一:映射器接口中写SQL语句

    /**
     * 保存
     *
     * @param people 人实体
     */
    @Insert("insert into people(name,age,description) values(#{name},#{age},#{description})")
    void insert(People people);

    /**
     * 根据姓名查询人
     *
     * @param name 姓名
     * @return 人实体集合
     */
    @Select("select * from people where name=#{name}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "age", column = "age"),
            @Result(property = "description", column = "description")
    })
    List<People> findByName(String name);

    //方式二:映射器接口调用SqlBuilder生成的SQL进行执行(过时，仅作了解)

    /**
     * 根据id查询人
     *
     * @param id 主键id
     * @return 人实体
     */
    @SelectProvider(type = PeopleSqlProvider.class, method = "getByIdSql")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "age", column = "age"),
            @Result(property = "description", column = "description")
    })
    People findById(@Param("id") Long id);

    /**
     * 查询所有
     *
     * @return 人实体集合
     */
    @SelectProvider(type = PeopleSqlProvider.class, method = "getAllSql")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "age", column = "age"),
            @Result(property = "description", column = "description")
    })
    List<People> findAll();
}
