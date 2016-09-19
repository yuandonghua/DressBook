package cn.dressbook.ui;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
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
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.net.UserExec;
import cn.dressbook.ui.utils.ToastUtils;

/**
 * @description: 编辑支付宝账号
 * @author:袁东华
 * @time:2015-8-31下午2:36:45
 */
@ContentView(R.layout.activity_bjzfb)
public class BJZFBActivity extends BaseActivity {
	private Context mContext = BJZFBActivity.this;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	@ViewInject(R.id.zfb_et)
	private EditText zfb_et;
	@ViewInject(R.id.name_et)
	private EditText name_et;
	@ViewInject(R.id.tv_bind)
	private TextView tv_bind;
	@ViewInject(R.id.tv_unbind)
	private TextView tv_unbind;
	private String mZFBAccount, mZFBName;

	@Event({ R.id.back_rl, R.id.tv_bind, R.id.tv_unbind })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		// 点击绑定
		case R.id.tv_bind:
			String name = tv_bind.getText().toString();
			if ("绑定".equals(name)) {

				mZFBAccount = zfb_et.getText().toString();
				mZFBName = name_et.getText().toString();
				if (mZFBAccount == null || "".equals(mZFBAccount)
						|| mZFBName == null || "".equals(mZFBName)) {
					ToastUtils.getInstance().showToast(getApplicationContext(),
							"请输入新支付宝账号或支付宝昵称", 200);

				} else if (mZFBAccount.equals(ManagerUtils.getInstance().yjtc
						.getAlipay())
						&& mZFBName.equals(ManagerUtils.getInstance().yjtc
								.getAlipayName())) {
					ToastUtils.getInstance().showToast(getApplicationContext(),
							"支付宝没有修改", 200);

				} else if (ManagerUtils.getInstance().getUser_id(mContext) == null
						|| "".equals(ManagerUtils.getInstance().getUser_id(
								mContext))
						|| ManagerUtils.getInstance().getPhoneNum(mContext) == null
						|| ManagerUtils.getInstance().getPhoneNum(mContext)
								.length() != 11) {
					ToastUtils.getInstance().showToast(getApplicationContext(),
							"请先绑定手机号", 200);
				} else {
					pbDialog.show();
					UserExec.getInstance().bindZFB(mHandler,
							ManagerUtils.getInstance().getUser_id(mContext),
							ManagerUtils.getInstance().getPhoneNum(mContext),
							mZFBAccount, mZFBName,
							NetworkAsyncCommonDefines.BIND_ZFB_S,
							NetworkAsyncCommonDefines.BIND_ZFB_F);
				}
			} else if ("更换".equals(name)) {
				tv_bind.setText("绑定");
				zfb_et.setEnabled(true);
				zfb_et.setText("");
				name_et.setEnabled(true);
				name_et.setText("");
			}
			break;
		// 点击解绑
		case R.id.tv_unbind:
			pbDialog.show();
			UserExec.getInstance().bindZFB(mHandler,
					ManagerUtils.getInstance().getUser_id(mContext),
					ManagerUtils.getInstance().getPhoneNum(mContext), "", "",
					NetworkAsyncCommonDefines.UNBIND_ZFB_S,
					NetworkAsyncCommonDefines.UNBIND_ZFB_F);

			break;

		}

	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			// 绑定支付宝信息成功
			case NetworkAsyncCommonDefines.BIND_ZFB_S:
				ManagerUtils.getInstance().yjtc.setAlipay(mZFBAccount);
				ManagerUtils.getInstance().yjtc.setAlipayName(mZFBName);

				pbDialog.dismiss();
				Toast.makeText(mContext, "操作成功", Toast.LENGTH_LONG).show();
				finish();
				break;
			// 绑定支付宝信息失败
			case NetworkAsyncCommonDefines.BIND_ZFB_F:
				pbDialog.dismiss();
				Toast.makeText(mContext, "操作失败", Toast.LENGTH_LONG).show();
				break;
			// 解绑支付宝信息失败
			case NetworkAsyncCommonDefines.UNBIND_ZFB_F:
				pbDialog.dismiss();
				Toast.makeText(mContext, "操作失败", Toast.LENGTH_LONG).show();
				break;
			// 解绑支付宝成功
			case NetworkAsyncCommonDefines.UNBIND_ZFB_S:
				pbDialog.dismiss();
				ManagerUtils.getInstance().yjtc.setAlipay("");
				ManagerUtils.getInstance().yjtc.setAlipayName("");
				Toast.makeText(mContext, "操作成功", Toast.LENGTH_LONG).show();
				finish();
				break;

			}
		}

	};

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("绑定支付宝");

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		if (!"".equals(ManagerUtils.getInstance().yjtc.getAlipay())
				&& !"".equals(ManagerUtils.getInstance().yjtc.getAlipayName())) {
			tv_bind.setText("更换");
			zfb_et.setText(ManagerUtils.getInstance().yjtc.getAlipay());
			name_et.setText(ManagerUtils.getInstance().yjtc.getAlipayName());
			zfb_et.setEnabled(false);
			name_et.setEnabled(false);
		} else {
			tv_bind.setText("绑定");
			zfb_et.setEnabled(true);
			name_et.setEnabled(true);
		}

	}

}
