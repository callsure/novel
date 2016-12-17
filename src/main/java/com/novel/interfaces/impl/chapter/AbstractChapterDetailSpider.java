package com.novel.interfaces.impl.chapter;

import com.novel.beans.ChapterDetail;
import com.novel.entitys.NovelRules;
import com.novel.exceptions.CrawlException;
import com.novel.interfaces.IChapterDetailSpider;
import com.novel.interfaces.impl.AbstractSpide;
import com.novel.utils.NovelSpiderUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by runshu.lin on 16/12/1.
 */
public abstract class AbstractChapterDetailSpider extends AbstractSpide implements IChapterDetailSpider {

	@Override
	public ChapterDetail getChapterDetail(String url) throws CrawlException {
		try {
			String result = super.cwal(url);
			result = result.replace("&nbsp;","${blank}").replace("<br />", "${line}").replace("<br/>", "${line}");
			Document doc = Jsoup.parse(result);
			doc.setBaseUri(url);
			NovelRules novelRules = NovelSpiderUtil.getContext(url);

			//拿标题
			String titleSelector = novelRules.getDetailTitleSelector();
			String[] splits = titleSelector.split("\\,");
			splits = parseSelector(splits);
			ChapterDetail detail = new ChapterDetail();
			detail.setTitle(doc.select(splits[0]).get(Integer.parseInt(splits[1])).text());

			//拿内容
			String contentSelector = novelRules.getDetailContentSelector();
			detail.setContent(doc.select(contentSelector).first().text());

			//拿上一章
			String prevSelector = novelRules.getDetailPrevSelector();
			splits = prevSelector.split("\\,");
			splits = parseSelector(splits);
			detail.setPrev(doc.select(splits[0]).get(Integer.parseInt(splits[1])).absUrl("href"));

			//拿下一章
			String nextSelector = novelRules.getDetailNextSelector();
			splits = nextSelector.split("\\,");
			splits = parseSelector(splits);
			detail.setNext(doc.select(splits[0]).get(Integer.parseInt(splits[1])).absUrl("href"));
			return detail;
		} catch (Exception e) {
			throw new CrawlException(e);
		}
	}

	/**
	 * 转换数组下标
	 * @param splits
	 * @return
	 */
	private String[] parseSelector(String[] splits){
		String[] newSplits = new String[2];
		if(splits.length==1){
			newSplits[0] = splits[0];
			newSplits[1] = "0";
			return newSplits;
		}else {
			return splits;
		}
	}
}
