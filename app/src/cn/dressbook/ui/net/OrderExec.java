package cn.dressbook.ui.net;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.json.OrderJson;
import cn.dressbook.ui.model.LogisticsInfo;
import cn.dressbook.ui.model.MSTJData;
import cn.dressbook.ui.model.OrderForm;
import cn.dressbook.ui.model.SerializableMap;

public class OrderExec {
	private static OrderExec mInstance = null;

	public static OrderExec getInstance() {

		if (mInstance == null) {
			mInstance = new OrderExec();
		}
		return mInstance;
	}

	private OrderExec() {

	}

	/**
	 * @description:提交订单
	 */
	public void submitOrder(final Handler handler, final String user_id,
			final String address_id, final String order_id,
			final String mes_user, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.SUBMIT_ORDER);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("address_id", address_id);
		params.addBodyParameter("order_id", order_id);
		params.addBodyParameter("mes_user", mes_user);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
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
	 * @description:加入购物车
	 */
	public void addShoppingCart(final Handler handler, final String user_id,
			final String attire_id, final String sdesc, final String num,
			final String priceStr, final String adviser_id, final String size,
			final String color, final String inviter, final int flag1,
			final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.ADD_SHOPPINGCART);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("attire_id", attire_id);
		params.addBodyParameter("num", num);
		params.addBodyParameter("sdesc", sdesc);
		params.addBodyParameter("price", priceStr);
		if (!"0".equals(adviser_id)) {
			params.addBodyParameter("adviser_id", adviser_id);
		}
		params.addBodyParameter("size", size);
		params.addBodyParameter("color", color);
		params.addBodyParameter("inviter", inviter);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				LogUtil.e(result);
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
				LogUtil.e(ex.getMessage());
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
	 * @description:获取购物车商品列表
	 */
	public void getShoppingCartInfo(final Handler handler,
			final String user_id, final String state, final int flag1,
			final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_ORDERLIST);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("state", state);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject json = new JSONObject(result);
					if (json != null) {
						int code = json.optInt("code");
						if (code == 1) {

							JSONObject info = json.getJSONObject("info");
							if (info != null) {
								String recode = info.optString("recode");
								String redesc = info.optString("redesc");
								String order_id = info.optString("order_id");
								String yqTotal = info.optString("yqTotal");
								JSONArray orderAttire = info
										.getJSONArray("orderAttire");
								ArrayList<MSTJData> orderAttireList = null;
								if (orderAttire != null
										&& orderAttire.length() > 0) {
									orderAttireList = OrderJson.getInstance()
											.analyzeShoppingList(orderAttire);
								}
								Message msg = new Message();
								Bundle bun = new Bundle();
								bun.putString("recode", recode);
								bun.putString("redesc", redesc);
								bun.putString("order_id", order_id);
								bun.putString("yqTotal", yqTotal);
								bun.putParcelableArrayList("list",
										orderAttireList);
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
	 * @description:获取订单列表
	 */
	public void getOrderList(final Handler handler, final String user_id,
			final String state, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_ORDERLIST);
		params.addBodyParameter("user_id", user_id);
		LogUtil.e(PathCommonDefines.GET_ORDERLIST + "?user_id=" + user_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				HashMap<String, ArrayList<OrderForm>> map = null;
				try {
					map = OrderJson.getInstance().analyzeOrderList(result);

				} catch (JSONException e) {
				}
				SerializableMap orderMap = new SerializableMap();
				orderMap.setMap(map);
				Message msg = new Message();
				Bundle bun = new Bundle();
				bun.putSerializable("orderMap", orderMap);
				msg.setData(bun);
				msg.what = flag1;
				handler.sendMessage(msg);
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
	 * @description:支付成功
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
	 * @description:获取订单详情
	 */
	public void getOrderInfo(final Handler handler, final String user_id,
			final String order_id, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_ORDER_INFO);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("order_id", order_id);
		LogUtil.e("获取订单详情:" + PathCommonDefines.GET_ORDER_INFO + "?user_id="
				+ user_id + "&order_id=" + order_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject json = new JSONObject(result);
					if (json != null) {
						int code = json.optInt("code");
						if (code == 1) {
							//
							JSONObject info = json.getJSONObject("info");
							if (info != null) {
								String recode = info.optString("recode");
								String redesc = info.optString("redesc");
								if (!info.isNull("order")) {

									JSONObject order = info
											.getJSONObject("order");
									OrderForm of = OrderJson.getInstance()
											.analyzeOrderInfo(order);

									Message msg = new Message();
									Bundle bun = new Bundle();
									bun.putString("recode", recode);
									bun.putString("redesc", redesc);
									bun.putParcelable("order", of);
									msg.setData(bun);
									msg.what = flag1;
									handler.sendMessage(msg);
								}
							} else {
								handler.sendEmptyMessage(flag2);
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
	 * @description:获取物流信息
	 */
	public void getLogisticsInfo(final Handler handler, final String url,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(url);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {

					LogisticsInfo logisticsInfo = OrderJson.getInstance()
							.analyzeLogisticInfo(result);

					Message msg = new Message();
					Bundle bun = new Bundle();
					bun.putParcelable("logisticsInfo", logisticsInfo);
					msg.setData(bun);
					msg.what = flag1;
					handler.sendMessage(msg);
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
	 * @description:取消订单
	 */
	public void cancelOrder(final Handler handler, final String user_id,
			final String order_id, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.CANCEL_ORDER);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("order_id", order_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
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
								handler.sendEmptyMessage(flag2);
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
	 * @description:取消订单
	 */
	public void applyAfterSale(final Handler handler, final String user_id,
			final String order_id, final String orderAttire_id,
			final String mes_user, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.APPLYAFTERSALE);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("order_id", order_id);
		params.addBodyParameter("orderAttire_id", orderAttire_id);
		params.addBodyParameter("mes_user", mes_user);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject json = new JSONObject(result);
					if (json != null) {
						int code = json.optInt("code");
						if (code == 1) {
							//
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
								handler.sendEmptyMessage(flag2);
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
	 * @description:确认收货
	 */
	public void receiptGoods(final Handler handler, final String user_id,
			final String order_id, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.RECEIPT_GOODS);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("order_id", order_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject json = new JSONObject(result);
					if (json != null) {
						int code = json.optInt("code");
						if (code == 1) {
							//
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
								handler.sendEmptyMessage(flag2);
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
	 * @description: 申请退货
	 */
	public void shenQingTuiHuo(final Handler handler, final String user_id,
			final String order_id, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.SHENQINGTUIHUO);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("order_id", order_id);
		LogUtil.e(PathCommonDefines.SHENQINGTUIHUO + "?user_id=" + user_id
				+ "&order_id=" + order_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject json = new JSONObject(result);
					if (json != null) {
						int code = json.optInt("code");
						if (code == 1) {
							//
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
								handler.sendEmptyMessage(flag2);
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
	 * @description:删除购物车中的商品
	 */
	public void deleteShoppingCart(final Handler handler, final String user_id,
			final String orderAttire_id, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.DELETE_SHOPPINGCART);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("orderAttire_id", orderAttire_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
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
	 * @description:提交购物车商品
	 */
	public void submitShoppingCartInfo(final Handler handler,
			final String user_id, final String orderAttire_id, final int flag1,
			final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.SUBMIT_SHOPPINGCART);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("orderAttire_id", orderAttire_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				String order_id = "", yqTotal = "", priceTotal = "", freightShow = "", users_availabe = "", users_yqPoints = "";
				ArrayList<MSTJData> mstjList = null;
				try {
					JSONObject json = new JSONObject(result);
					if (json != null) {

						if (!json.isNull("info")) {
							JSONObject info = json.getJSONObject("info");
							String recode = info.optString("recode");
							String redesc = info.optString("redesc");
							order_id = info.optString("order_id");
							yqTotal = info.optString("yqTotal");
							priceTotal = info.optString("priceTotal");
							freightShow = info.optString("freightShow");
							users_yqPoints = info.optString("users_yqPoints");
							users_availabe = info.optString("users_availabe");
							if (!info.isNull("orderAttire")) {

								JSONArray orderAttire = info
										.getJSONArray("orderAttire");

								if (orderAttire != null) {
									mstjList = OrderJson.getInstance()
											.analyzeShoppingList(orderAttire);
								}
							}

							Message msg = new Message();
							Bundle bun = new Bundle();
							bun.putString("order_id", order_id);
							bun.putString("recode", recode);
							bun.putString("redesc", redesc);
							bun.putString("yqTotal", yqTotal);
							bun.putString("freightShow", freightShow);
							bun.putString("users_yqPoints", users_yqPoints);
							bun.putString("users_availabe", users_availabe);
							bun.putString("priceTotal", priceTotal);
							bun.putParcelableArrayList("mstjList", mstjList);
							msg.setData(bun);
							msg.what = flag1;
							handler.sendMessage(msg);
						} else {
							handler.sendEmptyMessage(flag2);
						}
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
	 * @description:编辑购物车商品
	 */
	public void editShoppingCartInfo(final Handler handler,
			final String user_id, final String orderAttire_id,
			final String num, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.EDIT_SHOPPINGCART);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("orderAttire_id", orderAttire_id);
		params.addBodyParameter("num", num);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject json = new JSONObject(result);
					if (json != null) {
						int code = json.optInt("code");
						if (code == 1) {

							JSONObject info = json.getJSONObject("info");
							if (info != null && !"null".equals(info)) {
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
								handler.sendEmptyMessage(flag2);
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
}
