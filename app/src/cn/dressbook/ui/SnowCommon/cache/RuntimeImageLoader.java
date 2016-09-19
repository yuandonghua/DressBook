package cn.dressbook.ui.SnowCommon.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import cn.dressbook.ui.SnowCommon.common.RuntimeCache;

public class RuntimeImageLoader {

	public static RuntimeImageLoader instance;
	//private LruCache<String, Bitmap> mMemoryCache;

	public static RuntimeImageLoader getRuntimeCacheInstance() {
		if (instance == null) {
			instance = new RuntimeImageLoader();
		}
		return instance;
	}
	
	public static void initCache(){
		if (instance == null) {
			instance = new RuntimeImageLoader();
		}
	}



	/**
	 * Adds a bitmap to both memory and disk cache.
	 * 
	 * @param data
	 *            Unique identifier for the bitmap to store
	 * @param value
	 *            The bitmap drawable to store
	 */
	public void addBitmapToCache(String imagePath, Bitmap value) {
		// BEGIN_INCLUDE(add_bitmap_to_cache)
		if (imagePath == null || value == null) {
			return;
		}

	}

	/**
	 * Get from memory cache.
	 * 
	 * @param data
	 *            Unique identifier for which item to get
	 * @return The bitmap drawable if found in cache, null otherwise
	 */

	
	public static Bitmap getDisplayBitmapByPath(String path){
		if(path!=null){
			Bitmap value = BitmapFactory.decodeFile(path);
			
			if(value!=null){
				Bitmap smallImage= Bitmap.createBitmap(value, RuntimeCache.edgeSpace, RuntimeCache.edgeSpace, value.getWidth()-RuntimeCache.edgeSpace*2, value.getHeight()-RuntimeCache.edgeSpace*2);
				value.recycle();
				value=null;
				return smallImage;
			}
		}
		return null;
	}
	
	public static Bitmap getBitmapByPath(String path){
		if(path!=null){
			return BitmapFactory.decodeFile(path);
		}
		return null;
	}
	
	
}
