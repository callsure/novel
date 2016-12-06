package com.novel.interfaces.impl.download;

import com.base.BaseJunit;
import com.novel.configuration.Configuration;
import com.novel.interfaces.INovelDownload;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by runshu.lin on 16/12/2.
 */
public class NovelDownloadTest extends BaseJunit {

	@Resource(name = "novelDownload")
	private INovelDownload novelDownload;

	@Test
	public void novelDownload() throws Exception {
		Configuration config = new Configuration();
		config.setSavePath("/Users/xlin/oslin/novel");
		novelDownload.novelDownload("http://www.23wx.com/html/28/28373/", config);
	}

}