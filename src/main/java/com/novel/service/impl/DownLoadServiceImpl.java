package com.novel.service.impl;

import com.novel.beans.JsonBean;
import com.novel.entitys.DownNovel;
import com.novel.exceptions.NovelSpiderException;
import com.novel.interfaces.impl.download.DefaultNovelDownLoad;
import com.novel.mapper.DownNovelMapper;
import com.novel.service.DownLoadService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by runshu.lin on 16/12/16.
 */
@Service
public class DownLoadServiceImpl implements DownLoadService {

	@Resource
	DownNovelMapper downNovelMapper;

	@Resource
	DefaultNovelDownLoad defaultNovelDownLoad;

	@Override
	public JsonBean downLoadNovel(Integer bookId) {

		JsonBean jsonBean = new JsonBean();

		//查看是否已存在
		DownNovel downNovel = downNovelMapper.getDownNovelByNId(bookId);
		if (downNovel == null) {
			String message = "系统正在为你下载并打包小说,请稍等一会再来...";
			//查看是否正在下载
			downNovel = downNovelMapper.getDownNovelByNIdNow(bookId);
			if (downNovel == null) {
				//启动线程下载
				try {
					defaultNovelDownLoad.process(bookId);
				} catch (NovelSpiderException e) {
					message = e.toString();
				}
				jsonBean.setMsg(message);
				jsonBean.setSuccess(false);
				return jsonBean;
			}
			//返回信息,告诉请求者,系统为你正在下载
			jsonBean.setObj(downNovel);
			jsonBean.setNovelName(downNovel.getnName());
			jsonBean.setSavePath(downNovel.getSavePath());
			jsonBean.setMsg(message);
			jsonBean.setSuccess(false);
			return jsonBean;
		}
		jsonBean.setObj(downNovel);
		jsonBean.setNovelName(downNovel.getnName());
		jsonBean.setSavePath(downNovel.getSavePath());
		jsonBean.setMsg("");
		jsonBean.setSuccess(true);
		return jsonBean;
	}
}
