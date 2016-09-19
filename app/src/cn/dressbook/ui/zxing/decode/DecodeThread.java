
package cn.dressbook.ui.zxing.decode;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;


import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import cn.dressbook.ui.SaoYiSaoActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;

public class DecodeThread extends Thread {

	public static final String BARCODE_BITMAP = "barcode_bitmap";

	public static final int BARCODE_MODE = 0X100;
	public static final int QRCODE_MODE = 0X200;
	public static final int ALL_MODE = 0X300;

	private final SaoYiSaoActivity activity;
	private final Map<DecodeHintType, Object> hints;
	private Handler handler;
	private final CountDownLatch handlerInitLatch;

	public DecodeThread(SaoYiSaoActivity activity, int decodeMode) {

		this.activity = activity;
		handlerInitLatch = new CountDownLatch(1);

		hints = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);

		Collection<BarcodeFormat> decodeFormats = new ArrayList<BarcodeFormat>();
		decodeFormats.addAll(EnumSet.of(BarcodeFormat.AZTEC));
		decodeFormats.addAll(EnumSet.of(BarcodeFormat.PDF_417));

		switch (decodeMode) {
		case BARCODE_MODE:
			decodeFormats.addAll(DecodeFormatManager.getBarCodeFormats());
			break;

		case QRCODE_MODE:
			decodeFormats.addAll(DecodeFormatManager.getQrCodeFormats());
			break;

		case ALL_MODE:
			decodeFormats.addAll(DecodeFormatManager.getBarCodeFormats());
			decodeFormats.addAll(DecodeFormatManager.getQrCodeFormats());
			break;

		default:
			break;
		}

		hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);
	}

	public Handler getHandler() {
		try {
			handlerInitLatch.await();
		} catch (InterruptedException ie) {
			// continue?
		}
		return handler;
	}

	@Override
	public void run() {
		Looper.prepare();
		handler = new DecodeHandler(activity, hints);
		handlerInitLatch.countDown();
		Looper.loop();
	}

}
