package com.example.dao.part0301jpa.dao;

import com.example.dao.part0301jpa.entity.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Description:
 *
 * @author zhihao.wang
 * @date 2018/1/10
 */
public interface DogRepository extends JpaRepository<Dog, Long> {
    List<Dog> findByName(String name);
}
