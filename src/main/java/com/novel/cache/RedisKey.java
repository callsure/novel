package com.novel.cache;

/**
 * Created by runshu.lin on 2017/8/12.
 */
public enum RedisKey {
	USER,
	NOVEL_RULES,
	;
	private String key;

	public String getKey(Object o) {
		StringBuilder sb = new StringBuilder();
		sb.append(key.toLowerCase());
		sb.append("#");
		sb.append(o);
		return sb.toString();
	}
}
