package com.example.springframework.boot.jdbc.dao.impl;

import com.example.springframework.boot.jdbc.dao.UserDao;
import com.example.springframework.boot.jdbc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    /**
     * execute方法：可以用于执行任何SQL语句，一般用于执行DDL语句；
     * update方法/batchUpdate方法：update方法用于执行新增、修改、删除等语句；batchUpdate方法用于执行批处理相关语句；
     * query方法/queryForXXX方法：用于执行查询相关语句；
     * call方法：用于执行存储过程、函数相关语句。
     */

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Long insert(User user) {
        final String sql = "insert into `user`(`username`,`password`) values (?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Integer update(User user) {
        final String sql = "update `user` set `username`=?,`password`=? where `id`=?";
        return jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getId());
    }

    @Override
    public Integer delete(Long id) {
        final String sql = "delete from `user` where `id`=?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public Integer batchDelete(List<Long> ids) {
        final String sql = "delete from `user` where `id`=?";
        List<Object[]> batchArgs = new ArrayList<>();
        for (Long id : ids) {
            batchArgs.add(new Object[]{id});
        }
        int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
        Integer result = 0;
        for (int count : ints) {
            result = result + count;
        }
        return result;
    }

    @Override
    public User selectById(Long id) {
        final String sql = "select * from `user` where `id`=?";
        BeanPropertyRowMapper<User> beanPropertyRowMapper = new BeanPropertyRowMapper<>(User.class);
        List<User> users = jdbcTemplate.query(sql, beanPropertyRowMapper, id);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public List<User> selectAll() {
        final String sql = "select * from `user`";
        BeanPropertyRowMapper<User> beanPropertyRowMapper = new BeanPropertyRowMapper<>(User.class);
        return jdbcTemplate.query(sql, beanPropertyRowMapper);
    }

}
