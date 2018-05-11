DROP TABLE IF EXISTS `people`;
CREATE TABLE USER (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	username varchar(255) not null,
	password varchar(255) not null,
	PRIMARY KEY (`id`)
);

insert into user (username, password) values ('飞翔的大白菜', '123456');
insert into user (username, password) values ('飞翔的小菠菜', '123456');
insert into user (username, password) values ('飞翔的小荠菜', '123456');
insert into user (username, password) values ('飞翔的小青菜', '123456');
insert into user (username, password) values ('飞翔的黄花菜', '123456');
insert into user (username, password) values ('飞翔的小韭菜', '123456');
insert into user (username, password) values ('飞翔的豆芽菜', '123456');
insert into user (username, password) values ('飞翔的大花菜', '123456');
insert into user (username, password) values ('飞翔的小芹菜', '123456');
insert into user (username, password) values ('飞翔的小咸菜', '123456');