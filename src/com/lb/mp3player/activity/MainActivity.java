package com.lb.mp3player.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.lb.mp3player.R;
import com.lb.mp3player.fragment.OnlineMp3Fragment;
import com.lb.mp3player.fragment.SdCardMp3ListFragment;

public class MainActivity extends Activity implements OnClickListener{
	
	private OnlineMp3Fragment onlineFragment ;
	private SdCardMp3ListFragment sdCardFragment;
	private Button online,sdcard;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initEvent();
	}

	private void initView() {
		onlineFragment = new OnlineMp3Fragment();
		sdCardFragment = new SdCardMp3ListFragment();		
		online = (Button)findViewById(R.id.bt_onlineMp3List);
		sdcard = (Button)findViewById(R.id.bt_sdcardMp3List);
	}

	private void initEvent() {
		online.setOnClickListener(this);
		sdcard.setOnClickListener(this);
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.ll_content, sdCardFragment);
		ft.commit();
	}

	@Override
	public void onClick(View view) {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		switch (view.getId()) {
		case R.id.bt_onlineMp3List:
			ft.replace(R.id.ll_content, onlineFragment);
			break;
		case R.id.bt_sdcardMp3List:
			ft.replace(R.id.ll_content, sdCardFragment);
			break;
		}
		ft.commit();
	}
}
