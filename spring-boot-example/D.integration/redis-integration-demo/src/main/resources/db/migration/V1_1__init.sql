CREATE TABLE `user`
(
    id       bigint(20)   NOT NULL AUTO_INCREMENT,
    username varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
);

insert into user
values (1, '大白菜', '123')
     , (2, '胡萝卜', '456')
;