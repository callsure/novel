package com.novel.tasks;

import com.novel.interfaces.IProcessor;
import com.novel.mapper.NovelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * Created by runshu.lin on 16/12/4.
 */
@Component
public class NovelSpiderTask {

	private static Logger loger = LoggerFactory.getLogger(NovelSpiderTask.class);

	@Resource
	private NovelMapper novelMapper;

	@Resource(name = "kanShuZhongNovelStorage")
	private IProcessor kanShuZhongNovelStorage;

	@Resource(name = "bxwxNovelStorage")
	private IProcessor bxwxNovelStorage;

	@Scheduled(cron = "0 15 2 ? * *")
	public void UpdateNovelInfo(){
		//delete n_novel data
		novelMapper.deleteNovelAll();
		try {
			//cache data insert n_novel
			kanShuZhongNovelStorage.process();
			bxwxNovelStorage.process();
		} catch (Exception e) {
			loger.warn("小说列表更新失败:" + e);
		}
	}
}
