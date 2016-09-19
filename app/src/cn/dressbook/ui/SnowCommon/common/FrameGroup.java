package cn.dressbook.ui.SnowCommon.common;

import java.util.List;

import android.graphics.Bitmap;

public class FrameGroup {
	
	public int getExtraPosition() {
		return extraPosition;
	}

	public void setExtraPosition(int extraPosition) {
		this.extraPosition = extraPosition;
	}

	int extraPosition=0;
	List<Bitmap> frameBitmaps;
	public List<Bitmap> getFrameBitmaps() {
		return frameBitmaps;
	}

	public void setFrameBitmaps(List<Bitmap> frameBitmaps) {
		this.frameBitmaps = frameBitmaps;
	}

	public List<Bitmap> getExtraBitmaps() {
		return extraBitmaps;
	}

	public void setExtraBitmaps(List<Bitmap> extraBitmaps) {
		this.extraBitmaps = extraBitmaps;
	}

	List<Bitmap> extraBitmaps;
	
	public FrameGroup(){
		
	}
	
	public void clear(){
		if(frameBitmaps!=null){
			for(Bitmap im:frameBitmaps){
				im.recycle();
			}
			frameBitmaps.clear();
		}
		if(extraBitmaps!=null){
			for(Bitmap im:extraBitmaps){
				im.recycle();
			}
			extraBitmaps.clear();
		}
	}
	
}
