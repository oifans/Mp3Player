package com.lb.mp3player.fragment;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.lb.mp3player.Constants;
import com.lb.mp3player.R;
import com.lb.mp3player.activity.MainActivity;
import com.lb.mp3player.model.Mp3Info;
import com.lb.mp3player.service.DownloadService;
import com.lb.mp3player.util.HttpDownloader;
import com.lb.mp3player.xml.Mp3XmlHandler;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class OnlineMp3Fragment extends Fragment {

	List<Mp3Info> mp3InfoList;
	ListView mp3ListView;
	Context mcontext;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		mcontext = getActivity();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_onlinemp3list, null);
		downloadXML(Constants.MP3XMLURL);
		initView(view);
		return view;
	}
	
	private void initView(View view) {
		mp3ListView = (ListView)view.findViewById(R.id.lv_onlinemp3list);
		mp3ListView.setOnItemClickListener( new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Intent intent = new Intent(mcontext,DownloadService.class);
				intent.putExtra("mp3Info", mp3InfoList.get(position));
				mcontext.startService(intent);
			}
		});
	}
	
	private String downloadXML(String urlStr) {
		new AsyncTask<String, Void, String>() {

			@Override
			protected String doInBackground(String... str) {
				return HttpDownloader.download(str[0]);
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				mp3InfoList = new ArrayList<Mp3Info>();
				parseXMLWithSAX(result);
			}
		}.execute(urlStr);
		return urlStr;
	}
	
	private void parseXMLWithSAX(String xmlData){
		try{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		XMLReader xmlReader = factory.newSAXParser().getXMLReader();
		Mp3XmlHandler handler = new Mp3XmlHandler(mp3InfoList);
		xmlReader.setContentHandler(handler);
		xmlReader.parse(new InputSource(new StringReader(xmlData)));
		}catch(Exception e){
			e.printStackTrace();
		}
		List<String> mp3Name = new ArrayList<String>();
		for(Mp3Info mp3 : mp3InfoList){
			mp3Name.add(mp3.getMp3Name());
		}
		mp3ListView.setAdapter(new ArrayAdapter<String>(mcontext, android.R.layout.simple_list_item_1,mp3Name));
		
	}
	
}
