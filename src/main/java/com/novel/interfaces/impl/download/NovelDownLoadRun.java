package com.novel.interfaces.impl.download;

import com.novel.configuration.Configuration;
import com.novel.entitys.DownNovel;
import com.novel.mapper.DownNovelMapper;
import com.novel.utils.ConfigUtil;
import com.novel.utils.NovelSpiderUtil;
import com.novel.utils.SpringContextManager;

/**
 * Created by runshu.lin on 16/12/16.
 */
public class NovelDownLoadRun implements Runnable {

	private DownNovelMapper downNovelMapper;

	private NovelDownload novelDownload;

	private Integer dId;

	private String url;

	public NovelDownLoadRun(String url, Integer dId) {
		this.dId = dId;
		this.url = url;
		this.downNovelMapper = SpringContextManager.getBean("downNovelMapper");
		this.novelDownload = SpringContextManager.getBean("novelDownload");
	}

	@Override
	public void run() {
		try {
			Configuration configuration = new Configuration();
			configuration.setSavePath(ConfigUtil.getSysConfig("savePath"));
			configuration.setTryTimes(6);
			DownNovel downNovel = new DownNovel();
			downNovel.setdId(dId);
			String path = novelDownload.down(url, configuration);
			Thread.sleep(2_000);
			if (path != null) {
				String mutilPath = NovelSpiderUtil.multiFileMerge(path, null, true);
				downNovel.setIsDelete(1);
				downNovel.setdStatus(1);
				downNovel.setSavePath(mutilPath);
			} else {
				downNovel.setdStatus(-1);
			}
			downNovelMapper.updateByPrimaryKeySelective(downNovel);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DefaultNovelDownLoad.updateReduce();
		}
	}
}
