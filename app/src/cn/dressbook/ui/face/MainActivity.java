package cn.dressbook.ui.face;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.dressbook.ui.R;
import cn.dressbook.ui.face.component.BaseComponent;
import cn.dressbook.ui.face.component.CameraComponent;
import cn.dressbook.ui.face.component.HairComponent;
import cn.dressbook.ui.face.component.HeadComponent;
import cn.dressbook.ui.face.component.MergeComponent;
import cn.dressbook.ui.face.component.TestComponent;
import cn.dressbook.ui.face.data.SingletonDataMgr;
import cn.dressbook.ui.face.tools.FCTools;

public class MainActivity extends Activity {

	public static ConnectivityManager conMgr = null;
	public static String LEAK_TAG = "LEAK_TAG";

	public boolean setfirst = true;

	public LinearLayout mainpagelayout;
	public Context context = null;

	public static int screenWidth;
	public static int screenHeight;
	public static int body_orgi_x;
	public static int head_orgi_x;

	private Button btn[];
	OnTouchListener listener[];

	TestComponent test = null;
	HeadComponent head = null;
	HairComponent hair = null;
	CameraComponent camera = null;
	MergeComponent merge = null;
	HttpController iHttpController = null;

	BaseComponent curComponent = null;

	LinearLayout loadingView;

	// ���ɰ�ť
	Button generateBtn = null;

	private boolean userFinish = false;

	// int iCurDisplayComponentMode = -1;
	// 0�� ���գ� 1����ȡͼƬ 2, �и�
	int iCameraMode;

	String imgPath = "/sdcard/test/img.jpg";

	private static final String IMAGE_FILE_NAME = "/face.jpg";

