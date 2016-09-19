package cn.dressbook.ui.face.data;

import android.graphics.Matrix;

public abstract class FCGestureData {

	public Matrix matrix = new Matrix();

	public FCGestureData() {

	}

	public abstract void setDefaultMatrix();

}
