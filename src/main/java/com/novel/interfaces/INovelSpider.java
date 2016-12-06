package com.novel.interfaces;

import com.novel.entitys.Novel;

import java.util.Iterator;
import java.util.List;

/**
 * Created by runshu.lin on 16/12/3.
 */
public interface INovelSpider {

	public static final int MAX_TRY_TIMES = 6;

	/**
	 * 获取小说列表
	 * @param url
	 * @return
	 */
	public List<Novel> getsNovel(String url);

	/**
	 * 获取小说列表
	 * @param url
	 * @param maxTryTimes 尝试下次次数
	 * @return
	 */
	public List<Novel> getsNovel(String url,Integer maxTryTimes);

	public boolean hasNext();

	public String next();

	public Iterator<List<Novel>> iterator(String firstPage);

	public Iterator<List<Novel>> iterator(String firstPage, Integer maxTryTimes);

}
