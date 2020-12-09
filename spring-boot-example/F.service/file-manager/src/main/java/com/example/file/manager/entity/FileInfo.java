package com.example.file.manager.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
public class FileInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createTime;
    /**
     * 原文件名
     */
    private String originFileName;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件后缀
     */
    private String ext;
    /**
     * 相对路径
     */
    private String relativePath;
}