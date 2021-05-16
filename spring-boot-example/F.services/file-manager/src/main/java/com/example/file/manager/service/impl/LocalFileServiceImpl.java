package com.example.file.manager.service.impl;

import com.example.file.manager.config.FileProperties;
import com.example.file.manager.dao.FileInfoRepository;
import com.example.file.manager.entity.FileInfo;
import com.example.file.manager.service.LocalFileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class LocalFileServiceImpl implements LocalFileService {

    private final FileInfoRepository fileInfoRepository;
    private final FileProperties fileProperties;

    public LocalFileServiceImpl(FileInfoRepository fileInfoRepository, FileProperties fileProperties) {
        this.fileInfoRepository = fileInfoRepository;
        this.fileProperties = fileProperties;
    }

    @Override
    public Long upload(MultipartFile file) {
        LocalDateTime now = LocalDateTime.now();
        // 获取原始名字
        String fileName = file.getOriginalFilename();
        // 获取后缀名（这里不做校验）
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 文件新名字
        String fileNewName = UUID.randomUUID().toString().replace("-", "") + suffixName;
        // 生成文件路径
        String fileRelativePath = File.separator + now.getYear()
                + File.separator + now.getMonthValue()
                + File.separator + now.getDayOfMonth()
                + File.separator + now.getHour();
        String fileFullPath = fileProperties.getUploadBasePath() + fileRelativePath;
        File destFile = new File(fileFullPath + File.separator + fileNewName);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }

        if (!file.isEmpty()) {
            try {
                //方式一：BufferedOutputStream
                //byte[] bytes = file.getBytes();
                //@Cleanup BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(destFile));
                //out.write(bytes);

                //方式二：transferTo
                file.transferTo(destFile);
                System.out.println("You successfully uploaded " + fileName + " into " + fileFullPath + File.separator + fileNewName);

                FileInfo fileInfo = new FileInfo();
                fileInfo.setFileName(fileNewName);
                fileInfo.setOriginFileName(fileName);
                fileInfo.setExt(suffixName);
                fileInfo.setRelativePath(fileRelativePath);
                fileInfo.setAbsolutePath(destFile.getAbsolutePath());
                fileInfo.setCreateTime(now);
                fileInfoRepository.saveAndFlush(fileInfo);
                return fileInfo.getId();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("You failed to upload " + fileName + " => " + e.getMessage());
            }
        } else {
            System.out.println("You failed to upload " + fileName + " because the file was empty.");
        }

        return null;
    }

    @Override
    public void download(HttpServletRequest request, HttpServletResponse response, Long id) {
        FileInfo fileInfo = fileInfoRepository.getOne(id);
        String fileFullPath = fileProperties.getUploadBasePath()
                + File.separator + fileInfo.getRelativePath()
                + File.separator + fileInfo.getFileName();
        File downloadFile = new File(fileFullPath);

        ServletContext context = request.getServletContext();

        // get MIME type of the file
        String mimeType = context.getMimeType(fileFullPath);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        response.setContentType(mimeType);

        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", fileInfo.getFileName());
        response.setHeader(headerKey, headerValue);

        OutputStream out = null;
        BufferedInputStream bis = null;
        try {
            out = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(downloadFile));
            byte[] buffer = new byte[8192 * 10];
            int len = 0;
            while ((len = bis.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Boolean remove(Long id) {
        FileInfo fileInfo = fileInfoRepository.getOne(id);
        String fileFullPath = fileProperties.getUploadBasePath()
                + File.separator + fileInfo.getRelativePath()
                + File.separator + fileInfo.getFileName();
        File destFile = new File(fileFullPath);
        if (destFile.exists()) {
            destFile.delete();
            fileInfoRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
