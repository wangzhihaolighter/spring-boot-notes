drop table if exists t_scheduling_task_info;
create table t_scheduling_task_info
(
    id                bigint(20)   not null auto_increment,
    task_name         varchar(255) not null,
    task_memo         varchar(255) not null,
    task_cron         varchar(255) not null,
    bean_name         varchar(255) not null,
    method_name       varchar(255) not null,
    enable            boolean      not null,
    allow_concurrency boolean      not null,
    primary key (`id`)
);

drop table if exists t_scheduling_task_log;
create table t_scheduling_task_log
(
    id         bigint(20)   not null auto_increment,
    task_id    bigint(20)   not null,
    start_time datetime     not null,
    end_time   datetime     not null,
    success    boolean      not null,
    error_msg  varchar(255) null,
    primary key (`id`)
);
