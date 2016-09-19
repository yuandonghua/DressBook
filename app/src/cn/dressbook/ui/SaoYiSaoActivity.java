package cn.dressbook.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import com.google.zxing.Result;

import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.listener.SaoYiSaoResultListener;
import cn.dressbook.ui.net.UserExec;
import cn.dressbook.ui.zxing.camera.CameraManager;
import cn.dressbook.ui.zxing.decode.DecodeThread;
import cn.dressbook.ui.zxing.utils.BeepManager;
import cn.dressbook.ui.zxing.utils.CaptureActivityHandler;
import cn.dressbook.ui.zxing.utils.InactivityTimer;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * @description: 扫一扫界面
 * @author: 袁东华
 * @time: 2015-8-6下午2:57:18
 */
@ContentView(R.layout.saoyisao)
public class SaoYiSaoActivity extends BaseActivity implements
		SurfaceHolder.Callback {
	private CameraManager cameraManager;
	private CaptureActivityHandler handler;
	private InactivityTimer inactivityTimer;
	private BeepManager beepManager;

	private SurfaceView scanPreview = null;
	private RelativeLayout scanContainer;
	private RelativeLayout scanCropView;
	private ImageView scanLine;
	private TextView getqr;
	private Rect mCropRect = null;
	private String flag;

	public Handler getHandler() {
		return handler;
	}

	public CameraManager getCameraManager() {
		return cameraManager;
	}

	private boolean isHasSurface = false;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		scanPreview = (SurfaceView) findViewById(R.id.capture_preview);
		scanContainer = (RelativeLayout) findViewById(R.id.capture_container);
		scanCropView = (RelativeLayout) findViewById(R.id.capture_crop_view);
		scanLine = (ImageView) findViewById(R.id.capture_scan_line);

	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		flag = getIntent().getStringExtra("flag");
		inactivityTimer = new InactivityTimer(this);
		beepManager = new BeepManager(this);

		TranslateAnimation animation = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.9f);
		animation.setDuration(4500);
		animation.setRepeatCount(-1);
		animation.setRepeatMode(Animation.RESTART);
		scanLine.startAnimation(animation);
	}

	@Override
	protected void onResume() {
		super.onResume();

		cameraManager = new CameraManager(getApplication());

		handler = null;

		if (isHasSurface) {
			initCamera(scanPreview.getHolder());
		} else {
			scanPreview.getHolder().addCallback(this);
		}

		inactivityTimer.onResume();
	}

	@Override
	protected void onPause() {
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		inactivityTimer.onPause();
		beepManager.close();
		cameraManager.closeDriver();
		if (!isHasSurface) {
			scanPreview.getHolder().removeCallback(this);
		}
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (holder == null) {
		}
		if (!isHasSurface) {
			isHasSurface = true;
			initCamera(holder);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		isHasSurface = false;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	/**
	 * 扫描结果
	 * 
	 * @param rawResult
	 * @param bundle
	 */
	public void handleDecode(Result rawResult, Bundle bundle) {
		try {

			String result = rawResult.getText();
			LogUtil.e("result:" + result);
			// 扫描消费码
			if (result.contains("o_")) {

				if ("zhuye".equals(flag)) {
					Intent sys = new Intent(activity, DJSQActivity.class);
					sys.putExtra("xfm", result);
					startActivity(sys);
				} else {
					if (mSaoYiSaoResultListener != null) {
						mSaoYiSaoResultListener.onResult(result);
					}
				}
				pbDialog.dismiss();
				finish();
			} else if (result.contains("u_")) {
				// 扫描邀请码
				if ("zhuye".equals(flag)) {
					UserExec.getInstance().saoYQM(mHandler,
							ManagerUtils.getInstance().getUser_id(activity),
							result, 1, -1);
				} else {
					if (mSaoYiSaoResultListener != null) {
						mSaoYiSaoResultListener.onResult(result);
					}
					pbDialog.dismiss();
					finish();
				}
			}

		} catch (Exception e) {
		}

	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				Bundle data = msg.getData();
				if (data != null) {
					String redesc = data.getString("redesc");
					Toast.makeText(activity, redesc, Toast.LENGTH_SHORT).show();
				}
				pbDialog.dismiss();
				finish();
				break;
			case -1:
				Toast.makeText(activity, "服务器异常", Toast.LENGTH_SHORT).show();
				pbDialog.dismiss();
				finish();
				break;
			default:
				break;
			}
		}

	};
	private static SaoYiSaoResultListener mSaoYiSaoResultListener;

	public static void setSaoYiSaoResultListener(SaoYiSaoResultListener listener) {
		mSaoYiSaoResultListener = listener;
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		if (surfaceHolder == null) {
			throw new IllegalStateException("No SurfaceHolder provided");
		}
		if (cameraManager.isOpen()) {
			return;
		}
		try {
			cameraManager.openDriver(surfaceHolder);
			// Creating the handler starts the preview, which can also throw a
			// RuntimeException.
			if (handler == null) {
				handler = new CaptureActivityHandler(this, cameraManager,
						DecodeThread.ALL_MODE);
			}

			initCrop();
		} catch (IOException ioe) {
			displayFrameworkBugMessageAndExit();
		} catch (RuntimeException e) {
			// Barcode Scanner has seen crashes in the wild of this variety:
			// java.?lang.?RuntimeException: Fail to connect to camera service
			displayFrameworkBugMessageAndExit();
		}
	}

	private void displayFrameworkBugMessageAndExit() {
		// camera error
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.app_name));
		builder.setMessage("相机打开出错，请稍后重试");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}

		});
		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				finish();
			}
		});
		builder.show();
	}

	public void restartPreviewAfterDelay(long delayMS) {
		if (handler != null) {
			handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
		}
	}

	public Rect getCropRect() {
		return mCropRect;
	}

	/**
	 * 初始化截取的矩形区域
	 */
	private void initCrop() {
		int cameraWidth = cameraManager.getCameraResolution().y;
		int cameraHeight = cameraManager.getCameraResolution().x;

		/** 获取布局中扫描框的位置信息 */
		int[] location = new int[2];
		scanCropView.getLocationInWindow(location);

		int cropLeft = location[0];
		int cropTop = location[1] - getStatusBarHeight();

		int cropWidth = scanCropView.getWidth();
		int cropHeight = scanCropView.getHeight();

		/** 获取布局容器的宽高 */
		int containerWidth = scanContainer.getWidth();
		int containerHeight = scanContainer.getHeight();

		/** 计算最终截取的矩形的左上角顶点x坐标 */
		int x = cropLeft * cameraWidth / containerWidth;
		/** 计算最终截取的矩形的左上角顶点y坐标 */
		int y = cropTop * cameraHeight / containerHeight;

		/** 计算最终截取的矩形的宽度 */
		int width = cropWidth * cameraWidth / containerWidth;
		/** 计算最终截取的矩形的高度 */
		int height = cropHeight * cameraHeight / containerHeight;

		/** 生成最终的截取的矩形 */
		mCropRect = new Rect(x, y, width + x, height + y);
	}

	private int getStatusBarHeight() {
		try {
			Class<?> c = Class.forName("com.android.internal.R$dimen");
			Object obj = c.newInstance();
			Field field = c.getField("status_bar_height");
			int x = Integer.parseInt(field.get(obj).toString());
			return getResources().getDimensionPixelSize(x);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Event({ R.id.back_rl })
	private void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_rl:
			finish();
			break;

		default:
			break;
		}
	}
}