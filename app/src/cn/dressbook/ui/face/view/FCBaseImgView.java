package cn.dressbook.ui.face.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class FCBaseImgView extends View {

	public Bitmap bmp = null;
	public Paint mPaint;
	public Context facecontext;

	public FCBaseImgView(Context context) {
		super(context);
		this.facecontext = context;
		ini();
		// TODO Auto-generated constructor stub
	}

	public FCBaseImgView(Context context, AttributeSet attr) {
		super(context, attr);
		this.facecontext = context;
		ini();
	}

	private void ini() {
		// TODO Auto-generated method stub

	}

}
