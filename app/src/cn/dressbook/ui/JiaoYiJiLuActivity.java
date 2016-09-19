package cn.dressbook.ui;

import java.util.ArrayList;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import cn.dressbook.ui.adapter.JiaoYiJiLuAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.model.JiaoYiJiLuBiaoZhi;
import cn.dressbook.ui.net.JiaoYiJiLuExec;
import cn.dressbook.ui.viewpager.PagerSlidingTabStrip;

/**
 * @description 交易记录
 * @author 袁东华
 * @date 2016-2-19
 */
@ContentView(R.layout.jiaoyijilu)
public class JiaoYiJiLuActivity extends BaseFragmentActivity {
	@ViewInject(R.id.tabs)
	private PagerSlidingTabStrip mPagerSlidingTabStrip;
	@ViewInject(R.id.pager)
	private ViewPager mViewPager;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	private ArrayList<JiaoYiJiLuBiaoZhi> bzList;
	private JiaoYiJiLuAdapter mJiaoYiJiLuAdapter;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("交易记录");
		// mPagerSlidingTabStrip.setIndicatorColorResource(R.color.red1);

		mPagerSlidingTabStrip
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

					@Override
					public void onPageSelected(int arg0) {
					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {
					}

					@Override
					public void onPageScrollStateChanged(int arg0) {
					}
				});
	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		// 获取交易记录标识列表
		JiaoYiJiLuExec.getInstance().getJYJLBiaoZhiList(mHandler,
				NetworkAsyncCommonDefines.GET_BZ_LIST_S,
				NetworkAsyncCommonDefines.GET_BZ_LIST_F);
	}

	@Event({ R.id.back_rl })
	private void onClick(View v) {
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			finish();
			break;

		default:
			break;
		}

	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			// 获取交易记录标识列表成功
			case NetworkAsyncCommonDefines.GET_BZ_LIST_S:
				Bundle data = msg.getData();
				if (data != null) {
					bzList = data.getParcelableArrayList("list");
					mJiaoYiJiLuAdapter = new JiaoYiJiLuAdapter(
							getSupportFragmentManager());
					mJiaoYiJiLuAdapter.setData(bzList);
					mViewPager.setAdapter(mJiaoYiJiLuAdapter);
					mPagerSlidingTabStrip.setViewPager(mViewPager);
				}
				break;
			// 获取交易记录标识列表成功
			case NetworkAsyncCommonDefines.GET_BZ_LIST_F:

				break;
			default:
				break;
			}
		}

	};
}
