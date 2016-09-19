package cn.dressbook.ui.net;

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
 * @description: 短信
 * @author:袁东华
 * @time:2015-11-2下午1:02:40
 */
public class SMSExec {
	private static SMSExec mSMSExec;

	private SMSExec() {
	}

	public static SMSExec getInstance() {
		if (mSMSExec == null) {
			mSMSExec = new SMSExec();
		}
		return mSMSExec;
	}

	/**
	 * 发送验证码
	 * 
	 */
	public void faSongYanZhengMa(final Handler handler, String phone,
			String code, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.FSYZM);
		params.addBodyParameter("phone", phone);
		params.addBodyParameter("code", code);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				LogUtil.e("result:"+result);
				try {
					JSONObject jsonObject = new JSONObject(result);
					int code = jsonObject.optInt("code");
					if (code == 1) {
						handler.sendEmptyMessage(flag1);
					} else {
						handler.sendEmptyMessage(flag2);
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
	 * 发送验证码
	 * 
	 */
	public void faSongYanZhengMa2(final Handler handler, String phone,
			final String user_verify, String code, final int flag1,
			final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.FSYZM);
		params.addBodyParameter("phone", phone);
		params.addBodyParameter("code", code);
		params.addBodyParameter("user_verify", user_verify);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				LogUtil.e("result:"+result);
				try {
					JSONObject jsonObject = new JSONObject(result);
					int code = jsonObject.optInt("code");
					if (code == 1) {
						handler.sendEmptyMessage(flag1);
					} else {
						String re=jsonObject.getJSONObject("info").optString("result");
						Message msg=new Message();
						Bundle data=new Bundle();
						data.putString("result", re);
						msg.setData(data);
						msg.what=flag2;
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
}
