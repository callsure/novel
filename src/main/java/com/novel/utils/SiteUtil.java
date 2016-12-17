package com.novel.utils;

/**
 * Created by runshu.lin on 16/12/17.
 */
public class SiteUtil {

	private SiteUtil() {}

	private static String site;

	private static String email;

	static {
		init();
	}

	private static void init(){
		site = ConfigUtil.getSysConfig("site");
		email = ConfigUtil.getSysConfig("email");
	}

	public static String getEmail() {
		return email;
	}

	public static String getSite() {
		return site;
	}
}
