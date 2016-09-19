package cn.dressbook.ui;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

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
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.adapter.RequirementInfoAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.model.Requirement;
import cn.dressbook.ui.net.RequirementExec;
import cn.dressbook.ui.recyclerview.HorizontalDividerItemDecoration;


/**
 * @description: 需求详情界面
 * @author:袁东华
 * @time:2015-10-17上午9:47:35
 */
@ContentView(R.layout.requirementinfo)
public class RequirementInfoActivity extends BaseActivity {
	private Context mContext = RequirementInfoActivity.this;
	private RequirementInfoAdapter mRequirementInfoAdapter;
	private String requirement_id;
	private Requirement requirement = null;
	private boolean flag;
	/**
	 * 返回按钮
	 */
	@ViewInject(R.id.back_rl)
	private RelativeLayout back_rl;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/**
	 * 我帮他
	 */
	@ViewInject(R.id.operate_tv)
	private TextView operate_tv;
	/**
	 * 下拉刷新view
	 */
	@ViewInject(R.id.swiperefreshlayout)
	private SwipeRefreshLayout swiperefreshlayout;
	/**
	 * 
	 */
	@ViewInject(R.id.recyclerview)
	private RecyclerView recyclerview;


	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		if (intent != null) {
			requirement_id = intent.getStringExtra("requirement_id");
			flag = intent.getBooleanExtra("flag", false);
		}
		if (flag) {
			operate_tv.setVisibility(View.GONE);
		} else {
			operate_tv.setText("我帮TA");
			operate_tv.setVisibility(View.VISIBLE);
		}

		title_tv.setText("需求详情");
		mRequirementInfoAdapter = new RequirementInfoAdapter(mContext, mHandler);
		recyclerview.setHasFixedSize(true);
		recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
		recyclerview.setAdapter(mRequirementInfoAdapter);
		recyclerview
				.addItemDecoration(new HorizontalDividerItemDecoration.Builder(
						mContext)
						.color(getResources().getColor(R.color.black15))
						.size(getResources().getDimensionPixelSize(
								R.dimen.divider4))
						.margin(getResources().getDimensionPixelSize(
								R.dimen.leftmargin_no),
								getResources().getDimensionPixelSize(
										R.dimen.rightmargin_no)).build());
		swiperefreshlayout.setColorSchemeResources(R.color.red1);
		swiperefreshlayout.setSize(SwipeRefreshLayout.DEFAULT);
		//下拉刷新
		swiperefreshlayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				// 获取需求详情
				RequirementExec.getInstance().getRequirementInfoFromServer(mHandler,
						ManagerUtils.getInstance().getUser_id(mContext), requirement_id,
						NetworkAsyncCommonDefines.GET_REQUIREMENTINFO_S,
						NetworkAsyncCommonDefines.GET_REQUIREMENTINFO_F);
			}
		});
	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// 获取需求详情
		RequirementExec.getInstance().getRequirementInfoFromServer(mHandler,
				ManagerUtils.getInstance().getUser_id(mContext), requirement_id,
				NetworkAsyncCommonDefines.GET_REQUIREMENTINFO_S,
				NetworkAsyncCommonDefines.GET_REQUIREMENTINFO_F);
	}

	@Event({ R.id.back_rl, R.id.operate_tv })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		// 点击我帮TA
		case R.id.operate_tv:
			Intent intent = new Intent(mContext, WoBangTaActivity.class);
			intent.putExtra("childid", requirement.getchildId());
			intent.putExtra("id", requirement.getId());
			intent.putExtra("reqdesc", requirement.getReqDesc());
			intent.putExtra("addtime", requirement.getAddTime());
			intent.putExtra("username", requirement.getUserName());
			intent.putExtra("occation", requirement.getOccasion());
			intent.putExtra("category", requirement.getcategoryName());
			intent.putExtra("pricemax", requirement.getPriceMax());
			intent.putExtra("pricemin", requirement.getPriceMin());
			intent.putExtra("pricemin", requirement.getPriceMin());
			intent.putExtra("useravatar", requirement.getuserAvatar());
			intent.putExtra("sex", requirement.getsex());
			intent.putExtra("categoryid", requirement.getcategoryId());
			startActivity(intent);
			break;

		default:
			break;
		}
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			// 喜欢成功
			case NetworkAsyncCommonDefines.XH_S:
				Bundle xhData = msg.getData();
				if (xhData != null) {
					String recode = xhData.getString("recode");
					String redesc = xhData.getString("redesc");
					Toast.makeText(mContext, redesc, Toast.LENGTH_SHORT).show();
				}
				// 获取需求详情
				RequirementExec.getInstance().getRequirementInfoFromServer(
						mHandler, ManagerUtils.getInstance().getUser_id(mContext),
						requirement_id,
						NetworkAsyncCommonDefines.GET_REQUIREMENTINFO_S,
						NetworkAsyncCommonDefines.GET_REQUIREMENTINFO_F);
				break;
			// 喜欢失败
			case NetworkAsyncCommonDefines.XH_F:
				break;
			// 获取需求详情成功
			case NetworkAsyncCommonDefines.GET_REQUIREMENTINFO_S:
				Bundle requData = msg.getData();
				if (requData != null) {
					requirement = requData.getParcelable("requirement");
					if (requirement != null) {
						mRequirementInfoAdapter.setData(requirement);
						mRequirementInfoAdapter.notifyDataSetChanged();
					} else {
					}
				}
				swiperefreshlayout.setRefreshing(false);
				break;
			// 获取需求详情失败
			case NetworkAsyncCommonDefines.GET_REQUIREMENTINFO_F:
				swiperefreshlayout.setRefreshing(false);
				break;

			default:
				break;
			}
		};
	};
}
