package com.example.testing.repository;

import com.example.testing.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUsersByUsernameAndPassword(String username, String password);

    List<User> queryUsersByUsernameLike(String username);

    List<User> readUsersByUsernameNotNullOrderByUsername();

    Integer countByUsernameLike(String username);

    Page<User> queryByUsernameLike(String username, Pageable pageable);

    List<User> queryByUsernameLike(String username, Sort sort);

}
