package com.example.poi.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "student_info")
public class StudentInfo {
    @Id
    private String studentNumber;
    private String name;
    private Integer age;
    private String grade;
    private String classroom;
    private String photoUrl;
}
