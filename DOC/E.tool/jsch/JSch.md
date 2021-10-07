# JSch使用

## 资料

- 官网：[jcraft.com/jsch/](http://www.jcraft.com/jsch/)

- 官方示例：[jcraft.com/jsch/examples/](http://www.jcraft.com/jsch/examples/)

- Maven仓库：[Home » com.jcraft » jsch](https://mvnrepository.com/artifact/com.jcraft/jsch)

## 介绍

JSch是SSH2的纯Java实现。

JSch允许您连接到sshd服务器并使用端口转发，X11转发，文件传输等，并且可以将其功能集成到您自己的Java程序中。

## 实现原理

- 根据远程主机的IP地址，用户名和端口，建立会话（Session）
- 设置用户信息（包括密码和Userinfo），然后连接session
  - getSession()只是创建一个session，需要设置必要的认证信息之后，调用connect()才能建立连接。
- 设置channel上需要远程执行的Shell脚本，连接channel，就可以远程执行该Shell脚本
  - 调用openChannel(String type) 可以在session上打开指定类型的channel。该channel只是被初始化，使用前需要先调用connect()进行连接。
- 可以读取远程执行Shell脚本的输出，然后依次断开channel和session的连接

## Channel的类型

- shell - ChannelShell
- exec - ChannelExec
- direct-tcpip - ChannelDirectTCPIP
- sftp - ChannelSftp
- subsystem - ChannelSubsystem

## 整合使用

Maven项目中引入：

```xml
<!-- https://mvnrepository.com/artifact/com.jcraft/jsch -->
<dependency>
    <groupId>com.jcraft</groupId>
    <artifactId>jsch</artifactId>
    <version>0.1.55</version>
</dependency>
```

创建一个远程连接信息类，记录服务器登录信息

```java

@Data
public class Remote {
  private String host;
  private final int port = 22;
  private String user;
  private String password;
  private final String identity = "~/.ssh/id_rsa";
  private String passphrase;
}
```

JSch一个简单的工具类如下：

```java
import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * ssh工具类
 */
@Slf4j
public class JSchUtil {

    public static final int SESSION_TIMEOUT = 30000;
    public static final int CONNECT_TIMEOUT = 3000;

    /**
     * 获取会话
     *
     * @param remote 远程服务器信息
     * @return 会话
     * @throws JSchException /
     */
    public static Session getSession(Remote remote) throws JSchException {
        JSch jSch = new JSch();
        if (Files.exists(Paths.get(remote.getIdentity()))) {
            jSch.addIdentity(remote.getIdentity(), remote.getPassphrase());
        }
        Session session = jSch.getSession(remote.getUser(), remote.getHost(), remote.getPort());
        session.setPassword(remote.getPassword());
        session.setConfig("StrictHostKeyChecking", "no");
        return session;
    }

    /**
     * 远程执行执行命令
     *
     * @param session 会话
     * @param command 命令
     * @return /
     * @throws JSchException /
     */
    public static List<String> remoteExecute(Session session, String command) throws JSchException {
        log.debug(">> {}", command);
        List<String> resultLines = new ArrayList<>();
        ChannelExec channel = null;
        try {
            channel = openExecChannel(session);
            channel.setCommand(command);
            InputStream input = channel.getInputStream();
            channel.connect(CONNECT_TIMEOUT);
            try {
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(input));
                String inputLine;
                while ((inputLine = inputReader.readLine()) != null) {
                    log.debug("   {}", inputLine);
                    resultLines.add(inputLine);
                }
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (Exception e) {
                        log.error("JSch inputStream close error:", e);
                    }
                }
            }
        } catch (IOException e) {
            log.error("IOException:", e);
        } finally {
            disconnect(channel);
        }
        return resultLines;
    }

    /**
     * scp复制文件到远程指定路径
     *
     * @param session     会话
     * @param source      要复制的当前文件
     * @param destination 远程目标文件
     * @return 文件大小
     */
    public static long scpTo(Session session, String source, String destination) {
        FileInputStream fileInputStream = null;
        ChannelExec channel = null;
        try {
            channel = openExecChannel(session);
            OutputStream out = channel.getOutputStream();
            InputStream in = channel.getInputStream();
            boolean ptimestamp = false;
            String command = "scp";
            if (ptimestamp) {
                command += " -p";
            }
            command += " -t " + destination;
            channel.setCommand(command);
            channel.connect(CONNECT_TIMEOUT);
            if (checkAck(in) != 0) {
                return -1;
            }
            File _lfile = new File(source);
            if (ptimestamp) {
                command = "T " + (_lfile.lastModified() / 1000) + " 0";
                // The access time should be sent here,
                // but it is not accessible with JavaAPI ;-<
                command += (" " + (_lfile.lastModified() / 1000) + " 0\n");
                out.write(command.getBytes());
                out.flush();
                if (checkAck(in) != 0) {
                    return -1;
                }
            }
            //send "C0644 filesize filename", where filename should not include '/'
            long fileSize = _lfile.length();
            command = "C0644 " + fileSize + " ";
            if (source.lastIndexOf('/') > 0) {
                command += source.substring(source.lastIndexOf('/') + 1);
            } else {
                command += source;
            }
            command += "\n";
            out.write(command.getBytes());
            out.flush();
            if (checkAck(in) != 0) {
                return -1;
            }
            //send content of file
            fileInputStream = new FileInputStream(source);
            byte[] buf = new byte[1024];
            long sum = 0;
            while (true) {
                int len = fileInputStream.read(buf, 0, buf.length);
                if (len <= 0) {
                    break;
                }
                out.write(buf, 0, len);
                sum += len;
            }
            //send '\0'
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();
            if (checkAck(in) != 0) {
                return -1;
            }
            return sum;
        } catch (JSchException e) {
            log.error("scp to caught jsch exception, ", e);
        } catch (IOException e) {
            log.error("scp to caught io exception, ", e);
        } catch (Exception e) {
            log.error("scp to error, ", e);
        } finally {
            closeInputStream(fileInputStream);
            disconnect(channel);
        }
        return -1;
    }

    /**
     * scp获取远程文件到指定目录
     *
     * @param session     会话
     * @param source      远程文件
     * @param destination 获取到的目标文件
     * @return 文件大小
     */
    public static long scpFrom(Session session, String source, String destination) {
        FileOutputStream fileOutputStream = null;
        ChannelExec channel = null;
        try {
            channel = openExecChannel(session);
            channel.setCommand("scp -f " + source);
            OutputStream out = channel.getOutputStream();
            InputStream in = channel.getInputStream();
            channel.connect();
            byte[] buf = new byte[1024];
            //send '\0'
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();
            while (true) {
                if (checkAck(in) != 'C') {
                    break;
                }
            }
            //read '644 '
            in.read(buf, 0, 4);
            long fileSize = 0;
            while (true) {
                if (in.read(buf, 0, 1) < 0) {
                    break;
                }
                if (buf[0] == ' ') {
                    break;
                }
                fileSize = fileSize * 10L + (long) (buf[0] - '0');
            }
            String file = null;
            for (int i = 0; ; i++) {
                in.read(buf, i, 1);
                if (buf[i] == (byte) 0x0a) {
                    file = new String(buf, 0, i);
                    break;
                }
            }
            // send '\0'
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();
            // read a content of lfile
            if (Files.isDirectory(Paths.get(destination))) {
                fileOutputStream = new FileOutputStream(destination + File.separator + file);
            } else {
                fileOutputStream = new FileOutputStream(destination);
            }
            long sum = 0;
            while (true) {
                int len = in.read(buf, 0, buf.length);
                if (len <= 0) {
                    break;
                }
                sum += len;
                if (len >= fileSize) {
                    fileOutputStream.write(buf, 0, (int) fileSize);
                    break;
                }
                fileOutputStream.write(buf, 0, len);
                fileSize -= len;
            }
            return sum;
        } catch (JSchException e) {
            log.error("scp to caught jsch exception, ", e);
        } catch (IOException e) {
            log.error("scp to caught io exception, ", e);
        } catch (Exception e) {
            log.error("scp to error, ", e);
        } finally {
            closeOutputStream(fileOutputStream);
            disconnect(channel);
        }
        return -1;
    }

    /**
     * 远程编辑
     *
     * @param session 会话
     * @param source  目标文件
     * @param process 编辑命令集合
     * @return 是否成功
     */
    private static boolean remoteEdit(Session session, String source, Function<List<String>, List<String>> process) {
        InputStream in = null;
        OutputStream out = null;
        try {
            String fileName = source;
            int index = source.lastIndexOf('/');
            if (index >= 0) {
                fileName = source.substring(index + 1);
            }
            //backup source
            remoteExecute(session, String.format("cp %s %s", source, source + ".bak." + System.currentTimeMillis()));
            //scp from remote
            String tmpSource = System.getProperty("java.io.tmpdir") + session.getHost() + "-" + fileName;
            scpFrom(session, source, tmpSource);
            in = new FileInputStream(tmpSource);
            //edit file according function process
            String tmpDestination = tmpSource + ".des";
            out = new FileOutputStream(tmpDestination);
            List<String> inputLines = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String inputLine = null;
            while ((inputLine = reader.readLine()) != null) {
                inputLines.add(inputLine);
            }
            List<String> outputLines = process.apply(inputLines);
            for (String outputLine : outputLines) {
                out.write((outputLine + "\n").getBytes());
                out.flush();
            }
            //scp to remote
            scpTo(session, tmpDestination, source);
            return true;
        } catch (Exception e) {
            log.error("remote edit error, ", e);
            return false;
        } finally {
            closeInputStream(in);
            closeOutputStream(out);
        }
    }

    /**
     * 上传单个文件
     *
     * @param session   会话
     * @param in        文件流
     * @param directory 远程下载目录(以路径符号结束)
     * @param fileName  FTP服务器文件名称 如：xxx.txt ||xxx.txt.zip
     */
    public static boolean uploadFile(Session session, InputStream in, String directory, String fileName) {
        log.info(">>>>>>>>uploadFile--ftp上传文件开始>>>>>>>>>>>>>");
        ChannelSftp channel = null;
        try {
            channel = openSftpChannel(session);
            channel.connect(CONNECT_TIMEOUT);
            String[] folders = directory.split("/");
            try {
                for (int i = 0; i < folders.length; i++) {
                    if (i == 0 && folders[i].length() == 0) {
                        channel.cd("/");
                    } else if (folders[i].length() > 0) {
                        try {
                            channel.cd(folders[i]);
                        } catch (SftpException e) {
                            channel.mkdir(folders[i]);
                            channel.cd(folders[i]);
                        }
                    }
                }
            } catch (SftpException e) {
                log.error("ftp创建文件路径失败，路径为" + directory, e);
                return false;
            }

            try {
                channel.put(in, fileName);
            } catch (SftpException e) {
                log.error("sftp异常-->" + e.getMessage(), e);
                return false;
            }

            log.info(">>>>>>>>uploadFile--ftp上传文件结束>>>>>>>>>>>>>");
            log.info(">>>>>>>>ftp上传文件文件路径：{}，文件名：{}>>>>>>>>>>>>>", directory, fileName);

            return true;
        } catch (JSchException e) {
            log.error("JSch异常-->" + e.getMessage(), e);
            return false;
        } finally {
            closeInputStream(in);
            disconnect(channel);
        }
    }


    /**
     * ftp获取服务器目标文件的流文件
     *
     * @param channel   sftp连接
     * @param directory 文件夹
     * @param fileName  文件名
     * @return 文件流
     */
    public static InputStream stream(ChannelSftp channel, String directory, String fileName) {
        try {
            channel.connect(CONNECT_TIMEOUT);
            InputStream inputStream = channel.get(directory + "/" + fileName);
            log.info(">>>>>>>>ftp获取文件文件路径：{}，文件名：{}>>>>>>>>>>>>>", directory, fileName);
            return inputStream;
        } catch (SftpException e) {
            log.error("sftp异常-->" + e.getMessage());
            return null;
        } catch (JSchException e) {
            log.error("JSch异常-->" + e.getMessage());
            return null;
        }
    }


    /**
     * ftp删除远程文件
     *
     * @param session   会话
     * @param directory 文件夹
     * @param fileName  文件名
     * @return 是否成功
     */
    public static boolean deleteFile(Session session, String directory, String fileName) {
        log.info(">>>>>>>>deleteFile--ftp删除文件开始>>>>>>>>>>>>>");
        ChannelSftp channel = null;
        try {
            channel = openSftpChannel(session);
            channel.connect(CONNECT_TIMEOUT);
            channel.rm(directory + "/" + fileName);

            log.info(">>>>>>>>deleteFile--删除文件结束>>>>>>>>>>>>>");
            log.info(">>>>>>>>ftp删除文件文件路径：{}，文件名：{}>>>>>>>>>>>>>", directory, fileName);
        } catch (SftpException e) {
            log.error("ftp创建文件路径失败，路径为" + directory);
            return false;
        } catch (JSchException e) {
            log.error("JSch异常-->" + e.getMessage());
            return false;
        } finally {
            disconnect(channel);
        }

        return true;
    }

    public static Channel openChannel(Session session, String type) throws JSchException {
        if (!session.isConnected()) {
            session.connect(SESSION_TIMEOUT);
        }
        return session.openChannel(type);
    }

    public static ChannelSftp openSftpChannel(Session session) throws JSchException {
        return (ChannelSftp) openChannel(session, "sftp");
    }

    public static ChannelExec openExecChannel(Session session) throws JSchException {
        return (ChannelExec) openChannel(session, "exec");
    }

    /**
     * 登出
     *
     * @param session 会话
     */
    public static void disconnect(Session session) {
        if (session != null) {
            if (session.isConnected()) {
                try {
                    session.disconnect();
                    log.info("session disconnect successfully");
                } catch (Exception e) {
                    log.error("JSch session disconnect error:", e);
                }
            }
        }
    }

    /**
     * 关闭连接
     *
     * @param channel 渠道连接
     */
    public static void disconnect(Channel channel) {
        if (channel != null) {
            if (channel.isConnected()) {
                try {
                    channel.disconnect();
                    log.info("channel is closed already");
                } catch (Exception e) {
                    log.error("JSch channel disconnect error:", e);
                }
            }
        }
    }

    public static int checkAck(InputStream in) throws IOException {
        int b = in.read();
        // b may be 0 for success,
        //          1 for error,
        //          2 for fatal error,
        //          -1
        if (b == 0) {
            return b;
        }
        if (b == -1) {
            return b;
        }
        if (b == 1 || b == 2) {
            StringBuilder sb = new StringBuilder();
            int c;
            do {
                c = in.read();
                sb.append((char) c);
            }
            while (c != '\n');
            if (b == 1) { // error
                log.debug(sb.toString());
            }
            if (b == 2) { // fatal error
                log.debug(sb.toString());
            }
        }
        return b;
    }

    public static void closeInputStream(InputStream in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                log.error("Close input stream error." + e.getMessage());
            }
        }
    }

    public static void closeOutputStream(OutputStream out) {
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                log.error("Close output stream error." + e.getMessage());
            }
        }
    }

}
```
