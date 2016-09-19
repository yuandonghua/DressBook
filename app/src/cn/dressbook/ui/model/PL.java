package cn.dressbook.ui.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description: 品类
 * @author:袁东华
 * @time:2015-10-12下午6:33:11
 */
public class PL implements Parcelable {
	private String id;
	private String name;
	private int isSelected;
	private ArrayList<XL> xls;

	public PL() {

	}

	public int getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(int isSelected) {
		this.isSelected = isSelected;
	}

	public ArrayList<XL> getXls() {
		return xls;
	}

	public void setXls(ArrayList<XL> xls) {
		this.xls = xls;
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
		out.writeTypedList(xls);
		out.writeInt(isSelected);

	}

	public static final Creator<PL> CREATOR = new Creator<PL>() {
		@Override
		public PL createFromParcel(Parcel in) {
			return new PL(in);
		}

		@Override
		public PL[] newArray(int size) {
			return new PL[size];
		}
	};

	private PL(Parcel in) {
		id = in.readString();
		name = in.readString();
		in.readTypedList(xls, XL.CREATOR);
		isSelected = in.readInt();
	}

}
