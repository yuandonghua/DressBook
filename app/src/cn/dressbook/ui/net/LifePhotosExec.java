package cn.dressbook.ui.net;

import java.io.File;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.model.LifePhoto;

/**
 * @description: 生活照
 * @author:袁东华
 * @time:2015-9-28下午7:19:45
 */
public class LifePhotosExec {
	private static LifePhotosExec mInstance = null;

	private LifePhotosExec() {
	};

	public static LifePhotosExec getInstance() {
		if (mInstance == null) {
			mInstance = new LifePhotosExec();
		}
		return mInstance;
	}
	
	/**
	 * @description:上传生活照
	 */
	public void uploadLifePhotos(final Handler handler, final String user_id,
			final String wardrobe_id, final String path, final int flag1,
			final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.UPLOAD_LIFEPHOTOS);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("wardrobe_id", wardrobe_id);
		params.addBodyParameter("uploadFile", new File(path));
		x.http().post(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				LogUtil.e(result);
				if (handler != null) {

					handler.sendEmptyMessage(flag1);
				}

			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				if (handler != null) {
					handler.sendEmptyMessage(flag2);
				}
			}

			@Override
			public void onCancelled(CancelledException cex) {
				if (handler != null) {
					handler.sendEmptyMessage(flag2);
				}
			}

			@Override
			public void onFinished() {

			}
		});
	}

	/**
	 * @description:获取生活照列表
	 */
	public void getLifePhoto(final Handler handler, final String user_id,
			final String wardrobe_id, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_LIFEPHOTO);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("wardrobe_id", wardrobe_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				ArrayList<LifePhoto> mList = new ArrayList<LifePhoto>();
				try {
					JSONObject json = new JSONObject(result);
					if (json != null) {
						JSONObject info = json.getJSONObject("info");
						if (info != null) {
							JSONArray userPhotoList = info
									.getJSONArray("userPhotoList");
							if (userPhotoList != null
									&& userPhotoList.length() > 0) {
								for (int i = 0; i < userPhotoList.length(); i++) {
									JSONObject obj = userPhotoList
											.getJSONObject(i);
									if (obj != null) {
										LifePhoto lp = new LifePhoto();
										lp.setId(obj.optString("id"));
										lp.setPic(PathCommonDefines.SERVER_ADDRESS
												+ obj.optString("pic"));
										lp.setUploadTimeShow("uploadTimeShow");
										mList.add(lp);
									}
								}
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
	 * @description:删除生活照
	 */
	public void deleteLifePhoto(final Handler handler, final String user_id,
			final String wardrobe_id, final String photo_id, final int flag1,
			final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.DELETE_LIFEPHOTO);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("wardrobe_id", wardrobe_id);
		params.addBodyParameter("photo_id", photo_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				handler.sendEmptyMessage(flag1);

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
