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
public class BxwxNovelSpiderTest extends BaseJunit {

	@Resource
	private BxwxNovelSpider bxwxNovelSpider;

	@Test
	public void getNovel() throws Exception {
//		List<Novel> novels = bxwxNovelSpider.getsNovel("http://www.bxwx8.org/binitialA/0/1.htm");
//		for (Novel novel : novels){
//			System.out.println(novel.toString());
//		}

		Iterator<List<Novel>> iterator = bxwxNovelSpider.iterator("http://www.bxwx8.org/binitialA/0/1.htm");
		while (iterator.hasNext()) {
			List<Novel> novels = iterator.next();
			System.out.println(bxwxNovelSpider.next());
		}
	}

}