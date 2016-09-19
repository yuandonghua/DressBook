package cn.dressbook.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description 阿里百川数据解析
 * @author 熊天富
 * @2016-1-11
 */

public class AlbcBean implements Parcelable {
	public AlbcBean() {
	}

	// 商品id
	private String auction_id;
	// 图片url
	private String pic_url;
	// 价格
	private String price;
	// 规则
	private String rule_id;
	// 卖家id
	private String seller_id;
	// 卖家昵称
	private String seller_nick;
	// 商铺类型
	private String shop_type;
	// 标题
	private String title;
	// 是否淘宝客
	private String tk_item;
	//是否选中
	private int isView;

	public String getAuction_id() {
		return auction_id;
	}

	public void setAuction_id(String auction_id) {
		this.auction_id = auction_id;
	}

	public String getPic_url() {
		return pic_url;
	}

	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getRule_id() {
		return rule_id;
	}

	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}

	public String getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}

	public String getSeller_nick() {
		return seller_nick;
	}

	public void setSeller_nick(String seller_nick) {
		this.seller_nick = seller_nick;
	}

	public String getShop_type() {
		return shop_type;
	}

	public void setShop_type(String shop_type) {
		this.shop_type = shop_type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTk_item() {
		return tk_item;
	}

	public void setTk_item(String tk_item) {
		this.tk_item = tk_item;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	

	public int getIsView() {
		return isView;
	}

	public void setIsView(int isView) {
		this.isView = isView;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		// TODO Auto-generated method stub
		out.writeString(auction_id);
		out.writeString(pic_url);
		out.writeString(price);
		out.writeString(rule_id);
		out.writeString(seller_id);
		out.writeString(seller_nick);
		out.writeString(shop_type);
		out.writeString(title);
		out.writeString(tk_item);
		out.writeInt(isView);

	}

	private AlbcBean(Parcel in) {
		auction_id = in.readString();
		pic_url = in.readString();
		price = in.readString();
		rule_id = in.readString();
		seller_id = in.readString();
		seller_nick = in.readString();
		shop_type = in.readString();
		title = in.readString();
		tk_item = in.readString();
		isView=in.readInt();
	}

}
