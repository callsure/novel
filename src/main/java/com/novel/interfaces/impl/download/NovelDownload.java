package com.novel.interfaces.impl.download;

import com.novel.beans.Chapter;
import com.novel.configuration.Configuration;
import com.novel.exceptions.CrawlException;
import com.novel.interfaces.IChapterSpider;
import com.novel.interfaces.INovelDownload;
import com.novel.utils.ChapterSpiderFactory;
import com.novel.utils.NovelSpiderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by runshu.lin on 16/12/1.
 */
@Component
public class NovelDownload implements INovelDownload {

	private static Logger logger = LoggerFactory.getLogger(NovelDownload.class);

	@Override
	public String down(String url, Configuration config) {
		IChapterSpider spider = ChapterSpiderFactory.getChapterSpider(url);

		List<Chapter> chapters = null;
		try {
			chapters = spider.getChapter(url);
		} catch (CrawlException e) {
			logger.error(e.toString());
			return null;
		}

		int size = config.getSize();
		//计算最大线程数量
		int maxThreadSize = (int) Math.ceil(chapters.size()*1.0/size);
		Map<String,List<Chapter>> downloadTaskAlloc = new HashMap<>();
		for (int i=0;i<maxThreadSize;i++){
			//0~100 100
			//100~200
			//200~300
			int startIndex = i * size;
			int endIndex = i==maxThreadSize-1?chapters.size() : i * size + size;
			downloadTaskAlloc.put(startIndex + "-" + endIndex,chapters.subList(startIndex,endIndex));
		}

		//创建线程池
		ExecutorService service = Executors.newFixedThreadPool(maxThreadSize);
		List<Future> task = new ArrayList<>();

		String savePath = config.getSavePath() + NovelSpiderUtil.getContext(url).getSiteName();
		Random rand =new Random();
		int i = rand.nextInt(10000000) + 1;
		savePath += "/" + i;

		File file = new File(savePath);
		file.mkdirs();
		for (Map.Entry<String,List<Chapter>> entry : downloadTaskAlloc.entrySet()){
			String key = entry.getKey();
			task.add(service.submit(new DownLoadCallable(entry.getValue(),savePath + "/" + key + ".txt",config.getTryTimes())));
		}
		service.shutdown();
		for (Future<String> future : task){
			try {
				logger.info(future.get() + "下载完成!");
			} catch (InterruptedException | ExecutionException e) {
				logger.error(e.toString());
			}
		}
		return savePath;
	}
}
