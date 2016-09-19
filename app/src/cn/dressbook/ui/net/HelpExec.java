package cn.dressbook.ui.net;

import java.io.File;

import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.utils.FileSDCacher;

/**
 * @description:
 * @author:袁东华
 * @time:2015-9-16下午5:38:39
 */
public class HelpExec {
	private static HelpExec mInstance = null;

	public static HelpExec getInstance() {
		if (mInstance == null) {
			mInstance = new HelpExec();
		}
		return mInstance;
	}

	/**
	 * @description:检查是否更新文件
	 */
	public void checkDownLoad(final Handler handler, final String user_id,
			final int flag1, final int flag2) {

		final String suffix = "update_time.txt";
		final File file = new File(PathCommonDefines.JSON_FOLDER, suffix);
		if (!file.exists()) {
			RequestParams params = new RequestParams(
					PathCommonDefines.CHECK_DOWNLOAD);
			params.addBodyParameter("user_id", user_id);
			x.http().get(params, new Callback.CommonCallback<String>() {
				@Override
				public void onSuccess(String result) {
					// 保存最新更新时间
					FileSDCacher.createFile2(handler, result,
							PathCommonDefines.JSON_FOLDER, suffix, flag1);

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
		} else {
			RequestParams params = new RequestParams(
					PathCommonDefines.CHECK_DOWNLOAD);
			params.addBodyParameter("user_id", user_id);
			x.http().get(params, new Callback.CommonCallback<String>() {
				@Override
				public void onSuccess(String result) {
					String json1 = result;
					String json2 = FileSDCacher.ReadData(file);
					// 保存最新更新时间
					FileSDCacher.createFile2(handler, json1,
							PathCommonDefines.JSON_FOLDER, suffix, flag2);
					Message msg = new Message();
					msg.what = flag1;
					Bundle bundle = new Bundle();
					bundle.putString("json1", json1);
					bundle.putString("json2", json2);
					msg.setData(bundle);
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

}
