package cn.dressbook.ui.fragment;

import java.util.ArrayList;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.R;
import cn.dressbook.ui.adapter.WDSJAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.model.LiangTiShuJu;
import cn.dressbook.ui.net.LSDZExec;
import cn.dressbook.ui.net.LTSJExec;
import cn.dressbook.ui.recyclerview.HorizontalDividerItemDecoration;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

/**
 * @description: 我的数据界面
 * @author:ydh
 * @data:2016-4-7上午9:24:10
 */
@ContentView(R.layout.wdsj)
public class WoDeShuJuFragment extends BaseFragment {
	@ViewInject(R.id.swipeRefreshLayout)
	private SwipeRefreshLayout swipeRefreshLayout;
	@ViewInject(R.id.recyclerView)
	private RecyclerView recyclerView;
	private ArrayList<LiangTiShuJu> wdLTSJList;
	private WDSJAdapter wdsjAdapter;

	public WoDeShuJuFragment() {
	}

	@Override
	protected void lazyLoad() {
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		try {
			swipeRefreshLayout.setColorSchemeResources(R.color.red1);
			swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
			// 下拉刷新
			swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

				@Override
				public void onRefresh() {
					// TODO Auto-generated method stub
					// 获取我的数据
					LTSJExec.getInstance().getLTSJList(mHandler,
							ManagerUtils.getInstance().getUser_id(mContext),
							NetworkAsyncCommonDefines.GET_LTSJ_S_WD,
							NetworkAsyncCommonDefines.GET_LTSJ_F_WD);
				}
			});

			recyclerView.setHasFixedSize(true);
			recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
			// 添加分割线
			recyclerView
					.addItemDecoration(new HorizontalDividerItemDecoration.Builder(
							getActivity())
							.color(getResources().getColor(R.color.zhuye_bg))
							.size(getResources().getDimensionPixelSize(
									R.dimen.divider5)).margin(0, 0).build());
			wdsjAdapter = new WDSJAdapter();
			recyclerView.setAdapter(wdsjAdapter);
			initData();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void initData() {
		// TODO Auto-generated method stub
		pbDialog.show();
		// 获取我的数据
		LTSJExec.getInstance().getLTSJList(mHandler,
				ManagerUtils.getInstance().getUser_id(mContext),
				NetworkAsyncCommonDefines.GET_LTSJ_S_WD,
				NetworkAsyncCommonDefines.GET_LTSJ_F_WD);
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			// 获取我的量体数据成功
			case NetworkAsyncCommonDefines.GET_LTSJ_S_WD:

				Bundle wdsjData = msg.getData();
				if (wdsjData != null) {
					String lastOper = wdsjData.getString("lastOper");
					String lastOpTime = wdsjData.getString("lastOpTime");
					if (refreshLTS != null) {
						refreshLTS.onRefresh(lastOper, lastOpTime);
					}
					wdLTSJList = wdsjData.getParcelableArrayList("ltsj");
					wdsjAdapter.setData(wdLTSJList);
					wdsjAdapter.notifyDataSetChanged();
				}
				swipeRefreshLayout.setRefreshing(false);
				pbDialog.dismiss();
				break;
			// 获取我的量体数据失败
			case NetworkAsyncCommonDefines.GET_LTSJ_F_WD:
				pbDialog.dismiss();
				swipeRefreshLayout.setRefreshing(false);
				break;

			default:
				break;
			}
		}

	};
	private RefreshLTS refreshLTS;

	public void setRefreshLTS(RefreshLTS refreshLTS) {
		this.refreshLTS = refreshLTS;
	}

	public interface RefreshLTS {
		void onRefresh(String lastOper, String lastOpTime);
	}

	public ArrayList<LiangTiShuJu> getWdLTSJList() {
		return wdLTSJList;
	}
}
