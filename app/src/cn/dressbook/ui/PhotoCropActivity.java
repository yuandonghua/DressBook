package cn.dressbook.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.imgproc.Imgproc;
import org.opencv.samples.facedetect.DetectionBasedTracker;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.ui.SnowCommon.common.ButtonHighlighterOnTouchListener;
import cn.dressbook.ui.SnowCommon.common.RuntimeCache;
import cn.dressbook.ui.SnowCommon.common.util.CameraHelper;
import cn.dressbook.ui.SnowCommon.common.util.MediaHelper;
import cn.dressbook.ui.SnowCommon.common.util.NativeImageLoader;
import cn.dressbook.ui.SnowCommon.view.dialog.ProcessingDialog;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.dialog.ManyImageHintDialog;
import cn.dressbook.ui.face.data.SingletonDataMgr;
import cn.dressbook.ui.face.data.UserPhotoData;
import cn.dressbook.ui.face.view.BodyImageView;
import cn.dressbook.ui.face.view.FCGestureView;
import cn.dressbook.ui.face.view.IGestureViewIml;
import cn.dressbook.ui.utils.ImageUtils;
import cn.dressbook.ui.utils.ScreenUtil;
import cn.dressbook.ui.utils.SharedPreferenceUtils;

/**
 * @description: 图片调试界面
 * @author:袁东华
 * @time:2015-11-2下午3:47:33
 */
