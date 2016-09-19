package cn.dressbook.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description 定制商品详情图片
 * @author 袁东华
 * @date 2016-1-22
 */
public class Preval implements Parcelable {
	public static final int ISCHOOSE = 1;
	public static final int NOCHOOSE = 2;
	private String id;
	private String materialPrice;
	
	public String getMaterialPrice() {
		return materialPrice;
	}

	public void setMaterialPrice(String materialPrice) {
		this.materialPrice = materialPrice;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 图片id
	 */
	public String name;
	/**
	 * 路径
	 */
	public String pic;

	public int getIsChoose() {
		return isChoose;
	}

	public void setIsChoose(int isChoose) {
		this.isChoose = isChoose;
	}

	/**
	 * 是否选择 1：没有选择，2：选择
	 */
	public int isChoose = NOCHOOSE;

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

	public Preval() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(materialPrice);
		out.writeString(id);
		out.writeString(name);
		out.writeString(pic);
		out.writeInt(isChoose);
	}

	public static final Creator<Preval> CREATOR = new Creator<Preval>() {
		@Override
		public Preval createFromParcel(Parcel in) {
			return new Preval(in);
		}

		@Override
		public Preval[] newArray(int size) {
			return new Preval[size];
		}
	};

	private Preval(Parcel in) {
		materialPrice= in.readString();
		id = in.readString();
		name = in.readString();
		pic = in.readString();
		isChoose = in.readInt();

	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

}
