package com.novel.utils;

import com.base.BaseJunit;
import com.novel.factory.NovelSpiderUtil;
import org.junit.Test;

/**
 * Created by runshu.lin on 16/12/3.
 */
public class NovelSpiderUtilTest extends BaseJunit {
	@Test
	public void getContext() throws Exception {

	}

	@Test
	public void multiFileMerge() throws Exception {
		NovelSpiderUtil.multiFileMerge("/Users/xlin/oslin/AbstractNovelSpider/DingDianXiaoShuo/",null,false);
	}

}