package cn.dressbook.ui.net;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.model.GuangGao;
import cn.dressbook.ui.model.LSDZFL;

/**
 * @description: 配置相关
 * @author:ydh
 * @data:2016-4-28下午3:08:26
 */
public class PeiZhiExec {
	private static PeiZhiExec mInstance = null;

	private PeiZhiExec() {
	}

	public static PeiZhiExec getInstance() {
		if (mInstance == null) {
			mInstance = new PeiZhiExec();
		}
		return mInstance;
	}

	/**
	 * @description:获取定制分类按钮
	 */
	public void getAnNiu(final Handler handler, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.PEIZHI);
		params.addBodyParameter("code", "ggcx_btn");
		LogUtil.e(PathCommonDefines.PEIZHI + "?code=" + "ggcx_btn");
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				//
				try {
					JSONObject json = new JSONObject(result);
					if (!json.isNull("info")) {
						ArrayList<LSDZFL> flList = new ArrayList<LSDZFL>();
						JSONArray info = json.getJSONArray("info");
						for (int i = 0; i < info.length(); i++) {
							LSDZFL fl = new LSDZFL();
							fl.setDzCls_pic(PathCommonDefines.SERVER_IMAGE_ADDRESS
									+ info.optString(i));
							flList.add(fl);
						}
						Message msg = new Message();
						Bundle bundle = new Bundle();
						bundle.putParcelableArrayList("list", flList);
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
}
