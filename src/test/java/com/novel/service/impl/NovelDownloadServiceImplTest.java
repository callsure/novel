package com.novel.service.impl;

import com.base.BaseJunit;
import com.novel.entitys.DownNovel;
import com.novel.interfaces.impl.download.DefaultNovelDownLoad;
import com.novel.mapper.DownNovelMapper;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by runshu.lin on 16/12/16.
 */
public class NovelDownloadServiceImplTest extends BaseJunit {

	@Resource
	DefaultNovelDownLoad defaultNovelDownLoad;

	@Resource
	DownNovelMapper downNovelMapper;

	@Test
	public void process() throws Exception {
		Set<Integer> set = new HashSet<>();
		set.add(23);
		set.add(35);
		set.add(41);
		set.add(47);
		set.add(15);
		set.add(53);
		Iterator<Integer> iterator = set.iterator();
		while (iterator.hasNext()){
			Integer id = iterator.next();
			defaultNovelDownLoad.process(id);
		}
	}

	@Test
	public void Random(){
		Random rand =new Random();
		Random rand1 =new Random();
		int i;
		i=rand.nextInt(10000);
		int j = rand1.nextInt(10000);
		System.out.println(i);
		System.out.println(j);
	}

	@Test
	public void insert(){
		DownNovel downNovel = new DownNovel();
		downNovel.setnName("sssss");
		downNovel.setnId(22);
		downNovel.setnUrl("sssss");
		downNovel.setnAuthor("sdfs");
		int i = downNovelMapper.insert(downNovel);
		System.out.println(i);
		System.out.println(downNovel.getdId());
	}

}