package com.seckill.dto;
/**
 * ��¶��ɱ��ַDTO
 * @author Administrator
 *
 */
public class Exposer {
	
	private boolean exposed;
	private String md5;
	private long seckillId;
	private long now;
	private long start;
	private long end;
	
	public Exposer(boolean exposed, String md5, long seckillId) {
		this.exposed = exposed;
		this.md5 = md5;
		this.seckillId = seckillId;
	}

	public Exposer(boolean exposed, long seckillId,long now, long start, long end) {
		this.exposed = exposed;
		this.seckillId = seckillId;
		this.now = now;
		this.start = start;
		this.end = end;
	}

	public Exposer(boolean exposed, long seckillId) {
		this.exposed = exposed;
		this.seckillId = seckillId;
	}

	public Exposer(boolean exposed, String md5, long seckillId, long now, long start, long end) {
		this.exposed = exposed;
		this.md5 = md5;
		this.seckillId = seckillId;
		this.now = now;
		this.start = start;
		this.end = end;
	}
	
	
	
}
