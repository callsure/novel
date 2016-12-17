package com.novel.utils;

import com.novel.entitys.NovelRules;
import com.novel.service.impl.EhcacheDB;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by runshu.lin on 16/11/30.
 */
public final class NovelSpiderUtil {

	private static List<NovelRules> CONTEXT_OBJECT = new ArrayList<>();

	static {
		init();
	}

	private NovelSpiderUtil() {}

	private static void init() {
		EhcacheDB ehcacheDB = SpringContextManager.getBean("ehcacheDB");
		List<NovelRules> lists = ehcacheDB.getNovelRulesLists();
		CONTEXT_OBJECT = lists;
	}

	/**
	 * 获取规则选择器列表
	 * @param url
	 * @return
	 */
	public static NovelRules getContext(String url) {
		for (NovelRules novelRules : CONTEXT_OBJECT){
			if(url.contains(novelRules.getChapterUrl())){
				return novelRules;
			}
		}
		throw new RuntimeException("url=" + url + "是不被支持的小说网站");
	}

	/**
	 * 多个文件合并为一个文件
	 * @param path 基本目录,该目录下的所有文件合并到 merget/merget.txt
	 * @param mergePathFile 合并后的文本文件,可以不传这个参数
	 * @param deleteThisFile 合并后是否删除文件
	 */
	public static String multiFileMerge(String path, String mergePathFile, boolean deleteThisFile){
		String mergePathFileNew = mergePathFile == null ? path + "/merge/merge.txt" : path + "/merge/" + mergePathFile;
		File[] files = new File(path).listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".txt");
			}
		});

		Arrays.sort(files, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				int o1Index = Integer.parseInt(o1.getName().split("\\-")[0]);
				int o2Index = Integer.parseInt(o2.getName().split("\\-")[0]);
				if (o1Index > o2Index) {
					return 1;
				}else if (o1Index == o2Index) {
					return 0;
				}else {
					return -1;
				}
			}
		});

		PrintWriter out = null;
		try {
			File mergeFile = new File(mergePathFileNew.substring(0,mergePathFileNew.lastIndexOf('/')));
			mergeFile.mkdirs();
			out = new PrintWriter(new File(mergePathFileNew),"utf-8");
			for (File file : files) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
				String line = null;
				while ((line = reader.readLine()) != null){
					out.println(line);
				}
				reader.close();

				//删除文件
				if (deleteThisFile){
					file.delete();
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			out.close();
		}
		return mergePathFileNew;
	}

	/**
	 * 获取书籍的状态
	 * @param status
	 * @return
	 */
	public static int getNovelStatus(String status){
		if (status.contains("连载")){
			return 1;
		}else if(status.contains("完本") || status.contains("完结") || status.contains("完成")){
			return 2;
		}else {
			throw new RuntimeException("不支持的书籍状态:" + status);
		}
	}

	/**
	 * 格式化字符为日期对象
	 * @param dataStr
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Date getDate(String dataStr, String pattern) throws ParseException {
		if ("MM-dd".equals(pattern)) {
			pattern = "yyyy-MM-dd";
			dataStr = getDateField(Calendar.YEAR) + "-" + dataStr;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = sdf.parse(dataStr);
		return  date;
	}

	/**
	 * 获取本时刻的字符量
	 * @param field
	 * @return
	 */
	public static String getDateField(int field){
		Calendar calender = new GregorianCalendar();
		return calender.get(field) + "";
	}

}
