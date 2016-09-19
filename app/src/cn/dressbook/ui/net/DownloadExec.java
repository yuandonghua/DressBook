package cn.dressbook.ui.net;

import java.io.File;

import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;

import android.os.Handler;

public class DownloadExec {
	private static DownloadExec mDownloadExec;

	private DownloadExec() {
	}

	public static DownloadExec getInstance() {

		return mDownloadExec == null ? new DownloadExec() : mDownloadExec;
	}

	public void downloadFile(final Handler handler, final String url,
			final String path, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(url);
		params.setSaveFilePath(path);
		LogUtil.e("下载的url:" + url);
		x.http().get(params, new Callback.CommonCallback<File>() {
			@Override
			public void onSuccess(File file) {
				if (file.exists()) {
					if (handler != null) {
						handler.sendEmptyMessage(flag1);
					}
				} else {
					if (handler != null) {
						handler.sendEmptyMessage(flag2);
					}
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
			}

			@Override
			public void onFinished() {

			}
		});
	}

}
