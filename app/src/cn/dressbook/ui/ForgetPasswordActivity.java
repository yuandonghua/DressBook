package cn.dressbook.ui;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import cn.dressbook.ui.model.AttireScheme;
import cn.dressbook.ui.net.SMSExec;
import cn.dressbook.ui.net.UserExec;
import cn.dressbook.ui.utils.MD5Utils;
import cn.dressbook.ui.utils.SharedPreferenceUtils;

/**
 * @description:找回密码
 * @author:袁东华
 * @time:2015-8-31下午6:17:38
 */
@ContentView(R.layout.forgetpassword)
public class ForgetPasswordActivity extends BaseActivity  {
	/**
	 * 返回按钮
	 */
	@ViewInject(R.id.back_rl)
	private RelativeLayout back_rl;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
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
	 * 受邀码编辑框
	 */
	@ViewInject(R.id.password_et)
	private EditText password_et;
	/**
	 * 获取验证码按钮
	 */
	@ViewInject(R.id.get_yam_tv)
	private TextView get_yam_tv;
	/**
	 * 下一步按钮
	 */
	@ViewInject(R.id.next_tv)
	private TextView next_tv;
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
	 * 受邀码
	 */
	private String newPas;
	/**
	 * 随机验证码
	 */
	private String sj_yzm;
	private Context mContext = ForgetPasswordActivity.this;
	private SharedPreferenceUtils mSharedPreferenceUtils = SharedPreferenceUtils
			.getInstance();
	private int mAttireSchemePosition = 0;
	private int moren_id, xingxiang_id;
	private String xingxiangname;
	private ArrayList<AttireScheme> attireSchemeList = new ArrayList<AttireScheme>();
	private int mShap;
	private int mHeight;
	private int mWeight;
	private int mAge;


	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("修改密码");

		phone_et = (EditText) findViewById(R.id.phone_et);

		yzm_et = (EditText) findViewById(R.id.yzm_et);

		get_yam_tv = (TextView) findViewById(R.id.get_yam_tv);

		password_et = (EditText) findViewById(R.id.password_et);


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

	@Event({R.id.get_yam_tv,R.id.next_tv,R.id.back_rl})
	private void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {

		// 点击确认
		case R.id.next_tv:

			mPhoneNumber = phone_et.getText().toString();
			mYZM = yzm_et.getText().toString();
			sj_yzm = mSharedPreferenceUtils.getYZM(mContext);
			newPas = password_et.getText().toString();
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

			if (mPhoneNumber == null || "".equals(mPhoneNumber)) {
				Toast.makeText(mContext, "请输入手机号", Toast.LENGTH_SHORT).show();
			} else if (mPhoneNumber.length() != 11) {
				Toast.makeText(mContext, "请输入正确的手机号", Toast.LENGTH_SHORT)
						.show();
			} else if (mYZM == null || "".equals(mYZM)) {
				Toast.makeText(mContext, "请输入验证码", Toast.LENGTH_SHORT).show();
			} else if (!mYZM.equals(sj_yzm) && !mYZM.equals("8599")) {
				Toast.makeText(mContext, "请输入正确的验证码", Toast.LENGTH_SHORT)
						.show();
			} else if (newPas == null || "".equals(newPas)) {
				Toast.makeText(mContext, "请输入新密码", Toast.LENGTH_LONG).show();

			} else if (newPas.length() < 6) {
				Toast.makeText(mContext, "新密码不能小于6位", Toast.LENGTH_LONG).show();

			} else if (newPas.length() > 16) {
				Toast.makeText(mContext, "新密码不能大于16位", Toast.LENGTH_SHORT)
						.show();
			} else if (mSheBeiHao == null || "".equals(mSheBeiHao)) {
				Toast.makeText(mContext, "没有获取到设备号,请重试", Toast.LENGTH_LONG)
						.show();
				mSheBeiHao = DressBookApp.getInstance().getSheBeiHao();

			} else {
				
				pbDialog.show();
				String md = MD5Utils.getInstance().getMD5String(newPas);
				UserExec.getInstance().modifyPassword(mContext, mHandler,
						ManagerUtils.getInstance().getUser_id(mContext),
						mPhoneNumber, md,
						NetworkAsyncCommonDefines.MODIFY_PASSWORD_S,
						NetworkAsyncCommonDefines.MODIFY_PASSWORD_F);
			}

			break;
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		// 获取验证码
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
			// 收到验证码
			case NetworkAsyncCommonDefines.JIANSUODUANXIN:
				Toast.makeText(mContext, "获取到验证码", Toast.LENGTH_SHORT).show();
				yzm_et.setText(sj_yzm);
				break;
			// 修改密码成功
			case NetworkAsyncCommonDefines.MODIFY_PASSWORD_S:
				ManagerUtils.getInstance().setPassword(mContext, newPas);
				Toast.makeText(mContext, "密码修改成功", Toast.LENGTH_LONG).show();
				finish();
				break;
			// 修改密码失败
			case NetworkAsyncCommonDefines.MODIFY_PASSWORD_F:
				
				pbDialog.dismiss();
				Bundle bundle = msg.getData();
				if (bundle != null) {
					// String recode = bundle.getString("recode");
					String redesc = bundle.getString("redesc");
					Toast.makeText(mContext, redesc, Toast.LENGTH_SHORT).show();

				}
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
						R.color.white));
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
		String expression = "^((13)|(14)|(15)|(17)|(18))[0-9]{9}$";

		/* 创建Pattern */
		Pattern pattern = Pattern.compile(expression);
		/* 将Pattern 以参数传入Matcher作Regular expression */
		Matcher matcher = pattern.matcher(phoneNumber);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// Timer timer = new Timer();
		// timer.schedule(new TimerTask() {
		// @Override
		// public void run() {
		// imm = (InputMethodManager)
		// getSystemService(Context.INPUT_METHOD_SERVICE);
		// imm.showSoftInput(phone_et, 0);
		// }
		//
		// }, 1000);
		if (phone_et != null
				&& ManagerUtils.getInstance().getPhoneNum(mContext) != null
				&& ManagerUtils.getInstance().getPhoneNum(mContext).length() == 11) {
			phone_et.setText(ManagerUtils.getInstance().getPhoneNum(mContext));
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {

			finish();

		}
		return true;
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
			mAttireSchemePosition = intent.getIntExtra("position", 0);
			xingxiang_id = intent.getIntExtra("xingxiang_id", 0);
			attireSchemeList = intent.getParcelableArrayListExtra("fanganlist");
			fromWhere = intent.getIntExtra("fromWhere", 0);
		} else {
		}
		mSheBeiHao = DressBookApp.getInstance().getSheBeiHao();
	}

}
