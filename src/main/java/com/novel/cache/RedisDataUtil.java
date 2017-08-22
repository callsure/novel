package com.novel.cache;

import com.novel.utils.SpringContextManager;
import redis.clients.jedis.*;

/**
 * Created by runshu.lin on 2017/8/12.
 */
public class RedisDataUtil implements JedisCommands {
	private static volatile RedisDataUtil instance;
	private JedisCluster jedisCluster;

	public RedisDataUtil() {
		if (instance != null) throw new RuntimeException("instance is duplication!");
		this.jedisCluster = SpringContextManager.getBean("redisCluster");
		instance = this;
	}

	public static RedisDataUtil getInstance() {
		if (instance == null) {
			synchronized (RedisDataUtil.class) {
				if (instance == null) {
					return new RedisDataUtil();
				}
			}
		}
		return instance;
	}

	@Override
	public String set(String key, String value) {
		return null;
	}

	@Override
	public String set(String key, String value, String nxxx, String expx, long time) {
		return null;
	}

	@Override
	public String set(String key, String value, String nxxx) {
		return null;
	}

	@Override
	public String get(String key) {
		return null;
	}
}
