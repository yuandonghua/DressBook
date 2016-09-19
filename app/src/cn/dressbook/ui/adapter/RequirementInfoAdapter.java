package cn.dressbook.ui.adapter;


import java.util.ArrayList;

import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.image.ImageOptions;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.dressbook.app.DressBookApp;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.PhotoShowActivity;
import cn.dressbook.ui.R;
import cn.dressbook.ui.RecommendInfotActivity;
import cn.dressbook.ui.UserHomepageActivity;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.listener.OnItemLongClickListener;
import cn.dressbook.ui.model.BuyerResponse;
import cn.dressbook.ui.model.CustomService;
import cn.dressbook.ui.model.Requirement;
import cn.dressbook.ui.net.RequirementExec;
import cn.dressbook.ui.view.CircleImageView;

/**
 * @description: 需求详情适配器
 * @author:袁东华
 * @time:2015-10-17上午11:12:45
 */
public class RequirementInfoAdapter extends
		Adapter<RequirementInfoAdapter.ViewHolder> {
	private OnItemClickListener mOnItemClickListener;
	private OnItemLongClickListener mOnItemLongClickListener;
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions(ImageView.ScaleType.CENTER_CROP);
	private Context mContext;
	private Handler mHandler;
	/**
	 * 需求列表集合
	 */
	private Requirement requirement;

	public RequirementInfoAdapter(Context mContext, Handler mHandler) {
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.mHandler = mHandler;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return requirement != null
				&& requirement.getBuyerResponseList() != null ? requirement
				.getBuyerResponseList().size() : 1;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int poition) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.requirementinfo_item, parent, false);
		ViewHolder vh = new ViewHolder(v, mOnItemClickListener,
				mOnItemLongClickListener);
		return vh;
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
		if (requirement != null) {

			if (position == 0) {
				viewHolder.xynum_tv.setVisibility(View.VISIBLE);
				viewHolder.ch_tv.setVisibility(View.VISIBLE);
				viewHolder.pl_tv.setVisibility(View.VISIBLE);
				viewHolder.jw_tv.setVisibility(View.VISIBLE);
				viewHolder.rl4.setVisibility(View.GONE);
				viewHolder.zw_tv.setVisibility(View.GONE);
				viewHolder.ch_tv.setText(requirement.getOccasion());
				viewHolder.pl_tv.setText(requirement.getcategoryName());
				if (!"0".equals(requirement.getPriceMax())) {

					viewHolder.jw_tv.setText(requirement.getPriceMin() + "-"
							+ requirement.getPriceMax());
				} else {
					viewHolder.jw_tv.setText(requirement.getPriceMin() + "以上");
				}
				viewHolder.name_tv.setText(requirement.getUserName());
				viewHolder.time_tv.setText(requirement.getAddTime());
				viewHolder.yjh_tv.setText(requirement.getReqDesc());
				// 点击头像
				viewHolder.circleimageview
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(mContext,
										UserHomepageActivity.class);
								intent.putExtra("USER_ID",
										requirement.getUserId() + "");
								mContext.startActivity(intent);
							}
						});
				// 绑定图片
				x.image().bind(viewHolder.circleimageview,
						requirement.getuserAvatar(), mImageOptions,
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
				if (requirement.getPics().length > 0) {
					// 点击第一张图片
					viewHolder.iv1.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(mContext,
									PhotoShowActivity.class);
							intent.putExtra("BitmapUtilsFlag", 1);
							intent.putExtra("URL_TYPE", 1);
							intent.putExtra("SHOW_WHICH", 0);
							intent.putExtra("PHOTO_URI_DATA",
									requirement.getPics());
							mContext.startActivity(intent);
						}
					});
					// 绑定图片
					x.image().bind(viewHolder.iv1, requirement.getPics()[0],
							mImageOptions, new CommonCallback<Drawable>() {

								@Override
								public void onSuccess(Drawable arg0) {
									// TODO Auto-generated method stub
									viewHolder.iv1.setVisibility(View.VISIBLE);
								}

								@Override
								public void onFinished() {
									// TODO Auto-generated method stub

								}

								@Override
								public void onError(Throwable arg0, boolean arg1) {
									// TODO Auto-generated method stub
									viewHolder.iv1.setVisibility(View.GONE);
								}

								@Override
								public void onCancelled(CancelledException arg0) {
									// TODO Auto-generated method stub
									viewHolder.iv1.setVisibility(View.GONE);
								}
							});
				}
				if (requirement.getPics().length > 1) {
					// 点击第二张图片
					viewHolder.iv2.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(mContext,
									PhotoShowActivity.class);
							intent.putExtra("BitmapUtilsFlag", 1);
							intent.putExtra("URL_TYPE", 1);
							intent.putExtra("SHOW_WHICH", 1);
							intent.putExtra("PHOTO_URI_DATA",
									requirement.getPics());
							mContext.startActivity(intent);
						}
					});
					// 绑定图片
					x.image().bind(viewHolder.iv2, requirement.getPics()[1],
							mImageOptions, new CommonCallback<Drawable>() {

								@Override
								public void onSuccess(Drawable arg0) {
									// TODO Auto-generated method stub
									viewHolder.iv2.setVisibility(View.VISIBLE);
								}

								@Override
								public void onFinished() {
									// TODO Auto-generated method stub

								}

								@Override
								public void onError(Throwable arg0, boolean arg1) {
									// TODO Auto-generated method stub
									viewHolder.iv2.setVisibility(View.GONE);
								}

								@Override
								public void onCancelled(CancelledException arg0) {
									// TODO Auto-generated method stub
									viewHolder.iv2.setVisibility(View.GONE);
								}
							});
				}
				if (requirement.getPics().length > 2) {
					// 点击第三张图片
					viewHolder.iv3.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(mContext,
									PhotoShowActivity.class);
							intent.putExtra("BitmapUtilsFlag", 1);
							intent.putExtra("URL_TYPE", 1);
							intent.putExtra("SHOW_WHICH", 2);
							intent.putExtra("PHOTO_URI_DATA",
									requirement.getPics());
							mContext.startActivity(intent);
						}
					});
					// 绑定图片
					x.image().bind(viewHolder.iv3, requirement.getPics()[2],
							mImageOptions, new CommonCallback<Drawable>() {

								@Override
								public void onSuccess(Drawable arg0) {
									// TODO Auto-generated method stub
									viewHolder.iv3.setVisibility(View.VISIBLE);
								}

								@Override
								public void onFinished() {
									// TODO Auto-generated method stub

								}

								@Override
								public void onError(Throwable arg0, boolean arg1) {
									// TODO Auto-generated method stub
									viewHolder.iv3.setVisibility(View.GONE);
								}

								@Override
								public void onCancelled(CancelledException arg0) {
									// TODO Auto-generated method stub
									viewHolder.iv3.setVisibility(View.GONE);
								}
							});
				}
				if (!"0".equals(requirement.getbuyerResponsesNum())) {

					viewHolder.xynum_tv.setText("已有"
							+ requirement.getbuyerResponsesNum() + "人响应");
				} else {
					viewHolder.xynum_tv.setText("暂时无人响应");
				}
			} else {
				final BuyerResponse buyer = requirement.getBuyerResponseList()
						.get(position);
				if (buyer != null) {
					viewHolder.zw_tv.setVisibility(View.VISIBLE);
					viewHolder.xynum_tv.setVisibility(View.GONE);
					viewHolder.ch_tv.setVisibility(View.GONE);
					viewHolder.pl_tv.setVisibility(View.GONE);
					viewHolder.jw_tv.setVisibility(View.GONE);
					viewHolder.rl4.setVisibility(View.VISIBLE);
					viewHolder.name_tv.setText(buyer.getuser_name());
					viewHolder.time_tv.setText(buyer.getaddTimeShow());
					viewHolder.yjh_tv.setText(buyer.getWords());
					viewHolder.zw_tv.setText(buyer.getUser_level());
					
					if ("0".equals(buyer.getbuyerRespPraisesNum())) {
						viewHolder.xh_tv.setText("喜欢");
					} else {
						viewHolder.xh_tv.setText("喜欢("
								+ buyer.getbuyerRespPraisesNum() + ")");
					}
					if ("0".equals(buyer.getbuyerRespCommentsNum())) {
						viewHolder.pingl_tv.setText("评论");
					} else {
						viewHolder.pingl_tv.setText("评论("
								+ buyer.getbuyerRespCommentsNum() + ")");
					}
					if ("0".equals(buyer.getIsPraise())) {
						Drawable rightDrawable = mContext.getResources()
								.getDrawable(R.drawable.xh_unselected);
						rightDrawable.setBounds(0, 0,
								rightDrawable.getMinimumWidth(),
								rightDrawable.getMinimumHeight());
						viewHolder.xh_tv.setCompoundDrawables(null,
								rightDrawable, null, null);

					} else if ("1".equals(buyer.getIsPraise())) {
						Drawable rightDrawable = mContext.getResources()
								.getDrawable(R.drawable.xh_selected);
						rightDrawable.setBounds(0, 0,
								rightDrawable.getMinimumWidth(),
								rightDrawable.getMinimumHeight());
						viewHolder.xh_tv.setCompoundDrawables(null,
								rightDrawable, null, null);

					}
					// 点击喜欢
					viewHolder.xh_tv.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							RequirementExec.getInstance().xhRecommend(
									mHandler,
									ManagerUtils.getInstance().getUser_id(
											mContext), buyer.getId(),
									NetworkAsyncCommonDefines.XH_S,
									NetworkAsyncCommonDefines.XH_F);
						}
					});
					// 点击评论
					viewHolder.pingl_tv
							.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									Intent intent = new Intent(mContext,
											RecommendInfotActivity.class);
									intent.putExtra("requirementUserId",
											requirement.getUserId());
									intent.putExtra("buyerUserId",
											buyer.getuser_id());
									intent.putExtra("requirement_id",
											requirement.getId());
									intent.putExtra("name",
											requirement.getUserName());
									intent.putExtra("time",
											requirement.getAddTime());
									intent.putExtra("head",
											requirement.getuserAvatar());
									intent.putExtra("yjh",
											requirement.getReqDesc());
									intent.putExtra("ch",
											requirement.getOccasion());
									intent.putExtra("pl",
											requirement.getcategoryName());
									intent.putExtra("jw",
											requirement.getPriceMin() + "-"
													+ requirement.getPriceMax());
									intent.putExtra("buyer_id", buyer.getId());
									intent.putExtra("buyer_name",
											buyer.getuser_name());
									intent.putExtra("buyer_time",
											buyer.getaddTimeShow());
									intent.putExtra("buyer_head",
											buyer.getuser_avatar());
									intent.putExtra("buyer_yjh",
											buyer.getWords());
									intent.putExtra("buyer_attires",
											buyer.getbuyerRespAttiresNum());
									intent.putExtra("buyer_comment",
											buyer.getbuyerRespCommentsNum());
									intent.putExtra("ids",
											buyer.getBuyerRespAttiresIds());
									intent.putExtra("isPraise",
											buyer.getIsPraise());
									mContext.startActivity(intent);
								}
							});
					// 点击查看详情
					viewHolder.ckxq_tv
							.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									Intent intent = new Intent(mContext,
											RecommendInfotActivity.class);
									intent.putExtra("requirementUserId",
											requirement.getUserId());
									intent.putExtra("buyerUserId",
											buyer.getuser_id());
									intent.putExtra("requirement_id",
											requirement.getId());
									intent.putExtra("name",
											requirement.getUserName());
									intent.putExtra("time",
											requirement.getAddTime());
									intent.putExtra("head",
											requirement.getuserAvatar());
									intent.putExtra("yjh",
											requirement.getReqDesc());
									intent.putExtra("ch",
											requirement.getOccasion());
									intent.putExtra("pl",
											requirement.getcategoryName());
									intent.putExtra("jw",
											requirement.getPriceMin() + "-"
													+ requirement.getPriceMax());

									intent.putExtra("buyer_id", buyer.getId());
									intent.putExtra("buyer_name",
											buyer.getuser_name());
									intent.putExtra("buyer_time",
											buyer.getaddTimeShow());
									intent.putExtra("buyer_head",
											buyer.getuser_avatar());
									intent.putExtra("buyer_yjh",
											buyer.getWords());
									intent.putExtra("buyer_zw",
											buyer.getUser_level());
									intent.putExtra("buyer_attires",
											buyer.getbuyerRespAttiresNum());
									intent.putExtra("buyer_comment",
											buyer.getbuyerRespCommentsNum());
									intent.putExtra("ids",
											buyer.getBuyerRespAttiresIds());
									intent.putExtra("isPraise",
											buyer.getIsPraise());
									mContext.startActivity(intent);
								}
							});
					// 点击头像
					viewHolder.circleimageview
							.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									Intent intent = new Intent(mContext,
											UserHomepageActivity.class);
									intent.putExtra("USER_ID",
											buyer.getuser_id() + "");
									mContext.startActivity(intent);
								}
							});
					// 绑定图片
					x.image().bind(viewHolder.circleimageview,
							buyer.getuser_avatar(), mImageOptions,
							new CommonCallback<Drawable>() {

								@Override
								public void onSuccess(Drawable arg0) {
									// TODO Auto-generated method stub
									viewHolder.iv1.setVisibility(View.VISIBLE);
								}

								@Override
								public void onFinished() {
									// TODO Auto-generated method stub

								}

								@Override
								public void onError(Throwable arg0, boolean arg1) {
									// TODO Auto-generated method stub
									viewHolder.iv1.setVisibility(View.GONE);
								}

								@Override
								public void onCancelled(CancelledException arg0) {
									// TODO Auto-generated method stub
									viewHolder.iv1.setVisibility(View.GONE);
								}
							});

					final ArrayList<String> attireList = requirement
							.getBuyerResponseList().get(position)
							.getAttireList();
					if (attireList != null) {
						if (attireList.size() > 0) {
							final String url = attireList.get(0);
							// 点击第一张图片
							viewHolder.iv1
									.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											// TODO Auto-generated method stub
											setIntent(
													attireList,
													0);
										}
									});
							// 绑定图片
							x.image().bind(viewHolder.iv1, url, mImageOptions,
									new CommonCallback<Drawable>() {

										@Override
										public void onSuccess(Drawable arg0) {
											// TODO Auto-generated method stub
											viewHolder.iv1
													.setVisibility(View.VISIBLE);
										}

										@Override
										public void onFinished() {
											// TODO Auto-generated method stub

										}

										@Override
										public void onError(Throwable arg0,
												boolean arg1) {
											// TODO Auto-generated method stub
											viewHolder.iv1
													.setVisibility(View.GONE);
										}

										@Override
										public void onCancelled(
												CancelledException arg0) {
											// TODO Auto-generated method stub
											viewHolder.iv1
													.setVisibility(View.GONE);
										}
									});
						} else {
							viewHolder.iv1.setVisibility(View.GONE);
							viewHolder.iv2.setVisibility(View.GONE);
							viewHolder.iv3.setVisibility(View.GONE);
						}
						if (attireList.size() > 1) {

							final String url1 = attireList.get(1);
							// 点击第二张图片
							viewHolder.iv2
									.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											// TODO Auto-generated method stub
											setIntent(
													attireList,
													1);
										}
									});
							// 绑定图片
							x.image().bind(viewHolder.iv2, url1, mImageOptions,
									new CommonCallback<Drawable>() {

										@Override
										public void onSuccess(Drawable arg0) {
											// TODO Auto-generated method stub
											viewHolder.iv2
													.setVisibility(View.VISIBLE);
										}

										@Override
										public void onFinished() {
											// TODO Auto-generated method stub

										}

										@Override
										public void onError(Throwable arg0,
												boolean arg1) {
											// TODO Auto-generated method stub
											viewHolder.iv2
													.setVisibility(View.GONE);
										}

										@Override
										public void onCancelled(
												CancelledException arg0) {
											// TODO Auto-generated method stub
											viewHolder.iv2
													.setVisibility(View.GONE);
										}
									});
						} else {
							viewHolder.iv2.setVisibility(View.GONE);
							viewHolder.iv3.setVisibility(View.GONE);
						}
						if (attireList.size() > 2) {
							final String url3 = attireList.get(2);
							// 点击第三张图片
							viewHolder.iv3
									.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											// TODO Auto-generated method stub
											setIntent(
													attireList,
													2);
										}
									});
							// 绑定图片
							x.image().bind(viewHolder.iv3, url3, mImageOptions,
									new CommonCallback<Drawable>() {

										@Override
										public void onSuccess(Drawable arg0) {
											// TODO Auto-generated method stub
											viewHolder.iv3
													.setVisibility(View.VISIBLE);
										}

										@Override
										public void onFinished() {
											// TODO Auto-generated method stub

										}

										@Override
										public void onError(Throwable arg0,
												boolean arg1) {
											// TODO Auto-generated method stub
											viewHolder.iv3
													.setVisibility(View.GONE);
										}

										@Override
										public void onCancelled(
												CancelledException arg0) {
											// TODO Auto-generated method stub
											viewHolder.iv3
													.setVisibility(View.GONE);
										}
									});
						} else {
							viewHolder.iv3.setVisibility(View.GONE);
						}
					} else {
						viewHolder.iv1.setVisibility(View.GONE);
						viewHolder.iv2.setVisibility(View.GONE);
						viewHolder.iv3.setVisibility(View.GONE);
					}
				} else {
				}

			}
		}
	}

	public void setData(Requirement requirement) {
		this.requirement = requirement;
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
		 * 名字
		 */
		private TextView name_tv;
		/**
		 * 时间
		 */
		private TextView time_tv;
		/**
		 * 场合
		 */
		private TextView ch_tv;
		/**
		 * 品类
		 */
		private TextView pl_tv;
		/**
		 * 价位
		 */
		private TextView jw_tv;
		/**
		 * 一句话
		 */
		private TextView yjh_tv;
		/**
		 * 已响应人数
		 */
		private TextView xynum_tv;
		/**
		 * 够了
		 */
		private TextView gl_tv;
		/**
		 * 查看详情
		 */
		private TextView ckxq_tv;
		/**
		 * 头像
		 */
		private CircleImageView circleimageview;
		/**
		 * 生活照
		 */
		private ImageView iv1, iv2, iv3;
		private RelativeLayout rl4;
		/**
		 * 喜欢
		 */
		private TextView xh_tv;
		/**
		 * 评论
		 */
		private TextView pingl_tv;
		private TextView zw_tv;

		public ViewHolder(View v, OnItemClickListener itemClick,
				OnItemLongClickListener itemLongClick) {
			super(v);
			// TODO Auto-generated constructor stub
			mOnItemClickListener = itemClick;
			mOnItemLongClickListener = itemLongClick;
			circleimageview = (CircleImageView) v
					.findViewById(R.id.circleimageview);
			iv1 = (ImageView) v.findViewById(R.id.iv1);
			iv2 = (ImageView) v.findViewById(R.id.iv2);
			iv3 = (ImageView) v.findViewById(R.id.iv3);
			time_tv = (TextView) v.findViewById(R.id.time_tv);
			name_tv = (TextView) v.findViewById(R.id.name_tv);
			ch_tv = (TextView) v.findViewById(R.id.ch_tv);
			pl_tv = (TextView) v.findViewById(R.id.pl_tv);
			jw_tv = (TextView) v.findViewById(R.id.jw_tv);
			yjh_tv = (TextView) v.findViewById(R.id.yjh_tv);
			xh_tv = (TextView) v.findViewById(R.id.xh_tv);
			pingl_tv = (TextView) v.findViewById(R.id.pingl_tv);
			xynum_tv = (TextView) v.findViewById(R.id.xynum_tv);
			gl_tv = (TextView) v.findViewById(R.id.gl_tv);
			ckxq_tv = (TextView) v.findViewById(R.id.ckxq_tv);
			rl4 = (RelativeLayout) v.findViewById(R.id.rl4);

			zw_tv = (TextView) v.findViewById(R.id.zw_tv);
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

	private boolean flag;

	public void setFlag(boolean flag) {
		// TODO Auto-generated method stub
		this.flag = flag;
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
}
