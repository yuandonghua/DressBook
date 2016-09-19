package cn.dressbook.ui.SnowCommon.common;

import android.graphics.drawable.AnimationDrawable;

public class AnimationDrawableModel {
	
	private AnimationDrawable animation;
	
	private int frameCount;
	private int start;
	private int end;
	private int filterType;
	
	
	public AnimationDrawableModel(){
		
	}
	
	public AnimationDrawable getAnimation() {
		return animation;
	}
	public void setAnimation(AnimationDrawable animation) {
		this.animation = animation;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getFrameCount() {
		return frameCount;
	}

	public void setFrameCount(int frameCount) {
		this.frameCount = frameCount;
	}

	public int getFilterType() {
		return filterType;
	}

	public void setFilterType(int filterType) {
		this.filterType = filterType;
	}
}
