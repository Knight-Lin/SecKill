package com.seckill.exception;
//�ظ���ɱ�쳣
public class RepeatKillException extends SeckillException{

	

	public RepeatKillException(String message) {
		super(message);
	}
	public RepeatKillException(String message, Throwable e) {
		super(message, e);
	}
	
}
