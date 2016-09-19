package cn.dressbook.ui.adapter;

import java.util.ArrayList;

import org.xutils.common.util.LogUtil;

import android.app.Activity;
import android.graphics.drawable.Drawable;
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
 * @description 量体进度适配器
 * @author 熊天富
 * @date 2016年2月19日11:16:56
 */
public class LTJDAdapter extends Adapter<LTJDAdapter.ViewHolder> {
	private OnItemClickListener mOnItemClickListener;
	private OnItemLongClickListener mOnItemLongClickListener;
	private ArrayList<LiangTiShuJu> mList = new ArrayList<LiangTiShuJu>();
	private Activity activity;

	public LTJDAdapter(Activity activity) {
		this.activity = activity;
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
		if ("".equals(shuju.getCeliangValue())) {
			viewHolder.content_tv.setText("待测/" + shuju.getValue());
		} else {
			viewHolder.content_tv.setText(shuju.getCeliangValue() + "/"
					+ shuju.getValue());
		}
		viewHolder.tv_danwei.setText(shuju.getUnit());
		if (currentIndex == position) {
			viewHolder.content_tv.setCompoundDrawables(null, drawable1, null,
					drawable2);
		} else {
			viewHolder.content_tv.setCompoundDrawables(null, null, null, null);
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
		View v = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.item_adapter_ltjd, parent, false);
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
		private TextView tv_name, content_tv, tv_danwei;

		public ViewHolder(View v, OnItemClickListener itemClick,
				OnItemLongClickListener itemLongClick) {
			super(v);
			mOnItemClickListener = itemClick;
			mOnItemLongClickListener = itemLongClick;
			tv_name = (TextView) v.findViewById(R.id.tv_name);
			content_tv = (TextView) v.findViewById(R.id.content_tv);
			tv_danwei = (TextView) v.findViewById(R.id.tv_danwei);
			v.setOnClickListener(this);
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

	private int currentIndex;
	private Drawable drawable1, drawable2;

	/**
	 * 设置当前选中的索引
	 * 
	 * @param currentIndex
	 */
	public void setCurrentIndex(int currentIndex) {
		// TODO Auto-generated method stub
		this.currentIndex = currentIndex;
		if (drawable1 == null) {

			drawable1 = activity.getResources().getDrawable(R.drawable.bj_1);

			drawable1.setBounds(0, 0, drawable1.getMinimumWidth(),
					drawable1.getMinimumHeight());
		}
		if (drawable2 == null) {

			drawable2 = activity.getResources().getDrawable(R.drawable.bj_2);

			drawable2.setBounds(0, 0, drawable2.getMinimumWidth(),
					drawable1.getMinimumHeight());
		}
	}
}
