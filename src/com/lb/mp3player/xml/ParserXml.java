package com.lb.mp3player.xml;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.lb.mp3player.model.Mp3Info;

public class ParserXml {
	List<Mp3Info> mp3List = new ArrayList<Mp3Info>();
	Mp3Info mp3Info;

	public List<Mp3Info> parseXMLWithPull(String xmlData)
			throws XmlPullParserException, IOException {

		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		XmlPullParser xmlPullParser = factory.newPullParser();
		xmlPullParser.setInput(new StringReader(xmlData));
		int eventType = xmlPullParser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {

			String nodeName = xmlPullParser.getName();
			if (eventType == XmlPullParser.START_DOCUMENT) {
				mp3List = new ArrayList<Mp3Info>();
			}
			switch (eventType) {
			case XmlPullParser.START_TAG:
				if (nodeName.equals("resource")) {
					mp3Info = new Mp3Info();
				} else if (nodeName.equals("id")) {
					mp3Info.setId(xmlPullParser.nextText());
				} else if (nodeName.equals("mp3.name")) {
					mp3Info.setMp3Name(xmlPullParser.nextText());
				} else if (nodeName.equals("mp3.size")) {
					mp3Info.setMp3Size(xmlPullParser.nextText());
				} else if (nodeName.equals("lrc.name")) {
					mp3Info.setLrcName(xmlPullParser.nextText());
				} else if (nodeName.equals("lrc.size")) {
					mp3Info.setLrcSize(xmlPullParser.nextText());
				}
				break;
			case XmlPullParser.END_TAG:
				if (nodeName.equals("resource")) {
					mp3List.add(mp3Info);
					mp3Info=null;
				}
				break;
			}
			eventType = xmlPullParser.next();
		}
		return mp3List;
	}
}
