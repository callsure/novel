package com.novel.cache;

import com.novel.utils.SpringContextManager;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * 缓存管理器
 *
 * @author linrunshu
 */
public class EHcacheUtil {
	private static volatile EHcacheUtil instance;
	private CacheManager cacheManager;
	private Cache cache;
	private static final String ehcacheName = "novelChapter";

	private EHcacheUtil() {
		if (instance != null) throw new RuntimeException("instance is duplication!");
		cacheManager = SpringContextManager.getBean("cacheManagerFactor");
		this.cache = cacheManager.getCache(ehcacheName);
		instance = this;
	}

	public static EHcacheUtil getInstance() {
		if (instance == null) {
			synchronized (EHcacheUtil.class){
				if (instance == null) {
					return new EHcacheUtil();
				}
			}
		}
		return instance;
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
