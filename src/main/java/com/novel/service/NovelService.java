package com.novel.service;

import com.novel.entitys.Novel;

import java.util.List;

/**
 * Created by runshu.lin on 16/12/4.
 */
public interface NovelService {
	public List<Novel> getsNovelByKeyword(String keyword);
}
