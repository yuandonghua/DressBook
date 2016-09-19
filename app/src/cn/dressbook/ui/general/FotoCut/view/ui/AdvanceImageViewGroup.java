package cn.dressbook.ui.general.FotoCut.view.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cn.dressbook.ui.R;


public class AdvanceImageViewGroup extends LinearLayout implements
		OnTouchListener {

	private ImageView processImage;
	private ImageView coverImage;
	protected Drawable mDrawable;// 原图
	protected Drawable mFloatDrawable;
	// protected FloatDrawable mFloatDrawable;//浮层
	// protected Rect mDrawableSrc = new Rect();
	// protected Rect mDrawableDst = new Rect();
	// protected Rect mDrawableFloat = new Rect();// 浮层选择框，就是头像选择框
	// protected boolean isFirst = true;

	protected Context mContext;
	private AdvanceImageViewDelegate delegate;

	public AdvanceImageViewGroup(Context context) {
		super(context);
		this.mContext = context;
		initViewGroup();
	}

	public AdvanceImageViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		initViewGroup();
	}

	LayoutParams pLp;
	View mRoot;

	private void initViewGroup() {
		mRoot = View.inflate(getContext(), R.layout.advance_image, null);
		this.addView(mRoot);
		pLp = (LayoutParams) mRoot.getLayoutParams();

		processImage = (ImageView) mRoot.findViewById(R.id.advance_image);
		// pLp=(android.widget.FrameLayout.LayoutParams)
		// processImage.getLayoutParams();
		processImage.setOnTouchListener(this);
		coverImage = (ImageView) mRoot.findViewById(R.id.float_image);
	}

	public AdvanceImageViewGroup(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		initViewGroup();
	}

	public void clearDrawable() {
		processImage.clearAnimation();
		processImage.setImageDrawable(null);
		coverImage.clearAnimation();
		coverImage.setImageDrawable(null);
	}

	public void setDrawable(Drawable mDrawable, Drawable mFDrawable) {
//		if(this.mFloatDrawable!=null){
//			Bitmap bmp=((BitmapDrawable)this.mFloatDrawable).getBitmap();
//			if(bmp!=null&&!bmp.isRecycled())
//				bmp.recycle();
//			this.mFloatDrawable=null;
//		}
		this.mDrawable = mDrawable;
		this.mFloatDrawable = mFDrawable;
		pLp.leftMargin = 0;
		pLp.topMargin = 0;
		pLp.width = getWidth();
		pLp.height = getHeight();
		mRoot.setLayoutParams(pLp);

		// this.mFloatDrawable.setBounds(0,0,getWidth(),getHeight());
		processImage.setImageDrawable(this.mDrawable);
		processImage.invalidate();
		// matrix.postScale(processImage.getImageMatrix()., sy, getWidth(),
		// getHeight());
		// processImage.setImageMatrix(matrix);
		if (matrix == null) {
			matrix = new Matrix();
			savedMatrix = new Matrix();

			float sx = (float) (getWidth() / (this.mDrawable
					.getIntrinsicWidth() * 1.0));
			float sy = (float) (getHeight() / (this.mDrawable
					.getIntrinsicHeight() * 1.0));

			matrix.postScale(sx, sy, 0, 0);

			// matrix.mapRect(new RectF(0f,0f,getWidth(),getHeight()));
			processImage.setImageMatrix(matrix);
		}
		coverImage.setImageDrawable(this.mFloatDrawable);
		coverImage.invalidate();
		// processImage.setScaleType(ScaleType.MATRIX);
	}

	Handler handler = new Handler();

	public void startCoverAnimation() {
		processImage.startAnimation(AnimationUtils.loadAnimation(mContext,
				R.anim.inner_right_in));
		coverImage.setVisibility(View.INVISIBLE);
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				coverImage.startAnimation(AnimationUtils.loadAnimation(
						mContext, R.anim.cover_top_in));
				coverImage.setVisibility(View.VISIBLE);
				
				
			}

		}, 1000);

	}

	// these matrices will be used to move and zoom image
	private Matrix matrix = null;
	private Matrix savedMatrix = null;
	// we can be in one of these 3 states
	private static final int NONE = 0;
	private static final int DRAG = 1;
	private static final int ZOOM = 2;
	private int mode = NONE;
	// remember some things for zooming
	private PointF start = new PointF();
	private PointF mid = new PointF();
	private float oldDist = 1f;
	private float d = 0f;
	private float newRot = 0f;
	private float[] lastEvent = null;

	public boolean onTouch(View v, MotionEvent event) {
		// handle touch events here
		ImageView view = (ImageView) v;
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			savedMatrix.set(matrix);
			start.set(event.getX(), event.getY());
			mode = DRAG;
			lastEvent = null;
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			oldDist = spacing(event);
			if (oldDist > 10f) {
				savedMatrix.set(matrix);
				midPoint(mid, event);
				mode = ZOOM;
			}
			lastEvent = new float[4];
			lastEvent[0] = event.getX(0);
			lastEvent[1] = event.getX(1);
			lastEvent[2] = event.getY(0);
			lastEvent[3] = event.getY(1);
			d = rotation(event);
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			mode = NONE;
			lastEvent = null;
			break;
		case MotionEvent.ACTION_MOVE:
			if (mode == DRAG) {
				matrix.set(savedMatrix);
				float dx = event.getX() - start.x;
				float dy = event.getY() - start.y;
				matrix.postTranslate(dx, dy);
			} else if (mode == ZOOM) {
				float newDist = spacing(event);
				if (newDist > 10f) {
					matrix.set(savedMatrix);
					float scale = (newDist / oldDist);
					matrix.postScale(scale, scale, mid.x, mid.y);
				}
				if (lastEvent != null && event.getPointerCount() >= 2) {
					newRot = rotation(event);
					float r = newRot - d;
					float[] values = new float[9];
					matrix.getValues(values);
					float tx = values[2];
					float ty = values[5];
					float sx = values[0];
					float xc = (view.getWidth() / 2) * sx;
					float yc = (view.getHeight() / 2) * sx;
					matrix.postRotate(r, tx + xc, ty + yc);
				}
			}
			break;
		}
		view.setImageMatrix(matrix);
		delegate.postMove();
		return true;
	}

	/**
	 * Determine the space between the first two fingers
	 */
	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * Calculate the mid point of the first two fingers
	 */
	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}

	/**
	 * Calculate the degree to be rotated by.
	 * 
	 * @param event
	 * @return Degrees
	 */
	private float rotation(MotionEvent event) {
		double delta_x = (event.getX(0) - event.getX(1));
		double delta_y = (event.getY(0) - event.getY(1));
		double radians = Math.atan2(delta_y, delta_x);
		return (float) Math.toDegrees(radians);
	}

	public Bitmap getMovedBitmap(int dstWidth, int dstHeight) {

		if (this.mDrawable == null)
			return null;

		Bitmap tmpBitmap = Bitmap.createBitmap(getWidth(), getHeight(),
				Config.ARGB_8888);
		Canvas canvas = new Canvas(tmpBitmap);
		// mDrawable.draw(canvas);
		// canvas.setMatrix(matrix);
		processImage.draw(canvas);

		// Bitmap tmpBitmap=this.getMovingBitmap();
		// if(tmpBitmap==null) return null;

		Bitmap bitmap = Bitmap.createScaledBitmap(tmpBitmap, dstWidth,
				dstHeight, true);
		// tmpBitmap.recycle();
		// tmpBitmap = newRet;
		return bitmap;
	}

	public Bitmap getMovingBitmap() {

		BitmapDrawable drawable = (BitmapDrawable) processImage.getDrawable();

		return drawable.getBitmap();

	}

	public AdvanceImageViewDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(AdvanceImageViewDelegate delegate) {
		this.delegate = delegate;
	}
	
	
}
