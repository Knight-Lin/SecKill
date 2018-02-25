create database seckill;

use seckill;

create table seckill(
seckill_id bigint not null auto_increment comment '��ɱ��ƷID',
name varchar(120) not null comment '��Ʒ����',
number int not null comment '��Ʒ�������',
start_time timestamp not null default current_timestamp() comment '��ɱ��ʼʱ��',
end_time timestamp not null default current_timestamp() comment '��ɱ����ʱ��',
create_time timestamp not null default current_timestamp() comment '��ɱ��Ʒ����ʱ��',
primary key (seckill_id),
key idx_start_time(start_time),
key idx_end_time(end_time)
)engine=InnoDB default charset=UTF8 auto_increment=1000 comment='��ɱ��Ʒ����';

insert into seckill(name,number,start_time,end_time)
values
('1000Ԫ��ɱiphone6',100,'2016-5-22 00:00:00','2016-5-23 00:00:00'),
('500Ԫ��ɱiPad2',200,'2016-5-22 00:00:00','2016-5-23 00:00:00'),
('300Ԫ��ɱС��4',300,'2016-5-22 00:00:00','2016-5-23 00:00:00'),
('200Ԫ��ɱ����note',400,'2016-5-22 00:00:00','2016-5-23 00:00:00');

create table success_killed(
seckill_id bigint not null comment '��ɱ��ƷID',
user_phone bigint not null comment '�û��绰����',
state tinyint not null default 0 comment '״̬��ʾ -1��Ч 0�ɹ� 1�Ѹ���',
create_time timestamp not null comment '��ɱ����ʱ��',
primary key (seckill_id,user_phone),
key idx_create_time(create_time)
)engine=InnoDB default charset=utf8 comment='��ɱ�ɹ���ϸ��';