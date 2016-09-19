package cn.dressbook.ui.net;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;

import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.json.LSDZJson;
import cn.dressbook.ui.json.LTSJJson;
import cn.dressbook.ui.json.YJTCJson;
import cn.dressbook.ui.model.KHXX;
import cn.dressbook.ui.model.LiangTiShuJu;
import cn.dressbook.ui.model.User;
import cn.dressbook.ui.model.YJTCMolder;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * 
 * @author 熊天富
 * @描述 量体数据
 * @时间 2016年2月16日16:04:30
 * 
 */
public class LTSJExec {
	private LTSJExec() {
	}

	private static LTSJExec mAdviserJson;

	public static LTSJExec getInstance() {

		if (mAdviserJson == null) {
			mAdviserJson = new LTSJExec();
		}
		return mAdviserJson;
	}

	/**
	 * 获取量体数据列表
	 */
	public void getLTSJList(final Handler mHandler, String fg_user_mobile,
			String vip_user_id, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.GET_LTSJLIST);
		params.addBodyParameter("fg_user_mobile", fg_user_mobile);
		params.addBodyParameter("vip_user_id", vip_user_id);
		LogUtil.e(PathCommonDefines.GET_LTSJLIST + "?vip_user_id="
				+ vip_user_id + "&fg_user_mobile=" + fg_user_mobile);
		x.http().get(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				mHandler.sendEmptyMessage(flag2);
			}

			@Override
			public void onFinished() {

			}

			@Override
			public void onSuccess(String result) {
				try {
					ArrayList<LiangTiShuJu> arrayList = null;
					User user = null;
					JSONObject json = new JSONObject(result);
					String lastOper = null;
					String lastOpTime = null;
					if (!json.isNull("info")) {
						JSONObject info = json.getJSONObject("info");
						lastOper = info.optString("lastOper");
						lastOpTime = info.optString("lastOpTime");
						arrayList = LTSJJson.getInstance().getLTSJList(result);
						if ("".equals(lastOper)) {
							lastOper = "无";
							lastOpTime = "无";
						}
						user = new User();
						user.setId(info.optString("fg_user_id"));
						user.setPhone(info.optString("fg_mobile"));
						user.setAvatar(PathCommonDefines.SERVER_IMAGE_ADDRESS
								+ info.optString("fg_avatar"));
						user.setName(info.optString("fg_user_name"));
					}

					Message msg = new Message();
					Bundle bundle = new Bundle();
					bundle.putString("lastOper", lastOper);
					bundle.putString("lastOpTime", lastOpTime);
					bundle.putParcelableArrayList("ltsj", arrayList);
					bundle.putParcelable("user", user);
					msg.setData(bundle);
					msg.what = flag1;
					mHandler.sendMessage(msg);
				} catch (JSONException e) {
					mHandler.sendEmptyMessage(flag2);
				}
			}
		});

	}

	/**
	 * 获取量体数据列表
	 */
	public void getLTSJList(final Handler mHandler, String fg_user_id,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.GET_LTSJLIST);
		params.addBodyParameter("fg_user_id", fg_user_id);
		LogUtil.e(PathCommonDefines.GET_LTSJLIST + "?fg_user_id=" + fg_user_id);
		x.http().get(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				mHandler.sendEmptyMessage(flag2);
			}

			@Override
			public void onFinished() {

			}

			@Override
			public void onSuccess(String result) {
				try {
					ArrayList<LiangTiShuJu> arrayList = null;
					JSONObject json = new JSONObject(result);
					String lastOper = null;
					String lastOpTime = null;
					if (!json.isNull("info")) {
						JSONObject info = json.getJSONObject("info");
						lastOper = info.optString("lastOper");
						lastOpTime = info.optString("lastOpTime");
						arrayList = LTSJJson.getInstance().getLTSJList(result);
						if ("".equals(lastOper)) {
							lastOper = "无";
							lastOpTime = "无";
						}
					}

					Message msg = new Message();
					Bundle bundle = new Bundle();
					bundle.putString("lastOper", lastOper);
					bundle.putString("lastOpTime", lastOpTime);
					bundle.putParcelableArrayList("ltsj", arrayList);
					msg.setData(bundle);
					msg.what = flag1;
					mHandler.sendMessage(msg);
				} catch (JSONException e) {
					mHandler.sendEmptyMessage(flag2);
				}
			}
		});

	}

	/**
	 * 获取量体数据的验证码
	 */
	public void getLTSJYZM(final Handler mHandler, String phone, String code,
			String user_verify, final int sucFlag, final int failFlag) {
		RequestParams params = new RequestParams(PathCommonDefines.FSYZM);
		params.addBodyParameter("phone", phone);
		params.addBodyParameter("code", code);
		params.addBodyParameter("user_verify", "yes");
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
				try {
					JSONObject object = new JSONObject(result);
					String user_id = "";
					if (1 == object.optInt("code")) {
						JSONObject jsonInfo = object.getJSONObject("info");
						user_id = jsonInfo.optInt("user_id") + "";
						KHXX khxx = new KHXX();
						khxx.setUser_id(user_id);
						khxx.setName(jsonInfo.optString("user_name"));
						khxx.setPhone(jsonInfo.optString("mobile"));
						khxx.setPic(PathCommonDefines.SERVER_IMAGE_ADDRESS
								+ jsonInfo.optString("avatar"));
						Message msg = new Message();
						Bundle data = new Bundle();
						data.putParcelable("khxx", khxx);
						msg.setData(data);
						msg.what = sucFlag;
						mHandler.sendMessage(msg);
					} else {
						mHandler.sendEmptyMessage(failFlag);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					mHandler.sendEmptyMessage(failFlag);
				}
			}
		});

	}

	/**
	 * 上传量体数据
	 */
	public void upLoad(final Handler mHandler, String op_user_id,
			String fg_user_id, String fg_data, final int sucFlag,
			final int failFlag) {
		RequestParams params = new RequestParams(PathCommonDefines.UPLOAD_LTSJ);
		params.addBodyParameter("op_user_id", op_user_id);
		params.addBodyParameter("fg_user_id", fg_user_id);
		params.addBodyParameter("fg_data", fg_data);
		LogUtil.e(PathCommonDefines.UPLOAD_LTSJ + "?op_user_id=" + op_user_id
				+ "&fg_user_id=" + fg_user_id + "&fg_data=" + fg_data);
		x.http().post(params, new CommonCallback<String>() {

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
				try {
					JSONObject jObject = new JSONObject(result);
					if (1 == jObject.optInt("code")) {

						mHandler.sendEmptyMessage(sucFlag);
					} else {
						mHandler.sendEmptyMessage(failFlag);
					}
				} catch (JSONException e) {
					mHandler.sendEmptyMessage(failFlag);
					e.printStackTrace();
				}

			}
		});

	}

}
