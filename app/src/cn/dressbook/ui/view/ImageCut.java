package cn.dressbook.ui.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import cn.dressbook.ui.R;

public class ImageCut extends ImageCut2 {

	private Paint paint_rect = new Paint();
	private final int mRadius = 200;
	private Xfermode cur_xfermode;
	private Rect r;
	private RectF rf;
	private boolean isToCutImage = false;

	public ImageCut(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		// paint.setColor(Color.BLUE);
		paint_rect.setColor(getResources().getColor(R.color.blue1));
		paint_rect.setAntiAlias(true);
		cur_xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		return super.onTouchEvent(event);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		if (isToCutImage)
			return;

		if (rf == null || rf.isEmpty()) {
			r = new Rect(0, 0, getWidth(), getHeight());
			rf = new RectF(r);
		}
		// 在imageview上面画入背景和 圆形
		int sc = canvas.saveLayer(rf, null, Canvas.MATRIX_SAVE_FLAG
				| Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
				| Canvas.FULL_COLOR_LAYER_SAVE_FLAG
				| Canvas.CLIP_TO_LAYER_SAVE_FLAG | Canvas.ALL_SAVE_FLAG);
		paint_rect.setColor(getResources().getColor(R.color.translucent));
		canvas.drawRect(r, paint_rect);
		paint_rect.setXfermode(cur_xfermode);
		paint_rect.setColor(Color.WHITE);
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, mRadius, paint_rect);
		canvas.restoreToCount(sc);
		paint_rect.setXfermode(null);
	}

	public Bitmap onClip() {
		// 获取imageview的bitmap
		// 为了不带半透明的背景，从新刷新下imageview 好获干净的位图 然后截取
		Paint paint = new Paint();
		isToCutImage = true;
		invalidate();
		setDrawingCacheEnabled(true);
		Bitmap bitmap = getDrawingCache().copy(getDrawingCache().getConfig(),
				false);
		setDrawingCacheEnabled(false);
		// 创建你要截取的位图 这个代码大家估计都非常熟悉
		Bitmap bitmap2 = Bitmap.createBitmap(2 * mRadius, 2 * mRadius,
				Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap2);

		// 其实qq窃取的照片是方形的 不是圆形的 如果你要圆形的 ，可以再代码中加入
		// canvas.drawRoundRect(new RectF(0, 0, 2 * mRadius, 2 * mRadius),
		// mRadius, mRadius, paint);
		// paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		RectF dst = new RectF(-bitmap.getWidth() / 2 + mRadius, -getHeight()
				/ 2 + mRadius, bitmap.getWidth() - bitmap.getWidth() / 2
				+ mRadius, getHeight() - getHeight() / 2 + mRadius);
		canvas.drawBitmap(bitmap, null, dst, paint);
		isToCutImage = false;
		return bitmap2;
	}

}
