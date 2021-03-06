package cn.dressbook.ui.fragment;

import java.util.ArrayList;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.R;
import cn.dressbook.ui.adapter.JiaoYiJiLuAdapter;
import cn.dressbook.ui.adapter.XFJLAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.model.JYJL;
import cn.dressbook.ui.net.JiaoYiJiLuExec;
import cn.dressbook.ui.net.XFJLExec;
import cn.dressbook.ui.recyclerview.HorizontalDividerItemDecoration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

@ContentView(R.layout.jyjl)
public class JiaoYiJiLuFragment extends BaseFragment {

	private static final String ARG_POSITION = "position";
	/**
	 * 暂无数据提示
	 */
	@ViewInject(R.id.hint_tv)
	private TextView hint_tv;

	@ViewInject(R.id.recyclerview)
	private RecyclerView recyclerview;
	/**
	 * 下拉刷新view
	 */
	@ViewInject(R.id.swiperefreshlayout)
	private SwipeRefreshLayout swiperefreshlayout;
	private XFJLAdapter mXFJLAdapter;
	private String reason;
	private int curPage = 1;
	private int pageSize = 50;
	private ArrayList<JYJL> mList = new ArrayList<JYJL>();
	private int lastVisibleItem;
	private LinearLayoutManager mLinearLayoutManager;

	public static JiaoYiJiLuFragment newInstance(String reason) {
		
		JiaoYiJiLuFragment f = new JiaoYiJiLuFragment();
		Bundle b = new Bundle();
		b.putString(ARG_POSITION, reason);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		reason = getArguments().getString(ARG_POSITION);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		swiperefreshlayout.setColorSchemeResources(R.color.red1);
		swiperefreshlayout.setSize(SwipeRefreshLayout.DEFAULT);
		if (mList != null && mList.size() > 0) {
			mList.clear();
		}
		// 下拉刷新
		swiperefreshlayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				curPage = 1;
				mList.clear();
				// 获取交易记录列表
				JiaoYiJiLuExec.getInstance().getJiaoYiJiLuList(mHandler,
						ManagerUtils.getInstance().getUser_id(getActivity()),
						reason, curPage, pageSize,
						NetworkAsyncCommonDefines.GET_JYJL_LIST_S,
						NetworkAsyncCommonDefines.GET_JYJL_LIST_F);
			}
		});
		// 分割线
		recyclerview
				.addItemDecoration(new HorizontalDividerItemDecoration.Builder(
						mContext)
						.color(getResources().getColor(R.color.zhuye_bg))
						.size(getResources().getDimensionPixelSize(
								R.dimen.divider1))
						.margin(getResources().getDimensionPixelSize(
								R.dimen.leftmargin_no),
								getResources().getDimensionPixelSize(
										R.dimen.rightmargin_no)).build());
		// 上拉加载
		recyclerview.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(RecyclerView recyclerView,
					int newState) {
				// TODO Auto-generated method stub
				super.onScrollStateChanged(recyclerView, newState);
				if (newState == RecyclerView.SCROLL_STATE_IDLE
						&& lastVisibleItem + 1 == mXFJLAdapter.getItemCount()) {
					swiperefreshlayout.setRefreshing(true);
					curPage++;
					// 获取交易记录列表
					JiaoYiJiLuExec.getInstance().getJiaoYiJiLuList(
							mHandler,
							ManagerUtils.getInstance()
									.getUser_id(getActivity()), reason,
							curPage, pageSize,
							NetworkAsyncCommonDefines.GET_JYJL_LIST_S,
							NetworkAsyncCommonDefines.GET_JYJL_LIST_F);
				}

			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				lastVisibleItem = mLinearLayoutManager
						.findLastVisibleItemPosition();
			}
		});
		mXFJLAdapter = new XFJLAdapter(getActivity(), mHandler);
		mLinearLayoutManager = new LinearLayoutManager(getActivity());
		recyclerview.setLayoutManager(mLinearLayoutManager);
		recyclerview.setAdapter(mXFJLAdapter);
		// 获取交易记录列表
		JiaoYiJiLuExec.getInstance().getJiaoYiJiLuList(mHandler,
				ManagerUtils.getInstance().getUser_id(getActivity()), reason,
				curPage, pageSize, NetworkAsyncCommonDefines.GET_JYJL_LIST_S,
				NetworkAsyncCommonDefines.GET_JYJL_LIST_F);
	}



	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub

	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			// 获取交易记录列表成功
			case NetworkAsyncCommonDefines.GET_JYJL_LIST_S:
				Bundle data = msg.getData();
				if (data != null) {
					ArrayList<JYJL> list = data.getParcelableArrayList("list");
					if (list != null && list.size() > 0) {
						mList.addAll(list);
						mXFJLAdapter.setData(mList);
						mXFJLAdapter.notifyDataSetChanged();
					} else if (mList != null && mList.size() > 0) {

						Toast.makeText(mContext, "没有更多数据了", Toast.LENGTH_SHORT)
								.show();
					}
					if (mList == null || mList.size() == 0) {
						hint_tv.setVisibility(View.VISIBLE);
					} else {
						hint_tv.setVisibility(View.GONE);
					}
				}
				swiperefreshlayout.setRefreshing(false);
				break;
			// 获取交易记录列表成功
			case NetworkAsyncCommonDefines.GET_JYJL_LIST_F:

				break;
			default:
				break;
			}
		}

	};
}