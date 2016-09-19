package cn.dressbook.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description: 城市信息
 * @author:袁东华
 * @time:2015-9-1下午10:10:38
 */
public class CityInfo implements Parcelable {

	public String id;
	public String parentId;
	public String name;

	public CityInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(id);
		out.writeString(parentId);
		out.writeString(name);

	}

	public static final Creator<CityInfo> CREATOR = new Creator<CityInfo>() {
		@Override
		public CityInfo createFromParcel(Parcel in) {
			return new CityInfo(in);
		}

		@Override
		public CityInfo[] newArray(int size) {
			return new CityInfo[size];
		}
	};

	private CityInfo(Parcel in) {
		id = in.readString();
		parentId = in.readString();
		name = in.readString();

	}

}
