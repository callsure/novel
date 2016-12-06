package com.base;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * Created by linrunshu on 16/8/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@ContextConfiguration({"/spring-mybatis.xml"})
public class BaseJunit {
	@Before
	public void setUp() throws Exception {
		System.out.println("start==========>");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("end==========>");
	}
}
