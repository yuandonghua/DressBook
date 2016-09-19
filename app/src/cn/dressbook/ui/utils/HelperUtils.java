package cn.dressbook.ui.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import cn.dressbook.ui.R;
import cn.dressbook.ui.common.PathCommonDefines;

public class HelperUtils {
	private static final String TAG = HelperUtils.class.getSimpleName();
	public static final int SINA_SHARE_SUCCESS = 10;
	public static final int SINA_SHARE_ERROR = 11;
	public static final int SINA_SHARE_EXCEPTION = 12;
	public static final int TENCENT_SHARE_SUCCESS = 13;
	public static final int TENCENT_SHARE_ERROR = 14;
	public static final int QZONE_SHARE_SUCCESS = 18;
	public static final int QZONE_SHARE_ERROR = 19;
	public static final int TENCENT_SHARE_EXCEPTION = 15;
	public static final int FAVORITE_IMAGE_SUCCESS = 16;
	public static final int FAVORITE_IMAGE_FAILE = 17;
	private SharedPreferenceUtils mSharedPreferenceUtils = SharedPreferenceUtils
			.getInstance();
	private static HelperUtils mInstance = null;
	// 最后点击时间
	private static long lastClickTime;

	// 体验一下 true为点击体验一下按钮
	private boolean flag;

