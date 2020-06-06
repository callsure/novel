package com.novel.logs;


import com.novel.logs.workthread.LoggingThread;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DevelopLogger {

	public Logger logger= LogManager.getLogger("developLogger");
	private static DevelopLogger developper=new DevelopLogger();
	
	public static DevelopLogger getInstance()
	{
		return developper;
	}
	
	/***
	 * 
	 * @param module:所属模块
	 * @param id:发起者ID，如果是系统则为system
	 * @param operation:操作内容，一般为动宾结构
	 * @param result:操作结果，例如成功、失败
	 * @param notes:备注，动作对相关对象的影响。格式比较自由。不同类型数值用分号隔开，同一类型的不同值用逗号隔开
	 */
	public static void info(String module,String id,String operation,String result,String notes)
	{
		if(DevelopLogger.getInstance().logger.isInfoEnabled()){
			String logContent=module+"|"+id+"|"+operation+"|"+result+"|"+notes;
//			DevelopLogger.getInstance().logger.info(logContent);
			LoggingThread.getInstance().addLog(LogLevel.INFO,LoggerType.DEVELOPLOGGER, logContent);
			
		}
	}
	
	public static void info(String content)
	{
		if(DevelopLogger.getInstance().logger.isInfoEnabled()){
			String logContent=content;
//			DevelopLogger.getInstance().logger.info(logContent);
			LoggingThread.getInstance().addLog(LogLevel.INFO,LoggerType.DEVELOPLOGGER, logContent);
			
		}
	}
	
	/***
	 * 
	 * @param module:所属模块
	 * @param id:发起者ID，如果是系统则为system
	 * @param operation:操作内容，一般为动宾结构
	 * @param errorReason:失败原因
	 * @param notes:备注，动作对相关对象的影响。格式比较自由。不同类型数值用分号隔开，同一类型的不同值用逗号隔开
	 */	
	public static void error(String module,String id,String operation,String errorReason,String notes)
	{
		String logContent=module+"|"+id+"|"+operation+"|"+errorReason+"|"+notes;
//		DevelopLogger.getInstance().logger.error(logContent);
		LoggingThread.getInstance().addLog(LogLevel.ERROR,LoggerType.DEVELOPLOGGER, logContent);
	}

}
