package cn.dressbook.ui.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description 定制商品详情参数
 * @author 袁东华
 * @date 2016-1-22
 */
public class DZSPDetailsParams implements Parcelable {

	/**
	 * 参数位置
	 */
	public String id;
	/**
	 * 参数id
	 */
	public String paramId;
	/**
	 * 参数名
	 */
	public String paramName;
	/**
	 * 参数值
	 */
	public String paramValue;
	/**
	 * 
	 */
	public String custom;
	/**
	 * 参数图片
	 */
	public String paramPic;
	/**
	 * diy参数
	 */
	public ArrayList<TiaoZhenCanShu> prevalList;
	
	
	public DZSPDetailsParams() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ArrayList<TiaoZhenCanShu> getPrevalList() {
		return prevalList;
	}

	public void setPrevalList(ArrayList<TiaoZhenCanShu> prevalList) {
		this.prevalList = prevalList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getparamId() {
		return paramId;
	}

	public void setparamId(String paramId) {
		this.paramId = paramId;
	}

	public String getparamName() {
		return paramName;
	}

	public void setparamName(String paramName) {
		this.paramName = paramName;
	}

	public String getparamValue() {
		return paramValue;
	}

	public void setparamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getcustom() {
		return custom;
	}

	public void setcustom(String custom) {
		this.custom = custom;
	}
	public String getParamPic() {
		return paramPic;
	}

	public void setParamPic(String paramPic) {
		this.paramPic = paramPic;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(id);
		out.writeString(paramId);
		out.writeString(paramName);
		out.writeString(paramValue);
		out.writeString(custom);
		out.writeString(paramPic);
		out.writeTypedList(prevalList);

	}
	private DZSPDetailsParams(Parcel in) {
		id = in.readString();
		paramId = in.readString();
		paramName = in.readString();
		paramValue = in.readString();
		custom = in.readString();
		paramPic=in.readString();
		in.readTypedList(prevalList, TiaoZhenCanShu.CREATOR);
	}

	public static final Creator<DZSPDetailsParams> CREATOR = new Creator<DZSPDetailsParams>() {
		@Override
		public DZSPDetailsParams createFromParcel(Parcel in) {
			return new DZSPDetailsParams(in);
		}

		@Override
		public DZSPDetailsParams[] newArray(int size) {
			return new DZSPDetailsParams[size];
		}
	};

	

}
