package cn.dressbook.ui.SnowCommon.common.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.util.Log;
import cn.dressbook.ui.SnowCommon.common.AppImage;
import cn.dressbook.ui.SnowCommon.common.AppImageComparator;
import cn.dressbook.ui.SnowCommon.common.FileComparator;
import cn.dressbook.ui.SnowCommon.common.RuntimeCache;

public class MediaHelper {

	public static void copyImage(String dir, int imageNumber) {
		Bitmap srcImage = BitmapFactory.decodeFile(dir + File.separator
				+ "IMG_" + imageNumber + ".jpg");
		MediaHelper.saveResult(dir, imageNumber,
				Bitmap.createBitmap(srcImage, 10, 15, 300, 450));
		srcImage.recycle();
		srcImage = null;
	}

	public static String copyImagesToResult(String dir, int start, int end) {
		int count = 0;
		File resultFolder = new File(dir + File.separator + "result");
		String resultPath = resultFolder.getPath();
		if (!resultFolder.exists()) {
			if (!resultFolder.mkdirs()) {
				return null;
			}
		}
		for (int i = start; i <= end; i++) {
			String oldPath = dir + File.separator + "IMG_" + i + ".jpg";
			String newPath = dir + File.separator + "result" + File.separator
					+ "IMG_" + count + ".jpg";
			copyFile(oldPath, newPath);
			count++;
		}
		return resultPath;
	}

	public static void copyFile(String oldPath, String newPath) {
		try {
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { //

				InputStream inStream = new FileInputStream(oldPath); // ���������������
				FileOutputStream fs = new FileOutputStream(newPath);

				try {
					byte[] buffer = new byte[1024];
					while ((byteread = inStream.read(buffer)) != -1) {
						fs.write(buffer, 0, byteread);
					}

					fs.flush();
				} finally {

					if (fs != null)
						fs.close();
					if (inStream != null)
						inStream.close();
				}
			

			}
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();

		}

	}

	public static AppImage getCutBitmapByCount(String dir, int count) {
		AppImage result = new AppImage();
		String filePath = dir + File.separator + "IMG_" + count + ".jpg";
		Bitmap image = BitmapFactory.decodeFile(filePath);
		Bitmap smallImage = Bitmap.createBitmap(image, 10, 15, 300, 450);
		image.recycle();
		image = null;
		result.setBitmap(smallImage);
		result.setFileName("IMG_" + count + ".jpg");
		return result;
	}

	public static AppImage getBitmapByCount(String dir, int count) {
		AppImage result = new AppImage();
		String filePath = dir + File.separator + "IMG_" + count + ".jpg";
		Bitmap image = BitmapFactory.decodeFile(filePath);
		result.setBitmap(image);
		result.setFileName("IMG_" + count + ".jpg");
		return result;
	}

	public static Bitmap getBitmapByName(String dir, String fileName) {
		String filePath = dir + File.separator + fileName;
		Bitmap image = BitmapFactory.decodeFile(filePath);
		return image;
	}
	
	public static Bitmap getResultIconByPath(String path) {
		if (path != null) {
			Options opts = new Options();
			opts.inSampleSize = 5;
			Bitmap value = BitmapFactory.decodeFile(path);
			return value;
		}
		return null;
	}

	public static Bitmap getFirstBitmap(String dir) {
		String filePath = dir + File.separator + "IMG_" + 0 + ".jpg";
		Bitmap image = BitmapFactory.decodeFile(filePath);
		return image;
	}

	public static boolean copyFirst(String srcDir, String targetDir) {
		String filePath = srcDir + File.separator + "IMG_" + 0 + ".jpg";
		Bitmap image = BitmapFactory.decodeFile(filePath);
		String targetPath = targetDir + File.separator + "IMG_" + 0 + ".jpg";
		File resultFile = new File(targetPath);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(resultFile);
			image.compress(CompressFormat.JPEG, 100, fos);
			image.recycle();
			image = null;
			fos.flush();
		} catch (Exception e) {
			return false;
		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return true;
	}

	public static void saveBitmap(String dir, int number, Bitmap result) {
		File resultDir = new File(dir);
		if (!resultDir.exists()) {
			if (!resultDir.mkdirs()) {
				Log.d("SnowCam", "fail to create directory" + dir);
			}
		}
		String filePath = dir + File.separator + "IMG_" + number + ".jpg";
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			result.compress(CompressFormat.JPEG, 100, fos);
			fos.flush();
		} catch (Exception e) {

		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

	}

