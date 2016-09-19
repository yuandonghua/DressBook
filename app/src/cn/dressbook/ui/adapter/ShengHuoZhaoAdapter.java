package cn.dressbook.ui.adapter;

import java.util.ArrayList;

import org.xutils.x;
import org.xutils.image.ImageOptions;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.R;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.listener.OnItemLongClickListener;
import cn.dressbook.ui.model.LifePhoto;

/**
 * @description:生活照适配器
 * @author:袁东华
 * @time:2015-12-4下午4:13:28
 */
public class ShengHuoZhaoAdapter extends
		Adapter<ShengHuoZhaoAdapter.ViewHolder> {
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions(ImageView.ScaleType.CENTER_CROP);
	private OnItemClickListener mOnItemClickListener;
	private OnItemLongClickListener mOnItemLongClickListener;
	private ArrayList<LifePhoto> mList;
	private Handler mHandler;

	public ShengHuoZhaoAdapter(Context mContext, Handler mHandler) {
		// TODO Auto-generated constructor stub
	}

	public void setData(ArrayList<LifePhoto> mList) {
		this.mList = mList;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return mList != null ? mList.size() : 0;
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
		if (mList != null && mList.size() > position) {
			LifePhoto lifePhoto = mList.get(position);
			x.image().bind(viewHolder.imageview, lifePhoto.getPic(),
					mImageOptions);
			viewHolder.delete_iv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (mOnItemClickListener != null) {
						mOnItemClickListener.onItemClick(v, position);
					}
				}
			});
		}

	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.shenghuozhao_item, parent, false);
		// set the view's size, margins, paddings and layout parameters
		ViewHolder vh = new ViewHolder(v, mOnItemClickListener,
				mOnItemLongClickListener);
		return vh;
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
		/**
		 * 图标
		 */
		public ImageView imageview;
		public ImageView delete_iv;

		public ViewHolder(View v, OnItemClickListener itemClick,
				OnItemLongClickListener itemLongClick) {
			super(v);
			// TODO Auto-generated constructor stub
			mOnItemClickListener = itemClick;
			mOnItemLongClickListener = itemLongClick;
			imageview = (ImageView) v.findViewById(R.id.imageview);
			delete_iv = (ImageView) v.findViewById(R.id.delete_iv);

			v.setOnClickListener(this);
			v.setOnLongClickListener(this);

		}

		@Override
		public boolean onLongClick(View v) {
			// TODO Auto-generated method stub
//			if (mOnItemLongClickListener != null) {
//				mOnItemLongClickListener.onItemLongClick(v, getPosition());
//
//			}
			return true;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
//			if (mOnItemClickListener != null) {
//				mOnItemClickListener.onItemClick(v, getPosition());
//
//			}
		}

	}
}
