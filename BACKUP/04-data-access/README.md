# 04-data-access
说明：使用spring boot完成数据获取和处理

## pool:常用的数据库连接池
- HikariCP
- druid

## ORM:常用的ORM框架
- JdbcTemplate
- spring data jpa
    - 单表数据的基本操作 - 继承jpa通用接口，获取通用方法
    - 自定义简单查询 - jpa根据方法名来自动生成SQL
    - 分页查询/排序查询
    - 自定义SQL查询
- mybatis
    - 基本配置
    - 单表数据的基本操作
    - 高级查询
    - 批处理

## multiple dataSources:多数据源配置
- 多数据源配置 - 方式一：不同DAO层对应不同的数据源
    - 用途：倾向于访问多个数据源的数据
- 多数据源配置 - 方式二：AOP方式，约定service方法名动态设置数据源
    - 用途：倾向于主从配置，读操作访问从库，写操作访问主库
- 多数据源配置 - 方式三：AOP+注解方式,通过注解动态设置数据源
    - 用途：倾向于主从配置，读操作访问从库，写操作访问主库

## NoSQL:常用的非关系型数据库
- redis
    - redis java客户端选择
    - redis参数配置
    - 数据操作
- MongoDB
    - docker拉取MongoDB镜像
    - 启动MongoDB，创建管理员
    - 使用mongoTemplate实现对MongoDB的数据操作

## tool:常用的数据层管理框架和工具
- flyway:使用Flyway来管理数据库版本，flyway执行流程如下：
    - flyway包下`org.flywaydb.core.Flyway`加载flyway相关配置
    - spring autoconfigure中flyway初始化类`org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer`，实现InitializingBean，afterPropertiesSet方法中判断flyway.migrate数据库迁移方法的执行
    - spring autoconfigure中`org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration`自动配置flyway相关bean
- mybatis generator自动生成代码插件
- mybatis增强库 - 通用mapper
- mybatis增强库 - pageHelper分页插件
