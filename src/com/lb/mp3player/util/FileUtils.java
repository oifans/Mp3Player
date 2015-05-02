package com.lb.mp3player.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.lb.mp3player.Constants;
import com.lb.mp3player.model.Mp3Info;

import android.os.Environment;
import android.util.Log;

public class FileUtils {

	private static String SDPATH = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + File.separator;;
	private static FileUtils fileUtils;

	private FileUtils() {

	}

	public static FileUtils getInstance() {
		if (fileUtils == null) {
			fileUtils = new FileUtils();
		}
		return fileUtils;
	}

	public static String fileNameWithSDPath(String fileName) {
		return SDPATH + Constants.MP3SDPATH + fileName;
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
			while ((temp = input.read(buffer)) != -1) {
				output.write(buffer, 0, temp);
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

	public List<Mp3Info> getMp3InfosFromPath(String path) {
		File file = new File(SDPATH + path);
		File[] mp3Files = file.listFiles();
		List<Mp3Info> mp3Infos = new ArrayList<Mp3Info>();
		for (File f : mp3Files) {
			if (f.getName().endsWith(".mp3")) {
				Log.d("MT", f.getName());
				Mp3Info mp3Info = new Mp3Info();
				mp3Info.setMp3Name(f.getName());
				mp3Info.setMp3Size(f.length() + "");
				mp3Infos.add(mp3Info);
			}
		}
		return mp3Infos;
	}
}
