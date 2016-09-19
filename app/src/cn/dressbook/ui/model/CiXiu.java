package cn.dressbook.ui.model;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * @description 刺绣颜色和文字共用此类
 * @author 袁东华
 * @date 2016-1-25
 */
public class CiXiu implements Parcelable {
	private String name;
	private String pic;
	private String state;
	public CiXiu() {
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		// TODO Auto-generated method stub
		out.writeString(name);
		out.writeString(pic);
		out.writeString(state);

	}

	private CiXiu(Parcel in) {
		name = in.readString();
		pic = in.readString();
		state= in.readString();
	}

	public static final Creator<CiXiu> CREATOR = new Creator<CiXiu>() {
		@Override
		public CiXiu createFromParcel(Parcel in) {
			return new CiXiu(in);
		}

		@Override
		public CiXiu[] newArray(int size) {
			return new CiXiu[size];
		}
	};

}
