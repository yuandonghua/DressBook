package cn.dressbook.ui.SnowCommon.common;

public class AppProperties {
	private static int photoCount=30;
	private static int doration=100;
	public static int getPhotoCount() {
		return photoCount;
	}
	public static void setPhotoCount(int photoCount) {
		AppProperties.photoCount = photoCount;
	}
	public static int getDoration() {
		return doration;
	}
	public static void setDoration(int timer) {
		AppProperties.doration = timer;
	}
}
