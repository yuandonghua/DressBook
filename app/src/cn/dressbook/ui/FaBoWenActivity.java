package cn.dressbook.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.adapter.PhotoShowAdapter;
import cn.dressbook.ui.adapter.TopicAdapter;
import cn.dressbook.ui.base.MeitanActivity;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.listener.RefreshBoWen;
import cn.dressbook.ui.net.MeiTanExec;
import cn.dressbook.ui.utils.BitmapTool;
import cn.dressbook.ui.utils.DateTimeUtils;
import cn.dressbook.ui.utils.FileUtils;
import cn.dressbook.ui.utils.ImageUtils;
import cn.dressbook.ui.utils.ImageUtils2;
import cn.dressbook.ui.utils.SU;
import cn.dressbook.ui.utils.UriUtils;
import cn.dressbook.ui.view.HyperGridView;
import cn.dressbook.ui.view.crop.Crop;

/**
 * TODO 发博文界面
 * 
 * @author LiShen
 * @company Gifted Youngs Workshop
 * @date 2015-10-16 下午2:28:24
 * @since
 * @version
 */
@SuppressLint("InflateParams")
@SuppressWarnings("deprecation")
@ContentView(R.layout.activity_write_article)
public class FaBoWenActivity extends MeitanActivity {
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions();
	private Activity mContext = FaBoWenActivity.this;
	private EditText etWriteArticleTitle;
	private HyperGridView gvWriteArticleTopic;
	private EditText etWriteArticleContent;
	private HyperGridView gvWriteArticleUploadPhotos;
	private Button btnWriteArticleSubmit;
	private LinearLayout llWriteArticleImagePickerWays;
	private TextView tvWriteArticleImagePickerCamera;
	private TextView tvWriteArticleImagePickerPhoto;
	private TextView tvWriteArticleImagePickerCancle;
	private View vWriteArticleGradient;

	private Animation animSlideInBottom;
	private Animation animSlideOutBottom;

	private ProgressDialog progressDialog;

	/**
	 * TODO 提交冷却，防止重复提交请求
	 */
	private boolean submitCooldown = false;
	/**
	 * TODO
	 */
	private boolean isImagePickerWaysShow = false;

	private TopicAdapter topicAdapter;
	private UploadPhotosAdapter uploadPhotosAdapter;

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

	/**
	 * TODO 话题
	 */
	private String[] topics;
	/**
	 * TODO 选择了哪个话题
	 */
	private int topicChoice = -1;

	private String articleTitle = "";
	private String articleContent = "";
	private String articleTopic = "";
	/**
	 * TODO 待上传的图片
	 */
	private List<String> listUploadPhotos;

