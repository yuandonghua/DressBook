package cn.dressbook.ui.face.component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import cn.dressbook.ui.R;
import cn.dressbook.ui.face.MainActivity;
import cn.dressbook.ui.face.data.FCBaseXmlItem;
import cn.dressbook.ui.face.data.SingletonDataMgr;
import cn.dressbook.ui.face.tools.AsyncImageLoader;
import cn.dressbook.ui.face.tools.MatrixTools;
import cn.dressbook.ui.face.view.BodyImageView;
import cn.dressbook.ui.face.view.FCGestureView;
import cn.dressbook.ui.face.view.FCHairView;
import cn.dressbook.ui.face.view.IGestureViewIml;

public class HairComponent extends BaseComponent implements IGestureViewIml {

	FCHairView hair;
	BodyImageView body = null;

	LinearLayout btnbar;

	boolean setBtnBar = false;

	ArrayList<ImageButton> btnList = null;

	FCBaseXmlItem downItem = null;

	FCGestureView gestureView = null;

	RelativeLayout RelativeLayout_weitiao = null;

	int clsArrayIndex = 0;

	public HairComponent(Context context2, MainActivity mainActivity) {
		super(context2, mainActivity);

		btnList = new ArrayList<ImageButton>();

		if (comLayout == null) {
			LayoutInflater mInflater = LayoutInflater.from(context2);
			comLayout = (RelativeLayout) mInflater.inflate(
					R.layout.hair_layout, null);

			hair = (FCHairView) comLayout.findViewById(R.id.imageHead2);
			body = (BodyImageView) comLayout.findViewById(R.id.imageBody2);
			body.BodyImageView_Bmp_Mode = 1;
			HorizontalScrollView iHorizontalScrollView = (HorizontalScrollView) comLayout
					.findViewById(R.id.horizontalScrollView2);
			btnbar = (LinearLayout) iHorizontalScrollView
					.findViewById(R.id.linearLayout2);

			RelativeLayout_weitiao = (RelativeLayout) comLayout
					.findViewById(R.id.RelativeLayout_weitiao);

			RelativeLayout_weitiao.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View arg0, MotionEvent arg1) {
					// TODO Auto-generated method stub
					// hair.setD
					return true;
				}

			});

			Button defaultBtn = (Button) comLayout
					.findViewById(R.id.buttonDefaultBtn);

			defaultBtn.setOnTouchListener(new OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					SingletonDataMgr mgr = SingletonDataMgr.getInstance();
					if (mgr.iUserHairData != null
							&& mgr.iUserHairData.iUserSelectHair != null
							&& mgr.iUserHairData.iUserSelectHair.vurlImg != null) {
						mgr.iUserHairData.setDefaultMatrix();
						hair.postInvalidate();
					}
					return false;
				}
			});

			for (int i = 0; i < 8; i++) {
				final int k;
				k = i;
				ImageButton btn = (ImageButton) comLayout
						.findViewById(R.id.imageButton1 + i);
				OnTouchListener l = new OnTouchListener() {
					public boolean onTouch(View v, MotionEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN) {
							onClickToolbarButton(k);
						} else if (event.getAction() == MotionEvent.ACTION_UP) {

						}
						return false;
					}
				};
				btn.setOnTouchListener(l);
			}

			gestureView = (FCGestureView) comLayout
					.findViewById(R.id.FCGestureView);
			gestureView.FCGestureViewMode = FCGestureView.K_FC_GESTURE_HAIR_MODE;
			gestureView.iml = this;
		}
	}

	public void clearBodyBmp() {
		if (body != null) {
			body.clearBodyBmp();
		}
		setBtnBar = false;
		while (btnList.size() > 0) {
			btnList.remove(0);
		}

		btnbar.removeAllViews();
	}

	protected void onClickToolbarButton(int k) {

		SingletonDataMgr mgr = SingletonDataMgr.getInstance();
		if (mgr.iUserHairData == null
				|| mgr.iUserHairData.iUserSelectHair == null
				|| mgr.iUserHairData.iUserSelectHair.vurlImg == null) {
			return;
		}
		// TODO Auto-generated method stub
		switch (k) {
		case 0: {
			// ��
			Matrix matrix1 = new Matrix();
			matrix1.set(mgr.iUserHairData.matrix);
			matrix1.postTranslate(0, -2);
			mgr.iUserHairData.matrix.set(matrix1);
			hair.postInvalidate();
			break;
		}
		case 1: {
			// ��
			mgr.iUserHairData.matrix.postTranslate(0, 2);
			hair.postInvalidate();
			break;
		}
		case 2: {
			// ��
			mgr.iUserHairData.matrix.postTranslate(-2, 0);
			hair.postInvalidate();
			break;
		}
		case 3: {
			// ��
			mgr.iUserHairData.matrix.postTranslate(2, 0);
			hair.postInvalidate();
			break;
		}
		case 4: {
			// �Ŵ�

			if (mgr.iUserHairData.iUserSelectHair != null
					&& mgr.iUserHairData.iUserSelectHair.vurlImg != null) {
				MatrixTools.postScale(mgr.iUserHairData.matrix, 1.05f,
						mgr.iUserHairData.iUserSelectHair.vurlImg);
				hair.postInvalidate();
			}
			break;
		}
		case 5: {
			// ��С
			if (mgr.iUserHairData.iUserSelectHair != null
					&& mgr.iUserHairData.iUserSelectHair.vurlImg != null) {
				MatrixTools.postScale(mgr.iUserHairData.matrix, 0.95f,
						mgr.iUserHairData.iUserSelectHair.vurlImg);
				hair.postInvalidate();
			}
			// mgr.iUserHairData.matrix.preScale(0.95f, 0.95f);
			// hair.postInvalidate();
			break;
		}
		case 6: {
			// ����ת
			mgr.iUserHairData.matrix.preRotate(0.5f);
			hair.postInvalidate();
			break;
		}
		case 7: {
			// ����ת
			mgr.iUserHairData.matrix.preRotate(-0.5f);
			hair.postInvalidate();
			break;
		}
		default:
			break;
		}
	}

	@Override
	public View show(Context con) {
		// TODO Auto-generated method stub
		refreshComponent();
		return comLayout;
	}

	public void refreshComponent() {

		SingletonDataMgr mgr = SingletonDataMgr.getInstance();
		if (mgr.iHairItemList != null) {
			if (!setBtnBar) {
				setBtnBar = true;

				// if (mgr.iUserHairData != null) {
				// hair.setBmp(mgr.iUserHairData.iUserSelectHair.vurlImg);
				// }
				hair.invalidate();
				body.invalidate();

				Bitmap bmp = null;
				if (bmp == null) {
					InputStream is = null;
					try {
						String s = "head.png";
						is = context.getAssets().open(s);
					} catch (IOException e) {
						e.printStackTrace();
					}

					bmp = BitmapFactory.decodeStream(is);

					int w = MainActivity.screenWidth / 6;
					Bitmap mCreate = Bitmap
							.createScaledBitmap(bmp, w, w, false);
					clsArrayIndex = 0;
					int k = 0;
					for (int i = 0; i < mgr.iHairItemList.size(); i++) {
						FCBaseXmlItem item = mgr.iHairItemList.get(i);

						ImageButton btn = new ImageButton(context);
						btn.setImageBitmap(mCreate);
						if (item != null) {
							btnList.add(btn);
							btn.setTag(item);
							OnTouchListener l = new OnTouchListener() {
								public boolean onTouch(View v, MotionEvent event) {
									if (event.getAction() == MotionEvent.ACTION_DOWN) {
									} else if (event.getAction() == MotionEvent.ACTION_UP) {
										onClickToolbarButton(v);
									}
									return false;
								}
							};
							btn.setOnTouchListener(l);
						}
						btn.setVisibility(View.INVISIBLE);
						btn.setPadding(0, 0, 0, 0);

						btnbar.addView(btn);

					}
					startDown();
				}
			}
		}
	}

	protected void onClickToolbarButton(View v) {
		// TODO Auto-generated method stub
		FCBaseXmlItem item = (FCBaseXmlItem) v.getTag();
		// Log.e("IURL", item.iurl);
		SingletonDataMgr mgr = SingletonDataMgr.getInstance();
		mgr.iUserHairData.iUserSelectHair = item;
		// hair.setBmp(mgr.iUserHairData.iUserSelectHair.vurlImg);
		hair.invalidate();
	}

	private void startDown() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		SingletonDataMgr mgr = SingletonDataMgr.getInstance();

		downItem = getDownloadItrem();

		if (downItem == null) {
			return;
		}

		AsyncImageLoader load = new AsyncImageLoader();
		load.loadDrawable(downItem.vurl,
				new AsyncImageLoader.ImageCallback() {

					@Override
					public void imageLoaded(Bitmap bmp, String imageUrl) {
						// TODO Auto-generated method stub
						if (bmp != null && imageUrl.equals(downItem.vurl)) {
							downItem.vurlImg = bmp;

							showBtnBmp(downItem);
						}
					}
				});

	}

	protected void showBtnBmp(FCBaseXmlItem downItem2) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		ImageButton btn = null;
		for (int i = 0; i < btnList.size(); i++) {
			btn = btnList.get(i);
			FCBaseXmlItem item = (FCBaseXmlItem) btn.getTag();
			if (item.equals(downItem)) {
				break;
			}
		}

		if (btn != null) {
			int w = MainActivity.screenWidth / 6;
			Bitmap mCreate = Bitmap.createScaledBitmap(downItem.vurlImg, w, w,
					false);
			btn.setImageBitmap(mCreate);
			btn.setVisibility(View.VISIBLE);
		}

		startDown();

	}

	private FCBaseXmlItem getDownloadItrem() {
		// TODO Auto-generated method stub
		FCBaseXmlItem res = null;

		if (clsArrayIndex >= 0 && clsArrayIndex < btnList.size()) {
			ImageButton BTN = (ImageButton) btnList.get(clsArrayIndex);
			res = (FCBaseXmlItem) BTN.getTag();
		}

		clsArrayIndex++;
		return res;
	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		hair.postInvalidate();
	}

	@Override
	public void gestureTaped() {
		// TODO Auto-generated method stub
		if (RelativeLayout_weitiao.getVisibility() == View.INVISIBLE) {
			RelativeLayout_weitiao.setVisibility(View.VISIBLE);
		} else {
			RelativeLayout_weitiao.setVisibility(View.INVISIBLE);
		}

	}

}
