package com.novel.tasks;

import com.novel.beans.NovelDesc;
import com.novel.entitys.Tnovel;
import com.novel.interfaces.IProcessor;
import com.novel.mapper.NclassMapper;
import com.novel.mapper.NovelMapper;
import com.novel.mapper.TnovelMapper;
import com.novel.service.impl.EhcacheDB;
import com.novel.utils.NovelDescSpiderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


/**
 * 定时抓取数据
 * Created by runshu.lin on 16/12/4.
 */
@Component
public class NovelSpiderTask {

	private static Logger loger = LoggerFactory.getLogger(NovelSpiderTask.class);

	private final int size = 250;

	private static boolean flag = true;

	@Resource
	private NovelMapper novelMapper;

	@Resource(name = "kanShuZhongNovelStorage")
	private IProcessor kanShuZhongNovelStorage;

	@Resource(name = "bxwxNovelStorage")
	private IProcessor bxwxNovelStorage;

	@Resource
	NclassMapper nclassMapper;

	@Resource
	TnovelMapper tnovelMapper;

	@Resource
	EhcacheDB ehcacheDB;

	@Scheduled(cron = "0 15 2,12 ? * *")
	public void UpdateNovelInfo(){
		//delete n_novel data
		novelMapper.deleteNovelAll();
		try {
			//cache data insert n_novel
			kanShuZhongNovelStorage.process();
			bxwxNovelStorage.process();
			//调用存储过程,update type
			//nclassMapper.updateNovelRulesType();
			tnovelMapper.updateNovelChapter();
			//清除缓存
			ehcacheDB.clean();
		} catch (Exception e) {
			loger.warn("小说列表更新失败:" + e);
		}
		ehcacheDB.clean();
	}

	/**
	 * 更新简介
	 */
	@Scheduled(fixedDelay = 5000_000,initialDelay = 60_000)
	public void updateNovelDesc(){
		if(flag) {
			List<Tnovel> list = tnovelMapper.getsNovelAll();
			if(list!=null && !list.isEmpty()){
				flag = false;
				//最大线程数
				int maxThreadSize = (int) Math.ceil(list.size()*1.0/size);

				Map<String,List<Tnovel>> downloadTaskAlloc = new HashMap<>();
				for (int i = 0;i < maxThreadSize;i++) {
					int startIndex = i * size;
					int endIndex = i==maxThreadSize-1?list.size() : i * size + size;
					downloadTaskAlloc.put(startIndex + "-" + endIndex,list.subList(startIndex,endIndex));
				}

				ExecutorService service = Executors.newFixedThreadPool(maxThreadSize);

				List<Future> task = new ArrayList<>();

				for (Map.Entry<String,List<Tnovel>> entry : downloadTaskAlloc.entrySet()){
					String key = entry.getKey();
					task.add(service.submit(new DescCall(entry.getValue(),10,key)));
				}

				service.shutdown();

				for (Future<String> future : task){
					try {
						System.out.println(future.get() + "更新完成!");
					} catch (InterruptedException | ExecutionException e) {
						loger.warn(e.toString());
					}
				}
				flag = true;
			}
		}
	}

	private class DescCall implements Callable<String> {

		private List<Tnovel> tnovels;

		private int tryTimes;

		private String key;

		public DescCall(List<Tnovel> tnovels, int tryTimes, String key) {
			this.tnovels = tnovels;
			this.tryTimes = tryTimes;
			this.key = key;
		}

		@Override
		public String call() throws Exception {
			for (Tnovel tnovel : tnovels) {
				for (int i = 0;i < tryTimes; i++){
					String url = tnovel.getnUrl();
					NovelDesc novelDesc = NovelDescSpiderFactory.getNovelDescSpider(url).setNovelDescInfo(url, tnovel);
					System.out.println(novelDesc);
					tnovelMapper.updateByNameAndAuthor(novelDesc);
					break;
				}
				Thread.sleep(1_000);
			}
			return key;
		}
	}


}
