package cn.dressbook.ui.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description: 美衣讯
 * @author:袁东华
 * @time:2015-9-28上午11:56:56
 */
public class MYX1 implements Parcelable {
	private String subject;
	private String state;
	private String publishTimeShow;
	private String id;
	private ArrayList<MYXChild> btArticles;

	public MYX1() {

	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPublishTimeShow() {
		return publishTimeShow;
	}

	public void setPublishTimeShow(String publishTimeShow) {
		this.publishTimeShow = publishTimeShow;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	//
	public ArrayList<MYXChild> getBtArticles() {
		return btArticles;
	}

	public void setBtArticles(ArrayList<MYXChild> btArticles) {
		this.btArticles = btArticles;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(subject);
		out.writeString(state);
		out.writeString(publishTimeShow);
		out.writeString(id);
		out.writeString(state);
		out.writeTypedList(btArticles);

	}

	public static final Creator<MYX1> CREATOR = new Creator<MYX1>() {
		public MYX1 createFromParcel(Parcel in) {
			return new MYX1(in);
		}

		public MYX1[] newArray(int size) {
			return new MYX1[size];
		}
	};

	private MYX1(Parcel in) {
		subject = in.readString();
		state = in.readString();
		publishTimeShow = in.readString();
		id = in.readString();
		state = in.readString();
		in.readTypedList(btArticles, MYXChild.CREATOR);
	}
	
}
