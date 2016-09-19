package cn.dressbook.ui.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.model.AttireScheme;
import cn.dressbook.ui.model.BuyerResponse;
import cn.dressbook.ui.model.Requirement;

public class RequirementJson {
	private static RequirementJson mMaiShouTuiJianJson;

	private RequirementJson() {

	}

	public static RequirementJson getInstance() {
		if (mMaiShouTuiJianJson == null) {
			mMaiShouTuiJianJson = new RequirementJson();
		}
		return mMaiShouTuiJianJson;
	}

	/**
	 * @description:解析买手推荐列表
	 */
	public ArrayList<AttireScheme> analysisMSTJList(JSONArray info) {
		ArrayList<AttireScheme> attireSchemeList = new ArrayList<AttireScheme>();
		try {
			for (int i = 0; i < info.length(); i++) {
				AttireScheme attireScheme = new AttireScheme();
				JSONObject attireSchemeObj = info.optJSONObject(i);
				//判断是否是百川商品
				if ("3".equals(attireSchemeObj.optString("modfrom"))) {
					//是百川商品
					attireScheme.setReferrer(attireSchemeObj.optString("referrer"));
					attireScheme.setAuction_id(attireSchemeObj.optString("auction_id"));
					attireScheme.setId(attireSchemeObj.optString("id"));
					attireScheme.setPic_url(attireSchemeObj.optString("pic_url"));
					attireScheme.setPrice(attireSchemeObj.optString("price"));
					attireScheme.setModFrom( attireSchemeObj.optString("modfrom"));
					attireScheme.setTitle(attireSchemeObj.optString("title"));
					
				} else {
					//不是百川商品
					attireScheme.setReferrer(attireSchemeObj.optString("referrer"));
					// 试穿
					attireScheme.setCan_try(attireSchemeObj.optString("can_try"));
					// 小图片
					attireScheme.setThume(PathCommonDefines.SERVER_IMAGE_ADDRESS
							+ attireSchemeObj.optString("thume"));
					if (!attireSchemeObj.isNull("picture")) {

						JSONArray picObj = attireSchemeObj.optJSONArray("picture");
						ArrayList<String> picPathList = new ArrayList<String>();
						if (picObj != null) {

							for (int j = 0; j < picObj.length(); j++) {

								picPathList
										.add(PathCommonDefines.SERVER_IMAGE_ADDRESS
												+ picObj.optString(j));
							}
						}

						attireScheme.setPicture(picPathList);
					}
					if (!attireSchemeObj.isNull("modpic")) {
						if (attireSchemeObj.optString("modpic").endsWith("1.png")) {

							attireScheme
									.setModPic(PathCommonDefines.SERVER_IMAGE_ADDRESS
											+ attireSchemeObj.optString("modpic"));
						} else {
							attireScheme
									.setModPic(PathCommonDefines.SERVER_IMAGE_ADDRESS
											+ attireSchemeObj.optString("modpic")
											+ "/1.png");
						}
					} else {
						attireScheme.setModPic(null);
					}
					// 体型
					attireScheme.setMid(attireSchemeObj.optInt("mid"));
					// 方案id
					attireScheme
							.setAttireId(attireSchemeObj.optString("attire_id"));
					attireScheme.setPictureCount(attireSchemeObj
							.optInt("picture_count"));
					// 顾问师id
					attireScheme.setAdviserId(attireSchemeObj
							.optString("adviser_id"));
					// 顾问师昵称
					attireScheme.setAdviserName(attireSchemeObj
							.optString("adviser_name"));
					// 顾问师头像
					attireScheme
							.setAutographPath(PathCommonDefines.SERVER_IMAGE_ADDRESS
									+ attireSchemeObj.optString("autograph_path"));
					// 商品描述
					attireScheme.setDesc(attireSchemeObj.optString("adesc"));
					// 销售价格
					attireScheme
							.setShop_price(attireSchemeObj.optInt("shop_price"));
					// 衣扣
					attireScheme.setYq_value(attireSchemeObj.optString("yq_value"));
					// 获取方案发布日期
					attireScheme.setAttireTime(attireSchemeObj
							.optString("attiretime"));
					// 淘宝链接
					attireScheme.setTbkLink(attireSchemeObj.optString("tbklink"));
					// 场合
					attireScheme.setAttire_occasion(attireSchemeObj
							.optString("attire_occasion"));
					// 类型
					attireScheme.setTypeString(attireSchemeObj
							.optString("type_str"));
					// 颜色
					attireScheme.setColor_info(attireSchemeObj
							.optString("color_info"));
					// 尺寸
					attireScheme.setSize_info(attireSchemeObj
							.optString("size_info"));
					// 库存
					attireScheme.setStock_num(attireSchemeObj
							.optString("stock_num"));
					// 方案id
					attireScheme.setAttire_id_real(attireSchemeObj
							.optString("attire_id_real"));
					// 发布时间
					attireScheme.setPubtime(attireSchemeObj.optString("pubtime"));
					attireScheme.setIsView(attireSchemeObj.optInt("is_view"));

				}
				

				attireSchemeList.add(attireScheme);
			}

		} catch (Exception e) {
		}
		return attireSchemeList;
	}

