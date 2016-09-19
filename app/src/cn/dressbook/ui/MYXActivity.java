package cn.dressbook.ui;

import java.util.ArrayList;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.adapter.MYXAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.model.MYX1;
import cn.dressbook.ui.net.MYXExec;
import cn.dressbook.ui.net.RequirementExec;
import cn.dressbook.ui.recyclerview.FullyLinearLayoutManager;

/**
 * @description: 美衣讯
 * @author:袁东华
 * @time:2015-9-17下午3:45:54
 */
@ContentView(R.layout.myx)
public class MYXActivity extends BaseActivity {
	private Context mContext = MYXActivity.this;
	private ArrayList<MYX1> mList = new ArrayList<MYX1>();
	private MYXAdapter mMYXAdapter;
	private PopupWindow mPopupWindow;
	private int mPosition;
	/**
	 * 下拉刷新view
	 */
	@ViewInject(R.id.swiperefreshlayout)
	private SwipeRefreshLayout swiperefreshlayout;
	/** 返回按钮 */
	@ViewInject(R.id.back_rl)
	private RelativeLayout back_rl;
	/** 商品展示view */
	@ViewInject(R.id.recyclerview)
	private RecyclerView recyclerview;
	@ViewInject(R.id.all_tv)
	private TextView all_tv;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	private int page_num = 1;
	private int page_size = 20;
	private int lastVisibleItem;
	private FullyLinearLayoutManager mMyLinearLayoutManager;

	@SuppressWarnings("deprecation")
	@Override
	protected void initView() {
		title_tv.setText("美衣讯");
		swiperefreshlayout.setColorSchemeResources(R.color.red1);
		swiperefreshlayout.setSize(SwipeRefreshLayout.DEFAULT);
		// 下拉刷新
		swiperefreshlayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				page_num = 1;
				mList = null;
				// 获取美衣讯
				MYXExec.getInstance().getMYX(mHandler, page_num, page_size,
						NetworkAsyncCommonDefines.GET_MYX_S,
						NetworkAsyncCommonDefines.GET_MYX_F);
			}
		});
		recyclerview.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(RecyclerView recyclerView,
					int newState) {
				// TODO Auto-generated method stub
				super.onScrollStateChanged(recyclerView, newState);
				if (newState == RecyclerView.SCROLL_STATE_IDLE
						&& lastVisibleItem + 1 == mMYXAdapter.getItemCount()) {
					swiperefreshlayout.setRefreshing(true);
					// 上拉加载
					page_num++;
					// 获取美衣讯
					MYXExec.getInstance().getMYX(mHandler, page_num, page_size,
							NetworkAsyncCommonDefines.GET_MYX_S,
							NetworkAsyncCommonDefines.GET_MYX_F);
				}
				switch (newState) {
				// 滑动停止
				case RecyclerView.SCROLL_STATE_IDLE:
					break;
				// 拖拽
				case RecyclerView.SCROLL_STATE_DRAGGING:
					break;
				// 滑动
				case RecyclerView.SCROLL_STATE_SETTLING:
					break;
				default:
					break;
				}

			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				lastVisibleItem = mMyLinearLayoutManager
						.findLastVisibleItemPosition();
			}
		});
	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		mMYXAdapter = new MYXAdapter(MYXActivity.this, mHandler);
		mMyLinearLayoutManager = new FullyLinearLayoutManager(mContext);
		recyclerview.setLayoutManager(mMyLinearLayoutManager);
		recyclerview.setAdapter(mMYXAdapter);
		// 获取美衣讯
		MYXExec.getInstance().getMYX(mHandler, page_num, page_size,
				NetworkAsyncCommonDefines.GET_MYX_S,
				NetworkAsyncCommonDefines.GET_MYX_F);

	}

	@Event({ R.id.back_rl })
	private void onClick(View v) {
		// TODO Auto-generated method stub
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
			// 获取美衣讯成功
			case NetworkAsyncCommonDefines.GET_MYX_S:
				Bundle myxData = msg.getData();
				if (myxData != null) {
					ArrayList<MYX1> list = myxData
							.getParcelableArrayList("list");
					if (mList == null) {
						mList = new ArrayList<MYX1>();
					}
					if (list != null) {
						mList.addAll(list);
					}
					LogUtil.e("美衣讯的个数:"+mList.size());
					mMYXAdapter.setData(mList);
				}
				mMYXAdapter.notifyDataSetChanged();
				swiperefreshlayout.setRefreshing(false);
				break;
			// 获取美衣讯失败
			case NetworkAsyncCommonDefines.GET_MYX_F:

				break;

			default:
				break;
			}
		}

	};

}
