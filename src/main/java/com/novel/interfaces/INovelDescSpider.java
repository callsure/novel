package com.novel.interfaces;

import com.novel.beans.NovelDesc;
import com.novel.entitys.Tnovel;
import com.novel.exceptions.CrawlException;
import org.jsoup.nodes.Element;

/**
 * Created by runshu.lin on 16/12/7.
 */
public interface INovelDescSpider {
	Element getNovelDescInfo(String url, String selector) throws CrawlException;

	NovelDesc setNovelDescInfo(String url, Tnovel tnovel);
}
