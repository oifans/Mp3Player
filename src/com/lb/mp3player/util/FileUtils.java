package com.lb.mp3player.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Environment;

public class FileUtils {

	private static String SDPATH;
	private static FileUtils fileUtils;

	private FileUtils(){
		
	}
	public static FileUtils getInstance() {
		SDPATH = Environment.getExternalStorageDirectory().getAbsolutePath()
				+ "/";
		if(fileUtils == null){
			fileUtils = new FileUtils();
		}
		return fileUtils;
	}

	public File creatFileInSDCard(String fileName) throws IOException {
		File file = new File(SDPATH + fileName);
		file.createNewFile();
		return file;
	}


	public File createSDDir(String dirName) {
		File dir = new File(SDPATH + dirName);
		dir.mkdir();
		return dir;
	}

	public boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		return file.exists();
	}

	public String getSDPATH() {
		return SDPATH;
	}

	public File write2SDFromInput(String path, String fileName,
			InputStream input) {
		File file = null;
		OutputStream output = null;
		try {
			createSDDir(path);
			file = creatFileInSDCard(path + fileName);
			output = new FileOutputStream(file);
			byte buffer[] = new byte[4 * 1024];
			int temp;
			while ((temp =input.read(buffer)) != -1) {
				output.write(buffer,0,temp);
			}
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}
}
