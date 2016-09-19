package cn.dressbook.ui;

import java.util.ArrayList;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.adapter.FindAdviserAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.model.Adviser;
import cn.dressbook.ui.net.AdviserExec;
import cn.dressbook.ui.recyclerview.HorizontalDividerItemDecoration;


/**
 * @description: 指定顾问推荐
 * @author:袁东华
 * @time:2015-10-13下午3:35:28
 */
@ContentView(R.layout.findadviser)
public class FindAdviserActivity extends BaseActivity {
	private Context mContext = FindAdviserActivity.this;
	/**
	 * 返回
	 */
	@ViewInject(R.id.back_rl)
	private RelativeLayout back_rl;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/**
	 * 确定按钮
	 */
	@ViewInject(R.id.operate_tv)
	private TextView operate_tv;
	@ViewInject(R.id.recyclerview)
	private RecyclerView recyclerview;
	@ViewInject(R.id.swiperefreshlayout)
	private SwipeRefreshLayout swiperefreshlayout;

	/**
	 * 顾问师列表集合
	 */
	//private ArrayList<Adviser> mAdviserList;
	/**
	 * 顾问师列表适配器
	 */
	private FindAdviserAdapter mFindAdviserAdapter;


	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("指定推荐顾问师");
		operate_tv.setText("确定");
		operate_tv.setVisibility(View.VISIBLE);

	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub

		// 下拉刷新
		swiperefreshlayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				if (swiperefreshlayout.isRefreshing()) {

					swiperefreshlayout.setRefreshing(false);
				} else {
					// 从服务端获取顾问师列表
					AdviserExec
							.getInstance()
							.getAdviserListFromServer(
									mHandler,
									NetworkAsyncCommonDefines.GET_ADVISERLIST_FROM_SERVER_S,
									NetworkAsyncCommonDefines.GET_ADVISERLIST_FROM_SERVER_F);
				}
			}
		});
		mFindAdviserAdapter = new FindAdviserAdapter(mContext, mHandler);
		recyclerview.setAdapter(mFindAdviserAdapter);
		recyclerview.setHasFixedSize(true);
		LinearLayoutManager llm = new LinearLayoutManager(mContext);
		recyclerview.setLayoutManager(llm);
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
		// 点击条目
		mFindAdviserAdapter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(View view, int position) {
				Intent intent = new Intent(mContext,
						UserHomepageActivity.class);
				if (mAdviserList != null && mAdviserList.size() > position) {
					// 跳转到顾问师主页
					intent.putExtra("USER_ID", mAdviserList.get(position)
							.getUserId());
				}

				startActivity(intent);
				((Activity) mContext).overridePendingTransition(
						R.anim.back_enter, R.anim.anim_exit);
			}
		});
		AdviserExec.getInstance().getAdviserListFromServer(mHandler,
				NetworkAsyncCommonDefines.GET_ADVISERLIST_FROM_SERVER_S,
				NetworkAsyncCommonDefines.GET_ADVISERLIST_FROM_SERVER_F);
	}

	@Event({ R.id.back_rl, R.id.operate_tv })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		// 点击确定
		case R.id.operate_tv:
			ArrayList<String> list = mFindAdviserAdapter.getData();
			if (list != null && list.size() > 0) {
				ManagerUtils.getInstance().setAdviserIdList(list);
				finish();
			} else {
				Toast.makeText(mContext, "请选择顾问师", Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
	}

	private ArrayList<Adviser> mAdviserList;
	private Handler mHandler = new Handler() {


		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			// 去绑定手机号
			case NetworkAsyncCommonDefines.BIND_PHONE:
				Intent loginActivity = new Intent(mContext, LoginActivity.class);
				mContext.startActivity(loginActivity);
				((Activity) mContext).overridePendingTransition(
						R.anim.back_enter, R.anim.anim_exit);
				break;
			// 从SD卡获取顾问师列表成功
			case NetworkAsyncCommonDefines.GET_ADVISERLIST_FROM_SERVER_S:
				 mAdviserList= (ArrayList<Adviser>) msg.obj;
				if (mAdviserList!=null) {
						mFindAdviserAdapter.setData(mAdviserList);
						mFindAdviserAdapter.notifyDataSetChanged();
					
				}
				mFindAdviserAdapter.notifyDataSetChanged();
				/*Bundle adviserListBun = msg.getData();
				if (adviserListBun != null) {
					if (mAdviserList != null && mAdviserList.size() > 0) {
						mAdviserList.clear();
					} else {
						mAdviserList = new ArrayList<Adviser>();
					}
					ArrayList<Adviser> adviserList = adviserListBun
							.getParcelableArrayList("adviserList");
					if (adviserList == null) {
						// 从服务端获取顾问师列表
						AdviserExec
								.getInstance()
								.getAdviserListFromServer(
										mHandler,
										NetworkAsyncCommonDefines.GET_ADVISERLIST_FROM_SERVER_S,
										NetworkAsyncCommonDefines.GET_ADVISERLIST_FROM_SERVER_F);
					} else {
						mAdviserList.addAll(adviserList);
						mFindAdviserAdapter.setData(mAdviserList);
						mFindAdviserAdapter.notifyDataSetChanged();
					}
				}
				swiperefreshlayout.setRefreshing(false);*/
				break;
			// 从服务端获取顾问师列表失败
			case NetworkAsyncCommonDefines.GET_ADVISERLIST_FROM_SERVER_F:
				break;
			default:
				break;
			}
		}

	};
}
