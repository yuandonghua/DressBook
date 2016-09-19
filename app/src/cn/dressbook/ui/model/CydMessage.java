package cn.dressbook.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description:系统消息
 * @author:袁东华
 * @time:2015-11-24下午2:19:09
 */
public class CydMessage implements Parcelable {
	/**
	 * 文件名
	 */
	public String fileName;
	/**
	 * 时间
	 */
	public String time;
	/**
	 * 标题
	 */
	public String title;
	/**
	 * 内容
	 */
	public String text;
	/**
	 * 类型
	 */
	public String type;

	public CydMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String gettime() {
		return time;
	}

	public void settime(String time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String gettext() {
		return text;
	}

	public void settext(String text) {
		this.text = text;
	}

	public String gettype() {
		return type;
	}

	public void settype(String type) {
		this.type = type;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {

		out.writeString(fileName);
		out.writeString(time);
		out.writeString(title);
		out.writeString(text);
		out.writeString(type);

	}

	public static final Creator<CydMessage> CREATOR = new Creator<CydMessage>() {
		@Override
		public CydMessage createFromParcel(Parcel in) {
			return new CydMessage(in);
		}

		@Override
		public CydMessage[] newArray(int size) {
			return new CydMessage[size];
		}
	};

	private CydMessage(Parcel in) {
		fileName = in.readString();
		time = in.readString();
		title = in.readString();
		text = in.readString();
		type = in.readString();
	}

}
