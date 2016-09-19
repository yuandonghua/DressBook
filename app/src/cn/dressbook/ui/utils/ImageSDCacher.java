package cn.dressbook.ui.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Comparator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

/**
 * 图片保存到SD卡的工具类
 * 
 * @author 袁东华
 * */
public class ImageSDCacher {

	private final static String TAG = ImageSDCacher.class.getSimpleName();
	private static ImageSDCacher mInstance;
	private final static int FREE_SD_SPACE_NEEDED_TO_CACHE = 50; // Mb
	private final static int MAX_CACHE_SIZE_NEEDED = 100; // Mb
	private final static String WHOLESALE_CONV = ".jpg";
	private final static long CACHE_EXPIRE_THRESHOLD = 3600 * 24 * 7; // one
	HandlerThread mHandlerThread = null;
	private Handler mHandler = null;
	private BitmapTool mBitmapTool = BitmapTool.getInstance();

	private ImageSDCacher() {
		mHandlerThread = new HandlerThread("sdcacher_thread");
		mHandlerThread.start();
	}

	public static ImageSDCacher getImageSDCacher() {
		if (mInstance == null) {
			mInstance = new ImageSDCacher();
		}
		return mInstance;
	}

	/**
	 * 根据图片缓存路径返回Bitmap
	 * 
	 * @param url
	 *            图片缓存路径
	 * @param cachePath
	 *            本地缓存父路径</br>PathCommonDefines.PHOTOCACHE_FOLDER 程序缓存图片路径;</br>
	 *            PathCommonDefines.MY_FAVOURITE_FOLDER 我的收藏图片路径
	 * @return bitmap
	 */
	public Bitmap getBitmapByCachePath(String url, String cachePath) {

		Bitmap bitmap = null;
		// 如果SD卡下有此图片
		if (isImageSDCachedByPath(url, cachePath)) {
			String filePath = url != null && url.startsWith("http://") ? (cachePath
					+ File.separator + convertUrlToFileName(url))
					: url;
			// 读取SD卡下的图片
			bitmap = mBitmapTool.createImage(filePath);
		} else {
			Log.w(TAG, "******PHOTOCACHE_FOLDER里没有缓存此图片******");
		}

		return bitmap;
	}

	/**
	 * 判断图片是否存在
	 * 
	 * @param url
	 *            图片链接
	 * @param cachePath
	 *            本地缓存父路径</br>PathCommonDefines.PHOTOCACHE_FOLDER 程序缓存图片路径;</br>
	 *            PathCommonDefines.MY_FAVOURITE_FOLDER 我的收藏图片路径
	 * @return 是否存在
	 */
	public boolean isImageSDCachedByPath(String url, String cachePath) {
		if (url == null || url.trim().length() <= 0) {
			Log.e(TAG, "图片的url为空--------");
			return false;
		}
		// Log.d(TAG, "判断SD卡里是否有此图片，图片的url：" + url+"    cachePath:"+cachePath);
		String fileName = url.startsWith("http://") ? convertUrlToFileName(url)
				: url;
		// Log.d(TAG, "判断SD卡里是否有此图片，图片的名字：" + fileName);

		File file = new File(cachePath + File.separator);
		if (!file.exists()) {
			System.out.println("——————缓存文件夹不存在——————");
			file.mkdirs();
		}
		if (fileName != null) {
			String filePath = url.startsWith("http://") ? (cachePath
					+ File.separator + fileName) : fileName;
			File f = new File(filePath);

			if (f.exists()) {
				Log.d(TAG, "SD卡里有此图片----------");
				return true;
			} else {
				Log.d(TAG, "SD卡里没有此图片-----------");
			}
		}
		return false;
	}

