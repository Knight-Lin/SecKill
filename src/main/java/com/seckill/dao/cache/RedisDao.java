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
	//RuntimeSchema根据提供的类字节码（通过反射获得属性方法）自动生成Schema描述,且性能几乎没有损失
	private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);
	
	public RedisDao(String ip,int port){
		jedisPool = new JedisPool(ip,port);
	}
	//从jedis中拿到byte[]反序列化为Seckill对象
	
	public Seckill getSeckill(long seckillId) {
		
		//redis操作逻辑
		try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:"+seckillId;
				//并没有内部实现序列化操作
				//采用自定义的序列化而非java提供的序列化方式，这样更高效，并发效果越好
				//序列化对象必须是pojo（get/set）
				byte[] bytes = jedis.get(key.getBytes());
				if(bytes!=null) {
					//用schema创建一个空对象，用于赋值
					Seckill seckill = schema.newMessage();
					//根据schema把bytes的数据赋值到空对象seckill的属性值中
					//seckill即被反序列化
					//效率高两个数量级，空间为十分之一
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
	//把Seckill对象序列化为字节数组放入jedis中
	public String putSeckill(Seckill seckill) {
		try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:"+seckill.getSeckillId();
				//根据schema把seckill对象序列化成byte数组，需要一个linkedbuffer缓存空间
				byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema, 
						LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				int timeout = 60*60;//秒为单位
				//超时缓存，缓存一定时间
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
