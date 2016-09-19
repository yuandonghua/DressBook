package cn.dressbook.ui.face.data;

import android.graphics.Bitmap;

public class FCBaseXmlItem {

	public String itemID;
	public String vurl;
	public String iurl;
	public Bitmap vurlImg;
	public Bitmap iurlImg;

	public void onDestroy() {
		if (vurlImg != null) {
			vurlImg.recycle();
			vurlImg = null;
		}

		if (iurlImg != null) {
			iurlImg.recycle();
			iurlImg = null;
		}
	}

}
