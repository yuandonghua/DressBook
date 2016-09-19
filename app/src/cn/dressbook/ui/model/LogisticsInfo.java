package cn.dressbook.ui.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description: 物流信息
 * @author:袁东华
 * @time:2015-9-15下午5:57:44
 */
public class LogisticsInfo implements Parcelable {
	private String companytype;
	private String codenumber;
	private ArrayList<LogisticsInfo2> data;

	public LogisticsInfo() {

	}

	public ArrayList<LogisticsInfo2> getData() {
		return data;
	}

	public void setData(ArrayList<LogisticsInfo2> data) {
		this.data = data;
	}

	public String getCompanytype() {
		return companytype;
	}

	public void setCompanytype(String companytype) {
		this.companytype = companytype;
	}

	public String getCodenumber() {
		return codenumber;
	}

	public void setCodenumber(String codenumber) {
		this.codenumber = codenumber;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {

		out.writeString(companytype);
		out.writeString(codenumber);
		out.writeTypedList(data);

	}

	public static final Creator<LogisticsInfo> CREATOR = new Creator<LogisticsInfo>() {
		@Override
		public LogisticsInfo createFromParcel(Parcel in) {
			return new LogisticsInfo(in);
		}

		@Override
		public LogisticsInfo[] newArray(int size) {
			return new LogisticsInfo[size];
		}
	};

	private LogisticsInfo(Parcel in) {
		companytype = in.readString();
		codenumber = in.readString();
		in.readTypedList(data, LogisticsInfo2.CREATOR);
	}

}
