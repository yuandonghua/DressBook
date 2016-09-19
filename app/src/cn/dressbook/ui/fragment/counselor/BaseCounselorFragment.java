package cn.dressbook.ui.fragment.counselor;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import cn.dressbook.ui.view.NetFrameLayout;

/**
 * @description: 自定义基础counselorfragment
 * @author:熊天富
 * @modifier:熊天富
 * @remarks:
 * @2015-6-9 上午11:28:28
 * 
 *
 */

public abstract class BaseCounselorFragment extends Fragment {
	protected NetFrameLayout frameLayout;
	protected Context mContext;

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext=getActivity();
		if (frameLayout == null) {
			frameLayout = new NetFrameLayout(getActivity()) {
				@Override
				protected View getSuccessView() {
					// 注意在getsuccessview的前面添加类
					return BaseCounselorFragment.this.getSuccessView();
				}

				@Override
				protected Object getServiceData() {
					return BaseCounselorFragment.this.getServiceData();
				}
			};
		} else {
			ViewParent parent = frameLayout.getParent();
			if (parent instanceof ViewGroup) {
				ViewGroup vp=(ViewGroup) parent;
				vp.removeView(frameLayout);
			}

		}
		return frameLayout;
	}

	protected abstract View getSuccessView();

	protected abstract Object getServiceData();

}
