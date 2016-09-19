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
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
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
import cn.dressbook.ui.model.AttireScheme;
import cn.dressbook.ui.net.DianPuExec;
import cn.dressbook.ui.net.SMSExec;
import cn.dressbook.ui.net.UserExec;
import cn.dressbook.ui.service.DownLoadingService;
import cn.dressbook.ui.utils.MD5Utils;
import cn.dressbook.ui.utils.SharedPreferenceUtils;

/**
 * @description 增加员工界面
 * @author 袁东华
 * @date 2016-3-16
 */
@ContentView(R.layout.addyuangong)
public class AddYuanGongActivity extends BaseActivity {
	private SharedPreferenceUtils mSharedPreferenceUtils = SharedPreferenceUtils
			.getInstance();
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/**
	 * 员工手机号
	 */
	@ViewInject(R.id.ygsjh_et)
	private EditText ygsjh_et;
	/**
	 * 验证码
	 */
	@ViewInject(R.id.yzm_et)
	private EditText yzm_et;
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
	private String sj_yzm;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("增加员工");
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
	}

	@Event({ R.id.back_rl, R.id.register, R.id.get_yam_tv })
	private void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {

		// 点击提交
		case R.id.register:
			String yzm = yzm_et.getText().toString();
			sj_yzm = mSharedPreferenceUtils.getYZM(activity);
			String phone = ygsjh_et.getText().toString();
			if (phone == null || "".equals(phone)) {
				Toast.makeText(activity, "请输入手机号", Toast.LENGTH_SHORT).show();
				break;
			}
			if (phone.length() != 11) {
				Toast.makeText(activity, "请输入正确的手机号", Toast.LENGTH_SHORT)
						.show();
				break;
			}
			if (yzm == null || "".equals(yzm)) {
				Toast.makeText(activity, "请输入验证码", Toast.LENGTH_SHORT).show();
				break;
			}
			if (!yzm.equals(sj_yzm) && !yzm.equals(OrdinaryCommonDefines.MOREN_YZM)) {
				Toast.makeText(activity, "请输入正确的验证码", Toast.LENGTH_SHORT)
						.show();
				break;
			}
			pbDialog.show();
			DianPuExec.getInstance().addYuanGong(mHandler,
					ManagerUtils.getInstance().getUser_id(activity), phone, 1,
					-1);
			break;
		// 点击返回
		case R.id.back_rl:
			finish();
			break;

		// 点击获取验证码
		case R.id.get_yam_tv:
			sj_yzm = getCheckNum();
			mSharedPreferenceUtils.setYZM(activity, sj_yzm);
			String sjh = ygsjh_et.getText().toString();
			// 生成的随机密码
			if (sjh != null && !"".equals(sjh) && sj_yzm != null
					&& !"".equals(sj_yzm)) {
				if (isPhoneNumberValid(sjh) == false) {
					Toast.makeText(activity, "您输入的不是手机号", Toast.LENGTH_SHORT)
							.show();
				} else {
					pbDialog.show();
					Toast.makeText(activity, "正在发送请求,请稍后", Toast.LENGTH_SHORT)
							.show();
					SMSExec.getInstance().faSongYanZhengMa2(mHandler, sjh,
							"yes", sj_yzm,
							NetworkAsyncCommonDefines.FASONGYANZHENGMA_S,
							NetworkAsyncCommonDefines.FASONGYANZHENGMA_F);
				}
			} else {
				Toast.makeText(activity, "手机号不能为空", Toast.LENGTH_SHORT).show();
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
			case 1:
				Bundle bundle = msg.getData();
				String redesc = bundle.getString("redesc");
				pbDialog.dismiss();
				Toast.makeText(activity, redesc, Toast.LENGTH_SHORT).show();
				break;
			case -1:
				pbDialog.dismiss();
				Toast.makeText(activity, "操作成功", Toast.LENGTH_SHORT).show();
				finish();
				break;
			// 发送验证码成功
			case NetworkAsyncCommonDefines.FASONGYANZHENGMA_S:
				pbDialog.dismiss();
				Toast.makeText(activity, "请求发送成功，请稍后", Toast.LENGTH_SHORT)
						.show();
				paused = false;
				time = 180;
				get_yam_tv.setBackgroundColor(activity.getResources().getColor(
						R.color.black4));
				get_yam_tv.setEnabled(false);
				uiHandler.sendEmptyMessageDelayed(1, 1000);
				updateClockUI();
				break;

			case NetworkAsyncCommonDefines.FASONGYANZHENGMA_F:
				pbDialog.dismiss();
				Bundle data = msg.getData();
				if (data != null) {
					String result = data.getString("result");
					if (result != null) {
						Toast.makeText(activity, result, Toast.LENGTH_SHORT)
								.show();
					} else {
						Toast.makeText(activity, "请求发送失败", Toast.LENGTH_SHORT)
								.show();
					}
				}
				paused = true;
				time = 0;
				updateClockUI();
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

}
