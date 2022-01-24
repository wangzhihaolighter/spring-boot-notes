drop table if exists t_user;
create table t_user
(
    id          bigint(20) not null auto_increment,
    create_time timestamp null default current_timestamp,
    username    varchar(255) not null,
    password    varchar(255) not null,
    primary key (`id`)
);