	/**
	 * 保存Bitmap到指定的目录下
	 * 
	 * @param bitmap
	 *            保存的bitmap
	 * @param url
	 *            图片网络路径
	 * @param cachePath
	 *            本地缓存父路径</br>PathCommonDefines.PHOTOCACHE_FOLDER 程序缓存图片路径;</br>
	 *            PathCommonDefines.MY_FAVOURITE_FOLDER 我的收藏图片路径
	 * @param isJpg
	 *            是否是JPG
	 * @param quality
	 *            缩放比
	 * @return 是否成功
	 */
	public boolean saveBitmapToSDCard(Bitmap bitmap, String url,
			String cachePath, boolean isJpg) {
		File file = null;
		boolean result = false;

		if (bitmap == null) {
			Log.w(TAG, " 保存的图片为空！！！！");
			return false;
		}

		if (FREE_SD_SPACE_NEEDED_TO_CACHE > MiscUtils.freeSpaceOnSd()) {
			Log.w(TAG, "空间小于50MB，不能缓存！！！");
			return false;
		}

		// if (url == null || (url != null && "".equals(url))) {
		// Log.e(TAG, "保存到SD卡时，图片的url为空！！！");
		// return false;
		// }

//		File makeDirectoryPathFile = new File(cachePath);

//		if (!makeDirectoryPathFile.isDirectory()) {
//			makeDirectoryPathFile.mkdirs();
//		}

//		String filename = convertUrlToFileName(url);
//		if (filename == null) {
			file = new File(cachePath);
//		} else {
//
//			file = new File(cachePath + File.separator + filename);
//		}

		// Log.d(TAG, "url:" + url);
		// Log.i(TAG, "fileName:" + filename);
		OutputStream outStream = null;
		ByteArrayOutputStream byteArrayOutputStream = null;
		try {

			file.createNewFile();

			outStream = new FileOutputStream(file);

			// 压缩比例
			int quality = 100;

			// 判断是否是Jpg,png是无损压缩,所以不用进行质量压缩
			if (bitmap != null && isJpg) {

				byteArrayOutputStream = new ByteArrayOutputStream();

				bitmap.compress(Bitmap.CompressFormat.JPEG, quality,
						byteArrayOutputStream);

				// 循环判断如果压缩后图片是否大于100kb,大于继续压缩
				while (byteArrayOutputStream.toByteArray().length / 1024 > 100) {

					// 重置saveBaos即清空saveBaos
					byteArrayOutputStream.reset();

					// 每次都减少10
					quality -= 10;

					// 这里压缩optionsNum%，把压缩后的数据存放到saveBaos中
					bitmap.compress(Bitmap.CompressFormat.JPEG, quality,
							byteArrayOutputStream);

				}

				if (quality <= 0) {

					quality = 100;

				}

				// 输出
				bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outStream);
				Log.w(TAG, "图片保存到了SD卡，格式JPEG！！！");
			} else {

				// 输出
				bitmap.compress(Bitmap.CompressFormat.PNG, quality, outStream);
				Log.w(TAG, "图片保存到了SD卡，压缩成了PNG！！！");
			}

			result = true;

		} catch (IOException e) {

			Log.e(TAG, "保存图片到SD卡:失败=" + e.getMessage(), e);

			result = false;

		} finally {
			if (outStream != null) {
				try {
					outStream.close();
					outStream = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (byteArrayOutputStream != null) {
				try {
					byteArrayOutputStream.close();
					byteArrayOutputStream = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		// 清理缓存
		removeCache(cachePath);

		return result;
	}

	public void copyFile(File sourceFile, File targetFile) {
		try {

			// 新建文件输入流并对它进行缓冲

			FileInputStream input = new FileInputStream(sourceFile);

			BufferedInputStream inBuff = new BufferedInputStream(input);

			// 新建文件输出流并对它进行缓冲

			FileOutputStream output = new FileOutputStream(targetFile);

			BufferedOutputStream outBuff = new BufferedOutputStream(output);

			// 缓冲数组

			byte[] b = new byte[1024 * 5];

			int len;

			while ((len = inBuff.read(b)) != -1) {

				outBuff.write(b, 0, len);

			}

			// 刷新此缓冲的输出流

			outBuff.flush();

			// 关闭流

			inBuff.close();

			outBuff.close();

			output.close();

			input.close();
		} catch (IOException e) {
			// TODO: handle exception
		}

	}

	/**
	 * 将用户选中的图片拷贝到指定的缓存目录
	 * 
	 * @param originalPath
	 *            原始目录
	 * @return 指定的缓存目录
	 */
	// public String saveAvatarTemptoSDCard(String originalPath, int type) {
	// String fileName = "";
	// String tempPath = "";
	// String tempPath_fileName = "";
	// if (originalPath != null && !originalPath.equals("")) {
	//
	// fileName = originalPath
	// .substring(originalPath.lastIndexOf("/") + 1);
	//
	// switch (type) {
	// case OrdinaryCommonDefines.TRIP_ID_FROM_NEWACCOUNT:// 用户头像
	// case OrdinaryCommonDefines.TRIP_ID_FROM_PROFILE_AVATAR:
	// tempPath = PathCommonDefines.USER_AVATAR_FOLDER
	// + File.separator;
	//
	// break;
	// case OrdinaryCommonDefines.TRIP_ID_FROM_PROFILE_CAMERA://
	// ProfileActivity中的camera拍照(不编辑)
	// case OrdinaryCommonDefines.TRIP_ID_FROM_FEATUREDFEED:// 非用户头像
	// case OrdinaryCommonDefines.TRIP_ID_FROM_ILIKE:
	// case OrdinaryCommonDefines.TRIP_ID_FROM_MYTRIPS:
	// case OrdinaryCommonDefines.TRIP_ID_FROM_NEWTRIP:
	// case OrdinaryCommonDefines.TRIP_ID_FROM_PICKCOVER:
	// case OrdinaryCommonDefines.TRIP_ID_FROM_TRIPDETAIL:
	//
	// tempPath = PathCommonDefines.DCIM_FOTO_FOLDER + File.separator;
	// break;
	// default:
	// break;
	// }
	//
	// tempPath_fileName = tempPath + fileName;
	// try {
	//
	// File files = new File(tempPath);
	//
	// File sourceFile = new File(originalPath);
	// File targetFile = new File(tempPath_fileName);
	//
	// files.mkdirs();
	// targetFile.createNewFile();
	//
	// copyFile(sourceFile, targetFile);
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// tempPath_fileName = "";
	// }
	// }
	// return tempPath_fileName;
	// }
	/*** Method to calculate the sample size for the bitmap **/
	public static int computeSampleSize(BitmapFactory.Options options,
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

	/*** Method to calculate the sample size for the bitmap **/
	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	/**
	 * 把图片的url转化成文件名
	 * 
	 * */
	private String convertUrlToFileName(String url) {
		String fn = null;
		if (url != null && url.trim().length() > 0) {
			// .png的图片
			if (url.contains(".png")) {

				fn = String.valueOf(url.hashCode()) + ".png";

			}
			// .jpg的图片
			else {

				fn = String.valueOf(url.hashCode()) + ".jpg";

			}

		}
		return fn;
	}

	/**
	 * 清理一些SD卡缓存的jpg图片
	 * 
	 * @param cachePath
	 *            本地缓存父路径</br>PathCommonDefines.PHOTOCACHE_FOLDER 程序缓存图片路径;</br>
	 *            PathCommonDefines.MY_FAVOURITE_FOLDER 我的收藏图片路径
	 */
	private void removeCache(String cachePath) {
		String dirPath = cachePath;
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files == null) {
			return;
		}
		int dirSize = 0;
		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().contains(WHOLESALE_CONV)) {
				dirSize += files[i].length();
			}
		}
		System.out.println("存储的图片的总量：" + dirSize);
		// 缓存的图片大于30MB小于50MB
		if (dirSize > MAX_CACHE_SIZE_NEEDED * 1024 * 1024
				|| FREE_SD_SPACE_NEEDED_TO_CACHE > MiscUtils.freeSpaceOnSd()) {
			int removeFactor = (int) ((0.4 * files.length) + 1);

			Arrays.sort(files, new FileLastModifSort());

			for (int i = 0; i < removeFactor; i++) {

				if (files[i].getName().contains(WHOLESALE_CONV)) {
					files[i].delete();
					Log.i(TAG, "SD卡的jpg图片清除了一部分！！！ ");
				}
			}
		}
	}

	protected void removeExpiredCache(String dirPath, String filename) {

		File file = new File(dirPath, filename);
		if (System.currentTimeMillis() - file.lastModified() > CACHE_EXPIRE_THRESHOLD) {
			Log.i(TAG, "Clear some expired cache files ");
			file.delete();
		}
	}

	class FileLastModifSort implements Comparator<File> {
		@Override
		public int compare(File arg0, File arg1) {
			if (arg0.lastModified() > arg1.lastModified()) {
				return 1;
			} else if (arg0.lastModified() == arg1.lastModified()) {
				return 0;
			} else {
				return -1;
			}
		}
	}
}
