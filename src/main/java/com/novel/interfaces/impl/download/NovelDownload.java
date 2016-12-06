package com.novel.interfaces.impl.download;

import com.novel.configuration.Configuration;
import com.novel.beans.Chapter;
import com.novel.beans.ChapterDetail;
import com.novel.interfaces.IChapterDetailSpider;
import com.novel.interfaces.IChapterSpider;
import com.novel.interfaces.INovelDownload;
import com.novel.utils.ChapterDetailSpiderFactory;
import com.novel.utils.ChapterSpiderFactory;
import com.novel.utils.NovelSpiderUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by runshu.lin on 16/12/1.
 */
@Service
public class NovelDownload implements INovelDownload {
	@Override
	public String novelDownload(String url, Configuration config) {
		IChapterSpider spider = ChapterSpiderFactory.getChapterSpider(url);
		List<Chapter> chapters = spider.getChapter(url);
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
		String savePath = config.getSavePath() + "/" + NovelSpiderUtil.getContext(url).getSiteName();
		File file = new File(savePath);
		file.mkdirs();

		for (Map.Entry<String,List<Chapter>> entry : downloadTaskAlloc.entrySet()){
			String key = entry.getKey();
			task.add(service.submit(new DownloadCallable(entry.getValue(),savePath + "/" + key + ".txt",config.getTryTimes())));
		}
		service.shutdown();
		for (Future<String> future : task){
			try {
				System.out.println(future.get() + "下载完成!");
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}

class DownloadCallable implements Callable<String> {

	private List<Chapter> chapters;

	private String savePath;

	private int tryTimes;

	public DownloadCallable(List<Chapter> chapters, String savePath, int tryTimes) {
		this.chapters = chapters;
		this.savePath = savePath;
		this.tryTimes = tryTimes;
	}

	@Override
	public String call() throws Exception {
		try (PrintWriter out = new PrintWriter(new File(savePath),"utf-8")) {
			for (Chapter chapter : chapters){
				IChapterDetailSpider spider = ChapterDetailSpiderFactory.getChapterDetailSpider(chapter.getUrl());
				ChapterDetail detail = null;
				for (int i=0;i<tryTimes;i++){
					try {
						detail = spider.getChapterDetail(chapter.getUrl());
						detail.setContent(detail.getContent().replace("${blank}"," ").replace("${line}","\n"));
						out.println(detail.getTitle());
						out.println(detail.getContent());
						break;
					} catch (RuntimeException e) {
						System.err.println("尝试第[" + (i+1) + "/" + tryTimes + "]次下载失败!");
					}
				}
			}

		}catch (IOException e){
			throw new RuntimeException(e);
		}
		return savePath;
	}
}
