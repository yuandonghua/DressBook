package cn.dressbook.ui.general.FotoCut.view.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class BlackRectFrame extends View{

	public BlackRectFrame(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public BlackRectFrame(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public BlackRectFrame(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);       
		Paint paint=new Paint();
		paint.setStyle(Style.STROKE);
		paint.setAntiAlias(true);
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(2.0f);
		canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), paint);
	}
	
}
