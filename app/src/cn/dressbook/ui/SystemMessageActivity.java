package cn.dressbook.ui;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.adapter.SystemMessageAdapter;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.model.CydMessage;
import cn.dressbook.ui.net.CydMessageExec;
import cn.dressbook.ui.utils.FileSDCacher;
import cn.dressbook.ui.utils.SharedPreferenceUtils;

/**
 * @description: 系统消息
 * @author:袁东华
 * @time:2015-12-1下午2:54:40
 */
@SuppressLint("NewApi")
@ContentView(R.layout.xiaoxizz_layout)
public class SystemMessageActivity extends BaseActivity {
	private Context mContext = SystemMessageActivity.this;
	public ArrayList<CydMessage> msgList1 = new ArrayList<CydMessage>();
	private SystemMessageAdapter mAdviserChatAdapter;
	/**
	 * 系统通知的ListView
	 */
	@ViewInject(R.id.xxzx_lv)
	private ListView xxzx_lv;
	@ViewInject(R.id.hint_tv)
	private TextView hint_tv;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	@ViewInject(R.id.operate_tv)
	private TextView operate_tv;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("系统消息");
		xxzx_lv = (ListView) findViewById(R.id.xxzx_lv);
		operate_tv.setText("清除");
		operate_tv.setVisibility(View.VISIBLE);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		mAdviserChatAdapter = new SystemMessageAdapter(mContext, msgList1);
		xxzx_lv.setAdapter(mAdviserChatAdapter);
		File folder = new File(PathCommonDefines.MESSAGE);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		pbDialog.show();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);
		LogUtil.e("key:" + key);
		mSharedPreferenceUtils.setXXTime(activity, key);
		ManagerUtils.getInstance().setXxSize(0);
		// 获取系统消息
		CydMessageExec.getInstance().getMessage(mHandler,
				ManagerUtils.getInstance().getUser_id(mContext),
				NetworkAsyncCommonDefines.GET_SYSTEM_FROM_SD_S,
				NetworkAsyncCommonDefines.GET_SYSTEM_FROM_SD_F);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	private SharedPreferenceUtils mSharedPreferenceUtils = SharedPreferenceUtils
			.getInstance();
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			// 从SD卡获取系统消息成功
			case NetworkAsyncCommonDefines.GET_SYSTEM_FROM_SD_S:
				Bundle messageBun = msg.getData();
				if (messageBun != null) {
					msgList1 = messageBun.getParcelableArrayList("messageList");
					if (msgList1 != null) {
						Collections.reverse(msgList1);
						mAdviserChatAdapter = new SystemMessageAdapter(
								mContext, msgList1);
						xxzx_lv.setAdapter(mAdviserChatAdapter);
						
						hint_tv.setVisibility(View.GONE);
					} else {
						LogUtil.e("系统消息为空------------------");
						hint_tv.setVisibility(View.VISIBLE);
					}
				}

				pbDialog.dismiss();
				break;
			// 从SD卡获取系统消息失败
			case NetworkAsyncCommonDefines.GET_SYSTEM_FROM_SD_F:
				hint_tv.setVisibility(View.VISIBLE);
				pbDialog.dismiss();
				break;
			}
		}

	};

	@Event({ R.id.back_rl, R.id.operate_tv })
	private void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		// 点击清除
		case R.id.operate_tv:
			pbDialog.show();
			File folder=new File(PathCommonDefines.MESSAGE);
			FileSDCacher.deleteDirectory2(folder);
			msgList1.clear();
			mAdviserChatAdapter.notifyDataSetChanged();
			hint_tv.setVisibility(View.VISIBLE);
			pbDialog.dismiss();
			break;
		// 返回
		case R.id.back_rl:
			finish();

			break;
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
	}

}