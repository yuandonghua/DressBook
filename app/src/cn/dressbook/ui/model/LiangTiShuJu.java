package cn.dressbook.ui.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description 量体数据
 * @author 熊天富
 * @date 2016-01-29 18:41:06
 */
public class LiangTiShuJu implements Parcelable {

	private String id;
	private String name;
	private String cls;
	private String pic;
	private String descr;
	private String value;
	private ArrayList<String> prevalArr;
	private String celiangValue;
	private String lastOper;
	private String lastOpTime;
	private String unit;

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

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	public ArrayList<String> getPrevalArr() {
		return prevalArr;
	}

	public void setPrevalArr(ArrayList<String> prevalArr) {
		this.prevalArr = prevalArr;
	}
	

	public String getCeliangValue() {
		return celiangValue;
	}

	public void setCeliangValue(String celiangValue) {
		this.celiangValue = celiangValue;
	}
	

	public String getLastOper() {
		return lastOper;
	}

	public void setLastOper(String lastOper) {
		this.lastOper = lastOper;
	}

	public String getLastOpTime() {
		return lastOpTime;
	}

	public void setLastOpTime(String lastOpTime) {
		this.lastOpTime = lastOpTime;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public LiangTiShuJu() {
		super();
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(id);
		out.writeString(name);
		out.writeString(descr);
		out.writeString(cls);
		out.writeString(pic);
		out.writeString(value);
		out.writeStringList(prevalArr);
		out.writeString(celiangValue);
		out.writeString(lastOper);
		out.writeString(lastOpTime);
		out.writeString(unit);
	}

	public static final Creator<LiangTiShuJu> CREATOR = new Creator<LiangTiShuJu>() {
		@Override
		public LiangTiShuJu createFromParcel(Parcel in) {
			return new LiangTiShuJu(in);
		}

		@Override
		public LiangTiShuJu[] newArray(int size) {
			return new LiangTiShuJu[size];
		}
	};

	@SuppressWarnings("unchecked")
	private LiangTiShuJu(Parcel in) {
		id = in.readString();
		name = in.readString();
		descr = in.readString();
		cls = in.readString();
		pic = in.readString();
		value=in.readString();
		prevalArr=in.readArrayList(String.class.getClassLoader());
		celiangValue=in.readString();
		lastOper=in.readString();
		lastOpTime=in.readString();
		unit=in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

}
