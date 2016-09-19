package cn.dressbook.ui;

import java.util.ArrayList;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.dressbook.ui.adapter.LSDZAdapter;
import cn.dressbook.ui.adapter.ZldzAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.model.DZSPFL;
import cn.dressbook.ui.net.LSDZExec;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @description: 根据种类定制页面
 * @author:熊天富
 * @time:2016-01-22 16:22:36
 */
@ContentView(R.layout.activity_zldz)
public class ZldzActivity extends BaseActivity {
	private Context mContext;
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
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/**
	 * 分类
	 */
	@ViewInject(R.id.operate_tv)
	private TextView operate_tv;
	/**
	 * 品类的名称
	 */
	private String title;
	/**
	 * 品类的id
	 */
	private String clsId;
	private LinearLayoutManager mLinearLayoutManager;
	private ZldzAdapter zldzAdapter;

	@Override
	protected void initView() {
		mContext = this;
		Intent intent = getIntent();
		title = intent.getStringExtra("title");
		clsId = intent.getStringExtra("cls_id");
		title_tv.setText(title);
		operate_tv.setText("分类");
		operate_tv.setVisibility(View.VISIBLE);
		swiperefreshlayout.setColorSchemeResources(R.color.red1);
		swiperefreshlayout.setSize(SwipeRefreshLayout.DEFAULT);
		// 下拉刷新
		swiperefreshlayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {

				// 获取种类定制列表
				LSDZExec.getInstance().getZldzList(mHandler, clsId, 0, 0,
						NetworkAsyncCommonDefines.GET_ZLDZ_S,
						NetworkAsyncCommonDefines.GET_ZLDZ_F);
			}
		});

	}

	// 下拉刷新的页数
	private int index = 1;

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		zldzAdapter = new ZldzAdapter(ZldzActivity.this, mHandler);
		mLinearLayoutManager = new LinearLayoutManager(mContext);
		recyclerview.setLayoutManager(mLinearLayoutManager);

		// 获取种类定制列表
		LSDZExec.getInstance().getZldzList(mHandler, clsId, 0, 0,
				NetworkAsyncCommonDefines.GET_ZLDZ_S,
				NetworkAsyncCommonDefines.GET_ZLDZ_F);

	}

	@Event({ R.id.back_rl, R.id.operate_tv })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		// 点击分类
		case R.id.operate_tv:
			startActivity(new Intent(ZldzActivity.this, LSDZFLActivity.class));
			finish();
			break;
		default:
			break;
		}

	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			// 获取种类定制列表成功
			case NetworkAsyncCommonDefines.GET_ZLDZ_S:
				Bundle myxData = msg.getData();
				if (myxData != null) {
					DZSPFL zldz = myxData.getParcelable("dzspfl");
					zldzAdapter.setData(zldz);
					recyclerview.setAdapter(zldzAdapter);

				}
				zldzAdapter.notifyDataSetChanged();
				swiperefreshlayout.setRefreshing(false);
				break;
			// 获取种类定制列表失败
			case NetworkAsyncCommonDefines.GET_ZLDZ_F:
				swiperefreshlayout.setRefreshing(false);
				break;

			}

		};
	};

}
