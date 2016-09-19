/*
 * Copyright by Gifted Youngs Workshop and the original author or authors.
 * 
 * This document only allow internal use , any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Gifted Youngs Workshop from
 * 
 * giftedyoungs@163.com
 * 
 *
 * 此代码由天才少年工作小组开发完成, 仅限内部使用 
 * 外部使用该代码将负相应的法律责任
 * 更多信息请致信天才少年工作小组
 * 
 * giftedyoungs@163.com
 *
 */
package cn.dressbook.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 
 * TODO
 * 
 * @author LiShen
 * @company Gifted Youngs Workshop
 * @date 2015-10-21 下午7:11:43
 * @since
 * @version
 */
@SuppressLint("NewApi")
public class HyperListView extends ListView {

	/**
	 * TODO
	 * 
	 * @author Leonardo
	 * @date 2015-6-25 下午4:58:05
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 * @param defStyleRes
	 */
	public HyperListView(Context context, AttributeSet attrs, int defStyleAttr,
			int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	/**
	 * TODO
	 * 
	 * @author Leonardo
	 * @date 2015-6-25 下午4:57:59
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public HyperListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO
	}

	/**
	 * TODO
	 * 
	 * @author Leonardo
	 * @date 2015-6-25 下午4:57:52
	 * @param context
	 * @param attrs
	 */
	public HyperListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO
	}

	/**
	 * TODO
	 * 
	 * @author Leonardo
	 * @date 2015-6-25 下午4:57:45
	 * @param context
	 */
	public HyperListView(Context context) {
		super(context);
		// TODO
	}

	/**
	 * 
	 * TODO 重写该方法，达到使ListView适应ScrollView的效果
	 * 
	 * @author Leonardo
	 * @date 2015-6-25 下午4:58:55
	 * @param widthMeasureSpec
	 * @param heightMeasureSpec
	 * @see ListView#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
