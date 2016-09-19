package cn.dressbook.ui.model;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * @description 交易记录标识
 * @author 袁东华
 * @date 2016-2-19
 */
public class JiaoYiJiLuBiaoZhi implements Parcelable {
	private String name;
	private String reason;
	public JiaoYiJiLuBiaoZhi() {
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getreason() {
		return reason;
	}

	public void setreason(String reason) {
		this.reason = reason;
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
		out.writeString(reason);

	}

	private JiaoYiJiLuBiaoZhi(Parcel in) {
		name = in.readString();
		reason = in.readString();
	}

	public static final Creator<JiaoYiJiLuBiaoZhi> CREATOR = new Creator<JiaoYiJiLuBiaoZhi>() {
		@Override
		public JiaoYiJiLuBiaoZhi createFromParcel(Parcel in) {
			return new JiaoYiJiLuBiaoZhi(in);
		}

		@Override
		public JiaoYiJiLuBiaoZhi[] newArray(int size) {
			return new JiaoYiJiLuBiaoZhi[size];
		}
	};

}
