package cn.dressbook.ui.face.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import cn.dressbook.ui.face.Const;
import cn.dressbook.ui.face.MainActivity;
import cn.dressbook.ui.face.data.SingletonDataMgr;
import cn.dressbook.ui.face.tools.FCTools;

public class FCHairView extends View {
	public Context facecontext;

	public FCHairView(Context context) {
		super(context);
		this.facecontext = context;
		ini();
		// TODO Auto-generated constructor stub
	}

	public FCHairView(Context context, AttributeSet attr) {
		super(context, attr);
		this.facecontext = context;
		ini();
	}

	private void ini() {
		// TODO Auto-generated method stub

	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.drawColor(Color.TRANSPARENT);

		SingletonDataMgr mgr = SingletonDataMgr.getInstance();
		if (mgr.iUserHairData != null
				&& mgr.iUserHairData.iUserSelectHair != null
				&& mgr.iUserHairData.iUserSelectHair.vurlImg != null) {
			Matrix max = new Matrix(mgr.iUserHairData.matrix);
			if (FCTools.isNeedScaleBodyImg()) {

				float[] values = new float[9];
				max.getValues(values);

				// float x = values[Matrix.MTRANS_X];
				// float y = values[Matrix.MTRANS_Y];
				//
				// Log.e("max TAG 0", "x = " + x + " y = " + y);

				max.postScale(2.0f, 2.0f);
				// max.getValues(values);
				// x = values[Matrix.MTRANS_X];
				// y = values[Matrix.MTRANS_Y];
				//
				// Log.e("max TAG 1", "x = " + x + " y = " + y);
				// Log.e("max TAG 44", "MainActivity.head_orgi_x = "
				// + MainActivity.head_orgi_x);

				max.postTranslate(0 - MainActivity.head_orgi_x
						- Const.MODEL_HEAD_IMAGE_SIZE_W / 2, 0);
//				max.getValues(values);
//				x = values[Matrix.MTRANS_X];
//				y = values[Matrix.MTRANS_Y];
//
//				Log.e("max TAG 2", "x = " + x + " y = " + y);
			}
			canvas.drawBitmap(mgr.iUserHairData.iUserSelectHair.vurlImg, max,
					null);
		}

	}
}
