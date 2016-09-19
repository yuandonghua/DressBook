package cn.dressbook.ui.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description 衣柜
 * @author 袁东华
 * @date 2014-8-6 下午7:09:40
 */
public class Wardrobe implements Parcelable {
	public static final String WARDROBE_ID = "wardrobe_id";
	// 衣柜标识
	public static final String WARDROBE = "wardrobe";
	public static final String WARDROBE_LIST = "wardrobe_list";
	public static final String WARDROBE_NAME = "wardrobe_name";
	private String foot;

	public String getFoot() {
		return foot;
	}

	public void setFoot(String foot) {
		this.foot = foot;
	}

	private String birthday;

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public int user_id;
	public int wardrobeId;
	public String name;// 名称
	public int age;
	public int sex;// 0未知 1男2女
	public int shap;
	public String photo;// 照片路径
	public int height;// 身高 单位 厘米
	public int weight;// 体重 单位 公斤
	public int waistline;// 腰围
	public int chest;// 胸围
	public int hipline;// 臀围
	public int Jiankuan, Yaoweigao, Jingwei, Wanwei, Bichang;
	public String brand;// 品牌喜好多个品牌”,”分隔
	public int wardrobe_time;// 更新时间
	public int selected;
	public int status;
	private int isPaiZhao;
	// 是否是标准形象1是标准，0是非标准
	private int isBiaoZhun;
	// 头像
	private String mHeadImage;
	// 是否是客户端穿衣
	private int mClient;
	// 图片地址
	private String mPhotoext;
	private String mMidName;
	private String mMXZSex;
	// 相似头
	private String sim_head;
	/**
	 * 相对路径
	 */
	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 头像位置
	 */
	private int headPosition = 0;
	/**
	 * 头像缩放倍数
	 */
	private float headScale = 1;
	/**
	 * 身高
	 */
	private int bodyHeight = 0;
	/**
	 * 身体胖瘦
	 */
	private int bodyWeight = 0;

	public int getHeadPosition() {
		return headPosition;
	}

	public void setHeadPosition(int headPosition) {
		this.headPosition = headPosition;
	}

	public float getHeadScale() {
		return headScale;
	}

	public void setHeadScale(float headScale) {
		this.headScale = headScale;
	}

	public int getBodyHeight() {
		return bodyHeight;
	}

	public void setBodyHeight(int bodyHeight) {
		this.bodyHeight = bodyHeight;
	}

	public int getBodyWeight() {
		return bodyWeight;
	}

	public void setBodyWeight(int bodyWeight) {
		this.bodyWeight = bodyWeight;
	}

	public String getSim_head() {
		return sim_head;
	}

	public void setSim_head(String sim_head) {
		this.sim_head = sim_head;
	}

	public String getMXZSex() {
		return mMXZSex;
	}

	public void setMXZSex(String mMXZSex) {
		this.mMXZSex = mMXZSex;
	}

	/**
	 * @return the mMidName
	 */
	public String getMidName() {
		return mMidName;
	}

	/**
	 * @param mMidName
	 *            the mMidName to set
	 */
	public void setMidName(String mMidName) {
		this.mMidName = mMidName;
	}

	/**
	 * @return the mPhotoext
	 */
	public String getPhotoext() {
		return mPhotoext;
	}

	/**
	 * @param mPhotoext
	 *            the mPhotoext to set
	 */
	public void setPhotoext(String mPhotoext) {
		this.mPhotoext = mPhotoext;
	}

	/**
	 * @return the mClient
	 */
	public int getmClient() {
		return mClient;
	}

	/**
	 * @param mClient
	 *            the mClient to set
	 */
	public void setmClient(int mClient) {
		this.mClient = mClient;
	}

	/**
	 * @return the mHeadImage
	 */
	public String getHeadImage() {
		return mHeadImage;
	}

	/**
	 * @param mHeadImage
	 *            the mHeadImage to set
	 */
	public void setHeadImage(String mHeadImage) {
		this.mHeadImage = mHeadImage;
	}

	/**
	 * @return the isBiaoZhun
	 */
	public int getIsBiaoZhun() {
		return isBiaoZhun;
	}

