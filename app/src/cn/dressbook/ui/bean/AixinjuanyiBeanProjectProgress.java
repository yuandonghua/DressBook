/*
 * Copyright by Gifted Youngs Workshop and the original author or authors.
 * 
 * This document only allow internal use ; any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Gifted Youngs Workshop from
 * 
 * giftedyoungs@163.com
 * 
 *
 * 此代码由天才少年工作小组开发完成; 仅限内部使用 
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
 * TODO
 * 
 * @author LiShen
 * @company Gifted Youngs Workshop
 * @date 2015-10-30 下午4:26:33
 * @since
 * @version
 */
public class AixinjuanyiBeanProjectProgress implements Parcelable {
	/**
	 * TODO
	 */
	private long id;
	/**
	 * TODO
	 */
	private String title;
	/**
	 * TODO
	 */
	private String content;
	/**
	 * TODO
	 */
	private String pic;
	/**
	 * TODO
	 */
	private long occTime;
	/**
	 * TODO
	 */
	private long addTime;
	/**
	 * TODO
	 */
	private String addTimeShow;
	/**
	 * TODO
	 */
	private String occTimeShow;

	public AixinjuanyiBeanProjectProgress() {
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
	 * TODO 返回 content 的值
	 */
	public String getContent() {
		return content;
	}

	/**
	 * TODO 设置 content 的值
	 */
	public void setContent(String content) {
		this.content = content;
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

	/**
	 * TODO 返回 occTime 的值
	 */
	public long getOccTime() {
		return occTime;
	}

	/**
	 * TODO 设置 occTime 的值
	 */
	public void setOccTime(long occTime) {
		this.occTime = occTime;
	}

	/**
	 * TODO 返回 addTime 的值
	 */
	public long getAddTime() {
		return addTime;
	}

	/**
	 * TODO 设置 addTime 的值
	 */
	public void setAddTime(long addTime) {
		this.addTime = addTime;
	}

	/**
	 * TODO 返回 addTimeShow 的值
	 */
	public String getAddTimeShow() {
		return addTimeShow;
	}

	/**
	 * TODO 设置 addTimeShow 的值
	 */
	public void setAddTimeShow(String addTimeShow) {
		this.addTimeShow = addTimeShow;
	}

	/**
	 * TODO 返回 occTimeShow 的值
	 */
	public String getOccTimeShow() {
		return occTimeShow;
	}

	/**
	 * TODO 设置 occTimeShow 的值
	 */
	public void setOccTimeShow(String occTimeShow) {
		this.occTimeShow = occTimeShow;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static final Creator<AixinjuanyiBeanProjectProgress> CREATOR = new Creator<AixinjuanyiBeanProjectProgress>() {
		@Override
		public AixinjuanyiBeanProjectProgress createFromParcel(Parcel in) {
			return new AixinjuanyiBeanProjectProgress(in);
		}

		@Override
		public AixinjuanyiBeanProjectProgress[] newArray(int size) {
			return new AixinjuanyiBeanProjectProgress[size];
		}
	};

	@Override
	public void writeToParcel(Parcel out, int flags) {
		// TODO Auto-generated method stub
		out.writeLong(id);
		out.writeString(title);
		out.writeString(content);
		out.writeString(pic);
		out.writeLong(occTime);
		out.writeLong(addTime);
		out.writeString(addTimeShow);
		out.writeString(occTimeShow);

	}

	private AixinjuanyiBeanProjectProgress(Parcel in) {
		id = in.readLong();
		title = in.readString();
		content = in.readString();
		pic = in.readString();
		occTime = in.readLong();
		addTime = in.readLong();
		addTimeShow = in.readString();
		occTimeShow = in.readString();
	}
}
