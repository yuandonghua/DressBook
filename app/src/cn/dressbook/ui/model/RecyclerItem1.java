package cn.dressbook.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description: 顾问fragment界面中的条目
 * @author:袁东华
 * @time:2015-11-16上午10:43:32
 */
public class RecyclerItem1 implements Parcelable {

	public String title;
	public String miaoshu;
	public String time;
	public String image;

	public RecyclerItem1() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getmiaoshu() {
		return miaoshu;
	}

	public void setmiaoshu(String miaoshu) {
		this.miaoshu = miaoshu;
	}

	public String gettime() {
		return time;
	}

	public void settime(String time) {
		this.time = time;
	}

	public String getimage() {
		return image;
	}

	public void setimage(String image) {
		this.image = image;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(title);
		out.writeString(miaoshu);
		out.writeString(time);
		out.writeString(image);

	}

	public static final Creator<RecyclerItem1> CREATOR = new Creator<RecyclerItem1>() {
		@Override
		public RecyclerItem1 createFromParcel(Parcel in) {
			return new RecyclerItem1(in);
		}

		@Override
		public RecyclerItem1[] newArray(int size) {
			return new RecyclerItem1[size];
		}
	};

	private RecyclerItem1(Parcel in) {
		title = in.readString();
		miaoshu = in.readString();
		time = in.readString();
		image = in.readString();
	}

}
