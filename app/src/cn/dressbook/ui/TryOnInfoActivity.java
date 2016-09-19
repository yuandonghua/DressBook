package cn.dressbook.ui;

import java.io.File;

import org.opencv.samples.facedetect.DetectionBasedTracker;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.net.ShareExec;
import cn.dressbook.ui.service.DownLoadingService;
import cn.dressbook.ui.view.VerticalSeekBar;
import cn.dressbook.ui.view.VerticalSeekBar.OnSeekBarChangeListener;

import com.umeng.analytics.social.UMSocialService;

/**
 * @description: 试衣详情
 * @author:袁东华
 * @time:2015-9-29上午9:11:44
 */
@ContentView(R.layout.tryoninfo)
public class TryOnInfoActivity extends BaseActivity {
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions(ImageView.ScaleType.CENTER_INSIDE, false);
	private UMSocialService mController;
	/** 返回按钮 */
	@ViewInject(R.id.back_rl)
	private RelativeLayout back_rl;
	/** 标题 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/**
	 * 更多
	 */
	@ViewInject(R.id.operate_tv)
	private TextView operate_tv;
	@ViewInject(R.id.imageview)
	private ImageView imageview;
	/**
	 * 分享
	 */
	@ViewInject(R.id.share_tv)
	private TextView share_tv;
	/**
	 * 删除
	 */
	@ViewInject(R.id.delete_tv)
	private TextView delete_tv;
	/**
	 * 高矮
	 */
	@ViewInject(R.id.left_sb)
	private VerticalSeekBar left_sb;
	/**
	 * 胖瘦
	 */
	@ViewInject(R.id.right_sb)
	private VerticalSeekBar right_sb;
	private String url = null;
	private int height, weight;
	private String inputFilePath, outputFilePath, curFilePath;
	private Context mContext = TryOnInfoActivity.this;
	private String title, content, pic, uploadPath, sharePath;
	private String fileName;
	private boolean isShow = true;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("试穿效果");
		operate_tv.setText("更多");
		operate_tv.setVisibility(View.VISIBLE);
		// 调节高矮
		left_sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(int progress) {
				// TODO Auto-generated method stub
				if (progress > 5) {
					progress = 5;
				}
				if (progress < 0) {
					progress = 0;
				}
				pbDialog.show();
				height = progress;
				// 没有变身
				if (height == 0 && weight == 0) {
					if (curFilePath != null) {

						File file = new File(curFilePath);
						if (file.exists()) {
							file.delete();
						}
						curFilePath = null;
					}
					mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.NO_BS);
				} else {
					if (curFilePath != null) {

						File file = new File(curFilePath);
						if (file.exists()) {
							file.delete();
						}
						curFilePath = null;
					}
					// 变身了
					// 原文件路径
					inputFilePath = "wardrobe/tryOn"
							+ url.substring(url.lastIndexOf("/"));
					// 目标文件路径
					outputFilePath = inputFilePath.replace(".a", "_" + height
							+ "_" + weight + ".jpeg");
					long result = DetectionBasedTracker.nativeHumanWarps(
							inputFilePath, outputFilePath, height, weight);
					if (result == 0) {
						curFilePath = url.replace(".a", "_" + height + "_"
								+ weight + ".a");
						File file = new File(url.replace(".a", "_" + height
								+ "_" + weight + ".jpeg"));
						if (file.exists()) {

							if (file.renameTo(new File(curFilePath))) {
								// 绑定图片
								x.image().bind(imageview, curFilePath,
										mImageOptions,
										new CommonCallback<Drawable>() {

											@Override
											public void onSuccess(Drawable arg0) {
												// TODO Auto-generated method
												// stub
											}

											@Override
											public void onFinished() {
												// TODO Auto-generated method
												// stub

											}

											@Override
											public void onError(Throwable arg0,
													boolean arg1) {
												// TODO Auto-generated method
												// stub
											}

											@Override
											public void onCancelled(
													CancelledException arg0) {
												// TODO Auto-generated method
												// stub
											}
										});
							}
						}
						pbDialog.dismiss();
					} else {
						Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show();
						pbDialog.dismiss();
					}

				}

			}
		});
		// 调节胖瘦
		right_sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(int progress) {
				// TODO Auto-generated method stub
				if (progress > 5) {
					progress = 5;
				}
				if (progress < 0) {
					progress = 0;
				}
				pbDialog.show();
				weight = progress;
				// 没有变身
				if (height == 0 && weight == 0) {
					if (curFilePath != null) {

						File file = new File(curFilePath);
						if (file.exists()) {
							file.delete();
						}
						curFilePath = null;
					}
					mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.NO_BS);
				} else {
					if (curFilePath != null) {

						File file = new File(curFilePath);
						if (file.exists()) {
							file.delete();
						}
						curFilePath = null;
					}
					// 变身了
					// 原文件路径
					inputFilePath = "wardrobe/tryOn"
							+ url.substring(url.lastIndexOf("/"));
					// 目标文件路径
					outputFilePath = inputFilePath.replace(".a", "_" + height
							+ "_" + weight + ".jpeg");
					long result = DetectionBasedTracker.nativeHumanWarps(
							inputFilePath, outputFilePath, height, weight);
					if (result == 0) {
						curFilePath = url.replace(".a", "_" + height + "_"
								+ weight + ".a");
						File file = new File(url.replace(".a", "_" + height
								+ "_" + weight + ".jpeg"));
						file.renameTo(new File(curFilePath));
						// 绑定图片
						x.image().bind(imageview, curFilePath, mImageOptions,
								new CommonCallback<Drawable>() {

									@Override
									public void onSuccess(Drawable arg0) {
										// TODO Auto-generated method
										// stub
									}

									@Override
									public void onFinished() {
										// TODO Auto-generated method
										// stub

									}

									@Override
									public void onError(Throwable arg0,
											boolean arg1) {
										// TODO Auto-generated method
										// stub
									}

									@Override
									public void onCancelled(
											CancelledException arg0) {
										// TODO Auto-generated method
										// stub
									}
								});
						pbDialog.dismiss();
					} else {
						Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show();
						pbDialog.dismiss();
					}

				}
			}

		});
	}

	@Override
	protected void initData() throws Exception {
		// TODO Auto-generated method stub
		Intent intent = getIntent();

		if (intent != null) {
			url = intent.getStringExtra("path");

		}
		String url2 = getCurFilePath(url);
		// 绑定图片
		x.image().bind(imageview, url2, mImageOptions,
				new CommonCallback<Drawable>() {

					@Override
					public void onSuccess(Drawable arg0) {
						// TODO Auto-generated method
						// stub
					}

					@Override
					public void onFinished() {
						// TODO Auto-generated method
						// stub

					}

					@Override
					public void onError(Throwable arg0, boolean arg1) {
						// TODO Auto-generated method
						// stub
					}

					@Override
					public void onCancelled(CancelledException arg0) {
						// TODO Auto-generated method
						// stub
					}
				});
		left_sb.setProgress(height);
		right_sb.setProgress(weight);

	}

	/**
	 * @description:获取当前文件的路径
	 */
	private String getCurFilePath(String url2) {
		// TODO Auto-generated method stub
		if (url2 == null) {
			return null;
		}
		for (int i = 0; i <= 5; i++) {
			for (int j = 0; j <= 5; j++) {
				String path = url2.replace(".a", "_" + i + "_" + j + ".a");

				File file = new File(path);
				if (file.exists()) {
					height = i;
					weight = j;
					if (i != 0 || j != 0) {
						curFilePath = file.getAbsolutePath();
					}
					return file.getAbsolutePath();
				}
			}
		}
		return url2;
	}

	@Event({ R.id.imageview, R.id.back_rl, R.id.operate_tv, R.id.share_tv,
			R.id.delete_tv })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击图片
		case R.id.imageview:
			if (isShow) {
				isShow = false;
				left_sb.setVisibility(View.GONE);
				right_sb.setVisibility(View.GONE);
			} else {
				isShow = true;
				left_sb.setVisibility(View.VISIBLE);
				right_sb.setVisibility(View.VISIBLE);
			}
			break;
		// 点击分享
		case R.id.share_tv:
			pbDialog.show();
			if (curFilePath != null && curFilePath.contains("_")) {
				fileName = curFilePath.substring(
						curFilePath.lastIndexOf("/") + 1).replace(".a", "");
			} else {
				fileName = url.substring(url.lastIndexOf("/") + 1).replace(
						".a", "");
			}
			// 获取分享内容
			ShareExec.getInstance().getShareTryOnContent(mHandler,
					ManagerUtils.getInstance().getUser_id(mContext), fileName,
					NetworkAsyncCommonDefines.GET_SHARE_S,
					NetworkAsyncCommonDefines.GET_SHARE_F);
			break;
		// 点击删除
		case R.id.delete_tv:
			if (isFinish()) {
				pbDialog.show();
				if (curFilePath != null && curFilePath.contains("_")) {
					File file1 = new File(curFilePath);
					if (file1.exists()) {
						file1.delete();
					}
					File file2 = new File(url);
					if (file2.exists()) {
						file2.delete();
					}
					File file3 = new File(url.replace(".a", ".b"));
					if (file3.exists()) {
						file3.delete();
					}
					File file4 = new File(url.replace(".a", ".c"));
					if (file4.exists()) {
						file4.delete();
					}
				} else {
					File file1 = new File(url);
					if (file1.exists()) {
						file1.delete();
					}
					File file2 = new File(url.replace(".a", ".b"));
					if (file2.exists()) {
						file2.delete();
					}
					File file3 = new File(url.replace(".a", ".c"));
					if (file3.exists()) {
						file3.delete();
					}
				}
				pbDialog.dismiss();
				finish();
			}
			break;
		// 点击返回
		case R.id.back_rl:
			finish();
			break;
		// 点击更多
		case R.id.operate_tv:
			Intent tryOnCollectActivity = new Intent(TryOnInfoActivity.this,
					TryOnCollectActivity.class);
			startActivity(tryOnCollectActivity);
			break;
		default:
			break;
		}
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			// 获取分享内容成功
			case NetworkAsyncCommonDefines.GET_SHARE_S:
				Bundle data = msg.getData();
				if (data != null) {
					title = data.getString("title");
					content = data.getString("content");
					pic = data.getString("pic");
					uploadPath = data.getString("uploadPath");
					sharePath = data.getString("sharePath");
					Intent intent = new Intent(mContext,
							DownLoadingService.class);
					if (curFilePath != null) {
						intent.putExtra("uploadFile", curFilePath);
					} else {
						intent.putExtra("uploadFile", url);
					}
					intent.putExtra("uploadPath", uploadPath);
					intent.putExtra("id",
							NetworkAsyncCommonDefines.UPLOAD_TRYON_IMAGE);
					startService(intent);
					// shareWXCircle();
					pbDialog.dismiss();
					shareAll();
				}
				break;
			// 获取分享内容失败
			case NetworkAsyncCommonDefines.GET_SHARE_F:
				pbDialog.dismiss();
				Toast.makeText(mContext, "分享失败", Toast.LENGTH_SHORT).show();
				break;
			// 没有变身展示原图
			case NetworkAsyncCommonDefines.NO_BS:
				// 绑定图片
				x.image().bind(imageview, url, mImageOptions,
						new CommonCallback<Drawable>() {

							@Override
							public void onSuccess(Drawable arg0) {
								// TODO Auto-generated method
								// stub
							}

							@Override
							public void onFinished() {
								// TODO Auto-generated method
								// stub

							}

							@Override
							public void onError(Throwable arg0, boolean arg1) {
								// TODO Auto-generated method
								// stub
							}

							@Override
							public void onCancelled(CancelledException arg0) {
								// TODO Auto-generated method
								// stub
							}
						});
				pbDialog.dismiss();
				break;

			default:
				break;
			}
		}

	};

	/**
	 * @description:分享到所有平台
	 */
	protected void shareAll() {
		Intent intent = new Intent(mContext, MyShareActivity.class);
		intent.putExtra("targeturl", sharePath);
		intent.putExtra("content", content);
		intent.putExtra("title", title);
		intent.putExtra("pic", pic);
		startActivity(intent);
	}

}
