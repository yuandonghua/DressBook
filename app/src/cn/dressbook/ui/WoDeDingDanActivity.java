package cn.dressbook.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.adapter.MyOrderAdapter;
import cn.dressbook.ui.adapter.WoDeDingDanAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.fragment.DingDanFragment;
import cn.dressbook.ui.model.OrderForm;
import cn.dressbook.ui.model.SerializableMap;
import cn.dressbook.ui.net.OrderExec;
import cn.dressbook.ui.viewpager.PagerSlidingTabStrip;

/**
 * @description: 我的订单
 * @author:袁东华
 * @time:2015-9-17下午3:45:54
 */
@ContentView(R.layout.myorder)
public class WoDeDingDanActivity extends BaseFragmentActivity {

	private WoDeDingDanAdapter woDeDingDanAdapter;
	private int mPosition = 0;
	@ViewInject(R.id.tabs)
	private PagerSlidingTabStrip mPagerSlidingTabStrip;
	@ViewInject(R.id.pager)
	private ViewPager mViewPager;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;

	@Override
	protected void initView() {
		pbDialog.show();
		title_tv.setText("订单中心");

	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mHandler.sendEmptyMessage(0);
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

	private HashMap<String, ArrayList<OrderForm>> orderMap;
	private ArrayList<String> keyList = new ArrayList<String>();
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			// 获取订单列表成功
			case NetworkAsyncCommonDefines.GET_SHOPPINGCART_INFO_S:
				try {

					Bundle bun = msg.getData();
					if (bun != null) {
						SerializableMap orderMap2 = (SerializableMap) bun
								.getSerializable("orderMap");
						if (orderMap != null && orderMap.size() > 0) {
							orderMap.clear();
						}
						if (keyList != null && keyList.size() > 0) {
							keyList.clear();
						}
						LogUtil.e("orderMap:" + orderMap2.getMap());
						orderMap = orderMap2.getMap();

						Set<String> keySet = orderMap.keySet();
						for (String key : keySet) {
							keyList.add(key);
						}
						if (woDeDingDanAdapter == null) {
							woDeDingDanAdapter = new WoDeDingDanAdapter(
									getSupportFragmentManager());
							woDeDingDanAdapter.setData(mHandler, keyList,
									orderMap);
							mViewPager.setAdapter(woDeDingDanAdapter);
							mPagerSlidingTabStrip.setViewPager(mViewPager);
						} else {
							woDeDingDanAdapter.setData(mHandler, keyList,
									orderMap);
							for (int i = 0; i < keyList.size(); i++) {
								DingDanFragment ddf = (DingDanFragment) woDeDingDanAdapter
										.getItem(i);
								ddf.setRefresh(orderMap.get(keyList.get(i)));
							}

						}

						// mViewPager.setCurrentItem(mPosition);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				pbDialog.dismiss();
				break;
			// 获取订单列表失败
			case NetworkAsyncCommonDefines.GET_SHOPPINGCART_INFO_F:
				pbDialog.dismiss();
				break;
			// 获取订单列表
			case 0:
				// 获取订单列表
				OrderExec.getInstance().getOrderList(mHandler,
						ManagerUtils.getInstance().getUser_id(activity), "0",
						NetworkAsyncCommonDefines.GET_SHOPPINGCART_INFO_S,
						NetworkAsyncCommonDefines.GET_SHOPPINGCART_INFO_F);
				break;
			// 正在处理
			case NetworkAsyncCommonDefines.PROCESSING:
				Toast.makeText(activity, "正在处理", Toast.LENGTH_SHORT).show();
				pbDialog.show();
				break;

			default:
				break;
			}
		}

	};
	private static RefreshDingDanListener mRefreshDingDanListener;

	public static void setRefreshDingDanListener(
			RefreshDingDanListener refreshDingDanListener) {
		// TODO Auto-generated method stub
		mRefreshDingDanListener = refreshDingDanListener;
	}

	public interface RefreshDingDanListener {
		void onRefresh(int position);
	}
}
