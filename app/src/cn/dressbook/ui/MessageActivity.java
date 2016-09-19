package cn.dressbook.ui;

import org.xutils.x;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @description: 消息中心
 * @author:袁东华
 * @time:2015-10-23下午6:13:44
 */
@ContentView(R.layout.yifen_layout)
public class MessageActivity extends FragmentActivity {
	private Context mContext = MessageActivity.this;
	/**
	 * 系统消息
	 */
	@ViewInject(R.id.xtxx_rl)
	private RelativeLayout xtxx_rl;
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		x.view().inject(this);
		initView();
	}

	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("消息中心");
	}

	protected void initData() {
		// TODO Auto-generated method stub

	}

	@Event({ R.id.xtxx_rl, R.id.back_rl })
	private void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_rl:
			finish();
			break;
		// 点击系统消息
		case R.id.xtxx_rl:
			Intent xtxx = new Intent(mContext, SystemMessageActivity.class);
			startActivity(xtxx);
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
