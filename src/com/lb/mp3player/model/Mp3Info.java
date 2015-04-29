package com.lb.mp3player.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Mp3Info implements Parcelable {
	private String id;
	private String mp3Name;
	private String mp3Size;
	private String lrcName;
	private String lrcSize;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMp3Name() {
		return mp3Name;
	}

	public void setMp3Name(String mp3Name) {
		this.mp3Name = mp3Name;
	}

	public String getMp3Size() {
		return mp3Size;
	}

	public void setMp3Size(String mp3Size) {
		this.mp3Size = mp3Size;
	}

	public String getLrcName() {
		return lrcName;
	}

	public void setLrcName(String lrcName) {
		this.lrcName = lrcName;
	}

	public String getLrcSize() {
		return lrcSize;
	}

	public void setLrcSize(String lrcSize) {
		this.lrcSize = lrcSize;
	}

	public Mp3Info() {
	}

	public Mp3Info(String id, String mp3Name, String mp3Size, String lrcName,
			String lrcSize) {
		super();
		this.id = id;
		this.mp3Name = mp3Name;
		this.mp3Size = mp3Size;
		this.lrcName = lrcName;
		this.lrcSize = lrcSize;
	}

	@Override
	public String toString() {
		return "Mp3Info [id=" + id + ", mp3Name=" + mp3Name + ", mp3Size="
				+ mp3Size + ", lrcName=" + lrcName + ", lrcSize=" + lrcSize
				+ "]";
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeString(id);
		dest.writeString(mp3Name);
		dest.writeString(mp3Size);
		dest.writeString(lrcName);
		dest.writeString(lrcSize);
	}

	public static final Parcelable.Creator<Mp3Info> CREATOR = new Parcelable.Creator<Mp3Info>() {

		@Override
		public Mp3Info createFromParcel(Parcel source) {
			Mp3Info mp3Info = new Mp3Info();
			mp3Info.setId(source.readString());
			mp3Info.setMp3Name(source.readString());
			mp3Info.setMp3Size(source.readString());
			mp3Info.setLrcName(source.readString());
			mp3Info.setLrcSize(source.readString());
			return mp3Info;
		}

		@Override
		public Mp3Info[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Mp3Info[size];
		}
	};

}
