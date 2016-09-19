package cn.dressbook.ui.adapter;

import java.util.ArrayList;

import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.image.ImageOptions;

import android.app.Activity;
import android.content.Intent;
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
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.MYXLinkActivity;
import cn.dressbook.ui.R;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.listener.OnItemLongClickListener;
import cn.dressbook.ui.model.MYX1;
import cn.dressbook.ui.model.MYXChild;
import cn.dressbook.ui.recyclerview.FullyLinearLayoutManager;

/**
 * @description: 美衣讯适配器
 * @author:袁东华
 * @time:2015-9-28上午10:22:02
 */
public class MYXAdapter extends Adapter<MYXAdapter.ViewHolder> {
	private OnItemClickListener mOnItemClickListener;
	private OnItemLongClickListener mOnItemLongClickListener;
	private ArrayList<MYX1> mMYXList;
	private Activity mContext;
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions(ImageView.ScaleType.CENTER_CROP);
	private Handler mHandler;

	public MYXAdapter(Activity mContext, Handler handler) {
		super();
		this.mContext = mContext;
		mHandler = handler;

	}

	public void setData(ArrayList<MYX1> mMYXList) {
		this.mMYXList = mMYXList;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return mMYXList == null ? 0 : mMYXList.size();
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
		// stubmMYXChildAdapters.setBitmapUtils(mBitmapUtils);
		if (mMYXList != null && mMYXList.get(position) != null
				&& mMYXList.get(position).getBtArticles() != null
				&& mMYXList.get(position).getBtArticles().size() > 0) {
			final MYXChild mYXChild = mMYXList.get(position).getBtArticles()
					.get(0);

			viewHolder.time_tv.setText(mMYXList.get(position)
					.getPublishTimeShow());
			viewHolder.imageview1.getViewTreeObserver()
					.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

						@Override
						public void onGlobalLayout() {
							// TODO Auto-generated method stub
							int width = viewHolder.imageview1.getWidth();
							int height = width * 500 / 900;
							LayoutParams lp = (LayoutParams) viewHolder.imageview1
									.getLayoutParams();
							lp.height = height;
							lp.width = width;
							viewHolder.imageview1.setLayoutParams(lp);
							viewHolder.imageview1.getViewTreeObserver()
									.removeGlobalOnLayoutListener(this);
						}
					});
			// 标题
			if (mYXChild.getFirst().equals("1")) {
				viewHolder.rl_1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent mYXLinkActivity = new Intent(mContext,
								MYXLinkActivity.class);
						mYXLinkActivity.putExtra("url",
								mYXChild.getExternal_url());
						mContext.startActivity(mYXLinkActivity);
					}
				});
				viewHolder.title1_tv.setText(mYXChild.getTitle());
				// 绑定图片
				x.image().bind(viewHolder.imageview1, mYXChild.getUrlPic(),
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

			}
			MYXChildAdapters mMYXChildAdapters = new MYXChildAdapters(mContext,
					null);
			mMYXChildAdapters.setData(mMYXList.get(position).getBtArticles()
					.subList(1, mMYXList.get(position).getBtArticles().size()));
			viewHolder.recyclerview.setAdapter(mMYXChildAdapters);
			mMYXChildAdapters.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(View view, int p) {
					// TODO Auto-generated method stub
					Intent mYXLinkActivity = new Intent(mContext,
							MYXLinkActivity.class);
					mYXLinkActivity.putExtra("url", mMYXList.get(position)
							.getBtArticles().get(p + 1).getExternal_url());
					mContext.startActivity(mYXLinkActivity);
				}
			});
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.myx_item, parent, false);
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
		private RelativeLayout rl_1;
		private ImageView imageview1;
		private TextView title1_tv, time_tv;

		public ViewHolder(View v, OnItemClickListener itemClick,
				OnItemLongClickListener itemLongClick) {
			super(v);
			// TODO Auto-generated constructor stub
			mOnItemClickListener = itemClick;
			mOnItemLongClickListener = itemLongClick;
			rl_1 = (RelativeLayout) v.findViewById(R.id.rl_1);
			imageview1 = (ImageView) v.findViewById(R.id.imageview1);
			time_tv = (TextView) v.findViewById(R.id.time_tv);
			title1_tv = (TextView) v.findViewById(R.id.title1_tv);
			recyclerview = (RecyclerView) v.findViewById(R.id.recyclerview);
			recyclerview.setLayoutManager(new FullyLinearLayoutManager(mContext));
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
