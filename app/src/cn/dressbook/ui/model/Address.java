package cn.dressbook.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description: 收货地址
 * @author:袁东华
 * @time:2015-9-1上午11:26:50
 */
public class Address implements Parcelable {

	/**
	 * 地址id
	 */
	public String id;
	/**
	 * 地址状态
	 */
	public String state;
	public String title;
	public String consignee;
	public String email;
	public String country;
	public String province;
	public String city;
	public String district;
	public String address;
	public String zipcode;
	public String tel;
	public String mobile;
	public String signBuilding;
	public String bestTime;
	
	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSignBuilding() {
		return signBuilding;
	}

	public void setSignBuilding(String signBuilding) {
		this.signBuilding = signBuilding;
	}

	public String getBestTime() {
		return bestTime;
	}

	public void setBestTime(String bestTime) {
		this.bestTime = bestTime;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(id);
		out.writeString(state);
		out.writeString(title);
		out.writeString(consignee);
		out.writeString(email);
		out.writeString(country);
		out.writeString(province);
		out.writeString(city);
		out.writeString(district);
		out.writeString(address);
		out.writeString(zipcode);
		out.writeString(tel);
		out.writeString(mobile);
		out.writeString(signBuilding);
		out.writeString(bestTime);

	}

	public static final Creator<Address> CREATOR = new Creator<Address>() {
		@Override
		public Address createFromParcel(Parcel in) {
			return new Address(in);
		}

		@Override
		public Address[] newArray(int size) {
			return new Address[size];
		}
	};

	private Address(Parcel in) {
		id = in.readString();
		state = in.readString();
		title = in.readString();
		consignee = in.readString();
		email = in.readString();
		country = in.readString();
		province = in.readString();
		city = in.readString();
		district = in.readString();
		address = in.readString();
		zipcode = in.readString();
		tel = in.readString();
		mobile = in.readString();
		signBuilding = in.readString();
		bestTime = in.readString();
	}

}
