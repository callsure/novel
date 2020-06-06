package com.novel.logs.workthread;

import com.novel.logs.LogLevel;
import com.novel.logs.LogMessage;
import com.novel.utils.LogUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


/*
 * @Description: TODO
 * @author Melon
 * @
 * @date 2014-3-7 上午11:13:17
 */
public class LoggingThread extends WorkingThread {

	private volatile static   LoggingThread INSTANCE;
	public static   LoggingThread getInstance(){
		if (INSTANCE==null){
			synchronized (LoggingThread.class) {
				if (INSTANCE==null){
					INSTANCE = new LoggingThread();
				}
			}
		}
		return INSTANCE;
	}
	private static final Logger localLog = LogManager.getLogger(LoggingThread.class);
	private LoggingThread(){
	}
	
	@Override
	public void run() {
		localLog.debug("LoggerThread Starting");
		while(true){
				try{
					// read Logging Object
					LogMessage logMessage=null;
					Object obj=take();
					if(obj instanceof LogMessage)
					{
						logMessage=(LogMessage)obj;
					}
					else{
						if (obj.toString().equals(MagicWord)){ 
							localLog.debug("PERFORM MAGIC");
							break;
						}
					}
					
					Logger logger = LogUtil.getLogger(logMessage.getLogger());
					if(logger==null)
					{
						localLog.debug(logMessage.getLogger()+" not exit!!");
						continue;
					}
					switch (LogLevel.values()[logMessage.getLogLevel().ordinal()]) {
					case ERROR:
						logger.error(logMessage.getMessage());
						break;
					case INFO:
						logger.info(logMessage.getMessage());
						break;
					case DEBUG:
						logger.debug(logMessage.getMessage());
						break;
					default:
						break;
					}
					
				}catch(Exception e)
				{
					e.printStackTrace();
				}
		}
		localLog.debug("LoggerThread Thread die");
	}

	public void addLog(LogLevel logLevel, String loggerName, String message)
	{
		LogMessage logMessage = new LogMessage();
		logMessage.setLogger(loggerName);
		logMessage.setMessage(message);
		logMessage.setLogLevel(logLevel);
		put(logMessage);
	}
	

	
	public void addLoginLog(String message){
		LogMessage logMessage = new LogMessage();

		logMessage.setLogger("loginLogger");
		logMessage.setMessage(message);
		put(logMessage);
	}

}
