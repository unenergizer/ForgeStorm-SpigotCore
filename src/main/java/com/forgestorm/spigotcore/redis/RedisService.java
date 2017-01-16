package com.forgestorm.spigotcore.redis;

import com.forgestorm.spigotcore.SpigotCore;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisService { 

	private JedisPool pool;

	public RedisService(String redisServerIp, String redisServerPort) { 
		//Class loading for Redis by Tux on SpigotMc
		//https://www.spigotmc.org/wiki/using-redis-jedis/
		ClassLoader previous = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(SpigotCore.class.getClassLoader());
		pool = new JedisPool(redisServerIp, Integer.parseInt(redisServerPort));
		Thread.currentThread().setContextClassLoader(previous);
	} 

	public Jedis getPoolResource() {
		return pool.getResource();
	}

	public void disable() { 
		pool.destroy(); 
	} 
}
