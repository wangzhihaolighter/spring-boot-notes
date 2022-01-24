drop table if exists student_info;
create table student_info
(
    student_number varchar(255) not null comment '学号',
    name           varchar(255) default null comment '姓名',
    age            int(3)       default null comment '年龄',
    grade          varchar(255) default null comment '年级',
    classroom      varchar(255) default null comment '班级',
    photo_url      varchar(255) default null comment '照片',
    primary key (`student_number`)
);
