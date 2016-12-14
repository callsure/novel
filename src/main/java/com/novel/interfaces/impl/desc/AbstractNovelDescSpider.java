package com.novel.interfaces.impl.desc;

import com.novel.entitys.NovelRules;
import com.novel.interfaces.INovelDescSpider;
import com.novel.interfaces.impl.AbstractSpide;
import com.novel.utils.NovelSpiderUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by runshu.lin on 16/12/7.
 */
public abstract class AbstractNovelDescSpider extends AbstractSpide implements INovelDescSpider {

	@Override
	public Element getNovelDescInfo(String url,String selector) {
		Document doc = this.getDoc(url);

		String[] splits = selector.split("\\,");
		Element res = doc.select(splits[0]).get(Integer.parseInt(splits[1]));
		return res;
	}

	protected Document getDoc(String url){
		String result = super.cwal(url);
		Document doc = Jsoup.parse(result);
		doc.setBaseUri(url);
		return doc;
	}
}
