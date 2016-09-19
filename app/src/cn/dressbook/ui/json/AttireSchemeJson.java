package cn.dressbook.ui.json;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;

import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.model.AlbcBean;
import cn.dressbook.ui.model.AttireScheme;

/**
 * @description: 服装方案解析
 * @author:袁东华
 * @time:2015-9-24上午11:07:17
 */
public class AttireSchemeJson {
	private static AttireSchemeJson mAttireSchemeJson;

	private AttireSchemeJson() {
	}

	public static AttireSchemeJson getInstance() {
		return mAttireSchemeJson == null ? new AttireSchemeJson()
				: mAttireSchemeJson;
	}

	/** 解析穿衣典方案 */
	public ArrayList<AttireScheme> getAttireSchemeList(JSONArray jsonObj,
			String ids, int status) {
		ArrayList<AttireScheme> attireSchemeList = null;
		try {
			ArrayList<String> attireIDList = null;
			if (ids != null && !"".equals(ids)) {
				attireIDList = new ArrayList<String>();

				if (ids != null) {
					String[] idsArr = ids.split(",");
					for (int i = 0; i < idsArr.length; i++) {
						attireIDList.add(idsArr[i]);
					}

				}
			} else {
			}
			if (jsonObj != null && jsonObj.length() > 0) {

				attireSchemeList = new ArrayList<AttireScheme>();

				for (int i = 0; i < jsonObj.length(); i++) {
					boolean flag = false;
					JSONObject attireSchemeObj = jsonObj.optJSONObject(i);
					if (attireIDList != null && attireIDList.size() > 0) {
						for (int m = 0; m < attireIDList.size(); m++) {
							if (attireSchemeObj.optString("attire_id").equals(
									attireIDList.get(m))) {
								flag = true;
							}
						}
					}
					if (flag) {
						// LogUtils.e("方案ID为"
						// + attireSchemeObj.optString("attire_id")
						// + "的方案已经删除----------------");
					} else {
						// LogUtils.e("方案ID为"
						// + attireSchemeObj.optString("attire_id"));
						AttireScheme attireScheme = new AttireScheme();
						attireScheme.setAttireId(attireSchemeObj
								.optString("attire_id"));
						attireScheme.setAttire_id_real(attireSchemeObj
								.optString("attire_id_real"));
						attireScheme.setIsBiaoZhun(attireSchemeObj
								.optInt("default_head"));
						if (attireSchemeObj.optString("pubtime") != null
								&& !"".equals(attireSchemeObj
										.optString("pubtime"))) {
							// LogUtils.e("方案发布时间："
							// + attireSchemeObj.optString("pubtime"));
							String[] strs = attireSchemeObj
									.optString("pubtime").split("-");
							attireScheme.setPubtime(strs[1] + "/" + strs[2]);

						}
						// 获取相似款
						JSONObject dress_siminf = attireSchemeObj
								.getJSONObject("dress_siminf");
						if (dress_siminf != null) {
							Iterator<String> it = dress_siminf.keys();
							while (it.hasNext()) {
								String key = it.next();
								if (key != null && !"".equals(key)) {
									if (key.contains("1")) {
										// LogUtils.e("上衣相似款key:" + key +
										// "  上衣相似款value:"
										// + dress_siminf.getString(key));
										attireScheme.setSY_KEY(key);
										attireScheme.setSY_VALUE(dress_siminf
												.getString(key));
									} else if (key.contains("2")) {
										attireScheme.setKZ_KEY(key);
										attireScheme.setKZ_VALUE(dress_siminf
												.getString(key));
										// LogUtils.e("裤子相似款key:" + key +
										// "  裤子相似款value:"
										// + dress_siminf.getString(key));
									} else if (key.contains("3")) {
										attireScheme.setXZ_KEY(key);
										attireScheme.setXZ_VALUE(dress_siminf
												.getString(key));
										// LogUtils.e("鞋子相似款key:" + key +
										// "  鞋子相似款value:"
										// + dress_siminf.getString(key));
									}
								}

							}

						}
						// 销售价
						attireScheme.setShop_price(attireSchemeObj
								.optInt("shop_price"));
						// 市场价
						attireScheme.setMarketPrice(attireSchemeObj
								.optString("market_price"));
						// 衣服类型
						attireScheme.setTypeString(attireSchemeObj
								.optString("type_str"));

						if (attireSchemeObj.optString("modpic") != null
								&& !"".equals(attireSchemeObj
										.optString("modpic"))) {
							if (attireSchemeObj.optString("modpic").endsWith(
									"1.png")) {

								attireScheme
										.setModPic(PathCommonDefines.SERVER_IMAGE_ADDRESS
												+ "/"
												+ attireSchemeObj
														.optString("modpic"));
							} else {
								attireScheme
										.setModPic(PathCommonDefines.SERVER_IMAGE_ADDRESS
												+ "/"
												+ attireSchemeObj
														.optString("modpic")
												+ "/1.png");
							}
						} else {

						}
						if (attireSchemeObj.optString("modpic_jjh") != null
								&& !"".equals(attireSchemeObj
										.optString("modpic_jjh"))) {
							if (attireSchemeObj.optString("modpic_jjh")
									.endsWith("1.png")) {

								attireScheme
										.setModpic_jjh(PathCommonDefines.SERVER_IMAGE_ADDRESS
												+ "/"
												+ attireSchemeObj
														.optString("modpic_jjh"));
							} else {
								attireScheme
										.setModpic_jjh(PathCommonDefines.SERVER_IMAGE_ADDRESS
												+ "/"
												+ attireSchemeObj
														.optString("modpic_jjh")
												+ "/1.png");
							}
						} else {

						}
						// Log.e(TAG, "解析出来的模特：" +
						// attireScheme.getModPic());
						attireScheme.setIsView(attireSchemeObj
								.optInt("is_view"));
						attireScheme.setMid(attireSchemeObj.optInt("mid"));
						attireScheme.setWardrobeId(attireSchemeObj
								.optInt("wardrobe_id"));
						attireScheme.setStatus(status);
						attireScheme.setModFrom(attireSchemeObj
								.optString("modfrom"));
						attireScheme.setTbkLink(attireSchemeObj
								.optString("tbklink"));
						JSONArray picObj = attireSchemeObj
								.optJSONArray("picture");
						ArrayList<String> picPathList = new ArrayList<String>();
						if (picObj != null) {

							for (int j = 0; j < picObj.length(); j++) {

								String tempUrl = picObj.optString(j);

								String picPath = null;
								if (tempUrl != null && !"".equals(tempUrl)) {

									if (tempUrl.startsWith("/")) {

										picPath = PathCommonDefines.SERVER_IMAGE_ADDRESS
												+ tempUrl;

									} else {

										picPath = PathCommonDefines.SERVER_IMAGE_ADDRESS
												+ File.separator + tempUrl;
									}
								}

								picPathList.add(picPath);
							}
						}

						attireScheme.setPicture(picPathList);

						attireScheme.setPicWidth(attireSchemeObj
								.optInt("pic_width"));
						attireScheme.setPicHeight(attireSchemeObj
								.optInt("pic_height"));
						attireScheme.setPictureCount(attireSchemeObj
								.optInt("picture_count"));
						attireScheme.setAdviserId(attireSchemeObj
								.optString("adviser_id"));
						attireScheme
								.setAutographPath(PathCommonDefines.SERVER_IMAGE_ADDRESS
										+ File.separator
										+ attireSchemeObj
												.optString("autograph_path"));
						attireScheme
								.setAutographLogo(PathCommonDefines.SERVER_IMAGE_ADDRESS
										+ File.separator
										+ attireSchemeObj
												.optString("autograph_Logo"));
						attireScheme.setDesc(attireSchemeObj
								.optString("attire_desc"));
						attireScheme.setKeyWord(attireSchemeObj
								.optString("sale_point"));
						attireScheme.setAdviserName(attireSchemeObj
								.optString("adviser_name"));
						attireScheme.setMobile(attireSchemeObj
								.optString("mobile"));
						attireScheme.setMessageMobile(attireSchemeObj
								.optString("message_mobile"));
						if (attireSchemeObj.optString("thume") != null
								&& !"".equals(attireSchemeObj
										.optString("thume"))) {

							// 小图片
							attireScheme
									.setThume(PathCommonDefines.SERVER_IMAGE_ADDRESS
											+ File.separator
											+ attireSchemeObj
													.optString("thume"));
						} else {
							attireScheme.setThume(null);
						}
						// 获取方案发布日期
						attireScheme.setAttireTime(attireSchemeObj
								.optString("attiretime"));
						// 风格
						attireScheme.setAttire_style(attireSchemeObj
								.optString("attire_style"));
						// 场合
						attireScheme.setAttire_occasion(attireSchemeObj
								.optString("attire_occasion"));
						// 价格
						attireScheme.setShop_price(attireSchemeObj
								.optInt("shop_price"));
						attireScheme.setDemo(attireSchemeObj.optString("demo"));
						attireSchemeList.add(attireScheme);
					}
				}

			}

		} catch (Exception e) {
		}
		return attireSchemeList;
	}

