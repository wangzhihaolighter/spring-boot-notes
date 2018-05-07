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
	`method` varchar(255) not null COMMENT 'http请求方式',
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