package cn.dressbook.ui.net;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import android.os.Handler;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.model.CustomService;

/**
 * @description 客服
 * @author 袁东华
 * @date 2014-10-13 上午11:31:31
 */
public class CustomSerciceExec {
	private static CustomSerciceExec mInstance = null;

	public static CustomSerciceExec getInstance() {
		if (mInstance == null) {
			mInstance = new CustomSerciceExec();
		}
		return mInstance;
	}

	/**
	 * description: 获取订单客服
	 * 
	 * @exception
	 */
	public void getOneCustomService(final Handler handler, String user_id,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_CUSTOMSERVICE);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject json = new JSONObject(result);
					if (json != null) {
						int code = json.optInt("code");
						if (code == 1) {
							//
							JSONObject infoObject = json.getJSONObject("info");
							CustomService cs = new CustomService();
							if (infoObject != null) {
								cs.setId(infoObject.optString("userId"));
								cs.setName(infoObject.optString("nickName"));
								cs.setOnline(infoObject.optString("online"));
								cs.setInTime(infoObject.optString("inTime"));
								cs.setOutTime(infoObject.optString("outTime"));
							}
							ManagerUtils.getInstance().setCustomService(cs);
							handler.sendEmptyMessage(flag1);
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
