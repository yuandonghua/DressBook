package cn.dressbook.ui.adapter;

import java.util.ArrayList;

import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.image.ImageOptions;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.R;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.listener.OnItemLongClickListener;
import cn.dressbook.ui.model.MSTJData;
import cn.dressbook.ui.net.OrderExec;

/**
 * @description: 购物车的适配器
 * @author:袁东华
 * @time:2015-12-1下午5:02:30
 */
public class ShoppingCartAdapter extends
		Adapter<ShoppingCartAdapter.ViewHolder> {
	private OnItemClickListener mOnItemClickListener;
	private OnItemLongClickListener mOnItemLongClickListener;
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions(ImageView.ScaleType.CENTER_INSIDE);
	private Activity mContext;
	private Handler mHandler;
	private String state;
	private float sum = 0;
	private boolean isEdit = false;
	/**
	 * 买手推荐方案
	 */
	private ArrayList<MSTJData> mMSTJDataList;

	public ShoppingCartAdapter(Activity mContext, Handler mHandler) {
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.mHandler = mHandler;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return mMSTJDataList != null ? mMSTJDataList.size() : 0;
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
		if (mMSTJDataList != null && mMSTJDataList.size() > position) {
			final MSTJData mstjData = mMSTJDataList.get(position);
			if (mstjData != null) {
				viewHolder.cb
						.setOnCheckedChangeListener(new OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(
									CompoundButton buttonView, boolean isChecked) {
								// TODO Auto-generated method stub
								if (isChecked) {
									mMSTJDataList.get(position).setIsChecked(
											"1");
								} else {
									mMSTJDataList.get(position).setIsChecked(
											"0");
								}
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.REFRESH_SHOPPINGCART);

							}
						});
				viewHolder.title_tv.setText(mstjData.getAdesc());
				viewHolder.color_tv.setText("颜色:" + mstjData.getColor());
				viewHolder.size_tv.setText("尺码:" + mstjData.getSize());
				viewHolder.num_tv.setText("x" + mstjData.getNum());
				viewHolder.price_value.setText("￥" + mstjData.getShopPrice()+0);
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

				if ("1".equals(mstjData.getIsChecked())) {
					viewHolder.cb.setChecked(true);
				} else {
					viewHolder.cb.setChecked(false);
				}
				if (isEdit) {
					viewHolder.delete_tv.setVisibility(View.VISIBLE);
					viewHolder.rl_1.setVisibility(View.GONE);
					viewHolder.rl_2.setVisibility(View.VISIBLE);
					viewHolder.color.setText("颜色:" + mstjData.getColor());
					viewHolder.size.setText("尺码:" + mstjData.getSize());
					viewHolder.number_tv.setText(mstjData.getNum());
				} else {
					viewHolder.delete_tv.setVisibility(View.GONE);
					viewHolder.rl_1.setVisibility(View.VISIBLE);
					viewHolder.rl_2.setVisibility(View.GONE);
				}
				// 点击减号
				viewHolder.minus_iv.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						int size = Integer.parseInt(mMSTJDataList.get(position)
								.getNum());
						if (size > 1) {
							size--;
							mMSTJDataList.get(position).setNum(size + "");
							viewHolder.number_tv.setText(mMSTJDataList.get(
									position).getNum());
						}
					}
				});
				// 点击加号
				viewHolder.plus_iv.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						int size = Integer.parseInt(mMSTJDataList.get(position)
								.getNum());
						if (size > 0) {
							size++;
							mMSTJDataList.get(position).setNum(size + "");
							viewHolder.number_tv.setText(mMSTJDataList.get(
									position).getNum());
						}
					}
				});
				// 点击删除
				viewHolder.delete_tv.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (mMSTJDataList != null
								&& mMSTJDataList.size() > position) {

							OrderExec
									.getInstance()
									.deleteShoppingCart(
											mHandler,
											ManagerUtils.getInstance()
													.getUser_id(mContext),
											mstjData.getId(),
											NetworkAsyncCommonDefines.DELETE_SHOPPINGCART_S,
											NetworkAsyncCommonDefines.DELETE_SHOPPINGCART_F);
							mMSTJDataList.remove(position);
						}
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
				R.layout.shoppingcart_item, parent, false);
		// set the view's size, margins, paddings and layout parameters
		ViewHolder vh = new ViewHolder(v, mOnItemClickListener,
				mOnItemLongClickListener);
		return vh;
	}

	public void setData(ArrayList<MSTJData> mMSTJDataList) {
		this.mMSTJDataList = mMSTJDataList;
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
		private TextView title_tv, price_value, color_tv, size_tv, num_tv,
				delete_tv, color, size, number_tv;
		private ImageView imageview, minus_iv, plus_iv;
		private CheckBox cb;
		private RelativeLayout rl_1, rl_2;

		public ViewHolder(View v, OnItemClickListener itemClick,
				OnItemLongClickListener itemLongClick) {
			super(v);
			// TODO Auto-generated constructor stub
			mOnItemClickListener = itemClick;
			mOnItemLongClickListener = itemLongClick;
			cb = (CheckBox) v.findViewById(R.id.cb);
			imageview = (ImageView) v.findViewById(R.id.imageview);
			title_tv = (TextView) v.findViewById(R.id.title_tv);
			price_value = (TextView) v.findViewById(R.id.price_value);
			color_tv = (TextView) v.findViewById(R.id.color_tv);
			size_tv = (TextView) v.findViewById(R.id.size_tv);
			num_tv = (TextView) v.findViewById(R.id.num_tv);
			delete_tv = (TextView) v.findViewById(R.id.delete_tv);
			rl_1 = (RelativeLayout) v.findViewById(R.id.rl_1);
			rl_2 = (RelativeLayout) v.findViewById(R.id.rl_2);
			color = (TextView) v.findViewById(R.id.color);
			size = (TextView) v.findViewById(R.id.size);
			number_tv = (TextView) v.findViewById(R.id.number_tv);
			minus_iv = (ImageView) v.findViewById(R.id.minus_iv);
			plus_iv = (ImageView) v.findViewById(R.id.plus_iv);
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

	public float getSum() {
		sum = 0;
		if (mMSTJDataList != null && mMSTJDataList.size() > 0) {
			for (MSTJData mstj : mMSTJDataList) {
				if ("1".equals(mstj.getIsChecked())) {

					sum += Float.parseFloat(mstj.getShopPrice())
							* Integer.parseInt(mstj.getNum());
				}
			}
		}
		return sum;
	}

	public ArrayList<MSTJData> getData() {
		return mMSTJDataList;
	}

	public void setEdit(boolean boo) {
		isEdit = boo;
	}
}
