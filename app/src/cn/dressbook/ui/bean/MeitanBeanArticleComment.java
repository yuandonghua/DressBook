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

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/**
 * TODO 文章评论实体类
 * 
 * @author LiShen
 * @company Gifted Youngs Workshop
 * @date 2015-10-21 下午4:34:15
 * @since
 * @version
 */
public class MeitanBeanArticleComment implements Parcelable {
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
	private long addTime;
	/**
	 * TODO
	 */
	private String addTimeShow;
	/**
	 * TODO
	 */
	private long praiseNum;
	/**
	 * TODO
	 */
	private long userId;
	/**
	 * TODO
	 */
	private ArrayList<MeitanBeanArticlePhotosUrl> cmtReplyImgs;
	/**
	 * TODO
	 */
	private String userName;
	/**
	 * TODO
	 */
	private String userAvatar;
	/**
	 * TODO
	 */
	private String userLevel;
	/**
	 * TODO
	 */
	private int isPraise;

	public MeitanBeanArticleComment() {
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
	 * TODO 返回 praiseNum 的值
	 */
	public long getPraiseNum() {
		return praiseNum;
	}

	/**
	 * TODO 设置 praiseNum 的值
	 */
	public void setPraiseNum(long praiseNum) {
		this.praiseNum = praiseNum;
	}

	/**
	 * TODO 返回 userId 的值
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * TODO 设置 userId 的值
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * TODO 返回 cmtReplyImgs 的值
	 */
	public ArrayList<MeitanBeanArticlePhotosUrl> getCmtReplyImgs() {
		return cmtReplyImgs;
	}

	/**
	 * TODO 设置 cmtReplyImgs 的值
	 */
	public void setCmtReplyImgs(ArrayList<MeitanBeanArticlePhotosUrl> cmtReplyImgs) {
		this.cmtReplyImgs = cmtReplyImgs;
	}

	/**
	 * TODO 返回 userName 的值
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * TODO 设置 userName 的值
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * TODO 返回 userAvatar 的值
	 */
	public String getUserAvatar() {
		return userAvatar;
	}

	/**
	 * TODO 设置 userAvatar 的值
	 */
	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	/**
	 * TODO 返回 userLevel 的值
	 */
	public String getUserLevel() {
		return userLevel;
	}

	/**
	 * TODO 设置 userLevel 的值
	 */
	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	/**
	 * TODO 返回 isPraise 的值
	 */
	public int getIsPraise() {
		return isPraise;
	}

	/**
	 * TODO 设置 isPraise 的值
	 */
	public void setIsPraise(int isPraise) {
		this.isPraise = isPraise;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static final Creator<MeitanBeanArticleComment> CREATOR = new Creator<MeitanBeanArticleComment>() {
		@Override
		public MeitanBeanArticleComment createFromParcel(Parcel in) {
			return new MeitanBeanArticleComment(in);
		}

		@Override
		public MeitanBeanArticleComment[] newArray(int size) {
			return new MeitanBeanArticleComment[size];
		}
	};

	@Override
	public void writeToParcel(Parcel out, int flags) {
		// TODO Auto-generated method stub
		out.writeLong(id);
		out.writeString(title);
		out.writeString(content);
		out.writeString(addTimeShow);
		out.writeLong(praiseNum);
		out.writeLong(userId);
		out.writeString(userName);
		out.writeString(userAvatar);
		out.writeString(userLevel);
		out.writeInt(isPraise);
		out.writeTypedList(cmtReplyImgs);
	}

	private MeitanBeanArticleComment(Parcel in) {
		id = in.readLong();
		title = in.readString();
		content = in.readString();
		addTimeShow = in.readString();
		praiseNum = in.readLong();
		userId = in.readLong();
		userName = in.readString();
		userAvatar = in.readString();
		userLevel = in.readString();
		isPraise = in.readInt();
		in.readTypedList(cmtReplyImgs, MeitanBeanArticlePhotosUrl.CREATOR);
	}
}
