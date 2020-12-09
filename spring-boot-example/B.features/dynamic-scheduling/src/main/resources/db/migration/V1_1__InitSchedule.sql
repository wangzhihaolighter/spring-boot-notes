DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job`
(
    `id`                  BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `create_time`         DATETIME     NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `last_update_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `job_name`            VARCHAR(100) NOT NULL COMMENT '任务名',
    `job_memo`            VARCHAR(255) NOT NULL COMMENT '任务说明',
    `job_cron_expression` VARCHAR(255) NOT NULL COMMENT 'corn表达式',
    `enable`              BIT(1)       NOT NULL DEFAULT 1 COMMENT '是否启用 0-关闭 1-启用',
    `repeatable`          BIT(1)       NOT NULL DEFAULT 1 COMMENT '是否可重复执行（多实例情况） 0-否 1-是',
    `job_type`            TINYINT(4)   NOT NULL COMMENT '任务类别。0-spring bean method、1-HTTP',
    `job_ext`             VARCHAR(500) NOT NULL COMMENT '任务额外信息',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_job_name` (`job_name`)
) COMMENT '定时任务表';

DROP TABLE IF EXISTS `schedule_job_log`;
CREATE TABLE `schedule_job_log`
(
    `id`                  BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `create_time`         DATETIME     NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `job_id`              BIGINT(20)   NOT NULL COMMENT '任务ID',
    `job_name`            VARCHAR(100) NOT NULL COMMENT '任务名',
    `job_memo`            VARCHAR(255) NOT NULL COMMENT '任务说明',
    `job_cron_expression` VARCHAR(255) NOT NULL COMMENT 'corn表达式',
    `job_type`            TINYINT(4)   NOT NULL COMMENT '任务类别。0-spring bean method、1-HTTP',
    `job_ext`             VARCHAR(500) NOT NULL COMMENT '任务额外信息',
    `spend_time`          BIGINT(11)   NOT NULL COMMENT '时长，单位：毫秒',
    `success`             BIT(1)       NOT NULL DEFAULT 1 COMMENT '是否成功：0-失败 1-成功',
    `exception_detail`    TEXT         NULL     DEFAULT NULL COMMENT '失败异常信息',
    PRIMARY KEY (`id`),
    KEY `idx_job_id` (`job_id`),
    KEY `idx_job_name` (`job_name`)
) COMMENT '定时任务日志表';