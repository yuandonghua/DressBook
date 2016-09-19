package cn.dressbook.ui.SnowCommon.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.util.Log;
import cn.dressbook.ui.common.PathCommonDefines;


public class CameraHelper {
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;

	private static String resultPath, resultGifPath, resultCoverPath,
			resultVideoPath;

	// /** Create a file Uri for saving an image or video */
	// private static Uri getOutputMediaFileUri(int type){
	// return Uri.fromFile(getOutputMediaFile(type));
	// }

	// SnapCommonApplication application;

	public static void clearInternalImagessData(String timeStamp) {

		// File mediaStorageDir = new
		// File(Environment.getExternalStoragePublicDirectory(
		// Environment.DIRECTORY_PICTURES),
		// Snap.getAppFolderName());
		// File imagesFile=new
		// File(mediaStorageDir.getPath()+File.separator+"images");

		File imagesFile = new File(getInternalOutputImagesFolder(timeStamp));
		if (imagesFile.exists() && imagesFile.isDirectory()) {
			MediaHelper.deleteSubFiles(imagesFile);
		}
		imagesFile.delete();

	}

	public static void clearParentFolder(String file) {
		File imagesFile = new File(file);
		File parent = imagesFile.getParentFile();
		if (parent.exists() && parent.isDirectory()) {
			MediaHelper.deleteSubFiles(parent);
		}
	}

	public static void clearMp4File(String file) {

		MediaHelper.deleteFile(file);// .deleteSubFiles(parent);

	}

	public static void clearUp3Folder(String file) {
		File imagesFile = new File(file);
		File parent = imagesFile.getParentFile();
		if (parent.exists()) {
			File parent1 = parent.getParentFile();

			if (parent1.exists() && parent1.isDirectory()) {
				MediaHelper.deleteSubFiles(parent1);
			}
		}
	}

	public static String getResultDir() {

		if (resultPath != null)
			return resultPath;

		File resultDir = new File(PathCommonDefines.APP_FOLDER_ON_SD, "result");

		// File mediaStorageDir = new
		// File(Environment.getExternalStoragePublicDirectory(
		// Environment.DIRECTORY_PICTURES),
		// Snap.getAppFolderName());
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist

		// File gifDir=new File(mediaStorageDir.getPath());
		if (!resultDir.exists()) {
			if (!resultDir.mkdirs()) {
				return null;
			}
		}

		resultPath = resultDir.getPath();

		return resultPath;
	}

	public static String getResultGifDir() {

		if (resultGifPath != null)
			return resultGifPath;

		String resultPath = getResultDir();

		File resultGifDir = new File(resultPath, "gif");
		if (!resultGifDir.exists()) {
			if (!resultGifDir.mkdirs()) {
				return null;
			}
		}
		resultGifPath = resultGifDir.getPath();

		return resultGifPath;
	}

	public static String getResultCoverDir() {

		if (resultCoverPath != null)
			return resultCoverPath;

		String resultPath = getResultDir();

		File resultCoverDir = new File(resultPath, "cover");
		if (!resultCoverDir.exists()) {
			if (!resultCoverDir.mkdirs()) {
				return null;
			}
		}

		resultCoverPath = resultCoverDir.getPath();

		return resultCoverPath;
	}

	public static String getResultVideoDir() {

		if (resultVideoPath != null)
			return resultVideoPath;

		String resultPath = getResultDir();

		File resultVideoDir = new File(resultPath, "video");
		if (!resultVideoDir.exists()) {
			if (!resultVideoDir.mkdirs()) {
				return null;
			}
		}

		resultVideoPath = resultVideoDir.getPath();

		return resultVideoPath;
	}

	public void saveMask(Bitmap image) throws IOException {
		String dir = getResultDir();
		String fileName = dir + File.separator + "mask.jpg";
		FileOutputStream out = new FileOutputStream(fileName);
		image.compress(CompressFormat.JPEG, 100, out);
		out.flush();
		out.close();

	}

	/** Create a File for saving an image or video */
	public static File getOutputVideoFile() {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		// File mediaStorageDir = new
		// File(Environment.getExternalStoragePublicDirectory(
		// Environment.DIRECTORY_PICTURES),
		// Snap.getAppFolderName());
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist

		File videoDir = new File(PathCommonDefines.APP_FOLDER_ON_SD, "videos");

		// File videoDir=new
		// File(mediaStorageDir.getPath()+File.separator+"videos");
		if (!videoDir.exists()) {
			if (!videoDir.mkdirs()) {
				Log.d("SnowCam", "failed to create directory");
				return null;
			}
		}
		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		File mediaFile;

		mediaFile = new File(videoDir.getPath() + File.separator + "VID_"
				+ timeStamp + ".mp4");

		return mediaFile;
	}

