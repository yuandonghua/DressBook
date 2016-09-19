package cn.dressbook.ui.SnowCommon.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import cn.dressbook.ui.SnowCommon.common.RuntimeCache;

public class ImageUtil {

	/**
	 * @description:调整图片角度
	 * @exception
	 */
	public static String handleImageOrientation(String imagePath) {

		ExifInterface exif = null;
		try {
			exif = new ExifInterface(imagePath);
		} catch (IOException e) {
			e.printStackTrace();
			exif = null;
		}

		int degree = 0;
		if (exif != null) {
			int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_UNDEFINED);
			switch (ori) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			default:
				degree = 0;
				break;
			}
		}
		if (degree != 0) {
			// 旋转图片

			Bitmap bmp = NativeImageLoader.decodeProperBitmapForCrop(imagePath,
					800);

			Matrix m = new Matrix();
			m.postRotate(degree);
			Bitmap resultBitmap = Bitmap.createBitmap(bmp, 0, 0,
					bmp.getWidth(), bmp.getHeight(), m, true);

			File original = new File(imagePath);

			String path1 = RuntimeCache.getCurrentInternalCropImageFolder()
					+ File.separator + original.getName();
			try {
				FileOutputStream out1 = new FileOutputStream(path1);
				resultBitmap.compress(CompressFormat.JPEG, 100, out1);
				out1.flush();
				out1.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			resultBitmap.recycle();
			resultBitmap = null;
			bmp.recycle();
			bmp = null;
			return path1;
		} else {
			return imagePath;
		}
	}

	@SuppressLint("NewApi")
	public static String getPathFromUri(Uri uri, ContentResolver contentResolver) {

		String imgPath = null;

		if (Build.VERSION.SDK_INT < 19) {

			Cursor cursor = contentResolver.query(uri, null, null, null, null);
			cursor.moveToFirst();
			imgPath = cursor.getString(1);

			cursor.close();

		} else {

			Cursor cursor = null;

			try {
				String wholeID = DocumentsContract.getDocumentId(uri);
				// Split at colon, use second item in the array
				String id = wholeID.split(":")[1];

				String[] column = { MediaStore.Images.Media.DATA };

				// where id is equal to
				String sel = MediaStore.Images.Media._ID + "=?";

				cursor = contentResolver.query(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column,
						sel, new String[] { id }, null);

				int columnIndex = cursor.getColumnIndex(column[0]);

				if (cursor.moveToFirst()) {
					imgPath = cursor.getString(columnIndex);
				}
				cursor.close();
			} catch (Throwable t) {

				Cursor cursor2 = contentResolver.query(uri, null, null, null,
						null);
				try {
					cursor2.moveToFirst();
					imgPath = cursor2.getString(1);
				} finally {
					if (cursor2 != null)
						cursor2.close();
				}
			} finally {

				if (cursor != null)
					cursor.close();
			}
		}

		return imgPath;
	}

	public static boolean scaleSquareImage(String imagePath, int maxSize) {

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;

		BitmapFactory.decodeFile(imagePath, options);

		int bitmapWidth = options.outWidth;
		int bitmapHeight = options.outHeight;

		if (bitmapWidth == bitmapHeight) {

			if (bitmapWidth > maxSize) {
				int scale = Math.round((float) bitmapWidth / (float) maxSize);
				options.inSampleSize = scale;

				options.inJustDecodeBounds = false;

				Bitmap bmp = BitmapFactory.decodeFile(imagePath, options);
				try {
					FileOutputStream out1 = new FileOutputStream(imagePath);
					bmp.compress(CompressFormat.JPEG, 100, out1);
					out1.flush();
					out1.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

				bmp.recycle();
				bmp = null;

			}
			return true;
		}

		else
			return false;

	}

}
