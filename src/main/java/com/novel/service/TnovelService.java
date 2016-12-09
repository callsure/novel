package com.novel.service;

import com.novel.entitys.Nclass;
import com.novel.entitys.Tnovel;

import java.util.List;

/**
 * Created by runshu.lin on 16/12/8.
 */
public interface TnovelService {

	Tnovel getNovelById(Integer nId);

	List<Tnovel> getHotNovelAllType(Integer siteId, List<Nclass> nclasses);

	void updateBrowse(Tnovel record);

	List<Tnovel> searchNovel(String query);
}
