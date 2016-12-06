package com.novel.utils;

import com.novel.entitys.NovelRules;
import com.novel.interfaces.IChapterDetailSpider;

/**
 * Created by runshu.lin on 16/12/2.
 */
public final class ChapterDetailSpiderFactory {

	private ChapterDetailSpiderFactory(){}

	public static IChapterDetailSpider getChapterDetailSpider(String url){
		IChapterDetailSpider spider = null;
		NovelRules novelRules = NovelSpiderUtil.getContext(url);
		switch (novelRules.getSiteName()){
			case "DingDianXiaoShuo" :
			case "BiQuGe" :
			case "KanShuZhong" :
			case "Bxwx" :
				spider = SpringContextManager.getBean("defaultChapterDetailSpider");break;
		}
		return spider;
	}
}
