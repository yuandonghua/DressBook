package cn.dressbook.ui;

import java.util.ArrayList;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.adapter.TiXianJiLuAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.model.JYJL;
import cn.dressbook.ui.net.JiaoYiJiLuExec;
import cn.dressbook.ui.recyclerview.HorizontalDividerItemDecoration;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.View;
import android.widget.TextView;

/**
 * @description 提现记录
 * @author 袁东华
 * @date 2016-2-22
 */
@ContentView(R.layout.tixianjilu)
public class TiXianJiLuActivity extends BaseActivity {
	private Context mContext = TiXianJiLuActivity.this;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	@ViewInject(R.id.swiperefreshlayout)
	private SwipeRefreshLayout swiperefreshlayout;
	@ViewInject(R.id.recyclerview)
	private RecyclerView recyclerview;
	private int curPage = 1;
	private int pageSize = 50;
	private ArrayList<JYJL> mList = new ArrayList<JYJL>();
	private int lastVisibleItem;
	private LinearLayoutManager mLinearLayoutManager;
	private TiXianJiLuAdapter mTiXianJiLuAdapter;
	/**
	 * 暂无数据提示
	 */
	@ViewInject(R.id.hint_tv)
	private TextView hint_tv;

	@SuppressWarnings("deprecation")
	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("提现记录");
		swiperefreshlayout.setColorSchemeResources(R.color.red1);
		swiperefreshlayout.setSize(SwipeRefreshLayout.DEFAULT);
		// 下拉刷新
		swiperefreshlayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				curPage = 1;
				mList.clear();
				JiaoYiJiLuExec.getInstance().getTiXianJiLuList(mHandler,
						ManagerUtils.getInstance().yjtc.getId(), curPage,
						pageSize, NetworkAsyncCommonDefines.GET_TXJL_LIST_S,
						NetworkAsyncCommonDefines.GET_TXJL_LIST_F);
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
						&& lastVisibleItem + 1 == mTiXianJiLuAdapter
								.getItemCount()) {
					swiperefreshlayout.setRefreshing(true);
					curPage++;
					JiaoYiJiLuExec.getInstance().getTiXianJiLuList(mHandler,
							ManagerUtils.getInstance().yjtc.getId(),
							curPage, pageSize,
							NetworkAsyncCommonDefines.GET_TXJL_LIST_S,
							NetworkAsyncCommonDefines.GET_TXJL_LIST_F);
				}

			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				lastVisibleItem = mLinearLayoutManager
						.findLastVisibleItemPosition();
			}
		});
		mTiXianJiLuAdapter = new TiXianJiLuAdapter(mContext, mHandler);
		mLinearLayoutManager = new LinearLayoutManager(mContext);
		recyclerview.setLayoutManager(mLinearLayoutManager);
		recyclerview.setAdapter(mTiXianJiLuAdapter);
	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		if (ManagerUtils.getInstance().yjtc.getId() != null) {
			pbDialog.show();
			JiaoYiJiLuExec.getInstance().getTiXianJiLuList(mHandler,
					ManagerUtils.getInstance().yjtc.getId(), curPage,
					pageSize, NetworkAsyncCommonDefines.GET_TXJL_LIST_S,
					NetworkAsyncCommonDefines.GET_TXJL_LIST_F);
		} else {
			hint_tv.setVisibility(View.VISIBLE);
		}
	}

	@Event({ R.id.back_rl, R.id.operate_tv })
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
			// 获取提现记录列表成功
			case NetworkAsyncCommonDefines.GET_TXJL_LIST_S:
				Bundle data = msg.getData();
				if (data != null) {
					ArrayList<JYJL> jyjlList = data
							.getParcelableArrayList("list");
					if (jyjlList != null && jyjlList.size() > 0) {
						mList.addAll(jyjlList);
					}
					if (mList == null || mList.size() == 0) {
						hint_tv.setVisibility(View.VISIBLE);
					} else {
						hint_tv.setVisibility(View.GONE);
					}
					LogUtil.e("mList:" + mList.size());
					mTiXianJiLuAdapter.setData(mList);
					mTiXianJiLuAdapter.notifyDataSetChanged();
				}
				pbDialog.dismiss();
				swiperefreshlayout.setRefreshing(false);
				break;
			// 获取提现记录列表失败
			case NetworkAsyncCommonDefines.GET_TXJL_LIST_F:
				pbDialog.dismiss();
				swiperefreshlayout.setRefreshing(false);
				break;
			default:
				break;
			}
		}

	};
}
