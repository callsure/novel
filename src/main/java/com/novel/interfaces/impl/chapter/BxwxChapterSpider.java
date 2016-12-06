package com.novel.interfaces.impl.chapter;

import com.novel.beans.Chapter;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by runshu.lin on 16/12/2.
 */
@Service("bxwxChapterSpider")
public class BxwxChapterSpider extends AbstractChapterSpider {
	@Override
	public List<Chapter> getChapter(String url) {
		List<Chapter> chapters = super.getChapter(url);
		Collections.sort(chapters, new Comparator<Chapter>() {
			@Override
			public int compare(Chapter o1, Chapter o2) {
				String o1Url = o1.getUrl();
				String o2Url = o2.getUrl();
				String index1 = o1Url.substring(o1Url.lastIndexOf('/'),o1Url.lastIndexOf('.'));
				String index2 = o1Url.substring(o2Url.lastIndexOf('/'),o2Url.lastIndexOf('.'));
				return index1.compareTo(index2);
			}
		});
		return chapters;
	}
}
