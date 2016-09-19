package cn.dressbook.ui;

import java.util.ArrayList;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.adapter.DJSQAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.listener.SaoYiSaoResultListener;
import cn.dressbook.ui.model.DianPu;
import cn.dressbook.ui.model.JYJL;
import cn.dressbook.ui.net.JiaoYiJiLuExec;
import cn.dressbook.ui.recyclerview.HorizontalDividerItemDecoration;
import cn.dressbook.ui.utils.SharedPreferenceUtils;
import cn.dressbook.ui.view.SelectDianPuPopupWindow;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @description 店家收钱
 * @author 袁东华
 * @date 2016-3-16
 */
@ContentView(R.layout.activity_djsq)
public class DJSQActivity extends BaseActivity {
	private SharedPreferenceUtils mSharedPreferenceUtils = SharedPreferenceUtils
			.getInstance();
	@ViewInject(R.id.rl1)
	private RelativeLayout rl1;
	/**
	 * 扫一扫
	 */
	@ViewInject(R.id.sys_iv)
	private ImageView sys_iv;
	/**
	 * 下拉刷新view
	 */
	@ViewInject(R.id.swiperefreshlayout)
	private SwipeRefreshLayout swiperefreshlayout;
	/** 商品展示view */
	@ViewInject(R.id.recyclerview)
	private RecyclerView recyclerview;
	/**
	 * 收钱
	 */
	@ViewInject(R.id.shouqian_tv)
	private ImageView shouqian_tv;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/**
	 * 提示
	 */
	@ViewInject(R.id.hint_tv)
	private TextView hint_tv;
	private Context mContext = DJSQActivity.this;
	private DJSQAdapter mDjsqAdapter;
	private int curPage = 1, pageSize = 50;
	private ArrayList<JYJL> jyjlList = new ArrayList<JYJL>();
	private int lastVisibleItem;
	private LinearLayoutManager mLinearLayoutManager;
	@ViewInject(R.id.xfm_et)
	private EditText xfm_et;
	// 店铺
	@ViewInject(R.id.dp_tv)
	private TextView dp_tv;
	@ViewInject(R.id.rl)
	private RelativeLayout rl;
	private int dpId = -1;

