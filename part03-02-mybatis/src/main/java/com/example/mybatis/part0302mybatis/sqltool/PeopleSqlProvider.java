package com.example.mybatis.part0302mybatis.sqltool;

import java.util.Map;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

public class PeopleSqlProvider {

    private final static String TABLE_NAME = "people";

    public String getByIdSql(Map<Integer, Object> parameter) {
        BEGIN();
        SELECT("*");
        FROM(TABLE_NAME);
        //注意这里这种传递参数方式，#{}与map中的key对应，而map中的key又是注解param设置的
        WHERE("id = #{id}");
        return SQL();
    }

    public String getAllSql() {
        BEGIN();
        SELECT("*");
        FROM(TABLE_NAME);
        return SQL();
    }
}