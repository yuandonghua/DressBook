package cn.dressbook.ui.model;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * @description 店铺
 * @author 袁东华
 * @date 2016-3-15
 */
public class DianPu implements Parcelable {
	private String franchiseeId;
	private String franchiseeName;
	private String addTime;
	public DianPu() {
	}


	public String getAddTime() {
		return addTime;
	}


	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}


	public String getfranchiseeId() {
		return franchiseeId;
	}

	public void setfranchiseeId(String franchiseeId) {
		this.franchiseeId = franchiseeId;
	}

	public String getfranchiseeName() {
		return franchiseeName;
	}

	public void setfranchiseeName(String franchiseeName) {
		this.franchiseeName = franchiseeName;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		// TODO Auto-generated method stub
		out.writeString(franchiseeId);
		out.writeString(franchiseeName);
		out.writeString(addTime);
	}

	private DianPu(Parcel in) {
		franchiseeId = in.readString();
		franchiseeName = in.readString();
		addTime= in.readString();
	}

	public static final Creator<DianPu> CREATOR = new Creator<DianPu>() {
		@Override
		public DianPu createFromParcel(Parcel in) {
			return new DianPu(in);
		}

		@Override
		public DianPu[] newArray(int size) {
			return new DianPu[size];
		}
	};

}
