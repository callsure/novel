package com.novel.utils;

import java.io.File;

/**
 * Created by runshu.lin on 16/12/17.
 */
public class FileUtil {

	private FileUtil() {}

	/**
	 * 删除文件夹和文件
	 * @param file
	 */
	public static void clearDir(File file) {
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				clearDir(f);
				f.delete();
			}
		}
		file.delete();
	}
}
