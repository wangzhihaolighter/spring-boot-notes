package com.example.mongodb.dao;

import com.example.mongodb.dto.PageInfoDTO;
import com.example.mongodb.entity.User;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserDao {

    private final MongoTemplate mongoTemplate;

    public UserDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /* insert */

    public String save(User user) {
        mongoTemplate.save(user);
        return user.getId().toString();
    }

    public List<String> batchSave(List<User> users) {
        mongoTemplate.insert(users, User.class);
        return users.stream().map(user -> user.getId().toString()).collect(Collectors.toList());
    }

    /* select */

    public List<User> findAll() {
        return mongoTemplate.findAll(User.class);
    }

    public User findById(String id) {
        return mongoTemplate.findById(new ObjectId(id), User.class);
    }

    public List<User> findByUsername(String username) {
        Query query = new Query();
        //模糊查询
        query.addCriteria(Criteria.where("username").regex(username));
        return mongoTemplate.find(query, User.class);
    }

    public PageInfoDTO<User> pageQuery(Integer pageNum, Integer pageSize) {
        Query query = new Query();
        //注意：页码从0开始
        query.with(PageRequest.of(pageNum - 1, pageSize));
        List<User> users = mongoTemplate.find(query, User.class);
        long count = mongoTemplate.count(query, User.class);
        return new PageInfoDTO<>(pageNum, pageSize, users, (int) count);
    }

    public List<User> findSort(Sort.Order... orders) {
        Query query = new Query();
        query.with(Sort.by(orders));
        return mongoTemplate.find(query, User.class);
    }

    /* update */

    public Long update(User user) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(user.getId()));
        Update update = new Update();
        update.set("username", user.getUsername());
        update.set("password", user.getPassword());
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, User.class);
        return updateResult.getModifiedCount();
    }

    public Integer batchUpdate(List<User> users) {
        BulkOperations bulkOps = mongoTemplate.bulkOps(BulkOperations.BulkMode.ORDERED, User.class);
        List<Pair<Query, Update>> pairs = new ArrayList<>();
        for (User user : users) {
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(user.getId()));
            Update update = new Update();
            update.set("username", user.getUsername());
            update.set("password", user.getPassword());
            Pair<Query, Update> pair = Pair.of(query, update);
            pairs.add(pair);
        }
        bulkOps.updateMulti(pairs);
        BulkWriteResult execute = bulkOps.execute();
        return execute.getModifiedCount();
    }


    /* delete */

    public long delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        DeleteResult deleteResult = mongoTemplate.remove(query, User.class);
        return deleteResult.getDeletedCount();
    }

    public long batchDelete(List<String> ids) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").in(ids));
        DeleteResult deleteResult = mongoTemplate.remove(query, User.class);
        return deleteResult.getDeletedCount();
    }

}
