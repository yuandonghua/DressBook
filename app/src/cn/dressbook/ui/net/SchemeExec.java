package cn.dressbook.ui.net;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.json.AttireSchemeJson;
import cn.dressbook.ui.model.AttireScheme;
import cn.dressbook.ui.model.GuangGao;
import cn.dressbook.ui.utils.FileSDCacher;
import cn.dressbook.ui.utils.UserUtils;

public class SchemeExec {
	private static SchemeExec mInstance = null;

	public static SchemeExec getInstance() {
		if (mInstance == null) {
			mInstance = new SchemeExec();
		}
		return mInstance;
	}

	/**
	 * @description:获取广告信息
	 */
	public void getGGInfo(final Handler handler, final String user_id,
			final String sex, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_GG_LBT_INFO);
		params.addBodyParameter("user_id", user_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				LogUtil.e("result:" + result);
				ArrayList<GuangGao> list = new ArrayList<GuangGao>();
				GuangGao gg;
				try {
					JSONObject json = new JSONObject(result);
					if (json != null) {
						// JSONObject info = json.getJSONObject("info");
						JSONArray info = json.getJSONArray("info");
						if (info != null) {
							for (int i = 0; i < info.length(); i++) {
								JSONObject job = info.getJSONObject(i);
								gg = new GuangGao();
								if (job != null) {
									gg.setPic(PathCommonDefines.SERVER_IMAGE_ADDRESS
											+ job.optString("pic"));
									gg.setUrl(job.optString("to_url"));
									gg.setTitle(job.optString("title"));
									list.add(gg);
								}
							}

						}

					}

				} catch (Exception e) {
					// TODO: handle exception
				}
				Message msg = new Message();
				Bundle bundle = new Bundle();
				bundle.putParcelableArrayList("list", list);
				msg.setData(bundle);
				msg.what = flag1;
				handler.sendMessage(msg);

			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				handler.sendEmptyMessage(flag2);
				Log.i("xx", "执行到失败获取数据：" + ex);
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
	 * @description:获取定制界面广告
	 */
	public void getGuangGao(final Handler handler, final int flag1,
			final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.PEIZHI);
		params.addBodyParameter("code", "ggcx_ad_slide3");
		LogUtil.e(PathCommonDefines.PEIZHI + "?code=" + "ggcx_ad_slide3");
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				//
				try {
					JSONObject json = new JSONObject(result);
					if (!json.isNull("info")) {
						ArrayList<GuangGao> list = new ArrayList<GuangGao>();
						JSONArray info = json.getJSONArray("info");
						for (int i = 0; i < info.length(); i++) {
							JSONObject job = info.getJSONObject(i);
							GuangGao gg = new GuangGao();
							gg.setPic(PathCommonDefines.SERVER_IMAGE_ADDRESS
									+ job.optString("pic"));
							gg.setUrl(job.optString("to_url"));
							gg.setTitle(job.optString("title"));
							list.add(gg);
						}
						Message msg = new Message();
						Bundle bundle = new Bundle();
						bundle.putParcelableArrayList("list", list);
						msg.setData(bundle);
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
	 * 从服务端获取衣保会员方案列表
	 * 
	 */
	public void getYBHYFromServer(final Handler handler, final String user_id,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.YBHY);
		params.addBodyParameter("user_id", user_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				ArrayList<AttireScheme> mstjList = null;
				String is_vip = null;
				try {
					JSONObject json = new JSONObject(result);
					if (json != null) {
						JSONObject info = json.getJSONObject("info");
						if (info != null) {
							is_vip = info.optString("is_vip");
							JSONArray arr_i = info.optJSONArray("attires");

							if (arr_i != null) {
								mstjList = new ArrayList<AttireScheme>();
								for (int j = 0; j < arr_i.length(); j++) {
									JSONObject attireSchemeObj = arr_i
											.optJSONObject(j);
									if (attireSchemeObj != null) {
										AttireScheme attireScheme = new AttireScheme();
										// 小图片
										attireScheme.setThume(PathCommonDefines.SERVER_IMAGE_ADDRESS
												+ attireSchemeObj
														.optString("thume"));
										if (attireSchemeObj.optString("modpic") != null
												&& !"".equals(attireSchemeObj
														.optString("modpic"))) {
											if (attireSchemeObj.optString(
													"modpic").endsWith("1.png")) {

												attireScheme.setModPic(PathCommonDefines.SERVER_IMAGE_ADDRESS
														+ attireSchemeObj
																.optString("modpic"));
											} else {
												attireScheme.setModPic(PathCommonDefines.SERVER_IMAGE_ADDRESS
														+ attireSchemeObj
																.optString("modpic")
														+ "/1.png");
											}
										} else {
											attireScheme.setModPic(null);
										}
										attireScheme
												.setYq_value(attireSchemeObj
														.optString("yq_value"));
										attireScheme.setDesc(attireSchemeObj
												.optString("adesc"));
										attireScheme.setCan_try(attireSchemeObj
												.optString("can_try"));
										attireScheme.setAttireId(attireSchemeObj
												.optString("attire_id"));
										attireScheme
												.setAttire_id_real(attireSchemeObj
														.optString("attire_id_real"));
										// 价格
										attireScheme
												.setShop_price(attireSchemeObj
														.optInt("shop_price"));
										attireScheme.setTbkLink(attireSchemeObj
												.optString("tbklink"));
										// 淘宝商品id
										attireScheme.setOpeniid(attireSchemeObj
												.optString("openiid"));

										mstjList.add(attireScheme);
									}

								}
							}
						}
					}

				} catch (Exception e) {
					// TODO: handle exception
				}
				Message msg = new Message();
				Bundle data = new Bundle();
				data.putParcelableArrayList("list", mstjList);
				data.putString("is_vip", is_vip);
				msg.what = flag1;
				msg.setData(data);
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
	 * 从服务端获取量身推荐方案列表
	 * 
	 */
	public void getLSTJFromServer(final Handler handler, final String user_id,
			final int wardrobe_id, final int flag1, final int flag2) {
		String url = UserUtils.getInstance().getWardrobeUrl(user_id,
				wardrobe_id);
		LogUtil.e("量身推荐的列表:" + url);
		RequestParams params = new RequestParams(url);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				FileSDCacher.createFile2(handler, result,
						PathCommonDefines.JSON_FOLDER + "/" + user_id, "w_"
								+ wardrobe_id + ".txt", flag1);

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
	 * @description:从SD卡获取量身推荐
	 */
	public void getLSTJFromSD(final Handler handler, final String user_id,
			final String wardrobeId, final int flag1, final int flag2) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				File file = new File(PathCommonDefines.JSON_FOLDER + "/"
						+ user_id + "/" + "w_" + wardrobeId + ".txt");
				if (file.exists()) {
					String json = FileSDCacher.ReadData(file);
					try {
						JSONObject obj = new JSONObject(json);
						if (!obj.isNull(user_id + "_albcAttires")) {
							JSONArray arr = obj.getJSONArray(user_id
									+ "_albcAttires");

							ArrayList<AttireScheme> attireSchemeList = AttireSchemeJson
									.getInstance().analysisALBCList(arr);
							Message msg = new Message();
							msg.what = flag1;
							Bundle bundle = new Bundle();
							bundle.putParcelableArrayList("list",
									attireSchemeList);
							msg.setData(bundle);
							handler.sendMessage(msg);
						}
					} catch (Exception e) {
						handler.sendEmptyMessage(flag2);
					}
				} else {
					handler.sendEmptyMessage(flag2);
				}
			}
		}).start();

	}

	/**
	 * 从本地获取衣柜中的着装方案列表
	 */
	public void getAttireSchemeList(final Handler handler,
			final String user_id, final int wardrobeId, final int flag1,
			final int flag2) {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				File file = new File(PathCommonDefines.JSON_FOLDER + "/"
						+ user_id + "/" + "w_" + wardrobeId + ".txt");
				if (file.exists()
						&& Environment.MEDIA_MOUNTED.equals(Environment
								.getExternalStorageState())) {
					String json = FileSDCacher.ReadData(file);
					try {

						JSONObject jsonObject = new JSONObject(json);
						if (jsonObject != null) {

							JSONArray infoArray = jsonObject
									.getJSONArray("info");
							if (infoArray != null) {
								// attireSchemeList = mAttireSchemeJson
								// .getAttireSchemeList(infoArray,
								// idsjson, status);

								Message msg = new Message();
								msg.what = flag1;
								Bundle bundle = new Bundle();
								// bundle.putParcelableArrayList(
								// AttireScheme.ATTIRE_SCHEME_LIST,
								// attireSchemeList);
								msg.setData(bundle);
								handler.sendMessage(msg);
							} else {
							}
						}
					} catch (Exception e) {

						handler.sendEmptyMessage(12);
						e.printStackTrace();
					}
				} else {

				}
			}
		});
		thread.start();

	}
}
