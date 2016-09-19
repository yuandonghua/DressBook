package cn.dressbook.ui.adapter;

import java.util.ArrayList;

import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.image.ImageOptions;

import android.app.Activity;
import android.content.Intent;
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
import cn.dressbook.ui.ApplyAfterSaleActivity;
import cn.dressbook.ui.OrderInfoActivity;
import cn.dressbook.ui.R;
import cn.dressbook.ui.alipay.AlipayUtils;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.listener.OnItemLongClickListener;
import cn.dressbook.ui.model.MSTJData;
import cn.dressbook.ui.model.OrderForm;
import cn.dressbook.ui.net.CustomSerciceExec;
import cn.dressbook.ui.net.OrderExec;
import cn.dressbook.ui.recyclerview.FullyLinearLayoutManager;

/**
 * @description: 对应订单列表的适配器
 * @author:ydh
 * @data:2016-4-12下午2:55:13
 */
public class MyOrderAdapter extends Adapter<MyOrderAdapter.ViewHolder> {
	private OnItemClickListener mOnItemClickListener;
	private OnItemLongClickListener mOnItemLongClickListener;
	private ArrayList<OrderForm> mListBeans;
	private Activity mContext;
	public ArrayList<MSTJData> mChildListBeans = new ArrayList<MSTJData>();
	private Handler mHandler;
	private OrderForm mOrderForm;

	public MyOrderAdapter(Activity mContext, Handler handler) {
		super();
		this.mContext = mContext;
		mHandler = handler;

	}

	public void setData(ArrayList<OrderForm> mListBeans) {
		this.mListBeans = mListBeans;
	}

