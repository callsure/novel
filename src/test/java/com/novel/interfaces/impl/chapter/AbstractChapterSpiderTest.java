package com.novel.interfaces.impl.chapter;

import com.base.BaseJunit;
import com.novel.interfaces.IChapterSpider;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by runshu.lin on 16/11/30.
 */
public class AbstractChapterSpiderTest extends BaseJunit {

	@Resource(name = "defaultChapterSpider")
	private IChapterSpider defaultChapterSpider;

	@Test
	public void getChapter() throws Exception {
		System.out.println(defaultChapterSpider.getChapter("http://www.kanshuzhong.com/book/104547/").toString());
	}
}