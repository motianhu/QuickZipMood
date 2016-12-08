package com.smona.base.zipmood;

import java.io.File;

import com.smona.base.zipmood.util.Constants;
import com.smona.base.zipmood.util.FileOperator;
import com.smona.base.zipmood.util.Logger;
import com.smona.base.zipmood.util.ZipFileAction;

public class Main {

	public static void main(String[] args) {
		String encode = System.getProperty("file.encoding");
		println(encode);
		Logger.init();
		String path = System.getProperty("user.dir");
		println(path);
		action(path);
	}

	private static void action(String path) {
		copyFile(path);
		zipFile(path);
	}

	private static void copyFile(String path) {
		String sourcePath = path + Constants.FILE_SEPARATOR
				+ Constants.SOURCE_PATH;
		String xmlPath = path + Constants.FILE_SEPARATOR + Constants.XML_PATH;
		String copyPath = path + Constants.FILE_SEPARATOR + Constants.COPY_PATH;

		deleteFolder(copyPath);
		mkdir(copyPath);

		File source = new File(sourcePath);
		File xml = new File(xmlPath + Constants.FILE_SEPARATOR
				+ Constants.XML_TEMPLATE);
		File properties = new File(xmlPath + Constants.FILE_SEPARATOR
				+ Constants.PROPERTIES_TEMPLATE);

		File[] pics = source.listFiles();
		for (File pic : pics) {
			String picName = pic.getName().substring(0,
					pic.getName().length() - 4);

			String dirPath = copyPath + Constants.FILE_SEPARATOR + picName;
			mkdir(dirPath);

			FileOperator.fileChannelCopy(pic, dirPath
					+ Constants.FILE_SEPARATOR + pic.getName());

			FileOperator.fileChannelCopy(pic, dirPath
					+ Constants.FILE_SEPARATOR + picName + Constants.FONT + Constants.JPG);

			if (xml.exists()) {
				FileOperator.fileChannelCopy(xml, dirPath
						+ Constants.FILE_SEPARATOR + picName + Constants.XML);
			}
			if (properties.exists()) {
				FileOperator.fileChannelCopy(properties, dirPath
						+ Constants.FILE_SEPARATOR + picName
						+ Constants.PROPERTIES);
			}
		}
	}

	private static void zipFile(String path) {
		String copyPath = path + Constants.FILE_SEPARATOR + Constants.COPY_PATH;
		String targetPath = path + Constants.FILE_SEPARATOR
				+ Constants.TARGET_PATH;

		deleteFolder(targetPath);
		mkdir(targetPath);

		File copy = new File(copyPath);
		File[] childs = copy.listFiles();
		ZipFileAction action = new ZipFileAction();
		for (File child : childs) {
			try {
				action.zip(child.getAbsolutePath(), targetPath
						+ Constants.FILE_SEPARATOR + child.getName()
						+ Constants.ZIP);
			} catch (Exception e) {
				println(e.toString());
				e.printStackTrace();
			}
		}
	}

	private static void deleteFolder(String path) {
		File dirFile = new File(path);
		FileOperator.deleteDirectory(dirFile);
	}

	private static void mkdir(String target) {
		File tempDir = new File(target);
		tempDir.mkdir();
	}

	private static void println(String msg) {
		Logger.printDetail(msg);
	}

}
