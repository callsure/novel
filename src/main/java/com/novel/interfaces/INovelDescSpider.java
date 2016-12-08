package com.novel.interfaces;

import com.novel.beans.NovelDesc;

/**
 * Created by runshu.lin on 16/12/7.
 */
public interface INovelDescSpider {
	NovelDesc getNovelDescInfo(String url);

	NovelDesc setNovelDescInfo(String url);
}
