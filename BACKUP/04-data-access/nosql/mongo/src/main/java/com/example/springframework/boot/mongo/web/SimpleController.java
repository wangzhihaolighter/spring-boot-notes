package com.example.springframework.boot.mongo.web;

import com.example.springframework.boot.mongo.config.page.PageInfo;
import com.example.springframework.boot.mongo.dao.UserDao;
import com.example.springframework.boot.mongo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SimpleController {

    @GetMapping("/")
    public String home() {
        return "hello mongo";
    }

    /*
    mongodb 有哪些权限:
    1. 数据库用户角色：read、readWrite;
    2. 数据库管理角色：dbAdmin、dbOwner、userAdmin；
    3. 集群管理角色：clusterAdmin、clusterManager、clusterMonitor、hostManager；
    4. 备份恢复角色：backup、restore；
    5. 所有数据库角色：readAnyDatabase、readWriteAnyDatabase、userAdminAnyDatabase、dbAdminAnyDatabase
    6. 超级用户角色：root
    // 这里还有几个角色间接或直接提供了系统超级用户的访问（dbOwner 、userAdmin、userAdminAnyDatabase）
    7. 内部角色：__system
     */

    @Autowired
    private UserDao userDao;

    /* insert */

    @PostMapping("/user/save")
    public String save(@RequestBody User user) {
        return userDao.save(user);
    }

    @PostMapping("/user/save/batch")
    public List<String> save(@RequestBody List<User> users) {
        return userDao.batchSave(users);
    }

    /* select */

    @GetMapping("/user/query/all")
    public List<User> findAll() {
        return userDao.findAll();
    }

    @GetMapping("/user/query/{id}")
    public User findById(@PathVariable("id") String id) {
        return userDao.findById(id);
    }

    @GetMapping("/user/query/username")
    public List<User> findByUsername(@RequestParam("username") String username) {
        return userDao.findByUsername(username);
    }

    @GetMapping("/user/query/page")
    public PageInfo<User> pageQuery(@RequestParam("pageNum") Integer pageNum,
                                    @RequestParam("pageSize") Integer pageSize) {
        return userDao.pageQuery(pageNum, pageSize);
    }

    @GetMapping("/user/query/sort")
    public List<User> sortQuery() {
        return userDao.findSort(Sort.Order.asc("id"));
    }

    /* update */

    @PutMapping("/user/update")
    public Long update(@RequestBody User user) {
        return userDao.update(user);
    }

    @PutMapping("/user/update/batch")
    public Integer batchUpdate(@RequestBody List<User> users) {
        return userDao.batchUpdate(users);
    }

    /* delete */

    @DeleteMapping("/user/delete")
    public Long delete(@RequestParam("id") String id) {
        return userDao.delete(id);
    }

    @DeleteMapping("/user/delete/batch")
    public Long batchDelete(@RequestBody List<String> ids) {
        return userDao.batchDelete(ids);
    }

}
