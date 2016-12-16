package com.novel.tasks;

import com.base.BaseJunit;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by runshu.lin on 16/12/15.
 */
public class NovelSpiderTaskTest extends BaseJunit {

	@Resource
	NovelSpiderTask novelSpiderTask;

	@Test
	public void updateNovelInfo() throws Exception {
		novelSpiderTask.UpdateNovelInfo();
	}

}