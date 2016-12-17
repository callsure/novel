package com.novel.utils;

import java.util.ResourceBundle;

/**
 * 参数读取类
 * 
 * @author Chane
 * 
 */

public final class ConfigUtil {

	private ConfigUtil() {}

	private static ResourceBundle systemConfig = ResourceBundle.getBundle("config");

	/**
	 * 通过键获取值
	 * 
	 * @param key
	 * @return
	 */
	public static String getSysConfig(String key){
		try{
			String value = new String(systemConfig.getString(key).getBytes("ISO-8859-1"), "utf-8");
			return value;
		}catch(Exception e){
			throw new RuntimeException(e.toString());
		}
	}
}
