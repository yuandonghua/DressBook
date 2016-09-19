package cn.dressbook.ui.net;

import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.common.PathCommonDefines;

/**
 * @description: 上传文件
 * @author:袁东华
 * @time:2015-11-12下午2:31:21
 */
public class UploadExec {
	private static UploadExec mInstance = null;

	public static UploadExec getInstance() {
		if (mInstance == null) {
			mInstance = new UploadExec();
		}
		return mInstance;
	}

	/**
	 * 上传单个文件
	 * 
	 * @param mContext
	 * @param handler
	 * @param user_id
	 * @param path
	 * @param flag1
	 * @param flag2
	 */
	public void uploadFile(final Handler handler, final String url,
			final String user_id, final String path, final int flag1,
			final int flag2) {
		RequestParams params = new RequestParams(url);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("uploadFile", new File(path));
		x.http().post(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				LogUtil.e(result);

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
	 * @description:上传试衣图片
	 */
	public void uploadTryOnImage(final Handler handler,
			final String uploadPath, final String uploadFile, final int flag1,
			final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.UPLOAD_TRYON_IMAGE);
		params.addBodyParameter("uploadPath", uploadPath);
		params.addBodyParameter("uploadFile", new File(uploadFile));
		x.http().post(params, new Callback.CommonCallback<String>() {
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
			}

			@Override
			public void onFinished() {

			}
		});

	}

	/**
	 * @description:上传衣柜文件
	 */
	public void uploadWardrobeFiles(final Handler handler,
			final String user_id, final String wardrobe_id, final int flag1,
			final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.UPLOAD_WARDROBE_FILE);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("wardrobe_id", wardrobe_id);
		params.addBodyParameter("uploadFile", new File(
				PathCommonDefines.WARDROBE_HEAD + "/head.0maskfaceneck.png"));
		params.addBodyParameter("uploadFile", new File(
				PathCommonDefines.WARDROBE_HEAD + "/head.0maskhead.png"));
		params.addBodyParameter("uploadFile", new File(
				PathCommonDefines.WARDROBE_HEAD + "/xingxiang.0"));
		params.addBodyParameter("uploadFile", new File(
				PathCommonDefines.WARDROBE_HEAD + "/head.0"));
		x.http().post(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					LogUtil.e(result);
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
	 * @description:上传用户头像
	 */
	public void uploadUserHead(final Context mContext, final Handler handler,
			final String user_id, final String path, final int flag1,
			final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.EDIT_USERHEADIMAGE);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("uploadFile", new File(path));
		x.http().post(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject json = new JSONObject(result);
					if (json != null) {
						LogUtil.e("json:" + json);
						JSONObject info = json.getJSONObject("info");
						if (info != null) {
							String avatar = info.optString("avatar");
							if (avatar != null && !avatar.equals("")
									&& !avatar.equals("null")) {
								ManagerUtils.getInstance().setUserHead(
										mContext,
										PathCommonDefines.SERVER_ADDRESS
												+ avatar);
								handler.sendEmptyMessage(flag1);
							}
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
}
