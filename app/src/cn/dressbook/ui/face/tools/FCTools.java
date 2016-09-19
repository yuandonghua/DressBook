package cn.dressbook.ui.face.tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import cn.dressbook.ui.face.MainActivity;

public class FCTools {

	private FCTools() {

	}

	public static byte[] getImgFormByteArr(Bitmap b, String wid)
			throws IOException {
		// add by SKY 0822
		String bound = "0xKhTmLbOuNdArP";

		ByteArrayOutputStream res = new ByteArrayOutputStream();

		StringBuffer buf1 = new StringBuffer();
		buf1.append("--" + bound + "\r\n");
		buf1.append("Content-Disposition: form-data; name=\"uploadflag_\""
				+ "\r\n\r\n1");
		buf1.append("\r\n--" + bound + "\r\n");
		buf1.append("Content-Disposition: form-data; name=\"wid\"\r\n\r\n"
				+ wid);

		buf1.append("\r\n--" + bound + "\r\n");
		buf1.append("Content-Disposition: form-data; name=\"model_pic\"; filename=\"temp.png\"\r\nContent-Type: image/png\r\n\r\n");
		// buf1.append("Content-Disposition: form-data; name=\"model_pic\"; filename=\"temp.png\"\r\nContent-Type: application/octet-stream\r\n\r\n");

		res.write(buf1.toString().getBytes());
		{
			// д��bitmap
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			b.compress(Bitmap.CompressFormat.PNG, 100, baos);
			// return baos.toByteArray();
			res.write(baos.toByteArray());
			baos.close();
			// res.write(new String("aaa123456aaa").getBytes());
		}
		String s = "\r\n--" + bound + "--\r\n";
		res.write(s.getBytes());
		byte[] arr = res.toByteArray();
		res.close();
		return arr;
	}

	public static boolean isNeedScaleBodyImg() {
		if (MainActivity.screenWidth >= 700) {
			return true;
			// return false;
		} else {
			return false;
		}
	}

	public static int getBodyImageState() {
		if (MainActivity.screenWidth >= 700) {
			return 2;
		} else {
			return 1;
		}
	}

	public static Bitmap getBodyScaleBMP(Bitmap b, int componentW,
			int componentH) {
		int bw = b.getWidth();
		int bh = b.getHeight();
		Bitmap bRes = null;

		int bmpW = bw / 2;
		int bmpH = bh;

		bRes = Bitmap.createBitmap(bmpW, bmpH, Config.ARGB_8888);// ����һ���µĺ�SRC���ȿ��һ����λͼ
		Canvas canvas = new Canvas(bRes);
		canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
				| Paint.FILTER_BITMAP_FLAG)); // �����

		Paint paint = new Paint(); // �½�paint
		paint.setAntiAlias(true); // ���ÿ����,Ҳ���Ǳ�Ե��ƽ������
		Rect dst = new Rect(0, 0, bmpW, bmpH);

		int x = 0;
		x = (bw - bmpW) / 2;
		Rect src = new Rect(x, 0, bmpW + x, bmpH);
		canvas.drawBitmap(b, src, dst, paint);
		return bRes;
	}

	public static Bitmap getScaleBMP(Bitmap b, int w, int h) {
		int bw = b.getWidth();
		int bh = b.getHeight();
		int newW = 0;
		int newH = 0;

		if (bw >= bh) {
			newW = w;
			newH = newW * bh / bw;

			if (newH > h) {
				newH = h;
				newW = newH * bw / bh;
			}

		} else if (bw < bh) {
			newH = h;
			newW = newH * bw / bh;

			if (newW > w) {
				newW = w;
				newH = newW * bh / bw;
			}
		}

		float scaleWidth = ((float) newW) / bw;
		float scaleHeight = ((float) newH) / bh;
		// ȡ����Ҫ���ŵ�matrix����
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// �õ��µ�ͼƬ
		Bitmap bRes = Bitmap.createBitmap(b, 0, 0, bw, bh, matrix, false);

		return bRes;
	}
}
