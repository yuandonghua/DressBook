package cn.dressbook.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description 营收记录
 * @author 袁东华
 * @date 2016-3-16
 */
public class YSJL implements Parcelable {

	/**
	 * 时间
	 */
	public String addTime;
	/**
	 */
	public String userName;
	/**
	 */
	public String franchiseeName;
	/**
	 */
	public String orderCode;
	/**
	 */
	public String income;
	/**
	 */
	public String incomeType;
	/**
	 */
	public String reson;

	public YSJL() {
		super();
		// TODO Auto-generated constructor stub
	}



	public String getAddTime() {
		return addTime;
	}



	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getFranchiseeName() {
		return franchiseeName;
	}



	public void setFranchiseeName(String franchiseeName) {
		this.franchiseeName = franchiseeName;
	}



	public String getOrderCode() {
		return orderCode;
	}



	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}



	public String getIncome() {
		return income;
	}



	public void setIncome(String income) {
		this.income = income;
	}



	public String getIncomeType() {
		return incomeType;
	}



	public void setIncomeType(String incomeType) {
		this.incomeType = incomeType;
	}



	public String getReson() {
		return reson;
	}



	public void setReson(String reson) {
		this.reson = reson;
	}



	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(addTime);
		out.writeString(userName);
		out.writeString(franchiseeName);
		out.writeString(orderCode);
		out.writeString(income);
		out.writeString(incomeType);
		out.writeString(reson);
	}

	public static final Creator<YSJL> CREATOR = new Creator<YSJL>() {
		@Override
		public YSJL createFromParcel(Parcel in) {
			return new YSJL(in);
		}

		@Override
		public YSJL[] newArray(int size) {
			return new YSJL[size];
		}
	};

	private YSJL(Parcel in) {
		addTime = in.readString();
		userName = in.readString();
		franchiseeName = in.readString();
		orderCode = in.readString();
		income = in.readString();
		incomeType = in.readString();
		reson = in.readString();
	}

}
