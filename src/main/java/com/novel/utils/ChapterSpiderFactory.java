package com.novel.utils;

import com.novel.entitys.NovelRules;
import com.novel.interfaces.IChapterSpider;

/**
 * Created by runshu.lin on 16/12/2.
 */
public final class ChapterSpiderFactory {
	private ChapterSpiderFactory(){}

	public static IChapterSpider getChapterSpider(String url){
		NovelRules novelRules = NovelSpiderUtil.getContext(url);
		IChapterSpider chapterSpider = null;
		switch (novelRules.getSiteName()){
			case "Bxwx":
				chapterSpider = SpringContextManager.getBean("bxwxChapterSpider");break;
			case "DingDianXiaoShuo":
			case "BiQuGe":
			case "KanShuZhong":
				chapterSpider = SpringContextManager.getBean("defaultChapterSpider");break;
		}
		return chapterSpider;
	}
}
