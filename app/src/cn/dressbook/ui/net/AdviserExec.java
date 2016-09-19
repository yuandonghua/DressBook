package cn.dressbook.ui.net;

import java.io.File;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.json.AdviserJson;
import cn.dressbook.ui.model.Adviser;
import cn.dressbook.ui.utils.FileSDCacher;


/**
 * 获取该顾问师的相关信息
 * 
 * */
public class AdviserExec {
	private static AdviserExec mInstance = null;

	public static AdviserExec getInstance() {
		if (mInstance == null) {
			mInstance = new AdviserExec();
		}
		return mInstance;
	}

	/**
	 * @description:获取顾问更新信息
	 */
	public void getAdviserUpdateInfo(final Handler handler, final int flag1,
			final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_ADVISER_UPDATE_INFO);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				String atitle = "", aUpdateTime = "", lstjMap = "", lstjUpdateTime = "", myxTitle = "", myxUpdateTime = "", mtTitle = "", mtTime = "";
				try {
					JSONObject json = new JSONObject(result);
					if (json != null) {
						JSONObject info = json.getJSONObject("info");
						if (info != null) {
							JSONObject adviser = info
									.getJSONObject("adviser");
							if (adviser != null) {
								atitle = adviser.optString("Title");
								aUpdateTime = adviser
										.optString("UpdateTime");
							}

							JSONObject lstj = info
									.getJSONObject("lstj");
							if (lstj != null) {
								lstjMap = lstj.optString("lstjMap");
								lstjUpdateTime = lstj
										.optString("UpdateTime");
							}
							JSONObject btArts = info
									.getJSONObject("btArts");
							if (btArts != null) {
								myxTitle = btArts.optString("Title");
								myxUpdateTime = btArts
										.optString("UpdateTime");
							}
							if (!info.isNull("cmtPosts")) {
								JSONObject cmtPosts = info
										.getJSONObject("cmtPosts");
								mtTitle = cmtPosts.optString("Title");
								mtTime = cmtPosts
										.optString("UpdateTime");
							}
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Message msg = new Message();
				Bundle data = new Bundle();
				data.putString("atitle", atitle);
				data.putString("aUpdateTime", aUpdateTime);
				data.putString("lstjMap", lstjMap);
				data.putString("lstjUpdateTime", lstjUpdateTime);
				data.putString("myxTitle", myxTitle);
				data.putString("myxUpdateTime", myxUpdateTime);
				data.putString("mtTitle", mtTitle);
				data.putString("mtTime", mtTime);
				msg.setData(data);
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

	/**
	 * @description:从服务端获取顾问师详情
	 * @exception
	 */
	public void getAdviserDetailFromServer(final Handler handler,
			final String adviser_id, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_ADVISERDETAIL);
		params.addBodyParameter("adviser_id", adviser_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				FileSDCacher
				.createFile2(handler, result,
						PathCommonDefines.JSON_FOLDER,
						"adviser_" + adviser_id
								+ ".txt", flag1);
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
	 * @description: 从SD卡获取顾问师详情
	 * @exception
	 */
	public void getAdviserDetailFromSD(final Handler handler,
			final String adviser_id, final int flag1, final int flag2) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				File file = new File(PathCommonDefines.JSON_FOLDER, "adviser_"
						+ adviser_id + ".txt");
				if (file.exists()) {
					String json = FileSDCacher.ReadData(file);
					try {
						Adviser adviser = AdviserJson.getInstance()
								.analyzeAdviserDetail(json);
						Message msg = new Message();
						msg.what = flag1;
						Bundle bundle = new Bundle();
						bundle.putParcelable("adviser", adviser);
						msg.setData(bundle);
						handler.sendMessage(msg);

					} catch (Exception e) {
					}
				} else {
					handler.sendEmptyMessage(flag2);
				}
			}
		}).start();
	}

	/**
	 * @description:从服务端获取顾问师列表
	 * @exception
	 */
	public void getAdviserListFromServer(final Handler handler,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_COUNSELOR_LIST);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					ArrayList<Adviser> adviserList = AdviserJson
							.getInstance().analyzeAdviserList(
									result);
					Message msg = new Message();
					msg.what = flag1;
					msg.obj=adviserList;
					/*Bundle bundle = new Bundle();
					bundle.putParcelableArrayList("adviserList",
							adviserList);
					msg.setData(bundle);*/
					handler.sendMessage(msg);

				} catch (Exception e) {
					e.printStackTrace();
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
