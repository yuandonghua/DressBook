package cn.dressbook.ui;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.net.UserExec;
import cn.dressbook.ui.utils.ToastUtils;

/**
 * @description:用户反馈界面
 * @author:袁东华
 * @modifier:袁东华
 * @remarks:
 * @2015-6-17 下午5:10:32
 */
@ContentView(R.layout.userfeedback_layout)
public class UserFeedbackActivity extends BaseActivity {
	private Context mContext = UserFeedbackActivity.this;
	/**
	 * 内容编辑框
	 */
	@ViewInject(R.id.content)
	private EditText content;
	/**
	 * 用户反馈
	 */
	private String mFeedback;
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
	 * 提交按钮
	 */
	@ViewInject(R.id.submit)
	private TextView submit;

	@Event({ R.id.back_rl,R.id.submit })
	private void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		// 点击提交按钮
		case R.id.submit:
			mFeedback = content.getText().toString();
			if (mFeedback != null) {
				mFeedback.trim().replaceAll("\\s", "");
				mFeedback.replaceAll("\t", "");
				mFeedback.replaceAll("\r", "");
				mFeedback.replaceAll("\n", "");
			}
			if (mFeedback != null && !mFeedback.equals("")) {
				if (ManagerUtils.getInstance().getUser_id(mContext) != null) {

					pbDialog.show();
					UserExec.getInstance().submitUserFeedback(mHandler,
							ManagerUtils.getInstance().getUser_id(mContext),
							mFeedback,
							NetworkAsyncCommonDefines.SUBMIT_USERFEEDBACK_S,
							NetworkAsyncCommonDefines.SUBMIT_USERFEEDBACK_F);
				} else {
					Toast.makeText(mContext, "请先去主页我穿中创建形象", Toast.LENGTH_SHORT)
							.show();
				}

			} else {
				Toast.makeText(mContext, "请输入内容", Toast.LENGTH_LONG).show();
			}
			break;
		// 返回
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
			// 提交用户反馈成功
			case NetworkAsyncCommonDefines.SUBMIT_USERFEEDBACK_S:

				pbDialog.dismiss();
				Toast.makeText(mContext, "反馈提交成功,谢谢!", Toast.LENGTH_LONG)
						.show();
				finish();
				break;
			// 提交用户反馈失败
			case NetworkAsyncCommonDefines.SUBMIT_USERFEEDBACK_F:
				pbDialog.dismiss();
				Bundle data = msg.getData();
				if (data != null) {
					int code = data.getInt("code");
					switch (code) {
					case 0:
						ToastUtils.getInstance().showToast(mContext,
								"请不要输入表情或特殊字符", 0);
						break;
					case -1:
						ToastUtils.getInstance().showToast(mContext,
								"网络异常,请检查网络", 0);
						break;
					case -2:
						ToastUtils.getInstance().showToast(mContext,
								"返回数据异常,请稍后重试", 0);
						break;
					case -3:
						ToastUtils.getInstance().showToast(mContext,
								"返回数据为空,请稍后重试", 0);
						break;
					default:
						Toast.makeText(mContext, "反馈提交失败", Toast.LENGTH_LONG)
								.show();
						break;
					}
				}
				break;

			}

		}

	};


	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		content = (EditText) findViewById(R.id.content);
		title_tv.setText("用户反馈");
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		title_tv.setText("用户反馈");
	}

}