	/**
	 * 解析创建形象返回的模特json
	 * 
	 * @throws JSONException
	 */
	public ArrayList<AttireScheme> getAttireSchemeList(String json)
			throws JSONException {
		if (json == null) {
			return null;
		}
		JSONObject obj = new JSONObject(json);
		if (obj == null) {
			return null;
		}
		JSONArray arry = obj.getJSONArray("info");
		if (arry == null || arry.length() == 0) {
			return null;
		}
		/**
		 * 解析标签，把标签存入每一个info中
		 */
		JSONObject objKeyMap = obj.getJSONObject("keys_map");
		JSONArray arrayKeyMap = objKeyMap.getJSONArray("keys_category");
		ArrayList<String> keymapList = new ArrayList<String>();
		if (arrayKeyMap != null) {
			for (int k = 0; k < arrayKeyMap.length(); k++) {
				JSONObject objName = arrayKeyMap.getJSONObject(k);

				keymapList.add(objName.optString("name"));
			}

		}
		ArrayList<AttireScheme> attireSchemeList = null;
		try {

			attireSchemeList = new ArrayList<AttireScheme>();

			for (int i = 0; i < arry.length(); i++) {

				JSONObject attireSchemeObj = arry.optJSONObject(i);
				AttireScheme attireScheme = new AttireScheme();
				// 存入标签
				attireScheme.setKey_map(keymapList);
				// 商品的类型(是否是广告商品)
				attireScheme.setFor_vip(attireSchemeObj.optString("for_vip"));
				// 广告图
				attireScheme.setAd_pic(PathCommonDefines.SERVER_IMAGE_ADDRESS
						+ attireSchemeObj.optString("ad_pic"));
				attireScheme.setYq_value(attireSchemeObj.optString("yq_value"));
				attireScheme.setDesc(attireSchemeObj.optString("adesc"));
				attireScheme.setCan_try(attireSchemeObj.optString("can_try"));
				attireScheme.setAttire_id_real(attireSchemeObj
						.optString("attire_id_real"));
				attireScheme.setRootCatName(attireSchemeObj
						.optString("root_cat_name"));
				attireScheme
						.setAttireId(attireSchemeObj.optString("attire_id"));
				attireScheme.setPubtime(attireSchemeObj.optString("pubtime"));
				attireScheme.setIsView(attireSchemeObj.optInt("is_view"));
				attireScheme.setMid(attireSchemeObj.optInt("mid"));
				attireScheme.setWardrobeId(attireSchemeObj
						.optInt("wardrobe_id"));
				attireScheme.setWardrobeName(attireSchemeObj
						.optString("wardrobe_name"));
				if (attireSchemeObj.optString("modpic") != null
						&& !"".equals(attireSchemeObj.optString("modpic"))) {
					if (attireSchemeObj.optString("modpic").endsWith("1.png")) {

						attireScheme
								.setModPic(PathCommonDefines.SERVER_IMAGE_ADDRESS
										+ "/"
										+ attireSchemeObj.optString("modpic"));
					} else {
						attireScheme
								.setModPic(PathCommonDefines.SERVER_IMAGE_ADDRESS
										+ "/"
										+ attireSchemeObj.optString("modpic")
										+ "/1.png");
					}
				} else {
					attireScheme.setModPic(null);
				}
				attireScheme.setModFrom(attireSchemeObj.optString("modfrom"));
				attireScheme.setTbkLink(attireSchemeObj.optString("tbklink"));
				attireScheme.setBigImagePath(attireSchemeObj
						.optString("bigImage"));

				attireScheme.setPicWidth(attireSchemeObj.optInt("pic_width"));
				attireScheme.setPicHeight(attireSchemeObj.optInt("pic_height"));
				attireScheme.setPictureCount(attireSchemeObj
						.optInt("picture_count"));
				attireScheme.setAdviserId(attireSchemeObj
						.optString("adviser_id"));
				attireScheme
						.setAutographPath(PathCommonDefines.SERVER_IMAGE_ADDRESS
								+ File.separator
								+ attireSchemeObj.optString("autograph_path"));
				attireScheme
						.setAutographLogo(PathCommonDefines.SERVER_IMAGE_ADDRESS
								+ File.separator
								+ attireSchemeObj.optString("autograph_Logo"));
				attireScheme
						.setKeyWord(attireSchemeObj.optString("sale_point"));
				attireScheme.setAdviserName(attireSchemeObj
						.optString("adviser_name"));
				attireScheme.setMobile(attireSchemeObj.optString("mobile"));
				attireScheme.setMessageMobile(attireSchemeObj
						.optString("message_mobile"));
				// 获取类型
				attireScheme.setTypeString(attireSchemeObj
						.optString("type_str"));
				// 小图片
				attireScheme.setThume(PathCommonDefines.SERVER_IMAGE_ADDRESS
						+ attireSchemeObj.optString("thume"));
				// 获取方案发布日期
				attireScheme.setAttireTime(attireSchemeObj
						.optString("attiretime"));
				// 风格
				attireScheme.setAttire_style(attireSchemeObj
						.optString("attire_style"));
				// 场合
				attireScheme.setAttire_occasion(attireSchemeObj
						.optString("attire_occasion"));
				// 价格
				attireScheme
						.setShop_price(attireSchemeObj.optInt("shop_price"));
				// 淘宝商品ID
				attireScheme.setOpeniid(attireSchemeObj.optString("openiid"));
				attireSchemeList.add(attireScheme);
			}

		} catch (Exception e) {
		}
		return attireSchemeList;
	}

