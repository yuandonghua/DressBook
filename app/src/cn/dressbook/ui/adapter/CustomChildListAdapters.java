package cn.dressbook.ui.adapter;

import java.util.ArrayList;

import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.image.ImageOptions;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.R;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.listener.OnItemLongClickListener;
import cn.dressbook.ui.model.MSTJData;

/**
 * @description: 我的订单子适配器
 * @author:袁东华
 * @time:2015-9-28上午10:21:40
 */
public class CustomChildListAdapters extends BaseAdapter {
	private OnItemClickListener mOnItemClickListener;
	private OnItemLongClickListener mOnItemLongClickListener;
	private ArrayList<MSTJData> mMSTJDataList;
	private Context mContext;
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions();
	public CustomChildListAdapters(ArrayList<MSTJData> mMSTJDataList,
			Context mContext) {
		super();
		this.mMSTJDataList = mMSTJDataList;
		this.mContext = mContext;
	}
	public void setData(ArrayList<MSTJData> beans) {
		mMSTJDataList = beans;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mMSTJDataList == null ? 0 : mMSTJDataList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mMSTJDataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.submitorder_item, null);
			holder = new ViewHolder(convertView, mOnItemClickListener,
					mOnItemLongClickListener);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		setContent(holder, position);
		return convertView;
	}
	/**
	 * @description:给条目设置内容
	 * @parameters
	 */
	private void setContent(final ViewHolder viewHolder, final int position) {
		// TODO Auto-generated method stub
		if (mMSTJDataList != null && mMSTJDataList.size() > position) {
			final MSTJData mstjData = mMSTJDataList.get(position);
			if (mstjData != null) {
				viewHolder.title_tv.setText(mstjData.getAdesc());
				viewHolder.color_tv.setText("颜色:" + mstjData.getColor());
				viewHolder.size_tv.setText("尺码:" + mstjData.getSize());
				viewHolder.num_tv.setText("x" + mstjData.getNum());
				viewHolder.price_value.setText("￥" + mstjData.getShopPrice());
				// 绑定图片
				x.image().bind(viewHolder.imageview, mstjData.getPicUrl(),
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
		private TextView title_tv, price_value, color_tv, size_tv, num_tv;
		private ImageView imageview;

		public ViewHolder(View v, OnItemClickListener itemClick,
				OnItemLongClickListener itemLongClick) {
			super(v);
			// TODO Auto-generated constructor stub
			mOnItemClickListener = itemClick;
			mOnItemLongClickListener = itemLongClick;
			imageview = (ImageView) v.findViewById(R.id.imageview);
			title_tv = (TextView) v.findViewById(R.id.title_tv);
			price_value = (TextView) v.findViewById(R.id.price_value);
			color_tv = (TextView) v.findViewById(R.id.color_tv);
			size_tv = (TextView) v.findViewById(R.id.size_tv);
			num_tv = (TextView) v.findViewById(R.id.num_tv);

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
