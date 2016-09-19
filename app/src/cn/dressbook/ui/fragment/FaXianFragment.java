package cn.dressbook.ui.fragment;


import java.util.ArrayList;
import java.util.List;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.DressBookApp;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.AixinjuanyiHomeActivity;
import cn.dressbook.ui.GuWenShiActivity;
import cn.dressbook.ui.LoginActivity;
import cn.dressbook.ui.MYXActivity;
import cn.dressbook.ui.MeitanHomeActivity;
import cn.dressbook.ui.R;
import cn.dressbook.ui.ShangPinXiangQingActivity;
import cn.dressbook.ui.TryOnCollectActivity;
import cn.dressbook.ui.HuiYuanTuiJianActivity;
import cn.dressbook.ui.ZhiNengTuiJianActivity;
import cn.dressbook.ui.adapter.LbtVpAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.model.AttireScheme;
import cn.dressbook.ui.model.GuangGao;
import cn.dressbook.ui.net.SchemeExec;
import cn.dressbook.ui.utils.SharedPreferenceUtils;

/**
 * @description: 发现
 * @author:袁东华
 * @time:2015-9-23下午9:07:43
 */
@ContentView(R.layout.find)
public class FaXianFragment extends BaseFragment {
	private Context mContext;
	private SharedPreferenceUtils mSharedPreferenceUtils = SharedPreferenceUtils
			.getInstance();
	/**
	 * 爱心捐衣
	 */
	@ViewInject(R.id.axjy_rl)
	private RelativeLayout axjy_rl;
	/**
	 * 试衣收藏
	 */
	@ViewInject(R.id.sysc_rl)
	private RelativeLayout lstj_rl;
	/**
	 * 衣保会员
	 */
	@ViewInject(R.id.ybhy_rl)
	private RelativeLayout ybhy_rl;
	@ViewInject(R.id.rl1)
	private RelativeLayout rl1;
	@ViewInject(R.id.lbt_ll)
	private LinearLayout lbt_ll;
	@ViewInject(R.id.lbt_vp)
	private ViewPager lbt_vp;
	private LbtVpAdapter mLbtVpAdapter;

	/**
	 * 下拉刷新view
	 */
	@ViewInject(R.id.swiperefreshlayout)
	private SwipeRefreshLayout swiperefreshlayout;
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions();

	@Override
	public View getView() {
		// TODO Auto-generated method stub
		return super.getView();
	}

