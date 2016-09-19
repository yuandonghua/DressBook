package cn.dressbook.ui;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.fragment.BuyerFragment;
import cn.dressbook.ui.listener.DingZhiListener;
import cn.dressbook.ui.listener.BuyerListener;
import cn.dressbook.ui.listener.SaoYiSaoResultListener;
import cn.dressbook.ui.net.ShareExec;
import cn.dressbook.ui.service.DownLoadingService;
import cn.dressbook.ui.service.MyPushIntentService;
import cn.dressbook.ui.utils.SharedPreferenceUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.umeng.update.UmengUpdateAgent;

/**
 * @description:主activity
 * @author:袁东华
 * @modifier:袁东华
 * @remarks:
 * @2015年7月4日 下午3:45:50
 */
@ContentView(R.layout.mainactivity_layout)
public class MainActivity extends BaseFragmentActivity {
	private Context mContext = MainActivity.this;

	private PushAgent mPushAgent;
	private SharedPreferenceUtils mSharedPreferenceUtils = SharedPreferenceUtils
			.getInstance();
	private View mView;
	/**
	 * 加号的PopupWindow
	 */
	private PopupWindow mPopupWindow;
	/**
	 * 加号
	 */
	@ViewInject(R.id.operate_iv)
	private ImageView operate_iv;
	/**
	 * 搜索
	 */
	@ViewInject(R.id.operate_iv_2)
	private ImageView operate_iv_2;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/**
	 * 返回按钮
	 */
	@ViewInject(R.id.back_rl)
	private RelativeLayout back_rl;
	/**
	 * 分享
	 */
	private RelativeLayout share_rl;
	/**
	 * 扫一扫
	 */
	private RelativeLayout sys_rl;
	/**
	 * 发需求
	 */
	private RelativeLayout fxq_rl;

	/**
	 * 购物车
	 */
	private RelativeLayout shoppingcart_rl;
	/**
	 * 发博文
	 */
	private RelativeLayout fbw_rl;
	/**
	 * 顾问
	 */
	@ViewInject(R.id.adviser_content_rl)
	private RelativeLayout adviser_content_rl;
	/**
	 * 买手
	 */
	@ViewInject(R.id.buyer_content_rl)
	private RelativeLayout buyer_content_rl;
	/**
	 * 发现
	 */
	@ViewInject(R.id.find_content_rl)
	private RelativeLayout find_content_rl;
	/**
	 * 我的
	 */
	@ViewInject(R.id.my_content_rl)
	private RelativeLayout my_content_rl;
	/**
	 * 顾问按钮
	 */
	@ViewInject(R.id.adviser_bottom_rl)
	private RelativeLayout adviser_bottom_rl;
	@ViewInject(R.id.adviser_bottom_iv)
	private ImageView adviser_bottom_iv;
	@ViewInject(R.id.adviser_bottom_tv)
	private TextView adviser_bottom_tv;
	/**
	 * 买手按钮
	 */
	@ViewInject(R.id.buyer_bottom_rl)
	private RelativeLayout buyer_bottom_rl;
	@ViewInject(R.id.buyer_bottom_iv)
	private ImageView buyer_bottom_iv;
	@ViewInject(R.id.buyer_bottom_tv)
	private TextView buyer_bottom_tv;
	/**
	 * 发现按钮
	 */
	@ViewInject(R.id.find_bottom_rl)
	private RelativeLayout find_bottom_rl;
	@ViewInject(R.id.find_bottom_iv)
	private ImageView find_bottom_iv;
	@ViewInject(R.id.find_bottom_tv)
	private TextView find_bottom_tv;
	/**
	 * 我的按钮
	 */
	@ViewInject(R.id.my_bottom_rl)
	private RelativeLayout my_bottom_rl;
	@ViewInject(R.id.my_bottom_iv)
	private ImageView my_bottom_iv;
	@ViewInject(R.id.my_bottom_tv)
	private TextView my_bottom_tv;
	
