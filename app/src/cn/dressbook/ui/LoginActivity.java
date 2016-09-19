package cn.dressbook.ui;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.DressBookApp;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.OrdinaryCommonDefines;
import cn.dressbook.ui.net.UserExec;
import cn.dressbook.ui.net.YJTCExec;
import cn.dressbook.ui.service.DownLoadingService;
import cn.dressbook.ui.utils.MD5Utils;
import cn.dressbook.ui.utils.SharedPreferenceUtils;

/**
 * @Description 登陆界面
 * @author 袁东华
 * @date 2014-7-11 上午10:27:38
 */
@ContentView(R.layout.login)
public class LoginActivity extends BaseActivity {
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
	 * 手机号编辑框
	 */
	@ViewInject(R.id.phone_et)
	private EditText phone_et;
	/**
	 * 验证码编辑框
	 */
	@ViewInject(R.id.password_et)
	private EditText password_et;

	/**
	 * 登陆
	 */
	@ViewInject(R.id.login_tv)
	private TextView login_tv;
	/**
	 * 手机号
	 */
	private String mPhoneNumber;
	/**
	 * 设备号
	 */
	private String mSheBeiHao;

	private Context mContext = LoginActivity.this;
	private SharedPreferenceUtils mSharedPreferenceUtils = SharedPreferenceUtils
			.getInstance();
	@ViewInject(R.id.forgetpassword)
	private TextView forgetpassword;
	private String password;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("登录");
		operate_tv.setText("注册");
		operate_tv.setVisibility(View.VISIBLE);

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		DressBookApp.getInstance().getSheBeiHao();
	}

	@Event({ R.id.operate_tv, R.id.back_rl, R.id.login_tv, R.id.forgetpassword })
	private void onClick(View v) throws NameNotFoundException {
		// TODO Auto-generated method stub

		switch (v.getId()) {

		// 点击登陆
		case R.id.login_tv:
			if (isFinish()) {

				pbDialog.show();
				String phone = phone_et.getText().toString();
				password = password_et.getText().toString();
				if (phone != null) {

					phone = phone.trim().replaceAll("\\s", "");
					phone = phone.replaceAll("\t", "");
					phone = phone.replaceAll("\r", "");
					phone = phone.replaceAll("\n", "");
				}
				if (password != null) {

					password = password.trim().replaceAll("\\s", "");
					password = password.replaceAll("\t", "");
					password = password.replaceAll("\r", "");
					password = password.replaceAll("\n", "");
				}

				if (phone == null || "".equals(phone)) {
					Toast.makeText(mContext, "请输入手机号", Toast.LENGTH_SHORT)
							.show();
					pbDialog.dismiss();
				} else if (phone.length() != 11) {
					Toast.makeText(mContext, "请输入正确的手机号", Toast.LENGTH_SHORT)
							.show();
					pbDialog.dismiss();
				} else if (password == null || "".equals(password)) {
					Toast.makeText(mContext, "请输入密码", Toast.LENGTH_SHORT)
							.show();
					pbDialog.dismiss();
				} else if (password.length() < 6) {
					Toast.makeText(mContext, "密码不能小于6位,字母数字组成",
							Toast.LENGTH_SHORT).show();
					pbDialog.dismiss();
				} else if (password.length() > 16) {
					Toast.makeText(mContext, "密码不能大于16位,字母数字组成",
							Toast.LENGTH_SHORT).show();
					pbDialog.dismiss();
				} else if (DressBookApp.getInstance().getSheBeiHao() == null) {
					Toast.makeText(mContext, "没有获取到设备号,请重试", Toast.LENGTH_SHORT)
							.show();
					pbDialog.dismiss();
					DressBookApp.getInstance().getSheBeiHao();
				} else {
					ManagerUtils.getInstance().clearData(mContext);
					String md5 = MD5Utils.getMD5String(password);
					String model = "手机型号：" + android.os.Build.MODEL + "系统版本："
							+ android.os.Build.VERSION.RELEASE;

					String appver = getPackageManager().getPackageInfo(
							mContext.getPackageName(), 0).versionName;
					// 提交登陆数据
					UserExec.getInstance().login(mContext, mHandler, phone,
							md5, DressBookApp.getInstance().getSheBeiHao(),
							model.replace(" ", "-"), appver, "yes",
							NetworkAsyncCommonDefines.LOGIN_S,
							NetworkAsyncCommonDefines.LOGIN_F);
				}
			}
			break;
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		// 点击找回密码
		case R.id.forgetpassword:

			pbDialog.show();
			Intent forgetPasswordActivity = new Intent(mContext,
					ForgetPasswordActivity.class);
			startActivity(forgetPasswordActivity);
			((Activity) mContext).overridePendingTransition(R.anim.back_enter,
					R.anim.anim_exit);
			pbDialog.dismiss();
			break;
		// 点击注册
		case R.id.operate_tv:
			pbDialog.show();
			Intent bangDingPhoneActivity = new Intent(mContext,
					RegisterActivity.class);
			startActivity(bangDingPhoneActivity);
			pbDialog.dismiss();
			finish();
			break;

		}
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onResume();
		if (ManagerUtils.getInstance().getUser_id(mContext) != null
				&& ManagerUtils.getInstance().getPhoneNum(mContext) != null
				&& ManagerUtils.getInstance().getPhoneNum(mContext).length() == 11
				&& ManagerUtils.getInstance().getPassword(mContext) != null) {
			finish();
		}
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			switch (msg.what) {
			// 登陆成功
			case NetworkAsyncCommonDefines.LOGIN_S:
				ManagerUtils.getInstance().setPassword(mContext, password);
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
			// 获取用户的资产成功
			case NetworkAsyncCommonDefines.GET_USER_PROPERTY_S:
				pbDialog.dismiss();
				Toast.makeText(mContext, "登陆成功", Toast.LENGTH_SHORT).show();
				finish();
				break;
			// 获取用户的资产失败
			case NetworkAsyncCommonDefines.GET_USER_PROPERTY_F:
				pbDialog.dismiss();
				finish();
				break;
			// 登陆失败
			case NetworkAsyncCommonDefines.LOGIN_F:
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

			}

		}

	};

}
