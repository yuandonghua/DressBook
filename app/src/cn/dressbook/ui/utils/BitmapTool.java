package cn.dressbook.ui.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.SoftReference;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.Log;
import android.view.WindowManager;
import cn.dressbook.ui.utils.ScreenUtil.Screen;


/**
 * Bitmap工具类
 * 
 * @author 袁东华
 * */
public class BitmapTool {
	private static BitmapTool mBitmapTool;

	private BitmapTool() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static BitmapTool getInstance() {
		if (mBitmapTool == null) {
			mBitmapTool = new BitmapTool();
		}
		return mBitmapTool;
	}

	/**
	 * 得到bitmap的一部分
	 */
	public Bitmap getPartOfBitmap(String path, Context context) {
		Options opts = new Options();
		// 不读取像素数组到内存中，仅读取图片的信息
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, opts);
		// 从Options中获取图片的分辨率
		int imageHeight = opts.outHeight;
		int imageWidth = opts.outWidth;

		// 获取Android屏幕的服务
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		// 获取屏幕的分辨率，getHeight()、getWidth已经被废弃掉了
		// 应该使用getSize()，但是这里为了向下兼容所以依然使用它们
		int windowHeight = wm.getDefaultDisplay().getHeight();
		int windowWidth = wm.getDefaultDisplay().getWidth();

		// 计算采样率
		int scaleX = imageWidth / windowWidth;
		int scaleY = imageHeight / windowHeight;
		int scale = 1;
		// 采样率依照最大的方向为准
		if (scaleX > scaleY && scaleY >= 1) {
			scale = scaleX;
		}
		if (scaleX < scaleY && scaleX >= 1) {
			scale = scaleY;
		}

