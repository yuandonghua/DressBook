package cn.dressbook.ui.general.FotoCut.view.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import cn.dressbook.ui.general.FotoCut.util.GrabCutCache;

/**
 * 底图缩放，浮层不变
 * 
 * @author yanglonghui
 * 
 */
public class AdvanceImageView extends View {

	// 单点触摸的时候
	private float oldX = 0;
	private float oldY = 0;

	// 多点触摸的时候
	private float oldx_0 = 0;
	private float oldy_0 = 0;

	private float oldx_1 = 0;
	private float oldy_1 = 0;
	

	// 状态
	private final int STATUS_Touch_SINGLE = 1;// 单点
	private final int STATUS_TOUCH_MULTI_START = 2;// 多点开始
	private final int STATUS_TOUCH_MULTI_TOUCHING = 3;// 多点拖拽中
	private final int STATUS_Touch_SINGLE_MOVING = 4;// 单点

	private int mStatus = STATUS_Touch_SINGLE;

	// 默认的裁剪图片宽度与高度
	private final int defaultCropWidth = 300;
	private final int defaultCropHeight = 300;
	private int cropWidth = defaultCropWidth;
	private int cropHeight = defaultCropHeight;

	protected float oriRationWH = 0;// 原始宽高比率
	protected final float maxZoomOut = 6.0f;// 最大扩大到多少倍
	protected final float minZoomIn = 0.333333f;// 最小缩小到多少倍

	protected Drawable mDrawable;// 原图
	protected Drawable mFloatDrawable;
	// protected FloatDrawable mFloatDrawable;//浮层
	protected Rect mDrawableSrc = new Rect();
	protected Rect mDrawableDst = new Rect();
	protected Rect mDrawableFloat = new Rect();// 浮层选择框，就是头像选择框
	protected boolean isFrist = true;

	protected Context mContext;

	private static final double PI = 3.14159265359;

	private AdvanceImageViewDelegate delegate;

	public AdvanceImageView(Context context) {
		super(context);
		init(context);
	}

