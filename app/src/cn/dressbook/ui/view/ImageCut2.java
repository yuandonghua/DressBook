package cn.dressbook.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
/**
 * @description: 
 * @author: 袁东华
 * @time: 2015-8-11下午9:04:59
 */
public class ImageCut2 extends ImageView{
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    /**位图对象*/
    private Bitmap bitmap = null;
    /** 屏幕的分辨率*/
    //private DisplayMetrics dm;

    /** 最小缩放比例*/
    float minScaleR = 1.0f;
    
    /** 最大缩放比例*/
    static final float MAX_SCALE = 15f;

    /** 初始状态*/
    static final int NONE = 0;
    /** 拖动*/
    static final int DRAG = 1;
    /** 缩放*/
    static final int ZOOM = 2;
    
    /** 当前模式*/
    int mode = NONE;

    /** 存储float类型的x，y值，就是你点下的坐标的X和Y*/
    PointF prev = new PointF();
    PointF mid = new PointF();
    float dist = 1f;
    
    public ImageCut2(Context context) {
		super(context);
		setupView();
	}
	
	public ImageCut2(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupView();
	}
	
	
	@SuppressLint("ClickableViewAccessibility")
	public void setupView(){
		//获取屏幕分辨率,需要根据分辨率来使用图片居中
		//dm = context.getResources().getDisplayMetrics();
		
		//根据MyImageView来获取bitmap对象
		BitmapDrawable bd = (BitmapDrawable)this.getDrawable();
		if(bd != null){
			bitmap = bd.getBitmap();
		}
		
		//设置ScaleType为ScaleType.MATRIX，这一步很重要
		this.setScaleType(ScaleType.MATRIX);
		this.setImageBitmap(bitmap);
		
		//bitmap为空就不调用center函数
		if(bitmap != null){
			center(true, true);
		}
		this.setImageMatrix(matrix);
		this.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				 switch (event.getAction() & MotionEvent.ACTION_MASK) {
			        // 主点按下
			        case MotionEvent.ACTION_DOWN:
			            savedMatrix.set(matrix);
			            prev.set(event.getX(), event.getY());
			            mode = DRAG;
			            break;
			        // 副点按下
			        case MotionEvent.ACTION_POINTER_DOWN:
			            dist = spacing(event);
			            // 如果连续两点距离大于10，则判定为多点模式
			            if (spacing(event) > 10f) {
			                savedMatrix.set(matrix);
			                midPoint(mid, event);
			                mode = ZOOM;
			            }
			            break;
			        case MotionEvent.ACTION_UP:{
			        	break;
			        }
			        case MotionEvent.ACTION_POINTER_UP:
			            mode = NONE;
			            //savedMatrix.set(matrix);
			            break;
			        case MotionEvent.ACTION_MOVE:
			            if (mode == DRAG) {
			                matrix.set(savedMatrix);
			                matrix.postTranslate(event.getX() - prev.x, event.getY()
			                        - prev.y);
			            } else if (mode == ZOOM) {
			                float newDist = spacing(event);
			                if (newDist > 10f) {
			                    matrix.set(savedMatrix);
			                    float tScale = newDist / dist;
			                    matrix.postScale(tScale, tScale, mid.x, mid.y);
			                }
			            }
			            break;
			        }
				    ImageCut2.this.setImageMatrix(matrix);
//			        CheckView();
			        invalidate();
			        return true;
			}
		});
	}
	
	
    /**
     * 横向、纵向居中
     */
    protected void center(boolean horizontal, boolean vertical) {
        Matrix m = new Matrix();
        m.set(matrix);
        RectF rect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        m.mapRect(rect);

        float height = rect.height();
        float width = rect.width();

        float deltaX = 0, deltaY = 0;

        if (vertical) {
            // 图片小于屏幕大小，则居中显示。大于屏幕，上方留空则往上移，下方留空则往下移
            int screenHeight = getHeight();
            if (height < screenHeight) {
                deltaY = (screenHeight - height) / 2 - rect.top;
            } else if (rect.top > 0) {
                deltaY = -rect.top;
            } else if (rect.bottom < screenHeight) {
                deltaY = this.getHeight() - rect.bottom;
            }
        }

        if (horizontal) {
            int screenWidth = getWidth();
            if (width < screenWidth) {
                deltaX = (screenWidth - width) / 2 - rect.left;
            } else if (rect.left > 0) {
                deltaX = -rect.left;
            } else if (rect.right < screenWidth) {
                deltaX = screenWidth - rect.right;
            }
        }
        matrix.postTranslate(deltaX, deltaY);
    }

    
    
    
    /**
     * 两点的距离
     */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * 两点的中点
     */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }
}