	public OrderForm getOrderForm() {
		return mOrderForm;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return mListBeans == null ? 0 : mListBeans.size();
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
		try {
			final OrderForm of = mListBeans.get(position);
			String yf = of.getFreight();
			if ("null".equals(yf)) {
				yf = "0.00";
			}
			viewHolder.orderhint_tv.setText("共1" + "件商品,合计￥"
					+ of.getPriceTotal() + "(含运费￥" + yf + ")");
			viewHolder.number.setText("订单号:" + of.getId());
			if ("true".equals(of.getPayMoney()) && !"".equals(of.getxfm())) {
				viewHolder.ewm_rl.setVisibility(View.VISIBLE);
			} else {
				viewHolder.ewm_rl.setVisibility(View.GONE);
			}
			viewHolder.state.setText(of.getstateShow());
			viewHolder.title_tv.setText(of.getDzOda().getTitle());
			viewHolder.price_value.setText("￥" + of.getPriceTotal());
			// 绑定图片
			x.image().bind(
					viewHolder.imageview,
					of.getDzOda().getPic(),
					ManagerUtils.getInstance().getImageOptions(
							ImageView.ScaleType.CENTER_INSIDE),
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
			// 点击二维码
			viewHolder.ewm_tv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Message msg = new Message();
					Bundle data = new Bundle();
					data.putString("ewm", of.getxfm());
					msg.setData(data);
					msg.what = 1;
					mHandler.sendMessage(msg);
				}
			});
			if ("3".equals(of.getState()) || "102".equals(of.getState())) {
				// 代付款的订单,可以取消可以付款
				viewHolder.qxdd_rl.setVisibility(View.VISIBLE);
				viewHolder.qrsh_rl.setVisibility(View.GONE);
				viewHolder.fk_rl.setVisibility(View.VISIBLE);
				viewHolder.ckwl_rl.setVisibility(View.GONE);
				viewHolder.sqsh_rl.setVisibility(View.GONE);
				viewHolder.sqtk_rl.setVisibility(View.GONE);
			} else if ("4".equals(of.getState()) || "103".equals(of.getState())) {
				// 订单已经取消,不能操作了
				viewHolder.qxdd_rl.setVisibility(View.GONE);
				viewHolder.qrsh_rl.setVisibility(View.GONE);
				viewHolder.fk_rl.setVisibility(View.GONE);
				viewHolder.ckwl_rl.setVisibility(View.GONE);
				viewHolder.sqsh_rl.setVisibility(View.GONE);
				viewHolder.sqtk_rl.setVisibility(View.GONE);
			} else if ("5".equals(of.getState()) || "104".equals(of.getState())) {
				// 用户已经付款,商品生产中,系统还没有发货,用户可以申请退款
				viewHolder.qxdd_rl.setVisibility(View.GONE);
				viewHolder.qrsh_rl.setVisibility(View.GONE);
				viewHolder.fk_rl.setVisibility(View.GONE);
				viewHolder.ckwl_rl.setVisibility(View.GONE);
				viewHolder.sqsh_rl.setVisibility(View.GONE);
				viewHolder.sqtk_rl.setVisibility(View.VISIBLE);
			} else if ("6".equals(of.getState()) || "106".equals(of.getState())) {
				// 订单生产中,只能查看订单
				viewHolder.qxdd_rl.setVisibility(View.GONE);
				viewHolder.qrsh_rl.setVisibility(View.GONE);
				viewHolder.fk_rl.setVisibility(View.GONE);
				viewHolder.ckwl_rl.setVisibility(View.GONE);
				viewHolder.sqsh_rl.setVisibility(View.GONE);
				viewHolder.sqtk_rl.setVisibility(View.GONE);
			} else if ("7".equals(of.getState()) || "107".equals(of.getState())) {
				// 系统已发货,只能确认收货
				viewHolder.qxdd_rl.setVisibility(View.GONE);
				viewHolder.qrsh_rl.setVisibility(View.VISIBLE);
				viewHolder.fk_rl.setVisibility(View.GONE);
				viewHolder.ckwl_rl.setVisibility(View.GONE);
				viewHolder.sqsh_rl.setVisibility(View.GONE);
				viewHolder.sqtk_rl.setVisibility(View.GONE);
			} else if ("8".equals(of.getState()) || "108".equals(of.getState())) {
				// 用户已确认收货,并在售后期内,用户可以申请售后
				viewHolder.qxdd_rl.setVisibility(View.GONE);
				viewHolder.qrsh_rl.setVisibility(View.GONE);
				viewHolder.fk_rl.setVisibility(View.GONE);
				viewHolder.ckwl_rl.setVisibility(View.GONE);
				viewHolder.sqsh_rl.setVisibility(View.VISIBLE);
				viewHolder.sqtk_rl.setVisibility(View.GONE);
			} else if ("9".equals(of.getState()) || "109".equals(of.getState())) {
				// 系统已收货,不支持售后服务
				viewHolder.qxdd_rl.setVisibility(View.GONE);
				viewHolder.qrsh_rl.setVisibility(View.GONE);
				viewHolder.fk_rl.setVisibility(View.GONE);
				viewHolder.ckwl_rl.setVisibility(View.GONE);
				viewHolder.sqsh_rl.setVisibility(View.GONE);
				viewHolder.sqtk_rl.setVisibility(View.GONE);
			} else if ("11".equals(of.getState())
					|| "111".equals(of.getState())) {
				// 申请退货,但没有成功,可以协商,继续申请退货
				viewHolder.qxdd_rl.setVisibility(View.GONE);
				viewHolder.qrsh_rl.setVisibility(View.GONE);
				viewHolder.fk_rl.setVisibility(View.GONE);
				viewHolder.ckwl_rl.setVisibility(View.GONE);
				viewHolder.sqsh_rl.setVisibility(View.VISIBLE);
				viewHolder.sqtk_rl.setVisibility(View.GONE);
			} else if ("10".equals(of.getState())
					|| "110".equals(of.getState())
					|| "12".equals(of.getState())
					|| "112".equals(of.getState())
					|| "113".equals(of.getState())
					|| "114".equals(of.getState())) {
				// 其他情况,暂不处理
				viewHolder.qxdd_rl.setVisibility(View.GONE);
				viewHolder.qrsh_rl.setVisibility(View.GONE);
				viewHolder.fk_rl.setVisibility(View.GONE);
				viewHolder.ckwl_rl.setVisibility(View.GONE);
				viewHolder.sqsh_rl.setVisibility(View.GONE);
				viewHolder.sqtk_rl.setVisibility(View.GONE);
			}
			// 点击取消订单
			viewHolder.qxdd_rl.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.PROCESSING);
					OrderExec.getInstance().cancelOrder(mHandler,
							ManagerUtils.getInstance().getUser_id(mContext),
							of.getId(),
							NetworkAsyncCommonDefines.CANCEL_ORDER_S,
							NetworkAsyncCommonDefines.CANCEL_ORDER_F);
				}
			});
			// 点击查看订单
			viewHolder.ckdd_rl.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(mContext,
							OrderInfoActivity.class);
					intent.putExtra("order_id", of.getId());
					mContext.startActivity(intent);
				}
			});
			// 点击付款
			viewHolder.fk_rl.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stu
					mOrderForm = mListBeans.get(position);
					Intent intent = new Intent(mContext,
							OrderInfoActivity.class);
					intent.putExtra("order_id", of.getId());
					mContext.startActivity(intent);
				}
			});
			// 点击申请售后
			viewHolder.sqsh_rl.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(mContext,
							ApplyAfterSaleActivity.class);
					intent.putExtra("order_id", of.getId());
					intent.putParcelableArrayListExtra("list",
							mListBeans.get(position).getOrderAttite());
					mContext.startActivity(intent);
				}
			});
			// 点击申请退款
			viewHolder.sqtk_rl.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.PROCESSING);
					OrderExec.getInstance().shenQingTuiHuo(mHandler,
							ManagerUtils.getInstance().getUser_id(mContext),
							of.getId(), NetworkAsyncCommonDefines.SQTH_S,
							NetworkAsyncCommonDefines.SQTH_F);

				}
			});
			// 点击确认收货
			viewHolder.qrsh_rl.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.PROCESSING);
					OrderExec.getInstance().receiptGoods(mHandler,
							ManagerUtils.getInstance().getUser_id(mContext),
							of.getId(),
							NetworkAsyncCommonDefines.RECEOPT_GOODS_S,
							NetworkAsyncCommonDefines.RECEOPT_GOODS_F);
				}
			});

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.myorder_item, parent, false);
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
		private TextView number, state, title_tv, price_value, ewm_tv,
				orderhint_tv;
		public ImageView imageview;
		private RelativeLayout ckdd_rl, qxdd_rl, ckwl_rl, fk_rl, sqsh_rl,
				qrsh_rl, ewm_rl, sqtk_rl;

		public ViewHolder(View v, OnItemClickListener itemClick,
				OnItemLongClickListener itemLongClick) {
			super(v);
			// TODO Auto-generated constructor stub
			mOnItemClickListener = itemClick;
			mOnItemLongClickListener = itemLongClick;
			number = (TextView) v.findViewById(R.id.number);
			state = (TextView) v.findViewById(R.id.state);
			imageview = (ImageView) v.findViewById(R.id.imageview);
			sqtk_rl = (RelativeLayout) v.findViewById(R.id.sqtk_rl);
			ckdd_rl = (RelativeLayout) v.findViewById(R.id.ckdd_rl);
			qxdd_rl = (RelativeLayout) v.findViewById(R.id.qxdd_rl);
			ckwl_rl = (RelativeLayout) v.findViewById(R.id.ckwl_rl);
			fk_rl = (RelativeLayout) v.findViewById(R.id.fk_rl);
			sqsh_rl = (RelativeLayout) v.findViewById(R.id.sqsh_rl);
			qrsh_rl = (RelativeLayout) v.findViewById(R.id.qrsh_rl);
			orderhint_tv = (TextView) v.findViewById(R.id.orderhint_tv);
			ewm_tv = (TextView) v.findViewById(R.id.ewm_tv);
			ewm_rl = (RelativeLayout) v.findViewById(R.id.ewm_rl);
			title_tv = (TextView) v.findViewById(R.id.title_tv);
			price_value = (TextView) v.findViewById(R.id.price_value);

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
