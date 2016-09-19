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
package cn.dressbook.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 
 * TODO 屏幕相关工具类
 * 
 * @author LiShen
 * @company Gifted Youngs Workshop
 * @date 2015-10-15 下午5:35:09
 * @since
 * @version
 */
public class ScreenUtils {
	/**
	 * 
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-7-21 下午5:45:23
	 */
	private ScreenUtils() {
		throw new AssertionError();
	}

	/**
	 * 
	 * TODO dp转成px
	 * 
	 * @author LiShen
	 * @date 2015-7-21 下午5:45:30
	 * @param context
	 * @param dp
	 * @return
	 * @see
	 */
	public static float dpToPx(Context context, float dp) {
		if (context == null) {
			return -1;
		}
		return dp * context.getResources().getDisplayMetrics().density;
	}

	/**
	 * 
	 * TODO px转成dp
	 * 
	 * @author LiShen
	 * @date 2015-7-21 下午5:45:36
	 * @param context
	 * @param px
	 * @return
	 * @see
	 */
	public static float pxToDp(Context context, float px) {
		if (context == null) {
			return -1;
		}
		return px / context.getResources().getDisplayMetrics().density;
	}

	/**
	 * 
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-7-21 下午5:45:41
	 * @param context
	 * @param dp
	 * @return
	 * @see
	 */
	public static int dpToPxInt(Context context, float dp) {
		return (int) (dpToPx(context, dp) + 0.5f);
	}

	/**
	 * 
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-7-21 下午5:45:45
	 * @param context
	 * @param px
	 * @return
	 * @see
	 */
	public static float pxToDpCeilInt(Context context, float px) {
		return (int) (pxToDp(context, px) + 0.5f);
	}

	/**
	 * 
	 * TODO 获取屏幕宽度
	 * 
	 * @author LiShen
	 * @date 2015-9-8 下午8:22:57
	 * @param context
	 * @return
	 * @see
	 */
	public static int getScreenWidth(Activity context) {
		DisplayMetrics metric = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric.widthPixels;
	}

	/**
	 * 
	 * TODO 获取屏幕高度
	 * 
	 * @author LiShen
	 * @date 2015-9-8 下午8:22:32
	 * @param context
	 * @return
	 * @see
	 */
	public static int getScreenHeight(Activity context) {
		DisplayMetrics metric = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric.heightPixels;
	}
}
