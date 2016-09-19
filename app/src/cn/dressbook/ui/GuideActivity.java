package cn.dressbook.ui;

import java.util.ArrayList;
import java.util.List;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import cn.dressbook.ui.adapter.GuideAdapter;
import cn.dressbook.ui.bean.ContactInfo;
import cn.dressbook.ui.common.OrdinaryCommonDefines;
import cn.dressbook.ui.utils.ContactUtils;
import cn.dressbook.ui.utils.DialogUtil;
import cn.dressbook.ui.utils.HelperUtils;
import cn.dressbook.ui.utils.SharedPreferenceUtils;

/**
 * @description 导航页
 * @version
 * @author 袁东华
 * @update 2013-12-19 下午05:10:52
 */
@ContentView(R.layout.guide_layout)
public class GuideActivity extends BaseActivity {
	private Context mContext = GuideActivity.this;
	@ViewInject(R.id.viewpager)
	private ViewPager viewpager;
	private GuideAdapter mGuideAdapter;
	private List<View> views;
	private SharedPreferenceUtils mSharedUtils = SharedPreferenceUtils
			.getInstance();

	/**
	 * 检测并设置网络
	 */
	private void checkNetWork() {
		// 没有开启网络
		if (!HelperUtils.isConnect(mContext)) {
			DialogUtil.getInstance(mContext).openNetworkSettings(mContext,
					mHandler, -2, -3);
		} else {

		}

	}

	@Override
	protected void initData() {
	}

	

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		if (!HelperUtils.isConnect(mContext)) {
			checkNetWork();
		} else {
			// JiJieHaoExec.getInstance().getJiJieHaoHuoDong(mHandler, 1, -1);
		}

	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

		}

	};

	private void goHome() {
		mSharedUtils.setIsFirst(mContext, false);
		Intent sliding = new Intent(mContext, MainActivity.class);
		startActivity(sliding);
		overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
		finish();
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(this);

		views = new ArrayList<View>();
		// 初始化引导图片列表
		views.add(inflater.inflate(R.layout.guide_one_layout, null));
		views.add(inflater.inflate(R.layout.guide_two_layout, null));
		views.add(inflater.inflate(R.layout.guide_three_layout, null));
		views.add(inflater.inflate(R.layout.guide_four_layout, null));
		mGuideAdapter = new GuideAdapter(mContext, views);
		viewpager.setAdapter(mGuideAdapter);
		// 绑定回调
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				if (arg0 == 3) {
					mHandler.postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub

							goHome();
						}
					}, 2000);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

		if (!HelperUtils.isConnect(mContext)) {
			checkNetWork();
		} else {

		}
	}

}
