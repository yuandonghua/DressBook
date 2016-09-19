package cn.dressbook.ui.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import org.xutils.common.util.LogUtil;

import cn.dressbook.ui.fragment.DingDanFragment;
import cn.dressbook.ui.model.OrderForm;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class WoDeDingDanAdapter extends FragmentPagerAdapter {
	private ArrayList<String> keyList;
	private HashMap<String, ArrayList<OrderForm>> orderMap;
	private Handler mHandler;
	private ArrayList<DingDanFragment> ddfList = new ArrayList<DingDanFragment>();

	public void setData(Handler mHandler, ArrayList<String> keyList,
			HashMap<String, ArrayList<OrderForm>> orderMap) {
		this.mHandler = mHandler;
		this.keyList = keyList;
		this.orderMap = orderMap;
		if (ddfList != null && ddfList.size() < keyList.size()) {
			ddfList.clear();
			for (int i = 0; i < keyList.size(); i++) {
				ddfList.add(new DingDanFragment(mHandler, orderMap.get(keyList
						.get(i)), i));
			}

		}
	}

	public WoDeDingDanAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub

	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return keyList != null ? keyList.get(position) : "";
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub

		return ddfList.get(position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return keyList != null ? keyList.size() : 0;
	}

}