	/**
	 * @param isBiaoZhun
	 *            the isBiaoZhun to set
	 */
	public void setIsBiaoZhun(int isBiaoZhun) {
		this.isBiaoZhun = isBiaoZhun;
	}

	/**
	 * @return the isPaiZhao
	 */
	public int getIsPaiZhao() {
		return isPaiZhao;
	}

	/**
	 * @param isPaiZhao
	 *            the isPaiZhao to set
	 */
	public void setIsPaiZhao(int isPaiZhao) {
		this.isPaiZhao = isPaiZhao;
	}

	// 单品ID
	public ArrayList<Integer> danPin_id;

	public ArrayList<Integer> getDanPin_id() {
		return danPin_id;
	}

	public void setDanPin_id(ArrayList<Integer> danPin_id) {
		this.danPin_id = danPin_id;
	}

	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int minPrice;// 期望的价格区间最小 单位 元
	public int maxPrice;// 期望的价格区间最大 单位 元
	public String job;// 职业
	public String occasion;// 场合
	public int picWidth;// 图片宽
	public int picHeight;// 图片高度
	public int firsted;// 0可以删除 1不可以删除
	public int created;// 是否已经有搭配方案0 无 1有
	public int updated;// 是否有新修改
	public int viewCount;
	public String decription;
	public int ageGroup;
	public int isChange;
	private int tuiChang;

	/**
	 * @return the tuiChang
	 */
	public int getTuiChang() {
		return tuiChang;
	}

