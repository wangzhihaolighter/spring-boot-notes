drop table if exists user;
create table user
(
    id       bigint(20)   not null auto_increment,
    username varchar(255) not null,
    password varchar(255) not null,
    primary key (id)
);

insert into user (username, password)
values ('apple', '123456'),
       ('google', '123456'),
       ('microsoft', '123456');