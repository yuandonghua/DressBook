package cn.dressbook.ui.net;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.model.Wardrobe;

public class WardrobeExec {
	private static WardrobeExec mInstance = null;

	public static WardrobeExec getInstance() {
		if (mInstance == null) {
			mInstance = new WardrobeExec();
		}
		return mInstance;
	}

	/**
	 * @description:修改用户资料
	 */
	public void editWardrobe(final Handler handler, final String user_id,
			final String wardrobe_id, final String key, final String value,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.EDIT_WARDROBE);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("wardrobe_id", wardrobe_id);
		params.addBodyParameter(key, value);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				String mid = "";
				try {
					JSONObject json = new JSONObject(result);
					if (json != null) {
						int code = json.optInt("code");
						if (code == 1) {

							JSONObject info = json.getJSONObject("info");
							if (info != null) {
								if (!info.isNull("userWardrobe")) {
									JSONObject userWardrobe = info
											.getJSONObject("userWardrobe");
									mid = userWardrobe.optString("mid");
								}
								Message msg = new Message();
								Bundle bun = new Bundle();
								bun.putString("mid", mid);
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
	 * @description:创建衣柜
	 */
	public void createWardrobe(final Handler handler, final String user_id,
			final String sex, final String birthday, final String height,
			final String weight, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.CREATE_WARDROBE);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("sex", sex);
		params.addBodyParameter("birthday", birthday);
		params.addBodyParameter("height", height);
		params.addBodyParameter("weight", weight);
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
	 * @description:获取衣柜信息
	 */
	public void getWardrobe(final Handler handler, final String user_id,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.GET_WARDROBE);
		params.addBodyParameter("user_id", user_id);
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
								JSONObject userWardrobe = info
										.getJSONObject("userWardrobe");
								Message msg = new Message();
								Bundle bun = new Bundle();
								Wardrobe w = new Wardrobe();
								if (userWardrobe != null) {
									// 衣柜ID
									int wardrobeId = userWardrobe
											.optInt("wardrobeId");
									w.setWardrobeId(wardrobeId);
									// 体型
									int mid = userWardrobe.optInt("mid");
									w.setShap(mid);
									// 文件目录
									String photo = userWardrobe
											.optString("photo");
									w.setPhoto(PathCommonDefines.SERVER_IMAGE_ADDRESS
											+ photo);
									// 性别
									int sex = userWardrobe.optInt("sex");
									w.setSex(sex);
									// 体重
									int weight = userWardrobe.optInt("weight");
									w.setWeight(weight);
									// 身高
									int height = userWardrobe.optInt("height");
									w.setHeight(height);
									// 腰围
									int waistline = userWardrobe
											.optInt("waistline");
									w.setWaistline(waistline);
									// 胸围
									int chest = userWardrobe.optInt("chest");
									w.setChest(chest);
									// 臀围
									int hipline = userWardrobe
											.optInt("hipline");
									w.setHipline(hipline);
									// 年龄
									int age = userWardrobe.optInt("age");
									w.setAge(age);
									// 颈围
									int jingwei = userWardrobe
											.optInt("jingwei");
									w.setJingwei(jingwei);
									// 肩宽
									int jiankuan = userWardrobe
											.optInt("jiankuan");
									w.setJiankuan(jiankuan);
									// 臂长
									int bichang = userWardrobe
											.optInt("bichang");
									w.setBichang(bichang);
									// 腕围
									int wanwei = userWardrobe.optInt("wanwei");
									w.setWanwei(wanwei);
									// 腿长
									int tuichang = userWardrobe
											.optInt("tuichang");
									w.setTuiChang(tuichang);
									// 生日
									String birthdayShow = userWardrobe
											.optString("birthdayShow");
									w.setBirthday(birthdayShow);
									// 脚长
									String foot = userWardrobe
											.optString("xiema");
									w.setFoot(foot);
								}

								bun.putParcelable("wardrobe", w);
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

}
