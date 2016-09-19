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
package cn.dressbook.ui.base;

import org.xutils.x;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


/**
 * TODO 基本的FragmentActivity类
 * 
 * @author LiShen
 * @company Gifted Youngs Workshop
 * @date 2015-10-14 下午11:40:28
 * @since
 * @version
 */
public abstract class MeitanFragmentActivity extends FragmentActivity {
	/**
	 * TODO
	 */
	private Bundle savedInstanceState;
	/**
	 * TODO 成功代号
	 */
	public static final int BINGO = 520;
	/**
	 * TODO 未成功代号
	 */
	public static final int OHNO = 886;
	/**
	 * TODO Activity名字
	 */
	private String activityName;

	/**
	 * 
	 * TODO 基本的Activity类
	 * 
	 * @author LiShen
	 * @date 2015-7-28 下午2:33:00
	 * @param savedInstanceState
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// bundle
		this.savedInstanceState = savedInstanceState;
		// 添加此Activity至Activity管理栈
		MeitanAppManager.getInstance().addActivity(this);
		// 获取当前activity的类名
		try {
			String[] names = this.getClass().getName().split("\\.");
			activityName = names[names.length - 1];
		} catch (Exception e) {
			// TODO 如果报错则显示为UnknownActivity
			e.printStackTrace();
			activityName = "UnknownActivity";
		}
		// 执行Activity的各个方法步奏
		x.view().inject(this);
		findViewLayout();
		initializeData();
		setListener();
		performTask();
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-7-28 下午2:32:51
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();
	}

	/**
	 * 
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-7-28 下午2:32:47
	 * @see android.app.Activity#onRestart()
	 */
	@Override
	protected void onRestart() {
		super.onRestart();
	}

	/**
	 * 
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-7-28 下午2:32:44
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		// 调用activity切换动画
		// overridePendingTransition(0, 0);
	}

	/**
	 * 
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-7-28 下午2:32:40
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
	}

	/**
	 * 
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-7-28 下午2:32:37
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		super.onStop();
	}

	/**
	 * 
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-7-28 下午2:32:33
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 调用结束activity方法
		MeitanAppManager.getInstance().finishActivity(this);
	}

	/**
	 * TODO 退出app
	 * 
	 * @author LiShen
	 * @date 2015-6-3 下午5:09:09
	 * @see
	 */
	protected void exitApp() {
		// 调用退出app方法
		MeitanAppManager.getInstance().exitApp(this);
	}


	/**
	 * TODO 通过id找到view布局1
	 * 
	 * @author LiShen
	 * @date 2015-6-3 下午5:09:09
	 * @see
	 */
	protected abstract void findViewLayout();

	/**
	 * TODO 初始化数据
	 * 
	 * @author LiShen
	 * @date 2015-6-3 下午5:09:09
	 * @see
	 */
	protected abstract void initializeData();

	/**
	 * TODO 设置控件的监听事件
	 * 
	 * @author LiShen
	 * @date 2015-6-3 下午5:09:09
	 * @see
	 */
	protected abstract void setListener();

	/**
	 * TODO 执行activity初始的逻辑任务
	 * 
	 * @author LiShen
	 * @date 2015-6-3 下午5:09:09
	 * @see
	 */
	protected abstract void performTask();

	/**
	 * TODO 返回 savedInstanceState 的值
	 */
	public Bundle getSavedInstanceState() {
		return savedInstanceState;
	}
}
