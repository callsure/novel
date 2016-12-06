package com.novel.mapper;

import com.novel.entitys.NovelRules;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by runshu.lin on 16/11/30.
 */
@Repository
public interface NovelRulesMapper {
	List<NovelRules> findNovelRulesList();
}
