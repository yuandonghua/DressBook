package cn.dressbook.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.bean.AixinjuanyiBeanRecord;
import cn.dressbook.ui.common.Constants;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.net.AiXinJuanYiExec;
import cn.dressbook.ui.net.DownloadExec;
import cn.dressbook.ui.net.ShareExec;
import cn.dressbook.ui.utils.SU;

import com.umeng.analytics.social.UMSocialService;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.handler.UMWXHandler;
import com.umeng.socialize.media.UMImage;

/**
 * @description: 爱心记录
 * @author:袁东华
 * @time:2015-11-4上午10:17:02
 */
@SuppressLint("InflateParams")
@SuppressWarnings("deprecation")
@ContentView(R.layout.activity_record)
public class RecordActivity extends BaseActivity {
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions();
	private Context mContext = RecordActivity.this;
	private Activity activity;
	/**
	 * 返回按钮
	 */
	@ViewInject(R.id.back_rl)
	private RelativeLayout back_rl;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	private SwipeRefreshLayout srlRecord;
	private ListView lvRecord;
	private LinearLayout llRecordReceiveCoins;
	private ImageView ivRecordReceiveCoinsClose;
	private EditText etRecordReceiveCoinsZipCode;
	private Button btnRecordReceiveCoinsSure;

	private View fvLoadingMore;
	private ProgressBar pbViewLoadingMore;
	private TextView tvViewLoadingMore;

	private List<AixinjuanyiBeanRecord> recordList;

	private RecordAdapter recordAdapter;

	/**
	 * TODO 领取
	 */
	private boolean receiveCooldown = false;
	/**
	 * TODO 更新冷却
	 */
	private boolean updateCooldown = false;
	/**
	 * TODO 当前页数
	 */
	private int pages = 1;
	/**
	 * TODO 下拉刷新
	 */
	public static final int DROP_REFRESH = 0;
	/**
	 * TODO 上拉加载
	 */
	public static final int PULL_REFRESH = 1;

