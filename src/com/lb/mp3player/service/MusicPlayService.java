package com.lb.mp3player.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.lb.mp3player.Constants;
import com.lb.mp3player.model.Mp3Info;
import com.lb.mp3player.util.FileUtils;

public class MusicPlayService extends Service {

	private boolean isplay = false;
	private boolean isProgress = true;
	private MediaPlayer mediaPlayer;
	private Mp3Info mp3Info;
	private MusicBinder musicBinder = new MusicBinder();
	private OnProgressListener onProgressListener;
	private int progress = 0;

	public interface OnProgressListener {
		void onProgress(int progress);
	}

	
	@Override
	public void onCreate() {
		mediaPlayer = new MediaPlayer();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		mp3Info = intent.getParcelableExtra("mp3Info");

		try {
			mediaPlayer.setDataSource(FileUtils.fileNameWithSDPath(mp3Info
					.getMp3Name()));
			mediaPlayer.prepare();
		} catch (Exception e) {
			e.printStackTrace();
		}

		playMusic();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return musicBinder;
	}

	public class MusicBinder extends Binder {
		/**
		 * 得到当前播放进度
		 * 
		 * @return
		 */
		public int getMusicCurrent() {
			return mediaPlayer.getCurrentPosition();
		}

		/**
		 * 播放指定进度
		 * 
		 * @param progress
		 */
		public void seekPlayMusic(int progress) {
			mediaPlayer.seekTo(progress);
		}

		public int getMusicMax() {
			return mediaPlayer.getDuration();
		}
		
		public void setOnProgressListener(OnProgressListener theonProgressListener) {
			onProgressListener = theonProgressListener;
		}
		
		public void stopProgress(){
			isProgress=false;
		}
		public void startProgress(){
			isProgress=true;
			new Thread(new ProgressThread()).start();
		}

	}

	private void playMusic() {
		Intent intent = new Intent(Constants.RECEIVER_MUSICPLAY_CHANGE);
		
		if (!isplay) {
			mediaPlayer.start();
			isplay = true;
			new Thread(new ProgressThread()).start();
			intent.putExtra("isplay", isplay);
		} else {
			mediaPlayer.pause();
			isplay = false;
			intent.putExtra("isplay", isplay);
		}
		sendBroadcast(intent);
	}

	class ProgressThread implements Runnable {

		public void run() {
			Log.d("MT", "ProgressThread"+System.currentTimeMillis());
			progress = mediaPlayer.getCurrentPosition();
			int max = mediaPlayer.getDuration();
			//Log.d("MT", "progress="+progress+" max="+max+" isplay="+isplay);
			while (progress < max && isplay && isProgress){
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (onProgressListener != null) {
					onProgressListener.onProgress(progress);
				}
			progress = mediaPlayer.getCurrentPosition();
		}
		}
	}

}
