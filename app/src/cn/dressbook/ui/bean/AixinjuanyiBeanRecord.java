package cn.dressbook.ui.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description 爱心记录
 * @author 袁东华
 * @time 2015-12-11下午5:33:46
 */
public class AixinjuanyiBeanRecord implements Parcelable {
	private long id;
	private long addTime;
	private String logistics;
	private int yq;
	private long yqTime;
	private int state;
	private String addTimeShow;
	private String yqTimeShow;
	private AixinjuanyiBeanRecordProject donateProjectShow;

	public AixinjuanyiBeanRecord() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * TODO 返回 id 的值
	 */
	public long getId() {
		return id;
	}

	/**
	 * TODO 设置 id 的值
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * TODO 返回 addTime 的值
	 */
	public long getAddTime() {
		return addTime;
	}

	/**
	 * TODO 设置 addTime 的值
	 */
	public void setAddTime(long addTime) {
		this.addTime = addTime;
	}

	/**
	 * TODO 返回 logistics 的值
	 */
	public String getLogistics() {
		return logistics;
	}

	/**
	 * TODO 设置 logistics 的值
	 */
	public void setLogistics(String logistics) {
		this.logistics = logistics;
	}

	/**
	 * TODO 返回 yq 的值
	 */
	public int getYq() {
		return yq;
	}

	/**
	 * TODO 设置 yq 的值
	 */
	public void setYq(int yq) {
		this.yq = yq;
	}

	/**
	 * TODO 返回 yqTime 的值
	 */
	public long getYqTime() {
		return yqTime;
	}

	/**
	 * TODO 设置 yqTime 的值
	 */
	public void setYqTime(long yqTime) {
		this.yqTime = yqTime;
	}

	/**
	 * TODO 返回 state 的值
	 */
	public int getState() {
		return state;
	}

	/**
	 * TODO 设置 state 的值
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * TODO 返回 addTimeShow 的值
	 */
	public String getAddTimeShow() {
		return addTimeShow;
	}

	/**
	 * TODO 设置 addTimeShow 的值
	 */
	public void setAddTimeShow(String addTimeShow) {
		this.addTimeShow = addTimeShow;
	}

	/**
	 * TODO 返回 yqTimeShow 的值
	 */
	public String getYqTimeShow() {
		return yqTimeShow;
	}

	/**
	 * TODO 设置 yqTimeShow 的值
	 */
	public void setYqTimeShow(String yqTimeShow) {
		this.yqTimeShow = yqTimeShow;
	}

	/**
	 * TODO 返回 donateProjectShow 的值
	 */
	public AixinjuanyiBeanRecordProject getDonateProjectShow() {
		return donateProjectShow;
	}

	/**
	 * TODO 设置 donateProjectShow 的值
	 */
	public void setDonateProjectShow(
			AixinjuanyiBeanRecordProject donateProjectShow) {
		this.donateProjectShow = donateProjectShow;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static final Creator<AixinjuanyiBeanRecord> CREATOR = new Creator<AixinjuanyiBeanRecord>() {
		@Override
		public AixinjuanyiBeanRecord createFromParcel(Parcel in) {
			return new AixinjuanyiBeanRecord(in);
		}

		@Override
		public AixinjuanyiBeanRecord[] newArray(int size) {
			return new AixinjuanyiBeanRecord[size];
		}
	};

	@Override
	public void writeToParcel(Parcel out, int flags) {
		// TODO Auto-generated method stub
		out.writeLong(id);
		out.writeString(logistics);
		out.writeInt(yq);
		out.writeString(yqTimeShow);
		out.writeInt(state);
		out.writeString(addTimeShow);
		out.writeParcelable(donateProjectShow, flags);

	}

	private AixinjuanyiBeanRecord(Parcel in) {
		id = in.readLong();
		logistics = in.readString();
		yq = in.readInt();
		yqTimeShow = in.readString();
		state = in.readInt();
		addTimeShow = in.readString();
		donateProjectShow = in
				.readParcelable(AixinjuanyiBeanRecordProject.class
						.getClassLoader());
	}
}
