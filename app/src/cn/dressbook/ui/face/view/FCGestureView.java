package cn.dressbook.ui.face.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import cn.dressbook.ui.R;
import cn.dressbook.ui.face.data.SingletonDataMgr;
import cn.dressbook.ui.face.tools.MatrixTools;

public class FCGestureView extends View {

	private GestureDetector mGestureDetector;

	public IGestureViewIml iml = null;

	PointF mid = new PointF();

	float x_down = 0;
	float y_down = 0;

	public Context facecontext;

	float oldDist = 1f;
	float oldRotation = 0;

	public static int K_FC_GESTURE_HAIR_MODE = 0;
	public static int K_FC_GESTURE_PHOTO_MODE = 1;

	public int FCGestureViewMode = K_FC_GESTURE_HAIR_MODE;

	private static final int NONE = 0;
	private static final int DRAG = 1;
	private static final int ZOOM = 2;
	int mode = NONE;

	Matrix savedMatrix = new Matrix();
	Matrix matrix1 = new Matrix();

	boolean matrixCheck = false;

	Bitmap gintama;

	public FCGestureView(Context context) {
		super(context);
		this.facecontext = context;
		ini();
		// TODO Auto-generated constructor stub
	}

	public FCGestureView(Context context, AttributeSet attr) {
		super(context, attr);
		this.facecontext = context;
		ini();
	}

	private void ini() {
		// TODO Auto-generated method stub
		gintama = BitmapFactory.decodeResource(getResources(),
				R.drawable.logo6);

		mGestureDetector = new GestureDetector(facecontext,
				new GestureDetector.SimpleOnGestureListener() {

					public boolean onDown(MotionEvent arg0) {
						return false;
					}

					public boolean onFling(MotionEvent arg0, MotionEvent arg1,
							float arg2, float arg3) {
						// TODO Auto-generated method stub
						return false;
					}

					public void onLongPress(MotionEvent arg0) {
						// TODO Auto-generated method stub

					}

					public boolean onScroll(MotionEvent arg0, MotionEvent arg1,
							float arg2, float arg3) {
						return false;
					}

					public void onShowPress(MotionEvent arg0) {

					}

					public boolean onSingleTapUp(MotionEvent e) {
						// Toast.makeText(facecontext,
						// "SINGLE CONF " + e.getAction(),
						// Toast.LENGTH_SHORT).show();
						iml.gestureTaped();

						return false;
					}

				});

	}

	protected void onDraw(Canvas canvas) {
		// canvas.save();
		//
		// SingletonDataMgr mgr = SingletonDataMgr.getInstance();
		// if (mgr.iUserHairData != null
		// && mgr.iUserHairData.iUserSelectHair != null) {
		// canvas.drawBitmap(gintama, mgr.iUserHairData.matrix, null);
		// }
		//
		// canvas.restore();
	}

