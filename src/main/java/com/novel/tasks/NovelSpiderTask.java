package com.novel.tasks;

import com.novel.entitys.DownNovel;
import com.novel.entitys.Tnovel;
import com.novel.exceptions.NovelSpiderException;
import com.novel.interfaces.IProcessor;
import com.novel.interfaces.impl.desc.DescCallable;
import com.novel.interfaces.impl.download.DefaultNovelDownLoad;
import com.novel.mapper.DownNovelMapper;
import com.novel.mapper.NclassMapper;
import com.novel.mapper.NovelMapper;
import com.novel.mapper.TnovelMapper;
import com.novel.service.impl.EhcacheDB;
import com.novel.utils.ConfigUtil;
import com.novel.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


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

	@Resource
	private IProcessor kanShuZhongNovelStorage;

	@Resource
	private IProcessor bxwxNovelStorage;

	@Resource
	NclassMapper nclassMapper;

	@Resource
	TnovelMapper tnovelMapper;

	@Resource
	EhcacheDB ehcacheDB;

	@Resource
	DownNovelMapper downNovelMapper;

	@Resource
	DefaultNovelDownLoad defaultNovelDownLoad;

	/**
	 * 重新下载失败的小说
	 * @throws NovelSpiderException
	 */
	//@Scheduled(fixedDelay = 5000_000, initialDelay = 60_000)
	public void restartProcess() throws NovelSpiderException {
		Set<Integer> set = new HashSet<>();
		set.add(23);
		set.add(35);
		set.add(41);
		set.add(47);
		set.add(15);
		set.add(53);
		Iterator<Integer> iterator = set.iterator();
		while (iterator.hasNext()){
			Integer id = iterator.next();
			defaultNovelDownLoad.process(id);
		}
	}

	/**
	 * 每天定时清除已下载的文件
	 */
	@Scheduled(cron = "0 15 4 ? * *")
	public void deleteFileProcess(){
		List<DownNovel> list = downNovelMapper.getsFinishDownLoadNovel();
		if (list != null && !list.isEmpty()) {
			for (DownNovel downNovel : list) {
				downNovel.setIsDelete(0);
				File file = new File(downNovel.getSavePath());
				FileUtil.clearDir(file);
				downNovelMapper.updateByPrimaryKeySelective(downNovel);
			}
		}
	}

	@Scheduled(cron = "0 15 2,12 ? * *")
	public void UpdateNovelInfo() {
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
	@Scheduled(fixedDelay = 900_000, initialDelay = 60_000)
	public void updateNovelDesc() {
		if (flag) {
			List<Tnovel> list = tnovelMapper.getsNovelAll();
			if (list != null && !list.isEmpty()) {
				flag = false;
				//最大线程数
				int maxThreadSize = (int) Math.ceil(list.size() * 1.0 / size);

				Map<String, List<Tnovel>> downloadTaskAlloc = new HashMap<>();
				for (int i = 0; i < maxThreadSize; i++) {
					int startIndex = i * size;
					int endIndex = i == maxThreadSize - 1 ? list.size() : i * size + size;
					downloadTaskAlloc.put(startIndex + "-" + endIndex, list.subList(startIndex, endIndex));
				}

				ExecutorService service = Executors.newFixedThreadPool(maxThreadSize);

				List<Future> task = new ArrayList<>();

				for (Map.Entry<String, List<Tnovel>> entry : downloadTaskAlloc.entrySet()) {
					String key = entry.getKey();
					task.add(service.submit(new DescCallable(entry.getValue(), 10, key)));
				}

				service.shutdown();

				for (Future<String> future : task) {
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
}