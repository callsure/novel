package com.novel.interfaces.impl.novel;

import com.novel.entitys.Novel;
import com.novel.utils.NovelSpiderUtil;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 看书中小说列表爬取
 * Created by runshu.lin on 16/12/3.
 */
@Service
public class KanShuZhongNovelSpider extends AbstractNovelSpider {
	@Override
	public List<Novel> getsNovel(String url, Integer maxTryTimes) {
		List<Novel> novels = new ArrayList<>();
		try {
			Elements trs = super.getsTr(url, maxTryTimes);
			for (int i = 1,size = trs.size() - 1;i < size;i++){
				Elements tds = trs.get(i).getElementsByTag("td");
				Novel novel = new Novel();
				novel.setType("%"+tds.first().getElementsByTag("span").first().text()+"%");
				novel.setNovelName(tds.get(1).getElementsByTag("span").first().text());
				novel.setNovelUrl(tds.get(1).getElementsByTag("a").first().absUrl("href"));
				novel.setLastUpdateChapter(tds.get(2).getElementsByTag("span").first().text());
				novel.setLastUpdateChapterUrl(tds.get(2).getElementsByTag("a").first().absUrl("href"));
				novel.setAuthor(tds.get(3).getElementsByTag("span").first().text());
				novel.setLastUpdateTime(NovelSpiderUtil.getDate(tds.get(4).getElementsByTag("span").first().text(),"MM-dd"));
				novel.setStatus(NovelSpiderUtil.getNovelStatus(tds.get(5).getElementsByTag("span").first().text()));
				novel.setPlatFormId(NovelSpiderUtil.getContext(url).getSiteId());
				novels.add(novel);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return novels;
	}
}
