package cn.dressbook.ui;

import java.io.File;

import org.xutils.view.annotation.ContentView;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.service.DownLoadingService;
import cn.dressbook.ui.utils.DialogUtil;
import cn.dressbook.ui.utils.HelperUtils;
import cn.dressbook.ui.utils.SharedPreferenceUtils;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;

/**
 * @description 欢迎界面
 * @version
 * @author 袁东华
 * @update 2013-12-19 下午06:19:23
 */
@ContentView(R.layout.start_layout)
public class StartActivity extends BaseActivity {
	private Context mContext = StartActivity.this;
	private SharedPreferenceUtils mSharedUtils = SharedPreferenceUtils
			.getInstance();
	private boolean mIsFirst = false;
	private static final int GO_HOME = 13;
	private static final int GO_GUIDE = 12;
	private static final String TAG = StartActivity.class.getSimpleName();
	private ImageView start_huanying_iv;

	/**
	 * @description
	 * @parameters
	 */
	private void setContent() {
		// TODO Auto-generated method stub
		MobclickAgent.updateOnlineConfig(mContext);
		// 启动应用开始下载数据
		Intent downServiceIntent = new Intent(mContext,
				DownLoadingService.class);
		downServiceIntent.putExtra("id", NetworkAsyncCommonDefines.START_APP);
		startService(downServiceIntent);
		mIsFirst = mSharedUtils.getIsFirst(mContext);
		jianChaTiaoZhuan();
	}

	/**
	 * 
	 * @description 检查跳转情况
	 * @version
	 * @author
	 * @update 2014-1-10 上午11:42:14
	 */
	private void jianChaTiaoZhuan() {

		// 不是第一次
		if (!mIsFirst) {
			mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					goHome();
				}
			}, 3000);
		} else {
			goGuide();
		}

	}

	private int a2;
	private boolean positionFlag;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_HOME:
				goHome();
				break;
			case GO_GUIDE:
				goGuide();
				break;
			case 6:
				goHome();
				break;
			// 获取集结号活动的返回值
			case 1:

				break;
			// 获取集结号活动失败的返回值
			case -1:

				break;
			case -2:
				break;
			case -3:
				break;
			}
			super.handleMessage(msg);
		}
	};

	private void goHome() {
		Intent sliding = new Intent(mContext, MainActivity.class);
		startActivity(sliding);
		overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
		System.gc();
		finish();
	}

	private void goGuide() {
		Intent daohang = new Intent(mContext, GuideActivity.class);
		startActivity(daohang);
		overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
		finish();
	}

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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			finish();
			System.exit(0);
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.e(TAG, "onRestart------------------------------");
		if (!HelperUtils.isConnect(mContext)) {
			checkNetWork();
		} else {
			// JiJieHaoExec.getInstance().getJiJieHaoHuoDong(mHandler, 1, -1);
		}

	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		start_huanying_iv = (ImageView) findViewById(R.id.start_huanying_iv);
		start_huanying_iv.setVisibility(View.VISIBLE);

		if (!HelperUtils.isConnect(mContext)) {
			checkNetWork();
		} else {
			// JiJieHaoExec.getInstance().getJiJieHaoHuoDong(mHandler, 1, -1);
		}
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		File photo_cache = new File(PathCommonDefines.PHOTOCACHE_FOLDER);
		if (!photo_cache.exists()) {
			photo_cache.mkdirs();
		}
		File json_cache = new File(PathCommonDefines.JSON_FOLDER);
		if (!json_cache.exists()) {
			json_cache.mkdirs();
		}
		MobclickAgent.updateOnlineConfig(mContext);
		/** 设置是否对日志信息进行加密, 默认false(不加密). */
		AnalyticsConfig.enableEncrypt(true);
		setContent();
	}

}
