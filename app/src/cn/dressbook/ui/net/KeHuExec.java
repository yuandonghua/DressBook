package cn.dressbook.ui.net;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.json.XFJLJson;
import cn.dressbook.ui.model.KeHu;
import cn.dressbook.ui.model.XFJL;

/**
 * @description 与客户相关
 * @author 袁东华
 * @date 2016-2-19
 */
public class KeHuExec {
	private static KeHuExec mInstance = null;

	private KeHuExec() {
	};

	public static KeHuExec getInstance() {
		if (mInstance == null) {
			mInstance = new KeHuExec();
		}
		return mInstance;
	}

	/**
	 * @description 获取客户列表
	 */
	public void getKeHuList(final Handler handler, final String user_id,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_KEHU_LIST);
		params.addBodyParameter("user_id", user_id);
		LogUtil.e(PathCommonDefines.GET_KEHU_LIST+"?user_id="+user_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				ArrayList<KeHu> list = new ArrayList<KeHu>();
				String members = "";
				try {
					JSONObject json = new JSONObject(result);
					if (!json.isNull("info")) {
						JSONObject info = json.getJSONObject("info");
						members = info.optString("members");
						if (!info.isNull("memberList")) {
							JSONArray memberList = info
									.getJSONArray("memberList");
							for (int i = 0; i < memberList.length(); i++) {
								JSONObject member = memberList.getJSONObject(i);
								KeHu kh = new KeHu();
								kh.setUser_id(member.optString("user_id"));
								kh.setUserName(member.optString("userName"));
								kh.setAvatar(PathCommonDefines.SERVER_IMAGE_ADDRESS
										+ member.optString("avatar"));
								kh.setMbLevel(member.optString("mbLevel"));
								kh.setQuarterVary(member
										.optString("quarterVary"));
								kh.setTotalVary(member.optString("totalVary"));
								list.add(kh);
							}
						}
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
				Bundle bun = new Bundle();
				Message msg = new Message();
				bun.putString("members", members);
				bun.putParcelableArrayList("list", list);
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
