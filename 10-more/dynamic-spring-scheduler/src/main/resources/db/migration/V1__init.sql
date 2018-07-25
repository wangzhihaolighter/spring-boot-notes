CREATE TABLE `simple_schedule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(255) NOT NULL COMMENT '任务名',
  `task_memo` varchar(255) NOT NULL COMMENT '任务说明',
  `task_cron` varchar(255) NOT NULL COMMENT 'corn表达式',
  `is_enable` bit(1) NOT NULL DEFAULT 1 COMMENT '是否启用',
  `is_dealt` bit(1) NOT NULL DEFAULT 0 COMMENT '是否处理',
  `last_update_on` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `task_name` (`task_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

INSERT INTO simple_schedule ( id, task_name, task_memo, task_cron )
VALUES
	( 1, 'testTask', '测试', '0 1/1 * * * ?' ) , ( 2, 'doSomethingTask', '做些事', '0 1/1 * * * ?' );