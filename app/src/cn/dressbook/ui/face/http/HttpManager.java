package cn.dressbook.ui.face.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import cn.dressbook.ui.face.MainActivity;

/**
 * ����ʵ�֣������Ƕ�����ͬʱ����������������ȵ�����֣��Ѿ���װ��
 * 
 * @author Administrator
 * 
 */
public class HttpManager {

	HttpManagerImp imp;

	HttpURLConnection connection = null;

	String url;

	String postdata;

	httpThread iHttpThread = new httpThread();

	private boolean iISUseCmwapApn = false;
	private boolean iISUseOtherApn = false;
	private boolean needhttpdata = false;
	private int iTaskID = -1;

	private boolean isRunning = false;

	int hasAlreadyDown = 0;

	boolean forceByTimerByOutside = false;
	boolean forceByTimer = false;

	public long result = 0;

	public synchronized int getHasAlreadyDown() {
		return hasAlreadyDown;
	}

	public HttpManager(HttpManagerImp imp) {
		this.imp = imp;

		NetworkInfo info = MainActivity.conMgr.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			int type = info.getType();

			if (type == ConnectivityManager.TYPE_MOBILE) {
				// ȡ����
				String modeHttp = info.getExtraInfo();
				if (modeHttp.equalsIgnoreCase("cmwap")) {
					iISUseCmwapApn = true;
				} else {
					iISUseOtherApn = true;
				}
			} else {
				// ��ȡ����
			}
		}
	}

	public void StopForceConnect() {
		if (isRunning) {
			forceByTimerByOutside = true;
			if (connection != null) {
				connection.disconnect();
			}
			iHttpThread.interrupt();
		}
		isRunning = false;
	}

	public void cancelNet() {

		if (isRunning) {

			if (connection != null) {
				connection.disconnect();
			}
			iHttpThread.interrupt();
		}
		isRunning = false;
	}

	/**
	 * 
	 * @param url
	 * @param post
	 * @param needhttpdata
	 *            �Ƿ���Ҫ��¼http��������
	 */
	public void addTask(String url, String post, boolean needhttpdata,
			int aTaskID) {

		if (isRunning) {
			return;
		}

		this.iTaskID = aTaskID;
		this.url = url;
		this.postdata = post;
		this.needhttpdata = needhttpdata;
		hasAlreadyDown = 0;
		forceByTimer = false;
		forceByTimerByOutside = false;

		cancelNet();

		iHttpThread = new httpThread();
		iHttpThread.start();

		isRunning = true;
	}

	private class httpThread extends Thread {
		httpThread() {
		}

		public void run() {
			InputStream is = null;
			// String s = null;
			byte[] resultByteArr = null;
			boolean error = false;
			try {

				String httpURL = "";
				String urlWapHost = "";
				if (!iISUseCmwapApn) {
					httpURL = url;
				} else {
					String urlt = url;
					httpURL = "http://10.0.0.172";
					urlt = urlt.replace("http://", "");
					int pos = urlt.indexOf("/");
					if (pos != -1) {
						httpURL = httpURL + urlt.substring(pos);
						urlWapHost = urlt.substring(0, urlt.indexOf("/"));
					} else {
						httpURL = httpURL + urlt;
						urlWapHost = urlt;
					}
				}

				URL url = new URL(httpURL);
				connection = (HttpURLConnection) url.openConnection();

				connection.setRequestMethod("GET");
				// �ڿ�ʼ�ͷ���������֮ǰ��������Ҫ����һЩ�������
				connection.setConnectTimeout(60000);

				if (iISUseCmwapApn) {
					connection.addRequestProperty("X-Online-Host", urlWapHost);
				}

				connection.addRequestProperty("Accept", "*/*");
				connection.addRequestProperty("User-Agent", "Android");
				connection.setInstanceFollowRedirects(false);

				connection.connect();

				int code = connection.getResponseCode();

				String acookies = connection.getHeaderField("Set-Cookie");

				String len = connection.getHeaderField("Content-Length");

				if (len != null) {
					result = Long.parseLong(len);
				} else {
					result = 0;
				}

				if (acookies != null) {

					Log.v("Set-Cookie", acookies);

				}

				String i302url = connection.getHeaderField("Location");

				is = connection.getInputStream();

				if (is != null) {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] buf = new byte[1024];
					int ch = -1;
					int count = 0;

					while ((ch = is.read(buf)) != -1) {

						if (needhttpdata) {
							baos.write(buf, 0, ch);
						}

						count += ch;
						hasAlreadyDown += ch;

						// if (!isRunning
						// && (forceByTimer || forceByTimerByOutside)) {
						// Log.e("duan wang", "qiangzhi");
						// break;
						// }
					}

					if (needhttpdata) {
						// s = new String(baos.toByteArray());
						resultByteArr = baos.toByteArray();
					}
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
					if (connection != null) {
						// connection.
						connection.disconnect();
						connection = null;
					}
				} catch (IOException e) {
					error = true;
					e.printStackTrace();
				}

				isRunning = false;

			}

			if (!forceByTimerByOutside) {
				if (error) {
					imp.onHttpError(iTaskID);
				} else {
					imp.onHttpResponse(resultByteArr, iTaskID);
				}
				// û�б��ⲿֹͣ�ص���ȥ

			} else {
				int iii = 0;
			}

		}
	};

}