	public static boolean saveResult(String dir, int count, Bitmap result) {
		String parentDir = dir + File.separator + "result";
		File resultDir = new File(parentDir);
		if (!resultDir.exists()) {
			if (!resultDir.mkdirs()) {
				Log.d("SnowCam", "fail to create directory" + parentDir);
			}
		}
		String filePath = parentDir + File.separator + "IMG_" + count + ".jpg";
		File resultFile = new File(filePath);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(resultFile);
			result.compress(CompressFormat.JPEG, 100, fos);
			result.recycle();
			result = null;
			fos.flush();
		} catch (Exception e) {
			return false;
		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return true;
	}

	public static Bitmap getSrcBitmap(String dir, int count) {
		String filePath = dir + File.separator + "IMG_" + count + ".jpg";
		Bitmap image = BitmapFactory.decodeFile(filePath);
		return image;
	}

	public static List<BitmapDrawable> getAnimations(Resources resource,
			String dir) {
		List<BitmapDrawable> result = new ArrayList<BitmapDrawable>();
		File album = new File(dir);
		File[] images = album.listFiles();
		for (int j = 0; j < images.length; j++) {

			Bitmap image = BitmapFactory.decodeFile(images[j].getPath());
			BitmapDrawable drawable = new BitmapDrawable(resource, image);
			result.add(drawable);
		}
		return result;
	}

	public static AppImage getFirstImageByName(String dir) {
		File dirFile = new File(dir);
		File[] images = dirFile.listFiles();
		List<File> fileList = Arrays.asList(images);

		fileList = sortFilesByName(fileList);
		AppImage result = new AppImage();

		for (int i = 0; i < fileList.size(); i++) {
			if (fileList.get(i).getName().endsWith(".jpg")) {
				Bitmap image = BitmapFactory.decodeFile(fileList.get(i)
						.getPath());
				result.setBitmap(image);
				result.setFileName(fileList.get(i).getName());
				break;
			}
		}

		return result;
	}

	public static File getFirstImage(File album) {
		File[] images = album.listFiles();
		File resultFile = null;
		for (int j = 0; j < images.length; j++) {
			File image = images[j];
			if (!image.isDirectory()) {
				if (resultFile == null)
					resultFile = image;
				else if (image.lastModified() < resultFile.lastModified())
					resultFile = image;
			}
		}
		return resultFile;
	}

	public static Bitmap getBitmap(String fileName) {
		Bitmap bitmap = BitmapFactory.decodeFile(getAppMediaDir(fileName));
		return bitmap;
	}

