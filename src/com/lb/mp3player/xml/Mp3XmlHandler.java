package com.lb.mp3player.xml;

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.lb.mp3player.model.Mp3Info;

public class Mp3XmlHandler extends DefaultHandler {

	private List<Mp3Info> infos = null;
	private Mp3Info mp3Info = null;
	private String tagName = null;

	public Mp3XmlHandler(List<Mp3Info> list) {
		infos = list;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String temp = new String(ch, start, length).trim();
		if (tagName.equals("id")) {
			if (mp3Info.getId() == null) {
				mp3Info.setId(temp);
			}
		} else if (tagName.equals("mp3.name")) {
			if (mp3Info.getMp3Name() == null) {
				mp3Info.setMp3Name(temp);
			}
		} else if (tagName.equals("mp3.size")) {
			if (mp3Info.getMp3Size() == null) {
				mp3Info.setMp3Size(temp);
			}
		} else if (tagName.equals("lrc.name")) {
			if (mp3Info.getLrcName() == null) {
				mp3Info.setLrcName(temp);
			}
		} else if (tagName.equals("lrc.size")) {
			if (mp3Info.getLrcSize() == null) {
				mp3Info.setLrcSize(temp);
			}
		}
	}

	@Override
	public void endDocument() throws SAXException {
		Log.d("MT", "endDocument");
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		Log.d("MT", "endElement qName:" + qName);
		if (qName.equals("resource")) {
			Log.d("MT", "add" + mp3Info.toString());
			infos.add(mp3Info);
		}
	}

	@Override
	public void startDocument() throws SAXException {
		Log.d("MT", "startDocument");
		super.startDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		Log.d("MT", "startElement qName:" + qName);
		tagName = localName;
		if (tagName.equals("resource")) {
			mp3Info = new Mp3Info();
		}
	}

}
