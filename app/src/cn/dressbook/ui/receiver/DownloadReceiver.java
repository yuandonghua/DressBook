package cn.dressbook.ui.receiver;

import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.service.DownLoadingService;
import cn.dressbook.ui.service.ImageProcessingService;
import cn.dressbook.ui.utils.HelperUtils;


public class DownloadReceiver extends BroadcastReceiver {
	private String actionString = "android.intent.action.BOOT_COMPLETED";
	private Context context;

	@Override
	public void onReceive(final Context arg0, Intent intent) {
		// TODO Auto-generated method stub
		context = arg0;
		if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
			Intent downServiceIntent = new Intent(context,
					DownLoadingService.class);
			downServiceIntent.putExtra("id", 1);
			context.startService(downServiceIntent);
		}
		// 这个监听wifi的连接状态即是否连上了一个有效无线路由，当上边广播的状态是WifiManager.WIFI_STATE_DISABLING，和WIFI_STATE_DISABLED的时候，根本不会接到这个广播。
		// 在上边广播接到广播是WifiManager.WIFI_STATE_ENABLED状态的同时也会接到这个广播，当然刚打开wifi肯定还没有连接到有效的无线
		if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
			Parcelable parcelableExtra = intent
					.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
			if (null != parcelableExtra) {
				NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
				State state = networkInfo.getState();
				boolean isConnected = state == State.CONNECTED;// 当然，这边可以更精确的确定状态
				if (isConnected && isBackgroundRunning(context)) {

					// 检查下载更新数据
					Intent downServiceIntent = new Intent(context,
							DownLoadingService.class);
					downServiceIntent.putExtra("id", 1);
					context.startService(downServiceIntent);
					Timer timer = new Timer();
					timer.schedule(new TimerTask() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							// 开始预处理用户形象数据
							if (ManagerUtils.getInstance().getUser_id(context) != null) {
								Intent ycl = new Intent(context,
										ImageProcessingService.class);
								ycl.putExtra("id", -1);
								context.startService(ycl);
							}
						}
					}, 10 * 1000);
				} else {
				}
			} else {
			}
		} else {
			// LogUtils.e("没有收到android.net.wifi.STATE_CHANGE广播---------------------");
		}
		// 离开应用
		if ("leaveCYD".equals(intent.getAction())
				&& HelperUtils.isConnectWIFI(context)) {
			// 检查下载更新数据
			Intent downServiceIntent = new Intent(context,
					DownLoadingService.class);
			downServiceIntent.putExtra("id", 1);
			context.startService(downServiceIntent);
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					// 开始预处理用户形象数据
					if (ManagerUtils.getInstance().getUser_id(context) != null) {
						Intent ycl = new Intent(context,
								ImageProcessingService.class);
						ycl.putExtra("id", -1);
						context.startService(ycl);
					}
				}
			}, 10 * 1000);

		}
	}

	/**
	 * 检查是否后台运行
	 * 
	 * @return
	 */
	private boolean isBackgroundRunning(Context context) {
		String processName = "cn.dressbook.ui";

		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		KeyguardManager keyguardManager = (KeyguardManager) context
				.getSystemService(Context.KEYGUARD_SERVICE);

		if (activityManager == null)
			return false;
		// get running application processes
		List<ActivityManager.RunningAppProcessInfo> processList = activityManager
				.getRunningAppProcesses();
		for (ActivityManager.RunningAppProcessInfo process : processList) {
			if (process.processName.startsWith(processName)) {
				boolean isBackground = process.importance != IMPORTANCE_FOREGROUND
						&& process.importance != IMPORTANCE_VISIBLE;
				boolean isLockedState = keyguardManager
						.inKeyguardRestrictedInputMode();
				if (isBackground || isLockedState)
					return true;
				else
					return false;
			}
		}
		return false;
	}

}
