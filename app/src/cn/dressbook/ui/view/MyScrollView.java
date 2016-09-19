package cn.dressbook.ui.view;


import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
	private static final long DELAY = 100;

	private int currentScroll;
	private float y1, y2;
	private Runnable scrollCheckTask;
	private boolean isMove;

	/**
	 * @param context
	 */
	public MyScrollView(Context context) {
		super(context);
		init();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		scrollCheckTask = new Runnable() {
			@Override
			public void run() {
				int newScroll = getScrollY();
				if (currentScroll == newScroll) {
					if (onScrollListener != null) {
						onScrollListener.onScrollStopped();
					}
				} else {
					if (onScrollListener != null) {
						onScrollListener.onScrolling();
					}
					currentScroll = getScrollY();
					postDelayed(scrollCheckTask, DELAY);
				}
			}
		};
		setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					break;
				case MotionEvent.ACTION_MOVE:
					if (!isMove) {
						y1 = event.getY();
					} else {
						y2 = event.getY();
					}
					isMove = true;

					break;
				case MotionEvent.ACTION_UP:
					isMove = false;
					currentScroll = getScrollY();
					postDelayed(scrollCheckTask, DELAY);
					break;

				default:
					break;
				}
				if (onScrollListener != null) {

					onScrollListener.onScrolling2();
				}
				return false;
			}
		});
	}

	public interface ScrollListener {
		public void onScrollChanged(int x, int y, int oldX, int oldY);

		public void onScrollStopped();

		public void onScrolling();

		public void onScrolling2();
	}

	private ScrollListener onScrollListener;

	/**
	 * @param onScrollListener
	 */
	public void setOnScrollListener(ScrollListener onScrollListener) {
		this.onScrollListener = onScrollListener;
	}

	@Override
	protected void onScrollChanged(int x, int y, int oldX, int oldY) {
		super.onScrollChanged(x, y, oldX, oldY);
		if (onScrollListener != null) {
			onScrollListener.onScrollChanged(x, y, oldX, oldY);
		}
	}

	/**
	 * @param child
	 * @return
	 */
	public boolean isChildVisible(View child) {
		if (child == null) {
			return false;
		}
		Rect scrollBounds = new Rect();
		getHitRect(scrollBounds);
		return child.getLocalVisibleRect(scrollBounds);
	}

	public boolean isSlideUpwards() {
		return y1 > y2 ? true : false;
	}

	public boolean isSlideDown() {
		return y1 < y2 ? true : false;
	}

	/**
	 * @return
	 */
	public boolean isAtTop() {
		
		return getScrollY() <= 0;
	}

	/**
	 * @return
	 */
	public boolean isAtBottom() {
		return getChildAt(getChildCount() - 1).getBottom() + getPaddingBottom() == getHeight()
				+ getScrollY();
	}

	// @Override
	// public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	//
	// int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
	// MeasureSpec.AT_MOST);
	// super.onMeasure(widthMeasureSpec, expandSpec);
	// }
}
