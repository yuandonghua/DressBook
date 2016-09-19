package cn.dressbook.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description: 消费信息
 * @author:袁东华
 * @time:2015-11-9下午2:42:31
 */
public class XFJL implements Parcelable {

	/**
	 * 时间
	 */
	public String occurTimeShow;
	/**
	 * 描述
	 */
	public String memo;
	/**
	 * 数量
	 */
	public String valueShow;

	public XFJL() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getoccurTimeShow() {
		return occurTimeShow;
	}

	public void setoccurTimeShow(String occurTimeShow) {
		this.occurTimeShow = occurTimeShow;
	}

	public String getmemo() {
		return memo;
	}

	public void setmemo(String memo) {
		this.memo = memo;
	}

	public String getvalueShow() {
		return valueShow;
	}

	public void setvalueShow(String valueShow) {
		this.valueShow = valueShow;
	}


	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(occurTimeShow);
		out.writeString(memo);
		out.writeString(valueShow);

	}

	public static final Creator<XFJL> CREATOR = new Creator<XFJL>() {
		@Override
		public XFJL createFromParcel(Parcel in) {
			return new XFJL(in);
		}

		@Override
		public XFJL[] newArray(int size) {
			return new XFJL[size];
		}
	};

	private XFJL(Parcel in) {
		occurTimeShow = in.readString();
		memo = in.readString();
		valueShow = in.readString();
	}

}
