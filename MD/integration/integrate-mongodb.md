# Spring Boot 整合 mongodb

## 资料

- mongodb官网：[mongodb](https://www.mongodb.com/)

## mongodb介绍

MongoDB 是一个基于分布式文件存储的数据库。由 C++ 语言编写。旨在为 WEB 应用提供可扩展的高性能数据存储解决方案。

MongoDB 是一个介于关系数据库和非关系数据库之间的产品，是非关系数据库当中功能最丰富，最像关系数据库的。

## 整合使用

Maven项目引入依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
```

SpringBoot项目中配置mongodb连接：

```properties
spring.data.mongodb.uri=mongodb://user:pwd@localhost:27017/database
```

多个IP集群可以采用以下配置：
```properties
spring.data.mongodb.uri=mongodb://user:pwd@ip1:port1,ip2:port2/database
```

业务类中引入`org.springframework.data.mongodb.core.MongoTemplate`即可，一个简单的User操作示例如下：

User实体类

```java
@Data
public class User implements Serializable {
    private String id;
    private String username;
    private String password;
}
```

PageInfoDTO分页实体类

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfoDTO<T> {
    private Integer pageNum;
    private Integer pageSize;
    private List<T> data;
    private Integer total;
    //other
}
```

UserDao用户信息mongo数据操作类

```java
import com.example.mongodb.dto.PageInfoDTO;
import com.example.mongodb.entity.User;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
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
import java.util.UUID;

@Repository
public class UserDao {

    private final MongoTemplate mongoTemplate;

    public UserDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /* insert */

    public String save(User user) {
        String id = UUID.randomUUID().toString();
        user.setId(id);
        mongoTemplate.save(user);
        return id;
    }

    public List<String> batchSave(List<User> users) {
        List<String> ids = new ArrayList<>();
        users.forEach(user -> {
            String id = UUID.randomUUID().toString();
            user.setId(id);
            ids.add(id);
        });
        mongoTemplate.insert(users, User.class);
        return ids;
    }

    /* select */

    public List<User> findAll() {
        return mongoTemplate.findAll(User.class);
    }

    public User findById(String id) {
        return mongoTemplate.findById(id, User.class);
    }

    public List<User> findByUsername(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
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
```
