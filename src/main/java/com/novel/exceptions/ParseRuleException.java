package com.novel.exceptions;

/**
 * Created by runshu.lin on 16/11/29.
 */
public class ParseRuleException extends NovelSpiderException {
	private static final long serialVersionUID = 7914081413072133444L;

	public ParseRuleException() {
	}

	public ParseRuleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ParseRuleException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParseRuleException(String message) {
		super(message);
	}

	public ParseRuleException(Throwable cause) {
		super(cause);
	}
	
}
