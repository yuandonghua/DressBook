package cn.dressbook.ui.face.component;

import android.content.Context;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import cn.dressbook.ui.R;
import cn.dressbook.ui.face.MainActivity;
import cn.dressbook.ui.face.data.SingletonDataMgr;
import cn.dressbook.ui.face.tools.MatrixTools;
import cn.dressbook.ui.face.view.BodyImageView;
import cn.dressbook.ui.face.view.FCGestureView;
import cn.dressbook.ui.face.view.FCHairView;
import cn.dressbook.ui.face.view.IGestureViewIml;

public class MergeComponent extends BaseComponent implements IGestureViewIml,
		OnSeekBarChangeListener {

	FCHairView hair;
	BodyImageView camera;
	BodyImageView body;

	LinearLayout btnbar;

	OnTouchListener listener[];

	FCGestureView gestureView = null;

	RelativeLayout RelativeLayout_weitiao2 = null;

	SeekBar hue = null;
	TextView hueText = null;
	SeekBar light = null;
	SeekBar saturation = null;
	TextView lightText = null;
	TextView saturationText = null;

	public MergeComponent(Context context2, MainActivity mainActivity) {
		super(context2, mainActivity);

		if (comLayout == null) {
			LayoutInflater mInflater = LayoutInflater.from(context2);
			comLayout = (RelativeLayout) mInflater.inflate(
					R.layout.merge_layout, null);

			hair = (FCHairView) comLayout.findViewById(R.id.imageHead4);
			camera = (BodyImageView) comLayout.findViewById(R.id.imageUser);
			camera.BodyImageView_Bmp_Mode = 3;
			body = (BodyImageView) comLayout.findViewById(R.id.imageBody4);
			body.BodyImageView_Bmp_Mode = 2;

			gestureView = (FCGestureView) comLayout
					.findViewById(R.id.FCGestureView2);
			gestureView.FCGestureViewMode = FCGestureView.K_FC_GESTURE_PHOTO_MODE;
			gestureView.iml = this;

			RelativeLayout_weitiao2 = (RelativeLayout) comLayout
					.findViewById(R.id.RelativeLayout_weitiao2);
			RelativeLayout_weitiao2.setOnTouchListener(new OnTouchListener() {
				public boolean onTouch(View arg0, MotionEvent arg1) {
					return true;
				}

			});

			hue = (SeekBar) comLayout.findViewById(R.id.seekBar3);
			light = (SeekBar) comLayout.findViewById(R.id.seekBar1);
			saturation = (SeekBar) comLayout.findViewById(R.id.seekBar2);
			hueText = (TextView) comLayout.findViewById(R.id.textViewseekBar3);
			lightText = (TextView) comLayout
					.findViewById(R.id.textViewseekBar1);
			saturationText = (TextView) comLayout
					.findViewById(R.id.textViewseekBar2);
			light.setOnSeekBarChangeListener(this);
			hue.setOnSeekBarChangeListener(this);
			saturation.setOnSeekBarChangeListener(this);

		}
	}

	protected void onClickToolbarButton(int k) {
		SingletonDataMgr mgr = SingletonDataMgr.getInstance();
		if (mgr.iUserPhotoData == null || mgr.iUserPhotoData.cameraBmp == null) {
			return;
		}
		// TODO Auto-generated method stub
		switch (k) {
		case 0: {
			// ��
			Matrix matrix1 = new Matrix();
			matrix1.set(mgr.iUserPhotoData.matrix);
			matrix1.postTranslate(0, -2);
			mgr.iUserPhotoData.matrix.set(matrix1);
			camera.postInvalidate();
			break;
		}
		case 1: {
			// ��
			mgr.iUserPhotoData.matrix.postTranslate(0, 2);
			camera.postInvalidate();
			break;
		}
		case 2: {
			// ��
			mgr.iUserPhotoData.matrix.postTranslate(-2, 0);
			camera.postInvalidate();
			break;
		}
		case 3: {
			// ��
			mgr.iUserPhotoData.matrix.postTranslate(2, 0);
			camera.postInvalidate();
			break;
		}
		case 4: {
			// �Ŵ�

			MatrixTools.postScale(mgr.iUserPhotoData.matrix, 1.05f,
					mgr.iUserPhotoData.cameraBmp);
			camera.postInvalidate();
			break;
		}
		case 5: {
			// ��С
			// mgr.iUserPhotoData.matrix.preScale(0.95f, 0.95f);
			MatrixTools.postScale(mgr.iUserPhotoData.matrix, 0.95f,
					mgr.iUserPhotoData.cameraBmp);
			camera.postInvalidate();
			break;
		}
		case 6: {
			// ����ת
			mgr.iUserPhotoData.matrix.preRotate(0.5f);
			camera.postInvalidate();
			break;
		}
		case 7: {
			// ����ת
			mgr.iUserPhotoData.matrix.preRotate(-0.5f);
			camera.postInvalidate();
			break;
		}
		default:
			break;
		}
	}

	public void clearBodyBmp() {
		if (body != null) {
			body.clearBodyBmp();
		}
	}

	public View show(Context con) {
		refreshComponent();
		return comLayout;
	}

	public void refreshComponent() {
		SingletonDataMgr mgr = SingletonDataMgr.getInstance();
		// �޸�
		// if (mgr.iUserSelectHairItem != null) {
		// head.setBmp(mgr.iUserSelectHairItem.vurlImg);
		// }
//		hair.invalidate();
		camera.invalidate();
	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		// camera.BodyImageView_Bmp_Mode = 4;
		refreshComponent();
	}

	@Override
	public void gestureTaped() {
		// TODO Auto-generated method stub
		if (RelativeLayout_weitiao2.getVisibility() == View.INVISIBLE) {
			RelativeLayout_weitiao2.setVisibility(View.VISIBLE);
		} else {
			RelativeLayout_weitiao2.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
		// TODO Auto-generated method stub
		SingletonDataMgr mgr = SingletonDataMgr.getInstance();

		if (mgr.iUserPhotoData == null || mgr.iUserPhotoData.cameraBmp == null) {
			return;
		}

		// TODO Auto-generated method stub
		if (arg0.equals(light)) {
			// mgr.iUserPhotoData.mLightValue = progress * 1.0F
			// / UserPhotoData.MIDDLE_VALUE;
			mgr.iUserPhotoData.setmLightValue(progress);
			camera.postInvalidate();
			int aa = (progress * 200 / 255) - 100;
			if (aa == 0) {
				lightText.setText("0");
			} else if (aa > 0) {
				lightText.setText("+" + aa);
			} else if (aa < 0) {
				lightText.setText("" + aa);
			}

		} else if (arg0.equals(saturation)) {
			// mgr.iUserPhotoData.mSaturationValue = progress * 1.0F
			// / UserPhotoData.MIDDLE_VALUE;
			mgr.iUserPhotoData.setmSaturationValue(progress);
			camera.postInvalidate();
			int aa = (progress * 200 / 255) - 100;
			if (aa == 0) {
				saturationText.setText("0");
			} else if (aa > 0) {
				saturationText.setText("+" + aa);
			} else if (aa < 0) {
				saturationText.setText("" + aa);
			}
		} else if (arg0.equals(hue)) {
			mgr.iUserPhotoData.setmLuminance(progress);
			camera.postInvalidate();
			int aa = (progress * 200 / 255) - 100;
			if (aa == 0) {
				hueText.setText("0");
			} else if (aa > 0) {
				hueText.setText("+" + aa);
			} else if (aa < 0) {
				hueText.setText("" + aa);
			}
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub

	}

}
