package cn.dressbook.ui.adapter;

import java.util.ArrayList;

import org.xutils.x;
import org.xutils.image.ImageOptions;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.R;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.listener.OnItemLongClickListener;
import cn.dressbook.ui.model.KeHu;
import cn.dressbook.ui.view.CircleImageView;

/**
 * @description 我的客户
 * @author 袁东华
 * @date 2016-3-3
 */
public class WDKHAdapter extends Adapter<WDKHAdapter.ViewHolder> {
	private OnItemClickListener mOnItemClickListener;
	private OnItemLongClickListener mOnItemLongClickListener;
	private Context mContext;
	private Handler mHandler;
	private ArrayList<KeHu> mList;

	public WDKHAdapter(Context mContext, Handler mHandler) {
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.mHandler = mHandler;
	}

	public void setData(ArrayList<KeHu> mList) {
		this.mList = mList;
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
		KeHu kehu = mList.get(position);
		if (kehu != null) {
			viewHolder.name_tv.setText(kehu.getUserName());
			if (kehu.getTotalVary() != null) {

				if (Double.parseDouble(kehu.getTotalVary()) > 10000) {
					viewHolder.ljgx_tvv.setText("累计贡献"
							+ (Double.parseDouble(kehu.getTotalVary()) / 10000)
							+ "元");
				} else {
					viewHolder.ljgx_tvv.setText("累计贡献" + kehu.getTotalVary()
							+ "元");
				}
			}else{
				viewHolder.ljgx_tvv.setText("累计贡献0.00元");
			}

			if ("1".equals(kehu.getMbLevel())) {

				viewHolder.sf_tv.setText("会员");
			} else if ("2".equals(kehu.getMbLevel())) {

				viewHolder.sf_tv.setText("顾问");
			} else if ("3".equals(kehu.getMbLevel())) {

				viewHolder.sf_tv.setText("股东");
			} else if ("4".equals(kehu.getMbLevel())) {

				viewHolder.sf_tv.setText("董事");
			}
			x.image().bind(viewHolder.head_iv, kehu.getAvatar(),
					ManagerUtils.getInstance().getImageOptionsCircle(true));
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.item_adapter_wdkh, parent, false);
		// set the view's size, margins, paddings and layout parameters
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
		private TextView name_tv, sf_tv, ljgx_tvv;
		private ImageView head_iv;

		public ViewHolder(View v, OnItemClickListener itemClick,
				OnItemLongClickListener itemLongClick) {
			super(v);
			// TODO Auto-generated constructor stub
			mOnItemClickListener = itemClick;
			mOnItemLongClickListener = itemLongClick;
			head_iv = (ImageView) v.findViewById(R.id.head_iv);
			ljgx_tvv = (TextView) v.findViewById(R.id.ljgx_tvv);
			name_tv = (TextView) v.findViewById(R.id.name_tv);
			sf_tv = (TextView) v.findViewById(R.id.sf_tv);
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
