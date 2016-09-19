package cn.dressbook.ui.adapter;

import java.util.ArrayList;

import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.image.ImageOptions;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.R;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.listener.OnItemLongClickListener;
import cn.dressbook.ui.model.AlbcBean;
import cn.dressbook.ui.model.AlbcBean;
import cn.dressbook.ui.view.CircleImageView;

/**
 * @description: 搜索结果适配器
 * @author:袁东华
 * @time:2015-10-16上午11:30:36
 */
public class SearchResultAdapter extends
		Adapter<SearchResultAdapter.ViewHolder> {
	private OnItemClickListener mOnItemClickListener;
	private OnItemLongClickListener mOnItemLongClickListener;
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions();
	private Context mContext;
	private Handler mHandler;
	private String state;
	private ArrayList<String> list = new ArrayList<String>();
	/**
	 * 买手推荐方案
	 */
	private ArrayList<AlbcBean> mList;
	/**
	 * 一句话
	 */
	private String reqdesc;
	/**
	 * 发布时间
	 */
	private String addtime;
	private String username, occation, category, price, useravatar;

	public SearchResultAdapter(Context mContext, Handler mHandler) {
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.mHandler = mHandler;
	}

	public ArrayList<String> getList() {
		return list;
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
			final AlbcBean attire = mList.get(position);
			if (position == 0) {
				viewHolder.rl1.setVisibility(View.GONE);
				viewHolder.rl2.setVisibility(View.VISIBLE);
				viewHolder.yjh_tv.setText(reqdesc);
				viewHolder.time_tv.setText(addtime);
				viewHolder.name_tv.setText(username);
				viewHolder.ch_value.setText(occation);
				viewHolder.pl_value.setText(category);
				if ("1000-0".equals(price)) {
					viewHolder.jw_value.setText("1000以上");
				} else {
					viewHolder.jw_value.setText(price);
				}
				// 绑定图片
				x.image().bind(viewHolder.circleimageview, useravatar,
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
			} else {
				if (mList.get(position).getIsView() == 0) {
					viewHolder.bj_tv
							.setBackgroundResource(R.drawable.radio_unselected_1);
				} else {
					viewHolder.bj_tv
							.setBackgroundResource(R.drawable.radio_selected_1);
				}
				viewHolder.rl1.setVisibility(View.VISIBLE);
				viewHolder.rl2.setVisibility(View.GONE);
				viewHolder.price_tv.setText("￥" + attire.getPrice() + "");
				viewHolder.title_tv.setText(attire.getTitle());
				// 绑定图片
				x.image().bind(viewHolder.imageview, attire.getPic_url(),
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
			}
		} else {
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.searchresult_item, parent, false);
		// set the view's size, margins, paddings and layout parameters
		ViewHolder vh = new ViewHolder(v, mOnItemClickListener,
				mOnItemLongClickListener);
		return vh;
	}

	public void setData(ArrayList<AlbcBean> mList) {
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
		/**
		 * 标题
		 */
		private TextView title_tv;
		/**
		 * 价格
		 */
		private TextView price_tv;
		/**
		 * 图片
		 */
		private ImageView imageview;
		/**
		 * 头像
		 */
		private CircleImageView circleimageview;
		/**
		 * 昵称
		 */
		private TextView name_tv;
		/**
		 * 时间
		 */
		private TextView time_tv;
		/**
		 * 场合
		 */
		private TextView ch_value;
		/**
		 * 品类
		 */
		private TextView pl_value;
		/**
		 * 价位
		 */
		private TextView jw_value;
		/**
		 * 一句话
		 */
		private TextView yjh_tv;
		private RelativeLayout rl1, rl2;
		private ImageView bj_tv;

		public ViewHolder(View v, OnItemClickListener itemClick,
				OnItemLongClickListener itemLongClick) {
			super(v);
			// TODO Auto-generated constructor stub
			mOnItemClickListener = itemClick;
			mOnItemLongClickListener = itemLongClick;

			rl1 = (RelativeLayout) v.findViewById(R.id.rl1);
			rl2 = (RelativeLayout) v.findViewById(R.id.rl2);

			circleimageview = (CircleImageView) v
					.findViewById(R.id.circleimageview);
			name_tv = (TextView) v.findViewById(R.id.name_tv);
			time_tv = (TextView) v.findViewById(R.id.time_tv);
			ch_value = (TextView) v.findViewById(R.id.ch_value);
			pl_value = (TextView) v.findViewById(R.id.pl_value);
			jw_value = (TextView) v.findViewById(R.id.jw_value);
			yjh_tv = (TextView) v.findViewById(R.id.yjh_tv);

			imageview = (ImageView) v.findViewById(R.id.imageview);
			title_tv = (TextView) v.findViewById(R.id.title_tv);
			price_tv = (TextView) v.findViewById(R.id.price_tv);

			bj_tv = (ImageView) v.findViewById(R.id.bj_tv);
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

	public void setRequirement(String reqdesc, String addtime, String username,
			String occation, String category, String price, String useravatar) {
		// TODO Auto-generated method stub
		this.reqdesc = reqdesc;
		this.addtime = addtime;
		this.username = username;
		this.occation = occation;
		this.category = category;
		this.price = price;
		this.useravatar = useravatar;

	}
}
