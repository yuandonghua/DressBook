package cn.dressbook.ui.net;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.dressbook.ui.bean.AixinjuanyiBeanDonateAddress;
import cn.dressbook.ui.bean.AixinjuanyiBeanProject;
import cn.dressbook.ui.bean.AixinjuanyiBeanProjectProgress;
import cn.dressbook.ui.bean.AixinjuanyiBeanRecord;
import cn.dressbook.ui.bean.MeitanBeanArticle;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.json.AiXinJuanYiJson;
import cn.dressbook.ui.json.MeiTanJson;

/**
 * @description 爱心捐衣相关
 * @author 袁东华
 * @time 2015-12-9下午6:11:58
 */
public class AiXinJuanYiExec {
	private static AiXinJuanYiExec mInstance = null;

	public static AiXinJuanYiExec getInstance() {
		if (mInstance == null) {
			mInstance = new AiXinJuanYiExec();
		}
		return mInstance;
	}

	/**
	 * 获取爱心捐衣列表
	 * 
	 * @param handler
	 * @param user_id
	 * @param page_num
	 * @param page_size
	 * @param flag1
	 * @param flag2
	 */
	public void getAiXinJuanYiList(final Handler handler, final String user_id,
			final String page_num, final String page_size, final int flag1,
			final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_AIXINJUANYI_LIST);
		if (user_id != null) {

			params.addBodyParameter("user_id", user_id);
		}
		params.addBodyParameter("page_num", page_num);
		params.addBodyParameter("page_size", page_size);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				ArrayList<AixinjuanyiBeanProject> newProjectList = null;
				try {
					newProjectList = AiXinJuanYiJson.getInstance()
							.analysisAXJYList(result);

				} catch (Exception e) {
					// TODO: handle exception
					LogUtil.e("异常:" + e.getMessage());
				}
				if (newProjectList == null) {
					LogUtil.e("集合为空");
				} else {
					LogUtil.e("集合个数:" + newProjectList.size());
				}
				Message msg = new Message();
				Bundle data = new Bundle();
				data.putParcelableArrayList("newProjectList", newProjectList);
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
	 * 领取
	 * 
	 * @param handler
	 * @param user_id
	 * @param join_id
	 * @param logistics
	 * @param flag1
	 * @param flag2
	 */
	public void lingQu(final Handler handler, final String user_id,
			final String join_id, final String logistics, final int flag1,
			final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.LINGQU);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("join_id", join_id);
		params.addBodyParameter("logistics", logistics);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				Message msg = new Message();
				Bundle data = new Bundle();
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
	 * 获取爱心记录列表
	 * 
	 * @param handler
	 * @param user_id
	 * @param page_num
	 * @param page_size
	 * @param flag1
	 * @param flag2
	 */
	public void getAiXinJiLuList(final Handler handler, final String user_id,
			final String page_num, final String page_size, final int flag1,
			final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_AXJL_LIST);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("page_num", page_num);
		params.addBodyParameter("page_size", page_size);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				ArrayList<AixinjuanyiBeanRecord> newRecordList = null;
				try {
					newRecordList = AiXinJuanYiJson.getInstance()
							.analysisAXJLList(result);
				} catch (Exception e) {
					// TODO: handle exception
				}
				Message msg = new Message();
				Bundle data = new Bundle();
				data.putParcelableArrayList("list", newRecordList);
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
	 * 参加项目
	 * 
	 * @param handler
	 * @param user_id
	 * @param project_id
	 * @param flag1
	 * @param flag2
	 */
	public void canJiaAXJYProject(final Handler handler, final String user_id,
			final String project_id, final String phone, final int flag1,
			final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.CANJIA_PROJECT);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("project_id", project_id);
		if (phone != null && !"".equals(phone)) {
			params.addBodyParameter("phone", phone);
		}
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				Message msg = new Message();
				Bundle data = new Bundle();
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
	 * 获取项目详情
	 * 
	 * @param handler
	 * @param user_id
	 * @param project_id
	 * @param flag1
	 * @param flag2
	 */
	public void getAXJYProject(final Handler handler, final String user_id,
			final String project_id, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_AXJY_PROJECT);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("project_id", project_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					AixinjuanyiBeanProject project = AiXinJuanYiJson
							.getInstance().analysisDonateProject(result);
					AixinjuanyiBeanDonateAddress address = AiXinJuanYiJson
							.getInstance().analysisDonateAddress(result);
					ArrayList<AixinjuanyiBeanProjectProgress> progress = AiXinJuanYiJson
							.getInstance().analysisDonateProgres(result);
					Message msg = new Message();
					Bundle data = new Bundle();
					data.putParcelable("project", project);
					data.putParcelable("address", address);
					data.putParcelableArrayList("progress", progress);
					msg.setData(data);
					msg.what = flag1;
					handler.sendMessage(msg);
				} catch (Exception e) {
					// TODO: handle exception
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

	/**
	 * 支持项目
	 * 
	 * @param handler
	 * @param user_id
	 * @param project_id
	 * @param flag1
	 * @param flag2
	 */
	public void zhiChiXiangMu(final Handler handler, final String user_id,
			final String project_id, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.ZHICHI_XIANGMU);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("project_id", project_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				ArrayList<MeitanBeanArticle> mtList = null;
				try {
					mtList = MeiTanJson.getInstance().analysisWDGZList(result);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Message msg = new Message();
				Bundle data = new Bundle();
				data.putParcelableArrayList("list", mtList);
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
}
