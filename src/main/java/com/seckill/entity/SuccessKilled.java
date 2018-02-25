package com.seckill.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class SuccessKilled implements Serializable{

	private static final long serialVersionUID = 5198056981890871984L;
	
	private long seckillId;
	private short state;
	private Date createTime;
	private Seckill seckill;
	
	public SuccessKilled(long seckillId, short state, Date createTime, Seckill seckill) {
		this.seckillId = seckillId;
		this.state = state;
		this.createTime = createTime;
		this.seckill = seckill;
	}
	
	public SuccessKilled() {
		
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public short getState() {
		return state;
	}

	public void setState(short state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Seckill getSeckill() {
		return seckill;
	}

	public void setSeckill(Seckill seckill) {
		this.seckill = seckill;
	}

	@Override
	public String toString() {
		return "SuccessKilled [seckillId=" + seckillId + ", state=" + state + ", createTime=" + createTime
				+ ", seckill=" + seckill + "]";
	};
	
	
	
	
	
}