	public boolean onTouchEvent(MotionEvent event) {

		Matrix mgrMatrix = null;
		Bitmap bmp = null;

		boolean bGesture = mGestureDetector.onTouchEvent(event);

		if (FCGestureViewMode == K_FC_GESTURE_HAIR_MODE) {
			SingletonDataMgr mgr = SingletonDataMgr.getInstance();
			if (mgr.iUserHairData == null
					|| mgr.iUserHairData.iUserSelectHair == null) {
				return true;
			} else {
				mgrMatrix = mgr.iUserHairData.matrix;
				if (mgr.iUserHairData.iUserSelectHair != null) {
					bmp = mgr.iUserHairData.iUserSelectHair.vurlImg;
				}
			}
		} else if (K_FC_GESTURE_PHOTO_MODE == FCGestureViewMode) {
			SingletonDataMgr mgr = SingletonDataMgr.getInstance();
			if (mgr.iUserPhotoData == null) {
				return true;
			} else {
				mgrMatrix = mgr.iUserPhotoData.matrix;
				bmp = mgr.iUserPhotoData.cameraBmp;
				// Log.e("touch", "YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
				// // mgrMatrix.postScale(2, 10);
				// mgr.iUserPhotoData.matrix.postTranslate(0, 0);
				//
				// if (iml != null) {
				// iml.refreshView();
				// }
				// return true;
			}
		}

		if (bmp == null) {
			return true;
		}

		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			mode = DRAG;
			x_down = event.getX();
			y_down = event.getY();
			savedMatrix.set(mgrMatrix);
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			mode = ZOOM;
			oldDist = spacing(event);
			oldRotation = rotation(event);
			savedMatrix.set(mgrMatrix);
			midPoint(mid, event);
			break;
		case MotionEvent.ACTION_MOVE:
			if (mode == ZOOM) {
				matrix1.set(savedMatrix);
				float rotation = rotation(event) - oldRotation;
				float newDist = spacing(event);
				float scale = newDist / oldDist;
				// matrix1.postScale(scale, scale, mid.x, mid.y);// �s��

				// MatrixTools.postScale(matrix1, scale, mid.x, mid.y, bmp);
				MatrixTools.postScale(matrix1, scale, 0.0f, 0.0f, bmp);

				matrix1.postRotate(rotation, mid.x, mid.y);// ���D matrixCheck =
				matrixCheck();
				if (matrixCheck == false) {
					mgrMatrix.set(matrix1);
					if (iml != null) {
						iml.refreshView();
					}
					invalidate();
				}
			} else if (mode == DRAG) {
				matrix1.set(savedMatrix);
				matrix1.postTranslate(event.getX() - x_down, event.getY()
						- y_down);// ƽ�� matrixCheck = matrixCheck(); matrixCheck
									// =
				matrixCheck();
				if (matrixCheck == false) {
					mgrMatrix.set(matrix1);
					if (iml != null) {
						iml.refreshView();
					}
					invalidate();
				}
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			mode = NONE;
			break;
		}
		return true;

	}

	// ������������
	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return (float) Math.sqrt(x * x + y * y);
	}

	// ȡ�������ĵ�
	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}

	// ȡ��ת�Ƕ�
	private float rotation(MotionEvent event) {
		double delta_x = (event.getX(0) - event.getX(1));
		double delta_y = (event.getY(0) - event.getY(1));
		double radians = Math.atan2(delta_y, delta_x);
		return (float) Math.toDegrees(radians);
	}

	private boolean matrixCheck() {
		float[] f = new float[9];
		matrix1.getValues(f);
		// ͼƬ4�����������
		float x1 = f[0] * 0 + f[1] * 0 + f[2];
		float y1 = f[3] * 0 + f[4] * 0 + f[5];
		float x2 = f[0] * gintama.getWidth() + f[1] * 0 + f[2];
		float y2 = f[3] * gintama.getWidth() + f[4] * 0 + f[5];
		float x3 = f[0] * 0 + f[1] * gintama.getHeight() + f[2];
		float y3 = f[3] * 0 + f[4] * gintama.getHeight() + f[5];
		float x4 = f[0] * gintama.getWidth() + f[1] * gintama.getHeight()
				+ f[2];
		float y4 = f[3] * gintama.getWidth() + f[4] * gintama.getHeight()
				+ f[5];
		// ͼƬ�ֿ��
		double width = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		// ���ű����ж�
		if (width < this.getWidth() / 3 || width > this.getWidth() * 3) {
			return true;
		}
		// �����ж�
		if ((x1 < this.getWidth() / 3 && x2 < this.getWidth() / 3
				&& x3 < this.getWidth() / 3 && x4 < this.getWidth() / 3)
				|| (x1 > this.getWidth() * 2 / 3
						&& x2 > this.getWidth() * 2 / 3
						&& x3 > this.getWidth() * 2 / 3 && x4 > this.getWidth() * 2 / 3)
				|| (y1 < this.getHeight() / 3 && y2 < this.getHeight() / 3
						&& y3 < this.getHeight() / 3 && y4 < this.getHeight() / 3)
				|| (y1 > this.getHeight() * 2 / 3
						&& y2 > this.getHeight() * 2 / 3
						&& y3 > this.getHeight() * 2 / 3 && y4 > this
						.getHeight() * 2 / 3)) {
			return true;
		}
		return false;
	}
}