		// false表示读取图片像素数组到内存中，依照设定的采样率
		opts.inJustDecodeBounds = false;
		// 采样率
		opts.inSampleSize = scale;
		Bitmap bitmap = BitmapFactory.decodeFile(path, opts);
		return bitmap;
	}

	/**
	 * 得到bitmap的一部分
	 */
	public Bitmap getBitmapScale(String path, Context context,int num) {
		Options opts = new Options();
		// 不读取像素数组到内存中，仅读取图片的信息
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, opts);
		// 从Options中获取图片的分辨率
		int imageHeight = opts.outHeight;
		int imageWidth = opts.outWidth;

		// 获取Android屏幕的服务
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		// 获取屏幕的分辨率，getHeight()、getWidth已经被废弃掉了
		// 应该使用getSize()，但是这里为了向下兼容所以依然使用它们
		int windowWidth = wm.getDefaultDisplay().getWidth();

		// 计算采样率
		int scaleX = imageWidth / (windowWidth*num);
		int scale = 1;
		// 采样率依照最大的方向为准
		if (scaleX >= 2) {
			scale = scaleX;

			// false表示读取图片像素数组到内存中，依照设定的采样率
			opts.inJustDecodeBounds = false;
			// 采样率
			opts.inSampleSize = scale;
			Bitmap bitmap = BitmapFactory.decodeFile(path, opts);
			return bitmap;
		}
		return null;
	}

	/**
	 * 读取SD卡下的图片，并创建成Bitmap
	 * 
	 * @param url
	 *            URL地址
	 * @return bitmap
	 */
	public Bitmap createImage(String url) {
		// Log.d("ImageDownloader",
		// "开始调用CreateImage():" + System.currentTimeMillis());
		Bitmap bitmap = null;
		if (url == null || url.equals("")) {
			return null;
		}
		FileInputStream fileInputStream = null;
		try {
			// Log.d(
			// "ImageDownloader",
			// "C Before SDCard decodeStream==>" + "Heap:"
			// + (Debug.getNativeHeapSize() / 1024) + "KB "
			// + "FreeHeap:"
			// + (Debug.getNativeHeapFreeSize() / 1024) + "KB "
			// + "AllocatedHeap:"
			// + (Debug.getNativeHeapAllocatedSize() / 1024)
			// + "KB" + " url:" + url);

			fileInputStream = new FileInputStream(url);

			Options opts = new Options();
			opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
			opts.inTempStorage = new byte[100 * 1024];
			opts.inPurgeable = true;
			opts.inInputShareable = true;
			bitmap = BitmapFactory.decodeStream(fileInputStream, null, opts);

			// Log.d(
			// "ImageDownloader",
			// "C After SDCard decodeStream==>" + "Heap:"
			// + (Debug.getNativeHeapSize() / 1024) + "KB "
			// + "FreeHeap:"
			// + (Debug.getNativeHeapFreeSize() / 1024) + "KB "
			// + "AllocatedHeap:"
			// + (Debug.getNativeHeapAllocatedSize() / 1024)
			// + "KB" + " url:" + url);
		} catch (OutOfMemoryError e) {
			System.gc();
		} catch (FileNotFoundException e) {
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
					fileInputStream = null;
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
		// Log.d("ImageDownloader",
		// "结束调用CreateImage():" + System.currentTimeMillis());
		return bitmap;
	}

	/**
	 * 图片缩放处理,并保存到SDCard
	 * 
	 * @param byteArrayOutputStream
	 *            图片字节流
	 * @param screen
	 *            屏幕宽高
	 * @param url
	 *            图片网络路径
	 * @param cachePath
	 *            本地缓存父路径</br>PathCommonDefines.PHOTOCACHE_FOLDER 程序缓存图片路径;</br>
	 *            PathCommonDefines.MY_FAVOURITE_FOLDER 我的收藏图片路径
	 * @param isJpg
	 *            是否是Jpg
	 * @return 缩放后的图片bitmap
	 */
	public static Bitmap saveZoomBitmapToSDCard(
			ByteArrayOutputStream byteArrayOutputStream, Screen screen,
			String url, String cachePath, boolean isJpg) {
		Bitmap bitmap = null;
		// SoftReference<Bitmap> bitmapSoftReference = null;
		try {

			byte[] byteArray = byteArrayOutputStream.toByteArray();

			Options options = new Options();
			options.inTempStorage = new byte[16 * 1024];

			// 只加载图片的边界
			options.inJustDecodeBounds = true;

			// 获取Bitmap信息
			BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length,
					options);

			// 获取屏幕的宽和高
			int screenWidth = screen.widthPixels;
			int screenHeight = screen.heightPixels;

			// 屏幕最大像素个数
			int maxNumOfPixels = screenWidth * screenHeight;

			// 计算采样率
			int sampleSize = computeSampleSize(options, -1, maxNumOfPixels);

			options.inSampleSize = sampleSize;

			options.inJustDecodeBounds = false;

			// 重新读入图片,此时为缩放后的图片
			bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
					byteArray.length);

			// bitmapSoftReference = new SoftReference<Bitmap>(bitmap);

			// 保存到SDCard
			ImageSDCacher.getImageSDCacher().saveBitmapToSDCard(bitmap, url,
					cachePath, isJpg);

		} catch (Exception e) {
			Log.e("saveZoomBitmapToSDCard", "" + e);
		} finally {
			if (byteArrayOutputStream != null) {
				try {
					byteArrayOutputStream.close();
					byteArrayOutputStream = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// if(bitmapSoftReference.get()!=null){
		// Log.i(TAG, "图片保存后从软引用中获取的不为空----------------");
		// }

		return bitmap;
	}

	/**
	 * 图片缩放处理,并保存到SDCard
	 * 
	 * @param byteArrayOutputStream
	 *            图片字节流
	 * @param screen
	 *            屏幕宽高
	 * @param url
	 *            图片网络路径
	 * @param cachePath
	 *            本地缓存父路径</br>PathCommonDefines.PHOTOCACHE_FOLDER 程序缓存图片路径;</br>
	 *            PathCommonDefines.MY_FAVOURITE_FOLDER 我的收藏图片路径
	 * @param isJpg
	 *            是否是Jpg
	 * @return 缩放后的图片bitmap
	 */
	public static Bitmap saveZoomBitmapToSDCard(byte[] byteArray,
			Screen screen, String url, String cachePath, boolean isJpg) {
		Bitmap bitmap = null;
		// SoftReference<Bitmap> bitmapSoftReference = null;
		try {

			Options options = new Options();
			options.inTempStorage = new byte[16 * 1024];

			// 只加载图片的边界
			options.inJustDecodeBounds = true;

			// 获取Bitmap信息
			BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length,
					options);

			// 获取屏幕的宽和高
			int screenWidth = screen.widthPixels;
			int screenHeight = screen.heightPixels;

			// 屏幕最大像素个数
			int maxNumOfPixels = screenWidth * screenHeight;

			// 计算采样率
			int sampleSize = computeSampleSize(options, -1, maxNumOfPixels);

			options.inSampleSize = sampleSize;

			options.inJustDecodeBounds = false;

			// 重新读入图片,此时为缩放后的图片
			bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
					byteArray.length);

			// bitmapSoftReference = new SoftReference<Bitmap>(bitmap);

			// 保存到SDCard
			ImageSDCacher.getImageSDCacher().saveBitmapToSDCard(bitmap, url,
					cachePath, isJpg);

		} catch (Exception e) {
			Log.e("saveZoomBitmapToSDCard", "" + e);
		} finally {
		}
		// if(bitmapSoftReference.get()!=null){
		// Log.i(TAG, "图片保存后从软引用中获取的不为空----------------");
		// }

		return bitmap;
	}

	/**
	 * 图片缩放处理,并保存到SDCard
	 * 
	 * @param screen
	 *            屏幕宽高
	 * @param bitmap
	 *            图片bitmap
	 * @param cachePath
	 *            本地缓存父路径</br>PathCommonDefines.PHOTOCACHE_FOLDER 程序缓存图片路径;</br>
	 *            PathCommonDefines.MY_FAVOURITE_FOLDER 我的收藏图片路径
	 * @param isJpg
	 *            是否是Jpg
	 * @return 缩放后的图片bitmap
	 */
	public  Bitmap saveZoomBitmapToSDCard(Bitmap bitmap, Screen screen,
			String url, String cachePath, boolean isJpg) {

		SoftReference<Bitmap> bitmapSoftReference = null;

		byte[] byteArray = bitmap2Bytes(bitmap);

		try {

			Options options = new Options();

			// 获取屏幕的宽和高
			int screenWidth = screen.widthPixels;
			int screenHeight = screen.heightPixels;

			// 屏幕最大像素个数
			int maxNumOfPixels = screenWidth * screenHeight;

			// 计算采样率
			int sampleSize = computeSampleSize(options, -1, maxNumOfPixels);

			options.inSampleSize = sampleSize;

			options.inJustDecodeBounds = false;

			// 重新读入图片,此时为缩放后的图片
			Bitmap tempBitmap = BitmapFactory.decodeByteArray(byteArray, 0,
					byteArray.length, options);

			bitmapSoftReference = new SoftReference<Bitmap>(tempBitmap);

			// 保存到SDCard
			ImageSDCacher.getImageSDCacher().saveBitmapToSDCard(tempBitmap,
					url, cachePath, isJpg);

		} catch (Exception e) {
			Log.e("", e.getMessage());
		}

		return bitmapSoftReference.get();
	}

	public static byte[] bitmap2Bytes(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	// Recycle the resource of the Image
	public void recycleImage(Bitmap bitmap) {
		try {
			if (bitmap != null && !bitmap.isMutable() && !bitmap.isRecycled()) {
				bitmap.recycle();
				System.gc();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 替换特殊字符
	 * 
	 * @param fileName
	 *            图片的处理前的名字
	 * @return 图片处理后的名字
	 */
	public static String renameUploadFile(String fileName) {

		String result = "yepcolor";

		if (fileName != null && !fileName.equals("")) {

			result = fileName.hashCode() + "";// 获得文件名称的hashcode值

		}
		return result;
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		// String regEx =
		// "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		// Pattern p = Pattern.compile(regEx);
		// Matcher m = p.matcher(fileName);
		// result = m.replaceAll("").trim();

	}

	/**
	 * 计算采样率
	 * 
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	public static int computeSampleSize(Options options,
			int minSideLength, int maxNumOfPixels) {

		int initialSize = computeInitialSampleSize(options, minSideLength,

		maxNumOfPixels);

		int roundedSize;

		if (initialSize <= 8) {

			roundedSize = 1;

			while (roundedSize < initialSize) {

				roundedSize <<= 1;

			}

		} else {

			roundedSize = (initialSize + 7) / 8 * 8;

		}

		return roundedSize;

	}

	/**
	 * 计算初始采样率
	 * 
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	private static int computeInitialSampleSize(Options options,
			int minSideLength, int maxNumOfPixels) {

		double w = options.outWidth;

		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 :

		(int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));

		int upperBound = (minSideLength == -1) ? 128 :

		(int) Math.min(Math.floor(w / minSideLength),

		Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {

			// return the larger one when there is no overlapping zone.

			return lowerBound;

		}

		if ((maxNumOfPixels == -1) &&

		(minSideLength == -1)) {

			return 1;

		} else if (minSideLength == -1) {

			return lowerBound;

		} else {

			return upperBound;

		}

	}
	/**
	 * 根据图片的路径获取图片的大小
	 * 
	 * @param item
	 */
	// public static void getBitmapSize(Items item) {
	// URL url;
	// try {
	// url = new URL(item.getPicUrl());
	// URLConnection conn = url.openConnection();
	// conn.connect();
	// InputStream is = conn.getInputStream();
	// BitmapFactory.Options options = new BitmapFactory.Options();
	// BitmapFactory.decodeStream(is, null, options);
	// options.inJustDecodeBounds = true;
	// int height = options.outHeight;
	// int width = options.outWidth;
	// item.setImageWidth(width);
	// item.setImageHeight(height);
	// } catch (MalformedURLException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// }

}
