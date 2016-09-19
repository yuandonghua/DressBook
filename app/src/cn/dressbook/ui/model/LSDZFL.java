package cn.dressbook.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description 量身定制分类
 * @author 袁东华
 * @date 2016-1-22
 */
public class LSDZFL implements Parcelable {

	/**
	 * id
	 */
	public String DzCls_id;
	/**
	 * 分类名称
	 */
	public String DzCls_name;
	/**
	 * 分类图片
	 */
	public String DzCls_pic;
	/**
	 * 分类性别
	 */
	public String DzCls_sex;
	
	
	public LSDZFL() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getDzCls_id() {
		return DzCls_id;
	}

	public void setDzCls_id(String DzCls_id) {
		this.DzCls_id = DzCls_id;
	}

	public String getDzCls_name() {
		return DzCls_name;
	}

	public void setDzCls_name(String DzCls_name) {
		this.DzCls_name = DzCls_name;
	}

	public String getDzCls_pic() {
		return DzCls_pic;
	}

	public void setDzCls_pic(String DzCls_pic) {
		this.DzCls_pic = DzCls_pic;
	}

	public String getDzCls_sex() {
		return DzCls_sex;
	}

	public void setDzCls_sex(String DzCls_sex) {
		this.DzCls_sex = DzCls_sex;
	}


	

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(DzCls_id);
		out.writeString(DzCls_name);
		out.writeString(DzCls_pic);
		out.writeString(DzCls_sex);

	}

	public static final Creator<LSDZFL> CREATOR = new Creator<LSDZFL>() {
		@Override
		public LSDZFL createFromParcel(Parcel in) {
			return new LSDZFL(in);
		}

		@Override
		public LSDZFL[] newArray(int size) {
			return new LSDZFL[size];
		}
	};

	private LSDZFL(Parcel in) {
		DzCls_id = in.readString();
		DzCls_name = in.readString();
		DzCls_pic = in.readString();
		DzCls_sex = in.readString();
	}

}
