package cn.dressbook.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.BoWenZhengWenActivity;
import cn.dressbook.ui.FindActivity;
import cn.dressbook.ui.ZhiNengTuiJianActivity;
import cn.dressbook.ui.R;
import cn.dressbook.ui.ShangPinXiangQingActivity;
import cn.dressbook.ui.UserHomepageActivity;
import cn.dressbook.ui.adapter.FindResultAdapter.MyViewHolder;
import cn.dressbook.ui.bean.FindBwBean;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.listener.OnItemLongClickListener;
import cn.dressbook.ui.model.Adviser;
import cn.dressbook.ui.model.AlbcBean;
import cn.dressbook.ui.tb.TaoBaoUI;

/**
 * 搜索结果的适配器
 * 
 * @author 熊天富
 * 
 */
public class FindResultAdapter extends Adapter<MyViewHolder> implements
		OnClickListener {
	private OnItemClickListener mOnItemClickListener;
	private OnItemLongClickListener mOnItemLongClickListener;
	private int type;
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions(ImageView.ScaleType.CENTER_CROP, false);
	private Context mContext;
	private int height;
	private List<Adviser> listAdviser = new ArrayList<Adviser>();
	private List<FindBwBean> listFindBw = new ArrayList<FindBwBean>();
	private List<AlbcBean> listAttire = new ArrayList<AlbcBean>();
	private Activity mActivity;

	public FindResultAdapter(int type, Context context, Activity activity) {
		this.type = type;
		mContext = context;
		mActivity = activity;
		height = (int) mContext.getResources().getDimension(
				R.dimen.find_rl_height);

	}

	@Override
	public int getItemViewType(int position) {

		return super.getItemViewType(position);
	}
	

	@Override
	public int getItemCount() {
		int item = 0;
		switch (type) {
		case FindActivity.TYPE_BLOG:
			item = listFindBw != null && listFindBw.size() > 0 ? listFindBw
					.size() : 0;
			break;
		case FindActivity.TYPE_COUNSELOR:
			item = listAdviser != null && listAdviser.size() > 0 ? listAdviser
					.size() : 0;
			break;
		case FindActivity.TYPE_BRAND:
			item = listAttire != null && listAttire.size() > 0 ? listAttire
					.size() : 0;
			break;
		case FindActivity.TYPE_GOODS:
			item = listAttire != null && listAttire.size() > 0 ? listAttire
					.size() : 0;
			break;

		}
		return item;

	}

	@Override
	public void onBindViewHolder(MyViewHolder holder, int position) {
		switch (type) {
		/**
		 * 搜索品牌
		 */
		case FindActivity.TYPE_BRAND:
			holder.goodsIv.setVisibility(View.VISIBLE);
			AlbcBean attireScheme1 = listAttire.get(position);
			holder.itemView.setTag(attireScheme1);
			// 绑定图片
			x.image().bind(holder.goodsIv, attireScheme1.getPic_url(),
					mImageOptions, new CommonCallback<Drawable>() {

						@Override
						public void onSuccess(Drawable arg0) {
							LogUtil.e("onSuccess");
						}

						@Override
						public void onFinished() {
							LogUtil.e("onFinished");

						}

						@Override
						public void onError(Throwable arg0, boolean arg1) {
							LogUtil.e("异常:" + arg0.getMessage());
						}

						@Override
						public void onCancelled(CancelledException arg0) {
						}
					});
			holder.nameTv.setText(attireScheme1.getTitle());
			holder.goodsLl.setVisibility(View.VISIBLE);
			holder.priceTv.setText("¥" + attireScheme1.getPrice());
			holder.ideaTv.setVisibility(View.GONE);

			LayoutParams params = (LayoutParams) holder.goodsRl
					.getLayoutParams();
			params.height = height;
			holder.goodsRl.setLayoutParams(params);
			holder.counselorHeadIv.setVisibility(View.GONE);
			break;
		/**
		 * 搜索商品
		 */
		case FindActivity.TYPE_GOODS:
			holder.goodsIv.setVisibility(View.VISIBLE);
			AlbcBean attireScheme2 = listAttire.get(position);
			holder.itemView.setTag(attireScheme2);
			// 绑定图片
			x.image().bind(holder.goodsIv, attireScheme2.getPic_url(),
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
			holder.nameTv.setText(attireScheme2.getTitle());
			holder.goodsLl.setVisibility(View.VISIBLE);
			holder.priceTv.setText("¥" + attireScheme2.getPrice());
			holder.ideaTv.setVisibility(View.GONE);
			holder.goodsIv.setVisibility(View.VISIBLE);
			LayoutParams params1 = (LayoutParams) holder.goodsRl
					.getLayoutParams();
			params1.height = height;
			holder.goodsRl.setLayoutParams(params1);
			holder.counselorHeadIv.setVisibility(View.GONE);
			break;
		/**
		 * 搜索顾问师
		 */
		case FindActivity.TYPE_COUNSELOR:
			Adviser adviser = listAdviser.get(position);
			holder.itemView.setTag(adviser);
			// 绑定图片
			x.image().bind(holder.counselorHeadIv, adviser.getAutographPath(),
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

			holder.nameTv.setText(adviser.getName());
			holder.indentityTv.setText(adviser.getTitle());
			holder.fansTv.setText("粉丝" + adviser.getGuanzhu());
			holder.ideaTv.setText(adviser.getIdea());

			break;
		/**
		 * 搜索博客
		 */
		case FindActivity.TYPE_BLOG:
			FindBwBean bwBean = listFindBw.get(position);
			holder.itemView.setTag(bwBean);
			// 绑定图片
			x.image().bind(
					holder.counselorHeadIv,
					PathCommonDefines.SERVER_IMAGE_ADDRESS
							+ bwBean.getUserAvatar(), mImageOptions,
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
			holder.nameTv.setText(bwBean.getUserName());
			holder.indentityTv.setText(bwBean.getUserLevel());
			holder.fansTv.setText(bwBean.getAddTimeShow());
			holder.ideaTv.setText(bwBean.getTitle());
			break;
		}

	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.item_counselor, parent, false);
		MyViewHolder holder = new MyViewHolder(view, mOnItemClickListener,
				mOnItemLongClickListener);
		view.setOnClickListener(this);
		return holder;
	}

	/**
	 * 设置条目点击事件
	 * 
	 * @param v
	 */
	@Override
	public void onClick(View v) {

		switch (type) {
		// 点击品牌
		case FindActivity.TYPE_BRAND:
			AlbcBean attireScheme1 = (AlbcBean) v.getTag();
			TaoBaoUI.getInstance().showTaokeItemDetailByItemId(mActivity,
					attireScheme1.getAuction_id());
			break;
		// 点击商品
		case FindActivity.TYPE_GOODS:
			AlbcBean attireScheme2 = (AlbcBean) v.getTag();
			TaoBaoUI.getInstance().showTaokeItemDetailByItemId(mActivity,
					attireScheme2.getAuction_id());
			break;
		// 点击顾问师
		case FindActivity.TYPE_COUNSELOR:
			Adviser adviser = (Adviser) v.getTag();
			Intent counselor = new Intent(mContext, UserHomepageActivity.class);
			counselor.putExtra("USER_ID", adviser.getUserId() + "");
			mContext.startActivity(counselor);
			break;
		// 点击博客
		case FindActivity.TYPE_BLOG:
			FindBwBean bwBean = (FindBwBean) v.getTag();
			mContext.startActivity(new Intent(mContext,
					BoWenZhengWenActivity.class).putExtra("ARTICLE_ID",
					bwBean.getId()));
			break;

		}

	}

	public void setData(Object obj) {
		switch (type) {
		case FindActivity.TYPE_BLOG:
			listFindBw.addAll((List<FindBwBean>) obj);
			break;
		case FindActivity.TYPE_COUNSELOR:
			listAdviser.addAll((List<Adviser>) obj);
			break;
		case FindActivity.TYPE_BRAND:
			listAttire.addAll((List<AlbcBean>) obj);
			break;
		case FindActivity.TYPE_GOODS:
			listAttire.addAll((List<AlbcBean>) obj);
			break;

		}
	}

	class MyViewHolder extends ViewHolder implements OnClickListener,
			OnLongClickListener {
		View view;
		ImageView counselorHeadIv, goodsIv;
		TextView nameTv, indentityTv, fansTv, ideaTv, priceTv;
		LinearLayout goodsLl;
		RelativeLayout goodsRl;
		private OnItemClickListener mOnItemClickListener;
		private OnItemLongClickListener mOnItemLongClickListener;

		public MyViewHolder(View view, OnItemClickListener itemClick,
				OnItemLongClickListener itemLongClick) {
			super(view);
			this.view = view;
			counselorHeadIv = (ImageView) view
					.findViewById(R.id.counselor_head_iv);
			nameTv = (TextView) view.findViewById(R.id.name_tv);
			indentityTv = (TextView) view.findViewById(R.id.identity_tv);
			fansTv = (TextView) view.findViewById(R.id.fans_tv);
			ideaTv = (TextView) view.findViewById(R.id.idea_tv);
			goodsLl = (LinearLayout) view.findViewById(R.id.goods_ll);
			priceTv = (TextView) view.findViewById(R.id.price_tv);
			// tryTv = (TextView) view.findViewById(R.id.try_tv);
			goodsIv = (ImageView) view.findViewById(R.id.goods_iv);
			goodsRl = (RelativeLayout) view.findViewById(R.id.goods_rl);
			view.setOnClickListener(this);
			view.setOnLongClickListener(this);
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
}
