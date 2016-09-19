package cn.dressbook.ui.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description 定制商品详情信息
 * @author 袁东华
 * @date 2016-1-22
 */
public class DZSPDetails implements Parcelable {

	/**
	 * 方案id
	 */
	public String id;
	/**
	 * 定制商品标题
	 */
	public String title;
	/**
	 * vip价格
	 */
	public String priceVip;
	/**
	 * 普通价格
	 */
	public String price;
	/**
	 * 状态
	 */
	public String state;
	/**
	 * 额外信息
	 */
	public String extinf;
	/**
	 * 商品标题图片
	 */
	public String titlePic;
	/**
	 * 商品详情参数集合
	 */
	public ArrayList<DZSPDetailsParams> params;
	/**
	 * 商品详情图片集合
	 */
	public ArrayList<DZSPDetailsImgs> images;
	
	
	public String templateId;
	/**
	 * 面料
	 */
	private Mianliao mianliao;
	private ArrayList<Mianliao> mianliaoList;

	public Mianliao getMianliao() {
		return mianliao;
	}

	public void setMianliao(Mianliao mianliao) {
		this.mianliao = mianliao;
	}

	public ArrayList<Mianliao> getMianliaoList() {
		return mianliaoList;
	}

	public void setMianliaoList(ArrayList<Mianliao> mianliaoList) {
		this.mianliaoList = mianliaoList;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public DZSPDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ArrayList<DZSPDetailsParams> getParams() {
		return params;
	}

	public void setParams(ArrayList<DZSPDetailsParams> params) {
		this.params = params;
	}

	public ArrayList<DZSPDetailsImgs> getImages() {
		return images;
	}

	public void setImages(ArrayList<DZSPDetailsImgs> images) {
		this.images = images;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPriceVip() {
		return priceVip;
	}

	public void setPriceVip(String priceVip) {
		this.priceVip = priceVip;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getExtinf() {
		return extinf;
	}

	public void setExtinf(String extinf) {
		this.extinf = extinf;
	}

	public String getTitlePic() {
		return titlePic;
	}

	public void setTitlePic(String titlePic) {
		this.titlePic = titlePic;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(id);
		out.writeString(title);
		out.writeString(priceVip);
		out.writeString(price);
		out.writeString(state);
		out.writeString(extinf);
		out.writeString(titlePic);
		out.writeTypedList(params);
		out.writeTypedList(images);
		out.writeString(templateId);
		out.writeParcelable(mianliao, flags);
		out.writeTypedList(mianliaoList);
	}

	public static final Creator<DZSPDetails> CREATOR = new Creator<DZSPDetails>() {
		@Override
		public DZSPDetails createFromParcel(Parcel in) {
			return new DZSPDetails(in);
		}

		@Override
		public DZSPDetails[] newArray(int size) {
			return new DZSPDetails[size];
		}
	};

	private DZSPDetails(Parcel in) {
		id = in.readString();
		title = in.readString();
		priceVip = in.readString();
		price = in.readString();
		state = in.readString();
		extinf = in.readString();
		titlePic = in.readString();
		in.readTypedList(params, DZSPDetailsParams.CREATOR);
		in.readTypedList(images, DZSPDetailsImgs.CREATOR);
		templateId=in.readString();
		mianliao=in.readParcelable(Mianliao.class.getClassLoader());
		in.readTypedList(mianliaoList, Mianliao.CREATOR);
		
	}

	

}
