package cn.dressbook.ui;

import org.xutils.x;
import org.xutils.view.annotation.ContentView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.AudioManager;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Toast;
import cn.dressbook.ui.dialog.ProgressDialog;
import cn.dressbook.ui.listener.OptionListener;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

/**
 * @description: 基础fragmentactivity
 * @author:袁东华
 * @time:2015-11-2下午5:14:41
 */
@SuppressLint("NewApi")
public abstract class BaseFragmentActivity extends AppCompatActivity {
	private static final int TIME = 30000;
	public ProgressDialog pbDialog;
	public Activity activity;

	@Override
	protected void onCreate(@Nullable Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		activity = this;
		MobclickAgent.setSessionContinueMillis(TIME);
		PushAgent.getInstance(this).onAppStart();
		setVolumeControlStream(AudioManager.STREAM_MUSIC);// 使得音量键控制媒体声音
		x.view().inject(this);
		pbDialog = createProgressBar();
		initView();

		try {
			initData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

	protected <T extends View> T getViewById(int id) {
		return (T) findViewById(id);
	}

	/**
	 * @description:初始化组件
	 * @exception
	 */
	protected abstract void initView();

	/**
	 * @throws Exception
	 * @description:初始化数据
	 * @exception
	 */
	protected abstract void initData() throws Exception;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if (isFinish()) {
				finish();
			} else {
				Toast.makeText(this, "正在处理,请稍后", Toast.LENGTH_SHORT).show();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private ProgressDialog createProgressBar() {
		if (pbDialog == null) {
			pbDialog = new ProgressDialog(this);
		}

		return pbDialog;
	}

	public boolean isFinish() {
		return !pbDialog.isShowing();
	}
}
