package cn.dressbook.ui.fragment;

import java.util.ArrayList;
import java.util.List;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.FaBoWenActivity;
import cn.dressbook.ui.R;
import cn.dressbook.ui.adapter.SheQuGuangChangAdapter;
import cn.dressbook.ui.adapter.TopicIndicatorAdapter;
import cn.dressbook.ui.bean.MeitanBeanArticle;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.listener.RefreshBoWen;
import cn.dressbook.ui.net.MeiTanExec;
import cn.dressbook.ui.view.HorizontalListView;

/**
 * TODO 社区广场界面
 * 
 * @author LiShen
 * @company Gifted Youngs Workshop
 * @date 2015-10-15 下午9:35:19
 * @since
 * @version
 */
@ContentView(R.layout.fragment_square)
public class SheQuGuangChangFragment extends BaseFragment {
	@ViewInject(R.id.lvSquareTopics)
	private HorizontalListView lvSquareTopics;
	@ViewInject(R.id.srlSquare)
	private SwipeRefreshLayout srlSquare;
	@ViewInject(R.id.lvSquare)
	private ListView lvSquare;
	private View fvLoadingMore;
	private ProgressBar pbViewLoadingMore;
	private TextView tvViewLoadingMore;
	private List<MeitanBeanArticle> articleList;
	private String[] topics = { "全部" };
	private SheQuGuangChangAdapter mSquareAdapter;
	private TopicIndicatorAdapter topicIndicatorAdapter;

	/**
	 * TODO 选择了哪个标签
	 */
	private int topicChoice = 0;
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
	private static final int DROP_REFRESH = 0;
	/**
	 * TODO 上拉加载
	 */
	private static final int PULL_REFRESH = 1;
	private int mType, page_size = 20;

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
		initData();
		// 设置控件监听
		setListener();
		// 执行初始化任务
		performTask();
	}

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
		// 文章列表适配器
		articleList = new ArrayList<MeitanBeanArticle>();
		mSquareAdapter = new SheQuGuangChangAdapter(getActivity(), mHandler);
		lvSquare.addFooterView(fvLoadingMore);
		lvSquare.setAdapter(mSquareAdapter);
		// 指示器
		topicIndicatorAdapter = new TopicIndicatorAdapter(getActivity(), topics);
		lvSquareTopics.setAdapter(topicIndicatorAdapter);
		// 下拉刷新样式
		srlSquare.setColorSchemeColors(
				mContext.getResources().getColor(R.color.main_red), mContext
						.getResources().getColor(R.color.main_red), mContext
						.getResources().getColor(R.color.main_red), mContext
						.getResources().getColor(R.color.main_red));
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-15 下午10:20:03
	 * @see
	 */
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
		// 点击话题
		lvSquareTopics.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				topicIndicatorAdapter.setTopicChoice(position);
				topicChoice = position;
				// 根据所选的topic更新文章数据
				refreshDataSquare(DROP_REFRESH);
			}
		});
		lvSquare.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				// 滑动停止
				case OnScrollListener.SCROLL_STATE_IDLE:
					// 监听拉到最后一行
					if (view.getLastVisiblePosition() == view.getCount() - 1) {
						// 上拉加载
						refreshDataSquare(PULL_REFRESH);
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
		srlSquare.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// 下拉刷新
				refreshDataSquare(DROP_REFRESH);
			}
		});
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-15 下午10:19:59
	 * @see
	 */
	protected void performTask() {
		refreshDataSquare(DROP_REFRESH);
	}

	/**
	 * TODO 发送请求获取话题标签
	 * 
	 * @author LiShen
	 * @date 2015-10-17 下午1:26:59
	 * @see
	 */
	private void queryTopicsSquare() {
		// 获取话题列表
		MeiTanExec.getInstance().getHuaTiList(mHandler, "sqfx_subtag",
				NetworkAsyncCommonDefines.GET_HUATI_LIST_S,
				NetworkAsyncCommonDefines.GET_HUATI_LIST_F);
	}

	/**
	 * 
	 * TODO 更新数据并显示
	 * 
	 * @author LiShen
	 * @date 2015-10-18 下午10:35:18
	 * @see
	 */
	private void refreshDataSquare(final int type) {
		mType = type;
		if (!updateCooldown) {
			updateCooldown = true;
			// 如果是下拉刷新
			if (type == DROP_REFRESH) {
				// 刷新话题标签
				queryTopicsSquare();
				pages = 1;
			}
			// 如果是上拉加载
			if (type == PULL_REFRESH) {
				tvViewLoadingMore.setText("努力加载中...");
				pbViewLoadingMore.setVisibility(View.VISIBLE);
			}

			// 获取社区广场列表
			MeiTanExec.getInstance().getSQGCList(mHandler,
					ManagerUtils.getInstance().getUser_id(mContext),
					topics[topicChoice], topicChoice, pages, page_size,
					NetworkAsyncCommonDefines.GET_SQGC_LIST_S,
					NetworkAsyncCommonDefines.GET_SQGC_LIST_F);
		}
	}

	/**
	 * 刷新页面
	 */
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// 初始化数据
		refreshDataSquare(DROP_REFRESH);

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
			// 获取社区广场列表成功
			case NetworkAsyncCommonDefines.GET_SQGC_LIST_S:
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
						mSquareAdapter.setData(articleList);
						// 页面加1
						pages = pages + 1;
						// 只有一页不到
						if (articleList.size() <= page_size) {
							pbViewLoadingMore.setVisibility(View.GONE);
							tvViewLoadingMore.setText("没有更多了...");
						}
					} else {
						if (mType == DROP_REFRESH) {
							// 如果是下拉刷新，清空list
							articleList.clear();
							articleList = new ArrayList<MeitanBeanArticle>();
							articleList.addAll(newArticleList);
							mSquareAdapter.setData(articleList);
							pbViewLoadingMore.setVisibility(View.GONE);
							tvViewLoadingMore.setText("暂无数据...");
						} else {
							pbViewLoadingMore.setVisibility(View.GONE);
							tvViewLoadingMore.setText("没有更多了...");
						}
					}
				}
				mSquareAdapter.notifyDataSetChanged();
				updateCooldown = false;
				// 下拉刷新结束
				if (srlSquare.isRefreshing()) {
					srlSquare.setRefreshing(false);
				}
				break;
			// 获取社区广场列表失败
			case NetworkAsyncCommonDefines.GET_SQGC_LIST_F:
				// 网络错误
				pbViewLoadingMore.setVisibility(View.GONE);
				tvViewLoadingMore.setText("没有更多了...");
				updateCooldown = false;
				// 下拉刷新结束
				if (srlSquare.isRefreshing()) {
					srlSquare.setRefreshing(false);
				}
				break;
			// 获取话题列表成功
			case NetworkAsyncCommonDefines.GET_HUATI_LIST_S:
				Bundle huatiData = msg.getData();
				if (huatiData != null) {
					String huati = huatiData.getString("huati");
					huati = "全部#" + huati;
					topics = null;
					topics = huati.split("#");
					// 传入adapter
					topicIndicatorAdapter.setTopics(topics);
				}
				break;
			// 获取话题列表失败
			case NetworkAsyncCommonDefines.GET_HUATI_LIST_F:

				break;
			default:
				break;
			}
		};

	};
}
