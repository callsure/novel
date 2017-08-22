package com.novel.utils;

import com.base.BaseJunit;
import org.junit.Test;
import redis.clients.jedis.JedisCluster;

/**
 * Created by runshu.lin on 2017/8/12.
 */
public class RedisDataUtilTest extends BaseJunit{
	@Test
	public void testRedis() {
		JedisCluster jc = SpringContextManager.getBean("redisCluster");
		System.out.println(jc.get("lin"));
	}
}