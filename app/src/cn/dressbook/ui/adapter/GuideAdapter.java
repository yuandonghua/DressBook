/**   
 * @title GuideAdapter.java
 * @package cn.dressbook.ui.adapter
 * @description 
 * @author 袁东华   
 * @update 2013-12-19 下午05:32:31
 * @version    
 */
package cn.dressbook.ui.adapter;

import java.util.List;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;


/**
 * @description导航页的适配器
 * @version
 * @author 袁东华
 * @update 2013-12-19 下午05:32:31
 */

public class GuideAdapter extends PagerAdapter {
	private List<View> views;
	private Context mContext;


	/**
	 * 类的构造方法
	 * 
	 * @param views
	 */
	public GuideAdapter(Context context, List<View> view) {
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
		if(position==3){
			
		}
		return views.get(position);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub

		((ViewPager) container).removeView(views.get(position));

	}


	
}
