package com.novel.interfaces.impl.storage;

import com.base.BaseJunit;
import com.novel.interfaces.IProcessor;
import com.novel.utils.SpringContextManager;
import org.junit.Test;

/**
 * Created by runshu.lin on 16/12/4.
 */
public class BxwxNovelStorageTest extends BaseJunit{
	@Test
	public void processTest(){
		IProcessor processor = SpringContextManager.getBean("bxwxNovelStorage");
		processor.process();
	}
}