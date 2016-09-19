package cn.dressbook.ui.net;

import java.io.File;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;

import android.graphics.pdf.PdfDocument.PageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.json.AttireSchemeJson;
import cn.dressbook.ui.json.RequirementJson;
import cn.dressbook.ui.model.AlbcBean;
import cn.dressbook.ui.model.AttireScheme;
import cn.dressbook.ui.model.BuyerResponse;
import cn.dressbook.ui.model.PL;
import cn.dressbook.ui.model.Requirement;
import cn.dressbook.ui.model.XL;

/**
 * @description: 需求
 * @author:袁东华
 * @time:2015-8-11下午5:13:59
 */
public class RequirementExec {
	private static RequirementExec mInstance = null;

	public static RequirementExec getInstance() {
		if (mInstance == null) {
			mInstance = new RequirementExec();
		}
		return mInstance;
	}

	/**
	 * 从服务端获取推荐详情
	 * 
	 */
	public void getRecommendInfoFromServer(final Handler handler,
			final String user_id, final String response_id, final int flag1,
			final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_RECOMMENDLIST);
		params.addBodyParameter("response_id", response_id);
		params.addBodyParameter("user_id", user_id);
		LogUtil.e("推荐详情:" + PathCommonDefines.GET_RECOMMENDLIST
				+ "?response_id=" + response_id + "&user_id=" + user_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					ArrayList<AttireScheme> list = null;
					ArrayList<BuyerResponse> list2 = null;
					Requirement rq = null;
					BuyerResponse br = null;
					JSONObject json = new JSONObject(result);
					if (!json.isNull("info")) {
						JSONObject info = json.getJSONObject("info");
						String buyerResponseAttiresNum = info
								.optString("buyerResponseAttiresNum");

						if (!json.isNull("buyerRequest")) {
							JSONObject buyerRequest = json
									.getJSONObject("buyerRequest");
							rq = RequirementJson.getInstance()
									.analysisRequirementOfRecommend(
											buyerRequest);
							rq.setbuyerResponsesNum(buyerResponseAttiresNum);

						}
						if (!json.isNull("buyerResponse")) {
							JSONObject buyerResponse = json
									.getJSONObject("buyerResponse");
							br = RequirementJson.getInstance()
									.analysisBuyerResponseOfRecommend(
											buyerResponse);

						}

						if (!info.isNull("buyerResponseAttires")) {
							JSONArray buyerResponseAttires = info
									.getJSONArray("buyerResponseAttires");
							list = RequirementJson.getInstance()
									.analysisMSTJList(buyerResponseAttires);
						}
						if (!info.isNull("buyerResponseComments")) {
							JSONArray buyerResponseComments = info
									.getJSONArray("buyerResponseComments");
							list2 = RequirementJson.getInstance()
									.analysisCommentList(buyerResponseComments);
						}
						Message msg = new Message();
						Bundle bun = new Bundle();
						bun.putParcelableArrayList("list", list);
						bun.putParcelableArrayList("list2", list2);
						bun.putParcelable("rq", rq);
						bun.putParcelable("br", br);
						msg.setData(bun);
						msg.what = flag1;
						handler.sendMessage(msg);
					}
				} catch (Exception e) {
					handler.sendEmptyMessage(flag2);
				}

			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				handler.sendEmptyMessage(flag2);
			}

			@Override
			public void onCancelled(CancelledException cex) {
			}

			@Override
			public void onFinished() {

			}
		});

	}

	/**
	 * 从服务端获取推荐方案列表
	 * 
	 */
	public void getRecommendListFromServer(final Handler handler,
			final String ids, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_RECOMMENDLIST);
		params.addBodyParameter("ids", ids);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject json = new JSONObject(result);
					if (!json.isNull("info")) {
						JSONArray info = json.getJSONArray("info");
						ArrayList<AttireScheme> list = RequirementJson
								.getInstance().analysisMSTJList(info);
						Message msg = new Message();
						Bundle bun = new Bundle();
						bun.putParcelableArrayList("list", list);
						msg.setData(bun);
						msg.what = flag1;
						handler.sendMessage(msg);
					}
				} catch (Exception e) {
					handler.sendEmptyMessage(flag2);
				}

			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				handler.sendEmptyMessage(flag2);
			}

			@Override
			public void onCancelled(CancelledException cex) {
			}

			@Override
			public void onFinished() {

			}
		});
	}

	/**
	 * @description:举报评论
	 */
	public void jbRecommend2(final Handler handler, final String user_id,
			final String model_id, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.JB_RECOMMEND2);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("model_id", model_id);
		params.addBodyParameter("model", "BuyerRespCommen");
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject jsonObject = new JSONObject(result);
					if (!jsonObject.isNull("info")) {
						JSONObject info = jsonObject.getJSONObject("info");
						String recode = info.optString("recode");
						String redesc = info.optString("redesc");

						Message msg = new Message();
						Bundle bun = new Bundle();
						bun.putString("recode", recode);
						bun.putString("redesc", redesc);
						msg.setData(bun);
						msg.what = flag1;
						handler.sendMessage(msg);
					}
				} catch (Exception e) {
					handler.sendEmptyMessage(flag2);
				}

			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				handler.sendEmptyMessage(flag2);
			}

			@Override
			public void onCancelled(CancelledException cex) {
			}

			@Override
			public void onFinished() {

			}
		});

	}

	/**
	 * @description:喜欢评论
	 */
	public void xhRecommend2(final Handler handler, final String user_id,
			final String comment_id, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.XH_RECOMMEND2);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("comment_id", comment_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject jsonObject = new JSONObject(result);
					if (!jsonObject.isNull("info")) {
						JSONObject info = jsonObject.getJSONObject("info");
						String recode = info.optString("recode");
						String redesc = info.optString("redesc");

						Message msg = new Message();
						Bundle bun = new Bundle();
						bun.putString("recode", recode);
						bun.putString("redesc", redesc);
						msg.setData(bun);
						msg.what = flag1;
						handler.sendMessage(msg);
					}
				} catch (Exception e) {
					handler.sendEmptyMessage(flag2);
				}

			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				handler.sendEmptyMessage(flag2);
			}

			@Override
			public void onCancelled(CancelledException cex) {
			}

			@Override
			public void onFinished() {

			}
		});
	}

	/**
	 * @description:喜欢响应
	 */
	public void xhRecommend(final Handler handler, final String user_id,
			final String comment_id, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.XH_RECOMMEND);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("response_id", comment_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject jsonObject = new JSONObject(result);
					if (!jsonObject.isNull("info")) {
						JSONObject info = jsonObject.getJSONObject("info");
						String recode = info.optString("recode");
						String redesc = info.optString("redesc");

						Message msg = new Message();
						Bundle bun = new Bundle();
						bun.putString("recode", recode);
						bun.putString("redesc", redesc);
						msg.setData(bun);
						msg.what = flag1;
						handler.sendMessage(msg);
					}
				} catch (Exception e) {
					handler.sendEmptyMessage(flag2);
				}

			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				handler.sendEmptyMessage(flag2);
			}

			@Override
			public void onCancelled(CancelledException cex) {
			}

			@Override
			public void onFinished() {

			}
		});
	}

	/**
	 * @description:获取需求详情
	 */
	public void getRequirementInfoFromServer(final Handler handler,
			final String user_id, final String request_id, final int flag1,
			final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_REQUIREMENTINFO);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("request_id", request_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					Requirement requirement = null;
					JSONObject jsonObject = new JSONObject(result);
					if (!jsonObject.isNull("info")) {

						JSONObject info = jsonObject.getJSONObject("info");
						requirement = RequirementJson.getInstance()
								.analysisRequirementInfo(info);
						Message msg = new Message();
						Bundle bun = new Bundle();
						bun.putParcelable("requirement", requirement);
						msg.setData(bun);
						msg.what = flag1;
						handler.sendMessage(msg);
					}
				} catch (Exception e) {
					handler.sendEmptyMessage(flag2);
				}

			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				handler.sendEmptyMessage(flag2);
			}

			@Override
			public void onCancelled(CancelledException cex) {
			}

			@Override
			public void onFinished() {

			}
		});

	}

	/**
	 * @description:搜索服装方案
	 */
	public void searchClothing(final Handler handler, final String sex,
			final String price_range, final String xl, final String attr_tags,
			final String keyword, final int page_index, final int page_size,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.SEARCH_CLOTHING_ALBC);
		params.addBodyParameter("sex", sex);
		params.addBodyParameter("price_range", price_range);
		// params.addBodyParameter("pcid", pcid);
		// params.addBodyParameter("cid", cid);
		// params.addBodyParameter("attr_tags", attr_tags);
		if ("".equals(xl)) {
			params.addBodyParameter("keyword", keyword);

		} else {
			params.addBodyParameter("keyword", keyword + " " + xl);
		}
		params.addBodyParameter("page_index", page_index + "");
		params.addBodyParameter("page_size", page_size + "");
		
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject json = new JSONObject(result);
					if (!json.isNull("info")) {
						JSONArray info = json.getJSONArray("info");
						ArrayList<AlbcBean> list = AttireSchemeJson
								.getInstance().getAlbcFind(result);
						Message msg = new Message();
						Bundle bun = new Bundle();
						bun.putParcelableArrayList("list", list);
						msg.setData(bun);
						msg.what = flag1;
						handler.sendMessage(msg);
					}
				} catch (Exception e) {
					handler.sendEmptyMessage(flag2);
				}

			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				handler.sendEmptyMessage(flag2);
			}

			@Override
			public void onCancelled(CancelledException cex) {
			}

			@Override
			public void onFinished() {

			}
		});
	}

	/**
	 * @description:获取标签
	 */
	public void getBQ(final Handler handler, final String cat_id,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.GET_BQ);
		params.addBodyParameter("cat_id", cat_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					ArrayList<PL> list = new ArrayList<PL>();
					JSONObject json = new JSONObject(result);
					if (json != null) {
						JSONArray info = json.getJSONArray("info");
						if (info != null && info.length() > 0) {
							for (int i = 0; i < info.length(); i++) {
								JSONObject obji = info.getJSONObject(i);
								if (obji != null) {
									PL pl = new PL();
									pl.setId(obji.optString("id"));
									pl.setName(obji.optString("name"));
									if (!obji.isNull("preValues")) {

										String str = obji
												.optString("preValues");
										if (str != null && str.length() > 0) {
											ArrayList<XL> xls = new ArrayList<XL>();
											String[] strs = str.split("##");

											for (int j = 0; j < strs.length; j++) {
												XL xl = new XL();
												xl.setId(obji.optString("id"));
												xl.setName(strs[j]);
												xls.add(xl);
											}
											pl.setXls(xls);
											list.add(pl);
										}
									}
								}

							}
						}

					}
					Message msg = new Message();
					Bundle data = new Bundle();
					data.putParcelableArrayList("list", list);
					msg.setData(data);
					msg.what = flag1;
					handler.sendMessage(msg);
				} catch (Exception e) {
					// TODO: handle exception
					handler.sendEmptyMessage(flag2);
				}

			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				handler.sendEmptyMessage(flag2);
			}

			@Override
			public void onCancelled(CancelledException cex) {
			}

			@Override
			public void onFinished() {

			}
		});
	}

	/**
	 * @description:获取服装品类
	 */
	public void getPL(final Handler handler, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.GET_PL);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					ArrayList<PL> nanPL = new ArrayList<PL>();
					ArrayList<PL> nvPL = new ArrayList<PL>();
					JSONObject json = new JSONObject(result);
					if (json != null) {
						JSONObject info = json.getJSONObject("info");
						if (info != null) {
							JSONArray nan = info.getJSONArray("男装");
							if (nan != null && nan.length() > 0) {
								for (int i = 0; i < nan.length(); i++) {
									JSONObject obji = nan.getJSONObject(i);
									if (obji != null) {
										PL pl = new PL();
										pl.setId(obji.optString("id"));
										pl.setName(obji.optString("name"));
										if (!obji.isNull("child")) {

											JSONArray arri = obji
													.getJSONArray("child");
											if (arri != null
													&& arri.length() > 0) {
												ArrayList<XL> xls = new ArrayList<XL>();
												for (int j = 0; j < arri
														.length(); j++) {
													JSONObject objj = arri
															.getJSONObject(j);
													if (objj != null) {
														XL xl = new XL();
														xl.setId(objj
																.getString("id"));
														xl.setName(objj
																.getString("name"));
														xls.add(xl);
													}
												}
												pl.setXls(xls);
												nanPL.add(pl);
											}
										}
									}
								}

							}

							JSONArray nv = info.getJSONArray("女装");
							if (nv != null && nv.length() > 0) {
								for (int i = 0; i < nv.length(); i++) {
									JSONObject obji = nv.getJSONObject(i);
									if (obji != null) {
										PL pl = new PL();
										pl.setId(obji.optString("id"));
										pl.setName(obji.optString("name"));
										if (!obji.isNull("child")) {
											JSONArray arri = obji
													.getJSONArray("child");
											if (arri != null
													&& arri.length() > 0) {
												ArrayList<XL> xls = new ArrayList<XL>();
												for (int j = 0; j < arri
														.length(); j++) {
													JSONObject objj = arri
															.getJSONObject(j);
													if (objj != null) {
														XL xl = new XL();
														xl.setId(objj
																.getString("id"));
														xl.setName(objj
																.getString("name"));
														xls.add(xl);
													}
												}
												pl.setXls(xls);
												nvPL.add(pl);
											}

										}
									}
								}

							}
						}

					}
					Message msg = new Message();
					Bundle data = new Bundle();
					data.putParcelableArrayList("nan", nanPL);
					data.putParcelableArrayList("nv", nvPL);
					msg.setData(data);
					msg.what = flag1;
					handler.sendMessage(msg);
				} catch (Exception e) {
					// TODO: handle exception
					handler.sendEmptyMessage(flag2);
				}

			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				handler.sendEmptyMessage(flag2);
			}

			@Override
			public void onCancelled(CancelledException cex) {
			}

			@Override
			public void onFinished() {

			}
		});
	}

	/**
	 * @description:够了
	 */
	public void gl(final Handler handler, final String user_id,
			final String request_id, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.GL);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("request_id", request_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject json = new JSONObject(result);
					if (!json.isNull("info")) {
						JSONObject info = json.optJSONObject("info");
						String redesc = info.optString("redesc");
						Message msg = new Message();
						Bundle data = new Bundle();
						data.putString("redesc", redesc);
						msg.setData(data);
						msg.what = flag1;
						handler.sendMessage(msg);
					} else {

						handler.sendEmptyMessage(flag2);

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					handler.sendEmptyMessage(flag2);
				}

			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				handler.sendEmptyMessage(flag2);
			}

			@Override
			public void onCancelled(CancelledException cex) {
			}

			@Override
			public void onFinished() {

			}
		});
	}

	/**
	 * @description:完成需求
	 */
	public void completeRequirement(final Handler handler,
			final String shopping_id, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.COMPLETE_REQUIREMENT);
		params.addBodyParameter("shopping_id", shopping_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				handler.sendEmptyMessage(flag1);

			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				handler.sendEmptyMessage(flag2);
			}

			@Override
			public void onCancelled(CancelledException cex) {
			}

			@Override
			public void onFinished() {

			}
		});
	}

	/**
	 * @description:结束需求
	 */
	public void finishRequirement(final Handler handler,
			final String shopping_id, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.STOP_REQUIREMENT);
		params.addBodyParameter("shopping_id", shopping_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject object = new JSONObject(result);
					int code = object.getInt("code");
					if (code == 1) {
						handler.sendEmptyMessage(flag1);
					} else {
						handler.sendEmptyMessage(flag2);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					handler.sendEmptyMessage(flag2);
				}

			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				handler.sendEmptyMessage(flag2);
			}

			@Override
			public void onCancelled(CancelledException cex) {
			}

			@Override
			public void onFinished() {

			}
		});
	}

	/**
	 * @description:选择推荐商品
	 */
	public void recomendClothing(final Handler handler, final String user_id,
			final String request_id, final String mat_id, final String words,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.RECOMMEND_CLOTHING_ALBC);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("request_id", request_id);
		params.addBodyParameter("mat_id", mat_id);
		params.addBodyParameter("words", words);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject json = new JSONObject(result);
					if (!json.isNull("info")) {
						JSONObject info = json.getJSONObject("info");
						if (info.optInt("recode") == 0) {
							handler.sendEmptyMessage(flag1);
						} else {
							handler.sendEmptyMessage(flag2);
						}
					} else {
						handler.sendEmptyMessage(flag2);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					handler.sendEmptyMessage(flag2);
				}

			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				handler.sendEmptyMessage(flag2);
			}

			@Override
			public void onCancelled(CancelledException cex) {
			}

			@Override
			public void onFinished() {

			}
		});
	}

	/**
	 * @description:确认收货
	 */
	public void confirmationReceipt(final Handler handler,
			final String shopping_id, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.CONFIRMATION_RECEIPT);
		params.addBodyParameter("shopping_id", shopping_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject object = new JSONObject(shopping_id);
					int code = object.getInt("code");
					if (code == 1) {
						handler.sendEmptyMessage(flag1);
					} else {
						handler.sendEmptyMessage(flag2);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					handler.sendEmptyMessage(flag2);
				}

			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				handler.sendEmptyMessage(flag2);
			}

			@Override
			public void onCancelled(CancelledException cex) {
			}

			@Override
			public void onFinished() {

			}
		});
	}

	/**
	 * @description:发布需求
	 */
	public void publishRequirement(final Handler handler, final String user_id,
			final String occasion, final String category_id,
			final String price, final String sex, final String req_desc,
			final String exp_day, final String adv_spec, final String child_id,
			final String url1, final String url2, final String url3,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.PUBLISHREQUIREMENT);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("occasion", occasion);
		params.addBodyParameter("category_id", category_id);
		params.addBodyParameter("price", price);
		params.addBodyParameter("sex", sex);
		params.addBodyParameter("req_desc", req_desc);
		params.addBodyParameter("exp_day", exp_day);
		if (child_id != null) {

			params.addBodyParameter("child_id", child_id);
		}
		if (adv_spec != null) {

			params.addBodyParameter("adv_spec", adv_spec);
		}
		if (url1 != null && !"".equals(url1)) {

			params.addBodyParameter("uploadFile", new File(url1));
		}
		if (url2 != null && !"".equals(url2)) {

			params.addBodyParameter("uploadFile", new File(url2));
		}
		if (url3 != null && !"".equals(url3)) {

			params.addBodyParameter("uploadFile", new File(url3));
		}
		LogUtil.e("user_id:" + user_id + "occasion:" + occasion
				+ "category_id:" + category_id + "price:" + price + "sex:"
				+ sex + "req_desc:" + req_desc + "exp_day:" + exp_day);
		x.http().post(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				LogUtil.e("result:" + result);
				if (handler != null) {

					handler.sendEmptyMessage(flag1);

				}
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				LogUtil.e("异常:" + ex.getMessage());
				if (handler != null) {

					handler.sendEmptyMessage(flag2);

				}
			}

			@Override
			public void onCancelled(CancelledException cex) {
				if (handler != null) {

					handler.sendEmptyMessage(flag2);

				}
			}

			@Override
			public void onFinished() {

			}
		});
	}

	/**
	 * @description:发布评论
	 */
	public void sendComment(final Handler handler, final String user_id,
			final String response_id, final String words,
			final ArrayList<String> list, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.SEND_COMMENT);
		params.setMultipart(true);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("response_id", response_id);
		params.addBodyParameter("words", words);
		if (list != null && list.size() > 2) {
			for (int i = 2; i < list.size(); i++) {
				params.addBodyParameter("uploadFile", new File(list.get(i)));
			}
		}

		x.http().post(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				LogUtil.e("result:" + result);
				if (handler != null) {

					handler.sendEmptyMessage(flag1);

				}
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				if (handler != null) {

					handler.sendEmptyMessage(flag2);

				}
			}

			@Override
			public void onCancelled(CancelledException cex) {
				if (handler != null) {

					handler.sendEmptyMessage(flag2);

				}
			}

			@Override
			public void onFinished() {

			}
		});

	}

	/**
	 * @description:获取需求列表
	 */
	public void getRequirementListFromServer(final Handler handler,
			final String user_id, final String state, final int page_num,
			final int page_size, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_REQUIREMENTLIST_FROM_SERVER);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("state", state);
		params.addBodyParameter("page_num", page_num + "");
		params.addBodyParameter("page_size", page_size + "");
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					String buyerRespTotalNum = "0";
					ArrayList<Requirement> requirementList = null;
					JSONObject jsonObject = new JSONObject(result);
					JSONObject info = jsonObject.getJSONObject("info");
					if (info != null) {
						buyerRespTotalNum = info.optString("buyerRespTotalNum");
						JSONArray buyerRequest = info
								.getJSONArray("buyerRequest");
						requirementList = RequirementJson.getInstance()
								.analysisRequirementList(buyerRequest);
					}

					Message msg = new Message();
					Bundle bun = new Bundle();
					bun.putParcelableArrayList("requirementList",
							requirementList);
					bun.putString("buyerRespTotalNum", buyerRespTotalNum);
					msg.setData(bun);
					msg.what = flag1;
					handler.sendMessage(msg);

				} catch (Exception e) {
					handler.sendEmptyMessage(flag2);
				}

			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				handler.sendEmptyMessage(flag2);
			}

			@Override
			public void onCancelled(CancelledException cex) {
			}

			@Override
			public void onFinished() {

			}
		});

	}

	/**
	 * @description:获取其他用户需求列表
	 */
	public void getOtherRequirementListFromServer(final Handler handler,
			final String user_id, final int page_num, final int page_size,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_OTHERREQUIREMENTLIST_FROM_SERVER);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("page_num", page_num + "");
		params.addBodyParameter("page_size", page_size + "");
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					String buyerRequTotalNum = "0";
					ArrayList<Requirement> requirementList = null;
					JSONObject jsonObject = new JSONObject(result);
					JSONObject info = jsonObject.getJSONObject("info");
					if (info != null) {
						buyerRequTotalNum = info.optString("buyerRequTotalNum");
						JSONArray buyerRequest = info
								.getJSONArray("buyerRequest");
						requirementList = RequirementJson.getInstance()
								.analysisRequirementList(buyerRequest);
					}
					Message msg = new Message();
					Bundle bun = new Bundle();
					bun.putString("buyerRequTotalNum", buyerRequTotalNum);
					bun.putParcelableArrayList("list", requirementList);
					msg.setData(bun);
					msg.what = flag1;
					handler.sendMessage(msg);
				} catch (Exception e) {
					handler.sendEmptyMessage(flag2);
				}

			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				handler.sendEmptyMessage(flag2);
			}

			@Override
			public void onCancelled(CancelledException cex) {
			}

			@Override
			public void onFinished() {

			}
		});
	}

}
