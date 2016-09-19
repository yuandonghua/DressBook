package cn.dressbook.ui.net;

import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import android.os.Handler;
import cn.dressbook.ui.common.PathCommonDefines;

/**
 * @description: 收货地址
 * @author:袁东华
 * @time:2015-9-1下午12:00:00
 */
public class DeleteExec {
	private static DeleteExec mInstance = null;

	public static DeleteExec getInstance() {
		if (mInstance == null) {
			mInstance = new DeleteExec();
		}
		return mInstance;
	}

	/**
	 * @description:设置默认收货地址
	 */
	public void deleteServerSY(final Handler handler, final String user_id,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.DELETE_SY_FILE);
		params.addBodyParameter("user_id", user_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				LogUtil.e(result);
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
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
