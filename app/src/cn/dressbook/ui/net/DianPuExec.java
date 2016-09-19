package cn.dressbook.ui.net;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.model.CustomService;
import cn.dressbook.ui.model.User;

/**
 * @description 店铺相关
 * @author 袁东华
 * @date 2016-3-16
 */
public class DianPuExec {
	private static DianPuExec mInstance = null;

	public static DianPuExec getInstance() {
		if (mInstance == null) {
			mInstance = new DianPuExec();
		}
		return mInstance;
	}

	/**
	 * @description 获取员工列表
	 */
	public void getYuanGongList(final Handler handler, String user_id,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_YUANGONG_LIST);
		params.addBodyParameter("user_id", user_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					ArrayList<User> list = new ArrayList<User>();
					JSONObject json = new JSONObject(result);
					if (!json.isNull("info")) {
						JSONObject info = json.getJSONObject("info");
						if (!info.isNull("items")) {
							JSONArray items = info.getJSONArray("items");
							for (int i = 0; i < items.length(); i++) {
								JSONObject obj = items.getJSONObject(i);
								User user = new User();
								user.setName(obj.optString("userName"));
								user.setPhone(obj.optString("mobilePhone"));
								user.setAddTimeShow(obj
										.optString("addTimeShow"));
								list.add(user);
							}
						}
					}
					Message msg = new Message();
					Bundle bundle = new Bundle();
					bundle.putParcelableArrayList("list", list);
					msg.setData(bundle);
					msg.what = flag1;
					handler.sendMessage(msg);
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
	 * @description 添加员工
	 */
	public void addYuanGong(final Handler handler, String user_id, String emp_mb,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.ADDYUANGONG);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("emp_mb", emp_mb);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject json = new JSONObject(result);
					if (!json.isNull("info")) {
						JSONObject info=json.getJSONObject("info");
						String recode=info.optString("recode");
						String redesc=info.optString("redesc");
						if("0".equals(recode)){
							handler.sendEmptyMessage(flag2);
						}else{
							Message msg = new Message();
							Bundle bundle = new Bundle();
							bundle.putString("redesc", redesc);
							msg.setData(bundle);
							msg.what = flag1;
							handler.sendMessage(msg);
						}
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
}