	/**
	 * @param tuiChang
	 *            the tuiChang to set
	 */
	public void setTuiChang(int tuiChang) {
		this.tuiChang = tuiChang;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getJiankuan() {
		return Jiankuan;
	}

	public void setJiankuan(int jiankuan) {
		Jiankuan = jiankuan;
	}

	public int getYaoweigao() {
		return Yaoweigao;
	}

	public void setYaoweigao(int yaoweigao) {
		Yaoweigao = yaoweigao;
	}

	public int getJingwei() {
		return Jingwei;
	}

	public void setJingwei(int jingwei) {
		Jingwei = jingwei;
	}

	public int getWanwei() {
		return Wanwei;
	}

	public void setWanwei(int wanwei) {
		Wanwei = wanwei;
	}

	public int getBichang() {
		return Bichang;
	}

	public void setBichang(int bichang) {
		Bichang = bichang;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getShap() {
		return shap;
	}

	public void setShap(int shap) {
		this.shap = shap;
	}

	public int getIsChange() {
		return isChange;
	}

	public void setIsChange(int isChange) {
		this.isChange = isChange;
	}

	public int getAgeGroup() {
		return ageGroup;
	}

	public void setAgeGroup(int ageGroup) {
		this.ageGroup = ageGroup;
	}

	public int getWaistline() {
		return waistline;
	}

	public void setWaistline(int waistline) {
		this.waistline = waistline;
	}

	public int getChest() {
		return chest;
	}

	public void setChest(int chest) {
		this.chest = chest;
	}

	public int getHipline() {
		return hipline;
	}

	/**
	 * 臀围
	 * */
	public void setHipline(int hipline) {
		this.hipline = hipline;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public String getDecription() {
		return decription;
	}

	public void setDecription(String decription) {
		this.decription = decription;
	}

	public int getCreated() {
		return created;
	}

	public void setCreated(int created) {
		this.created = created;
	}

	public int getUpdated() {
		return updated;
	}

	public void setUpdated(int updated) {
		this.updated = updated;
	}

	public int getFirsted() {
		return firsted;
	}

	public void setFirsted(int firsted) {
		this.firsted = firsted;
	}

	public int getPicWidth() {
		return picWidth;
	}

	public void setPicWidth(int picWidth) {
		this.picWidth = picWidth;
	}

	public int getPicHeight() {
		return picHeight;
	}

	public void setPicHeight(int picHeight) {
		this.picHeight = picHeight;
	}

	public int getWardrobeId() {
		return wardrobeId;
	}

	public void setWardrobeId(int wardrobeId) {
		this.wardrobeId = wardrobeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		if (brand == null) {
			brand = "";
		}
		this.brand = brand;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}

	public int getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getOccasion() {
		return occasion;
	}

	public void setOccasion(String occasion) {
		this.occasion = occasion;
	}

	public Wardrobe() {

	}

	public Wardrobe(String id, int id2, String photo) {
		this.mMXZSex = id;
		this.wardrobeId = id2;
		this.photo = photo;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {

		out.writeString(foot);
		out.writeString(birthday);
		out.writeString(sim_head);
		out.writeString(mMXZSex);
		out.writeString(mMidName);
		out.writeInt(tuiChang);
		out.writeString(mPhotoext);
		out.writeInt(mClient);
		out.writeString(mHeadImage);
		out.writeInt(isBiaoZhun);
		out.writeInt(isPaiZhao);
		out.writeInt(status);
		out.writeInt(wardrobeId);
		out.writeString(name);
		out.writeInt(sex);
		out.writeString(photo);
		out.writeString(brand);
		out.writeInt(height);
		out.writeInt(weight);
		out.writeInt(waistline);
		out.writeInt(chest);
		out.writeInt(hipline);
		out.writeInt(minPrice);
		out.writeInt(maxPrice);
		out.writeString(job);
		out.writeString(occasion);
		out.writeInt(picWidth);
		out.writeInt(picHeight);
		out.writeInt(firsted);
		out.writeInt(created);
		out.writeInt(updated);
		out.writeInt(viewCount);
		out.writeString(decription);
		out.writeInt(ageGroup);
		out.writeInt(isChange);
		out.writeInt(wardrobe_time);
		out.writeInt(age);
		out.writeInt(Jiankuan);
		out.writeInt(Yaoweigao);
		out.writeInt(Jingwei);
		out.writeInt(Wanwei);
		out.writeInt(Bichang);
		out.writeInt(user_id);
		int num = 0;
		if (danPin_id != null && danPin_id.size() > 0) {
			num = danPin_id.size();
			out.writeInt(num);
			for (int i = 0; i < num; i++) {
				out.writeInt(danPin_id.get(i));
			}
		} else {
			out.writeInt(num);
		}

	}

	public static final Creator<Wardrobe> CREATOR = new Creator<Wardrobe>() {
		@Override
		public Wardrobe createFromParcel(Parcel in) {
			return new Wardrobe(in);
		}

		@Override
		public Wardrobe[] newArray(int size) {
			return new Wardrobe[size];
		}
	};

	private Wardrobe(Parcel in) {
		foot = in.readString();
		birthday = in.readString();
		sim_head = in.readString();
		mMXZSex = in.readString();
		mMidName = in.readString();
		tuiChang = in.readInt();
		mPhotoext = in.readString();
		mClient = in.readInt();
		mHeadImage = in.readString();
		isBiaoZhun = in.readInt();
		isPaiZhao = in.readInt();
		status = in.readInt();
		wardrobeId = in.readInt();
		name = in.readString();
		sex = in.readInt();
		photo = in.readString();
		brand = in.readString();
		height = in.readInt();
		weight = in.readInt();
		waistline = in.readInt();
		chest = in.readInt();
		hipline = in.readInt();
		minPrice = in.readInt();
		maxPrice = in.readInt();
		job = in.readString();
		occasion = in.readString();
		picWidth = in.readInt();
		picHeight = in.readInt();
		firsted = in.readInt();
		created = in.readInt();
		updated = in.readInt();
		viewCount = in.readInt();
		decription = in.readString();
		ageGroup = in.readInt();
		isChange = in.readInt();
		wardrobe_time = in.readInt();
		age = in.readInt();
		Jiankuan = in.readInt();
		Yaoweigao = in.readInt();
		Jingwei = in.readInt();
		Wanwei = in.readInt();
		Bichang = in.readInt();
		user_id = in.readInt();
		int num = in.readInt();
		danPin_id = new ArrayList<Integer>();
		if (num > 0) {
			for (int i = 0; i < num; i++) {
				danPin_id.add(in.readInt());
			}
		} else {
			danPin_id = null;
		}
	}

	public void setWardrobe_Time(int optInt) {
		// TODO Auto-generated method stub
		wardrobe_time = optInt;

	}

	public int getWardrobe_Time() {
		// TODO Auto-generated method stub
		return wardrobe_time;
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return super.equals(o);
	}

}