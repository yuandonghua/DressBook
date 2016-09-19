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

/**
 * TODO 博文实体类
 * 
 * @author LiShen
 * @company Gifted Youngs Workshop
 * @date 2015-10-16 上午11:45:58
 * @since
 * @version
 */
public class MeitanBeanArticle implements Parcelable {
	/**
	 * TODO 文章id
	 */
	private long id;
	/**
	 * TODO 文章标题
	 */
	private String title;
	/**
	 * TODO 文章标签
	 */
	private String subjectTags;
	/**
	 * TODO 文章内容
	 */
	private String content;
	/**
	 * TODO 发表时间
	 */
	private long addTime;
	/**
	 * TODO 显示的发表时间
	 */
	private String addTimeShow;
	/**
	 * TODO 赞数
	 */
	private long praiseNum;
	/**
	 * TODO 评论数
	 */
	private long cmdNum;
	/**
	 * TODO 文章照片地址列表
	 */
	private ArrayList<MeitanBeanArticlePhotosUrl> cmtPostsImgs;
	/**
	 * TODO 用户昵称
	 */
	private String userName;
	/**
	 * TODO 用户id
	 */
	private long userId;
	/**
	 * TODO 用户头像地址
	 */
	private String userAvatar;
	/**
	 * TODO 用户种类
	 */
	private String userLevel;
	/**
	 * TODO 是否已经被当前用户喜欢
	 */
	private int isPraise;
	/**
	 * TODO 博主是否被当前用户关注
	 */
	private int isFollow;

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
	 * TODO 设置 title 的值
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * TODO 返回 subjectTags 的值
	 */
	public String getSubjectTags() {
		return subjectTags;
	}

	/**
	 * TODO 设置 subjectTags 的值
	 */
	public void setSubjectTags(String subjectTags) {
		this.subjectTags = subjectTags;
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
	 * TODO 返回 cmdNum 的值
	 */
	public long getCmdNum() {
		return cmdNum;
	}

	/**
	 * TODO 设置 cmdNum 的值
	 */
	public void setCmdNum(long cmdNum) {
		this.cmdNum = cmdNum;
	}

	/**
	 * TODO 返回 cmtPostsImgs 的值
	 */
	public ArrayList<MeitanBeanArticlePhotosUrl> getCmtPostsImgs() {
		return cmtPostsImgs;
	}

	/**
	 * TODO 设置 cmtPostsImgs 的值
	 */
	public void setCmtPostsImgs(ArrayList<MeitanBeanArticlePhotosUrl> cmtPostsImgs) {
		this.cmtPostsImgs = cmtPostsImgs;
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

	/**
	 * TODO 返回 isFollow 的值
	 */
	public int getIsFollow() {
		return isFollow;
	}

	/**
	 * TODO 设置 isFollow 的值
	 */
	public void setIsFollow(int isFollow) {
		this.isFollow = isFollow;
	}

	public MeitanBeanArticle() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static final Creator<MeitanBeanArticle> CREATOR = new Creator<MeitanBeanArticle>() {
		@Override
		public MeitanBeanArticle createFromParcel(Parcel in) {
			return new MeitanBeanArticle(in);
		}

		@Override
		public MeitanBeanArticle[] newArray(int size) {
			return new MeitanBeanArticle[size];
		}
	};

	@Override
	public void writeToParcel(Parcel out, int flags) {
		// TODO Auto-generated method stub
		out.writeLong(id);
		out.writeString(title);
		out.writeString(subjectTags);
		out.writeString(content);
		out.writeString(addTimeShow);
		out.writeLong(praiseNum);
		out.writeLong(cmdNum);
		out.writeLong(userId);
		out.writeString(userName);
		out.writeString(userAvatar);
		out.writeString(userLevel);
		out.writeTypedList(cmtPostsImgs);
	}

	private MeitanBeanArticle(Parcel in) {
		id = in.readLong();
		title = in.readString();
		subjectTags = in.readString();
		content = in.readString();
		addTimeShow = in.readString();
		praiseNum = in.readLong();
		cmdNum = in.readLong();
		userId = in.readLong();
		userName = in.readString();
		userAvatar = in.readString();
		userLevel = in.readString();
		in.readTypedList(cmtPostsImgs, MeitanBeanArticlePhotosUrl.CREATOR);
	}
}