	private final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int httpcode = msg.what;
			// hideLoading();
			generateBtn.setEnabled(true);
			loadingView.setVisibility(View.INVISIBLE);
			if (httpcode == 200) {
				Toast.makeText(MainActivity.this, "�ϴ�ͼƬ�ɹ�", Toast.LENGTH_LONG)
						.show();
			} else {
				Toast.makeText(MainActivity.this, "�ϴ�ͼƬʧ��", Toast.LENGTH_LONG)
						.show();
			}
		}
	};

	public void startCamera() {
		try {

			SingletonDataMgr.getInstance().iComponentH = camera.body
					.getHeight();
			SingletonDataMgr.getInstance().iComponentW = camera.body.getWidth();

			iCameraMode = 0;
			Intent intentFromCapture = new Intent(
					MediaStore.ACTION_IMAGE_CAPTURE);
			// �жϴ洢���Ƿ�����ã����ý��д洢
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {

				String path = Environment.getExternalStorageDirectory()
						.getAbsolutePath();

				Log.e("PATH", path);

				intentFromCapture
						.putExtra(MediaStore.EXTRA_OUTPUT, Uri
								.fromFile(new File(Environment
										.getExternalStorageDirectory(),
										IMAGE_FILE_NAME)));

			}

			startActivityForResult(intentFromCapture, iCameraMode);

		} catch (Exception e) {
		}
	}

	/*
	 * add by SKY 0822 (non-Javadoc)
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	public void onDestroy() {
		super.onDestroy();

	}

	public void startSelectImg() {
		iCameraMode = 1;
		try {

			SingletonDataMgr.getInstance().iComponentH = camera.body
					.getHeight();
			SingletonDataMgr.getInstance().iComponentW = camera.body.getWidth();
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);

			intent.setType("image/*");

			startActivityForResult(intent, iCameraMode);

		} catch (ActivityNotFoundException e) {
			Toast.makeText(this, "��ȡϵͳͼƬʧ��", Toast.LENGTH_LONG).show();

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {

				if (camera != null) {
					if (camera.body != null) {
						int w = camera.body.getWidth();
					}
				}
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					File tempFile = new File(
							Environment.getExternalStorageDirectory()
									+ IMAGE_FILE_NAME);
					startPhotoZoom(Uri.fromFile(tempFile));
				} else {
					Toast.makeText(context, "δ�ҵ��洢�����޷��洢��Ƭ��", Toast.LENGTH_LONG)
							.show();
				}

			}
		} else if (requestCode == 1) {
			//选取照片
			if (resultCode == RESULT_OK) {
				ContentResolver resolver = getContentResolver();
				Uri originalUri = data.getData(); // ���ͼƬ��uri

				try {
					Bitmap b = null;

					Log.e(LEAK_TAG, "LINE = " + 192);
					b = MediaStore.Images.Media
							.getBitmap(resolver, originalUri);
					Bitmap dis = FCTools.getScaleBMP(b,
							SingletonDataMgr.getInstance().iComponentW,
							SingletonDataMgr.getInstance().iComponentH);
					Log.e(LEAK_TAG, "LINE = " + 198);
					b.recycle();
					Log.e(LEAK_TAG, "LINE = " + 199);
					b = null;
					// SingletonDataMgr.getInstance().iCurUserSelectPhotoBmp =
					// dis;
					SingletonDataMgr.getInstance().iCurUserSelectPhotoBmp = dis
							.copy(Bitmap.Config.RGB_565, true);

					Log.e(LEAK_TAG, "LINE = " + 207);

					dis.recycle();

					Log.e(LEAK_TAG, "LINE = " + 209);
					// add by 0822
					dis = null;

					Log.e(LEAK_TAG, "LINE = " + 213);
					showComponnet(BaseComponent.CAMERA_COMPONENT);

					Log.e(LEAK_TAG, "LINE = " + 216);
					// camera.userBmp = dis;
					// camera.focusEyes();
					// ImageView img = new ImageView(this);
					// img.setImageBitmap(b);
					// setContentView(img);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					Toast.makeText(this, "��ȡϵͳͼƬʧ��", Toast.LENGTH_LONG).show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Toast.makeText(this, "��ȡϵͳͼƬʧ��", Toast.LENGTH_LONG).show();
				}
			}
		} else if (requestCode == 2) {
			if (resultCode == RESULT_OK) {

				if (camera != null) {
					if (camera.body != null) {
						int w = camera.body.getWidth();
					}
				}
				// Bitmap b = data.getParcelableExtra("data");
				Bitmap b = null;
				try {
					File file = new File(
							Environment.getExternalStorageDirectory()
									+ IMAGE_FILE_NAME);
					if (file.exists()) {
						String pathString = file.getAbsolutePath();
						b = BitmapFactory.decodeFile(pathString);
					}
				} catch (Exception e) {
					b = null;
					// TODO: handle exception
				}

				if (b == null) {
					Toast.makeText(this, "��ǰ״̬���޷���ȡͼƬ��Ϣ", Toast.LENGTH_LONG)
							.show();
				} else {

					Log.e(LEAK_TAG, "LINE = " + 260);
					Bitmap dis = FCTools.getScaleBMP(b,
							SingletonDataMgr.getInstance().iComponentW,
							SingletonDataMgr.getInstance().iComponentH);
					Log.e(LEAK_TAG, "LINE = " + 264);
					b.recycle();
					b = null;
					Log.e(LEAK_TAG, "LINE = " + 267);
					// SingletonDataMgr.getInstance().iCurUserSelectPhotoBmp =
					// dis;
					SingletonDataMgr.getInstance().iCurUserSelectPhotoBmp = dis
							.copy(Bitmap.Config.RGB_565, true);
					Log.e(LEAK_TAG, "LINE = " + 272);
					dis.recycle();
					dis = null;
					Log.e(LEAK_TAG, "LINE = " + 274);
					showComponnet(BaseComponent.CAMERA_COMPONENT);
					Log.e(LEAK_TAG, "LINE = " + 276);
				}

			}
		}

	}

	private void forceShowCameraPhoto(Bitmap b) {
		// TODO Auto-generated method stub
		if (camera == null) {

		}
	}

	/**
	 * �ü�ͼƬ����ʵ��
	 * 
	 * @author SKY
	 * @param fromFile
	 */
	private void startPhotoZoom(Uri uri) {

		iCameraMode = 2;
		// TODO Auto-generated method stub
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// ���òü�
		intent.putExtra("crop", "true");
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY �ǲü�ͼƬ���
		intent.putExtra("outputX", 480);
		intent.putExtra("outputY", 480);
		intent.putExtra("output", Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
		intent.putExtra("noFaceDetection", true);// ȡ������ʶ����
		intent.putExtra("return-data", true);

		startActivityForResult(intent, iCameraMode);
	}

	protected void onPause() {

		if (userFinish) {
			SingletonDataMgr mgr = SingletonDataMgr.getInstance();
			mgr.clealAll();
		}
		super.onPause();
		Log.e("RRRRRRRRRRRRRRRRRonPause", "onPause");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;

		// int iii = iCurDisplayComponentMode;

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;

		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		int width = metric.widthPixels; // ��Ļ��ȣ����أ�
		int height = metric.heightPixels; // ��Ļ�߶ȣ����أ�
		body_orgi_x = (Const.MODEL_BODY_IMAGE_SIZE_W - width) / 2;
		head_orgi_x = Const.MODEL_HEAD_IMAGE_ORIG_X - body_orgi_x;

		conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);

		if (setfirst) {

			Log.e("RRRRRRRRRRRRRRRRRonCreate", "onCreate");

			iHttpController = new HttpController(MainActivity.this);

			// if (SingletonDataMgr.getInstance().iCurDisplayComponentMode ==
			// -1) {
			// String url = Const.getStep1Url("14");
			// iHttpController.startStep1(url);
			// }
			btn = new Button[4];
			listener = new OnTouchListener[4];

			btn[0] = (Button) findViewById(R.id.buttonTableRow1);
			btn[1] = (Button) findViewById(R.id.buttonTableRow2);
			btn[2] = (Button) findViewById(R.id.buttonTableRow3);
			btn[3] = (Button) findViewById(R.id.buttonTableRow4);
			// �ײ���ť�����ť�Ŵ��button
			for (int i = 0; i < btn.length; i++) {
				final int k = i;
				listener[i] = new OnTouchListener() {
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
				btn[i].setOnTouchListener(listener[i]);
			}

			if (generateBtn == null) {
				generateBtn = (Button) findViewById(R.id.buttonmake);
			}

			generateBtn.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View arg0, MotionEvent arg1) {
					// TODO Auto-generated method stub

					saveImg();

					return false;
				}
			});

			if (mainpagelayout == null) {
				mainpagelayout = (LinearLayout) findViewById(R.id.page);
			}

			loadingView = (LinearLayout) findViewById(R.id.loadingView);

			// head = new HeadComponent(context, MainActivity.this);
			SingletonDataMgr.getInstance().iCurDisplayComponentMode = BaseComponent.CAMERA_COMPONENT;
			showComponentInMainThread();
			// if (SingletonDataMgr.getInstance().iCurDisplayComponentMode !=
			// -1) {
			// showComponentInMainThread();
			// //
			// showComponnet(SingletonDataMgr.getInstance().iCurDisplayComponentMode);
			// } else {
			// loadingView.setVisibility(View.VISIBLE);
			// }

			setfirst = false;
		}

		// mainpagelayout.addView(head.show(context));

	}

	protected void saveImg() {
		// TODO Auto-generated method stub

//		if (!SingletonDataMgr.getInstance().canMakeModelBmp()) {
//			Toast.makeText(MainActivity.this, "��©����ĳһ�ؼ����裬��ʹ�û�������ѡȡ���ձ���ͷ��",
//					Toast.LENGTH_LONG).show();
//			return;
//		}

		generateBtn.setEnabled(false);
		loadingView.setVisibility(View.VISIBLE);

		if (true) {
			// ���Դ��룬��android�ֻ���ֱ����ʾ�����ɵĴ��û�����ͼƬ
			generateBtn.setEnabled(true);
			loadingView.setVisibility(View.INVISIBLE);
			SingletonDataMgr mgr = SingletonDataMgr.getInstance();

			Bitmap bm = mgr.getUpdateBmp();
			Intent intent = new Intent(this, ShowPicActivity.class);
			// intent.putExtra("IMG", bm);
			startActivityForResult(intent, 100);
			// ImageView img = new ImageView(this);
			// img.setImageBitmap(bm);
			// setContentView(img);
			return;
		}

		new Thread() {
			public void run() {
				String bound = "0xKhTmLbOuNdArP";

				SingletonDataMgr mgr = SingletonDataMgr.getInstance();

				Bitmap bm = mgr.getUpdateBmp();

				if (bm == null) {
					return;
				}

				InputStream is = null;
				DataOutputStream out = null;
				// String s = null;
				byte[] resultByteArr = null;
				HttpURLConnection connection = null;
				boolean error = false;
				int httpcode = 404;
				try {

					String httpURL = "http://admin.trimview.com/iface.php?";
					// String httpURL =
					// "http://admin.trimview.com/iface_test.php?";
					// String httpURL =
					// "http://58.215.176.161/liantong/upload.jsp?";
					String urlWapHost = "";

					byte[] postData = FCTools.getImgFormByteArr(bm, "777");

					URL url = new URL(httpURL);
					connection = (HttpURLConnection) url.openConnection();

					connection.setDoOutput(true);
					connection.setDoInput(true);
					connection.setRequestMethod("POST");
					// Post������ʹ�û���
					connection.setUseCaches(false);
					connection.setInstanceFollowRedirects(true);
					connection.setRequestProperty("Connection", "keep-alive");
					// Pragma: no-cache
					// Cache-Control: no-cache
					connection.addRequestProperty("Cache-Control", "no-cache");
					connection.addRequestProperty("Pragma", "no-cache");
					connection.addRequestProperty("Accept", "*/*");
					connection.addRequestProperty("Content-Length", ""
							+ postData.length);

					Log.e("Content-Length", postData.length + "");

					connection.addRequestProperty("Content-Type",
							"multipart/form-data; boundary=" + bound);
					// connection.addRequestProperty("User-Agent", "Android");
					connection.setConnectTimeout(60000);
					connection.connect();

					out = new DataOutputStream(connection.getOutputStream());

					out.write(postData);

					out.flush();

					httpcode = connection.getResponseCode();

					is = connection.getInputStream();

					if (is != null) {
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						byte[] buf = new byte[1024];
						int ch = -1;
						int count = 0;
						while ((ch = is.read(buf)) != -1) {
							count += ch;
						}

						String serverReturn = new String(buf);
						Log.e("Server", "[" + serverReturn + "]");

					}
				} catch (Exception e) {
					Log.e("duan wang", "catch" + e.getMessage());
					error = true;
					e.printStackTrace();
				} finally {
					try {
						if (is != null) {
							is.close();
						}
						if (out != null) {
							out.close();
						}
						if (connection != null) {
							// connection.
							connection.disconnect();
							connection = null;
						}
					} catch (IOException e) {
						error = true;
						e.printStackTrace();
					}

					if (bm != null) {
						bm.recycle();
						bm = null;
					}
					// hideLoading();
					// formUpdateOK(httpcode);
					Message msg = new Message();
					msg.what = httpcode;
					handler.sendMessage(msg);

				}

			}
		}.start();

		// SingletonDataMgr mgr = SingletonDataMgr.getInstance();
		// ImageView img = new ImageView(this);
		// img.setImageBitmap(mgr.getUpdateBmp());
		// setContentView(img);

	}

	/**
	 * 0828��� ���֮ǰ������ʾ��ģ������
	 * 
	 * @author SKY
	 */
	private void clearComponentBodyBmp() {

		SingletonDataMgr mgr = SingletonDataMgr.getInstance();
		mgr.clearBodyData();
		if (hair != null) {
			hair.clearBodyBmp();
		}

		if (camera != null) {
			camera.clearBodyBmp();
		}

		if (merge != null) {
			merge.clearBodyBmp();
		}
	}

	public void onClickToolbarButton(int index) {

		if (SingletonDataMgr.getInstance().iCurDisplayComponentMode == -1) {
			return;
		}

		if (SingletonDataMgr.getInstance().iCurDisplayComponentMode == index) {
			return;
		}

		if (SingletonDataMgr.getInstance().iCurDisplayComponentMode == BaseComponent.HEAD_COMPONENT) {
			SingletonDataMgr mgr = SingletonDataMgr.getInstance();

			if (head.iUserSelectItem == null) {
				return;
			}

			if (head.iUserSelectItem != null
					&& mgr.iUserSelectHeadItem != head.iUserSelectItem) {
				mgr.iUserSelectHeadItem = head.iUserSelectItem;
				// 0828��� ���֮ǰ������ʾ��ģ������
				clearComponentBodyBmp();
				iHttpController.startDownModelImg();
			}

		}

		switch (index) {
		case 0: {
			showComponnet(index);
			break;
		}
		case 1:
		case 2:
		case 3: {
			SingletonDataMgr mgr = SingletonDataMgr.getInstance();
			if (mgr.iUserSelectHeadItem != null) {
				showComponnet(index);
			}
		}
		default:
			break;
		}

	}


	public void showHttpErr(int err) {

	}

	public void showComponnet(int headComponent) {
		SingletonDataMgr.getInstance().iCurDisplayComponentMode = headComponent;
		handler.post(showRunnable);
	}

	private Runnable showRunnable = new Runnable() {

		@Override
		public void run() {

			showComponentInMainThread();

		}
	};

	public void showLoading() {
		// TODO Auto-generated method stub
		// loadingView.setVisibility(View.VISIBLE);
		handler.post(showLoadRun);
	}

	protected void showComponentInMainThread() {
		// TODO Auto-generated method stub
		switch (SingletonDataMgr.getInstance().iCurDisplayComponentMode) {
		case BaseComponent.HEAD_COMPONENT: {

			if (head == null) {
				head = new HeadComponent(context, MainActivity.this);
			}

			loadingView.setVisibility(View.INVISIBLE);
			mainpagelayout.removeAllViews();
			mainpagelayout.addView(head.show(context));
			curComponent = head;
			break;
		}
		case BaseComponent.HAIR_COMPONENT: {
			// mainpagelayout.addView(head.show(context));
			if (hair == null) {
				hair = new HairComponent(context, MainActivity.this);
			}
			mainpagelayout.removeAllViews();
			mainpagelayout.addView(hair.show(context));
			curComponent = hair;
			break;
		}
		case BaseComponent.CAMERA_COMPONENT: {
			if (camera == null) {
				camera = new CameraComponent(context, MainActivity.this);
			}
			mainpagelayout.removeAllViews();
			mainpagelayout.addView(camera.show(context));
			curComponent = camera;
			break;
		}
		case BaseComponent.MERGE_COMPONENT: {
			if (merge == null) {
				merge = new MergeComponent(context, MainActivity.this);
			}
			mainpagelayout.removeAllViews();
			mainpagelayout.addView(merge.show(context));
			curComponent = merge;
			break;
		}
		default:
			break;
		}

		if (SingletonDataMgr.getInstance().iCurDisplayComponentMode == BaseComponent.MERGE_COMPONENT) {
			generateBtn.setVisibility(View.VISIBLE);
		} else {
			generateBtn.setVisibility(View.INVISIBLE);
		}

		curComponent.refreshComponent();
	}

	public void hideLoading() {
		handler.post(hideLoadRun);
	}

	private Runnable showLoadRun = new Runnable() {

		@Override
		public void run() {
			loadingView.setVisibility(View.VISIBLE);
		}
	};

	private Runnable hideLoadRun = new Runnable() {

		@Override
		public void run() {
			loadingView.setVisibility(View.INVISIBLE);
		}
	};

	private Runnable doFreshRun = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub

			if (curComponent != null) {
				curComponent.refreshComponent();
			}

		}
	};

	public void doFreshComponent() {
		// TODO Auto-generated method stub
		hideLoading();
		handler.post(doFreshRun);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (KeyEvent.KEYCODE_BACK == keyCode) {
			this.userFinish = true;
			finish();
		} else if (keyCode == KeyEvent.KEYCODE_MENU) {
			// ��������������������
		}

		return true;
	}
}
