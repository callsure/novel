package com.novel.factory;

import com.novel.entitys.NovelRules;
import com.novel.interfaces.IChapterSpider;
import com.novel.utils.SpringContextManager;

/**
 * Created by runshu.lin on 16/12/2.
 */
public final class ChapterSpiderFactory {
	private ChapterSpiderFactory(){}

	public static IChapterSpider getChapterSpider(String url) throws Exception {
		NovelRules novelRules = NovelSpiderUtil.getContext(url);
		IChapterSpider chapterSpider = null;
		switch (NovelRulesKey.valueOf(novelRules.getSiteName())){
			case Bxwx:
				chapterSpider = SpringContextManager.getBean("bxwxChapterSpider");break;
			case DingDianXiaoShuo:
			case BiQuGe:
			case KanShuZhong:
				chapterSpider = SpringContextManager.getBean("defaultChapterSpider");break;
		}
		return chapterSpider;
	}
}
