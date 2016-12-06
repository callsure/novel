package com.novel.interfaces;

import com.novel.beans.Chapter;

import java.util.List;

/**
 * Created by runshu.lin on 16/11/29.
 */
public interface IChapterSpider {
	public List<Chapter> getChapter(String url);
}
