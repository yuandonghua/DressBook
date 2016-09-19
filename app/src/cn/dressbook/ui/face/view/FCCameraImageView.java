package cn.dressbook.ui.face.view;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import cn.dressbook.ui.face.MainActivity;
import cn.dressbook.ui.face.data.EyePoint;
import cn.dressbook.ui.face.data.SingletonDataMgr;
import cn.dressbook.ui.face.tools.FCTools;

public class FCCameraImageView extends FCBaseImgView {

	// 1�� ��ʾĬ�ϣ� 2�� ��ʾ����
	public int BodyImageView_Bmp_Mode;

	public EyePoint leftEye = null;
	public EyePoint defaultLeftEye = null;
	public EyePoint RightEye = null;
	public EyePoint defaultRightEye = null;

	public Bitmap eyeBmp = null;

	private static final int NONE = 0;
	private static final int DRAG = 1;
	private static final int ZOOM = 2;
	int mode = NONE;

	boolean responeMode = false;
	boolean islefteyemove = true;

	public FCCameraImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub

		InputStream is = null;
		try {
			String s = "szem.png";
			is = context.getAssets().open(s);
		} catch (IOException e) {
			e.printStackTrace();
		}

		eyeBmp = BitmapFactory.decodeStream(is);
		if (leftEye == null) {
			leftEye = new EyePoint();
			leftEye.x = 50;
			leftEye.y = 100;
		}

		if (RightEye == null) {
			RightEye = new EyePoint();
			RightEye.x = 150;
			RightEye.y = 100;
		}

		if (defaultLeftEye == null) {
			defaultLeftEye = new EyePoint();
			defaultLeftEye.x = 50;
			defaultLeftEye.y = 100;
		}

