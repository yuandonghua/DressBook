package cn.dressbook.ui.adapter;

import java.util.ArrayList;

import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.image.ImageOptions;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.R;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.listener.OnItemLongClickListener;
import cn.dressbook.ui.model.Adviser;
import cn.dressbook.ui.view.CircleImageView2;


/**
 * @description: 指定推荐顾问师的适配器
 * @author:袁东华
 * @time:2015-10-13下午3:43:41
 */
public class FindAdviserAdapter extends Adapter<FindAdviserAdapter.ViewHolder> {
	private OnItemClickListener mOnItemClickListener;
	private OnItemLongClickListener mOnItemLongClickListener;
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions();
	private ArrayList<String> list = new ArrayList<String>();
	/**
	 * 顾问师列表集合
	 */
	private ArrayList<Adviser> mAdviserList;

	public FindAdviserAdapter(Context mContext, Handler mHandler) {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<String> getData() {
		return list;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return mAdviserList != null ? mAdviserList.size() : 0;
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
		if (mAdviserList == null || mAdviserList.size() == 0
				|| mAdviserList.get(position) == null) {
			return;
		}
		/*if (mAdviserList.get(position).getShowpic() == null
				|| mAdviserList.get(position).getShowpic().size() == 0) {
			return;
		}*/
		//ArrayList<String> picList = mAdviserList.get(position).getShowpic();
		// 设置作品
		/*if (picList.size() == 2) {
			mBitmapUtils.display(viewHolder.adviser_works_one, picList.get(0));
			mBitmapUtils.display(viewHolder.adviser_works_two, picList.get(1));
		} else if (picList.size() == 1) {
			mBitmapUtils.display(viewHolder.adviser_works_one, picList.get(0));
		} else {
			mBitmapUtils.display(viewHolder.adviser_works_one, picList.get(0));
			mBitmapUtils.display(viewHolder.adviser_works_two, picList.get(1));
		}*/
		// 设置头像
		// 绑定图片
		x.image().bind(viewHolder.adviser_head, 
				mAdviserList.get(position).getAutographPath(),
				mImageOptions, new CommonCallback<Drawable>() {

					@Override
					public void onSuccess(Drawable arg0) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onFinished() {
						// TODO Auto-generated method stub

					}

					@Override
					public void onError(Throwable arg0, boolean arg1) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onCancelled(CancelledException arg0) {
						// TODO Auto-generated method stub
					}
				});
		// 设置昵称
		viewHolder.adviser_name.setText(mAdviserList.get(position).getName());
		// 设置介绍
		viewHolder.adviser_introduce.setText(mAdviserList.get(position)
				.getIdea());
		viewHolder.llCheck.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (viewHolder.cb.isChecked()) {
					viewHolder.cb.setChecked(false);
					list.remove(mAdviserList.get(position).getAdviserId());
					Log.i("xx", "移除了参数:"+mAdviserList.get(position).getAdviserId()+"长度:"+list.size());
				} else {
					viewHolder.cb.setChecked(true);
					list.add(mAdviserList.get(position).getAdviserId());
					Log.i("xx", "添加了参数:"+mAdviserList.get(position).getAdviserId()+"长度:"+list.size());
				}
				
			}
		});
		/*viewHolder.cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					list.add(String.valueOf(mAdviserList.get(position).adviserId));
				} else {
					list.remove(String.valueOf(mAdviserList.get(position).adviserId));
				}

			}
		});*/
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.findadviser_item, parent, false);
		// set the view's size, margins, paddings and layout parameters
		ViewHolder vh = new ViewHolder(v, mOnItemClickListener,
				mOnItemLongClickListener);
		return vh;
	}


	public void setData(ArrayList<Adviser> mAdviserList) {
		this.mAdviserList = mAdviserList;
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
		 * 头像
		 */
		public CircleImageView2 adviser_head;
		/**
		 * 作品1
		 */
		//public ImageView adviser_works_one;
		/**
		 * 作品2
		 */
		//public ImageView adviser_works_two;
		/**
		 * 昵称
		 */
		public TextView adviser_name;
		/**
		 * 介绍
		 */
		public TextView adviser_introduce;
		public CheckBox cb;
		public LinearLayout llCheck;

		public ViewHolder(View v, OnItemClickListener itemClick,
				OnItemLongClickListener itemLongClick) {
			super(v);
			// TODO Auto-generated constructor stub
			mOnItemClickListener = itemClick;
			mOnItemLongClickListener = itemLongClick;
			adviser_head = (CircleImageView2) v.findViewById(R.id.adviser_head);
			/*adviser_works_one = (ImageView) v
					.findViewById(R.id.adviser_works_one);
			adviser_works_two = (ImageView) v
					.findViewById(R.id.adviser_works_two);*/
			adviser_name = (TextView) v.findViewById(R.id.adviser_name);
			adviser_introduce = (TextView) v
					.findViewById(R.id.adviser_introduce);
			cb = (CheckBox) v.findViewById(R.id.cb);
			llCheck=(LinearLayout) v.findViewById(R.id.ll_check);
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
