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
import cn.dressbook.ui.model.XFJL;

/**
 * @description:衣保金系列
 * @author:袁东华
 * @modifier:袁东华
 * @remarks:
 * @2015年7月21日 上午10:37:28
 */
public class XFJLExec {
	private static XFJLExec mInstance = null;

	private XFJLExec() {
	};

	public static XFJLExec getInstance() {
		if (mInstance == null) {
			mInstance = new XFJLExec();
		}
		return mInstance;
	}

	/**
	 * @description:获取消费记录
	 * @exception
	 */
	public void getXFJL(final Handler handler, final String user_id,
			final String type, final int page_num, final int page_size,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_EXPENSE_RECORD);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("type", type);
		params.addBodyParameter("page_num", page_num + "");
		params.addBodyParameter("page_size", page_size + "");
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				ArrayList<XFJL> xfjlList = null;
				try {
					JSONObject json = new JSONObject(result);
					if (!json.isNull("info")) {
						JSONArray info = json.getJSONArray("info");
						xfjlList = XFJLJson.getInstance().analysisXFJL(info);

					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
				Bundle bun = new Bundle();
				Message msg = new Message();
				bun.putParcelableArrayList("list", xfjlList);
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

}