	/**
	 * @description:解析买手推荐列表
	 */
	public ArrayList<Requirement> analysisRequirementList(JSONArray info) {
		ArrayList<Requirement> mList = new ArrayList<Requirement>();
		try {
			for (int i = 0; i < info.length(); i++) {
				JSONObject obj_i = info.optJSONObject(i);
				if (obj_i != null) {
					Requirement rq = new Requirement();
					// /需求ID
					rq.setId(obj_i.optString("id"));
					// 状态
					rq.setState(obj_i.optString("state"));
					// 一句话
					rq.setReqDesc(obj_i.optString("reqDesc"));
					// 场合
					rq.setOccasion(obj_i.optString("occasion"));
					// 细类
					rq.setchildId(obj_i.optString("childId"));
					rq.setPriceMax(obj_i.optString("priceMax"));
					rq.setPriceMin(obj_i.optString("priceMin"));
					rq.setexpDay(obj_i.optString("expDay"));
					rq.setadvSpec(obj_i.optString("advSpec"));
					rq.setAddTime(obj_i.optString("addTimeShow"));
					rq.setUserId(obj_i.optString("user_id"));
					rq.setUser_level(obj_i.optString("user_level"));
					rq.setUserName(obj_i.optString("user_name"));
					rq.setuserAvatar(PathCommonDefines.SERVER_IMAGE_ADDRESS
							+ obj_i.optString("user_avatar"));
					rq.setcategoryId(obj_i.optString("categoryId"));
					rq.setcategoryName(obj_i.optString("categoryName"));
					rq.setbuyerResponsesNum(obj_i
							.optString("buyerResponsesNum"));
					rq.setPhotos(PathCommonDefines.SERVER_IMAGE_ADDRESS
							+ obj_i.optString("photos"));
					rq.setsex(obj_i.optString("sex"));
					// 照片
					if (!obj_i.isNull("pics")) {
						JSONArray pics = obj_i.optJSONArray("pics");
						String[] picArr = new String[pics.length()];
						for (int j = 0; j < pics.length(); j++) {
							// if (pics.optString(j).endsWith("1.jpg")) {
							// picArr[j] =
							// PathCommonDefines.SERVER_IMAGE_ADDRESS
							// + pics.optString(j).replace("1.jpg",
							// "1s.jpg");
							// } else {
							picArr[j] = PathCommonDefines.SERVER_IMAGE_ADDRESS
									+ pics.optString(j);
							// }

						}
						rq.setPics(picArr);
					}
					// 推荐人头像
					if (!obj_i.isNull("buyerResponsesAvatar")) {
						JSONArray heads = obj_i
								.optJSONArray("buyerResponsesAvatar");
						String[] headArr = new String[heads.length()];
						for (int j = 0; j < heads.length(); j++) {
							headArr[j] = PathCommonDefines.SERVER_IMAGE_ADDRESS
									+ heads.optString(j);
						}
						rq.setBuyerResponsesAvatar(headArr);
					}

					mList.add(rq);
				}
			}
		} catch (Exception e) {
		}
		return mList;
	}

