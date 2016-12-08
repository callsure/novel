package com.novel.interfaces.impl.desc;

import com.novel.beans.NovelDesc;
import com.novel.entitys.NovelRules;
import com.novel.interfaces.INovelDescSpider;
import com.novel.interfaces.impl.AbstractSpide;
import com.novel.utils.NovelSpiderUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by runshu.lin on 16/12/7.
 */
public abstract class AbstractNovelDescSpider extends AbstractSpide implements INovelDescSpider {

	@Override
	public NovelDesc getNovelDescInfo(String url) {
		String result = super.cwal(url);
		Document doc = Jsoup.parse(result);
		doc.setBaseUri(url);

		NovelRules novelRules = NovelSpiderUtil.getContext(url);

		NovelDesc novelDesc = new NovelDesc();

		//简介
		String descSelector = novelRules.getNovelDescSelector();
		String[] splits = descSelector.split("\\,");
		String desc = doc.select(splits[0]).get(Integer.parseInt(splits[1])).html();
		novelDesc.setDesc(desc);

		//小说名
		splits = novelRules.getNovelNameSelector().split("\\,");
		String novelName = doc.select(splits[0]).get(Integer.parseInt(splits[1])).text();
		novelDesc.setNovelName(novelName);

		//小说作者
		splits = novelRules.getNovelAuthorSelector().split("\\,");
		String author = doc.select(splits[0]).get(Integer.parseInt(splits[1])).text();
		novelDesc.setNovelAuthor(author);

		//平台id
		novelDesc.setSiteId(novelRules.getSiteId());

		return novelDesc;
	}
}
