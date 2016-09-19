package cn.dressbook.ui;

import java.io.File;
import java.io.FileOutputStream;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.photo.Photo;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.SnowCommon.common.MessageWhat;
import cn.dressbook.ui.SnowCommon.common.RuntimeCache;
import cn.dressbook.ui.SnowCommon.view.dialog.ProcessingDialog;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.dialog.ImageHintDialog;
import cn.dressbook.ui.general.FotoCut.util.GrabCutCache;
import cn.dressbook.ui.general.FotoCut.view.ui.MaskCanvas;
import cn.dressbook.ui.general.FotoCut.view.ui.MaskCanvasDelegate;
import cn.dressbook.ui.general.FotoCut.view.ui.ScaleableView;
import cn.dressbook.ui.utils.ImageUtils;
import cn.dressbook.ui.utils.ScreenUtil;

/**
 * @description: 描绘面部和脖子
 * @author:袁东华
 * @modifier:袁东华
 * @remarks:
 * @2015-5-26 下午2:50:26
 */
@SuppressLint("NewApi")
@ContentView(R.layout.activity_grab_cut_main)
public class DescribeFaceNeckActivity extends BaseActivity implements
		MaskCanvasDelegate {
	private Context mContext = DescribeFaceNeckActivity.this;
	/**
	 * 图片提示对话框
	 */
	private ImageHintDialog mImageHintDialog;
	// private View performanim_erase;
	@ViewInject(R.id.image_show_frame)
	private FrameLayout image_show_frame;
	/**
	 * 展示头像的ImageView
	 */
	@ViewInject(R.id.anim_cover_image)
	private ImageView anim_cover_image;
	@ViewInject(R.id.maskCanvas_1)
	private MaskCanvas maskCanvas_1;
	@ViewInject(R.id.image_show_frame_container)
	private View image_show_frame_container;
	@ViewInject(R.id.touchable_view)
	private ScaleableView touchable_view;
	@ViewInject(R.id.model_draw)
	private View model_draw;
	@ViewInject(R.id.model_move)
	private View model_move;
	@ViewInject(R.id.grab_control)
	private View grab_control;
	@ViewInject(R.id.anim_draw)
	private View anim_draw;
	@ViewInject(R.id.anim_erase)
	private View anim_erase;
	@ViewInject(R.id.undo)
	private View undo;
	/**
	 * 魔法画笔
	 */
	@ViewInject(R.id.anim_draw_p)
	private View anim_draw_p;
	@ViewInject(R.id.anim_erase_p)
	private View anim_erase_p;

	// private DrawRect drawRect;
	// private FrameLayout rectLayout;

	private Dialog processDialog;

	public static int MAX_GRAB_SIZE = 280;

	private String imagePath;

	private float xRatio = 1;
	private float yRatio = 1;
	// private AnimationDrawable animations;

	// private View pant;

	private Mat sourceMat;
	private Mat sourceBGRMat;
	private DescribeFaceNeckActivity self = this;
	@ViewInject(R.id.msg_control)
	private View msg_control;
	private boolean needProcess = false;
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
	 * @description:下一步
	 * @exception
	 */
	public void clickNext() {
		if (!maskCanvas_1.isChoose()) {
			Toast toast = Toast.makeText(getApplicationContext(),
					R.string.changebg, 300);
			toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
			toast.setDuration(5000);
			toast.show();
			return;
		}

		processDialog = ProgressDialog.show(mContext, null, "正在处理,请耐心等待");
		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// Mat result = canvas.generateGrabCutResult();
		//
		// Bitmap bmp = Bitmap.createBitmap(result.cols(), result.rows(),
		// Config.ARGB_8888);
		// Utils.matToBitmap(result, bmp);
		//
		// result.release();
		//
		// // Bitmap bmp=this.inPaint();
		//
		// final String path1 = PathCommonDefines.PAIZHAO
		// + "/camerahead3.jpg"; // RuntimeCache.getCurrentInternalImageFolder()
		// // + File.separator + name;
		// // String path1 = RuntimeCache.getCurrentExternalImageFolder() +
		// // File.separator
		// // + name;
		//
		// try {
		// FileOutputStream out1 = new FileOutputStream(path1);
		// bmp.compress(CompressFormat.JPEG, 100, out1);
		// out1.flush();
		// out1.close();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				File srcFile = new File(PathCommonDefines.PAIZHAO,
						"camerahead2.jpg");
				if (srcFile.exists()) {
					Bitmap bmp = maskCanvas_1.getBitmap1();
					if (bmp != null) {
						ManagerUtils.getInstance().setFaceNeckBitmap(bmp);
						ManagerUtils.getInstance().setPicBitmap(currentCover);
						ManagerUtils.getInstance().setLastShowMatList(
								maskCanvas_1.getLastShowMatList());
						ManagerUtils.getInstance().setWholeDrawMat(
								maskCanvas_1.getWholeDrawMat());
						ManagerUtils.getInstance().setBackBitmap(
								maskCanvas_1.getBackBitmap());
						ManagerUtils.getInstance().setCanvasWidth(
								maskCanvas_1.getCanvasWidth());
						ManagerUtils.getInstance().setCanvasHeight(
								maskCanvas_1.getCanvasHeight());
						ManagerUtils.getInstance().setBgdModel(
								maskCanvas_1.getBgdModel());
						ManagerUtils.getInstance().setForeModel(
								maskCanvas_1.getForeModel());
						ManagerUtils.getInstance().setLastMaskMat(
								maskCanvas_1.getLastMaskMat());
						ManagerUtils.getInstance().setImgCanvas(
								maskCanvas_1.getImgCanvas());
						ManagerUtils.getInstance().setBackCanvas(
								maskCanvas_1.getBackCanvas());
						// ManagerUtils.getInstance().setDelegate(
						// maskCanvas_1.getDelegate());
						Mat mat = new Mat();
						Utils.bitmapToMat(bmp, mat);
						// Mat mat = maskCanvas_1.generateGrabCutResult();
						if (mat != null) {
							Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGRA2GRAY);
							mat.clone();
							if (mat != null) {
								Imgproc.resize(mat, mat, new Size(500, 500));
								if (Highgui.imwrite(PathCommonDefines.PAIZHAO
										+ "/camerahead.0maskfaceneck.png", mat)) {
									//
									// long result = DetectionBasedTracker
									// .nativeHeadMatting(
									// "paizhao/camerahead2.jpg",
									// "paizhao/cameraheadmat.png",
									// "paizhao/camerahead4.jpeg");
									// if (result == 0) {
									// LogUtils.e("磨边成功-----------------");
									// File t = new File(
									// PathCommonDefines.PAIZHAO,
									// "camerahead.0");
									// File s = new File(
									// PathCommonDefines.PAIZHAO,
									// "camerahead4.jpeg");
									//
									//
									// if (FileSDCacher.fuZhiFile(s, t)) {
									mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.EDGING_S);
									// }

									// } else {
									// LogUtils.e("result:" + result);
									// mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.EDGING_F);
									// }
								} else {
								}
							} else {
							}
						} else {
						}
					} else {
					}
				} else {
				}
			}
		}).start();

		// mHandler.post(new Runnable() {
		//
		// @Override
		// public void run() {
		//
		//
		// Intent intent = null;
		//
		// intent = new Intent(DescribeFaceNeckActivity.this,
		// ProcessActivity.class);
		//
		// intent.putExtra("imagePath", path1);
		// intent.putExtra("xRatio", xRatio);
		// intent.putExtra("yRatio", yRatio);
		//
		// startActivityForResult(intent, 1);
		// processDialog.setVisibility(View.INVISIBLE);
		//
		// }
		//
		// });

		// }

		// }).start();

	}

	private String inPaint() {

		final Mat mask = maskCanvas_1.getInpaintMask();
		Mat result = new Mat();

		Photo.inpaint(maskCanvas_1.getScaledSourceMat(), mask, result, 3,
				Photo.INPAINT_TELEA);

		Imgproc.cvtColor(result, result, Imgproc.COLOR_BGR2BGRA);

		Imgproc.resize(result, result, sourceMat.size());

		// if(sourceBGRMat==null){
		//
		// sourceBGRMat=new Mat();
		// Imgproc.cvtColor(sourceMat, sourceBGRMat, Imgproc.COLOR_BGRA2BGR);
		// }

		Mat resultLarger = sourceMat.clone();

		result.copyTo(resultLarger, GrabCutCache.originalMask);

		result.release();

		mask.release();
		Bitmap bmp = Bitmap.createBitmap(resultLarger.cols(),
				resultLarger.rows(), Config.ARGB_8888);
		Utils.matToBitmap(resultLarger, bmp);

		File original = new File(imagePath);
		int index = original.getName().lastIndexOf(".");
		final String pureName = original.getName().substring(0, index);
		String name = pureName + "_inpaint.png";

		final String path1 = RuntimeCache.getCurrentInternalImageFolder()
				+ File.separator + name;
		// String path1 = RuntimeCache.getCurrentExternalImageFolder() +
		// File.separator
		// + name;

		try {
			FileOutputStream out1 = new FileOutputStream(path1);
			bmp.compress(CompressFormat.PNG, 100, out1);
			out1.flush();
			out1.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		final Mat fResult = resultLarger;

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// Imgproc.cvtColor(fResult, fResult, Imgproc.COLOR_BGR2BGRA);

				fResult.setTo(Scalar.all(0), GrabCutCache.originalMask);

				fResult.setTo(new Scalar(100, 0, 44, 128),
						GrabCutCache.diffMask);

				Bitmap bmp2 = Bitmap.createBitmap(fResult.cols(),
						fResult.rows(), Config.ARGB_8888);
				Utils.matToBitmap(fResult, bmp2);

				String name = pureName + "_inpaint_clear.png";

				final String path2 = RuntimeCache
						.getCurrentInternalImageFolder()
						+ File.separator
						+ name;
				// String path1 = RuntimeCache.getCurrentExternalImageFolder() +
				// File.separator
				// + name;

				try {
					FileOutputStream out1 = new FileOutputStream(path2);
					bmp2.compress(CompressFormat.PNG, 100, out1);
					out1.flush();
					out1.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

				fResult.release();
			}

		}).start();

		return pureName;

	}

	// private Bitmap coverBitmap;
	/**
	 * 当前的头像的bitmap
	 */
	private Bitmap currentCover;

	public class LoadCover extends AsyncTask<Void, Void, Bitmap> {
		int type = 0; // 0 is init, 1 is reload

		public LoadCover(int type) {
			this.type = type;
		}

		@Override
		protected Bitmap doInBackground(Void... params) {
			Bitmap bmp = ImageUtils.getBitmapFromFile(new File(imagePath),
					anim_cover_image.getWidth(), anim_cover_image.getHeight());
			// Bitmap image = RuntimeImageLoader.getBitmapByPath(imagePath);
			if (bmp != null) {
				// float scale;
				// if (anim_cover_image.getWidth() / (float) bmp.getWidth() >=
				// anim_cover_image
				// .getHeight() / (float) bmp.getHeight()) {
				// scale = anim_cover_image.getHeight() / (float)
				// bmp.getHeight();
				// } else {
				// scale = anim_cover_image.getWidth() / (float) bmp.getWidth();
				// }
				// bmp2 = ImageUtils.toMatrix(bmp, 0, scale, 0);
				// Log.i(TAG, "图片的大小2：" + bmp.getByteCount());
				// bmp.recycle();
				// bmp = null;
				// LogUtils.e("bmp2的宽度:" + bmp2.getWidth());
				// LogUtils.e("bmp2的高度:" + bmp2.getHeight());
				// Matrix matrix = new Matrix();
				// matrix.postScale(
				// bmp.getWidth() / (float) anim_cover_image.getWidth(),
				// bmp.getHeight() / (float) anim_cover_image.getHeight()); //
				// 长和宽放大缩小的比例
				// bmp2 = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(),
				// bmp.getHeight(), matrix, true);
				// final String path1 = PathCommonDefines.PAIZHAO
				// + "/camerahead4.jpg"; //
				// RuntimeCache.getCurrentInternalImageFolder()
				// + File.separator + name;
				// String path1 = RuntimeCache.getCurrentExternalImageFolder() +
				// File.separator
				// + name;

				// try {
				// FileOutputStream out1 = new FileOutputStream(path1);
				// bmp2.compress(CompressFormat.JPEG, 100, out1);
				// out1.flush();
				// out1.close();
				// } catch (Exception e) {
				// e.printStackTrace();
				// }
			} else {
			}
			return bmp;

		}

		@Override
		protected void onPostExecute(Bitmap value) {
			// animations = value;
			processDialog.dismiss();
			if (value != null) {
				currentCover = value;
				// currentCover = coverBitmap.copy(Config.ARGB_8888, true);
				// BitmapDrawable bd = new BitmapDrawable(self.getResources(),
				// currentCover);
				// anim_cover_image.setDrawable(bd, 640, 640);

				anim_cover_image.setImageBitmap(currentCover);
				// maskCanvas_1.setMatSize(value.getWidth(), value.getHeight());
				sourceMat = new Mat();
				Utils.bitmapToMat(currentCover, sourceMat);
				// float height = image_show_frame.getLayoutParams().height;

				// xRatio = (float) (height / sourceMat.cols());
				// yRatio = (float) (height / sourceMat.rows());
				// double scale=calcScaleRatio(mat.cols(),mat.rows());
				// Imgproc.resize(mat, mat, new Size(mat.cols()*scale,
				// mat.rows()*scale));

				// Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGRA2BGR);

				maskCanvas_1.setSourceMat(sourceMat);
			}
			// if (type == 1)
			// reloadDisplay();
			// LoadAnimation animTask = new LoadAnimation();
			// animTask.execute();
		}
	}

	// List<Bitmap> bitmaps = new ArrayList<Bitmap>();

	private double calcScaleRatio(int width, int height) {

		double scale = 1.0;

		if (width < height) {
			scale = (MAX_GRAB_SIZE / (width + 0.0));

		} else {
			scale = (MAX_GRAB_SIZE / (height + 0.0));

		}
		return scale;
	}

	private void clearActivity() {
		// if (animations != null) {
		// animations.stop();
		// animations = null;
		// }
		// if (coverBitmap != null) {
		// coverBitmap.recycle();
		// coverBitmap = null;
		if (currentCover != null)
			currentCover.recycle();
		currentCover = null;
		// anim_cover_image.setImageBitmap(null);
		// }
		// animImage.setImageDrawable(null);

	}

	@Event({ R.id.back_rl, R.id.operate_tv, R.id.anim_draw, R.id.anim_erase,
			R.id.anim_draw_p, R.id.anim_erase_p, R.id.undo, R.id.model_draw,
			R.id.model_move })
	private void onClick(View v) {
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		// 点击下一步
		case R.id.operate_tv:
			clickNext();

			break;
		// 点击画笔
		case R.id.anim_draw:
			maskCanvas_1.setPaintMode(MaskCanvas.CANVAS_MODE_DRAW_MANUAL);

			needProcess = false;
			setSelectStatus(maskCanvas_1.getPaintMode());
			break;
		// 点击擦除
		case R.id.anim_erase:
			maskCanvas_1.setPaintMode(MaskCanvas.CANVAS_MODE_ERASE_MANUAL);

			needProcess = false;
			setSelectStatus(maskCanvas_1.getPaintMode());
			break;
		// 点击魔法画笔
		case R.id.anim_draw_p:
			maskCanvas_1.setPaintMode(MaskCanvas.CANVAS_MODE_DRAW_AUTO);
			// anim_draw.setSelected(false);

			needProcess = true;
			setSelectStatus(maskCanvas_1.getPaintMode());
			break;
		// 点击魔法擦除
		case R.id.anim_erase_p:
			maskCanvas_1.setPaintMode(MaskCanvas.CANVAS_MODE_ERASE_AUTO);

			needProcess = true;
			setSelectStatus(maskCanvas_1.getPaintMode());
			break;
		// 点击撤销
		case R.id.undo:
			maskCanvas_1.undo();
			break;
		// 点击画笔操作模块
		case R.id.model_draw:
			if (editMode != 0) {
				editMode = 0;
				model_draw.setSelected(true);
				model_move.setSelected(false);
				touchable_view.setVisibility(View.GONE);
				msg_control.setVisibility(View.GONE);
				grab_control.setVisibility(View.VISIBLE);
				// image_show_frame.setLayerType(View.LAYER_TYPE_SOFTWARE,
				// null);
				grab_control.startAnimation(AnimationUtils.loadAnimation(this,
						R.anim.push_bottom_in));
				undo.setEnabled(true);
			}
			break;
		// 点击手势操作模块
		case R.id.model_move:
			if (editMode != 1) {
				editMode = 1;
				model_draw.setSelected(false);
				model_move.setSelected(true);
				touchable_view.setVisibility(View.VISIBLE);
				touchable_view.setEffectView(image_show_frame);
				msg_control.setVisibility(View.VISIBLE);
				// image_show_frame.setLayerType(View.LAYER_TYPE_SOFTWARE,
				// null);
				touchable_view.setCanvas(maskCanvas_1);
				grab_control.startAnimation(AnimationUtils.loadAnimation(this,
						R.anim.push_bottom_out));
				grab_control.setVisibility(View.GONE);
				undo.setEnabled(false);
			}
			break;

		default:
			break;
		}
	}

	/**
	 * @description: 设置选择状态标识
	 * @exception
	 */
	public void setSelectStatus(int paintMode) {

		anim_erase
				.setSelected(paintMode == MaskCanvas.CANVAS_MODE_ERASE_MANUAL);
		anim_draw_p.setSelected(paintMode == MaskCanvas.CANVAS_MODE_DRAW_AUTO);
		anim_erase_p
				.setSelected(paintMode == MaskCanvas.CANVAS_MODE_ERASE_AUTO);
		anim_draw.setSelected(paintMode == MaskCanvas.CANVAS_MODE_DRAW_MANUAL);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {
			if (requestCode == 0) {
				this.setResult(RESULT_OK);
				this.finish();
				// RuntimeCache.removeActivity(this);
				return;
			}
		} else if (resultCode == 1) {
			if (requestCode == 1) {
				this.setResult(1);
				this.finish();
				// RuntimeCache.removeActivity(this);
				return;
			}
		}
		// RuntimeCache.removeMasks();
		// clearRuntimeImageResult();
		processDialog = ProgressDialog.show(mContext, null, "正在处理,请耐心等待");
		LoadCover coverTask = new LoadCover(1);
		coverTask.execute();
		super.onActivityResult(requestCode, resultCode, data);

	}

	@Override
	public void finish() {
		// RuntimeCache.removeMasks();
		// clearActivity();
		// clearRuntimeImageResult();
		// RuntimeCache.isWaitingForWarp = false;
		// RuntimeCache.currentWorkingActivity = null
		// canvas.destory();

		if (this.sourceBGRMat != null) {

			sourceBGRMat.release();
			sourceBGRMat = null;
		}

		if (this.sourceMat != null) {

			sourceMat.release();
			sourceMat = null;
		}
		super.finish();
		System.gc();
	}

	public boolean isBusy() {
		return processDialog.isShowing();
	}

	public View getViewimage_show_frame() {
		return this.image_show_frame;

	}

	private float origX = 0;
	private float origY = 0;

	public void setViewimage_show_frameTranslate(float x, float y) {
		origX = this.image_show_frame.getX();
		origY = this.image_show_frame.getY();

		this.image_show_frame.setX(origX + x);
		this.image_show_frame.setY(origY + y);

	}

	public void setViewimage_show_frameScale(int center_x, int center_y,
			float scale) {
		this.image_show_frame.setPivotX(center_x);
		this.image_show_frame.setPivotY(center_y);
		this.image_show_frame.setScaleX(scale);
		this.image_show_frame.setScaleY(scale);
		// this.image_show_frame.getsca
		// this.image_show_frame.setp
	}

	public void resetViewimage_show_frameTranslate() {

		this.image_show_frame.setX(origX);
		this.image_show_frame.setY(origY);
	}

	private int editMode = 0; // 0 is draw, 1 is adjust

	@Override
	public void onStop() {
		super.onStop();
		System.gc();
	}

	public Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case (MessageWhat.WHAT_ADD_DRAW_WIDGET):
				// addDrawWidget((DrawWidgetData) msg.obj);
				break;
			case (MessageWhat.WHAT_MASK_TASK):

				break;

			case (MessageWhat.WHAT_SHOW_DIALOG):
				processDialog = ProgressDialog.show(mContext, null,
						"正在处理,请耐心等待");
				break;
			case (MessageWhat.WHAT_CLOSE_DIALOG):
				processDialog.dismiss();
				maskCanvas_1.postInvalidate();
				break;
			case (MessageWhat.WHAT_REFRESH_BUTTON_STATE):
				int count = maskCanvas_1.getHistoryCount();
				if (count >= 1) {
					undo.setEnabled(true);
				} else {
					undo.setEnabled(false);

				}
				break;
			// 磨边成功,跳转
			case NetworkAsyncCommonDefines.EDGING_S:
				processDialog.dismiss();
				// 去扣头
				// Intent koutou = new Intent(mContext,
				// ImageProcessingService.class);
				// koutou.putExtra("id", 13);
				// startService(koutou);
				Intent headIntent = new Intent(mContext,
						DescribeHeadActivity.class);
				// headIntent.putExtra("bitmap", canvas.getBitmap1());
				startActivity(headIntent);
				overridePendingTransition(R.anim.back_enter, R.anim.anim_exit);
				finish();

				break;
			// 磨边失败
			case NetworkAsyncCommonDefines.EDGING_F:
				processDialog.dismiss();
				finish();
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		imagePath = PathCommonDefines.PAIZHAO + "/camerahead2.jpg";
		processDialog = new ProcessingDialog(this);
		// performanim_erase = findViewById(R.id.perfrom_erase);
		// modelGroup = (SegmentedRadioGroup) findViewById(R.id.edit_model);
		// modelGroup.setOnCheckedChangeListener(this);
		msg_control.setVisibility(View.GONE);
		model_draw.setSelected(true);
		// anim_cover_image.setImageViewDelegate(this);
		// drawRect = new DrawRect(rectLayout);
		// drawRect.setParentSize(RuntimeCache.getScreenW(),
		// RuntimeCache.getScreenW());
		RuntimeCache.setScreenW(ScreenUtil.getScreenWidthPix(mContext));
		maskCanvas_1 = (MaskCanvas) findViewById(R.id.maskCanvas_1);
		maskCanvas_1.setDelegate(self);
		// pant = findViewById(R.id.pant);

		undo = findViewById(R.id.undo);
		undo.setEnabled(false);

		LinearLayout.LayoutParams lp = (LayoutParams) image_show_frame_container
				.getLayoutParams();
		lp.height = RuntimeCache.getScreenW();
		image_show_frame_container.setLayoutParams(lp);

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		title_tv.setText("编辑");
		operate_tv.setText("下一步");
		operate_tv.setVisibility(View.VISIBLE);
		maskCanvas_1.setpHandler(mHandler);
		LoadCover coverTask = new LoadCover(0);
		coverTask.execute();
		// image_show_frame.setOnTouchListener(this);
		// 默认设置魔法画笔状态标识
		setSelectStatus(MaskCanvas.CANVAS_MODE_DRAW_AUTO);
		// RuntimeCache.addActivity(this);

		if (mImageHintDialog == null) {
			mImageHintDialog = new ImageHintDialog(mContext);
		}
		mImageHintDialog.initData(R.drawable.describefaceneck);
		mImageHintDialog.show();
	}

	@Override
	public View getViewContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setViewContainerTranslate(float x, float y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetViewContainerTranslate() {
		// TODO Auto-generated method stub

	}
}
