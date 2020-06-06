package com.novel.logs.workthread;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;


public abstract class WorkingThread implements Runnable {

	protected static final long MILLI_NANO_CONVERSION = 1000 * 1000L;
	/** 默认超时时间（毫秒） */
	private static final long DEFAULT_TIME_OUT = 2000;

	protected final Random RANDOM = new Random();

	public static final String MagicWord = "MAGIC_WORD_EXIT";
	protected boolean flag = false;
	protected LinkedBlockingQueue<Object> queue;

	public WorkingThread() {
		queue = new LinkedBlockingQueue<>();
		
	}

	public void stopProcessing() {
		flag = true;

		try {
			queue.put(MagicWord);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean requestToQuit() {

		return requestToQuit(DEFAULT_TIME_OUT);
	}

	public void waitForCompletion(long timeout) {
		long nano = System.nanoTime();
		timeout *= MILLI_NANO_CONVERSION;
		try {
			while ((System.nanoTime() - nano) < timeout) {

				if (queue.isEmpty()) {
					break;
				}
				// 短暂休眠，避免出现活锁
				Thread.sleep(30, RANDOM.nextInt(500));
			}
		} catch (Exception e) {
			throw new RuntimeException("Getting List Size Error", e);
		}
	}

	public boolean requestToQuit(long timeout) {

		stopProcessing();
		waitForCompletion(timeout);
		return true;

	}

	public void put(Object obj){
		try {
			queue.put(obj);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Object take(){
		try {
			return queue.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
