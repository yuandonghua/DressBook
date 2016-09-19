package cn.dressbook.ui.face.data;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import cn.dressbook.ui.PhotoCropActivity;


public class SingletonDataMgr {
	/*
	 * The volatile keyword ensures that multiple threads handle the
	 * uniqueInstance variable correctly when it is being initialized to the
	 * Singleton instance.
	 */

	public int iComponentH = 0;
	public int iComponentW = 0;

	private volatile static SingletonDataMgr uniqueInstance;

	public FCBaseXmlItem iStep1Item = null;

	public FCBaseXmlItem iDefaultHeadItem = null;
	public FCBaseXmlItem iUserSelectHeadItem = null;
	// public FCBaseXmlItem iUserSelectHairItem = null;

	public UserHairData iUserHairData = null;

	public FCModelXmlItem iModelBodyItem = null;

	public ArrayList<Headcls> iHeadClsList = null;

	public ArrayList<FCBaseXmlItem> iHairItemList = null;

	public UserPhotoData iUserPhotoData = null;

	public int iCurDisplayComponentMode = -1;

	public Bitmap iCurUserSelectPhotoBmp = null;

	private SingletonDataMgr() {
	}

	public static SingletonDataMgr getInstance() {

		if (uniqueInstance == null) {

			synchronized (SingletonDataMgr.class) {

				if (uniqueInstance == null) {

					uniqueInstance = new SingletonDataMgr();
				}
			}
		}
		return uniqueInstance;
	}

	public void onDestroy() {
		// clealAll();
	}

	public void clearBodyData() {
		if (iUserHairData != null) {
			iUserHairData.onDestroy();
			iUserHairData = null;
		}

		if (iHairItemList != null) {
			for (int i = 0; i < iHairItemList.size(); i++) {
				FCBaseXmlItem item = iHairItemList.get(i);
				item.onDestroy();
			}
			// iHairItemList.re

			while (iHairItemList.size() >= 1) {
				iHairItemList.remove(0);
			}
			// iHairItemList = null;
		}

		iHairItemList = null;

		if (iModelBodyItem != null) {
			iModelBodyItem.onDestroy();
			iModelBodyItem = null;
		}

	}

	// ��Ҫ�޸� Bitmap��Ҫ�ͷ�
	public void clealAll() {
		if (iCurUserSelectPhotoBmp != null) {
			iCurUserSelectPhotoBmp.recycle();
			iCurUserSelectPhotoBmp = null;
		}
		iCurDisplayComponentMode = -1;
		if (iUserHairData != null) {
			iUserHairData.onDestroy();
			iUserHairData = null;
		}

		if (iHairItemList != null) {
			for (int i = 0; i < iHairItemList.size(); i++) {
				FCBaseXmlItem item = iHairItemList.get(i);
				item.onDestroy();
			}
			// iHairItemList.re

			while (iHairItemList.size() >= 1) {
				iHairItemList.remove(0);
			}
			// iHairItemList = null;
		}

		iHairItemList = null;

		if (iModelBodyItem != null) {
			iModelBodyItem.onDestroy();
			iModelBodyItem = null;
		}

		if (iStep1Item != null) {
			iStep1Item.onDestroy();
			iStep1Item = null;
		}
		iHeadClsList = null;

		if (iDefaultHeadItem != null) {
			iDefaultHeadItem.onDestroy();
			iDefaultHeadItem = null;
		}

		if (iUserSelectHeadItem != null) {
			iUserSelectHeadItem.onDestroy();
			iUserSelectHeadItem = null;
		}
	}

	public String getDefaultHeadImgUrl() {
		String res = "";

		if (iHeadClsList.size() > 0) {
			Headcls cls = iHeadClsList.get(0);
			if (cls.headList.size() > 0) {
				FCBaseXmlItem item = cls.headList.get(0);
				res = item.vurl;
				iDefaultHeadItem = item;
			}
		}

		return res;
	}

	public boolean canMakeModelBmp() {
		boolean res = true;

		if (iModelBodyItem == null || iModelBodyItem.surlImg == null) {
			res = false;
		}

		if (iUserPhotoData == null || iUserPhotoData.cameraBmp == null) {
			res = false;
		}

		if (iUserHairData == null
				|| iUserHairData.iUserSelectHair.vurlImg == null) {
			res = false;
		}

		return res;
	}

