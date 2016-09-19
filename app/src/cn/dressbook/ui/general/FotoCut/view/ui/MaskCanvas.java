package cn.dressbook.ui.general.FotoCut.view.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import cn.dressbook.ui.SnowCommon.common.MessageWhat;
import cn.dressbook.ui.SnowCommon.common.RuntimeCache;
import cn.dressbook.ui.SnowCommon.common.util.GraphicUtil;
import cn.dressbook.ui.SnowCommon.common.util.OpenCVUtil;
import cn.dressbook.ui.general.FotoCut.util.GrabCutCache;


/**
 * @description:操作画布
 * @author:袁东华
 * @modifier:袁东华
 * @remarks:
 * @2015-5-27 上午10:09:23
 */
public class MaskCanvas extends View {

	// public static final int CANVAS_MODE_NONE = 0;
	/**
	 * 魔法画笔标识
	 */
	public static final int CANVAS_MODE_DRAW_AUTO = 0;
	/**
	 * 画笔标识
	 */
	public static final int CANVAS_MODE_DRAW_MANUAL = 1;
	/**
	 * 魔法擦除
	 */
	public static final int CANVAS_MODE_ERASE_AUTO = 2;
	/**
	 * 擦除
	 */
	public static final int CANVAS_MODE_ERASE_MANUAL = 3;

	public static int MAX_GRAB_SIZE = 320;
	/**
	 * 画笔
	 */
	private Paint draw_Paint = null;
	/**
	 * 涂红的bitmap
	 */
	private Bitmap new1Bitmap = null;
	/**
	 * 画笔的bitmap
	 */
	private Bitmap backBitmap = null;
	/**
	 * 画笔宽度
	 */
	private int canvasWidth;
	/**
	 * 画笔高度
	 */
	private int canvasHeight;
	/**
	 * 点击的x坐标
	 */
	private float clickx;
	/**
	 * 点击的y坐标
	 */
	private float clicky;
	/**
	 * 画笔类型
	 */
	private int paintMode;

	private int color = Color.WHITE;
	/**
	 * 画笔宽度
	 */
	private float strokeWidth = 40.0f;

	private float minx, miny, maxx, maxy;

	private Handler pHandler;
	// private PhotoEditPage parent;

	private boolean isPainting = false;
	/**
	 * 橡皮的画笔
	 */
	private Paint erase_Paint = null;
	/**
	 * 路径
	 */
	private Path mPath = null;

	private float mX, mY;
	private static final float TOUCH_TOLERANCE = 4;
	/**
	 * 背景mat
	 */
	Mat bgdModel;
	/**
	 * 前景mat
	 */
	Mat foreModel;

	public Scalar drawScalar = new Scalar(133, 0, 44, 40);
	public int drawColor = Color.argb(40, 133, 0, 40);;
	/**
	 * 资源mat
	 */
	private Mat sourceMat;
	/**
	 * 缩放后的资源mat
	 */
	private Mat scaledSourceMat;
	/**
	 * 最后一个mat
	 */
	private Mat lastMaskMat;
	/**
	 * mat集合
	 */
	private List<Mat> lastShowMatList;
	/**
	 * 整个mat
	 */
	private Mat wholeDrawMat;
	// private Mat lastCalcMaskMat;
	/**
	 * 当前画布
	 */
	private Canvas imgCanvas;
	/**
	 * 画笔的画布
	 */
	private Canvas backCanvas;
	// private Mat backMat;
	private double scale = 1;

	private MaskCanvasDelegate delegate;

	public Paint getDraw_Paint() {
		return draw_Paint;
	}

	public void setDraw_Paint(Paint draw_Paint) {
		this.draw_Paint = draw_Paint;
	}

	public int getCanvasWidth() {
		return canvasWidth;
	}

	public void setCanvasWidth(int canvasWidth) {
		this.canvasWidth = canvasWidth;
	}

	public int getCanvasHeight() {
		return canvasHeight;
	}

	public void setCanvasHeight(int canvasHeight) {
		this.canvasHeight = canvasHeight;
	}

	public Mat getBgdModel() {
		return bgdModel;
	}

	public void setBgdModel(Mat bgdModel) {
		this.bgdModel = bgdModel;
	}

	public Mat getForeModel() {
		return foreModel;
	}

	public void setForeModel(Mat foreModel) {
		this.foreModel = foreModel;
	}

	public Mat getLastMaskMat() {
		return lastMaskMat;
	}

	public void setLastMaskMat(Mat lastMaskMat) {
		this.lastMaskMat = lastMaskMat;
	}

	public Canvas getImgCanvas() {
		return imgCanvas;
	}

	public void setImgCanvas(Canvas imgCanvas) {
		this.imgCanvas = imgCanvas;
	}

	public Canvas getBackCanvas() {
		return backCanvas;
	}

