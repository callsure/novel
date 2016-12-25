package com.novel.interfaces.impl.download;

import com.novel.entitys.DownNovel;
import com.novel.entitys.Tnovel;
import com.novel.exceptions.NovelSpiderException;
import com.novel.mapper.DownNovelMapper;
import com.novel.mapper.TnovelMapper;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.*;

/**
 * 小说下载	限制小说最多只能2个人同时下载,其他请求下载进入等待
 * Created by runshu.lin on 16/12/15.
 */
@Service
public class DefaultNovelDownLoad {

	@Resource
	private TnovelMapper tnovelMapper;

	@Resource
	private DownNovelMapper downNovelMapper;

	private static ExecutorService service = Executors.newCachedThreadPool();

	//声明队列
	private static BlockingQueue queue = new LinkedBlockingQueue(100);

	private static boolean isRunning = false;

	//最多3个同时下载
	private static final int taskAllocCount = 2;
	//已开始的任务
	private static int hasTaskCount = 0;

	public void process(Integer bookId) throws NovelSpiderException {
		try {
			Tnovel tnovel = tnovelMapper.selectByPrimaryKey(bookId);
			if (tnovel != null) {
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
				//添加队列
				queue.offer(downNovel, 2, TimeUnit.SECONDS);
			}else {
				throw new NovelSpiderException("没有该小说可以下载!");
			}
		} catch (InterruptedException e) {
			throw new NovelSpiderException("小说id:" + bookId + ",添加队列失败!");
		} finally {
			if (!isRunning) {
				poolProcess();
			}
		}
	}

	private void poolProcess() throws NovelSpiderException {
		isRunning = true;
		while (isRunning){
			if (checkTask()) {
				try {
					DownNovel downNovel = (DownNovel) queue.poll(2, TimeUnit.SECONDS);
					if (downNovel != null) {
						updateAdd();
						service.execute(new NovelDownLoadRun(downNovel.getnUrl(), downNovel.getdId()));
					} else {
						//没有任务了
						isRunning = false;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
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
