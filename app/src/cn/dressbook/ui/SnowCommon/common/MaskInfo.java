package cn.dressbook.ui.SnowCommon.common;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;

import android.graphics.Bitmap;

public class MaskInfo {
	private Bitmap maskImage;
	private Point maskCenter;
	private Rect maskRect;
	private Mat maskMat;
	private Mat weightMat;
	public MaskInfo(){
		
	}
	
	public Bitmap getMaskImage() {
		return maskImage;
	}
	public void setMaskImage(Bitmap maskImage) {
		this.maskImage = maskImage;
	}

	public Point getMaskCenter() {
		return maskCenter;
	}

	public void setMaskCenter(Point maskCenter) {
		this.maskCenter = maskCenter;
	}

	public Mat getMaskMat() {
		return maskMat;
	}

	public void setMaskMat(Mat maskMat) {
		this.maskMat = maskMat;
	}

	public Rect getMaskRect() {
		return maskRect;
	}

	public void setMaskRect(Rect maskRect) {
		this.maskRect = maskRect;
	}

	public Mat getWeightMat() {
		return weightMat;
	}

	public void setWeightMat(Mat weightMat) {
		this.weightMat = weightMat;
	}
	
}
