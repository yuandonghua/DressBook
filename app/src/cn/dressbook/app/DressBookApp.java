package cn.dressbook.app;


import java.util.HashMap;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.model.CustomService;
import cn.dressbook.ui.service.MyPushIntentService;
import cn.dressbook.ui.utils.FileSDCacher;
import cn.dressbook.ui.utils.SharedPreferenceUtils;

import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.callback.InitResultCallback;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.IUmengUnregisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;

import org.xutils.x;

/**
 * @description: 应用全局管理类
 * @author:袁东华
 * @time:2015-11-2上午11:54:36
 */
public class DressBookApp extends Application {
	private static DressBookApp mMainApplication;
	private SharedPreferenceUtils mSharedPreferenceUtils = SharedPreferenceUtils
			.getInstance();
	private Context mContext = DressBookApp.this;
	private String mSheBeiHao;
	// 开启的下载任务
	private HashMap<String, Thread> mThreads;
	// 融云连接状态
	private boolean rongYun;
	private PushAgent mPushAgent;

	public static final String CALLBACK_RECEIVER_ACTION = "callback_receiver_action";

	public static IUmengRegisterCallback mRegisterCallback;

	public static IUmengUnregisterCallback mUnregisterCallback;
	@Override
	public void onCreate() {
		super.onCreate();
//		dexTool();
		mMainApplication = this;
		x.Ext.init(this);
		// 是否输出debug日志
		x.Ext.setDebug(true);
		initTaoBao();
		mPushAgent = PushAgent.getInstance(this);
		mPushAgent.setDebugMode(true);

	}
	 /**
     * Copy the following code and call dexTool() after super.onCreate() in
     * Application.onCreate()
     * <p>
     * This method hacks the default PathClassLoader and load the secondary dex
     * file as it's parent.
     */
//    @SuppressLint("NewApi")
//    private void dexTool() {
//
//        File dexDir = new File(getFilesDir(), "dlibs");
//        dexDir.mkdir();
//        File dexFile = new File(dexDir, "libs.apk");
//        File dexOpt = new File(dexDir, "opt");
//        dexOpt.mkdir();
//        try {
//            InputStream ins = getAssets().open("libs.apk");
//            if (dexFile.length() != ins.available()) {
//                FileOutputStream fos = new FileOutputStream(dexFile);
//                byte[] buf = new byte[4096];
//                int l;
//                while ((l = ins.read(buf)) != -1) {
//                    fos.write(buf, 0, l);
//                }
//                fos.close();
//            }
//            ins.close();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        ClassLoader cl = getClassLoader();
//        ApplicationInfo ai = getApplicationInfo();
//        String nativeLibraryDir = null;
//        if (Build.VERSION.SDK_INT > 8) {
//            nativeLibraryDir = ai.nativeLibraryDir;
//        } else {
//            nativeLibraryDir = "/data/data/" + ai.packageName + "/lib/";
//        }
//        DexClassLoader dcl = new DexClassLoader(dexFile.getAbsolutePath(),
//                dexOpt.getAbsolutePath(), nativeLibraryDir, cl.getParent());
//
//        try {
//            Field f = ClassLoader.class.getDeclaredField("parent");
//            f.setAccessible(true);
//            f.set(cl, dcl);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
	/**
	 * 正在沟通的客服
	 */
	private CustomService mCustomService;

	public CustomService getCustomService() {
		return mCustomService;
	}

	public void setCustomService(CustomService mCustomService) {
		this.mCustomService = mCustomService;
	}

	public boolean getRongYun() {
		return rongYun;
	}

	public void setRongYun(boolean rongYun) {
		this.rongYun = rongYun;
	}

	private boolean isRun = true;

	public void setIsRun(boolean isRun) {
		this.isRun = isRun;
	}

	public boolean getIsRun() {
		return isRun;
	}

	/**
	 * @return the mThreads
	 */
	public HashMap<String, Thread> getThreads() {
		if (mThreads == null) {
			mThreads = new HashMap<String, Thread>();
		}
		return mThreads;
	}

	public void setThreads(HashMap<String, Thread> mThreads) {
		this.mThreads = mThreads;
	}

	/**
	 * @description 获取设备号
	 * @parameters
	 */
	public String getSheBeiHao() {
		if (mSheBeiHao == null || "".equals(mSheBeiHao)
				|| mSheBeiHao.equals("null")) {
			mSheBeiHao = mSharedPreferenceUtils.getSheBeiHao(mContext);
			if (mSheBeiHao == null || "".equals(mSheBeiHao)
					|| mSheBeiHao.equals("null")) {
				if (PushAgent.getInstance(mContext).isEnabled()) {
					mSheBeiHao = UmengRegistrar.getRegistrationId(mContext);
					if (mSheBeiHao == null || "".equals(mSheBeiHao)
							|| mSheBeiHao.equals("null")) {
						mHandler.postDelayed(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								getSheBeiHao();

							}
						}, 1000 * 10);
					} else {
						setSheBeiHao(mSheBeiHao);
					}
				} else {
					// 友盟推送
					PushAgent.getInstance(mContext).onAppStart();
					PushAgent mPushAgent = PushAgent.getInstance(mContext);
					mPushAgent
							.setPushIntentServiceClass(MyPushIntentService.class);
					mPushAgent.enable();
					mSheBeiHao = UmengRegistrar.getRegistrationId(mContext);
					if (mSheBeiHao == null || "".equals(mSheBeiHao)
							|| mSheBeiHao.equals("null")) {
						mHandler.postDelayed(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								getSheBeiHao();

							}
						}, 1000 * 10);
					} else {
						setSheBeiHao(mSheBeiHao);
					}
				}
			}
		}
		return mSheBeiHao;
	}

	public void setSheBeiHao(String sheBeiHao) {
		this.mSheBeiHao = sheBeiHao;
		mSharedPreferenceUtils.setSheBeiHao(mContext, mSheBeiHao);
		FileSDCacher.createFile2(mHandler, mSheBeiHao,
				PathCommonDefines.JSON_FOLDER, "shebeihao.txt", 100);
	}

	public static DressBookApp getInstance() {
		return mMainApplication;
	}

	

	private void initTaoBao() {
		// TODO Auto-generated method stub
		AlibabaSDK.asyncInit(this, new InitResultCallback() {

			@Override
			public void onSuccess() {
			}

			@Override
			public void onFailure(int code, String message) {
			}

		});
	}



	public String getCurProcessName(Context context) {
		try {

			int pid = android.os.Process.myPid();
			ActivityManager activityManager = (ActivityManager) context
					.getSystemService(Activity.ACTIVITY_SERVICE);
			for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
					.getRunningAppProcesses()) {
				if (appProcess.pid == pid) {
					return appProcess.processName;
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	private String mToken;

	public void setToken(String mToken2) {
		// TODO Auto-generated method stub
		mToken = mToken2;
		mSharedPreferenceUtils.setToken(mContext, mToken);
	}

	public String getToken() {
		if (mToken == null) {
			mToken = mSharedPreferenceUtils.getToken(mContext);
		}
		return mToken;
	}

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			// 获取token失败
			case NetworkAsyncCommonDefines.GET_TOKEN_F:
				mToken = null;
				break;
			// 获取token的返回
			case NetworkAsyncCommonDefines.GET_TOKEN_S:
				Bundle tokenBun = msg.getData();
				if (tokenBun != null) {
					mToken = tokenBun.getString("token");
					// 此处直接 hardcode 给 token 赋值，请替换为您自己的 Token。
					// 连接融云服务器。
					if (mToken != null) {

						try {
							setToken(mToken);

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				break;
			}

		}

	};

}