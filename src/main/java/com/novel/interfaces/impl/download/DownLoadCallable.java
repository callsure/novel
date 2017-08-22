package com.novel.interfaces.impl.download;

import com.novel.beans.Chapter;
import com.novel.beans.ChapterDetail;
import com.novel.exceptions.CrawlException;
import com.novel.interfaces.IChapterDetailSpider;
import com.novel.factory.ChapterDetailSpiderFactory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by runshu.lin on 16/12/16.
 */
public class DownLoadCallable implements Callable<String> {
	private List<Chapter> chapters;

	private String savePath;

	private int tryTimes;

	public DownLoadCallable(List<Chapter> chapters, String savePath, int tryTimes) {
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
						break;
					} catch (CrawlException e) {
						detail = new ChapterDetail();
						detail.setTitle(chapter.getTitle());
						detail.setContent("很抱歉，" + e.getMessage());
						System.err.println("尝试第[" + (i+1) + "/" + tryTimes + "]次下载失败!");
					}
				}
				out.println(detail.getTitle());
				out.println(detail.getContent());
				Thread.sleep(1_000);
			}

		}catch (IOException e){
			throw new RuntimeException(e);
		}
		return savePath;
	}
}
