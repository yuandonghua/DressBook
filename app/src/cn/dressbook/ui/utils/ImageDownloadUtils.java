/**
 *@name ImageDownloadUtils.java
 *@description
 *@author 袁东华
 *@data 2014-8-13下午1:55:12
 */
package cn.dressbook.ui.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.os.Handler;


/**
 * @description 下载图片
 * @author 袁东华
 * @date 2014-8-13 下午1:55:12
 */
public class ImageDownloadUtils {
	private static ImageDownloadUtils mImageDownloadUtils;

	/**
	 * 私有化构造
	 */
	private ImageDownloadUtils() {
	}

	public static ImageDownloadUtils getInstance() {
		if (mImageDownloadUtils == null) {
			mImageDownloadUtils = new ImageDownloadUtils();
		}

		return mImageDownloadUtils;

	}

	public void downloadImage(Handler handler, String url, String path, int flag) {
		try {
			URL u = new URL(url);
			URLConnection conn = u.openConnection();
			conn.setConnectTimeout(1000 * 120);
			conn.connect();
			InputStream is = conn.getInputStream();
			int fileSize = conn.getContentLength();

			FileOutputStream fos = new FileOutputStream(path);
			byte[] bytes = new byte[1024];
			int len = -1;
			while ((len = is.read(bytes)) != -1) {
				fos.write(bytes, 0, len);
			}
			is.close();
			fos.close();
			File file = new File(path);
			boolean isBreak;
			if (len == -1 && file.exists() && file.length() == fileSize) {
				handler.sendEmptyMessage(flag);
			} else {
				isBreak = true;
				while (isBreak) {
					if (file.delete()) {
						isBreak = false;
						// 重新下载
						downloadImage(handler, url, path, flag);
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
