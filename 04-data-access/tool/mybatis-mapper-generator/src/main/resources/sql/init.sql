CREATE TABLE USER (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	username varchar(255) not null,
	password varchar(255) not null,
	PRIMARY KEY (`id`)
);

insert into user (username, password) values ('飞翔的大白菜', '123456');