package cn.dressbook.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description: 调整细节的调整参数
 * @author:ydh
 * @data:2016-4-21上午11:02:50
 */
public class TiaoZhenCanShu implements Parcelable {
	private String title;
	private String pic;
	private String content;
	private String id;
	private String isChecked;
	private String materialPrice;
	private String material;
	
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

	public String getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}

	public TiaoZhenCanShu() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getcontent() {
		return content;
	}

	public void setcontent(String content) {
		this.content = content;
	}

	public String gettitle() {
		return title;
	}

	public void settitle(String title) {
		this.title = title;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		// TODO Auto-generated method stub
		out.writeString(material);
		out.writeString(materialPrice);
		out.writeString(isChecked);
		out.writeString(title);
		out.writeString(pic);
		out.writeString(content);
		out.writeString(id);

	}

	private TiaoZhenCanShu(Parcel in) {
		material= in.readString();
		materialPrice = in.readString();
		isChecked = in.readString();
		title = in.readString();
		pic = in.readString();
		content = in.readString();
		id = in.readString();
	}

	public static final Creator<TiaoZhenCanShu> CREATOR = new Creator<TiaoZhenCanShu>() {
		@Override
		public TiaoZhenCanShu createFromParcel(Parcel in) {
			return new TiaoZhenCanShu(in);
		}

		@Override
		public TiaoZhenCanShu[] newArray(int size) {
			return new TiaoZhenCanShu[size];
		}
	};

}