	public static final int FOR_REFRESH = 1121;
	/**
	 * 返回按钮
	 */
	@ViewInject(R.id.back_rl)
	private RelativeLayout back_rl;
	/**
	 * 标题
	 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;

	@Event({ R.id.back_rl })
	private void onClick(View v) {
		switch (v.getId()) {
		// 点击返回
		case R.id.back_rl:
			// 对话框
			AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
			dialog.setMessage("确定退出编辑博文？");
			dialog.setTitle("提示");
			dialog.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							finish();
						}
					});
			dialog.setNeutralButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			dialog.show();
			break;
		}
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-16 下午2:28:24
	 * @see MeitanActivity#findViewLayout()
	 */
	@Override
	protected void findViewLayout() {
		title_tv.setText("发博文");
		etWriteArticleTitle = (EditText) findViewById(R.id.etWriteArticleTitle);
		gvWriteArticleTopic = (HyperGridView) findViewById(R.id.gvWriteArticleTopic);
		etWriteArticleContent = (EditText) findViewById(R.id.etWriteArticleContent);
		gvWriteArticleUploadPhotos = (HyperGridView) findViewById(R.id.gvWriteArticleUploadPhotos);
		btnWriteArticleSubmit = (Button) findViewById(R.id.btnWriteArticleSubmit);
		llWriteArticleImagePickerWays = (LinearLayout) findViewById(R.id.llWriteArticleImagePickerWays);
		tvWriteArticleImagePickerCamera = (TextView) findViewById(R.id.tvWriteArticleImagePickerCamera);
		tvWriteArticleImagePickerPhoto = (TextView) findViewById(R.id.tvWriteArticleImagePickerPhoto);
		tvWriteArticleImagePickerCancle = (TextView) findViewById(R.id.tvWriteArticleImagePickerCancle);
		vWriteArticleGradient = findViewById(R.id.vWriteArticleGradient);
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-16 下午2:28:24
	 * @see MeitanActivity#initializeData()
	 */
	@Override
	protected void initializeData() {
		// 待上传的图片
		listUploadPhotos = new ArrayList<String>();
		uploadPhotosAdapter = new UploadPhotosAdapter(mContext,
				listUploadPhotos);
		gvWriteArticleUploadPhotos.setAdapter(uploadPhotosAdapter);
		// 动画
		animSlideInBottom = AnimationUtils.loadAnimation(this,
				R.anim.anim_slide_in_from_bottom2);
		animSlideOutBottom = AnimationUtils.loadAnimation(this,
				R.anim.anim_slide_out_to_bottom2);
		// gridview点击效果
		gvWriteArticleTopic.setSelector(R.color.transparent);
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-16 下午2:28:24
	 * @see MeitanActivity#setListener()
	 */
	@Override
	protected void setListener() {
		OnClickListener l = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				// 点击完成
				case R.id.btnWriteArticleSubmit:
					if (checkInputWriteArticle() && !submitCooldown) {
						submitWriteArticle();
					}
					break;
				case R.id.tvWriteArticleImagePickerCamera:
					showImageCropWayWriteArticle();
					// 去拍摄照片
					gotoCameraWriteArticle();
					break;
				// 点击相册
				case R.id.tvWriteArticleImagePickerPhoto:
					showImageCropWayWriteArticle();
					Crop.pickImage(mContext);
					break;
				case R.id.tvWriteArticleImagePickerCancle:
					// 关闭菜单
					showImageCropWayWriteArticle();
					break;
				}
			}
		};
		tvWriteArticleImagePickerCamera.setOnClickListener(l);
		tvWriteArticleImagePickerPhoto.setOnClickListener(l);
		tvWriteArticleImagePickerCancle.setOnClickListener(l);
		btnWriteArticleSubmit.setOnClickListener(l);
		// 话题点击效果
		gvWriteArticleTopic.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == topicChoice) {
					// 再次点击清除选择
					topicAdapter.setTopicChoice(-1);
					topicAdapter.setTopicChoice(-1);
					articleTopic = "";
				} else {
					// 点击选择话题
					topicChoice = position;
					topicAdapter.setTopicChoice(position);
					articleTopic = topics[position];
				}
			}

		});
		// 点击添加图片
		gvWriteArticleUploadPhotos
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// 点击最后一个则获取图片
						if (position == uploadPhotosAdapter.getCount() - 1) {
							if (listUploadPhotos.size() >= 9) {
								// 不能超过9张图片
							} else {
								showImageCropWayWriteArticle();
							}
						} else {
							Intent intent = new Intent(mContext,
									PhotoShowActivity.class);
							intent.putExtra("SHOW_WHICH", position);
							intent.putExtra("URL_TYPE",
									PhotoShowAdapter.PHOTO_URL_TYPE_LOCAL);
							intent.putExtra("PHOTO_URI_DATA",
									listUploadPhotos.toArray());
							mContext.startActivity(intent);
						}
					}
				});
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-16 下午2:28:24
	 * @see MeitanActivity#performTask()
	 */
	@Override
	protected void performTask() {
		queryTopicsWriteArticle();
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
	 * @date 2015-10-18 上午11:22:59
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
			beginCropWriteArticle(resultCode, data, CROP_TYPE_IMAGE);
			break;
		// 拍摄界面的回传数据
		case PORTRAIT_TEMP_SHOT:
			beginCropWriteArticle(resultCode, data, CROP_TYPE_SHOT);
			break;
		// 裁剪照片的回传数据
		case Crop.REQUEST_CROP:
			handleCropWriteArticle(resultCode, data);
			break;
		}
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
	private void handleCropWriteArticle(int resultCode, Intent result) {
		if (resultCode == RESULT_OK) {
			Bitmap bmPortraitTemp = BitmapFactory.decodeFile(Crop.getOutput(
					result).getPath());
			// 重新调整大小至800*800
			bmPortraitTemp = ImageUtils2.resizeBitmap(bmPortraitTemp, 800);
			String saveUri = FileUtils.getPhotoUploadFolder() + "PHOTO_UPLOAD_"
					+ DateTimeUtils.getCurrentDateTimeSecond() + ".jpg";
			// 保存照片
			ImageUtils2.saveBitmap(saveUri, bmPortraitTemp, ImageUtils2.JPEG,
					75);
			// 添加至待上传列表
			listUploadPhotos.add(saveUri);
			// 待上传的图片显示adapter更新
			uploadPhotosAdapter.setData(listUploadPhotos);
		} else if (resultCode == Crop.RESULT_ERROR) {
		}
	}

	/**
	 * TODO 发送请求获取话题标签
	 * 
	 * @author LiShen
	 * @date 2015-10-17 下午1:26:59
	 * @see
	 */
	private void queryTopicsWriteArticle() {
		// 获取话题列表
		MeiTanExec.getInstance().getHuaTiList(mHandler, "sqfx_subtag",
				NetworkAsyncCommonDefines.GET_HUATI_LIST_S,
				NetworkAsyncCommonDefines.GET_HUATI_LIST_F);

	}

	/**
	 * 
	 * TODO 弹出或者隐藏选择增加照片的菜单
	 * 
	 * @author LiShen
	 * @date 2015-10-18 上午10:58:58
	 * @see
	 */
	private void showImageCropWayWriteArticle() {
		if (!isImagePickerWaysShow) {
			// 弹出菜单
			llWriteArticleImagePickerWays.setAnimation(animSlideInBottom);
			llWriteArticleImagePickerWays.startAnimation(animSlideInBottom);
			llWriteArticleImagePickerWays.setVisibility(View.VISIBLE);
			isImagePickerWaysShow = true;
			vWriteArticleGradient.setVisibility(View.VISIBLE);
		} else {
			// 隐藏菜单
			vWriteArticleGradient.setVisibility(View.GONE);
			llWriteArticleImagePickerWays.setAnimation(animSlideOutBottom);
			llWriteArticleImagePickerWays.startAnimation(animSlideOutBottom);
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					llWriteArticleImagePickerWays.setVisibility(View.GONE);
					isImagePickerWaysShow = false;
				}
			}, 500);
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
	private void gotoCameraWriteArticle() {
		// 进入拍照界面
		photoShotTepUri = FileUtils.getPhotoShotFolder() + "PHOTO_SHOT_TEMP_"
				+ DateTimeUtils.getCurrentDateTimeSecond();
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(photoShotTepUri)));
		// 启动
		startActivityForResult(intent, PORTRAIT_TEMP_SHOT);
	}

	private void beginCropWriteArticle(int resultCode, Intent data, int type) {
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
	private boolean checkInputWriteArticle() {
		// 获取内容
		articleContent = etWriteArticleContent.getEditableText().toString();
		articleTitle = etWriteArticleTitle.getEditableText().toString();
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
	private void submitWriteArticle() {
		if (articleTitle == null || "".equals(articleTitle)) {
			Toast.makeText(mContext, "标题不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		if (articleContent == null || "".equals(articleContent)) {
			Toast.makeText(mContext, "内容不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		// 冷却开始
		submitCooldown = true;
		progressDialog = ProgressDialog.show(this, "提交博文", "正在努力提交博文中...");
		// 发博文
		MeiTanExec.getInstance().faBoWen(mHandler,
				ManagerUtils.getInstance().getUser_id(mContext), articleTopic,
				articleTitle, articleContent, listUploadPhotos,
				NetworkAsyncCommonDefines.FABOWEN_S,
				NetworkAsyncCommonDefines.FABOWEN_F);

	}

	/**
	 * 
	 * TODO 显示上传图片的gridview显示adapter
	 * 
	 * @author LiShen
	 * @company Gifted Youngs Workshop
	 * @date 2015-10-24 上午11:38:56
	 * @since
	 * @version
	 */
	class UploadPhotosAdapter extends BaseAdapter {
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
		public static final String ADD_PHOTO = "default_add_photo_picture";

		/**
		 * 适配器
		 * 
		 * @param activity
		 * @param uploadPhotoList
		 */
		public UploadPhotosAdapter(Activity activity,
				List<String> uploadPhotoList) {
			this.activity = activity;
			data = new ArrayList<String>();
			data.addAll(uploadPhotoList);
			data.add(ADD_PHOTO);

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
			if (data.get(position).equals(ADD_PHOTO)) {
				holder.ivItemUploadPhotos.setImageDrawable(activity
						.getResources().getDrawable(
								R.drawable.pic_grey_add_photo));
			} else {
				// 绑定图片
				x.image().bind(holder.ivItemUploadPhotos, data.get(position),
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
			}
			// 删除键
			if (position == data.size() - 1) {
				holder.ivItemUploadPhotosDelete.setVisibility(View.GONE);
			} else {
				holder.ivItemUploadPhotosDelete.setVisibility(View.VISIBLE);
			}
			// 点击删除指定照片
			holder.ivItemUploadPhotosDelete
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							listUploadPhotos.remove(listUploadPhotos
									.get(position));
							uploadPhotosAdapter.setData(listUploadPhotos);
						}
					});
			return convertView;
		}

		class ViewHolder {
			ImageView ivItemUploadPhotos;
			ImageView ivItemUploadPhotosDelete;
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
			data.addAll(uploadPhotoList);
			data.add(ADD_PHOTO);
			notifyDataSetChanged();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			// 对话框
			AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
			dialog.setMessage("确定退出编辑博文？");
			dialog.setTitle("提示");
			dialog.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							finish();
						}
					});
			dialog.setNeutralButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			dialog.show();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 发博文成功
			case NetworkAsyncCommonDefines.FABOWEN_S:
				// 加载条消失，冷却结束
				progressDialog.dismiss();
				submitCooldown = false;
				if (mRefreshBoWenList != null) {
					mRefreshBoWenList.onRefresh(true);
				}
				finish();
				break;
			// 发博文失败
			case NetworkAsyncCommonDefines.FABOWEN_F:
				Toast.makeText(mContext, "操作失败", Toast.LENGTH_SHORT);
				// 加载条消失，冷却结束
				progressDialog.dismiss();
				submitCooldown = false;
				break;
			// 获取话题列表成功
			case NetworkAsyncCommonDefines.GET_HUATI_LIST_S:
				Bundle huatiData = msg.getData();
				if (huatiData != null) {
					String huati = huatiData.getString("huati");
					topics = null;
					topics = huati.split("#");
					if (topics != null && topics.length > 0) {
						// 传入adapter
						topicAdapter = new TopicAdapter(mContext, topics);
						gvWriteArticleTopic.setAdapter(topicAdapter);
					}
				}
				break;
			// 获取话题列表失败
			case NetworkAsyncCommonDefines.GET_HUATI_LIST_F:

				break;

			default:
				break;
			}
		};
	};
	public static RefreshBoWen mRefreshBoWenList;

	public static void setOnRefreshBoWenList(RefreshBoWen refreshBoWenList) {
		mRefreshBoWenList = refreshBoWenList;
	}
}
