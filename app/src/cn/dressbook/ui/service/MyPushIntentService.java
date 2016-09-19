package cn.dressbook.ui.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import java.util.Locale;

import org.android.agoo.client.BaseConstants;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.widget.RemoteViews;
import cn.dressbook.ui.R;
import cn.dressbook.ui.SystemMessageActivity;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.receiver.DownloadReceiver;
import cn.dressbook.ui.utils.FileSDCacher;

import com.umeng.message.UTrack;
import com.umeng.message.UmengBaseIntentService;
import com.umeng.message.entity.UMessage;

/**
 * @description:消息推送处理
 * @author:袁东华
 * @time:2015-11-18下午4:18:31
 */
public class MyPushIntentService extends UmengBaseIntentService {

//	private DownloadReceiver mDownloadReceiver;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
//		mDownloadReceiver = new DownloadReceiver();
//		IntentFilter filter = new IntentFilter();
//		filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
//		filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
//		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//		registerReceiver(mDownloadReceiver, filter);
//		// 友盟消息服务创建的时候通知后台处理
//		Intent likai = new Intent();
//		likai.setAction("likaicyd");
//		sendBroadcast(likai);

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		unregisterReceiver(mDownloadReceiver);
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onMessage(context, intent);
		try {
			String message = intent.getStringExtra(BaseConstants.MESSAGE_BODY);
			File messageFolder = new File(PathCommonDefines.MESSAGE);
			if (!messageFolder.exists()) {
				messageFolder.mkdirs();
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss",
					Locale.getDefault());
			Date date = new Date();
			String key = format.format(date);
			String suffix = key + ".txt";
			Calendar c = Calendar.getInstance();
			int mYear = c.get(Calendar.YEAR);
			int mMonth = c.get(Calendar.MONTH) + 1;
			int mDay = c.get(Calendar.DAY_OF_MONTH);
			int mHour = c.get(Calendar.HOUR_OF_DAY);
			int mMinu = c.get(Calendar.MINUTE);
			String timeString = mYear + "-" + mMonth + "-" + mDay + " " + mHour
					+ ":" + mMinu;
//			message.replace("\"穿衣典\"", "\"" + timeString + "\"");
			UMessage msg = new UMessage(new JSONObject(message));
			UTrack.getInstance(context).trackMsgClick(msg);
			HashMap<String, String> map = (HashMap<String, String>) msg.extra;
			JSONObject json = new JSONObject();
			json.put("fileName", suffix);
			json.put("time", timeString);
			json.put("title", msg.title);
			json.put("text", msg.text);
			json.put("type", map.get("cyd_type"));
			LogUtil.e("msg.title:"+msg.title);
			LogUtil.e("msg.text:"+msg.text);
			LogUtil.e("msg.get(cyd_type):"+map.get("cyd_type"));
			LogUtil.e("timeString:"+timeString);
			LogUtil.e("suffix:"+suffix);
			FileSDCacher.createFile(mHandler, json.toString(),
					PathCommonDefines.MESSAGE, suffix,
					NetworkAsyncCommonDefines.SAVE_MESSAGE_S,
					NetworkAsyncCommonDefines.SAVE_MESSAGE_F);

			setMsgNotification(timeString, map.get("cyd_type"), msg.title,
					msg.ticker, msg.text, map.get("cyd_state"));
			
			
			

		} catch (Exception e) {
		}
	}

	private void setMsgNotification(String time, String type, String title,
			String ticker, String text, String state) {

		if (type != null && type.equals("2")) {
			// 穿衣典的系统通知
			initNotification(type, title, ticker, text, state);
		}

	}

	private void initNotification(String type, String title, String ticker,
			String text, String state) {
		// TODO Auto-generated method stub
		int icon = R.drawable.logo6; // 窗口通知栏的图标
		long when = System.currentTimeMillis();
		NotificationManager nm = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification mNotification = new Notification(icon, text, when); // 创建通知栏实例
		RemoteViews contentView = new RemoteViews(getPackageName(),
				R.layout.notification_layout);
		mNotification.contentView = contentView;
		contentView.setTextViewText(R.id.title, title);
		contentView.setTextViewText(R.id.text, text);
		Intent intent;
		// 消息中心
		intent = new Intent(this, SystemMessageActivity.class);
		if (type != null && type.equals("1")) {

		} else if (type != null && type.equals("2")) {
		}

		PendingIntent contentIntent = PendingIntent.getActivity(this, 100,
				intent, 0);
		mNotification.contentIntent = contentIntent;// 点击的事件
		mNotification.flags = Notification.FLAG_AUTO_CANCEL;// 点击通知之后自动消失
		nm.notify(0, mNotification);
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			// 消息保存成功
			case NetworkAsyncCommonDefines.SAVE_MESSAGE_S:
				break;
			// 消息保存失败
			case NetworkAsyncCommonDefines.SAVE_MESSAGE_F:
				break;
			default:
				break;
			}
		}

	};
}
