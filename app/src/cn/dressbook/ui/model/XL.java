package cn.dressbook.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description: 细类
 * @author:袁东华
 * @time:2015-10-12下午6:30:47
 */
public class XL implements Parcelable {
	private String id;
	private String name;
	private int isSelected;

	public XL() {

	}

	public int getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(int isSelected) {
		this.isSelected = isSelected;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		out.writeString(name);
		out.writeInt(isSelected);
	}

	public static final Creator<XL> CREATOR = new Creator<XL>() {
		@Override
		public XL createFromParcel(Parcel in) {
			return new XL(in);
		}

		@Override
		public XL[] newArray(int size) {
			return new XL[size];
		}
	};

	private XL(Parcel in) {
		id = in.readString();
		name = in.readString();
		isSelected = in.readInt();
	}

}
