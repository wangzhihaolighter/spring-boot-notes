DROP TABLE IF EXISTS `people`;
CREATE TABLE `people` (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	username varchar(255) not null,
	password varchar(255) not null,
	PRIMARY KEY (`id`)
);

insert into `people` (username, password) values ('傲娇的大白菜', '123456');
insert into `people` (username, password) values ('傲娇的小菠菜', '123456');
insert into `people` (username, password) values ('傲娇的小荠菜', '123456');
insert into `people` (username, password) values ('傲娇的小青菜', '123456');
insert into `people` (username, password) values ('傲娇的黄花菜', '123456');
insert into `people` (username, password) values ('傲娇的小韭菜', '123456');
insert into `people` (username, password) values ('傲娇的豆芽菜', '123456');
insert into `people` (username, password) values ('傲娇的大花菜', '123456');
insert into `people` (username, password) values ('傲娇的小芹菜', '123456');
insert into `people` (username, password) values ('傲娇的小咸菜', '123456');