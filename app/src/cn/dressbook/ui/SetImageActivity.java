package cn.dressbook.ui;

import java.io.File;

import org.opencv.samples.facedetect.DetectionBasedTracker;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.common.NetworkAsyncCommonDefines;
import cn.dressbook.ui.common.PathCommonDefines;
import cn.dressbook.ui.utils.FileSDCacher;
import cn.dressbook.ui.utils.UriUtils;

/**
 * @description: 设置形象
 * @author:袁东华
 * @time:2015-11-2下午2:20:03
 */
@ContentView(R.layout.paizhaoyindao_layout)
public class SetImageActivity extends BaseActivity {
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions(ImageView.ScaleType.CENTER_INSIDE, false);
	private Context mContext = SetImageActivity.this;
	/** 返回按钮 */
	@ViewInject(R.id.back_rl)
	private RelativeLayout back_rl;
	/** 标题 */
	@ViewInject(R.id.title_tv)
	private TextView title_tv;
	/** 默认图片 */
	@ViewInject(R.id.imageview)
	private ImageView imageview;
	/** 拍照攻略 */
	@ViewInject(R.id.pzgl_tv)
	private TextView pzgl_tv;
	@ViewInject(R.id.pzyd_xc_rl)
	private RelativeLayout pzyd_xc_rl;
	@ViewInject(R.id.pzyd_pz_rl)
	private RelativeLayout pzyd_pz_rl;
	/**
	 * 拍照的照片存储位置
	 */
	private File mPhotoDir;
	/**
	 * 照相机拍照得到的图片
	 */
	private File mCameraHead;
	/**
	 * 照片的名字
	 */
	private String mFileName = "camerahead1.jpg";
	/**
	 * 用来标识请求照相功能
	 */
	private static final int CAMERA_WITH_DATA = 2015;
	/**
	 * 用来标识裁剪功能
	 */
	private static final int CROP_BIG_PICTURE = 0x12;
	/**
	 * 临时图片名字
	 */
	private String mTemporaryName = "camerahead1.jpg";

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		title_tv.setText("形象");

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
	boolean islondImage=false;
	@Override
	protected void initData() {
		x.image().bind(imageview,
				PathCommonDefines.WARDROBE_HEAD + "/xingxiang.0",
				mImageOptions, new CommonCallback<Drawable>() {

					@Override
					public void onSuccess(Drawable arg0) {
						// TODO Auto-generated method stub
						islondImage=true;
					}

					@Override
					public void onFinished() {
						// TODO Auto-generated method stub
						if (!islondImage) {
							imageview.setImageResource(R.drawable.setimage_src_1);
							
						} 
					}

					@Override
					public void onError(Throwable arg0, boolean arg1) {
						// TODO Auto-generated method stub
						imageview.setImageResource(R.drawable.setimage_src_1);
					}

					@Override
					public void onCancelled(CancelledException arg0) {
						// TODO Auto-generated method stub
						imageview.setImageResource(R.drawable.setimage_src_1);
					}
				});

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		x.image().bind(imageview,
				PathCommonDefines.WARDROBE_HEAD + "/xingxiang.0",
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
						imageview.setImageResource(R.drawable.setimage_src_1);
					}

					@Override
					public void onCancelled(CancelledException arg0) {
						// TODO Auto-generated method stub
						imageview.setImageResource(R.drawable.setimage_src_1);
					}
				});

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);

	}

	/**
	 * @description 判断是否有SD卡
	 * @parameters
	 */
	private void judgeExternalStorage() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {// 判断是否有SD卡
			toTakePic();
		} else {
		}
	}

	/**
	 * 创建照片目录，调用相机
	 * 
	 */
	protected void toTakePic() {
		try {
			mPhotoDir = new File(PathCommonDefines.PAIZHAO);
			if (mPhotoDir.exists()) {
				// 调用相机时清空拍照目录
				FileSDCacher.deleteDirectory2(mPhotoDir);
			} else {
				boolean iscreat = mPhotoDir.mkdirs();// 创建照片的存储目录

			}
			mCameraHead = new File(mPhotoDir, mFileName);

			final Intent intent = getTakePickIntent(mCameraHead);
			startActivityForResult(intent, CAMERA_WITH_DATA);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(this, "photoPickerNotFoundText", Toast.LENGTH_LONG)
					.show();
		}
	}

	/**
	 * 获取图片的intent
	 * 
	 * @param f
	 * @return
	 */
	public static Intent getTakePickIntent(File f) {
		if (new File(PathCommonDefines.PAIZHAO).exists()) {
		} else {
		}
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		return intent;
	}

	@Event({ R.id.pzyd_xc_rl, R.id.pzyd_pz_rl, R.id.pzgl_tv, R.id.back_rl })
	private void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击拍照攻略
		case R.id.pzgl_tv:

			pbDialog.show();
			Intent pzgl = new Intent(this, PaiZhaoGongLueActivity.class);
			startActivity(pzgl);

			pbDialog.dismiss();
			break;
		// 点击拍照
		case R.id.pzyd_pz_rl:
			toTakePic();
			break;
		// 点击相册
		case R.id.pzyd_xc_rl:
			xiangCe();
			break;
		// 点击返回
		case R.id.back_rl:
			finish();
			System.gc();
			break;
		}
	}

	/**
	 * @description:打开相册
	 */
	private void xiangCe() {
		// TODO Auto-generated method stub
		// 从相簿中获得照片
		Intent mIntent = new Intent(Intent.ACTION_GET_CONTENT);
		mIntent.addCategory(Intent.CATEGORY_OPENABLE);
		mIntent.setType("image/*");
		startActivityForResult(mIntent, NetworkAsyncCommonDefines.ENTER_ALBUM);
		overridePendingTransition(R.anim.back_enter, R.anim.anim_exit);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		// 拍照返回
		case CAMERA_WITH_DATA:
			try {
				if (mCameraHead.exists()) {
					pbDialog.dismiss();
					Intent manipulatingImage = new Intent(mContext,
							PhotoCropActivity.class);
					startActivity(manipulatingImage);
					// 调用裁剪
					// CropImageUri(Uri.fromFile(mCameraHead), Uri.fromFile(f2),
					// CROP_BIG_PICTURE);

				} else {

				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			break;
		// 裁剪返回
		case CROP_BIG_PICTURE:
			File f1 = new File(PathCommonDefines.PAIZHAO, mTemporaryName);
			if (f1.exists()) {
				if (FileSDCacher.fuZhiFile(f1, mCameraHead)) {
					// 裁剪后的图片复制成/paizhao/camerahead.0
					File t = new File(PathCommonDefines.PAIZHAO,
							"camerahead.jpg");
					if (FileSDCacher.fuZhiFile(mCameraHead, t)) {
						Toast.makeText(mContext, "正在检测图片是否合格,请耐心等待",
								Toast.LENGTH_LONG).show();
						pbDialog.show();
						new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub

								long result = DetectionBasedTracker
										.nativeHeadDetect(
												"paizhao/camerahead.jpg",
												"paizhao/camerahead1.jpg");
								if (result == 0) {
									mHandler.sendEmptyMessage(NetworkAsyncCommonDefines.CHECK_HEAD_EXIST);

								} else {
								}
							}
						}).start();
					}
				}
			} else {

			}
			break;
		// 进入相册返回
		case NetworkAsyncCommonDefines.ENTER_ALBUM:
			if (data != null) {
				Uri uri = data.getData(); // 返回的是一个Uri
				if (uri != null) {
					String path = UriUtils.getInstance().getImageAbsolutePath(
							this, uri);
					if (path == null || path.equals("")) {
						Toast.makeText(mContext, "没找到图片,请拍照",
								Toast.LENGTH_SHORT).show();
						return;
					}
					File s = new File(path);
					if (s != null && s.exists()) {
						s = new File(path);
						File t = new File(PathCommonDefines.PAIZHAO,
								"/camerahead1.jpg");
						FileSDCacher.fuZhiFile(s, t);
						pbDialog.dismiss();
						Intent manipulatingImage = new Intent(mContext,
								PhotoCropActivity.class);
						startActivity(manipulatingImage);
					} else {
						Toast.makeText(mContext, "没找到图片,请拍照",
								Toast.LENGTH_SHORT).show();
					}
				}
			} else {
				Toast.makeText(mContext, "没找到图片,请拍照", Toast.LENGTH_SHORT)
						.show();

			}

			break;
		}
	}

	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case NetworkAsyncCommonDefines.CHECK_HEAD_EXIST:
				pbDialog.dismiss();
				Intent manipulatingImage = new Intent(mContext,
						DescribeFaceNeckActivity.class);
				startActivity(manipulatingImage);
				break;

			default:
				break;
			}
		};

	};

	/**
	 * 从uri得到真实的file路径
	 * 
	 * @param uri
	 * @return
	 */
	private String GetPath(Uri uri) {
		//
		String path = uri.getPath();
		File mfile = new File(path);
		if (mfile.exists())
			return mfile.getPath();

		try {
			String[] projection = { MediaStore.Images.Media.DATA };
			Cursor cursor = managedQuery(uri, projection, null, null, null);
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			String s = cursor.getString(column_index);
			cursor.close();
			return s;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			finish();
		}
		return null;
	}

	/**
	 * 图像剪裁
	 * 
	 * @param uri
	 *            原始图片uri
	 * @param outuri
	 *            输出的图片uri
	 * @param requestCode
	 */
	private void CropImageUri(Uri uri, Uri outuri, int requestCode) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// intent.putExtra("aspectX", 2);
		// intent.putExtra("aspectY", 1);
		// intent.putExtra("outputX", outputX);
		// intent.putExtra("outputY", outputY);
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, outuri);
		intent.putExtra("return-data", false);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		startActivityForResult(intent, requestCode);
	}

}
