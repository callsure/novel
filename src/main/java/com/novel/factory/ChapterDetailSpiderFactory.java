package com.novel.factory;

import com.novel.entitys.NovelRules;
import com.novel.interfaces.IChapterDetailSpider;
import com.novel.utils.SpringContextManager;

/**
 * Created by runshu.lin on 16/12/2.
 */
public final class ChapterDetailSpiderFactory {

	private ChapterDetailSpiderFactory(){}

	public static IChapterDetailSpider getChapterDetailSpider(String url) throws Exception {
		IChapterDetailSpider spider = null;
		NovelRules novelRules = NovelSpiderUtil.getContext(url);
		switch (NovelRulesKey.valueOf(novelRules.getSiteName())){
			case DingDianXiaoShuo :
			case BiQuGe :
			case KanShuZhong :
			case Bxwx :
				spider = SpringContextManager.getBean("defaultChapterDetailSpider");break;
		}
		return spider;
	}
}
