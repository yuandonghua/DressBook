package cn.dressbook.ui.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
/**
 * 屏幕的工具类
 * @author 袁东华
 * */
public class ScreenUtil {
//	private  Context context;
	
	/**
	 * 获取屏幕的大小
	 * 
	 * @param context
	 * @return
	 */
	public static  Screen getScreenPix(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(dm);
		return new Screen(dm.widthPixels, dm.heightPixels);
	}

	/**
	 * 获取屏幕的宽
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidthPix(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(dm);
		return new Screen(dm.widthPixels, dm.heightPixels).widthPixels;
	}

	/**
	 * 获取屏幕的高
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenHeightPix(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(dm);
		return new Screen(dm.widthPixels, dm.heightPixels).heightPixels;
	}

	public static class Screen {
		public int widthPixels;
		public int heightPixels;

		public Screen() {
		}

		public  Screen(int widthPixels, int heightPixels) {
			this.widthPixels = widthPixels;
			this.heightPixels = heightPixels;
		}

		@Override
		public String toString() {
			return "(" + widthPixels + "," + heightPixels + ")";
		}
	}
}
