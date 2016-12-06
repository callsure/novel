package com.novel.configuration;

import java.io.Serializable;

/**
 * Created by runshu.lin on 16/12/2.
 */
public class Configuration implements Serializable {
	private static final long serialVersionUID = 3447037849486547472L;

	private static final int DEFAULT_SIZE = 100;

	private static final int DAFAULT_TRY_TIMES = 3;

	private String savePath;

	private int size;

	private int tryTimes;

	public Configuration() {
		this.size = DEFAULT_SIZE;
		this.tryTimes = DAFAULT_TRY_TIMES;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getTryTimes() {
		return tryTimes;
	}

	public void setTryTimes(int tryTimes) {
		this.tryTimes = tryTimes;
	}
}
