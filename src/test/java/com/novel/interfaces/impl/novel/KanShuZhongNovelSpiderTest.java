package com.novel.interfaces.impl.novel;

import com.base.BaseJunit;
import com.novel.entitys.Novel;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by runshu.lin on 16/12/3.
 */
public class KanShuZhongNovelSpiderTest extends BaseJunit {

	@Resource
	private KanShuZhongNovelSpider kanShuZhongNovelSpider;

	@Test
	public void getNovel() throws Exception {
//		List<Novel> novels = kanShuZhongNovelSpider.getsNovel("http://www.kanshuzhong.com/booktype/2/1/");
//		for (Novel novel : novels) {
//			System.out.println(novel);
//		}
		Iterator<List<Novel>> iterator = kanShuZhongNovelSpider.iterator("http://www.kanshuzhong.com/map/U/1/");
		int i = 0;
		while (iterator.hasNext()) {
			List<Novel> novels = iterator.next();
			System.out.println(kanShuZhongNovelSpider.next());
			i++;
		}
		System.out.println(i);
	}

}