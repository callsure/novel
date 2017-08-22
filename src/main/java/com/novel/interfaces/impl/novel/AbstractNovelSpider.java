package com.novel.interfaces.impl.novel;

import com.novel.entitys.Novel;
import com.novel.entitys.NovelRules;
import com.novel.interfaces.INovelSpider;
import com.novel.interfaces.impl.AbstractSpide;
import com.novel.factory.NovelSpiderUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Iterator;
import java.util.List;

/**
 * Created by runshu.lin on 16/12/3.
 */
public abstract class AbstractNovelSpider extends AbstractSpide implements INovelSpider {

	public static final int MAX_TRY_TIMES = 10;

	protected Element nextPageElement;

	//下一页url
	protected String nextPage;

	/**
	 * 小说列表抓取
	 * @param url
	 * @return
	 */
	protected Elements getsTr(String url) throws Exception{
		return getsTr(url,MAX_TRY_TIMES);
	}

	/**
	 * 小说列表抓取
	 * @param url
	 * @param maxTryTimes 尝试下次次数
	 * @return
	 * @throws Exception
	 */
	protected Elements getsTr(String url, Integer maxTryTimes) throws Exception{
		maxTryTimes = maxTryTimes == null ? MAX_TRY_TIMES : maxTryTimes;
		Elements trs = null;
		for (int i = 0; i < maxTryTimes; i++){
			try {
				String result = super.cwal(url);
				NovelRules novelRules = NovelSpiderUtil.getContext(url);
				String novelSelector = novelRules.getNovelSelector();
				if (novelSelector == null) throw new RuntimeException("url = " + url + "目前不支持抓取小说列表!");
				Document doc = Jsoup.parse(result);
				doc.setBaseUri(url);
				trs = doc.select(novelSelector);

				String novelNextPageSelector = novelRules.getNovelNextPageSelector();
				if (novelNextPageSelector != null) {
					Elements nextPageElements = doc.select(novelNextPageSelector);
					nextPageElement = nextPageElements == null ? null : nextPageElements.first();
					if (nextPageElement != null) {
						nextPage = nextPageElement.absUrl("href");
					}else {
						nextPage = "";
					}
				}

				return trs;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		throw new RuntimeException(url + "尝试了" + maxTryTimes + "次下载依然失败!");
	}

	@Override
	public List<Novel> getsNovel(String url) {
		return getsNovel(url,MAX_TRY_TIMES);
	}

	@Override
	public boolean hasNext() {
		return !nextPage.isEmpty();
	}

	@Override
	public String next() {
		return nextPage;
	}

	@Override
	public Iterator<List<Novel>> iterator(String firstPage) {
		return iterator(firstPage, MAX_TRY_TIMES);
	}

	@Override
	public Iterator<List<Novel>> iterator(String firstPage, Integer maxTryTimes) {
		nextPage = firstPage;
		return new NovelIterator(maxTryTimes);
	}

	private class NovelIterator implements Iterator<List<Novel>>{

		private Integer maxTryTimes;

		public NovelIterator(Integer maxTryTimes) {
			this.maxTryTimes = maxTryTimes;
		}

		@Override
		public boolean hasNext() {
			return AbstractNovelSpider.this.hasNext();
		}

		@Override
		public List<Novel> next() {
			List<Novel> novels = getsNovel(nextPage,maxTryTimes);
			return novels;
		}

		@Override
		public void remove() {

		}
	}
}
