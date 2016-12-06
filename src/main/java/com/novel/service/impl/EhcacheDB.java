package com.novel.service.impl;

import com.novel.entitys.Nclass;
import com.novel.entitys.Tnovel;
import com.novel.mapper.NclassMapper;
import com.novel.mapper.NovelRulesMapper;
import com.novel.entitys.NovelRules;
import com.novel.mapper.TnovelMapper;
import com.novel.utils.EHcacheUtil;
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

	private final String novelRules = "novelRules";

	private final Map<String,String> tnovel = new HashMap<>();

	@Resource
	private NovelRulesMapper novelRulesMapper;
	@Resource
	private TnovelMapper tnovelMapper;

	/**
	 * 获取支持的小说站点列表
	 * @return
	 */
	public List<NovelRules> getNovelRulesLists(){
		List<NovelRules> lists = null;
		lists = (List<NovelRules>) EHcacheUtil.getInstance().get(novelRules);
		if(lists!=null&&!lists.isEmpty()){
			return lists;
		}
		lists = novelRulesMapper.findNovelRulesList();
		EHcacheUtil.getInstance().put(novelRules,lists);
		return lists;
	}

	/**
	 * 根据类型获取热门小说
	 * @param type 小说类型
	 * @return
	 */
	public List<Tnovel> getsNovelByType(String type){
		tnovel.put(type,"class" + type);
		List<Tnovel> lists = null;
		lists = (List<Tnovel>) EHcacheUtil.getInstance().get(tnovel.get(type));
		if(lists!=null&&!lists.isEmpty()){
			return lists;
		}
		lists = tnovelMapper.getsNovelByType(type);
		EHcacheUtil.getInstance().put(tnovel.get(type),lists);
		return lists;
	}

}
