package cn.dressbook.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description 面料
 * @author 熊天富
 * @date 2016-01-29 18:41:06
 */
public class Mianliao implements Parcelable {

	/**
	 * 图片id
	 */
	private String id;
	/**
	 * 路径
	 */
	private String name;
	/**
	 * 是否选择
	 */
	private boolean isChoose=false;
	private String paramId;
	private String pic;
	private String material;
	private String materialPrice;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isChoose() {
		return isChoose;
	}

	public void setChoose(boolean isChoose) {
		this.isChoose = isChoose;
	}

	public String getParamId() {
		return paramId;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getMaterialPrice() {
		return materialPrice;
	}

	public void setMaterialPrice(String materialPrice) {
		this.materialPrice = materialPrice;
	}

	public Mianliao() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(id);
		out.writeString(name);
		out.writeString(material);
		out.writeString(materialPrice);
		out.writeString(paramId);
		out.writeString(pic);
	}

	public static final Creator<Mianliao> CREATOR = new Creator<Mianliao>() {
		@Override
		public Mianliao createFromParcel(Parcel in) {
			return new Mianliao(in);
		}

		@Override
		public Mianliao[] newArray(int size) {
			return new Mianliao[size];
		}
	};

	private Mianliao(Parcel in) {
		id = in.readString();
		name = in.readString();
		material=in.readString();
		materialPrice=in.readString();
		paramId=in.readString();
		pic=in.readString();

	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

}
