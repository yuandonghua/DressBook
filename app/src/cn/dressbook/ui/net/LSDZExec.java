package cn.dressbook.ui.net;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.json.LSDZJson;
import cn.dressbook.ui.model.CiXiu;
import cn.dressbook.ui.model.DIYUpLoading;
import cn.dressbook.ui.model.DZSPDetails;
import cn.dressbook.ui.model.DZSPFL;
import cn.dressbook.ui.model.LSDZFL;
import cn.dressbook.ui.model.MYX1;
import cn.dressbook.ui.model.MYXChild;
import cn.dressbook.ui.model.Param;

/**
 * @description 量身定制
 * @author 袁东华
 * @date 2016-1-22
 */
public class LSDZExec {
	private static LSDZExec mInstance = null;

	public static LSDZExec getInstance() {
		if (mInstance == null) {
			mInstance = new LSDZExec();
		}
		return mInstance;
	}

	/**
	 * @description:告诉服务端支付成功
	 */
	public void paymentSuccess(final Handler handler, final String user_id,
			final String order_id, final String price_net, final String pay_yb,
			final String out_trade_no, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.PAYMENT_SUCCESS_DZSP);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("order_id", order_id);
		params.addBodyParameter("price_net", price_net);
		params.addBodyParameter("pay_yb", pay_yb);
		params.addBodyParameter("out_trade_no", out_trade_no);
		LogUtil.e(PathCommonDefines.PAYMENT_SUCCESS_DZSP + "?user_id="
				+ user_id + "&order_id=" + order_id + "&price_net=" + price_net
				+ "&pay_yb=" + pay_yb + "&out_trade_no=" + out_trade_no);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				LogUtil.e("result:" + result);
				try {
					JSONObject json = new JSONObject(result);
					if (json != null) {
						int code = json.optInt("code");
						if (code == 1) {

							JSONObject info = json.getJSONObject("info");
							if (info != null) {
								String recode = info.optString("recode");
								String redesc = info.optString("redesc");
								Message msg = new Message();
								Bundle bun = new Bundle();
								bun.putString("recode", recode);
								bun.putString("redesc", redesc);
								msg.setData(bun);
								msg.what = flag1;
								handler.sendMessage(msg);

							} else {

							}
						} else {
							handler.sendEmptyMessage(flag2);
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
	 * @description:提交补充信息
	 */
	public void getTJBCXX(final Handler handler, String user_id, String oda_id,
			String address_id, String color, String font_family, String words,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.TJ_BCXX);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("oda_id", oda_id);
		params.addBodyParameter("address_id", address_id);
		params.addBodyParameter("color", color);
		params.addBodyParameter("font_family", font_family);
		params.addBodyParameter("words", words);
		LogUtil.e(PathCommonDefines.TJ_BCXX + "?user_id=" + user_id
				+ "&oda_id=" + oda_id + "&address_id=" + address_id + "&color="
				+ color + "&font_family=" + font_family + "&words=" + words);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				LogUtil.e("result:" + result);
				try {

					JSONObject obj = new JSONObject(result);
					String message = obj.optString("message");
					if (obj != null) {
						if (!obj.isNull("info")) {
							JSONObject info = obj.getJSONObject("info");
							if (!info.isNull("recode")) {

								String recode = info.optString("recode");
								if ("0".equals(recode)) {

									handler.sendEmptyMessage(flag1);
									return;
								}
							}
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				handler.sendEmptyMessage(flag2);
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
	 * @description:获取定制商品的补充信息id
	 */
	public void getDZSPBCXX(final Handler handler, String user_id,
			String dzattire_id, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_DZSP_BCXX);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("dzattire_id", dzattire_id);
		LogUtil.e(PathCommonDefines.GET_DZSP_BCXX + "?user_id=" + user_id
				+ "&dzattire_id=" + dzattire_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {

					JSONObject obj = new JSONObject(result);
					if (obj != null) {
						if (!obj.isNull("info")) {
							JSONObject info = obj.getJSONObject("info");
							String sex = info.optString("sex");
							String order_id = info.optString("order_id");
							String order_dz_attire_id = info
									.optString("order_dz_attire_id");
							String template_id = info.optString("template_id");
							String order_priceTotal = info
									.optString("order_priceTotal");
							Message msg = new Message();
							Bundle data = new Bundle();
							data.putString("order_priceTotal", order_priceTotal);
							data.putString("order_id", order_id);
							data.putString("order_dz_attire_id",
									order_dz_attire_id);
							data.putString("template_id", template_id);
							data.putString("sex", sex);
							msg.what = flag1;
							msg.setData(data);
							handler.sendMessage(msg);
						}
					}
				} catch (Exception e) {
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
	 * @description:获取定制商品的刺绣信息
	 */
	public void getDZSPCX(final Handler handler, String template_id,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.GET_DZSP_CX);
		params.addBodyParameter("template_id", template_id);
		LogUtil.e(PathCommonDefines.GET_DZSP_CX + "?template_id=" + template_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {

					JSONObject obj = new JSONObject(result);
					if (obj != null) {

						if (!obj.isNull("info")) {

							JSONObject info = obj.getJSONObject("info");
							ArrayList<CiXiu> colorList = new ArrayList<CiXiu>();
							ArrayList<CiXiu> fontFamilyList = new ArrayList<CiXiu>();
							// 解析刺绣颜色信息
							if (!info.isNull("color")) {
								JSONArray color = info.getJSONArray("color");
								colorList = LSDZJson.getInstance()
										.analyzeDZSPCX(color);

							}
							// 解析刺绣字体信息
							if (!info.isNull("fontFamily")) {
								JSONArray fontFamily = info
										.getJSONArray("fontFamily");
								fontFamilyList = LSDZJson.getInstance()
										.analyzeDZSPCX(fontFamily);
							}
							Message msg = new Message();
							Bundle data = new Bundle();
							data.putParcelableArrayList("colorList", colorList);
							data.putParcelableArrayList("fontFamilyList",
									fontFamilyList);
							msg.what = flag1;
							msg.setData(data);
							handler.sendMessage(msg);
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
	 * @description:获取定制商品详情数据
	 */
	public void getDZSPDetails(final Handler handler, String attire_id,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_DZSP_DETAILS);
		params.addBodyParameter("attire_id", attire_id);
		LogUtil.e(PathCommonDefines.GET_DZSP_DETAILS + "?attire_id="
				+ attire_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					DZSPDetails dZSPDetails = LSDZJson.getInstance()
							.analyzeDZSPDetails(result);
					Message msg = new Message();
					Bundle data = new Bundle();
					data.putParcelable("DZSPDetails", dZSPDetails);
					msg.what = flag1;
					msg.setData(data);
					handler.sendMessage(msg);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
	 * @description:获取量身定制分类列表 sex=0全部,sex=1男,sex=2女
	 */
	public void getLSDZFLList(final Handler handler, String sex,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.LSDZFL_LIST);
		params.addBodyParameter("sex", sex + "");
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					ArrayList<LSDZFL> mList = LSDZJson.getInstance()
							.analyzeLSDZFLList(result);
					Message msg = new Message();
					Bundle data = new Bundle();
					data.putParcelableArrayList("list", mList);
					msg.what = flag1;
					msg.setData(data);
					handler.sendMessage(msg);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
	 * @description:获取量身定制列表 sex=0全部,sex=1男,sex=2女
	 */
	public void getLSDZList(final Handler handler, String sex, int page_index,
			int page_size, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.LSDZ_LIST);
		params.addBodyParameter("sex", sex);
		params.addBodyParameter("page_index", page_index + "");
		params.addBodyParameter("page_size", page_size + "");
		LogUtil.e(PathCommonDefines.LSDZ_LIST + "?sex=" + sex);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					ArrayList<DZSPFL> mList = LSDZJson.getInstance()
							.analyzeLSDZList(result);
					Message msg = new Message();
					Bundle data = new Bundle();
					data.putParcelableArrayList("list", mList);
					msg.what = flag1;
					msg.setData(data);
					handler.sendMessage(msg);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
	 * @description:获取种类定制列表 【必选】 cls_id 类别id
	 * @param page_index
	 *            当前页数，默认为1
	 * @param page_size
	 *            每页数量，默认为10
	 */
	public void getZldzList(final Handler handler, String clsId,
			int page_index, int page_size, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.ZLDZ_LIST);
		params.addBodyParameter("cls_id", clsId + "");
		params.addBodyParameter("page_index", page_index + "");
		params.addBodyParameter("page_size", page_size + "");

		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				LogUtil.e("result:" + result);
				try {
					DZSPFL dzspfl = LSDZJson.getInstance().analyzeZlDZList(
							result);
					Message msg = new Message();
					Bundle data = new Bundle();
					data.putParcelable("dzspfl", dzspfl);
					msg.what = flag1;
					msg.setData(data);
					handler.sendMessage(msg);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
	 * @description:获取DIY列表 【必选】 cls_id 类别id
	 */
	public void getDIYList(final Handler handler, final String attire_id,
			final String user_id, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.DIY_LIST);
		params.addBodyParameter("attire_id", attire_id + "");
		params.addBodyParameter("user_id", user_id + "");
		LogUtil.e("获取DIY列表:" + PathCommonDefines.DIY_LIST + "?attire_id="
				+ attire_id + "&user_id=" + user_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					DZSPDetails dzspDetails = LSDZJson.getInstance()
							.analyzeDIYDetails(result);
					Message msg = new Message();
					Bundle data = new Bundle();
					data.putParcelable("dzspDetails", dzspDetails);
					msg.what = flag1;
					msg.setData(data);
					handler.sendMessage(msg);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
	 * @description:上传DIY列表 【必选】 cls_id 类别id
	 */
	public void pullUpDIYList(final Handler handler, String user_id,
			String is_dz, String dzattire_id, String template_id, String mes,
			String paramjson, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.PULL_UP_DIY_LIST);
		params.addBodyParameter("is_dz", is_dz);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("dzattire_id", dzattire_id);
		params.addBodyParameter("template_id", template_id);
		params.addBodyParameter("mes", mes);
		params.addParameter("paramjson", paramjson);
		x.http().post(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				LogUtil.e(result);
				try {
					JSONObject obj = new JSONObject(result);
					String message = obj.optString("message");
					if ("success".equals(message)) {
						JSONObject info = obj.getJSONObject("info");
						String order_id = info.optString("order_id");
						String order_dz_attire_id = info
								.optString("order_dz_attire_id");
						String template_id = info.optString("template_id");
						String order_priceTotal = info
								.optString("order_priceTotal");
						String sex = info.optString("sex");
						Message msg = new Message();
						Bundle data = new Bundle();
						data.putString("order_priceTotal", order_priceTotal);
						data.putString("order_id", order_id);
						data.putString("order_dz_attire_id", order_dz_attire_id);
						data.putString("template_id", template_id);
						data.putString("sex", sex);
						msg.what = flag1;
						msg.setData(data);
						handler.sendMessage(msg);
					} else {
						handler.sendEmptyMessage(flag2);
					}
				} catch (Exception e) {
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

}
