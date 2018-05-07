-- 创建默认用户
-- admin/123456
insert into SYSTEM_USER (id, username, password,flag) values (1,'admin','e10adc3949ba59abbe56e057f20f883e',1);
-- lighter/123456
insert into SYSTEM_USER (id, username, password,flag) values (2,'lighter','e10adc3949ba59abbe56e057f20f883e',1);
-- 创建默认角色
insert into SYSTEM_ROLE (id, name) values (1,'ADMIN');
insert into SYSTEM_ROLE (id, name) values (2,'DEVELOPER');
insert into SYSTEM_ROLE (id, name) values (3,'USER');
-- 创建默认权限
insert into SYSTEM_PERMISSION (id, pid, name, description, url, method) values (1,null,'welcome','欢迎','/welcome','GET');
insert into SYSTEM_PERMISSION (id, pid, name, description, url, method) values (2,null,'manage','系统管理','/system/**','ALL');
insert into SYSTEM_PERMISSION (id, pid, name, description, url, method) values (3,null,'api docs','系统接口文档','/swagger-ui.html','GET');
-- 创建默认用户角色关联
insert into SYSTEM_USER_ROLE (id, system_user_id, system_role_id) values (1,1,1);
insert into SYSTEM_USER_ROLE (id, system_user_id, system_role_id) values (2,2,2);
-- 创建默认角色权限关联
insert into SYSTEM_ROLE_PERMISSION (id, system_role_id, system_permission_id) values (1,1,1);
insert into SYSTEM_ROLE_PERMISSION (id, system_role_id, system_permission_id) values (2,1,2);
insert into SYSTEM_ROLE_PERMISSION (id, system_role_id, system_permission_id) values (3,1,3);
insert into SYSTEM_ROLE_PERMISSION (id, system_role_id, system_permission_id) values (4,2,1);
insert into SYSTEM_ROLE_PERMISSION (id, system_role_id, system_permission_id) values (5,2,3);