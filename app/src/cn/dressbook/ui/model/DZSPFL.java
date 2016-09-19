package cn.dressbook.ui.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description 定制商品分类
 * @author 袁东华
 * @date 2016-1-22
 */
public class DZSPFL implements Parcelable {
	private String DzCls_id;

	public String getDzCls_id() {
		return DzCls_id;
	}

	public void setDzCls_id(String DzCls_id) {
		this.DzCls_id = DzCls_id;
	}

	private String DzCls_name;
	private String DzCls_pic;
	private String DzCls_sex;
	
	private ArrayList<DZSP> DzAttire;

	public ArrayList<DZSP> getDzAttire() {
		return DzAttire;
	}

	public void setDzAttire(ArrayList<DZSP> DzAttire) {
		this.DzAttire = DzAttire;
	}

	

	public DZSPFL() {

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
		out.writeTypedList(DzAttire);

	}

	public static final Creator<DZSPFL> CREATOR = new Creator<DZSPFL>() {
		@Override
		public DZSPFL createFromParcel(Parcel in) {
			return new DZSPFL(in);
		}

		@Override
		public DZSPFL[] newArray(int size) {
			return new DZSPFL[size];
		}
	};

	private DZSPFL(Parcel in) {
		DzCls_id = in.readString();
		DzCls_name = in.readString();
		DzCls_pic = in.readString();
		DzCls_sex = in.readString();
		in.readTypedList(DzAttire, DZSP.CREATOR);

	}

}
