package cn.dressbook.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description 定制商品详情图片
 * @author 袁东华
 * @date 2016-1-22
 */
public class KHXX implements Parcelable {
	/**
	 * 图片id
	 */
	public String name;
	/**
	 * 路径
	 */
	public String pic;
	private String phone;

	private String user_id;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
	

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public KHXX() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(name);
		out.writeString(pic);
		out.writeString(phone);
		out.writeString(user_id);
	}

	public static final Creator<KHXX> CREATOR = new Creator<KHXX>() {
		@Override
		public KHXX createFromParcel(Parcel in) {
			return new KHXX(in);
		}

		@Override
		public KHXX[] newArray(int size) {
			return new KHXX[size];
		}
	};

	private KHXX(Parcel in) {
		name = in.readString();
		pic = in.readString();
		phone=in.readString();
		user_id=in.readString();

	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

}
