package cn.dressbook.ui.general.FotoCut.view.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class RectFrame extends View{

	
//	Matrix matrix=new Matrix();
	public RectFrame(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public RectFrame(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public RectFrame(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		
	}
	
	
	
//	public Matrix getMatrix() {
//		return matrix;
//	}
//
//	public void setMatrix(Matrix matrix) {
//		this.matrix = matrix;
//	}

	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);       
		Paint paint=new Paint();
		paint.setStyle(Style.STROKE);
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(8.0f);
//		if(matrix==null){
//			matrix=new Matrix();
//		}
	//	canvas.setMatrix(matrix);
		canvas.drawRect(0, 0, this.getWidth()-2, this.getHeight()-2, paint);
	}
	
}
