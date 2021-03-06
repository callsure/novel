package com.novel.interfaces.impl.storage;

import com.novel.entitys.Novel;
import com.novel.entitys.NovelList;
import com.novel.interfaces.INovelSpider;
import com.novel.interfaces.IProcessor;
import com.novel.mapper.NovelListMapper;
import com.novel.mapper.NovelMapper;
import com.novel.factory.NovelSpiderFactory;

import javax.annotation.Resource;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by runshu.lin on 16/12/4.
 */
public abstract class AbstractNovelStorage implements IProcessor {

	protected Map<String, String> tasks = new TreeMap<>();

	protected int siteId;

	@Resource
	private NovelMapper novelMapper;

	@Resource
	private NovelListMapper novelListMapper;

	@Override
	public void process() {
		//获取要爬去的数据列表
		List<NovelList> novelLists = novelListMapper.findNovelListListsBySiteId(siteId);
		for (int i = 0,length = novelLists.size(); i<length; i++){
			tasks.put(novelLists.get(i).getListMap(),novelLists.get(i).getListUrl());
		}

		ExecutorService service = Executors.newFixedThreadPool(tasks.size());
		List<Future<String>> futures = new ArrayList<>(tasks.size());
		for (Entry<String, String> entry : tasks.entrySet()) {
			final String key = entry.getKey();
			final String value = entry.getValue();
			futures.add(service.submit(new Callable<String>() {
				@Override
				public String call() throws Exception {
					INovelSpider spider = NovelSpiderFactory.getNovelSpider(value);
					Iterator<List<Novel>> iterator = spider.iterator(value);
					while (iterator.hasNext()) {
						List<Novel> novels = iterator.next();
						for (Novel novel : novels) {
							novel.setFirstLetter(key.charAt(0));
						}
						if(novels != null && !novels.isEmpty()){
							novelMapper.batchInsert(novels);
						}
						Thread.sleep(10_000);
					}
					return key;
				}
			}));
		}
		service.shutdown();
		for (Future<String> future : futures) {
			try {
				System.out.println("抓取[" + future.get() + "]结束!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
