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
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.R;
import cn.dressbook.ui.UserHomepageActivity;
import cn.dressbook.ui.bean.MeitanBeanArticleComment;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.view.CircleImageView2;
import cn.dressbook.ui.view.HyperGridView;

/**
 * TODO
 * 
 * @author LiShen
 * @company Gifted Youngs Workshop
 * @date 2015-10-16 上午11:45:04
 * @since
 * @version
 */
@SuppressLint("InflateParams")
@SuppressWarnings("deprecation")
public class ArticleCommentAdapter extends BaseAdapter {

	/**
	 * TODO
	 */
	private Activity mContext;
	/**
	 * TODO
	 */
	private List<MeitanBeanArticleComment> data = new ArrayList<MeitanBeanArticleComment>();
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions();
	/**
	 * TODO 点赞请求cd
	 */
	private boolean likeCooldown = false;
	/**
	 * TODO
	 */
	private boolean cancleLikeCooldown = false;
	/**
	 * TODO
	 */
	private boolean reportCooldown = false;

	private Handler mHandler;

	public ArticleCommentAdapter(Activity activity, Handler handler) {
		this.mContext = activity;
		mHandler = handler;
	}

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
	public MeitanBeanArticleComment getItem(int position) {
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
					R.layout.item_listview_article_comment, null);
			holder.ivItemArticleCommentPortrait = (CircleImageView2) convertView
					.findViewById(R.id.ivItemArticleCommentPortrait);
			holder.tvItemArticleCommentUserName = (TextView) convertView
					.findViewById(R.id.tvItemArticleCommentUserName);
			holder.tvItemArticleCommentUserLevel = (TextView) convertView
					.findViewById(R.id.tvItemArticleCommentUserLevel);
			holder.tvItemArticleCommentDate = (TextView) convertView
					.findViewById(R.id.tvItemArticleCommentDate);
			holder.tvItemArticleCommentTitle = (TextView) convertView
					.findViewById(R.id.tvItemArticleCommentTitle);
			holder.tvItemArticleCommentContent = (TextView) convertView
					.findViewById(R.id.tvItemArticleCommentContent);
			holder.gvItemArticleCommentPhotos = (HyperGridView) convertView
					.findViewById(R.id.gvItemArticleCommentPhotos);
			holder.llItemArticleCommentReport = (LinearLayout) convertView
					.findViewById(R.id.llItemArticleCommentReport);
			holder.llItemArticleCommentLike = (LinearLayout) convertView
					.findViewById(R.id.llItemArticleCommentLike);
			holder.tvItemArticleCommentLike = (TextView) convertView
					.findViewById(R.id.tvItemArticleCommentLike);
			holder.ivItemArticleCommentLike = (ImageView) convertView
					.findViewById(R.id.ivItemArticleCommentLike);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final MeitanBeanArticleComment articleComment = data.get(position);
		// 用户昵称
		holder.tvItemArticleCommentUserName.setText(articleComment
				.getUserName());
		// 文章照片
		holder.gvItemArticleCommentPhotos.setAdapter(new ArticlePhotosAdapter(
				mContext, articleComment.getCmtReplyImgs()));
		// 用户头像
		// 绑定图片
		x.image().bind(holder.ivItemArticleCommentPortrait,
				articleComment.getUserAvatar(), mImageOptions,
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
		// 点击头像
		holder.ivItemArticleCommentPortrait
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(mContext,
								UserHomepageActivity.class);
						intent.putExtra("IS_FOLLOWED", 0);
						intent.putExtra("USER_ID", articleComment.getUserId()
								+ "");
						mContext.startActivity(intent);
						mContext.finish();
					}
				});
		// 用户等级
		holder.tvItemArticleCommentUserLevel.setText(articleComment
				.getUserLevel());
		// 发表时间
		holder.tvItemArticleCommentDate
				.setText(articleComment.getAddTimeShow());
		if (!"null".equals(articleComment.getTitle())) {

			// 文章标题
			holder.tvItemArticleCommentTitle.setText(articleComment.getTitle());
		} else {
			holder.tvItemArticleCommentTitle.setVisibility(View.GONE);
		}
		// 文章内容
		holder.tvItemArticleCommentContent.setText(articleComment.getContent());
		// 本人是否赞过
		if (articleComment.getIsPraise() == 1) {
			holder.ivItemArticleCommentLike.setImageDrawable(mContext
					.getResources().getDrawable(R.drawable.ic_orange_like));
			holder.tvItemArticleCommentLike.setTextColor(mContext
					.getResources().getColor(R.color.main_text_orange));
		} else {
			holder.ivItemArticleCommentLike.setImageDrawable(mContext
					.getResources().getDrawable(R.drawable.ic_grey_like));
			holder.tvItemArticleCommentLike.setTextColor(mContext
					.getResources().getColor(R.color.main_text_grey));
		}
		// 赞数
		holder.tvItemArticleCommentLike.setText("喜欢("
				+ articleComment.getPraiseNum() + ")");
		// 点赞
		holder.llItemArticleCommentLike
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (!likeCooldown
								&& data.get(position).getIsPraise() != 1) {
							likeArticleComment(position);
						}
						if (!cancleLikeCooldown
								&& data.get(position).getIsPraise() == 1) {
							cancleLikeArticleComment(position);
						}
					}
				});
		// 举报
		holder.llItemArticleCommentReport
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (!reportCooldown)
							reportArticleComment(position);
					}
				});
		return convertView;
	}

	/**
	 * TODO 点赞
	 * 
	 * @author LiShen
	 * @date 2015-10-21 下午4:04:23
	 * @see
	 */
	private void likeArticleComment(final int index) {
		likeCooldown = true;
		// 对评论点赞
		RequestParams params = new RequestParams(
				PathCommonDefines.DIANZAN_PINGLUN);
		params.addBodyParameter("user_id", ManagerUtils.getInstance()
				.getUser_id(mContext));
		params.addBodyParameter("reply_id", data.get(index).getId() + "");
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
				likeCooldown = false;
			}

			@Override
			public void onCancelled(CancelledException cex) {
			}

			@Override
			public void onFinished() {

			}
		});

	}

	private void cancleLikeArticleComment(final int index) {
		cancleLikeCooldown = true;
		// 对评论取消点赞
		RequestParams params = new RequestParams(
				PathCommonDefines.QUXIAO_DIANZAN_PINGLUN);
		params.addBodyParameter("user_id", ManagerUtils.getInstance().getUser_id(mContext));
		params.addBodyParameter("reply_id", data.get(index).getId() + "");
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				data.get(index).setIsPraise(0);
				data.get(index).setPraiseNum(data.get(index).getPraiseNum() - 1);
				// 刷新数据
				notifyDataSetChanged();

				cancleLikeCooldown = false;

			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				cancleLikeCooldown = false;
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
	 * 
	 * TODO 举报博文评论
	 * 
	 * @author LiShen
	 * @date 2015-10-24 下午4:44:14
	 * @param index
	 * @see
	 */
	private void reportArticleComment(final int index) {
		reportCooldown = true;

		// 举报
		RequestParams params = new RequestParams(PathCommonDefines.JUBAOBOWEN);
		params.addBodyParameter("user_id", ManagerUtils.getInstance().getUser_id(mContext));
		params.addBodyParameter("model", "CmtReply");
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
		reportCooldown = false;
	}

	class ViewHolder {
		CircleImageView2 ivItemArticleCommentPortrait;
		TextView tvItemArticleCommentUserName;
		TextView tvItemArticleCommentUserLevel;
		TextView tvItemArticleCommentDate;
		TextView tvItemArticleCommentTitle;
		TextView tvItemArticleCommentContent;
		HyperGridView gvItemArticleCommentPhotos;
		LinearLayout llItemArticleCommentReport;
		LinearLayout llItemArticleCommentLike;
		TextView tvItemArticleCommentLike;
		ImageView ivItemArticleCommentLike;
	}

	/**
	 * TODO 设置 data 的值
	 */
	public void setData(List<MeitanBeanArticleComment> data) {
		this.data = data;
	}
}
