CREATE TABLE student_info
(
    student_number varchar(255) NOT NULL COMMENT '学号',
    name           varchar(255) DEFAULT NULL COMMENT '姓名',
    age            int(3)       DEFAULT NULL COMMENT '年龄',
    grade          varchar(255) DEFAULT NULL COMMENT '年级',
    classroom      varchar(255) DEFAULT NULL COMMENT '班级',
    photo_url      varchar(255) DEFAULT NULL COMMENT '照片',
    PRIMARY KEY (`student_number`)
);

insert into student_info (student_number, name, age, grade, classroom, photo_url)
values ('001', '埼玉', 26, '博士二年级', '一击男', 'https://i.loli.net/2019/09/22/HaWN8jXVuPGwdri.jpg'),
       ('002', '蒙奇·D·路飞', 18, '高中三年级', '海贼王', 'https://i.loli.net/2019/09/22/ZaIMgPrxstYk4ze.jpg'),
       ('003', '漩涡鸣人', 22, '大学四年级', '火影忍者', 'https://i.loli.net/2019/09/22/u2GvA7CHp35qWlE.jpg')
;