
package cn.dressbook.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.FaBoWenActivity;
import cn.dressbook.ui.R;
import cn.dressbook.ui.adapter.WoDeGuanZhuAdapter;
import cn.dressbook.ui.bean.MeitanBeanArticle;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.listener.RefreshBoWen;
import cn.dressbook.ui.net.MeiTanExec;

/**
 * @description 我的关注列表
 * @author 袁东华
 * @time 2015-12-9上午11:50:12
 */
@ContentView(R.layout.fragment_focus)
public class WoDeGuanZhuFragment extends BaseFragment {
	@ViewInject(R.id.srlFocus)
	private SwipeRefreshLayout srlFocus;
	@ViewInject(R.id.lvFocus)
	private ListView lvFocus;

	private View fvLoadingMore;
	private ProgressBar pbViewLoadingMore;
	private TextView tvViewLoadingMore;

	private List<MeitanBeanArticle> articleList;

	private WoDeGuanZhuAdapter mFocusAdapter;

	/**
	 * TODO 更新冷却
	 */
	private boolean updateCooldown = false;
	/**
	 * TODO 当前页数
	 */
	private int pages = 1, page_size = 20;
	/**
	 * TODO 下拉刷新
	 */
	public static final int DROP_REFRESH = 0;
	/**
	 * TODO 上拉加载
	 */
	public static final int PULL_REFRESH = 1;
	private int mType;

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		// 初始化控件
		initView();
		// 初始化数据
		initData();
		// 设置控件监听
		setListener();
		// 执行初始化任务
		performTask();

	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-15 下午10:20:08
	 * @see
	 */
	protected void initView() {
		// 列表加载更多的footerview
		fvLoadingMore = ((Activity) mContext).getLayoutInflater().inflate(
				R.layout.view_loading_more, null);
		pbViewLoadingMore = (ProgressBar) fvLoadingMore
				.findViewById(R.id.pbViewLoadingMore);
		tvViewLoadingMore = (TextView) fvLoadingMore
				.findViewById(R.id.tvViewLoadingMore);
	}

	protected void initData() {
		// 文章列表
		articleList = new ArrayList<MeitanBeanArticle>();
		mFocusAdapter = new WoDeGuanZhuAdapter(getActivity(), mHandler);
		lvFocus.addFooterView(fvLoadingMore);
		lvFocus.setAdapter(mFocusAdapter);
		// 下拉刷新样式
		srlFocus.setColorSchemeColors(
				mContext.getResources().getColor(R.color.main_red), mContext
						.getResources().getColor(R.color.main_red), mContext
						.getResources().getColor(R.color.main_red), mContext
						.getResources().getColor(R.color.main_red));
	}

	protected void setListener() {
		// 发完博文刷新列表
		FaBoWenActivity.setOnRefreshBoWenList(new RefreshBoWen() {

			@Override
			public void onRefresh(boolean boo) {
				// TODO Auto-generated method stub
				// 执行初始化任务
				performTask();
			}
		});
		lvFocus.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

				switch (scrollState) {
				// 滑动停止
				case OnScrollListener.SCROLL_STATE_IDLE:
					// 监听拉到最后一行
					if (view.getLastVisiblePosition() == view.getCount() - 1) {
						// 上拉加载
						refreshDataFocus(PULL_REFRESH);
					}
					break;
				// 拖拽
				case OnScrollListener.SCROLL_STATE_FLING:
					break;
				// 滑动
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
					break;
				default:
					break;
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			}
		});
		// 下拉刷新
		srlFocus.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// 下拉刷新
				refreshDataFocus(DROP_REFRESH);
			}
		});
	}

	protected void performTask() {
		refreshDataFocus(DROP_REFRESH);
	}

	/**
	 * 
	 * TODO 发起请求获取数据
	 * 
	 * @author LiShen
	 * @date 2015-10-18 下午10:35:18
	 * @see
	 */
	public void refreshDataFocus(final int type) {
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

			// 获取我的关注列表
			MeiTanExec.getInstance().getWDGZList(mHandler,
					ManagerUtils.getInstance().getUser_id(mContext), pages,
					page_size, NetworkAsyncCommonDefines.GET_WDGZ_LIST_S,
					NetworkAsyncCommonDefines.GET_WDGZ_LIST_F);

		}
	}

	@Override
	public void onStart() {
		super.onStart();
		refreshDataFocus(DROP_REFRESH);

	}

	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub

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
			// 获取我的关注列表成功
			case NetworkAsyncCommonDefines.GET_WDGZ_LIST_S:
				Bundle listData = msg.getData();
				if (listData != null) {
					List<MeitanBeanArticle> newArticleList = listData
							.getParcelableArrayList("list");
					if (newArticleList != null && newArticleList.size() > 0) {
						if (mType == DROP_REFRESH) {
							// 如果是下拉刷新，清空list
							articleList.clear();
							articleList = new ArrayList<MeitanBeanArticle>();
						}
						articleList.addAll(newArticleList);
						mFocusAdapter.setData(articleList);
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
				// 下拉刷新结束
				if (srlFocus.isRefreshing()) {
					srlFocus.setRefreshing(false);
				}
				updateCooldown = false;
				break;
			// 获取我的关注列表失败
			case NetworkAsyncCommonDefines.GET_WDGZ_LIST_F:
				// 网络错误
				pbViewLoadingMore.setVisibility(View.GONE);
				tvViewLoadingMore.setText("没有更多了...");
				updateCooldown = false;
				// 下拉刷新结束
				if (srlFocus.isRefreshing()) {
					srlFocus.setRefreshing(false);
				}
				break;
			default:
				break;
			}
		};

	};
}
