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

import android.os.Bundle;
import android.support.v4.app.Fragment;


/**
 * TODO 基本的fragment类
 * 
 * @author LiShen
 * @company Gifted Youngs Workshop
 * @date 2015-10-14 下午11:42:46
 * @since
 * @version
 */
public class MeitanFragment extends Fragment {
	private String fragmentName;

	/**
	 * 
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-15 下午5:17:00
	 * @param savedInstanceState
	 * @see Fragment#onCreate(Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 获取当前fragment的类名
		try {
			String[] names = this.getClass().getName().split("\\.");
			fragmentName = names[names.length - 1];
		} catch (Exception e) {
			// TODO 如果报错则显示为UnknownActivity
			e.printStackTrace();
			fragmentName = "UnknownFragment";
		}
	}

	/**
	 * 
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-15 下午5:17:08
	 * @see Fragment#onStart()
	 */
	@Override
	public void onStart() {
		super.onStart();
	}

	/**
	 * 
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-15 下午5:17:12
	 * @see Fragment#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();
	}

	/**
	 * 
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-15 下午5:17:16
	 * @see Fragment#onPause()
	 */
	@Override
	public void onPause() {
		super.onPause();
	}

	/**
	 * 
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-15 下午5:17:21
	 * @see Fragment#onStop()
	 */
	@Override
	public void onStop() {
		super.onStop();
	}

	/**
	 * 
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-15 下午5:17:25
	 * @see Fragment#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-15 下午5:17:30
	 * @see Fragment#onDetach()
	 */
	@Override
	public void onDetach() {
		super.onDetach();
	}
}
