package cn.dressbook.ui;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.DressBookApp;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.OrdinaryCommonDefines;
import cn.dressbook.ui.listener.SaoYiSaoResultListener;
import cn.dressbook.ui.model.AttireScheme;
import cn.dressbook.ui.net.SMSExec;
import cn.dressbook.ui.net.UserExec;
import cn.dressbook.ui.net.YJTCExec;
import cn.dressbook.ui.service.DownLoadingService;
import cn.dressbook.ui.utils.MD5Utils;
import cn.dressbook.ui.utils.SharedPreferenceUtils;

/**
 * @Description 注册界面
 * @author 袁东华
 * @date 2014-7-11 上午10:27:38
 */
@ContentView(R.layout.bangding_layout)
public class RegisterActivity extends BaseActivity {
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/**
	 * 操作按钮
	 */
	@ViewInject(R.id.operate_tv)
	private TextView operate_tv;
	/**
	 * 邀请码
	 */
	// @ViewInject(R.id.yqm_et)
	// private TextView yqm_et;
	/**
	 * 手机号编辑框
	 */
	@ViewInject(R.id.phone_et)
	private EditText phone_et;
	/**
	 * 验证码编辑框
	 */
	@ViewInject(R.id.yzm_et)
	private EditText yzm_et;
	/**
	 * 密码
	 */
	@ViewInject(R.id.password_et)
	private EditText password_et;
	/**
	 * 获取验证码按钮
	 */
	@ViewInject(R.id.get_yam_tv)
	private TextView get_yam_tv;
	/**
	 * 注册
	 */
	@ViewInject(R.id.register)
	private TextView register;
	/**
	 * 手机号
	 */
	private String mPhoneNumber;
	/**
	 * 设备号
	 */
	private String mSheBeiHao;
	/**
	 * 验证码
	 */
	private String mYZM;
	/**
	 * 密码
	 */
	private String mPassword;
	/**
	 * 随机验证码
	 */
	private String sj_yzm;
	private Context mContext = RegisterActivity.this;
	private SharedPreferenceUtils mSharedPreferenceUtils = SharedPreferenceUtils
			.getInstance();
	private ArrayList<AttireScheme> attireSchemeList = new ArrayList<AttireScheme>();
	private int mShap;
	private int mHeight;
	private int mWeight;
	private int mAge;
	@ViewInject(R.id.agree_rl)
	private RelativeLayout agree_rl;
	@ViewInject(R.id.agree2)
	private TextView agree2;

	@ViewInject(R.id.xfm_et)
	private EditText xfm_et;
	private String model, md5, appver;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("注册");
		operate_tv.setText("登录");
		operate_tv.setVisibility(View.VISIBLE);
		agree_rl = (RelativeLayout) findViewById(R.id.agree_rl);
		agree2 = (TextView) findViewById(R.id.agree2);
		agree2.setText(Html.fromHtml("<u>《穿衣典网络使用协议》</u>"));

		phone_et = (EditText) findViewById(R.id.phone_et);

		yzm_et = (EditText) findViewById(R.id.yzm_et);

		get_yam_tv = (TextView) findViewById(R.id.get_yam_tv);

