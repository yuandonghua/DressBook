package cn.dressbook.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.opencv.samples.facedetect.DetectionBasedTracker;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.listener.OptionListener;
import cn.dressbook.ui.model.AttireScheme;
import cn.dressbook.ui.model.Wardrobe;
import cn.dressbook.ui.service.DownLoadingService;
import cn.dressbook.ui.service.ImageProcessingService;
import cn.dressbook.ui.utils.FileSDCacher;
import cn.dressbook.ui.utils.SharedPreferenceUtils;
import cn.dressbook.ui.utils.ToastUtils;

/**
 * @description: 形象编辑
 * @author:袁东华
 * @modifier:袁东华
 * @remarks:
 * @2015年6月19日 下午1:13:34
 */
@ContentView(R.layout.showpic_layout)
public class ShowPicActivity extends BaseActivity {
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions(ImageView.ScaleType.CENTER_INSIDE, false);
	private String wardrobeId;
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
	/**
	 * 操作按钮
	 */
	@ViewInject(R.id.operate_tv)
	private TextView operate_tv;
	/**
	 * 操作按钮
	 */
	@ViewInject(R.id.operate_tv_1)
	private TextView operate_tv_1;
	/**
	 * 进度提示
	 */
	@ViewInject(R.id.progress_tv)
	private TextView progress_tv;
	/**
	 * 方案列表集合
	 */
	private ArrayList<AttireScheme> attireSchemeList;
	/**
	 * 我明白按钮
	 */
	@ViewInject(R.id.isee_iv)
	private ImageView isee_iv;
	/**
	 * 向上移动提示图
	 */
	@ViewInject(R.id.upward_hint)
	private ImageView upward_hint;
	/**
	 * 向下移动提示图
	 */
	@ViewInject(R.id.downward_hint)
	private ImageView downward_hint;
	/**
	 * 放大提示图
	 */
	@ViewInject(R.id.largen_hint)
	private ImageView largen_hint;
	/**
	 * 缩小提示图
	 */
	@ViewInject(R.id.micrify_hint)
	private ImageView micrify_hint;
	/**
	 * 调节胖瘦提示图
	 */
	@ViewInject(R.id.bodyweight_hint)
	private ImageView bodyweight_hint;
	/**
	 * 调节高矮提示图
	 */
	@ViewInject(R.id.bodyheight_hint)
	private ImageView bodyheight_hint;
	/**
	 * 编辑Relativelayout
	 */
	@ViewInject(R.id.image_edit_rl)
	private RelativeLayout image_edit_rl;
	/**
	 * 高低进度条
	 */
	@ViewInject(R.id.short_tall_seekbar)
	private SeekBar short_tall_seekbar;
	/**
	 * 胖瘦进度条
	 */
	@ViewInject(R.id.thin_fat_seekbar)
	private SeekBar thin_fat_seekbar;
	/**
	 * 向上
	 */
	@ViewInject(R.id.upward_iv)
	private ImageView upward_iv;
	/**
	 * 向下
	 */
	@ViewInject(R.id.downward_iv)
	private ImageView downward_iv;
	/**
	 * 放大
	 */
	@ViewInject(R.id.largen_iv)
	private ImageView largen_iv;
	/**
	 * 缩小
	 */
	@ViewInject(R.id.micrify_iv)
	private ImageView micrify_iv;
	private Context mContext = ShowPicActivity.this;
	private SharedPreferenceUtils mSharedPreferenceUtils = SharedPreferenceUtils
			.getInstance();
	private boolean bianji;
	private Wardrobe mWardrobe;
	/**
	 * 照片地址
	 */
	private String mCameraPath;
	/**
	 * 形象地址
	 */
	private String mNewImage;
	private HashMap<String, Integer> mBodyData = new HashMap<String, Integer>();
	private int mid;
	/**
	 * 我的形象
	 */
	@ViewInject(R.id.myimage_iv)
	private ImageView myimage_iv;

