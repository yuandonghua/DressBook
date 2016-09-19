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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * 
 * TODO 图像工具类
 * 
 * @author LiShen
 * @company Gifted Youngs Workshop
 * @date 2015-10-15 下午5:34:32
 * @since
 * @version
 */
public class ImageUtils2 {
	public static final int PNG = 3;
	public static final int JPEG = 4;

	/**
	 * 
	 * TODO 压缩bitmap至指定的分辨率宽度
	 * 
	 * @author Lishen
	 * @date 2015-7-9 下午5:18:10
	 * @param bitmap
	 * @param newWidth
	 * @return
	 * @see
	 */
	public static Bitmap resizeBitmap(Bitmap bitmap, int newWidth) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float temp = ((float) height) / ((float) width);
		int newHeight = (int) ((newWidth) * temp);
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		bitmap.recycle();
		return resizedBitmap;
	}

	/**
	 * 
	 * TODO 将bitmap保存至指定路径
	 * 
	 * @author LiShen
	 * @date 2015-10-18 上午11:39:28
	 * @param path
	 *            路径
	 * @param bm
	 *            bitmap
	 * @param type
	 *            类型,jpeg,png
	 * @param quality
	 *            质量
	 * @see
	 */
	public static void saveBitmap(String path, Bitmap bm, int type, int quality) {
		File f = new File(path);
		if (f.exists()) {
			f.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(f);
			// 格式
			switch (type) {
			case PNG:
				if (bm != null) {
					bm.compress(Bitmap.CompressFormat.PNG, quality, out);
				}
				break;
			case JPEG:
				if (bm != null) {
					bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
				}
				break;
			}
			if (out != null) {

				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bm != null) {
				bm.recycle();
			}
		}
	}
}
