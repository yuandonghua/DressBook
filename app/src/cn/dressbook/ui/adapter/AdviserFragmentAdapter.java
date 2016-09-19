package cn.dressbook.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
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
import cn.dressbook.ui.R;
import cn.dressbook.ui.bean.MeitanBeanArticle;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.listener.OnItemLongClickListener;
import cn.dressbook.ui.model.RecyclerItem1;


/**
 * @description: 首页顾问fragment的适配器
 * @author:袁东华
 * @time:2015-11-16上午10:29:30
 */
public class AdviserFragmentAdapter extends
		Adapter<AdviserFragmentAdapter.ViewHolder> {
	private OnItemClickListener mOnItemClickListener;
	private OnItemLongClickListener mOnItemLongClickListener;
	private ArrayList<RecyclerItem1> mList;
	private MeitanBeanArticle mMeitanBeanArticle;

	public AdviserFragmentAdapter(Context mContext, Handler mHandler) {
		// TODO Auto-generated constructor stub
	}

	public void setData(ArrayList<RecyclerItem1> mList) {
		this.mList = mList;
	}

	public void setBoWen(MeitanBeanArticle mMeitanBeanArticle) {
		this.mMeitanBeanArticle = mMeitanBeanArticle;
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
			RecyclerItem1 ri = mList.get(position);
			viewHolder.title_tv.setText(ri.getTitle());
			viewHolder.ms_tv.setText(ri.getmiaoshu());
			viewHolder.time_tv.setText(ri.gettime());
			switch (position) {
			// 美谈
			case 0:
				viewHolder.imageview.setImageResource(R.drawable.mt_src_1);
				
				break;
			// 顾问
			case 1:
				viewHolder.imageview.setImageResource(R.drawable.adviser_src_1);
				break;
				//量身定制
			case 2:
				viewHolder.imageview.setImageResource(R.drawable.lsdz_src_1);

				break;
				// 智能推荐
			case 3:
				viewHolder.imageview.setImageResource(R.drawable.lstj_src_1);
				break;
				// 美衣讯
			case 4:
				viewHolder.imageview.setImageResource(R.drawable.myx_src_1);

				break;
			default:
				break;
			}
		}

	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.adviserfragment_item, parent, false);
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
		/**
		 * 标题
		 */
		public TextView title_tv;
		/**
		 * 描述
		 */
		public TextView ms_tv;
		/**
		 * 时间
		 */
		public TextView time_tv;
		

		public ViewHolder(View v, OnItemClickListener itemClick,
				OnItemLongClickListener itemLongClick) {
			super(v);
			// TODO Auto-generated constructor stub
			mOnItemClickListener = itemClick;
			mOnItemLongClickListener = itemLongClick;
			imageview = (ImageView) v.findViewById(R.id.imageview);
			title_tv = (TextView) v.findViewById(R.id.title_tv);
			ms_tv = (TextView) v.findViewById(R.id.ms_tv);
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
