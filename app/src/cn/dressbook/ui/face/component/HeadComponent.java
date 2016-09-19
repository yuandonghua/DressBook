package cn.dressbook.ui.face.component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import cn.dressbook.ui.R;
import cn.dressbook.ui.face.MainActivity;
import cn.dressbook.ui.face.data.FCBaseXmlItem;
import cn.dressbook.ui.face.data.Headcls;
import cn.dressbook.ui.face.data.SingletonDataMgr;
import cn.dressbook.ui.face.http.HttpManager;
import cn.dressbook.ui.face.http.HttpManagerImp;
import cn.dressbook.ui.face.tools.AsyncImageLoader;
import cn.dressbook.ui.face.view.BodyImageView;
import cn.dressbook.ui.face.view.FaceImageView;

public class HeadComponent extends BaseComponent implements HttpManagerImp {

	FaceImageView head;
	BodyImageView body;

	LinearLayout btnbar;
	HorizontalScrollView iHorizontalScrollView;

	ArrayList<ImageButton> btnList = null;
	// ArrayList<Button.OnTouchListener> lisArr = null;

	boolean setBtnBar = false;

	HttpManager iHttpManager = null;

	int clsArrayIndex = 0;

	AsyncImageLoader iAsyncImageLoader = new AsyncImageLoader();

	FCBaseXmlItem downItem = null;

	public FCBaseXmlItem iUserSelectItem = null;

	private final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			// switch (msg.what) {
			// case WHAT_GET_MY_STATE: {
			// singleMgr.getMyStateNow();
			// break;
			// }
			// }
			super.handleMessage(msg);
		}
	};

	public HeadComponent(Context context2, MainActivity mainActivity) {
		super(context2, mainActivity);

		btnList = new ArrayList<ImageButton>();

		iHttpManager = new HttpManager(this);

		if (comLayout == null) {
			LayoutInflater mInflater = LayoutInflater.from(context2);
			comLayout = (RelativeLayout) mInflater.inflate(
					R.layout.head_layout, null);

			head = (FaceImageView) comLayout.findViewById(R.id.imageHead1);
			body = (BodyImageView) comLayout.findViewById(R.id.imageBody1);
			body.BodyImageView_Bmp_Mode = 0;
			iHorizontalScrollView = (HorizontalScrollView) comLayout
					.findViewById(R.id.horizontalScrollView1);
			btnbar = (LinearLayout) iHorizontalScrollView
					.findViewById(R.id.linearLayout1);
		}
		// TODO Auto-generated constructor stub
	}

	@Override
	public View show(Context con) {
		this.context = con;

		SingletonDataMgr mgr = SingletonDataMgr.getInstance();

		if (head.bmp == null) {

			head.setBmp(mgr.iDefaultHeadItem.vurlImg);
		}

		if (setBtnBar == false) {
			setBtnBar = true;
			Bitmap bmp = null;
			if (bmp == null) {
				InputStream is = null;
				try {
					String s = "head.png";
					is = con.getAssets().open(s);
				} catch (IOException e) {
					e.printStackTrace();
				}

				bmp = BitmapFactory.decodeStream(is);

				int w = MainActivity.screenWidth / 6;
				Bitmap mCreate = Bitmap.createScaledBitmap(bmp, w, w, false);

				int k = 0;
				for (int i = 0; i < mgr.iHeadClsList.size(); i++) {
					Headcls cls = mgr.iHeadClsList.get(i);

					for (int j = 0; j < 6; j++) {
						FCBaseXmlItem item = null;
						if (j < cls.headList.size()) {
							item = cls.headList.get(j);
						}

						ImageButton btn = new ImageButton(con);
						btn.setImageBitmap(mCreate);
						if (item != null) {
							btnList.add(btn);
							btn.setTag(item);
							Button.OnTouchListener l = new Button.OnTouchListener() {
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

				}
				startDown();
			}
		}

		return comLayout;
	}

	protected void onClickToolbarButton(View v) {
		// TODO Auto-generated method stub
		FCBaseXmlItem item = (FCBaseXmlItem) v.getTag();
		// Log.e("IURL", item.iurl);
		mainActivity.showLoading();
		iUserSelectItem = item;
		iHttpManager.addTask(iUserSelectItem.vurl, "", true, 0);
	}

	private void startDown() {
		// TODO Auto-generated method stub
		SingletonDataMgr mgr = SingletonDataMgr.getInstance();

		downItem = getDownloadItrem();

		if (downItem == null) {
			return;
		}

		AsyncImageLoader load = new AsyncImageLoader();
		load.loadDrawable(downItem.iurl,
				new AsyncImageLoader.ImageCallback() {

					@Override
					public void imageLoaded(Bitmap bmp, String imageUrl) {
						// TODO Auto-generated method stub
						if (bmp != null && imageUrl.equals(downItem.iurl)) {
							downItem.iurlImg = bmp;

							showBtnBmp(downItem);
						}
					}
				});
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

	protected void showBtnBmp(FCBaseXmlItem downItem) {
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
			Bitmap mCreate = Bitmap.createScaledBitmap(downItem.iurlImg, w, w,
					false);
			btn.setImageBitmap(mCreate);
			btn.setVisibility(View.VISIBLE);
		}

		startDown();

	}

	Runnable refresh = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			mainActivity.hideLoading();
			head.setBmp(iUserSelectItem.vurlImg);
		}
	};

	@Override
	public void onHttpResponse(byte[] resultByteArr, int taskID) {
		// TODO Auto-generated method stub

		if (resultByteArr.length > 0) {
			iUserSelectItem.vurlImg = BitmapFactory.decodeByteArray(
					resultByteArr, 0, resultByteArr.length);
		}

		handler.post(refresh);

	}

	@Override
	public void onHttpError(int taskID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onHttpResponseIntime(int alreadyLen) {
		// TODO Auto-generated method stub

	}

}
