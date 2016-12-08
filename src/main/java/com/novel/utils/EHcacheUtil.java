package com.novel.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * 缓存管理器
 *
 * @author linrunshu
 */
public class EHcacheUtil {
	private static EHcacheUtil ehcacheUtil;
	private CacheManager cacheManager;
	private Cache cache;
	private final String ehcacheName = "novelChapter";

	private EHcacheUtil() {
		cacheManager = SpringContextManager.getBean("cacheManagerFactor");
		this.cache = cacheManager.getCache(ehcacheName);
	}

	public Cache getCache() {
		if (cache == null) {
			throw new RuntimeException("无法获取到对应的cache!请检查配置文件");
		}
		return cache;
	}

	public void setCache(Cache cache) {
		this.cache = cache;
	}

	public synchronized static EHcacheUtil getInstance() {
		if (ehcacheUtil == null) {
			return new EHcacheUtil();
		}
		return ehcacheUtil;
	}

	/**
	 * 将对象添加到缓存中
	 * @param key
	 * @param value
	 */
	public void put(String key, Object value) {
		Element element = new Element(key, value);
		cache.put(element);
	}

	/**
	 * 通过名称从缓存中获取数据
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		Element element = cache.get(key);
		return element == null ? null : element.getObjectValue();
	}

	/**
	 * 移除数据
	 * @param key
	 */
	public void remove(String key) {
		cache.remove(key);
	}

	/**
	 * 清除所有缓存
	 */
	public void cleanAll() {
		cache.removeAll();
	}

}
