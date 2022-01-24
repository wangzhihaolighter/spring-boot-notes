drop table if exists t_user;
create table t_user
(
    id          bigint(20) not null auto_increment,
    create_time timestamp null default current_timestamp,
    username    varchar(255) not null,
    password    varchar(255) not null,
    primary key (`id`)
);

drop table if exists t_role;
create table t_role
(
    id          bigint(20) not null auto_increment,
    create_time timestamp null default current_timestamp,
    name        varchar(255) not null,
    description varchar(255) not null,
    primary key (`id`)
);

drop table if exists t_permission;
create table t_permission
(
    id          bigint(20) not null auto_increment,
    create_time timestamp null default current_timestamp,
    url         varchar(255) not null,
    method      varchar(255) not null,
    description varchar(255) not null,
    primary key (`id`)
);

drop table if exists t_user_role;
create table t_user_role
(
    id          bigint(20) not null auto_increment,
    create_time timestamp null default current_timestamp,
    user_id     bigint(20) not null,
    role_id     bigint(20) not null,
    primary key (`id`)
);

drop table if exists t_role_permission;
create table t_role_permission
(
    id            bigint(20) not null auto_increment,
    create_time   timestamp null default current_timestamp,
    role_id       bigint(20) not null,
    permission_id bigint(20) not null,
    primary key (`id`)
);
