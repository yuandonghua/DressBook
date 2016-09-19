package cn.dressbook.ui.general.FotoCut.opencvImpl;

import android.util.Log;

public class OpencvLoader {
	public static final String TAG = "Load Opencv";

	public static void loadLibary() {
		try {
			System.loadLibrary("opencv_java");
			//System.loadLibrary("libnative_camera_r4.4.0");
		} catch (UnsatisfiedLinkError e) {
			Log.e(TAG, "OpenCV error: Cannot load info library for OpenCV");
		}
	}
}
