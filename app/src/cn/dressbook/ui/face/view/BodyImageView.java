package cn.dressbook.ui.face.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import cn.dressbook.ui.face.Const;
import cn.dressbook.ui.face.data.SingletonDataMgr;
import cn.dressbook.ui.face.tools.FCTools;

public class BodyImageView extends FCBaseImgView {

	// 0, ��ͷ�ͣ� 1.�������գ� 2�� �ϳ�, 3,��ʾ��Ƭ
	public int BodyImageView_Bmp_Mode = -1;

	public BodyImageView(Context context) {
		super(context);
	}

	public BodyImageView(Context context, AttributeSet attr) {
		super(context, attr);
	}

	public void clearBodyBmp() {
		if (BodyImageView_Bmp_Mode == 1 || BodyImageView_Bmp_Mode == 2) {
			if (bmp != null) {
				bmp.recycle();
				bmp = null;
			}
		}
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.drawColor(Color.TRANSPARENT);

		int h = 450;

//		if (bmp == null) {
//
//			switch (BodyImageView_Bmp_Mode) {
//			case 0: {
//				SingletonDataMgr mgr = SingletonDataMgr.getInstance();
//				if (FCTools.isNeedScaleBodyImg()) {
//					if (mgr.iStep1Item != null
//							&& mgr.iStep1Item.vurlImg != null) {
//						bmp = FCTools.getBodyScaleBMP(mgr.iStep1Item.vurlImg,
//								MainActivity.screenWidth, this.getHeight());
//					}
//				} else {
//					if (mgr.iStep1Item != null) {
//						bmp = mgr.iStep1Item.vurlImg;
//					}
//				}
//				// bmp = mgr.iStep1Item.vurlImg;
//				break;
//			}
//			// case 1: {
//			// SingletonDataMgr mgr = SingletonDataMgr.getInstance();
//			// if (mgr.iModelBodyItem != null) {
//			// bmp = mgr.iModelBodyItem.furlImg;
//			// }
//			// break;
//			// }
//			case 1: {
//				if (FCTools.isNeedScaleBodyImg()) {
//					SingletonDataMgr mgr = SingletonDataMgr.getInstance();
//					if (mgr.iModelBodyItem != null
//							&& mgr.iModelBodyItem.furlImg != null) {
//						// bmp = mgr.iModelBodyItem.vurlImg;
//						bmp = FCTools.getBodyScaleBMP(
//								mgr.iModelBodyItem.furlImg,
//								MainActivity.screenWidth, this.getHeight());
//					}
//				} else {
//					SingletonDataMgr mgr = SingletonDataMgr.getInstance();
//					if (mgr.iModelBodyItem != null) {
//						bmp = mgr.iModelBodyItem.furlImg;
//					}
//				}
//				break;
//
//			}
//			case 2: {
//				SingletonDataMgr mgr = SingletonDataMgr.getInstance();
//				if (FCTools.isNeedScaleBodyImg()) {
//					if (mgr.iModelBodyItem != null
//							&& mgr.iModelBodyItem.vurlImg != null) {
//						// bmp = mgr.iModelBodyItem.vurlImg;
//						bmp = FCTools.getBodyScaleBMP(
//								mgr.iModelBodyItem.vurlImg,
//								MainActivity.screenWidth, this.getHeight());
//					}
//				} else {
//					if (mgr.iModelBodyItem != null) {
//						bmp = mgr.iModelBodyItem.vurlImg;
//					}
//				}
//
//				break;
//			}
//			case 3: {
//				// SingletonDataMgr mgr = SingletonDataMgr.getInstance();
//				// if (mgr.iUserPhotoData != null) {
//				// bmp = mgr.iUserPhotoData.cameraBmp;
//				// }
//			}
//			}
//		}

		if (BodyImageView_Bmp_Mode == 3) {

			SingletonDataMgr mgr = SingletonDataMgr.getInstance();
			if (mgr.iUserPhotoData != null
					&& mgr.iUserPhotoData.cameraBmp != null) {

				Paint paint = new Paint(); // �½�paint
				paint.setAntiAlias(true); // ���ÿ����,Ҳ���Ǳ�Ե��ƽ������

				ColorMatrix aAllMatrix = new ColorMatrix();

				ColorMatrix mHueMatrix = new ColorMatrix();
				mHueMatrix.reset();
				mHueMatrix.setScale(mgr.iUserPhotoData.getmLuminance(),
						mgr.iUserPhotoData.getmLuminance(),
						mgr.iUserPhotoData.getmLuminance(), 1);

				ColorMatrix mSaturationMatrix = new ColorMatrix();
				mSaturationMatrix.reset();
				mSaturationMatrix.setSaturation(mgr.iUserPhotoData
						.getmSaturationValue());

				ColorMatrix mLightnessMatrix = new ColorMatrix(); // ������ɫ�任�ľ���androidλͼ��ɫ�仯������Ҫ�ǿ��ö������
				// rix.reset(); //
				// ��ΪĬ��ֵ
				mLightnessMatrix.setRotate(0,
						mgr.iUserPhotoData.getmLightValue()); // �����ú�ɫ����ɫ������ת�ĽǶ�
				mLightnessMatrix.setRotate(1,
						mgr.iUserPhotoData.getmLightValue()); // �������̺�ɫ����ɫ������ת�ĽǶ�
				mLightnessMatrix.setRotate(2,
						mgr.iUserPhotoData.getmLightValue()); // ��������ɫ����ɫ������ת�ĽǶ�
				aAllMatrix.reset();

				aAllMatrix.postConcat(mLightnessMatrix);
				aAllMatrix.postConcat(mHueMatrix);
				aAllMatrix.postConcat(mSaturationMatrix); // Ч������
				paint.setColorFilter(new ColorMatrixColorFilter(aAllMatrix));// ������ɫ�任Ч��

				Matrix max = new Matrix(mgr.iUserPhotoData.matrix);

				if (FCTools.isNeedScaleBodyImg()) {

					float[] values = new float[9];

					Log.e("TAG aaaa", "mgr.iUserPhotoData.defaultMatrixX="
							+ mgr.iUserPhotoData.defaultMatrixX
							+ " Const.MODEL_HEAD_IMAGE_SIZE_W / 2="
							+ Const.MODEL_HEAD_IMAGE_SIZE_W / 2);
					Log.e("eyePosX", "eyePosX = " + mgr.iUserPhotoData.eyePosX);

					max.getValues(values);
					float x = values[Matrix.MTRANS_X];
					float y = values[Matrix.MTRANS_Y];
					Log.e("max TAG 111", "x = " + x + " y = " + y);
					max.postScale(2.0f, 2.0f);

					max.getValues(values);
					x = values[Matrix.MTRANS_X];
					y = values[Matrix.MTRANS_Y];
					Log.e("max TAG 222", "x = " + x + " y = " + y);

					// max.postTranslate(0 - mgr.iUserPhotoData.defaultMatrixX
					// - Const.MODEL_HEAD_IMAGE_SIZE_W / 2, 0);
					max.postTranslate(0 - mgr.iUserPhotoData.defaultMatrixX
							- mgr.iUserPhotoData.getScaleBmpW() / 2
							- mgr.iUserPhotoData.eyePosX, 0);

					max.getValues(values);
					x = values[Matrix.MTRANS_X];
					y = values[Matrix.MTRANS_Y];
					Log.e("max TAG 333", "x = " + x + " y = " + y);
					// max.postTranslate(0 - mgr.iUserPhotoData.defaultMatrixX
					// - Const.MODEL_HEAD_IMAGE_SIZE_W / 2, 0);
				}
				if (mgr.iUserPhotoData.cameraBmp == null
						|| mgr.iUserPhotoData.cameraBmp.isRecycled()) {
					return;
				}
				canvas.drawBitmap(mgr.iUserPhotoData.cameraBmp, max, paint);
			}

			return;
		}

//		if ((BodyImageView_Bmp_Mode == 2 || BodyImageView_Bmp_Mode == 1 || BodyImageView_Bmp_Mode == 0)
//				&& FCTools.isNeedScaleBodyImg()) {
//			if (bmp != null) {
//				Matrix ma = new Matrix();
//				ma.postScale(2.0f, 2.0f);
//
//				int bmpw = bmp.getWidth() * 2;
//				int sW = MainActivity.screenWidth;
//
//				if (sW > bmpw) {
//					int posX = (sW - bmpw) / 2;
//					ma.postTranslate(posX, 0);
//				}
//
//				canvas.drawBitmap(bmp, ma, mPaint);
//			}
//
//			return;
//		}
//
//		if (BodyImageView_Bmp_Mode == 0) {
//			if (bmp != null) {
//
//				{
//					Rect dst = new Rect(0, 0, MainActivity.screenWidth, h);
//					Rect src = new Rect(
//							MainActivity.body_orgi_x,
//							0,
//							MainActivity.screenWidth + MainActivity.body_orgi_x,
//							h);
//					canvas.drawBitmap(bmp, src, dst, mPaint);
//				}
//
//			}
//			return;
//		}

//		if (bmp != null) {
//			LogUtils.e("bmp不为空-------------");
//			Rect dst = new Rect(0, 0, MainActivity.screenWidth, h);
//			Rect src = new Rect(MainActivity.body_orgi_x, 0,
//					MainActivity.screenWidth + MainActivity.body_orgi_x, h);
//			canvas.drawBitmap(bmp, src, dst, mPaint);
//			// Matrix matrix = new Matrix();
//			// matrix.postScale(2.0f, 2.0f);
//			// matrix.postTranslate(MainActivity.body_orgi_x * 2, 0);
//			// canvas.drawBitmap(bmp, matrix, mPaint);
//
//		}

	}
}
