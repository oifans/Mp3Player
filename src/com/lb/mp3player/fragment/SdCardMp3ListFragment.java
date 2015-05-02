package com.lb.mp3player.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lb.mp3player.R;
import com.lb.mp3player.activity.Mp3PlayActivity;
import com.lb.mp3player.model.Mp3Info;
import com.lb.mp3player.util.FileUtils;
import com.lb.mp3player.util.HttpDownloader;

public class SdCardMp3ListFragment extends Fragment {

	private Context mcontext;
	private ListView sdCardList;
	private List<Mp3Info> mp3Infos;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		mcontext=getActivity();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_sdcardmp3list, null);
		initView(rootView);
		initEvent();
		return rootView;
	}

	private void initEvent() {
		sdCardList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> listView, View view, int position,
					long id) {
				Intent intent = new Intent(mcontext,Mp3PlayActivity.class);
				intent.putExtra("mp3Info", mp3Infos.get(position));
				startActivity(intent);
			}
		});
	}

	private void initView(View rootView) {
		sdCardList = (ListView)rootView.findViewById(R.id.lv_sdcardmp3list);
		FileUtils fileUtils = FileUtils.getInstance();
		mp3Infos = fileUtils.getMp3InfosFromPath("mymp3/");
		List<String> mp3Names = new ArrayList<String>();
		for(Mp3Info mp3 : mp3Infos){
			mp3Names.add(mp3.getMp3Name());
		}
		sdCardList.setAdapter(new ArrayAdapter<String>(mcontext, android.R.layout.simple_list_item_1, mp3Names));
	}

}
