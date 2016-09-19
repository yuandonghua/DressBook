package cn.dressbook.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description 定制商品的订单信息
 * @author 袁东华
 * @date 2016-2-24
 */
public class DzOda implements Parcelable {
	public String id;
	public String title;
	public String paramValue;
	public String pic;
	public String dzattireId;
	public String templateId;
	public String inviter;
	public String odaParams;
	
	public DzOda() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getParamValue() {
		return paramValue;
	}


	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}


	public String getPic() {
		return pic;
	}


	public void setPic(String pic) {
		this.pic = pic;
	}


	public String getDzattireId() {
		return dzattireId;
	}


	public void setDzattireId(String dzattireId) {
		this.dzattireId = dzattireId;
	}


	public String getTemplateId() {
		return templateId;
	}


	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}


	public String getInviter() {
		return inviter;
	}


	public void setInviter(String inviter) {
		this.inviter = inviter;
	}


	public String getOdaParams() {
		return odaParams;
	}


	public void setOdaParams(String odaParams) {
		this.odaParams = odaParams;
	}


	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(id);
		out.writeString(title);
		out.writeString(paramValue);
		out.writeString(pic);
		out.writeString(dzattireId);
		out.writeString(templateId);
		out.writeString(inviter);
		out.writeString(odaParams);
	}

	public static final Creator<DzOda> CREATOR = new Creator<DzOda>() {
		@Override
		public DzOda createFromParcel(Parcel in) {
			return new DzOda(in);
		}

		@Override
		public DzOda[] newArray(int size) {
			return new DzOda[size];
		}
	};

	private DzOda(Parcel in) {
		id = in.readString();
		title = in.readString();
		paramValue = in.readString();
		pic = in.readString();
		dzattireId = in.readString();
		templateId = in.readString();
		inviter = in.readString();
		odaParams = in.readString();
	}

}
