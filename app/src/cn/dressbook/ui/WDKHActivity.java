package cn.dressbook.ui;

import java.util.ArrayList;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.adapter.WDKHAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.model.KeHu;
import cn.dressbook.ui.net.KeHuExec;
import cn.dressbook.ui.recyclerview.HorizontalDividerItemDecoration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @description 我的客户界面
 * @author 袁东华
 * @date 2016-2-19
 */
@ContentView(R.layout.activity_wdkh)
public class WDKHActivity extends BaseActivity {
	private Context mContext = WDKHActivity.this;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/**
	 * 描述
	 */
	@ViewInject(R.id.tv_desc)
	private TextView tv_desc;
	/**
	 * 暂无数据提示
	 */
	@ViewInject(R.id.hint_tv)
	private TextView hint_tv;
	/**
	 * 下拉刷新
	 */
	@ViewInject(R.id.swiperefreshlayout)
	private SwipeRefreshLayout swipeRefreshLayout;
	@ViewInject(R.id.recyclerview)
	private RecyclerView recyclerView;
	private ArrayList<KeHu> mList;
	private String members = "";
	private WDKHAdapter mWDKHAdapter;

	@Override
	protected void initView() {
		title_tv.setText("我的客户");
		LinearLayoutManager llm = new LinearLayoutManager(mContext);
		recyclerView.setLayoutManager(llm);
		mWDKHAdapter = new WDKHAdapter(mContext, mHandler);
		recyclerView.setAdapter(mWDKHAdapter);
		// 分割线
		recyclerView
				.addItemDecoration(new HorizontalDividerItemDecoration.Builder(
						mContext)
						.color(getResources().getColor(R.color.zhuye_bg))
						.size(getResources().getDimensionPixelSize(
								R.dimen.divider1))
						.margin(getResources().getDimensionPixelSize(
								R.dimen.leftmargin_no),
								getResources().getDimensionPixelSize(
										R.dimen.rightmargin_no)).build());

		swipeRefreshLayout.setColorSchemeResources(R.color.red1);
		swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				initData();
			}
		});
	}

	@Override
	protected void initData() {
		// 获取客户列表
		KeHuExec.getInstance().getKeHuList(mHandler,
				ManagerUtils.getInstance().getUser_id(mContext),
				NetworkAsyncCommonDefines.GET_KH_LIST_S,
				NetworkAsyncCommonDefines.GET_KH_LIST_F);
	}

	@Event({ R.id.back_rl })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			finish();
			break;

		}

	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			// 获取客户列表成功
			case NetworkAsyncCommonDefines.GET_KH_LIST_S:
				Bundle data = msg.getData();
				if (data != null) {
					mList = data.getParcelableArrayList("list");
					members = data.getString("members");
					if (members == null || "".equals(members)) {
						tv_desc.setText("成为会员，拥有客户，乐享奖励");

					} else {

						tv_desc.setText(members);
					}
					if (mList == null || mList.size() == 0) {
						hint_tv.setVisibility(View.VISIBLE);
					} else {
						hint_tv.setVisibility(View.GONE);
					}
					mWDKHAdapter.setData(mList);
					mWDKHAdapter.notifyDataSetChanged();
				}
				swipeRefreshLayout.setRefreshing(false);
				break;
			// 获取客户列表失败
			case NetworkAsyncCommonDefines.GET_KH_LIST_F:
				swipeRefreshLayout.setRefreshing(false);
				if (mList == null || mList.size() == 0) {
					hint_tv.setVisibility(View.VISIBLE);
				} else {
					hint_tv.setVisibility(View.GONE);
				}
				break;
			}
		}

	};

}
