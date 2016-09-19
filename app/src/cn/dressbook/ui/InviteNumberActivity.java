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
import cn.dressbook.ui.net.ShareExec;

/**
 * @description:邀请码界面
 * @author:袁东华
 * @modifier:袁东华
 * @remarks:
 * @2015年7月21日 下午3:18:52
 */
@ContentView(R.layout.invitenumber_layout)
public class InviteNumberActivity extends BaseActivity {
	private Context mContext = InviteNumberActivity.this;
	private String title, url, param, pic, id, mContent;
	/**
	 * 我的邀请码
	 */
	@ViewInject(R.id.myinvite_value)
	private TextView myinvite_value;
	/**
	 * 发送邀请
	 */
	@ViewInject(R.id.send_tv)
	private TextView send_tv;
	/**
	 * 支付宝帐号
	 */
	private String mZFBAccount;

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

	@Event({ R.id.back_rl, R.id.send_tv })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击发送
		case R.id.send_tv:
			shareAll();
			break;
		// 点击返回
		case R.id.back_rl:
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
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("邀请码");
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		myinvite_value.setText(ManagerUtils.getInstance().getUser_id(mContext));
		// 获取分享内容
		ShareExec.getInstance().getShareContent(mHandler, "sqfx_inviter",
				NetworkAsyncCommonDefines.GET_SHARE_S,
				NetworkAsyncCommonDefines.GET_SHARE_F);
	}

	/**
	 * 分享到所有平台
	 */
	protected void shareAll() {
		try {

			Intent intent = new Intent(mContext, MyShareActivity.class);
			intent.putExtra("targeturl", url + "?" + param + "="
					+ ManagerUtils.getInstance().getUser_id(mContext));
			intent.putExtra("content", mContent);
			intent.putExtra("title", title);
			intent.putExtra("pic", pic);
			startActivity(intent);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
