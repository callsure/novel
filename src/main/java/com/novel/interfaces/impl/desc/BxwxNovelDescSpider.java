package com.novel.interfaces.impl.desc;

import com.novel.beans.NovelDesc;
import com.novel.entitys.Nclass;
import com.novel.entitys.NovelRules;
import com.novel.entitys.Tnovel;
import com.novel.mapper.NclassMapper;
import com.novel.utils.NovelSpiderUtil;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by runshu.lin on 16/12/7.
 */
@Service
public class BxwxNovelDescSpider extends AbstractNovelDescSpider {

	@Resource
	private NclassMapper nclassMapper;

	@Override
	public NovelDesc setNovelDescInfo(String url, Tnovel tnovel) {
		NovelDesc novelDesc = new NovelDesc();
		try {
			url = url.replace("/b/","/binfo/");
			url = url.substring(0,url.length()-1);
			url = url + ".htm";

			NovelRules novelRules = NovelSpiderUtil.getContext(url);

			//简介
			String selector = novelRules.getNovelDescSelector();
			Element element = super.getNovelDescInfo(url, selector);
			String desc = element.text().replace("bxwx8.Org","").replace("wWw.BxWx8.Org","");
			novelDesc.setDesc(desc);

			//类型
			selector = novelRules.getNovelTypeSelector();
			element = super.getNovelDescInfo(url, selector);
			String type = element.text();
			type = "%" + type + "%";
			Nclass nclass = nclassMapper.getNclassByTypeName(type);
			novelDesc.setNovelType(nclass.getnTypeId());

			//小说名
			novelDesc.setNovelAuthor(tnovel.getnAuthor());
			//小说作者
			novelDesc.setNovelName(tnovel.getnName());
			//平台id
			novelDesc.setSiteId(tnovel.getSiteId());
		} catch (Exception e) {
			throw new RuntimeException(tnovel.getnName()+":简介截取出错!");
		}
		return novelDesc;
	}

}
