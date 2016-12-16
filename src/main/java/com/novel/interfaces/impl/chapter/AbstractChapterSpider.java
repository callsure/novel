package com.novel.interfaces.impl.chapter;

import com.novel.beans.Chapter;
import com.novel.entitys.NovelRules;
import com.novel.exceptions.CrawlException;
import com.novel.interfaces.IChapterSpider;
import com.novel.interfaces.impl.AbstractSpide;
import com.novel.utils.NovelSpiderUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by runshu.lin on 16/11/29.
 */
public abstract class AbstractChapterSpider extends AbstractSpide implements IChapterSpider {
	@Override
	public List<Chapter> getChapter(String url) throws CrawlException {
		String res = super.cwal(url);
		Document doc = Jsoup.parse(res);
		doc.setBaseUri(url);
		NovelRules novelRules = NovelSpiderUtil.getContext(url);

		String[] splits = novelRules.getNovelNameSelector().split("\\,");
		String novelName = doc.select(splits[0]).get(Integer.parseInt(splits[1])).text();

		Elements as = doc.select(novelRules.getChapterListSelector());
		List<Chapter> chapters = new ArrayList<>();
		for (Element a : as){
			Chapter chapter = new Chapter();
			chapter.setTitle(a.text());
			chapter.setUrl(a.absUrl("href"));
			chapter.setNovelName(novelName);
			chapters.add(chapter);
		}
		return chapters;
	}
}
