package cn.dressbook.ui.adapter;

import java.util.ArrayList;

import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.DZSPActivity;
import cn.dressbook.ui.MYXLinkActivity;
import cn.dressbook.ui.R;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.listener.OnItemLongClickListener;
import cn.dressbook.ui.model.DZSP;
import cn.dressbook.ui.model.DZSPFL;
import cn.dressbook.ui.model.MYXChild;
import cn.dressbook.ui.recyclerview.FullyGridLayoutManager;

/**
 * @description: 定制种类详情适配器
 * @author:袁东华
 * @time:2015-9-28上午10:22:02
 */
public class ZldzAdapter extends Adapter<ZldzAdapter.ViewHolder> {
	private OnItemClickListener mOnItemClickListener;
	private OnItemLongClickListener mOnItemLongClickListener;
	private DZSPFL dzspfl;
	private Activity mContext;
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions(ImageView.ScaleType.CENTER_CROP);
	private Handler mHandler;

	public ZldzAdapter(Activity mContext, Handler handler) {
		super();
		this.mContext = mContext;
		mHandler = handler;

	}

	/**
	 * 初始设置数据
	 * 
	 * @param dzspfl
	 */
	public void setData(DZSPFL dzspfl) {
		this.dzspfl = dzspfl;
	}

	/**
	 * 刷新设置数据
	 * 
	 * @param dzspfl
	 */
	public void setListData(DZSPFL dzspfl) {
		this.dzspfl.getDzAttire().addAll(dzspfl.getDzAttire());
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return 1;
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
		// TODO Auto-generated method
		// stubmLSDZChildAdapter.setBitmapUtils(mBitmapUtils);
		if (dzspfl != null) {
			x.image().bind(viewHolder.imageview1, dzspfl.getDzCls_pic(),
					mImageOptions);
			// viewHolder.imageview1.getViewTreeObserver()
			// .addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			//
			// @Override
			// public void onGlobalLayout() {
			// // TODO Auto-generated method stub
			// int width = viewHolder.imageview1.getWidth();
			// int height = width * 500 / 900;
			// LayoutParams lp = (LayoutParams) viewHolder.imageview1
			// .getLayoutParams();
			// lp.height = height;
			// lp.width = width;
			// viewHolder.imageview1.setLayoutParams(lp);
			// viewHolder.imageview1.getViewTreeObserver()
			// .removeGlobalOnLayoutListener(this);
			// }
			// });

			ZLDZChildAdapter mLSDZChildAdapter = new ZLDZChildAdapter(mContext,
					null);
			mLSDZChildAdapter.setData(dzspfl.getDzAttire());
			viewHolder.recyclerview.setAdapter(mLSDZChildAdapter);
			// 点击条目
			mLSDZChildAdapter.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(View view, int p) {
					// TODO Auto-generated method stub
					// Intent mYXLinkActivity = new Intent(mContext,
					// MYXLinkActivity.class);
					// mContext.startActivity(mYXLinkActivity);
					Intent DZSPActivity = new Intent(mContext,
							DZSPActivity.class);
					DZSPActivity.putExtra("attire_id", dzspfl.getDzAttire()
							.get(p).getDzAttire_id());
					mContext.startActivity(DZSPActivity);
				}
			});
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.zldz_item, parent, false);
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
		public RecyclerView recyclerview;
		private ImageView imageview1;

		public ViewHolder(View v, OnItemClickListener itemClick,
				OnItemLongClickListener itemLongClick) {
			super(v);
			// TODO Auto-generated constructor stub
			mOnItemClickListener = itemClick;
			mOnItemLongClickListener = itemLongClick;
			imageview1 = (ImageView) v.findViewById(R.id.imageview1);
			recyclerview = (RecyclerView) v.findViewById(R.id.recyclerview);
			recyclerview.setLayoutManager(new FullyGridLayoutManager(mContext,
					2));
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
