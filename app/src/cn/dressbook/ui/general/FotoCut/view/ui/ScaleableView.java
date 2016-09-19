package cn.dressbook.ui.general.FotoCut.view.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ScaleableView extends View {

	public ScaleableView(Context context) {
		super(context);
	}

	public ScaleableView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScaleableView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	private View effectView;
	private MaskCanvas canvas;

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

	protected float oriRationWH = 1;// 原始宽高比率
	protected final float maxZoomOut = 6.0f;// 最大扩大到多少倍
	protected final float minZoomIn = 0.333333f;// 最小缩小到多少倍

	protected Rect mDrawableSrc;
	protected Rect mDrawableDst;

//	float pointCenterX;
//	float pointCenterY;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		if (event.getPointerCount() > 1) {
			if (mStatus == STATUS_Touch_SINGLE) {
				mStatus = STATUS_TOUCH_MULTI_START;

				oldx_0 = event.getX(0);
				oldy_0 = event.getY(0);

				oldx_1 = event.getX(1);
				oldy_1 = event.getY(1);
			} else if (mStatus == STATUS_TOUCH_MULTI_START) {
				mStatus = STATUS_TOUCH_MULTI_TOUCHING;
			}
		} else {
			if (mStatus == STATUS_TOUCH_MULTI_START
					|| mStatus == STATUS_TOUCH_MULTI_TOUCHING) {
				oldx_0 = 0;
				oldy_0 = 0;

				oldx_1 = 0;
				oldy_1 = 0;
//				pointCenterX=(float) ((oldx_0+oldx_1)*0.5);
//				pointCenterY=(float) ((oldy_0+oldy_1)*0.5);

				oldX = event.getX();
				oldY = event.getY();
			}
			mStatus = STATUS_Touch_SINGLE;
		}

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// Log.v("count ACTION_DOWN", "-------");
			oldX = event.getX();
			oldY = event.getY();
			break;

		case MotionEvent.ACTION_UP:
			// Log.v("count ACTION_UP", "-------");
			// checkBounds();
			break;

		case MotionEvent.ACTION_POINTER_1_DOWN:
			// Log.v("count ACTION_POINTER_1_DOWN", "-------");
			break;

		case MotionEvent.ACTION_POINTER_UP:
			// Log.v("count ACTION_POINTER_UP", "-------");
			break;

		case MotionEvent.ACTION_MOVE:
			// Log.v("count ACTION_MOVE", "-------");
			if (mStatus == STATUS_TOUCH_MULTI_TOUCHING) {
				float newx_0 = event.getX(0);
				float newy_0 = event.getY(0);

				float newx_1 = event.getX(1);
				float newy_1 = event.getY(1);
				
				

				float oldWidth = Math.abs(oldx_1 - oldx_0);
				float oldHeight = Math.abs(oldy_1 - oldy_0);

				float newWidth = Math.abs(newx_1 - newx_0);
				float newHeight = Math.abs(newy_1 - newy_0);

				float hD = Math.abs(newHeight - oldHeight);
				float hW = Math.abs(newWidth - oldWidth);
				if (hD > 10f || hW > 10f) {
					boolean isDependHeight = hD > hW;

					float ration = isDependHeight ? ((float) newHeight / (float) oldHeight)
							: ((float) newWidth / (float) oldWidth);
					int centerX = mDrawableDst.centerX();
					int centerY = mDrawableDst.centerY();
					int _newWidth = (int) (mDrawableDst.width() * ration);
					int _newHeight = (int) ((float) _newWidth / oriRationWH);

					float tmpZoomRation = (float) _newWidth
							/ (float) mDrawableSrc.width();
					if (tmpZoomRation >= maxZoomOut) {
						_newWidth = (int) (maxZoomOut * mDrawableSrc.width());
						_newHeight = (int) ((float) _newWidth / oriRationWH);
						setViewContainerScale((int)centerX, (int)centerY, maxZoomOut);
					} else if (tmpZoomRation <= minZoomIn) {
						_newWidth = (int) (minZoomIn * mDrawableSrc.width());
						_newHeight = (int) ((float) _newWidth / oriRationWH);
						setViewContainerScale((int)centerX, (int)centerY, minZoomIn);
					} else {
						setViewContainerScale((int)centerX, (int)centerY, tmpZoomRation);
					}

					mDrawableDst.set(centerX - _newWidth / 2, centerY
							- _newHeight / 2, centerX + _newWidth / 2, centerY
							+ _newHeight / 2);

					oldx_0 = newx_0;
					oldy_0 = newy_0;

					oldx_1 = newx_1;
					oldy_1 = newy_1;
				}
			} else if (mStatus == STATUS_Touch_SINGLE) {
				int dx = (int) (event.getX() - oldX);
				int dy = (int) (event.getY() - oldY);

				oldX = event.getX();
				oldY = event.getY();

				if (!(dx == 0 && dy == 0)) {
					setViewContainerTranslate(dx, dy);
					mDrawableDst.offset((int) dx, (int) dy);
				}
			}
			break;
		}

		return true;

	}

	public View getEffectView() {
		return effectView;
	}

	public void setEffectView(View effectView) {
		this.effectView = effectView;
		if (mDrawableDst == null) {
			mDrawableDst = new Rect();
			mDrawableSrc = new Rect();
//			System.out.println("width  is:"+this.effectView.getWidth());
//			System.out.println("height  is:"+this.effectView.getHeight());
			mDrawableDst.set(0, 0, this.effectView.getWidth(), this.effectView.getHeight());
			mDrawableSrc.set(mDrawableDst);
		}
	}
	
	private float origX = 0;
	private float origY = 0;

	public void setViewContainerTranslate(float x, float y) {
		origX = this.effectView.getX();
		origY = this.effectView.getY();

		this.effectView.setX(origX + x);
		this.effectView.setY(origY + y);
		effectView.invalidate();
	}

	public void setViewContainerScale(int center_x, int center_y, float scale) {
//		System.out.println("center x is:"+center_x);
//		System.out.println("center y is:"+center_y);
//		System.out.println("scale  is:"+scale);
//		this.effectView.setPivotX(center_x);
//		this.effectView.setPivotY(center_y);
		this.effectView.setScaleX(scale);
		this.effectView.setScaleY(scale);
		canvas.refreshPaintStroke(scale);
		effectView.invalidate();
	}

	public MaskCanvas getCanvas() {
		return canvas;
	}

	public void setCanvas(MaskCanvas canvas) {
		this.canvas = canvas;
	}

}
