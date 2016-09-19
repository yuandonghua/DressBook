package cn.dressbook.ui.viewpager;

import org.xutils.common.util.LogUtil;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @description: 可以控制是否滚动的ViewPager
 * @author:ydh
 * @data:2016-4-8下午1:19:21
 */
public class RollingControlViewPager extends ViewPager {
	/**
	 * 是否可以滚动
	 */
	private boolean isCanScroll = true;

	public RollingControlViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public RollingControlViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void setCanScroll(boolean isCanScroll) {
		this.isCanScroll = isCanScroll;
	}

	@Override
	public void scrollTo(int x, int y) {
		if (isCanScroll) {
			super.scrollTo(x, y);
		}
	}

	// @Override
	// public boolean onTouchEvent(MotionEvent ev) {
	// if (!isCanScroll) {
	// return true;
	// }
	// return super.onTouchEvent(ev);
	// }
	//
	public boolean isScrollble() {
		return isCanScroll;
	}

}
