package cn.dressbook.ui.json;

import java.io.File;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.model.Wardrobe;
public class WardrobeJson {
	

	/** 解析形象列表，获取形象列表中的所有形象 */
	public ArrayList<Wardrobe> getWardrobeList(JSONArray jsonObj) {
		ArrayList<Wardrobe> wardrobeList = null;
		try {

			if (jsonObj != null && jsonObj.length() > 0) {

				wardrobeList = new ArrayList<Wardrobe>();

				for (int i = 0; i < jsonObj.length(); i++) {

					JSONObject wardrobeObj = jsonObj.optJSONObject(i);
					Wardrobe wardrobe = new Wardrobe();
					JSONObject head_verify = wardrobeObj
							.getJSONObject("head_verify");
					if (head_verify != null) {
						wardrobe.setHeadPosition(head_verify
								.optInt("headPosition"));
						wardrobe.setHeadScale(head_verify.optInt("headScale"));
						wardrobe.setBodyHeight(head_verify.optInt("bodyHeight"));
						wardrobe.setBodyWeight(head_verify.optInt("bodyWeight"));
					}
					if (wardrobeObj.optString("photo") != null
							&& !wardrobeObj.optString("photo").equals("")) {
						if (wardrobeObj.optString("photo_ext") != null
								&& !"".equals(wardrobeObj
										.optString("photo_ext"))) {
							if (!wardrobeObj.optString("photo").endsWith(
									"ubody.jpg")) {
								wardrobe.setPhoto(PathCommonDefines.SERVER_IMAGE_ADDRESS
										+ "/"
										+ wardrobeObj.optString("photo")
										+ "/"
										+ wardrobeObj.optInt("photo_ext")
										+ "/ubody.jpg");
								wardrobe.setHeadImage(PathCommonDefines.SERVER_IMAGE_ADDRESS
										+ "/"
										+ wardrobeObj.optString("photo")
										+ "/head_src.jpg");
							}
						} else {
							if (wardrobeObj.optString("photo").endsWith(
									"ubody.jpg")) {

								wardrobe.setPhoto(PathCommonDefines.SERVER_IMAGE_ADDRESS
										+ "/" + wardrobeObj.optString("photo"));
								wardrobe.setHeadImage(wardrobe.getPhoto()
										.replace("ubody.jpg", "head_src.jpg"));
							} else {
								wardrobe.setPhoto(PathCommonDefines.SERVER_IMAGE_ADDRESS
										+ "/"
										+ wardrobeObj.optString("photo")
										+ "/0s.png");
								wardrobe.setHeadImage(wardrobe.getPhoto()
										+ "/head_src.jpg");
							}
						}

					} else {
						// LogUtils.e("photo为空---------------------");
					}
					// LogUtils.e("形象照地址："+wardrobe.getHeadImage());
					wardrobe.setPath(wardrobeObj.optString("photo"));
					wardrobe.setWardrobeId(wardrobeObj.optInt("wardrobe_id"));
					wardrobe.setWardrobe_Time(wardrobeObj
							.optInt("wardrobe_time"));
					wardrobe.setName(wardrobeObj.optString("name"));
					wardrobe.setIsBiaoZhun(wardrobeObj.optInt("default_head"));
					// 审核状态
					wardrobe.setStatus(wardrobeObj.optInt("state"));
					// 相似头
					wardrobe.setSim_head(wardrobeObj.optString("simHead"));
					wardrobe.setSex(wardrobeObj.optInt("sex"));
					wardrobe.setShap(wardrobeObj.optInt("mid"));
					wardrobe.setMidName(wardrobeObj.optString("midName"));
					wardrobe.setAge(wardrobeObj.optInt("age"));
					wardrobe.setHeight(wardrobeObj.optInt("height"));
					wardrobe.setWeight(wardrobeObj.optInt("weight"));
					wardrobe.setWaistline(wardrobeObj.optInt("waistline"));
					wardrobe.setChest(wardrobeObj.optInt("chest"));
					wardrobe.setHipline(wardrobeObj.optInt("hipline"));
					wardrobe.setJingwei(wardrobeObj.optInt("jingwei"));
					wardrobe.setJiankuan(wardrobeObj.optInt("jiankuan"));
					wardrobe.setBichang(wardrobeObj.optInt("bichang"));
					wardrobe.setWanwei(wardrobeObj.optInt("wanwei"));
					wardrobe.setYaoweigao(wardrobeObj.optInt("yaoweigao"));

					wardrobe.setCreated(wardrobeObj.optInt("created"));
					wardrobe.setUpdated(wardrobeObj.optInt("updated"));
					wardrobe.setFirsted(wardrobeObj.optInt("firsted"));
					wardrobe.setViewCount(wardrobeObj.optInt("view_count"));
					wardrobe.setPicHeight(wardrobeObj.optInt("pic_width"));
					wardrobe.setPicWidth(wardrobeObj.optInt("pic_height"));
					wardrobe.setWardrobe_Time(wardrobeObj
							.optInt("wardrobe_time"));
					if (wardrobeObj.optInt("height") == 0) {
						wardrobe.setIsChange(1);// 1为没有设置
					}
					// Log.e(TAG, "解析出来的衣柜数据："+wardrobe);
					wardrobeList.add(wardrobe);

				}
			}

		} catch (Exception e) {
		}
		return wardrobeList;
	}

