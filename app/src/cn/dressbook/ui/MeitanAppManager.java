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
package cn.dressbook.ui;

import java.util.Stack;

import android.app.Activity;
import android.content.Context;

/**
 * TODO Activity管理类
 * 
 * @author LiShen
 * @company Gifted Youngs Workshop
 * @date 2015-10-15 下午4:58:49
 * @since
 * @version
 */
public class MeitanAppManager {
	/**
	 * TODO activity栈
	 */
	private static Stack<Activity> activityStack;
	/**
	 * TODO 单例
	 */
	private static MeitanAppManager instance;

	/**
	 * TODO 构造函数私有化
	 * 
	 * @author LiShen
	 * @date 2015-6-3 下午3:33:47
	 */
	private MeitanAppManager() {

	}

	/**
	 * TODO 单例模式
	 * 
	 * @author LiShen
	 * @date 2015-6-3 下午3:49:14
	 * @see
	 */
	public static MeitanAppManager getInstance() {

		if (instance == null) {
			instance = new MeitanAppManager();
		}
		return instance;
	}

	/**
	 * TODO 添加一个activity入栈
	 * 
	 * @author LiShen
	 * @date 2015-6-3 下午3:51:00
	 * @see
	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	/**
	 * TODO 获得当前activity
	 * 
	 * @author LiShen
	 * @date 2015-6-3 下午3:51:00
	 * @see
	 */
	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * TODO 关闭当前activity
	 * 
	 * @author LiShen
	 * @date 2015-6-3 下午3:51:00
	 * @see
	 */
	public void finishActivity() {
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	/**
	 * TODO 关闭指定activity
	 * 
	 * @author LiShen
	 * @date 2015-6-3 下午3:51:00
	 * @see
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * TODO 关闭指定activity.class
	 * 
	 * @author LiShen
	 * @date 2015-6-3 下午3:51:00
	 * @see
	 */
	public void finishActivity(Class<?> cls) {
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
	}

	/**
	 * TODO 关闭所有的activity
	 * 
	 * @author LiShen
	 * @date 2015-6-3 下午3:51:00
	 * @see
	 */
	public void finishAllActivity() {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (null != activityStack.get(i)) {
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}

	/**
	 * TODO 退出app，关闭所有的activity
	 * 
	 * @author LiShen
	 * @date 2015-6-3 下午3:51:00
	 * @see
	 */
	@SuppressWarnings("deprecation")
	public void exitApp(Context context) {
		try {
			finishAllActivity();
		} catch (Exception e) {
		}
	}
}
