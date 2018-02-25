package com.seckill.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.seckill.dao.SeckillDao;
import com.seckill.dao.SuccessKilledDao;
import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.entity.SuccessKilled;
import com.seckill.enums.SeckillStateEnum;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;
import com.seckill.service.SeckillService;
@Service
public class SeckillServiceImpl implements SeckillService{
	@Resource
	private SeckillDao seckillDao;
	@Resource
	private SuccessKilledDao successKilledDao;
	private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
	private final String salt = "huigy234fsdgf45423fsd";
	
	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0,4);
	}

	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	public Exposer exportSeckillUrl(long seckillId) {
		Seckill seckill  = seckillDao.queryById(seckillId);
		if(seckill==null) {
			return new Exposer(false,seckillId);
		}
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		Date nowTime = new Date();
		if(nowTime.getTime()<startTime.getTime()||nowTime.getTime()>endTime.getTime()){
			
			return new Exposer(false,seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
			
		}
		String md5 = getMD5(seckillId);
		return new Exposer(true,md5,seckillId);
	}
	
	private String getMD5(long seckillId) {
		String base = seckillId+"/"+salt;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}
	
	@Transactional
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillCloseException {
		if(md5==null||!md5.equals(getMD5(seckillId))) {
			throw new SeckillException("seckill data rewrite");
		}
		Date nowTime = new Date();
		try {
		int updateCount = seckillDao.reduceNumber(seckillId,nowTime);
		if(updateCount<=0) {
			throw new SeckillCloseException("seckill is close");
		}else {
			int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
			if(insertCount<=0) {
				throw new RepeatKillException("seckill repeat");
			}else {
				SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
				return new SeckillExecution(seckillId,SeckillStateEnum.SUCCESS,successKilled);
			}
			
		}
		}catch(SeckillCloseException e1) {
			throw e1;
		}catch(RepeatKillException e2) {
			throw e2;
		}catch(Exception e3) {
			logger.error(e3.getMessage(),e3);
			//所有编译期异常转化为运行期异常
			throw new SeckillException("seckill inner error:"+e3.getMessage());
		}
		
	}
	
}
