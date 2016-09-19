package cn.dressbook.ui.face.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import cn.dressbook.ui.face.Const;
import cn.dressbook.ui.face.MainActivity;
import cn.dressbook.ui.face.tools.FCTools;

public class FaceImageView extends FCBaseImgView {

	boolean test = false;

	public FaceImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private void ini() {
		// TODO Auto-generated method stub
		mPaint = new Paint();
	}

	public void setBmp(Bitmap abmp) {
		this.bmp = abmp;
		this.invalidate();
	}

	public FaceImageView(Context context, AttributeSet attr) {
		super(context, attr);
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.drawColor(Color.TRANSPARENT);

		// if (bmp == null) {
		// InputStream is = null;
		// try {
		// // String s = imagesStr[position];
		//
		// // int positionIndex = position % iStrArrayList.size();
		//
		// String s = "head.png";
		// is = facecontext.getAssets().open(s);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		//
		// bmp = BitmapFactory.decodeStream(is);
		// }

		if (bmp != null) {

			if (FCTools.isNeedScaleBodyImg()) {
				Matrix max = new Matrix();
				max.postScale(2.0f, 2.0f);
				max.postTranslate(MainActivity.head_orgi_x
						- Const.MODEL_HEAD_IMAGE_SIZE_W / 2,
						Const.MODEL_HEAD_IMAGE_ORIG_Y * 2);
				canvas.drawBitmap(bmp, max, mPaint);

			} else {
				if (test) {
					canvas.drawBitmap(bmp, 0, 50, mPaint);
					return;
				}
				canvas.drawBitmap(bmp, MainActivity.head_orgi_x,
						Const.MODEL_HEAD_IMAGE_ORIG_Y, mPaint);
			}

		}

	}

	public void test() {
		// TODO Auto-generated method stub
		test = true;
		this.invalidate();

	}

}