	private int receiveChoice = -1;
	private String title, url, param, pic, id, mContent;
	private int page_size = 20;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("爱心记录");
		srlRecord = (SwipeRefreshLayout) findViewById(R.id.srlRecord);
		lvRecord = (ListView) findViewById(R.id.lvRecord);
		llRecordReceiveCoins = (LinearLayout) findViewById(R.id.llRecordReceiveCoins);
		ivRecordReceiveCoinsClose = (ImageView) findViewById(R.id.ivRecordReceiveCoinsClose);
		etRecordReceiveCoinsZipCode = (EditText) findViewById(R.id.etRecordReceiveCoinsZipCode);
		btnRecordReceiveCoinsSure = (Button) findViewById(R.id.btnRecordReceiveCoinsSure);
		// 列表加载更多的footerview
		fvLoadingMore = getLayoutInflater().inflate(R.layout.view_loading_more,
				null);
		pbViewLoadingMore = (ProgressBar) fvLoadingMore
				.findViewById(R.id.pbViewLoadingMore);
		tvViewLoadingMore = (TextView) fvLoadingMore
				.findViewById(R.id.tvViewLoadingMore);
		setListener();
	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		activity = this;
		// 记录列表
		recordList = new ArrayList<AixinjuanyiBeanRecord>();
		recordAdapter = new RecordAdapter(activity, recordList);
		lvRecord.addFooterView(fvLoadingMore);
		lvRecord.setAdapter(recordAdapter);
		// 下拉刷新样式
		srlRecord.setColorSchemeColors(
				activity.getResources().getColor(R.color.main_red), activity
						.getResources().getColor(R.color.main_red), activity
						.getResources().getColor(R.color.main_red), activity
						.getResources().getColor(R.color.main_red));
		// 获取分享内容
		ShareExec.getInstance().getShareContent(mHandler, "sqfx_donate",
				NetworkAsyncCommonDefines.GET_SHARE_S,
				NetworkAsyncCommonDefines.GET_SHARE_F);
		performTask();
	}

	@Event({ R.id.back_rl })
	private void onClick(View v) {
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		default:
			break;
		}
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			// 获取分享内容成功
			case NetworkAsyncCommonDefines.GET_SHARE_S:
				Bundle data = msg.getData();
				if (data != null) {
					title = data.getString("title");
					url = data.getString("url");
					param = data.getString("param");
					pic = data.getString("pic");
					File file = new File(PathCommonDefines.SHARE);
					if (!file.exists()) {
						file.mkdirs();
					}
					File file1 = new File(PathCommonDefines.SHARE, "axjy.jpg");
					if (!file1.exists()) {
						DownloadExec.getInstance().downloadFile(mHandler, pic,
								PathCommonDefines.SHARE + "/axjy.jpg",
								NetworkAsyncCommonDefines.DOWNLOAD_AXJY_S,
								NetworkAsyncCommonDefines.DOWNLOAD_AXJY_F);
					}
				}
				break;
			// 获取分享内容失败
			case NetworkAsyncCommonDefines.GET_SHARE_F:

				break;
			default:
				break;
			}
		}

	};

	protected void setListener() {
		OnClickListener l = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.ivRecordReceiveCoinsClose:
					llRecordReceiveCoins.setVisibility(View.GONE);
					break;
				case R.id.back_rl:
					finish();
					break;
				// 点击确定
				case R.id.btnRecordReceiveCoinsSure:
					String x = etRecordReceiveCoinsZipCode.getEditableText()
							.toString();
					if (!receiveCooldown && SU.n(x))
						receiveCoinsRecord(receiveChoice, x);
					break;
				}
			}
		};
		ivRecordReceiveCoinsClose.setOnClickListener(l);
		btnRecordReceiveCoinsSure.setOnClickListener(l);
		// 上拉加载
		lvRecord.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					// 监听拉到最后一行
					if (view.getLastVisiblePosition() == view.getCount() - 1) {
						// 上拉加载
						refreshDataRecord(PULL_REFRESH);
					}
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			}
		});
		// 下拉刷新
		srlRecord.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// 下拉刷新
				refreshDataRecord(DROP_REFRESH);
			}
		});
	}

	protected void performTask() {
		refreshDataRecord(DROP_REFRESH);
	}

	/**
	 * 
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-30 下午1:20:56
	 * @param type
	 * @see
	 */
	private void refreshDataRecord(final int type) {
		if (!updateCooldown) {
			updateCooldown = true;
			// 如果是下拉刷新
			fvLoadingMore.setVisibility(View.VISIBLE);
			if (type == DROP_REFRESH) {
				pages = 1;
			}
			// 如果是上拉加载
			if (type == PULL_REFRESH) {
				tvViewLoadingMore.setText("努力加载中...");
				pbViewLoadingMore.setVisibility(View.VISIBLE);
			}
			// 获取爱心记录列表
			AiXinJuanYiExec.getInstance().getAiXinJiLuList(mHandler,
					ManagerUtils.getInstance().getUser_id(mContext),
					pages + "", page_size + "",
					NetworkAsyncCommonDefines.GET_AIXINJILU_LIST_S,
					NetworkAsyncCommonDefines.GET_AIXINJILU_LIST_F);

			// // 网络错误
			// pbViewLoadingMore.setVisibility(View.GONE);
			// tvViewLoadingMore.setText("没有更多了...");
			//
			// updateCooldown = false;
			// // 下拉刷新结束
			// if (srlRecord.isRefreshing()) {
			// srlRecord.setRefreshing(false);
			// }
		}
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-30 下午1:22:47
	 * @param response
	 * @param type
	 * @throws JSONException
	 * @see
	 */
	// private void handleDataRecord(JSONObject response, int type)
	// throws JSONException {
	// List<AixinjuanyiBeanRecord> newRecordList = JSON.parseArray(response
	// .getJSONObject("info").getString("listDonateJoin"),
	// AixinjuanyiBeanRecord.class);
	// L.i(newRecordList.size() + "");
	// if (newRecordList != null && newRecordList.size() > 0) {
	// if (type == DROP_REFRESH) {
	// // 如果是下拉刷新，清空list
	// recordList.clear();
	// recordList = new ArrayList<AixinjuanyiBeanRecord>();
	// }
	// recordList.addAll(newRecordList);
	// recordAdapter.setData(recordList);
	// // 页面加1
	// pages = pages + 1;
	// // 只有一页不到
	// if (recordList.size() <= H.PAGE_SIZE) {
	// fvLoadingMore.setVisibility(View.GONE);
	// // pbViewLoadingMore.setVisibility(View.GONE);
	// // tvViewLoadingMore.setText("没有更多了...");
	// }
	// } else {
	// pbViewLoadingMore.setVisibility(View.GONE);
	// tvViewLoadingMore.setText("没有更多了...");
	// }
	// }

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-11-2 下午8:26:49
	 * @param position
	 * @see
	 */
	private void receiveCoinsRecord(final int position, String zipCodeInput) {
		receiveCooldown = true;
		// 领取
		AiXinJuanYiExec.getInstance().lingQu(mHandler,
				ManagerUtils.getInstance().getUser_id(mContext),
				recordList.get(position).getId() + "", zipCodeInput,
				NetworkAsyncCommonDefines.LINGQU_S,
				NetworkAsyncCommonDefines.LINGQU_F);

		// // 变成待审核状态
		// recordList.get(position).setState(2);
		// recordAdapter.setData(recordList);
		// llRecordReceiveCoins.setVisibility(View.GONE);
		// // 变成正常状态
		// recordList.get(position).setState(1);
		// recordAdapter.setData(recordList);
		// // 变成正常状态
		// recordList.get(position).setState(1);
		// recordAdapter.setData(recordList);
		// receiveCooldown = false;
		// etRecordReceiveCoinsZipCode.setText(null);
	}

	/**
	 * 
	 * TODO
	 * 
	 * @author LiShen
	 * @company Gifted Youngs Workshop
	 * @date 2015-11-2 下午11:07:15
	 * @since
	 * @version
	 */
	class RecordAdapter extends BaseAdapter {
		private Activity activity;
		private List<AixinjuanyiBeanRecord> data = new ArrayList<AixinjuanyiBeanRecord>();;

		/**
		 * TODO
		 * 
		 * @author LiShen
		 * @date 2015-11-2 下午8:09:47
		 */
		public RecordAdapter(Activity activity, List<AixinjuanyiBeanRecord> data) {
			this.activity = activity;
			this.data = data;
		}

		/**
		 * TODO
		 * 
		 * @author LiShen
		 * @date 2015-11-2 下午8:04:05
		 * @return
		 * @see android.widget.Adapter#getCount()
		 */
		@Override
		public int getCount() {
			return data.size();
		}

		/**
		 * TODO
		 * 
		 * @author LiShen
		 * @date 2015-11-2 下午8:04:05
		 * @param position
		 * @return
		 * @see android.widget.Adapter#getItem(int)
		 */
		@Override
		public AixinjuanyiBeanRecord getItem(int position) {
			return data.get(position);
		}

		/**
		 * TODO
		 * 
		 * @author LiShen
		 * @date 2015-11-2 下午8:04:05
		 * @param position
		 * @return
		 * @see android.widget.Adapter#getItemId(int)
		 */
		@Override
		public long getItemId(int position) {
			return data.get(position).hashCode();
		}

		/**
		 * TODO
		 * 
		 * @author LiShen
		 * @date 2015-11-2 下午8:04:05
		 * @param position
		 * @param convertView
		 * @param parent
		 * @return
		 * @see android.widget.Adapter#getView(int, View, ViewGroup)
		 */
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder = new ViewHolder();
			if (convertView == null) {
				convertView = activity.getLayoutInflater().inflate(
						R.layout.item_listview_record, null);
				holder.tvItemRecordTitle = (TextView) convertView
						.findViewById(R.id.tvItemRecordTitle);
				holder.ivItemRecordPicture = (ImageView) convertView
						.findViewById(R.id.ivItemRecordPicture);
				holder.btnItemRecordReceive = (Button) convertView
						.findViewById(R.id.btnItemRecordReceive);
				holder.tvItemRecordCoins = (TextView) convertView
						.findViewById(R.id.tvItemRecordCoins);
				holder.tvItemRecordTime = (TextView) convertView
						.findViewById(R.id.tvItemRecordTime);
				holder.btnItemRecordShare = (Button) convertView
						.findViewById(R.id.btnItemRecordShare);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final AixinjuanyiBeanRecord record = data.get(position);
			if (record != null) {
				// 点击晒爱心
				holder.btnItemRecordShare
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								id = record.getDonateProjectShow().getId() + "";
								mContent = record.getDonateProjectShow()
										.getTitle();
								//shareWXCircle();
								shareAll();
							}
						});
				holder.tvItemRecordTitle.setText(record.getDonateProjectShow()
						.getTitle());
				// 绑定图片
				x.image().bind(
						holder.ivItemRecordPicture,
						record.getDonateProjectShow().getPic(),
						mImageOptions, new CustomBitmapLoadCallBack(holder));
				// 点击详情
				holder.ivItemRecordPicture
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								activity.startActivity(new Intent(activity,
										ProjectDetailActivity.class).putExtra(
										"PROJECT_ID", data.get(position)
												.getDonateProjectShow().getId()
												+ ""));
							}
						});
				holder.tvItemRecordTime.setText(record.getAddTimeShow());
				holder.tvItemRecordCoins.setText(record.getYq() + "衣扣");
				// 点击领取
				holder.btnItemRecordReceive
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								receiveChoice = position;
								llRecordReceiveCoins
										.setVisibility(View.VISIBLE);
							}
						});
				switch (record.getState()) {
				case 1:
					holder.btnItemRecordReceive.setText("领取");
					holder.btnItemRecordReceive.setBackgroundDrawable(activity
							.getResources().getDrawable(
									R.drawable.selector_red_button));
					holder.btnItemRecordReceive.setClickable(true);
					break;
				case 2:
					holder.btnItemRecordReceive.setText("审核中");
					holder.btnItemRecordReceive.setBackgroundDrawable(activity
							.getResources().getDrawable(
									R.drawable.selector_unavailable_button));
					holder.btnItemRecordReceive.setClickable(false);
					break;
				case 3:
					holder.btnItemRecordReceive.setText("已领取");
					holder.btnItemRecordReceive.setBackgroundDrawable(activity
							.getResources().getDrawable(
									R.drawable.selector_unavailable_button));
					holder.btnItemRecordReceive.setClickable(false);
					break;
				}
			}
			return convertView;
		}

		/**
		 * TODO 设置 data 的值
		 */
		public void setData(List<AixinjuanyiBeanRecord> data) {
			this.data = data;
			notifyDataSetChanged();
		}

		/**
		 * 
		 * TODO
		 * 
		 * @author LiShen
		 * @company Gifted Youngs Workshop
		 * @date 2015-11-2 下午8:11:39
		 * @since
		 * @version
		 */
		private class ViewHolder {
			private TextView tvItemRecordTitle;
			private ImageView ivItemRecordPicture;
			private Button btnItemRecordReceive;
			private TextView tvItemRecordCoins;
			private TextView tvItemRecordTime;
			private Button btnItemRecordShare;
		}

		public class CustomBitmapLoadCallBack implements
				Callback.ProgressCallback<Drawable> {
			private final ViewHolder holder;

			public CustomBitmapLoadCallBack(ViewHolder holder) {
				this.holder = holder;
			}

			@Override
			public void onWaiting() {

				// this.holder.imgPb.setProgress(0);
			}

			@Override
			public void onStarted() {

			}

			@Override
			public void onLoading(long total, long current,
					boolean isDownloading) {
				// this.holder.imgPb.setProgress((int) (current * 100 / total));
			}

			@Override
			public void onSuccess(Drawable result) {

				// this.holder.imgPb.setProgress(100);
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
		}
		/**
		 * @description:分享到所有平台
		 */
		protected void shareAll() {
			try {
				
				Intent intent = new Intent(mContext, MyShareActivity.class);
				intent.putExtra("targeturl", url + "?" + param + "=" + id);
				intent.putExtra("content", mContent);
				intent.putExtra("title", title);
				intent.putExtra("pic", PathCommonDefines.SHARE+"/axjy.jpg");
				startActivity(intent);
			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		/**
		 * @description:分享到微信朋友圈
		 */
		/*protected void shareWXCircle() {
			// TODO Auto-generated method stub
			if (mController == null) {
				mController = UMServiceFactory
						.getUMSocialService("com.umeng.share");
			}
			// 支持微信朋友圈
			UMWXHandler wxCircleHandler = new UMWXHandler(mContext,
					Constants.APP_ID, Constants.AppSecret);
			if (wxCircleHandler.isClientInstalled()) {

				wxCircleHandler.setToCircle(true);
				wxCircleHandler.addToSocialSDK();
				CircleShareContent circleMedia = new CircleShareContent();

				UMImage image = new UMImage(mContext, new File(
						PathCommonDefines.SHARE, "axjy.jpg"));
				circleMedia.setShareImage(image);
				circleMedia.setTargetUrl(url + "?" + param + "=" + id);
				circleMedia.setShareContent(mContent);
				circleMedia.setTitle(title);
				mController.setShareMedia(circleMedia);
				// 参数1为Context类型对象， 参数2为要分享到的目标平台， 参数3为分享操作的回调接口
				mController.postShare(mContext, SHARE_MEDIA.WEIXIN_CIRCLE,
						new SnsPostListener() {
							@Override
							public void onStart() {
								// Toast.makeText(mContext, "开始分享.",
								// Toast.LENGTH_SHORT)
								// .show();
							}

							@Override
							public void onComplete(SHARE_MEDIA platform,
									int eCode, SocializeEntity entity) {
								if (eCode == 200) {
								} else {
									String eMsg = "";
									if (eCode == -101) {
										eMsg = "没有授权";
									}
								}
							}
						});
			} else {
				Toast.makeText(mContext, "请安装微信", Toast.LENGTH_LONG).show();
			}
		}*/
	}

}