		if (defaultRightEye == null) {
			defaultRightEye = new EyePoint();
			defaultRightEye.x = 150;
			defaultRightEye.y = 100;
		}

	}

	public FCCameraImageView(Context context, AttributeSet attr) {
		super(context, attr);
		InputStream is = null;
		try {
			String s = "szem.png";
			is = context.getAssets().open(s);
		} catch (IOException e) {
			e.printStackTrace();
		}

		eyeBmp = BitmapFactory.decodeStream(is);

		if (leftEye == null) {
			leftEye = new EyePoint();
			leftEye.x = 50;
			leftEye.y = 100;
		}

		if (RightEye == null) {
			RightEye = new EyePoint();
			RightEye.x = 150;
			RightEye.y = 100;
		}

		if (defaultLeftEye == null) {
			defaultLeftEye = new EyePoint();
			defaultLeftEye.x = 50;
			defaultLeftEye.y = 100;
		}

		if (defaultRightEye == null) {
			defaultRightEye = new EyePoint();
			defaultRightEye.x = 150;
			defaultRightEye.y = 100;
		}

	}

	public void setBmp(Bitmap abmp) {
		this.bmp = abmp;
		this.invalidate();
	}

	public void clearBodyBmp() {
		if (BodyImageView_Bmp_Mode == 1) {
			if (bmp != null) {
				// bmp.recycle();
				bmp = null;
			}
		}
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.drawColor(Color.TRANSPARENT);

		int h = 450;

		if (bmp == null) {

			switch (BodyImageView_Bmp_Mode) {
			case 1: {
				// SingletonDataMgr mgr = SingletonDataMgr.getInstance();
				// if (mgr.iModelBodyItem != null) {
				// bmp = mgr.iModelBodyItem.furlImg;
				// }
				if (FCTools.isNeedScaleBodyImg()) {
					SingletonDataMgr mgr = SingletonDataMgr.getInstance();
					if (mgr.iModelBodyItem != null
							&& mgr.iModelBodyItem.furlImg != null) {
						bmp = FCTools.getBodyScaleBMP(
								mgr.iModelBodyItem.furlImg,
								MainActivity.screenWidth, this.getHeight());
					}
				} else {
					SingletonDataMgr mgr = SingletonDataMgr.getInstance();
					if (mgr.iModelBodyItem != null) {
						bmp = mgr.iModelBodyItem.furlImg;
					}
				}
				break;
			}
			case 2: {
			}
			}
		}

		if (bmp != null) {

			if (BodyImageView_Bmp_Mode == 2) {
				canvas.drawBitmap(bmp, 0, 0, mPaint);
				if (leftEye != null && eyeBmp != null) {
					int x = leftEye.x - eyeBmp.getWidth() / 2;
					int y = leftEye.y - eyeBmp.getHeight() / 2;
					// canvas.drawBitmap(eyeBmp, x, y, mPaint);
				}

				if (RightEye != null && eyeBmp != null) {
					int x = RightEye.x - eyeBmp.getWidth() / 2;
					int y = RightEye.y - eyeBmp.getHeight() / 2;
					// canvas.drawBitmap(eyeBmp, x, y, mPaint);
				}
				// return;
			} else {
				if (FCTools.isNeedScaleBodyImg()) {
					if (bmp != null) {
						Matrix ma = new Matrix();
						ma.postScale(2.0f, 2.0f);
						int bmpw = bmp.getWidth() * 2;
						int sW = MainActivity.screenWidth;

						if (sW > bmpw) {
							int posX = (sW - bmpw) / 2;
							ma.postTranslate(posX, 0);
						}

						canvas.drawBitmap(bmp, ma, mPaint);
					}

				} else {
					Rect dst = new Rect(0, 0, MainActivity.screenWidth, h);
					Rect src = new Rect(
							MainActivity.body_orgi_x,
							0,
							MainActivity.screenWidth + MainActivity.body_orgi_x,
							h);
					canvas.drawBitmap(bmp, src, dst, mPaint);
				}

			}

		}
		// canvas.drawBitmap(eyeBmp, 0, 0, mPaint);

	}

	public boolean onTouchEvent(MotionEvent event) {

		Matrix mgrMatrix = null;

		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			responeMode = false;
			mode = DRAG;
			float x_down = event.getX();
			float y_down = event.getY();
			{
				int x = leftEye.x - eyeBmp.getWidth() / 2;
				int y = leftEye.y - eyeBmp.getHeight() / 2;

				Rect r = new Rect(x, y, x + eyeBmp.getWidth(), y
						+ eyeBmp.getHeight());

				if (r.contains((int) x_down, (int) y_down)) {
					responeMode = true;
					islefteyemove = true;
				}
			}

			{
				int x = RightEye.x - eyeBmp.getWidth() / 2;
				int y = RightEye.y - eyeBmp.getHeight() / 2;

				Rect r = new Rect(x, y, x + eyeBmp.getWidth(), y
						+ eyeBmp.getHeight());

				if (r.contains((int) x_down, (int) y_down)) {
					responeMode = true;
					islefteyemove = false;
				}
			}

			// if (x_down >= leftEye.x && x_down < leftEye.x + ) {
			//
			// }

			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			mode = ZOOM;
			break;
		case MotionEvent.ACTION_MOVE:
			if (mode == DRAG && responeMode) {
				float x_down1 = event.getX();
				float y_down1 = event.getY();
				if (islefteyemove) {
					leftEye.x = eyeBmp.getWidth() / 2 + (int) x_down1;
					leftEye.y = eyeBmp.getWidth() / 2 + (int) y_down1;
				} else {
					RightEye.x = eyeBmp.getWidth() / 2 + (int) x_down1;
					RightEye.y = eyeBmp.getWidth() / 2 + (int) y_down1;
				}
				invalidate();
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP: {
			responeMode = false;
			mode = NONE;
			break;
		}

		}
		return true;

	}

	public void setLeftEye(EyePoint pL) {
		// TODO Auto-generated method stub
		leftEye.x = pL.x;
		leftEye.y = pL.y;

		defaultLeftEye.x = pL.x;
		defaultLeftEye.y = pL.y;

	}

	public void setRightEye(EyePoint pL) {
		// TODO Auto-generated method stub
		RightEye.x = pL.x;
		RightEye.y = pL.y;

		defaultRightEye.x = pL.x;
		defaultRightEye.y = pL.y;
	}

	public void setDefaultEye() {
		leftEye.x = defaultLeftEye.x;
		leftEye.y = defaultLeftEye.y;
		// TODO Auto-generated method stub
		RightEye.x = defaultRightEye.x;
		RightEye.y = defaultRightEye.y;
		invalidate();
	}

}
