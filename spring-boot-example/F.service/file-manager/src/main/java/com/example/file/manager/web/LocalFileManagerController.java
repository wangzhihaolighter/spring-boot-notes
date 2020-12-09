package com.example.file.manager.web;

import com.example.file.manager.service.LocalFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 本地文件管理
 */
@Slf4j
@Controller
@RequestMapping("/file/local")
public class LocalFileManagerController {

    private final LocalFileService localFileService;

    public LocalFileManagerController(LocalFileService localFileService) {
        this.localFileService = localFileService;
    }

    /**
     * 单文件上传
     *
     * @param file 文件
     * @return 文件记录id
     */
    @PostMapping(value = "/upload")
    @ResponseBody
    public Long upload(@RequestParam("file") MultipartFile file) {
        return localFileService.upload(file);
    }

    /**
     * 文件下载
     *
     * @param request  /
     * @param response /
     * @param id       文件记录id
     */
    @GetMapping(value = "/download")
    public void download(HttpServletRequest request, HttpServletResponse response,
                         @RequestParam("id") Long id) {
        localFileService.download(request, response, id);
    }

    /**
     * 文件删除
     *
     * @param id 文件记录id
     */
    @GetMapping(value = "/remove")
    @ResponseBody
    public Boolean download(@RequestParam("id") Long id) {
        return localFileService.remove(id);
    }

    /**
     * 断点续传
     *
     * @param file 上传文件
     * @return 文件记录id
     */
    @PostMapping(value = "/upload/breakpointResume")
    @ResponseBody
    public Long breakpointResumeUpload(HttpServletRequest request,
                                       @RequestParam("file") MultipartFile file) {
        return localFileService.breakpointResumeUpload(request, file);
    }

    /**
     * 分片上传
     *
     * @param file 上传文件
     * @return 文件记录id
     */
    @PostMapping(value = "/upload/shard")
    @ResponseBody
    public Long shardUpload(HttpServletRequest request,
                            @RequestParam("file") MultipartFile file) {
        return localFileService.shardUpload(request, file);
    }

}