	public void setBackCanvas(Canvas backCanvas) {
		this.backCanvas = backCanvas;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public Bitmap getOrigMaskBitmap() {
		return origMaskBitmap;
	}

	public void setOrigMaskBitmap(Bitmap origMaskBitmap) {
		this.origMaskBitmap = origMaskBitmap;
	}

	public boolean isPainting() {
		return isPainting;
	}

	public void setScaledSourceMat(Mat scaledSourceMat) {
		this.scaledSourceMat = scaledSourceMat;
	}

	public void setPainting(boolean isPainting) {
		this.isPainting = isPainting;
	}

	// private List<Point> linePoints = new ArrayList<Point>();

	public MaskCanvas(Context context) {
		super(context);
		initPaint();
	}

	public MaskCanvas(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
	}

	public MaskCanvas(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initPaint();
	}

	// public void setParent(PhotoEditPage parent) {
	// this.parent = parent;
	// }
	/**
	 * @description: 设置画笔和橡皮
	 * @exception
	 */
	public void refreshPaintStroke(float scale) {
		// 设置画笔参数
		if (draw_Paint == null) {
			draw_Paint = new Paint();
			draw_Paint.setAlpha(128);

			draw_Paint.setStyle(Style.STROKE);
			// paint.setStyle(Style.FILL);
			draw_Paint.setAntiAlias(true);
			draw_Paint.setColor(color);
			draw_Paint.setStrokeWidth(strokeWidth);
			draw_Paint.setStrokeJoin(Paint.Join.ROUND);
			draw_Paint.setStrokeCap(Paint.Cap.ROUND);

		}
		// 设置橡皮
		if (erase_Paint == null) {
			erase_Paint = new Paint();
			erase_Paint.setAlpha(0);
			erase_Paint.setColor(Color.TRANSPARENT);
			erase_Paint.setXfermode(new PorterDuffXfermode(
					PorterDuff.Mode.DST_IN));
			erase_Paint.setAntiAlias(true);

			erase_Paint.setDither(true);
			erase_Paint.setStyle(Style.STROKE);
			erase_Paint.setStrokeJoin(Paint.Join.ROUND);
			erase_Paint.setStrokeCap(Paint.Cap.ROUND);
			erase_Paint.setStrokeWidth(50);

			// set path
			mPath = new Path();
		}
		draw_Paint.setStrokeWidth(strokeWidth / scale);
		erase_Paint.setStrokeWidth(50 / scale);
	}

	/**
	 * @description: 初始化画笔和mat
	 * @exception
	 */
	public void initPaint() {
		if (draw_Paint == null) {
			draw_Paint = new Paint();
			draw_Paint.setAlpha(128);

			draw_Paint.setStyle(Style.STROKE);
			// paint.setStyle(Style.FILL);
			draw_Paint.setAntiAlias(true);
			draw_Paint.setColor(color);
			draw_Paint.setStrokeWidth(strokeWidth);
			draw_Paint.setStrokeJoin(Paint.Join.ROUND);
			draw_Paint.setStrokeCap(Paint.Cap.ROUND);

		}

		if (erase_Paint == null) {
			erase_Paint = new Paint();
			erase_Paint.setAlpha(0);
			erase_Paint.setColor(Color.TRANSPARENT);
			erase_Paint.setXfermode(new PorterDuffXfermode(
					PorterDuff.Mode.DST_IN));
			erase_Paint.setAntiAlias(true);

			erase_Paint.setDither(true);
			erase_Paint.setStyle(Style.STROKE);
			erase_Paint.setStrokeJoin(Paint.Join.ROUND);
			erase_Paint.setStrokeCap(Paint.Cap.ROUND);
			erase_Paint.setStrokeWidth(50);

			// set path
			mPath = new Path();
		}

		bgdModel = new Mat();
		foreModel = new Mat();
		lastShowMatList = new ArrayList<Mat>();
		lastMaskMat = new Mat();
		wholeDrawMat = new Mat();
		// mBitmap = Bitmap.createBitmap(SCREEN_W, SCREEN_H, Config.ARGB_8888);
		// mCanvas = new Canvas();
		// mCanvas.setBitmap(mBitmap);

	}

	// private Rect mDrawableDst = new Rect();

	// private Bitmap subBitmap;

	// public void subBitmap() {
	// if (subBitmap == null) {
	// int width = mDrawableDst.width();
	// int height = mDrawableDst.height();
	// new1Bitmap = Bitmap.createScaledBitmap(new1Bitmap, width, height,
	// true);
	// int x = 0 - mDrawableDst.left;
	// int y = 0 - mDrawableDst.top;
	// int right = x + canvasWidth;
	// int bottom = y + canvasWidth;
	// subBitmap = Bitmap.createBitmap(new1Bitmap, x, y, right - x, bottom
	// - y);
	// }
	// }
	/**
	 * @description: 初始化画布
	 * @exception
	 */
	public void iniCanvas() {
		canvasWidth = this.getWidth();
		canvasHeight = this.getHeight();
		// new1Bitmap = Bitmap.createBitmap(canvasWidth, canvasHeight,
		// Config.ARGB_8888);
		// mDrawableDst.set(0, 0, canvasWidth, canvasHeight);
		new1Bitmap = OpenCVUtil.genClearBitmap(canvasHeight, canvasWidth);

	}

	// public void setImageSize(int w, int h, Bitmap bitmap) {
	// this.src = bitmap;
	// canvasWidth = w;
	// canvasHeight = h;
	// new1Bitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
	// // new1Bitmap=MediaHelper.findPropertiesBitmapForCancas(w, h, bitmap);
	// invalidate();
	// }
	/**
	 * @description: 设置画笔的类型
	 * @exception
	 */
	public void setPaintMode(int mode) {
		this.paintMode = mode;

	}

	/**
	 * @description: 得到画笔的类型
	 * @exception
	 */
	public int getPaintMode() {
		return paintMode;
	}

	public void clearBack() {
		if (backBitmap != null) {
			backBitmap.recycle();
			backBitmap = null;
		}
	}

	/**
	 * @description: 撤销
	 * @exception
	 */
	public void undo() {

		clearBack();

		if (lastShowMatList.size() < 1) {
			return;
		}
		Mat topMat = lastShowMatList.remove(lastShowMatList.size() - 1);
		topMat.release();
		lastMaskMat = null;

		Mat mask = new Mat();

		if (lastShowMatList.size() > 0) {
			mask = lastShowMatList.get(lastShowMatList.size() - 1);
			// Imgproc.resize(lastShowMat, mask, new org.opencv.core.Size(
			// canvasWidth, canvasHeight));
			// Core.bitwise_and(wholeDrawMat, lastShowMat, wholeDrawMat);

		}

		this.genDrawBitmap(mask.clone());

		invalidate();

		Message message2 = new Message();
		message2.what = MessageWhat.WHAT_REFRESH_BUTTON_STATE;
		pHandler.sendMessage(message2);

	}

	private void genDrawBitmap(Mat mask) {
		if (canvasHeight == 0) {
			canvasHeight = this.getHeight();
		}
		if (canvasWidth == 0) {
			canvasWidth = this.getWidth();
		}
		Mat result = new Mat(canvasHeight, canvasWidth, CvType.CV_8UC4);
		Scalar zeroScalar = Scalar.all(0);

		result.setTo(zeroScalar);
		if (mask.empty() == false) {
			result.setTo(drawScalar, mask);
		}
		mask.release();
		// if(new1Bitmap!=null&&!new1Bitmap.isRecycled()){
		// new1Bitmap.recycle();
		// new1Bitmap=null;
		// new1Bitmap= Bitmap.createBitmap(canvasWidth, canvasHeight,
		// Config.ARGB_8888);
		// }
		Utils.matToBitmap(result, new1Bitmap);
		result.release();

	}

	private boolean isClear = false;

	Message msg = new Message();

	// Canvas mCanvas = new Canvas();;
	/**
	 * 绘制内容
	 */
	@Override
	protected void onDraw(Canvas canvas) {

		if (new1Bitmap == null) {
			iniCanvas();
		}
		if (backBitmap == null) {
			canvasWidth = this.getWidth();
			canvasHeight = this.getHeight();
			backBitmap = OpenCVUtil.genBlackBitmap(canvasHeight, canvasWidth);
		}
		// Bitmap drawBitmap;
		// if (mDrawableDst.left == 0 && mDrawableDst.top == 0
		// && mDrawableDst.right == canvasWidth
		// && mDrawableDst.bottom == canvasHeight) {
		// drawBitmap = new1Bitmap;
		// } else {
		// subBitmap();
		// drawBitmap = subBitmap;
		// }

		if (imgCanvas == null) {
			imgCanvas = new Canvas();
			imgCanvas.setBitmap(new1Bitmap);
			// imgCanvas.drawColor(Color.TRANSPARENT);
		}
		if (backCanvas == null) {
			backCanvas = new Canvas();
			backCanvas.setBitmap(backBitmap);
			// imgCanvas.drawColor(Color.TRANSPARENT);
		}

		// canvas.drawColor(Color.TRANSPARENT);
		if (this.paintMode == CANVAS_MODE_DRAW_AUTO) {
			// 绘制魔法画笔内容
			draw_Paint.setColor(Color.WHITE);
			imgCanvas.drawPath(mPath, draw_Paint);
		}

		else if (this.paintMode == CANVAS_MODE_DRAW_MANUAL) {
			// 绘制画笔内容
			draw_Paint.setColor(this.drawColor);
			imgCanvas.drawPath(mPath, draw_Paint);
		} else {
			imgCanvas.drawPath(mPath, erase_Paint);

		}
		canvas.drawBitmap(new1Bitmap, 0, 0, null);

		super.onDraw(canvas);
	}

	public Bitmap getBitmap1() {
		return new1Bitmap;
	}

	/**
	 * @description:设置面部和脖子的红色区域
	 * @exception
	 */
	public void setBitmap1(Bitmap bit) {
		new1Bitmap = bit;
		// manualDraw();
		// lastShowMatList.add(mask.clone());
		//
		// if (lastShowMatList.size() > 5) {
		// lastShowMatList.remove(0);
		// }
		//
		// this.genDrawBitmap(mask);
		// if (imgCanvas == null) {
		// imgCanvas = new Canvas();
		// imgCanvas.setBitmap(new1Bitmap);
		// // imgCanvas.drawColor(Color.TRANSPARENT);
		// } else {
		// imgCanvas.setBitmap(new1Bitmap);
		// }
	}

	/**
	 * 手势操作
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		float x = event.getX();
		float y = event.getY();
		if (delegate == null) {
			return true;
		}
		switch (event.getAction()) {
		// 手指放下
		case MotionEvent.ACTION_DOWN:
			if (delegate.isBusy() == false) {
				touch_start(x, y);
				invalidate();
			}
			break;
		// 手指移动
		case MotionEvent.ACTION_MOVE:
			if (delegate.isBusy() == false) {
				touch_move(x, y);
				invalidate();
			}
			break;
		// 手指抬起
		case MotionEvent.ACTION_UP:
			if (delegate.isBusy() == false) {
				touch_up();
				// invalidate();
			}
			break;
		}
		return true;

	}

	private void touch_start(float x, float y) {

		float deltaX = 0;
		float deltaY = 0;
		if (x < this.canvasWidth * 0.20) {
			deltaX = 50;
		} else if (x > this.canvasWidth * 0.8) {
			deltaX = -50;
		}
		if (y < this.canvasHeight * 0.2) {
			deltaY = 50;
		} else if (y > this.canvasHeight * 0.8) {
			deltaY = -50;
		}

		// this.delegate.setViewContainerTranslate(deltaX,deltaY);

		mPath.reset();
		mPath.moveTo(x, y);
		mX = x;
		mY = y;
	}

	private void touch_move(float x, float y) {
		float dx = Math.abs(x - mX);
		float dy = Math.abs(y - mY);
		if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
			mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
			mX = x;
			mY = y;
		}
	}

	/**
	 * @description: 手指抬起
	 * @exception
	 */
	private void touch_up() {
		mPath.lineTo(mX, mY);
		// commit the path to our offscreen
		RectF rectF = new RectF();
		mPath.computeBounds(rectF, true);
		rectF = GraphicUtil.expandRectF(rectF, (float) 3.0, new Rect(0, 0,
				canvasWidth, canvasHeight));
		if (this.paintMode == CANVAS_MODE_DRAW_AUTO) {
			// 魔法画笔

			draw_Paint.setColor(Color.WHITE);
			imgCanvas.drawPath(mPath, draw_Paint);
			if (backBitmap == null) {
				return;
			}
			backCanvas.setBitmap(backBitmap);
			backCanvas.drawPath(mPath, draw_Paint);
			mPath.reset();

			// org.opencv.core.Rect cvRect = new
			// org.opencv.core.Rect((int)rectF.left,(int)(rectF.top),(int)(rectF.width()),(int)(rectF.height()));
			// this.delegate.resetViewContainerTranslate();

			createMask(rectF);
		}

		else if (this.paintMode == CANVAS_MODE_DRAW_MANUAL) {
			// 画笔
			draw_Paint.setColor(drawColor);
			imgCanvas.drawPath(mPath, draw_Paint);
			// backCanvas.setBitmap(backBitmap);
			// backCanvas.drawPath(mPath, draw_Paint);
			mPath.reset();

			// org.opencv.core.Rect cvRect = new
			// org.opencv.core.Rect((int)rectF.left,(int)(rectF.top),(int)(rectF.width()),(int)(rectF.height()));
			// this.delegate.resetViewContainerTranslate();

			manualDraw();
			clearBack();
		} else {
			imgCanvas.drawPath(mPath, erase_Paint);
			backCanvas.setBitmap(backBitmap);
			if (this.paintMode == CANVAS_MODE_ERASE_AUTO) {
				// 魔法擦除
				backCanvas.drawPath(mPath, draw_Paint);
			}

			mPath.reset();
			// handleErashHistory();

			// this.delegate.resetViewContainerTranslate();

			if (this.paintMode == CANVAS_MODE_ERASE_AUTO)
				// 魔法擦除
				createMask(rectF);
			else
				manualErase();

		}

		// this.delegate.getViewContainer().setScaleX((float) 1.0);
		// this.delegate.getViewContainer().setScaleY((float) 1.0);

		// kill this so we don't double draw
		// createMask();
	}

	Bitmap origMaskBitmap;

	public void manualDraw() {
		Mat mat = new Mat();// .CV_8UC1);
		Utils.bitmapToMat(new1Bitmap, mat);
		Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGRA2GRAY);
		Mat mask = new Mat();
		Imgproc.threshold(mat, mask, 3, 255, Imgproc.THRESH_BINARY);
		mat.release();
		lastShowMatList.add(mask);
		if (lastShowMatList.size() > 5) {
			lastShowMatList.get(0).release();
			lastShowMatList.remove(0);
		}

		this.genDrawBitmap(mask.clone());

		invalidate();
		Message message2 = new Message();
		message2.what = MessageWhat.WHAT_REFRESH_BUTTON_STATE;
		pHandler.sendMessage(message2);

	}