	static {
		try {
			// 加载魔法抠图lib库
			System.loadLibrary("opencv_java");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	static {
		try {
			// 加载扣头穿衣lib库
			System.loadLibrary("detection_based_tracker");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		UmengUpdateAgent.update(this);
		// 友盟推送
		mPushAgent = PushAgent.getInstance(this);
		mPushAgent.onAppStart();
		mPushAgent.enable(ManagerUtils.mRegisterCallback);
		mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
		if (mPushAgent.isEnabled()) {
		} else {
			mPushAgent.enable();
		}
		updateVersion();
	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		mSharedPreferenceUtils.setIsFirst(this, false);
		// 获取分享内容
		ShareExec.getInstance().getShareContent(mHandler, "sqfx_plus",
				NetworkAsyncCommonDefines.GET_SHARE_S,
				NetworkAsyncCommonDefines.GET_SHARE_F);
//		mHandler.postDelayed(new Runnable() {
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//
//				Intent sliding = new Intent(mContext, TongXunLuActivity.class);
//				startActivity(sliding);
//				overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
//			}
//		}, 1000 * 3);
	}

	

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		operate_iv_2.setImageResource(R.drawable.search_src_1);
		operate_iv_2.setVisibility(View.VISIBLE);
		// 加号view
		mView = this.getLayoutInflater().inflate(R.layout.add_popu, null);
		title_tv.setText("穿衣典");
		back_rl.setVisibility(View.GONE);
		operate_iv.setImageResource(R.drawable.add_src_1);
		operate_iv.setVisibility(View.VISIBLE);
		mPopupWindow = new PopupWindow(mView,
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		mPopupWindow.setOutsideTouchable(true);
		sys_rl = (RelativeLayout) mView.findViewById(R.id.sys_rl);
		// 点击扫一扫
		sys_rl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mPopupWindow.isShowing()) {
					mPopupWindow.dismiss();
				}
				// shareToCircle();
				if (ManagerUtils.getInstance().isLogin(mContext)) {
					Intent sys = new Intent(activity, SaoYiSaoActivity.class);
					sys.putExtra("flag", "zhuye");
					startActivity(sys);
				} else {
					Intent bindPhone = new Intent(mContext, LoginActivity.class);
					startActivity(bindPhone);
				}
			}
		});
		share_rl = (RelativeLayout) mView.findViewById(R.id.share_rl);
		// 点击分享
		share_rl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mPopupWindow.isShowing()) {
					mPopupWindow.dismiss();
				}
				// shareToCircle();
				if (ManagerUtils.getInstance().isLogin(mContext)) {
					shareAll();
				} else {
					Intent bindPhone = new Intent(mContext, LoginActivity.class);
					startActivity(bindPhone);
				}
			}
		});
		fxq_rl = (RelativeLayout) mView.findViewById(R.id.fxq_rl);
		// 点击发布需求
		fxq_rl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mPopupWindow.isShowing()) {
					mPopupWindow.dismiss();
				}
				if (ManagerUtils.getInstance().isLogin(mContext)) {
					Intent publishRequirementActivity = new Intent(mContext,
							PublishRequirementActivity.class);
					startActivity(publishRequirementActivity);
				} else {

					Intent bindPhone = new Intent(mContext, LoginActivity.class);
					startActivity(bindPhone);
				}
			}
		});
		shoppingcart_rl = (RelativeLayout) mView
				.findViewById(R.id.shoppingcart_rl);
		// 点击购物车
		shoppingcart_rl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mPopupWindow.isShowing()) {
					mPopupWindow.dismiss();
				}
				if (ManagerUtils.getInstance().isLogin(mContext)) {
					Intent shoppingcartIntent = new Intent(mContext,
							ShoppingCartActivity.class);
					startActivity(shoppingcartIntent);
				} else {

					Intent bindPhone = new Intent(mContext, LoginActivity.class);
					startActivity(bindPhone);
				}
			}
		});
		fbw_rl = (RelativeLayout) mView.findViewById(R.id.fbw_rl);
		// 点击发博文
		fbw_rl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mPopupWindow.isShowing()) {
					mPopupWindow.dismiss();
				}
				if (ManagerUtils.getInstance().isLogin(mContext)) {
					Intent writeArticleActivity = new Intent(mContext,
							FaBoWenActivity.class);
					startActivity(writeArticleActivity);
				} else {
					Intent bindPhone = new Intent(mContext, LoginActivity.class);
					startActivity(bindPhone);
				}
			}
		});
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

	/**
	 * 
	 * @description 版本更新
	 * @version
	 * @author
	 * @update 2013-12-31 上午11:28:49
	 */
	private void updateVersion() {
		// TODO Auto-generated method stub
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.update(this);
	}

	public static interface SelectSheQuGuWenShi {
		void setOnSelect(int flag);
	}

	@Event({ R.id.adviser_bottom_rl, R.id.buyer_bottom_rl, R.id.find_bottom_rl,
			R.id.my_bottom_rl, R.id.operate_iv, R.id.share_rl,
			R.id.shoppingcart_rl, R.id.operate_iv_2 })
	private void onClick(View v) {

		switch (v.getId()) {
		// 点击搜索
		case R.id.operate_iv_2:
			if (mPopupWindow.isShowing()) {
				mPopupWindow.dismiss();
			}
			if (!ManagerUtils.getInstance().isLogin(mContext)) {
				// 跳转到登录页
				Intent bindPhone = new Intent(mContext, LoginActivity.class);
				mContext.startActivity(bindPhone);
			} else {
				// 跳转到搜索界面
				Intent findActivity = new Intent(this, FindActivity.class);
				startActivity(findActivity);
			}
			break;

		// 点击加号
		case R.id.operate_iv:
			if (mPopupWindow.isShowing()) {
				mPopupWindow.dismiss();
			} else {
				mPopupWindow.showAsDropDown(v);
			}
			break;
		// 点击定制
		case R.id.adviser_bottom_rl:
			if (mPopupWindow.isShowing()) {
				mPopupWindow.dismiss();
			}
			setBottomNavigationState(0);
			break;
		// 点击买手
		case R.id.buyer_bottom_rl:
			if (mPopupWindow.isShowing()) {
				mPopupWindow.dismiss();
			}
			setBottomNavigationState(1);
			break;

		// 点击发现
		case R.id.find_bottom_rl:
			if (mPopupWindow.isShowing()) {
				mPopupWindow.dismiss();
			}
			setBottomNavigationState(2);
			break;
		// 点击我的
		case R.id.my_bottom_rl:
			if (mPopupWindow.isShowing()) {
				mPopupWindow.dismiss();
			}
			setBottomNavigationState(3);
			break;

		}
	}

	/**
	 * 分享到所有平台
	 */
	protected void shareAll() {
		try {

			Intent intent = new Intent(mContext, MyShareActivity.class);
			intent.putExtra("targeturl",
					url + "?" + param + "="
							+ ManagerUtils.getInstance().getUser_id(mContext)
							+ "&mobile="
							+ ManagerUtils.getInstance().getPhoneNum(mContext));
			intent.putExtra("content", mContent);
			intent.putExtra("title", title);
			intent.putExtra("pic", pic);
			startActivity(intent);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private static SaoYiSaoResultListener mYiFenListener;

	public static void setYiFenListener(
			SaoYiSaoResultListener saoYiSaoResultListener) {
		mYiFenListener = saoYiSaoResultListener;
	}

	private static BuyerListener mBuyerListener;

	public static void setBuyerListener(BuyerListener buyerListener) {
		mBuyerListener = buyerListener;
	}

	private static DingZhiListener mDingZhiListener;

	public static void setDingZhiListener(DingZhiListener DingZhiListener) {
		mDingZhiListener = DingZhiListener;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/* 在这里，我们通过碎片管理器中的Tag，就是每个碎片的名称，来获取对应的fragment */
		Fragment f = getSupportFragmentManager().findFragmentById(
				R.id.myfragment);
		/* 然后在碎片中调用重写的onActivityResult方法 */
		f.onActivityResult(requestCode, resultCode, data);
	}

	private long exitTime = 0;

	private BuyerFragment buyerFragment;

	@Override
	protected void onStop() {
		super.onStop();
		// 如果您同时使用了手动更新和自动检查更新，请加上下面这句代码，因为这些配置是全局静态的。
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.setUpdateAutoPopup(true);
		UmengUpdateAgent.setUpdateListener(null);
		UmengUpdateAgent.setDownloadListener(null);
		UmengUpdateAgent.setDialogListener(null);
	}

	private void setBottomNavigationState(int i) {
		clearBootomNavigation();
		switch (i) {
		// 定制
		case 0:
			adviser_bottom_iv.setImageResource(R.drawable.zhuye_selected);
			adviser_bottom_tv.setTextColor(getResources()
					.getColor(R.color.red1));
			adviser_content_rl.setVisibility(View.VISIBLE);
			if (mDingZhiListener != null) {
				mDingZhiListener.onLazyLoad();
			}
			break;
		// 买手
		case 1:
			buyer_bottom_iv.setImageResource(R.drawable.cyd_selected);
			buyer_bottom_tv.setTextColor(getResources().getColor(R.color.red1));
			buyer_content_rl.setVisibility(View.VISIBLE);
			// FragmentManager sfm = getSupportFragmentManager();
			// FragmentTransaction transaction = sfm.beginTransaction();
			// if (buyerFragment == null) {
			// buyerFragment = new BuyerFragment();
			// }
			// transaction.replace(R.id.buyer_fl, buyerFragment);
			// transaction.commit();
			if (mBuyerListener != null) {
				mBuyerListener.onLazyLoad();
			}
			break;

		// 发现
		case 2:
			find_bottom_iv.setImageResource(R.drawable.myb_selected);
			find_bottom_tv.setTextColor(getResources().getColor(R.color.red1));
			find_content_rl.setVisibility(View.VISIBLE);
			break;
		// 我的
		case 3:
			my_bottom_iv.setImageResource(R.drawable.w_selected);
			my_bottom_tv.setTextColor(getResources().getColor(R.color.red1));
			my_content_rl.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
	}

	private void clearBootomNavigation() {
		// TODO Auto-generated method stub
		adviser_bottom_iv.setImageResource(R.drawable.zhuye_unselected);
		adviser_bottom_tv.setTextColor(getResources().getColor(R.color.black4));
		buyer_bottom_iv.setImageResource(R.drawable.cyd_unselected);
		buyer_bottom_tv.setTextColor(getResources().getColor(R.color.black4));
		find_bottom_iv.setImageResource(R.drawable.myb_unselected);
		find_bottom_tv.setTextColor(getResources().getColor(R.color.black4));
		my_bottom_iv.setImageResource(R.drawable.w_unselected);
		my_bottom_tv.setTextColor(getResources().getColor(R.color.black4));
		buyer_content_rl.setVisibility(View.GONE);
		adviser_content_rl.setVisibility(View.GONE);
		find_content_rl.setVisibility(View.GONE);
		my_content_rl.setVisibility(View.GONE);

	}

	private String title, url, param, pic, id, mContent;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			// 上传通讯录成功
			case NetworkAsyncCommonDefines.UPLOAD_TXL_S:
				mSharedPreferenceUtils.setTXL(activity, true);
				break;
			// 上传通讯录失败
			case NetworkAsyncCommonDefines.UPLOAD_TXL_F:
				mSharedPreferenceUtils.setTXL(activity, false);
				break;
			// 获取分享内容成功
			case NetworkAsyncCommonDefines.GET_SHARE_S:
				Bundle data = msg.getData();
				if (data != null) {
					title = data.getString("title");
					url = data.getString("url");
					param = data.getString("param");
					pic = data.getString("pic");
					mContent = data.getString("content");
				}
				break;
			// 获取分享内容失败
			case NetworkAsyncCommonDefines.GET_SHARE_F:

				break;

			default:
				break;
			}
		}

	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次才能离开",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				// RongIM.getInstance().disconnect();
				// 删除文件
				Intent service = new Intent(this, DownLoadingService.class);
				service.putExtra("id", 8);
				startService(service);
				finish();

			}
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_MENU
				&& event.getAction() == KeyEvent.ACTION_DOWN) {

		} else if (keyCode == KeyEvent.KEYCODE_HOME
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
		}

		return true;
	}

	@SuppressLint("NewApi")
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);

	}

}