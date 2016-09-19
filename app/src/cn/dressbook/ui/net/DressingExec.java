package cn.dressbook.ui.net;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.dressbook.ui.common.PathCommonDefines;

/**
 * @description: 着装
 * @author:袁东华
 * @time:2015-9-25下午4:06:04
 */
public class DressingExec {
	private static DressingExec mInstance = null;

	public static DressingExec getInstance() {
		if (mInstance == null) {
			mInstance = new DressingExec();
		}
		return mInstance;
	}

	/**
	 * @description:获取诊断结果
	 */
	public void getDiagnoseResult(final Handler handler, final String user_id,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_DIAGNOSE_RESULT);
		params.addBodyParameter("user_id", user_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				JSONObject json;
				String color_result = null;
				try {
					json = new JSONObject(result);

					if (json != null) {
						JSONObject info = json.getJSONObject("info");
						if (info != null) {
							color_result = info.optString("color_result");

						}

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Bundle data = new Bundle();
				data.putString("url", color_result);
				Message msg = new Message();
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
}
