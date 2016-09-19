package cn.dressbook.ui;

import java.util.ArrayList;
import java.util.List;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.adapter.UserHomepageAdapter;
import cn.dressbook.ui.bean.MeitanBeanArticle;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.net.MeiTanExec;
import cn.dressbook.ui.view.CircleImageView2;

/**
 * @description 用户主页
 * @author 袁东华
 * @time 2015-12-9下午3:53:17
 */
@SuppressLint("NewApi")
@ContentView(R.layout.activity_user_homepage)
public class UserHomepageActivity extends BaseActivity {
	private Activity mContext = UserHomepageActivity.this;
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions(ImageView.ScaleType.CENTER_CROP);
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
	private ImageView ivUserHomepageHeadBackground;
	private CircleImageView2 ivUserHomepagePortrait;
	private TextView tvUserHomepageName;
	private TextView tvUserHomepageLevel;
	private TextView tvUserHomepageIdea;
	private TextView tvUserHomepageFansNum;
	private TextView tvUserHomepageRecommendNum;
	@ViewInject(R.id.srlUserHomepage)
	private SwipeRefreshLayout srlUserHomepage;
	@ViewInject(R.id.lvUserHomepage)
	private ListView lvUserHomepage;
	private TextView tvUserHomepageFocus;
	private View fvLoadingMore;
	private ProgressBar pbViewLoadingMore;
	private TextView tvViewLoadingMore;

	private String homepageOwnerId;
	private boolean isFollowed;

	private List<MeitanBeanArticle> articleList;

	private UserHomepageAdapter mUserHomepageAdapter;

	/**
	 * TODO 更新冷却
	 */
	private boolean updateCooldown = false;
	/**
	 * TODO 请求关注冷却
	 */
	private boolean focusCooldown = false;
	/**
	 * TODO
	 */
	private boolean cancelFocusCooldown = false;
	/**
	 * TODO 当前页数
	 */
	private int pages = 1;
	/**
	 * TODO 下拉刷新
	 */
	private static final int DROP_REFRESH = 0;
	/**
	 * TODO 上拉加载
	 */
	private static final int PULL_REFRESH = 1;
	private int mType, page_size = 20;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		// 头部view
		View viewHead = View.inflate(this, R.layout.item_user_homepage_header,
				null);
		ivUserHomepageHeadBackground = (ImageView) viewHead
				.findViewById(R.id.ivUserHomepageHeadBackground);
		ivUserHomepagePortrait = (CircleImageView2) viewHead
				.findViewById(R.id.ivUserHomepagePortrait);
		tvUserHomepageName = (TextView) viewHead
				.findViewById(R.id.tvUserHomepageName);
		tvUserHomepageLevel = (TextView) viewHead
				.findViewById(R.id.tvUserHomepageLevel);
		tvUserHomepageIdea = (TextView) viewHead
				.findViewById(R.id.tvUserHomepageIdea);
		tvUserHomepageFansNum = (TextView) viewHead
				.findViewById(R.id.tvUserHomepageFansNum);
		tvUserHomepageRecommendNum = (TextView) viewHead
				.findViewById(R.id.tvUserHomepageRecommendNum);
		tvUserHomepageFocus = (TextView) viewHead
				.findViewById(R.id.tvUserHomepageFocus);
		lvUserHomepage.addHeaderView(viewHead);
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

		// intent数据
		homepageOwnerId = getIntent().getStringExtra("USER_ID");
		articleList = new ArrayList<MeitanBeanArticle>();
		mUserHomepageAdapter = new UserHomepageAdapter(mContext, mHandler);
		lvUserHomepage.addFooterView(fvLoadingMore);
		lvUserHomepage.setAdapter(mUserHomepageAdapter);
		// 下拉刷新样式
		srlUserHomepage.setColorSchemeColors(
				getResources().getColor(R.color.main_red), getResources()
						.getColor(R.color.main_red),
				getResources().getColor(R.color.main_red), getResources()
						.getColor(R.color.main_red));
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