	/**
	 * @description 解析阿里百川商品列表
	 */
	public ArrayList<AttireScheme> analysisALBCList(JSONArray arry)
			throws JSONException {

		ArrayList<AttireScheme> attireSchemeList = null;
		try {

			attireSchemeList = new ArrayList<AttireScheme>();

			for (int i = 0; i < arry.length(); i++) {

				JSONObject attireSchemeObj = arry.optJSONObject(i);
				AttireScheme attireScheme = new AttireScheme();
				attireScheme.setOpeniid(attireSchemeObj
						.optString("auction_id"));// 阿里百川商品编号
				attireScheme.setModPic(PathCommonDefines.ALBC_IMAGE
						+ attireSchemeObj.optString("pic_url"));// 商品图片地址相对地址
				attireScheme.setShop_price(attireSchemeObj.optInt("price"));// 商品价格
				attireScheme.setRule_id(attireSchemeObj.optString("rule_id"));// 商品池id
				attireScheme.setSeller_id(attireSchemeObj
						.optString("seller_id"));// 卖家编号
				attireScheme.setSeller_nick(attireSchemeObj
						.optString("seller_nick"));// 卖家昵称
				attireScheme.setShop_type(attireSchemeObj
						.optString("shop_type"));// 店铺类型,0淘宝店铺,1天猫店铺
				attireScheme.setTitle(attireSchemeObj
						.optString("title"));// 商品标题（转码后的）
				attireScheme.setTk_item(attireSchemeObj
						.optString("tk_item"));// 是否是淘客商品,0不是,1是
				attireSchemeList.add(attireScheme);
			}

		} catch (Exception e) {
		}
		return attireSchemeList;
	}

