/*
 * Copyright by Gifted Youngs Workshop and the original author or authors.
 * 
 * This document only allow internal use , any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Gifted Youngs Workshop from
 * 
 * giftedyoungs@163.com
 * 
 *
 * 此代码由天才少年工作小组开发完成, 仅限内部使用 
 * 外部使用该代码将负相应的法律责任
 * 更多信息请致信天才少年工作小组
 * 
 * giftedyoungs@163.com
 *
 */
package cn.dressbook.ui.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/**
 * @description
 * @author 袁东华
 * @time 2015-12-11下午5:43:19
 */
public class AixinjuanyiBeanRecordProject implements Parcelable {
	private long id;
	private String title;
	private String pic;

	public AixinjuanyiBeanRecordProject() {
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
	 * TODO 返回 title 的值
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * TODO 设置 title 的值
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * TODO 返回 pic 的值
	 */
	public String getPic() {
		return pic;
	}

	/**
	 * TODO 设置 pic 的值
	 */
	public void setPic(String pic) {
		this.pic = pic;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static final Creator<AixinjuanyiBeanRecordProject> CREATOR = new Creator<AixinjuanyiBeanRecordProject>() {
		@Override
		public AixinjuanyiBeanRecordProject createFromParcel(Parcel in) {
			return new AixinjuanyiBeanRecordProject(in);
		}

		@Override
		public AixinjuanyiBeanRecordProject[] newArray(int size) {
			return new AixinjuanyiBeanRecordProject[size];
		}
	};

	@Override
	public void writeToParcel(Parcel out, int flags) {
		// TODO Auto-generated method stub
		out.writeLong(id);
		out.writeString(title);
		out.writeString(pic);

	}

	private AixinjuanyiBeanRecordProject(Parcel in) {
		id = in.readLong();
		title = in.readString();
		pic = in.readString();
	}
}
