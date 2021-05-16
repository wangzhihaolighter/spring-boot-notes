package com.example.file.manager.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LocalFileService {
    Long upload(MultipartFile file);

    void download(HttpServletRequest request, HttpServletResponse response, Long id);

    Boolean remove(Long id);
}
