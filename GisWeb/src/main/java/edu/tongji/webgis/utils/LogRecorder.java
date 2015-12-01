package edu.tongji.webgis.utils;

import java.util.logging.Logger;


public class LogRecorder {
	private static Logger info = Logger.getLogger("InfoLogger");
	private static Logger error = Logger.getLogger("ErrorLogger");
	
	public static void info(String information){
		info.info(information);
	}
	public static void error(String information){
		error.info(information);
	}
}
