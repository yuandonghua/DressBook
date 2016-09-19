package cn.dressbook.ui.face.data;

import android.graphics.Bitmap;
import android.graphics.Point;
import cn.dressbook.ui.face.tools.FCTools;

public class FCModelXmlItem extends FCBaseXmlItem {

	public Point left;
	public Point right;

	public String furl;
	public String surl;

	public Bitmap furlImg = null;
	public Bitmap surlImg = null;

	public void onDestroy() {
		if (furlImg != null) {
			furlImg.recycle();
			furlImg = null;
		}

		if (surlImg != null) {
			surlImg.recycle();
			surlImg = null;
		}
	}

	public void setLeftP(String x, String y) {
		// TODO Auto-generated method stub

		try {
			int ix = Integer.parseInt(x);
			int iy = Integer.parseInt(y);
			left = new Point(ix, iy);
		} catch (Exception e) {
			// TODO: handle exception
			left = new Point(0, 0);
		}

	}

	public float getEyeDistance() {
		float res = 1;
		res = right.x - left.x;
		if (res == 0) {
			res = 1.0f;
		}
		if (FCTools.isNeedScaleBodyImg()) {
			// res = res * 2;
		}
		return res;
	}

	public void setRightP(String x, String y) {
		// TODO Auto-generated method stub

		try {
			int ix = Integer.parseInt(x);
			int iy = Integer.parseInt(y);
			right = new Point(ix, iy);
		} catch (Exception e) {
			// TODO: handle exception
			right = new Point(0, 0);
		}

	}

	// public FCModelXmlItem() {
	// left = new Point(1, 2);
	// }

}
