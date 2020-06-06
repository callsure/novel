package com.novel.utils;

import com.novel.logs.LogLevel;
import com.novel.logs.LoggerType;
import com.novel.logs.workthread.LoggingThread;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;


public class LogUtil {
	public static Map<String, Logger> map = new HashMap<>();
	
	static{
		map.put(LoggerType.DEVELOPLOGGER, LogManager.getLogger(LoggerType.DEVELOPLOGGER));
		map.put(LoggerType.APPLOGGER, LogManager.getLogger(LoggerType.APPLOGGER));
		map.put(LoggerType.LOGINLOGGER, LogManager.getLogger(LoggerType.LOGINLOGGER));
		map.put(LoggerType.ONLINELOGGER, LogManager.getLogger(LoggerType.ONLINELOGGER));
	}
	private LogUtil(){}

	public static Logger getLogger(String loggerName){
		return map.get(loggerName);
	}
	
	/***
	 * 
	 * @param module:所属模块
	 * @param id:发起者ID，如果是系统则为system
	 * @param operation:操作内容，一般为动宾结构
	 * @param result:操作结果，例如成功、失败
	 * @param notes:备注，动作对相关对象的影响。格式比较自由。不同类型数值用分号隔开，同一类型的不同值用逗号隔开
	 */
	public static void info(String loggerType,String module,String id,String operation,String result,String notes)
	{
		StackTraceElement[] temp=Thread.currentThread().getStackTrace();
		StackTraceElement a=temp[2];
		
		StringBuffer sb=new StringBuffer();
		//method info
		sb.append(a.getClassName());
		sb.append(".");
		sb.append(a.getMethodName());
		sb.append("(");
		sb.append(a.getFileName());
		sb.append(":");
		sb.append(a.getLineNumber());
		sb.append(")");
		sb.append(" ");
		//log msg
		sb.append(module);
		sb.append("|");
		sb.append(id);
		sb.append("|");
		sb.append(operation);
		sb.append("|");
		sb.append(result);
		if(!StringUtils.isEmpty(notes))
		{
			sb.append("|");
			sb.append(notes);	
		}
		
		LoggingThread.getInstance().addLog(LogLevel.INFO,loggerType, sb.toString());
		
	}
	
	public static void info(String loggerType,String content)
	{
		StackTraceElement[] temp=Thread.currentThread().getStackTrace();
		StackTraceElement a=temp[2];
		
		StringBuffer sb=new StringBuffer();
		//method info
		sb.append(a.getClassName());
		sb.append(".");
		sb.append(a.getMethodName());
		sb.append("(");
		sb.append(a.getFileName());
		sb.append(":");
		sb.append(a.getLineNumber());
		sb.append(")");
		sb.append(" ");
		//log msg
		sb.append(content);
		LoggingThread.getInstance().addLog(LogLevel.INFO,loggerType, sb.toString());
		
	}
	
	/***
	 * 
	 * @param module:所属模块
	 * @param id:发起者ID，如果是系统则为system
	 * @param operation:操作内容，一般为动宾结构
	 * @param errorReason:失败原因
	 * @param notes:备注，动作对相关对象的影响。格式比较自由。不同类型数值用分号隔开，同一类型的不同值用逗号隔开
	 */	
	public static void error(String loggerType,String module,String id,String operation,String errorReason,String notes)
	{
		StackTraceElement[] temp=Thread.currentThread().getStackTrace();
		StackTraceElement a=(StackTraceElement)temp[2];
		
		StringBuffer sb=new StringBuffer();
		//method info
		sb.append(a.getClassName());
		sb.append(".");
		sb.append(a.getMethodName());
		sb.append("(");
		sb.append(a.getFileName());
		sb.append(":");
		sb.append(a.getLineNumber());
		sb.append(")");
		sb.append(" ");
		//log msg
		sb.append(module);
		sb.append("|");
		sb.append(id);
		sb.append("|");
		sb.append(operation);
		sb.append("|");
		sb.append(errorReason);
		if(!StringUtils.isEmpty(notes))
		{
			sb.append("|");
			sb.append(notes);	
		}
		LoggingThread.getInstance().addLog(LogLevel.ERROR, loggerType, sb.toString());
	}

	public static Logger getLogger(){
		return LogManager.getLogger(LogUtil.class);
	}
}
