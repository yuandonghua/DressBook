package cn.dressbook.ui.face.data;

import android.graphics.Bitmap;
import cn.dressbook.ui.face.Const;
import cn.dressbook.ui.face.MainActivity;

public class UserPhotoData extends FCGestureData {

	/**
	 * ����
	 */
	private float mLightValue = 1F;

	/**
	 * ���Ͷ�
	 */
	private float mSaturationValue = 1F;

	/**
	 * ɫ��
	 */
	private float mLuminance = 1F;

	public float defaultMatrixX = 0.0f;

	public float eyePosX = 0.0f;

	/**
	 * SeekBar���м�ֵ
	 */
	public static final int MIDDLE_VALUE = 127;

	public Bitmap cameraBmp = null;
	public EyePoint leftEye = null;
	public EyePoint rightEye = null;

	public UserPhotoData() {
		super();
	}

	public void setDefaultMatrix() {

		mLightValue = 1F;
		mSaturationValue = 1F;
		mLuminance = 1F;
		SingletonDataMgr mgr = SingletonDataMgr.getInstance();
		FCModelXmlItem model = mgr.iModelBodyItem;

		if (model == null) {
			return;
		}

		if (leftEye == null) {
			leftEye = new EyePoint();
			leftEye.x = 50;
			leftEye.y = 100;
		}

		if (rightEye == null) {
			rightEye = new EyePoint();
			rightEye.x = 150;
			rightEye.y = 100;
		}

		float scale = (float) (model.getEyeDistance() * 1.0 / getEyeDistance());
		matrix.postScale(scale, scale, 0, 0);

		// if (FCTools.isNeedScaleBodyImg()) {
		// int origCenterX = MainActivity.head_orgi_x
		// + Const.MODEL_HEAD_IMAGE_SIZE_W / 2;
		// float newCenterX = (float) (cameraBmp.getWidth() / 2 * scale);
		// int origCenterY = model.left.y * 2 - Const.MODEL_HEAD_IMAGE_ORIG_Y;
		// // int origCenterY = model.left.y * 2 -
		// // Const.MODEL_HEAD_IMAGE_ORIG_Y
		// // * 2;
		// float newY = (float) (leftEye.y * scale);
		//
		// matrix.postTranslate(origCenterX - newCenterX,
		// Const.MODEL_HEAD_IMAGE_ORIG_Y + origCenterY - newY);
		// } else {
		int origCenterX = MainActivity.head_orgi_x
				+ Const.MODEL_HEAD_IMAGE_SIZE_W / 2;

		float newCenterX = (float) (cameraBmp.getWidth() / 2 * scale);

		int origCenterY = model.left.y - Const.MODEL_HEAD_IMAGE_ORIG_Y;
		float newY = (float) (leftEye.y * scale);

		eyePosX = (getCenterX() - cameraBmp.getWidth() / 2) * scale;

		defaultMatrixX = origCenterX - newCenterX - eyePosX;

		matrix.postTranslate(defaultMatrixX, Const.MODEL_HEAD_IMAGE_ORIG_Y
				+ origCenterY - newY);
		// }

	}

	public float getScaleBmpW() {
		float res = 0.0f;
		SingletonDataMgr mgr = SingletonDataMgr.getInstance();

		if (mgr.iModelBodyItem != null) {
			FCModelXmlItem model = mgr.iModelBodyItem;

			float scale = (float) (model.getEyeDistance() * 1.0 / getEyeDistance());
			res = (float) (cameraBmp.getWidth() * scale);
		}

		return res;
	}

	public float getCenterX() {
		float res = 1;
		res = leftEye.x + (rightEye.x - leftEye.x) / 2;
		if (res == 0) {
			res = 1.0f;
		}
		return res;
	}

	public float getEyeDistance() {
		float res = 1;
		res = rightEye.x - leftEye.x;
		if (res == 0) {
			res = 1.0f;
		}
		return res;
	}

	public float getmLightValue() {
		return mLightValue;
	}

	/**
	 * @description:设置亮度
	 * @exception
	 */
	public void setmLightValue(float lum) {
		this.mLightValue = (lum / 2 - MIDDLE_VALUE) * 1.0F / MIDDLE_VALUE * 180;
	}

	public float getmSaturationValue() {
		return mSaturationValue;
	}

	public void setmSaturationValue(float saturation) {
		mSaturationValue = saturation * 1.0F / MIDDLE_VALUE;
	}

	public float getmLuminance() {
		return mLuminance;
	}

	/**
	 * @description:调节亮度
	 * @exception
	 */
	public void setmLuminance(float hue) {
		this.mLuminance = hue * 1.0F / MIDDLE_VALUE;
	}

}
