package cn.dressbook.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description 交易记录
 * @author 袁东华
 * @date 2016-2-19
 */
public class JYJL implements Parcelable {

	/**
	 * 时间
	 */
	public String addTime;
	/**
	 * 交易类型：1收入，2支出
	 */
	public String direction;
	/**
	 * 变动金额
	 */
	public String vary;
	/**
	 * 变动原因 1下级消费分红，2季度奖励，3年度奖励，4订单消费
	 */
	public String reson;
	/**
	 * 余额
	 */
	public String balance;

	public JYJL() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getVary() {
		return vary;
	}

	public void setVary(String vary) {
		this.vary = vary;
	}

	public String getReson() {
		return reson;
	}

	public void setReson(String reson) {
		this.reson = reson;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(addTime);
		out.writeString(direction);
		out.writeString(vary);
		out.writeString(reson);
		out.writeString(balance);
	}

	public static final Creator<JYJL> CREATOR = new Creator<JYJL>() {
		@Override
		public JYJL createFromParcel(Parcel in) {
			return new JYJL(in);
		}

		@Override
		public JYJL[] newArray(int size) {
			return new JYJL[size];
		}
	};

	private JYJL(Parcel in) {
		addTime = in.readString();
		direction = in.readString();
		vary = in.readString();
		reson = in.readString();
		balance = in.readString();
	}

}
