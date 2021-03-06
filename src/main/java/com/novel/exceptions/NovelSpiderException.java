package com.novel.exceptions;

/**
 * 对所有爬取过程中的异常进行封装
 * Created by runshu.lin on 16/11/29.
 */
public class NovelSpiderException extends Exception {
	private static final long serialVersionUID = 6758225611864282799L;

	public NovelSpiderException() {
	}

	public NovelSpiderException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NovelSpiderException(String message, Throwable cause) {
		super(message, cause);
	}

	public NovelSpiderException(String message) {
		super(message);
	}

	public NovelSpiderException(Throwable cause) {
		super(cause);
	}
}
