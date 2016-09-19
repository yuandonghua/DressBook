package cn.dressbook.ui;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.net.UserExec;

import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.login.LoginService;
import com.alibaba.sdk.android.login.callback.LogoutCallback;

/**
 * @description 账号管理界面
 * @author 袁东华
 * @date 2016-2-22
 */
@ContentView(R.layout.myaccount_layout)
public class ZhangHaoGuanLiActivity extends BaseActivity {
	private Context mContext = ZhangHaoGuanLiActivity.this;
	/**
	 * 手机号
	 */
	@ViewInject(R.id.phone_value)
	private TextView phone_value;
	@ViewInject(R.id.zfb_rl)
	private RelativeLayout zfb_rl;
	@ViewInject(R.id.zfbname_rl)
	private RelativeLayout zfbname_rl;
	/**
	 * 我的邀请码
	 */
	@ViewInject(R.id.invite_rl)
	private RelativeLayout invite_rl;
	/**
	 * 手机号
	 */
	private String mPhoneNumber;
	/**
	 * 昵称的RelativeLayout
	 */
	@ViewInject(R.id.name_rl)
	private RelativeLayout name_rl;
	@ViewInject(R.id.name_value)
	private TextView name_value;
	@ViewInject(R.id.dlmm_rl)
	private RelativeLayout dlmm_rl;
	@ViewInject(R.id.shdz_rl)
	private RelativeLayout shdz_rl;
	@ViewInject(R.id.logout_tv)
	private TextView logout_tv;
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

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("我的帐户");

	}

	@Event({ R.id.back_rl, R.id.invite_rl, R.id.name_rl, R.id.zfb_rl,
			R.id.zfbname_rl, R.id.dlmm_rl, R.id.shdz_rl, R.id.logout_tv })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		
		// 点击昵称
		case R.id.name_rl:
			Intent editNameActivity = new Intent(mContext,
					EditNameActivity.class);
			startActivity(editNameActivity);
			break;
		// 点击登陆密码
		case R.id.dlmm_rl:
			Intent editPasswordActivity = new Intent(mContext,
					EditPasswordActivity.class);
			startActivity(editPasswordActivity);
			break;
		// 点击管理收货地址
		case R.id.shdz_rl:
			Intent manageAddress = new Intent(mContext, GuanLiShouHuoDiZhi.class);
			startActivity(manageAddress);

			break;
		// 点击注销
		case R.id.logout_tv:
			ManagerUtils.getInstance().clearData(mContext);
			logout();
			finish();
			break;
		// 点击返回
		case R.id.back_rl:
			finish();
			break;

		}

	}
	/**
	 * 退出淘宝账号的登录
	 */
	public void logout() {
	    LoginService loginService = AlibabaSDK.getService(LoginService.class);
	    loginService.logout(this, new LogoutCallback() {
	 
	        @Override
	        public void onFailure(int code, String msg) {
	 
	        }
	 
	        @Override
	        public void onSuccess() {
	        }
	    });
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		mPhoneNumber = ManagerUtils.getInstance().getPhoneNum(mContext);
		if (mPhoneNumber != null) {
			phone_value.setText(mPhoneNumber);
		}

		// 获取支付宝
		UserExec.getInstance().getUserAlipayInfo(mHandler,
				ManagerUtils.getInstance().getUser_id(mContext),
				NetworkAsyncCommonDefines.GET_USER_ZFB_S,
				NetworkAsyncCommonDefines.GET_USER_ZFB_F);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (name_value != null
				&& ManagerUtils.getInstance().getUserName(mContext) != null) {
			name_value
					.setText(ManagerUtils.getInstance().getUserName(mContext));
		}

	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			default:
				break;
			}
		}

	};
}
