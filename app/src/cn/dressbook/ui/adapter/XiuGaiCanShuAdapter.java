package cn.dressbook.ui.adapter;

import java.util.ArrayList;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
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
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.R;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.listener.OnItemLongClickListener;
import cn.dressbook.ui.model.TiaoZhenCanShu;

/**
 * @description: 调整细节的适配器
 * @author:ydh
 * @data:2016-4-21上午10:30:11
 */
public class XiuGaiCanShuAdapter extends
		Adapter<XiuGaiCanShuAdapter.ViewHolder> {
	private OnItemClickListener mOnItemClickListener;
	private OnItemLongClickListener mOnItemLongClickListener;
	private ArrayList<TiaoZhenCanShu> mList;
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions(ImageView.ScaleType.CENTER_INSIDE);
	private Context context;
	private Handler mHandler;
	private int mPosition = -1;

	public XiuGaiCanShuAdapter(Context context, Handler mHandler) {
		this.context = context;
		this.mHandler = mHandler;
	}

	public void setData(ArrayList<TiaoZhenCanShu> mList) {
		this.mList = mList;
	}

	public ArrayList<TiaoZhenCanShu> getData() {
		return mList;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return mList == null ? 0 : mList.size();
	}

	@Override
	public void onBindViewHolder(final ViewHolder viewHolder, int position) {
		// TODO Auto-generated method stub
		setContent(viewHolder, position);

	}

	/**
	 * @description:给条目设置内容
	 * @parameters
	 */
	private void setContent(final ViewHolder viewHolder, final int position) {
		viewHolder.wz_tv.setText((position + 1) + "");
		viewHolder.content_tv.setText(mList.get(position).getcontent()+"\n"+mList.get(position).getMaterial());
		x.image().bind(
				viewHolder.imageView,
				mList.get(position).getPic(),
				ManagerUtils.getInstance().getImageOptions(
						ImageView.ScaleType.CENTER_INSIDE));
		if (mPosition == position) {
			viewHolder.rb.setChecked(true);
		} else {
			viewHolder.rb.setChecked(false);
		}

		// 点击修改
		viewHolder.rb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mPosition = position;
				Message msg = new Message();
				Bundle data = new Bundle();
				data.putInt("position", position);
				msg.setData(data);
				msg.what = NetworkAsyncCommonDefines.CLICK_XIUGAI;
				mHandler.sendMessage(msg);
			}
		});

	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.xiugaicanshu_item, parent, false);
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
		private ImageView imageView;
		private RadioButton rb;
		private TextView content_tv, wz_tv;

		public ViewHolder(View v, OnItemClickListener itemClick,
				OnItemLongClickListener itemLongClick) {
			super(v);
			// TODO Auto-generated constructor stub
			mOnItemClickListener = itemClick;
			mOnItemLongClickListener = itemLongClick;
			imageView = (ImageView) v.findViewById(R.id.imageView);
			content_tv = (TextView) v.findViewById(R.id.content_tv);
			wz_tv = (TextView) v.findViewById(R.id.wz_tv);
			rb = (RadioButton) v.findViewById(R.id.rb);
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

	public void setPosition(int mPosition2) {
		// TODO Auto-generated method stub
		mPosition = mPosition2;
	}
}
