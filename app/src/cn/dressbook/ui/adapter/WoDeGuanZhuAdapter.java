package cn.dressbook.ui.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.BoWenZhengWenActivity;
import cn.dressbook.ui.R;
import cn.dressbook.ui.UserHomepageActivity;
import cn.dressbook.ui.bean.MeitanBeanArticle;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.net.MeiTanExec;
import cn.dressbook.ui.view.CircleImageView2;
import cn.dressbook.ui.view.HyperGridView;

/**
 * @description: 我的关注适配器
 * @author:袁东华
 * @time:2015-10-27下午3:09:11
 */
@SuppressLint("InflateParams")
@SuppressWarnings("deprecation")
public class WoDeGuanZhuAdapter extends BaseAdapter {

	/**
	 * TODO
	 */
	private Activity mContext;
	/**
	 * TODO
	 */
	private List<MeitanBeanArticle> data = new ArrayList<MeitanBeanArticle>();
	/**
	 * TODO 点赞请求cd
	 */
	private boolean likeCooldown = false;
	/**
	 * TODO 取消点赞请求cd
	 */
	private boolean canclelikeCooldown = false;
	/**
	 * TODO 举报请求cd
	 */
	private boolean reportCooldown = false;

	private Handler mHandler;

	public WoDeGuanZhuAdapter(Activity activity, Handler handler) {
		this.mContext = activity;
		mHandler = handler;
	}

	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions(ImageView.ScaleType.CENTER_CROP);

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-16 上午11:45:05
	 * @return
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return data.size();
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-16 上午11:45:05
	 * @param position
	 * @return
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public MeitanBeanArticle getItem(int position) {
		return data.get(position);
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-16 上午11:45:05
	 * @param position
	 * @return
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return data.get(position).hashCode();
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-16 上午11:45:05
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return
	 * @see android.widget.Adapter#getView(int, View, ViewGroup)
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();
		if (convertView == null) {
			convertView = mContext.getLayoutInflater().inflate(
					R.layout.item_listview_article, null);
			holder.ivItemArticlePortrait = (CircleImageView2) convertView
					.findViewById(R.id.ivItemArticlePortrait);
			holder.tvItemArticleUserName = (TextView) convertView
					.findViewById(R.id.tvItemArticleUserName);
			holder.tvItemArticleUserLevel = (TextView) convertView
					.findViewById(R.id.tvItemArticleUserLevel);
			holder.tvItemArticleDate = (TextView) convertView
					.findViewById(R.id.tvItemArticleDate);
			holder.tvItemArticleTitle = (TextView) convertView
					.findViewById(R.id.tvItemArticleTitle);
			holder.tvItemArticleContent = (TextView) convertView
					.findViewById(R.id.tvItemArticleContent);
			holder.gvItemArticlePhotos = (HyperGridView) convertView
					.findViewById(R.id.gvItemArticlePhotos);
			holder.llItemArticleReport = (LinearLayout) convertView
					.findViewById(R.id.llItemArticleReport);
			holder.llItemArticleComment = (LinearLayout) convertView
					.findViewById(R.id.llItemArticleComment);
			holder.tvItemArticleCommentNum = (TextView) convertView
					.findViewById(R.id.tvItemArticleCommentNum);
			holder.llItemArticleLike = (LinearLayout) convertView
					.findViewById(R.id.llItemArticleLike);
			holder.ivItemArticleLike = (ImageView) convertView
					.findViewById(R.id.ivItemArticleLike);
			holder.tvItemArticleLikeNum = (TextView) convertView
					.findViewById(R.id.tvItemArticleLikeNum);
			holder.llItemArticle = (LinearLayout) convertView
					.findViewById(R.id.llItemArticle);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final MeitanBeanArticle article = data.get(position);
		// 用户昵称
		holder.tvItemArticleUserName.setText(article.getUserName());
		// 文章照片
		holder.gvItemArticlePhotos.setAdapter(new ArticlePhotosAdapter(
				mContext, article.getCmtPostsImgs()));
		// 用户头像
		holder.ivItemArticlePortrait.setImageResource(R.drawable.un_login_in);
		// 绑定图片
		x.image().bind(holder.ivItemArticlePortrait, article.getUserAvatar(),
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

		// 点击头像
		holder.ivItemArticlePortrait.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, UserHomepageActivity.class);
				intent.putExtra("IS_FOLLOWED", article.getIsFollow());
				intent.putExtra("USER_ID", article.getUserId() + "");
				mContext.startActivity(intent);
			}
		});
		// 评论数
		holder.tvItemArticleCommentNum.setText("评论(" + article.getCmdNum()
				+ ")");
		// 用户等级
		holder.tvItemArticleUserLevel.setText(article.getUserLevel());
		// 发表时间
		holder.tvItemArticleDate.setText(article.getAddTimeShow());
		// 文章标题
		holder.tvItemArticleTitle.setText(article.getTitle());
		// 文章内容
		holder.tvItemArticleContent.setText(article.getContent());
		// 赞数
		holder.tvItemArticleLikeNum.setText("喜欢(" + article.getPraiseNum()
				+ ")");
		// 本人是否赞过
		if (article.getIsPraise() == 1) {
			holder.ivItemArticleLike.setImageDrawable(mContext.getResources()
					.getDrawable(R.drawable.ic_orange_like));
			holder.tvItemArticleLikeNum.setTextColor(mContext.getResources()
					.getColor(R.color.main_text_orange));
		} else {
			holder.ivItemArticleLike.setImageDrawable(mContext.getResources()
					.getDrawable(R.drawable.ic_grey_like));
			holder.tvItemArticleLikeNum.setTextColor(mContext.getResources()
					.getColor(R.color.main_text_grey));
		}
		// 点赞或者取消点赞
		holder.llItemArticleLike.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!likeCooldown && data.get(position).getIsPraise() != 1) {
					likeArticle(position);
				}
				if (!canclelikeCooldown
						&& data.get(position).getIsPraise() == 1) {
					cancleLikeArticle(position);
				}
			}
		});
		// 跳转博文详情
		holder.llItemArticle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mContext.startActivity(new Intent(mContext,
						BoWenZhengWenActivity.class).putExtra("ARTICLE_ID",
						data.get(position).getId() + ""));
			}
		});
		// 点击评论
		holder.llItemArticleComment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mContext.startActivity(new Intent(mContext,
						BoWenZhengWenActivity.class).putExtra("TO_COMMENT",
						true).putExtra("ARTICLE_ID",
						data.get(position).getId() + ""));
			}
		});
		// 点击举报
		holder.llItemArticleReport.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!reportCooldown)
					reportArticle(position);
			}
		});
		return convertView;
	}

	/**
	 * TODO 举报博文
	 * 
	 * @author LiShen
	 * @date 2015-10-24 下午4:08:37
	 * @see
	 */
	private void reportArticle(final int index) {
		reportCooldown = true;
		RequestParams params = new RequestParams(PathCommonDefines.JUBAOBOWEN);
		params.addBodyParameter("user_id", ManagerUtils.getInstance()
				.getUser_id(mContext));
		params.addBodyParameter("model", "CmtPosts");
		params.addBodyParameter("model_id", data.get(index).getId() + "");
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				reportCooldown = false;
				mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.JUBAOBOWEN_S);

			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				reportCooldown = false;
				mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.JUBAOBOWEN_F);
			}

			@Override
			public void onCancelled(CancelledException cex) {
			}

			@Override
			public void onFinished() {

			}
		});

	}

	/**
	 * TODO 取消点赞
	 * 
	 * @author LiShen
	 * @date 2015-10-24 下午3:44:13
	 * @param position
	 * @see
	 */
	private void cancleLikeArticle(final int index) {
		canclelikeCooldown = true;
		// 取消点赞
		RequestParams params = new RequestParams(
				PathCommonDefines.QUXIAODIANZAN);
		params.addBodyParameter("user_id", ManagerUtils.getInstance().getUser_id(mContext));
		params.addBodyParameter("posts_id", data.get(index).getId() + "");
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				data.get(index).setIsPraise(0);
				data.get(index).setPraiseNum(data.get(index).getPraiseNum() - 1);
				// 刷新数据
				notifyDataSetChanged();

				canclelikeCooldown = false;

			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
			}

			@Override
			public void onCancelled(CancelledException cex) {
			}

			@Override
			public void onFinished() {

			}
		});
		
	}

	/**
	 * TODO 点赞
	 * 
	 * @author LiShen
	 * @date 2015-10-21 下午4:04:23
	 * @see
	 */
	private void likeArticle(final int index) {
		likeCooldown = true;
		RequestParams params = new RequestParams(PathCommonDefines.DIANZAN);
		params.addBodyParameter("user_id", ManagerUtils.getInstance()
				.getUser_id(mContext));
		params.addBodyParameter("posts_id", data.get(index).getId() + "");
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				data.get(index).setIsPraise(1);
				data.get(index)
						.setPraiseNum(data.get(index).getPraiseNum() + 1);
				// 刷新数据
				notifyDataSetChanged();
				likeCooldown = false;
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
			}

			@Override
			public void onCancelled(CancelledException cex) {
			}

			@Override
			public void onFinished() {

			}
		});

	}

	class ViewHolder {
		CircleImageView2 ivItemArticlePortrait;
		TextView tvItemArticleUserName;
		TextView tvItemArticleUserLevel;
		TextView tvItemArticleDate;
		TextView tvItemArticleTitle;
		TextView tvItemArticleContent;
		HyperGridView gvItemArticlePhotos;
		LinearLayout llItemArticleReport;
		LinearLayout llItemArticleComment;
		TextView tvItemArticleCommentNum;
		LinearLayout llItemArticleLike;
		ImageView ivItemArticleLike;
		TextView tvItemArticleLikeNum;
		LinearLayout llItemArticle;
	}

	/**
	 * TODO 设置 data 的值
	 */
	public void setData(List<MeitanBeanArticle> data) {
		this.data = data;
		notifyDataSetChanged();
	}
}
