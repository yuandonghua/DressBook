/*
 * Copyright by Gifted Youngs Workshop and the original author or authors.
 * 
 * This document only allow internal use , any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Gifted Youngs Workshop from
 * 
 * giftedyoungs@163.com
 * 
 *
 * 此代码由天才少年工作小组开发完成, 仅限内部使用 
 * 外部使用该代码将负相应的法律责任
 * 更多信息请致信天才少年工作小组
 * 
 * giftedyoungs@163.com
 *
 */
package cn.dressbook.ui.utils;

import java.io.File;
import java.math.BigDecimal;

import android.os.Environment;
import android.text.TextUtils;

/**
 * 
 * TODO
 * 
 * @author LiShen
 * @company Gifted Youngs Workshop
 * @date 2015-10-15 下午5:33:30
 * @since
 * @version
 */
public class FileUtils {
	/**
	 * TODO sd卡根目录名字
	 */
	public static final String FOLDER_NAME_ROOT = "/Meitan";
	/**
	 * TODO 图片缓存的文件夹
	 */
	public static final String FOLDER_NAME_IMAGE_CACHE = "/ImageCache/";
	/**
	 * TODO 保存待上传图片的地址
	 */
	public static final String FOLDER_NAME_WAIT_UPLOAD_PHOTO = "/PhotoUpload/";
	/**
	 * TODO 临时保存拍摄的照片的地址
	 */
	public static final String FOLDER_NAME_PHOTO_SHOT = "/PhotoShot/";

	/**
	 * 
	 * TODO 获取在手机sd卡根目录里的目录
	 * 
	 * @author LiShen
	 * @date 2015-7-9 下午4:07:16
	 * @return
	 * @see
	 */
	public static String getAppRootFolder() {
		String uri = "";
		try {
			uri = Environment.getExternalStorageDirectory().getPath()
					+ FOLDER_NAME_ROOT;
			File file = new File(uri);
			if (!file.exists()) {
				file.mkdir();
			}
		} catch (Exception e) {
			// TODO
			e.printStackTrace();
		}
		return uri;
	}

	/**
	 * 
	 * TODO 获取图片缓存路径
	 * 
	 * @author LiShen
	 * @date 2015-8-4 下午1:27:02
	 * @return
	 * @see
	 */
	public static String getImageCachedFolder() {
		String uri = "";
		try {
			uri = getAppRootFolder() + FOLDER_NAME_IMAGE_CACHE;
			File file = new File(uri);
			if (!file.exists()) {
				file.mkdir();
			}
		} catch (Exception e) {
			// TODO
			e.printStackTrace();
		}
		return uri;
	}

	/**
	 * 
	 * TODO 临时保存拍摄的照片的地址
	 * 
	 * @author LiShen
	 * @date 2015-10-17 上午10:47:11
	 * @return
	 * @see
	 */
	public static String getPhotoShotFolder() {
		String uri = "";
		try {
			uri = getAppRootFolder() + FOLDER_NAME_PHOTO_SHOT;
			File file = new File(uri);
			if (!file.exists()) {
				file.mkdir();
			}
		} catch (Exception e) {
			// TODO
			e.printStackTrace();
		}
		return uri;
	}

	/**
	 * 
	 * TODO 获取待上传的图片文件夹
	 * 
	 * @author LiShen
	 * @date 2015-10-18 上午10:16:19
	 * @return
	 * @see
	 */
	public static String getPhotoUploadFolder() {
		String uri = "";
		try {
			uri = getAppRootFolder() + FOLDER_NAME_WAIT_UPLOAD_PHOTO;
			File file = new File(uri);
			if (!file.exists()) {
				file.mkdir();
			}
		} catch (Exception e) {
			// TODO
			e.printStackTrace();
		}
		return uri;
	}

	/**
	 * 
	 * TODO 获取文件夹所有内容大小
	 * 
	 * @author LiShen
	 * @date 2015-8-24 下午2:39:17
	 * @param file
	 * @return
	 * @see
	 */
	public static long getFolderSize(File file) {
		long size = 0;
		try {
			File[] fileList = file.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				if (fileList[i].isDirectory()) {
					size = size + getFolderSize(fileList[i]);

				} else {
					size = size + fileList[i].length();

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}

	/**
	 * 
	 * TODO 删除指定路径文件和文件夹
	 * 
	 * @author LiShen
	 * @date 2015-8-24 下午2:39:41
	 * @param filePath
	 *            路径
	 * @param deleteThisPath
	 *            是否删除这个文件夹
	 * @see
	 */
	public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
		if (!TextUtils.isEmpty(filePath)) {
			try {
				File file = new File(filePath);
				if (file.isDirectory()) {
					// 处理目录
					File files[] = file.listFiles();
					for (int i = 0; i < files.length; i++) {
						deleteFolderFile(files[i].getAbsolutePath(), true);
					}
				}
				if (deleteThisPath) {
					if (!file.isDirectory()) {
						// 如果是文件，删除
						file.delete();
					} else {
						// 如果是目录
						if (file.listFiles().length == 0) {
							// 目录下没有文件或者目录，删除
							file.delete();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * TODO 格式化大小单位
	 * 
	 * @author LiShen
	 * @date 2015-8-24 下午2:40:26
	 * @param size
	 * @return
	 * @see
	 */
	public static String getFormatSize(double size) {
		double kiloByte = size / 1024;
		if (kiloByte < 1) {
			return (int) size + "KB";
		}

		double megaByte = kiloByte / 1024;
		if (megaByte < 1) {
			BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
			return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "KB";
		}

		double gigaByte = megaByte / 1024;
		if (gigaByte < 1) {
			BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
			return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "MB";
		}

		double teraBytes = gigaByte / 1024;
		if (teraBytes < 1) {
			BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
			return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "GB";
		}
		BigDecimal result4 = new BigDecimal(teraBytes);
		return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
				+ "TB";
	}
}
