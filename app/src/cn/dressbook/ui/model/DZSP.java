package cn.dressbook.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description 定制商品
 * @author 袁东华
 * @date 2016-1-22
 */
public class DZSP implements Parcelable {

	/**
	 * 方案id
	 */
	public String DzAttire_id;
	/**
	 * 定制商品标题
	 */
	public String DzAttire_title;
	/**
	 * 普通价格
	 */
	public String DzAttire_price;
	/**
	 * vip价格
	 */
	public String DzAttire_priceVip;
	/**
	 * 商品标题图片
	 */
	public String DzAttire_titlePic;
	
	
	public DZSP() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getDzAttire_id() {
		return DzAttire_id;
	}

	public void setDzAttire_id(String DzAttire_id) {
		this.DzAttire_id = DzAttire_id;
	}

	public String getDzAttire_title() {
		return DzAttire_title;
	}

	public void setDzAttire_title(String DzAttire_title) {
		this.DzAttire_title = DzAttire_title;
	}

	public String getDzAttire_price() {
		return DzAttire_price;
	}

	public void setDzAttire_price(String DzAttire_price) {
		this.DzAttire_price = DzAttire_price;
	}

	public String getDzAttire_priceVip() {
		return DzAttire_priceVip;
	}

	public void setDzAttire_priceVip(String DzAttire_priceVip) {
		this.DzAttire_priceVip = DzAttire_priceVip;
	}

	public String getDzAttire_titlePic() {
		return DzAttire_titlePic;
	}

	public void setDzAttire_titlePic(String DzAttire_titlePic) {
		this.DzAttire_titlePic = DzAttire_titlePic;
	}

	

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(DzAttire_id);
		out.writeString(DzAttire_title);
		out.writeString(DzAttire_price);
		out.writeString(DzAttire_priceVip);
		out.writeString(DzAttire_titlePic);

	}

	public static final Creator<DZSP> CREATOR = new Creator<DZSP>() {
		@Override
		public DZSP createFromParcel(Parcel in) {
			return new DZSP(in);
		}

		@Override
		public DZSP[] newArray(int size) {
			return new DZSP[size];
		}
	};

	private DZSP(Parcel in) {
		DzAttire_id = in.readString();
		DzAttire_title = in.readString();
		DzAttire_price = in.readString();
		DzAttire_priceVip = in.readString();
		DzAttire_titlePic = in.readString();
	}

}
