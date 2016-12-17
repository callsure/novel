package com.novel.service.impl;

import com.novel.entitys.Nclass;
import com.novel.entitys.Tnovel;
import com.novel.mapper.TnovelMapper;
import com.novel.service.TnovelService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by runshu.lin on 16/12/8.
 */
@Service
public class TnovelServiceImpl implements TnovelService {

	private static final Logger logger = Logger.getLogger(TnovelServiceImpl.class);

	@Resource
	TnovelMapper tnovelMapper;

	@Override
	public Tnovel getNovelById(Integer nId) {
		return tnovelMapper.selectByPrimaryKey(nId);
	}

	/**
	 * 获取首页的所有类型的前五条数据
	 * @param siteId
	 * @param nclasses
	 * @return
	 */
	@Override
	public List<Tnovel> getHotNovelAllType(Integer siteId, List<Nclass> nclasses) {
		List<Tnovel> datas = new ArrayList<>();
		for (int i = 0,size = nclasses.size();i < size;i++){
			List<Tnovel> list = tnovelMapper.getHotNovelAllType(siteId, nclasses.get(i).getnTypeId());
			datas.addAll(list);
		}
		return datas;
	}

	/**
	 * 更新浏览数
	 * @param record
	 * @return
	 */
	@Override
	public void updateBrowse(Tnovel record) {
		int i = tnovelMapper.updateByPrimaryKeySelective(record);
		if (i<1){
			logger.warn("小说id:"+record.getnId()+"的小说浏览数量更新出错");
		}
	}

	/**
	 * 根据query 搜查小说名字和作者
	 * @param query
	 * @return
	 */
	@Override
	public List<Tnovel> searchNovel(String query){
		query = "%" + query + "%";
		List<Tnovel> list = tnovelMapper.findNovelByQuery(query);
		return list;
	}


}
