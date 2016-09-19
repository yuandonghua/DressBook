package cn.dressbook.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.image.ImageOptions;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.ShangPinXiangQingActivity;
import cn.dressbook.ui.R;
import cn.dressbook.ui.WangYeActivity;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.listener.OnItemLongClickListener;
import cn.dressbook.ui.model.AttireScheme;
import cn.dressbook.ui.model.GuangGao;

/**
 * @description: 衣保会员适配器
 * @author:袁东华
 * @time:2015-9-26下午7:38:27
 */
public class YBHYAdapter extends Adapter<YBHYAdapter.ViewHolder> {
	private OnItemClickListener mOnItemClickListener;
	private OnItemLongClickListener mOnItemLongClickListener;
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions();
	private Context mContext;
	private Handler mHandler;
	private GuangGao gg;

	/**
	 * 买手推荐方案
	 */
	private ArrayList<AttireScheme> mMSTJDataList;

	public YBHYAdapter(Context mContext, Handler mHandler) {
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.mHandler = mHandler;
	}


	public void setGGData(GuangGao gg) {
		this.gg = gg;
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
			final AttireScheme mstjData = mMSTJDataList.get(position);
			if (mstjData != null && position != 0) {
				viewHolder.imageview.setVisibility(View.GONE);
				viewHolder.ybhy_rl.setVisibility(View.VISIBLE);
				if (mstjData.getCan_try().equals("yes")) {
					viewHolder.add_tv.setVisibility(View.VISIBLE);
				} else {
					viewHolder.add_tv.setVisibility(View.GONE);
				}
				// 点击详情
				viewHolder.info_tv.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (mstjData.getOpeniid() != null
								&& mstjData.getOpeniid().length() > 0) {
							Message msg = new Message();
							Bundle data = new Bundle();
							data.putString("openiid", mstjData.getOpeniid());
							msg.setData(data);
							msg.what = NetworkAsyncCommonDefines.LOAD_TB_SHANGPIN;
							mHandler.sendMessage(msg);
						} else {
							Intent intent = new Intent(mContext,
									ShangPinXiangQingActivity.class);
							intent.putExtra("AttireScheme", mstjData);
							mContext.startActivity(intent);
						}
					}
				});
				// 点击试穿
				viewHolder.add_tv.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Message msg = new Message();
						Bundle data = new Bundle();
						data.putInt("position", position);
						msg.setData(data);
						msg.what = NetworkAsyncCommonDefines.TRYON;
						mHandler.sendMessage(msg);
					}
				});
				viewHolder.desc_value.setText(mstjData.getDesc());
				viewHolder.price_value.setText("￥" + mstjData.getShop_price()
						+ ".00");
				// 绑定图片
				x.image().bind(viewHolder.image, mstjData.getThume(),
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
				viewHolder.imageview.setVisibility(View.VISIBLE);
				viewHolder.ybhy_rl.setVisibility(View.GONE);
				// 绑定图片
				x.image().bind(viewHolder.imageview,
						gg.getPic(), mImageOptions,
						new CommonCallback<Drawable>() {

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

				viewHolder.imageview.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent productIntent = new Intent(mContext,
								WangYeActivity.class);
						productIntent.putExtra("title", gg.getTitle());
						productIntent.putExtra("url", gg.getUrl());
						mContext.startActivity(productIntent);
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
				R.layout.ybhy_item, parent, false);
		// set the view's size, margins, paddings and layout parameters
		ViewHolder vh = new ViewHolder(v, mOnItemClickListener,
				mOnItemLongClickListener);
		return vh;
	}

	public void setData(ArrayList<AttireScheme> mMSTJDataList) {
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
		private ImageView imageview;
		private TextView desc_value, price_value, info_tv, add_tv;
		private ImageView image;
		private RelativeLayout ybhy_rl;

		public ViewHolder(View v, OnItemClickListener itemClick,
				OnItemLongClickListener itemLongClick) {
			super(v);
			// TODO Auto-generated constructor stub
			mOnItemClickListener = itemClick;
			mOnItemLongClickListener = itemLongClick;
			image = (ImageView) v.findViewById(R.id.image);
			desc_value = (TextView) v.findViewById(R.id.desc_value);
			price_value = (TextView) v.findViewById(R.id.price_value);
			info_tv = (TextView) v.findViewById(R.id.info_tv);
			add_tv = (TextView) v.findViewById(R.id.add_tv);
			ybhy_rl = (RelativeLayout) v.findViewById(R.id.ybhy_rl);
			imageview = (ImageView) v.findViewById(R.id.imageview);
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
