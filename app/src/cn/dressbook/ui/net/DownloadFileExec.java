package cn.dressbook.ui.net;

import java.io.File;

import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.common.Callback.CancelledException;
import org.xutils.http.RequestParams;

import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.utils.FileSDCacher;

import android.os.Handler;

/**
 * @description: 与分享相关
 * @author:袁东华
 * @time:2015-11-5上午9:59:31
 */
public class DownloadFileExec {
	private static DownloadFileExec mInstance = null;

	public static DownloadFileExec getInstance() {
		if (mInstance == null) {
			mInstance = new DownloadFileExec();
		}
		return mInstance;
	}

	/**
	 * @description:下载文件
	 */
	public void downloadFile(final Handler handler, final String url,
			final String path, final String fileName, final int flag1,
			final int flag2) {
		RequestParams params = new RequestParams(url);
		params.setSaveFilePath(path + "/" + fileName);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
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

}
