package cn.dressbook.ui.face.tools;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class AsyncImageLoader {

	public AsyncImageLoader() {
	}

	public void loadDrawable(final String imageUrl,
			final ImageCallback imageCallback) {
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				imageCallback.imageLoaded((Bitmap) message.obj, imageUrl);
			}
		};

		new Thread() {
			@Override
			public void run() {
				Bitmap bmp = loadImageFromUrl(imageUrl);
				if (bmp != null) {

					Message message = handler.obtainMessage(0, bmp);

					handler.sendMessage(message);
				}

			}
		}.start();

	}

	private Bitmap loadImageFromUrl(String url) {
		// TODO Auto-generated method stub
		URL m;
		InputStream i = null;

		Log.e("STEP6 DOWN", url);

		boolean eB = false;
		try {
			m = new URL(url);
			i = (InputStream) m.getContent();
		} catch (MalformedURLException e1) {
			eB = true;
			e1.printStackTrace();
		} catch (IOException e) {
			eB = true;
			e.printStackTrace();
		}

		if (!eB) {
			Bitmap d = BitmapFactory.decodeStream(i);
			return d;
		} else {
			return null;
		}
	}

	public interface ImageCallback {
		public void imageLoaded(Bitmap bmp, String imageUrl);
	}

}
