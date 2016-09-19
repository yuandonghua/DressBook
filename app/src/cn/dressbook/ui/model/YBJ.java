package cn.dressbook.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description:衣保金
 * @author:袁东华
 * @modifier:袁东华
 * @remarks:
 * @2015年7月21日 上午11:59:48
 */
public class YBJ implements Parcelable {
	private String baseValue;
	private String ybValue;
	private String occurTime;
	private String id;
	private String memo;
	private String oper;
	private String state;

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(baseValue);
		out.writeString(ybValue);
		out.writeString(occurTime);
		out.writeString(id);
		out.writeString(memo);
		out.writeString(oper);
		out.writeString(state);
	}

	public static final Creator<YBJ> CREATOR = new Creator<YBJ>() {
		@Override
		public YBJ createFromParcel(Parcel in) {
			return new YBJ(in);
		}

		@Override
		public YBJ[] newArray(int size) {
			return new YBJ[size];
		}
	};

	private YBJ(Parcel in) {
		baseValue = in.readString();
		ybValue = in.readString();
		occurTime = in.readString();
		id = in.readString();
		memo = in.readString();
		oper = in.readString();
		state = in.readString();

	}

	public String getBaseValue() {
		return baseValue;
	}

	public void setBaseValue(String baseValue) {
		this.baseValue = baseValue;
	}

	public String getYbValue() {
		return ybValue;
	}

	public void setYbValue(String ybValue) {
		this.ybValue = ybValue;
	}

	public String getOccurTime() {
		return occurTime;
	}

	public void setOccurTime(String occurTime) {
		this.occurTime = occurTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public YBJ() {

	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

}
