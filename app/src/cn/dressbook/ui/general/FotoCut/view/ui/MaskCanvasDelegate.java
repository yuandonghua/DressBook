package cn.dressbook.ui.general.FotoCut.view.ui;

import android.view.View;


public interface MaskCanvasDelegate {

	
	
	public boolean isBusy();
	
	public View getViewContainer();
	

	public void setViewContainerTranslate(float x, float y);
	
	public void resetViewContainerTranslate();
	
	
}