	public static String getAppMediaDir(String fileName) {
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"SnowCam");

		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("SnowCam", "fail to create directory");
				return null;
			}
		}
		String filePath = mediaStorageDir + File.separator + fileName;
		File resultFile = new File(filePath);
		if (!resultFile.exists())
			if (!resultFile.mkdirs()) {
				Log.d("SnowCam", "fail to create directory");
				return null;
			}
		return filePath;
	}

	public static int dip2px(Context context, float dpValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static AppImage getStartImageByDir(String imageDir, int number) {
		AppImage appImage = new AppImage();
		String filePath = imageDir + File.separator + "IMG_" + number + ".jpg";
		File imageFile = new File(filePath);
		if (imageFile.exists()) {
			Bitmap image = BitmapFactory.decodeFile(filePath);
			Bitmap smallImage = Bitmap.createBitmap(image, 10, 15, 300, 450);
			image.recycle();
			image = null;
			appImage.setBitmap(smallImage);
			appImage.setFileName(imageFile.getName());
		}
		return appImage;
	}

	public static List<AppImage> getFrameIconByDir(String imageDir) {
		List<AppImage> resultIcons = new ArrayList<AppImage>();
		Options opt = new Options();
		opt.inSampleSize = 10;
		File dirFile = new File(imageDir);
		File[] images = dirFile.listFiles();
		for (int j = 0; j < images.length; j++) {
			File image = images[j];
			if (!image.isDirectory()) {
				AppImage iconImage = new AppImage();
				Bitmap bitmap = BitmapFactory.decodeFile(image.getPath(), opt);
				iconImage.setBitmap(bitmap);
				iconImage.setFileName(image.getName());
				resultIcons.add(iconImage);
			}
		}
		return sortAppImages(resultIcons);
	}

	public static List<AppImage> getAllImagesByDir(String imageDir) {
		List<AppImage> resultIcons = new ArrayList<AppImage>();

		File dirFile = new File(imageDir);
		if (!dirFile.exists())
			return resultIcons;
		File[] images = dirFile.listFiles();
		for (int j = 0; j < images.length; j++) {
			File image = images[j];
			if (!image.isDirectory()) {
				AppImage iconImage = new AppImage();
				Bitmap bitmap = BitmapFactory.decodeFile(image.getPath());
				iconImage.setBitmap(bitmap);
				iconImage.setFileName(image.getName());
				resultIcons.add(iconImage);
			}
		}
		return sortAppImages(resultIcons);
	}

	public static List<AppImage> sortAppImages(List<AppImage> list) {
		AppImageComparator comparator = new AppImageComparator();
		Collections.sort(list, comparator);
		return list;
	}

	public static List<File> sortFilesByName(List<File> list) {
		FileComparator comparator = new FileComparator();
		Collections.sort(list, comparator);
		return list;
	}

	public static void saveFilterImage(String imageDir, Bitmap image,
			String fileName, int type) {
		String filterDir = null;
		if (type == Constants.FILTER_NAGETIVE) {
			filterDir = imageDir + File.separator + "nagetive";
		} else if (type == Constants.FILTER_REMEMBER) {
			filterDir = imageDir + File.separator + "remember";
		} else if (type == Constants.FILTER_RELIEF) {
			filterDir = imageDir + File.separator + "relief";
		} else if (type == Constants.FILTER_BRIGHT) {
			filterDir = imageDir + File.separator + "bright";
		} else if (type == Constants.FILTER_DARK) {
			filterDir = imageDir + File.separator + "dark";
		} else if (type == Constants.FILTER_FUZZY) {
			filterDir = imageDir + File.separator + "fuzzy";
		} else if (type == Constants.FILTER_SHARPEN) {
			filterDir = imageDir + File.separator + "sharpen";
		}
		File resultDir = new File(filterDir);
		if (!resultDir.exists()) {
			if (!resultDir.mkdirs()) {
				Log.d("SnowCam", "fail to create directory" + filterDir);
			}
		}
		String filePath = filterDir + File.separator + fileName;
		File resultFile = new File(filePath);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(resultFile);
			image.compress(CompressFormat.JPEG, 100, fos);
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	public static void deleteFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteFile(files[i].getPath());
				}
				file.delete();
			} else
				file.delete();
		}
	}

	public static void deleteSubFiles(File file){
		File[] files = file.listFiles();
		if(files!=null){
		for (int i = 0; i < files.length; i++) {
			deleteFile(files[i].getPath());
		}
		}
	}

	public static String getImageFromVideo(String path, int doration,
			int totalCount) {

		// TOO: check
		String dir = RuntimeCache.getCurrentInternalImageFolder();
		MediaMetadataRetriever mRetriever = new MediaMetadataRetriever();
		mRetriever.setDataSource(path);
		String time = mRetriever
				.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
		long totalTime = Long.valueOf(time);
		int count = 0;
		long frameTime = 0;
		while (count < totalCount && frameTime < totalTime) {
			frameTime = frameTime * 1000;
			Bitmap bitmap = mRetriever.getFrameAtTime(frameTime);
			MediaHelper.saveBitmap(dir, count, bitmap);
			bitmap.recycle();
			bitmap = null;
			count++;
			frameTime = count * doration;
		}
		mRetriever.release();
		mRetriever = null;
		return dir;
	}

	public static Bitmap resizeBitmap(Bitmap bitmap, float scale) {

		if (bitmap != null) {
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			Matrix matrix = new Matrix();
			matrix.postScale(scale, scale);
			Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width,
					height, matrix, true);
			return resizedBitmap;
		} else {
			return null;
		}
	}

	public static Bitmap findPropertiesBitmapForCancas(int w, int h, Bitmap src) {
		int width = src.getWidth();
		int height = src.getHeight();
		Matrix matrix = new Matrix();
		float scale = w / (width + 0.0f);

		matrix.postScale(scale, scale);
		Bitmap resizedBitmap = Bitmap.createBitmap(src, 0, 0, width, height,
				matrix, true);
		Bitmap result = Bitmap.createBitmap(resizedBitmap, 0,
				(int) ((resizedBitmap.getHeight() - h) * 0.5), w, h);
		resizedBitmap.recycle();
		resizedBitmap = null;
		return result;
	}

	private static byte[] InputStreamToByte(InputStream is) throws IOException {

		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[1024];
		int rc = 0;
		while ((rc = is.read(buff, 0, 1024)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		byte[] in_b = swapStream.toByteArray();

		is.close();
		swapStream.close();

		return in_b;
	}

	public static byte[] getShareDisplay(String path) throws IOException {
		InputStream is = new BufferedInputStream(new FileInputStream(new File(
				path)), 16 * 1024);
		;// new FileInputStream(new
			// File(CameraHelper.getAppMediaStringDir(),key));
		byte[] bytes = InputStreamToByte(is);

		return bytes;
	}

	public static Bitmap getShareDisplayImage(String path) {
		Bitmap result = BitmapFactory.decodeFile(path);
		return result;
	}

	public static Bitmap setTransparentDrawAreaForBitmap(Bitmap mask, int w,
			int h, Bitmap src, Rect rect) throws IOException {
		if (src == null) {
			return null;
		}
		int width = src.getWidth();
		int height = src.getHeight();
		float scale = width / (w + 0.0f);
		Rect newRect = new Rect();
		newRect.x = (int) (rect.x * scale);
		newRect.y = (int) (rect.y * scale);
		newRect.width = (int) (rect.width * scale);
		newRect.height = (int) (rect.height * scale);
		Bitmap drawImage = MediaHelper.resizeBitmap(mask, scale);

		Bitmap origBitmap = Bitmap.createBitmap(drawImage.getWidth(),
				drawImage.getHeight(), Config.ARGB_8888);

		Mat maskMat = new Mat();
		Mat origMat = new Mat();
		Utils.bitmapToMat(drawImage, maskMat);
		Utils.bitmapToMat(origBitmap, origMat);

		Mat resultGrayMat = new Mat(maskMat.rows(), maskMat.cols(),
				CvType.CV_8UC1);
		Mat origGrayMat = new Mat(origMat.rows(), origMat.cols(),
				CvType.CV_8UC1);

		Imgproc.cvtColor(maskMat, resultGrayMat, Imgproc.COLOR_BGRA2GRAY);
		Imgproc.cvtColor(origMat, origGrayMat, Imgproc.COLOR_BGRA2GRAY);

		Mat resultMat = new Mat(origMat.rows(), origMat.cols(), CvType.CV_8UC1);
		Core.subtract(resultGrayMat, origGrayMat, resultMat);

		Mat maskThreshold = new Mat();
		Imgproc.threshold(resultMat, maskThreshold, 3, 255,
				Imgproc.THRESH_BINARY);

		Mat finalMask = new Mat(maskThreshold.rows() + 2,
				maskThreshold.cols() + 2, CvType.CV_8UC1);
		Point maskCenter = new Point();
		maskCenter.x = maskThreshold.cols() * 0.5 + 0.5;
		maskCenter.y = maskThreshold.rows() * 0.5 + 0.5;

		System.out.println("center:" + maskCenter.x + "," + maskCenter.y);
		Imgproc.floodFill(maskThreshold, finalMask, maskCenter, new Scalar(255));
		resultMat.get(30, 60);
		Bitmap maskBitmap = Bitmap.createBitmap(maskThreshold.cols(),
				maskThreshold.rows(), Config.ARGB_8888);
		Utils.matToBitmap(maskThreshold, maskBitmap);
		String path2 = RuntimeCache.getCurrentInternalImageFolder()
				+ File.separator + "draw_mask_floodFill" + ".jpg";
		try {
			FileOutputStream out1 = new FileOutputStream(path2);
			maskBitmap.compress(CompressFormat.JPEG, 90, out1);
			out1.flush();
			out1.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		int[] pix1 = new int[width * height];
		int[] pix2 = new int[maskBitmap.getWidth() * maskBitmap.getHeight()];
		maskBitmap.getPixels(pix2, 0, maskBitmap.getWidth(), 0, 0,
				maskBitmap.getWidth(), maskBitmap.getHeight());
		src.getPixels(pix1, 0, width, 0, 0, width, height);

		for (int i = 0; i < maskBitmap.getHeight(); i++) {
			boolean isTransparent = false;
			for (int k = 0; k < maskBitmap.getWidth(); k++) {
				int index1 = maskBitmap.getWidth() * i + k;
				if (pix2[index1] == -1) {
					int index2 = width * (i + newRect.y) + k + newRect.x;
					pix1[index2] = 0;
				}
			}
		}
		Bitmap result = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		result.setPixels(pix1, 0, width, 0, 0, width, height);
		return result;
	}

	public static Bitmap setTransparentDrawAreaForBitmap_2(Mat mask, int w,
			int h, Bitmap src, Rect rect) throws IOException {
		if (src == null) {
			return null;
		}
		int width = src.getWidth();
		int height = src.getHeight();
		float scale = width / (w + 0.0f);
		Rect newRect = new Rect();
		newRect.x = (int) (rect.x * scale);
		newRect.y = (int) (rect.y * scale);
		newRect.width = (int) (rect.width * scale);
		newRect.height = (int) (rect.height * scale);

		Bitmap origMaskBitmap = Bitmap.createBitmap(mask.cols(), mask.rows(),
				Config.ARGB_8888);
		Utils.matToBitmap(mask, origMaskBitmap);

		Bitmap maskBitmap = MediaHelper.resizeBitmap(origMaskBitmap, scale);
		origMaskBitmap.recycle();
		origMaskBitmap = null;

		int[] pix1 = new int[width * height];
		int[] pix2 = new int[maskBitmap.getWidth() * maskBitmap.getHeight()];
		maskBitmap.getPixels(pix2, 0, maskBitmap.getWidth(), 0, 0,
				maskBitmap.getWidth(), maskBitmap.getHeight());
		src.getPixels(pix1, 0, width, 0, 0, width, height);

		for (int i = 0; i < maskBitmap.getHeight(); i++) {
			for (int k = 0; k < maskBitmap.getWidth(); k++) {
				int index1 = maskBitmap.getWidth() * i + k;
				if (pix2[index1] == -1) {
					int index2 = width * (i + newRect.y) + k + newRect.x;
					pix1[index2] = 0;
				}
			}
		}
		Bitmap result = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		result.setPixels(pix1, 0, width, 0, 0, width, height);
		return result;
	}

	public static Bitmap setTransparentAreaForBitmap(int w, int h, Bitmap src,
			Rect rect) throws IOException {
		Mat newMat = new Mat(rect.height, rect.width, CvType.CV_8UC1);
		List<Point> pointList = new ArrayList<Point>();
		Point point1 = new Point(0, 0);
		Point point2 = new Point(rect.width, 0);
		Point point3 = new Point(rect.width, rect.height);
		Point point4 = new Point(0, rect.height);
		pointList.add(point1);
		pointList.add(point2);
		pointList.add(point3);
		pointList.add(point4);

		MatOfPoint matPoint = new MatOfPoint();

		matPoint.fromList(pointList);

		Core.fillConvexPoly(newMat, matPoint, new Scalar(255), 8, 0);

		String path2 = RuntimeCache.getCurrentInternalImageFolder()
				+ File.separator + "draw_mask_rect" + ".jpg";
		Bitmap origMaskBitmap = Bitmap.createBitmap(newMat.cols(),
				newMat.rows(), Config.ARGB_8888);
		Utils.matToBitmap(newMat, origMaskBitmap);
		try {
			FileOutputStream out1 = new FileOutputStream(path2);
			origMaskBitmap.compress(CompressFormat.JPEG, 100, out1);
			out1.flush();
			out1.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (src == null) {
			return null;
		}
		int width = src.getWidth();
		int height = src.getHeight();
		float scale = width / (w + 0.0f);
		Rect newRect = new Rect();
		newRect.x = (int) (rect.x * scale);
		newRect.y = (int) (rect.y * scale + (height - h * scale) * 0.5);
		newRect.width = (int) (rect.width * scale);
		newRect.height = (int) (rect.height * scale);

		int[] pix = new int[width * height];

		src.getPixels(pix, 0, width, 0, 0, width, height);

		for (int i = newRect.y; i < newRect.y + newRect.height; i++) {
			for (int k = newRect.x; k < newRect.x + newRect.width; k++) {
				int index = width * i + k;
				pix[index] = 0;
			}
		}
		Bitmap result = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		result.setPixels(pix, 0, width, 0, 0, width, height);
		return result;
	}

}