	private long time1 = 0;
	private long time2 = 0;
	/**
	 * 形象编辑的源文件
	 */
	private String mSrcPath = "paizhao/camerahead.0";
	/**
	 * 头像位置
	 */
	private int mHeadPosition = 0;
	/**
	 * 头像缩放值
	 */
	private float mHeadScale = 1;
	/**
	 * 形象身高变化值
	 */
	private int mBodyHeight = 0;
	/**
	 * 形象胖瘦
	 */
	private int mBodyWeight = 0;
	/**
	 * 模特的路径
	 */
	private String mModelPath = null;
	/**
	 * 穿衣输出的形象路径
	 */
	private String mMyImage = "xingxiang/xingxiang.jpeg";

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		title_tv.setText("形象照");
		operate_tv.setText("完成");
		operate_tv_1.setText("重拍");
		operate_tv_1.setVisibility(View.VISIBLE);
		operate_tv.setVisibility(View.VISIBLE);
		wardrobeId = mSharedPreferenceUtils.getWardrobeID(mContext);
		mid = Integer.parseInt(mSharedPreferenceUtils.getMid(mContext));
		// 合成形象照
		Intent service = new Intent(mContext, ImageProcessingService.class);
		service.putExtra("id", NetworkAsyncCommonDefines.COMPOUND_IMAGE);
		service.putExtra("shap", mid);
		startService(service);

