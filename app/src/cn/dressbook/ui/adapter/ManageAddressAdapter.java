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
 * @description:管理收货地址适配器
 * @author:袁东华
 * @time:2015-9-1上午10:28:22
 */
public class ManageAddressAdapter extends
		Adapter<ManageAddressAdapter.ViewHolder> {
	private OnItemClickListener mOnItemClickListener;
	private OnItemLongClickListener mOnItemLongClickListener;
	private Context mContext;
	private Handler mHandler;
	/**
	 * 需求列表集合
	 */
	private ArrayList<Address> mAddressList;

	public ManageAddressAdapter(Context mContext, Handler mHandler) {
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
					viewHolder.rb.setChecked(true);
				} else {
					viewHolder.rb.setChecked(false);
				}
				viewHolder.name_tv.setText(address.getConsignee());
				viewHolder.phone_tv.setText(address.getMobile());
				viewHolder.address_tv.setText(address.getProvince()
						+ address.getCity() + address.getDistrict()
						+ address.getAddress());
				// 点击设为默认地址
				viewHolder.rb
						.setOnCheckedChangeListener(new OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(
									CompoundButton buttonView, boolean isChecked) {
								// TODO Auto-generated method stub
								if (isChecked) {
									Toast.makeText(mContext, "正在修改成默认地址,请耐心等等",
											Toast.LENGTH_SHORT).show();
									AddressExec
											.getInstance()
											.setDefaultAddress(
													mHandler,
													ManagerUtils
															.getInstance()
															.getUser_id(mContext),
													address.getId(),
													NetworkAsyncCommonDefines.SET_DEFAULT_ADDRESS_S,
													NetworkAsyncCommonDefines.SET_DEFAULT_ADDRESS_F);
								}

							}
						});
				// 点击编辑
				viewHolder.edit_iv.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent editAddressActivity = new Intent(mContext,
								EditAddressActivity.class);
						editAddressActivity.putExtra("address", address);
						mContext.startActivity(editAddressActivity);
						((Activity) mContext).overridePendingTransition(
								R.anim.back_enter, R.anim.anim_exit);
					}
				});
				// 点击删除
				viewHolder.delete_iv.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						AddressExec
								.getInstance()
								.deleteAddress(
										mHandler,
										ManagerUtils.getInstance()
												.getUser_id(mContext),
										address.getId(),
										NetworkAsyncCommonDefines.SET_DEFAULT_ADDRESS_S,
										NetworkAsyncCommonDefines.SET_DEFAULT_ADDRESS_F);
					}
				});
			}
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int poition) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.manageaddress_item, parent, false);
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
		private RadioButton rb;
		private TextView name_tv, phone_tv, address_tv;
		private ImageView edit_iv, delete_iv;

		public ViewHolder(View v, OnItemClickListener itemClick,
				OnItemLongClickListener itemLongClick) {
			super(v);
			// TODO Auto-generated constructor stub
			mOnItemClickListener = itemClick;
			mOnItemLongClickListener = itemLongClick;

			rb = (RadioButton) v.findViewById(R.id.rb);
			name_tv = (TextView) v.findViewById(R.id.name_tv);
			phone_tv = (TextView) v.findViewById(R.id.phone_tv);
			address_tv = (TextView) v.findViewById(R.id.address_tv);

			edit_iv = (ImageView) v.findViewById(R.id.edit_iv);
			delete_iv = (ImageView) v.findViewById(R.id.delete_iv);

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
