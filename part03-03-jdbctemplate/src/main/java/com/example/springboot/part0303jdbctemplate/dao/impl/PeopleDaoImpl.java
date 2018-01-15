package com.example.springboot.part0303jdbctemplate.dao.impl;

import com.example.springboot.part0303jdbctemplate.dao.PeopleDao;
import com.example.springboot.part0303jdbctemplate.entity.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Description:
 *
 * @author zhihao.wang
 * @date 2018/1/15
 */
@Repository
public class PeopleDaoImpl implements PeopleDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insertSimple(People people) {
        String sql = "INSERT INTO people(name,age,description) VALUES (?,?,?)";
        jdbcTemplate.update(sql, people.getName(), people.getAge(), people.getDescription());
    }

    @Override
    public Long insert(People people) {
        final String sql = "INSERT INTO people(name,age,description) VALUES (?,?,?)";
        //返回自动生成的主键，并可以防止sql注入
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, people.getName());
            ps.setInt(2, people.getAge());
            ps.setString(3, people.getDescription());
            return ps;
        }, holder);
        return holder.getKey().longValue();
    }

    @Override
    public People findById(Long id) {
        People result = null;
        String sql = "SELECT * FROM people WHERE id=?";
        List<People> peopleList = jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper(People.class));
        if (null != peopleList && 0 < peopleList.size()) {
            result = peopleList.get(0);
        }
        return result;
    }
}
