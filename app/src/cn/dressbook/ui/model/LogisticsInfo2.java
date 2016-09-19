package cn.dressbook.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description: 物流信息
 * @author:袁东华
 * @time:2015-9-15下午5:57:44
 */
public class LogisticsInfo2 implements Parcelable {
	private String time;
	private String context;

	public LogisticsInfo2() {

	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {

		out.writeString(time);
		out.writeString(context);

	}

	public static final Creator<LogisticsInfo2> CREATOR = new Creator<LogisticsInfo2>() {
		@Override
		public LogisticsInfo2 createFromParcel(Parcel in) {
			return new LogisticsInfo2(in);
		}

		@Override
		public LogisticsInfo2[] newArray(int size) {
			return new LogisticsInfo2[size];
		}
	};

	private LogisticsInfo2(Parcel in) {
		time = in.readString();
		context = in.readString();
	}

}