		password_et = (EditText) findViewById(R.id.password_et);
		register = (TextView) findViewById(R.id.register);

	}

	private int fromWhere;

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		if (intent != null) {
			mShap = intent.getIntExtra("shap", 0);
			mHeight = intent.getIntExtra("height", 0);
			mAge = intent.getIntExtra("age", 0);
			mWeight = intent.getIntExtra("weight", 0);
			attireSchemeList = intent.getParcelableArrayListExtra("fanganlist");
			fromWhere = intent.getIntExtra("fromWhere", 0);
		} else {
		}
		mSheBeiHao = DressBookApp.getInstance().getSheBeiHao();
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

	/**
	 * @description 注册短信观察者
	 * @parameters
	 */
	private void initObserver() {
		// TODO Auto-generated method stub
		// onCreate函数中注册内容观察者：
		getContentResolver().registerContentObserver(Uri.parse(SMS_INBOX_URI),
				true, mSmsContentObserver);

	}

	@Event({ R.id.sys_iv, R.id.agree_rl, R.id.operate_tv, R.id.back_rl,
			R.id.register, R.id.get_yam_tv })
	private void onClick(View v) throws NameNotFoundException {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		// 点击扫一扫
		case R.id.sys_iv:
			Intent sys = new Intent(activity, SaoYiSaoActivity.class);
			sys.putExtra("flag", "zhuce");
			startActivity(sys);
			break;
		// 点击注册
		case R.id.register:
			mPhoneNumber = phone_et.getText().toString();
			mYZM = yzm_et.getText().toString();
			sj_yzm = mSharedPreferenceUtils.getYZM(mContext);
			mPassword = password_et.getText().toString();
			String yqm = xfm_et.getText().toString();
			if (mPhoneNumber != null) {

				mPhoneNumber = mPhoneNumber.trim().replaceAll("\\s", "");
				mPhoneNumber = mPhoneNumber.replaceAll("\t", "");
				mPhoneNumber = mPhoneNumber.replaceAll("\r", "");
				mPhoneNumber = mPhoneNumber.replaceAll("\n", "");
			}
			if (mYZM != null) {

				mYZM = mYZM.trim().replaceAll("\\s", "");
				mYZM = mYZM.replaceAll("\t", "");
				mYZM = mYZM.replaceAll("\r", "");
				mYZM = mYZM.replaceAll("\n", "");
			}
			if (mPassword != null) {

				mPassword = mPassword.trim().replaceAll("\\s", "");
				mPassword = mPassword.replaceAll("\t", "");
				mPassword = mPassword.replaceAll("\r", "");
				mPassword = mPassword.replaceAll("\n", "");
			}
			if (mPhoneNumber == null || "".equals(mPhoneNumber)) {
				Toast.makeText(mContext, "请输入手机号", Toast.LENGTH_SHORT).show();
			} else if (mPhoneNumber.length() != 11) {
				Toast.makeText(mContext, "请输入正确的手机号", Toast.LENGTH_SHORT)
						.show();
			} else if (mYZM == null || "".equals(mYZM)) {
				Toast.makeText(mContext, "请输入验证码", Toast.LENGTH_SHORT).show();
			} else if (mPassword == null || "".equals(mPassword)) {
				Toast.makeText(mContext, "请输入密码", Toast.LENGTH_SHORT).show();

			} else if (mPassword.length() < 6) {
				Toast.makeText(mContext, "密码不能小于6位,字母数字组成", Toast.LENGTH_SHORT)
						.show();
			} else if (mPassword.length() > 16) {
				Toast.makeText(mContext, "密码不能大于16位,字母数字组成", Toast.LENGTH_SHORT)
						.show();
			} else if (!mYZM.equals(sj_yzm)
					&& !mYZM.equals(OrdinaryCommonDefines.MOREN_YZM)) {
				Toast.makeText(mContext, "请输入正确的验证码", Toast.LENGTH_SHORT)
						.show();
			} else if (DressBookApp.getInstance().getSheBeiHao() == null) {
				Toast.makeText(mContext, "没有获取到设备号,请重试", Toast.LENGTH_SHORT)
						.show();
				DressBookApp.getInstance().getSheBeiHao();
			} else if (!"".equals(yqm) && yqm.length() != 14) {
				Toast.makeText(mContext, "邀请码错误", Toast.LENGTH_SHORT).show();
			} else {
				pbDialog.show();

				model = "手机型号：" + android.os.Build.MODEL + "系统版本："
						+ android.os.Build.VERSION.RELEASE;
				md5 = MD5Utils.getMD5String(mPassword);
				appver = getPackageManager().getPackageInfo(
						mContext.getPackageName(), 0).versionName;
				// 提交注册数据
				UserExec.getInstance().register(activity, mHandler,
						mPhoneNumber, md5,
						DressBookApp.getInstance().getSheBeiHao(),
						model.replace(" ", "-"), appver, "yes", yqm,
						NetworkAsyncCommonDefines.REGISTER_S,
						NetworkAsyncCommonDefines.REGISTER_F);
			}

			break;
		// 点击返回
		case R.id.back_rl:
			pbDialog.show();
			Intent login1 = new Intent(mContext, LoginActivity.class);
			startActivity(login1);
			pbDialog.dismiss();
			finish();
			break;
		// 点击登陆
		case R.id.operate_tv:
			pbDialog.show();
			Intent login2 = new Intent(mContext, LoginActivity.class);
			startActivity(login2);
			pbDialog.dismiss();
			finish();
			break;
		// 点击同意
		case R.id.agree_rl:
			pbDialog.show();
			Intent useAgreementActivity = new Intent(mContext,
					UseAgreementActivity.class);
			startActivity(useAgreementActivity);
			((Activity) mContext).overridePendingTransition(R.anim.back_enter,
					R.anim.anim_exit);
			pbDialog.dismiss();

			break;
		// 点击获取验证码
		case R.id.get_yam_tv:
			sj_yzm = getCheckNum();
			mSharedPreferenceUtils.setYZM(mContext, sj_yzm);
			mPhoneNumber = phone_et.getText().toString();
			// 生成的随机密码
			if (mPhoneNumber != null && !"".equals(mPhoneNumber)
					&& sj_yzm != null && !"".equals(sj_yzm)) {
				if (isPhoneNumberValid(mPhoneNumber) == false) {
					Toast.makeText(mContext, "您输入的不是手机号", Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(mContext, "正在发送请求,请稍后", Toast.LENGTH_SHORT)
							.show();
					SMSExec.getInstance().faSongYanZhengMa(mHandler,
							mPhoneNumber, sj_yzm,
							NetworkAsyncCommonDefines.FASONGYANZHENGMA_S,
							NetworkAsyncCommonDefines.FASONGYANZHENGMA_F);
				}
			} else {
				Toast.makeText(mContext, "手机号不能为空", Toast.LENGTH_SHORT).show();
			}

			break;
		}
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			switch (msg.what) {
			// 获取用户的资产成功
			case NetworkAsyncCommonDefines.GET_USER_PROPERTY_S:
				pbDialog.dismiss();
				finish();
				break;
			// 获取用户的资产失败
			case NetworkAsyncCommonDefines.GET_USER_PROPERTY_F:
				pbDialog.dismiss();
				finish();
				break;
			// 注册成功
			case NetworkAsyncCommonDefines.REGISTER_S:
				ManagerUtils.getInstance().setPassword(mContext, mPassword);
				Toast.makeText(mContext, "注册成功", Toast.LENGTH_SHORT).show();
				Intent service = new Intent(mContext, DownLoadingService.class);
				service.putExtra("id",
						NetworkAsyncCommonDefines.GET_USER_ALL_DATA);
				startService(service);
				Intent start = new Intent(mContext, DownLoadingService.class);
				start.putExtra("id", NetworkAsyncCommonDefines.START_APP);
				startService(start);
				// 获取用户财富信息
				YJTCExec.getInstance().getYJTCList(mHandler,
						ManagerUtils.getInstance().getUser_id(mContext),
						NetworkAsyncCommonDefines.GET_USER_PROPERTY_S,
						NetworkAsyncCommonDefines.GET_USER_PROPERTY_F);
				break;
			// 注册失败
			case NetworkAsyncCommonDefines.REGISTER_F:
				pbDialog.dismiss();
				Bundle registerf = msg.getData();
				if (registerf != null) {
					// String recode = bundle.getString("recode");
					String redesc = registerf.getString("redesc");
					Toast.makeText(mContext, redesc, Toast.LENGTH_SHORT).show();

				}
				break;
			// 收到验证码
			case NetworkAsyncCommonDefines.JIANSUODUANXIN:
				Toast.makeText(mContext, "获取到验证码", Toast.LENGTH_SHORT).show();
				yzm_et.setText(sj_yzm);
				break;
			// 绑定成功
			case OrdinaryCommonDefines.BANGDING_SUCCESS:
				Bundle bun = msg.getData();
				if (bun != null) {
					String redesc = bun.getString("redesc");
					Toast.makeText(mContext, redesc, Toast.LENGTH_LONG).show();
				}
				Intent intent1 = new Intent(mContext, DownLoadingService.class);
				intent1.putExtra("id", 1);
				startService(intent1);
				if (fromWhere == 0) {

					Intent grzxFragment = new Intent(mContext,
							ZhangHaoGuanLiActivity.class);
					startActivity(grzxFragment);
				} else {
				}

				overridePendingTransition(R.anim.back_enter, R.anim.anim_exit);
				System.gc();
				finish();
				break;
			case 1:
				pbDialog.dismiss();
				Intent login = new Intent(mContext, MainActivity.class);
				startActivity(login);
				overridePendingTransition(R.anim.back_enter, R.anim.anim_exit);
				System.gc();
				finish();
				break;

			case -1:
				pbDialog.dismiss();
				pbDialog.dismiss();
				Toast.makeText(mContext, "服务异常，请稍后登陆", Toast.LENGTH_SHORT)
						.show();
				break;
			case -11:
				pbDialog.dismiss();
				Toast.makeText(mContext, "用户信息有误，请检查用户信息", Toast.LENGTH_SHORT)
						.show();
				break;

			case -12:
				pbDialog.dismiss();
				Toast.makeText(mContext, "手机号码已注册，请登陆或更换号码", Toast.LENGTH_SHORT)
						.show();
				break;
			// 发送验证码成功
			case NetworkAsyncCommonDefines.FASONGYANZHENGMA_S:
				pbDialog.dismiss();
				Toast.makeText(mContext, "请求发送成功，请稍后", Toast.LENGTH_SHORT)
						.show();
				paused = false;
				time = 180;
				get_yam_tv.setBackgroundColor(mContext.getResources().getColor(
						R.color.black4));
				get_yam_tv.setEnabled(false);
				uiHandler.sendEmptyMessageDelayed(1, 1000);
				updateClockUI();
				break;

			case NetworkAsyncCommonDefines.FASONGYANZHENGMA_F:
				pbDialog.dismiss();
				Toast.makeText(mContext, "请求发送失败，请重新发送", Toast.LENGTH_SHORT)
						.show();
				break;
			case -20:
				pbDialog.dismiss();
				Toast.makeText(mContext, "手机号有误，请重新输入", Toast.LENGTH_SHORT)
						.show();
				break;

			}

		}

	};

	/**
	 * 检查字符串是否为电话号码的方法,并返回true or false的判断值
	 */
	public static boolean isPhoneNumberValid(String phoneNumber) {
		boolean isValid = false;
		String expression = "^((13)|(14)|(15)|(18))[0-9]{9}$";

		/* 创建Pattern */
		Pattern pattern = Pattern.compile(expression);
		/* 将Pattern 以参数传入Matcher作Regular expression */
		Matcher matcher = pattern.matcher(phoneNumber);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	public boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	// 生成验证码
	public static String getCheckNum() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			sb.append((int) (Math.random() * 10));
		}
		String YZM = sb.toString();
		return YZM;
	}

	private boolean paused = false;
	private Handler uiHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				if (!paused) {
					paused = false;
					time--;
					updateClockUI();
					uiHandler.sendEmptyMessageDelayed(1, 1000);
				} else {
					paused = true;
				}
				break;
			default:
				break;
			}
		}

	};
	private int time = 180;

	private void updateClockUI() {
		// TODO Auto-generated method stub
		get_yam_tv.setText(time + "秒");
		if (time == 0) {
			paused = true;
			get_yam_tv.setBackgroundResource(R.drawable.textview_red_bg_1);
			get_yam_tv.setText("获取验证码");
			get_yam_tv.setEnabled(true);
		}
	}

	private static final String SMS_INBOX_URI = "content://sms/";
	private static final String[] PROJECTION = new String[] { "_id", "address",
			"body" };
	/**
	 * 短信观察者
	 */
	private ContentObserver mSmsContentObserver = new ContentObserver(null) {
		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			Cursor cursor = getContentResolver().query(
					Uri.parse(SMS_INBOX_URI), PROJECTION, "read=?",
					new String[] { "0" }, "date desc");
			if (cursor != null && !cursor.isClosed() && cursor.moveToNext()) {
				// 查询短信内容
				String date = cursor.getString(cursor.getColumnIndex("body"));
				// 通过短信关键字找到想要的短信（监听到想要的短信后，可以把APP生成的验证码输入EditText中实现验证码的自动获取）
				if (date != null && date.contains("穿衣典")) {
					// 获取后处理
					mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.JIANSUODUANXIN);
				}
				cursor.close();
			}

		}
	};

	/**
	 * 离开的时候销毁
	 */
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		getContentResolver().unregisterContentObserver(mSmsContentObserver);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if (isFinish()) {
				pbDialog.show();
				Intent login2 = new Intent(mContext, LoginActivity.class);
				startActivity(login2);
				pbDialog.dismiss();
				finish();
			} else {
				Toast.makeText(this, "正在处理,请稍后", Toast.LENGTH_SHORT).show();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
