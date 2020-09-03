package com.example.poi.dao;

import com.example.poi.entity.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentInfoRepository extends JpaRepository<StudentInfo, String> {
}