	// public Bitmap getUpdateBmp() {
	// Bitmap newb = Bitmap.createBitmap(
	// this.iModelBodyItem.surlImg.getWidth(),
	// this.iModelBodyItem.surlImg.getHeight(), Config.ARGB_8888);//
	// // ����һ���µĺ�SRC���ȿ��һ����λͼ
	// Canvas canvas = new Canvas(newb);
	// canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
	// | Paint.FILTER_BITMAP_FLAG)); // �����
	//
	// Paint paint = new Paint(); // �½�paint
	// paint.setAntiAlias(true); // ���ÿ����,Ҳ���Ǳ�Ե��ƽ������
	//
	// ColorMatrix aAllMatrix = new ColorMatrix();
	//
	// ColorMatrix mHueMatrix = new ColorMatrix();
	// mHueMatrix.reset();
	// mHueMatrix
	// .setScale(this.iUserPhotoData.mHueValue,
	// this.iUserPhotoData.mHueValue,
	// this.iUserPhotoData.mHueValue, 1);
	//
	// ColorMatrix mSaturationMatrix = new ColorMatrix();
	// mSaturationMatrix.reset();
	// mSaturationMatrix.setSaturation(this.iUserPhotoData.mSaturationValue);
	//
	// ColorMatrix mLightnessMatrix = new ColorMatrix(); //
	// ������ɫ�任�ľ���androidλͼ��ɫ�仯������Ҫ�ǿ��ö������
	// // rix.reset(); //
	// // ��ΪĬ��ֵ
	// mLightnessMatrix.setRotate(0, this.iUserPhotoData.mLightValue); //
	// �����ú�ɫ����ɫ������ת�ĽǶ�
	// mLightnessMatrix.setRotate(1, this.iUserPhotoData.mLightValue); //
	// �������̺�ɫ����ɫ������ת�ĽǶ�
	// mLightnessMatrix.setRotate(2, this.iUserPhotoData.mLightValue);
	// //��������ɫ����ɫ������ת�ĽǶ�
	//
	// aAllMatrix.reset();
	// aAllMatrix.postConcat(mHueMatrix);
	// aAllMatrix.postConcat(mSaturationMatrix); // Ч������
	// paint.setColorFilter(new ColorMatrixColorFilter(aAllMatrix));// ������ɫ�任Ч��
	//
	// canvas.drawBitmap(this.iUserPhotoData.cameraBmp,
	// this.iUserPhotoData.matrix, paint);
	//
	// canvas.drawBitmap(iModelBodyItem.surlImg, 0, 0, paint);
	//
	// canvas.drawBitmap(this.iUserHairData.iUserSelectHair.vurlImg,
	// this.iUserHairData.matrix, null);
	//
	// canvas.save(Canvas.ALL_SAVE_FLAG);
	// canvas.restore();
	//
	// return newb;
	// }

	public Bitmap getUpdateBmp() {

		int w = PhotoCropActivity.parentWidth;
		int h = PhotoCropActivity.parentWidth;
		Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// ����һ���µĺ�SRC���ȿ��һ����λͼ
		Canvas canvas = new Canvas(newb);
		canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
				| Paint.FILTER_BITMAP_FLAG)); // �����

		Paint paint = new Paint(); // �½�paint
		paint.setAntiAlias(true); // ���ÿ����,Ҳ���Ǳ�Ե��ƽ������

		ColorMatrix aAllMatrix = new ColorMatrix();

		ColorMatrix mHueMatrix = new ColorMatrix();
		mHueMatrix.reset();
		mHueMatrix.setScale(this.iUserPhotoData.getmLuminance(),
				this.iUserPhotoData.getmLuminance(),
				this.iUserPhotoData.getmLuminance(), 1);

		ColorMatrix mSaturationMatrix = new ColorMatrix();
		mSaturationMatrix.reset();
		mSaturationMatrix.setSaturation(this.iUserPhotoData
				.getmSaturationValue());

		ColorMatrix mLightnessMatrix = new ColorMatrix(); // ������ɫ�任�ľ���androidλͼ��ɫ�仯������Ҫ�ǿ��ö������
		// rix.reset(); //
		// ��ΪĬ��ֵ
		mLightnessMatrix.setRotate(0, this.iUserPhotoData.getmLightValue()); // �����ú�ɫ����ɫ������ת�ĽǶ�
		mLightnessMatrix.setRotate(1, this.iUserPhotoData.getmLightValue()); // �������̺�ɫ����ɫ������ת�ĽǶ�
		mLightnessMatrix.setRotate(2, this.iUserPhotoData.getmLightValue()); // ��������ɫ����ɫ������ת�ĽǶ�

		aAllMatrix.reset();
		aAllMatrix.postConcat(mHueMatrix);
		aAllMatrix.postConcat(mLightnessMatrix);
		aAllMatrix.postConcat(mSaturationMatrix); // Ч������
		paint.setColorFilter(new ColorMatrixColorFilter(aAllMatrix));// ������ɫ�任Ч��

		Matrix cameraM = new Matrix(this.iUserPhotoData.matrix);
//		cameraM.postTranslate(MainActivity.body_orgi_x, 0);

		canvas.drawBitmap(this.iUserPhotoData.cameraBmp, cameraM, paint);

		// 0828
		// canvas.drawBitmap(iModelBodyItem.surlImg, 0, 0, null);

		// Matrix hair = new Matrix(this.iUserHairData.matrix);
		// // ����ƫ����
		// hair.postTranslate(MainActivity.body_orgi_x, 0);

		// canvas.drawBitmap(this.iUserHairData.iUserSelectHair.vurlImg,
		// hair,
		// null);

		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();

		return newb;

	}
}
