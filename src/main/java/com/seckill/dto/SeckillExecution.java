package com.seckill.dto;

import java.io.Serializable;

import com.seckill.entity.SuccessKilled;
import com.seckill.enums.SeckillStateEnum;

/**
 * 封装秒杀执行后的结果
 * @author Administrator
 *
 */
public class SeckillExecution implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4824039447507835345L;
	private long seckillId;
	private int state;
	private String stateInfo;
	private SuccessKilled successKilled;
	
	public SeckillExecution(long seckillId, SeckillStateEnum state,  SuccessKilled successKilled) {
		this.seckillId = seckillId;
		this.state = state.getState();
		this.stateInfo = state.getStateInfo();
		this.successKilled = successKilled;
	}

	
	
	public SeckillExecution(long seckillId, int state, String stateInfo) {
		this.seckillId = seckillId;
		this.state = state;
		this.stateInfo = stateInfo;
	}



	public SeckillExecution(Long seckillId, SeckillStateEnum state) {
		this.seckillId = seckillId;
		this.state = state.getState();
		this.stateInfo = state.getStateInfo();
	}



	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public SuccessKilled getSuccessKilled() {
		return successKilled;
	}

	public void setSuccessKilled(SuccessKilled successKilled) {
		this.successKilled = successKilled;
	}



	@Override
	public String toString() {
		return "SeckillExecution [seckillId=" + seckillId + ", state=" + state + ", stateInfo=" + stateInfo
				+ ", successKilled=" + successKilled + "]";
	}
	
	
}
