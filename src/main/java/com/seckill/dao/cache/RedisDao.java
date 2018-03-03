package com.seckill.dao.cache;

import org.slf4j.LoggerFactory;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.seckill.entity.Seckill;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {
	
	private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
	private final JedisPool jedisPool;
	//RuntimeSchema�����ṩ�����ֽ��루ͨ�����������Է������Զ�����Schema����,�����ܼ���û����ʧ
	private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);
	
	public RedisDao(String ip,int port){
		jedisPool = new JedisPool(ip,port);
	}
	//��jedis���õ�byte[]�����л�ΪSeckill����
	
	public Seckill getSeckill(long seckillId) {
		
		//redis�����߼�
		try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:"+seckillId;
				//��û���ڲ�ʵ�����л�����
				//�����Զ�������л�����java�ṩ�����л���ʽ����������Ч������Ч��Խ��
				//���л����������pojo��get/set��
				byte[] bytes = jedis.get(key.getBytes());
				if(bytes!=null) {
					//��schema����һ���ն������ڸ�ֵ
					Seckill seckill = schema.newMessage();
					//����schema��bytes�����ݸ�ֵ���ն���seckill������ֵ��
					//seckill���������л�
					//Ч�ʸ��������������ռ�Ϊʮ��֮һ
					ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
					
					return seckill;
				}
			}finally {
				jedis.close();
			}
			
		}catch(Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}
	//��Seckill�������л�Ϊ�ֽ��������jedis��
	public String putSeckill(Seckill seckill) {
		try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:"+seckill.getSeckillId();
				//����schema��seckill�������л���byte���飬��Ҫһ��linkedbuffer����ռ�
				byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema, 
						LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				int timeout = 60*60;//��Ϊ��λ
				//��ʱ���棬����һ��ʱ��
				String result = jedis.setex(key.getBytes(), timeout, bytes);
				return result;
				
			}finally {
				jedis.close();
			}
		}catch(Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}
	
}
