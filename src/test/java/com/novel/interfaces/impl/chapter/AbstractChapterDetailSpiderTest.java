package com.novel.interfaces.impl.chapter;

import com.base.BaseJunit;
import com.novel.interfaces.IChapterDetailSpider;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by runshu.lin on 16/12/1.
 */
public class AbstractChapterDetailSpiderTest extends BaseJunit {

	@Resource(name = "defaultChapterDetailSpider")
	private IChapterDetailSpider defaultChapterDetailSpider;

	@Test
	public void getChapterDetail() throws Exception {
		System.out.println(defaultChapterDetailSpider.getChapterDetail("http://www.23wx.com/html/28/28373/18568314.html").toString());
	}

}