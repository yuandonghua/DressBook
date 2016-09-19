package cn.dressbook.ui.net;

import java.io.File;
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
import cn.dressbook.ui.bean.MeitanBeanArticle;
import cn.dressbook.ui.bean.MeitanBeanArticleComment;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.json.MeiTanJson;

/**
 * @description: 与美谈相关的请求服务
 * @author:袁东华
 * @time:2015-11-4下午3:22:21
 */
public class MeiTanExec {
	private static MeiTanExec mInstance = null;

	public static MeiTanExec getInstance() {
		if (mInstance == null) {
			mInstance = new MeiTanExec();
		}
		return mInstance;
	}

	/**
	 * 发评论
	 * 
	 * @param handler
	 * @param user_id
	 * @param posts_id
	 * @param content
	 * @param listUploadPhotos
	 * @param flag1
	 * @param flag2
	 */
	public void faPingLun(final Handler handler, final String user_id,
			final String posts_id, final String content,
			final List<String> listUploadPhotos, final int flag1,
			final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.FA_PINGLUN);
		params.setMultipart(true);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("posts_id", posts_id);
		params.addBodyParameter("content", content);
		if (listUploadPhotos != null && listUploadPhotos.size() > 0) {
			for (String url : listUploadPhotos) {
				params.addBodyParameter("uploadFile", new File(url));
			}
		}
		x.http().post(params, new Callback.CommonCallback<String>() {
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

	/**
	 * 获取博文详情
	 * 
	 * @param handler
	 * @param user_id
	 * @param posts_id
	 * @param flag1
	 * @param flag2
	 */
	public void getBoWenInfo(final Handler handler, final String user_id,
			final String posts_id, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_BOWEN_INFO);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("posts_id", posts_id);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				MeitanBeanArticle mt = new MeitanBeanArticle();
				ArrayList<MeitanBeanArticleComment> commentList = null;
				try {
					mt = MeiTanJson.getInstance().analysisBoWen(result);
					commentList = MeiTanJson.getInstance().analysisCmtReplys(
							result);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Message msg = new Message();
				Bundle data = new Bundle();
				data.putParcelable("mt", mt);
				data.putParcelableArrayList("list", commentList);
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
	 * 发博文
	 * 
	 * @param handler
	 * @param user_id
	 * @param tag
	 * @param articleTitle
	 * @param listUploadPhotos
	 * @param articleContent
	 * @param flag1
	 * @param flag2
	 */
	public void faBoWen(final Handler handler, final String user_id,
			final String tag, final String articleTitle,
			final String articleContent, final List<String> listUploadPhotos,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.FA_BOWEN);
		params.setMultipart(true);
		params.addBodyParameter("user_id", user_id);
		if (tag != null && !tag.equals("")) {
			params.addBodyParameter("tag", tag);
		}
		params.addBodyParameter("title", articleTitle);
		params.addBodyParameter("content", articleContent);
		if (listUploadPhotos != null & listUploadPhotos.size() > 0) {
			for (String url : listUploadPhotos) {
				params.addBodyParameter("uploadFile", new File(url));
			}
		}
		x.http().post(params, new Callback.CommonCallback<String>() {
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

	/**
	 * 对评论取消点赞
	 * 
	 * @param handler
	 * @param user_id
	 * @param reply_id
	 * @param flag1
	 * @param flag2
	 */
	public void quXiaoDianZanPingLun(final Handler handler,
			final String user_id, final String reply_id, final int flag1,
			final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.QUXIAO_DIANZAN_PINGLUN);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("reply_id", reply_id);
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
	 * 对评论点赞
	 * 
	 * @param handler
	 * @param user_id
	 * @param reply_id
	 * @param flag1
	 * @param flag2
	 */
	public void dianZanPingLun(final Handler handler, final String user_id,
			final String reply_id, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.DIANZAN_PINGLUN);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("reply_id", reply_id);
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
	 * 获取最新美谈博文
	 * 
	 * @param handler
	 * @param flag1
	 * @param flag2
	 */
	public void getLatestBW(final Handler handler, final int flag1,
			final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_MEITAN_SQGC);
		params.addBodyParameter("page_num", 1 + "");
		params.addBodyParameter("page_size", 1 + "");
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				MeitanBeanArticle mt = new MeitanBeanArticle();
				try {
					JSONObject json = new JSONObject(result);
					if (json != null) {
						if (!json.isNull("info")) {

							JSONObject info = json.getJSONObject("info");

							if (!info.isNull("cmtPosts")) {
								JSONArray cmtPosts = info
										.getJSONArray("cmtPosts");
								JSONObject obj = cmtPosts.getJSONObject(0);
								if (obj != null) {
									mt.setId(obj.optLong("id"));
									mt.setTitle(obj.optString("title"));
									mt.setSubjectTags(obj
											.optString("subjectTags"));
									mt.setContent(obj.optString("content"));
									mt.setAddTimeShow(obj
											.optString("addTimeShow"));
									mt.setPraiseNum(obj.optLong("praiseNum"));
									mt.setCmdNum(obj.optLong("cmdNum"));
									mt.setUserId(obj.optLong("userId"));
									mt.setUserName(obj.optString("userName"));
									mt.setUserAvatar(PathCommonDefines.SERVER_IMAGE_ADDRESS
											+ obj.optString("userAvatar"));
									mt.setUserLevel(obj.optString("userLevel"));
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
				data.putParcelable("mt", mt);
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
	 * @description:获取我的关注列表
	 */
	public void getWDGZList(final Handler handler, final String user_id,
			final int pages, final int page_size, final int flag1,
			final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_WDGZ_LIST);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("page_num", pages + "");
		params.addBodyParameter("page_size", page_size + "");
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

	/**
	 * @description:获取话题列表
	 */
	public void getHuaTiList(final Handler handler, final String code,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.PEIZHI);
		params.addBodyParameter("code", code);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				String huati = "";
				try {
					JSONObject json = new JSONObject(result);
					if (!json.isNull("info")) {
						huati = json.optString("info");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Message msg = new Message();
				Bundle data = new Bundle();
				data.putString("huati", huati);
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
	 * @description:获取社区广场列表
	 */
	public void getSQGCList(final Handler handler, final String user_id,
			final String subject_tag, final int position, final int pages,
			final int page_size, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_MEITAN_SQGC);
		params.addBodyParameter("user_id", user_id);
		if (position != 0) {
			params.addBodyParameter("subject_tag", subject_tag);
		}
		params.addBodyParameter("page_num", pages + "");
		params.addBodyParameter("page_size", page_size + "");
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

	/**
	 * 举报
	 * 
	 * @param handler
	 * @param user_id
	 * @param subject_tag
	 * @param position
	 * @param pages
	 * @param page_size
	 * @param flag1
	 * @param flag2
	 */
	public void juBaoBoWen(final Handler handler, final String user_id,
			final String model, final String model_id, final int flag1,
			final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.JUBAOBOWEN);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("model", model);
		params.addBodyParameter("model_id", model_id);
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
	 * 取消点赞
	 * 
	 * @param handler
	 * @param user_id
	 * @param posts_id
	 * @param flag1
	 * @param flag2
	 */
	public void quXiaoDianZan(final Handler handler, final String user_id,
			final String posts_id, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.QUXIAODIANZAN);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("posts_id", posts_id);
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
	 * 点赞
	 * 
	 * @param handler
	 * @param user_id
	 * @param posts_id
	 * @param flag1
	 * @param flag2
	 */
	public void dianZan(final Handler handler, final String user_id,
			final String posts_id, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.DIANZAN);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("posts_id", posts_id);
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
	 * 获取用户主页信息
	 * 
	 * @param handler
	 * @param user_id
	 * @param touser_id
	 * @param page_num
	 * @param page_size
	 * @param flag1
	 * @param flag2
	 */
	public void getUserZhuYeInfo(final Handler handler, final String user_id,
			final String touser_id, final int page_num, final int page_size,
			final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.GET_USER_ZHUYE_INFO);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("touser_id", touser_id);
		params.addBodyParameter("page_num", page_num + "");
		params.addBodyParameter("page_size", page_size + "");
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				String isFollow = "-1", userName = "", userAvatar = "", userIder = "", userLevel = "", followNum = "", employNum = "";
				ArrayList<MeitanBeanArticle> mtList = null;
				try {
					JSONObject json = new JSONObject(result);
					if (!json.isNull("info")) {
						JSONObject info = json.getJSONObject("info");
						isFollow = info.optString("isFollow");
						userName = info.optString("userName");
						userAvatar = PathCommonDefines.SERVER_IMAGE_ADDRESS
								+ info.optString("userAvatar");
						userIder = info.optString("userIder");
						userLevel = info.optString("userLevel");
						followNum = info.optString("followNum");
						employNum = info.optString("employNum");
					}
					mtList = MeiTanJson.getInstance().analysisWDGZList(result);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Message msg = new Message();
				Bundle data = new Bundle();
				data.putString("isFollow", isFollow);
				data.putString("userName", userName);
				data.putString("userAvatar", userAvatar);
				data.putString("userIder", userIder);
				data.putString("userLevel", userLevel);
				data.putString("followNum", followNum);
				data.putString("employNum", employNum);
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

	/**
	 * 关注
	 * 
	 * @param handler
	 * @param user_id
	 * @param touser_id
	 * @param flag1
	 * @param flag2
	 */
	public void guanZhu(final Handler handler, final String user_id,
			final String touser_id, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(PathCommonDefines.GUANZHU_USER);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("touser_id", touser_id);
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

	/**
	 * 取消关注
	 * 
	 * @param handler
	 * @param user_id
	 * @param touser_id
	 * @param flag1
	 * @param flag2
	 */
	public void quXiaoGuanZhu(final Handler handler, final String user_id,
			final String touser_id, final int flag1, final int flag2) {
		RequestParams params = new RequestParams(
				PathCommonDefines.QUXIAO_GUANZHU_USER);
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("touser_id", touser_id);
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