	/** Create a File for saving an image or video */
	public static File getOriginalOutputVideoFile(String timeStamp) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		File mediaStorageDir = new File(PathCommonDefines.APP_FOLDER_ON_SD);
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist

		// File videoDir = new
		// File(SnapPathCommonDefines.APP_FOLDER_ON_SD,
		// "videos");

		// File videoDir=new
		// File(mediaStorageDir.getPath()+File.separator+"videos");
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("SnowCam", "failed to create directory");
				return null;
			}
		}
		// Create a media file name
		// String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new
		// Date());
		File mediaFile;

		mediaFile = new File(mediaStorageDir.getPath() + File.separator
				+ "VID_" + timeStamp + ".mp4");

		return mediaFile;
	}

	public static File getOriginalOutputImageFile(String timeStamp) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		File mediaStorageDir = new File(PathCommonDefines.APP_FOLDER_ON_SD);
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist

		// File videoDir = new
		// File(SnapPathCommonDefines.APP_FOLDER_ON_SD,
		// "videos");

		// File videoDir=new
		// File(mediaStorageDir.getPath()+File.separator+"videos");
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("SnowCam", "failed to create directory");
				return null;
			}
		}
		// Create a media file name
		// String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new
		// Date());
		File mediaFile;

		mediaFile = new File(mediaStorageDir.getPath() + File.separator
				+ "IMG_" + timeStamp + ".jpg");

		return mediaFile;
	}

	public static String getInternalImagesFolderPath() {

		File imageFolder = new File(PathCommonDefines.APP_FOLDER_ON_SD, "images");

		return imageFolder.getPath();
	}
	
	public static String getInternalCropSrcImagesFolder(String timeStamp) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		// File mediaStorageDir = new
		// File(Environment.getExternalStoragePublicDirectory(
		// Environment.DIRECTORY_PICTURES),
		// Snap.getAppFolderName());
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		// String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new
		// Date());

		File imageFolder = new File(PathCommonDefines.APP_FOLDER_ON_SD, "crop");

		if (!imageFolder.exists()) {
			if (!imageFolder.mkdirs()) {
				Log.d("SnowCam", "failed to create directory");
				return null;
			}
		}
		File subFolder = new File(imageFolder, timeStamp);

		// File imageFolder=new
		// File(mediaStorageDir.getPath()+File.separator+"images"
		// +File.separator+timeStamp);

		// File tmpFolder=new
		// File(mediaStorageDir.getPath()+File.separator+"images"
		// +File.separator+timeStamp+"_tmp");
		// if (! tmpFolder.exists()){
		// if (! tmpFolder.mkdirs()){
		// Log.d("snapwaver", "failed to create directory");
		// return null;
		// }
		// }
		if (!subFolder.exists()) {
			if (!subFolder.mkdirs()) {
				Log.d("SnowCam", "failed to create directory");
				return null;
			}
		}
		return subFolder.getPath();
	}

	public static String getInternalOutputImagesFolder(String timeStamp) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		// File mediaStorageDir = new
		// File(Environment.getExternalStoragePublicDirectory(
		// Environment.DIRECTORY_PICTURES),
		// Snap.getAppFolderName());
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		// String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new
		// Date());

		File imageFolder = new File(
				PathCommonDefines.APP_FOLDER_ON_SD, "images");

		if (!imageFolder.exists()) {
			if (!imageFolder.mkdirs()) {
				Log.d("SnowCam", "failed to create directory");
				return null;
			}
		}
		File subFolder = new File(imageFolder, timeStamp);

		// File imageFolder=new
		// File(mediaStorageDir.getPath()+File.separator+"images"
		// +File.separator+timeStamp);

		// File tmpFolder=new
		// File(mediaStorageDir.getPath()+File.separator+"images"
		// +File.separator+timeStamp+"_tmp");
		// if (! tmpFolder.exists()){
		// if (! tmpFolder.mkdirs()){
		// Log.d("snapwaver", "failed to create directory");
		// return null;
		// }
		// }
		if (!subFolder.exists()) {
			if (!subFolder.mkdirs()) {
				Log.d("SnowCam", "failed to create directory");
				return null;
			}
		}
		return subFolder.getPath();
	}
	
	public static String getTmpProcessImageFolder(){
		File imageFolder = new File(
				PathCommonDefines.APP_FOLDER_ON_SD, "tmp");

		if (!imageFolder.exists()) {
			if (!imageFolder.mkdirs()) {
				Log.d("SnowCam", "failed to create directory");
				return null;
			}
		}
		return imageFolder.getPath();
	}
	
	public static String getInternalProcessImagesFolder(String timeStamp) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		// File mediaStorageDir = new
		// File(Environment.getExternalStoragePublicDirectory(
		// Environment.DIRECTORY_PICTURES),
		// Snap.getAppFolderName());
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		// String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new
		// Date());

		File imageFolder = new File(
				PathCommonDefines.APP_FOLDER_ON_SD, "tmp");

		if (!imageFolder.exists()) {
			if (!imageFolder.mkdirs()) {
				Log.d("SnowCam", "failed to create directory");
				return null;
			}
		}
		File subFolder = new File(imageFolder, timeStamp);

		// File imageFolder=new
		// File(mediaStorageDir.getPath()+File.separator+"images"
		// +File.separator+timeStamp);

		// File tmpFolder=new
		// File(mediaStorageDir.getPath()+File.separator+"images"
		// +File.separator+timeStamp+"_tmp");
		// if (! tmpFolder.exists()){
		// if (! tmpFolder.mkdirs()){
		// Log.d("snapwaver", "failed to create directory");
		// return null;
		// }
		// }
		if (!subFolder.exists()) {
			if (!subFolder.mkdirs()) {
				Log.d("SnowCam", "failed to create directory");
				return null;
			}
		}
		return subFolder.getPath();
	}

	public static String getExternalOutputImagesFolder(String timeStamp) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		File mediaStorageDir = new File(
				PathCommonDefines.APP_FOLDER_ON_SD);
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		// String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new
		// Date());

		File imageFolder = new File(mediaStorageDir, timeStamp);

		// File imageFolder=new
		// File(mediaStorageDir.getPath()+File.separator+"images"
		// +File.separator+timeStamp);
		if (!imageFolder.exists()) {
			if (!imageFolder.mkdirs()) {
				Log.d("SnowCam", "failed to create directory");
				return null;
			}
		}
		// File tmpFolder=new
		// File(mediaStorageDir.getPath()+File.separator+"images"
		// +File.separator+timeStamp+"_tmp");
		// if (! tmpFolder.exists()){
		// if (! tmpFolder.mkdirs()){
		// Log.d("snapwaver", "failed to create directory");
		// return null;
		// }
		// }

		return imageFolder.getPath();
	}
	
	public static String getExternalOutputFolderTesing(String timeStamp) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		File mediaStorageDir = new File(
				PathCommonDefines.APP_FOLDER_ON_SD);
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		// String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new
		// Date());

		File imageFolder = new File(mediaStorageDir, timeStamp);

		// File imageFolder=new
		// File(mediaStorageDir.getPath()+File.separator+"images"
		// +File.separator+timeStamp);
		if (!imageFolder.exists()) {
			if (!imageFolder.mkdirs()) {
				Log.d("SnowCam", "failed to create directory");
				return null;
			}
		}
		// File tmpFolder=new
		// File(mediaStorageDir.getPath()+File.separator+"images"
		// +File.separator+timeStamp+"_tmp");
		// if (! tmpFolder.exists()){
		// if (! tmpFolder.mkdirs()){
		// Log.d("snapwaver", "failed to create directory");
		// return null;
		// }
		// }

		return imageFolder.getPath();
	}

	public static File getAppMediaDir() {
		File mediaStorageDir = new File(
				PathCommonDefines.APP_FOLDER_ON_SD);

		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				return null;
			}
		}
		return mediaStorageDir;
	}

	public static String getAppMediaStringDir() {

		String path = PathCommonDefines.APP_FOLDER_ON_SD;

		return path;
	}

	public static String getPictureTmpDir() {
		// File mediaStorageDir= getAppMediaDir();
		//
		// if(mediaStorageDir==null)
		// return null;

		String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HHmmss")
				.format(new Date());

		File mediaFile = new File(
				PathCommonDefines.APP_FOLDER_ON_SD, timeStamp);

		// File mediaFile = new File(mediaStorageDir.getPath() + File.separator
		// + timeStamp);
		if (!mediaFile.exists()) {
			if (!mediaFile.mkdirs()) {
				Log.d("SnowCam", "fail to create directory");
				return null;
			}
		}
		return mediaFile.getPath();
	}

	public static String savePicture(byte[] data, String path, boolean isBack) {
		File pictureFile = new File(path);
		try {
			int size;
			Bitmap bMap;
			int orientation;
			bMap = BitmapFactory.decodeByteArray(data, 0, data.length);
			if (bMap.getHeight() <= bMap.getWidth()) {
				orientation = 90;
				size = bMap.getHeight();
			} else {
				orientation = 0;
				size = bMap.getWidth();
			}

			if (!isBack) {
				orientation = orientation + 180;
			}

			Bitmap bMapRotate;
			if (orientation != 0) {
				Matrix matrix = new Matrix();
				matrix.postRotate(orientation);
				// bMapRotate = Bitmap.createBitmap(bMap, 0, 0, bMap.getWidth(),
				// bMap.getHeight(), matrix, true);
				bMapRotate = Bitmap.createBitmap(bMap, 0, 0, size, size,
						matrix, true);
			} else
				bMapRotate = Bitmap.createBitmap(bMap, 0, 0, size, size);
			// bMapRotate = Bitmap.createScaledBitmap(bMap, bMap.getWidth(),
			// bMap.getHeight(), true);

			bMap.recycle();
			bMap = null;

			if (bMapRotate.getWidth() > 360) {
				bMap = Bitmap.createScaledBitmap(bMapRotate, 360, 360, true);
				bMapRotate.recycle();
				bMapRotate = null;
			} else {
				bMap = bMapRotate;
			}

			FileOutputStream fos = new FileOutputStream(pictureFile);
			bMap.compress(CompressFormat.JPEG, 100, fos);
			if (bMapRotate != null) {
				bMapRotate.recycle();
				bMapRotate = null;
			}
			fos.flush();
			fos.close();
			bMap.recycle();
			bMap = null;
		} catch (FileNotFoundException e) {
			Log.d("GirCamera", "File not found: " + e.getMessage());
			return null;
		} catch (IOException e) {
			Log.d("GirCamera", "Error accessing file: " + e.getMessage());
			return null;
		}
		return pictureFile.getPath();
	}

	public static String onPreviewFrame(byte[] data, Camera arg1,
			String filePath, boolean isBack) {

		Log.i("image data size", String.valueOf(data.length));
		Parameters params = arg1.getParameters();
		params.getSupportedPictureFormats();

		YuvImage yuvimage = new YuvImage(data, ImageFormat.NV21,
				params.getPreviewSize().width, params.getPreviewSize().height,
				null);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		yuvimage.compressToJpeg(new Rect(0, 0, params.getPreviewSize().width,
				params.getPreviewSize().height), 100, baos);
		return savePicture(baos.toByteArray(), filePath, isBack);
	}

	public static Bitmap deSizeBitmap(Bitmap bitmap, int px) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float scale = 450 / (px + 0.0f);

		if (bitmap != null) {
			Matrix matrix = new Matrix();
			matrix.postScale(scale, scale);
			Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width,
					height, matrix, true);
			bitmap.recycle();
			bitmap = null;
			return resizedBitmap;
		} else {
			return null;
		}
	}

	public static void createResultDir() {
		String path = getResultDir();
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}
	}

	// getInternalImagesFolderPath

	public static String getInternalGrabFolderPath() {

		File imageFolder = new File(
				PathCommonDefines.APP_FOLDER_ON_SD, "images/grab");

		if (!imageFolder.exists()) {
			if (!imageFolder.mkdirs()) {
				Log.d("SnowCam", "failed to create directory");
				return null;
			}
		}
		return imageFolder.getPath();
	}
	
	
	public static String getResultFaceobjPath() {

		File imageFolder = new File(
				PathCommonDefines.APP_FOLDER_ON_SD, "images/faceobj");

		if (!imageFolder.exists()) {
			if (!imageFolder.mkdirs()) {
				Log.d("SnowCam", "failed to create directory");
				return null;
			}
		}
		return imageFolder.getPath();
	}

	public static String getInternalResultFolderPath() {

		File imageFolder = new File(
				PathCommonDefines.APP_FOLDER_ON_SD, "images/result");
		if (!imageFolder.exists()) {
			if (!imageFolder.mkdirs()) {
				Log.d("SnowCam", "failed to create directory");
				return null;
			}
		}

		return imageFolder.getPath();
	}
	
	public static String getInternalFileFolderPath(String name) {

		File imageFolder = new File(
				PathCommonDefines.APP_FOLDER_ON_SD, "images/"+name);
		if (!imageFolder.exists()) {
			if (!imageFolder.mkdirs()) {
				Log.d("SnowCam", "failed to create directory");
				return null;
			}
		}

		return imageFolder.getPath();
	}

	public static String getExternalFileTesing(String name) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		File mediaStorageDir = new File(PathCommonDefines.APP_FOLDER_ON_SD);
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		// String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new
		// Date());

		File imageFolder = new File(mediaStorageDir, name);

		// File imageFolder=new
		// File(mediaStorageDir.getPath()+File.separator+"images"
		// +File.separator+timeStamp);
		if (!imageFolder.exists()) {
			if (!imageFolder.mkdirs()) {
				Log.d("SnowCam", "failed to create directory");
				return null;
			}
		}
		// File tmpFolder=new
		// File(mediaStorageDir.getPath()+File.separator+"images"
		// +File.separator+timeStamp+"_tmp");
		// if (! tmpFolder.exists()){
		// if (! tmpFolder.mkdirs()){
		// Log.d("snapwaver", "failed to create directory");
		// return null;
		// }
		// }

		return imageFolder.getPath();
	}
}
