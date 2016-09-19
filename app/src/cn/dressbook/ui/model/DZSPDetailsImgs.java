package cn.dressbook.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description 定制商品详情图片
 * @author 袁东华
 * @date 2016-1-22
 */
public class DZSPDetailsImgs implements Parcelable {

	/**
	 * 图片id
	 */
	public String id;
	/**
	 * 路径
	 */
	public String path;
	/**
	 * 后缀名
	 */
	public String suffix;
	/**
	 * 宽度
	 */
	public String width;
	/**
	 * 高度
	 */
	public String height;

	/**
	 * 未格式化时间
	 */
	public String addTime;
	/**
	 * 
	 */
	public String sort;
	/**
	 * 格式化时间
	 */
	public String addTimeShow;
	/**
	 * 图片地址
	 */
	public String url;

	public DZSPDetailsImgs() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getAddTimeShow() {
		return addTimeShow;
	}

	public void setAddTimeShow(String addTimeShow) {
		this.addTimeShow = addTimeShow;
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
		out.writeString(id);
		out.writeString(path);
		out.writeString(suffix);
		out.writeString(width);
		out.writeString(height);
		out.writeString(addTime);
		out.writeString(sort);
		out.writeString(addTimeShow);
		out.writeString(url);
	}

	public static final Creator<DZSPDetailsImgs> CREATOR = new Creator<DZSPDetailsImgs>() {
		@Override
		public DZSPDetailsImgs createFromParcel(Parcel in) {
			return new DZSPDetailsImgs(in);
		}

		@Override
		public DZSPDetailsImgs[] newArray(int size) {
			return new DZSPDetailsImgs[size];
		}
	};

	private DZSPDetailsImgs(Parcel in) {
		id = in.readString();
		path = in.readString();
		suffix = in.readString();
		width = in.readString();
		height = in.readString();
		addTime = in.readString();
		sort = in.readString();
		addTimeShow = in.readString();
		url = in.readString();
	}

}
