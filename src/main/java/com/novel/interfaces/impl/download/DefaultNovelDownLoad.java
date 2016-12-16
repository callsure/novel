package com.novel.interfaces.impl.download;

import com.novel.entitys.DownNovel;
import com.novel.entitys.Tnovel;
import com.novel.exceptions.NovelSpiderException;
import com.novel.mapper.DownNovelMapper;
import com.novel.mapper.TnovelMapper;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 小说下载	限制小说最多只能3个人同时下载
 * Created by runshu.lin on 16/12/15.
 */
@Service
public class DefaultNovelDownLoad {

	@Resource
	private TnovelMapper tnovelMapper;

	@Resource
	private DownNovelMapper downNovelMapper;

	private static ExecutorService service = Executors.newCachedThreadPool();

	//最多3个同时下载
	private static final int taskAllocCount = 3;
	//已开始的任务
	private static int hasTaskCount = 0;

	public void process(Integer bookId) throws NovelSpiderException {
		if (checkTask()) {
			Tnovel tnovel = tnovelMapper.selectByPrimaryKey(bookId);
			if (tnovel != null) {
				updateAdd();
				DownNovel downNovel = new DownNovel();
				downNovel.setnId(bookId);
				downNovel.setnName(tnovel.getnName());
				downNovel.setnUrl(tnovel.getnUrl());
				downNovel.setnAuthor(tnovel.getnAuthor());
				downNovel.setSiteId(tnovel.getSiteId());
				downNovel.setDownTime(new DateTime().toDate());
				downNovel.setIsDelete(0);
				downNovel.setdStatus(0);
				//插入记录下载
				downNovelMapper.insert(downNovel);
				service.execute(new NovelDownLoadRun(tnovel.getnUrl(), downNovel.getdId()));
			} else {
				throw new NovelSpiderException("没有该小说可以下载!");
			}
		} else {
			throw new NovelSpiderException("现在是下载高峰期,请避开高峰期!");
		}
	}

	public static synchronized void updateAdd() {
		hasTaskCount = hasTaskCount + 1;
	}

	public static synchronized void updateReduce() {
		hasTaskCount = hasTaskCount - 1;
	}

	//判断是否可以下载
	public static boolean checkTask() {
		if (hasTaskCount >= taskAllocCount) {
			return false;
		} else {
			return true;
		}
	}
}