	protected void setListener() {
		OnClickListener l = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.tvUserHomepageFocus:
					// 关注和取消关注
					if (!isFollowed && !focusCooldown) {
						toFocusHomepage();
					}
					if (isFollowed && !cancelFocusCooldown) {
						toCancelFocusArticleDetail();

					}
					break;
				}
			}
		};
		tvUserHomepageFocus.setOnClickListener(l);
		// 上拉监听s
		lvUserHomepage.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					// 监听拉到最后一行
					if (view.getLastVisiblePosition() == view.getCount() - 1) {
						// 上拉加载
						refreshDataUserHomepage(PULL_REFRESH);
					}
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			}
		});
		// 下拉刷新
		srlUserHomepage.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// 下拉刷新
				refreshDataUserHomepage(DROP_REFRESH);
			}
		});
	}

	protected void performTask() {
		// 发起请求获取数据
		refreshDataUserHomepage(DROP_REFRESH);
	}

	/**
	 * TODO 发起请求获取数据
	 * 
	 * @author LiShen
	 * @date 2015-10-20 下午10:53:24
	 * @see
	 */
	private void refreshDataUserHomepage(final int type) {
		mType = type;
		if (!updateCooldown) {
			updateCooldown = true;
			// 如果是下拉刷新
			if (type == DROP_REFRESH) {
				pages = 1;
			}
			// 如果是上拉加载
			if (type == PULL_REFRESH) {
				tvViewLoadingMore.setText("努力加载中...");
				pbViewLoadingMore.setVisibility(View.VISIBLE);
			}
			// 获取用户主页信息
			MeiTanExec.getInstance().getUserZhuYeInfo(mHandler,
					ManagerUtils.getInstance().getUser_id(mContext),
					homepageOwnerId, pages, page_size,
					NetworkAsyncCommonDefines.GET_USER_ZHUYE_INFO_S,
					NetworkAsyncCommonDefines.GET_USER_ZHUYE_INFO_F);

		}
	}

	/**
	 * TODO 发起请求关注某人
	 * 
	 * @author LiShen
	 * @date 2015-10-21 上午12:25:51
	 * @see
	 */
	private void toFocusHomepage() {
		focusCooldown = true;
		// 关注用户
		MeiTanExec.getInstance().guanZhu(mHandler,
				ManagerUtils.getInstance().getUser_id(mContext),
				homepageOwnerId, NetworkAsyncCommonDefines.GUANZHU_USER_S,
				NetworkAsyncCommonDefines.GUANZHU_USER_F);

	}

	/**
	 * 
	 * TODO 发起请求取消关注某人
	 * 
	 * @author LiShen
	 * @date 2015-10-24 下午3:28:46
	 * @see
	 */
	private void toCancelFocusArticleDetail() {
		cancelFocusCooldown = true;
		// 取消关注用户
		MeiTanExec.getInstance().quXiaoGuanZhu(mHandler,
				ManagerUtils.getInstance().getUser_id(mContext),
				homepageOwnerId,
				NetworkAsyncCommonDefines.QUXIAO_GUANZHU_USER_S,
				NetworkAsyncCommonDefines.QUXIAO_GUANZHU_USER_F);
	}

	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			// 点赞成功
			case NetworkAsyncCommonDefines.DIANZAN_S:

				break;
			// 点赞失败
			case NetworkAsyncCommonDefines.DIANZAN_F:
				break;
			// 取消点赞成功
			case NetworkAsyncCommonDefines.QUXIAO_DIANZAN_S:

				break;
			// 取消点赞失败
			case NetworkAsyncCommonDefines.QUXIAO_DIANZAN_F:
				break;
			// 举报成功
			case NetworkAsyncCommonDefines.JUBAOBOWEN_S:
				Toast.makeText(mContext, "举报成功", Toast.LENGTH_SHORT).show();
				break;
			// 举报失败
			case NetworkAsyncCommonDefines.JUBAOBOWEN_F:
				Toast.makeText(mContext, "操作失败", Toast.LENGTH_SHORT).show();
				break;
			// 取消关注成功
			case NetworkAsyncCommonDefines.QUXIAO_GUANZHU_USER_S:
				isFollowed = false;
				// 界面变化
				tvUserHomepageFocus.setBackground(getResources().getDrawable(
						R.drawable.shape_white_grey_stroke_small_radius));
				tvUserHomepageFocus.setText("+ 关注");
				tvUserHomepageFocus.setTextColor(getResources().getColor(
						R.color.main_text_grey));
				refreshDataUserHomepage(DROP_REFRESH);
				cancelFocusCooldown = false;
				break;
			// 取消关注失败
			case NetworkAsyncCommonDefines.QUXIAO_GUANZHU_USER_F:
				cancelFocusCooldown = false;
				break;
			// 关注用户成功
			case NetworkAsyncCommonDefines.GUANZHU_USER_S:
				isFollowed = true;
				// 界面变化
				tvUserHomepageFocus.setBackground(getResources().getDrawable(
						R.drawable.shape_white_orange_stroke_small_radius));
				tvUserHomepageFocus.setText("已关注");
				tvUserHomepageFocus.setTextColor(getResources().getColor(
						R.color.main_text_orange));
				refreshDataUserHomepage(DROP_REFRESH);
				focusCooldown = false;
				break;
			// 关注用户失败
			case NetworkAsyncCommonDefines.GUANZHU_USER_F:
				focusCooldown = false;

				break;
			// 获取用户主页信息成功
			case NetworkAsyncCommonDefines.GET_USER_ZHUYE_INFO_S:
				Bundle listData = msg.getData();
				if (listData != null) {
					List<MeitanBeanArticle> newArticleList = listData
							.getParcelableArrayList("list");
					String isFollow = listData.getString("isFollow");
					String userName = listData.getString("userName");
					String userAvatar = listData.getString("userAvatar");
					String userIder = listData.getString("userIder");
					String userLevel = listData.getString("userLevel");
					String followNum = listData.getString("followNum");
					String employNum = listData.getString("employNum");
					// 是否关注
					if ("-2".equals(isFollow)) {
						tvUserHomepageFocus.setVisibility(View.GONE);
					} else if ("1".equals(isFollow)) {
						isFollowed = true;
						tvUserHomepageFocus
								.setBackground(getResources()
										.getDrawable(
												R.drawable.shape_white_orange_stroke_small_radius));
						tvUserHomepageFocus.setText("已关注");
						tvUserHomepageFocus.setTextColor(getResources()
								.getColor(R.color.main_text_orange));
					} else {
						isFollowed = false;
						tvUserHomepageFocus
								.setBackground(getResources()
										.getDrawable(
												R.drawable.shape_white_grey_stroke_small_radius));
						tvUserHomepageFocus.setText("+ 关注");
						tvUserHomepageFocus.setTextColor(getResources()
								.getColor(R.color.main_text_grey));
					}
					// 标题栏
					// 昵称多于6个字时，显示前5个字，并加上“…”
					if (userName.length() > 6) {
						userName = userName.substring(0, 5);
						userName = userName + "…";
					}
					title_tv.setText(userName + "的主页");
					// 头像及背景
					// 绑定图片
					x.image().bind(ivUserHomepagePortrait, userAvatar,
							mImageOptions, new CommonCallback<Drawable>() {

								@Override
								public void onSuccess(Drawable arg0) {
									// TODO Auto-generated method stub
								}

								@Override
								public void onFinished() {
									// TODO Auto-generated method stub

								}

								@Override
								public void onError(Throwable arg0, boolean arg1) {
									// TODO Auto-generated method stub
								}

								@Override
								public void onCancelled(CancelledException arg0) {
									// TODO Auto-generated method stub
								}
							});
					// 绑定图片
					x.image().bind(ivUserHomepageHeadBackground, userAvatar,
							mImageOptions, new CommonCallback<Drawable>() {

								@Override
								public void onSuccess(Drawable arg0) {
									// TODO Auto-generated method stub
								}

								@Override
								public void onFinished() {
									// TODO Auto-generated method stub

								}

								@Override
								public void onError(Throwable arg0, boolean arg1) {
									// TODO Auto-generated method stub
								}

								@Override
								public void onCancelled(CancelledException arg0) {
									// TODO Auto-generated method stub
								}
							});
					// 名字
					tvUserHomepageName.setText(userName);
					// 粉丝数
					tvUserHomepageFansNum.setText(followNum);
					// idea
					tvUserHomepageIdea.setText(userIder);
					// 等级
					tvUserHomepageLevel.setText(userLevel);
					// 推荐数
					tvUserHomepageRecommendNum.setText(employNum);

					if (newArticleList != null && newArticleList.size() > 0) {
						if (mType == DROP_REFRESH) {
							// 如果是下拉刷新，清空list
							articleList.clear();
							articleList = new ArrayList<MeitanBeanArticle>();
						}
						articleList.addAll(newArticleList);
						mUserHomepageAdapter.setData(articleList);
						// 页面加1
						pages = pages + 1;
						// 只有一页不到
						if (articleList.size() <= page_size) {
							pbViewLoadingMore.setVisibility(View.GONE);
							tvViewLoadingMore.setText("没有更多了...");
						}
					} else {
						pbViewLoadingMore.setVisibility(View.GONE);
						tvViewLoadingMore.setText("没有更多了...");
					}

				}

				updateCooldown = false;
				// 下拉刷新结束
				if (srlUserHomepage.isRefreshing()) {
					srlUserHomepage.setRefreshing(false);
				}
				break;
			// 获取用户主页信息失败
			case NetworkAsyncCommonDefines.GET_USER_ZHUYE_INFO_F:
				// 网络错误
				pbViewLoadingMore.setVisibility(View.GONE);
				tvViewLoadingMore.setText("没有更多了...");

				updateCooldown = false;
				// 下拉刷新结束
				if (srlUserHomepage.isRefreshing()) {
					srlUserHomepage.setRefreshing(false);
				}
				break;
			default:
				break;
			}
		};
	};
}
