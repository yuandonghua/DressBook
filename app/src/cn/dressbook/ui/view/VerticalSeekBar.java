package cn.dressbook.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;
/**
 * 
 * 垂直进度条
 * @author 袁东华
 *
 */
public class VerticalSeekBar extends SeekBar {

	private Drawable mThumb;



	public VerticalSeekBar(Context context) {
		this(context, null);
	}

	public VerticalSeekBar(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.seekBarStyle);
	}

	public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}



	void onProgressRefresh(float scale, boolean fromUser) {
		Drawable thumb = mThumb;
		if (thumb != null) {
			setThumbPos(getHeight(), thumb, scale, Integer.MIN_VALUE);
			invalidate();
		}
	}

	private void setThumbPos(int w, Drawable thumb, float scale, int gap) {
		int available = w + getPaddingLeft() - getPaddingRight();
		int thumbWidth = thumb.getIntrinsicWidth();
		int thumbHeight = thumb.getIntrinsicHeight();
		available -= thumbWidth;
		// The extra space for the thumb to move on the track
		available += getThumbOffset() * 2;
		int thumbPos = (int) (scale * available);
		int topBound, bottomBound;
		if (gap == Integer.MIN_VALUE) {
			Rect oldBounds = thumb.getBounds();
			topBound = oldBounds.top;
			bottomBound = oldBounds.bottom;
		} else {
			topBound = gap;
			bottomBound = gap + thumbHeight;
		}
		thumb.setBounds(thumbPos, topBound, thumbPos + thumbWidth, bottomBound);
	}

	@Override
	protected void onDraw(Canvas c) {
		c.rotate(-90);
		c.translate(-getHeight(), 0);
		super.onDraw(c);
	}

	@Override
	protected synchronized void onMeasure(int widthMeasureSpec,
			int heightMeasureSpec) {
		super.onMeasure(heightMeasureSpec, widthMeasureSpec);
		setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
	}

	@Override
	public void setThumb(Drawable thumb) {
		mThumb = thumb;
		super.setThumb(thumb);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(h, w, oldw, oldh);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!isEnabled()) {
			return false;
		}
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			setPressed(true);
			int i1 = 0;
			// 获取滑动的距离
			i1 = getMax() - (int) (getMax() * event.getY() / getHeight());
			// 设置进度
			setProgress(i1);
			onSizeChanged(getWidth(), getHeight(), 0, 0);
			break;

		case MotionEvent.ACTION_MOVE:
			int i2 = 0;
			// 获取滑动的距离
			i2 = getMax() - (int) (getMax() * event.getY() / getHeight());
			// 设置进度
			setProgress(i2);
			attemptClaimDrag();
			onSizeChanged(getWidth(), getHeight(), 0, 0);
			break;

		case MotionEvent.ACTION_UP:
			setPressed(false);
			int i3 = 0;
			// 获取滑动的距离
			i3 = getMax() - (int) (getMax() * event.getY() / getHeight());
			setProgress(i3);
			onSizeChanged(getWidth(), getHeight(), 0, 0);
			if (mOnSeekBarChangeListener != null) {
				mOnSeekBarChangeListener.onStopTrackingTouch(i3);
			}
			break;

		case MotionEvent.ACTION_CANCEL:
			setPressed(false);
			onSizeChanged(getWidth(), getHeight(), 0, 0);
			break;
		}
		return true;
	}

	private void trackTouchEvent(MotionEvent event) {
		final int Height = getHeight();
		final int available = Height - getPaddingBottom() - getPaddingTop();
		int Y = (int) event.getY();
		float scale;
		float progress = 0;
		if (Y > Height - getPaddingBottom()) {
			scale = 0.0f;
		} else if (Y < getPaddingTop()) {
			scale = 1.0f;
		} else {
			scale = (float) (Height - getPaddingBottom() - Y)
					/ (float) available;
		}

		final int max = getMax();
		progress = scale * max;

		setProgress((int) progress);
	}

	private void attemptClaimDrag() {
		if (getParent() != null) {
			getParent().requestDisallowInterceptTouchEvent(true);
		}
	}

	public interface OnSeekBarChangeListener {

		void onStopTrackingTouch(int progress);
	}

	public void setOnSeekBarChangeListener(OnSeekBarChangeListener l) {
		mOnSeekBarChangeListener = l;
	}

	private OnSeekBarChangeListener mOnSeekBarChangeListener;
}
