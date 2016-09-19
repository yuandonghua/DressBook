package cn.dressbook.ui.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description 形象的方案
 * @author 袁东华
 * @date 2014-8-5 下午12:15:20
 */
public class AttireScheme implements Parcelable {
	private String tk_item;

	public String getTk_item() {
		return tk_item;
	}

	public void setTk_item(String tk_item) {
		this.tk_item = tk_item;
	}

	private String shop_type;

	public String getShop_type() {
		return shop_type;
	}

	public void setShop_type(String shop_type) {
		this.shop_type = shop_type;
	}

	private String seller_nick;

	public String getSeller_nick() {
		return seller_nick;
	}

	public void setSeller_nick(String seller_nick) {
		this.seller_nick = seller_nick;
	}

	private String seller_id;

	public String getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}

	private String rule_id;

	public String getRule_id() {
		return rule_id;
	}

	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}

	/**
	 * 阿里百川数据 起始
	 */
	private String auction_id;
	private String id;
	private String pic_url;
	private String price;
	private String title;

	public String getAuction_id() {
		return auction_id;
	}

	public void setAuction_id(String auction_id) {
		this.auction_id = auction_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	// 阿里百川数据结束

	/**
	 * 淘宝商品id
	 */
	private String openiid;

	public String getOpeniid() {
		return openiid;
	}

	public void setOpeniid(String openiid) {
		this.openiid = openiid;
	}

	/**
	 * 商品类型(普通商品/广告商品)
	 */
	private String for_vip;

	public String getFor_vip() {
		return for_vip;
	}

	public void setFor_vip(String for_vip) {
		this.for_vip = for_vip;
	}

	/**
	 * 获取类型
	 */
	private String rootCatName;

	public String getRootCatName() {
		return rootCatName;
	}

	public void setRootCatName(String rootCatName) {
		this.rootCatName = rootCatName;
	}

	/**
	 * 广告图片
	 */
	private String ad_pic;

	public String getAd_pic() {
		return ad_pic;
	}

	public void setAd_pic(String ad_pic) {
		this.ad_pic = ad_pic;
	}

	private String referrer;

	public String getReferrer() {
		return referrer;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	/**
	 * 描述
	 */
	private String attireDesc;

	public String getAttireDesc() {
		return attireDesc;
	}

	public void setAttireDesc(String attireDesc) {
		this.attireDesc = attireDesc;
	}

	/**
	 * 库存
	 */
	private String stock_num;

	public String getStock_num() {
		return stock_num;
	}

	public void setStock_num(String stock_num) {
		this.stock_num = stock_num;
	}

	/**
	 * 尺寸大小
	 */
	private String size_info;

	public String getSize_info() {
		return size_info;
	}

	public void setSize_info(String size_info) {
		this.size_info = size_info;
	}

	/**
	 * 颜色
	 */
	private String color_info;

	public String getColor_info() {
		return color_info;
	}

	public void setColor_info(String color_info) {
		this.color_info = color_info;
	}

	/**
	 * 获取标签
	 */
	private ArrayList<String> key_map;

	public ArrayList<String> getKey_map() {
		return key_map;
	}

	public void setKey_map(ArrayList<String> key_map) {
		this.key_map = key_map;
	}

	private String yq_value;
	private String can_try;
	private String MXId;
	private String attireId;// 方案id
	private String attire_id_real;
	private int wardrobeId;// 对应衣柜id号
	private ArrayList<String> pictureList;// 图片目录路径目录名Attire_id 第一副图做为封面
	private int picWidth;
	private int picHeight;
	private int pictureCount;
	private String adviserId;// 顾问师
	private int mAttireSchemeType;
	private String adviserName;
	private String autographPath;
	private String desc;// 搭配描述
	private String keyWord;// 关键词 多个关键词 用“,”作为分割符
	private String mobile;
	private String messageMobile;
	private String thume;
	private String autographLogo;
	// 方案发布时间
	private String attireTime;
	// 方案的风格
	private String attire_style;
	// 方案的场合
	private String attire_occasion;
	// 方案的销售价
	private int shop_price;
	private int mid;
	private String modFrom;
	private String tbkLink;
	private int status;
	private int isView;
	// 发布时间
	private String pubtime;
	// 服务端模特图片地址
	private String modPic;
	// 客户端模特图片地址
	private String localModPic;
	// 客户端大图片的地址
	private String mBigImagePath;
	// 衣柜名称
	private String mWardrobeName;
	// 市场价
	private String mMarketPrice;
	// 服装类型
	private String mTypeString;
	// 是否是标准衣柜
	private int isBiaoZhun;
	// 相似款-上衣
	private String mSY_KEY;
	// 相似款-上衣
	private String mSY_VALUE;

	// 相似款-裤子
	private String mKZ_KEY;
	// 相似款-裤子
	private String mKZ_VALUE;
	// 相似款-鞋子
	private String mXZ_KEY;
	// 相似款-鞋子
	private String mXZ_VALUE;
	// 视频链接
	private String video_url;
	private String video_id;
	private String demo;
	private String modpic_jjh;

	public String getYq_value() {
		return yq_value;
	}

	public void setYq_value(String yq_value) {
		this.yq_value = yq_value;
	}

	public String getCan_try() {
		return can_try;
	}

	public void setCan_try(String can_try) {
		this.can_try = can_try;
	}

	public String getAttire_id_real() {
		return attire_id_real;
	}

	public void setAttire_id_real(String attire_id_real) {
		this.attire_id_real = attire_id_real;
	}

	public String getModpic_jjh() {
		return modpic_jjh;
	}

	public void setModpic_jjh(String modpic_jjh) {
		this.modpic_jjh = modpic_jjh;
	}

	public String getDemo() {
		return demo;
	}

	public void setDemo(String demo) {
		this.demo = demo;
	}

	public String getVideo_id() {
		return video_id;
	}

	public void setVideo_id(String video_id) {
		this.video_id = video_id;
	}

	public String getVideo_url() {
		return video_url;
	}

	public void setVideo_url(String video_url) {
		this.video_url = video_url;
	}

	public String getSY_VALUE() {
		return mSY_VALUE;
	}

	public void setSY_VALUE(String mSY_VALUE) {
		this.mSY_VALUE = mSY_VALUE;
	}

	public String getKZ_VALUE() {
		return mKZ_VALUE;
	}

	public void setKZ_VALUE(String mKZ_VALUE) {
		this.mKZ_VALUE = mKZ_VALUE;
	}

	public String getXZ_VALUE() {
		return mXZ_VALUE;
	}

	public void setXZ_VALUE(String mXZ_VALUE) {
		this.mXZ_VALUE = mXZ_VALUE;
	}

	public String getSY_KEY() {
		return mSY_KEY;
	}

	public void setSY_KEY(String mSY_KEY) {
		this.mSY_KEY = mSY_KEY;
	}

	public String getKZ_KEY() {
		return mKZ_KEY;
	}

	public void setKZ_KEY(String mKZ_KEY) {
		this.mKZ_KEY = mKZ_KEY;
	}

	public String getXZ_KEY() {
		return mXZ_KEY;
	}

	public void setXZ_KEY(String mXZ_KEY) {
		this.mXZ_KEY = mXZ_KEY;
	}

	public String getMXId() {
		return MXId;
	}

	public void setMXId(String mXId) {
		MXId = mXId;
	}

	/**
	 * @return the isBiaoZhun
	 */
	public int getIsBiaoZhun() {
		return isBiaoZhun;
	}

	/**
	 * @param isBiaoZhun
	 *            the isBiaoZhun to set
	 */
	public void setIsBiaoZhun(int isBiaoZhun) {
		this.isBiaoZhun = isBiaoZhun;
	}

	/**
	 * @return the mTypeString
	 */
	public String getTypeString() {
		return mTypeString;
	}

	/**
	 * @param mTypeString
	 *            the mTypeString to set
	 */
	public void setTypeString(String mTypeString) {
		this.mTypeString = mTypeString;
	}

	/**
	 * @return the mMarketPrice
	 */
	public String getMarketPrice() {
		return mMarketPrice;
	}

	/**
	 * @param mMarketPrice
	 *            the mMarketPrice to set
	 */
	public void setMarketPrice(String mMarketPrice) {
		this.mMarketPrice = mMarketPrice;
	}

	/**
	 * @return the mWardrobeName
	 */
	public String getWardrobeName() {
		return mWardrobeName;
	}

	/**
	 * @param mWardrobeName
	 *            the mWardrobeName to set
	 */
	public void setWardrobeName(String mWardrobeName) {
		this.mWardrobeName = mWardrobeName;
	}

	/**
	 * @return the bigImagePath
	 */
	public String getBigImagePath() {
		return mBigImagePath;
	}

	/**
	 * @param bigImagePath
	 *            the bigImagePath to set
	 */
	public void setBigImagePath(String bigImagePath) {
		this.mBigImagePath = bigImagePath;
	}

	/**
	 * @return the localModPic
	 */
	public String getLocalModPic() {
		return localModPic;
	}

	/**
	 * @param localModPic
	 *            the localModPic to set
	 */
	public void setLocalModPic(String localModPic) {
		this.localModPic = localModPic;
	}

	/**
	 * @return the modPic
	 */
	public String getModPic() {
		return modPic;
	}

	/**
	 * @param modPic
	 *            the modPic to set
	 */
	public void setModPic(String modPic) {
		this.modPic = modPic;
	}

	public String getPubtime() {
		return pubtime;
	}

	public void setPubtime(String pubtime) {
		this.pubtime = pubtime;
	}

	public int getIsView() {
		return isView;
	}

	public void setIsView(int isView) {
		this.isView = isView;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getModFrom() {
		return modFrom;
	}

	public void setModFrom(String modFrom) {
		this.modFrom = modFrom;
	}

	public String getTbkLink() {
		return tbkLink;
	}

	public void setTbkLink(String tbkLink) {
		this.tbkLink = tbkLink;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public String getAttire_style() {
		return attire_style;
	}

	public void setAttire_style(String attire_style) {
		this.attire_style = attire_style;
	}

	public String getAttire_occasion() {
		return attire_occasion;
	}

	public void setAttire_occasion(String attire_occasion) {
		this.attire_occasion = attire_occasion;
	}

	public int getShop_price() {
		return shop_price;
	}

	public void setShop_price(int shop_price) {
		this.shop_price = shop_price;
	}

	public String getAttireTime() {
		return attireTime;
	}

	public void setAttireTime(String attireTime) {
		this.attireTime = attireTime;
	}

	public int getAttireSchemeType() {
		return mAttireSchemeType;
	}

	public void setAttireSchemeType(int attireSchemeType) {
		mAttireSchemeType = attireSchemeType;
	}

	public String getAutographLogo() {
		return autographLogo;
	}

	public void setAutographLogo(String autographLogo) {
		this.autographLogo = autographLogo;
	}

	public String getThume() {
		return thume;
	}

	public void setThume(String thume) {
		this.thume = thume;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMessageMobile() {
		return messageMobile;
	}

	public void setMessageMobile(String messageMobile) {
		this.messageMobile = messageMobile;
	}

	public String getAdviserName() {
		return adviserName;
	}

	public void setAdviserName(String adviserName) {
		this.adviserName = adviserName;
	}

	public String getAdviserId() {
		return adviserId;
	}

	public void setAdviserId(String adviserId) {
		this.adviserId = adviserId;
	}

	public String getAutographPath() {
		return autographPath;
	}

	public void setAutographPath(String autographPath) {
		this.autographPath = autographPath;
	}

	public String getAttireId() {
		return attireId;
	}

	public void setAttireId(String attireId) {
		this.attireId = attireId;
	}

	public int getWardrobeId() {
		return wardrobeId;
	}

	public void setWardrobeId(int wardrobeId) {
		this.wardrobeId = wardrobeId;
	}

	public ArrayList<String> getPicture() {
		return pictureList;
	}

	public void setPicture(ArrayList<String> picture) {
		this.pictureList = picture;
	}

	public int getPicWidth() {
		return picWidth;
	}

	public void setPicWidth(int picWidth) {
		this.picWidth = picWidth;
	}

	public int getPicHeight() {
		return picHeight;
	}

	public void setPicHeight(int picHeight) {
		this.picHeight = picHeight;
	}

	public int getPictureCount() {
		return pictureCount;
	}

	public void setPictureCount(int pictureCount) {
		this.pictureCount = pictureCount;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public AttireScheme() {

	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(openiid);
		out.writeString(tk_item);
		out.writeString(shop_type);
		out.writeString(seller_nick);
		out.writeString(seller_id);
		out.writeString(rule_id);
		// 阿里百川数据
		out.writeString(auction_id);
		out.writeString(id);
		out.writeString(pic_url);
		out.writeString(price);
		out.writeString(title);

		out.writeString(for_vip);
		out.writeString(ad_pic);
		out.writeString(referrer);
		out.writeInt(isView);
		out.writeString(attireDesc);
		out.writeString(stock_num);
		out.writeString(size_info);
		out.writeString(color_info);
		out.writeString(yq_value);
		out.writeString(desc);
		out.writeString(can_try);
		out.writeString(attire_id_real);
		out.writeString(modpic_jjh);
		out.writeString(demo);
		out.writeString(video_id);
		out.writeString(video_url);
		out.writeString(mSY_KEY);
		out.writeString(mSY_VALUE);
		out.writeString(mKZ_KEY);
		out.writeString(mKZ_VALUE);
		out.writeString(mXZ_KEY);
		out.writeString(mXZ_VALUE);
		out.writeString(MXId);
		out.writeInt(isBiaoZhun);
		out.writeString(mWardrobeName);
		out.writeString(localModPic);
		out.writeString(mBigImagePath);
		out.writeString(modPic);
		out.writeString(pubtime);
		out.writeInt(status);
		out.writeString(modFrom);
		out.writeString(tbkLink);
		out.writeInt(mid);
		out.writeString(attireId);
		out.writeInt(wardrobeId);
		int pictureCount = 0;
		if (pictureList != null && pictureList.size() > 0) {
			pictureCount = pictureList.size();
			out.writeInt(pictureCount);
			for (int i = 0; i < pictureCount; i++) {
				out.writeString(pictureList.get(i));
			}
		} else {
			out.writeInt(pictureCount);
		}
		out.writeInt(picWidth);
		out.writeInt(picHeight);
		out.writeInt(pictureCount);
		out.writeString(adviserId);
		out.writeString(adviserName);
		out.writeString(autographPath);
		out.writeString(keyWord);
		out.writeString(mobile);
		out.writeString(messageMobile);
		out.writeString(thume);
		out.writeString(autographLogo);
		out.writeString(attireTime);
		out.writeString(attire_style);
		out.writeString(attire_occasion);
		out.writeInt(shop_price);
		out.writeString(mMarketPrice);
		out.writeString(mTypeString);
	}

	public static final Creator<AttireScheme> CREATOR = new Creator<AttireScheme>() {
		@Override
		public AttireScheme createFromParcel(Parcel in) {
			return new AttireScheme(in);
		}

		@Override
		public AttireScheme[] newArray(int size) {
			return new AttireScheme[size];
		}
	};

	private AttireScheme(Parcel in) {
		openiid = in.readString();
		tk_item = in.readString();
		shop_type = in.readString();
		seller_nick = in.readString();
		seller_id = in.readString();
		rule_id = in.readString();
		auction_id = in.readString();
		id = in.readString();
		pic_url = in.readString();
		price = in.readString();
		title = in.readString();

		for_vip = in.readString();
		ad_pic = in.readString();
		referrer = in.readString();
		isView = in.readInt();
		attireDesc = in.readString();
		stock_num = in.readString();
		size_info = in.readString();
		color_info = in.readString();
		yq_value = in.readString();
		desc = in.readString();
		can_try = in.readString();
		attire_id_real = in.readString();
		modpic_jjh = in.readString();
		demo = in.readString();
		video_id = in.readString();
		video_url = in.readString();
		mSY_KEY = in.readString();
		mSY_VALUE = in.readString();
		mKZ_KEY = in.readString();
		mKZ_VALUE = in.readString();
		mXZ_KEY = in.readString();
		mXZ_VALUE = in.readString();
		MXId = in.readString();
		isBiaoZhun = in.readInt();
		mWardrobeName = in.readString();
		localModPic = in.readString();
		mBigImagePath = in.readString();
		modPic = in.readString();
		pubtime = in.readString();
		status = in.readInt();
		modFrom = in.readString();
		tbkLink = in.readString();
		mid = in.readInt();
		attireId = in.readString();
		wardrobeId = in.readInt();

		int picCount = in.readInt();
		if (picCount > 0) {
			pictureList = new ArrayList<String>();
			String picUrl = null;
			for (int i = 0; i < picCount; i++) {
				picUrl = in.readString();
				pictureList.add(picUrl);
			}
		} else {
			pictureList = null;
		}

		picWidth = in.readInt();
		picHeight = in.readInt();
		pictureCount = in.readInt();
		adviserId = in.readString();
		adviserName = in.readString();
		autographPath = in.readString();
		keyWord = in.readString();
		mobile = in.readString();
		messageMobile = in.readString();
		thume = in.readString();
		autographLogo = in.readString();
		attireTime = in.readString();
		attire_style = in.readString();
		attire_occasion = in.readString();
		shop_price = in.readInt();
		mMarketPrice = in.readString();
		mTypeString = in.readString();

	}

}