	/** 解析形象列表的第一个形象 */
	public Wardrobe getFirstWardrobe(JSONArray jsonObj) {
		Wardrobe wardrobe = new Wardrobe();
		try {

			if (jsonObj != null && jsonObj.length() > 0) {

				JSONObject wardrobeObj = jsonObj.optJSONObject(0);
				wardrobe.setWardrobeId(wardrobeObj.optInt("wardrobe_id"));
				wardrobe.setName(wardrobeObj.optString("name"));
			}

		} catch (Exception e) {
		}
		return wardrobe;
	}

	/** 解析形象列表，获取所需要的形象 */
	public Wardrobe getWardrobeFromList(JSONArray jsonObj, int mWardrobeId) {
		Wardrobe wardrobe = null;
		try {
			if (jsonObj != null && jsonObj.length() > 0) {
				for (int i = 0; i < jsonObj.length(); i++) {
					JSONObject wardrobeObj = jsonObj.optJSONObject(i);
					int wardrobe_id = wardrobeObj.optInt("wardrobe_id");
					wardrobe = new Wardrobe();
					if (wardrobe_id == mWardrobeId) {
						wardrobe.setWardrobeId(wardrobeObj
								.optInt("wardrobe_id"));
						wardrobe.setWardrobe_Time(wardrobeObj
								.optInt("wardrobe_time"));
						wardrobe.setName(wardrobeObj.optString("name"));
						if (wardrobeObj.optString("photo") != null
								&& !wardrobeObj.optString("photo").equals("")) {
							wardrobe.setPhoto("/"
									+ wardrobeObj.optString("photo"));
						}
						wardrobe.setSex(wardrobeObj.optInt("sex"));
						wardrobe.setShap(wardrobeObj.optInt("mid"));
						wardrobe.setAge(wardrobeObj.optInt("age"));
						wardrobe.setHeight(wardrobeObj.optInt("height"));
						wardrobe.setWeight(wardrobeObj.optInt("weight"));
						wardrobe.setWaistline(wardrobeObj.optInt("waistline"));
						wardrobe.setChest(wardrobeObj.optInt("chest"));
						wardrobe.setHipline(wardrobeObj.optInt("hipline"));
						wardrobe.setJingwei(wardrobeObj.optInt("jingwei"));
						wardrobe.setJiankuan(wardrobeObj.optInt("jiankuan"));
						wardrobe.setBichang(wardrobeObj.optInt("bichang"));
						wardrobe.setWanwei(wardrobeObj.optInt("wanwei"));
						wardrobe.setYaoweigao(wardrobeObj.optInt("yaoweigao"));

						wardrobe.setCreated(wardrobeObj.optInt("created"));
						wardrobe.setUpdated(wardrobeObj.optInt("updated"));
						wardrobe.setFirsted(wardrobeObj.optInt("firsted"));
						wardrobe.setViewCount(wardrobeObj.optInt("view_count"));
						wardrobe.setPicHeight(wardrobeObj.optInt("pic_width"));
						wardrobe.setPicWidth(wardrobeObj.optInt("pic_height"));
						wardrobe.setWardrobe_Time(wardrobeObj
								.optInt("wardrobe_time"));
						if (wardrobeObj.optInt("height") == 0) {
							wardrobe.setIsChange(1);// 1为没有设置
						}
						return wardrobe;
					}
				}
			}

		} catch (Exception e) {
		}
		return wardrobe;
	}

	public Wardrobe getWardrobe(JSONObject jsonObj) {
		Wardrobe wardrobe = null;
		try {

			if (jsonObj != null) {

				wardrobe = new Wardrobe();

				wardrobe.setWardrobeId(jsonObj.optInt("wardrobe_id"));
				wardrobe.setName(jsonObj.optString("name"));
				wardrobe.setSex(jsonObj.optInt("sex"));
				wardrobe.setPhoto(PathCommonDefines.SERVER_IMAGE_ADDRESS
						+ File.separator + jsonObj.optString("photo"));
				wardrobe.setBrand(jsonObj.optString("brand"));
				wardrobe.setHeight(jsonObj.optInt("height"));
				wardrobe.setWeight(jsonObj.optInt("weight"));
				wardrobe.setMinPrice(jsonObj.optInt("min_price"));
				wardrobe.setMaxPrice(jsonObj.optInt("max_price"));
				wardrobe.setJob(jsonObj.optString("job"));
				wardrobe.setOccasion(jsonObj.optString("occasion"));
				wardrobe.setWaistline(jsonObj.optInt("waistline"));
				wardrobe.setChest(jsonObj.optInt("chest"));
				wardrobe.setHipline(jsonObj.optInt("hipline"));
				wardrobe.setAgeGroup(jsonObj.optInt("agegroup"));

			}

		} catch (Exception e) {
		}
		return wardrobe;
	}

}
