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
		List<Tnovel> list = ehcacheDB.getsNovelAll();
		NovelDesc novelDesc = NovelDescSpiderFactory.getNovelDescSpider(list.get(0).getnUrl()).getNovelDescInfo(list.get(0).getnUrl());
		System.out.println(novelDesc);
	}

}