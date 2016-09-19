package cn.dressbook.ui.adapter;

import java.util.ArrayList;

import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.PhotoShowActivity;
import cn.dressbook.ui.R;
import cn.dressbook.ui.RequirementInfoActivity;
import cn.dressbook.ui.WoBangTaActivity;
import cn.dressbook.ui.UserHomepageActivity;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.listener.OnItemLongClickListener;
import cn.dressbook.ui.model.Requirement;
import cn.dressbook.ui.net.RequirementExec;
import cn.dressbook.ui.view.CircleImageView;

/**
 * @description: 买手的适配器
 * @author:袁东华
 * @time:2015-8-7下午3:17:48
 */
public class BuyerAdapter extends Adapter<BuyerAdapter.ViewHolder> {
	private OnItemClickListener mOnItemClickListener;
	private OnItemLongClickListener mOnItemLongClickListener;
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions(ImageView.ScaleType.CENTER_CROP);
	private Context mContext;
	private Handler mHandler;
	/**
	 * 需求列表集合
	 */
	private ArrayList<Requirement> mRequirementList;

	public BuyerAdapter(Context mContext, Handler mHandler) {
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.mHandler = mHandler;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return mRequirementList != null ? mRequirementList.size() : 0;
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
			LogUtil.e("flag:" + flag);
			// 帮我买
			if (flag) {
				LogUtil.e("帮我买");
				viewHolder.line1.setVisibility(View.VISIBLE);
				viewHolder.line2.setVisibility(View.GONE);
			} else {
				// 我帮人买
				LogUtil.e("我帮人买");
				viewHolder.line1.setVisibility(View.GONE);
				viewHolder.line2.setVisibility(View.VISIBLE);
			}
			// 隐藏需求详情布局
			viewHolder.rl_1.setVisibility(View.GONE);
			// 显示头部view
			viewHolder.fbxq_rl.setVisibility(View.VISIBLE);
			// 点击发布需求
			viewHolder.fbxq_tv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.CLICK_PUBLISH_REQUIREMENT);
				}
			});
			// 点击帮我买
			viewHolder.bwm_tv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
//					viewHolder.line1.setVisibility(View.VISIBLE);
//					viewHolder.line2.setVisibility(View.GONE);
					mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.CLICK_UNDERWAY);
				}
			});
			// 点击我帮人买
			viewHolder.wbrm_tv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
