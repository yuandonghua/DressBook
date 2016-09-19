package cn.dressbook.ui.net;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.json.XFJLJson;
import cn.dressbook.ui.model.DianPu;
import cn.dressbook.ui.model.JYJL;
import cn.dressbook.ui.model.JiaoYiJiLuBiaoZhi;
import cn.dressbook.ui.model.XFJL;
import cn.dressbook.ui.model.YSJL;

/**
 * @description 交易记录相关
 * @author 袁东华
 * @date 2016-2-19
 */
public class JiaoYiJiLuExec {
	private static JiaoYiJiLuExec mJiaoYiJiLuExec;

	private JiaoYiJiLuExec() {
	}

	public static JiaoYiJiLuExec getInstance() {
		if (mJiaoYiJiLuExec == null) {
			mJiaoYiJiLuExec = new JiaoYiJiLuExec();
		}
		return mJiaoYiJiLuExec;
	}

	/**
	 * @description 店家收钱
	 */
	public void dianJiaShouQian(final Handler handler, final String user_id,
			final String order_code, final String franchisee_id,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.DJSQ);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("order_code", order_code);
		params.addBodyParameter("franchisee_id", franchisee_id);
		LogUtil.e(PathCommonDefines.DJSQ + "?user_id=" + user_id
				+ "&order_code=" + order_code + "&franchisee_id="
				+ franchisee_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				LogUtil.e("result:" + result);
				try {
					JSONObject json = new JSONObject(result);
					if (!json.isNull("info")) {
						JSONObject info = json.getJSONObject("info");
						String recode = info.optString("recode");
						String redesc = info.optString("redesc");
						Message msg = new Message();
						Bundle bundle = new Bundle();
						bundle.putString("recode", recode);
						bundle.putString("redesc", redesc);
						msg.setData(bundle);
						msg.what = flag1;
						handler.sendMessage(msg);
					}
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
	 * @description 获取店家收钱列表
	 */
	public void getDianJiaShouQianList(final Handler handler,
			final String user_id, final int curPage, final int pageSize,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_DJSQ_LIST);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("curPage", curPage + "");
		params.addBodyParameter("pageSize", pageSize + "");
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					ArrayList<JYJL> jyjlList = new ArrayList<JYJL>();
					JSONObject json = new JSONObject(result);
					if (!json.isNull("info")) {
						JSONObject info = json.getJSONObject("info");
						if (!info.isNull("mbShopCollects")) {
							JSONArray mbShopCollects = info
									.getJSONArray("mbShopCollects");
							for (int i = 0; i < mbShopCollects.length(); i++) {
								JSONObject obj = mbShopCollects
										.getJSONObject(i);
								if (obj != null) {
									JYJL jyjl = new JYJL();
									jyjl.setVary(obj.optString("income"));
									jyjl.setAddTime(obj.optString("addTime"));
									jyjl.setReson(obj.optString("reson"));
									jyjl.setDirection(obj
											.optString("orderCode"));
									jyjl.setBalance(obj.optString("userName"));
									jyjlList.add(jyjl);

								}
							}
						}
					}
					Message msg = new Message();
					Bundle bundle = new Bundle();
					bundle.putParcelableArrayList("list", jyjlList);
					msg.setData(bundle);
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
	 * @description 获取提现记录列表
	 */
	public void getTiXianJiLuList(final Handler handler, final String mb_id,
			final int curPage, final int pageSize, final int flag1,
			final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GETTIXIANJILULIST);
		params.addBodyParameter("mb_id", mb_id);
		params.addBodyParameter("curPage", curPage + "");
		params.addBodyParameter("pageSize", pageSize + "");
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					ArrayList<JYJL> jyjlList = new ArrayList<JYJL>();
					JSONObject json = new JSONObject(result);
					if (!json.isNull("info")) {
						JSONObject info = json.getJSONObject("info");
						if (!info.isNull("transList")) {
							JSONArray transList = info
									.getJSONArray("transList");
							for (int i = 0; i < transList.length(); i++) {
								JSONObject obj = transList.getJSONObject(i);
								if (obj != null) {
									JYJL jyjl = new JYJL();
									jyjl.setVary(obj.optString("amount"));
									String disposeCode = obj
											.optString("disposeCode");
									if ("0".equals(disposeCode)) {

										jyjl.setReson("待处理");
									} else if ("1".equals(disposeCode)) {

										jyjl.setReson("转账完成");
									} else if ("2".equals(disposeCode)) {

										jyjl.setReson("申请退回");
									}
									jyjl.setAddTime(obj
											.optString("addTimeShow"));
									jyjlList.add(jyjl);

								}
							}
						}
					}
					Message msg = new Message();
					Bundle bundle = new Bundle();
					bundle.putParcelableArrayList("list", jyjlList);
					msg.setData(bundle);
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
	 * @description 申请余额提现
	 */
	public void shenQinYuETiXian(final Handler handler, final String mb_id,
			final String alipay, final String alipayName, final String amount,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.SHENQINYUETIXIAN);
		params.addBodyParameter("mb_id", mb_id);
		params.addBodyParameter("alipay", alipay);
		params.addBodyParameter("alipayName", alipayName);
		params.addBodyParameter("amount", amount);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {

					JSONObject json = new JSONObject(result);
					if (!json.isNull("info")) {
						JSONObject info = json.getJSONObject("info");
						String recode = info.optString("recode");
						if ("0".equals(recode)) {
							handler.sendEmptyMessage(flag1);
						} else {
							handler.sendEmptyMessage(flag2);
						}
					}
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
	 * @description 获取预期收入列表
	 */
	public void getYQSRList(final Handler handler, final String user_id,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_YQSR_LIST);
		params.addBodyParameter("user_id", user_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				LogUtil.e("result:" + result);
				ArrayList<JYJL> list = new ArrayList<JYJL>();
				String totalIncome = "";
				try {
					JSONObject json = new JSONObject(result);
					if (!json.isNull("info")) {

						JSONObject info = json.getJSONObject("info");
						String recode = info.optString("recode");
						if ("2".equals(recode)) {

						} else {

							totalIncome = info.optString("totalIncome");
							if (!info.isNull("expectIncome")) {
								JSONArray expectIncome = info
										.getJSONArray("expectIncome");
								for (int i = 0; i < expectIncome.length(); i++) {
									JSONObject obji = expectIncome
											.getJSONObject(i);
									if (obji != null) {
										JYJL jyjl = new JYJL();
										jyjl.setAddTime(obji.optString("time"));
										jyjl.setReson(obji.optString("reson"));
										jyjl.setVary(obji.optString("vary"));
										list.add(jyjl);
									}
								}

							}
						}

					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
				Bundle bun = new Bundle();
				Message msg = new Message();
				bun.putString("totalIncome", totalIncome);
				bun.putParcelableArrayList("list", list);
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
	 * @description:获取交易记录
	 * @exception
	 */
	public void getJiaoYiJiLuList(final Handler handler, final String user_id,
			final String direction, final int curPage, final int pageSize,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_JYJL_LIST);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("reson", direction);
		params.addBodyParameter("curPage", curPage + "");
		params.addBodyParameter("pageSize", pageSize + "");
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				LogUtil.e("result:" + result);
				ArrayList<JYJL> list = new ArrayList<JYJL>();
				try {
					JSONObject json = new JSONObject(result);
					if (!json.isNull("info")) {
						JSONObject info = json.getJSONObject("info");
						if (!info.isNull("mbLogs")) {
							JSONArray mbLogs = info.getJSONArray("mbLogs");
							for (int i = 0; i < mbLogs.length(); i++) {
								JSONObject obji = mbLogs.getJSONObject(i);
								if (obji != null) {
									JYJL jyjl = new JYJL();
									jyjl.setAddTime(obji.optString("addTime"));
									jyjl.setBalance(obji.optString("balance"));
									jyjl.setReson(obji.optString("reson"));
									jyjl.setVary(obji.optString("vary"));
									jyjl.setDirection(obji
											.optString("direction"));
									list.add(jyjl);
								}
							}

						}

					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
				Bundle bun = new Bundle();
				Message msg = new Message();
				bun.putParcelableArrayList("list", list);
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
	 * @description:获取营收记录
	 * @exception
	 */
	public void getYingShouJiLuList(final Handler handler,
			final String franchisee_id, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_DPSQ_LIST);
		params.addBodyParameter("franchisee_id", franchisee_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				ArrayList<YSJL> list = new ArrayList<YSJL>();
				try {
					JSONObject json = new JSONObject(result);
					if (!json.isNull("info")) {
						JSONObject info = json.getJSONObject("info");
						if (!info.isNull("itemList")) {
							JSONArray mbLogs = info.getJSONArray("itemList");
							for (int i = 0; i < mbLogs.length(); i++) {
								JSONObject obji = mbLogs.getJSONObject(i);
								if (obji != null) {
									YSJL jyjl = new YSJL();
									jyjl.setAddTime(obji.optString("addTime"));
									jyjl.setUserName(obji.optString("userName"));
									jyjl.setReson(obji.optString("reson"));
									jyjl.setFranchiseeName(obji
											.optString("franchiseeName"));
									jyjl.setOrderCode(obji
											.optString("orderCode"));
									jyjl.setIncome(obji.optString("income"));
									jyjl.setIncomeType(obji
											.optString("incomeType"));
									list.add(jyjl);
								}
							}

						}

					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
				Bundle bun = new Bundle();
				Message msg = new Message();
				bun.putParcelableArrayList("list", list);
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
	 * @description 获取交易记录标识列表
	 */
	public void getJYJLBiaoZhiList(final Handler handler, final int flag1,
			final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_JYJLBZ_LIST);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					ArrayList<JiaoYiJiLuBiaoZhi> bzList = new ArrayList<JiaoYiJiLuBiaoZhi>();
					JSONObject jsonObject = new JSONObject(result);
					if (!jsonObject.isNull("info")) {
						JSONObject info = jsonObject.getJSONObject("info");

						if (!info.isNull("infos")) {
							JSONArray infos = info.getJSONArray("infos");
							for (int i = 0; i < infos.length(); i++) {
								JSONObject obj = infos.getJSONObject(i);
								if (obj != null) {
									JiaoYiJiLuBiaoZhi bz = new JiaoYiJiLuBiaoZhi();
									bz.setreason(obj.optString("reason"));
									bz.setName(obj.optString("name"));
									bzList.add(bz);
								}
							}
						}
					}
					Message msg = new Message();
					Bundle data = new Bundle();
					LogUtil.e("bzList个数:" + bzList.size());
					data.putParcelableArrayList("list", bzList);
					msg.setData(data);
					msg.what = flag1;
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
	 * @description 获取店铺列表
	 */
	public void getDianPuList(final Handler handler, final String employer_id,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.GET_DP_LIST);
		params.addBodyParameter("employer_id", employer_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					ArrayList<DianPu> bzList = new ArrayList<DianPu>();
					JSONObject jsonObject = new JSONObject(result);
					if (!jsonObject.isNull("info")) {
						JSONObject info = jsonObject.getJSONObject("info");

						if (!info.isNull("itemList")) {
							JSONArray itemList = info.getJSONArray("itemList");
							for (int i = 0; i < itemList.length(); i++) {
								JSONObject obj = itemList.getJSONObject(i);
								if (obj != null) {
									DianPu dp = new DianPu();
									dp.setfranchiseeId(obj
											.optString("franchiseeId"));
									dp.setfranchiseeName(obj
											.optString("franchiseeName"));
									dp.setAddTime(obj.optString("addTime"));
									bzList.add(dp);
								}
							}
						}
					}
					Message msg = new Message();
					Bundle data = new Bundle();
					data.putParcelableArrayList("list", bzList);
					msg.setData(data);
					msg.what = flag1;
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
}
