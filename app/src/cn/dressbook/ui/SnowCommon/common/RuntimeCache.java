package cn.dressbook.ui.SnowCommon.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import cn.dressbook.ui.SnowCommon.common.util.CameraHelper;

public class RuntimeCache {
	public static String imagesDir = null;
	public static String resultDir = null;
	public static boolean isAnimated = false;
	public static boolean isLogin = false;

	private static long userId;
	// private static Bitmap photoImage;
	private static int screenW = 0;
	private static int screedH = 0;
	private static float des = 0;

	public static String currentProcessKey=null;
	public static String lastProcessKey=null;

	public static Map<String, String> resultMap = new HashMap<String, String>();
	public static Map<String, AsyncTask> gifTask = new HashMap<String, AsyncTask>();

	public static String currentImageFolderName;
	public static String currentVideoPath;
	public static List<SelectModel> currentImages = new ArrayList<SelectModel>();
	public static List<SelectModel> textImagePath=new ArrayList<SelectModel>();
	public static List<MaskInfo> currentMasks;
	public static int frameCount = 0;
	public static List<Bitmap> resultBitmaps;
	public static Map<String, List<Bitmap>> resultBitmapCache=new  HashMap<String, List<Bitmap>>();
	
	//public static List<Bitmap>currentBitmaps;
//	public static BackgroundTaskDelegate currentTaskDelegate;
	// public static List<SelectModel> finalResultImage = new
	// ArrayList<SelectModel>();

	// public static Activity currentWorkingActivity = null;
	
	public static boolean isBackCamera=true;

	public static int edgeSpace = 20;

	// public static boolean useWarp = false;
	// public static boolean isWaitingForWarp = false;
	// public static boolean isWarpOk = false;
	// public static int warpCount = 0;
	public static int animatedType = 0; // 0 is normal, 1 is animated

	// private static Set<Activity> activitySet = new HashSet<Activity>();

	// public static String token;
	// public static String getToken() {
	// if(token==null)
	// return "";
	// return token;
	// }
	// public static void setToken(String token) {
	// isLogin=true;
	// RuntimeCache.token = token;
	// }
	

	public static String srcCropImagePath;

	public static void stopPretASK() {

		Iterator<String> its = RuntimeCache.gifTask.keySet().iterator();

		while (its.hasNext()) {
			String key = its.next();
			AsyncTask t = RuntimeCache.gifTask.get(key);
			t.cancel(true);
		}
		
		RuntimeCache.resultMap.clear();

	}

	public static boolean isLogin() {
		return isLogin;
	}

	public static void setLogin(boolean isLogin) {
		RuntimeCache.isLogin = isLogin;
	}

	public static String getImagesDir() {
		return imagesDir;
	}

	public static void setImagesDir(String imagesDir) {
		RuntimeCache.imagesDir = imagesDir;
	}

	public static String getResultDir() {
		return resultDir;
	}

	public static void setResultDir(String resultDir) {
		RuntimeCache.resultDir = resultDir;
	}

	public static boolean isAnimated() {
		return isAnimated;
	}

	public static void setAnimated(boolean isAnimated) {
		RuntimeCache.isAnimated = isAnimated;
	}

	public static void clear() {
		imagesDir = null;
		resultDir = null;
		isAnimated = false;
	}

	public static long getUserId() {
		return userId;
	}

	public static void setUserId(long userId) {
		RuntimeCache.userId = userId;
	}

	// public static Bitmap getPhotoImage() {
	// return photoImage;
	// }
	// public static void setPhotoImage(Bitmap photoImage) {
	// RuntimeCache.photoImage = photoImage;
	// }
	public static int getScreenW() {
		return screenW;
	}

	public static void setScreenW(int screenW) {
		RuntimeCache.screenW = screenW;
	}

	public static int getScreedH() {
		return screedH;
	}

	public static void setScreedH(int screedH) {
		RuntimeCache.screedH = screedH;
	}

	public static int getRefIndex() {
		int refId = currentImages.size() / 2;
		int count = 0;
		while ((refId - count) >= 0 || (refId + count) < currentImages.size()) {
			if ((refId + count) < currentImages.size()) {
				if (currentImages.get(refId + count).isSelected()) {
					return refId + count;
					// return currentImages.get(refId+count);
				}
			} else if ((refId - count) >= 0) {
				if (currentImages.get(refId - count).isSelected()) {
					return refId - count;
				}
			}
			count++;
		}
		return refId;
	}

	public static Bitmap getResultIcon() {
		if (currentImages != null) {
			for (int i = 0; i < currentImages.size(); i++) {
				SelectModel model = currentImages.get(i);
				if (model.isSelected()) {
					BitmapFactory.Options opts = new BitmapFactory.Options();
					opts.inSampleSize = 5;
					Bitmap value = BitmapFactory.decodeFile(
							model.getResultPath(), opts);
					return value;
				}

			}
		}
		return null;
	}

	public static void removeMasks() {
		if (currentMasks != null && !currentMasks.isEmpty()) {
			for (MaskInfo mask : currentMasks) {
				Bitmap maskImage = mask.getMaskImage();
				if (maskImage != null && !maskImage.isRecycled()) {
					maskImage.recycle();
					maskImage = null;
				}
			}
			currentMasks.clear();
		}
	}

	// public static void addActivity(Activity activity) {
	// activitySet.add(activity);
	// }
	//
	// public static void removeActivity(Activity activity) {
	// activitySet.remove(activity);
	// }
	//
	// public static void finishAllActivity() {
	// for (Activity activity : activitySet)
	// activity.finish();
	// activitySet.clear();
	// }

	public static float getDes() {
		return des;
	}

	public static void setDes(float des) {
		RuntimeCache.des = des;
	}

	static int hotWidth = 0;

	public static int getHotWidth() {

		if (hotWidth != 0)
			return hotWidth;

		// if (screenW / des > 500) {
		// hotWidth = ((screenW / 3));
		// } else {
		hotWidth = ((screenW / 2) - 5);
		// }

		return hotWidth;
	}

	public static int getDetailWidth() {
		return (screenW - 10);
	}
	
	
	public static String getCurrentExternalImageFolder(){
		
		return CameraHelper.getExternalOutputImagesFolder(currentImageFolderName);
	}
	
	public static String getCurrentInternalImageFolder(){
		
		if(currentImageFolderName==null){
			
			newInternalImageFolder();
		}
		
		return CameraHelper.getInternalOutputImagesFolder(currentImageFolderName);
	}
	
	public static String getCurrentInternalCropImageFolder(){
		
		if(currentImageFolderName==null){
			
			newInternalImageFolder();
		}
		
		return CameraHelper.getInternalCropSrcImagesFolder(currentImageFolderName);
	}

	
	public static String generateNewTimeStamp(){
		
		
		currentImageFolderName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		
		return currentImageFolderName;
		
	}
	
public static String newExternalImageFolder(){
		
		return CameraHelper.getExternalOutputImagesFolder(generateNewTimeStamp());
	}
	
	public static String newInternalImageFolder(){
		
	   lastProcessKey=currentProcessKey;
	   
		currentProcessKey=generateNewTimeStamp();
		
		return CameraHelper.getInternalOutputImagesFolder(currentProcessKey);
	}
	
	public static String getCurrentInternalProcessFolder(){
		return CameraHelper.getInternalProcessImagesFolder(currentProcessKey);
	}
}
