package com.example.file.manager.util;

import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Properties;

@Slf4j
public class SftpUtil {
    public static final String NO_FILE = "No such file";

    private ChannelSftp sftp = null;

    private Session sshSession = null;

    private final String username;

    private final String password;

    private final String host;

    private final int port;

    public SftpUtil(String username, String password, String host, int port) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
    }


    /**
     * 连接sftp服务器
     *
     * @return ChannelSftp sftp类型
     * GoPayException
     */
    public ChannelSftp connect() throws JSchException {
        log.info("FtpUtil-->connect--ftp连接开始>>>>>>host=" + host + ">>>port" + port + ">>>username=" + username);
        JSch jsch = new JSch();
        try {
            jsch.getSession(username, host, port);
            sshSession = jsch.getSession(username, host, port);
            log.info("ftp---Session created.");
            sshSession.setPassword(password);
            Properties properties = new Properties();
            properties.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(properties);
            sshSession.connect();
            log.info("ftp---Session connected.");
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            log.info("Opening Channel.");
            sftp = (ChannelSftp) channel;
            log.info("ftp---Connected to " + host);
        } catch (JSchException e) {
            throw new JSchException("FtpUtil-->connect异常" + e.getMessage());
        }
        return sftp;
    }

    /**
     * 下载单个文件
     * directory       ：远程下载目录(以路径符号结束)
     * remoteFileName  FTP服务器文件名称 如：xxx.txt ||xxx.txt.zip
     * localFile       本地文件路径 如 D:\\xxx.txt
     * <p>
     * GoPayException
     */
    public File downloadFile(String directory, String remoteFileName, String localFile) throws JSchException {
        log.info(">>>>>>>>FtpUtil-->downloadFile--ftp下载文件" + remoteFileName + "开始>>>>>>>>>>>>>");
        connect();
        File file = null;
        OutputStream output = null;
        try {
            file = new File(localFile);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            sftp.cd(directory);
            output = new FileOutputStream(file);
            sftp.get(remoteFileName, output);
            log.info("===DownloadFile:" + remoteFileName + " success from sftp.");
        } catch (SftpException e) {
            if (e.toString().equals(NO_FILE)) {
                log.info(">>>>>>>>FtpUtil-->downloadFile--ftp下载文件失败" + directory + remoteFileName + "不存在>>>>>>>>>>>>>");
            }
            log.info("ftp目录或者文件异常，检查ftp目录和文件" + e.toString());
        } catch (FileNotFoundException e) {
            log.info("本地目录异常，请检查" + file.getPath() + e.getMessage());
        } catch (IOException e) {
            log.info("创建本地文件失败" + file.getPath() + e.getMessage());

        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    log.info("Close stream error." + e.getMessage());

                }
            }
            disconnect();
        }

        log.info(">>>>>>>>FtpUtil-->downloadFile--ftp下载文件结束>>>>>>>>>>>>>");
        return file;
    }

    /**
     * 上传单个文件
     * directory      ：远程下载目录(以路径符号结束)
     * fileName       FTP服务器文件名称 如：xxx.txt ||xxx.txt.zip
     */
    public Boolean uploadFile(InputStream in, String directory, String fileName)
            throws JSchException {
        log.info(">>>>>>>>FtpUtil-->uploadFile--ftp上传文件开始>>>>>>>>>>>>>");
        connect();
        String[] folders = directory.split("/");
        try {
            for (int i = 0; i < folders.length; i++) {
                if (i == 0 && folders[i].length() == 0) {
                    sftp.cd("/");
                } else if (folders[i].length() > 0) {
                    try {
                        sftp.cd(folders[i]);
                    } catch (SftpException e) {
                        sftp.mkdir(folders[i]);
                        sftp.cd(folders[i]);
                    }
                }
            }
        } catch (Exception e) {
            log.info("ftp创建文件路径失败，路径为" + directory);
            return false;

        }
        try {
            sftp.put(in, fileName);
        } catch (SftpException e) {
            log.info("sftp异常-->" + e.getMessage());
            return false;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.info("Close stream error." + e.getMessage());
                }
            }
            disconnect();
        }
        log.info(">>>>>>>>FtpUtil-->uploadFile--ftp上传文件结束>>>>>>>>>>>>>");
        log.info(">>>>>>>>FtpUtil-->ftp上传文件文件路径：{}，文件名：{}>>>>>>>>>>>>>", directory, fileName);
        return true;
    }

    /**
     * 关闭连接
     */
    public void disconnect() {
        if (this.sftp != null) {
            if (this.sftp.isConnected()) {
                this.sftp.disconnect();
                this.sftp = null;
                log.info("sftp is closed already");
            }
        }
        if (this.sshSession != null) {
            if (this.sshSession.isConnected()) {
                this.sshSession.disconnect();
                this.sshSession = null;
                log.info("sshSession is closed already");
            }
        }
    }

    /**
     * 获取流文件
     *
     * @param ins
     * @param file
     */
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static InputStream stream(String path, String filename, String fileUsername, String filePassword, String
            fileIp, int port) {
        try {
            SftpUtil ftpUtil = new SftpUtil(fileUsername, filePassword, fileIp, port);
            ChannelSftp channel = ftpUtil.connect();
            System.out.println(channel.ls(path));
            return channel.get(path + filename);
        } catch (JSchException | SftpException e) {
            log.info("文件获取异常：" + filename + ":" + e.getMessage());
            return null;
        }
    }

    public static InputStream stream(String filePath, String fileServerUsername, String fileServerPassword, String
            fileServerIp, int fileServerPort) {
        try {
            SftpUtil ftpUtil = new SftpUtil(fileServerUsername, fileServerPassword, fileServerIp, fileServerPort);
            ChannelSftp channel = ftpUtil.connect();
            return channel.get(filePath);
        } catch (JSchException | SftpException e) {
            log.info("服务器[{}]文件[{}]获取异常：{}", fileServerIp, filePath, e.getMessage());
            return null;
        }
    }

}