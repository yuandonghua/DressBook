package cn.dressbook.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description 广告
 * @author 袁东华
 * @date 2016-2-25
 */
public class GuangGao implements Parcelable {

	/**
	 * 标题
	 */
	public String title;
	/**
	 * 图片
	 */
	public String pic;
	/**
	 * 网页链接
	 */
	public String url;

	public GuangGao() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(title);
		out.writeString(pic);
		out.writeString(url);
	}

	public static final Creator<GuangGao> CREATOR = new Creator<GuangGao>() {
		@Override
		public GuangGao createFromParcel(Parcel in) {
			return new GuangGao(in);
		}

		@Override
		public GuangGao[] newArray(int size) {
			return new GuangGao[size];
		}
	};

	private GuangGao(Parcel in) {
		title = in.readString();
		pic = in.readString();
		url = in.readString();
	}

}
