package cn.dressbook.ui.SnowCommon.common;

import android.graphics.Bitmap;
import cn.dressbook.ui.SnowCommon.cache.RuntimeImageLoader;

public class SelectModel {
		private boolean isSelected;
		private String path;
		private String resultPath;
		private String warpPath;
		private Bitmap value;
		private boolean useWarp=true;

		public SelectModel() {

		}

		public boolean isSelected() {
			return isSelected;
		}

		public void setSelected(boolean isSelected) {
			this.isSelected = isSelected;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public Bitmap getValue() {
			return value;
		}

		public void setValue(Bitmap value) {
			this.value = value;
		}

		

		public String getResultPath() {
			if(resultPath!=null&&!resultPath.isEmpty())
				return resultPath;
			return path;
//			else{ 
//				if(RuntimeCache.useWarp&&useWarp&&warpPath!=null){
//					return warpPath;
//				}
//				return path;
//			}
		}

		public void setResultPath(String resultPath) {
			this.resultPath = resultPath;
		}

		public String getWarpPath() {
			return warpPath;
		}

		public void setWarpPath(String warpPath) {
			this.warpPath = warpPath;
		}

		public boolean isUseWarp() {
			return useWarp;
		}

		public void setUseWarp(boolean useWarp) {
			this.useWarp = useWarp;
		}
		
				
		public Bitmap getProcessBitmap(){
//			if(RuntimeCache.useWarp){
//				if(this.useWarp){
//					System.out.println("process image:"+this.getWarpPath());
//					return RuntimeImageLoader.getBitmapByPath(this.getWarpPath());
//					//return RuntimeImageLoader.getRuntimeCacheInstance().getBitmapFromMemCache_bak(this.getWarpPath());
//				}
//			}
//			System.out.println("process image:"+this.getPath());
			return RuntimeImageLoader.getBitmapByPath(this.getPath());
			//return RuntimeImageLoader.getRuntimeCacheInstance().getBitmapFromMemCache_bak(this.getPath());
		}
	}