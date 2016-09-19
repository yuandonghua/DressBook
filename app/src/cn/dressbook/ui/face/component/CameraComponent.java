package cn.dressbook.ui.face.component;

import android.content.Context;
import android.graphics.PointF;
import android.media.FaceDetector;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import cn.dressbook.ui.R;
import cn.dressbook.ui.face.MainActivity;
import cn.dressbook.ui.face.data.EyePoint;
import cn.dressbook.ui.face.data.SingletonDataMgr;
import cn.dressbook.ui.face.data.UserPhotoData;
import cn.dressbook.ui.face.view.FCCameraImageView;
import cn.dressbook.ui.face.view.FaceImageView;

public class CameraComponent extends BaseComponent {

	FaceImageView head;
	public FCCameraImageView body;

	// public Bitmap userBmp = null;

	LinearLayout btnbar;

	boolean bHasFocusEye = false;

	private static int MAX_FACES = 10;

	protected Handler handle = new Handler() {
		public void handleMessage(Message m) {
			body.invalidate();
			super.handleMessage(m);
		}
	};

	public CameraComponent(Context context2, MainActivity mainActivity) {
		super(context2, mainActivity);
		if (comLayout == null) {
			LayoutInflater mInflater = LayoutInflater.from(context2);
			comLayout = (RelativeLayout) mInflater.inflate(
					R.layout.camera_layout, null);

			head = (FaceImageView) comLayout.findViewById(R.id.imageHead3);
			body = (FCCameraImageView) comLayout.findViewById(R.id.imageBody3);
			body.BodyImageView_Bmp_Mode = 1;
			// HorizontalScrollView iHorizontalScrollView =
			// (HorizontalScrollView) comLayout
			// .findViewById(R.id.horizontalScrollView3);
			btnbar = (LinearLayout) comLayout.findViewById(R.id.linearLayout3);

			for (int i = 0; i < 3; i++) {
				Button btn = (Button) btnbar.findViewById(R.id.button1 + i);
				final int k = i;
				Button.OnTouchListener listener = new Button.OnTouchListener() {
					// @Override
					public boolean onTouch(View v, MotionEvent event) {

						// TODO Auto-generated method stub
						if (event.getAction() == MotionEvent.ACTION_DOWN) {
							// onClickToolbarButton(k);

						} else if (event.getAction() == MotionEvent.ACTION_UP) {
							onClickToolbarButton(k);
						}
						return false;
					}
				};
				btn.setOnTouchListener(listener);
			}

		}
	}

	public void clearBodyBmp() {
		if (body != null) {
			body.clearBodyBmp();
		}
	}

	protected void onClickToolbarButton(int k) {
		// TODO Auto-generated method stub
		Log.e("Camera", "pressed " + k);
		switch (k) {
		// case 0: {
		// mainActivity.showComponnet(BaseComponent.MERGE_COMPONENT);
		// break;
		// }
		case 1: {
			mainActivity.startCamera();
			break;
		}
		case 0: {
			mainActivity.startSelectImg();
			break;
		}
		// case 3: {
		// body.setDefaultEye();
		// // Ĭ��λ��
		// break;
		// }
		case 2: {
			UserPhotoData photo = new UserPhotoData();
			photo.cameraBmp = body.bmp;
			photo.leftEye = body.leftEye;
			photo.rightEye = body.RightEye;
			photo.setDefaultMatrix();
			SingletonDataMgr mgr = SingletonDataMgr.getInstance();
			mgr.iUserPhotoData = photo;
			mainActivity.showComponnet(BaseComponent.MERGE_COMPONENT);
			break;
		}
		default:
			break;
		}
	}

	@Override
	public View show(Context con) {
		refreshComponent();
		return comLayout;
	}

	public void refreshComponent() {
		SingletonDataMgr mgr = SingletonDataMgr.getInstance();
		// if (mgr.iUserSelectHairItem != null) {
		// head.setBmp(mgr.iUserSelectHairItem.vurlImg);
		// }
		if (mgr.iUserHairData != null) {
			head.setBmp(mgr.iUserHairData.iUserSelectHair.vurlImg);
		}
		body.invalidate();
		head.invalidate();
		focusEyes();
	}

	/**
	 * 
	 * @author SKY
	 */
	public void focusEyes() {

		if (SingletonDataMgr.getInstance().iCurUserSelectPhotoBmp == null) {
			return;
		}

		mainActivity.showLoading();
		body.setBmp(SingletonDataMgr.getInstance().iCurUserSelectPhotoBmp);
		body.BodyImageView_Bmp_Mode = 2;
		head.setVisibility(View.INVISIBLE);
		Thread t = new Thread() {

			Message m = new Message();

			public void run() {
				setFace();

				CameraComponent.this.handle.sendMessage(m);
			}
		};
		t.start();
		// �����Դ���
		SingletonDataMgr.getInstance().iCurUserSelectPhotoBmp = null;

	}

	protected void setFace() {
		FaceDetector fd;
		FaceDetector.Face[] faces = new FaceDetector.Face[MAX_FACES];
		PointF eyescenter = new PointF();
		float eyesdist = 0.0f;
		int[] fpx = null;
		int[] fpy = null;
		int count = 0;

		try {
			fd = new FaceDetector(body.bmp.getWidth(), body.bmp.getHeight(),
					MAX_FACES);
			count = fd.findFaces(body.bmp, faces);
		} catch (Exception e) {
			Log.e("ERROR", "setFace(): " + e.toString());
			mainActivity.hideLoading();
			return;
		}

		// check if we detect any faces
		if (count > 0) {
			fpx = new int[count * 2];
			fpy = new int[count * 2];

			for (int i = 0; i < 1; i++) {
				try {
					faces[i].getMidPoint(eyescenter);
					eyesdist = faces[i].eyesDistance();

					// set up left eye location
					fpx[2 * i] = (int) (eyescenter.x - eyesdist / 2);
					fpy[2 * i] = (int) eyescenter.y;

					EyePoint pL = new EyePoint();
					pL.x = fpx[0];
					pL.y = fpy[0];
					body.setLeftEye(pL);
					// body.leftEye = pL;
					// body.defaultLeftEye = pL;

					// set up right eye location
					fpx[2 * i + 1] = (int) (eyescenter.x + eyesdist / 2);
					fpy[2 * i + 1] = (int) eyescenter.y;

					EyePoint pR = new EyePoint();
					pR.x = fpx[1];
					pR.y = fpy[1];
					body.setRightEye(pR);
					// body.RightEye = pR;
					// body.defaultRightEye = pR;

				} catch (Exception e) {
					Log.e("ERROR", "setFace(): face " + i + ": " + e.toString());
				}
			}
		}

		mainActivity.hideLoading();

		// mIV.setDisplayPoints(fpx, fpy, count * 2, 1);
	}
}