@ContentView(R.layout.activity_photo_crop)
public class PhotoCropActivity extends BaseActivity implements OnTouchListener,
		OnSeekBarChangeListener, IGestureViewIml {
	private Context mContext = PhotoCropActivity.this;
	private SharedPreferenceUtils mSharedPreferenceUtils = SharedPreferenceUtils
			.getInstance();
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
	 * 操作按钮
	 */
	@ViewInject(R.id.operate_tv)
	private TextView operate_tv;
	/**
	 * 图片提示对话框
	 */
	private ManyImageHintDialog mManyImageHintDialog;
	// private ImageView photoView;
	private View containerView;
	private Bitmap showImage;
	private BodyImageView cropView;

	private FrameLayout.LayoutParams frameLp;
	public static int parentWidth;
	private int parentHeight;
	private int videoWidth;
	private int videoHeight;
	private Dialog processDialog;
	// GenerateFrameUtil generate;
	private Handler handler = new Handler();

	private String imagePath;

	private Class nextClass;

	private String croppedImagePath;

	private String bannerId;
	private int MAX_SIZE = 400;
	/**
	 * 旋转角度
	 */
	private float degree = 0;

	private BitmapDrawable bd;
	/**
	 * 左旋
	 */
	@ViewInject(R.id.aleft_rl)
	private RelativeLayout aleft_rl;
	/**
	 * 右旋
	 */
	@ViewInject(R.id.rightward_rl)
	private RelativeLayout rightward_rl;
	/**
	 * 色相
	 */
	private SeekBar hue_seekbar;
	/**
	 * 饱和度
	 */
	private SeekBar saturation_seekbar;

	/**
	 * 亮度
	 */
	private SeekBar luminance_seekbar;
	/**
	 * 手势控制器
	 */
	private FCGestureView gestureView = null;
	/**
	 * 图片提示按钮
	 */
	@ViewInject(R.id.hint_iv)
	private ImageView hint_iv;
	private boolean isRegulate;
	static {
		try {
			// 加载扣头穿衣lib库
			System.loadLibrary("detection_based_tracker");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		title_tv.setText("图片调试");
		operate_tv.setText("下一步");
		operate_tv.setVisibility(View.VISIBLE);
		processDialog = new ProcessingDialog(this);
		imagePath = PathCommonDefines.PAIZHAO + "/camerahead1.jpg";

		new Thread(new Runnable() {
			@Override
			public void run() {
				if (showImage == null) {

					showImage = NativeImageLoader.decodeProperBitmapForCrop(
							imagePath, 800);
				}
				if (showImage == null) {
					return;
				}
				// showImage = FCTools.getScaleBMP(showImage,
				// SingletonDataMgr.getInstance().iComponentW,
				// SingletonDataMgr.getInstance().iComponentH);
				// showImage.recycle();
				// showImage = null;
				SingletonDataMgr.getInstance().iCurUserSelectPhotoBmp = showImage
						.copy(Bitmap.Config.RGB_565, true);
				UserPhotoData photo = new UserPhotoData();
				photo.cameraBmp = showImage;
				photo.setDefaultMatrix();
				SingletonDataMgr mgr = SingletonDataMgr.getInstance();
				mgr.iUserPhotoData = photo;
				// dis.recycle();
				// dis = null;
				SingletonDataMgr.getInstance().iCurDisplayComponentMode = 3;

				// RuntimeCache.srcCropImagePath = imagePath;
				// bd = new BitmapDrawable(this.getResources(), showImage);

				// if (showImage == null) {
				// mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.HANDLE_FINISH);
				// return;
				//
				// }

				mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.INIT_PHOTOCROP);
				// } else {
				// LogUtils.e("图片为空----------------");
				// mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.HANDLE_FINISH);
				// }

			}

		}).start();
		if (mManyImageHintDialog == null) {
			mManyImageHintDialog = new ManyImageHintDialog(mContext);
		}
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				mManyImageHintDialog.show();
				mManyImageHintDialog.windowDeploy(0, 0);
				Toast.makeText(mContext, "左右滑动切换图片提示", Toast.LENGTH_LONG)
						.show();
			}
		}, 1000);

	}

	public void init(final boolean isInit) {
		if (showImage == null) {

			showImage = NativeImageLoader.decodeProperBitmapForCrop(imagePath,
					800);
		}
		// showImage = FCTools.getScaleBMP(showImage,
		// SingletonDataMgr.getInstance().iComponentW,
		// SingletonDataMgr.getInstance().iComponentH);
		// showImage.recycle();
		// showImage = null;
		SingletonDataMgr.getInstance().iCurUserSelectPhotoBmp = showImage.copy(
				Bitmap.Config.RGB_565, true);
		UserPhotoData photo = new UserPhotoData();
		photo.cameraBmp = showImage;
		photo.setDefaultMatrix();
		SingletonDataMgr mgr = SingletonDataMgr.getInstance();
		mgr.iUserPhotoData = photo;
		// dis.recycle();
		// dis = null;
		SingletonDataMgr.getInstance().iCurDisplayComponentMode = 3;

		// RuntimeCache.srcCropImagePath = imagePath;
		// bd = new BitmapDrawable(this.getResources(), showImage);

		// if (showImage == null) {
		// mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.HANDLE_FINISH);
		// return;
		//
		// }
		handler.post(new Runnable() {

			// @Override
			public void run() {
				// initFrameView();
				// // if (bd != null) {
				cropView.postInvalidate();
				// cropView.setDrawable(showImage, MAX_SIZE, MAX_SIZE, isInit);
				hasInit = true;
				mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.HANDLE_FINISH);
				// } else {
				// LogUtils.e("图片为空----------------");
				// mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.HANDLE_FINISH);
				// }

			}

		});

	}

	boolean hasInit = false;

	@Override
	public void onResume() {
		super.onResume();
		if (hasInit) {
			init(false);
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		if (bd != null && bd.getBitmap() != null) {
			bd.getBitmap().recycle();
			bd = null;
		}
		if (showImage != null && !showImage.isRecycled()) {
			showImage.recycle();
			showImage = null;
		}
	}

	@Override
	public void finish() {
		super.finish();
		if (bd != null && bd.getBitmap() != null) {
			bd.getBitmap().recycle();
			bd = null;
		}
		if (showImage != null && !showImage.isRecycled()) {
			showImage.recycle();
			showImage = null;
		}
		if (RuntimeCache.lastProcessKey != null) {
			String parentFolder = CameraHelper
					.getInternalCropSrcImagesFolder(RuntimeCache.lastProcessKey);
			MediaHelper.deleteSubFiles(new File(parentFolder));

		}
		System.gc();
	}

	/**
	 * @description: 图片处理
	 * @exception
	 */
	private void ImageMatrix() {

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (isRegulate) {

						SingletonDataMgr mgr = SingletonDataMgr.getInstance();

						showImage = mgr.getUpdateBmp();
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				showImage = ImageUtils.toMatrix(showImage, degree, 1, 0);
				System.gc();
				init(true);
			}

		}).start();
	}

	@Event({ R.id.back_rl, R.id.operate_tv, R.id.hint_iv, R.id.aleft_rl,
			R.id.rightward_rl })
	private void onClick(View v) {
		switch (v.getId()) {
		// 点击图片提示开关
		case R.id.hint_iv:
			if (!mManyImageHintDialog.isShowing()) {

				mManyImageHintDialog.show();
				mManyImageHintDialog.windowDeploy(100, 100);
				Toast.makeText(mContext, "左右滑动切换图片提示", Toast.LENGTH_LONG)
						.show();
			}
			break;
		// 点击左旋
		case R.id.aleft_rl:
			processDialog = ProgressDialog.show(mContext, null, "正在向左旋转,请耐心等待");
			degree = -90;
			// degree = degree % 360;
			try {
				ImageMatrix();
			} catch (Exception e) {
				Toast.makeText(mContext, "操作失败", Toast.LENGTH_SHORT).show();
				mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.HANDLE_FINISH);
			}
			break;
		// 点击右旋
		case R.id.rightward_rl:
			processDialog = ProgressDialog.show(mContext, null, "正在向右旋转,请耐心等待");
			degree = 90;
			// degree = degree % 360;
			try {

				ImageMatrix();
			} catch (Exception e) {
				Toast.makeText(mContext, "操作失败", Toast.LENGTH_SHORT).show();
				mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.HANDLE_FINISH);
			}
			break;
		// 返回按钮
		case R.id.back_rl:
			finish();
			break;
		// 点击下一步
		case R.id.operate_tv:
			cropBitmaps();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1 && (resultCode == RESULT_OK || resultCode == 1)) {

			this.setResult(RESULT_OK);

			this.finish();
		} else if (requestCode != 1)
			this.finish();
		super.onActivityResult(requestCode, resultCode, data);

	}

	int rotation;

	public double compareTwoImages(Mat src, Mat tar) {

		Mat hsv_src = new Mat();
		Mat hsv_tar = new Mat();
		Imgproc.cvtColor(src, hsv_src, Imgproc.COLOR_BGR2HSV);
		Imgproc.cvtColor(tar, hsv_tar, Imgproc.COLOR_BGR2HSV);

		MatOfInt channels = new MatOfInt(0, 1);
		MatOfInt size = new MatOfInt(50, 60);
		MatOfFloat rang = new MatOfFloat(0, 180, 0, 256);

		// / Histograms
		Mat hist_src = new Mat();
		Mat hist_tar = new Mat();
		// MatND hist_test1;
		// MatND hist_test2;
		List<Mat> smatList = new ArrayList<Mat>();
		smatList.add(hsv_src);
		List<Mat> tmatList = new ArrayList<Mat>();
		tmatList.add(hsv_tar);
		// / Calculate the histograms for the HSV images
		Imgproc.calcHist(smatList, channels, new Mat(), hist_src, size, rang);
		Core.normalize(hist_src, hist_src, 0, 1, Core.NORM_MINMAX, -1,
				new Mat());

		Imgproc.calcHist(tmatList, channels, new Mat(), hist_tar, size, rang);
		Core.normalize(hist_tar, hist_tar, 0, 1, Core.NORM_MINMAX, -1,
				new Mat());

		// / Apply the histogram comparison methods

		int compare_method = 0;
		// double base_base = compareHist( hist_src, hist_src, compare_method );
		double base_half = Imgproc.compareHist(hist_src, hist_tar,
				compare_method);

		return base_half;

	}

	boolean isFrameInit = false;
	float offset = 0;

	public void initFrameView() {
		// photoView.setImageBitmap(showImage);
		int width = showImage.getWidth();
		int height = showImage.getHeight();
		if (!isFrameInit) {
			int cWidth = containerView.getWidth();
			int cHeight = containerView.getHeight();
			offset = (float) (cWidth * 0.08);
			int cropWidth = (int) (cWidth - offset);
			int cropHeight = (int) (cHeight - offset);
			if (width > height) {
				float scale = (float) (cropHeight / (height + 0.0));
				frameLp.height = cropHeight;
				frameLp.width = (int) (width * scale);
				frameLp.leftMargin = (int) ((cWidth - frameLp.width) * 0.5);
				frameLp.topMargin = (int) ((cHeight - cropHeight) * 0.5);
			} else {
				float scale = (float) (cropWidth / (width + 0.0));
				frameLp.width = cropWidth;
				frameLp.height = (int) (height * scale);
				frameLp.leftMargin = (int) ((cWidth - cropWidth) * 0.5);
				frameLp.topMargin = (int) ((cHeight - frameLp.height) * 0.5);
			}

			videoWidth = width;
			videoHeight = height;

			isFrameInit = true;
		}
	}

	private float startX;
	private float startY;
	private float clickX;
	private float clickY;

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			startX = event.getX();
			startY = event.getY();
			return true;
		}

		if (event.getAction() == MotionEvent.ACTION_MOVE) {

			clickX = event.getX();
			clickY = event.getY();
			float xD = clickX - startX;
			float yD = clickY - startY;
			// if (v == frameView) {
			positionChange(xD, yD);
			// }
			startX = clickX;
			startY = clickY;
			return true;
		}

		return true;
	}

	public void positionChange(float xDis, float yDis) {
		if ((frameLp.leftMargin + xDis + frameLp.width) < (parentWidth - offset * 0.5)) {
			frameLp.leftMargin = (int) (parentWidth - offset * 0.5 - frameLp.width);
		} else if ((frameLp.leftMargin + xDis) > offset * 0.5) {
			frameLp.leftMargin = (int) (offset * 0.5);
		} else {
			frameLp.leftMargin = (int) (frameLp.leftMargin + xDis);
		}

		if ((frameLp.topMargin + yDis + frameLp.height) < (parentHeight - offset * 0.5)) {
			frameLp.topMargin = (int) (parentHeight - offset * 0.5 - frameLp.height);
		} else if ((frameLp.topMargin + yDis) > offset * 0.5) {
			frameLp.topMargin = (int) (offset * 0.5);
		} else {
			frameLp.topMargin = (int) (frameLp.topMargin + yDis);
		}

		// photoView.setLayoutParams(frameLp);
	}

	/**
	 * @description: 下一步
	 * @exception
	 */
	public void cropBitmaps() {
		try {
			processDialog = ProgressDialog.show(mContext, null, "正在处理,请耐心等待");

		} catch (Exception e) {
			// TODO: handle exception
		}

		new Thread(new Runnable() {

			@Override
			public void run() {

				Bitmap result = null;
				try {
					SingletonDataMgr mgr = SingletonDataMgr.getInstance();
					result = mgr.getUpdateBmp();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//
				if (result != null) {

					// String imagePath=RuntimeCache.currentVideoPath;

					croppedImagePath = PathCommonDefines.PAIZHAO
							+ "/camerahead2.jpg";

					FileOutputStream fos = null;
					try {
						fos = new FileOutputStream(croppedImagePath);
						result.compress(Bitmap.CompressFormat.JPEG, 100, fos);
						fos.flush();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						if (fos != null) {
							try {
								fos.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						result.recycle();
						result = null;
						mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.CUT_HEAD);
					}

				} else {
				}

			}
		}).start();

	}

	public void cropOk() {

		// if (nextClass != null) {
		File file = new File(croppedImagePath);
		if (file.exists()) {

			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					File file = new File(PathCommonDefines.PAIZHAO,
							"camerahead2.jpg");
					if (file.exists()) {
						LogUtil.e("头像存在,开始检测头像");
						long result = DetectionBasedTracker.nativeHeadDetect(
								"paizhao/camerahead2.jpg", "");
						if (result == 0) {
							mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.CHECK_HEAD_EXIST);
						} else {
							mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.NO_HEAD);

						}
					} else {
						mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.NO_HEAD);

					}
				}
			}).start();

		} else {
		}
		// } else {
		// Intent intent = new Intent();
		// intent.putExtra("imagePath", croppedImagePath);
		//
		// setResult(RESULT_OK, intent);
		// this.finish();
		//
		// }

	}

	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			// 初始化图片
			case NetworkAsyncCommonDefines.INIT_PHOTOCROP:
				// SingletonDataMgr mgr = SingletonDataMgr.getInstance();
				// LogUtils.e("mgr.iUserPhotoData.getmLightValue():"
				// + mgr.iUserPhotoData.getmLightValue());
				// LogUtils.e("mgr.iUserPhotoData.getmLuminance():"
				// + mgr.iUserPhotoData.getmLuminance());
				// LogUtils.e("mgr.iUserPhotoData.getmSaturationValue():"
				// + mgr.iUserPhotoData.getmSaturationValue());
				// if (mgr.iUserPhotoData == null
				// || mgr.iUserPhotoData.cameraBmp == null) {
				// return;
				// }
				// // 调节色度
				// if (hue_seekbar != null) {
				// LogUtils.e("hue_seekbar.getProgress():"
				// + hue_seekbar.getProgress());
				//
				// mgr.iUserPhotoData
				// .setmLightValue(hue_seekbar.getProgress());
				// }
				// if (saturation_seekbar != null) {
				// // 调节饱和度
				// LogUtils.e("saturation_seekbar.getProgress():"
				// + saturation_seekbar.getProgress());
				// mgr.iUserPhotoData.setmSaturationValue(saturation_seekbar
				// .getProgress());
				// }
				// if (luminance_seekbar != null) {
				// // 调节亮度
				// LogUtils.e("luminance_seekbar.getProgress():"
				// + luminance_seekbar.getProgress());
				//
				// mgr.iUserPhotoData.setmLuminance(luminance_seekbar
				// .getProgress());
				// }
				cropView.postInvalidate();
				// cropView.setDrawable(showImage, MAX_SIZE, MAX_SIZE,
				// isInit);
				hasInit = true;
				break;
			// 处理完毕
			case NetworkAsyncCommonDefines.HANDLE_FINISH:
				processDialog.dismiss();
				break;
			case NetworkAsyncCommonDefines.CHECK_HEAD_EXIST:
				processDialog.dismiss();
				mSharedPreferenceUtils.setIsFirstCutImage(mContext, true);
				Intent intent = new Intent(mContext,
						DescribeFaceNeckActivity.class);
				startActivityForResult(intent, 1);
				overridePendingTransition(R.anim.back_enter, R.anim.anim_exit);
				finish();
				break;

			case NetworkAsyncCommonDefines.CUT_HEAD:
				cropOk();

				break;
			case NetworkAsyncCommonDefines.NO_HEAD:
				processDialog.dismiss();
				Toast.makeText(mContext, "没有找到完整头部", Toast.LENGTH_LONG).show();
				Intent PaiZhaoYinDaoActivity = new Intent(mContext,
						SetImageActivity.class);
				startActivity(PaiZhaoYinDaoActivity);
				overridePendingTransition(R.anim.back_exit, R.anim.anim_exit);
				finish();
				break;
			}
		};

	};

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		containerView = this.findViewById(R.id.container_view);
		cropView = (BodyImageView) this.findViewById(R.id.crop_view);
		cropView.BodyImageView_Bmp_Mode = 3;
		LinearLayout.LayoutParams clp = (LinearLayout.LayoutParams) containerView
				.getLayoutParams();
		RuntimeCache.setScreenW(ScreenUtil.getScreenWidthPix(mContext));
		clp.width = RuntimeCache.getScreenW();
		clp.height = RuntimeCache.getScreenW();
		parentWidth = clp.width;
		parentHeight = clp.height;
		containerView.setLayoutParams(clp);
		SingletonDataMgr.getInstance().iComponentH = parentWidth;
		SingletonDataMgr.getInstance().iComponentW = parentHeight;
		MAX_SIZE = RuntimeCache.getScreenW();

		operate_tv.setOnTouchListener(new ButtonHighlighterOnTouchListener(
				operate_tv, PhotoCropActivity.this));
		hue_seekbar = (SeekBar) findViewById(R.id.hue_seekbar);
		hue_seekbar.setOnSeekBarChangeListener(this);
		saturation_seekbar = (SeekBar) findViewById(R.id.saturation_seekbar);
		saturation_seekbar.setOnSeekBarChangeListener(this);
		luminance_seekbar = (SeekBar) findViewById(R.id.luminance_seekbar);
		luminance_seekbar.setOnSeekBarChangeListener(this);

		// 控制手势滑动和缩放
		gestureView = (FCGestureView) findViewById(R.id.FCGestureView2);
		gestureView.FCGestureViewMode = FCGestureView.K_FC_GESTURE_PHOTO_MODE;
		gestureView.iml = this;

	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		SingletonDataMgr mgr = SingletonDataMgr.getInstance();
		if (mgr.iUserPhotoData == null || mgr.iUserPhotoData.cameraBmp == null) {
			return;
		}
		isRegulate = true;
		// 调节色度
		if (seekBar.equals(hue_seekbar)) {

			mgr.iUserPhotoData.setmLightValue(progress);
			cropView.postInvalidate();
		} else if (seekBar.equals(saturation_seekbar)) {
			// 调节饱和度
			mgr.iUserPhotoData.setmSaturationValue(progress);
			cropView.postInvalidate();
		} else if (seekBar.equals(luminance_seekbar)) {
			// 调节亮度

			mgr.iUserPhotoData.setmLuminance(progress);
			cropView.postInvalidate();
		}

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		cropView.invalidate();
	}

	@Override
	public void gestureTaped() {
		// TODO Auto-generated method stub

	}
}
