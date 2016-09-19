package cn.dressbook.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 用户
 * 
 * @author 袁东华
 * 
 */
public class User implements Parcelable {
	// 用户的默认形象id
	private int xingxiang_id;
	// 用户的默认形象name
	private String xingxiang_name;

	// 用户ID
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String phone;//手机号
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	private String addTimeShow;//注册时间
	
	public String getAddTimeShow() {
		return addTimeShow;
	}

	public void setAddTimeShow(String addTimeShow) {
		this.addTimeShow = addTimeShow;
	}

	// 用户昵称
	private String mName;
	// 用户密码
	private String mUserPassword;
	// 用户邮箱
	private String mUserEmail;
	// 用户头像
	private String mAvatar;
	// 用户SNS社区昵称
	private String mUserSNSName;
	// 用户SNS社区唯一ID
	private long mUserSNSId;
	// 喜欢数
	private int mUserFavouriteNum;
	// 关注数
	private int mUserFollowerNum;
	// 积分
	private int mJiFen;

	public User() {

	}

	public int getmJiFen() {
		return mJiFen;
	}

	public void setmJiFen(int mJiFen) {
		this.mJiFen = mJiFen;
	}

	public String getXingxiang_name() {
		return xingxiang_name;
	}

	public void setXingxiang_name(String xingxiang_name) {
		this.xingxiang_name = xingxiang_name;
	}

	public int getXingxiang_id() {
		return xingxiang_id;
	}

	public void setXingxiang_id(int xingxiang_id) {
		this.xingxiang_id = xingxiang_id;
	}


	public String getName() {
		return mName;
	}

	public void setName(String Name) {
		this.mName = Name;
	}

	public String getUserPassword() {
		return mUserPassword;
	}

	public void setUserPassword(String userPassword) {
		this.mUserPassword = userPassword;
	}

	public String getUserEmail() {
		return mUserEmail;
	}

	public void setUserEmail(String userEmail) {
		this.mUserEmail = userEmail;
	}

	public String getAvatar() {
		return mAvatar;
	}

	public void setAvatar(String Avatar) {
		this.mAvatar = Avatar;
	}

	public String getUserSNSName() {
		return mUserSNSName;
	}

	public void setUserSNSName(String userSNSName) {
		this.mUserSNSName = userSNSName;
	}

	public long getUserSNSId() {
		return mUserSNSId;
	}

	public void setUserSNSId(long userSNSId) {
		this.mUserSNSId = userSNSId;
	}

	public int getUserFavouriteNum() {
		return mUserFavouriteNum;
	}

	public void setUserFavouriteNum(int userFavouriteNum) {
		this.mUserFavouriteNum = userFavouriteNum;
	}

	public int getUserFollowerNum() {
		return mUserFollowerNum;
	}

	public void setUserFollowerNum(int userFollowerNum) {
		this.mUserFollowerNum = userFollowerNum;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(mJiFen);
		out.writeString(id);
		out.writeString(phone);
		out.writeString(addTimeShow);
		out.writeString(mName);
		out.writeString(mUserPassword);
		out.writeString(mUserEmail);
		out.writeString(mUserSNSName);
		out.writeString(mAvatar);
		out.writeLong(mUserSNSId);
		out.writeInt(mUserFavouriteNum);
		out.writeInt(mUserFollowerNum);
	}

	public static final Creator<User> CREATOR = new Creator<User>() {
		@Override
		public User createFromParcel(Parcel in) {
			return new User(in);
		}

		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};

	private User(Parcel in) {
		mJiFen = in.readInt();
		id = in.readString();
		phone = in.readString();
		addTimeShow = in.readString();
		mName = in.readString();
		mUserPassword = in.readString();
		mUserEmail = in.readString();
		mUserSNSName = in.readString();
		mAvatar = in.readString();
		mUserSNSId = in.readLong();
		mUserFavouriteNum = in.readInt();
		mUserFollowerNum = in.readInt();
	}

}
