CREATE TABLE user (
	id varchar(60) not null,
	username varchar(255) not null,
	age int (11) not null,
	PRIMARY KEY (`id`),
	UNIQUE INDEX(`username`)
);
CREATE TABLE city (
	id varchar(60) not null,
	name varchar(255) not null,
	description varchar(255) not null,
	PRIMARY KEY (`id`),
	UNIQUE INDEX(`name`)
);
insert into user (id, username, age) values ('1','菜狗子',1);
insert into user (id, username, age) values ('2','包子',1);
insert into user (id, username, age) values ('3','杂粮馒头',1);
insert into city (id, name, description) values ('1','上海','金融中心');
insert into city (id, name, description) values ('2','北京','政治中心');
insert into city (id, name, description) values ('3','深圳','年轻，有活力的新城');