package cn.dressbook.ui.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;

public class DialogUtil {

	// 判断是否有网络 true 有网络
	private boolean flag = false;

	private static DialogUtil mInstance = null;
	private static Context mContext;

	private DialogUtil(Context context) {
		mContext = context;
	}

	public static synchronized DialogUtil getInstance(Context context) {

		if (mInstance == null) {
			synchronized (DialogUtil.class) {
				if (mInstance == null) {
					mInstance = new DialogUtil(context);
				}
			}
		}
		return mInstance;
	}

	/**
	 * 开启网络设置
	 */
	public void openNetworkSettings(final Context context,
			final Handler handler, final int flag1, final int flag2) {

		AlertDialog dialog = new AlertDialog.Builder(context)
				.setTitle("开启网络服务")
				.setMessage("您的网络不给力，请检查一下您的网络，是否开启网络？")
				.setPositiveButton("设置", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 无网络
						if (!HelperUtils.isConnect(context)) {
							context.startActivity(new Intent(
									Settings.ACTION_WIRELESS_SETTINGS));
							dialog.cancel();
						} else {
							handler.sendEmptyMessage(flag1);
							dialog.cancel();
						}

					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						handler.sendEmptyMessage(flag2);
						dialog.cancel();
					}
				}).show();
		dialog.setCancelable(false);
	}

}