//					viewHolder.line1.setVisibility(View.GONE);
//					viewHolder.line2.setVisibility(View.VISIBLE);
					mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.CLICK_FINISH);
				}
			});

		} else {
			// 隐藏头部view
			viewHolder.fbxq_rl.setVisibility(View.GONE);
			// 显示需求详情布局
			viewHolder.rl_1.setVisibility(View.VISIBLE);
			if (mRequirementList != null && mRequirementList.size() > position) {
				final Requirement requirement = mRequirementList.get(position);
				if (requirement != null) {
					if (position == 1) {
						if (flag) {
							viewHolder.num_tv.setText("! 已累计得到"
									+ buyerRespTotalNum + "人次的帮助");
						} else {
							viewHolder.num_tv.setText("! " + buyerRequTotalNum
									+ "人等着你帮");
						}
						viewHolder.num_tv.setVisibility(View.VISIBLE);
					} else {
						viewHolder.num_tv.setVisibility(View.GONE);
					}
					if (flag) {
						if ("1".equals(requirement.getState())
								|| "2".equals(requirement.getState())) {
							viewHolder.gl_tv
									.setBackgroundResource(R.drawable.textview_red_bg_1);
							viewHolder.gl_tv.setText("够了");
						} else {
							viewHolder.gl_tv
									.setBackgroundResource(R.drawable.textview_black_bg_1);
							viewHolder.gl_tv.setText("已结束");
						}

					} else {
						viewHolder.gl_tv
								.setBackgroundResource(R.drawable.textview_red_bg_1);
						viewHolder.gl_tv.setText("我帮TA");

					}
					viewHolder.gl_tv.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							// 点击够了
							if (flag) {
								if ("1".equals(requirement.getState())
										|| "2".equals(requirement.getState())) {
									RequirementExec
											.getInstance()
											.gl(mHandler,
													ManagerUtils.getInstance()
															.getUser_id(
																	mContext),
													requirement.getId(),
													NetworkAsyncCommonDefines.CLICK_GL_S,
													NetworkAsyncCommonDefines.CLICK_GL_F);
								}
							} else {
								// 点击我帮TA
								Intent intent = new Intent(mContext,
										WoBangTaActivity.class);
								intent.putExtra("childid",
										requirement.getchildId());
								intent.putExtra("id", requirement.getId());
								intent.putExtra("reqdesc",
										requirement.getReqDesc());
								intent.putExtra("addtime",
										requirement.getAddTime());
								intent.putExtra("username",
										requirement.getUserName());
								intent.putExtra("occation",
										requirement.getOccasion());
								intent.putExtra("category",
										requirement.getcategoryName());
								intent.putExtra("pricemax",
										requirement.getPriceMax());
								intent.putExtra("pricemin",
										requirement.getPriceMin());
								intent.putExtra("pricemin",
										requirement.getPriceMin());
								intent.putExtra("useravatar",
										requirement.getuserAvatar());
								intent.putExtra("sex", requirement.getsex());
								intent.putExtra("categoryid",
										requirement.getcategoryId());
								mContext.startActivity(intent);
							}
						}
					});
					// 点击查看详情
					viewHolder.ckxq_tv
							.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									Intent intent = new Intent(mContext,
											RequirementInfoActivity.class);
									intent.putExtra("requirement_id",
											requirement.getId());
									intent.putExtra("flag", flag);
									mContext.startActivity(intent);

								}
							});
					viewHolder.name_tv.setText(requirement.getUserName());
					viewHolder.time_tv.setText(requirement.getAddTime());
					viewHolder.ch_tv.setText(requirement.getOccasion());
					viewHolder.pl_tv.setText(requirement.getcategoryName());
					if (!"0".equals(requirement.getPriceMax())) {

						viewHolder.jw_tv.setText(requirement.getPriceMin()
								+ "-" + requirement.getPriceMax());
					} else {
						viewHolder.jw_tv.setText(requirement.getPriceMin()
								+ "以上");
					}
					if (!"0".equals(requirement.getbuyerResponsesNum())) {

						viewHolder.xynum_tv.setText("已有"
								+ requirement.getbuyerResponsesNum() + "人响应");
						if ("1".equals(requirement.getbuyerResponsesNum())) {
							viewHolder.iv5.setVisibility(View.GONE);
							viewHolder.iv6.setVisibility(View.GONE);
							viewHolder.iv7.setVisibility(View.GONE);
							viewHolder.iv8.setVisibility(View.GONE);
						} else if ("2".equals(requirement
								.getbuyerResponsesNum())) {
							viewHolder.iv6.setVisibility(View.GONE);
							viewHolder.iv7.setVisibility(View.GONE);
							viewHolder.iv8.setVisibility(View.GONE);
						} else if ("3".equals(requirement
								.getbuyerResponsesNum())) {
							viewHolder.iv7.setVisibility(View.GONE);
							viewHolder.iv8.setVisibility(View.GONE);
						} else if ("4".equals(requirement
								.getbuyerResponsesNum())) {
							viewHolder.iv8.setVisibility(View.GONE);
						} else if ("5".equals(requirement
								.getbuyerResponsesNum())) {
						}
					} else {
						viewHolder.xynum_tv.setText("暂时无人响应");
						viewHolder.iv4.setVisibility(View.GONE);
						viewHolder.iv5.setVisibility(View.GONE);
						viewHolder.iv6.setVisibility(View.GONE);
						viewHolder.iv7.setVisibility(View.GONE);
						viewHolder.iv8.setVisibility(View.GONE);

					}
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
									viewHolder.circleimageview
											.setImageResource(R.drawable.un_login_in);
								}

								@Override
								public void onCancelled(CancelledException arg0) {
									// TODO Auto-generated method stub
									viewHolder.circleimageview
											.setImageResource(R.drawable.un_login_in);
								}

							});
					if (requirement.getPics().length > 0) {
						// 点击第一张图片
						viewHolder.iv1
								.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {
										if (clickWhat != null) {
											clickWhat.clickWhat(true);
										}
										Log.i("xx", "点击了第一张图片");
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
						x.image().bind(viewHolder.iv1,
								requirement.getPics()[0], mImageOptions,
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
									}

									@Override
									public void onCancelled(
											CancelledException arg0) {
										// TODO Auto-generated method stub
									}
								});

					} else {

						viewHolder.iv1.setVisibility(View.GONE);
					}
					if (requirement.getPics().length > 1) {
						// 点击第二张图片
						viewHolder.iv2
								.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {
										if (clickWhat != null) {
											clickWhat.clickWhat(true);
										}
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
						x.image().bind(viewHolder.iv2,
								requirement.getPics()[1], mImageOptions,
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
										viewHolder.iv2.setVisibility(View.GONE);
									}

									@Override
									public void onCancelled(
											CancelledException arg0) {
										// TODO Auto-generated method stub
										viewHolder.iv2.setVisibility(View.GONE);
									}
								});

					} else {

						viewHolder.iv2.setVisibility(View.GONE);
					}
					if (requirement.getPics().length > 2) {
						// 点击第三张图片
						viewHolder.iv3
								.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {
										if (clickWhat != null) {
											clickWhat.clickWhat(true);
										}
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
						x.image().bind(viewHolder.iv3,
								requirement.getPics()[2], mImageOptions,
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
										viewHolder.iv3.setVisibility(View.GONE);
									}

									@Override
									public void onCancelled(
											CancelledException arg0) {
										// TODO Auto-generated method stub
										viewHolder.iv3.setVisibility(View.GONE);
									}
								});
					} else {

						viewHolder.iv3.setVisibility(View.GONE);
					}
					// 设置推荐人的头像
					if (requirement.getBuyerResponsesAvatar() != null
							&& requirement.getBuyerResponsesAvatar().length > 0) {
						if (requirement.getBuyerResponsesAvatar().length > 0) {
							viewHolder.iv4.setVisibility(View.VISIBLE);
							// 绑定图片
							x.image().bind(viewHolder.iv4,
									requirement.getBuyerResponsesAvatar()[0],
									mImageOptions,
									new CommonCallback<Drawable>() {

										@Override
										public void onSuccess(Drawable arg0) {
											// TODO Auto-generated method stub
											viewHolder.iv4
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
											viewHolder.iv4
													.setVisibility(View.GONE);
										}

										@Override
										public void onCancelled(
												CancelledException arg0) {
											// TODO Auto-generated method stub
											viewHolder.iv4
													.setVisibility(View.GONE);
										}
									});
						} else {
							viewHolder.iv5.setVisibility(View.GONE);
							viewHolder.iv6.setVisibility(View.GONE);
							viewHolder.iv7.setVisibility(View.GONE);
							viewHolder.iv8.setVisibility(View.GONE);

						}
						if (requirement.getBuyerResponsesAvatar().length > 1) {
							viewHolder.iv5.setVisibility(View.VISIBLE);
							// 绑定图片
							x.image().bind(viewHolder.iv5,
									requirement.getBuyerResponsesAvatar()[1],
									mImageOptions,
									new CommonCallback<Drawable>() {

										@Override
										public void onSuccess(Drawable arg0) {
											// TODO Auto-generated method stub
											viewHolder.iv5
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
											viewHolder.iv5
													.setVisibility(View.GONE);
										}

										@Override
										public void onCancelled(
												CancelledException arg0) {
											// TODO Auto-generated method stub
											viewHolder.iv5
													.setVisibility(View.GONE);
										}
									});
						} else {
							viewHolder.iv6.setVisibility(View.GONE);
							viewHolder.iv7.setVisibility(View.GONE);
							viewHolder.iv8.setVisibility(View.GONE);

						}
						if (requirement.getBuyerResponsesAvatar().length > 2) {
							viewHolder.iv6.setVisibility(View.VISIBLE);
							// 绑定图片
							x.image().bind(viewHolder.iv6,
									requirement.getBuyerResponsesAvatar()[2],
									mImageOptions,
									new CommonCallback<Drawable>() {

										@Override
										public void onSuccess(Drawable arg0) {
											// TODO Auto-generated method stub
											viewHolder.iv6
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
											viewHolder.iv6
													.setVisibility(View.GONE);
										}

										@Override
										public void onCancelled(
												CancelledException arg0) {
											// TODO Auto-generated method stub
											viewHolder.iv6
													.setVisibility(View.GONE);
										}
									});
						} else {
							viewHolder.iv7.setVisibility(View.GONE);
							viewHolder.iv8.setVisibility(View.GONE);

						}
						if (requirement.getBuyerResponsesAvatar().length > 3) {
							viewHolder.iv7.setVisibility(View.VISIBLE);
							// 绑定图片
							x.image().bind(viewHolder.iv7,
									requirement.getBuyerResponsesAvatar()[3],
									mImageOptions,
									new CommonCallback<Drawable>() {

										@Override
										public void onSuccess(Drawable arg0) {
											// TODO Auto-generated method stub
											viewHolder.iv7
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
											viewHolder.iv7
													.setVisibility(View.GONE);
										}

										@Override
										public void onCancelled(
												CancelledException arg0) {
											// TODO Auto-generated method stub
											viewHolder.iv7
													.setVisibility(View.GONE);
										}
									});
						} else {
							viewHolder.iv8.setVisibility(View.GONE);

						}
						if (requirement.getBuyerResponsesAvatar().length > 4) {
							viewHolder.iv8.setVisibility(View.VISIBLE);
							// 绑定图片
							x.image().bind(viewHolder.iv8,
									requirement.getBuyerResponsesAvatar()[4],
									mImageOptions,
									new CommonCallback<Drawable>() {

										@Override
										public void onSuccess(Drawable arg0) {
											// TODO Auto-generated method stub
											viewHolder.iv8
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
											viewHolder.iv8
													.setVisibility(View.GONE);
										}

										@Override
										public void onCancelled(
												CancelledException arg0) {
											// TODO Auto-generated method stub
											viewHolder.iv8
													.setVisibility(View.GONE);
										}
									});
						} else {

						}
					} else {
					}
				}
			}
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int poition) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.buyer_item, parent, false);
		ViewHolder vh = new ViewHolder(v, mOnItemClickListener,
				mOnItemLongClickListener);
		return vh;
	}

	public void setData(ArrayList<Requirement> requirementList) {
		mRequirementList = null;
		this.mRequirementList = requirementList;
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
		private TextView num_tv;
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
		/**
		 * 已响应人的头像
		 */
		private CircleImageView iv4, iv5, iv6, iv7, iv8;
		/**
		 * 需求的布局
		 */
		private RelativeLayout rl_1;
		/**
		 * 头部的view
		 */
		private RelativeLayout fbxq_rl;
		/**
		 * 已响应人的头像的布局
		 */
		private RelativeLayout rl4, rl5, rl6, rl7, rl8;

		/**
		 * 帮我买的下划线
		 */
		private ImageView line1;
		/**
		 * 我帮人买的下划线
		 */
		private ImageView line2;
		/**
		 * 发布需求按钮
		 */
		private TextView fbxq_tv;
		/**
		 * 帮我买按钮
		 */
		private TextView bwm_tv;
		/**
		 * 我帮人买按钮
		 */
		private TextView wbrm_tv;

		public ViewHolder(View v, OnItemClickListener itemClick,
				OnItemLongClickListener itemLongClick) {
			super(v);
			// TODO Auto-generated constructor stub
			mOnItemClickListener = itemClick;
			mOnItemLongClickListener = itemLongClick;
			line1 = (ImageView) v.findViewById(R.id.line1);
			line2 = (ImageView) v.findViewById(R.id.line2);
			num_tv = (TextView) v.findViewById(R.id.num_tv);
			circleimageview = (CircleImageView) v
					.findViewById(R.id.circleimageview);
			iv1 = (ImageView) v.findViewById(R.id.iv1);
			iv2 = (ImageView) v.findViewById(R.id.iv2);
			iv3 = (ImageView) v.findViewById(R.id.iv3);
			iv4 = (CircleImageView) v.findViewById(R.id.iv4);
			iv5 = (CircleImageView) v.findViewById(R.id.iv5);
			iv6 = (CircleImageView) v.findViewById(R.id.iv6);
			iv7 = (CircleImageView) v.findViewById(R.id.iv7);
			iv8 = (CircleImageView) v.findViewById(R.id.iv8);
			time_tv = (TextView) v.findViewById(R.id.time_tv);
			name_tv = (TextView) v.findViewById(R.id.name_tv);
			ch_tv = (TextView) v.findViewById(R.id.ch_tv);
			pl_tv = (TextView) v.findViewById(R.id.pl_tv);
			jw_tv = (TextView) v.findViewById(R.id.jw_tv);
			yjh_tv = (TextView) v.findViewById(R.id.yjh_tv);

			xynum_tv = (TextView) v.findViewById(R.id.xynum_tv);
			xynum_tv = (TextView) v.findViewById(R.id.xynum_tv);
			gl_tv = (TextView) v.findViewById(R.id.gl_tv);
			ckxq_tv = (TextView) v.findViewById(R.id.ckxq_tv);

			rl_1 = (RelativeLayout) v.findViewById(R.id.rl_1);
			fbxq_rl = (RelativeLayout) v.findViewById(R.id.fbxq_rl);
			rl4 = (RelativeLayout) v.findViewById(R.id.rl4);
			rl5 = (RelativeLayout) v.findViewById(R.id.rl5);
			rl6 = (RelativeLayout) v.findViewById(R.id.rl6);
			rl7 = (RelativeLayout) v.findViewById(R.id.rl7);
			rl8 = (RelativeLayout) v.findViewById(R.id.rl8);

			fbxq_tv = (TextView) v.findViewById(R.id.fbxq_tv);
			bwm_tv = (TextView) v.findViewById(R.id.bwm_tv);
			wbrm_tv = (TextView) v.findViewById(R.id.wbrm_tv);
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
		LogUtil.e("setFlag:" + flag);
		this.flag = flag;
	}

	private String buyerRespTotalNum = "0";

	public void setNum1(String buyerRespTotalNum) {
		// TODO Auto-generated method stub
		this.buyerRespTotalNum = buyerRespTotalNum;
	}

	private String buyerRequTotalNum = "0";

	public void setNum2(String buyerRequTotalNum) {
		// TODO Auto-generated method stub
		this.buyerRequTotalNum = buyerRequTotalNum;
	}

	private OnClickWhat clickWhat;

	public void setOnClickWhat(OnClickWhat clickWhat) {
		this.clickWhat = clickWhat;
	}

	/**
	 * 判断点击的内容是否是图片
	 * 
	 * @author 15712
	 * 
	 */
	public interface OnClickWhat {
		void clickWhat(boolean isImage);
	}
}
