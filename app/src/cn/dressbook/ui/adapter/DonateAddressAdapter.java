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
package cn.dressbook.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.dressbook.ui.R;
import cn.dressbook.ui.bean.AixinjuanyiBeanDonateAddress;


/**
 * TODO
 * 
 * @author LiShen
 * @company Gifted Youngs Workshop
 * @date 2015-10-31 下午12:50:02
 * @since
 * @version
 */
@SuppressLint("InflateParams")
public class DonateAddressAdapter extends BaseAdapter {

	private Activity activity;
	private List<AixinjuanyiBeanDonateAddress> data = new ArrayList<AixinjuanyiBeanDonateAddress>();

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-31 下午12:50:27
	 */
	public DonateAddressAdapter(Activity activity,
			List<AixinjuanyiBeanDonateAddress> data) {
		this.activity = activity;
		this.data = data;
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-31 下午12:50:02
	 * @return
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return data.size();
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-31 下午12:50:02
	 * @param position
	 * @return
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public AixinjuanyiBeanDonateAddress getItem(int position) {
		return data.get(position);
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-31 下午12:50:02
	 * @param position
	 * @return
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return data.get(position).hashCode();
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-31 下午12:50:02
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return
	 * @see android.widget.Adapter#getView(int, View,
	 *      ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = activity.getLayoutInflater().inflate(
					R.layout.item_listview_donate_address, null);
			holder.tvItemDonateAddressName = (TextView) convertView
					.findViewById(R.id.tvItemDonateAddressName);
			holder.tvItemDonateAddressZipCode = (TextView) convertView
					.findViewById(R.id.tvItemDonateAddressZipCode);
			holder.tvItemDonateAddressPhone = (TextView) convertView
					.findViewById(R.id.tvItemDonateAddressPhone);
			holder.tvItemDonateAddressAddress = (TextView) convertView
					.findViewById(R.id.tvItemDonateAddressAddress);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		AixinjuanyiBeanDonateAddress address = data.get(position);
		holder.tvItemDonateAddressName.setText("收件人：" + address.getName());
		holder.tvItemDonateAddressZipCode.setText("邮政编码："
				+ address.getPostcode());
		holder.tvItemDonateAddressPhone.setText("电话：" + address.getTel());
		holder.tvItemDonateAddressAddress
				.setText(address.getAddrShow());
		return convertView;
	}

	/**
	 * TODO 设置 data 的值
	 */
	public void setData(List<AixinjuanyiBeanDonateAddress> data) {
		this.data = data;
		notifyDataSetChanged();
	}

	/**
	 * 
	 * TODO
	 * 
	 * @author LiShen
	 * @company Gifted Youngs Workshop
	 * @date 2015-10-31 下午12:53:35
	 * @since
	 * @version
	 */
	private class ViewHolder {
		private TextView tvItemDonateAddressName;
		private TextView tvItemDonateAddressZipCode;
		private TextView tvItemDonateAddressPhone;
		private TextView tvItemDonateAddressAddress;
	}
}
