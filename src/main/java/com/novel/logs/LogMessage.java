package com.novel.logs;

import java.io.Serializable;
/**
 * 日志信息
 * @author Melon
 * @date: 2014-10-29 下午8:14:33
 */
public class LogMessage implements Serializable {
	private static final long serialVersionUID = 6527596173932783475L;
	
	private String logger;
	private String message;
	private LogLevel logLevel;
	public String getLogger() {
		return logger;
	}
	public void setLogger(String logger) {
		this.logger = logger;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LogLevel getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(LogLevel logLevel) {
		this.logLevel = logLevel;
	}
}
