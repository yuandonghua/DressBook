package cn.dressbook.ui.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * TODO
 * 
 * @author LiShen
 * @company Gifted Youngs Workshop
 * @date 2015-10-30 下午4;29;06
 * @since
 * @version
 */
public class AixinjuanyiBeanDonateAddress implements Parcelable {
	/**
	 * TODO
	 */
	private long id;
	/**
	 * TODO
	 */
	private String name;
	/**
	 * TODO
	 */
	private String postcode;
	/**
	 * TODO
	 */
	private String tel;
	/**
	 * TODO
	 */
	private String province;
	/**
	 * TODO
	 */
	private String city;
	/**
	 * TODO
	 */
	private String district;
	/**
	 * TODO
	 */
	private String addr;
	/**
	 * TODO
	 */
	private String addrShow;

	public AixinjuanyiBeanDonateAddress() {
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
	 * TODO 返回 name 的值
	 */
	public String getName() {
		return name;
	}

	/**
	 * TODO 设置 name 的值
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * TODO 返回 postcode 的值
	 */
	public String getPostcode() {
		return postcode;
	}

	/**
	 * TODO 设置 postcode 的值
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	/**
	 * TODO 返回 tel 的值
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * TODO 设置 tel 的值
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * TODO 返回 province 的值
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * TODO 设置 province 的值
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * TODO 返回 city 的值
	 */
	public String getCity() {
		return city;
	}

	/**
	 * TODO 设置 city 的值
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * TODO 返回 district 的值
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * TODO 设置 district 的值
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	/**
	 * TODO 返回 addr 的值
	 */
	public String getAddr() {
		return addr;
	}

	/**
	 * TODO 设置 addr 的值
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}

	/**
	 * TODO 返回 addrShow 的值
	 */
	public String getAddrShow() {
		return addrShow;
	}

	/**
	 * TODO 设置 addrShow 的值
	 */
	public void setAddrShow(String addrShow) {
		this.addrShow = addrShow;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static final Creator<AixinjuanyiBeanDonateAddress> CREATOR = new Creator<AixinjuanyiBeanDonateAddress>() {
		@Override
		public AixinjuanyiBeanDonateAddress createFromParcel(Parcel in) {
			return new AixinjuanyiBeanDonateAddress(in);
		}

		@Override
		public AixinjuanyiBeanDonateAddress[] newArray(int size) {
			return new AixinjuanyiBeanDonateAddress[size];
		}
	};

	@Override
	public void writeToParcel(Parcel out, int flags) {
		// TODO Auto-generated method stub
		out.writeLong(id);
		out.writeString(name);
		out.writeString(postcode);
		out.writeString(tel);
		out.writeString(province);
		out.writeString(city);
		out.writeString(district);
		out.writeString(addr);
		out.writeString(addrShow);

	}

	private AixinjuanyiBeanDonateAddress(Parcel in) {
		id = in.readLong();
		name = in.readString();
		postcode = in.readString();
		tel = in.readString();
		province = in.readString();
		city = in.readString();
		district = in.readString();
		addr = in.readString();
		addrShow = in.readString();
	}
}