	/**
	 * 解析搜索品牌与服装
	 * 
	 * @throws JSONException
	 */
	public ArrayList<AlbcBean> getAlbcFind(String json) throws JSONException {
		ArrayList<AlbcBean> list = new ArrayList<AlbcBean>();
		if (json == null) {
			return null;
		}
		JSONObject obj = new JSONObject(json);
		if (obj == null) {
			return null;
		}
		JSONArray array = obj.getJSONArray("info");
		if (array == null) {
			return null;
		}
		AlbcBean albc = null;
		for (int i = 0; i < array.length(); i++) {
			albc = new AlbcBean();
			JSONObject jsonObj = array.getJSONObject(i);
			albc.setAuction_id(jsonObj.optString("auction_id"));
			albc.setPic_url(jsonObj.optString("pic_url"));
			albc.setPrice(jsonObj.optString("price"));
			albc.setRule_id(jsonObj.optString("rule_id"));
			albc.setSeller_id(jsonObj.optString("seller_id"));
			albc.setSeller_nick(jsonObj.optString("seller_nick"));
			albc.setShop_type(jsonObj.optString("shop_type"));
			albc.setTitle(jsonObj.optString("title"));
			albc.setTk_item(jsonObj.optString("tk_item"));
			list.add(albc);
			albc = null;

		}
		return list;
	}
	/*
	 * public ArrayList<AttireScheme> getAttireSchemeFind(String json) throws
	 * JSONException { if (json == null) { return null; } JSONObject obj = new
	 * JSONObject(json); if (obj == null) { return null; } JSONArray arry =
	 * obj.getJSONArray("info"); if (arry == null || arry.length() == 0) {
	 * return null; }
	 *//**
	 * 解析标签，把标签存入每一个info中
	 */
	/*
	 * ArrayList<AttireScheme> attireSchemeList = null; try {
	 * 
	 * attireSchemeList = new ArrayList<AttireScheme>();
	 * 
	 * for (int i = 0; i < arry.length(); i++) {
	 * 
	 * JSONObject attireSchemeObj = arry.optJSONObject(i); AttireScheme
	 * attireScheme = new AttireScheme(); // 商品的类型(是否是广告商品)
	 * attireScheme.setFor_vip(attireSchemeObj.optString("for_vip")); // 广告图
	 * attireScheme.setAd_pic(PathCommonDefines.SERVER_IMAGE_ADDRESS +
	 * attireSchemeObj.optString("ad_pic"));
	 * attireScheme.setYq_value(attireSchemeObj.optString("yq_value"));
	 * attireScheme.setDesc(attireSchemeObj.optString("adesc"));
	 * attireScheme.setCan_try(attireSchemeObj.optString("can_try"));
	 * attireScheme.setAttire_id_real(attireSchemeObj
	 * .optString("attire_id_real"));
	 * attireScheme.setRootCatName(attireSchemeObj .optString("root_cat_name"));
	 * attireScheme .setAttireId(attireSchemeObj.optString("attire_id"));
	 * attireScheme.setPubtime(attireSchemeObj.optString("pubtime"));
	 * attireScheme.setIsView(attireSchemeObj.optInt("is_view"));
	 * attireScheme.setMid(attireSchemeObj.optInt("mid"));
	 * attireScheme.setWardrobeId(attireSchemeObj .optInt("wardrobe_id"));
	 * attireScheme.setWardrobeName(attireSchemeObj
	 * .optString("wardrobe_name")); JSONArray picObj =
	 * attireSchemeObj.optJSONArray("picture"); if (picObj != null) {
	 * ArrayList<String> imageuri = new ArrayList<String>(); for (int j = 0; j <
	 * picObj.length(); j++) {
	 * 
	 * String tempUrl = PathCommonDefines.SERVER_IMAGE_ADDRESS +
	 * picObj.optString(j);
	 * 
	 * imageuri.add(tempUrl); } attireScheme.setPicture(imageuri); } if
	 * (attireSchemeObj.optString("modpic") != null &&
	 * !"".equals(attireSchemeObj.optString("modpic"))) { if
	 * (attireSchemeObj.optString("modpic").endsWith("1.png")) {
	 * 
	 * attireScheme .setModPic(PathCommonDefines.SERVER_IMAGE_ADDRESS + "/" +
	 * attireSchemeObj.optString("modpic")); } else { attireScheme
	 * .setModPic(PathCommonDefines.SERVER_IMAGE_ADDRESS + "/" +
	 * attireSchemeObj.optString("modpic") + "/1.png"); } } else {
	 * attireScheme.setModPic(null); }
	 * attireScheme.setModFrom(attireSchemeObj.optString("modfrom"));
	 * attireScheme.setTbkLink(attireSchemeObj.optString("tbklink"));
	 * attireScheme.setBigImagePath(attireSchemeObj .optString("bigImage"));
	 * 
	 * attireScheme.setPicWidth(attireSchemeObj.optInt("pic_width"));
	 * attireScheme.setPicHeight(attireSchemeObj.optInt("pic_height"));
	 * attireScheme.setPictureCount(attireSchemeObj .optInt("picture_count"));
	 * attireScheme.setAdviserId(attireSchemeObj .optString("adviser_id"));
	 * attireScheme .setAutographPath(PathCommonDefines.SERVER_IMAGE_ADDRESS +
	 * File.separator + attireSchemeObj.optString("autograph_path"));
	 * attireScheme .setAutographLogo(PathCommonDefines.SERVER_IMAGE_ADDRESS +
	 * File.separator + attireSchemeObj.optString("autograph_Logo"));
	 * attireScheme .setKeyWord(attireSchemeObj.optString("sale_point"));
	 * attireScheme.setAdviserName(attireSchemeObj .optString("adviser_name"));
	 * attireScheme.setMobile(attireSchemeObj.optString("mobile"));
	 * attireScheme.setMessageMobile(attireSchemeObj
	 * .optString("message_mobile")); // 获取类型
	 * attireScheme.setTypeString(attireSchemeObj .optString("type_str")); //
	 * 小图片 获取picture的第一个值
	 * attireScheme.setThume(PathCommonDefines.SERVER_IMAGE_ADDRESS +
	 * attireSchemeObj.optString("thume"));
	 * UtilLog.i("解析后的路径:"+attireScheme.getThume()); // 获取方案发布日期
	 * attireScheme.setAttireTime(attireSchemeObj .optString("attiretime")); //
	 * 风格 attireScheme.setAttire_style(attireSchemeObj
	 * .optString("attire_style")); // 场合
	 * attireScheme.setAttire_occasion(attireSchemeObj
	 * .optString("attire_occasion")); // 价格 attireScheme
	 * .setShop_price(attireSchemeObj.optInt("shop_price")); // 淘宝商品ID
	 * attireScheme.setOpeniid(attireSchemeObj.optString("openiid"));
	 * attireSchemeList.add(attireScheme); }
	 * 
	 * } catch (Exception e) { } return attireSchemeList; }
	 */
}
