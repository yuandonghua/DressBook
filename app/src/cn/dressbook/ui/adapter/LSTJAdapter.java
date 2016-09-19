package cn.dressbook.ui.adapter;

import java.util.ArrayList;

import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.common.util.LogUtil;
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
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.R;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.listener.OnItemLongClickListener;
import cn.dressbook.ui.model.AttireScheme;

/**
 * @description: 量身推荐适配器
 * @author:袁东华
 * @time:2015-9-24上午9:48:58
 */
public class LSTJAdapter extends Adapter<LSTJAdapter.ViewHolder> {
	private OnItemClickListener mOnItemClickListener;
	private OnItemLongClickListener mOnItemLongClickListener;
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions(ImageView.ScaleType.CENTER_INSIDE, 2);
	private Context mContext;
	private Handler mHandler;
	private String state;
	private ArrayList<String> list = new ArrayList<String>();
	/**
	 * 买手推荐方案
	 */
	private ArrayList<AttireScheme> mList;

	public LSTJAdapter(Context mContext, Handler mHandler) {
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
			final AttireScheme attire = mList.get(position);
			if (attire != null) {
				LogUtil.e("openiid:"+attire.getOpeniid());
				viewHolder.price_tv.setText("￥" + attire.getShop_price()
						+ ".00");
				viewHolder.title_tv.setText(attire.getTitle());
				// 绑定图片
				x.image().bind(viewHolder.imageview, attire.getModPic(),
						mImageOptions, new CommonCallback<Drawable>() {

							@Override
							public void onSuccess(Drawable arg0) {
								// TODO Auto-generated method stub
								LayoutParams lp = (LayoutParams) viewHolder.imageview
										.getLayoutParams();
								lp.height = arg0.getBounds().height()
										* viewHolder.imageview.getWidth()
										/ arg0.getBounds().width();
								viewHolder.imageview.setLayoutParams(lp);
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
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.lstj_item, parent, false);
		// set the view's size, margins, paddings and layout parameters
		ViewHolder vh = new ViewHolder(v, mOnItemClickListener,
				mOnItemLongClickListener);
		return vh;
	}

	public void setData(ArrayList<AttireScheme> mList) {
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
		private ProgressBar pbLoading;
		private TextView title_tv, price_tv;
		private ImageView imageview;

		public ViewHolder(View v, OnItemClickListener itemClick,
				OnItemLongClickListener itemLongClick) {
			super(v);
			// TODO Auto-generated constructor stub
			mOnItemClickListener = itemClick;
			mOnItemLongClickListener = itemLongClick;
			pbLoading = (ProgressBar) v.findViewById(R.id.pbLoading);
			imageview = (ImageView) v.findViewById(R.id.imageview);
			title_tv = (TextView) v.findViewById(R.id.title_tv);
			price_tv = (TextView) v.findViewById(R.id.price_tv);
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