		mIsFirst = mSharedPreferenceUtils.getisFirstEditImage(mContext);
		// 获取创建形象时输入的身体数据
		mBodyData = mSharedPreferenceUtils.getCreateImageData(mContext);
		mModelPath = "mote/" + mid + ".png";
		mCameraPath = PathCommonDefines.PAIZHAO + "/camerahead.0";
		mNewImage = PathCommonDefines.XINGXIANG + "/xingxiang.0";
		File newImageFile = new File(mNewImage);
		if (newImageFile.exists()) {
			// 绑定图片
			x.image().bind(myimage_iv, mNewImage, mImageOptions);
			isHideHintView();
		} else {
			time1 = System.currentTimeMillis();
			pbDialog.show();
			mHandler.postDelayed(searchNewImage, 5000);
			progress_tv.setVisibility(View.VISIBLE);
			setProgressContent();
		}

	}

	/**
	 * 寻找合成的形象文件
	 */
	private Runnable searchNewImage = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			jianChaXXZ();
		}
	};

	/**
	 * @description 检查形象照
	 * @parameters
	 */
	protected void jianChaXXZ() {
		// TODO Auto-generated method stub
		File xingxiangFile = new File(mNewImage);
		if (xingxiangFile.exists()) {
			progress_tv.setVisibility(View.GONE);

			pbDialog.dismiss();
			// 绑定图片
			x.image().bind(myimage_iv, mNewImage, mImageOptions);
			isHideHintView();
		} else {
			time2 = System.currentTimeMillis();

			if ((time2 - time1) >= 60000) {
				Toast.makeText(mContext, "亲，形象创建失败，请重新创建形象", Toast.LENGTH_LONG)
						.show();
				finish();
			} else {
				mHandler.postDelayed(searchNewImage, 3000);
			}
		}
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			// 编辑失败
			case NetworkAsyncCommonDefines.EDIT_F:
				thin_fat_seekbar.setEnabled(true);
				short_tall_seekbar.setEnabled(true);
				pbDialog.dismiss();
				Toast.makeText(mContext, "形象编辑失败", Toast.LENGTH_SHORT).show();
				break;
			// 形象处理完成
			case NetworkAsyncCommonDefines.UPWARD:
				thin_fat_seekbar.setEnabled(true);
				short_tall_seekbar.setEnabled(true);
				// 绑定图片
				x.image().bind(myimage_iv, mNewImage, mImageOptions);
				ToastUtils.getInstance().showToast(mContext, "编辑完成", 0);
				pbDialog.dismiss();
				break;

			}

		}

	};
	/**
	 * 是否第一次编辑形象
	 */
	private Boolean mIsFirst;

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		ToastUtils.getInstance().cancelToast();
	}

	@Event({ R.id.operate_tv, R.id.operate_tv_1, R.id.largen_iv,
			R.id.micrify_iv, R.id.downward_iv, R.id.upward_iv, R.id.myimage_iv,
			R.id.image_edit_rl, R.id.isee_iv, R.id.operate_tv,
			R.id.operate_tv_1 })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击我明白了
		case R.id.isee_iv:
			mIsFirst = false;
			mSharedPreferenceUtils.setisFirstEditImage(mContext, mIsFirst);
			isHideHintView();
			break;
		// 点击编辑部分
		case R.id.image_edit_rl:
			image_edit_rl.setVisibility(View.GONE);
			break;
		// 点击形象
		case R.id.myimage_iv:
			image_edit_rl.setVisibility(View.VISIBLE);
			break;
		// 点击向上
		case R.id.upward_iv:
			if (isFinish()) {

				mHeadPosition++;
				if (mHeadPosition > 5) {
					Toast.makeText(mContext, "不能移动了", Toast.LENGTH_SHORT)
							.show();
				} else {

					pbDialog.show();
					changePosition();
				}
			} else {
				ToastUtils.getInstance().showToast(mContext, "正在处理请耐心等待", 0);
			}
			break;
		// 点击向下
		case R.id.downward_iv:
			if (isFinish()) {
				mHeadPosition--;
				if (mHeadPosition < -5) {
					Toast.makeText(mContext, "不能移动了", Toast.LENGTH_SHORT)
							.show();
				} else {
					pbDialog.show();
					changePosition();

				}
			} else {
				ToastUtils.getInstance().showToast(mContext, "正在处理请耐心等待", 0);
			}
			break;
		// 点击缩小
		case R.id.micrify_iv:
			if (isFinish()) {
				mHeadScale -= 0.02;
				if (mHeadScale < 0.9) {
					Toast.makeText(mContext, "不能缩小了", Toast.LENGTH_SHORT)
							.show();
				} else {
					pbDialog.show();
					scaleHead();
				}
			} else {
				ToastUtils.getInstance().showToast(mContext, "正在处理请耐心等待", 0);
			}
			break;
		// 点击放大
		case R.id.largen_iv:
			if (isFinish()) {
				mHeadScale += 0.02;
				if (mHeadScale > 1.1) {
					Toast.makeText(mContext, "不能放大了", Toast.LENGTH_SHORT)
							.show();
				} else {
					pbDialog.show();
					scaleHead();
				}
			} else {
				ToastUtils.getInstance().showToast(mContext, "正在处理请耐心等待", 0);
			}
			break;
		// 点击重拍
		case R.id.operate_tv_1:
			if (isFinish()) {
				pbDialog.show();
				File file = new File(PathCommonDefines.PAIZHAO);
				if (FileSDCacher.deleteDirectory2(file)) {

					finish();
				}
			} else {
				ToastUtils.getInstance().showToast(mContext, "正在处理请耐心等待", 0);
			}
			break;
		// 点击完成
		case R.id.operate_tv:
			if (isFinish()) {
				if (ManagerUtils.getInstance().isLogin(mContext)) {
					fuzhi();

					finish();

				} else {

					Toast.makeText(mContext, "创建形象需要登陆", Toast.LENGTH_SHORT)
							.show();
					Intent next = new Intent(mContext, LoginActivity.class);
					startActivity(next);
				}
			} else {
				ToastUtils.getInstance().showToast(mContext, "正在处理请耐心等待", 0);
			}
			break;
		}
	}

	/**
	 * @description:隐藏提示View
	 * @exception
	 */
	@SuppressLint("NewApi")
	private void isHideHintView() {
		// TODO Auto-generated method stub

		if (mIsFirst) {
			image_edit_rl.setBackgroundResource(R.drawable.bodyedit_bg);
			isee_iv.setVisibility(View.VISIBLE);
			upward_hint.setVisibility(View.VISIBLE);
			downward_hint.setVisibility(View.VISIBLE);
			largen_hint.setVisibility(View.VISIBLE);
			micrify_hint.setVisibility(View.VISIBLE);
			bodyweight_hint.setVisibility(View.VISIBLE);
			bodyheight_hint.setVisibility(View.VISIBLE);
		} else {
			image_edit_rl.setBackground(null);
			isee_iv.setVisibility(View.GONE);
			upward_hint.setVisibility(View.GONE);
			downward_hint.setVisibility(View.GONE);
			largen_hint.setVisibility(View.GONE);
			micrify_hint.setVisibility(View.GONE);
			bodyweight_hint.setVisibility(View.GONE);
			bodyheight_hint.setVisibility(View.GONE);
		}
		image_edit_rl.setVisibility(View.VISIBLE);
	}

	/**
	 * @description:缩放头部
	 * @exception
	 */
	private void scaleHead() {
		// TODO Auto-generated method stub
		ManagerUtils.getInstance().getExecutorService2()
				.execute(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						mNewImage = PathCommonDefines.XINGXIANG
								+ "/xingxiang.jpeg";
						File imageFile = new File(mNewImage);
						if (imageFile.exists()) {
							imageFile.delete();
						}

						long positionResult = DetectionBasedTracker
								.nativeScaleHead(mSrcPath, mHeadScale);
						if (positionResult == 0) {

							long dressResult = DetectionBasedTracker
									.nativeMergeBody(mSrcPath, mModelPath,
											mMyImage);
							if (dressResult == 0) {
								mNewImage = PathCommonDefines.XINGXIANG
										+ "/xingxiang.jpeg";
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.UPWARD);
							}
						} else {
							mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.EDIT_F);
						}

					}
				});
	}

	/**
	 * @description:改变头部位置
	 * @exception
	 */
	private void changePosition() {
		// TODO Auto-generated method stub
		ManagerUtils.getInstance().getExecutorService2()
				.execute(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

						long positionResult = DetectionBasedTracker
								.nativeChangePosition(mSrcPath, mHeadPosition);
						if (positionResult == 0) {
							mNewImage = PathCommonDefines.XINGXIANG
									+ "/xingxiang.jpeg";
							File imageFile = new File(mNewImage);
							if (imageFile.exists()) {
								imageFile.delete();
							}

							long dressResult = DetectionBasedTracker
									.nativeMergeBody(mSrcPath, mModelPath,
											mMyImage);
							if (dressResult == 0) {
								mNewImage = PathCommonDefines.XINGXIANG
										+ "/xingxiang.jpeg";
								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.UPWARD);
							}
						}

					}
				});
	}

	/**
	 * @param xx_id
	 * @description 复制形象
	 * @parameters
	 */
	protected void fuzhi() {
		// TODO Auto-generated method stub
		File folder = new File(PathCommonDefines.WARDROBE_HEAD);
		if(!folder.exists()){
			folder.mkdirs();
		}
		// 复制头像
		File f = new File(PathCommonDefines.PAIZHAO + "/camerahead.0");
		File f1 = new File(PathCommonDefines.WARDROBE_HEAD + "/head.0");
		if (f1.exists()) {
			f1.delete();
		}
		FileSDCacher.fuZhiFile(f, f1);
		// 复制形象照
		File f2 = new File(PathCommonDefines.XINGXIANG + "/xingxiang.0");
		File f3 = new File(PathCommonDefines.WARDROBE_HEAD + "/xingxiang.0");
		if (f3.exists()) {
			f3.delete();
		}
		FileSDCacher.fuZhiFile(f2, f3);
		// 复制头像的xml
		File f4 = new File(PathCommonDefines.PAIZHAO + "/camerahead.0.xml");
		File f5 = new File(PathCommonDefines.WARDROBE_HEAD + "/head.0.xml");
		if (f5.exists()) {
			f5.delete();
		}
		FileSDCacher.fuZhiFile(f4, f5);
		// 复制脸脖mask
		File f6 = new File(PathCommonDefines.PAIZHAO
				+ "/camerahead.0maskfaceneck.png");
		File f7 = new File(PathCommonDefines.WARDROBE_HEAD
				+ "/head.0maskfaceneck.png");
		if (f7.exists()) {
			f7.delete();
		}
		FileSDCacher.fuZhiFile(f6, f7);
		// 复制头像的xml
		File f8 = new File(PathCommonDefines.PAIZHAO
				+ "/camerahead.0maskhead.png");
		File f9 = new File(PathCommonDefines.WARDROBE_HEAD
				+ "/head.0maskhead.png");
		if (f9.exists()) {
			f9.delete();
		}
		FileSDCacher.fuZhiFile(f8, f9);
		String tryResult = mSharedPreferenceUtils.getTryResultSave(mContext);
		if (!"no".equals(tryResult)) {
			// 删除服务端图片
			Intent service = new Intent(mContext, DownLoadingService.class);
			service.putExtra("id", NetworkAsyncCommonDefines.DELETE_SY_SERVER);
			startService(service);
		}
		// 开始上传
		Intent service = new Intent(mContext, ImageProcessingService.class);
		service.putExtra("id", NetworkAsyncCommonDefines.UPLOAD);
		service.putExtra("wardrobeId", wardrobeId);
		startService(service);
		
		File tryon = new File(PathCommonDefines.WARDROBE_TRYON);
		if (!tryon.exists()) {
			tryon.mkdirs();
		}
		FileSDCacher.deleteDirectory2(tryon);
		File file = new File(PathCommonDefines.XINGXIANG);
		if (!file.exists()) {
			file.mkdirs();
		}
		FileSDCacher.deleteDirectory2(file);
		File file2 = new File(PathCommonDefines.PAIZHAO);
		if (!file2.exists()) {
			file2.mkdirs();
		}
		FileSDCacher.deleteDirectory2(file2);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		Toast.makeText(mContext, "亲,失败也是一种胜利,要坚强的走下去", Toast.LENGTH_LONG)
				.show();
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#finish()
	 * 
	 * @Description TODO
	 */
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		if (searchNewImage != null) {
			mHandler.removeCallbacks(searchNewImage);
		}
	}

	/**
	 * @description:设置进度内容
	 * @exception
	 */
	private void setProgressContent() {
		if (progress_tv.getVisibility() != View.GONE) {

			if (progress_tv.getVisibility() != View.GONE) {
				progress_tv.setText("正在处理您的面部形象 --3s");
				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						if (progress_tv.getVisibility() != View.GONE) {
							progress_tv.setText("正在生成您的形象照  --2s");
							mHandler.postDelayed(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									if (progress_tv.getVisibility() != View.GONE) {
										progress_tv.setText("即将完成……");
									}
								}
							}, 3000);
						}
					}
				}, 3000);
			}

		}
	};

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		back_rl.setVisibility(View.GONE);
		// 调节胖瘦
		thin_fat_seekbar
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
						if (isFinish()) {
							int progress = seekBar.getProgress();
							if (mBodyWeight != progress) {
								thin_fat_seekbar.setEnabled(false);
								short_tall_seekbar.setEnabled(false);

								mBodyWeight = progress;
								pbDialog.show();
								changeBody();
							}

						} else {
							ToastUtils.getInstance().showToast(mContext,
									"正在处理请耐心等待", 0);
						}
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						// TODO Auto-generated method stub
					}
				});
		// 调节高矮
		short_tall_seekbar
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
						if (isFinish()) {
							int progress = seekBar.getProgress();
							if (mBodyHeight != progress) {
								thin_fat_seekbar.setEnabled(false);
								short_tall_seekbar.setEnabled(false);

								mBodyHeight = progress;
								pbDialog.show();
								changeHeight();
							}
						} else {
							ToastUtils.getInstance().showToast(mContext,
									"正在处理请耐心等待", 0);
						}
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						// TODO Auto-generated method stub

					}
				});

	}

	/**
	 * @description:修改高矮
	 * @exception
	 */
	protected void changeHeight() {
		// TODO Auto-generated method stub
		ManagerUtils.getInstance().getExecutorService2()
				.execute(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

						long positionResult = DetectionBasedTracker
								.nativeSetWarpParam(mSrcPath, mBodyWeight,
										mBodyHeight);
						if (positionResult == 0) {
							mNewImage = PathCommonDefines.XINGXIANG
									+ "/xingxiang.jpeg";
							File imageFile = new File(mNewImage);
							if (imageFile.exists()) {
								imageFile.delete();
							}

							long dressResult = DetectionBasedTracker
									.nativeMergeBody(mSrcPath, mModelPath,
											mMyImage);
							if (dressResult == 0) {

								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.UPWARD);
							}
						} else {
							mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.EDIT_F);
						}

					}
				});
	}

	/**
	 * @description:修改胖瘦
	 * @exception
	 */
	protected void changeBody() {
		// TODO Auto-generated method stub
		ManagerUtils.getInstance().getExecutorService2()
				.execute(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

						long positionResult = DetectionBasedTracker
								.nativeSetWarpParam(mSrcPath, mBodyWeight,
										mBodyHeight);
						if (positionResult == 0) {
							mNewImage = PathCommonDefines.XINGXIANG
									+ "/xingxiang.jpeg";
							File imageFile = new File(mNewImage);
							if (imageFile.exists()) {
								imageFile.delete();
							}

							long dressResult = DetectionBasedTracker
									.nativeMergeBody(mSrcPath, mModelPath,
											mMyImage);
							if (dressResult == 0) {

								mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.UPWARD);
							}
						}

					}
				});
	}

}
