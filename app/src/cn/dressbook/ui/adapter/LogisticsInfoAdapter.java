package cn.dressbook.ui.adapter;

import java.util.ArrayList;

import org.xutils.image.ImageOptions;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.R;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.listener.OnItemLongClickListener;
import cn.dressbook.ui.model.LogisticsInfo2;


/**
 * @description: 物流信息的适配器
 * @author:袁东华
 * @time:2015-9-15下午4:31:52
 */
public class LogisticsInfoAdapter extends
		Adapter<LogisticsInfoAdapter.ViewHolder> {
	private OnItemClickListener mOnItemClickListener;
	private OnItemLongClickListener mOnItemLongClickListener;
	private Activity mContext;
	private Handler mHandler;
	private String state;
	private float sum = 0;
	private boolean isEdit = false;
	private ArrayList<LogisticsInfo2> mList;

	public LogisticsInfoAdapter(Activity mContext, Handler mHandler) {
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.mHandler = mHandler;
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
			final LogisticsInfo2 log = mList.get(position);
			if (log != null) {
				viewHolder.title_tv.setText(log.getContext());
				viewHolder.time_tv.setText(log.getTime());
				if (position == 0) {
					viewHolder.title_tv.setTextColor(mContext.getResources()
							.getColor(R.color.zhuye_selected));
					viewHolder.time_tv.setTextColor(mContext.getResources()
							.getColor(R.color.zhuye_selected));
					viewHolder.imageview
							.setImageResource(R.drawable.circle_src_1);
				} else {
					viewHolder.title_tv.setTextColor(mContext.getResources()
							.getColor(R.color.black6));
					viewHolder.time_tv.setTextColor(mContext.getResources()
							.getColor(R.color.black6));
					viewHolder.imageview
							.setImageResource(R.drawable.circle_src_2);
				}
			}
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.logisticsinfo_item, parent, false);
		// set the view's size, margins, paddings and layout parameters
		ViewHolder vh = new ViewHolder(v, mOnItemClickListener,
				mOnItemLongClickListener);
		return vh;
	}


	public void setData(ArrayList<LogisticsInfo2> mLogisticsInfo) {
		this.mList = mLogisticsInfo;

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
		private TextView title_tv, time_tv;
		private ImageView imageview, line;

		public ViewHolder(View v, OnItemClickListener itemClick,
				OnItemLongClickListener itemLongClick) {
			super(v);
			// TODO Auto-generated constructor stub
			mOnItemClickListener = itemClick;
			mOnItemLongClickListener = itemLongClick;
			imageview = (ImageView) v.findViewById(R.id.imageview);
			line = (ImageView) v.findViewById(R.id.line);
			title_tv = (TextView) v.findViewById(R.id.title_tv);
			time_tv = (TextView) v.findViewById(R.id.time_tv);

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
