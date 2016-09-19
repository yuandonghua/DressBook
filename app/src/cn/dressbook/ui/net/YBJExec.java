package cn.dressbook.ui.net;

import java.util.ArrayList;
import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.json.YBJJson;
import cn.dressbook.ui.model.YBJ;

/**
 * @description:衣保金系列
 * @author:袁东华
 * @modifier:袁东华
 * @remarks:
 * @2015年7月21日 上午10:37:28
 */
public class YBJExec {
	private static YBJExec mInstance = null;

	private YBJExec() {
	};

	public static YBJExec getInstance() {
		if (mInstance == null) {
			mInstance = new YBJExec();
		}
		return mInstance;
	}

	/**
	 * @description:获取消费记录
	 * @exception
	 */
	public void getExpenseRecord(final Handler handler, final String user_id,
			final String type, final int page_num, final int page_size,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_EXPENSE_RECORD);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("type", type);
		params.addBodyParameter("page_num", page_num + "");
		params.addBodyParameter("page_size", page_size + "");
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					ArrayList<YBJ> ybjList = YBJJson.getInstance().analyzeYBJ(
							result);
					Bundle bun = new Bundle();
					Message msg = new Message();
					bun.putParcelableArrayList("ybjList", ybjList);
					msg.setData(bun);
					msg.what = flag1;
					handler.sendMessage(msg);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
