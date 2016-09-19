package cn.dressbook.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description:客服实体类
 * @author:袁东华
 * @modifier:袁东华
 * @remarks:
 * @2015-6-2 上午9:35:25
 */
public class CustomService implements Parcelable{
	private String id;
	private String icon;

	private String name;

	private String online;

	private String inTime;
	private String outTime;

	public String getOutTime() {
		return outTime;
	}

	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}

	public CustomService() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOnline() {
		return online;
	}

	public void setOnline(String online) {
		this.online = online;
	}

	public String getInTime() {
		return inTime;
	}

	public void setInTime(String inTime) {
		this.inTime = inTime;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		// TODO Auto-generated method stub
		out.writeString(id);
		out.writeString(icon);
		out.writeString(name);
		out.writeString(online);
		out.writeString(inTime);
		out.writeString(outTime);
	}

}