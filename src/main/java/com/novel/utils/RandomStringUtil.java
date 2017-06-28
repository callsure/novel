package com.novel.utils;

import com.novel.beans.JsonBean;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by runshu.lin on 16/12/17.
 */
public class RandomStringUtil {

	private static final int length = 5;

	public static final Map<String, JsonBean> token = new HashMap<>();

	private RandomStringUtil() {}

	/**
	 * 生成随机字符
	 * @param length 表示生成字符串的长度
	 * @return
	 */
	public static String getRandomString(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz";
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 获取下载令牌
	 * @param key
	 * @return
	 */
	public static JsonBean getToken(String key) {
		JsonBean jsonBean = token.get(key);
		removeToken(key);
		return jsonBean;
	}

	/**
	 * 设置下载路径
	 * @param jsonBean
	 */
	public static String setToken(JsonBean jsonBean) {
		String key = getRandomString(length);
		token.put(key, jsonBean);
		return key;
	}

	/**
	 * 移除下载
	 * @param key
	 */
	public static void removeToken(String key){
		token.remove(key);
	}
}
