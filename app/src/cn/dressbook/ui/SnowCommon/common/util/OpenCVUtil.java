package cn.dressbook.ui.SnowCommon.common.util;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

public class OpenCVUtil {

	public static Scalar scalar255 = new Scalar(255, 255, 255);

	public static Bitmap genClearBitmap(int rows, int cols) {
		try {

			Mat mat = new Mat(rows, cols, CvType.CV_8UC4);
			Scalar zeroScalar = Scalar.all(0);
			// Scalar zeroScalar =new Scalar(150,150,150,250);

			mat.setTo(zeroScalar);

			Bitmap bmp = Bitmap.createBitmap(cols, rows, Config.ARGB_8888);
			org.opencv.android.Utils.matToBitmap(mat, bmp);
			return bmp;
		} catch (Exception e) {
			// TODO: handle exception
			
		}
		return null;
	}

	public static Bitmap genBlackBitmap(int rows, int cols) {

		Mat mat = new Mat(rows, cols, CvType.CV_8UC3);
		Scalar zeroScalar = Scalar.all(0);
		// Scalar zeroScalar =new Scalar(150,150,150,250);

		mat.setTo(zeroScalar);

		Bitmap bmp = Bitmap.createBitmap(cols, rows, Config.RGB_565);
		org.opencv.android.Utils.matToBitmap(mat, bmp);
		return bmp;
	}
}
