package cn.dressbook.ui.model;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description: 订单
 * @author:袁东华
 * @time:2015-9-12下午12:05:03
 */
public class OrderForm implements Parcelable {
	// 订单中的刺绣信息
	private DingDanCiXiu dingDanCiXiu;

	public DingDanCiXiu getDingDanCiXiu() {
		return dingDanCiXiu;
	}

	public void setDingDanCiXiu(DingDanCiXiu dingDanCiXiu) {
		this.dingDanCiXiu = dingDanCiXiu;
	}

	/**
	 * 是否可以被扫码
	 */
	private String payMoney;

	public String getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(String payMoney) {
		this.payMoney = payMoney;
	}

	/**
	 * 消费码
	 */
	private String xfm;

	public String getxfm() {
		return xfm;
	}

	public void setxfm(String xfm) {
		this.xfm = xfm;
	}

	/**
	 * 状态名称
	 */
	private String stateShow;

	public String getstateShow() {
		return stateShow;
	}

	public void setstateShow(String stateShow) {
		this.stateShow = stateShow;
	}

	private String priceNet;
	private String mesUser;
	private String priceTotal;
	private String id;
	private String state;
	private String payYb;
	private String freight;
	private String payMode;
	private String addTime;
	private String addrConsignee;
	private String addrCompose;
	private String addrMobile;
	private String orderFormTime;
	private String addrZipcode;
	private ArrayList<MSTJData> orderAttite;
	private DzOda dzOda;
	private ArrayList<TiaoZhenCanShu> tzcsList;

	public ArrayList<TiaoZhenCanShu> getTzcsList() {
		return tzcsList;
	}

	public void setTzcsList(ArrayList<TiaoZhenCanShu> tzcsList) {
		this.tzcsList = tzcsList;
	}

	public DzOda getDzOda() {
		return dzOda;
	}

	public void setDzOda(DzOda dzOda) {
		this.dzOda = dzOda;
	}

	public OrderForm() {

	}

	public String getPriceNet() {
		return priceNet;
	}

	public void setPriceNet(String priceNet) {
		this.priceNet = priceNet;
	}

	public String getMesUser() {
		return mesUser;
	}

	public void setMesUser(String mesUser) {
		this.mesUser = mesUser;
	}

	public String getPriceTotal() {
		return priceTotal;
	}

	public void setPriceTotal(String priceTotal) {
		this.priceTotal = priceTotal;
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

	public String getpayYb() {
		return payYb;
	}

	public void setpayYb(String payYb) {
		this.payYb = payYb;
	}

	public String getFreight() {
		return freight;
	}

	public void setFreight(String freight) {
		this.freight = freight;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getAddrConsignee() {
		return addrConsignee;
	}

	public void setAddrConsignee(String addrConsignee) {
		this.addrConsignee = addrConsignee;
	}

	public String getAddrCompose() {
		return addrCompose;
	}

	public void setAddrCompose(String addrCompose) {
		this.addrCompose = addrCompose;
	}

	public String getAddrMobile() {
		return addrMobile;
	}

	public void setAddrMobile(String addrMobile) {
		this.addrMobile = addrMobile;
	}

	public String getOrderFormTime() {
		return orderFormTime;
	}

	public void setOrderFormTime(String orderFormTime) {
		this.orderFormTime = orderFormTime;
	}

	public String getAddrZipcode() {
		return addrZipcode;
	}

	public void setAddrZipcode(String addrZipcode) {
		this.addrZipcode = addrZipcode;
	}

	public ArrayList<MSTJData> getOrderAttite() {
		return orderAttite;
	}

	public void setOrderAttite(ArrayList<MSTJData> orderAttite) {
		this.orderAttite = orderAttite;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressLint("NewApi")
	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(xfm);
		out.writeString(stateShow);
		out.writeString(priceNet);
		out.writeString(mesUser);
		out.writeString(priceTotal);
		out.writeString(id);
		out.writeString(state);
		out.writeString(payYb);
		out.writeString(freight);
		out.writeString(payMode);
		out.writeString(addTime);
		out.writeString(addrConsignee);
		out.writeString(addrCompose);
		out.writeString(addrMobile);
		out.writeString(orderFormTime);
		out.writeString(addrZipcode);
		out.writeTypedList(orderAttite);
		out.writeTypedList(tzcsList);
		out.writeParcelable(dingDanCiXiu, flags);
	}

	public static final Creator<OrderForm> CREATOR = new Creator<OrderForm>() {
		public OrderForm createFromParcel(Parcel in) {
			return new OrderForm(in);
		}

		public OrderForm[] newArray(int size) {
			return new OrderForm[size];
		}
	};

	@SuppressLint("NewApi")
	private OrderForm(Parcel in) {
		xfm = in.readString();
		stateShow = in.readString();
		priceNet = in.readString();
		mesUser = in.readString();
		priceTotal = in.readString();
		id = in.readString();
		state = in.readString();
		payYb = in.readString();
		freight = in.readString();
		payMode = in.readString();
		addTime = in.readString();
		addrConsignee = in.readString();
		addrCompose = in.readString();
		addrMobile = in.readString();
		orderFormTime = in.readString();
		addrZipcode = in.readString();
		in.readTypedList(orderAttite, MSTJData.CREATOR);
		in.readTypedList(tzcsList, TiaoZhenCanShu.CREATOR);
		in.readParcelable(DingDanCiXiu.class.getClassLoader());
	}

}