	public AdvanceImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public AdvanceImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);

	}

	private void init(Context context) {
		this.mContext = context;
		try {
			if (android.os.Build.VERSION.SDK_INT >= 11) {
				this.setLayerType(LAYER_TYPE_SOFTWARE, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// mFloatDrawable=new FloatDrawable(context);//头像选择框
	}

	public void setDrawable(Drawable mDrawable, Drawable mFDrawable) {
		this.mDrawable = mDrawable;
		this.mFloatDrawable = mFDrawable;

		// this.cropWidth=cropWidth;
		// this.cropHeight=cropHeight;
		this.isFrist = true;
		invalidate();
	}

//	double lastComAngle;
//	int lastImgAngle;
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//
//		if (event.getPointerCount() > 1) {
//			if (mStatus == STATUS_Touch_SINGLE
//					|| mStatus == STATUS_Touch_SINGLE_MOVING) {
//				mStatus = STATUS_TOUCH_MULTI_START;
//
//				oldx_0 = event.getX(0);
//				oldy_0 = event.getY(0);
//
//				oldx_1 = event.getX(1);
//				oldy_1 = event.getY(1);
//
//			} else if (mStatus == STATUS_TOUCH_MULTI_START) {
//				mStatus = STATUS_TOUCH_MULTI_TOUCHING;
//			}
//		} else {
//			if (mStatus == STATUS_TOUCH_MULTI_START
//					|| mStatus == STATUS_TOUCH_MULTI_TOUCHING) {
//				oldx_0 = 0;
//				oldy_0 = 0;
//
//				oldx_1 = 0;
//				oldy_1 = 0;
//
//				oldX = event.getX();
//				oldY = event.getY();
//
//			}
//			// if(mStatus==STATUS_Touch_SINGLE
//			// ||mStatus==STATUS_Touch_SINGLE_MOVING){
//			//
//			// }
//			// else{
//			mStatus = STATUS_Touch_SINGLE;
//
//			// }
//
//		}
//
//		// Log.v("count currentTouch"+currentTouch, "-------");
//
//		switch (event.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//			// Log.v("count ACTION_DOWN", "-------");
//			oldX = event.getX();
//			oldY = event.getY();
//			break;
//
//		case MotionEvent.ACTION_UP:
//			// Log.v("count ACTION_UP", "-------");
//			checkBounds();
//
//			// if(mStatus==STATUS_Touch_SINGLE){
//			// delegate.viewDone(this);
//			// }
//			break;
//
//		case MotionEvent.ACTION_POINTER_1_DOWN:
//			// Log.v("count ACTION_POINTER_1_DOWN", "-------");
//			break;
//
//		case MotionEvent.ACTION_POINTER_UP:
//			// Log.v("count ACTION_POINTER_UP", "-------");
//			break;
//
//		case MotionEvent.ACTION_MOVE:
//			// Log.v("count ACTION_MOVE", "-------");
//			if (mStatus == STATUS_TOUCH_MULTI_TOUCHING) {
//				float newx_0 = event.getX(0);
//				float newy_0 = event.getY(0);
//
//				float newx_1 = event.getX(1);
//				float newy_1 = event.getY(1);
//				
//				lastImgAngle = (int) this.getRotation();
//
//				float oldWidth = Math.abs(oldx_1 - oldx_0);
//				float oldHeight = Math.abs(oldy_1 - oldy_0);
//
//				float newWidth = Math.abs(newx_1 - newx_0);
//				float newHeight = Math.abs(newy_1 - newy_0);
//
//				boolean isDependHeight = Math.abs(newHeight - oldHeight) > Math
//						.abs(newWidth - oldWidth);
//
//				float ration = isDependHeight ? ((float) newHeight / (float) oldHeight)
//						: ((float) newWidth / (float) oldWidth);
//				int centerX = mDrawableDst.centerX();
//				int centerY = mDrawableDst.centerY();
//				int _newWidth = (int) (mDrawableDst.width() * ration);
//				int _newHeight = (int) ((float) _newWidth / oriRationWH);
//
//				float tmpZoomRation = (float) _newWidth
//						/ (float) mDrawableSrc.width();
//				if (tmpZoomRation >= maxZoomOut) {
//					_newWidth = (int) (maxZoomOut * mDrawableSrc.width());
//					_newHeight = (int) ((float) _newWidth / oriRationWH);
//				} else if (tmpZoomRation <= minZoomIn) {
//					_newWidth = (int) (minZoomIn * mDrawableSrc.width());
//					_newHeight = (int) ((float) _newWidth / oriRationWH);
//				}
//
//				mDrawableDst.set(centerX - _newWidth / 2, centerY - _newHeight
//						/ 2, centerX + _newWidth / 2, centerY + _newHeight / 2);
//				
//				invalidate();
//
//				// Log.e("width():"+(mDrawableSrc.width())+"height():"+(mDrawableSrc.height()),
//				// "new width():"+(mDrawableDst.width())+"new height():"+(mDrawableDst.height()));
//				// Log.e(""+(float)mDrawableSrc.height()/(float)mDrawableSrc.width(),
//				// "mDrawableDst:"+(float)mDrawableDst.height()/(float)mDrawableDst.width());
//
//				oldx_0 = newx_0;
//				oldy_0 = newy_0;
//
//				oldx_1 = newx_1;
//				oldy_1 = newy_1;
//
//			} else if (mStatus == STATUS_Touch_SINGLE) {
//
//				// mStatus=STATUS_Touch_SINGLE_MOVING;
//				int dx = (int) (event.getX() - oldX);
//				int dy = (int) (event.getY() - oldY);
//
//				oldX = event.getX();
//				oldY = event.getY();
//
//				if (!(dx == 0 && dy == 0)) {
//					mDrawableDst.offset((int) dx, (int) dy);
//					invalidate();
//				}
//			}
//			break;
//		}
//
//		// Log.v("event.getAction()："+event.getAction()+"count："+event.getPointerCount(),
//		// "-------getX:"+event.getX()+"--------getY:"+event.getY());
//		return false;
//	}

	@Override
	protected void onDraw(Canvas canvas) {
		// super.onDraw(canvas);
		if (mDrawable == null) {
			return; // couldn't resolve the URI
		}

		if (mDrawable.getIntrinsicWidth() == 0
				|| mDrawable.getIntrinsicHeight() == 0) {
			return; // nothing to draw (empty bounds)
		}

		configureBounds();

		// Log.v("mTempDst", "---------"+mDrawableDst);
		// Log.v("mTempDst:", "---------"+mDrawable.getBounds().width());
		// Log.v("mTempDst:", "---------"+mDrawable.getBounds().height());
		// Log.v("mTempDst:",
		// "-------ration:"+(float)mDrawable.getBounds().width()/(float)mDrawable.getBounds().height());

		mDrawable.draw(canvas);
//		canvas.save();
//		canvas.clipRect(mDrawableFloat, Region.Op.DIFFERENCE);
//		canvas.drawColor(Color.parseColor("#a0000000"));
//		canvas.restore();
//		mFloatDrawable.draw(canvas);
	}

	private float currentDegree = 0;

	protected void configureBounds() {
		if (isFrist) {
			//
			oriRationWH = ((float) mDrawable.getIntrinsicWidth())
					/ ((float) mDrawable.getIntrinsicHeight());
			float scale = (((float) getWidth() / (float) mFloatDrawable
					.getIntrinsicWidth()));
			// final float scale =
			// mContext.getResources().getDisplayMetrics().density;
			// int w=Math.min(getWidth(),
			// (int)(mDrawable.getIntrinsicWidth()*scale+0.5f));
			// int h=(int) (w/oriRationWH);

			int left = (int) (0 - GrabCutCache.roi.left * scale);
			int top = (int) (0 - GrabCutCache.roi.top * scale);
			int right = (int) (left + mDrawable.getIntrinsicWidth() * scale + 0.5f);
			int bottom = (int) (top + mDrawable.getIntrinsicHeight() * scale + 0.5f);

			mDrawableSrc.set(left, top, right, bottom);
			mDrawableDst.set(mDrawableSrc);
			//
			// int floatWidth=dipTopx(mContext, cropWidth);
			// int floatHeight=dipTopx(mContext, cropHeight);
			//
			// if(floatWidth>getWidth())
			// {
			// floatWidth=getWidth();
			// floatHeight=cropHeight*floatWidth/cropWidth;
			// }
			//
			// if(floatHeight>getHeight())
			// {
			// floatHeight=getHeight();
			// floatWidth=cropWidth*floatHeight/cropHeight;
			// }
			//
			// int floatLeft=(getWidth()-floatWidth)/2;
			// int floatTop = (getHeight()-floatHeight)/2;
			mDrawableFloat.set(0, 0, getWidth(), getHeight());

			isFrist = false;
		}

//		if (currentDegree != 0) {
//			BitmapDrawable bd = (BitmapDrawable) mDrawable;
//			int width=mDrawable.getIntrinsicWidth();
//			int height=mDrawable.getIntrinsicHeight();
//			Bitmap bm = bd.getBitmap();
//			Matrix m = new Matrix();
//			m.postRotate(currentDegree);
//			
//			Bitmap result = Bitmap.createBitmap(bm, 0, 0, width,
//					height, m, true);
//			if(lastBitmap!=null){
//				lastBitmap.recycle();
//				lastBitmap=null;
//			}
//			lastBitmap=result;
//			bm.recycle();
//			bm = null;
//			mDrawable = new BitmapDrawable(getResources(), lastBitmap);
//		}

		mDrawable.setBounds(mDrawableDst);
		mFloatDrawable.setBounds(mDrawableFloat);
	}
	
	private Bitmap lastBitmap;

	protected void checkBounds() {
		int newLeft = mDrawableDst.left;
		int newTop = mDrawableDst.top;

		boolean isChange = false;
		if (mDrawableDst.left < -mDrawableDst.width()) {
			newLeft = -mDrawableDst.width();
			isChange = true;
		}

		if (mDrawableDst.top < -mDrawableDst.height()) {
			newTop = -mDrawableDst.height();
			isChange = true;
		}

		if (mDrawableDst.left > getWidth()) {
			newLeft = getWidth();
			isChange = true;
		}

		if (mDrawableDst.top > getHeight()) {
			newTop = getHeight();
			isChange = true;
		}

		mDrawableDst.offsetTo(newLeft, newTop);
		if (isChange) {
			invalidate();
		}
	}

	public Bitmap getResultImage(int dstWidth, int dstHeight) {
		Bitmap tmpBitmap = Bitmap.createBitmap(getWidth(), getHeight(),
				Config.ARGB_8888);
		Canvas canvas = new Canvas(tmpBitmap);
		// mDrawable.draw(canvas);
		canvas.setMatrix(this.getMatrix());
		this.draw(canvas);
		
		// Matrix matrix=new Matrix();
		// float
		// scale=(float)(mDrawableSrc.width())/(float)(mDrawableDst.width());
		// matrix.postScale(scale, scale);
		//
		// Bitmap ret = Bitmap.createBitmap(tmpBitmap, mDrawableFloat.left,
		// mDrawableFloat.top, mDrawableFloat.width(), mDrawableFloat.height(),
		// matrix, true);
		// tmpBitmap.recycle();
		// tmpBitmap=null;

		Bitmap newRet = Bitmap.createScaledBitmap(tmpBitmap, dstWidth,
				dstHeight, true);
		tmpBitmap.recycle();
		tmpBitmap = newRet;

		return newRet;
	}

	public int dipTopx(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public AdvanceImageViewDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(AdvanceImageViewDelegate delagate) {
		this.delegate = delagate;
	}

	private float getDistance(float x1, float y1, float x2, float y2) {
		float v = ((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2));
		return ((int) (Math.sqrt(v) * 100)) / 100f;
	}

}