	private int width;
	private int height;
	private AttireScheme attireScheme;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mContext = getActivity();
		String sexFlag = mSharedPreferenceUtils.getSex(mContext);
		String sex = "";
		if ("女".equals(sexFlag)) {
			sex = "2";
		} else {
			sex = "1";
		}
		// 获取广告信息
//		SchemeExec.getInstance().getGGInfo(mHandler,
//				ManagerUtils.getInstance().getUser_id(mContext), sex,
//				NetworkAsyncCommonDefines.GET_GG_INFO_S,
//				NetworkAsyncCommonDefines.GET_GG_INFO_F);
		swiperefreshlayout.setColorSchemeResources(R.color.red1);
		swiperefreshlayout.setSize(SwipeRefreshLayout.DEFAULT);
		WindowManager wm = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		lbtdot = (int) mContext.getResources().getDimension(R.dimen.lbt_dot);
		width = wm.getDefaultDisplay().getWidth();
		height = width * 230 / 720;
		android.widget.RelativeLayout.LayoutParams paramsvp = (android.widget.RelativeLayout.LayoutParams) lbt_vp
				.getLayoutParams();
		paramsvp.height = height;
		lbt_vp.setLayoutParams(paramsvp);
		android.widget.RelativeLayout.LayoutParams paramsrl = (android.widget.RelativeLayout.LayoutParams) rl1
				.getLayoutParams();
		paramsrl.height = height;
		rl1.setLayoutParams(paramsrl);
		pre = 0;
		// 下拉刷新
		swiperefreshlayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				attireScheme = null;
				String sexFlag = mSharedPreferenceUtils.getSex(mContext);
				String sex = "";
				if ("女".equals(sexFlag)) {
					sex = "2";
				} else {
					sex = "1";
				}
				// 获取广告信息
				SchemeExec.getInstance().getGGInfo(mHandler,
						ManagerUtils.getInstance().getUser_id(mContext), sex,
						NetworkAsyncCommonDefines.GET_GG_INFO_S,
						NetworkAsyncCommonDefines.GET_GG_INFO_F);
			}
		});
		mLbtVpAdapter = new LbtVpAdapter(mContext);
		mLbtVpAdapter.setData(ggList);
		lbt_vp.setAdapter(mLbtVpAdapter);

		lbt_vp.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				int newposition = position % ggList.size();
				lbt_ll.getChildAt(pre).setEnabled(false);
				lbt_ll.getChildAt(newposition).setEnabled(true);
				pre = newposition;

			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
		// 轮播
		new Thread() {
			public void run() {
				while (isRunning) {
					try {
						Thread.sleep(3000);
						mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.LUNBO);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		}.start();
	}

	@Event({ R.id.meiyixun_rl, R.id.zhinengtuijian_rl, R.id.guwenshi_rl,
			R.id.meitan_rl, R.id.axjy_rl, R.id.sysc_rl, R.id.ybhy_rl, R.id.rl1 })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击美衣讯
		case R.id.meiyixun_rl:
			Intent mYXActivity = new Intent(mContext, MYXActivity.class);
			mContext.startActivity(mYXActivity);
			break;
		// 点击智能推荐
		case R.id.zhinengtuijian_rl:
			if (ManagerUtils.getInstance().isLogin(mContext)) {
				Intent lSTJActivity = new Intent(mContext,
						ZhiNengTuiJianActivity.class);
				mContext.startActivity(lSTJActivity);
			} else {

				Intent bindPhone = new Intent(mContext, LoginActivity.class);
				startActivity(bindPhone);
			}
			break;
		// 点击顾问师
		case R.id.guwenshi_rl:
			Intent adviserActivity = new Intent(mContext,
					GuWenShiActivity.class);
			mContext.startActivity(adviserActivity);
			break;
		// 点击美谈
		case R.id.meitan_rl:
			if (ManagerUtils.getInstance().isLogin(mContext)) {
				Intent meitanHomeActivity = new Intent(mContext,
						MeitanHomeActivity.class);
				mContext.startActivity(meitanHomeActivity);
			} else {
				Intent bindPhone = new Intent(mContext, LoginActivity.class);
				startActivity(bindPhone);
			}
			break;
		// 点击广告
		case R.id.rl1:
			if (ManagerUtils.getInstance().isLogin(mContext)) {

				Intent productIntent = new Intent(mContext,
						ShangPinXiangQingActivity.class);
				productIntent.putExtra("AttireScheme", attireScheme);
				mContext.startActivity(productIntent);
			} else {
				Intent loginIntent = new Intent(mContext, LoginActivity.class);
				startActivity(loginIntent);
			}
			break;
		// 点击爱心捐衣
		case R.id.axjy_rl:
			Intent axjy = new Intent(mContext, AixinjuanyiHomeActivity.class);
			mContext.startActivity(axjy);
			break;
		// 点击试衣收藏
		case R.id.sysc_rl:
			if (ManagerUtils.getInstance().isLogin(mContext)) {
				Intent tryOnCollectActivity = new Intent(mContext,
						TryOnCollectActivity.class);
				startActivity(tryOnCollectActivity);
			} else {
				Intent bindPhone = new Intent(mContext, LoginActivity.class);
				startActivity(bindPhone);
			}
			break;
		// 点击会员推荐
		case R.id.ybhy_rl:
			if (ManagerUtils.getInstance().isLogin(mContext)) {
				Intent yBHYActivity = new Intent(mContext, HuiYuanTuiJianActivity.class);
				mContext.startActivity(yBHYActivity);
			} else {

				Intent bindPhone = new Intent(mContext, LoginActivity.class);
				startActivity(bindPhone);
			}
			break;

		}
	}


	private List<GuangGao> ggList = new ArrayList<GuangGao>();
	private int pre;
	private int lbtdot;
	private boolean isRunning = true;
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 获取广告信息成功
			case NetworkAsyncCommonDefines.GET_GG_INFO_S:
				Bundle ggData = msg.getData();
				if (ggData != null) {
					ggList = ggData.getParcelableArrayList("list");

					if (ggList != null && ggList.size() > 0) {
						lbt_ll.removeAllViews();
						// 增加滑动的点
						for (int i = 0; i < ggList.size(); i++) {
							ImageView iv1 = new ImageView(mContext);
							iv1.setBackgroundResource(R.drawable.point_selector);
							LayoutParams params = new LayoutParams(lbtdot,
									lbtdot);
							if (i != 0) {
								params.leftMargin = lbtdot;
							}
							iv1.setLayoutParams(params);
							iv1.setEnabled(false);
							lbt_ll.addView(iv1);
						}
						rl1.setVisibility(View.VISIBLE);
						mLbtVpAdapter.setData(ggList);
						mLbtVpAdapter.notifyDataSetChanged();
						lbt_ll.getChildAt(0).setEnabled(true);
					}
				} else {
					rl1.setVisibility(View.GONE);
				}

				swiperefreshlayout.setRefreshing(false);
				break;
			// 轮播
			case NetworkAsyncCommonDefines.LUNBO:
				lbt_vp.setCurrentItem(lbt_vp.getCurrentItem() + 1);
				// 获取广告信失败
			case NetworkAsyncCommonDefines.GET_GG_INFO_F:
				swiperefreshlayout.setRefreshing(false);
				break;

			}
		}
	};

	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		isRunning = false;
	}
}
