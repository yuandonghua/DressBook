package cn.dressbook.ui.model;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * 
 * @author 熊天富
 * @描述 业绩提成的molder
 * @时间 2016年2月16日16:42:49
 *
 */
public class YJTCMolder implements Parcelable {
	public YJTCMolder(){
		
	}
	private String id;
	/** 会员等级 */
	private String level;
	/** 邀请码 */
	private String shareCode;
	/** 待确认金额（预计收入） */
	private String moneyOrder="0.00";
	/** 待发放金额（预计季度，年度总收益） */
	private String moneyConting="0.00";
	/** 可用现金 */
	private String moneyAvailable="0.00";
	/** 可取现金额 */
	private String moneyCash="0.00";
	/** 成为会员时间 */
	private String mbTimeShow;
	/** 当前职称时间 */
	private String curTimeShow;
	/** 发展会员 */
	private String members;

	private String flag;
	/** 用户id */
	private String user_id;
	/** 手机号码 */
	private String mobile;
	/** 支付宝账号 */
	private String alipay;
	/** 支付宝昵称 */
	private String alipayName;
	/** 累计提成收入 */
	private String totalIncom="0.00";
	
	public String getTotalIncom() {
		return totalIncom;
	}

	public void setTotalIncom(String totalIncom) {
		this.totalIncom = totalIncom;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getShareCode() {
		return shareCode;
	}

	public void setShareCode(String shareCode) {
		this.shareCode = shareCode;
	}

	public String getMoneyOrder() {
		return moneyOrder;
	}

	public void setMoneyOrder(String moneyOrder) {
		this.moneyOrder = moneyOrder;
	}

	public String getMoneyConting() {
		return moneyConting;
	}

	public void setMoneyConting(String moneyConting) {
		this.moneyConting = moneyConting;
	}

	public String getMoneyAvailable() {
		return moneyAvailable;
	}

	public void setMoneyAvailable(String moneyAvailable) {
		this.moneyAvailable = moneyAvailable;
	}

	public String getMoneyCash() {
		return moneyCash;
	}

	public void setMoneyCash(String moneyCash) {
		this.moneyCash = moneyCash;
	}

	public String getMbTimeShow() {
		return mbTimeShow;
	}

	public void setMbTimeShow(String mbTimeShow) {
		this.mbTimeShow = mbTimeShow;
	}

	public String getCurTimeShow() {
		return curTimeShow;
	}

	public void setCurTimeShow(String curTimeShow) {
		this.curTimeShow = curTimeShow;
	}

	public String getMembers() {
		return members;
	}

	public void setMembers(String members) {
		this.members = members;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAlipay() {
		return alipay;
	}

	public void setAlipay(String alipay) {
		this.alipay = alipay;
	}

	public String getAlipayName() {
		return alipayName;
	}

	public void setAlipayName(String alipayName) {
		this.alipayName = alipayName;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(alipay);
		out.writeString(alipayName);
		out.writeString(curTimeShow);
		out.writeString(flag);
		out.writeString(id);
		out.writeString(level);
		out.writeString(mbTimeShow);
		out.writeString(members);
		out.writeString(mobile);
		out.writeString(moneyAvailable);
		out.writeString(moneyCash);
		out.writeString(moneyConting);
		out.writeString(moneyOrder);
		out.writeString(shareCode);
		out.writeString(user_id);
		out.writeString(totalIncom);
	}

	private YJTCMolder(Parcel in) {
		alipay=in.readString();
		alipayName=in.readString();
		curTimeShow=in.readString();
		flag=in.readString();
		id=in.readString();
		level=in.readString();
		mbTimeShow=in.readString();
		members=in.readString();
		mobile=in.readString();
		moneyAvailable=in.readString();
		moneyCash=in.readString();
		moneyConting=in.readString();
		moneyOrder=in.readString();
		shareCode=in.readString();
		user_id=in.readString();
		totalIncom=in.readString();

	}

	public static final Creator<YJTCMolder> CREATOR = new Creator<YJTCMolder>() {
		@Override
		public YJTCMolder createFromParcel(Parcel in) {
			return new YJTCMolder(in);
		}

		@Override
		public YJTCMolder[] newArray(int size) {
			return new YJTCMolder[size];
		}
	};

}
