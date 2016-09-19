package cn.dressbook.ui.net;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.json.XFJLJson;
import cn.dressbook.ui.model.KeHu;
import cn.dressbook.ui.model.XFJL;

/**
 * @description 与会员相关
 * @author 袁东华
 * @date 2016-2-23
 */
public class HuiYuanExec {
	private static HuiYuanExec mInstance = null;

	private HuiYuanExec() {
	};

	public static HuiYuanExec getInstance() {
		if (mInstance == null) {
			mInstance = new HuiYuanExec();
		}
		return mInstance;
	}

	/**
	 * @description 获取会员帮助网页
	 */
	public void getHuiYuanHTML(final Handler handler,
			final String ggcx_mbabout, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.PEIZHI);
		params.addBodyParameter("code", ggcx_mbabout);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject json = new JSONObject(result);
					if (!json.isNull("info")) {
						String url = json.optString("info");
						Message msg = new Message();
						Bundle data = new Bundle();
						data.putString("url", url);
						msg.setData(data);
						msg.what = flag1;
						handler.sendMessage(msg);
					}
				} catch (Exception e) {
					// TODO: handle exception
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
