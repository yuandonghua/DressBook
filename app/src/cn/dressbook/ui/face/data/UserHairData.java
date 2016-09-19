package cn.dressbook.ui.face.data;

import android.graphics.Matrix;
import cn.dressbook.ui.face.Const;
import cn.dressbook.ui.face.MainActivity;
import cn.dressbook.ui.face.tools.FCTools;

public class UserHairData extends FCGestureData {

	public FCBaseXmlItem iUserSelectHair = null;

	public UserHairData() {
		super();
		setDefaultMatrix();
	}

	public void onDestroy() {
		if (iUserSelectHair != null) {
			iUserSelectHair.onDestroy();
		}
	}

	public void setDefaultMatrix() {

		matrix = new Matrix();
		if (FCTools.isNeedScaleBodyImg()) {
			// matrix.postScale(2.0f, 2.0f);
			matrix.postTranslate(MainActivity.head_orgi_x,
					Const.MODEL_HEAD_IMAGE_ORIG_Y * 1.0f);
			// matrix.postTranslate(0 - (Const.MODEL_HEAD_IMAGE_SIZE_W / 2),
			// Const.MODEL_HEAD_IMAGE_ORIG_Y);
		} else {
			matrix.postTranslate(MainActivity.head_orgi_x,
					Const.MODEL_HEAD_IMAGE_ORIG_Y);
		}

	}

}
