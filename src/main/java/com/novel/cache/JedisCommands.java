package com.novel.cache;

/**
 * Created by runshu.lin on 2017/8/12.
 */
public interface JedisCommands {

	String set(String key, String value);

	String set(String key, String value, String nxxx, String expx, long time);

	String set(String key, String value, String nxxx);

	String get(String key);
}
