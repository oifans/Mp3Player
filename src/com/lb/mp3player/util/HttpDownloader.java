package com.lb.mp3player.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class HttpDownloader {

	public final static int DOWNLOAD_OK = 0;
	public final static int DOWNLOAD_ERROR = -1;
	public final static int DOWNLOAD_EXIST = 1;
	/**
	 * 从网络读取文件返回String
	 * 
	 * @param urlStr
	 * @return
	 */
	public static String download(String urlStr) {
		BufferedReader buffer = null;
		String line = null;
		StringBuilder sb = new StringBuilder();
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			buffer = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			while ((line = buffer.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
/**
 * 下载任意文件
 * @param urlStr
 * @param path
 * @param fileName
 * @return
 */
	public static int downFile(String urlStr, String path, String fileName) {
		InputStream inputStream = null;
		try {
			FileUtils fileUtils = FileUtils.getInstance();
			if (fileUtils.isFileExist(path + fileName)) {
				return DOWNLOAD_EXIST;
			} else {
				inputStream = getInputStreamFromUrl(urlStr);
				File resultFile = fileUtils.write2SDFromInput(path, fileName,
						inputStream);
				if (resultFile == null) {
					return DOWNLOAD_ERROR;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return DOWNLOAD_ERROR;

		} finally {
			try {
				if(inputStream != null){
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return DOWNLOAD_OK;
	}

	public static InputStream getInputStreamFromUrl(String urlStr) {
		Log.d("MT", urlStr);
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			return conn.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}
}
