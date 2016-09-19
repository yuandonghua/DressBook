package cn.dressbook.ui.SnowCommon.common.util;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;

public class NativeImageLoader {
	private LruCache<String, Bitmap> mMemoryCache;
	private Set<String> keySet=new HashSet<String>();
 	private static NativeImageLoader mInstance = new NativeImageLoader();
	private ExecutorService mImageThreadPool = Executors.newFixedThreadPool(1);

	private NativeImageLoader() {

		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

		final int cacheSize = maxMemory / 16;
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {

			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
			}
		};
	}

	public static NativeImageLoader getInstance() {
		return mInstance;
	}

	public Bitmap loadNativeImage(final String path,
			final NativeImageCallBack mCallBack) {
		return this.loadNativeImage(path, null, mCallBack);
	}

	public Bitmap loadBigImage(final String path,
			final NativeImageCallBack mCallBack) {
		final String key = "native_big_" + path;
		Bitmap bitmap = getBitmapFromMemCache(key);

		final Handler mHander = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				mCallBack.onImageLoader((Bitmap) msg.obj, path);
			}

		};

		if (bitmap == null) {
			mImageThreadPool.execute(new Runnable() {

				@Override
				public void run() {
					Bitmap mBitmap = decodeOrigBitmapForFile(path);
					Message msg = mHander.obtainMessage();
					msg.obj = mBitmap;
					mHander.sendMessage(msg);
					addBitmapToMemoryCache(key, mBitmap);
				}
			});
		}
		return bitmap;
	}

	public Bitmap loadNativeImage(final String path, final Point mPoint,
			final NativeImageCallBack mCallBack) {

		Bitmap bitmap = getBitmapFromMemCache(path);

		final Handler mHander = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				mCallBack.onImageLoader((Bitmap) msg.obj, path);
			}

		};

		if (bitmap == null) {
			mImageThreadPool.execute(new Runnable() {

				@Override
				public void run() {
					Bitmap mBitmap = decodeThumbBitmapForFile(path,
							mPoint == null ? 0 : mPoint.x, mPoint == null ? 0
									: mPoint.y);
					Message msg = mHander.obtainMessage();
					msg.obj = mBitmap;
					mHander.sendMessage(msg);
					addBitmapToMemoryCache(path, mBitmap);
				}
			});
		}
		return bitmap;

	}

	private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemCache(key) == null && bitmap != null) {
			keySet.add(key);
			mMemoryCache.put(key, bitmap);
		}
	}

	private Bitmap getBitmapFromMemCache(String key) {
		return mMemoryCache.get(key);
	}
	
	public void removeCache(){
		if (mMemoryCache != null) {
			mMemoryCache.evictAll();
		}
	}

	public synchronized void clearCache() {
		if (mMemoryCache != null&&!keySet.isEmpty()) {
			for(String key:keySet){
				Bitmap value=mMemoryCache.remove(key);
				if(value!=null&&!value.isRecycled()){
					value.recycle();
				}
			}
			keySet.clear();
		}
	}

	public synchronized void removeImageCache(String key) {
		if (key != null) {
			if (mMemoryCache != null) {
				Bitmap bm = mMemoryCache.remove(key);
				if (bm != null)
					bm.recycle();
			}
		}
	}

	private Bitmap decodeThumbBitmapForFile(String path, int viewWidth,
			int viewHeight) {
		BitmapFactory.Options options = new BitmapFactory.Options();

		options.inSampleSize = 6;

		return BitmapFactory.decodeFile(path, options);
	}

	private Bitmap decodeOrigBitmapForFile(String path) {
		return BitmapFactory.decodeFile(path);
	}

	public static Bitmap decodeProperBitmapForCrop(String path,int maxSize) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;

		BitmapFactory.decodeFile(path, options);

		options.inSampleSize = computeScale(options,maxSize);

		options.inJustDecodeBounds = false;
		options.inPreferredConfig=Config.RGB_565;
		return BitmapFactory.decodeFile(path, options);
	}

	private static int computeScale(BitmapFactory.Options options,int maxSize) {
		int inSampleSize = 1;

		int bitmapWidth = options.outWidth;
		int bitmapHeight = options.outHeight;

		if (bitmapWidth > maxSize && bitmapHeight > maxSize) {
			int widthScale = Math.round((float) bitmapWidth / (float) maxSize);
			int heightScale = Math.round((float) bitmapHeight / (float) maxSize);

			inSampleSize = widthScale < heightScale ? widthScale : heightScale;
		}
		return inSampleSize;
	}

	public interface NativeImageCallBack {
		public void onImageLoader(Bitmap bitmap, String path);
	}
}
