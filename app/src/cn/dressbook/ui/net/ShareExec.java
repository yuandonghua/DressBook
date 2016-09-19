package cn.dressbook.ui.net;

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

/**
 * @description: 与分享相关
 * @author:袁东华
 * @time:2015-11-5上午9:59:31
 */
public class ShareExec {
	private static ShareExec mInstance = null;

	public static ShareExec getInstance() {
		if (mInstance == null) {
			mInstance = new ShareExec();
		}
		return mInstance;
	}

	/**
	 * @description:获取分享信息
	 */
	public void getShareContent(final Handler handler, final String code,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.PEIZHI);
		params.addBodyParameter("code", code);
		LogUtil.e(PathCommonDefines.PEIZHI+"?code="+code);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				String title = "", url = "", param = "", pic = "", content = "";
				try {
					JSONObject json = new JSONObject(result);
					if (json != null) {
						if (!json.isNull("info")) {

							JSONObject info = json.getJSONObject("info");
							title = info.optString("title");
							url = info.optString("url");
							param = info.optString("param");
							pic = info.optString("pic");
							content = info.optString("content");
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Message msg = new Message();
				Bundle data = new Bundle();
				data.putString("title", title);
				data.putString("url", url);
				data.putString("param", param);
				data.putString("pic", pic);
				data.putString("content", content);
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
	 * @description:获取试衣收藏分享内容
	 */
	public void getShareTryOnContent(final Handler handler,
			final String userId, final String fileName, final int flag1,
			final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_SHARE_TRYON);
		params.addBodyParameter("userId", userId);
		params.addBodyParameter("fileName", fileName);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				String title = "", content = "", uploadPath = "", sharePath = "",pic="";
				try {
					JSONObject json = new JSONObject(result);
					if (json != null) {
						if (!json.isNull("info")) {

							JSONObject info = json.getJSONObject("info");
							title = info.optString("title");
							pic = info.optString("pic");
							content = info.optString("content");
							uploadPath = info.optString("uploadPath");
							sharePath = info.optString("sharePath");
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Message msg = new Message();
				Bundle data = new Bundle();
				data.putString("pic", pic);
				data.putString("title", title);
				data.putString("content", content);
				data.putString("uploadPath", uploadPath);
				data.putString("sharePath", sharePath);
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
}
