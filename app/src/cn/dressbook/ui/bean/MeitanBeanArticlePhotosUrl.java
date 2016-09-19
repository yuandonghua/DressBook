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
 * TODO 文章图片实体类
 * 
 * @author LiShen
 * @company Gifted Youngs Workshop
 * @date 2015-10-18 下午9:47:02
 * @since
 * @version
 */
public class MeitanBeanArticlePhotosUrl implements Parcelable{
	/**
	 * TODO
	 */
	private String url;

	/**
	 * TODO 返回 url 的值
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * TODO 设置 url 的值
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	public MeitanBeanArticlePhotosUrl() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static final Creator<MeitanBeanArticlePhotosUrl> CREATOR = new Creator<MeitanBeanArticlePhotosUrl>() {
		@Override
		public MeitanBeanArticlePhotosUrl createFromParcel(Parcel in) {
			return new MeitanBeanArticlePhotosUrl(in);
		}

		@Override
		public MeitanBeanArticlePhotosUrl[] newArray(int size) {
			return new MeitanBeanArticlePhotosUrl[size];
		}
	};
	@Override
	public void writeToParcel(Parcel out, int flags) {
		// TODO Auto-generated method stub
		out.writeString(url);
	}

	private MeitanBeanArticlePhotosUrl(Parcel in) {
		url = in.readString();
	}
}
