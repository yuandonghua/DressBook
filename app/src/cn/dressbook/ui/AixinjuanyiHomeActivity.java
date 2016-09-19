/*
 * Copyright by Gifted Youngs Workshop and the original author or authors.
 * 
 * This document only allow internal use , any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Gifted Youngs Workshop from
 * 
 * giftedyoungs@163.com
 * 
 *
 * 此代码由天才少年工作小组开发完成, 仅限内部使用 
 * 外部使用该代码将负相应的法律责任
 * 更多信息请致信天才少年工作小组
 * 
 * giftedyoungs@163.com
 *
 */
package cn.dressbook.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.adapter.ProjectAdapter;
import cn.dressbook.ui.adapter.ProjectAdapter.OnLoadState;
import cn.dressbook.ui.bean.AixinjuanyiBeanProject;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.net.AiXinJuanYiExec;
import cn.dressbook.ui.net.MeiTanExec;

/**
 * 
 * TODO 爱心捐衣主页
 * 
 * @author LiShen
 * @company Gifted Youngs Workshop
 * @date 2015-10-30 上午11:30:47
 * @since
 * @version
 */
@ContentView(R.layout.activity_aixinjuanyi_home)
public class AixinjuanyiHomeActivity extends BaseActivity {
	private Activity activity;

	/**
	 * 返回按钮
	 */
	@ViewInject(R.id.back_rl)
	private RelativeLayout back_rl;
	/**
	 * 爱心记录
	 */
	@ViewInject(R.id.operate_tv)
	private TextView operate_tv;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	private SwipeRefreshLayout srlAixinjuanyiHome;
	private ListView lvAixinjuanyiHome;

	private View fvLoadingMore;
	private ProgressBar pbViewLoadingMore;
	private TextView tvViewLoadingMore;

	private List<AixinjuanyiBeanProject> projectList;

	private ProjectAdapter projectAdapter;

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
	private int page_size = 20;

	private Context mContext = AixinjuanyiHomeActivity.this;
	private int mType;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		operate_tv.setText("爱心记录");
		operate_tv.setVisibility(View.VISIBLE);
		title_tv.setText("爱心捐衣");
		srlAixinjuanyiHome = (SwipeRefreshLayout) findViewById(R.id.srlAixinjuanyiHome);
		lvAixinjuanyiHome = (ListView) findViewById(R.id.lvAixinjuanyiHome);
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
		// 文章列表
		projectList = new ArrayList<AixinjuanyiBeanProject>();
		projectAdapter = new ProjectAdapter(activity, mHandler);
		projectAdapter.setOnLoadState(new OnLoadState() {

			@Override
			public void loadState(boolean isLoad) {
				// TODO Auto-generated method stub
				Load = isLoad;
			}
		});
		lvAixinjuanyiHome.addFooterView(fvLoadingMore);
		lvAixinjuanyiHome.setAdapter(projectAdapter);
		// 下拉刷新样式
		srlAixinjuanyiHome.setColorSchemeColors(activity.getResources()
				.getColor(R.color.main_red),
				activity.getResources().getColor(R.color.main_red), activity
						.getResources().getColor(R.color.main_red), activity
						.getResources().getColor(R.color.main_red));
		performTask();

	}

	private boolean Load = false;

	@Override
	protected void onResume() {
		super.onResume();
		if (Load) {
			refreshDataAixinjuanyiHome(DROP_REFRESH);
			Load = false;
		}

	}

	@Event({ R.id.back_rl, R.id.operate_tv })
	private void onClick(View v) {
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		// 点击爱心记录
		case R.id.operate_tv:
			if (ManagerUtils.getInstance().isLogin(mContext)) {

				startActivity(new Intent(activity, AiXinJiLuActivity.class));
			} else {
				startActivity(new Intent(activity, LoginActivity.class));
			}

			break;
		default:
			break;
		}
	}

	protected void setListener() {
		// 上拉加载
		lvAixinjuanyiHome.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					// 监听拉到最后一行
					if (view.getLastVisiblePosition() == view.getCount() - 1) {
						// 上拉加载
						refreshDataAixinjuanyiHome(PULL_REFRESH);
					}
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			}
		});
		// 下拉刷新
		srlAixinjuanyiHome.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// 下拉刷新
				refreshDataAixinjuanyiHome(DROP_REFRESH);
			}
		});
	}

	protected void performTask() {
		refreshDataAixinjuanyiHome(DROP_REFRESH);
	}

	private void refreshDataAixinjuanyiHome(final int type) {
		mType = type;
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
			// 获取爱心捐衣列表
			AiXinJuanYiExec.getInstance().getAiXinJuanYiList(mHandler,
					ManagerUtils.getInstance().getUser_id(mContext),
					pages + "", page_size + "",
					NetworkAsyncCommonDefines.GET_AIXINJUANYI_LIST_S,
					NetworkAsyncCommonDefines.GET_AIXINJUANYI_LIST_F);

			// // 网络错误
			// pbViewLoadingMore.setVisibility(View.GONE);
			// tvViewLoadingMore.setText("没有更多了...");
			//
			// updateCooldown = false;
			// // 下拉刷新结束
			// if (srlAixinjuanyiHome.isRefreshing()) {
			// srlAixinjuanyiHome.setRefreshing(false);
			// }
		}
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 获取爱心捐衣列表成功
			case NetworkAsyncCommonDefines.GET_AIXINJUANYI_LIST_S:
				Bundle listData = msg.getData();
				if (listData != null) {
					List<AixinjuanyiBeanProject> newProjectList = listData
							.getParcelableArrayList("newProjectList");
					if (newProjectList != null && newProjectList.size() > 0) {
						LogUtil.e(" newProjectList.size() :"
								+ newProjectList.size());
						if (mType == DROP_REFRESH) {
							// 如果是下拉刷新，清空list
							projectList.clear();
							projectList = new ArrayList<AixinjuanyiBeanProject>();
						}
						projectList.addAll(newProjectList);
						projectAdapter.setData(projectList);
						// 页面加1
						pages = pages + 1;
						// 只有一页不到
						if (projectList.size() < page_size) {
							fvLoadingMore.setVisibility(View.GONE);
							// pbViewLoadingMore.setVisibility(View.GONE);
							// tvViewLoadingMore.setText("没有更多了...");
						}
					} else {
						LogUtil.e(" 数据为空");
						pbViewLoadingMore.setVisibility(View.GONE);
						tvViewLoadingMore.setText("没有更多了...");
					}
				}
				if (srlAixinjuanyiHome.isRefreshing()) {
					srlAixinjuanyiHome.setRefreshing(false);
				}
				updateCooldown = false;
				break;
			// 获取爱心捐衣列表失败
			case NetworkAsyncCommonDefines.GET_AIXINJUANYI_LIST_F:
				break;
			default:
				break;
			}
		};
	};
}
