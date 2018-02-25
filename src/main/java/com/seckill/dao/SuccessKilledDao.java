package com.seckill.dao;

import org.apache.ibatis.annotations.Param;

import com.seckill.entity.SuccessKilled;

public interface SuccessKilledDao {
	
	int insertSuccessKilled(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);

	SuccessKilled queryByIdWithSeckill(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);
}
