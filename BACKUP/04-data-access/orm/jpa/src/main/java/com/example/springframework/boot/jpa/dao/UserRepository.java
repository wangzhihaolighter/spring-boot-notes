package com.example.springframework.boot.jpa.dao;

import com.example.springframework.boot.jpa.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    /*----------自定查询，jpa根据方法名自动生成SQL语句----------*/

    User findUsersByUsernameAndPassword(String username, String password);

    List<User> queryUsersByUsernameLike(String username);

    List<User> readUsersByUsernameNotNullOrderByUsername();

    Integer countByUsernameLike(String username);

    /*----------分页查询/排序查询----------*/

    Page<User> queryByUsernameLike(String username, Pageable pageable);

    List<User> queryByUsernameLike(String username, Sort sort);

    /*----------自定义SQL查询,注意，这里@Query中默认sql中的表名需为实体类的类名,字段名需为类中属性名----------*/

    @Query("select u from User u where u.username like %?1%")
    List<User> selectByUsernameLike(String username);

    @Query("select u from User u where u.username=:username and u.password=:password")
    List<User> selectUserByUsernameAndPassword(@Param(value = "username") String username,
                                               @Param(value = "password") String password);

    /**
     * 注意，nativeQuery = true，表示使用原生SQL
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update `user` set username=?2,password=?3 where id=?1", nativeQuery = true)
    void updateUser(Long id, String username, String password);
}
