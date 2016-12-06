package com.novel.service.impl;

import com.novel.mapper.NovelRulesMapper;
import com.novel.entitys.NovelRules;
import com.novel.utils.EHcacheUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by runshu.lin on 16/11/30.
 */
@Service
public class EhcacheDB {

	private final String novelRules = "novelRules";

	@Resource
	private NovelRulesMapper novelRulesMapper;

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
}
