package com.novel.mapper;

import com.base.BaseJunit;
import com.novel.entitys.Novel;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by runshu.lin on 16/12/4.
 */
public class NovelMapperTest extends BaseJunit {

	@Resource
	private NovelMapper novelMapper;

	@Test
	public void batchInsert() throws Exception {
		List<Novel> novels = new ArrayList<>();
		Novel novel = new Novel();
		novel.setNovelName("2222");
		novel.setNovelUrl("11111");
		novel.setAuthor("eee");
		novels.add(novel);
		Novel novel1 = new Novel();
		novel1.setNovelName("3333");
		novel1.setNovelUrl("535");
		novel1.setAuthor("ccc");
		novels.add(novel1);
		Novel novel2 = new Novel();
		novel2.setNovelName("6565");
		novel2.setNovelUrl("fd35");
		novel2.setAuthor("nbg");
		novels.add(novel2);
		novelMapper.batchInsert(novels);
	}

}