package cn.dressbook.ui;

import java.util.ArrayList;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.adapter.YuanGongAdapter;
import cn.dressbook.ui.adapter.YingShouJiLuAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.model.DianPu;
import cn.dressbook.ui.model.User;
import cn.dressbook.ui.net.DianPuExec;
import cn.dressbook.ui.net.JiaoYiJiLuExec;
import cn.dressbook.ui.recyclerview.HorizontalDividerItemDecoration;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @description 员工界面
 * @author 袁东华
 * @date 2016-3-16
 */
@ContentView(R.layout.dplist)
public class YuanGongActivity extends BaseActivity {
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	@ViewInject(R.id.operate_iv)
	private ImageView operate_iv;
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
	private LinearLayoutManager mLinearLayoutManager;
	private YuanGongAdapter mYuanGongAdapter;
	private ArrayList<User> dpList;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("员工");
		operate_iv.setImageResource(R.drawable.add_yg);
		operate_iv.setVisibility(View.VISIBLE);
		swiperefreshlayout.setColorSchemeResources(R.color.red1);
		swiperefreshlayout.setSize(SwipeRefreshLayout.DEFAULT);
		mYuanGongAdapter = new YuanGongAdapter(activity, mHandler);
		mLinearLayoutManager = new LinearLayoutManager(activity);
		recyclerview.setLayoutManager(mLinearLayoutManager);
		recyclerview.setAdapter(mYuanGongAdapter);
		// 分割线
		recyclerview
				.addItemDecoration(new HorizontalDividerItemDecoration.Builder(
						activity)
						.color(getResources().getColor(R.color.zhuye_bg))
						.size(getResources().getDimensionPixelSize(
								R.dimen.divider1))
						.margin(getResources().getDimensionPixelSize(
								R.dimen.leftmargin_no),
								getResources().getDimensionPixelSize(
										R.dimen.rightmargin_no)).build());
		// 下拉刷新
		swiperefreshlayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				// 获取员工列表
				DianPuExec.getInstance().getYuanGongList(mHandler,
						ManagerUtils.getInstance().getUser_id(activity),
						NetworkAsyncCommonDefines.GET_BZ_LIST_S,
						NetworkAsyncCommonDefines.GET_BZ_LIST_F);
			}
		});
	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		// 获取员工列表
		DianPuExec.getInstance().getYuanGongList(mHandler,
				ManagerUtils.getInstance().getUser_id(activity),
				NetworkAsyncCommonDefines.GET_BZ_LIST_S,
				NetworkAsyncCommonDefines.GET_BZ_LIST_F);
	}

	@Event({ R.id.back_rl, R.id.operate_iv })
	private void onClick(View v) {
		switch (v.getId()) {
		// 点击添加员工
		case R.id.operate_iv:
			startActivity(new Intent(activity, AddYuanGongActivity.class));
			break;
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
			// 获取员工列表成功
			case NetworkAsyncCommonDefines.GET_BZ_LIST_S:
				Bundle data = msg.getData();
				if (data != null) {
					dpList = data.getParcelableArrayList("list");
					mYuanGongAdapter.setData(dpList);
					mYuanGongAdapter.notifyDataSetChanged();
				}
				swiperefreshlayout.setRefreshing(false);
				break;
			// 获取员工列表成功
			case NetworkAsyncCommonDefines.GET_BZ_LIST_F:
				swiperefreshlayout.setRefreshing(false);
				break;
			default:
				break;
			}
		}
	};
}
