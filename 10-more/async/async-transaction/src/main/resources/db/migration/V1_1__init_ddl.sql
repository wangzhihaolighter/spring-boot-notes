DROP TABLE IF EXISTS `USER`;
CREATE TABLE USER (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	username varchar(255) not null,
	password varchar(255) not null,
	PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `USER_INFO`;
CREATE TABLE USER_INFO (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	user_id bigint(20) not null,
	country varchar(255) not null,
	province varchar(255) not null,
	PRIMARY KEY (`id`),
	UNIQUE INDEX (`user_id`)
);

DROP TABLE IF EXISTS `USER_ROLE`;
CREATE TABLE USER_ROLE (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	user_id bigint(20) not null,
	role varchar(255) not null,
	PRIMARY KEY (`id`)
);