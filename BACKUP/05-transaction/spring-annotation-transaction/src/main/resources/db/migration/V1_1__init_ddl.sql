DROP TABLE IF EXISTS `USER`;
CREATE TABLE USER (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	username varchar(255) not null,
	password varchar(255) not null,
	PRIMARY KEY (`id`)
);