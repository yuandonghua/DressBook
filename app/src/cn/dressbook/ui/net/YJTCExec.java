package cn.dressbook.ui.net;

import org.json.JSONException;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;

import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.json.LSDZJson;
import cn.dressbook.ui.json.YJTCJson;
import cn.dressbook.ui.model.YJTCMolder;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * 
 * @author 熊天富
 * @描述 业绩提成
 * @时间 2016年2月16日16:04:30
 * 
 */
public class YJTCExec {
	private YJTCExec() {
	}

	private static YJTCExec mAdviserJson;

	public static YJTCExec getInstance() {

		if (mAdviserJson == null) {
			mAdviserJson = new YJTCExec();
		}
		return mAdviserJson;
	}

	/**
	 * 获取业绩提成列表
	 */
	public void getYJTCList(final Handler mHandler, String user_id,
			final int sucFlag, final int failFlag) {
		RequestParams params = new RequestParams(PathCommonDefines.GET_YJTCLIST);
		params.addBodyParameter("user_id", user_id);
		LogUtil.e(PathCommonDefines.GET_YJTCLIST+"?user_id="+user_id);
		x.http().get(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				mHandler.sendEmptyMessage(failFlag);
			}

			@Override
			public void onFinished() {

			}

			@Override
			public void onSuccess(String result) {
				LogUtil.e("result:"+result);
				try {
					YJTCMolder molder = YJTCJson.getInstance().getYJTC(result);
					if(molder==null){
						molder=new YJTCMolder();
					}
					ManagerUtils.getInstance().setYjtc(molder);
					mHandler.sendEmptyMessage(sucFlag);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					mHandler.sendEmptyMessage(failFlag);
				}
			}
		});

	}

}
