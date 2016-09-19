package cn.dressbook.ui.adapter;

import java.util.List;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;


/**
 * @description:多张图片展示适配器
 * @author:袁东华
 * @modifier:袁东华
 * @remarks:
 * @2015年7月2日 下午1:05:19
 */

public class ManyImageHintAdapter extends PagerAdapter {
	private List<View> views;
	private Context mContext;


	/**
	 * 类的构造方法
	 * 
	 * @param views
	 */
	public ManyImageHintAdapter(Context context, List<View> view) {
		super();
		mContext = context;
		views = view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.view.PagerAdapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return views != null ? views.size() : 0;
	}

	// 判断是否由对象生成界面
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return (arg0 == arg1);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		((ViewPager) container).addView(views.get(position), 0);
		return views.get(position);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub

		((ViewPager) container).removeView(views.get(position));

	}


	
}
