package cn.dressbook.ui.net;

import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.common.OrdinaryCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.model.User;

/**
 * @description 和用户有关的请求
 * @author 袁东华
 * @date 2014-7-22 下午5:16:06
 */
public class UserExec {
	public final static String MESSAGE = "message";
	private static UserExec mInstance = null;

	public static UserExec getInstance() {
		if (mInstance == null) {
			mInstance = new UserExec();
		}
		return mInstance;
	}

	/**
	 * 上传用户的通讯录
	 * 
	 * @param handler
	 * @param user_id
	 * @param pbk
	 * @param flag1
	 * @param flag2
	 */
	public void uploadTXL(final Handler handler, final String user_id,
			final String pbk, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.CONTACTS);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("pbk", pbk);
		// LogUtil.e(PathCommonDefines.CONTACTS + "?user_id=" + user_id +
		// "&pbk="
		// + pbk);
		x.http().post(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				// LogUtil.e(result);
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
	 * @description: 扫描邀请码
	 * @exception
	 */
	public void saoYQM(final Handler handler, final String user_id,
			final String shareCode, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.SAO_YQM);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("shareCode", shareCode);
		LogUtil.e(PathCommonDefines.SAO_YQM + "?user_id=" + user_id
				+ "&shareCode=" + shareCode);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				String redesc = "绑定成功";
				try {
					JSONObject obj = new JSONObject(result);
					if (!obj.isNull("info")) {

						JSONObject info = obj.getJSONObject("info");
						redesc = info.optString("redesc");

					} else {
						handler.sendEmptyMessage(flag2);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					handler.sendEmptyMessage(flag2);
				}
				Message msg = new Message();
				Bundle data = new Bundle();
				data.putString("redesc", redesc);
				msg.setData(data);
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
	 * @description:获取用户的支付宝信息
	 * @exception
	 */
	public void getUserAlipayInfo(final Handler handler, final String user_id,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.GET_USER_ZFB);
		params.addBodyParameter("user_id", user_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject obj = new JSONObject(result);
					int code = obj.optInt("code");
					if (code == 1) {
						JSONObject info = obj.getJSONObject("info");
						if (info != null) {
							String mobile_phone = info
									.optString("mobile_phone");
							String alipay = info.optString("alipay");
							String alipay_name = info.optString("alipay_name");
							Message msg = new Message();
							Bundle data = new Bundle();
							data.putString("mobile_phone", mobile_phone);
							data.putString("alipay", alipay);
							data.putString("alipay_name", alipay_name);
							msg.setData(data);
							msg.what = flag1;
							handler.sendMessage(msg);
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
	 * @description:获取用户的衣保计划
	 * @exception
	 */
	public void getUserYBPlan(final Handler handler, final String user_id,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.GET_YBPLAN);
		params.addBodyParameter("user_id", user_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject obj = new JSONObject(result);
					int code = obj.optInt("code");
					if (code == 1) {
						JSONObject info = obj.getJSONObject("info");
						if (info != null) {
							String yq_points = info.optString("yq_points");
							String yb_base_points = info
									.optString("yb_base_points");
							String yb_points = info.optString("yb_points");
							String yb_start_date = info
									.optString("yb_start_date");
							String yb_end_date = info.optString("yb_end_date");
							Message msg = new Message();
							Bundle data = new Bundle();
							data.putString("yq_points", yq_points);
							data.putString("yb_base_points", yb_base_points);
							data.putString("yb_points", yb_points);
							data.putString("yb_start_date", yb_start_date);
							data.putString("yb_end_date", yb_end_date);
							msg.setData(data);
							msg.what = flag1;
							handler.sendMessage(msg);
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
	 * @description:用户签到
	 * @exception
	 */
	public void signin(final Handler handler, final String user_id,
			final int flag1, final int flag2) {

		RequestParams params = new RequestParams(PathCommonDefines.SIGNIN);
		params.addBodyParameter("user_id", user_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject obj = new JSONObject(result);
					int code = obj.optInt("code");
					if (code == 1) {
						String info = obj.optString("info");
						Message msg = new Message();
						Bundle data = new Bundle();
						data.putString("result", info);
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
	 * @description:绑定用户支付宝信息
	 * @exception
	 */
	public void bindZFB(final Handler handler, final String user_id,
			final String mobile, final String alipay, final String alipayName,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.BIND_ZFB);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("mobile", mobile);
		params.addBodyParameter("alipay", alipay);
		params.addBodyParameter("alipayName", alipayName);
		x.http().get(params, new Callback.CommonCallback<String>() {
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
	 * @description:用户提交反馈
	 * @exception
	 */
	public void submitUserFeedback(final Handler handler, final String user_id,
			String content, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.SUBMIT_USER_FEEDBACK);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("content", content);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
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
	 * @description:获取用户信息
	 */
	public void getUserInfo(final Handler handler, final String user_id,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_USER_INFO);
		params.addBodyParameter("user_id", user_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject json = new JSONObject(result);
					if (!json.isNull("info")) {
						User user = new User();
						JSONObject info = json.optJSONObject("info");
						user.setId(info.optString("user_id"));
						user.setName(info.optString("user_name"));
						user.setAvatar(PathCommonDefines.SERVER_IMAGE_ADDRESS
								+ info.optString("avatar"));
					} else {
						handler.sendEmptyMessage(flag2);
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
	 * @description: 根据用户手机号获取用户信息
	 */
	public void getUserByPhone(final Handler handler, final String phone,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GETUSERBYPHONE);
		params.addBodyParameter("phone", phone);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject json = new JSONObject(result);
					if (!json.isNull("user")) {
						LogUtil.e("用户不为空");
						User user = new User();
						JSONObject info = json.optJSONObject("user");
						user.setId(info.optString("userId"));
						user.setName(info.optString("userName"));
						user.setPhone(info.optString("mobilePhone"));
						user.setAvatar(PathCommonDefines.SERVER_IMAGE_ADDRESS
								+ info.optString("avatar"));
						Message msg = new Message();
						Bundle data = new Bundle();
						data.putParcelable("user", user);
						msg.setData(data);
						msg.what = flag1;
						handler.sendMessage(msg);
					} else {
						LogUtil.e("用户为空");
						handler.sendEmptyMessage(flag2);
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
	 * 修改用户名称
	 */
	public void xiuGaiUserName(final Context mContext, final Handler handler,
			String user_id, final String mobile_phone, final String password,
			final String username, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.API_GET_MODIFY_USER_NAME);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("mobile_phone", mobile_phone);
		params.addBodyParameter("password", password);
		params.addBodyParameter("username", username);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject json = new JSONObject(result);
					if (json != null) {
						JSONObject info = json.getJSONObject("info");
						if (info != null) {
							String user_id = info.optString("user_id");
							String user_name = info.optString("user_name");
							String mobile_phone = info
									.optString("mobile_phone");
							String avatar = info.optString("avatar");
							String recode = info.optString("recode");
							String redesc = info.optString("redesc");
							if (user_id != null && !"null".equals(user_id)) {
								ManagerUtils.getInstance().setUser_id(mContext,
										user_id);
							}
							if (user_name != null && !"null".equals(user_name)) {
								ManagerUtils.getInstance().setUserName(
										mContext, user_name);
							}
							if (mobile_phone != null
									&& !"null".equals(mobile_phone)) {
								ManagerUtils.getInstance().setPhoneNum(
										mContext, mobile_phone);
							}
							if (avatar != null && !"null".equals(avatar)) {
								ManagerUtils.getInstance().setUserHead(
										mContext,
										PathCommonDefines.SERVER_IMAGE_ADDRESS
												+ avatar);
							}
							Message msg = new Message();
							Bundle bun = new Bundle();
							bun.putString("recode", recode);
							bun.putString("redesc", redesc);
							msg.setData(bun);
							if ("6".equals(recode)) {
								// 成功
								msg.what = flag1;
								handler.sendMessage(msg);
							} else {
								msg.what = flag2;
								handler.sendMessage(msg);
							}

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
	 * @description:修改密码
	 */
	public void modifyPassword(final Context mContext, final Handler handler,
			String user_id, final String mobile_phone, final String password,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.MODIFY_PASSWORD);
		params.addBodyParameter("mobile_phone", mobile_phone);
		params.addBodyParameter("password", password);
		params.addBodyParameter("user_id", user_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject json = new JSONObject(result);
					if (json != null) {
						JSONObject info = json.getJSONObject("info");
						if (info != null) {
							String user_id = info.optString("user_id");
							String user_name = info.optString("user_name");
							String mobile_phone = info
									.optString("mobile_phone");
							String avatar = info.optString("avatar");
							String recode = info.optString("recode");
							String redesc = info.optString("redesc");
							if (user_id != null && !"null".equals(user_id)) {
								ManagerUtils.getInstance().setUser_id(mContext,
										user_id);
							}
							if (user_name != null && !"null".equals(user_name)) {
								ManagerUtils.getInstance().setUserName(
										mContext, user_name);
							}
							if (mobile_phone != null
									&& !"null".equals(mobile_phone)) {
								ManagerUtils.getInstance().setPhoneNum(
										mContext, mobile_phone);
							}
							if (avatar != null && !"null".equals(avatar)) {
								ManagerUtils.getInstance().setUserHead(
										mContext,
										PathCommonDefines.SERVER_IMAGE_ADDRESS
												+ avatar);
							}
							Message msg = new Message();
							Bundle bun = new Bundle();
							bun.putString("recode", recode);
							bun.putString("redesc", redesc);
							msg.setData(bun);
							if ("4".equals(recode)) {
								// 成功
								msg.what = flag1;
								handler.sendMessage(msg);
							} else {
								msg.what = flag2;
								handler.sendMessage(msg);
							}

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
	 * 初始化用户
	 * 
	 * @param mContext
	 * @param handler
	 * @param mobile_phone
	 * @param password
	 * @param device_code
	 * @param device_name
	 * @param flag1
	 * @param flag2
	 */
	public void initUser(final Context mContext, final Handler handler,
			String mobile_phone, String password, String device_code,
			String device_name, String appver, String albc, final int flag1,
			final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.INITUSER);
		params.addBodyParameter("mobile_phone", mobile_phone);
		params.addBodyParameter("password", password);
		params.addBodyParameter("device_code", device_code);
		params.addBodyParameter("device_name", device_name);
		params.addBodyParameter("appver", "A_" + appver);
		params.addBodyParameter("albc", albc);
		LogUtil.e("刷新用户信息:" + PathCommonDefines.INITUSER + "?mobile_phone="
				+ mobile_phone + "&password=" + password + "&device_code="
				+ device_code + "&device_name=" + device_name + "&appver="
				+ "A_" + appver + "&albc=yes");
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject json = new JSONObject(result);
					if (json != null) {
						JSONObject info = json.getJSONObject("info");
						if (info != null) {
							String superior_id = info.optString("superior_id");
							ManagerUtils.getInstance().setShangXianId(mContext,
									superior_id);

							String employer_id = info.optString("employer_id");
							if (employer_id != null
									&& !"null".equals(employer_id)) {
								ManagerUtils.getInstance().setDjId(employer_id);
							}
							String vip = info.optString("vip");
							if (vip != null && !"null".equals(vip)) {
								ManagerUtils.getInstance().setShenFen(mContext,
										vip);
							}
							String user_id = info.optString("user_id");
							if (user_id != null && !"null".equals(user_id)) {
								ManagerUtils.getInstance().setUser_id(mContext,
										user_id);
							}
							String user_name = info.optString("user_name");
							if (user_name != null && !"null".equals(user_name)) {
								ManagerUtils.getInstance().setUserName(
										mContext, user_name);
							}
							String mobile_phone = info
									.optString("mobile_phone");
							if (mobile_phone != null
									&& !"null".equals(mobile_phone)) {
								ManagerUtils.getInstance().setPhoneNum(
										mContext, mobile_phone);
							}
							String avatar = info.optString("avatar");
							if (avatar != null && !"null".equals(avatar)) {
								ManagerUtils.getInstance().setUserHead(
										mContext,
										PathCommonDefines.SERVER_IMAGE_ADDRESS
												+ avatar);
							}
							String try_result_save = info
									.optString("try_result_save");
							if (try_result_save != null) {
								ManagerUtils.getInstance().setTry_result_save(
										mContext, try_result_save);
							}
							String model_update_date = info
									.optString("model_update_date");
							String wardrobe_time = info
									.optString("wardrobe_time");
							String wardrobe_id = info.optString("wardrobe_id");
							ManagerUtils.getInstance().setWardrobeId(mContext,
									wardrobe_id);
							Message msg = new Message();
							Bundle data = new Bundle();
							data.putString("model_update_date",
									model_update_date);
							data.putString("wardrobe_time", wardrobe_time);
							msg.setData(data);
							msg.what = flag1;
							handler.sendMessage(msg);

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
	 * @description:登陆
	 */
	public void login(final Context mContext, final Handler handler,
			String mobile_phone, String password, String device_code,
			String device_name, String appver, String albc, final int flag1,
			final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.LOGIN);
		params.addBodyParameter("mobile_phone", mobile_phone);
		params.addBodyParameter("password", password);
		params.addBodyParameter("device_code", device_code);
		params.addBodyParameter("device_name", device_name);
		params.addBodyParameter("appver", "A_" + appver);
		params.addBodyParameter("albc", albc);
		LogUtil.e(PathCommonDefines.LOGIN + "?mobile_phone=" + mobile_phone
				+ "&password=" + password + "&device_code=" + device_code
				+ "&device_name=" + device_name + "&appver=A_" + appver
				+ "&albc=" + albc);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				LogUtil.e(result);
				try {
					JSONObject json = new JSONObject(result);
					if (json != null) {
						JSONObject info = json.getJSONObject("info");
						if (info != null) {
							String employer_id = info.optString("employer_id");
							if (employer_id != null
									&& !"null".equals(employer_id)) {
								ManagerUtils.getInstance().setDjId(employer_id);
							}
							String vip = info.optString("vip");
							if (vip != null && !"null".equals(vip)) {
								ManagerUtils.getInstance().setShenFen(mContext,
										vip);
							}
							String user_id = info.optString("user_id");
							String user_name = info.optString("user_name");
							String mobile_phone = info
									.optString("mobile_phone");
							String avatar = info.optString("avatar");
							String recode = info.optString("recode");
							String redesc = info.optString("redesc");
							if (user_id != null && !"null".equals(user_id)) {
								ManagerUtils.getInstance().setUser_id(mContext,
										user_id);
							}
							if (user_name != null && !"null".equals(user_name)) {
								ManagerUtils.getInstance().setUserName(
										mContext, user_name);
							}
							if (mobile_phone != null
									&& !"null".equals(mobile_phone)) {
								ManagerUtils.getInstance().setPhoneNum(
										mContext, mobile_phone);
							}
							if (avatar != null && !"null".equals(avatar)) {
								ManagerUtils.getInstance().setUserHead(
										mContext,
										PathCommonDefines.SERVER_IMAGE_ADDRESS
												+ avatar);
							}
							Message msg = new Message();
							Bundle bun = new Bundle();
							bun.putString("recode", recode);
							bun.putString("redesc", redesc);
							msg.setData(bun);
							if ("4".equals(recode)) {
								// 成功
								msg.what = flag1;
								handler.sendMessage(msg);
							} else {
								msg.what = flag2;
								handler.sendMessage(msg);
							}

						}
					}
				} catch (JSONException e) {
					Message msg = new Message();
					Bundle bun = new Bundle();
					bun.putString("redesc",
							OrdinaryCommonDefines.SERVER_EXCEPTION);
					msg.setData(bun);
					// 成功
					msg.what = flag2;
					handler.sendMessage(msg);
				}

			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				Message msg = new Message();
				Bundle bun = new Bundle();
				bun.putString("redesc", OrdinaryCommonDefines.SERVER_EXCEPTION);
				msg.setData(bun);
				// 成功
				msg.what = flag2;
				handler.sendMessage(msg);
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
	 * @description:注册
	 */
	public void register(final Activity mContext, final Handler handler,
			String mobile_phone, String password, String device_code,
			String device_name, String appver, String albc, String share_code,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.REGISTER);
		params.addBodyParameter("mobile_phone", mobile_phone);
		params.addBodyParameter("password", password);
		params.addBodyParameter("device_code", device_code);
		params.addBodyParameter("device_name", device_name);
		params.addBodyParameter("appver", "A_" + appver);
		params.addBodyParameter("albc", albc);
		params.addBodyParameter("share_code", share_code);
		LogUtil.e(PathCommonDefines.REGISTER + "?mobile_phone=" + mobile_phone
				+ "&password=" + password + "&device_code=" + device_code
				+ "&device_name=" + device_name + "&appver=A_" + appver
				+ "&albc=" + albc + "&share_code=" + share_code);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				LogUtil.e(result);
				try {
					JSONObject json = new JSONObject(result);
					if (json != null) {
						JSONObject info = json.getJSONObject("info");
						if (info != null) {
							String superior_id = info.optString("superior_id");
							ManagerUtils.getInstance().setShangXianId(mContext,
									superior_id);

							String employer_id = info.optString("employer_id");
							if (employer_id != null
									&& !"null".equals(employer_id)) {
								ManagerUtils.getInstance().setDjId(employer_id);
							}
							String vip = info.optString("vip");
							if (vip != null && !"null".equals(vip)) {
								ManagerUtils.getInstance().setShenFen(mContext,
										vip);
							}
							String user_id = info.optString("user_id");
							String user_name = info.optString("user_name");
							String mobile_phone = info
									.optString("mobile_phone");
							String avatar = info.optString("avatar");
							String recode = info.optString("recode");
							String redesc = info.optString("redesc");
							if (user_id != null && !"null".equals(user_id)) {
								ManagerUtils.getInstance().setUser_id(mContext,
										user_id);
							}
							if (user_name != null && !"null".equals(user_name)) {
								ManagerUtils.getInstance().setUserName(
										mContext, user_name);
							}
							if (mobile_phone != null
									&& !"null".equals(mobile_phone)) {
								ManagerUtils.getInstance().setPhoneNum(
										mContext, mobile_phone);
							}
							if (avatar != null && !"null".equals(avatar)) {
								ManagerUtils.getInstance().setUserHead(
										mContext,
										PathCommonDefines.SERVER_IMAGE_ADDRESS
												+ avatar);
							}
							Message msg = new Message();
							Bundle bun = new Bundle();
							bun.putString("recode", recode);
							bun.putString("redesc", redesc);
							msg.setData(bun);
							if ("4".equals(recode)) {
								// 成功
								msg.what = flag1;
								handler.sendMessage(msg);
							} else {
								msg.what = flag2;
								handler.sendMessage(msg);
							}

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
