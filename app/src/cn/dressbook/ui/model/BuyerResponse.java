package cn.dressbook.ui.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description: 推荐人的信息及商品
 * @author:袁东华
 * @time:2015-10-16下午6:11:37
 */
public class BuyerResponse implements Parcelable {
	// 是否被喜欢
	private String isPraise;

	public String getIsPraise() {
		return isPraise;
	}

	public void setIsPraise(String isPraise) {
		this.isPraise = isPraise;
	}

	private String buyerRespAttiresIds;

	public String getBuyerRespAttiresIds() {
		return buyerRespAttiresIds;
	}

	public void setBuyerRespAttiresIds(String buyerRespAttiresIds) {
		this.buyerRespAttiresIds = buyerRespAttiresIds;
	}

	private String words;

	public String getWords() {
		return words;
	}

	public void setWords(String words) {
		this.words = words;
	}

	private String addTimeShow;
	private String id;
	private String buyerRespCommentsNum;
	private String buyerRespPraisesNum;
	private String buyerRespAttiresNum;
	private String user_id;
	private String user_avatar;
	private String user_name;
	private String user_level;

	public String getUser_level() {
		return user_level;
	}

	public void setUser_level(String user_level) {
		this.user_level = user_level;
	}

	private ArrayList<String> attireList;

	public ArrayList<String> getAttireList() {
		return attireList;
	}

	public void setAttireList(ArrayList<String> attireList) {
		this.attireList = attireList;
	}

	public BuyerResponse() {

	}

	public String getaddTimeShow() {
		return addTimeShow;
	}

	public void setaddTimeShow(String addTimeShow) {
		this.addTimeShow = addTimeShow;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getbuyerRespCommentsNum() {
		return buyerRespCommentsNum;
	}

	public void setbuyerRespCommentsNum(String buyerRespCommentsNum) {
		this.buyerRespCommentsNum = buyerRespCommentsNum;
	}

	public String getbuyerRespPraisesNum() {
		return buyerRespPraisesNum;
	}

	public void setbuyerRespPraisesNum(String buyerRespPraisesNum) {
		this.buyerRespPraisesNum = buyerRespPraisesNum;
	}

	public String getbuyerRespAttiresNum() {
		return buyerRespAttiresNum;
	}

	public void setbuyerRespAttiresNum(String buyerRespAttiresNum) {
		this.buyerRespAttiresNum = buyerRespAttiresNum;
	}

	public String getuser_id() {
		return user_id;
	}

	public void setuser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getuser_avatar() {
		return user_avatar;
	}

	public void setuser_avatar(String user_avatar) {
		this.user_avatar = user_avatar;
	}

	public String getuser_name() {
		return user_name;
	}

	public void setuser_name(String user_name) {
		this.user_name = user_name;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {

		out.writeString(isPraise);
		out.writeString(user_level);
		out.writeString(buyerRespAttiresIds);
		out.writeString(words);
		out.writeString(addTimeShow);
		out.writeString(id);
		out.writeString(buyerRespCommentsNum);
		out.writeString(buyerRespPraisesNum);
		out.writeString(buyerRespAttiresNum);
		out.writeString(user_id);
		out.writeString(user_avatar);
		out.writeString(user_name);
		out.writeStringList(attireList);

	}

	public static final Creator<BuyerResponse> CREATOR = new Creator<BuyerResponse>() {
		@Override
		public BuyerResponse createFromParcel(Parcel in) {
			return new BuyerResponse(in);
		}

		@Override
		public BuyerResponse[] newArray(int buyerRespAttiresNum) {
			return new BuyerResponse[buyerRespAttiresNum];
		}
	};

	private BuyerResponse(Parcel in) {
		isPraise = in.readString();
		user_level = in.readString();
		buyerRespAttiresIds = in.readString();
		words = in.readString();
		addTimeShow = in.readString();
		id = in.readString();
		buyerRespCommentsNum = in.readString();
		buyerRespPraisesNum = in.readString();
		buyerRespAttiresNum = in.readString();
		user_id = in.readString();
		user_avatar = in.readString();
		user_name = in.readString();
		in.readStringList(attireList);
	}

}
