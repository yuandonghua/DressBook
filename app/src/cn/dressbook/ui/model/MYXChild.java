package cn.dressbook.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description: 买手推荐方案
 * @author:袁东华
 * @time:2015-8-7下午4:54:15
 */
public class MYXChild implements Parcelable {
	private String id;
	private String title;
	private String first;
	private String urlPic;
	private String url;
	private String external_url;

	public MYXChild() {

	}

	public String getExternal_url() {
		return external_url;
	}

	public void setExternal_url(String external_url) {
		this.external_url = external_url;
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

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getUrlPic() {
		return urlPic;
	}

	public void setUrlPic(String urlPic) {
		this.urlPic = urlPic;
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

		out.writeString(external_url);
		out.writeString(id);
		out.writeString(title);
		out.writeString(first);
		out.writeString(urlPic);
		out.writeString(url);

	}

	public static final Creator<MYXChild> CREATOR = new Creator<MYXChild>() {
		@Override
		public MYXChild createFromParcel(Parcel in) {
			return new MYXChild(in);
		}

		@Override
		public MYXChild[] newArray(int urlPic) {
			return new MYXChild[urlPic];
		}
	};

	private MYXChild(Parcel in) {
		external_url = in.readString();
		id = in.readString();
		title = in.readString();
		first = in.readString();
		urlPic = in.readString();
		url = in.readString();
	}

}
