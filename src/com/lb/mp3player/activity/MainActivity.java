package com.lb.mp3player.activity;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lb.mp3player.Contants;
import com.lb.mp3player.R;
import com.lb.mp3player.model.Mp3Info;
import com.lb.mp3player.util.HttpDownloader;
import com.lb.mp3player.xml.Mp3XmlHandler;

public class MainActivity extends Activity {

	List<Mp3Info> mp3InfoList;
	ListView mp3ListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		downloadXML(Contants.MP3XMLURL);
		initView();
		initEvent();
	}

	private void initEvent() {
	}

	private void initView() {
		mp3ListView = (ListView)findViewById(R.id.lv_mp3list);
		
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
		mp3ListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,mp3Name));
		
	}
}