	/**
	 * @description:解析需求详情
	 */
	public Requirement analysisRequirementInfo(JSONObject info) {
		try {
			Requirement rq = new Requirement();
			if (info != null) {
				rq.setbuyerResponsesNum(info.optString("buyerResponsesNum"));
				if (!info.isNull("buyerRequest")) {
					JSONObject buyerRequest = info
							.getJSONObject("buyerRequest");

					// /需求ID
					rq.setId(buyerRequest.optString("id"));
					// 状态
					rq.setState(buyerRequest.optString("state"));
					// 一句话
					rq.setReqDesc(buyerRequest.optString("reqDesc"));
					// 场合
					rq.setOccasion(buyerRequest.optString("occasion"));
					// 细类
					rq.setchildId(buyerRequest.optString("childId"));
					rq.setPriceMax(buyerRequest.optString("priceMax"));
					rq.setPriceMin(buyerRequest.optString("priceMin"));
					rq.setexpDay(buyerRequest.optString("expDay"));
					rq.setadvSpec(buyerRequest.optString("advSpec"));
					rq.setAddTime(buyerRequest.optString("addTimeShow"));
					rq.setUserId(buyerRequest.optString("user_id"));
					rq.setUserName(buyerRequest.optString("user_name"));
					rq.setUser_level(buyerRequest.optString("user_level"));
					rq.setuserAvatar(PathCommonDefines.SERVER_IMAGE_ADDRESS
							+ buyerRequest.optString("user_avatar"));
					rq.setcategoryId(buyerRequest.optString("categoryId"));
					rq.setcategoryName(buyerRequest.optString("categoryName"));
					rq.setPhotos(PathCommonDefines.SERVER_IMAGE_ADDRESS
							+ buyerRequest.optString("photos"));
					rq.setsex(buyerRequest.optString("sex"));
					// 照片
					if (!buyerRequest.isNull("pics")) {
						JSONArray pics = buyerRequest.optJSONArray("pics");
						String[] picArr = new String[pics.length()];
						for (int j = 0; j < pics.length(); j++) {
							picArr[j] = PathCommonDefines.SERVER_IMAGE_ADDRESS
									+ pics.optString(j);
						}
						rq.setPics(picArr);
					}
				}
				// 推荐人头像
				// if (!info.isNull("buyerResponsesAvatar")) {
				// JSONArray heads = info.optJSONArray("buyerResponsesAvatar");
				// String[] headArr = new String[heads.length()];
				// for (int j = 0; j < heads.length(); j++) {
				// headArr[j] = PathCommonDefines.SERVER_IMAGE_ADDRESS
				// + heads.optString(j);
				// }
				// rq.setBuyerResponsesAvatar(headArr);
				// }
				// 推荐人信息
				if (!info.isNull("buyerResponses")) {
					JSONArray buyerResponses = info
							.optJSONArray("buyerResponses");
					ArrayList<BuyerResponse> buyerList = new ArrayList<BuyerResponse>();
					for (int i = 0; i < buyerResponses.length(); i++) {
						JSONObject buyerResponses_i = buyerResponses
								.getJSONObject(i);
						if (buyerResponses_i != null) {
							BuyerResponse b = new BuyerResponse();
							b.setIsPraise(buyerResponses_i
									.optString("isPraise"));
							b.setWords(buyerResponses_i.optString("words"));
							b.setId(buyerResponses_i.optString("id"));
							b.setaddTimeShow(buyerResponses_i
									.optString("addTimeShow"));
							b.setbuyerRespCommentsNum(buyerResponses_i
									.optString("buyerRespCommentsNum"));
							b.setbuyerRespPraisesNum(buyerResponses_i
									.optString("buyerRespPraisesNum"));
							b.setbuyerRespAttiresNum(buyerResponses_i
									.optString("buyerRespAttiresNum"));
							// 获取推荐人的用户信息
							b.setuser_id(buyerResponses_i.optString("user_id"));
							b.setuser_avatar(PathCommonDefines.SERVER_IMAGE_ADDRESS
									+ buyerResponses_i.optString("user_avatar"));
							b.setuser_name(buyerResponses_i
									.optString("user_name"));
							b.setUser_level(buyerResponses_i
									.optString("user_level"));
							// 推荐方案
							if (!buyerResponses_i
									.isNull("buyerRespAttiresPics")) {
								ArrayList<String> attireList = new ArrayList<String>();
								JSONArray buyerRespAttiresPics = buyerResponses_i
										.getJSONArray("buyerRespAttiresPics");
								for (int j = 0; j < buyerRespAttiresPics
										.length(); j++) {
									String picurl= buyerRespAttiresPics
											.optString(j);
									if ("http".equals(picurl.substring(0, 4))) {
									attireList.add(picurl);
									} else {

										attireList
										.add(PathCommonDefines.SERVER_IMAGE_ADDRESS
												+ picurl);
									};
								}
								b.setAttireList(attireList);
							}
							if (!buyerResponses_i.isNull("buyerRespAttiresIds")) {
								b.setBuyerRespAttiresIds(buyerResponses_i
										.optJSONArray("buyerRespAttiresIds")
										.toString().replace("[", "")
										.replace("]", ""));
							}
							buyerList.add(b);
						}
					}
					buyerList.add(0, new BuyerResponse());
					rq.setBuyerResponseList(buyerList);
				}

				return rq;
			}
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * @description:解析推荐详情中的响应人信息
	 */
	public BuyerResponse analysisBuyerResponseOfRecommend(
			JSONObject buyerResponse) {
		try {

			BuyerResponse b = new BuyerResponse();
			b.setIsPraise(buyerResponse.optString("isPraise"));
			b.setWords(buyerResponse.optString("words"));
			b.setId(buyerResponse.optString("id"));
			b.setaddTimeShow(buyerResponse.optString("addTimeShow"));
			// 获取推荐人的用户信息
			b.setuser_id(buyerResponse.optString("user_id"));
			b.setuser_avatar(PathCommonDefines.SERVER_IMAGE_ADDRESS
					+ buyerResponse.optString("user_avatar"));
			b.setuser_name(buyerResponse.optString("user_name"));
			b.setUser_level(buyerResponse.optString("user_level"));

			return b;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * @description:解析推荐详情中的需求
	 */
	public Requirement analysisRequirementOfRecommend(JSONObject buyerRequest) {
		try {
			Requirement rq = new Requirement();
			// /需求ID
			rq.setId(buyerRequest.optString("id"));
			// 状态
			rq.setState(buyerRequest.optString("state"));
			// 一句话
			rq.setReqDesc(buyerRequest.optString("reqDesc"));
			// 场合
			rq.setOccasion(buyerRequest.optString("occasion"));
			// 细类
			rq.setchildId(buyerRequest.optString("childId"));
			rq.setPriceMax(buyerRequest.optString("priceMax"));
			rq.setPriceMin(buyerRequest.optString("priceMin"));
			rq.setexpDay(buyerRequest.optString("expDay"));
			rq.setadvSpec(buyerRequest.optString("advSpec"));
			rq.setAddTime(buyerRequest.optString("addTimeShow"));
			rq.setUserId(buyerRequest.optString("user_id"));
			rq.setUserName(buyerRequest.optString("user_name"));
			rq.setUser_level(buyerRequest.optString("user_level"));
			rq.setuserAvatar(PathCommonDefines.SERVER_IMAGE_ADDRESS
					+ buyerRequest.optString("user_avatar"));
			rq.setcategoryId(buyerRequest.optString("categoryId"));
			rq.setcategoryName(buyerRequest.optString("categoryName"));
			rq.setPhotos(PathCommonDefines.SERVER_IMAGE_ADDRESS
					+ buyerRequest.optString("photos"));
			rq.setsex(buyerRequest.optString("sex"));

			return rq;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * @description:解析评论列表
	 */
	public ArrayList<BuyerResponse> analysisCommentList(
			JSONArray buyerRespComments) {
		try {
			ArrayList<BuyerResponse> list2 = new ArrayList<BuyerResponse>();
			for (int i = 0; i < buyerRespComments.length(); i++) {
				JSONObject buyerRespComments_i = buyerRespComments
						.getJSONObject(i);
				if (buyerRespComments_i != null) {
					BuyerResponse b = new BuyerResponse();
					b.setIsPraise(buyerRespComments_i.optString("isPraise"));
					b.setWords(buyerRespComments_i.optString("words"));
					b.setId(buyerRespComments_i.optString("id"));
					b.setaddTimeShow(buyerRespComments_i
							.optString("addTimeShow"));
					b.setbuyerRespPraisesNum(buyerRespComments_i
							.optString("buyerRespCommPraisesNum"));
					b.setuser_id(buyerRespComments_i.optString("user_id"));
					b.setuser_avatar(PathCommonDefines.SERVER_IMAGE_ADDRESS
							+ buyerRespComments_i.optString("user_avatar"));
					b.setuser_name(buyerRespComments_i.optString("user_name"));
					b.setUser_level(buyerRespComments_i.optString("user_level"));
					// 推荐方案
					if (!buyerRespComments_i.isNull("buyerResponseCommentImg")) {
						ArrayList<String> attireList = new ArrayList<String>();
						JSONArray buyerResponseCommentImg = buyerRespComments_i
								.getJSONArray("buyerResponseCommentImg");
						for (int j = 0; j < buyerResponseCommentImg.length(); j++) {
							attireList
									.add(PathCommonDefines.SERVER_IMAGE_ADDRESS
											+ buyerResponseCommentImg
													.optString(j));
						}
						b.setAttireList(attireList);
					}
					list2.add(b);
				}

			}
			return list2;
		} catch (Exception e) {
		}
		return null;
	}
}
