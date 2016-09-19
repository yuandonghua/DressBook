package cn.dressbook.ui.SnowCommon.common.util;

import android.graphics.Rect;
import android.graphics.RectF;

public class GraphicUtil {

	
	public static Rect expandRect(Rect rect, float scale, Rect limit){
		
		Rect result=new Rect();
		
		 if (scale != 1.0f) {
			 result.left = (int) (rect.left +(rect.width()* (1.0-scale)*0.5) + 0.5f);
			 result.top = (int) (rect.top +(rect.height()* (1.0-scale)*0.5) + 0.5f);
			 result.right = (int) (rect.right - (rect.width()*  (1.0-scale)*0.5) + 0.5f);
			 result.bottom = (int) (rect.bottom -(rect.height()*  (1.0-scale)*0.5) + 0.5f);
			 
			 result.left=Math.max( result.left, limit.left);
			 result.top=Math.max( result.top, limit.top);
			 result.right=Math.min( result.right, limit.right);
			 result.bottom=Math.min( result.bottom, limit.bottom);
		
			 return result;
		 }
		 
		 else 
		 
		 return rect;
		
	}
	public static Rect expandRect(Rect rect, double scaleX,double scaleY, Rect limit){
		
		Rect result=new Rect();
		
		 if (scaleX != 1.0f || scaleY != 1.0f) {
			 result.left = (int) (rect.left +(rect.width()*  (1.0-scaleX)*0.5) + 0.5f);
			 result.top = (int) (rect.top +(rect.height()*  (1.0-scaleY)*0.5) + 0.5f);
			 result.right = (int) (rect.right  -(rect.width()*  (1.0-scaleX)*0.5)+ 0.5f);
			 result.bottom = (int) (rect.bottom -(rect.height()*  (1.0-scaleY)*0.5) + 0.5f);
			 
			 result.left=Math.max( result.left, limit.left);
			 result.top=Math.max( result.top, limit.top);
			 result.right=Math.min( result.right, limit.right);
			 result.bottom=Math.min( result.bottom, limit.bottom);
		
			 return result;
		 }
		 
		 else 
		 
		 return rect;
		
	}
	
	
public static RectF expandRectF(RectF rect, float scale, Rect limit){
		
		RectF result=new RectF();
		
		 if (scale != 1.0f) {
			 result.left = rect.left +(int)(rect.width()*  (1.0-scale)*0.5) ;
			 result.top =rect.top +(int)(rect.height()*  (1.0-scale)*0.5);
			 result.right = rect.right -(int)(rect.width()*  (1.0-scale)*0.5) ;
			 result.bottom = rect.bottom  -(int)(rect.height()*  (1.0-scale)*0.5) ;
			 
			 result.left=Math.max( result.left, limit.left);
			 result.top=Math.max( result.top, limit.top);
			 result.right=Math.min( result.right, limit.right);
			 result.bottom=Math.min( result.bottom, limit.bottom);
		
			 return result;
		 }
		 
		 else 
		 
		 return rect;
		
	}

public static RectF expandRectF(RectF rect, double scaleX,double scaleY, Rect limit){
	
	RectF result=new RectF();
	
	 if (scaleX != 1.0f || scaleY != 1.0f) {
		 result.left = (float) (rect.left +(rect.height()*  (1.0-scaleX)*0.5)) ;
		 result.top =(float) (rect.top +(rect.height()*  (1.0-scaleY)*0.5));
		 result.right = (float) (rect.right -(rect.height()*  (1.0-scaleX)*0.5)) ;
		 result.bottom = (float) (rect.bottom  -(rect.height()*  (1.0-scaleY)*0.5)) ;
		 
		 result.left=Math.max( result.left, limit.left);
		 result.top=Math.max( result.top, limit.top);
		 result.right=Math.min( result.right, limit.right);
		 result.bottom=Math.min( result.bottom, limit.bottom);
	
		 return result;
	 }
	 
	 else 
	 
	 return rect;
	
}

public static RectF scaleRectF(RectF rect, double scaleX,double scaleY){
	
	RectF result=new RectF();
	
	 if (scaleX != 1.0f || scaleY != 1.0f) {
		 result.left = (float) (rect.left*scaleX) ;
		 result.top =(float) (rect.top *scaleY);
		 result.right = (float) (rect.right*scaleX) ;
		 result.bottom = (float) (rect.bottom * scaleY) ;
		 
		
	
		 return result;
	 }
	 
	 else 
	 
	 return rect;
	
}
public static Rect scaleRect(Rect rect, double scaleX,double scaleY){
	
	Rect result=new Rect();
	
	 if (scaleX != 1.0f || scaleY != 1.0f) {
		 result.left = (int) (rect.left*scaleX) ;
		 result.top =(int) (rect.top *scaleY);
		 result.right = (int) (rect.right*scaleX) ;
		 result.bottom = (int) (rect.bottom * scaleY) ;
		 
		
	
		 return result;
	 }
	 
	 else 
	 
	 return rect;
	
}

}
