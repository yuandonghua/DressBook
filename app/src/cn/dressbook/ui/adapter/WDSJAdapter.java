package cn.dressbook.ui.adapter;

import java.util.ArrayList;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.dressbook.ui.R;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.listener.OnItemLongClickListener;
import cn.dressbook.ui.model.LiangTiShuJu;
import cn.dressbook.ui.model.Preval;

/**
 * @description 我的数据适配器
 * @author 熊天富
 * @date 2016年2月19日11:16:56
 */
public class WDSJAdapter extends Adapter<WDSJAdapter.ViewHolder> {
	private OnItemClickListener mOnItemClickListener;
	private OnItemLongClickListener mOnItemLongClickListener;
	private ArrayList<LiangTiShuJu> mList = new ArrayList<LiangTiShuJu>();

	public WDSJAdapter() {
	}

	@Override
	public int getItemCount() {
		return mList != null ? mList.size() : 0;
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int position) {
		setContent(viewHolder, position);
	}

	/**
	 * @description:给条目设置内容
	 */
	private void setContent(final ViewHolder viewHolder, final int position) {
		LiangTiShuJu shuju = mList.get(position);
		viewHolder.tv_name.setText(shuju.getName());

		if ("".equals(shuju.getUnit()) || shuju.getUnit() == null) {

			viewHolder.tv_shuju.setText(shuju.getValue());
		} else {
			viewHolder.tv_shuju.setText(shuju.getValue() + " "
					+ shuju.getUnit());
		}

	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
		View v = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.item_adapter_wdsj, parent, false);
		ViewHolder vh = new ViewHolder(v, mOnItemClickListener,
				mOnItemLongClickListener);
		return vh;
	}

	public void setData(ArrayList<LiangTiShuJu> mList) {
		this.mList = mList;
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
		private TextView tv_name, tv_shuju;

		public ViewHolder(View v, OnItemClickListener itemClick,
				OnItemLongClickListener itemLongClick) {
			super(v);
			tv_name = (TextView) v.findViewById(R.id.tv_name);
			tv_shuju = (TextView) v.findViewById(R.id.tv_shuju);
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
