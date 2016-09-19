package cn.dressbook.ui.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description: 用户需求
 * @author:袁东华
 * @time:2015-8-7下午3:39:34
 */
public class Requirement implements Parcelable {
	private String user_level;

	public String getUser_level() {
		return user_level;
	}

	public void setUser_level(String user_level) {
		this.user_level = user_level;
	}

	private String id;
	private String userId;
	private String userName;
	private String expDay;
	private String occasion;
	private String advSpec;
	private String userAvatar;
	private String reqDesc;
	private String addTime;
	private String priceMax;
	private String priceMin;
	private String categoryId;
	private String photos;
	private String state;
	private String categoryName;
	private String buyerResponsesNum;
	private String childId;
	private String sex;
	private String kfHeadPic;
	private String descAll;
	/**
	 * @description:照片
	 */
	private String[] pics;
	/**
	 * @description:头像
	 */
	private String[] buyerResponsesAvatar;
	private ArrayList<BuyerResponse> buyerResponseList;

	public ArrayList<BuyerResponse> getBuyerResponseList() {
		return buyerResponseList;
	}

	public void setBuyerResponseList(ArrayList<BuyerResponse> buyerResponseList) {
		this.buyerResponseList = buyerResponseList;
	}

	public String[] getBuyerResponsesAvatar() {
		return buyerResponsesAvatar;
	}

	public void setBuyerResponsesAvatar(String[] buyerResponsesAvatar) {
		this.buyerResponsesAvatar = buyerResponsesAvatar;
	}

	public String[] getPics() {
		return pics;
	}

	public void setPics(String[] pics) {
		this.pics = pics;
	}

	/**
	 * 买手推荐方案
	 */
	private ArrayList<MSTJData> attires;

	public Requirement() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getexpDay() {
		return expDay;
	}

	public void setexpDay(String expDay) {
		this.expDay = expDay;
	}

	public String getOccasion() {
		return occasion;
	}

	public void setOccasion(String occasion) {
		this.occasion = occasion;
	}

	public String getadvSpec() {
		return advSpec;
	}

	public void setadvSpec(String advSpec) {
		this.advSpec = advSpec;
	}

	public String getuserAvatar() {
		return userAvatar;
	}

	public void setuserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	public String getReqDesc() {
		return reqDesc;
	}

	public void setReqDesc(String reqDesc) {
		this.reqDesc = reqDesc;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getPriceMax() {
		return priceMax;
	}

	public void setPriceMax(String priceMax) {
		this.priceMax = priceMax;
	}

	public String getPriceMin() {
		return priceMin;
	}

	public void setPriceMin(String priceMin) {
		this.priceMin = priceMin;
	}

	public String getcategoryId() {
		return categoryId;
	}

	public void setcategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getcategoryName() {
		return categoryName;
	}

	public void setcategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getbuyerResponsesNum() {
		return buyerResponsesNum;
	}

	public void setbuyerResponsesNum(String buyerResponsesNum) {
		this.buyerResponsesNum = buyerResponsesNum;
	}

	public String getchildId() {
		return childId;
	}

	public void setchildId(String childId) {
		this.childId = childId;
	}

	public String getsex() {
		return sex;
	}

	public void setsex(String sex) {
		this.sex = sex;
	}

	public String getKfHeadPic() {
		return kfHeadPic;
	}

	public void setKfHeadPic(String kfHeadPic) {
		this.kfHeadPic = kfHeadPic;
	}

	public String getDescAll() {
		return descAll;
	}

	public void setDescAll(String descAll) {
		this.descAll = descAll;
	}

	public ArrayList<MSTJData> getAttires() {
		return attires;
	}

	public void setAttires(ArrayList<MSTJData> attires) {
		this.attires = attires;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {

		out.writeString(user_level);
		out.writeString(id);
		out.writeString(userId);
		out.writeString(userName);
		out.writeString(expDay);
		out.writeString(occasion);
		out.writeString(advSpec);
		out.writeString(userAvatar);
		out.writeString(reqDesc);
		out.writeString(addTime);
		out.writeString(priceMax);
		out.writeString(priceMin);
		out.writeString(categoryId);
		out.writeString(photos);
		out.writeString(state);
		out.writeString(categoryName);
		out.writeString(buyerResponsesNum);
		out.writeString(childId);
		out.writeString(sex);
		out.writeString(kfHeadPic);
		out.writeString(descAll);
		out.writeTypedList(attires);
		out.writeStringArray(pics);
		out.writeStringArray(buyerResponsesAvatar);
		out.writeTypedList(buyerResponseList);

	}

	public static final Creator<Requirement> CREATOR = new Creator<Requirement>() {
		@Override
		public Requirement createFromParcel(Parcel in) {
			return new Requirement(in);
		}

		@Override
		public Requirement[] newArray(int size) {
			return new Requirement[size];
		}
	};

	private Requirement(Parcel in) {
		user_level = in.readString();
		id = in.readString();
		userId = in.readString();
		userName = in.readString();
		expDay = in.readString();
		occasion = in.readString();
		advSpec = in.readString();
		userAvatar = in.readString();
		reqDesc = in.readString();
		addTime = in.readString();
		priceMax = in.readString();
		priceMin = in.readString();
		categoryId = in.readString();
		photos = in.readString();
		state = in.readString();
		categoryName = in.readString();
		buyerResponsesNum = in.readString();
		childId = in.readString();
		sex = in.readString();
		kfHeadPic = in.readString();
		descAll = in.readString();
		in.readTypedList(attires, MSTJData.CREATOR);
		in.readStringArray(pics);
		in.readStringArray(buyerResponsesAvatar);
		in.readTypedList(buyerResponseList, BuyerResponse.CREATOR);

	}

}
