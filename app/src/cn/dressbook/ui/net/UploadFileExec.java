package cn.dressbook.ui.net;

import java.io.File;

import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.common.Callback.CancelledException;
import org.xutils.http.RequestParams;

import android.os.Handler;
import cn.dressbook.ui.common.PathCommonDefines;

/**
 * @description: 上传文件
 * @author:袁东华
 * @time:2015-11-12下午2:31:21
 */
public class UploadFileExec {
	private static UploadFileExec mInstance = null;

	public static UploadFileExec getInstance() {
		if (mInstance == null) {
			mInstance = new UploadFileExec();
		}
		return mInstance;
	}

	/**
	 * @description:上传试衣图片
	 */
	public void uploadTryOnImage(final Handler handler,
			final String uploadPath, final String uploadFile, final int flag1,
			final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.UPLOAD_TRYON_IMAGE);
		params.addBodyParameter("uploadPath", uploadPath);
		params.addBodyParameter("uploadFile", new File(uploadFile));
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
