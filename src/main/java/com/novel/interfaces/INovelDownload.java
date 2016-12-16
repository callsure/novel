package com.novel.interfaces;

import com.novel.configuration.Configuration;
import com.novel.exceptions.CrawlException;

/**
 * Created by runshu.lin on 16/12/1.
 */
public interface INovelDownload {

	public String down(String url, Configuration config) throws CrawlException;
}
