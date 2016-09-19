package cn.dressbook.ui;

import java.io.File;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.samples.facedetect.DetectionBasedTracker;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
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
import cn.dressbook.ui.general.FotoCut.view.ui.MaskCanvas;
import cn.dressbook.ui.general.FotoCut.view.ui.MaskCanvasDelegate;
import cn.dressbook.ui.general.FotoCut.view.ui.ScaleableView;
import cn.dressbook.ui.utils.FileSDCacher;
import cn.dressbook.ui.utils.ScreenUtil;

/**
 * @description:描绘头发,面部,脖子
 * @author:袁东华
 * @modifier:袁东华
 * @remarks:
 * @2015-6-5 下午1:55:35
 */
@SuppressLint("NewApi")
@ContentView(R.layout.drawinghair_layout)
public class DescribeHeadActivity extends BaseActivity implements
		MaskCanvasDelegate {
	private Context mContext = DescribeHeadActivity.this;
	/**
	 * 图片提示对话框
	 */
	private ImageHintDialog mImageHintDialog;
	@ViewInject(R.id.image_show_frame)
	private FrameLayout image_show_frame;
	@ViewInject(R.id.image_show_frame_container)
	private View image_show_frame_container;
	/**
	 * 展示头像的ImageView
	 */
	@ViewInject(R.id.anim_cover_image)
	private ImageView anim_cover_image;
	// private GrubCutImageView coverImage;
	@ViewInject(R.id.maskCanvas_1)
	private MaskCanvas maskCanvas_1;

	// private SegmentedRadioGroup modelGroup;
	@ViewInject(R.id.touchable_view)
	private ScaleableView touchable_view;
	@ViewInject(R.id.model_draw)
	private View model_draw;
	@ViewInject(R.id.model_move)
	private View model_move;
	@ViewInject(R.id.grab_control)
	private View grab_control;
	@ViewInject(R.id.operate_tv)
	private View drawBtn;
	@ViewInject(R.id.operate_tv)
	private View eraseBtn;
	@ViewInject(R.id.operate_tv)
	private View undoBtn;
	/**
	 * 魔法画笔
	 */
	@ViewInject(R.id.operate_tv)
	private View drawpBtn;
	@ViewInject(R.id.operate_tv)
	private View erasepBtn;

	// private DrawRect drawRect;
	// private FrameLayout rectLayout;

	public static int MAX_GRAB_SIZE = 280;

	// private String imagePath;

	private float xRatio = 1;
	private float yRatio = 1;
	// private AnimationDrawable animations;
	private Dialog processDialog;
	// private View pant;

	private Mat sourceMat;
	private Mat sourceBGRMat;
	private DescribeHeadActivity self = this;
	@ViewInject(R.id.msg_control)
	private View msg_control;
	private boolean needProcess = false;
	/**
	 * 头和脖子的bitmap
	 */
	private Bitmap mBitmap1;
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

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		// imagePath = PathCommonDefines.PAIZHAO + "/camerahead2.jpg";
		processDialog = new ProcessingDialog(this);
		// performEraseBtn = findViewById(R.id.perfrom_erase);
		// modelGroup = (SegmentedRadioGroup) findViewById(R.id.edit_model);
		// modelGroup.setOnCheckedChangeListener(this);
		msg_control = findViewById(R.id.msg_control);
		msg_control.setVisibility(View.GONE);
		model_draw.setSelected(true);
		// coverImage.setImageViewDelegate(this);
		// drawRect = new DrawRect(rectLayout);
		// drawRect.setParentSize(RuntimeCache.getScreenW(),
		// RuntimeCache.getScreenW());
		RuntimeCache.setScreenW(ScreenUtil.getScreenWidthPix(mContext));
		maskCanvas_1.setDelegate(self);
		// pant = findViewById(R.id.pant);

		drawBtn = findViewById(R.id.anim_draw);
		eraseBtn = findViewById(R.id.anim_erase);
		drawpBtn = findViewById(R.id.anim_draw_p);
		erasepBtn = findViewById(R.id.anim_erase_p);
		undoBtn = findViewById(R.id.undo);
		undoBtn.setEnabled(false);

		LayoutParams lp = (LayoutParams) image_show_frame_container
				.getLayoutParams();
		lp.height = RuntimeCache.getScreenW();
		image_show_frame_container.setLayoutParams(lp);

		// performEraseBtn.setOnClickListener(this);
		drawpBtn.setSelected(true);

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		title_tv.setText("编辑");
		operate_tv.setText("下一步");
		operate_tv.setVisibility(View.VISIBLE);
		initIntent();
		maskCanvas_1.setpHandler(mHandler);
		LoadCover coverTask = new LoadCover(0);
		coverTask.execute();
		// container.setOnTouchListener(this);
		// 默认设置魔法画笔状态标识
		setSelectStatus(MaskCanvas.CANVAS_MODE_DRAW_AUTO);
		// RuntimeCache.addActivity(this);

		if (mImageHintDialog == null) {
			mImageHintDialog = new ImageHintDialog(mContext);
		}
		mImageHintDialog.initData(R.drawable.describehead);
		mImageHintDialog.show();

	}

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
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				File srcFile = new File(PathCommonDefines.PAIZHAO,
						"camerahead2.jpg");
				if (srcFile.exists()) {
					Bitmap bmp = maskCanvas_1.getBitmap1();
					if (bmp != null) {
						Mat mat = new Mat();
						Utils.bitmapToMat(bmp, mat);
						// Mat mat = maskCanvas_1.generateGrabCutResult();
						if (mat != null) {
							Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGRA2GRAY);
							if (mat != null) {
								Imgproc.resize(mat, mat, new Size(500, 500));
								if (Highgui.imwrite(PathCommonDefines.PAIZHAO
										+ "/camerahead.0maskhead.png", mat)) {

									Imgproc.resize(sourceMat, sourceMat,
											new Size(500, 500));
									Imgproc.cvtColor(sourceMat, sourceMat,
											Imgproc.COLOR_RGB2BGR);
									if (Highgui.imwrite(
											PathCommonDefines.PAIZHAO
													+ "/camerahead.jpg",
											sourceMat)) {

										File t = new File(
												PathCommonDefines.PAIZHAO,
												"camerahead.0");
										File s = new File(
												PathCommonDefines.PAIZHAO,
												"camerahead.jpg");
										//
										if (FileSDCacher.fuZhiFile(s, t)) {
											mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.EDGING_S);
										}
									}
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

	}

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
			return null;

		}

		@Override
		protected void onPostExecute(Bitmap bit) {
			// animations = value;
			processDialog.dismiss();
			if (ManagerUtils.getInstance().getPicBitmap() != null) {
				currentCover = ManagerUtils.getInstance().getPicBitmap();
				// currentCover = coverBitmap.copy(Config.ARGB_8888, true);
				// BitmapDrawable bd = new BitmapDrawable(self.getResources(),
				// currentCover);
				// coverImage.setDrawable(bd, 640, 640);
				if (currentCover != null) {
				} else {
				}
				anim_cover_image.setImageBitmap(currentCover);
				sourceMat = new Mat();
				Utils.bitmapToMat(currentCover, sourceMat);

				maskCanvas_1.setSourceMat(sourceMat);
				maskCanvas_1.setLastShowMatList(ManagerUtils.getInstance()
						.getLastShowMatList());
				maskCanvas_1.setWholeDrawMat(ManagerUtils.getInstance()
						.getWholeDrawMat());
				maskCanvas_1.setBackBitmap(ManagerUtils.getInstance()
						.getBackBitmap());
				maskCanvas_1.setBitmap1(ManagerUtils.getInstance()
						.getFaceNeckBitmap());
				maskCanvas_1.setCanvasWidth(ManagerUtils.getInstance()
						.getCanvasWidth());
				maskCanvas_1.setCanvasHeight(ManagerUtils.getInstance()
						.getCanvasHeight());
				maskCanvas_1.setBgdModel(ManagerUtils.getInstance()
						.getBgdModel());
				maskCanvas_1.setForeModel(ManagerUtils.getInstance()
						.getForeModel());
				maskCanvas_1.setLastMaskMat(ManagerUtils.getInstance()
						.getLastMaskMat());
				maskCanvas_1.setImgCanvas(ManagerUtils.getInstance()
						.getImgCanvas());
				maskCanvas_1.setBackCanvas(ManagerUtils.getInstance()
						.getBackCanvas());
				// maskCanvas_1.setDelegate(ManagerUtils.getInstance().getDelegate());
				maskCanvas_1.invalidate();
			}
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
		if (currentCover != null)
			currentCover.recycle();
		currentCover = null;
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
			// drawBtn.setSelected(false);

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
				// container.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
				grab_control.startAnimation(AnimationUtils.loadAnimation(this,
						R.anim.push_bottom_in));
				undoBtn.setEnabled(true);
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
				undoBtn.setEnabled(false);
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

		eraseBtn.setSelected(paintMode == MaskCanvas.CANVAS_MODE_ERASE_MANUAL);
		drawpBtn.setSelected(paintMode == MaskCanvas.CANVAS_MODE_DRAW_AUTO);
		erasepBtn.setSelected(paintMode == MaskCanvas.CANVAS_MODE_ERASE_AUTO);
		drawBtn.setSelected(paintMode == MaskCanvas.CANVAS_MODE_DRAW_MANUAL);

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
		processDialog = ProgressDialog.show(mContext, null, "正在处理...");
		LoadCover coverTask = new LoadCover(1);
		coverTask.execute();
		super.onActivityResult(requestCode, resultCode, data);

	}

	@Override
	public void finish() {
		// RuntimeCache.removeMasks();
		clearActivity();
		// clearRuntimeImageResult();
		// RuntimeCache.isWaitingForWarp = false;
		// RuntimeCache.currentWorkingActivity = null
		maskCanvas_1.destory();

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

	public void setViewContainerTranslate(float x, float y) {
		origX = this.image_show_frame.getX();
		origY = this.image_show_frame.getY();

		this.image_show_frame.setX(origX + x);
		this.image_show_frame.setY(origY + y);

	}

	public void setViewContainerScale(int center_x, int center_y, float scale) {
		this.image_show_frame.setPivotX(center_x);
		this.image_show_frame.setPivotY(center_y);
		this.image_show_frame.setScaleX(scale);
		this.image_show_frame.setScaleY(scale);
		// this.container.getsca
		// this.container.setp
	}

	public void resetViewContainerTranslate() {

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
			super.handleMessage(msg);
			switch (msg.what) {
			case (MessageWhat.WHAT_ADD_DRAW_WIDGET):
				// addDrawWidget((DrawWidgetData) msg.obj);
				break;
			case (MessageWhat.WHAT_SHOW_DIALOG):
				processDialog = ProgressDialog.show(mContext, null, "正在处理...");
				break;
			case (MessageWhat.WHAT_CLOSE_DIALOG):
				processDialog.dismiss();
				maskCanvas_1.postInvalidate();
				break;
			case (MessageWhat.WHAT_REFRESH_BUTTON_STATE):
				int count = maskCanvas_1.getHistoryCount();
				if (count >= 1) {
					undoBtn.setEnabled(true);
				} else {
					undoBtn.setEnabled(false);

				}
				break;
			// 磨边成功,跳转
			case NetworkAsyncCommonDefines.EDGING_S:
				processDialog.dismiss();

				pbDialog.show();
				Toast.makeText(mContext, "正在处理头像", Toast.LENGTH_SHORT).show();
				dealHead();
				break;
			// 磨边失败
			case NetworkAsyncCommonDefines.EDGING_F:
				processDialog.dismiss();
				finish();
				break;
			// 扣头成功
			case NetworkAsyncCommonDefines.DEAL_HEAD_S:
				Toast.makeText(mContext, "头像处理成功", Toast.LENGTH_SHORT).show();
				Intent body = new Intent(mContext, ShowPicActivity.class);
				startActivity(body);
				pbDialog.dismiss();
				finish();
				break;
			case NetworkAsyncCommonDefines.DEAL_HEAD_NO_FACE:
				Toast.makeText(getApplicationContext(), "面部太暗,没有找到面部,请重新选择头像",
						Toast.LENGTH_LONG).show();
				pbDialog.dismiss();
				finish();
				break;
			case NetworkAsyncCommonDefines.DEAL_HEAD_NO_BODY:
				Toast.makeText(getApplicationContext(), "没有找到符合的身体,请重新提交数据",
						Toast.LENGTH_LONG).show();
				pbDialog.dismiss();
				finish();
				break;
			case NetworkAsyncCommonDefines.DEAL_HEAD_SMALL_HEAD:
				Toast.makeText(getApplicationContext(), "头部太小,请重新选择头像",
						Toast.LENGTH_LONG).show();
				pbDialog.dismiss();
				finish();
				break;
			case NetworkAsyncCommonDefines.DEAL_HEAD_TOP_HEAD:
				Toast.makeText(getApplicationContext(), "头部距离上边框太近,请重新选择头像",
						Toast.LENGTH_LONG).show();
				pbDialog.dismiss();
				finish();
				break;
			case NetworkAsyncCommonDefines.DEAL_HEAD_LEFT_HEAD:
				Toast.makeText(getApplicationContext(), "头部距离左边框太近,请重新选择头像",
						Toast.LENGTH_LONG).show();
				pbDialog.dismiss();
				finish();
				break;
			case NetworkAsyncCommonDefines.DEAL_HEAD_RIGHT_HEAD:
				Toast.makeText(getApplicationContext(), "头部距离右边框太近,请重新选择头像",
						Toast.LENGTH_LONG).show();
				pbDialog.dismiss();
				finish();
				break;
			case NetworkAsyncCommonDefines.DEAL_HEAD_bottom_HEAD:
				Toast.makeText(getApplicationContext(), "头部距离下边框太近,请重新选择头像",
						Toast.LENGTH_LONG).show();
				pbDialog.dismiss();
				finish();
				break;
			case NetworkAsyncCommonDefines.DEAL_HEAD_DIM_HEAD:
				Toast.makeText(getApplicationContext(), "头部太模糊,请重新选择头像",
						Toast.LENGTH_LONG).show();
				pbDialog.dismiss();
				finish();
				break;
			case NetworkAsyncCommonDefines.DEAL_HEAD_OTHER_HEAD:
				Toast.makeText(getApplicationContext(), "头部处理错误,请重新选择头像",
						Toast.LENGTH_LONG).show();
				pbDialog.dismiss();
				finish();
				break;
			}

		}
	};

	private void initIntent() {
		// TODO Auto-generated method stub
		// Intent intent=getIntent();
		// if(intent!=null){
		// mBitmap1=intent.getParcelableExtra("bitmap");
		// }
	}

	private void dealHead() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				try {
					ManagerUtils.getInstance().setKouTouResult(0);
					final long time1 = System.currentTimeMillis();
					final int msg = (int) DetectionBasedTracker
							.nativeMattingHead("paizhao/camerahead.0");
					ManagerUtils.getInstance().setKouTouResult(msg);
					new Handler(Looper.getMainLooper()).post(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							long time2 = System.currentTimeMillis();

							switch (msg) {
							// 头像处理成功
							case 0:
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DEAL_HEAD_S);
								break;
							// 没有找到面部
							case 1:
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DEAL_HEAD_NO_FACE);
								break;
							// 没有找到身体
							case 2:
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DEAL_HEAD_NO_BODY);
								break;
							// 头部太小
							case 3:
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DEAL_HEAD_SMALL_HEAD);
								break;
							// 头部距离上边框太近
							case 4:
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DEAL_HEAD_TOP_HEAD);
								break;
							// 头部距离左边框太近
							case 5:
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DEAL_HEAD_LEFT_HEAD);
								break;
							// 头部距离右边框太近
							case 6:
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DEAL_HEAD_RIGHT_HEAD);
								break;
							// 头部距离下边框太近
							case 7:
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DEAL_HEAD_bottom_HEAD);
								break;
							// 头部模糊
							case 8:
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DEAL_HEAD_DIM_HEAD);
								break;
							// 其他错误
							case 9:
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DEAL_HEAD_OTHER_HEAD);
								break;
							}
						}
					});
				} catch (Exception e) {
					// TODO: handle exception
					mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.DEAL_HEAD_OTHER_HEAD);
				}
			}
		}).start();
	}

	@Override
	public View getViewContainer() {
		// TODO Auto-generated method stub
		return null;
	}

}
