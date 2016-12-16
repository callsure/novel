package com.novel.interfaces;

import com.novel.beans.ChapterDetail;
import com.novel.exceptions.CrawlException;

/**
 * Created by runshu.lin on 16/12/1.
 */
public interface IChapterDetailSpider {
	public ChapterDetail getChapterDetail(String url) throws CrawlException;
}
