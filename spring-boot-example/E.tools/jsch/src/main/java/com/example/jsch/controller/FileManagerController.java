package com.example.jsch.controller;

import com.example.jsch.entity.FileInfo;
import com.example.jsch.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 本地文件管理
 */
@Slf4j
@Controller
@RequestMapping("/file")
public class FileManagerController {

    private final FileService fileService;

    public FileManagerController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(value = "/all", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public List<FileInfo> getAll() {
        return fileService.getAll();
    }

    /**
     * 单文件上传
     *
     * @param file 上传文件
     * @return 文件记录id
     */
    @PostMapping(value = "/upload")
    @ResponseBody
    public Long upload(@RequestParam("file") MultipartFile file) {
        return fileService.upload(file);
    }

    /**
     * 文件下载
     *
     * @param request  请求
     * @param response 响应
     * @param id       文件记录id
     */
    @GetMapping(value = "/download")
    public void download(HttpServletRequest request, HttpServletResponse response,
                         @RequestParam("id") Long id) {
        fileService.download(request, response, id);
    }

    /**
     * 文件删除
     *
     * @param id 文件记录id
     */
    @GetMapping(value = "/remove")
    @ResponseBody
    public Boolean download(@RequestParam("id") Long id) {
        return fileService.remove(id);
    }

}
