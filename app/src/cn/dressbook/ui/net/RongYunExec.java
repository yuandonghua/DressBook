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
 * 融云
 * 
 * @author 袁东华
 * 
 */
public class RongYunExec {
	private static RongYunExec mInstance = null;

	public static RongYunExec getInstance() {
		if (mInstance == null) {
			mInstance = new RongYunExec();
		}
		return mInstance;
	}

	/**
	 * @description:从服务端获取用户的token
	 * @exception
	 */
	public void getTokenFromServer(final Handler handler, final String userId,
			String name, final String portraitUri, final String product,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.GET_TOKEN);
		params.addBodyParameter("userId", "c" + userId);

		if (product != null && !"".equals(product)) {
			params.addBodyParameter("ver", product);
		}
		if (name == null || "".equals(name)) {
			name = "c" + userId;
		}
		params.addBodyParameter("name", name);
		if (portraitUri != null && !"".equals(portraitUri)) {
			params.addBodyParameter("portraitUri", portraitUri);
		}
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				LogUtil.e("获取token:"+result);
				try {
					JSONObject obj = new JSONObject(result);
					int code = obj.optInt("code");
					if (code == 1) {
						JSONObject info = obj.optJSONObject("info");
						if (info != null) {
							String token = info.optString("token");
							Bundle bun = new Bundle();
							Message msg = new Message();
							bun.putString("token", token);
							msg.setData(bun);
							msg.what = flag1;
							handler.sendMessage(msg);
						}

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
