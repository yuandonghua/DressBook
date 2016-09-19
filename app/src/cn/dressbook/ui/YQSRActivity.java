package cn.dressbook.ui;

import java.util.ArrayList;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.adapter.YQSRAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.model.JYJL;
import cn.dressbook.ui.model.XFJL;
import cn.dressbook.ui.net.JiaoYiJiLuExec;
import cn.dressbook.ui.net.XFJLExec;
import cn.dressbook.ui.recyclerview.FullyLinearLayoutManager;
import cn.dressbook.ui.recyclerview.HorizontalDividerItemDecoration;

/**
 * @description 预期收入
 * @author 袁东华
 * @date 2016-2-19
 */
@ContentView(R.layout.expensecalendar_layout)
public class YQSRActivity extends BaseFragmentActivity {
	private Context mContext = YQSRActivity.this;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/**
	 * 预期收入金额
	 */
	@ViewInject(R.id.yqsr_tv)
	private TextView yqsr_tv;
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
	private String flag;
	private YQSRAdapter mYQSRAdapter;
	private String type;
	private int page_num = 1, page_size = 50;
	private ArrayList<JYJL> mList = new ArrayList<JYJL>();
	
	private FullyLinearLayoutManager mLinearLayoutManager;
	private String totalIncome = "";

	@SuppressWarnings("deprecation")
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("预期收入");
		swiperefreshlayout.setColorSchemeResources(R.color.red1);
		swiperefreshlayout.setSize(SwipeRefreshLayout.DEFAULT);
		// 下拉刷新
		swiperefreshlayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
			initData();
			}
		});
		recyclerview.setHasFixedSize(true);
		mLinearLayoutManager = new FullyLinearLayoutManager(mContext);
		mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		recyclerview.setLayoutManager(mLinearLayoutManager);
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
		mYQSRAdapter = new YQSRAdapter(mContext, mHandler);
		recyclerview.setAdapter(mYQSRAdapter);

	}

	protected void initData() {
		// TODO Auto-generated method stub

		JiaoYiJiLuExec.getInstance().getYQSRList(mHandler,
				ManagerUtils.getInstance().getUser_id(mContext),
				NetworkAsyncCommonDefines.GET_YBJ_RECORD_S,
				NetworkAsyncCommonDefines.GET_YBJ_RECORD_F);

	}

	@Event({ R.id.back_rl })
	private void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		// 点击返回按钮
		case R.id.back_rl:
			finish();
			break;

		}
	}

	@Override
	public void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		super.onDetachedFromWindow();
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			// 获取预期收入成功
			case NetworkAsyncCommonDefines.GET_YBJ_RECORD_S:
				Bundle xfjlData = msg.getData();
				if (xfjlData != null) {
					mList = xfjlData.getParcelableArrayList("list");
					totalIncome = xfjlData.getString("totalIncome");
					if(totalIncome==null||"".equals(totalIncome)){
						yqsr_tv.setText("预期收益金额:"+"0.00元");
					}else{
						yqsr_tv.setText("预期收益金额:"+totalIncome+"元");
					}
				}
				mYQSRAdapter.setData(mList);
				mYQSRAdapter.notifyDataSetChanged();
				swiperefreshlayout.setRefreshing(false);
				if (mList != null && mList.size() > 0) {
					hint_tv.setVisibility(View.GONE);
					LogUtil.e("隐藏");
				} else {
					LogUtil.e("显示");
					hint_tv.setVisibility(View.VISIBLE);
				}
				break;
			// 获取预期收入失败
			case NetworkAsyncCommonDefines.GET_YBJ_RECORD_F:
				swiperefreshlayout.setRefreshing(false);
				yqsr_tv.setText("预期收益金额:"+"0.00元");
				if (mList != null && mList.size() > 0) {
					hint_tv.setVisibility(View.GONE);
					LogUtil.e("隐藏");
				} else {
					LogUtil.e("显示");
					hint_tv.setVisibility(View.VISIBLE);
				}
				break;
			}

		}

	};

}
