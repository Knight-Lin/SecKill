package com.seckill.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.seckill.entity.Seckill;

public interface SeckillDao {
	
	int reduceNumber(@Param("seckillId")long seckillId,@Param("killTime")Date nowTime);
	
	Seckill queryById(@Param("seckillId")long seckillId);
	
	List<Seckill> queryAll(@Param("offset")int offset,@Param("limit")int limit);
	
	
}
