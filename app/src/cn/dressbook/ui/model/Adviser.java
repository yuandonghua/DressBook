package cn.dressbook.ui.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description: 顾问师
 * @author:袁东华
 * @modifier:袁东华
 * @remarks:
 * @2015-6-8 下午5:45:35
 */
public class Adviser implements Parcelable {
	/**
	 * 学校
	 */
	private String school;
	/**
	 * 专业
	 */
	private String major;
	/**
	 * 标题描述
	 */
	private String title;
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 用户id
	 */
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 理念
	 */
	private String resume;
	/**
	 * 风格
	 */
	private String style;
	/**
	 * 设计方案性别
	 */
	private String attireSex;
	/**
	 * 顾问师的方形头像
	 */
	private String photoPath;
	/**
	 * 顾问师ID
	 */
	private String adviserId;
	/**
	 * 赞
	 */
	private String zan;
	/**
	 * 顾问师的idea
	 */
	private String idea;
	/**
	 * 顾问师的昵称
	 */
	private String name;
	/**
	 * 顾问师的圆形头像
	 */
	private String autographPath;
	/**
	 * 关注
	 */
	private String guanzhu;
	/**
	 * 客服在线情况
	 */
	private String online;
	/**
	 * 博文id
	 */
	private String postsId;
	/**
	 * 博文时间
	 */
	private String postsTime;
	public String getPostsId() {
		return postsId;
	}

	public void setPostsId(String postsId) {
		this.postsId = postsId;
	}

	public String getPostsTime() {
		return postsTime;
	}

	public void setPostsTime(String postsTime) {
		this.postsTime = postsTime;
	}

	public String getPostsTitle() {
		return postsTitle;
	}

	public void setPostsTitle(String postsTitle) {
		this.postsTitle = postsTitle;
	}

	/**
	 * 博文标题
	 */
	private String postsTitle;
	/**
	 * 客服ID
	 */
	private String kfId;
	/**
	 * 顾问师作品
	 */
	private ArrayList<String> showpic;
	/**
	 * 
	 * 创建一个新的实例 Adviser.
	 * 
	 */
	public Adviser() {

	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}


	public String getOnline() {
		return online;
	}

	public void setOnline(String online) {
		this.online = online;
	}

	public String getKfId() {
		return kfId;
	}

	public void setKfId(String kfId) {
		this.kfId = kfId;
	}

	public ArrayList<String> getShowpic() {
		return showpic;
	}

	public void setShowpic(ArrayList<String> showpic) {
		this.showpic = showpic;
	}

	public String getAttireSex() {
		return attireSex;
	}

	public void setAttireSex(String attireSex) {
		this.attireSex = attireSex;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getAdviserId() {
		return adviserId;
	}

	public void setAdviserId(String adviserId) {
		this.adviserId = adviserId;
	}

	public String getZan() {
		return zan;
	}

	public void setZan(String zan) {
		this.zan = zan;
	}

	public String getIdea() {
		return idea;
	}

	public void setIdea(String idea) {
		this.idea = idea;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAutographPath() {
		return autographPath;
	}

	public void setAutographPath(String autographPath) {
		this.autographPath = autographPath;
	}

	public String getGuanzhu() {
		return guanzhu;
	}

	public void setGuanzhu(String guanzhu) {
		this.guanzhu = guanzhu;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(resume);
		out.writeString(userId);
		out.writeString(title);
		out.writeString(postsId);
		out.writeString(postsTime);
		out.writeString(postsTitle);
		out.writeString(school);
		out.writeString(major);
		out.writeString(style);
		out.writeString(attireSex);
		out.writeString(photoPath);
		out.writeString(adviserId);
		out.writeString(zan);
		out.writeString(idea);
		out.writeString(name);
		out.writeString(autographPath);
		out.writeString(guanzhu);
		out.writeString(online);
		out.writeString(kfId);
		int picCount = 0;
		if (showpic != null && showpic.size() > 0) {
			picCount = showpic.size();
			out.writeInt(picCount);
			for (int i = 0; i < showpic.size(); i++) {
				out.writeString(showpic.get(i));
			}
		} else {
			out.writeInt(picCount);

		}
	}

	public static final Creator<Adviser> CREATOR = new Creator<Adviser>() {
		@Override
		public Adviser createFromParcel(Parcel in) {
			return new Adviser(in);
		}

		@Override
		public Adviser[] newArray(int size) {
			return new Adviser[size];
		}
	};

	private Adviser(Parcel in) {
		resume=in.readString();
		userId=in.readString();
		title=in.readString();
		postsId= in.readString();
		postsTime= in.readString();
		postsTitle= in.readString();
		school= in.readString();
		major= in.readString();
		style= in.readString();
		attireSex = in.readString();
		photoPath = in.readString();
		adviserId = in.readString();
		zan = in.readString();
		idea = in.readString();
		name = in.readString();
		autographPath = in.readString();
		guanzhu = in.readString();
		online = in.readString();
		kfId = in.readString();
		int picCount = in.readInt();
		if (picCount > 0) {
			showpic = new ArrayList<String>();
			for (int i = 0; i < picCount; i++) {
				showpic.add(in.readString());
			}
		} else {
			showpic = null;
		}
	}

}
