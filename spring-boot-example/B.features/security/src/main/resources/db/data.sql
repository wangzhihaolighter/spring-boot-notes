-- password: BCryptPasswordEncoder("123456")
insert into t_user (id, username, password)
values (1, 'admin', '$2a$10$rLN0GnC9pUcUTghfjVwSHe4.Gqk7BbTGRDNqZKy0thZ9S3MxTXSg.'),
       (2, 'test', '$2a$10$rLN0GnC9pUcUTghfjVwSHe4.Gqk7BbTGRDNqZKy0thZ9S3MxTXSg.');

insert into t_role (id, name, description)
values (1, 'admin', '管理员'),
       (2, 'test', '测试');

insert into t_permission (id, url, method, description)
values (1, '/user', 'GET', '用户查询'),
       (2, '/user', 'POST', '用户新增'),
       (3, '/user', 'PUT', '用户更新'),
       (4, '/user', 'DELETE', '用户删除');

insert into t_user_role (user_id, role_id)
values (1, 1),
       (2, 2);

insert into t_role_permission (role_id, permission_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (2, 1);
