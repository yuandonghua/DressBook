package cn.dressbook.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import com.alibaba.sdk.android.callback.CallbackContext;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.adapter.LSTJAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.model.AttireScheme;
import cn.dressbook.ui.net.SchemeExec;
import cn.dressbook.ui.tb.TaoBaoUI;
import cn.dressbook.ui.utils.SharedPreferenceUtils;

/**
 * @description 智能推荐
 * @author 袁东华
 * @date 2016-3-22
 */
@ContentView(R.layout.lstj)
public class ZhiNengTuiJianActivity extends BaseActivity {
	private Context mContext = ZhiNengTuiJianActivity.this;
	private SharedPreferenceUtils mSharedPreferenceUtils = SharedPreferenceUtils
			.getInstance();
	private LSTJAdapter mLSTJAdapter;
	/**
	 * 标题
	 */
	private TextView title_tv;
	@ViewInject(R.id.recyclerview)
	private RecyclerView recyclerview;
	/**
	 * 下拉刷新view
	 */
	@ViewInject(R.id.swiperefreshlayout)
	private SwipeRefreshLayout swiperefreshlayout;
	private String mWardrobeId;
	private ArrayList<AttireScheme> mList;
	/**
	 * 购物车
	 */
	@ViewInject(R.id.operate_iv)
	private ImageView operate_iv;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv=(TextView) findViewById(R.id.title_tv);
		title_tv.setText("量身推荐");
		operate_iv.setImageResource(R.drawable.shoppingcart_src_2);
		operate_iv.setVisibility(View.VISIBLE);

	}

	@SuppressWarnings("deprecation")
	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		mWardrobeId = mSharedPreferenceUtils.getWardrobeID(this);
		mLSTJAdapter = new LSTJAdapter(this, mHandler);
		recyclerview.setHasFixedSize(true);
		recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2,
				StaggeredGridLayoutManager.VERTICAL));
		recyclerview.setAdapter(mLSTJAdapter);
		recyclerview.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(RecyclerView recyclerView,
					int newState) {
				// TODO Auto-generated method stub
				super.onScrollStateChanged(recyclerView, newState);
				switch (newState) {
				// 滑动停止
				case RecyclerView.SCROLL_STATE_IDLE:
					break;
				// 拖拽
				case RecyclerView.SCROLL_STATE_DRAGGING:
					break;
				// 滑动
				case RecyclerView.SCROLL_STATE_SETTLING:
					break;
				default:
					break;
				}
			}

		});
		// 点击条目
		mLSTJAdapter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(View view, int position) {
				// TODO Auto-generated method stub

				if (mList.get(position).getOpeniid() != null
						&& mList.get(position).getOpeniid().length() > 0) {
					LogUtil.e("getOpeniid:"+mList.get(position).getOpeniid());
					TaoBaoUI.getInstance().showTaokeItemDetailByItemId(
							ZhiNengTuiJianActivity.this,
							mList.get(position).getOpeniid());
				} else {
					Intent intent = new Intent(mContext,
							ShangPinXiangQingActivity.class);
					intent.putExtra("AttireScheme", mList.get(position));
					mContext.startActivity(intent);
				}
			}
		});
		swiperefreshlayout.setColorSchemeResources(R.color.red1);
		swiperefreshlayout.setSize(SwipeRefreshLayout.DEFAULT);
		// 下拉刷新
		swiperefreshlayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				// 从服务端获取量身推荐列表
				SchemeExec.getInstance().getLSTJFromServer(mHandler,
						ManagerUtils.getInstance().getUser_id(mContext),
						Integer.parseInt(mWardrobeId),
						NetworkAsyncCommonDefines.GET_ATTIRESCHEME_LIST_S,
						NetworkAsyncCommonDefines.GET_ATTIRESCHEME_LIST_F);
			}
		});
		// 获取推荐
		if (mWardrobeId != null) {
			pbDialog.show();
			// 从SD卡获取我穿方案列表
			SchemeExec
					.getInstance()
					.getLSTJFromSD(
							mHandler,
							ManagerUtils.getInstance().getUser_id(mContext),
							mWardrobeId,
							NetworkAsyncCommonDefines.GET_WC_ATTIRESCHEME_LIST_FROM_SD_S,
							NetworkAsyncCommonDefines.GET_WC_ATTIRESCHEME_LIST_FROM_SD_F);
		} else {
			Toast.makeText(ZhiNengTuiJianActivity.this, "请设置基础资料", Toast.LENGTH_SHORT)
					.show();
			Intent intent = new Intent(mContext, JiChuZiLiaoActivity.class);
			startActivity(intent);
			finish();
		}

	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@Event({ R.id.back_rl, R.id.operate_iv, R.id.operate_iv_2 })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		/**
		 * 点击购物车
		 */
		case R.id.operate_iv:
			Intent shoppingCartActivity = new Intent(ZhiNengTuiJianActivity.this,
					ShoppingCartActivity.class);
			startActivity(shoppingCartActivity);
			break;
		default:
			break;
		}

	}

	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			// 从SD卡获取量身推荐成功
			case NetworkAsyncCommonDefines.GET_WC_ATTIRESCHEME_LIST_FROM_SD_S:
				Bundle lstjFromSD = msg.getData();
				if (lstjFromSD != null) {
					mList = lstjFromSD.getParcelableArrayList("list");
					mLSTJAdapter.setData(mList);
					swiperefreshlayout.setRefreshing(false);
					mLSTJAdapter.notifyDataSetChanged();
				}
				pbDialog.dismiss();
				break;
			// 从SD卡获取量身推荐失败
			case NetworkAsyncCommonDefines.GET_WC_ATTIRESCHEME_LIST_FROM_SD_F:
				// 从服务端获取量身推荐列表
				SchemeExec.getInstance().getLSTJFromServer(mHandler,
						ManagerUtils.getInstance().getUser_id(mContext),
						Integer.parseInt(mWardrobeId),
						NetworkAsyncCommonDefines.GET_ATTIRESCHEME_LIST_S,
						NetworkAsyncCommonDefines.GET_ATTIRESCHEME_LIST_F);
				break;
			// 从服务端获取量身推荐列表成功
			case NetworkAsyncCommonDefines.GET_ATTIRESCHEME_LIST_S:
				// 从SD卡获取我穿方案列表
				SchemeExec
						.getInstance()
						.getLSTJFromSD(
								mHandler,
								ManagerUtils.getInstance().getUser_id(mContext),
								mWardrobeId,
								NetworkAsyncCommonDefines.GET_WC_ATTIRESCHEME_LIST_FROM_SD_S,
								NetworkAsyncCommonDefines.GET_WC_ATTIRESCHEME_LIST_FROM_SD_F);
				break;
			// 从服务端获取量身推荐列表失败
			case NetworkAsyncCommonDefines.GET_ATTIRESCHEME_LIST_F:
				swiperefreshlayout.setRefreshing(false);
				File file = new File(PathCommonDefines.WARDROBE_HEAD, "head.0");
				String sex = mSharedPreferenceUtils.getSex(mContext);
				String birthday = mSharedPreferenceUtils.getBirthday(mContext);
				String height = mSharedPreferenceUtils.getHeight(mContext);
				String weight = mSharedPreferenceUtils.getWeight(mContext);
				if (sex == null || sex.equals("") || sex.equals("未设置")
						|| birthday == null || birthday.equals("")
						|| birthday.equals("未设置") || height == null
						|| height.equals("") || height.equals("未设置")
						|| weight == null || weight.equals("")
						|| weight.equals("未设置")) {
					Toast.makeText(ZhiNengTuiJianActivity.this, "请设置基础资料",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(mContext,
							JiChuZiLiaoActivity.class);
					startActivity(intent);
					finish();
				} 
				pbDialog.dismiss();
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		CallbackContext.onActivityResult(requestCode, resultCode, data);
	}
}
