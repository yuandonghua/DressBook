package cn.dressbook.ui.fragment;

import java.util.ArrayList;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import cn.dressbook.ui.R;
import cn.dressbook.ui.WoDeDingDanActivity;
import cn.dressbook.ui.WoDeDingDanActivity.RefreshDingDanListener;
import cn.dressbook.ui.adapter.MyOrderAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.dialog.EWMDialog;
import cn.dressbook.ui.model.OrderForm;
import cn.dressbook.ui.recyclerview.HorizontalDividerItemDecoration;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @description: 对应的订单列表界面
 * @author:ydh
 * @data:2016-4-12下午2:46:09
 */
@ContentView(R.layout.dingdan)
public class DingDanFragment extends BaseFragment {
	/**
	 * 下拉刷新view
	 */
	@ViewInject(R.id.swiperefreshlayout)
	private SwipeRefreshLayout swiperefreshlayout;
	/**
	 * 暂无数据提示
	 */
	@ViewInject(R.id.hint_tv)
	private TextView hint_tv;
	@ViewInject(R.id.recyclerview)
	private RecyclerView recyclerview;
	private int curPage = 1;
	private int pageSize = 50;
	private ArrayList<OrderForm> mList = new ArrayList<OrderForm>();
	private int lastVisibleItem;
	private LinearLayoutManager mLinearLayoutManager;
	private MyOrderAdapter myOrderAdapter;
	private int position;
	private Handler mainHandler;

	public DingDanFragment(Handler handler, ArrayList<OrderForm> mList,
			int position) {
		LogUtil.e("创建一个DingDanFragment");
		mainHandler = handler;
		this.mList = mList;
		this.position = position;
	}

	public void setRefresh(ArrayList<OrderForm> mList) {
		this.mList = mList;
		if (swiperefreshlayout != null) {
			swiperefreshlayout.setRefreshing(false);
			myOrderAdapter.setData(mList);
			myOrderAdapter.notifyDataSetChanged();

		} else {
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		swiperefreshlayout.setColorSchemeResources(R.color.red1);
		swiperefreshlayout.setSize(SwipeRefreshLayout.DEFAULT);

		// 下拉刷新
		swiperefreshlayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				if (mainHandler != null) {

					mainHandler.sendEmptyMessage(0);
				}

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
		// recyclerview.setOnScrollListener(new OnScrollListener() {
		//
		// @Override
		// public void onScrollStateChanged(RecyclerView recyclerView,
		// int newState) {
		// // TODO Auto-generated method stub
		// super.onScrollStateChanged(recyclerView, newState);
		// if (newState == RecyclerView.SCROLL_STATE_IDLE
		// && lastVisibleItem + 1 == mXFJLAdapter.getItemCount()) {
		// swiperefreshlayout.setRefreshing(true);
		// curPage++;
		// // 获取交易记录列表
		// JiaoYiJiLuExec.getInstance().getJiaoYiJiLuList(
		// mHandler,
		// ManagerUtils.getInstance()
		// .getUser_id(getActivity()), reason,
		// curPage, pageSize,
		// NetworkAsyncCommonDefines.GET_JYJL_LIST_S,
		// NetworkAsyncCommonDefines.GET_JYJL_LIST_F);
		// }
		//
		// }
		//
		// @Override
		// public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
		// super.onScrolled(recyclerView, dx, dy);
		// lastVisibleItem = mLinearLayoutManager
		// .findLastVisibleItemPosition();
		// }
		// });
		myOrderAdapter = new MyOrderAdapter(getActivity(), mHandler);
		myOrderAdapter.setData(mList);
		mLinearLayoutManager = new LinearLayoutManager(getActivity());
		recyclerview.setLayoutManager(mLinearLayoutManager);
		recyclerview.setAdapter(myOrderAdapter);
		if (mList == null || mList.size() == 0) {
			hint_tv.setVisibility(View.VISIBLE);
		} else {
			hint_tv.setVisibility(View.GONE);
		}

	}

	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub

	}

