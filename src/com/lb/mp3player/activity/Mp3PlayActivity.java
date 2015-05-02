package com.lb.mp3player.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.lb.mp3player.Constants;
import com.lb.mp3player.R;
import com.lb.mp3player.model.Mp3Info;
import com.lb.mp3player.service.MusicPlayService;
import com.lb.mp3player.service.MusicPlayService.OnProgressListener;

public class Mp3PlayActivity extends Activity implements OnClickListener,OnSeekBarChangeListener {

	private ImageButton play;
	private Mp3Info mp3Info;
	private SeekBar seekBar;
	private BroadcastReceiver playreceiver;
	private MusicPlayService.MusicBinder musicBinder;
	private ServiceConnection connection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			musicBinder = (MusicPlayService.MusicBinder)service;
			musicBinder.setOnProgressListener(new OnProgressListener() {
				
				@Override
				public void onProgress(int progress) {
					seekBar.setMax(musicBinder.getMusicMax());
					seekBar.setProgress(progress);
				}
			});
		}
	};

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mp3play);
		initView();
		init();
		initEvent();
	}

	private void initEvent() {
		play.setOnClickListener(this);
		seekBar.setOnSeekBarChangeListener(this);
	}

	private void init() {
		mp3Info = getIntent().getParcelableExtra("mp3Info");
		IntentFilter filter = new IntentFilter(Constants.RECEIVER_MUSICPLAY_CHANGE);
		playreceiver = new PlayChangeReceiver();
		registerReceiver(playreceiver, filter);
		bindService(new Intent(this,MusicPlayService.class), connection, BIND_AUTO_CREATE);
	}

	private void initView() {
		play = (ImageButton) findViewById(R.id.bt_play);
		seekBar = (SeekBar)findViewById(R.id.sb_music);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.bt_play:
			Intent intent = new Intent(this,MusicPlayService.class);
			intent.putExtra("mp3Info", mp3Info);
			startService(intent);
		
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(connection);
		unregisterReceiver(playreceiver);
	}

	/**
	 * 播放状态改变广播接收器，根据状态不同，改变UI
	 * @author Administrator
	 *
	 */
	class PlayChangeReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			boolean isplay = intent.getBooleanExtra("isplay", false);
			if(isplay){
				play.setBackgroundResource(R.drawable.play);
			}else{
				play.setBackgroundResource(R.drawable.pause);
			}
		}
	}

	@Override
	public void onProgressChanged(SeekBar s, int progress, boolean arg2) {
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		musicBinder.stopProgress();
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		seekBar.setProgress(seekBar.getProgress());
		musicBinder.seekPlayMusic(seekBar.getProgress());
		musicBinder.startProgress();
	}
}
