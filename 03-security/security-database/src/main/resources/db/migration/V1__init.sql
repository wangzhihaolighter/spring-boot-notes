DROP TABLE IF EXISTS `SYSTEM_USER`;
DROP TABLE IF EXISTS `SYSTEM_ROLE`;
DROP TABLE IF EXISTS `SYSTEM_PERMISSION`;
DROP TABLE IF EXISTS `SYSTEM_USER_ROLE`;
DROP TABLE IF EXISTS `SYSTEM_ROLE_PERMISSION`;
-- 系统用户表
CREATE TABLE SYSTEM_USER (
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	`username` varchar(255) not null COMMENT '系统用户名',
	`password` varchar(255) not null COMMENT '密码',
	`name` varchar(255) default null COMMENT '真实姓名',
	`email` varchar(255) default null COMMENT '邮箱',
	`flag` bit(1) DEFAULT 0 COMMENT '是否启用',
	PRIMARY KEY (`id`)
);
-- 系统角色表
CREATE TABLE SYSTEM_ROLE (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
	`name` varchar(255) not null COMMENT '系统角色名',
	`roleLevel` int(11) default 0 COMMENT '角色等级',
	`description` varchar(255) default null COMMENT '描述',
	`menuItems` varchar(255) default null COMMENT '菜单项',
	`flag` bit(1) DEFAULT 1 COMMENT '是否启用',
	PRIMARY KEY (`id`)
);
-- 系统权限表
CREATE TABLE SYSTEM_PERMISSION (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) default null COMMENT '父权限id',
	`name` varchar(255) not null COMMENT '权限名',
	`description` varchar(255) not null COMMENT '描述',
	`url` varchar(255) not null COMMENT '资源路径',
	`flag` bit(1) DEFAULT 1 COMMENT '是否启用',
	PRIMARY KEY (`id`)
);
-- 用户角色中间表
CREATE TABLE SYSTEM_USER_ROLE (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  system_user_id BIGINT not null,
  system_role_id BIGINT not null,
  PRIMARY KEY (`id`)
);
-- 角色权限中间表
CREATE TABLE SYSTEM_ROLE_PERMISSION (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  system_role_id BIGINT not null,
  system_permission_id BIGINT not null,
  PRIMARY KEY (`id`)
);

-- 创建默认用户
insert into SYSTEM_USER (id, username, password,flag) values (1,'admin','123456',1);
insert into SYSTEM_USER (id, username, password,flag) values (2,'lighter','666666',1);
-- 创建默认角色
insert into SYSTEM_ROLE (id, name) values (1,'admin');
insert into SYSTEM_ROLE (id, name) values (2,'developer');
-- 创建默认权限
insert into SYSTEM_PERMISSION (id, pid, name, description, url) values (1,null,'welcome','欢迎','/');
insert into SYSTEM_PERMISSION (id, pid, name, description, url) values (2,null,'manage','系统管理页','/manage');
insert into SYSTEM_PERMISSION (id, pid, name, description, url) values (3,null,'api docs','系统接口文档','/api/docs');
-- 创建默认用户角色关联
insert into SYSTEM_USER_ROLE (id, system_user_id, system_role_id) values (1,1,1);
insert into SYSTEM_USER_ROLE (id, system_user_id, system_role_id) values (2,2,2);
-- 创建默认角色权限关联
insert into SYSTEM_ROLE_PERMISSION (id, system_role_id, system_permission_id) values (1,1,1);
insert into SYSTEM_ROLE_PERMISSION (id, system_role_id, system_permission_id) values (2,1,2);
insert into SYSTEM_ROLE_PERMISSION (id, system_role_id, system_permission_id) values (3,1,3);
insert into SYSTEM_ROLE_PERMISSION (id, system_role_id, system_permission_id) values (4,2,1);
insert into SYSTEM_ROLE_PERMISSION (id, system_role_id, system_permission_id) values (5,2,3);