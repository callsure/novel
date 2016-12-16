package com.novel.service.impl;

import com.novel.beans.Chapter;
import com.novel.beans.ChapterDetail;
import com.novel.entitys.Nclass;
import com.novel.entitys.Tnovel;
import com.novel.exceptions.CrawlException;
import com.novel.mapper.NclassMapper;
import com.novel.mapper.NovelRulesMapper;
import com.novel.entitys.NovelRules;
import com.novel.mapper.TnovelMapper;
import com.novel.utils.ChapterDetailSpiderFactory;
import com.novel.utils.ChapterSpiderFactory;
import com.novel.utils.EHcacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by runshu.lin on 16/11/30.
 */
@Service
public class EhcacheDB {

	private static Logger logger = LoggerFactory.getLogger(EhcacheDB.class);

	private final String novelRules = "novelRules";

	private final Map<String,String> tnovelMap = new HashMap<>();

	private final String nclass = "allnclass";

	private final String novelALL = "novelAll";

	private final Map<String, String> novelMap = new HashMap<>();

	private final Map<String, String> chapterDetailMap = new HashMap<>();

	@Resource
	private NovelRulesMapper novelRulesMapper;
	@Resource
	private TnovelMapper tnovelMapper;
	@Resource
	private NclassMapper nclassMapper;

	/**
	 * 清理全部的缓存
	 */
	public void clean() {
		EHcacheUtil.getInstance().cleanAll();
	}

	/**
	 * 获取支持的小说站点列表
	 * @return
	 */
	public List<NovelRules> getNovelRulesLists(){
		List<NovelRules> lists = null;
		lists = (List<NovelRules>) EHcacheUtil.getInstance().get(novelRules);
		if(lists != null && !lists.isEmpty()){
			return lists;
		}
		lists = novelRulesMapper.findNovelRulesList();
		EHcacheUtil.getInstance().put(novelRules,lists);
		return lists;
	}

	/**
	 * 根据类型获取小说
	 * @param type 小说类型
	 * @return
	 */
	public List<Tnovel> getsNovelByType(String type,int pageNum){
		String key = "class" + type + pageNum;
		tnovelMap.put(key, key);
		List<Tnovel> lists = null;
		lists = (List<Tnovel>) EHcacheUtil.getInstance().get(key);
		if(lists != null && !lists.isEmpty()){
			return lists;
		}
		lists = tnovelMapper.getsNovelByType(type);
		EHcacheUtil.getInstance().put(key,lists);
		return lists;
	}

	/**
	 * 获取全部的类型
	 * @return
	 */
	public List<Nclass> getsNclassAll(){
		List<Nclass> nclasses = null;
		nclasses = (List<Nclass>) EHcacheUtil.getInstance().get(nclass);
		if(nclasses != null && !nclasses.isEmpty()){
			return nclasses;
		}
		nclasses = nclassMapper.getsNclassAll();
		EHcacheUtil.getInstance().put(nclass,nclasses);
		return nclasses;
	}

	/**
	 * 获取 t_novel 表全部数据
	 * @return
	 */
	public List<Tnovel> getsNovelAll(){
		List<Tnovel> lists = null;
		lists = (List<Tnovel>) EHcacheUtil.getInstance().get(novelALL);
		if(lists != null && !lists.isEmpty()){
			return lists;
		}
		lists = tnovelMapper.getsNovelAll();
		EHcacheUtil.getInstance().put(novelALL,lists);
		return lists;
	}

	/**
	 * 清除t_novel表全部数据的缓存
	 */
	public void removeNovelAll(){
		EHcacheUtil.getInstance().remove(novelALL);
	}

	/**
	 * 根据书籍id查询 章节列表
	 * @param tnovel
	 * @return
	 */
	public List<Chapter> getsChapterById(Tnovel tnovel){
		String url = tnovel.getnUrl();
		String key = "book"+tnovel.getnId();
		novelMap.put(key, key);
		List<Chapter> chapters = null;
		chapters = (List<Chapter>) EHcacheUtil.getInstance().get(key);
		if(chapters != null && !chapters.isEmpty()){
			return chapters;
		}
		try {
			chapters = ChapterSpiderFactory.getChapterSpider(url).getChapter(url);
		} catch (CrawlException e) {
			logger.error(e.toString());
			return null;
		}
		EHcacheUtil.getInstance().put(key, chapters);
		return chapters;
	}

	/**
	 * 根据url抓取章节内容
	 * @param id
	 * @param chapterId
	 * @param url
	 * @return
	 */
	public ChapterDetail getChapterDetailByUrl(Integer id, Integer chapterId, String url){
		String key = "book"+id+"chapter"+chapterId;
		chapterDetailMap.put(key, key);
		ChapterDetail chapterDetails = null;
		chapterDetails = (ChapterDetail) EHcacheUtil.getInstance().get(key);
		if (chapterDetails != null) {
			return chapterDetails;
		}
		try {
			chapterDetails = ChapterDetailSpiderFactory.getChapterDetailSpider(url).getChapterDetail(url);
		} catch (CrawlException e) {
			logger.error(e.toString());
			return null;
		}
		chapterDetails.setContent(chapterDetails.getContent().replace("${blank}","　").replace("${line}","<br>"));
		EHcacheUtil.getInstance().put(key, chapterDetails);
		return chapterDetails;
	}

}
