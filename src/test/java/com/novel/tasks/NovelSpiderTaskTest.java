package com.novel.tasks;

import com.base.BaseJunit;
import com.novel.interfaces.impl.storage.BxwxNovelStorage;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by runshu.lin on 16/12/15.
 */
public class NovelSpiderTaskTest extends BaseJunit {

	@Resource
	NovelSpiderTask novelSpiderTask;

	@Resource
	BxwxNovelStorage bxwxNovelStorage;

	@Test
	public void updateNovelInfo() throws Exception {
		novelSpiderTask.UpdateNovelInfo();
	}

}