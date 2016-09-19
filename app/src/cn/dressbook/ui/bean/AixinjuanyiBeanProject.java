/*
 * Copyright by Gifted Youngs Workshop and the original author or authors.
 * 
 * This document only allow internal use ; any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Gifted Youngs Workshop from
 * 
 * giftedyoungs@63.com
 * 
 *
 * 此代码由天才少年工作小组开发完成; 仅限内部使用 
 * 外部使用该代码将负相应的法律责任
 * 更多信息请致信天才少年工作小组
 * 
 * giftedyoungs@63.com
 *
 */
package cn.dressbook.ui.bean;

import cn.dressbook.ui.model.Address;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/**
 * @description 爱心捐衣项目
 * @author 袁东华
 * @time 2015-12-10下午2:02:17
 */
public class AixinjuanyiBeanProject implements Parcelable {
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
	private String sponsor;
	/**
	 * TODO
	 */
	private String surl;
	/**
	 * TODO
	 */
	private long addTime;
	/**
	 * TODO
	 */
	private int state;
	/**
	 * TODO
	 */
	private String addTimeShow;
	/**
	 * TODO
	 */
	private int isPraise;
	/**
	 * TODO
	 */
	private int isJoin;
	/**
	 * TODO
	 */
	private long praiseNum;
	/**
	 * TODO
	 */
	private long joinNum;

	public AixinjuanyiBeanProject() {
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
	 * TODO 返回 sponsor 的值
	 */
	public String getSponsor() {
		return sponsor;
	}

	/**
	 * TODO 设置 sponsor 的值
	 */
	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	/**
	 * TODO 返回 surl 的值
	 */
	public String getSurl() {
		return surl;
	}

	/**
	 * TODO 设置 surl 的值
	 */
	public void setSurl(String surl) {
		this.surl = surl;
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
	 * TODO 返回 state 的值
	 */
	public int getState() {
		return state;
	}

	/**
	 * TODO 设置 state 的值
	 */
	public void setState(int state) {
		this.state = state;
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
	 * TODO 返回 isJoin 的值
	 */
	public int getIsJoin() {
		return isJoin;
	}

	/**
	 * TODO 设置 isJoin 的值
	 */
	public void setIsJoin(int isJoin) {
		this.isJoin = isJoin;
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
	 * TODO 返回 joinNum 的值
	 */
	public long getJoinNum() {
		return joinNum;
	}

	/**
	 * TODO 设置 joinNum 的值
	 */
	public void setJoinNum(long joinNum) {
		this.joinNum = joinNum;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static final Creator<AixinjuanyiBeanProject> CREATOR = new Creator<AixinjuanyiBeanProject>() {
		@Override
		public AixinjuanyiBeanProject createFromParcel(Parcel in) {
			return new AixinjuanyiBeanProject(in);
		}

		@Override
		public AixinjuanyiBeanProject[] newArray(int size) {
			return new AixinjuanyiBeanProject[size];
		}
	};

	@Override
	public void writeToParcel(Parcel out, int flags) {
		// TODO Auto-generated method stub
		out.writeLong(id);
		out.writeString(title);
		out.writeString(content);
		out.writeString(pic);
		out.writeString(sponsor);
		out.writeString(surl);
		out.writeInt(state);
		out.writeString(addTimeShow);
		out.writeInt(isPraise);
		out.writeInt(isJoin);
		out.writeLong(praiseNum);
		out.writeLong(joinNum);
		
	}

	private AixinjuanyiBeanProject(Parcel in) {
		id = in.readLong();
		title = in.readString();
		content = in.readString();
		pic = in.readString();
		sponsor = in.readString();
		surl = in.readString();
		state = in.readInt();
		addTimeShow = in.readString();
		isPraise = in.readInt();
		isJoin = in.readInt();
		praiseNum = in.readLong();
		joinNum = in.readLong();
	}

}
