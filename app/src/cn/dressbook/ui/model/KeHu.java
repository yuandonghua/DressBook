package cn.dressbook.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description 客户
 * @author 袁东华
 * @date 2016-2-19
 */
public class KeHu implements Parcelable {

	/**
	 * 客户的用户ID
	 */
	public String user_id;
	/**
	 * 客户昵称
	 */
	public String userName;
	/**
	 * 客户头像
	 */
	public String avatar;
	/**
	 * 会员职级----1普通会员，2顾问，3股东，4 董事
	 */
	public String mbLevel;
	/**
	 * 本季度贡献额
	 */
	public String quarterVary;
	/**
	 * 累计贡献额
	 */
	public String totalVary;


	public KeHu() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getMbLevel() {
		return mbLevel;
	}

	public void setMbLevel(String mbLevel) {
		this.mbLevel = mbLevel;
	}

	public String getQuarterVary() {
		return quarterVary;
	}

	public void setQuarterVary(String quarterVary) {
		this.quarterVary = quarterVary;
	}

	public String getTotalVary() {
		return totalVary;
	}

	public void setTotalVary(String totalVary) {
		this.totalVary = totalVary;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(user_id);
		out.writeString(userName);
		out.writeString(avatar);
		out.writeString(mbLevel);
		out.writeString(quarterVary);
		out.writeString(totalVary);
		
	}

	public static final Creator<KeHu> CREATOR = new Creator<KeHu>() {
		@Override
		public KeHu createFromParcel(Parcel in) {
			return new KeHu(in);
		}

		@Override
		public KeHu[] newArray(int size) {
			return new KeHu[size];
		}
	};

	private KeHu(Parcel in) {
		user_id = in.readString();
		userName = in.readString();
		avatar = in.readString();
		mbLevel = in.readString();
		quarterVary = in.readString();
		totalVary= in.readString();
	}

}
