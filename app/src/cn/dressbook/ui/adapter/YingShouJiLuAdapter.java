package cn.dressbook.ui.adapter;

import java.util.ArrayList;

import org.xutils.common.util.LogUtil;

import cn.dressbook.ui.fragment.JiaoYiJiLuFragment;
import cn.dressbook.ui.fragment.YingShouYiJiLuFragment;
import cn.dressbook.ui.model.DianPu;
import cn.dressbook.ui.model.JiaoYiJiLuBiaoZhi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * @description 交易记录的适配器
 * @author 袁东华
 * @date 2016-2-19
 */
public class YingShouJiLuAdapter extends FragmentPagerAdapter {

	public YingShouJiLuAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return bzList != null ? bzList.get(position).getfranchiseeName() : "";
	}

	@Override
	public int getCount() {
		return bzList != null ? bzList.size() : 0;
	}

	@Override
	public Fragment getItem(int position) {
		return YingShouYiJiLuFragment.newInstance(bzList.get(
				position).getfranchiseeId());
	}

	private ArrayList<DianPu> bzList;

	public void setData(ArrayList<DianPu> bzList) {
		// TODO Auto-generated method stub
		this.bzList = bzList;
	}

}
