package cn.dressbook.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description: 买手推荐方案
 * @author:袁东华
 * @time:2015-8-7下午4:54:15
 */
public class MSTJData implements Parcelable {
	private String can_try;
	private String isChecked = "0";
	private String id;
	private String attireFrom;
	private String adviserId;
	private String size;
	private String color;
	private String num;
	private String said;
	private String atid;
	private String picUrl;
	private String tbklink;
	private String adesc;
	private String marketPrice;
	private String shopPrice;
	private String pubtime;
	private String state;
	private String yk;
	private String priceCur;
	private String priceNet;
	private String url;

	public MSTJData() {

	}

	public String getCan_try() {
		return can_try;
	}

	public void setCan_try(String can_try) {
		this.can_try = can_try;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPriceCur() {
		return priceCur;
	}

	public void setPriceCur(String priceCur) {
		this.priceCur = priceCur;
	}

	public String getPriceNet() {
		return priceNet;
	}

	public void setPriceNet(String priceNet) {
		this.priceNet = priceNet;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAttireFrom() {
		return attireFrom;
	}

	public void setAttireFrom(String attireFrom) {
		this.attireFrom = attireFrom;
	}

	public String getAdviserId() {
		return adviserId;
	}

	public void setAdviserId(String adviserId) {
		this.adviserId = adviserId;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}

	public String getYk() {
		return yk;
	}

	public void setYk(String yk) {
		this.yk = yk;
	}

	public String getSaid() {
		return said;
	}

	public void setSaid(String said) {
		this.said = said;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAtid() {
		return atid;
	}

	public void setAtid(String atid) {
		this.atid = atid;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getTbklink() {
		return tbklink;
	}

	public void setTbklink(String tbklink) {
		this.tbklink = tbklink;
	}

	public String getAdesc() {
		return adesc;
	}

	public void setAdesc(String adesc) {
		this.adesc = adesc;
	}

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getShopPrice() {
		return shopPrice;
	}

	public void setShopPrice(String shopPrice) {
		this.shopPrice = shopPrice;
	}

	public String getPubtime() {
		return pubtime;
	}

	public void setPubtime(String pubtime) {
		this.pubtime = pubtime;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(can_try);
		out.writeString(isChecked);
		out.writeString(id);
		out.writeString(attireFrom);
		out.writeString(adviserId);
		out.writeString(size);
		out.writeString(color);
		out.writeString(num);
		out.writeString(said);
		out.writeString(atid);
		out.writeString(picUrl);
		out.writeString(tbklink);
		out.writeString(adesc);
		out.writeString(shopPrice);
		out.writeString(pubtime);
		out.writeString(state);
		out.writeString(yk);
		out.writeString(priceCur);
		out.writeString(priceNet);
		out.writeString(url);

	}

	public static final Creator<MSTJData> CREATOR = new Creator<MSTJData>() {
		@Override
		public MSTJData createFromParcel(Parcel in) {
			return new MSTJData(in);
		}

		@Override
		public MSTJData[] newArray(int size) {
			return new MSTJData[size];
		}
	};

	private MSTJData(Parcel in) {
		can_try= in.readString();
		isChecked = in.readString();
		id = in.readString();
		attireFrom = in.readString();
		adviserId = in.readString();
		size = in.readString();
		color = in.readString();
		num = in.readString();
		said = in.readString();
		atid = in.readString();
		picUrl = in.readString();
		tbklink = in.readString();
		adesc = in.readString();
		shopPrice = in.readString();
		pubtime = in.readString();
		state = in.readString();
		yk = in.readString();
		priceCur = in.readString();
		priceNet = in.readString();
		url = in.readString();
	}

}
