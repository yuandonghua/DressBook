package cn.dressbook.ui.adapter;


import java.util.ArrayList;

import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.common.util.DensityUtil;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
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
import cn.dressbook.app.DressBookApp;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.PhotoShowActivity;
import cn.dressbook.ui.ShangPinXiangQingActivity;
import cn.dressbook.ui.R;
import cn.dressbook.ui.UserHomepageActivity;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.listener.OnItemLongClickListener;
import cn.dressbook.ui.model.AttireScheme;
import cn.dressbook.ui.model.BuyerResponse;
import cn.dressbook.ui.model.CustomService;
import cn.dressbook.ui.model.Requirement;
import cn.dressbook.ui.net.RequirementExec;
import cn.dressbook.ui.tb.TaoBaoUI;
import cn.dressbook.ui.utils.ToastUtils;
import cn.dressbook.ui.view.CircleImageView;

/**
 * @description: 搜索结果适配器
 * @author:袁东华
 * @time:2015-10-16上午11:30:36
 */
public class RecommendInfoAdapter extends
		Adapter<RecommendInfoAdapter.ViewHolder> {
	private OnItemClickListener mOnItemClickListener;
	private OnItemLongClickListener mOnItemLongClickListener;
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions(ImageView.ScaleType.CENTER_CROP);
	private Context mContext;
	private Handler mHandler;
	private String state;
	private boolean flag = true;
	private ArrayList<AttireScheme> list;
	private ArrayList<BuyerResponse> list2;
	private Activity activity;

	public RecommendInfoAdapter(Activity activity,Context mContext, Handler mHandler) {
		// TODO Auto-generated constructor stub
		this.activity=activity;
		this.mContext = mContext;
		this.mHandler = mHandler;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		if (flag) {

			return list != null ? list.size() : 1;
		} else {
			return list2 != null ? list2.size() : 1;
		}
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

		if (position == 0) {
			viewHolder.rl1.setVisibility(View.GONE);
			viewHolder.rl2.setVisibility(View.VISIBLE);
			viewHolder.rl3.setVisibility(View.GONE);
			if (list != null && list.size() > 1) {

				viewHolder.tuijian_tv.setText("TA推荐" + (list.size() - 1) + "件");
			} else {
				viewHolder.tuijian_tv.setText("TA推荐" + 0 + "件");
			}
			if (flag) {
				viewHolder.line1.setVisibility(View.VISIBLE);
				viewHolder.line2.setVisibility(View.GONE);
			} else {
				viewHolder.line1.setVisibility(View.GONE);
				viewHolder.line2.setVisibility(View.VISIBLE);
			}
			viewHolder.tuijian_tv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					flag = true;
					mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.CLICK_TJ);
				}
			});
			// 点击评论
			viewHolder.pinglun_tv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					flag = false;
					mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.CLICK_TJ);
				}
			});
			if (list2 != null && list2.size() > 1) {

				buyer_comment = (list2.size() - 1) + "";
			}
			viewHolder.pinglun_tv.setText("评论" + buyer_comment + "条");
			viewHolder.yjh2_tv.setText(buyer_yjh);
			viewHolder.time2_tv.setText(buyer_time);
			viewHolder.name2_tv.setText(buyer_name);
			viewHolder.zw_tv.setText(buyer_zw);
			// 绑定图片
			x.image().bind(viewHolder.circleimageview2, buyer_head,
					mImageOptions);
			viewHolder.yjh_tv.setText(yjh);
			viewHolder.time_tv.setText(time);
			viewHolder.name_tv.setText(name);
			viewHolder.ch_value.setText(ch);
			viewHolder.pl_value.setText(pl);
			if (!"1000-0".equals(jw)) {

				viewHolder.jw_value.setText(jw);
			} else {
				viewHolder.jw_value.setText("1000以上");
			}
			// 绑定图片
			x.image().bind(viewHolder.circleimageview, head, mImageOptions);
			// 点击需求的头像
			viewHolder.circleimageview
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(mContext,
									UserHomepageActivity.class);
							intent.putExtra("USER_ID", requirementUserId);
							mContext.startActivity(intent);
						}
					});
			// 点击响应人的头像
			viewHolder.circleimageview2
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(mContext,
									UserHomepageActivity.class);
							intent.putExtra("USER_ID", buyerUserId);
							mContext.startActivity(intent);
						}
					});
		} else {
			if (flag) {
				viewHolder.rl1.setVisibility(View.VISIBLE);
				viewHolder.rl2.setVisibility(View.GONE);
				viewHolder.rl3.setVisibility(View.GONE);
				// 加载推荐
				final AttireScheme attire = list.get(position);
				if (attire != null) {
					//判断是否是百川商品
					//是百川商品
					if ("3".equals(attire.getModFrom())) {
						viewHolder.price_tv.setText("￥"
								+ attire.getPrice() + "元");
						viewHolder.title_tv.setText(attire.getTitle());
						viewHolder.yk_tv.setVisibility(View.GONE);
						viewHolder.sc_tv.setVisibility(View.GONE);
						// 点击详情
						viewHolder.xq_tv
								.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {
										TaoBaoUI.getInstance().showTaokeItemDetailByItemId(activity, attire.getAuction_id());
									}
								});
						// 绑定图片
						x.image().bind(viewHolder.imageview, attire.getPic_url(),
								mImageOptions);

					} else {

						viewHolder.price_tv.setText("￥"
								+ attire.getShop_price() + "元");
						viewHolder.title_tv.setText(attire.getDesc());
						if (!"null".equals(attire.getYq_value())) {
							viewHolder.yk_tv.setVisibility(View.VISIBLE);
							viewHolder.yk_tv.setText("可抵用"
									+ attire.getYq_value() + "衣扣");
						} else {
							viewHolder.yk_tv.setVisibility(View.GONE);
						}
						if ("yes".equals(attire.getCan_try())) {
							viewHolder.sc_tv.setVisibility(View.VISIBLE);
						} else {
							viewHolder.sc_tv.setVisibility(View.GONE);
						}
						// 点击试穿
						viewHolder.sc_tv
								.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										Message msg = new Message();
										Bundle data = new Bundle();
										data.putInt("position", position);
										msg.setData(data);
										msg.what = NetworkAsyncCommonDefines.TRYON;
										mHandler.sendMessage(msg);
									}
								});
						// 点击详情
						viewHolder.xq_tv
								.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										Intent intent = new Intent(mContext,
												ShangPinXiangQingActivity.class);
										intent.putExtra("AttireScheme",
												list.get(position));
										mContext.startActivity(intent);
									}
								});
						// 绑定图片
						x.image().bind(viewHolder.imageview, attire.getThume(),
								mImageOptions);
					}
				}
			} else {
				viewHolder.rl1.setVisibility(View.GONE);
				viewHolder.rl2.setVisibility(View.GONE);
				viewHolder.rl3.setVisibility(View.VISIBLE);
				if (list2 != null && list2.size() > position) {

					// 加载评论
					final BuyerResponse buyer = list2.get(position);
					if (buyer != null) {
						// 点击举报
						viewHolder.jb_tv
								.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										RequirementExec
												.getInstance()
												.jbRecommend2(
														mHandler,
														ManagerUtils
																.getInstance()
																.getUser_id(
																		mContext),
														buyer.getId(),
														NetworkAsyncCommonDefines.XH_COMMENT_S,
														NetworkAsyncCommonDefines.XH_COMMENT_F);
									}
								});
						if (buyer.getIsPraise().equals("1")) {
							Drawable rightDrawable = mContext.getResources()
									.getDrawable(R.drawable.xh_selected);
							rightDrawable.setBounds(0, 0,
									rightDrawable.getMinimumWidth(),
									rightDrawable.getMinimumHeight());
							viewHolder.xh_tv.setCompoundDrawables(null,
									rightDrawable, null, null);
						} else {
							Drawable rightDrawable = mContext.getResources()
									.getDrawable(R.drawable.xh_unselected);
							rightDrawable.setBounds(0, 0,
									rightDrawable.getMinimumWidth(),
									rightDrawable.getMinimumHeight());
							viewHolder.xh_tv.setCompoundDrawables(null,
									rightDrawable, null, null);
						}
						// 点击喜欢
						viewHolder.xh_tv
								.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										if (!buyer.getIsPraise().equals("1")) {
											RequirementExec
													.getInstance()
													.xhRecommend2(
															mHandler,
															ManagerUtils
																	.getInstance()
																	.getUser_id(
																			mContext),
															buyer.getId(),
															NetworkAsyncCommonDefines.XH_COMMENT_S,
															NetworkAsyncCommonDefines.XH_COMMENT_F);
										}
									}
								});
						viewHolder.yjh3_tv.setText(buyer.getWords());
						viewHolder.name3_tv.setText(buyer.getuser_name());
						viewHolder.time3_tv.setText(buyer.getaddTimeShow());
						viewHolder.zw3_tv.setText(buyer.getUser_level());
						// 点击响应人的头像
						viewHolder.circleimageview3
								.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										Intent intent = new Intent(mContext,
												UserHomepageActivity.class);
										intent.putExtra("USER_ID",
												buyer.getuser_id());
										mContext.startActivity(intent);
									}
								});
						// 绑定图片
						x.image().bind(viewHolder.circleimageview3,
								buyer.getuser_avatar(), mImageOptions);
						if (buyer.getAttireList() == null
								|| buyer.getAttireList().size() == 0) {
							viewHolder.recyclerview.setVisibility(View.GONE);
						} else {
							int m, n;
							m = buyer.getAttireList().size() / 3;
							n = buyer.getAttireList().size() % 3;
							LayoutParams lp = (LayoutParams) viewHolder.recyclerview
									.getLayoutParams();
							if (m == 0) {
								lp.height = DensityUtil.dip2px(108 * 1 + 2 * 2);
							} else if (m != 0 && n == 0) {
								lp.height = DensityUtil.dip2px(108 * m + 2 * m
										* 2);
							} else {
								lp.height = DensityUtil.dip2px(108 * (m + 1)
										+ 2 * (m + 1) * 2);
							}
							LogUtil.e("height:" + lp.height);
							viewHolder.recyclerview.setLayoutParams(lp);
							viewHolder.recyclerview.setVisibility(View.VISIBLE);
						}
						GridLayoutAdapter mGridLayoutAdapter = new GridLayoutAdapter(
								mContext, mHandler);
						mGridLayoutAdapter.setData(buyer.getAttireList());
						viewHolder.recyclerview.setAdapter(mGridLayoutAdapter);
						mGridLayoutAdapter
								.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(View view,
											int position) {
										// TODO Auto-generated method stub
										Intent intent = new Intent(mContext,
												PhotoShowActivity.class);
										intent.putExtra("SHOW_WHICH", position);
										String[] urls = new String[buyer
												.getAttireList().size()];
										for (int i = 0; i < buyer
												.getAttireList().size(); i++) {
											urls[i] = buyer.getAttireList()
													.get(i);
										}
										intent.putExtra("PHOTO_URI_DATA", urls);
										mContext.startActivity(intent);
									}
								});
						// if (buyer.getAttireList() != null) {
						//
						// if (buyer.getAttireList().size() > 0) {
						// // 点击第一张图片
						// viewHolder.iv1
						// .setOnClickListener(new OnClickListener() {
						//
						// @Override
						// public void onClick(View v) {
						// // TODO Auto-generated method
						// setIntent(
						// buyer.getAttireList(),
						// 0);
						//
						// }
						// });
						// // 绑定图片
						// x.image().bind(viewHolder.iv1,
						// buyer.getAttireList().get(0),
						// mImageOptions);
						// } else {
						// viewHolder.iv1.setVisibility(View.GONE);
						// viewHolder.iv2.setVisibility(View.GONE);
						// viewHolder.iv3.setVisibility(View.GONE);
						// }
						// if (buyer.getAttireList().size() > 1) {
						// // 点击第二张图片
						// viewHolder.iv2
						// .setOnClickListener(new OnClickListener() {
						//
						// @Override
						// public void onClick(View v) {
						// // TODO Auto-generated method
						// setIntent(
						// buyer.getAttireList(),
						// 1);
						// }
						// });
						// // 绑定图片
						// x.image().bind(viewHolder.iv2,
						// buyer.getAttireList().get(1),
						// mImageOptions);
						// } else {
						// viewHolder.iv2.setVisibility(View.GONE);
						// viewHolder.iv3.setVisibility(View.GONE);
						// }
						// if (buyer.getAttireList().size() > 2) {
						// // 点击第三张图片
						// viewHolder.iv3
						// .setOnClickListener(new OnClickListener() {
						//
						// @Override
						// public void onClick(View v) {
						// // TODO Auto-generated method
						// setIntent(
						// buyer.getAttireList(),
						// 2);
						// }
						// });
						// // 绑定图片
						// x.image().bind(viewHolder.iv3,
						// buyer.getAttireList().get(2),
						// mImageOptions);
						// } else {
						// viewHolder.iv3.setVisibility(View.GONE);
						// }
						// }
					}
				}
			}
		}
	}

	protected void setIntent(ArrayList<String> arrayList, int p) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(mContext, PhotoShowActivity.class);
		intent.putExtra("BitmapUtilsFlag", 1);
		intent.putExtra("URL_TYPE", 1);
		intent.putExtra("SHOW_WHICH", p);
		String[] urls = new String[arrayList.size()];
		for (int i = 0; i < arrayList.size(); i++) {
			urls[i] = arrayList.get(i);
		}
		intent.putExtra("PHOTO_URI_DATA", urls);
		mContext.startActivity(intent);
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.recommendinfo_item, parent, false);
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
		/**
		 * 标题
		 */
		private TextView title_tv;
		/**
		 * 价格
		 */
		private TextView price_tv;
		/**
		 * 衣扣
		 */
		private TextView yk_tv;
		private TextView zw_tv;
		/**
		 * 图片
		 */
		private ImageView imageview;
		/**
		 * 头像
		 */
		private CircleImageView circleimageview;
		/**
		 * 评论人头像
		 */
		private CircleImageView circleimageview3;
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
		private RelativeLayout rl1, rl3;
		private LinearLayout rl2;
		/**
		 * 推荐语
		 */
		private TextView yjh2_tv;
		/**
		 * 顾问昵称
		 */
		private TextView name2_tv;
		/**
		 * 顾问头像
		 */
		private CircleImageView circleimageview2;
		/**
		 * 时间
		 */
		private TextView time2_tv;
		/**
		 * 推荐
		 */
		private TextView tuijian_tv;
		/**
		 * 评论
		 */
		private TextView pinglun_tv;

		private ImageView line1, line2;
		/**
		 * 试穿
		 */
		private TextView sc_tv;
		/**
		 * 试穿
		 */
		private TextView xq_tv;

		/**
		 * 评论语
		 */
		private TextView yjh3_tv;
		/**
		 * 评论昵称
		 */
		private TextView name3_tv;
		/**
		 * 评论职称
		 */
		private TextView zw3_tv;
		/**
		 * 评论时间
		 */
		private TextView time3_tv;
		private TextView xh_tv, jb_tv;
		private RecyclerView recyclerview;

		public ViewHolder(View v, OnItemClickListener itemClick,
				OnItemLongClickListener itemLongClick) {
			super(v);
			// TODO Auto-generated constructor stub
			mOnItemClickListener = itemClick;
			mOnItemLongClickListener = itemLongClick;
			rl1 = (RelativeLayout) v.findViewById(R.id.rl1);
			rl2 = (LinearLayout) v.findViewById(R.id.rl2);
			rl3 = (RelativeLayout) v.findViewById(R.id.rl3);
			circleimageview = (CircleImageView) v
					.findViewById(R.id.circleimageview);
			circleimageview3 = (CircleImageView) v
					.findViewById(R.id.circleimageview3);
			name_tv = (TextView) v.findViewById(R.id.name_tv);
			time_tv = (TextView) v.findViewById(R.id.time_tv);
			ch_value = (TextView) v.findViewById(R.id.ch_value);
			pl_value = (TextView) v.findViewById(R.id.pl_value);
			jw_value = (TextView) v.findViewById(R.id.jw_value);
			yjh_tv = (TextView) v.findViewById(R.id.yjh_tv);
			imageview = (ImageView) v.findViewById(R.id.imageview);
			title_tv = (TextView) v.findViewById(R.id.title_tv);
			price_tv = (TextView) v.findViewById(R.id.price_tv);
			yk_tv = (TextView) v.findViewById(R.id.yk_tv);
			sc_tv = (TextView) v.findViewById(R.id.sc_tv);
			xq_tv = (TextView) v.findViewById(R.id.xq_tv);
			circleimageview2 = (CircleImageView) v
					.findViewById(R.id.circleimageview2);
			yjh2_tv = (TextView) v.findViewById(R.id.yjh2_tv);
			name2_tv = (TextView) v.findViewById(R.id.name2_tv);
			time2_tv = (TextView) v.findViewById(R.id.time2_tv);
			tuijian_tv = (TextView) v.findViewById(R.id.tuijian_tv);
			pinglun_tv = (TextView) v.findViewById(R.id.pinglun_tv);
			line1 = (ImageView) v.findViewById(R.id.line1);
			line2 = (ImageView) v.findViewById(R.id.line2);
			time3_tv = (TextView) v.findViewById(R.id.time3_tv);
			zw3_tv = (TextView) v.findViewById(R.id.zw3_tv);
			name3_tv = (TextView) v.findViewById(R.id.name3_tv);
			yjh3_tv = (TextView) v.findViewById(R.id.yjh3_tv);
			xh_tv = (TextView) v.findViewById(R.id.xh_tv);
			jb_tv = (TextView) v.findViewById(R.id.jb_tv);
			zw_tv = (TextView) v.findViewById(R.id.zw_tv);
			recyclerview = (RecyclerView) v.findViewById(R.id.recyclerview);
			GridLayoutManager glm = new GridLayoutManager(mContext, 3);
			recyclerview.setLayoutManager(glm);

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

	public void setList(ArrayList<AttireScheme> list) {
		// TODO Auto-generated method stub
		this.list = list;
	}

	public void setList2(ArrayList<BuyerResponse> list) {
		// TODO Auto-generated method stub
		this.list2 = list;
	}

	private String buyer_zw, name, time, head, yjh, ch, pl, jw, buyer_name,
			buyer_time, buyer_head, buyer_yjh, buyer_attires, buyer_comment,
			requirementUserId, buyerUserId;

	public void setData(String requirementUserId, String buyerUserId,
			String buyer_zw, String buyer_attires, String buyer_comment,
			String buyer_name, String buyer_time, String buyer_head,
			String buyer_yjh, String name, String time, String head,
			String yjh, String ch, String pl, String jw) {
		this.requirementUserId = requirementUserId;
		this.buyerUserId = buyerUserId;
		this.buyer_zw = buyer_zw;
		this.buyer_attires = buyer_attires;
		this.buyer_comment = buyer_comment;
		this.buyer_name = buyer_name;
		this.buyer_time = buyer_time;
		this.buyer_head = buyer_head;
		this.buyer_yjh = buyer_yjh;
		this.name = name;
		this.time = time;
		this.head = head;
		this.yjh = yjh;
		this.ch = ch;
		this.pl = pl;
		this.jw = jw;
	}

	Requirement rq;

	public void setRQ(Requirement rq) {
		// TODO Auto-generated method stub
		this.rq = rq;
	};
}
