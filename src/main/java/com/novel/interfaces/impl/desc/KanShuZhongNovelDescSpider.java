package com.novel.interfaces.impl.desc;

import com.novel.beans.NovelDesc;
import com.novel.entitys.NovelRules;
import com.novel.entitys.Tnovel;
import com.novel.utils.NovelSpiderUtil;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

/**
 * Created by runshu.lin on 16/12/7.
 */
@Service
public class KanShuZhongNovelDescSpider extends AbstractNovelDescSpider {

	@Override
	public NovelDesc setNovelDescInfo(String url, Tnovel tnovel) {
		NovelDesc novelDesc = null;
		try {
			NovelRules novelRules = NovelSpiderUtil.getContext(url);
			String selector = novelRules.getNovelDescSelector();

			Element element = super.getNovelDescInfo(url, selector);
			String desc = element.html();
			int formIndex = desc.indexOf("<br>") + 4;
			int endIndex = desc.indexOf("<br>",formIndex);
			desc = desc.substring(formIndex,endIndex);

			novelDesc.setNovelAuthor(tnovel.getnAuthor());
			novelDesc.setNovelName(tnovel.getnName());
			novelDesc.setDesc(desc);
			novelDesc.setSiteId(tnovel.getSiteId());
		} catch (Exception e) {
			throw new RuntimeException(novelDesc.getNovelName()+":简介截取出错!");
		}
		return novelDesc;
	}
}
