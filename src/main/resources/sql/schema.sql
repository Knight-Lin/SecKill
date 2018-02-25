create database seckill;

use seckill;

create table seckill(
seckill_id bigint not null auto_increment comment '秒杀商品ID',
name varchar(120) not null comment '商品名称',
number int not null comment '商品库存数量',
start_time timestamp not null default current_timestamp() comment '秒杀开始时间',
end_time timestamp not null default current_timestamp() comment '秒杀结束时间',
create_time timestamp not null default current_timestamp() comment '秒杀商品创建时间',
primary key (seckill_id),
key idx_start_time(start_time),
key idx_end_time(end_time)
)engine=InnoDB default charset=UTF8 auto_increment=1000 comment='秒杀商品库存表';

insert into seckill(name,number,start_time,end_time)
values
('1000元秒杀iphone6',100,'2016-5-22 00:00:00','2016-5-23 00:00:00'),
('500元秒杀iPad2',200,'2016-5-22 00:00:00','2016-5-23 00:00:00'),
('300元秒杀小米4',300,'2016-5-22 00:00:00','2016-5-23 00:00:00'),
('200元秒杀红米note',400,'2016-5-22 00:00:00','2016-5-23 00:00:00');

create table success_killed(
seckill_id bigint not null comment '秒杀商品ID',
user_phone bigint not null comment '用户电话号码',
state tinyint not null default 0 comment '状态表示 -1无效 0成功 1已付款',
create_time timestamp not null comment '秒杀创建时间',
primary key (seckill_id,user_phone),
key idx_create_time(create_time)
)engine=InnoDB default charset=utf8 comment='秒杀成功明细表';