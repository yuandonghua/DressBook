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
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.model.MYX1;
import cn.dressbook.ui.model.MYXChild;

/**
 * @description: 美衣讯
 * @author:袁东华
 * @time:2015-9-28上午10:01:13
 */
public class MYXExec {
	private static MYXExec mInstance = null;

	public static MYXExec getInstance() {
		if (mInstance == null) {
			mInstance = new MYXExec();
		}
		return mInstance;
	}

	/**
	 * @description:获取美衣讯
	 */
	public void getMYX(final Handler handler, int page_num, int page_size,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.MYX);
		params.addBodyParameter("page_num", page_num + "");
		params.addBodyParameter("page_size", page_size + "");
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				ArrayList<MYX1> mList = null;
				try {
					JSONObject json = new JSONObject(
							result);
					if (json != null) {
						JSONArray info = json.getJSONArray("info");
						if (info != null && info.length() > 0) {
							mList = new ArrayList<MYX1>();
							for (int i = 0; i < info.length(); i++) {
								JSONObject obj = info.getJSONObject(i);
								MYX1 myx = new MYX1();
								if (obj != null) {
									myx.setId(obj.optString("id"));
									myx.setSubject(obj
											.optString("subject"));
									myx.setState(obj.optString("state"));
									myx.setPublishTimeShow(obj
											.optString("publishTimeShow"));
									JSONArray btArticles = obj
											.getJSONArray("btArticles");
									if (btArticles != null
											&& btArticles.length() > 0) {
										ArrayList<MYXChild> myxlist = new ArrayList<MYXChild>();
										for (int j = 0; j < btArticles
												.length(); j++) {
											MYXChild mYXChild = new MYXChild();
											JSONObject myxObj = btArticles
													.getJSONObject(j);
											if (myxObj != null) {
												mYXChild.setFirst(myxObj
														.optString("first"));
												mYXChild.setTitle(myxObj
														.optString("title"));
												mYXChild.setId(myxObj
														.optString("id"));
												mYXChild.setUrlPic(PathCommonDefines.SERVER_ADDRESS
														+ myxObj.optString("urlPic"));
												mYXChild.setUrl(PathCommonDefines.SERVER_ADDRESS
														+ myxObj.optString("url"));
												mYXChild.setExternal_url(myxObj
														.optString("externalUrl"));
												myxlist.add(mYXChild);
											}
										}
										myx.setBtArticles(myxlist);
									}

								}
								mList.add(myx);
							}
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Message msg = new Message();
				Bundle data = new Bundle();
				data.putParcelableArrayList("list", mList);
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
