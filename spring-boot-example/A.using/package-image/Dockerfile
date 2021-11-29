# 基础镜像
FROM java:8

# 作者信息
MAINTAINER lighter

# 创建工作目录
RUN mkdir -p /deploy/app

# 切换工作目录
WORKDIR /deploy/app

# 容器开放端口
EXPOSE 8080

# 参数
ARG JAR_FILE

# 添加外部文件
ADD ./target/${JAR_FILE} ./app.jar

# 容器启动执行命令
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/deploy/app/app.jar"]