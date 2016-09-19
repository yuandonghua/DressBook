package cn.dressbook.ui.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.xutils.common.util.LogUtil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

/**
 * @description 图片处理
 * @author 袁东华
 * @time 2015-12-17下午1:59:36
 */
public class ImageUtils {

	public static boolean isImageFile(String filename) {
		String s = filename.toLowerCase();
		return (s.endsWith(".0") || s.endsWith(".jpeg") || s.endsWith(".jpg")
				|| s.endsWith(".gif") || s.endsWith(".bmp") || s
					.endsWith(".png"));
	}
	/**
	 * 图片缩放处理
	 * @param fileName
	 * @param fileName2
	 * @return
	 */
	public static String dealImage(String fileName, String fileName2) {
		// TODO Auto-generated method stub
		try {
			Bitmap bitmap = BitmapTool.getInstance().createImage(fileName);
			if (bitmap != null) {
				int width = bitmap.getWidth();
				int height = bitmap.getHeight();
				if (width > 800) {
					float scale = 800 / (float) width;
					Matrix matrix = new Matrix();
					matrix.postScale(scale, scale); // 长和宽放大缩小的比例
					Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, width,
							height, matrix, true);
					File file = new File(fileName2);
					if (file.exists()) {
						file.delete();
					}
					File file2 = new File(fileName2);
					FileOutputStream out = new FileOutputStream(file2);
					if (bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
						if (bitmap != null) {
							bitmap.recycle();
						}
						if (bitmap2 != null) {
							bitmap2.recycle();
						}
						out.flush();
						out.close();
						return fileName2;
					} else {
					}
				} else {
					bitmap.recycle();
					return fileName;
				}

			} else {

			}
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.e(e.getMessage());
		}
		return null;
	}

	/**
	 * 返回文件大小单位K
	 * 
	 * @param strSize
	 * @return
	 */
	public static String Format_Size(String strSize) {
		String s;
		int L = strSize.length();
		if (L < 4)
			s = "0." + strSize.substring(0, 1) + "k";
		else if (L > 6)
			s = strSize.substring(0, L - 6) + "." + strSize.substring(6, 7)
					+ "M";
		else if (L == 4)
			s = strSize.substring(0, 1) + "." + strSize.substring(1, 2) + "k";
		else
			s = strSize.substring(0, L - 3) + "k";
		return (s);
	}

	/**
	 * 加载超大图片文件的方法
	 * 
	 * @param dst
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap getBitmapFromFile(File dst, int width, int height) {
		if (null != dst && dst.exists()) {
			BitmapFactory.Options opts = null;
			if (width > 0 && height > 0) {
				opts = new BitmapFactory.Options();
				opts.inJustDecodeBounds = true;
				BitmapFactory.decodeFile(dst.getPath(), opts);
				// 计算图片缩放比例
				float out_pix = opts.outWidth * opts.outHeight;
				float max_pix = width * height;
				float scale = out_pix / max_pix;
				//
				// float scaleWidth = ((float) opts.outWidth /width );
				// float scaleHeight = ((float)opts.outHeight /height );
				// float scale = scaleHeight;
				// if(scaleWidth>scaleHeight)
				// scale = scaleWidth;

				// scale += 0.7;
				opts.inSampleSize = (int) (scale);
				if (opts.inSampleSize == 0)
					opts.inSampleSize = 1;

				opts.inJustDecodeBounds = false;
				opts.inInputShareable = true;
				opts.inPurgeable = true;
			}
			try {
				return BitmapFactory.decodeFile(dst.getPath(), opts);
			} catch (OutOfMemoryError e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 调整图片角度
	 * 
	 * @param degree
	 */
	public static Bitmap toMatrix(Bitmap bmp, float degree, float scale,
			int reverseflag) {
		Bitmap bm = null;
		try {

			Matrix matrix = new Matrix();
			matrix.postRotate(degree);

			if (reverseflag == 0)
				matrix.postScale(scale, scale);
			else if (reverseflag == 1)
				matrix.postScale(-scale, scale); // 水平反转
			else if (reverseflag == 2)
				matrix.postScale(scale, -scale); // 垂直反转
			else if (reverseflag == 3)
				matrix.postScale(-scale, -scale); // 水平、垂直反转

			int width = bmp.getWidth();
			int height = bmp.getHeight();
			bm = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, true);
		} catch (Exception e) {
			if (bmp != null && !bmp.isRecycled()) {
				bmp.isRecycled();
			}
			if (bm != null && !bm.isRecycled()) {
				bm.isRecycled();
			}
		}
		return bm;
	}

	/**
	 * 把Bitmap转Byte
	 * 
	 * @Author HEH
	 * @EditTime 2010-07-19 上午11:45:56
	 */
	public byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 90, baos);
		return baos.toByteArray();
	}

	/**
	 * 把字节数组保存为一个文件
	 */
	public boolean SaveFile(File file, byte[] b) {
		return SaveFile(file, b, 0, b.length);
	}

	/**
	 * 把字节数组保存为一个文件
	 */
	public boolean SaveFile(File file, byte[] b, int offset, int length) {
		boolean ret = false;
		BufferedOutputStream stream = null;
		try {
			FileOutputStream fstream = new FileOutputStream(file);
			stream = new BufferedOutputStream(fstream);
			stream.write(b, offset, length);
			ret = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return ret;
	}
}