	@SuppressWarnings("deprecation")
	@Override
	protected void initView() {
		title_tv.setText("店家收钱");
		mContext = this;
		mLinearLayoutManager = new LinearLayoutManager(mContext);
		recyclerview.setLayoutManager(mLinearLayoutManager);
		swiperefreshlayout.setColorSchemeResources(R.color.red1);
		swiperefreshlayout.setSize(SwipeRefreshLayout.DEFAULT);
		swiperefreshlayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				curPage = 1;
				jyjlList.clear();
				// 获取店家收钱列表
				JiaoYiJiLuExec.getInstance().getDianJiaShouQianList(mHandler,
						ManagerUtils.getInstance().getUser_id(mContext),
						curPage, pageSize,
						NetworkAsyncCommonDefines.GET_DJSQ_LIST_S,
						NetworkAsyncCommonDefines.GET_DJSQ_LIST_F);
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
						&& lastVisibleItem + 1 == mDjsqAdapter.getItemCount()) {
					swiperefreshlayout.setRefreshing(true);
					curPage++;
					// 获取店家收钱列表
					JiaoYiJiLuExec.getInstance().getDianJiaShouQianList(
							mHandler,
							ManagerUtils.getInstance().getUser_id(mContext),
							curPage, pageSize,
							NetworkAsyncCommonDefines.GET_DJSQ_LIST_S,
							NetworkAsyncCommonDefines.GET_DJSQ_LIST_F);
				}

			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				lastVisibleItem = mLinearLayoutManager
						.findLastVisibleItemPosition();
			}
		});
		// 扫一扫结果
		SaoYiSaoActivity
				.setSaoYiSaoResultListener(new SaoYiSaoResultListener() {

					@Override
					public void onResult(String result) {
						// TODO Auto-generated method stub
						xfm_et.setText(result);
					}
				});
	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		String xfm = getIntent().getStringExtra("xfm");
		xfm_et.setText(xfm);
		mDjsqAdapter = new DJSQAdapter(mContext, mHandler);
		recyclerview.setAdapter(mDjsqAdapter);
		if (ManagerUtils.getInstance().getShenFen(mContext).contains("@店家@")
				|| ManagerUtils.getInstance().getShenFen(mContext)
						.contains("@雇员@")) {
			hint_tv.setVisibility(View.GONE);
			rl1.setVisibility(View.VISIBLE);
			xfm_et.setEnabled(true);
			shouqian_tv.setImageResource(R.drawable.shouqian_selected);
			shouqian_tv.setEnabled(true);
			sys_iv.setEnabled(true);
			// 获取店家收钱列表
			JiaoYiJiLuExec.getInstance().getDianJiaShouQianList(mHandler,
					ManagerUtils.getInstance().getUser_id(mContext), curPage,
					pageSize, NetworkAsyncCommonDefines.GET_DJSQ_LIST_S,
					NetworkAsyncCommonDefines.GET_DJSQ_LIST_F);
			if (ManagerUtils.getInstance().getShenFen(mContext)
					.contains("@店家@")) {
				pbDialog.show();
				// 获取店铺列表
				JiaoYiJiLuExec.getInstance().getDianPuList(mHandler,
						ManagerUtils.getInstance().getUser_id(activity),
						NetworkAsyncCommonDefines.GET_BZ_LIST_S,
						NetworkAsyncCommonDefines.GET_BZ_LIST_F);
			} else if (ManagerUtils.getInstance().getShenFen(mContext)
					.contains("@雇员@")) {
				pbDialog.show();
				// 获取店铺列表
				JiaoYiJiLuExec.getInstance().getDianPuList(mHandler,
						ManagerUtils.getInstance().getDjId(),
						NetworkAsyncCommonDefines.GET_BZ_LIST_S,
						NetworkAsyncCommonDefines.GET_BZ_LIST_F);
			}

		} else {
			xfm_et.setEnabled(false);
			rl1.setVisibility(View.GONE);
			hint_tv.setVisibility(View.VISIBLE);
			shouqian_tv.setImageResource(R.drawable.shouqian_unselected);
			shouqian_tv.setEnabled(false);
			sys_iv.setEnabled(false);
		}

	}

	private ArrayList<DianPu> dpList;// 店铺列表
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			// 获取店铺列表成功
			case NetworkAsyncCommonDefines.GET_BZ_LIST_S:
				Bundle data = msg.getData();
				if (data != null) {
					dpList = data.getParcelableArrayList("list");
					if (mSelectDianPuPopupWindow == null) {
						mSelectDianPuPopupWindow = new SelectDianPuPopupWindow(
								activity, itemsOnClick);
					}
					mSelectDianPuPopupWindow.dpPicker.setData(dpList);
					dpId=mSharedPreferenceUtils.getDPPosition(activity);
					// 如果只有一个店铺,默认选中
					if (dpList != null && dpList.size() == 1) {
						dpId = 0;
						String name = dpList.get(0).getfranchiseeName();
						dp_tv.setText(name);
					} else if (dpList != null && dpList.size() > 1
							&& dpId != -1 && dpList.size() > dpId) {
						// 如果有多个店铺,默认选中上次选择的店铺,
						String name = dpList.get(dpId).getfranchiseeName();
						dp_tv.setText(name);
					} else if (dpList != null && dpList.size() > 1
							&& dpId == -1) {
						// 如果有多个店铺,如果没有选择过,则选中第一个店铺
						dpId = 0;
						String name = dpList.get(0).getfranchiseeName();
						dp_tv.setText(name);
					}

				}
				pbDialog.dismiss();
				break;
			// 获取店铺列表失败
			case NetworkAsyncCommonDefines.GET_BZ_LIST_F:
				pbDialog.dismiss();
				break;
			// 店家收钱成功
			case NetworkAsyncCommonDefines.DJSQ_S:
				Bundle recodeData = msg.getData();
				if (recodeData != null) {
					String recode = recodeData.getString("recode");
					String redesc = recodeData.getString("redesc");
					if ("0".equals(recode)) {
						Toast.makeText(mContext, "收钱成功", Toast.LENGTH_SHORT)
								.show();
					} else {
						Toast.makeText(mContext, redesc, Toast.LENGTH_SHORT)
								.show();
					}

				}
				// 获取店家收钱列表
				JiaoYiJiLuExec.getInstance().getDianJiaShouQianList(mHandler,
						ManagerUtils.getInstance().getUser_id(mContext),
						curPage, pageSize,
						NetworkAsyncCommonDefines.GET_DJSQ_LIST_S,
						NetworkAsyncCommonDefines.GET_DJSQ_LIST_F);
				pbDialog.dismiss();

				break;
			// 店家收钱失败
			case NetworkAsyncCommonDefines.DJSQ_F:
				pbDialog.dismiss();
				break;
			// 获取店家收钱列表成功
			case NetworkAsyncCommonDefines.GET_DJSQ_LIST_S:
				Bundle bundle = msg.getData();
				if (bundle != null) {
					ArrayList<JYJL> list = bundle
							.getParcelableArrayList("list");
					if (list != null && list.size() > 0) {
						jyjlList.addAll(list);
						mDjsqAdapter.setData(jyjlList);
						mDjsqAdapter.notifyDataSetChanged();

					}
				}
				swiperefreshlayout.setRefreshing(false);
				break;
			// 获取店家收钱列表失败
			case NetworkAsyncCommonDefines.GET_DJSQ_LIST_F:
				hint_tv.setVisibility(View.VISIBLE);
				swiperefreshlayout.setRefreshing(false);

				break;
			default:
				break;
			}
		};
	};
	private SelectDianPuPopupWindow mSelectDianPuPopupWindow;

	@Event({ R.id.back_rl, R.id.shouqian_tv, R.id.dp_tv, R.id.sys_iv })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击扫一扫
		case R.id.sys_iv:
			Intent sys = new Intent(activity, SaoYiSaoActivity.class);
			sys.putExtra("flag", "xfm");
			startActivity(sys);
			break;
		// 点击店铺
		case R.id.dp_tv:
			if (dpList != null && dpList.size() > 0) {

				if (mSelectDianPuPopupWindow == null) {
					mSelectDianPuPopupWindow = new SelectDianPuPopupWindow(
							activity, itemsOnClick);
				}
				mSelectDianPuPopupWindow.showAtLocation(dp_tv, Gravity.BOTTOM
						| Gravity.CENTER_HORIZONTAL, 0, 0);
			}
			break;
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		// 点击收钱
		case R.id.shouqian_tv:
			String xfm = xfm_et.getText().toString().trim();
			if (xfm == null || "".equals(xfm)) {
				Toast.makeText(mContext, "消费码不能为空", Toast.LENGTH_SHORT).show();
				break;
			}
			if (xfm.length() != 14) {
				Toast.makeText(mContext, "消费码错误", Toast.LENGTH_SHORT).show();
				break;
			}
			if (dpId == -1) {
				Toast.makeText(mContext, "请选择店铺", Toast.LENGTH_SHORT).show();
				break;
			}
			pbDialog.show();
			mSharedPreferenceUtils.setDPPosition(activity, dpId);
			JiaoYiJiLuExec.getInstance().dianJiaShouQian(mHandler,
					ManagerUtils.getInstance().getUser_id(mContext), xfm,
					dpList.get(dpId).getfranchiseeId(),
					NetworkAsyncCommonDefines.DJSQ_S,
					NetworkAsyncCommonDefines.DJSQ_F);
			break;
		default:
			break;
		}

	}

	// 为弹出窗口实现监听类
	private OnClickListener itemsOnClick = new OnClickListener() {

		public void onClick(View v) {
			mSelectDianPuPopupWindow.dismiss();
			switch (v.getId()) {
			case R.id.cancle_tv:
				break;
			case R.id.ok_tv:
				dpId = mSelectDianPuPopupWindow.dpPicker.tempdayIndex;
				String name = dpList.get(dpId).getfranchiseeName();
				dp_tv.setText(name);

				break;

			}

		}
	};
}
