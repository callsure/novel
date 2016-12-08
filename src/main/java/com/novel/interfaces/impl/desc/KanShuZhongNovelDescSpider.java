package com.novel.interfaces.impl.desc;

import com.novel.beans.NovelDesc;
import org.springframework.stereotype.Service;

/**
 * Created by runshu.lin on 16/12/7.
 */
@Service
public class KanShuZhongNovelDescSpider extends AbstractNovelDescSpider {

	@Override
	public NovelDesc setNovelDescInfo(String url) {
		NovelDesc novelDesc = null;
		try {
			novelDesc = super.getNovelDescInfo(url);
			String desc = novelDesc.getDesc();
			int formIndex = desc.indexOf("<br>") + 4;
			int endIndex = desc.indexOf("<br>",formIndex);
			desc = desc.substring(formIndex,endIndex);
			novelDesc.setDesc(desc);
		} catch (Exception e) {
			throw new RuntimeException(novelDesc.getNovelName()+":简介截取出错!");
		}
		return novelDesc;
	}
}
