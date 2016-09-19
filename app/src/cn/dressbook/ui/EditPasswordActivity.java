package cn.dressbook.ui;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.DressBookApp;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.net.UserExec;
import cn.dressbook.ui.utils.MD5Utils;

/**
 * @description: 修改密码
 * @author:袁东华
 * @time:2015-8-31下午3:15:47
 */
@ContentView(R.layout.editpassword_layout)
public class EditPasswordActivity extends BaseActivity  {
	private Context mContext = EditPasswordActivity.this;
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
	 * 原密码
	 */
	@ViewInject(R.id.old_et)
	private EditText old_et;
	/**
	 * 新密码
	 */
	@ViewInject(R.id.new_et)
	private EditText new_et;
	/**
	 * 保存
	 */
	@ViewInject(R.id.save_tv)
	private TextView save_tv;
	@ViewInject(R.id.other_tv)
	private TextView other_tv;
	private String newPas;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("修改密码");
		other_tv.setText(Html.fromHtml("<u>通过手机验证码修改</u>"));
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	@Event({ R.id.back_rl,R.id.save_tv,R.id.other_tv })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击保存
		case R.id.save_tv:
			if (isFinish()) {

				String oldPas = old_et.getText().toString();
				newPas = new_et.getText().toString();
				if (oldPas == null || "".equals(oldPas)) {
					Toast.makeText(mContext, "请输入原密码", Toast.LENGTH_LONG)
							.show();

				} else if (oldPas.length() < 6) {
					Toast.makeText(mContext, "原密码不能小于6位", Toast.LENGTH_LONG)
							.show();

				} else if (oldPas.length() > 16) {
					Toast.makeText(mContext, "原密码不能大于16位", Toast.LENGTH_SHORT)
							.show();
				} else if (!oldPas.equals(ManagerUtils.getInstance()
						.getPassword(mContext))) {
					Toast.makeText(mContext, "原密码错误", Toast.LENGTH_SHORT)
							.show();
				} else if (newPas == null || "".equals(newPas)) {
					Toast.makeText(mContext, "请输入新密码", Toast.LENGTH_LONG)
							.show();

				} else if (newPas.length() < 6) {
					Toast.makeText(mContext, "新密码不能小于6位", Toast.LENGTH_LONG)
							.show();

				} else if (newPas.length() > 16) {
					Toast.makeText(mContext, "新密码不能大于16位", Toast.LENGTH_SHORT)
							.show();
				} else if (ManagerUtils.getInstance().getUser_id(mContext) == null
						|| ManagerUtils.getInstance().getPhoneNum(mContext) == null
						|| ManagerUtils.getInstance().getPhoneNum(mContext)
								.length() != 11) {
					Toast.makeText(mContext, "请绑定手机号", Toast.LENGTH_SHORT)
							.show();
				} else {
					try {
						String md = MD5Utils.getInstance().getMD5String(newPas);
						pbDialog.show();
						UserExec.getInstance()
								.modifyPassword(
										mContext,
										mHandler,
										ManagerUtils.getInstance().getUser_id(
												mContext),
										ManagerUtils.getInstance().getPhoneNum(
												mContext),
										md,
										NetworkAsyncCommonDefines.MODIFY_PASSWORD_S,
										NetworkAsyncCommonDefines.MODIFY_PASSWORD_F);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
			break;
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		// 点击通过验证码修改
		case R.id.other_tv:
			Intent forgetPasswordActivity = new Intent(mContext,
					ForgetPasswordActivity.class);
			startActivity(forgetPasswordActivity);
			overridePendingTransition(R.anim.back_enter, R.anim.anim_exit);
			finish();
			break;

		}

	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			// 修改密码成功
			case NetworkAsyncCommonDefines.MODIFY_PASSWORD_S:
				pbDialog.dismiss();
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
			default:
				break;
			}
		}

	};

}
