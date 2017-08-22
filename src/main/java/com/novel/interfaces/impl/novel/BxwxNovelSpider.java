package com.novel.interfaces.impl.novel;

import com.novel.entitys.Novel;
import com.novel.factory.NovelSpiderUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 笔下文学小说列表爬取
 * Created by runshu.lin on 16/12/3.
 */
@Service
public class BxwxNovelSpider extends AbstractNovelSpider  {

	@Override
	public List<Novel> getsNovel(String url, Integer maxTryTimes) {
		List<Novel> novels = new ArrayList<>();
		try {
			Elements trs = super.getsTr(url, maxTryTimes);
			for (int i = 1, size = trs.size();i < size; i++){
				Element tr = trs.get(i);
				Elements tds = tr.getElementsByTag("td");
				Novel novel = new Novel();
				novel.setNovelName(tds.first().getElementsByTag("a").first().text());
				novel.setNovelUrl(tds.first().getElementsByTag("a").first().absUrl("href").replace(".htm","/").replace("/binfo/","/b/"));
				novel.setLastUpdateChapter(tds.get(1).getElementsByTag("a").first().text());
				novel.setLastUpdateChapterUrl(tds.get(1).getElementsByTag("a").first().absUrl("href"));
				novel.setAuthor(tds.get(2).text());
				novel.setLastUpdateTime(NovelSpiderUtil.getDate(tds.get(4).text(),"yy-MM-dd"));
				novel.setStatus(NovelSpiderUtil.getNovelStatus(tds.get(5).text()));
				novel.setPlatFormId(NovelSpiderUtil.getContext(url).getSiteId());
				novels.add(novel);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return novels;
	}
}
