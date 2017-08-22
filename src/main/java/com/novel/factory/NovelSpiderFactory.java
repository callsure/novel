package com.novel.factory;

import com.novel.entitys.NovelRules;
import com.novel.interfaces.INovelSpider;
import com.novel.utils.SpringContextManager;

/**
 * 生产抓取小说列表的实现类
 * Created by runshu.lin on 16/12/3.
 */
public final class NovelSpiderFactory {
	private NovelSpiderFactory(){}

	public static INovelSpider getNovelSpider(String url) throws Exception {
		NovelRules novelRules = NovelSpiderUtil.getContext(url);
		INovelSpider spider = null;
		switch (NovelRulesKey.valueOf(novelRules.getSiteName())){
			case Bxwx:
				spider = SpringContextManager.getBean("bxwxNovelSpider");
				break;
			case KanShuZhong:
				spider = SpringContextManager.getBean("kanShuZhongNovelSpider");
				break;
			default:
				throw new RuntimeException(url + "暂时不支持!");
		}
		return spider;
	}
}
