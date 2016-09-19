package cn.dressbook.ui.face.tools;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * @description: matrix工具类
 * @author:袁东华
 * @time:2015-8-4下午6:12:46
 */
public class MatrixTools {

	private MatrixTools() {

	}

	// PointF

	public static void postScale(Matrix max, float scale, float pointX,
			float pointY, Bitmap orgMap) {
		float[] values = new float[9];

		max.getValues(values);
		float x = values[Matrix.MTRANS_X];
		float y = values[Matrix.MTRANS_Y];

		float scalex = values[Matrix.MSCALE_X];
		float scaley = values[Matrix.MSCALE_Y];

		float bmpW = (float) orgMap.getWidth() * scalex;
		float bmpH = (float) orgMap.getHeight() * scaley;

		max.postScale(scale, scale, pointX, pointY);

		max.getValues(values);

		scalex = values[Matrix.MSCALE_X];
		scaley = values[Matrix.MSCALE_Y];

		float x1 = values[Matrix.MTRANS_X];
		float y1 = values[Matrix.MTRANS_Y];

		float bmpX = ((float) orgMap.getWidth() * scalex - bmpW) / 2;
		float bmpY = ((float) orgMap.getHeight() * scalex - bmpH) / 2;

		float x0 = (x1 - x);
		float y0 = (y1 - y);
		max.postTranslate(-x0 - bmpX, -y0 - bmpY);

		max.getValues(values);
		x = values[Matrix.MTRANS_X];
		y = values[Matrix.MTRANS_Y];
	}

	public static void postScale(Matrix max, float scale, Bitmap orgMap) {

		MatrixTools.postScale(max, scale, 0, 0, orgMap);
	}

}
