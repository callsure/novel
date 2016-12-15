package com.novel.interfaces.impl.desc;

import com.base.BaseJunit;
import com.novel.beans.NovelDesc;
import com.novel.entitys.Tnovel;
import com.novel.mapper.TnovelMapper;
import com.novel.service.impl.EhcacheDB;
import com.novel.utils.NovelDescSpiderFactory;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by runshu.lin on 16/12/7.
 */
public class NovelDescSpiderTest extends BaseJunit {

	@Resource
	private EhcacheDB ehcacheDB;
	@Resource
	private TnovelMapper tnovelMapper;

	private final int size = 250;

	@Test
	public void kanShuZhongNovelDescSpider(){
		String url = "http://www.kanshuzhong.com/book/105109/";
		NovelDesc novelDesc = NovelDescSpiderFactory.getNovelDescSpider(url).setNovelDescInfo(url,null);
		System.out.println(novelDesc);
	}

	@Test
	public void BxwxNovelDescSpider(){
		List<Tnovel> list = ehcacheDB.getsNovelAll();
		for (Tnovel tnovel : list){
			String url = tnovel.getnUrl();
			NovelDesc novelDesc = NovelDescSpiderFactory.getNovelDescSpider(url).setNovelDescInfo(url,tnovel);
			System.out.println(novelDesc);
		}
	}

}