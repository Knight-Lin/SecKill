package com.seckill.service;

import java.util.List;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;

/**
 * 站在使用者的角度设计接口
方法定义粒度明确
参数越简练越直接
返回类型要友好/异常
 * @author Administrator
 *
 */


public interface SeckillService {
	
	List<Seckill> getSeckillList();
	
	Seckill getById(long seckillId);
	
	/**
	 * 秒杀开始时输出秒杀地址
	 * 否则输出系统时间和秒杀时间
	 * @param seckillId
	 */
	Exposer exportSeckillUrl(long seckillId);
	
	SeckillExecution executeSeckill(long seckillId,long userPhone ,String md5)
	throws SeckillException,RepeatKillException,SeckillCloseException;
	
	SeckillExecution executeSeckillProcedure(long seckillId,long userPhone ,String md5);
	
	
}
