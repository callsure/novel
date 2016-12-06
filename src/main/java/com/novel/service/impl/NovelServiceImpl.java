package com.novel.service.impl;

import com.novel.entitys.Novel;
import com.novel.mapper.NovelMapper;
import com.novel.service.NovelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by runshu.lin on 16/12/4.
 */
@Service("novelService")
public class NovelServiceImpl implements NovelService {

	@Resource
	private NovelMapper novelMapper;

	@Override
	public List<Novel> getsNovelByKeyword(String keyword){
		keyword = "%" + keyword + "%";
		return novelMapper.getsNovelByKeyword(keyword);
	}
}
