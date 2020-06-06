package com.novel.listener;

import com.novel.logs.workthread.LoggingThread;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by runshu.lin on 2017/10/28.
 */
public class LogListener implements ServletContextListener {
	private Thread thread;
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String str = null;
		if (str == null && thread == null) {
			thread = new Thread(LoggingThread.getInstance(),"日志线程");
			thread.start(); // servlet 上下文初始化时启动
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		if (thread != null && thread.isInterrupted()) {
			thread.interrupt();
		}
	}
}
