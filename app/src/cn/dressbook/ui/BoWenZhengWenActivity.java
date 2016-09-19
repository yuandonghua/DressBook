package cn.dressbook.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.adapter.ArticleCommentAdapter;
import cn.dressbook.ui.adapter.ArticlePhotosAdapter;
import cn.dressbook.ui.adapter.PhotoShowAdapter;
import cn.dressbook.ui.base.MeitanActivity;
import cn.dressbook.ui.bean.MeitanBeanArticle;
import cn.dressbook.ui.bean.MeitanBeanArticleComment;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.net.MeiTanExec;
import cn.dressbook.ui.net.ShareExec;
import cn.dressbook.ui.utils.DateTimeUtils;
import cn.dressbook.ui.utils.FileUtils;
import cn.dressbook.ui.utils.ImageUtils;
import cn.dressbook.ui.utils.ImageUtils2;
import cn.dressbook.ui.utils.UriUtils;
import cn.dressbook.ui.view.CircleImageView2;
import cn.dressbook.ui.view.HyperGridView;
import cn.dressbook.ui.view.HyperListView;
import cn.dressbook.ui.view.crop.Crop;

import com.umeng.analytics.social.UMSocialService;

/**
 * TODO 博文详情界面
 * 
 * @author LiShen
 * @company Gifted Youngs Workshop
 * @date 2015-10-21 下午6:09:43
 * @since
 * @version
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
@SuppressLint("InflateParams")
@ContentView(R.layout.activity_article_detail)
public class BoWenZhengWenActivity extends BaseActivity {
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions();
	private Context mContext = BoWenZhengWenActivity.this;
	/**
	 * 返回按钮
	 */
	@ViewInject(R.id.back_rl)
	private RelativeLayout back_rl;
	/**
	 * 发博文
	 */
	@ViewInject(R.id.operate_tv)
	private TextView operate_tv;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	private CircleImageView2 ivArticleDetailPortrait;
	private TextView tvArticleDetailUserName;
	private TextView tvArticleDetailUserLevel;
	private TextView tvArticleDetailDate;
	private TextView tvArticleDetailFocus;
	private TextView tvArticleDetailTitle;
	private TextView tvArticleDetailContent;
	private HyperGridView gvArticleDetailPhotos;
	private TextView tvArticleDetailCommentsNum;
	private HyperListView lvArticleDetailComment;
	private RelativeLayout rlArticleDetailLike;
	private ImageView ivArticleDetailLike;
	private TextView tvArticleDetailLike;
	private RelativeLayout rlArticleDetailComment;
	private RelativeLayout rlArticleDetailTransmit;
	private RelativeLayout rlArticleDetailCommentInput;
	private TextView tvArticleDetailCommentSubmit;
	private ImageView ivArticleDetailAddPhoto;
	private EditText etArticleDetailCommentContent;
	private LinearLayout llArticleDetailAddPhoto;
	private HyperGridView gvArticleDetailUploadPhotos;

	private String articleId;

	private ArticleCommentAdapter articleCommentAdapter;
	private UploadPhotosAdapter uploadPhotosAdapter;

	private ArrayList<MeitanBeanArticleComment> commentList;
	private MeitanBeanArticle article;

	private Activity activity;

	private boolean isFocused = false;
	private boolean isLiked = false;

	private boolean focusCooldown = false;
	private boolean cancelFocusCooldown = false;
	private boolean likeCooldown = false;
	private boolean cancleLikeCooldown = false;
	private boolean isEditComment = false;

	private boolean hasCommentContent = false;

	public static final int FOR_REPORT = 112;

	private ProgressDialog progressDialog;
	/**
	 * TODO 提交冷却，防止重复提交请求
	 */
	private boolean submitCooldown = false;
	/**
	 * TODO 启动相机的intent code
	 */
	private static final int PORTRAIT_TEMP_SHOT = 1112;
	/**
	 * TODO 裁剪拍照的照片
	 */
	private static final int CROP_TYPE_SHOT = 127;
	/**
	 * TODO 裁剪本地相册
	 */
	private static final int CROP_TYPE_IMAGE = 128;

	private String photoShotTepUri = "";
	private String photoCropTempUri = "";
	private String commentContent = "";
	/**
	 * TODO 待上传的图片
	 */
	private List<String> listUploadPhotos;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("博文正文");
		operate_tv.setText("发博文");
		operate_tv.setVisibility(View.VISIBLE);
		ivArticleDetailPortrait = (CircleImageView2) findViewById(R.id.ivArticleDetailPortrait);
		tvArticleDetailUserName = (TextView) findViewById(R.id.tvArticleDetailUserName);
		tvArticleDetailUserLevel = (TextView) findViewById(R.id.tvArticleDetailUserLevel);
		tvArticleDetailDate = (TextView) findViewById(R.id.tvArticleDetailDate);
		tvArticleDetailFocus = (TextView) findViewById(R.id.tvArticleDetailFocus);
		tvArticleDetailTitle = (TextView) findViewById(R.id.tvArticleDetailTitle);
		tvArticleDetailContent = (TextView) findViewById(R.id.tvArticleDetailContent);
		gvArticleDetailPhotos = (HyperGridView) findViewById(R.id.gvArticleDetailPhotos);
		tvArticleDetailCommentsNum = (TextView) findViewById(R.id.tvArticleDetailCommentsNum);
		lvArticleDetailComment = (HyperListView) findViewById(R.id.lvArticleDetailComment);
		rlArticleDetailLike = (RelativeLayout) findViewById(R.id.rlArticleDetailLike);
		ivArticleDetailLike = (ImageView) findViewById(R.id.ivArticleDetailLike);
		tvArticleDetailLike = (TextView) findViewById(R.id.tvArticleDetailLike);
		rlArticleDetailComment = (RelativeLayout) findViewById(R.id.rlArticleDetailComment);
		rlArticleDetailTransmit = (RelativeLayout) findViewById(R.id.rlArticleDetailTransmit);
		rlArticleDetailCommentInput = (RelativeLayout) findViewById(R.id.rlArticleDetailCommentInput);
		tvArticleDetailCommentSubmit = (TextView) findViewById(R.id.tvArticleDetailCommentSubmit);
		ivArticleDetailAddPhoto = (ImageView) findViewById(R.id.ivArticleDetailAddPhoto);
		etArticleDetailCommentContent = (EditText) findViewById(R.id.etArticleDetailCommentContent);
		llArticleDetailAddPhoto = (LinearLayout) findViewById(R.id.llArticleDetailAddPhoto);
		gvArticleDetailUploadPhotos = (HyperGridView) findViewById(R.id.gvArticleDetailUploadPhotos);
		setListener();
	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		activity = this;
		// 博文id
		articleId = getIntent().getStringExtra("ARTICLE_ID");
		commentList = new ArrayList<MeitanBeanArticleComment>();
		articleCommentAdapter = new ArticleCommentAdapter(activity, mHandler);
		lvArticleDetailComment.setAdapter(articleCommentAdapter);
		// 待上传的图片
		listUploadPhotos = new ArrayList<String>();
		uploadPhotosAdapter = new UploadPhotosAdapter(activity,
				listUploadPhotos);
		gvArticleDetailUploadPhotos.setAdapter(uploadPhotosAdapter);
		gvArticleDetailUploadPhotos.setSelector(R.color.transparent);
		// 获取分享内容
		ShareExec.getInstance().getShareContent(mHandler, "sqfx_posts",
				NetworkAsyncCommonDefines.GET_SHARE_S,
				NetworkAsyncCommonDefines.GET_SHARE_F);
		performTask();
	}

	private String title, url, param, pic, id, mContent;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			// 取消关注成功
			case NetworkAsyncCommonDefines.QUXIAO_GUANZHU_USER_S:
				isFocused = false;
				// 界面变化
				tvArticleDetailFocus.setBackground(getResources().getDrawable(
						R.drawable.shape_white_grey_stroke_small_radius));
				tvArticleDetailFocus.setText("+ 关注");
				tvArticleDetailFocus.setTextColor(getResources().getColor(
						R.color.main_text_grey));
				cancelFocusCooldown = false;

				break;
			// 取消关注失败
			case NetworkAsyncCommonDefines.QUXIAO_GUANZHU_USER_F:
				cancelFocusCooldown = false;

				break;
			// 关注成功
			case NetworkAsyncCommonDefines.GUANZHU_USER_S:
				isFocused = true;
				// 界面变化
				tvArticleDetailFocus.setBackground(getResources().getDrawable(
						R.drawable.shape_white_orange_stroke_small_radius));
				tvArticleDetailFocus.setText("已关注");
				tvArticleDetailFocus.setTextColor(getResources().getColor(
						R.color.main_text_orange));
				focusCooldown = false;
				break;
			// 关注失败
			case NetworkAsyncCommonDefines.GUANZHU_USER_F:
				focusCooldown = false;
				break;
			// 取消点赞成功
			case NetworkAsyncCommonDefines.QUXIAO_DIANZAN_S:

				ivArticleDetailLike.setImageDrawable(getResources()
						.getDrawable(R.drawable.ic_grey_like_large));
				tvArticleDetailLike.setTextColor(getResources().getColor(
						R.color.main_text_grey));
				isLiked = false;
				cancleLikeCooldown = false;
				break;
			// 取消点赞失败
			case NetworkAsyncCommonDefines.QUXIAO_DIANZAN_F:
				cancleLikeCooldown = false;
				break;
			// 点赞成功
			case NetworkAsyncCommonDefines.DIANZAN_S:
				ivArticleDetailLike.setImageDrawable(getResources()
						.getDrawable(R.drawable.ic_orange_like_large_large));
				tvArticleDetailLike.setTextColor(getResources().getColor(
						R.color.main_text_orange));
				isLiked = true;
				likeCooldown = false;
				break;
			// 点赞失败
			case NetworkAsyncCommonDefines.DIANZAN_F:
				likeCooldown = false;
				break;
			// 举报成功
			case NetworkAsyncCommonDefines.JUBAOBOWEN_S:
				Toast.makeText(mContext, "举报成功", Toast.LENGTH_SHORT).show();
				break;
			// 举报失败
			case NetworkAsyncCommonDefines.JUBAOBOWEN_F:
				Toast.makeText(mContext, "操作失败", Toast.LENGTH_SHORT).show();
				break;
			case NetworkAsyncCommonDefines.GET_BOWEN_INFO_S:
				Bundle listData = msg.getData();
				if (listData != null) {
					article = listData.getParcelable("mt");
					commentList = listData.getParcelableArrayList("list");
					if (article != null && commentList != null) {
						// 用户昵称
						tvArticleDetailUserName.setText(article.getUserName());
						// 文章照片
						gvArticleDetailPhotos
								.setAdapter(new ArticlePhotosAdapter(activity,
										article.getCmtPostsImgs()));
						// 用户头像
						x.image().bind(ivArticleDetailPortrait,
								article.getUserAvatar(), mImageOptions,
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
									public void onError(Throwable arg0,
											boolean arg1) {
										// TODO Auto-generated method stub
										ivArticleDetailPortrait
												.setImageResource(R.drawable.un_login_in);
									}

									@Override
									public void onCancelled(
											CancelledException arg0) {
										// TODO Auto-generated method stub

									}
								});
						// 评论数
						tvArticleDetailCommentsNum.setText(article.getCmdNum()
								+ "");
						// 用户等级
						tvArticleDetailUserLevel
								.setText(article.getUserLevel());
						// 发表时间
						tvArticleDetailDate.setText(article.getAddTimeShow());
						if (!"null".equals(article.getTitle())) {

							// 文章标题
							tvArticleDetailTitle.setText(article.getTitle());
						} else {
							tvArticleDetailTitle.setVisibility(View.GONE);
						}
						// 文章内容
						tvArticleDetailContent.setText(article.getContent());
						// 本人是否赞过
						if (article.getIsPraise() == 1) {
							ivArticleDetailLike
									.setImageDrawable(getResources()
											.getDrawable(
													R.drawable.ic_orange_like_large_large));
							tvArticleDetailLike.setTextColor(getResources()
									.getColor(R.color.main_text_orange));
							isLiked = true;
						} else {
							ivArticleDetailLike
									.setImageDrawable(getResources()
											.getDrawable(
													R.drawable.ic_grey_like_large));
							tvArticleDetailLike.setTextColor(getResources()
									.getColor(R.color.main_text_grey));
							isLiked = false;
						}
						// 该博主是否关注
						if (article.getIsFollow() == -2) {
							tvArticleDetailFocus.setVisibility(View.GONE);
						} else if (article.getIsFollow() == 1) {
							tvArticleDetailFocus
									.setBackground(getResources()
											.getDrawable(
													R.drawable.shape_white_orange_stroke_small_radius));
							tvArticleDetailFocus.setText("已关注");
							tvArticleDetailFocus.setTextColor(getResources()
									.getColor(R.color.main_text_orange));
							isFocused = true;
						} else {
							tvArticleDetailFocus
									.setBackground(getResources()
											.getDrawable(
													R.drawable.shape_white_grey_stroke_small_radius));
							tvArticleDetailFocus.setText("+ 关注");
							tvArticleDetailFocus.setTextColor(getResources()
									.getColor(R.color.main_text_grey));
							isFocused = false;
						}
						// 评论列表
						articleCommentAdapter.setData(commentList);
						articleCommentAdapter.notifyDataSetChanged();
					}
				}
				break;
			// 发送评论成功
			case NetworkAsyncCommonDefines.FA_PINGLUN_S:
				// // 提交成功返回
				etArticleDetailCommentContent.setText(null);
				rlArticleDetailCommentInput.setVisibility(View.GONE);
				llArticleDetailAddPhoto.setVisibility(View.GONE);
				listUploadPhotos = new ArrayList<String>();
				uploadPhotosAdapter.setData(listUploadPhotos);
				isEditComment = false;
				hideSoftInpuArticleDetail();
				// 刷新数据
				queryDataArticleDetail();
				// 加载条消失，冷却结束
				progressDialog.dismiss();
				submitCooldown = false;
				break;
			// 发送评论失败
			case NetworkAsyncCommonDefines.FA_PINGLUN_F:
				Toast.makeText(mContext, "操作失败", Toast.LENGTH_SHORT).show();
				// 刷新数据
				queryDataArticleDetail();
				// 加载条消失，冷却结束
				progressDialog.dismiss();
				submitCooldown = false;
				break;

			// 获取分享内容成功
			case NetworkAsyncCommonDefines.GET_SHARE_S:
				Bundle data = msg.getData();
				if (data != null) {
					title = data.getString("title");
					url = data.getString("url");
					param = data.getString("param");
					pic = data.getString("pic");

				}
				break;
			// 获取分享内容失败
			case NetworkAsyncCommonDefines.GET_SHARE_F:

				break;
			default:
				break;
			}
		}

	};

	@Event(value = { R.id.back_rl, R.id.operate_tv })
	private void onClick(View v) {
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		// 点击发博文
		case R.id.operate_tv:
			operate_tv.setFocusable(false);
			startActivity(new Intent(activity, FaBoWenActivity.class));
			finish();
			break;
		default:
			break;
		}
	}

	protected void setListener() {
		OnClickListener l = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				// 点击转发
				case R.id.rlArticleDetailTransmit:
					// shareWXCircle();
					shareAll();
					break;
				// 点击头像
				case R.id.ivArticleDetailPortrait:
					Intent intent = new Intent(activity,
							UserHomepageActivity.class);
					intent.putExtra("IS_FOLLOWED", article.getIsFollow());
					intent.putExtra("USER_ID", article.getUserId() + "");
					startActivity(intent);
					activity.finish();
					break;
				// 点击关注
				case R.id.tvArticleDetailFocus:
					if (article != null) {

						// 关注取消关注
						if (!isFocused && !focusCooldown) {
							toFocusArticleDetail();
						}
						if (isFocused && !cancelFocusCooldown) {
							toCancelFocusArticleDetail();
						}
					}
					break;
				// 点击评论
				case R.id.rlArticleDetailComment:
					showInputCommentArticleDetail();
					break;
				case R.id.rlArticleDetailLike:
					// 点赞
					if (!isLiked && !likeCooldown) {
						likeArticleDetail();
					}
					// 取消点赞
					if (isLiked && !cancleLikeCooldown) {
						cancleLikeArticle();
					}
					break;
				case R.id.ivArticleDetailAddPhoto:
					hideSoftInpuArticleDetail();
					if (llArticleDetailAddPhoto.getVisibility() == View.GONE) {
						llArticleDetailAddPhoto.setVisibility(View.VISIBLE);
					} else {
						llArticleDetailAddPhoto.setVisibility(View.GONE);
					}
					break;
				case R.id.etArticleDetailCommentContent:
					if (llArticleDetailAddPhoto.getVisibility() == View.VISIBLE) {
						llArticleDetailAddPhoto.setVisibility(View.GONE);
					}
					break;
				// 点击发送/点击取消
				case R.id.tvArticleDetailCommentSubmit:
					// 如果有内容则提交，没内容则取消
					if (hasCommentContent) {
						if (checkInputArticleDetail() && !submitCooldown) {
							submitArticleDetail();
						}
					} else {
						if (listUploadPhotos.size() > 0
								|| etArticleDetailCommentContent
										.getEditableText().toString().length() > 0) {
							// 如果有内容，提示是否放弃
							AlertDialog.Builder dialog = new AlertDialog.Builder(
									activity);
							dialog.setMessage("确定放弃编辑评论？");
							dialog.setTitle("提示");
							dialog.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											etArticleDetailCommentContent
													.setText(null);
											rlArticleDetailCommentInput
													.setVisibility(View.GONE);
											llArticleDetailAddPhoto
													.setVisibility(View.GONE);
											listUploadPhotos = new ArrayList<String>();
											uploadPhotosAdapter
													.setData(listUploadPhotos);
											isEditComment = false;
											hideSoftInpuArticleDetail();
										}
									});
							dialog.setNeutralButton("取消",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
										}
									});
							dialog.show();
						} else {
							etArticleDetailCommentContent.setText(null);
							rlArticleDetailCommentInput
									.setVisibility(View.GONE);
							llArticleDetailAddPhoto.setVisibility(View.GONE);
							listUploadPhotos = new ArrayList<String>();
							uploadPhotosAdapter.setData(listUploadPhotos);
							isEditComment = false;
							hideSoftInpuArticleDetail();
						}
					}
					break;
				}
			}
		};
		rlArticleDetailComment.setOnClickListener(l);
		rlArticleDetailLike.setOnClickListener(l);
		rlArticleDetailTransmit.setOnClickListener(l);
		ivArticleDetailPortrait.setOnClickListener(l);
		tvArticleDetailFocus.setOnClickListener(l);
		ivArticleDetailAddPhoto.setOnClickListener(l);
		etArticleDetailCommentContent.setOnClickListener(l);
		tvArticleDetailCommentSubmit.setOnClickListener(l);
		rlArticleDetailCommentInput.setOnClickListener(l);
		// 待传图片
		gvArticleDetailUploadPhotos
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// 点击最后一个则相册获取图片
						if (position == 1) {
							if (listUploadPhotos.size() >= 9) {
								// 不能超过9张图片
							} else {
								// 相册挑选照片
								Crop.pickImage(activity);
							}
							// 倒数第二个拍摄照片
						} else if (position == 0) {
							if (listUploadPhotos.size() >= 9) {
								// 不能超过9张图片
							} else {
								// 去拍摄照片
								gotoCameraArticleDetail();
							}
						} else {
							Intent intent = new Intent(activity,
									PhotoShowActivity.class);
							intent.putExtra("SHOW_WHICH", position);
							intent.putExtra("URL_TYPE",
									PhotoShowAdapter.PHOTO_URL_TYPE_LOCAL);
							intent.putExtra("PHOTO_URI_DATA",
									listUploadPhotos.toArray());
							activity.startActivity(intent);
						}
					}
				});
		// 输入框监听
		etArticleDetailCommentContent.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString() != null && s.toString().length() > 0) {
					hasCommentContent = true;
					tvArticleDetailCommentSubmit.setText("发送");
				} else {
					hasCommentContent = false;
					tvArticleDetailCommentSubmit.setText("取消");
				}
			}
		});
	}

	protected void performTask() {
		// 获取intent判断是否要评论
		if (getIntent().getBooleanExtra("TO_COMMENT", false)) {
			showInputCommentArticleDetail();
		}
		// 初始刷新
		queryDataArticleDetail();
	}

	/**
	 * TODO 评论后返回刷新界面
	 * 
	 * @author LiShen
	 * @date 2015-10-22 上午12:21:56
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 * @see Activity#onActivityResult(int, int, Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		// 挑选照片的回传数据
		case Crop.REQUEST_PICK:
			beginCropArticleDetail(resultCode, data, CROP_TYPE_IMAGE);
			break;
		// 拍摄界面的回传数据
		case PORTRAIT_TEMP_SHOT:
			beginCropArticleDetail(resultCode, data, CROP_TYPE_SHOT);
			break;
		// 裁剪照片的回传数据
		case Crop.REQUEST_CROP:
			handleCropArticleDetail(resultCode, data);
			break;
		}
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-24 下午1:25:48
	 * @see cn.dressbook.ui.base.MeitanFragmentActivity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		operate_tv.setFocusable(true);
	}

	/**
	 * TODO 关闭界面则停止发送请求
	 * 
	 * @author LiShen
	 * @date 2015-10-16 下午2:46:34
	 * @see MeitanActivity#onStop()
	 */
	@Override
	protected void onStop() {
		super.onStop();
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-25 上午12:16:00
	 * @param keyCode
	 * @param event
	 * @return
	 * @see Activity#onKeyDown(int, KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isEditComment) {
				rlArticleDetailCommentInput.setVisibility(View.GONE);
				llArticleDetailAddPhoto.setVisibility(View.GONE);
				isEditComment = false;
			} else if (listUploadPhotos.size() > 0
					|| etArticleDetailCommentContent.getEditableText()
							.toString().length() > 0) {
				// 如果有内容，提示是否放弃
				AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
				dialog.setMessage("确定放弃编辑评论？");
				dialog.setTitle("提示");
				dialog.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								finish();
							}
						});
				dialog.setNeutralButton("取消",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});
				dialog.show();
			} else {
				finish();
			}
		}
		return false;
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-21 下午6:31:45
	 * @see
	 */
	private void queryDataArticleDetail() {
		// 获取博文详情
		MeiTanExec.getInstance().getBoWenInfo(mHandler,
				ManagerUtils.getInstance().getUser_id(mContext), articleId,
				NetworkAsyncCommonDefines.GET_BOWEN_INFO_S,
				NetworkAsyncCommonDefines.GET_BOWEN_INFO_F);
	}

	/**
	 * TODO 发起请求关注某人
	 * 
	 * @author LiShen
	 * @date 2015-10-21 上午12:25:51
	 * @see
	 */
	private void toFocusArticleDetail() {
		focusCooldown = true;
		// 关注
		MeiTanExec.getInstance().guanZhu(mHandler,
				ManagerUtils.getInstance().getUser_id(mContext),
				article.getUserId() + "",
				NetworkAsyncCommonDefines.GUANZHU_USER_S,
				NetworkAsyncCommonDefines.GUANZHU_USER_F);

	}

	/**
	 * 
	 * TODO 发起请求取消关注某人
	 * 
	 * @author LiShen
	 * @date 2015-10-24 下午3:28:46
	 * @see
	 */
	private void toCancelFocusArticleDetail() {
		cancelFocusCooldown = true;
		// 取消关注
		MeiTanExec.getInstance().quXiaoGuanZhu(mHandler,
				ManagerUtils.getInstance().getUser_id(mContext),
				article.getUserId() + "",
				NetworkAsyncCommonDefines.QUXIAO_GUANZHU_USER_S,
				NetworkAsyncCommonDefines.QUXIAO_GUANZHU_USER_F);

	}

	/**
	 * TODO 点赞
	 * 
	 * @author LiShen
	 * @date 2015-10-21 下午4:04:23
	 * @see
	 */
	private void likeArticleDetail() {
		likeCooldown = true;
		// 点赞
		MeiTanExec.getInstance().dianZan(mHandler,
				ManagerUtils.getInstance().getUser_id(mContext),
				article.getId() + "", NetworkAsyncCommonDefines.DIANZAN_S,
				NetworkAsyncCommonDefines.DIANZAN_F);
	}

	/**
	 * TODO 取消点赞
	 * 
	 * @author LiShen
	 * @date 2015-10-24 下午3:44:13
	 * @param position
	 * @see
	 */
	private void cancleLikeArticle() {
		cancleLikeCooldown = true;
		// 取消点赞
		MeiTanExec.getInstance().quXiaoDianZan(mHandler,
				ManagerUtils.getInstance().getUser_id(mContext),
				article.getId() + "",
				NetworkAsyncCommonDefines.QUXIAO_DIANZAN_S,
				NetworkAsyncCommonDefines.QUXIAO_DIANZAN_F);
	}

	/**
	 * 
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-18 上午11:29:06
	 * @param resultCode
	 * @param result
	 * @see
	 */
	private void handleCropArticleDetail(int resultCode, Intent result) {
		try {

			if (resultCode == RESULT_OK) {
				Bitmap bmPortraitTemp = BitmapFactory.decodeFile(Crop
						.getOutput(result).getPath());
				// 重新调整大小至800*800
				Bitmap bmPortraitTemp2 = ImageUtils2.resizeBitmap(
						bmPortraitTemp, 800);
				String saveUri = FileUtils.getPhotoUploadFolder()
						+ "PHOTO_UPLOAD_"
						+ DateTimeUtils.getCurrentDateTimeSecond() + ".jpg";
				// 保存照片
				ImageUtils2.saveBitmap(saveUri, bmPortraitTemp2,
						ImageUtils2.JPEG, 75);
				// 添加至待上传列表
				listUploadPhotos.add(saveUri);
				// 待上传的图片显示adapter更新
				uploadPhotosAdapter.setData(listUploadPhotos);
			} else if (resultCode == Crop.RESULT_ERROR) {
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 
	 * TODO 去拍摄图片
	 * 
	 * @author LiShen
	 * @date 2015-10-18 上午11:15:34
	 * @see
	 */
	private void gotoCameraArticleDetail() {
		// 进入拍照界面
		photoShotTepUri = FileUtils.getPhotoShotFolder() + "PHOTO_SHOT_TEMP_"
				+ DateTimeUtils.getCurrentDateTimeSecond();
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(photoShotTepUri)));
		// 启动
		startActivityForResult(intent, PORTRAIT_TEMP_SHOT);
	}

	private void beginCropArticleDetail(int resultCode, Intent data, int type) {
		if (resultCode == RESULT_OK) {
			// 头像裁剪完暂存的地方
			photoCropTempUri = FileUtils.getImageCachedFolder()
					+ "PHOTO_CROP_TEMP_"
					+ DateTimeUtils.getCurrentDateTimeSecond();
			Uri uriPortraitTemp = Uri.fromFile(new File(photoCropTempUri));
			switch (type) {
			// 本地相册裁剪
			case CROP_TYPE_IMAGE:
				// 保存的路径
				String saveUri = FileUtils.getPhotoUploadFolder()
						+ "PHOTO_UPLOAD_"
						+ DateTimeUtils.getCurrentDateTimeSecond() + ".jpg";
				// 缩放图片到800*等比例高,并保存图片
				scaleImage(data.getData(), saveUri);
				// Crop.of(data.getData(),
				// uriPortraitTemp).asSquare().start(this);
				break;
			// 拍摄的照片裁剪
			case CROP_TYPE_SHOT:
				Uri uriPortraitShot = Uri.fromFile(new File(photoShotTepUri));
				// 保存的路径
				String saveUri2 = FileUtils.getPhotoUploadFolder()
						+ "PHOTO_UPLOAD_"
						+ DateTimeUtils.getCurrentDateTimeSecond() + ".jpg";
				// 缩放图片到800*等比例高,并保存图片
				scaleImage(uriPortraitShot, saveUri2);
				break;
			}
		}
	}

	/**
	 * @description:缩放图片
	 */
	private void scaleImage(Uri uri, String path2) {
		// TODO Auto-generated method stub

		if (uri != null) {
			String path = UriUtils.getInstance()
					.getImageAbsolutePath(this, uri);
			if (path == null || path.equals("")) {
				Toast.makeText(mContext, "没找到图片", Toast.LENGTH_SHORT).show();
				return;
			}
			File s = new File(path);
			if (s != null && s.exists()) {
				path2 = ImageUtils.dealImage(path, path2);
				// 添加至待上传列表
				listUploadPhotos.add(path2);
				// 待上传的图片显示adapter更新
				uploadPhotosAdapter.setData(listUploadPhotos);
			}

			// Intent manipulatingImage = new Intent(mContext,
			// PhotoCropActivity.class);
			// startActivity(manipulatingImage);
		} else {
			Toast.makeText(mContext, "没找到图片", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 
	 * TODO 检查输入
	 * 
	 * @author LiShen
	 * @date 2015-10-16 下午2:37:21
	 * @return
	 * @see
	 */
	private boolean checkInputArticleDetail() {
		// 获取内容
		commentContent = etArticleDetailCommentContent.getEditableText()
				.toString();
		if (commentContent == null || commentContent.length() < 1) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * TODO 发起请求提交博文
	 * 
	 * @author LiShen
	 * @date 2015-10-16 下午2:45:45
	 * @see
	 */
	private void submitArticleDetail() {
		// 冷却开始
		submitCooldown = true;
		progressDialog = ProgressDialog.show(this, "提交评论", "正在努力提交评论中...");
		// 发布评论
		MeiTanExec.getInstance().faPingLun(mHandler,
				ManagerUtils.getInstance().getUser_id(mContext), articleId,
				commentContent, listUploadPhotos,
				NetworkAsyncCommonDefines.FA_PINGLUN_S,
				NetworkAsyncCommonDefines.FA_PINGLUN_F);

	}

	/**
	 * 
	 * TODO 隐藏软键盘
	 * 
	 * @author LiShen
	 * @date 2015-10-24 下午10:21:14
	 * @see
	 */
	private void hideSoftInpuArticleDetail() {
		// InputMethodManager manager = ((InputMethodManager) this
		// .getSystemService(Activity.INPUT_METHOD_SERVICE));
		// if (getWindow().getAttributes().softInputMode !=
		// WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
		// if (getCurrentFocus() != null)
		// manager.hideSoftInputFromWindow(getCurrentFocus()
		// .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		// }
		View view = getWindow().peekDecorView();
		InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	/**
	 * 
	 * TODO 弹出评论框
	 * 
	 * @author LiShen
	 * @date 2015-10-24 下午10:26:36
	 * @see
	 */
	public void showInputCommentArticleDetail() {
		rlArticleDetailCommentInput.setVisibility(View.VISIBLE);
		etArticleDetailCommentContent.requestFocus();
		isEditComment = true;
	}

	/**
	 * 
	 * TODO
	 * 
	 * @author LiShen
	 * @company Gifted Youngs Workshop
	 * @date 2015-10-25 上午12:24:07
	 * @since
	 * @version
	 */
	class UploadPhotosAdapter extends BaseAdapter {
		private ImageOptions mImageOptions;
		/**
		 * TODO
		 */
		private Activity activity;
		/**
		 * TODO
		 */
		private List<String> data;
		/**
		 * TODO
		 */
		public static final String ADD_PHOTO = "add_photo_picture";
		/**
		 * TODO
		 */
		public static final String SHOT_PHOTO = "shot_photo_picture";

		/**
		 * TODO
		 * 
		 * @author LiShen
		 * @date 2015-10-16 下午12:22:23
		 */
		public UploadPhotosAdapter(Activity activity,
				List<String> uploadPhotoList) {
			this.activity = activity;

			data = new ArrayList<String>();
			data.add(SHOT_PHOTO);
			data.add(ADD_PHOTO);
			data.addAll(uploadPhotoList);

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
		public String getItem(int position) {
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
			return position;
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
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder = new ViewHolder();
			if (convertView == null) {
				convertView = activity.getLayoutInflater().inflate(
						R.layout.item_gridview_upload_photos, null);
				holder.ivItemUploadPhotos = (ImageView) convertView
						.findViewById(R.id.ivItemUploadPhotos);
				holder.ivItemUploadPhotosDelete = (ImageView) convertView
						.findViewById(R.id.ivItemUploadPhotosDelete);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			// 增加照片和拍摄照片
			if (data.get(position).equals(ADD_PHOTO)) {
				holder.ivItemUploadPhotos.setImageDrawable(activity
						.getResources().getDrawable(
								R.drawable.pic_grey_add_photo));
			} else if (data.get(position).equals(SHOT_PHOTO)) {
				holder.ivItemUploadPhotos.setImageDrawable(activity
						.getResources().getDrawable(
								R.drawable.pic_grey_shot_photo));
			} else {
				// 绑定图片
				x.image().bind(holder.ivItemUploadPhotos, data.get(position),
						mImageOptions, new CustomBitmapLoadCallBack(holder));
			}
			// 删除键
			if (position == 0 || position == 1) {
				holder.ivItemUploadPhotosDelete.setVisibility(View.GONE);
			} else {
				holder.ivItemUploadPhotosDelete.setVisibility(View.VISIBLE);
			}
			// 删除指定照片
			holder.ivItemUploadPhotosDelete
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							listUploadPhotos.remove(listUploadPhotos
									.get(position - 2));
							uploadPhotosAdapter.setData(listUploadPhotos);
						}
					});
			return convertView;
		}

		class ViewHolder {
			ImageView ivItemUploadPhotos;
			ImageView ivItemUploadPhotosDelete;
		}

		public class CustomBitmapLoadCallBack implements
				Callback.ProgressCallback<Drawable> {
			private final ViewHolder holder;

			public CustomBitmapLoadCallBack(ViewHolder holder) {
				this.holder = holder;
			}

			@Override
			public void onWaiting() {

				// this.holder.imgPb.setProgress(0);
			}

			@Override
			public void onStarted() {

			}

			@Override
			public void onLoading(long total, long current,
					boolean isDownloading) {
				// this.holder.imgPb.setProgress((int) (current * 100 / total));
			}

			@Override
			public void onSuccess(Drawable result) {

				// this.holder.imgPb.setProgress(100);
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
		}

		/**
		 * TODO 返回 data 的值
		 */
		public List<String> getData() {
			return data;
		}

		/**
		 * TODO 设置 data 的值
		 */
		public void setData(List<String> uploadPhotoList) {
			data.clear();
			data = new ArrayList<String>();
			data.add(SHOT_PHOTO);
			data.add(ADD_PHOTO);
			data.addAll(uploadPhotoList);
			notifyDataSetChanged();
		}
	}

	private UMSocialService mController;

	/**
	 * 分享到所有平台
	 */
	protected void shareAll() {
		try {

			Intent intent = new Intent(mContext, MyShareActivity.class);
			intent.putExtra("pic", pic);
			intent.putExtra("targeturl", url + "?" + param + "=" + articleId);
			intent.putExtra("content", article.getContent());
			intent.putExtra("title", article.getTitle());
			startActivity(intent);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	/**
	 * @description:分享到微信朋友圈
	 */
	/*
	 * protected void shareWXCircle() { // TODO Auto-generated method stub if
	 * (mController == null) { mController = UMServiceFactory
	 * .getUMSocialService("com.umeng.share"); } // 支持微信朋友圈 UMWXHandler
	 * wxCircleHandler = new UMWXHandler(mContext, Constants.APP_ID,
	 * Constants.AppSecret); if (wxCircleHandler.isClientInstalled()) {
	 * wxCircleHandler.setToCircle(true); wxCircleHandler.addToSocialSDK();
	 * CircleShareContent circleMedia = new CircleShareContent(); UMImage image
	 * = new UMImage(mContext, new File( PathCommonDefines.SHARE, "bw.jpg"));
	 * circleMedia.setShareImage(image); circleMedia.setTargetUrl(url + "?" +
	 * param + "=" + articleId);
	 * circleMedia.setShareContent(article.getContent());
	 * circleMedia.setTitle(article.getTitle());
	 * mController.setShareMedia(circleMedia); // 参数1为Context类型对象，
	 * 参数2为要分享到的目标平台， 参数3为分享操作的回调接口 mController.postShare(mContext,
	 * SHARE_MEDIA.WEIXIN_CIRCLE, new SnsPostListener() {
	 * 
	 * @Override public void onStart() { // Toast.makeText(mContext, "开始分享.", //
	 * Toast.LENGTH_SHORT) // .show(); }
	 * 
	 * @Override public void onComplete(SHARE_MEDIA platform, int eCode,
	 * SocializeEntity entity) { if (eCode == 200) { } else { String eMsg = "";
	 * if (eCode == -101) { eMsg = "没有授权"; } } } }); } else {
	 * Toast.makeText(mContext, "请安装微信", Toast.LENGTH_LONG).show(); } }
	 */

}
