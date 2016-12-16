package com.novel.interfaces.impl.desc;

import com.novel.beans.NovelDesc;
import com.novel.entitys.Tnovel;
import com.novel.mapper.TnovelMapper;
import com.novel.utils.NovelDescSpiderFactory;
import com.novel.utils.SpringContextManager;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by runshu.lin on 16/12/16.
 */
public class DescCallable implements Callable<String> {
	private List<Tnovel> tnovels;

	private int tryTimes;

	private String key;

	TnovelMapper tnovelMapper = SpringContextManager.getBean("tnovelMapper");

	public DescCallable(List<Tnovel> tnovels, int tryTimes, String key) {
		this.tnovels = tnovels;
		this.tryTimes = tryTimes;
		this.key = key;
	}

	@Override
	public String call() throws Exception {
		for (Tnovel tnovel : tnovels) {
			for (int i = 0;i < tryTimes; i++){
				String url = tnovel.getnUrl();
				NovelDesc novelDesc = NovelDescSpiderFactory.getNovelDescSpider(url).setNovelDescInfo(url, tnovel);
				tnovelMapper.updateByNameAndAuthor(novelDesc);
				break;
			}
			Thread.sleep(1_000);
		}
		return key;
	}
}
