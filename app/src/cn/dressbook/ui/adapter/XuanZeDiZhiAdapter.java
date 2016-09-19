package cn.dressbook.ui.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.EditAddressActivity;
import cn.dressbook.ui.R;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.listener.OnItemLongClickListener;
import cn.dressbook.ui.model.Address;
import cn.dressbook.ui.net.AddressExec;

/**
 * @description: 选择收货地址适配器
 * @author:袁东华
 * @time:2015-11-27上午10:04:06
 */
public class XuanZeDiZhiAdapter extends Adapter<XuanZeDiZhiAdapter.ViewHolder> {
	private OnItemClickListener mOnItemClickListener;
	private OnItemLongClickListener mOnItemLongClickListener;
	private Context mContext;
	private Handler mHandler;
	/**
	 * 需求列表集合
	 */
	private ArrayList<Address> mAddressList;

	public XuanZeDiZhiAdapter(Context mContext, Handler mHandler) {
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.mHandler = mHandler;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return mAddressList != null ? mAddressList.size() : 0;
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int position) {
		// TODO Auto-generated method stub
		setContent(viewHolder, position);
	}

	/**
	 * @description:给条目设置内容
	 * @parameters
	 */
	private void setContent(final ViewHolder viewHolder, final int position) {
		// TODO Auto-generated method stub

		if (mAddressList != null && mAddressList.size() > position) {
			final Address address = mAddressList.get(position);
			if (address != null) {
				if ("1".equals(address.getState())) {
					viewHolder.check_iv
							.setImageResource(R.drawable.radio_selected_1);
				} else {
					viewHolder.check_iv
							.setImageResource(R.drawable.radio_unselected_1);
				}
				viewHolder.name_tv.setText(address.getConsignee());
				viewHolder.phone_tv.setText(address.getMobile());
				viewHolder.address_tv.setText(address.getProvince()
						+ address.getCity() + address.getDistrict()
						+ address.getAddress());

			}
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int poition) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.xuanzedizhi_item, parent, false);
		// set the view's size, margins, paddings and layout parameters
		ViewHolder vh = new ViewHolder(v, mOnItemClickListener,
				mOnItemLongClickListener);
		return vh;
	}

	public void setData(ArrayList<Address> AddressList) {
		mAddressList = null;
		this.mAddressList = AddressList;
	}

	/**
	 * @Description:设置条目点击监听,供外部调用
	 */
	public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
		this.mOnItemClickListener = mOnItemClickListener;

	}

	/**
	 * @Description:设置长按点击监听,供外部调用
	 */
	public void setOnItemLongClickListener(
			OnItemLongClickListener mOnItemLongClickListener) {
		this.mOnItemLongClickListener = mOnItemLongClickListener;

	}

	public class ViewHolder extends RecyclerView.ViewHolder implements
			OnClickListener, OnLongClickListener {
		private OnItemClickListener mOnItemClickListener;
		private OnItemLongClickListener mOnItemLongClickListener;
		private TextView name_tv, phone_tv, address_tv;
		private ImageView check_iv;

		public ViewHolder(View v, OnItemClickListener itemClick,
				OnItemLongClickListener itemLongClick) {
			super(v);
			// TODO Auto-generated constructor stub
			mOnItemClickListener = itemClick;
			mOnItemLongClickListener = itemLongClick;

			name_tv = (TextView) v.findViewById(R.id.name_tv);
			phone_tv = (TextView) v.findViewById(R.id.phone_tv);
			address_tv = (TextView) v.findViewById(R.id.address_tv);

			check_iv = (ImageView) v.findViewById(R.id.check_iv);

			v.setOnClickListener(this);
			v.setOnLongClickListener(this);

		}

		@Override
		public boolean onLongClick(View v) {
			// TODO Auto-generated method stub
			if (mOnItemLongClickListener != null) {
				mOnItemLongClickListener.onItemLongClick(v, getPosition());

			}
			return true;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (mOnItemClickListener != null) {
				mOnItemClickListener.onItemClick(v, getPosition());

			}
		}

	}
}
