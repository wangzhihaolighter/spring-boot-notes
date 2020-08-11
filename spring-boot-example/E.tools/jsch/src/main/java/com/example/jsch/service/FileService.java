package com.example.jsch.service;

import com.example.jsch.config.FileProperties;
import com.example.jsch.dao.FileInfoRepository;
import com.example.jsch.entity.FileInfo;
import com.example.jsch.util.JSchUtil;
import com.example.jsch.util.Remote;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    private final FileInfoRepository fileInfoRepository;
    private final FileProperties fileProperties;
    private final Remote remote;

    public FileService(FileInfoRepository fileInfoRepository, FileProperties fileProperties, Remote remote) {
        this.fileInfoRepository = fileInfoRepository;
        this.fileProperties = fileProperties;
        this.remote = remote;
    }

    /**
     * 查询所有文件信息
     *
     * @return 文件信息
     */
    public List<FileInfo> getAll() {
        return fileInfoRepository.findAll();
    }

    /**
     * ftp形式上传文件
     *
     * @param file 上传文件
     * @return 文件记录id
     */
    public Long upload(MultipartFile file) {
        LocalDateTime now = LocalDateTime.now();
        // 获取原始名字
        String fileName = file.getOriginalFilename();
        // 获取后缀名（这里不做校验）
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 文件新名字
        String fileNewName = UUID.randomUUID().toString().replace("-", "") + suffixName;
        // 生成文件路径
        String fileRelativePath = now.getYear()
                + "/" + now.getMonthValue()
                + "/" + now.getDayOfMonth()
                + "/" + now.getHour();
        String fileFullPath = fileProperties.getUploadBasePath() + "/" + fileRelativePath;

        if (!file.isEmpty()) {
            Session session = null;
            try {
                session = JSchUtil.getSession(remote);
                boolean b = JSchUtil.uploadFile(session, file.getInputStream(), fileFullPath, fileNewName);
                if (b) {
                    System.out.println("You successfully uploaded " + fileName + " into " + fileFullPath + "/" + fileNewName);

                    FileInfo fileInfo = new FileInfo();
                    fileInfo.setFileName(fileNewName);
                    fileInfo.setOriginFileName(fileName);
                    fileInfo.setExt(suffixName);
                    fileInfo.setRelativePath(fileRelativePath);
                    fileInfo.setCreateTime(now);
                    fileInfoRepository.saveAndFlush(fileInfo);
                    return fileInfo.getId();
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("You failed to upload " + fileName + " => " + e.getMessage());
            } finally {
                JSchUtil.disconnect(session);
            }
        } else {
            System.out.println("You failed to upload " + fileName + " because the file was empty.");
        }

        return null;
    }

    /**
     * 下载文件
     *
     * @param request  /
     * @param response /
     * @param id       文件记录id
     */
    public void download(HttpServletRequest request, HttpServletResponse response, Long id) {
        Session session = null;
        ChannelSftp channel = null;
        InputStream fileInputStream = null;
        BufferedInputStream bis = null;
        OutputStream out = null;
        try {
            // get ssh session
            session = JSchUtil.getSession(remote);

            //获取文件信息，这里不做校验
            FileInfo fileInfo = fileInfoRepository.getOne(id);
            String fileFullPath = fileProperties.getUploadBasePath() + "/" + fileInfo.getRelativePath();
            String fileAbsolutePath = fileFullPath + "/" + fileInfo.getFileName();

            ServletContext context = request.getServletContext();

            // get MIME type of the file
            String mimeType = context.getMimeType(fileAbsolutePath);
            if (mimeType == null) {
                // set to binary type if MIME mapping not found
                mimeType = "application/octet-stream";
            }
            response.setContentType(mimeType);

            // set headers for the response
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", fileInfo.getFileName());
            response.setHeader(headerKey, headerValue);

            try {
                out = response.getOutputStream();
                channel = JSchUtil.openSftpChannel(session);
                fileInputStream = JSchUtil.stream(channel, fileFullPath, fileInfo.getFileName());
                assert fileInputStream != null;
                bis = new BufferedInputStream(fileInputStream);
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JSchUtil.disconnect(channel);
            JSchUtil.disconnect(session);
            JSchUtil.closeInputStream(fileInputStream);
            JSchUtil.closeInputStream(bis);
            JSchUtil.closeOutputStream(out);
        }

    }

    /**
     * 删除文件
     *
     * @param id 文件记录id
     * @return 是否成功
     */
    public Boolean remove(Long id) {
        Session session = null;
        try {
            // get ssh session
            session = JSchUtil.getSession(remote);

            //获取文件信息，这里不做校验
            FileInfo fileInfo = fileInfoRepository.getOne(id);
            String fileFullPath = fileProperties.getUploadBasePath() + "/" + fileInfo.getRelativePath();

            boolean res = JSchUtil.deleteFile(session, fileFullPath, fileInfo.getFileName());
            if (res) {
                fileInfoRepository.deleteById(id);
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JSchUtil.disconnect(session);
        }
        return false;
    }

}
