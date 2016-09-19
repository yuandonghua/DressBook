package cn.dressbook.ui.SnowCommon.common;

import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Point;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class DrawWidgetData {
	private Bitmap resultImage;
	private Rect rect;
	private List<Point> pointList;
	private Mat maskMat;
	
	public DrawWidgetData(){
		
	}

	public Bitmap getResultImage() {
		return resultImage;
	}

	public void setResultImage(Bitmap resultImage) {
		this.resultImage = resultImage;
	}

	public Rect getRect() {
		return rect;
	}

	public void setRect(Rect rect) {
		this.rect = rect;
	}

	public List<Point> getPointList() {
		return pointList;
	}

	public void setPointList(List<Point> pointList) {
		this.pointList = pointList;
	}

	public Mat getMaskMat() {
		return maskMat;
	}

	public void setMaskMat(Mat maskMat) {
		this.maskMat = maskMat;
	}
}