	public void manualErase() {

		Mat mat = new Mat();// .CV_8UC1);

		Utils.bitmapToMat(new1Bitmap, mat);

		Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGRA2GRAY);

		Mat mask = new Mat();
		Imgproc.threshold(mat, mask, 3, 255, Imgproc.THRESH_BINARY);

		mat.release();

		lastShowMatList.add(mask);
		if (lastShowMatList.size() > 5) {
			lastShowMatList.remove(0);
		}

		// if(lastMaskMat.empty()==false){
		//
		// Mat eraseMask=getPaintMask();
		// Scalar zeroScalar = Scalar.all(0);
		//
		// lastMaskMat.setTo(zeroScalar,eraseMask);
		//
		// eraseMask.release();
		//
		// }

		Message message2 = new Message();
		message2.what = MessageWhat.WHAT_REFRESH_BUTTON_STATE;
		pHandler.sendMessage(message2);

	}

	/**
	 * @description:创建mask
	 * @exception
	 */
	public void createMask(final RectF roi) {

		new Thread(new Runnable() {
			@Override
			public void run() {

				Message message = new Message();
				message.what = MessageWhat.WHAT_SHOW_DIALOG;

				pHandler.sendMessage(message);

				// double scaleX=scaledSourceMat.cols()/(canvasWidth+0.0);
				// double scaleY=scaledSourceMat.rows()/(canvasHeight+0.0);
				// RectF scaledRect=GraphicUtil.scaleRectF(roi, scaleX, scaleY);
				// org.opencv.core.Rect cvRect = new
				// org.opencv.core.Rect((int)scaledRect.left,(int)scaledRect.top,(int)scaledRect.width(),(int)scaledRect.height());
				org.opencv.core.Rect cvRect = new org.opencv.core.Rect();
				Mat curMask = calcMask();

				doGrab(curMask, cvRect);

				pHandler.post(new Runnable() {

					@Override
					public void run() {

						// invalidate();
						Message message = new Message();
						message.what = MessageWhat.WHAT_CLOSE_DIALOG;

						pHandler.sendMessage(message);

						Message message2 = new Message();
						message2.what = MessageWhat.WHAT_REFRESH_BUTTON_STATE;
						pHandler.sendMessage(message2);

					}
					//
				});

			}
		}).start();
		;
		// new Thread().start();

	}

	private Mat calcMask() {

		Mat mask = getPaintMask();

		Mat result = new Mat(scaledSourceMat.size(), CvType.CV_8U);

		if (lastMaskMat != null && lastMaskMat.empty() == false) {
			result = lastMaskMat;

		} else {
			// 2--means
			result.setTo(Scalar.all(2));
			// result.setTo(Scalar.all(3), mask);
		}

		// if(lastShowMatList.size()>0){
		// Mat lastMat=lastShowMatList.get(lastShowMatList.size()-1);
		// Mat lastScaledMat=new Mat();
		// Imgproc.resize(lastMat, lastScaledMat, result.size());
		// result.setTo(Scalar.all(1), lastScaledMat);
		//
		// }
		// else{
		// result.setTo(Scalar.all(2));
		// }
		if (result == null || mask == null) {
			return null;
		}
		if (this.paintMode == CANVAS_MODE_DRAW_AUTO) {
			// 1--means foreground
			result.setTo(Scalar.all(1), mask);
		} else {
			// 0--means background
			result.setTo(Scalar.all(0), mask);
		}
		mask.release();
		return result;

	}

	/**
	 * @description:做grabcut运算
	 * @exception
	 */
	private void doGrab(Mat mask, org.opencv.core.Rect roi) {
		// lastCalcMaskMat=mask.clone();
		try {
			Imgproc.grabCut(scaledSourceMat, mask, roi, bgdModel, foreModel, 1,
					1);

		} catch (Exception e) {
			Log.e("grabCut error", e.toString());
			return;
		}
		lastMaskMat = mask.clone();

		Mat mask1 = new Mat();
		Mat mask2 = new Mat();

		Core.compare(mask, Scalar.all(3), mask1, Core.CMP_EQ);
		Core.compare(mask, Scalar.all(1), mask2, Core.CMP_EQ);

		mask.setTo(Scalar.all(0));
		mask.setTo(Scalar.all(255), mask1);
		mask.setTo(Scalar.all(255), mask2);

		// Core.bitwise_or(mask1, mask2, mask);

		mask1.release();
		mask2.release();

		// result.release();
		if (canvasWidth == 0) {
			canvasWidth = this.getWidth();
		}
		if (canvasHeight == 0) {
			canvasHeight = this.getHeight();
		}
		org.opencv.core.Size tarSize = new org.opencv.core.Size(canvasWidth,
				canvasHeight);

		mask = this.blurMask(mask, tarSize);

		lastShowMatList.add(mask.clone());

		if (lastShowMatList.size() > 5) {
			lastShowMatList.remove(0);
		}

		this.genDrawBitmap(mask);

	}

	/**
	 * @description:获取画笔的mat
	 * @exception
	 */
	private Mat getPaintMask() {

		Mat mat = new Mat();
		if (backBitmap == null) {
			return null;
		}
		Utils.bitmapToMat(backBitmap, mat);
		if (scaledSourceMat == null) {
			return null;
		}
		Imgproc.resize(mat, mat, scaledSourceMat.size());

		Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGRA2GRAY);

		Mat mask = new Mat();
		Imgproc.threshold(mat, mask, 3, 255, Imgproc.THRESH_BINARY);

		mat.release();

		clearBack();

		if (false) {

			Mat saveMat = mask.clone();

			Imgproc.cvtColor(saveMat, saveMat, Imgproc.COLOR_GRAY2BGRA);
			Bitmap bmp = Bitmap.createBitmap(saveMat.cols(), saveMat.rows(),
					Config.ARGB_8888);
			Utils.matToBitmap(saveMat, bmp);
			String path1 = RuntimeCache.getCurrentExternalImageFolder()
					+ File.separator + "orig_mask.jpg";
			try {
				FileOutputStream out1 = new FileOutputStream(path1);
				bmp.compress(CompressFormat.JPEG, 90, out1);
				out1.flush();
				out1.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mask;
	}

	public boolean isChoose() {
		if (lastShowMatList == null || lastShowMatList.size() < 1) {

			return false;
		}

		return true;

	}

	public Mat generateGrabCutResult() {
		// if(lastShowMatList==null||lastShowMatList.size()<1)
		// {
		// GrabCutCache.originalMask=sourceMat;
		//
		// return sourceMat;
		// }

		Mat mask = lastShowMatList.get(lastShowMatList.size() - 1);

		org.opencv.core.Size tarSize = sourceMat.size();

		Imgproc.resize(mask, mask, tarSize);

		// Mat mask=this.blurMask(scaledMask, tarSize);

		org.opencv.core.Rect croppedRect = calcCropRect(mask);

		// double inverseScale=1.0/scale;
		// org.opencv.core.Rect croppedRect=new
		// org.opencv.core.Rect((int)inverseScale*scaledCroppedRect.x,(int)inverseScale*scaledCroppedRect.y,(int)inverseScale*scaledCroppedRect.width,(int)inverseScale*scaledCroppedRect.height);

		Mat result = new Mat(sourceMat.size(), CvType.CV_8UC4);

		Scalar zeroScalar = Scalar.all(0);
		result.setTo(zeroScalar);

		sourceMat.copyTo(result, mask);

		GrabCutCache.originalMask = mask;

		return result.submat(croppedRect);

	}

	public Mat getInpaintMask() {
		Mat mask = lastShowMatList.get(lastShowMatList.size() - 1).clone();

		// mask=inPaintResize(mask);

		org.opencv.core.Size smallSize = scaledSourceMat.size();

		// mask=inPaintResize(mask,tarSize);
		Mat smallMask = new Mat();

		Imgproc.resize(mask, smallMask, smallSize);

		org.opencv.core.Rect scaledCroppedRect = calcCropRect(smallMask);

		double inverseScale = 1.0 / scale;
		org.opencv.core.Rect croppedRect = new org.opencv.core.Rect(
				(int) (inverseScale * scaledCroppedRect.x),
				(int) (inverseScale * scaledCroppedRect.y),
				(int) (inverseScale * scaledCroppedRect.width),
				(int) (inverseScale * scaledCroppedRect.height));

		Mat result = new Mat(sourceMat.size(), CvType.CV_8UC1);

		Scalar zeroScalar = Scalar.all(0);
		result.setTo(zeroScalar);

		Imgproc.resize(mask, mask, sourceMat.size());

		mask.submat(croppedRect).copyTo(result.submat(croppedRect));
		// mask.release();

		GrabCutCache.originalMask = result.clone();

		/*
		 * Mat diffMask=GrabCutCache.diffMask; Mat diffMaskLast=new
		 * Mat(sourceMat.size(),CvType.CV_8UC1);
		 * 
		 * 
		 * diffMaskLast.setTo(zeroScalar);
		 * 
		 * 
		 * diffMask.submat(croppedRect).copyTo(diffMaskLast.submat(croppedRect));
		 * 
		 * GrabCutCache.diffMask=diffMaskLast;
		 */

		Imgproc.resize(result, result, smallSize);
		Imgproc.resize(GrabCutCache.diffMask, GrabCutCache.diffMask,
				sourceMat.size());

		// Rect rect=new
		// Rect(croppedRect.x,croppedRect.y,croppedRect.width+croppedRect.x,croppedRect.height+croppedRect.y);

		GrabCutCache.originalRect = croppedRect;

		// Rect roiRect=GraphicUtil.expandRect(rect, (float) 2.0, new
		// Rect(0,0,sourceMat.cols(),sourceMat.rows()) );

		// TODO:
		GrabCutCache.roi = new Rect(0, 0, sourceMat.cols(), sourceMat.rows());

		return result;

	}

	private org.opencv.core.Rect calcCropRect(Mat mat) {
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Mat hierarchy = new Mat();

		Imgproc.findContours(mat, contours, hierarchy, Imgproc.RETR_EXTERNAL,
				Imgproc.CHAIN_APPROX_SIMPLE);

		org.opencv.core.Rect[] boundRect = new org.opencv.core.Rect[contours
				.size()];

		int count = 0;

		double max = 0;
		int maxIndex = 0;
		for (int i = 0; i < contours.size(); i++) {
			double area = Imgproc.contourArea(contours.get(i));

			if (area > max) {

				max = area;
				maxIndex = count;
			}
			boundRect[count] = Imgproc.boundingRect(contours.get(i));
			// minEnclosingCircle( (Mat)contours_poly[i], center[i], radius[i]
			// );

			count++;

		}

		Mat cMat = new Mat(mat.size(), CvType.CV_8UC1);
		cMat.setTo(Scalar.all(0));

		Core.drawContours(cMat, contours, maxIndex, Scalar.all(255));
		hierarchy.release();
		contours.clear();

		GrabCutCache.diffMask = cMat;

		return boundRect[maxIndex];

	}

	// public Mat doLastGrab(Mat mask){
	//
	// org.opencv.core.Rect cvRect = new org.opencv.core.Rect();
	//
	// //Mat mask=lastCalcMaskMat.clone();
	//
	//
	// Imgproc.resize(mask, mask, sourceMat.size());
	//
	//
	// bgdModel=new Mat();
	// foreModel=new Mat();
	// Imgproc.grabCut(sourceMat, mask, cvRect, bgdModel, foreModel,
	// 1, 1);
	//
	//
	// Mat mask1 = new Mat();
	// Mat mask2 = new Mat();
	//
	// Core.compare(mask, Scalar.all(3), mask1, Core.CMP_EQ);
	// Core.compare(mask, Scalar.all(1), mask2, Core.CMP_EQ);
	//
	// mask.setTo(Scalar.all(0));
	// mask.setTo(Scalar.all(255),mask1);
	// mask.setTo(Scalar.all(255),mask2);
	//
	// //Core.bitwise_or(mask1, mask2, mask);
	//
	// mask1.release();
	// mask2.release();
	//
	// if(true){
	//
	// Mat saveMat=mask.clone();
	//
	// Imgproc.cvtColor(saveMat, saveMat, Imgproc.COLOR_GRAY2BGRA);
	// Bitmap bmp = Bitmap.createBitmap(saveMat.cols(), saveMat.rows(),
	// Config.ARGB_8888);
	// Utils.matToBitmap(saveMat, bmp);
	// String path1 = RuntimeCache.getCurrentExternalImageFolder() +
	// File.separator
	// + "last_resut_mask.jpg";
	// try {
	// FileOutputStream out1 = new FileOutputStream(path1);
	// bmp.compress(CompressFormat.JPEG, 90, out1);
	// out1.flush();
	// out1.close();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// return mask;
	//
	//
	// }
	public void setMaskArea() {
		if (clickx < minx) {
			minx = clickx;
		}
		if (clickx > maxx) {
			maxx = clickx;
		}
		if (clicky < miny) {
			miny = clicky;
		}
		if (clicky > maxy) {
			maxy = clicky;
		}
	}

	public boolean isClear() {
		return isClear;
	}

	public void setClear(boolean isClear) {
		this.isClear = isClear;
	}

	public Handler getpHandler() {
		return pHandler;
	}

	public void setpHandler(Handler pHandler) {
		this.pHandler = pHandler;
	}

	public Mat getSourceMat() {
		return sourceMat;
	}

	public void setSourceMat(Mat source) {
		this.sourceMat = source;
		this.scaledSourceMat = new Mat();

		scale = calcScaleRatio(sourceMat.cols(), sourceMat.rows());
		Imgproc.resize(sourceMat, scaledSourceMat, new Size(sourceMat.cols()
				* scale, sourceMat.rows() * scale));
		// Imgproc.pyrDown(sourceMat, scaledSourceMat, new
		// Size(sourceMat.cols()*0.5, sourceMat.rows()*0.5));
		Imgproc.cvtColor(scaledSourceMat, scaledSourceMat,
				Imgproc.COLOR_BGRA2BGR);

		// Imgproc.cvtColor(scaledSourceMat, scaledSourceMat,
		// Imgproc.COLOR_BGRA2BGR);

	}

	private double calcScaleRatio(int width, int height) {

		double scale = 1.0;

		if (width < height) {
			scale = (MAX_GRAB_SIZE / (width + 0.0));

		} else {
			scale = (MAX_GRAB_SIZE / (height + 0.0));

		}
		return scale;
	}

	public int getHistoryCount() {

		return this.lastShowMatList.size();
	}

	// private boolean isDestroy;

	public void destory() {
		// if (isDestroy) {

		if (new1Bitmap != null && !new1Bitmap.isRecycled()) {
			new1Bitmap.recycle();
			new1Bitmap = null;
		}
		for (Mat mat : this.lastShowMatList) {
			mat.release();
		}
		this.lastShowMatList.clear();
		this.wholeDrawMat.release();
		// }

	}

	public void setLastShowMatList(List<Mat> lastShowMatList) {
		// TODO Auto-generated method stub
		this.lastShowMatList = lastShowMatList;
		// lastShowMatList = new ArrayList<Mat>();
	}

	public List<Mat> getLastShowMatList() {
		return lastShowMatList;
	}

	public void setWholeDrawMat(Mat wholeDrawMat) {
		// TODO Auto-generated method stub
		this.wholeDrawMat = wholeDrawMat;
	}

	public Mat getWholeDrawMat() {

		return wholeDrawMat;
	}

	public void setBackBitmap(Bitmap backBitmap) {
		this.backBitmap = backBitmap;
	}

	public Bitmap getBackBitmap() {
		return backBitmap;
	}

	private Mat blurMask(Mat mask, org.opencv.core.Size tarSize) {

		int k = 13;
		int m = (k - 1) / 2;
		// Imgproc.copyMakeBorder(mask,mask, (k-1)/2,(k-1)/2 ,0, 0, 0);
		org.opencv.core.Rect cvRect = new org.opencv.core.Rect(m, m,
				(int) (tarSize.width - m * 2), (int) (tarSize.height - m * 2));
		Mat lmask = new Mat(tarSize, CvType.CV_8UC1);
		lmask.setTo(Scalar.all(0));

		Imgproc.resize(mask, mask, cvRect.size());
		mask.copyTo(lmask.submat(cvRect));

		// Imgproc.blur(lmask, mask, new org.opencv.core.Size(k,k),new
		// org.opencv.core.Point(-1,-1));

		Imgproc.GaussianBlur(lmask, mask, new org.opencv.core.Size(k, k), 0);

		return mask;
	}

	// private Mat inPaintResize(Mat mask, org.opencv.core.Size tarSize) {
	//
	// int k = 13;
	// int m = (k - 1) / 2;
	// // Imgproc.copyMakeBorder(mask,mask, (k-1)/2,(k-1)/2 ,0, 0, 0);
	// org.opencv.core.Size cvSize = new org.opencv.core.Size(
	// (int) (tarSize.width + 2 * m), (int) (tarSize.height + 2 * m));
	// org.opencv.core.Rect cvRect = new org.opencv.core.Rect(m, m,
	// (int) (tarSize.width), (int) (tarSize.height));
	// Mat smask = new Mat(tarSize, CvType.CV_8UC1);
	// smask.setTo(Scalar.all(0));
	//
	// Mat lmask = new Mat();
	//
	// Imgproc.resize(mask, lmask, cvSize);
	// lmask.submat(cvRect).copyTo(smask);
	//
	// // Imgproc.blur(lmask, mask, new org.opencv.core.Size(k,k),new
	// // org.opencv.core.Point(-1,-1));
	//
	// // Imgproc.GaussianBlur(lmask, mask, new org.opencv.core.Size(k,k), 0);
	//
	// Imgproc.resize(mask, mask, tarSize);
	// Mat dmask = new Mat();
	//
	// Core.subtract(smask, mask, dmask);
	//
	// // Imgproc.GaussianBlur(dmask, dmask, new org.opencv.core.Size(k,k), 0);
	//
	// GrabCutCache.diffMask = dmask;
	//
	// return smask;
	//
	// /*
	// * Imgproc.resize(mask, mask,tarSize); Mat lmask=new Mat();
	// *
	// * Imgproc.GaussianBlur(mask, lmask, new org.opencv.core.Size(k,k), 0);
	// *
	// * //Imgproc.threshold(lmask, lmask, 130, 255, Imgproc.THRESH_BINARY);
	// * // Mat dmask=new Mat();
	// *
	// * Core.subtract(lmask, mask, dmask);
	// *
	// *
	// * GrabCutCache.diffMask=dmask;
	// *
	// * return lmask;
	// */
	//
	// }

	// private Mat inPaintResize(Mat mask) {
	//
	// int k = 13;
	// int m = 13;
	//
	// org.opencv.core.Size tarSize = mask.size();
	// // Imgproc.copyMakeBorder(mask,mask, (k-1)/2,(k-1)/2 ,0, 0, 0);
	// org.opencv.core.Size cvSize = new org.opencv.core.Size(
	// (int) (tarSize.width + 2 * m), (int) (tarSize.height + 2 * m));
	// org.opencv.core.Rect cvRect = new org.opencv.core.Rect(m, m,
	// (int) (tarSize.width), (int) (tarSize.height));
	// Mat smask = new Mat(tarSize, CvType.CV_8UC1);
	// smask.setTo(Scalar.all(0));
	//
	// Mat lmask = new Mat();
	//
	// Imgproc.resize(mask, lmask, cvSize);
	// lmask.submat(cvRect).copyTo(smask);
	//
	// // Imgproc.blur(lmask, mask, new org.opencv.core.Size(k,k),new
	// // org.opencv.core.Point(-1,-1));
	//
	// // Imgproc.GaussianBlur(lmask, mask, new org.opencv.core.Size(k,k), 0);
	//
	// // Imgproc.resize(mask, mask,tarSize);
	// Mat dmask = new Mat();
	//
	// Core.subtract(smask, mask, dmask);
	//
	// // Imgproc.GaussianBlur(dmask, dmask, new org.opencv.core.Size(k,k), 0);
	// Imgproc.resize(dmask, dmask, sourceMat.size());
	// GrabCutCache.diffMask = dmask;
	//
	// return smask;
	//
	// /*
	// * Imgproc.resize(mask, mask,tarSize); Mat lmask=new Mat();
	// *
	// * Imgproc.GaussianBlur(mask, lmask, new org.opencv.core.Size(k,k), 0);
	// *
	// * //Imgproc.threshold(lmask, lmask, 130, 255, Imgproc.THRESH_BINARY);
	// * // Mat dmask=new Mat();
	// *
	// * Core.subtract(lmask, mask, dmask);
	// *
	// *
	// * GrabCutCache.diffMask=dmask;
	// *
	// * return lmask;
	// */
	//
	// }

	public Mat getScaledSourceMat() {
		return scaledSourceMat;
	}

	public MaskCanvasDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(MaskCanvasDelegate delegate) {
		this.delegate = delegate;
	}

	public void setMatSize(int width, int height) {
		// TODO Auto-generated method stub
		// canvasHeight=height;
		// canvasWidth=width;
		// new1Bitmap = OpenCVUtil.genClearBitmap(canvasHeight, canvasWidth);
	}

}
