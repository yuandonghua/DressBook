package cn.dressbook.ui.fragment.counselor;

import android.util.Log;


public class FragmentFactory {
	public static BaseCounselorFragment getFragment(int position){
		BaseCounselorFragment fragment=null;
		Log.i("xx", "执行到点击替换");
		switch (position) {
		case 0:
			fragment=new RecommendFragment();
			break;

		case 1:
			fragment=new CounselorFragment();
			break;
		}
		return fragment;
	}

}
