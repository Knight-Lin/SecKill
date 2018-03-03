--秒杀执行存储过程
DELIMITER $$  --;改变换行符成$$
--定义存储过程
--参数：in：输入参数，out：输出参数
--row_count():返回上一条修改类型sql（除了select）的影响行数
--0:未修改，>0:修改行数，<0:未执行sql
--存储过程名称前面是数据库名称
create procedure execute_seckill
(in v_seckill_id bigint,in v_phone bigint,
in v_kill_time timestamp,out r_result int)
begin
	declare insert_count int default 0;
	start transaction;
	insert ignore into success_killed
	(seckill_id,user_phone,state)
	values(v_seckill_id,v_phone,0);
	select row_count() into insert_count;
	if(insert_count=0) then
		rollback;
		set r_result = -1;
	elseif(insert_count<0)then
		rollback;
		set r_result = -2;
	else
		update seckill
		set number=number-1
		where seckill_id = v_seckill_id
			and end_time>v_kill_time
			and start_time<v_kill_time
			and number>0;
	select row_count() into insert_count;
	if(insert_count=0)then
		rollback;
		set r_result=0;
	elseif(insert_count<0)then
		rollback;
		set r_result = -2;
	else
		commit;
		set r_result = 1;
	end if;	
	end if;
end;
$$
--存储过程定义结束
DELIMITER ;
set @r_result=-3;
--执行存储过程
call execute_seckill(1007,18328582498,now(),@r_result);
--获取结果
select @r_result；

--存储过程：优化事务行级锁持有的时间
--不要过度依赖存储过程
--简单逻辑可以用存储过程
	
	