	public static HelperUtils getInstance() {
		if (mInstance == null) {
			mInstance = new HelperUtils();
		}
		return mInstance;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	/**
	 * 格式化文件路径(用于辨别是否是路径)
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 格式化后的文件路径
	 */
	public static String enCodeFilePath(String filePath) {
		filePath = "file:" + filePath;
		return filePath;
	}

	/**
	 * px to sp
	 * 
	 * @param pxValue
	 * @param fontScale
	 * @return
	 */
	public static int px2sp(float pxValue, Context context) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 格式化文件路径(用于辨别是否是路径)
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 格式化后的文件路径
	 */
	public static String unEnCodeFilePath(String filePath) {
		if (filePath != null) {
			filePath = filePath.replace("file:", "");
		}
		return filePath;
	}

	/**
	 * 判断是否是文件路径
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean isFilePath(String filePath) {
		if (filePath != null) {
			if (filePath.contains("file:")) {
				return true;
			}
		}

		return false;
	}

	// 判断注册邮箱是否填写规范
	public static boolean emailFormat(String email) {
		boolean tag = true;
		final String pattern1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		final Pattern pattern = Pattern.compile(pattern1);
		final Matcher mat = pattern.matcher(email);
		if (!mat.find()) {
			tag = false;
		}
		return tag;
	}

	/**
	 * 检查是否存在SDCard
	 * 
	 * @return
	 */
	public static boolean hasSdcard() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/***
	 * 对话框
	 */
	public static void dialogMessage(Context context, String message,
			OnClickListener sureListener, OnClickListener negativeListener) {
		Builder builder = new Builder(context);
		builder.setTitle("提示:");
		builder.setMessage(message);
		builder.setIcon(R.drawable.logo6);
		if (null != sureListener) {
			builder.setPositiveButton("确定", sureListener);
		}
		if (null != negativeListener) {
			builder.setCancelable(true);
			builder.setNegativeButton("取消", negativeListener);
		}
		builder.show();
	}

	/**
	 * 设置白天和夜间模式
	 * 
	 * @param window
	 *            当前的Window窗体
	 * @param isNightModel
	 *            是否是夜间模式
	 */
	public void setScreenBrightness(Window window, boolean isNightModel) {

		WindowManager.LayoutParams lp = window.getAttributes();

		if (isNightModel) {

			lp.screenBrightness = 0.4f;

		} else {

			lp.screenBrightness = 1.0f;

		}

		window.setAttributes(lp);

	}

	/**
	 * 显示提示(String)
	 * 
	 * @param context
	 * @param msg
	 */
	public void showToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 显示提示(resource id)
	 * 
	 * @param context
	 * @param msg
	 */
	public void showToast(Context context, int msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 跳转Activity
	 * 
	 * @param context
	 * @param to
	 */
	public static void jump(Context context, Class to) {
		Intent intent = new Intent(context, to);
		context.startActivity(intent);
	}

	/**
	 * 验证字符串是否为空 或 ""
	 * 
	 * @param str
	 * @return
	 */
	public static boolean validateString(String str) {
		if (str != null && !"".equals(str.trim()))
			return false;

		return true;
	}

	/**
	 * 得到版本号
	 * 
	 * @return
	 */
	public static String getVersionName(Context context) {
		PackageInfo pinfo;
		String versionName = "";
		try {
			pinfo = context.getPackageManager().getPackageInfo(
					context.getPackageName(),
					android.content.pm.PackageManager.GET_CONFIGURATIONS);
			versionName = pinfo.versionName;

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return versionName;
	}

	/**
	 * 获取APP的版本号
	 * 
	 * @return
	 */
	public static int getVersionCode(Context context) {
		PackageInfo pinfo;
		int versionCode = 0;
		try {
			pinfo = context.getPackageManager().getPackageInfo(
					context.getPackageName(),
					android.content.pm.PackageManager.GET_CONFIGURATIONS);
			versionCode = pinfo.versionCode;

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return versionCode;
	}

	/**
	 * 获得渠道号(推广平台0=本站,1=安智,2=机锋,3=安卓官方)
	 * 
	 * @return
	 */
	public static String getChannelCode(Context context) {
		String channelCode = "0";
		try {
			ApplicationInfo ai = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							android.content.pm.PackageManager.GET_META_DATA);
			Bundle bundle = ai.metaData;
			if (bundle != null) {

				Object obj = bundle.get("UMENG_CHANNEL");
				if (obj != null) {
					channelCode = obj.toString();
				}
				Log.d(TAG, "channelCode:" + channelCode + " obj:" + obj);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return channelCode;
	}

	/***
	 * 检查网络是否可用 true连接
	 */
	public static boolean isConnect(Context context) {
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// 获取网络连接管理的对象
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					// 判断当前网络是否已经连接
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			Log.v("error", e.toString());
		}
		return false;
	}

	/***
	 * 检查网络是否连接上wifi
	 */
	public static boolean isConnectWIFI(Context context) {
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// 获取网络连接管理的对象
				NetworkInfo info = connectivity
						.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
				if (info != null && info.isConnected()) {
					// 判断当前网络是否已经连接wifi
					return true;
				}
			}
		} catch (Exception e) {
		}
		return false;
	}

	// 这个函数会对图片的大小进行判断，并得到合适的缩放比例，比如2即1/2,3即1/3
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

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 80 : (int) Math.min(
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
	 * 根据图片名称删除图片
	 * 
	 * @param fileName
	 *            文件名
	 */
	public void deletePictureByFilePath(final Context context,
			final Handler handler, final String url) {

		new Thread() {
			@Override
			public void run() {
				if (url != null && !url.equals("")) {
					// url转换HashCode
					String urlHashCode = convertUrlToFileName(url);

					// PictureSDUtil myPictureSDUtil = new
					// PictureSDUtil(context);
					File file = new File(PathCommonDefines.MY_FAVOURITE_FOLDER,
							urlHashCode + ".png");
					if (file != null) {
						file.delete();
					}
					Message message = new Message();
					message.what = 2;
					handler.sendMessage(message);

				}
			}
		}.start();
	}

	/**
	 * 根据图片名称拷贝图片
	 * 
	 * @param fileName
	 *            文件名
	 */
	public void copyPictureByFilePath(final Handler handler, final String url) {

		new Thread() {
			@Override
			public void run() {
				try {

					if (url != null && !url.equals("")) {

						// url转换HashCode
						String urlHashCode = convertUrlToFileName(url);

						copyFile(urlHashCode, urlHashCode);

						Message message = new Message();
						message.what = 3;
						handler.sendMessage(message);

					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}.start();
	}

	/**
	 * 文件的复制(从一个文件夹复制到新的文件夹)
	 * 
	 * @param sourceFile
	 *            源文件
	 * @param targetFile
	 *            目标文件
	 */
	public void copyFile(String filename, String urlHashCode)
			throws IOException {

		File myFavouriteFolder = new File(PathCommonDefines.MY_FAVOURITE_FOLDER);
		if (!myFavouriteFolder.exists()) {
			myFavouriteFolder.mkdirs();
		}

		// 新建文件输入流并对它进行缓冲
		FileInputStream input = new FileInputStream(
				PathCommonDefines.PHOTOCACHE_FOLDER + File.separator
						+ urlHashCode);
		BufferedInputStream inBuff = new BufferedInputStream(input);

		// 新建文件输出流并对它进行缓冲
		FileOutputStream output = new FileOutputStream(
				PathCommonDefines.MY_FAVOURITE_FOLDER + File.separator
						+ filename);
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
	}

	/**
	 * 将URL转换成HashCode
	 * 
	 * @param url
	 * @return
	 */
	public String convertUrlToFileName(String url) {
		String fn = null;
		if (url != null && url.trim().length() > 0) {
			if (url.contains(".png")) {

				fn = String.valueOf(url.hashCode()) + ".png";

			} else {

				fn = String.valueOf(url.hashCode()) + ".jpg";

			}
		}
		return fn;
	}

	/**
	 * 以md5方式加密字符串
	 * 
	 * @param content
	 *            字符串
	 * @param length
	 *            返回的长度,支持16位和32位,例length=16,length=32
	 * @return 返回加密后的字符串
	 */
	public static String getMd5(String content, int length) {
		try {
			MessageDigest bmd5 = MessageDigest.getInstance("MD5");
			bmd5.update(content.getBytes());
			int i;
			StringBuffer buf = new StringBuffer();
			byte[] b = bmd5.digest();
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			String md5Content = buf.toString();
			switch (length) {
			case 16:
				md5Content = md5Content.substring(0, 16);
				break;
			case 32:
			default:
				break;
			}
			return md5Content;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	public ByteArrayOutputStream getByteArrayOutputStreamByInputStream(
			InputStream inputStream) throws Exception {

		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			byteArrayOutputStream.write(buffer, 0, len);
		}
		return byteArrayOutputStream;
	}

	/**
	 * 判断是否重复点击
	 */
	public boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 1000) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

}
