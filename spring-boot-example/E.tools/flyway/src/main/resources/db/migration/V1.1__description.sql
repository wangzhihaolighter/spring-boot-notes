-- 版本升级的迁移示例

drop table if exists user;
create table user
(
    id       bigint(20)   not null auto_increment,
    username varchar(255) not null,
    password varchar(255) not null,
    primary key (`id`)
);

insert into user
values (1, '大白菜', '123')
     , (2, '胡萝卜', '456')
;