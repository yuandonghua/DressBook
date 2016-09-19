package cn.dressbook.ui.SnowCommon.common;

import org.opencv.core.Mat;

import android.graphics.Bitmap;

public class CanvasModel {
	private int canvasWidth;
	private int canvasHeight;
	private float minX;
	private float maxX;
	private float minY;
	private float maxY;
	private Bitmap canvasBitmap;
	private Mat oMask;
	

	public int getCanvasHeight() {
		return canvasHeight;
	}


	public void setCanvasHeight(int canvasHeight) {
		this.canvasHeight = canvasHeight;
	}


	public float getMinX() {
		return minX;
	}


	public void setMinX(float minX) {
		this.minX = minX;
	}


	public float getMaxX() {
		return maxX;
	}


	public void setMaxX(float maxX) {
		this.maxX = maxX;
	}


	public float getMinY() {
		return minY;
	}


	public void setMinY(float minY) {
		this.minY = minY;
	}


	public float getMaxY() {
		return maxY;
	}


	public void setMaxY(float maxY) {
		this.maxY = maxY;
	}


	public CanvasModel(int w, int h, float minx, float maxx, float miny, float maxy){
		this.canvasWidth=w;
		this.canvasHeight=h;
		this.minX=minx;
		this.maxX=maxx;
		this.minY=miny;
		this.maxY=maxy;
	}


	public int getCanvasWidth() {
		return canvasWidth;
	}


	public void setCanvasWidth(int canvasWidth) {
		this.canvasWidth = canvasWidth;
	}


	public Bitmap getCanvasBitmap() {
		return canvasBitmap;
	}


	public void setCanvasBitmap(Bitmap canvasBitmap) {
		this.canvasBitmap = canvasBitmap;
	}


	public Mat getoMask() {
		return oMask;
	}


	public void setoMask(Mat oMask) {
		this.oMask = oMask;
	}
}
