package cn.dressbook.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description: 生活照
 * @author:袁东华
 * @time:2015-9-28下午8:17:09
 */
public class LifePhoto implements Parcelable {

	public String id;
	public String pic;
	public String uploadTimeShow;

	public LifePhoto() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getUploadTimeShow() {
		return uploadTimeShow;
	}

	public void setUploadTimeShow(String uploadTimeShow) {
		this.uploadTimeShow = uploadTimeShow;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(id);
		out.writeString(pic);
		out.writeString(uploadTimeShow);
	}

	public static final Creator<LifePhoto> CREATOR = new Creator<LifePhoto>() {
		@Override
		public LifePhoto createFromParcel(Parcel in) {
			return new LifePhoto(in);
		}

		@Override
		public LifePhoto[] newArray(int size) {
			return new LifePhoto[size];
		}
	};

	private LifePhoto(Parcel in) {
		id = in.readString();
		pic = in.readString();
		uploadTimeShow = in.readString();
	}

}