	private EWMDialog mEWMDialog;
	private int mPosition;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {

			// 正在处理
			case NetworkAsyncCommonDefines.PROCESSING:
				Toast.makeText(getActivity(), "正在处理", Toast.LENGTH_SHORT)
						.show();
				pbDialog.show();
				break;
			// 申请退款成功
			case NetworkAsyncCommonDefines.SQTH_S:
				Bundle sqthData = msg.getData();
				if (sqthData != null) {
					String recode = sqthData.getString("recode");
					if ("0".equals(recode)) {
						Toast.makeText(getActivity(), "申请退款成功,请等待审核",
								Toast.LENGTH_SHORT).show();
					} else if ("1".equals(recode)) {
						Toast.makeText(getActivity(), "用户不存在",
								Toast.LENGTH_SHORT).show();
					} else if ("2".equals(recode)) {
						Toast.makeText(getActivity(), "订单不存在",
								Toast.LENGTH_SHORT).show();
					} else if ("3".equals(recode)) {
						Toast.makeText(getActivity(), "订单状态不正确",
								Toast.LENGTH_SHORT).show();
					} else if ("4".equals(recode)) {
						Toast.makeText(getActivity(), "用户与订单不对应",
								Toast.LENGTH_SHORT).show();
					}
				}
				pbDialog.dismiss();
				if (mainHandler != null) {

					mainHandler.sendEmptyMessage(0);
				}
				break;
			// 申请退款失败
			case NetworkAsyncCommonDefines.SQTH_F:
				Toast.makeText(getActivity(), "服务器或网络异常", Toast.LENGTH_SHORT)
						.show();
				pbDialog.dismiss();
				if (mainHandler != null) {

					mainHandler.sendEmptyMessage(0);
				}
				break;
			// 获取消费码
			case 1:
				Bundle ewmData = msg.getData();
				if (ewmData != null) {
					String ewm = ewmData.getString("ewm");
					if (mEWMDialog == null) {
						mEWMDialog = new EWMDialog(getActivity());
					}
					mEWMDialog.show();
					mEWMDialog.setEWM("消费码", ewm);
				}
				break;
			// 确认收货成功
			case NetworkAsyncCommonDefines.RECEOPT_GOODS_S:
				Bundle receiptBun = msg.getData();
				if (receiptBun != null) {
					String recode = receiptBun.getString("recode");
					String redesc = receiptBun.getString("redesc");
					Toast.makeText(getActivity(), redesc, Toast.LENGTH_SHORT)
							.show();
					pbDialog.dismiss();
					if (mainHandler != null) {

						mainHandler.sendEmptyMessage(0);
					}
				}
				break;
			// 失败确认收货
			case NetworkAsyncCommonDefines.RECEOPT_GOODS_F:
				Toast.makeText(getActivity(), "订单操作失败", Toast.LENGTH_SHORT)
						.show();
				pbDialog.dismiss();
				if (mainHandler != null) {

					mainHandler.sendEmptyMessage(0);
				}
				break;
			// 取消订单成功
			case NetworkAsyncCommonDefines.CANCEL_ORDER_S:
				Bundle deleteBun = msg.getData();
				if (deleteBun != null) {
					String recode = deleteBun.getString("recode");
					String redesc = deleteBun.getString("redesc");
					Toast.makeText(getActivity(), redesc, Toast.LENGTH_SHORT)
							.show();
					pbDialog.dismiss();
					if (mainHandler != null) {

						mainHandler.sendEmptyMessage(0);
					}
				}
				break;
			// 取消订单失败
			case NetworkAsyncCommonDefines.CANCEL_ORDER_F:
				Toast.makeText(getActivity(), "订单操作失败", Toast.LENGTH_SHORT)
						.show();
				pbDialog.dismiss();
				if (mainHandler != null) {

					mainHandler.sendEmptyMessage(0);
				}
				break;

			default:
				break;
			}
		}

	};

}