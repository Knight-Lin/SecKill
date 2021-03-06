package com.seckill.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class Seckill implements Serializable{

	private static final long serialVersionUID = 7212519612881362417L;
	
	private long seckillId;
	private String name;
	private int number;
	private Date startTime;
	private Date endTime;
	private Date createTime;
	
	public Seckill() {
		
	}

	public Seckill(long seckillId, String name, int number, Date startTime, Date endTime,
			Date createTime) {
		this.seckillId = seckillId;
		this.name = name;
		this.number = number;
		this.startTime = startTime;
		this.endTime = endTime;
		this.createTime = createTime;
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "Seckill [seckillId=" + seckillId + ", name=" + name + ", number=" + number + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", createTime=" + createTime + "]";
	}
	
	
}
