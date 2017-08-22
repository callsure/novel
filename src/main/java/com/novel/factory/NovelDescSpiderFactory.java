package com.novel.factory;

import com.novel.entitys.NovelRules;
import com.novel.interfaces.INovelDescSpider;
import com.novel.utils.SpringContextManager;

/**
 * 小说简介接口工厂
 * Created by runshu.lin on 16/12/7.
 */
public final class NovelDescSpiderFactory {
	private NovelDescSpiderFactory() {}

	public static INovelDescSpider getNovelDescSpider(String url) throws Exception {
		NovelRules novelRules = NovelSpiderUtil.getContext(url);
		INovelDescSpider novelDescSpider = null;
		switch (NovelRulesKey.valueOf(novelRules.getSiteName())){
			case Bxwx:
				novelDescSpider = SpringContextManager.getBean("bxwxNovelDescSpider");break;
			case DingDianXiaoShuo:
			case BiQuGe:
			case KanShuZhong:
				novelDescSpider = SpringContextManager.getBean("kanShuZhongNovelDescSpider");break;
		}
		return novelDescSpider;
	}
}
