package com.lb.mp3player.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.IBinder;
import android.util.Log;

import com.lb.mp3player.Constants;
import com.lb.mp3player.R;
import com.lb.mp3player.model.Mp3Info;
import com.lb.mp3player.util.FileUtils;
import com.lb.mp3player.util.HttpDownloader;

/**
 * 下载MP3歌曲服务
 * 
 * @author Administrator
 * 
 */
public class DownloadService extends Service {

	FileUtils fileUtils;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		fileUtils = FileUtils.getInstance();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		final Mp3Info mp3Info = intent.getParcelableExtra("mp3Info");
		DownloadThread downloadThread = new DownloadThread(mp3Info);
		new Thread(downloadThread).start();
		return super.onStartCommand(intent, flags, startId);
	}

	class DownloadThread implements Runnable {

		private Mp3Info mp3Info;

		public DownloadThread(Mp3Info mp3Info) {
			super();
			this.mp3Info = mp3Info;
		}

		@Override
		public void run() {
			int stateCode = HttpDownloader.downFile(
					Constants.MP3URL + mp3Info.getMp3Name(), "mymp3/",
					mp3Info.getMp3Name());
			Log.d("MT", stateCode + "");
			String resultMessage = null;
			Resources resources =getResources();
			switch (stateCode) {
			case HttpDownloader.DOWNLOAD_OK:
				resultMessage = resources.getString(R.string.download_ok);
				break;
			case HttpDownloader.DOWNLOAD_ERROR:
				resultMessage = resources.getString(R.string.download_error);
				break;
			case HttpDownloader.DOWNLOAD_EXIST:
				resultMessage = resources.getString(R.string.download_exist);
				break;
			}
			NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			Notification notification = new Notification.Builder(
					DownloadService.this).setContentTitle("文件下载")
					.setContentText(resultMessage)
					.setSmallIcon(R.drawable.ic_launcher).build();
			notificationManager.notify(1, notification);
			stopSelf();
		}
	}
}
